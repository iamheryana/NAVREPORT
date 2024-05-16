package solusi.hapis.backend.navbi.model;

import java.util.Date;

import solusi.hapis.common.CommonUtils;

public class T20PiVendor {
	private static final long serialVersionUID = 1L;
	private long t20Id;
	private Date tglMulai;	
	private String principalCode;
	private String vendorCode;
	private String berlaku;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;	
	
	public T20PiVendor(){
		
	}

	public long getT20Id() {
		return t20Id;
	}

	public void setT20Id(long t20Id) {
		this.t20Id = t20Id;
	}

	public Date getTglMulai() {
		return tglMulai;
	}

	public void setTglMulai(Date tglMulai) {
		this.tglMulai = tglMulai;
	}

	public String getPrincipalCode() {
		return principalCode;
	}

	public void setPrincipalCode(String principalCode) {
		this.principalCode = principalCode;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getBerlaku() {
		return berlaku;
	}

	public void setBerlaku(String berlaku) {
		this.berlaku = CommonUtils.toUpperCase(berlaku);
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
