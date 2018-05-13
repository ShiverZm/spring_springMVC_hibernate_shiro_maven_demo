package cn.yznu.demo.controller.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.yznu.demo.pojo.auth.Role;
import cn.yznu.demo.pojo.auth.User;
import cn.yznu.demo.service.auth.UserInfoService;
import cn.yznu.demo.service.auth.impl.*;
import cn.yznu.demo.utils.auth.PasswordHelper;
import cn.yznu.demo.utils.auth.UserUtil;

@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController {

  @Autowired
  UserInfoService userInfoService;

  /**
   * 跳转到首页
   * 
   * @return
   */
  @RequestMapping(params = "index")
  public ModelAndView index() {
    return new ModelAndView("index");
  }

  /**
   * 跳转到个人中心
   * 
   * @return
   */
  @RequestMapping(params = "userindex")
  public ModelAndView userindex() {
    return new ModelAndView("/authc/user/index");
  }

  /**
   * 跳转到购物车
   * 
   * @return
   */
  @RequestMapping(params = "shopCar")
  public ModelAndView login() {
    return new ModelAndView("/authc/shopCar");
  }

  /**
   * 跳转到登录页
   * 
   * @return
   */
  @RequestMapping(params = "register")
  public ModelAndView register() {
    return new ModelAndView("page/register");
  }

  /**
   * 用户登录
   * 
   * @param username
   * @param password
   * @return
   */
  @RequestMapping(params = "doLogin")
  public ModelAndView doLogin(@RequestParam("username") String username,
      @RequestParam("password") String password) {
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    Subject subject = SecurityUtils.getSubject();
    try {
      subject.login(token);
    } catch (IncorrectCredentialsException ice) {
      // 捕获密码错误异常
      ModelAndView mv = new ModelAndView("error");
      mv.addObject("message", "password error!");
      return mv;
    } catch (UnknownAccountException uae) {
      // 捕获未知用户名异常
      ModelAndView mv = new ModelAndView("error");
      mv.addObject("message", "username error!");
      return mv;
    } catch (ExcessiveAttemptsException eae) {
      // 捕获错误登录过多的异常
      ModelAndView mv = new ModelAndView("error");
      mv.addObject("message", "times error");
      return mv;
    }
    User user = userInfoService.findByUsername(username);
    subject.getSession().setAttribute("user", user);
    // 顾客 重定向 到 个人中心
    String url = "redirect:/userInfo.htm?userindex";

    List<Role> roles = userInfoService.findRoles(username);
    for (Role role : roles) {
      if (role.getRoleName().equals("admin")) {
        // 管理员 重定向 到 后台首页
        url = "redirect:/authc/admin/index.html";
        break;
      }
    }
    return new ModelAndView(url);
  }


  /**
   * 用户注册
   * 
   * @param username
   * @param password
   * @return
   */
  @RequestMapping(params = "doRegister")
  @ResponseBody
  public Boolean doRegister(@RequestParam("username") String username,
      @RequestParam("password") String password) {
    System.out.println("获取到用户名：" + username);
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    PasswordHelper passwordHelper = new PasswordHelper();
    // 对用户秘密 进行加密处理 并保存
    User newUser = passwordHelper.encryptPassword(user);
    userInfoService.save(newUser);
    Role role = userInfoService.getSingleByHQL("from Role where roleName = 'Customer'");
    userInfoService.set_Role(newUser, role);
    // 验证用户是否提交到数据库
    return userInfoService.verifyUser(newUser);
  }

  /**
   * 用户 修改 密码，对用户 旧密码进行验证
   * 
   * @param password
   * @return
   */
  @RequestMapping(params = "doPassword")
  @ResponseBody
  public Boolean doPassword(@RequestParam("password") String password) {
    return userInfoService.authentication(password);
  }

  /**
   * 修改用户密码
   * 
   * @param password
   * @return
   */
  @RequestMapping(params = "updatePassword")
  @ResponseBody
  public Boolean updatePassword(@RequestParam("password") String password) {
    User user = new UserUtil().get_User();
    Boolean boo = true;
    // 判断 新密码 与 旧密码 是否相同
    if (userInfoService.authentication(password)) {
      boo = false;
    } else {
      user.setPassword(password);
      PasswordHelper passwordHelper = new PasswordHelper();
      // 对用户秘密 进行加密处理 并保存
      User newUser = passwordHelper.encryptPassword(user);
      userInfoService.update(newUser);
      new UserUtil().remove_User();
    }
    return boo;
  }

}
