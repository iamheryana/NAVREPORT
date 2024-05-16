package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class T12PsAdjPrice {
	private static final long serialVersionUID = 1L;
	private long t12Id;	
	private Date tglBerlaku;
	private String custNo;
	private String itemNo;
	private String currCode;
	private BigDecimal adjPrice;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	
	public T12PsAdjPrice(){
		
	}


	public long getT12Id() {
		return t12Id;
	}


	public void setT12Id(long t12Id) {
		this.t12Id = t12Id;
	}


	public Date getTglBerlaku() {
		return tglBerlaku;
	}


	public void setTglBerlaku(Date tglBerlaku) {
		this.tglBerlaku = tglBerlaku;
	}


	public String getCustNo() {
		return custNo;
	}


	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}


	public String getItemNo() {
		return itemNo;
	}


	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}


	public String getCurrCode() {
		return currCode;
	}


	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}


	public BigDecimal getAdjPrice() {
		return adjPrice;
	}


	public void setAdjPrice(BigDecimal adjPrice) {
		this.adjPrice = adjPrice;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
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
