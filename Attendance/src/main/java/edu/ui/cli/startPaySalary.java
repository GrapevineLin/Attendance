package edu.ui.cli;

import edu.bean.PaySalary;
import edu.bean.RepairCard;
import edu.service.impl.PaySalaryService;
import edu.service.impl.RepairCardService;
import edu.service.impl.impl.PaySalaryServiceImpl;
import edu.service.impl.impl.RepairCardServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class startPaySalary {

    public static void main(String[] args) throws ParseException {
        List<RepairCard> list=repairCardService.pagerByName("admin",1L,10L);
        for (RepairCard item:
             list) {
            System.out.println(item.getDate());
        }
    }

    private static RepairCardService repairCardService = new RepairCardServiceImpl();
    private static PaySalaryService paySalaryService = new PaySalaryServiceImpl();

    private static void list() {
        List<PaySalary> list = paySalaryService.pagerByName("admin",1L,100L);
        for (PaySalary item : list) {
            System.out.println(item.getPayId() + " " + item.getEmpId() + " " + item.getBeginDate() + " " + item.getEndDate() + " " + item.getSalary());
        }
    }

    private static void load() {
        PaySalary item = paySalaryService.load(7L);
        System.out.println(item.getPayId() + " " + item.getEmpId() + " " + item.getBeginDate() + " " + item.getEndDate() + " " + item.getSalary());
    }

    private static void update() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date1=sdf.parse("2019-5-20 00:00:00");
        Date date2=sdf.parse("2019-6-20 00:00:00");

        PaySalary bean=new PaySalary();
        bean.setPayId(66L);
        bean.setBeginDate(date1);
        bean.setEndDate(date2);
        bean.setEmpId(2L);
        bean.setSalary(596L);
        paySalaryService.insert(bean);
        List<PaySalary> list = paySalaryService.list();
        for (PaySalary item : list) {
            System.out.println(item.getPayId() + " " + item.getEmpId() + " " + item.getBeginDate() + " " + item.getEndDate() + " " + item.getSalary());
        }
    }

    public static void isBelong(){

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("06:00");
            endTime = df.parse("22:00");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean flag = belongCalendar(now, beginTime, endTime);
        System.out.println(flag);
    }


    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
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

}