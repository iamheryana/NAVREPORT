package solusi.hapis.backend.navbi.model;

import java.util.Date;

import solusi.hapis.common.CommonUtils;

public class T19PiItem {
	private static final long serialVersionUID = 1L;
	private long t19Id;
	private Date tglMulai;	
	private String principalCode;
	private String itemCatCode;
	private String productCode;
	private String berlaku;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T19PiItem(){
		
	}

	public long getT19Id() {
		return t19Id;
	}

	public void setT19Id(long t19Id) {
		this.t19Id = t19Id;
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

	public String getItemCatCode() {
		return itemCatCode;
	}

	public void setItemCatCode(String itemCatCode) {
		this.itemCatCode = itemCatCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
