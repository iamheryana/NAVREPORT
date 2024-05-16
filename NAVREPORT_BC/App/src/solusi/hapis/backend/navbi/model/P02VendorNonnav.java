package solusi.hapis.backend.navbi.model;

import java.util.Date;

import solusi.hapis.common.CommonUtils;

public class P02VendorNonnav {
private static final long serialVersionUID = 1L;
	
	private String kode;
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
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public P02VendorNonnav(){
    	
    }

	public P02VendorNonnav(String kode, String valutaTrans,
			String noRekPenerima, String namaPenerima, String alamatPenerima1,
			String alamatPenerima2, String countryPenerima, String namaBank,
			String alamatBank1, String alamatBank2, String countryBank,
			String swiftCode, Integer version, String createdBy,
			Date createdOn, String updatedBy, Date updatedOn) {
		super();
		this.kode = kode;
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
		this.version = version;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = CommonUtils.toUpperCase(kode);
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
