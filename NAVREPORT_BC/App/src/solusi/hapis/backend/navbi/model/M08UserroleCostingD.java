package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class M08UserroleCostingD {
	private long m08Id;
	private String filteruser;		
	private M07UserroleCostingH m07UserroleCostingH;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	
	public M08UserroleCostingD(){
		
	}


	public long getM08Id() {
		return m08Id;
	}


	public void setM08Id(long m08Id) {
		this.m08Id = m08Id;
	}


	public String getFilteruser() {
		return filteruser;
	}


	public void setFilteruser(String filteruser) {
		this.filteruser = filteruser;
	}


	public M07UserroleCostingH getM07UserroleCostingH() {
		return m07UserroleCostingH;
	}


	public void setM07UserroleCostingH(M07UserroleCostingH m07UserroleCostingH) {
		this.m07UserroleCostingH = m07UserroleCostingH;
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
