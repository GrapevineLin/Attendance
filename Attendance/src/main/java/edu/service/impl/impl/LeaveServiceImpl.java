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
        return null;
    }

    @Override
    public Long insert(Leave bean) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

    @Override
    public Long update(Leave bean) {
        return null;
    }

    @Override
    public Leave load(Long id) {
        return null;
    }

    @Override
    public Leave loadByName(String name) {
        return null;
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
        return null;
    }

    @Override
    public List<Leave> pagerByName(String name, Long pageNum, Long pageSize) {
        return null;
    }
}
