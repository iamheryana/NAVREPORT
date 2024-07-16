package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class T33CostingDOther {
	private static final long serialVersionUID = 1L;
	private long t33Id;	
	private T29CostingH t29CostingH;
	private String itemDesc;
	private String itemNo;
	private BigDecimal qty;
	private BigDecimal cogsSatuan;
	private BigDecimal cogsTotal;
	private String catatan;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T33CostingDOther(){
    	
    }

	public long getT33Id() {
		return t33Id;
	}

	public void setT33Id(long t33Id) {
		this.t33Id = t33Id;
	}

	public T29CostingH getT29CostingH() {
		return t29CostingH;
	}

	public void setT29CostingH(T29CostingH t29CostingH) {
		this.t29CostingH = t29CostingH;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getCogsSatuan() {
		return cogsSatuan;
	}

	public void setCogsSatuan(BigDecimal cogsSatuan) {
		this.cogsSatuan = cogsSatuan;
	}

	public BigDecimal getCogsTotal() {
		return cogsTotal;
	}

	public void setCogsTotal(BigDecimal cogsTotal) {
		this.cogsTotal = cogsTotal;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
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
