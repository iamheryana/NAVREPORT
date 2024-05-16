package solusi.hapis.backend.tabel.model;

import java.math.BigDecimal;
import java.util.Date;

public class T06rekapkomisisatindo {

	private static final long serialVersionUID = 1L;
	private int t06Id;
	private String company;
	private String reg;
	private String sales;
	private String noInvoice;
	private Date tglInvoice;	
	private String noFp;
	private String noPoCust;	
	private String sellToCode;
	private String sellToName;
	private String billToCode;
	private String billToName;	
	private BigDecimal nilaiInvoice;
	private Date tglLunas;
	private String noLunas;
	private String noItem;
	private String itemDesc;
	private BigDecimal qty;
	private BigDecimal harga;
	private BigDecimal amountKomisi;
	private String masaKomisi;
	private String tahunKomisi;
	private String flagKomisi;	

	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	


	public T06rekapkomisisatindo(){
    	
    }



	public T06rekapkomisisatindo(String company, String reg, String sales,
			String noInvoice, Date tglInvoice, String noFp, String noPoCust,
			String sellToCode, String sellToName, String billToCode,
			String billToName, BigDecimal nilaiInvoice, Date tglLunas,
			String noLunas, String noItem, String itemDesc, BigDecimal qty,
			BigDecimal harga, BigDecimal amountKomisi, String masaKomisi,
			String tahunKomisi, String flagKomisi) {
		super();
		this.company = company;
		this.reg = reg;
		this.sales = sales;
		this.noInvoice = noInvoice;
		this.tglInvoice = tglInvoice;
		this.noFp = noFp;
		this.noPoCust = noPoCust;
		this.sellToCode = sellToCode;
		this.sellToName = sellToName;
		this.billToCode = billToCode;
		this.billToName = billToName;
		this.nilaiInvoice = nilaiInvoice;
		this.tglLunas = tglLunas;
		this.noLunas = noLunas;
		this.noItem = noItem;
		this.itemDesc = itemDesc;
		this.qty = qty;
		this.harga = harga;
		this.amountKomisi = amountKomisi;
		this.masaKomisi = masaKomisi;
		this.tahunKomisi = tahunKomisi;
		this.flagKomisi = flagKomisi;
	}



	public int getT06Id() {
		return t06Id;
	}



	public void setT06Id(int t06Id) {
		this.t06Id = t06Id;
	}



	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}



	public String getReg() {
		return reg;
	}



	public void setReg(String reg) {
		this.reg = reg;
	}



	public String getSales() {
		return sales;
	}



	public void setSales(String sales) {
		this.sales = sales;
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



	public String getNoFp() {
		return noFp;
	}



	public void setNoFp(String noFp) {
		this.noFp = noFp;
	}



	public String getNoPoCust() {
		return noPoCust;
	}



	public void setNoPoCust(String noPoCust) {
		this.noPoCust = noPoCust;
	}



	public String getSellToCode() {
		return sellToCode;
	}



	public void setSellToCode(String sellToCode) {
		this.sellToCode = sellToCode;
	}



	public String getSellToName() {
		return sellToName;
	}



	public void setSellToName(String sellToName) {
		this.sellToName = sellToName;
	}



	public String getBillToCode() {
		return billToCode;
	}



	public void setBillToCode(String billToCode) {
		this.billToCode = billToCode;
	}



	public String getBillToName() {
		return billToName;
	}



	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}



	public BigDecimal getNilaiInvoice() {
		return nilaiInvoice;
	}



	public void setNilaiInvoice(BigDecimal nilaiInvoice) {
		this.nilaiInvoice = nilaiInvoice;
	}



	public Date getTglLunas() {
		return tglLunas;
	}



	public void setTglLunas(Date tglLunas) {
		this.tglLunas = tglLunas;
	}



	public String getNoLunas() {
		return noLunas;
	}



	public void setNoLunas(String noLunas) {
		this.noLunas = noLunas;
	}



	public String getNoItem() {
		return noItem;
	}



	public void setNoItem(String noItem) {
		this.noItem = noItem;
	}



	public String getItemDesc() {
		return itemDesc;
	}



	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}



	public BigDecimal getQty() {
		return qty;
	}



	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}



	public BigDecimal getHarga() {
		return harga;
	}



	public void setHarga(BigDecimal harga) {
		this.harga = harga;
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
