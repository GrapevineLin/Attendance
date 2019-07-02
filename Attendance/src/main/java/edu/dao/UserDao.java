package edu.dao;


import edu.bean.Employee;
import edu.bean.User;

import java.util.List;

public interface UserDao {
    //    List<UserDao> list();
    Long insert(Employee bean);
//    Long delete(Long id);
//    Long update(UserDao bean);
//    UserDao Load(Long id);
    User LoadByName(String name);
//    Long count();
//    List<UserDao> pager(Long pageNum, Long pageSize);
//    Long countByName(String name);
//    List<UserDao> pagerByName(String name, Long pageNum, Long pageSize);

}
