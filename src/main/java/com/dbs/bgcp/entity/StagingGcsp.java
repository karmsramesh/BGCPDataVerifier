package com.dbs.bgcp.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "staging_gcsp")
public class StagingGcsp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INXEXTR_ACC_OPEN_DATE")
    @Temporal(TemporalType.DATE)
    private Date accOpenDate;

    @Column(name = "INXEXTR_ACC_CLOSE_DATE")
    @Temporal(TemporalType.DATE)
    private Date accCloseDate;

    @Column(name = "INXEXTR_ACC_RESTARTED")
    private String accRestarted;

    @Column(name = "INXEXTR_ACC_TRANSFER")
    private String accTransfer;

    @Column(name = "INXEXTR_ACC_CLOSE_CODE")
    private String accCloseCode;

    @Column(name = "INXEXTR_STATUS_DATE")
    @Temporal(TemporalType.DATE)
    private Date statusDate;

    @Column(name = "INXEXTR_SINGLE_UNIT")
    private String singleUnit;

    @Column(name = "INXEXTR_CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "INXEXTR_AMOUNT_DUE_1")
    private BigDecimal amountDue1;

    @Column(name = "INXEXTR_AMOUNT_DUE_2")
    private BigDecimal amountDue2;

    @Column(name = "INXEXTR_AMOUNT_DUE_3")
    private BigDecimal amountDue3;

    @Column(name = "INXEXTR_AMOUNT_DUE_4")
    private BigDecimal amountDue4;

    @Column(name = "INXEXTR_AMOUNT_DUE_5")
    private BigDecimal amountDue5;

    @Column(name = "INXEXTR_AMOUNT_DUE_6")
    private BigDecimal amountDue6;

    @Column(name = "INXEXTR_AMOUNT_DUE_7")
    private BigDecimal amountDue7;

    @Column(name = "INXEXTR_AMOUNT_DUE_8")
    private BigDecimal amountDue8;

    @Column(name = "INXEXTR_ACC_STATUS")
    private String accStatus;

    @Column(name = "INXEXTR_LOAN_STATUS")
    private String loanStatus;

    @Column(name = "INXEXTR_LEDGER_BAL_IND")
    private String ledgerBalInd;

    @Column(name = "INXEXTR_CREDIT_AMT")
    private BigDecimal creditAmt;

    @Column(name = "INXEXTR_DELING_CTR_2")
    private Integer delinquencyCounter2;

    @Column(name = "INXEXTR_DELING_CTR_3")
    private Integer delinquencyCounter3;

    @Column(name = "INXEXTR_DELING_CTR_4")
    private Integer delinquencyCounter4;

    @Column(name = "INXEXTR_DELING_CTR_5")
    private Integer delinquencyCounter5;

    @Column(name = "INXEXTR_NO_LATE_PMT")
    private Integer noLatePayment;

    @Column(name = "INXEXTR_12_MONTH_USAGE_CODE")
    private String twelveMonthUsageCode;

    @Column(name = "INXEXTR_ACCT_OUTS_BAL")
    private BigDecimal acctOutsBalance;

    @Column(name = "INXEXTR_ACCT_CREDIT_LIMIT")
    private BigDecimal acctCreditLimit;

    @Column(name = "INXEXTR_ACCT_STAFF_LOAN_FLAG")
    private String acctStaffLoanFlag;

    // Getters and Setters
    public long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getAccOpenDate() { return accOpenDate; }
    public void setAccOpenDate(Date accOpenDate) { this.accOpenDate = accOpenDate; }

    public Date getAccCloseDate() { return accCloseDate; }
    public void setAccCloseDate(Date accCloseDate) { this.accCloseDate = accCloseDate; }

    public BigDecimal getAcctOutsBalance() { return acctOutsBalance; }
    public void setAcctOutsBalance(BigDecimal acctOutsBalance) { this.acctOutsBalance = acctOutsBalance; }

    public BigDecimal getAcctCreditLimit() { return acctCreditLimit; }
    public void setAcctCreditLimit(BigDecimal acctCreditLimit) { this.acctCreditLimit = acctCreditLimit; }

    public String getAcctStaffLoanFlag() { return acctStaffLoanFlag; }
    public void setAcctStaffLoanFlag(String acctStaffLoanFlag) { this.acctStaffLoanFlag = acctStaffLoanFlag; }

    public String getAccStatus() { return accStatus; }
    public void setAccStatus(String accStatus) { this.accStatus = accStatus; }

    public String getLoanStatus() { return loanStatus; }
    public void setLoanStatus(String loanStatus) { this.loanStatus = loanStatus; }
}
