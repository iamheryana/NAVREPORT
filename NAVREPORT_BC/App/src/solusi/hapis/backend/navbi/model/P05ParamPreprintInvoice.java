package solusi.hapis.backend.navbi.model;

import java.util.Date;


public class P05ParamPreprintInvoice {
	private static final long serialVersionUID = 1L;
	private String kode;
	private String emailFinance;
	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public P05ParamPreprintInvoice(){
    	
    }
	
	

	public P05ParamPreprintInvoice(String kode, String emailFinance) {
		super();
		this.kode = kode;
		this.emailFinance = emailFinance;
	}



	public String getKode() {
		return kode;
	}



	public void setKode(String kode) {
		this.kode = kode;
	}



	public String getEmailFinance() {
		return emailFinance;
	}



	public void setEmailFinance(String emailFinance) {
		this.emailFinance = emailFinance;
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
