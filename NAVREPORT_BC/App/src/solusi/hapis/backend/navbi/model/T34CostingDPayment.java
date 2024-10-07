package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class T34CostingDPayment {
	private static final long serialVersionUID = 1L;
	private long t34Id;	
	private T29CostingH t29CostingH;	
	private String noInvoice;
	private Date tglInvoice;
	private BigDecimal nilaiInvoice;	
	private String noLunas;
	private Date tglLunas;
	private BigDecimal nilaiLunas;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	
	public T34CostingDPayment(){
    	
    }


	public long getT34Id() {
		return t34Id;
	}


	public void setT34Id(long t34Id) {
		this.t34Id = t34Id;
	}


	public T29CostingH getT29CostingH() {
		return t29CostingH;
	}


	public void setT29CostingH(T29CostingH t29CostingH) {
		this.t29CostingH = t29CostingH;
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


	public BigDecimal getNilaiInvoice() {
		return nilaiInvoice;
	}


	public void setNilaiInvoice(BigDecimal nilaiInvoice) {
		this.nilaiInvoice = nilaiInvoice;
	}


	public String getNoLunas() {
		return noLunas;
	}


	public void setNoLunas(String noLunas) {
		this.noLunas = noLunas;
	}


	public Date getTglLunas() {
		return tglLunas;
	}


	public void setTglLunas(Date tglLunas) {
		this.tglLunas = tglLunas;
	}


	public BigDecimal getNilaiLunas() {
		return nilaiLunas;
	}


	public void setNilaiLunas(BigDecimal nilaiLunas) {
		this.nilaiLunas = nilaiLunas;
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
