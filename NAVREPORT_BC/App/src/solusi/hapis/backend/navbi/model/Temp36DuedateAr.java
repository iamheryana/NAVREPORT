package solusi.hapis.backend.navbi.model;


import java.util.Date;

public class Temp36DuedateAr {
	private static final long serialVersionUID = 1L;
	private long temp36Id;
	private String sellTo;	
	private String sellToName;		
	private String billTo;	
	private String billToName;	
	private String cabang;
	private String noInvoice;
	private String tglInvoice;
	private String duedateAr;
	private String amountAr;
	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	
	public Temp36DuedateAr(){
		
	}

	
	
	
	public Temp36DuedateAr(String sellTo, String sellToName, String billTo,
			String billToName, String cabang, String noInvoice,
			String tglInvoice, String duedateAr, String amountAr, String prosesId) {
		super();
		this.sellTo = sellTo;
		this.sellToName = sellToName;
		this.billTo = billTo;
		this.billToName = billToName;
		this.cabang = cabang;
		this.noInvoice = noInvoice;
		this.tglInvoice = tglInvoice;
		this.duedateAr = duedateAr;
		this.amountAr = amountAr;
		this.prosesId = prosesId;
	}




	public long getTemp36Id() {
		return temp36Id;
	}



	public void setTemp36Id(long temp36Id) {
		this.temp36Id = temp36Id;
	}



	public String getSellTo() {
		return sellTo;
	}



	public void setSellTo(String sellTo) {
		this.sellTo = sellTo;
	}



	public String getSellToName() {
		return sellToName;
	}



	public void setSellToName(String sellToName) {
		this.sellToName = sellToName;
	}



	public String getBillTo() {
		return billTo;
	}



	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}



	public String getBillToName() {
		return billToName;
	}



	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}



	public String getCabang() {
		return cabang;
	}



	public void setCabang(String cabang) {
		this.cabang = cabang;
	}



	public String getNoInvoice() {
		return noInvoice;
	}



	public void setNoInvoice(String noInvoice) {
		this.noInvoice = noInvoice;
	}



	public String getTglInvoice() {
		return tglInvoice;
	}



	public void setTglInvoice(String tglInvoice) {
		this.tglInvoice = tglInvoice;
	}



	public String getDuedateAr() {
		return duedateAr;
	}



	public void setDuedateAr(String duedateAr) {
		this.duedateAr = duedateAr;
	}



	public String getAmountAr() {
		return amountAr;
	}



	public void setAmountAr(String amountAr) {
		this.amountAr = amountAr;
	}




	public String getProsesId() {
		return prosesId;
	}


	public void setProsesId(String prosesId) {
		this.prosesId = prosesId;
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
