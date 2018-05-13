package cn.yznu.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.demo.pojo.auth.Role;
import cn.yznu.demo.pojo.auth.User;
import cn.yznu.demo.service.AdminService;
import cn.yznu.demo.service.auth.UserInfoService;
import cn.yznu.demo.utils.auth.PasswordHelper;
import cn.yznu.demo.utils.auth.UserUtil;

@Controller
@RequestMapping(value = "/adminController")
public class AdminController {
  @Autowired
  AdminService adminService;
  @Autowired
  UserInfoService userInfoService;

  /**
   * 跳转到后台管理首页
   * 
   * @return
   */
  @RequestMapping(params = "index")
  public ModelAndView index() {
    return new ModelAndView("authc/admin/index");
  }

  /**
   * 通过session获取用户名
   * 
   * @return
   */
  @RequestMapping(params = "getUsername")
  @ResponseBody
  public String get_Username() {
    System.out.println("正在获取管理员名称。。。");
    return new UserUtil().get_User().getUsername();
  }

  /**
   * 超级管理员 获取 全部普通管理员
   * 
   * @return 
   */
  @RequestMapping(params = "get_adminList")
  public ModelAndView get_adminList(HttpServletRequest request) {
    List<User> adminList = adminService.get_adminList();
    System.out.println(adminList.size());
    request.setAttribute("adminList", adminList);
    return new ModelAndView("/authc/admin/member-list");
  }

  /**
   * 添加管理员
   * 
   * @return Boolean true 成功 ,false 失败
   */
  @RequestMapping(params = "addAdmin")
  @ResponseBody
  public Boolean add_manager(String username, String password) {
    Boolean bool = true;
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    PasswordHelper passwordHelper = new PasswordHelper();
    // 对用户秘密 进行加密处理 并保存
    User newUser = passwordHelper.encryptPassword(user);
    adminService.save(newUser);
    // 验证用户是否提交到数据库
    return userInfoService.verifyUser(newUser);
  }
}
