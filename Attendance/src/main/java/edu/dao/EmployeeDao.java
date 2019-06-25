package edu.dao;

import edu.bean.Employee;

import java.util.List;

public interface EmployeeDao {

    List<Employee> list();
    Long insert(Employee bean);
    Long delete(Long id);
    Long update(Employee bean);
    Employee load(Long id);
    Employee loadByName(String name);
    Long count();
    List<Employee> pager(Long pageNum, Long pageSize);
    Long countByName(String name);
    List<Employee> pagerByName(String name, Long pageNum, Long pageSize);

}
