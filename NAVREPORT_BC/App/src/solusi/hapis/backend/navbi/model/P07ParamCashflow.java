package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class P07ParamCashflow {
	
	private static final long serialVersionUID = 1L;
	private String kode;
	
	private BigDecimal  kursUsd;
	private BigDecimal  kursCny;
	private BigDecimal  kursSgd;
	private BigDecimal  kursEur;
	private BigDecimal  pibAj;
	private BigDecimal  pibSp;
	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public P07ParamCashflow(){
    	
    }

	public P07ParamCashflow(String kode, BigDecimal kursUsd,
			BigDecimal kursCny, BigDecimal kursSgd, BigDecimal kursEur,
			BigDecimal pibAj, BigDecimal pibSp) {
		super();
		this.kode = kode;
		this.kursUsd = kursUsd;
		this.kursCny = kursCny;
		this.kursSgd = kursSgd;
		this.kursEur = kursEur;
		this.pibAj = pibAj;
		this.pibSp = pibSp;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public BigDecimal getKursUsd() {
		return kursUsd;
	}

	public void setKursUsd(BigDecimal kursUsd) {
		this.kursUsd = kursUsd;
	}

	public BigDecimal getKursCny() {
		return kursCny;
	}

	public void setKursCny(BigDecimal kursCny) {
		this.kursCny = kursCny;
	}

	public BigDecimal getKursSgd() {
		return kursSgd;
	}

	public void setKursSgd(BigDecimal kursSgd) {
		this.kursSgd = kursSgd;
	}

	public BigDecimal getKursEur() {
		return kursEur;
	}

	public void setKursEur(BigDecimal kursEur) {
		this.kursEur = kursEur;
	}

	public BigDecimal getPibAj() {
		return pibAj;
	}

	public void setPibAj(BigDecimal pibAj) {
		this.pibAj = pibAj;
	}

	public BigDecimal getPibSp() {
		return pibSp;
	}

	public void setPibSp(BigDecimal pibSp) {
		this.pibSp = pibSp;
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
