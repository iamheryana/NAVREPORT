package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class M04ItemSatindo {
	private static final long serialVersionUID = 1L;
	private long m04Id;
	private Date tglBerlaku;	
	private String noItem;
	private BigDecimal satAmountKomisi;
	private BigDecimal satAmountBns;
	private BigDecimal idmrAmountKomisi;
	private BigDecimal idmrAmountBns;	
	private BigDecimal satAmountBnsSales;
	private BigDecimal idmrAmountBnsSales;	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public M04ItemSatindo(){
		
	}

	public M04ItemSatindo(Date tglBerlaku, String noItem,
			BigDecimal satAmountKomisi, BigDecimal satAmountBns, BigDecimal satAmountBnsSales,
			BigDecimal idmrAmountKomisi, BigDecimal idmrAmountBns, BigDecimal idmrAmountBnsSales
			) {
		super();
		this.tglBerlaku = tglBerlaku;
		this.noItem = noItem;
		this.satAmountKomisi = satAmountKomisi;
		this.satAmountBns = satAmountBns;
		this.satAmountBnsSales = satAmountBnsSales;
		this.idmrAmountKomisi = idmrAmountKomisi;
		this.idmrAmountBns = idmrAmountBns;
		this.idmrAmountBnsSales = idmrAmountBnsSales;
	}

	public long getM04Id() {
		return m04Id;
	}

	public void setM04Id(long m04Id) {
		this.m04Id = m04Id;
	}

	public Date getTglBerlaku() {
		return tglBerlaku;
	}

	public void setTglBerlaku(Date tglBerlaku) {
		this.tglBerlaku = tglBerlaku;
	}

	public String getNoItem() {
		return noItem;
	}

	public void setNoItem(String noItem) {
		this.noItem = noItem;
	}

	public BigDecimal getSatAmountKomisi() {
		return satAmountKomisi;
	}

	public void setSatAmountKomisi(BigDecimal satAmountKomisi) {
		this.satAmountKomisi = satAmountKomisi;
	}

	public BigDecimal getSatAmountBns() {
		return satAmountBns;
	}

	public void setSatAmountBns(BigDecimal satAmountBns) {
		this.satAmountBns = satAmountBns;
	}

	public BigDecimal getIdmrAmountKomisi() {
		return idmrAmountKomisi;
	}

	public void setIdmrAmountKomisi(BigDecimal idmrAmountKomisi) {
		this.idmrAmountKomisi = idmrAmountKomisi;
	}

	public BigDecimal getIdmrAmountBns() {
		return idmrAmountBns;
	}

	public void setIdmrAmountBns(BigDecimal idmrAmountBns) {
		this.idmrAmountBns = idmrAmountBns;
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

	public BigDecimal getSatAmountBnsSales() {
		return satAmountBnsSales;
	}

	public void setSatAmountBnsSales(BigDecimal satAmountBnsSales) {
		this.satAmountBnsSales = satAmountBnsSales;
	}

	public BigDecimal getIdmrAmountBnsSales() {
		return idmrAmountBnsSales;
	}

	public void setIdmrAmountBnsSales(BigDecimal idmrAmountBnsSales) {
		this.idmrAmountBnsSales = idmrAmountBnsSales;
	}
	
	

}
