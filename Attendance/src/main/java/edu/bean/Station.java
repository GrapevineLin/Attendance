package edu.bean;

public class Station {
    private Long jobId;//岗位ID
    private String jobCode;//岗位编码
    private String jobName;//岗位名称
    private String dep;//所在部门
    private String dirSup;//直接上级
    private String jobCat;//岗位类名

    public String getJobDes() {
        return jobDes;
    }

    public void setJobDes(String jobDes) {
        this.jobDes = jobDes;
    }

    private String jobDes;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getDirSup() {
        return dirSup;
    }

    public void setDirSup(String dirSup) {
        this.dirSup = dirSup;
    }

    public String getJobCat() {
        return jobCat;
    }

    public void setJobCat(String jobCat) {
        this.jobCat = jobCat;
    }
}