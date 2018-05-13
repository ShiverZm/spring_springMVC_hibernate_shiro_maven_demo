package cn.yznu.demo.utils.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.yznu.demo.pojo.auth.User;

public class UserUtil {

  
  public User get_User() {
    // TODO Auto-generated method stub
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getSession().getAttribute("user");   
    return user;
  }
  
  public void remove_User() {
    // TODO Auto-generated method stub
    Subject subject = SecurityUtils.getSubject();
    subject.getSession().removeAttribute("user"); 
  }
}
