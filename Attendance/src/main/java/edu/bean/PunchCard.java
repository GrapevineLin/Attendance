package edu.bean;

import java.util.Date;

public class PunchCard {
    private Long punchId;
    private String empCode;
    private String empName;
    private Date date;
    private String remark;

    public Long getPunchId() {
        return punchId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public Date getDate() {
        return date;
    }

    public String getRemark() {
        return remark;
    }

    public void setPunchId(Long punchId) {
        this.punchId = punchId;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
