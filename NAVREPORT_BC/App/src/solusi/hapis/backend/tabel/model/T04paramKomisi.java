package solusi.hapis.backend.tabel.model;

import java.math.BigDecimal;
import java.util.Date;

public class T04paramKomisi {
	private static final long serialVersionUID = 1L;
	private String kode;
	private String finance;
	private String finance2;
	private String spvFinance;
	private String bod;
	private String berita;
	private String namapic;
	private String hppic;	
	private BigDecimal provisiusdtousd;
	private BigDecimal chargeusdtousd;
	private BigDecimal provisiidrtousd;
	private BigDecimal chargeidrtousd;
	private BigDecimal provisiidrtoidrnonbcabawah;
	private BigDecimal provisiidrtoidrnonbcaatas;  
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T04paramKomisi(){
    	
    }
	
	

	public T04paramKomisi(String kode, String finance, String finance2, String spvFinance,
			String bod) {
		super();
		this.kode = kode;
		this.finance = finance;
		this.finance2 = finance2;
		this.spvFinance = spvFinance;
		this.bod = bod;
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
	
	public String getBod() {
		return bod;
	}
	
	public void setBod(String bod) {
		this.bod = bod;
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



	public String getBerita() {
		return berita;
	}



	public void setBerita(String berita) {
		this.berita = berita;
	}



	public String getNamapic() {
		return namapic;
	}



	public void setNamapic(String namapic) {
		this.namapic = namapic;
	}



	public String getHppic() {
		return hppic;
	}



	public void setHppic(String hppic) {
		this.hppic = hppic;
	}



	public BigDecimal getProvisiusdtousd() {
		return provisiusdtousd;
	}



	public void setProvisiusdtousd(BigDecimal provisiusdtousd) {
		this.provisiusdtousd = provisiusdtousd;
	}



	public BigDecimal getChargeusdtousd() {
		return chargeusdtousd;
	}



	public void setChargeusdtousd(BigDecimal chargeusdtousd) {
		this.chargeusdtousd = chargeusdtousd;
	}



	public BigDecimal getProvisiidrtousd() {
		return provisiidrtousd;
	}



	public void setProvisiidrtousd(BigDecimal provisiidrtousd) {
		this.provisiidrtousd = provisiidrtousd;
	}



	public BigDecimal getChargeidrtousd() {
		return chargeidrtousd;
	}



	public void setChargeidrtousd(BigDecimal chargeidrtousd) {
		this.chargeidrtousd = chargeidrtousd;
	}



	public BigDecimal getProvisiidrtoidrnonbcabawah() {
		return provisiidrtoidrnonbcabawah;
	}



	public void setProvisiidrtoidrnonbcabawah(BigDecimal provisiidrtoidrnonbcabawah) {
		this.provisiidrtoidrnonbcabawah = provisiidrtoidrnonbcabawah;
	}



	public BigDecimal getProvisiidrtoidrnonbcaatas() {
		return provisiidrtoidrnonbcaatas;
	}



	public void setProvisiidrtoidrnonbcaatas(BigDecimal provisiidrtoidrnonbcaatas) {
		this.provisiidrtoidrnonbcaatas = provisiidrtoidrnonbcaatas;
	}
	
	
	
}
