package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class P06ParamDefaultRpt {
	private static final long serialVersionUID = 1L;
	private String kode;
	private String periodeKolomCf;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public P06ParamDefaultRpt(){
    	
    }

	public P06ParamDefaultRpt(String kode, String periodeKolomCf) {
		super();
		this.kode = kode;
		this.periodeKolomCf = periodeKolomCf;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getPeriodeKolomCf() {
		return periodeKolomCf;
	}

	public void setPeriodeKolomCf(String periodeKolomCf) {
		this.periodeKolomCf = periodeKolomCf;
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
