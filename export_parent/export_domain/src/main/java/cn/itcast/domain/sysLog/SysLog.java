package cn.itcast.domain.sysLog;

import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable {
    private String id;        //VARCHA R(40
    private String userName;        //varchar(30
    private String ip;        //varchaR(20
    private Date time;        //datetime
    private String method;        //VARchar(40
    private String action;        //varCHAR(40
    private String companyId;        //VARChAR(40

    @Override
    public String toString() {
        return "SysLog{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", ip='" + ip + '\'' +
                ", time=" + time +
                ", method='" + method + '\'' +
                ", action='" + action + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String companyName;        //VARCHAR(40


    public SysLog() {
    }
}
