package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

import solusi.hapis.common.CommonUtils;

public class T03CetakSlip {
	private static final long serialVersionUID = 1L;
	private long t03CsId;
	private String company;
	private String jenisTrans;
	private String noVoucher;
	private String noCheque;
	private BigDecimal kurs;
	private String berita;
	private String kodeVendorNonnav;
	private String valutaTrans;
	private String noRekPenerima;
	private String namaPenerima;
	private String alamatPenerima1;
	private String alamatPenerima2;
	private String countryPenerima;
	private String namaBank;
	private String alamatBank1;
	private String alamatBank2;
	private String countryBank;
	private String swiftCode;
	private BigDecimal provisiIdrToCny;
	private Integer printCount;
	private String printBy;
	private Date printOn;
	private String reprintBy;
	private Date reprintOn;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T03CetakSlip(){
		
	}
	
	public T03CetakSlip(long t03CsId, String company, String jenisTrans,
			String noVoucher, String noCheque, BigDecimal kurs,
			String berita, String kodeVendorNonnav, String valutaTrans,
			String noRekPenerima, String namaPenerima, String alamatPenerima1,
			String alamatPenerima2, String countryPenerima, String namaBank,
			String alamatBank1, String alamatBank2, String countryBank,
			String swiftCode, BigDecimal provisiIdrToCny, Integer printCount, String printBy, Date printOn,
			String reprintBy, Date reprintOn, Integer version,
			String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
		super();
		this.t03CsId = t03CsId;
		this.company = company;
		this.jenisTrans = jenisTrans;
		this.noVoucher = noVoucher;
		this.noCheque = noCheque;
		this.kurs = kurs;
		this.berita = berita;
		this.kodeVendorNonnav = kodeVendorNonnav;
		this.valutaTrans = valutaTrans;
		this.noRekPenerima = noRekPenerima;
		this.namaPenerima = namaPenerima;
		this.alamatPenerima1 = alamatPenerima1;
		this.alamatPenerima2 = alamatPenerima2;
		this.countryPenerima = countryPenerima;
		this.namaBank = namaBank;
		this.alamatBank1 = alamatBank1;
		this.alamatBank2 = alamatBank2;
		this.countryBank = countryBank;
		this.swiftCode = swiftCode;
		this.provisiIdrToCny = provisiIdrToCny;		
		this.printCount = printCount;
		this.printBy = printBy;
		this.printOn = printOn;
		this.reprintBy = reprintBy;
		this.reprintOn = reprintOn;
		this.version = version;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}


	public long getT03CsId() {
		return t03CsId;
	}


	public void setT03CsId(long t03CsId) {
		this.t03CsId = t03CsId;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getJenisTrans() {
		return jenisTrans;
	}


	public void setJenisTrans(String jenisTrans) {
		this.jenisTrans = jenisTrans;
	}


	public String getNoVoucher() {
		return noVoucher;
	}


	public void setNoVoucher(String noVoucher) {
		this.noVoucher = CommonUtils.toUpperCase(noVoucher);
	}

	
	public String getNoCheque() {
		return noCheque;
	}


	public void setNoCheque(String noCheque) {
		this.noCheque = CommonUtils.toUpperCase(noCheque);
	}


	public BigDecimal getKurs() {
		return kurs;
	}


	public void setKurs(BigDecimal kurs) {
		this.kurs = kurs;
	}


	public String getBerita() {
		return berita;
	}


	public void setBerita(String berita) {
		this.berita = berita;
	}


	public String getKodeVendorNonnav() {
		return kodeVendorNonnav;
	}


	public void setKodeVendorNonnav(String kodeVendorNonnav) {
		this.kodeVendorNonnav = CommonUtils.toUpperCase(kodeVendorNonnav);
	}


	public String getValutaTrans() {
		return valutaTrans;
	}


	public void setValutaTrans(String valutaTrans) {
		this.valutaTrans = CommonUtils.toUpperCase(valutaTrans);
	}


	public String getNoRekPenerima() {
		return noRekPenerima;
	}


	public void setNoRekPenerima(String noRekPenerima) {
		this.noRekPenerima = noRekPenerima;
	}


	public String getNamaPenerima() {
		return namaPenerima;
	}


	public void setNamaPenerima(String namaPenerima) {
		this.namaPenerima = namaPenerima;
	}


	public String getAlamatPenerima1() {
		return alamatPenerima1;
	}


	public void setAlamatPenerima1(String alamatPenerima1) {
		this.alamatPenerima1 = alamatPenerima1;
	}


	public String getAlamatPenerima2() {
		return alamatPenerima2;
	}


	public void setAlamatPenerima2(String alamatPenerima2) {
		this.alamatPenerima2 = alamatPenerima2;
	}


	public String getCountryPenerima() {
		return countryPenerima;
	}


	public void setCountryPenerima(String countryPenerima) {
		this.countryPenerima = countryPenerima;
	}


	public String getNamaBank() {
		return namaBank;
	}


	public void setNamaBank(String namaBank) {
		this.namaBank = namaBank;
	}


	public String getAlamatBank1() {
		return alamatBank1;
	}


	public void setAlamatBank1(String alamatBank1) {
		this.alamatBank1 = alamatBank1;
	}


	public String getAlamatBank2() {
		return alamatBank2;
	}


	public void setAlamatBank2(String alamatBank2) {
		this.alamatBank2 = alamatBank2;
	}


	public String getCountryBank() {
		return countryBank;
	}


	public void setCountryBank(String countryBank) {
		this.countryBank = countryBank;
	}


	public String getSwiftCode() {
		return swiftCode;
	}


	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}


	public Integer getPrintCount() {
		return printCount;
	}


	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}


	public String getPrintBy() {
		return printBy;
	}


	public void setPrintBy(String printBy) {
		this.printBy = printBy;
	}


	public Date getPrintOn() {
		return printOn;
	}


	public void setPrintOn(Date printOn) {
		this.printOn = printOn;
	}


	public String getReprintBy() {
		return reprintBy;
	}


	public void setReprintBy(String reprintBy) {
		this.reprintBy = reprintBy;
	}


	public Date getReprintOn() {
		return reprintOn;
	}


	public void setReprintOn(Date reprintOn) {
		this.reprintOn = reprintOn;
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

	public BigDecimal getProvisiIdrToCny() {
		return provisiIdrToCny;
	}

	public void setProvisiIdrToCny(BigDecimal provisiIdrToCny) {
		this.provisiIdrToCny = provisiIdrToCny;
	}
	
	
	
}
