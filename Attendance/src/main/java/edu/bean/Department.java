package edu.bean;

public class Department {
    private Long depId;//部门id
    private String depCode;//部门编码
    private String depName;//部门名称
    private String depHead;//部门负责人
    private String depResp;//部门描述
    private Long supDepId;//上级部门ID

    private String supDepName;//上级部门名

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