package edu.service.impl.impl;

import edu.bean.EmpDetail;
import edu.dao.EmpDetailDao;
import edu.dao.impl.EmpDetailDaoImpl;
import edu.service.impl.EmpDetailService;

public class EmpDetailServiceImpl implements EmpDetailService {
    private static EmpDetailDao empDetailDao = new EmpDetailDaoImpl();
    @Override
    public EmpDetail getDetail(String name) {
        return empDetailDao.getDetail(name);
    }
}
