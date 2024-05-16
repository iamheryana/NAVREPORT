package solusi.hapis.backend.tabel.model;

import java.math.BigDecimal;
import java.util.Date;

import solusi.hapis.common.CommonUtils;

public class T02rekapcosting {
	private static final long serialVersionUID = 1L;
	private int t02Id;
	private String company;
	private String sales;
	private String noBso;
	private String noSo;
	private Date tglSo;	
	private String customer;
	private String noPoCust;
	private BigDecimal amount;
	private String noInvoice;
	private Date tglInvoice;	
	private Date tglLunas;
	private BigDecimal pcnKomisi;
	private BigDecimal amountKomisi;
	private String masaKomisi;
	private String tahunKomisi;
	private String flagKomisi;
	private BigDecimal pcnTqs;
	private BigDecimal amountTqs;
	private String masaTqs;
	private String tahunTqs;
	private String flagTqs;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T02rekapcosting(){
    	
    }



	public T02rekapcosting(String company, String sales, String noBso, String noSo,
			Date tglSo, String customer, String noPoCust, BigDecimal amount,
			String noInvoice, Date tglInvoice, Date tglLunas,
			BigDecimal pcnKomisi, BigDecimal amountKomisi, String masaKomisi,
			String tahunKomisi, String flagKomisi, BigDecimal pcnTqs,
			BigDecimal amountTqs, String masaTqs, String tahunTqs,
			String flagTqs) {
		super();
		this.company = company;
		this.sales = sales;
		this.noBso = noBso;
		this.noSo = noSo;
		this.tglSo = tglSo;
		this.customer = customer;
		this.noPoCust = noPoCust;
		this.amount = amount;
		this.noInvoice = noInvoice;
		this.tglInvoice = tglInvoice;
		this.tglLunas = tglLunas;
		this.pcnKomisi = pcnKomisi;
		this.amountKomisi = amountKomisi;
		this.masaKomisi = masaKomisi;
		this.tahunKomisi = tahunKomisi;
		this.flagKomisi = flagKomisi;
		this.pcnTqs = pcnTqs;
		this.amountTqs = amountTqs;
		this.masaTqs = masaTqs;
		this.tahunTqs = tahunTqs;
		this.flagTqs = flagTqs;
	}



	public int getT02Id() {
		return t02Id;
	}

	public void setT02Id(int t02Id) {
		this.t02Id = t02Id;
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
		this.sales = CommonUtils.toUpperCase(sales);
	}


	public String getNoBso() {
		return noBso;
	}

	public void setNoBso(String noBso) {
		this.noBso = CommonUtils.toUpperCase(noBso);
	}

	
	public String getNoSo() {
		return noSo;
	}

	public void setNoSo(String noSo) {
		this.noSo = CommonUtils.toUpperCase(noSo);
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
		this.customer = CommonUtils.toUpperCase(customer);
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
		this.noInvoice = CommonUtils.toUpperCase(noInvoice);
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

	public BigDecimal getPcnKomisi() {
		return pcnKomisi;
	}

	public void setPcnKomisi(BigDecimal pcnKomisi) {
		this.pcnKomisi = pcnKomisi;
	}

	public BigDecimal getAmountKomisi() {
		return amountKomisi;
	}

	public void setAmountKomisi(BigDecimal amountKomisi) {
		this.amountKomisi = amountKomisi;
	}

	public String getMasaKomisi() {
		return masaKomisi;
	}

	public void setMasaKomisi(String masaKomisi) {
		this.masaKomisi = masaKomisi;
	}

	public String getTahunKomisi() {
		return tahunKomisi;
	}

	public void setTahunKomisi(String tahunKomisi) {
		this.tahunKomisi = tahunKomisi;
	}

	public String getFlagKomisi() {
		return flagKomisi;
	}

	public void setFlagKomisi(String flagKomisi) {
		this.flagKomisi = flagKomisi;
	}

	public BigDecimal getPcnTqs() {
		return pcnTqs;
	}

	public void setPcnTqs(BigDecimal pcnTqs) {
		this.pcnTqs = pcnTqs;
	}

	public BigDecimal getAmountTqs() {
		return amountTqs;
	}

	public void setAmountTqs(BigDecimal amountTqs) {
		this.amountTqs = amountTqs;
	}


	public String getMasaTqs() {
		return masaTqs;
	}

	public void setMasaTqs(String masaTqs) {
		this.masaTqs = masaTqs;
	}

	public String getTahunTqs() {
		return tahunTqs;
	}

	public void setTahunTqs(String tahunTqs) {
		this.tahunTqs = tahunTqs;
	}

	public String getFlagTqs() {
		return flagTqs;
	}

	public void setFlagTqs(String flagTqs) {
		this.flagTqs = flagTqs;
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


