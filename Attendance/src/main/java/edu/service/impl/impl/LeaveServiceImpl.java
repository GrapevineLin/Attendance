package edu.service.impl.impl;

import edu.bean.Leave;
import edu.dao.LeaveDao;
import edu.dao.impl.LeaveDaoImpl;
import edu.service.impl.LeaveService;

import java.util.List;

public class LeaveServiceImpl implements LeaveService {
    LeaveDao leaveDao = new LeaveDaoImpl();

    @Override
    public List<Leave> list() {
        return leaveDao.list();
    }

    @Override
    public Long insert(Leave bean) {
        return leaveDao.insert(bean);
    }

    @Override
    public Long delete(Long id) {
        return leaveDao.delete(id);
    }

    @Override
    public Long update(Leave bean) {
        return leaveDao.update(bean);
    }

    @Override
    public Leave load(Long id) {
        return leaveDao.load(id);
    }

    @Override
    public Leave loadByName(String name) {
        return leaveDao.loadByName(name);
    }

    @Override
    public Long count() {
        return leaveDao.count();
    }

    @Override
    public List<Leave> pager(Long pageNum, Long pageSize) {

        return leaveDao.pager(pageNum, pageSize);
    }

    @Override
    public Long countByName(String name) {
        return leaveDao.countByName(name);
    }

    @Override
    public List<Leave> pagerByName(String name, Long pageNum, Long pageSize) {
        return leaveDao.pagerByName(name,pageNum,pageSize);
    }
}
