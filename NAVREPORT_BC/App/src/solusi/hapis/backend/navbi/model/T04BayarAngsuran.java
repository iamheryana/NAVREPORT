package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

import solusi.hapis.common.CommonUtils;

public class T04BayarAngsuran {
	private static final long serialVersionUID = 1L;
	private long t04Id;
	private String company;
	private String suppCode;
	private String noPo;
	private String valutaPo;
	private BigDecimal nilaiPo;
	private int jmlGiro;
	private int interval;
	private String intervalWaktu;
	private Date tmt;
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

	
	public T04BayarAngsuran(){
		
	}


	public long getT04Id() {
		return t04Id;
	}


	public void setT04Id(long t04Id) {
		this.t04Id = t04Id;
	}


	public String getSuppCode() {
		return suppCode;
	}


	public void setSuppCode(String suppCode) {
		this.suppCode =  CommonUtils.toUpperCase(suppCode);
	}


	public String getNoPo() {
		return noPo;
	}


	public void setNoPo(String noPo) {
		this.noPo =  CommonUtils.toUpperCase(noPo);
	}


	public String getValutaPo() {
		return valutaPo;
	}


	public void setValutaPo(String valutaPo) {
		this.valutaPo =  CommonUtils.toUpperCase(valutaPo);
	}


	public BigDecimal getNilaiPo() {
		return nilaiPo;
	}


	public void setNilaiPo(BigDecimal nilaiPo) {
		this.nilaiPo = nilaiPo;
	}


	public int getJmlGiro() {
		return jmlGiro;
	}


	public void setJmlGiro(int jmlGiro) {
		this.jmlGiro = jmlGiro;
	}


	public int getInterval() {
		return interval;
	}


	public void setInterval(int interval) {
		this.interval = interval;
	}


	public String getIntervalWaktu() {
		return intervalWaktu;
	}


	public void setIntervalWaktu(String intervalWaktu) {
		this.intervalWaktu = intervalWaktu;
	}


	public Date getTmt() {
		return tmt;
	}


	public void setTmt(Date tmt) {
		this.tmt = tmt;
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


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}
	
	
	

}
