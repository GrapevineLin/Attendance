package edu.service.impl.impl;

import edu.bean.Department;
import edu.dao.DepartmentDao;
import edu.dao.impl.DepartmentDaoImpl;
import edu.service.impl.DepartmentService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentDao departmentDao=new DepartmentDaoImpl();

    @Override
    public List<Department> list() {
        return departmentDao.list();
    }

    @Override
    public Long insert(Department bean) {
        return departmentDao.insert(bean);
    }

    @Override
    public Long delete(Long id) {
        return departmentDao.delete(id);
    }

    @Override
    public Long update(Department bean) {
        return departmentDao.update(bean);
    }

    @Override
    public Department load(Long id) {
        return departmentDao.load(id);
    }

    @Override
    public Department loadByName(String name) {
        return departmentDao.loadByName(name);
    }

    @Override
    public Long count() {
        return departmentDao.count();
    }

    @Override
    public List<Department> pager(Long pageNum, Long pageSize) {
        return departmentDao.pager(pageNum,pageSize);
    }

    @Override
    public Long countByName(String name) {
        return departmentDao.countByName(name);
    }

    @Override
    public List<Department> pagerByName(String name, Long pageNum, Long pageSize) {
        return departmentDao.pagerByName(name,pageNum,pageSize);
    }

}