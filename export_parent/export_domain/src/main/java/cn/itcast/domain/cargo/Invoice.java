package cn.itcast.domain.cargo;

import cn.itcast.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class Invoice extends BaseEntity implements Serializable {

    private String invoiceId;           //VARCHAR(40)                发票id
    private String scNo;            //varchar(100)               订单号
    private String blNo;            //varCHar(100)               提单号
    private String clause;          //varchaR(100)               条款
    private Integer state;           //int(11)               状态
    private String creatBy;             //vARCHAR(40)                创建人
    private String creatDept;           //vARCHAR(40)                创建部门
    private Date creatDate;           //datetime                 创建日期
    private Double amount;          //double                 总金额
    private String companyId;           //VARCHAr(100)       创建的企业Id
    private String companyName;             //varchar(100)   创建的企业名称

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getScNo() {
        return scNo;
    }

    public void setScNo(String scNo) {
        this.scNo = scNo;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreatBy() {
        return creatBy;
    }

    public void setCreatBy(String creatBy) {
        this.creatBy = creatBy;
    }

    public String getCreatDept() {
        return creatDept;
    }

    public void setCreatDept(String creatDept) {
        this.creatDept = creatDept;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", scNo='" + scNo + '\'' +
                ", blNo='" + blNo + '\'' +
                ", clause='" + clause + '\'' +
                ", state=" + state +
                ", creatBy='" + creatBy + '\'' +
                ", creatDept='" + creatDept + '\'' +
                ", creatDate=" + creatDate +
                ", amount=" + amount +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
