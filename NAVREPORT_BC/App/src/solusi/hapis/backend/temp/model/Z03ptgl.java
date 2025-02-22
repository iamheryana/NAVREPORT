package solusi.hapis.backend.temp.model;
// Generated Apr 16, 2013 9:31:13 AM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;

/**
 * Z03ptgl generated by hbm2java
 */
public class Z03ptgl  implements java.io.Serializable {


     private Z03ptglId id;
     private Integer version;
     private String journaltype;
     private String ketcabang;
     private String description;
     private Date applydate;
     private String currency;
     private BigDecimal debit;
     private BigDecimal credit;
     private String description2;
     private Integer hold;
     private Integer intercompany;
     private Integer reversing;
     private Integer repeating;
     private Integer recurring;
     private Integer captureflg;
     private String userid;
     private Date upddate;
     private Date updtime;
     private String ws;
     private String createdBy;
     private Date createdOn;
     private String updatedBy;
     private Date updatedOn;

    public Z03ptgl() {
    }

	
    public Z03ptgl(Z03ptglId id) {
        this.id = id;
    }
    public Z03ptgl(Z03ptglId id, String journaltype, String ketcabang, String description, Date applydate, String currency, BigDecimal debit, BigDecimal credit, String description2, Integer hold, Integer intercompany, Integer reversing, Integer repeating, Integer recurring, Integer captureflg, String userid, Date upddate, Date updtime, String ws, String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
       this.id = id;
       this.journaltype = journaltype;
       this.ketcabang = ketcabang;
       this.description = description;
       this.applydate = applydate;
       this.currency = currency;
       this.debit = debit;
       this.credit = credit;
       this.description2 = description2;
       this.hold = hold;
       this.intercompany = intercompany;
       this.reversing = reversing;
       this.repeating = repeating;
       this.recurring = recurring;
       this.captureflg = captureflg;
       this.userid = userid;
       this.upddate = upddate;
       this.updtime = updtime;
       this.ws = ws;
       this.createdBy = createdBy;
       this.createdOn = createdOn;
       this.updatedBy = updatedBy;
       this.updatedOn = updatedOn;
    }
   
    public Z03ptglId getId() {
        return this.id;
    }
    
    public void setId(Z03ptglId id) {
        this.id = id;
    }
    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getJournaltype() {
        return this.journaltype;
    }
    
    public void setJournaltype(String journaltype) {
        this.journaltype = journaltype;
    }
    public String getKetcabang() {
        return this.ketcabang;
    }
    
    public void setKetcabang(String ketcabang) {
        this.ketcabang = ketcabang;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getApplydate() {
        return this.applydate;
    }
    
    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }
    public String getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public BigDecimal getDebit() {
        return this.debit;
    }
    
    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }
    public BigDecimal getCredit() {
        return this.credit;
    }
    
    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
    public String getDescription2() {
        return this.description2;
    }
    
    public void setDescription2(String description2) {
        this.description2 = description2;
    }
    public Integer getHold() {
        return this.hold;
    }
    
    public void setHold(Integer hold) {
        this.hold = hold;
    }
    public Integer getIntercompany() {
        return this.intercompany;
    }
    
    public void setIntercompany(Integer intercompany) {
        this.intercompany = intercompany;
    }
    public Integer getReversing() {
        return this.reversing;
    }
    
    public void setReversing(Integer reversing) {
        this.reversing = reversing;
    }
    public Integer getRepeating() {
        return this.repeating;
    }
    
    public void setRepeating(Integer repeating) {
        this.repeating = repeating;
    }
    public Integer getRecurring() {
        return this.recurring;
    }
    
    public void setRecurring(Integer recurring) {
        this.recurring = recurring;
    }
    public Integer getCaptureflg() {
        return this.captureflg;
    }
    
    public void setCaptureflg(Integer captureflg) {
        this.captureflg = captureflg;
    }
    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public Date getUpddate() {
        return this.upddate;
    }
    
    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }
    public Date getUpdtime() {
        return this.updtime;
    }
    
    public void setUpdtime(Date updtime) {
        this.updtime = updtime;
    }
    public String getWs() {
        return this.ws;
    }
    
    public void setWs(String ws) {
        this.ws = ws;
    }
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public Date getUpdatedOn() {
        return this.updatedOn;
    }
    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }




}


