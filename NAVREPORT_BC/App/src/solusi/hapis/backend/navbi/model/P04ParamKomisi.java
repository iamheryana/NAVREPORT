package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class P04ParamKomisi {
	private static final long serialVersionUID = 1L;
	private String kode;
	private String finance;
	private String finance2;
	private String finance3;
	private String finance4;
	private String finance5;
	private String spvFinance;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public P04ParamKomisi(){
    	
    }
	
	

	public P04ParamKomisi(String kode, String finance, String finance2, String finance3, String finance4, String finance5, String spvFinance) {
		super();
		this.kode = kode;
		this.finance = finance;
		this.finance2 = finance2;
		this.finance3 = finance3;
		this.finance4 = finance4;
		this.finance5 = finance5;
		this.spvFinance = spvFinance;
	}



	public String getKode() {
		return kode;
	}



	public void setKode(String kode) {
		this.kode = kode;
	}



	public String getFinance() {
		return finance;
	}



	public void setFinance(String finance) {
		this.finance = finance;
	}



	public String getFinance2() {
		return finance2;
	}



	public void setFinance2(String finance2) {
		this.finance2 = finance2;
	}



	public String getSpvFinance() {
		return spvFinance;
	}



	public void setSpvFinance(String spvFinance) {
		this.spvFinance = spvFinance;
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



	public String getFinance3() {
		return finance3;
	}



	public void setFinance3(String finance3) {
		this.finance3 = finance3;
	}



	public String getFinance4() {
		return finance4;
	}



	public void setFinance4(String finance4) {
		this.finance4 = finance4;
	}



	public String getFinance5() {
		return finance5;
	}



	public void setFinance5(String finance5) {
		this.finance5 = finance5;
	}
	
	
	
}
