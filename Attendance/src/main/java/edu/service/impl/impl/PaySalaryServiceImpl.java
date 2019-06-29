package edu.service.impl.impl;

import edu.bean.PaySalary;
import edu.bean.PunchCard;
import edu.dao.PaySalaryDao;
import edu.dao.impl.PaySalaryDaoImpl;
import edu.service.impl.PaySalaryService;
import edu.service.impl.PunchCardService;

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
    public static Long getSalary(String code, Date beginDate, Date endDate) throws ParseException {
        Long salary = 0L;//薪水

        //定义变量
        SimpleDateFormat daySfd = new SimpleDateFormat("yyyy-MM-dd");
        PunchCardService punchCardService = new PunchCardServiceImpl();
        List<PunchCard> bean = punchCardService.pagerByCode(code, 0L, 100L);
        List<Date> time = new ArrayList<Date>();
        Long date1 = 0L, date2 = 0L;
        Long time1 = 0L, time2 = 0L;
        Long day = 0L;

        //望list里添加PunchCard日期
        for (PunchCard item :
                bean) {
            time.add(item.getDate());
        }

        /*判断日期是否在开始时间和结束时间中
        * 如果是则判断日期是否相同
        * 相同则计算当日的出勤时间
        * 最后计算薪水值*/
        for (int i = 0; i < time.size(); i++) {
            if (belongCalendar(time.get(i), beginDate, endDate)) {
                for (int j = i; j < time.size(); j++) {
                    if (belongCalendar(time.get(j), beginDate, endDate)) {
                        date1 = daySfd.parse(daySfd.format(time.get(i))).getTime();
                        date2 = daySfd.parse(daySfd.format(time.get(j))).getTime();
                        if (date1.equals(date2)) {
                            day++;
                            time1 = time.get(i).getTime();
                            time2 = time.get(j).getTime();
                            //应得薪水数*实际出勤时间/应该出勤时间
                            salary += 2000 * Math.abs(time1 - time2) / (3600000 * day * 8);
                        }
                    }
                }
            }
        }
        return salary;
    }

    //判断日期是否在区间内
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
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

    public static void main(String[] args) throws ParseException {
        PaySalary bean=paySalaryDao.loadByName("ad");
        System.out.println(getSalary(bean.getEmpCode(), bean.getBeginDate(), bean.getEndDate()));
    }
}