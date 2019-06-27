package edu.service.impl.impl;

import edu.bean.Station;
import edu.dao.StationDao;
import edu.dao.impl.StationDaoImpl;
import edu.service.impl.StationService;

import java.util.List;

public class StationServiceImpl implements StationService {

    StationDao stationDao = new StationDaoImpl();

    @Override
    public List<Station> list() {
        return stationDao.list();
    }

    @Override
    public Long insert(Station bean) {
        return stationDao.insert(bean);
    }

    @Override
    public Long delete(Long id) {
        return stationDao.delete(id);
    }

    @Override
    public Long update(Station bean) {
        return stationDao.update(bean);
    }

    @Override
    public Station load(Long id) {
        return stationDao.load(id);
    }

    @Override
    public Station loadByName(String name) {
        return stationDao.loadByName(name);
    }

    @Override
    public Long count() {
        return stationDao.count();
    }

    @Override
    public List<Station> pager(Long pageNum, Long pageSize) {
        return stationDao.pager(pageNum, pageSize);
    }

    @Override
    public Long countByName(String name) {
        return stationDao.countByName(name);
    }

    @Override
    public List<Station> pagerByName(String name, Long pageNum, Long pageSize) {
        return stationDao.pagerByName(name, pageNum, pageSize);
    }
}