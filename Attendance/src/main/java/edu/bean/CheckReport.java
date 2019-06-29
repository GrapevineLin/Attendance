package edu.bean;

import java.util.Date;

public class CheckReport {
    private Long id;
    private String empCode;
    private String empName;
    private Date am;
    private Date pm;
    private String attendance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getAm() {
        return am;
    }

    public void setAm(Date am) {
        this.am = am;
    }

    public Date getPm() {
        return pm;
    }

    public void setPm(Date pm) {
        this.pm = pm;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }



}
