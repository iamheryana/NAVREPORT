package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class T23AdjTopCust {
	private static final long serialVersionUID = 1L;
	private long t23Id;	
	private String custNo;
	private int topAdj;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	
	
	public T23AdjTopCust(){
		
	}



	public long getT23Id() {
		return t23Id;
	}



	public void setT23Id(long t23Id) {
		this.t23Id = t23Id;
	}



	public String getCustNo() {
		return custNo;
	}



	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}



	public int getTopAdj() {
		return topAdj;
	}



	public void setTopAdj(int topAdj) {
		this.topAdj = topAdj;
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
