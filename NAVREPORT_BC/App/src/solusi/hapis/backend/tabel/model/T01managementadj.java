package solusi.hapis.backend.tabel.model;

import java.math.BigDecimal;
import java.util.Date;

import solusi.hapis.common.CommonUtils;



public class T01managementadj  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int t01Id;
	private Date tanggal;
	private String cabang;
	private String sales;
	private String keterangan;
	private BigDecimal amountHw01;
	private BigDecimal amountPs01;
	private BigDecimal amountPs02;
	private BigDecimal amountPs03;
	private BigDecimal amountPs04;
	private BigDecimal amountPs05;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
    
    
    public T01managementadj(){
    	
    }

    
    public T01managementadj(int t01Id, Date tanggal, String cabang,
			String sales, String keterangan, BigDecimal amountHw01,
			BigDecimal amountPs01, BigDecimal amountPs02,
			BigDecimal amountPs03, BigDecimal amountPs04,
			BigDecimal amountPs05) {

		this.tanggal = tanggal;
		this.cabang = cabang;
		this.sales = sales;
		this.keterangan = keterangan;
		this.amountHw01 = amountHw01;
		this.amountPs01 = amountPs01;
		this.amountPs02 = amountPs02;
		this.amountPs03 = amountPs03;
		this.amountPs04 = amountPs04;
		this.amountPs05 = amountPs05;
	}
    
	public T01managementadj(int t01Id, Date tanggal, String cabang,
			String sales, String keterangan, BigDecimal amountHw01,
			BigDecimal amountPs01, BigDecimal amountPs02,
			BigDecimal amountPs03, BigDecimal amountPs04,
			BigDecimal amountPs05, Integer version, String createdBy,
			Date createdOn, String updatedBy, Date updatedOn) {

		this.t01Id = t01Id;
		this.tanggal = tanggal;
		this.cabang = cabang;
		this.sales = sales;
		this.keterangan = keterangan;
		this.amountHw01 = amountHw01;
		this.amountPs01 = amountPs01;
		this.amountPs02 = amountPs02;
		this.amountPs03 = amountPs03;
		this.amountPs04 = amountPs04;
		this.amountPs05 = amountPs05;
		this.version = version;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}



	public int getT01Id() {
		return t01Id;
	}


	public void setT01Id(int t01Id) {
		this.t01Id = t01Id;
	}


	public Date getTanggal() {
		return tanggal;
	}


	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}


	public String getCabang() {
		return cabang;
	}


	public void setCabang(String cabang) {
		this.cabang = cabang;
	}


	public String getSales() {
		return sales;
	}


	public void setSales(String sales) {
		this.sales = CommonUtils.toUpperCase(sales);
		//this.sales = sales;
	}


	public String getKeterangan() {
		return keterangan;
	}


	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}


	public BigDecimal getAmountHw01() {
		return amountHw01;
	}


	public void setAmountHw01(BigDecimal amountHw01) {
		this.amountHw01 = amountHw01;
	}

	public BigDecimal getAmountPs01() {
		return amountPs01;
	}


	public void setAmountPs01(BigDecimal amountPs01) {
		this.amountPs01 = amountPs01;
	}


	public BigDecimal getAmountPs02() {
		return amountPs02;
	}


	public void setAmountPs02(BigDecimal amountPs02) {
		this.amountPs02 = amountPs02;
	}


	public BigDecimal getAmountPs03() {
		return amountPs03;
	}


	public void setAmountPs03(BigDecimal amountPs03) {
		this.amountPs03 = amountPs03;
	}


	public BigDecimal getAmountPs04() {
		return amountPs04;
	}


	public void setAmountPs04(BigDecimal amountPs04) {
		this.amountPs04 = amountPs04;
	}


	public BigDecimal getAmountPs05() {
		return amountPs05;
	}


	public void setAmountPs05(BigDecimal amountPs05) {
		this.amountPs05 = amountPs05;
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
