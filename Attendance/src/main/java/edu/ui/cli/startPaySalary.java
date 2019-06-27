package edu.ui.cli;

import edu.bean.PaySalary;
import edu.service.impl.PaySalaryService;
import edu.service.impl.impl.PaySalaryServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class startPaySalary {

    public static void main(String[] args){
        System.out.println(paySalaryService.countByName("张三"));
        load();
    }

    private static PaySalaryService paySalaryService = new PaySalaryServiceImpl();

    private static void list() {
        List<PaySalary> list = paySalaryService.pagerByName("z",1L,1L);
        for (PaySalary item : list) {
            System.out.println(item.getPayId() + " " + item.getEmpId() + " " + item.getBeginDate() + " " + item.getEndDate() + " " + item.getSalary());
        }
    }

    private static void load() {
        PaySalary item = paySalaryService.loadByName("z");
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

}