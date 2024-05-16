package solusi.hapis.backend.tabel.model;

import java.util.Date;

public class Tmp03hasilopname implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int tmp03Id;
	private String itemNo;
	private String lokasi;
	private String qty;
	private String waktu;
	private String prefix;
	private String terminal;
	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public Tmp03hasilopname(String itemNo, String lokasi, String qty,
			String waktu, String prefix, String terminal, String prosesId) {
		this.itemNo = itemNo;
		this.lokasi = lokasi;
		this.qty = qty;
		this.waktu = waktu;
		this.prefix = prefix;
		this.terminal = terminal;
		this.prosesId = prosesId;
	}

	public int getTmp03Id() {
		return tmp03Id;
	}

	public void setTmp03Id(int tmp03Id) {
		this.tmp03Id = tmp03Id;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getWaktu() {
		return waktu;
	}

	public void setWaktu(String waktu) {
		this.waktu = waktu;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
	
	
	
	

}
