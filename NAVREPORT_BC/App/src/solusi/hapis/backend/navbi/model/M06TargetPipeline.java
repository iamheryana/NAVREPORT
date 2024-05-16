package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;

public class M06TargetPipeline {
	private static final long serialVersionUID = 1L;
	private long m06Id;
	private String tahun;
	private String jenis;
	private String slsOrCab;
	private BigDecimal target;
	private BigDecimal targetPs;
	private String status;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;	
	
	public M06TargetPipeline(){
		
	}

	
	public M06TargetPipeline(String tahun, String jenis, String slsOrCab,
			BigDecimal target,BigDecimal targetPs, String status) {
		super();
		this.tahun = tahun;
		this.jenis = jenis;
		this.slsOrCab = slsOrCab;
		this.target = target;
		this.targetPs = targetPs;
		this.status = status;
	}


	public long getM06Id() {
		return m06Id;
	}

	public void setM06Id(long m06Id) {
		this.m06Id = m06Id;
	}

	public String getTahun() {
		return tahun;
	}

	public void setTahun(String tahun) {
		this.tahun = tahun;
	}

	public String getJenis() {
		return jenis;
	}

	public void setJenis(String jenis) {
		this.jenis = jenis;
	}

	public String getSlsOrCab() {
		return slsOrCab;
	}

	public void setSlsOrCab(String slsOrCab) {
		this.slsOrCab = slsOrCab;
	}

	public BigDecimal getTarget() {
		return target;
	}

	public void setTarget(BigDecimal target) {
		this.target = target;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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


	public BigDecimal getTargetPs() {
		return targetPs;
	}


	public void setTargetPs(BigDecimal targetPs) {
		this.targetPs = targetPs;
	}
	
	
}
