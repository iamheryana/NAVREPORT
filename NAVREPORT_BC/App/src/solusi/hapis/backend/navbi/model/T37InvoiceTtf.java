package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class T37InvoiceTtf {
	private static final long serialVersionUID = 1L;
	private long t37Id;	
	private Date tglInv;
	private String noInv;	
	private String sellToCode;	
	private String sellToName;	
	private String billToCode;	
	private String billToName;
	private BigDecimal amount;
	private BigDecimal amountPpn;
	private Date tglTtf;
	private String flagTerima;
	private String flagCancel;	
	private String terimaBy;
	private Date terimaOn;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T37InvoiceTtf(){
    	
    }

	public long getT37Id() {
		return t37Id;
	}

	public void setT37Id(long t37Id) {
		this.t37Id = t37Id;
	}

	public Date getTglInv() {
		return tglInv;
	}

	public void setTglInv(Date tglInv) {
		this.tglInv = tglInv;
	}

	public String getNoInv() {
		return noInv;
	}

	public void setNoInv(String noInv) {
		this.noInv = noInv;
	}

	public String getSellToCode() {
		return sellToCode;
	}

	public void setSellToCode(String sellToCode) {
		this.sellToCode = sellToCode;
	}

	public String getSellToName() {
		return sellToName;
	}

	public void setSellToName(String sellToName) {
		this.sellToName = sellToName;
	}

	public String getBillToCode() {
		return billToCode;
	}

	public void setBillToCode(String billToCode) {
		this.billToCode = billToCode;
	}

	public String getBillToName() {
		return billToName;
	}

	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountPpn() {
		return amountPpn;
	}

	public void setAmountPpn(BigDecimal amountPpn) {
		this.amountPpn = amountPpn;
	}

	public Date getTglTtf() {
		return tglTtf;
	}

	public void setTglTtf(Date tglTtf) {
		this.tglTtf = tglTtf;
	}

	public String getFlagTerima() {
		return flagTerima;
	}

	public void setFlagTerima(String flagTerima) {
		this.flagTerima = flagTerima;
	}

	public String getFlagCancel() {
		return flagCancel;
	}

	public void setFlagCancel(String flagCancel) {
		this.flagCancel = flagCancel;
	}

	public String getTerimaBy() {
		return terimaBy;
	}

	public void setTerimaBy(String terimaBy) {
		this.terimaBy = terimaBy;
	}

	public Date getTerimaOn() {
		return terimaOn;
	}

	public void setTerimaOn(Date terimaOn) {
		this.terimaOn = terimaOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
	
	
}
