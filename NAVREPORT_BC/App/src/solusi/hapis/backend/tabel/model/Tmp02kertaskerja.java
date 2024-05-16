package solusi.hapis.backend.tabel.model;

import java.util.Date;

public class Tmp02kertaskerja implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int tmp02Id;
	private String itemNo;
	private String itemDesc;
	private String itemCat;
	private String itemCatDesc;
	private String productGrp;
	private String productGrpDesc;
	private String posisi;
	private String uom;
	private String qty;
	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public Tmp02kertaskerja(String itemNo, String itemDesc,
			String itemCat, String itemCatDesc, String productGrp,
			String productGrpDesc, String posisi, String uom, String qty, String prosesId) {
		this.itemNo = itemNo;
		this.itemDesc = itemDesc;
		this.itemCat = itemCat;
		this.itemCatDesc = itemCatDesc;
		this.productGrp = productGrp;
		this.productGrpDesc = productGrpDesc;
		this.posisi = posisi;
		this.uom = uom;
		this.qty = qty;
		this.prosesId = prosesId;
	}

	public int getTmp02Id() {
		return tmp02Id;
	}

	public void setTmp02Id(int tmp02Id) {
		this.tmp02Id = tmp02Id;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemCat() {
		return itemCat;
	}

	public void setItemCat(String itemCat) {
		this.itemCat = itemCat;
	}

	public String getItemCatDesc() {
		return itemCatDesc;
	}

	public void setItemCatDesc(String itemCatDesc) {
		this.itemCatDesc = itemCatDesc;
	}

	public String getProductGrp() {
		return productGrp;
	}

	public void setProductGrp(String productGrp) {
		this.productGrp = productGrp;
	}

	public String getProductGrpDesc() {
		return productGrpDesc;
	}

	public void setProductGrpDesc(String productGrpDesc) {
		this.productGrpDesc = productGrpDesc;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getProsesId() {
		return prosesId;
	}

	public void setProsesId(String prosesId) {
		this.prosesId = prosesId;
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

	public String getPosisi() {
		return posisi;
	}

	public void setPosisi(String posisi) {
		this.posisi = posisi;
	}
	
	
	
	
}
