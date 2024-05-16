package solusi.hapis.backend.tabel.model;

import java.util.Date;

public class Tmp01rebate {
	private static final long serialVersionUID = 1L;
	private int tmp01Id;
	private String tanggal;
	private String noInvoice;
	private String noPo;
	private String itemNo;
	private String itemDesc;
	private String qty;
	private String keterangan;
	private String amount;
	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public Tmp01rebate(String tanggal, String noInvoice,
			String noPo, String itemNo, String itemDesc, String qty,
			String keterangan, String amount, String prosesId) {
		
		this.tanggal = tanggal;
		this.noInvoice = noInvoice;
		this.noPo = noPo;
		this.itemNo = itemNo;
		this.itemDesc = itemDesc;
		this.qty = qty;
		this.keterangan = keterangan;
		this.amount = amount;
		this.prosesId = prosesId;
		
	}

	public int getTmp01Id() {
		return tmp01Id;
	}

	public void setTmp01Id(int tmp01Id) {
		this.tmp01Id = tmp01Id;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getNoInvoice() {
		return noInvoice;
	}

	public void setNoInvoice(String noInvoice) {
		this.noInvoice = noInvoice;
	}

	public String getNoPo() {
		return noPo;
	}

	public void setNoPo(String noPo) {
		this.noPo = noPo;
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

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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
		
	
}
