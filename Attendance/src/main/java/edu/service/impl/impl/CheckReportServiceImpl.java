package edu.service.impl.impl;

import edu.bean.CheckReport;
import edu.bean.PunchCard;
import edu.bean.RepairCard;
import edu.service.impl.CheckReportService;
import edu.service.impl.PunchCardService;
import edu.service.impl.RepairCardService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckReportServiceImpl implements CheckReportService {

    RepairCardService repairCardService = new RepairCardServiceImpl();
    PunchCardService punchCardService = new PunchCardServiceImpl();

    @Override
    public List<CheckReport> list() {

        List<CheckReport> cheList = new ArrayList<CheckReport>();
        RepairCard repairCard = new RepairCard();
        List<RepairCard> repList = repairCardService.list();
        PunchCard punchCard = new PunchCard();
        List<PunchCard> punList = punchCardService.list();

        //打卡
        for (PunchCard pun : punList) {
            int flag = 0;
            CheckReport checkReport = new CheckReport();
            for (CheckReport che : cheList) {
                System.out.println(che.getAm());
                if (pun.getEmpCode().equals(che.getEmpCode()) && (isTheSameDay(pun.getDate(), che.getAm()) || isTheSameDay(pun.getDate(), che.getPm()))) {
                    if (che.getAm() != null && pun.getDate().after(che.getAm())) {
                        che.setPm(pun.getDate());
                        flag = 1;
                        break;
                    } else if (che.getPm() != null && pun.getDate().before(che.getPm())) {
                        che.setAm(pun.getDate());
                        flag = 1;
                        break;
                    }
                }
            }
            if (flag == 0) {
                checkReport.setEmpCode(pun.getEmpCode());
                checkReport.setEmpName(pun.getEmpName());
                if (isMorning(pun.getDate())) {
                    checkReport.setAm(pun.getDate());
                } else {
                    checkReport.setPm(pun.getDate());
                }
                cheList.add(checkReport);
            }
        }
        //补卡
        for (RepairCard rep : repList) {
            int flag = 0;
            CheckReport checkReport = new CheckReport();
            for (CheckReport che : cheList) {
                if (rep.getEmpCode().equals(che.getEmpCode()) && (isTheSameDay(rep.getDate(), che.getAm()) || isTheSameDay(rep.getDate(), che.getPm()))) {
                    if (che.getAm() != null && rep.getDate().after(che.getAm())) {
                        che.setPm(rep.getDate());
                        flag = 1;
                        break;
                    } else if (che.getPm() != null && rep.getDate().before(che.getPm())) {
                        che.setAm(rep.getDate());
                        flag = 1;
                        break;
                    }
                }
            }
            if (flag == 0) {
                checkReport.setEmpCode(rep.getEmpCode());
                if (isMorning(rep.getDate())) {
                    checkReport.setAm(rep.getDate());
                } else {
                    checkReport.setPm(rep.getDate());
                }
                cheList.add(checkReport);
            }
        }

        //出勤情况
        for (CheckReport che : cheList) {
            Calendar cal = Calendar.getInstance();

            /*
            * 有时间*/
            if (che.getAm() != null && che.getPm() != null) {

                if (isBelong(che.getAm())) {
                    che.setAttendance("迟到");
                } else if (isBelong(che.getPm())) {
                    che.setAttendance("早退");
                } else {
                    che.setAttendance("正常");
                }
            } else {
                che.setAttendance("矿工");
            }
        }
        return cheList;
    }

    public static boolean isBelong(Date date) {

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(date));
            beginTime = df.parse("09:00");
            endTime = df.parse("17:00");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return belongCalendar(now, beginTime, endTime);

    }


    /**
     * 判断时间是否在时间段内
     *
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


    public static boolean isMorning(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour < 12) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isTheSameDay(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                    && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)));

        } else {
            return false;
        }

    }
}
