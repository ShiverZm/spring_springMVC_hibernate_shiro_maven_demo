package cn.yznu.demo.service.auth;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.yznu.demo.dao.IBaseDao;
import cn.yznu.demo.pojo.auth.Permission;
import cn.yznu.demo.pojo.auth.Role;
import cn.yznu.demo.pojo.auth.User;

public interface UserInfoService extends IBaseDao {

  /**
   * 根据 用户名 获取 角色 集
   * 
   * @param userName 用户名
   * @return 角色集
   */
  public List<Role> findRoles(String userName);

  /**
   * 根据 用户名 获取 权限集
   * 
   * @param userName 用户名
   * @return 权限 集
   */
  public List<Permission> findPermissions(String userName);


  /**
   * 根据 用户名 获取 用户
   * 
   * @param userName 用户名
   * @return 用户
   */
  public User findByUsername(String userName);

  /**
   * 给 用户 授予 角色
   * 
   * @param user 用户
   */
  public void set_Role(User user, Role role);

  /**
   * 通过 user 验证 数据库是否 存在
   * 
   * @param user
   * @return
   */
  public Boolean verifyUser(User user);

  /**
   * 将 暴露密码 加密 与用户秘密 匹配
   * 
   * @param decodePassword
   * @return
   */
  public Boolean authentication(String decodePassword);
}
