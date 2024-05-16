package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class P01BiayaTrans {
	private static final long serialVersionUID = 1L;
	
	private String kode;
	private String berita;
	private String namaPic;
	private String hpPic;	
	private BigDecimal provisiUsdToUsd;
	private BigDecimal chargeUsdToUsd;
	private BigDecimal provisiIdrToUsd;
	private BigDecimal chargeIdrToUsd;
	private BigDecimal provisiIdrToIdrNonbcaBawah;
	private BigDecimal provisiIdrToIdrNonbcaAtas;  	
	private BigDecimal chargeIdrBcaLk;
	private BigDecimal chargeIdrBcaVa;	
	private BigDecimal chargeMandiriBawah;	
	private BigDecimal chargeMandiriAtas;
	
	private BigDecimal provisiIdrToEur;
	private BigDecimal chargeIdrToEur;
	
	private BigDecimal chargeIdrToCny;
	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public P01BiayaTrans(){
    	
    }

	public P01BiayaTrans(String kode, String berita, String namaPic,
			String hpPic, BigDecimal provisiUsdToUsd,
			BigDecimal chargeUsdToUsd, BigDecimal provisiIdrToUsd,
			BigDecimal chargeIdrToUsd, BigDecimal provisiIdrToIdrNonbcaBawah,
			BigDecimal provisiIdrToIdrNonbcaAtas, 
			BigDecimal chargeIdrBcaLk, BigDecimal chargeIdrBcaVa,
			BigDecimal chargeMandiriBawah, BigDecimal chargeMandiriAtas,
			BigDecimal provisiIdrToEur, BigDecimal chargeIdrToEur,
			BigDecimal chargeIdrToCny,
			Integer version,
			String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
		super();
		this.kode = kode;
		this.berita = berita;
		this.namaPic = namaPic;
		this.hpPic = hpPic;
		this.provisiUsdToUsd = provisiUsdToUsd;
		this.chargeUsdToUsd = chargeUsdToUsd;
		this.provisiIdrToUsd = provisiIdrToUsd;
		this.chargeIdrToUsd = chargeIdrToUsd;
		this.provisiIdrToIdrNonbcaBawah = provisiIdrToIdrNonbcaBawah;
		this.provisiIdrToIdrNonbcaAtas = provisiIdrToIdrNonbcaAtas;
		this.chargeIdrBcaLk = chargeIdrBcaLk;
		this.chargeIdrBcaVa = chargeIdrBcaVa;
		this.chargeMandiriBawah = chargeMandiriBawah;
		this.chargeMandiriAtas = chargeMandiriAtas;
		this.provisiIdrToEur = provisiIdrToEur;
		this.chargeIdrToEur = chargeIdrToEur;
		this.chargeIdrToCny = chargeIdrToCny;
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
		this.kode = kode;
	}

	public String getBerita() {
		return berita;
	}

	public void setBerita(String berita) {
		this.berita = berita;
	}

	public String getNamaPic() {
		return namaPic;
	}

	public void setNamaPic(String namaPic) {
		this.namaPic = namaPic;
	}

	public String getHpPic() {
		return hpPic;
	}

	public void setHpPic(String hpPic) {
		this.hpPic = hpPic;
	}

	public BigDecimal getProvisiUsdToUsd() {
		return provisiUsdToUsd;
	}

	public void setProvisiUsdToUsd(BigDecimal provisiUsdToUsd) {
		this.provisiUsdToUsd = provisiUsdToUsd;
	}

	public BigDecimal getChargeUsdToUsd() {
		return chargeUsdToUsd;
	}

	public void setChargeUsdToUsd(BigDecimal chargeUsdToUsd) {
		this.chargeUsdToUsd = chargeUsdToUsd;
	}

	public BigDecimal getProvisiIdrToUsd() {
		return provisiIdrToUsd;
	}

	public void setProvisiIdrToUsd(BigDecimal provisiIdrToUsd) {
		this.provisiIdrToUsd = provisiIdrToUsd;
	}

	public BigDecimal getChargeIdrToUsd() {
		return chargeIdrToUsd;
	}

	public void setChargeIdrToUsd(BigDecimal chargeIdrToUsd) {
		this.chargeIdrToUsd = chargeIdrToUsd;
	}

	public BigDecimal getProvisiIdrToIdrNonbcaBawah() {
		return provisiIdrToIdrNonbcaBawah;
	}

	public void setProvisiIdrToIdrNonbcaBawah(BigDecimal provisiIdrToIdrNonbcaBawah) {
		this.provisiIdrToIdrNonbcaBawah = provisiIdrToIdrNonbcaBawah;
	}

	public BigDecimal getProvisiIdrToIdrNonbcaAtas() {
		return provisiIdrToIdrNonbcaAtas;
	}

	public void setProvisiIdrToIdrNonbcaAtas(BigDecimal provisiIdrToIdrNonbcaAtas) {
		this.provisiIdrToIdrNonbcaAtas = provisiIdrToIdrNonbcaAtas;
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

	public BigDecimal getChargeIdrBcaLk() {
		return chargeIdrBcaLk;
	}

	public void setChargeIdrBcaLk(BigDecimal chargeIdrBcaLk) {
		this.chargeIdrBcaLk = chargeIdrBcaLk;
	}

	public BigDecimal getChargeIdrBcaVa() {
		return chargeIdrBcaVa;
	}

	public void setChargeIdrBcaVa(BigDecimal chargeIdrBcaVa) {
		this.chargeIdrBcaVa = chargeIdrBcaVa;
	}

	public BigDecimal getChargeMandiriBawah() {
		return chargeMandiriBawah;
	}

	public void setChargeMandiriBawah(BigDecimal chargeMandiriBawah) {
		this.chargeMandiriBawah = chargeMandiriBawah;
	}

	public BigDecimal getChargeMandiriAtas() {
		return chargeMandiriAtas;
	}

	public void setChargeMandiriAtas(BigDecimal chargeMandiriAtas) {
		this.chargeMandiriAtas = chargeMandiriAtas;
	}

	public BigDecimal getProvisiIdrToEur() {
		return provisiIdrToEur;
	}

	public void setProvisiIdrToEur(BigDecimal provisiIdrToEur) {
		this.provisiIdrToEur = provisiIdrToEur;
	}

	public BigDecimal getChargeIdrToEur() {
		return chargeIdrToEur;
	}

	public void setChargeIdrToEur(BigDecimal chargeIdrToEur) {
		this.chargeIdrToEur = chargeIdrToEur;
	}

	public BigDecimal getChargeIdrToCny() {
		return chargeIdrToCny;
	}

	public void setChargeIdrToCny(BigDecimal chargeIdrToCny) {
		this.chargeIdrToCny = chargeIdrToCny;
	}
	
	
	
}
