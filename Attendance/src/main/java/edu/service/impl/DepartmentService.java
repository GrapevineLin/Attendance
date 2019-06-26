package edu.service.impl;

import edu.bean.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> list();

    Long insert(Department bean);

    Long delete(Long id);

    Long update(Department bean);

    Department load(Long id);

    Department loadByName(String name);

    Long count();

    List<Department> pager(Long pageNum, Long pageSize);

    Long countByName(String name);

    List<Department> pagerByName(String name, Long pageNum, Long pageSize);
}