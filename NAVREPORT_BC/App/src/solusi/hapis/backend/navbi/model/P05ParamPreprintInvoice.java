package solusi.hapis.backend.navbi.model;

import java.util.Date;


public class P05ParamPreprintInvoice {
	private static final long serialVersionUID = 1L;
	private String kode;
	private String emailFinance;
	private String ttdSby;
	private String jabatanSby;
	private String ttdCkr;
	private String jabatanCkr;
	private String ttdSmr;
	private String jabatanSmr;
	private String ttdDps;
	private String jabatanDps;	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public P05ParamPreprintInvoice(){
    	
    }
	
	





	public P05ParamPreprintInvoice(String kode, String emailFinance,
			String ttdSby, String jabatanSby, String ttdCkr, String jabatanCkr,
			String ttdSmr, String jabatanSmr, String ttdDps, String jabatanDps) {
		super();
		this.kode = kode;
		this.emailFinance = emailFinance;
		this.ttdSby = ttdSby;
		this.jabatanSby = jabatanSby;
		this.ttdCkr = ttdCkr;
		this.jabatanCkr = jabatanCkr;
		this.ttdSmr = ttdSmr;
		this.jabatanSmr = jabatanSmr;
		this.ttdDps = ttdDps;
		this.jabatanDps = jabatanDps;

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



	public String getTtdSby() {
		return ttdSby;
	}



	public void setTtdSby(String ttdSby) {
		this.ttdSby = ttdSby;
	}



	public String getJabatanSby() {
		return jabatanSby;
	}



	public void setJabatanSby(String jabatanSby) {
		this.jabatanSby = jabatanSby;
	}



	public String getTtdCkr() {
		return ttdCkr;
	}



	public void setTtdCkr(String ttdCkr) {
		this.ttdCkr = ttdCkr;
	}



	public String getJabatanCkr() {
		return jabatanCkr;
	}



	public void setJabatanCkr(String jabatanCkr) {
		this.jabatanCkr = jabatanCkr;
	}



	public String getTtdSmr() {
		return ttdSmr;
	}



	public void setTtdSmr(String ttdSmr) {
		this.ttdSmr = ttdSmr;
	}



	public String getJabatanSmr() {
		return jabatanSmr;
	}



	public void setJabatanSmr(String jabatanSmr) {
		this.jabatanSmr = jabatanSmr;
	}



	public String getTtdDps() {
		return ttdDps;
	}



	public void setTtdDps(String ttdDps) {
		this.ttdDps = ttdDps;
	}



	public String getJabatanDps() {
		return jabatanDps;
	}



	public void setJabatanDps(String jabatanDps) {
		this.jabatanDps = jabatanDps;
	}

	
	
	
}
