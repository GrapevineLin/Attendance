package edu.service.impl.impl;

import edu.bean.RepairCard;
import edu.dao.RepairCardDao;
import edu.dao.impl.RepairCardDaoImpl;
import edu.service.impl.RepairCardService;

import java.util.List;

public class RepairCardServiceImpl implements RepairCardService {
    private RepairCardDao repairCardDao = new RepairCardDaoImpl();

    @Override
    public List<RepairCard> list() {
        return repairCardDao.list();
    }

    @Override
    public Long insert(RepairCard bean) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return repairCardDao.delete(id);
    }

    @Override
    public Long update(RepairCard bean) {
        return null;
    }

    @Override
    public RepairCard load(Long id) {
        return null;
    }

    @Override
    public RepairCard loadByName(String name) {
        return null;
    }

    @Override
    public Long count() {
        return repairCardDao.count();
    }

    @Override
    public List<RepairCard> pager(Long pageNum, Long pageSize) {

        return repairCardDao.pager(pageNum, pageSize);
    }

    @Override
    public Long countByName(String name) {
        return null;
    }

    @Override
    public List<RepairCard> pagerByName(String name, Long pageNum, Long pageSize) {
        return null;
    }
}
