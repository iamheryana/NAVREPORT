package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class M03Targetsales {
	private long m03Id;
	private String tahun;	
	private BigDecimal target;
	private M02Salesperson m02Salesperson;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	
	public M03Targetsales(){
		
	}


	public long getM03Id() {
		return m03Id;
	}


	public void setM03Id(long m03Id) {
		this.m03Id = m03Id;
	}


	public String getTahun() {
		return tahun;
	}


	public void setTahun(String tahun) {
		this.tahun = tahun;
	}


	public BigDecimal getTarget() {
		return target;
	}


	public void setTarget(BigDecimal target) {
		this.target = target;
	}


	public M02Salesperson getM02Salesperson() {
		return m02Salesperson;
	}


	public void setM02Salesperson(M02Salesperson m02Salesperson) {
		this.m02Salesperson = m02Salesperson;
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
