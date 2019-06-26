package edu.bean;

public class Department {
    private Long depId;
    private String depCode;
    private String depName;
    private String depHead;
    private String depResp;
    private Long supDepId;

    private String supDepName;

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepHead() {
        return depHead;
    }

    public void setDepHead(String depHead) {
        this.depHead = depHead;
    }

    public String getDepResp() {
        return depResp;
    }

    public void setDepResp(String depResp) {
        this.depResp = depResp;
    }

    public Long getSupDepId() {
        return supDepId;
    }

    public void setSupDepId(Long supDepId) {
        this.supDepId = supDepId;
    }

    public String getSupDepName() {
        return supDepName;
    }

    public void setSupDepName(String supDepName) {
        this.supDepName = supDepName;
    }
}