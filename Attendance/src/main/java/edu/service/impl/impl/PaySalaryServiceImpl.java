package edu.service.impl.impl;

import edu.bean.PaySalary;
import edu.dao.PaySalaryDao;
import edu.dao.impl.PaySalaryDaoImpl;
import edu.service.impl.PaySalaryService;

import java.util.List;

public class PaySalaryServiceImpl implements PaySalaryService {

    PaySalaryDao paySalaryDao=new PaySalaryDaoImpl();

    @Override
    public List<PaySalary> list() {
        return paySalaryDao.list();
    }

    @Override
    public Long insert(PaySalary bean) {
        return paySalaryDao.insert(bean);
    }

    @Override
    public Long delete(Long id) {
        return paySalaryDao.delete(id);
    }

    @Override
    public Long update(PaySalary bean) {
        return paySalaryDao.update(bean);
    }

    @Override
    public PaySalary load(Long id) {
        return paySalaryDao.load(id);
    }

    @Override
    public PaySalary loadByName(String name) {
        return paySalaryDao.loadByName(name);
    }

    @Override
    public Long count() {
        return paySalaryDao.count();
    }

    @Override
    public List<PaySalary> pager(Long pageNum, Long pageSize) {
        return paySalaryDao.pager(pageNum,pageSize);
    }

    @Override
    public Long countByName(String name) {
        return paySalaryDao.countByName(name);
    }

    @Override
    public List<PaySalary> pagerByName(String name, Long pageNum, Long pageSize) {
        return paySalaryDao.pagerByName(name,pageNum,pageSize);
    }
}