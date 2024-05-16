package solusi.hapis.backend.tabel.model;

import java.math.BigDecimal;
import java.util.Date;



public class T08targetsales {
	private int t08Id;
	private String tahun;	
	private BigDecimal target;
	private T03salesperson t03salesperson;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T08targetsales(){
		
	}
	
	
	
	public int getT08Id() {
		return t08Id;
	}
	public void setT08Id(int t08Id) {
		this.t08Id = t08Id;
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
	public T03salesperson getT03salesperson() {
		return t03salesperson;
	}
	public void setT03salesperson(T03salesperson t03salesperson) {
		this.t03salesperson = t03salesperson;
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



