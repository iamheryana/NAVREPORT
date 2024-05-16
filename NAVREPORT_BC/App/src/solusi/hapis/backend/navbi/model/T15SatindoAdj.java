package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class T15SatindoAdj {
	private static final long serialVersionUID = 1L;
	private long t15Id;		
	private String noInvoice;
	private Date orderDate;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T15SatindoAdj(){
		
	}

	public long getT15Id() {
		return t15Id;
	}

	public void setT15Id(long t15Id) {
		this.t15Id = t15Id;
	}

	public String getNoInvoice() {
		return noInvoice;
	}

	public void setNoInvoice(String noInvoice) {
		this.noInvoice = noInvoice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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
