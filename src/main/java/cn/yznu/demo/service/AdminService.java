package cn.yznu.demo.service;

import java.util.List;

import cn.yznu.demo.dao.IBaseDao;
import cn.yznu.demo.pojo.auth.User;

public interface AdminService extends IBaseDao {
  /**
   * 获取全部普通管理员
   * 
   * @return
   */
  public List<User> get_adminList();

}
