package edu.dao;

import edu.bean.PaySalary;

import java.util.List;

public interface PaySalaryDao {
    List<PaySalary> list();

    Long insert(PaySalary bean);

    Long delete(Long id);

    Long update(PaySalary bean);

    PaySalary load(Long id);

    PaySalary loadByName(String name);

    Long count();

    List<PaySalary> pager(Long pageNum, Long pageSize);

    Long countByName(String name);

    List<PaySalary> pagerByName(String name, Long pageNum, Long pageSize);
}