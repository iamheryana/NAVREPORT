package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class T32CostingDOwnsw {
	private static final long serialVersionUID = 1L;
	private long t32Id;	
	private T29CostingH t29CostingH;
	private String itemDesc;
	private String itemNo;
	private BigDecimal qty;
	private BigDecimal salesSatuan;
	private BigDecimal salesTotal;
	private String catatan;
	private String itemCategory;
	private String product;	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T32CostingDOwnsw(){
    	
    }

	public long getT32Id() {
		return t32Id;
	}

	public void setT32Id(long t32Id) {
		this.t32Id = t32Id;
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

	public BigDecimal getSalesSatuan() {
		return salesSatuan;
	}

	public void setSalesSatuan(BigDecimal salesSatuan) {
		this.salesSatuan = salesSatuan;
	}

	public BigDecimal getSalesTotal() {
		return salesTotal;
	}

	public void setSalesTotal(BigDecimal salesTotal) {
		this.salesTotal = salesTotal;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
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
