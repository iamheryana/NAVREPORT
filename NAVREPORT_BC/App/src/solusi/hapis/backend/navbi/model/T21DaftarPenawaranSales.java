package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

import solusi.hapis.common.CommonUtils;

public class T21DaftarPenawaranSales {
	private static final long serialVersionUID = 1L;
	private long t21Id;
	private String company;
	private String cabang;
	private Date tglPenawaran;	
	private String noPenawaran;
	private String namaCustomer;
	private String sektorIndustri;
	private String keterangan;
	private String salesCode;
	private BigDecimal nilai;
	private String statusPenawaran;	
	private Date tglAwarded;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;	
	
	public T21DaftarPenawaranSales(){
		
	}

	public long getT21Id() {
		return t21Id;
	}

	public void setT21Id(long t21Id) {
		this.t21Id = t21Id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getCabang() {
		return cabang;
	}

	public void setCabang(String cabang) {
		this.cabang = cabang;
	}


	public Date getTglPenawaran() {
		return tglPenawaran;
	}

	public void setTglPenawaran(Date tglPenawaran) {
		this.tglPenawaran = tglPenawaran;
	}

	public String getNoPenawaran() {
		return noPenawaran;
	}

	public void setNoPenawaran(String noPenawaran) {
		this.noPenawaran = noPenawaran;
	}

	public String getNamaCustomer() {
		return namaCustomer;
	}

	public void setNamaCustomer(String namaCustomer) {
		this.namaCustomer = namaCustomer;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = CommonUtils.toUpperCase(salesCode);
	}

	public BigDecimal getNilai() {
		return nilai;
	}

	public void setNilai(BigDecimal nilai) {
		this.nilai = nilai;
	}

	public String getStatusPenawaran() {
		return statusPenawaran;
	}

	public void setStatusPenawaran(String statusPenawaran) {
		this.statusPenawaran = statusPenawaran;
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

	public String getSektorIndustri() {
		return sektorIndustri;
	}

	public void setSektorIndustri(String sektorIndustri) {
		this.sektorIndustri = sektorIndustri;
	}

	public Date getTglAwarded() {
		return tglAwarded;
	}

	public void setTglAwarded(Date tglAwarded) {
		this.tglAwarded = tglAwarded;
	}
	
	

}
