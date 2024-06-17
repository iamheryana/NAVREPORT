package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class BCAccessFrom {
	private static final long serialVersionUID = 1L;
	private String code;
	private Date lastSync;
	private String statusSync;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public BCAccessFrom(){
		
	}


	public BCAccessFrom(String code, Date lastSync, String statusSync) {
		super();
		this.code = code;
		this.lastSync = lastSync;
		this.statusSync = statusSync;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Date getLastSync() {
		return lastSync;
	}


	public void setLastSync(Date lastSync) {
		this.lastSync = lastSync;
	}


	public String getStatusSync() {
		return statusSync;
	}


	public void setStatusSync(String statusSync) {
		this.statusSync = statusSync;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	
	
}
