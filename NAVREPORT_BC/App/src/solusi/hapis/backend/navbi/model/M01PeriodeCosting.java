package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class M01PeriodeCosting {
	private static final long serialVersionUID = 1L;
	private long m01Id;
	private String masa;
	private String tahun;
	private String closeKomisi;	
	private String closeTqs;	
	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public M01PeriodeCosting(){
    	
    }


	public M01PeriodeCosting(String masa, String tahun, String closeKomisi,
			String closeTqs) {
		super();
		this.masa = masa;
		this.tahun = tahun;
		this.closeKomisi = closeKomisi;
		this.closeTqs = closeTqs;
	}


	public long getM01Id() {
		return m01Id;
	}


	public void setM01Id(long m01Id) {
		this.m01Id = m01Id;
	}


	public String getMasa() {
		return masa;
	}


	public void setMasa(String masa) {
		this.masa = masa;
	}


	public String getTahun() {
		return tahun;
	}


	public void setTahun(String tahun) {
		this.tahun = tahun;
	}


	public String getCloseKomisi() {
		return closeKomisi;
	}


	public void setCloseKomisi(String closeKomisi) {
		this.closeKomisi = closeKomisi;
	}


	public String getCloseTqs() {
		return closeTqs;
	}


	public void setCloseTqs(String closeTqs) {
		this.closeTqs = closeTqs;
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
