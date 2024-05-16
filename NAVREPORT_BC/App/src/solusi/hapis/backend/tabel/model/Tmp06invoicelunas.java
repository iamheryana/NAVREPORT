package solusi.hapis.backend.tabel.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tmp06invoicelunas implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int tmp06Id;	

	private String company;
	private String reg;
	private String sales;
	private String salesName;
	private String noBso;
	private String noSo;
	private Date tglSo;
	private String customer;
	private String noPoCust;
	private BigDecimal amount;
	private String noInvoice;
	private Date tglInvoice;
	private Date tglLunas;

	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	
	public Tmp06invoicelunas(String company, String reg, String sales, String salesName,
			String noBso, String noSo,
			Date tglSo, String customer, String noPoCust, BigDecimal amount,
			String noInvoice, Date tglInvoice, Date tglLunas, String prosesId) {

		this.company = company;
		this.reg = reg;
		this.sales = sales;
		this.salesName = salesName;
		this.noBso = noBso;
		this.noSo = noSo;
		this.tglSo = tglSo;
		this.customer = customer;
		this.noPoCust = noPoCust;
		this.amount = amount;
		this.noInvoice = noInvoice;
		this.tglInvoice = tglInvoice;
		this.tglLunas = tglLunas;
		this.prosesId = prosesId;
	}


	public int getTmp06Id() {
		return tmp06Id;
	}


	public void setTmp06Id(int tmp06Id) {
		this.tmp06Id = tmp06Id;
	}
	

	public String getReg() {
		return reg;
	}


	public void setReg(String reg) {
		this.reg = reg;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getSales() {
		return sales;
	}


	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getSalesName() {
		return salesName;
	}


	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getNoBso() {
		return noBso;
	}


	public void setNoBso(String noBso) {
		this.noBso = noBso;
	}

	public String getNoSo() {
		return noSo;
	}


	public void setNoSo(String noSo) {
		this.noSo = noSo;
	}


	public Date getTglSo() {
		return tglSo;
	}


	public void setTglSo(Date tglSo) {
		this.tglSo = tglSo;
	}


	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customer) {
		this.customer = customer;
	}


	public String getNoPoCust() {
		return noPoCust;
	}


	public void setNoPoCust(String noPoCust) {
		this.noPoCust = noPoCust;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getNoInvoice() {
		return noInvoice;
	}


	public void setNoInvoice(String noInvoice) {
		this.noInvoice = noInvoice;
	}


	public Date getTglInvoice() {
		return tglInvoice;
	}


	public void setTglInvoice(Date tglInvoice) {
		this.tglInvoice = tglInvoice;
	}


	public Date getTglLunas() {
		return tglLunas;
	}


	public void setTglLunas(Date tglLunas) {
		this.tglLunas = tglLunas;
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
