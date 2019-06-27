package edu.service.impl.impl;

import edu.bean.PunchCard;
import edu.dao.PunchCardDao;
import edu.dao.impl.PunchCardDaoImpl;
import edu.service.impl.PunchCardService;

import java.util.List;

public class PunchCardServiceImpl implements PunchCardService {
    private static PunchCardDao punchCardDao = new PunchCardDaoImpl();
    @Override
    public List<PunchCard> list() {
        return punchCardDao.list();
    }

    @Override
    public Long insert(PunchCard bean) {
        return punchCardDao.insert(bean);
    }



    @Override
    public PunchCard load(Long id) {
        return punchCardDao.load(id);
    }

    @Override
    public PunchCard loadByCode(String code) {
        return punchCardDao.loadByCode(code);
    }

    @Override
    public PunchCard loadByName(String name) {
        return punchCardDao.loadByName(name);
    }

    @Override
    public Long count() {
        return punchCardDao.count();
    }

    @Override
    public List<PunchCard> pager(Long pageNum, Long pageSize) {
        return punchCardDao.pager(pageNum, pageSize);
    }

    @Override
    public Long countByCode(String code) {
        return punchCardDao.countByCode(code);
    }

    @Override
    public List<PunchCard> pagerByCode(String code, Long pageNum, Long pageSize) {
        return punchCardDao.pagerByCode(code, pageNum, pageSize);
    }
}
