package solusi.hapis.backend.tabel.model;

import java.math.BigDecimal;
import java.util.Date;

public class T07itemsatindo {	
	private static final long serialVersionUID = 1L;
	private int t07Id;
	private Date tglBerlaku;	
	private String noItem;
	private BigDecimal satAmountKomisi;
	private BigDecimal satAmountBns;
	private BigDecimal idmrAmountKomisi;
	private BigDecimal idmrAmountBns;	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T07itemsatindo(){
		
	}

	public T07itemsatindo(Date tglBerlaku, String noItem,
			BigDecimal satAmountKomisi, BigDecimal satAmountBns,
			BigDecimal idmrAmountKomisi, BigDecimal idmrAmountBns) {
		super();
		this.tglBerlaku = tglBerlaku;
		this.noItem = noItem;
		this.satAmountKomisi = satAmountKomisi;
		this.satAmountBns = satAmountBns;
		this.idmrAmountKomisi = idmrAmountKomisi;
		this.idmrAmountBns = idmrAmountBns;
	}

	public int getT07Id() {
		return t07Id;
	}

	public void setT07Id(int t07Id) {
		this.t07Id = t07Id;
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


}
