package cn.itcast.domain.dept;

import java.io.Serializable;

public class Dept implements Serializable {
    private String id;
    private String deptName;
    private Dept parent;
    private Integer state;
    private String companyId;
    private String companyName;

    public Dept(String deptId, String deptName, Dept parent, Integer state, String companyId, String companyName) {
        this.id = deptId;
        this.deptName = deptName;
        this.parent = parent;
        this.state = state;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Dept() {
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptId='" + id + '\'' +
                ", deptName='" + deptName + '\'' +
                ", parentId='" + parent + '\'' +
                ", state='" + state + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String deptId) {
        this.id = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Dept getParent() {
        return parent;
    }

    public void setParent(Dept parentId) {
        this.parent = parentId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
}
