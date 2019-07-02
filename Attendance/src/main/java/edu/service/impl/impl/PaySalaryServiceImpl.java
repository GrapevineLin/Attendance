package edu.service.impl.impl;

import edu.bean.PaySalary;
import edu.bean.PunchCard;
import edu.bean.RepairCard;
import edu.dao.PaySalaryDao;
import edu.dao.impl.PaySalaryDaoImpl;
import edu.service.impl.PaySalaryService;
import edu.service.impl.PunchCardService;
import edu.service.impl.RepairCardService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PaySalaryServiceImpl implements PaySalaryService {

    private static PaySalaryDao paySalaryDao = new PaySalaryDaoImpl();

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
        return paySalaryDao.pager(pageNum, pageSize);
    }

    @Override
    public Long countByName(String name) {
        return paySalaryDao.countByName(name);
    }

    @Override
    public List<PaySalary> pagerByName(String name, Long pageNum, Long pageSize) {
        return paySalaryDao.pagerByName(name, pageNum, pageSize);
    }

    //返回应得薪水
    public Long getSalary(String code, Date beginDate, Date endDate) throws ParseException {
        Long salary = 0L;//薪水

        //定义变量
        SimpleDateFormat daySfd = new SimpleDateFormat("yyyy-MM-dd");
        PunchCardService punchCardService = new PunchCardServiceImpl();
        RepairCardService repairCardService = new RepairCardServiceImpl();
        List<PunchCard> pBean = punchCardService.pagerByCode(code, 0L, 100L);
        List<RepairCard> rBean = repairCardService.pagerByName(code, 0L, 100L);
        List<Date> time = new ArrayList<Date>();
        String date1 = null, date2 = null;
        Long time1 = 0L, time2 = 0L;
        Long day = 0L,sumTime=0L;

        //往time里添加PunchCard日期
        for (PunchCard item :
                pBean) {
            time.add(item.getDate());
        }
        //往time里添加RepairCard日期
        for (RepairCard item :
                rBean) {
            time.add(item.getDate());
        }

        /*判断日期是否在开始时间和结束时间中
         * 如果是则判断日期是否相同
         * 相同则计算当日的出勤时间
         * 最后计算薪水值*/
        for (int i = 0; i < time.size(); i++) {
            if (belongCalendar(time.get(i), beginDate, endDate)) {
                for (int j = i+1; j < time.size(); j++) {
                    if (belongCalendar(time.get(j), beginDate, endDate)) {
                        date1 = daySfd.format(time.get(i));
                        date2 = daySfd.format(time.get(j));
                        if (date1.equals(date2)) {
                            day++;
                            time1 = time.get(i).getTime();
                            time2 = time.get(j).getTime();
                            //计算实际出勤时间
                            sumTime += Math.abs(time1 - time2);
                        }
                    }
                }
            }
        }
        //计算总薪水
        salary = (200*day)*sumTime / (3600000 * day * 8);

        return salary;
    }

    //判断日期是否在区间内
    public boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}