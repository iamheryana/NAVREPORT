package solusi.hapis.backend.tabel.model;

import java.util.Date;

public class Tmp04efaktur  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int tmp04Id;
	private String namaCust;
	private String noFakturPajak;	
	private String noFakturPajakOri;	
	private String tanggal;
	private String masa;
	private String tahun;
	private String status;
	private String dpp;
	private String ppn;
	private String referansi;
	private String statusApproval;	
	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public Tmp04efaktur(String namaCust, String noFakturPajak, String noFakturPajakOri, String tanggal,
			String masa, String tahun, String status, String dpp, String ppn, String referansi,
			String statusApproval, String prosesId) {
		this.namaCust = namaCust;
		this.noFakturPajak = noFakturPajak;
		this.noFakturPajakOri = noFakturPajakOri;
		this.tanggal = tanggal;
		this.masa = masa;
		this.tahun = tahun;
		this.status = status;
		this.dpp = dpp;
		this.ppn = ppn;
		this.referansi = referansi;
		this.statusApproval = statusApproval;
		this.prosesId = prosesId;
	}

	public int getTmp04Id() {
		return tmp04Id;
	}

	public void setTmp04Id(int tmp04Id) {
		this.tmp04Id = tmp04Id;
	}

	public String getNamaCust() {
		return namaCust;
	}

	public void setNamaCust(String namaCust) {
		this.namaCust = namaCust;
	}

	public String getNoFakturPajak() {
		return noFakturPajak;
	}

	public void setNoFakturPajak(String noFakturPajak) {
		this.noFakturPajak = noFakturPajak;
	}

	public String getNoFakturPajakOri() {
		return noFakturPajakOri;
	}

	public void setNoFakturPajakOri(String noFakturPajakOri) {
		this.noFakturPajakOri = noFakturPajakOri;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getMasa() {
		return masa;
	}

	public void setMasa(String masa) {
		this.masa = masa;
	}

	public String getTahun() {
		return tahun;
	}

	public void setTahun(String tahun) {
		this.tahun = tahun;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDpp() {
		return dpp;
	}

	public void setDpp(String dpp) {
		this.dpp = dpp;
	}

	public String getPpn() {
		return ppn;
	}

	public void setPpn(String ppn) {
		this.ppn = ppn;
	}

	public String getReferansi() {
		return referansi;
	}

	public void setReferansi(String referansi) {
		this.referansi = referansi;
	}

	public String getStatusApproval() {
		return statusApproval;
	}

	public void setStatusApproval(String statusApproval) {
		this.statusApproval = statusApproval;
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
