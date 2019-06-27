package edu.service.impl.impl;

import edu.bean.Employee;
import edu.dao.EmployeeDao;
import edu.dao.impl.EmployeeDaoImpl;
import edu.service.impl.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDao employeeDao = new EmployeeDaoImpl();
    @Override
    public List<Employee> list() {
        return employeeDao.list();
    }

    @Override
    public Long insert(Employee bean) {
        return employeeDao.insert(bean);
    }

    @Override
    public Long delete(Long id) {
        return employeeDao.delete(id);
    }

    @Override
    public Long update(Employee bean) {
        return employeeDao.update(bean);
    }

    @Override
    public Employee load(Long id) {
        return employeeDao.load(id);
    }

    @Override
    public Employee loadByName(String name) {
        return employeeDao.loadByName(name);
    }

    @Override
    public Long count() {
        return employeeDao.count();
    }

    @Override
    public List<Employee> pager(Long pageNum, Long pageSize) {
        return employeeDao.pager(pageNum,pageSize);
    }

    @Override
    public Long countByName(String name) {
        return employeeDao.countByName(name);
    }

    @Override
    public List<Employee> pagerByName(String name, Long pageNum, Long pageSize) {
        return employeeDao.pagerByName(name,pageNum,pageSize);
    }
}
