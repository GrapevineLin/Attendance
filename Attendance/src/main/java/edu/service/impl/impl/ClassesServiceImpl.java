package edu.service.impl.impl;

import edu.bean.Classes;
import edu.dao.ClassesDao;
import edu.dao.impl.ClassesDaoImpl;
import edu.service.impl.ClassesService;

import java.util.List;

public class ClassesServiceImpl implements ClassesService {

    private static ClassesDao classesDao = new ClassesDaoImpl();

    @Override
    public List<Classes> list() {
        return classesDao.list();
    }

    @Override
    public Long insert(Classes bean) {
        return classesDao.insert(bean);
    }

    @Override
    public Long delete(Long id) {
        return classesDao.delete(id);
    }

    @Override
    public Long update(Classes bean) {
        return classesDao.update(bean);
    }

    @Override
    public Classes load(Long id) {
        return classesDao.load(id);
    }

    @Override
    public Classes loadByCode(String code) {
        return classesDao.loadByCode(code);
    }

    @Override
    public Classes loadByName(String name) {
        return classesDao.loadByCode(name);
    }

    @Override
    public Long count() {
        return classesDao.count();
    }

    @Override
    public List<Classes> pager(Long pageNum, Long pageSize) {
        return classesDao.pager(pageNum, pageSize);
    }

    @Override
    public Long countByName(String name) {
        return classesDao.countByName(name);
    }

    @Override
    public List<Classes> pagerByName(String name, Long pageNum, Long pageSize) {
        return classesDao.pagerByName(name, pageNum, pageSize);
    }
}
