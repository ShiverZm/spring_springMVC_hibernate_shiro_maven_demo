package cn.yznu.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yznu.demo.dao.impl.BaseDaoImpl;
import cn.yznu.demo.pojo.auth.User;
import cn.yznu.demo.pojo.auth.UserRole;
import cn.yznu.demo.service.AdminService;
@Service
@Transactional
public class AdminServiceImpl extends BaseDaoImpl implements AdminService{

  @Override
  public List<User> get_adminList() {
    // TODO Auto-generated method stub
    String hql = " from UserRole where role.roleName ='customer'";
    List<UserRole> userRoles = getListByHQL(hql);
    List<User> users = new ArrayList<User>();
    for (UserRole userRole : userRoles) {
      users.add(userRole.getUser());
    }
    return users;
  }

}
