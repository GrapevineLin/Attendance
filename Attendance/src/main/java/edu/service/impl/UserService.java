package edu.service.impl;


import edu.bean.Employee;
import edu.bean.User;

import java.util.List;

public interface UserService {
//    List<User> list();
    Long insert(Employee bean);
//    Long delete(Long id);
//    Long update(User bean);
//    User Load(Long id);
    User LoadByName(String name);
//    Long count();
//    List<User> pager(Long pageNum, Long pageSize);
//    Long countByName(String name);
//    List<User> pagerByName(String name, Long pageNum, Long pageSize);

}
