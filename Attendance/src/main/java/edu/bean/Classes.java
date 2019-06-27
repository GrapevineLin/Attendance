package edu.bean;

import java.sql.Time;
import java.util.Date;

public class Classes {
    private Long classId;
    private String classCode;
    private String className;
    private Time am;
    private Time pm;
    private String remark;

    public Long getClassId() {
        return classId;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getClassName() {
        return className;
    }

    public Time getAm() {
        return am;
    }

    public Time getPm() {
        return pm;
    }

    public String getRemark() {
        return remark;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setAm(Time am) {
        this.am = am;
    }

    public void setPm(Time pm) {
        this.pm = pm;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
