package solusi.hapis.backend.tabel.model;

import java.util.Date;

public class Tmp05navfaktur implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int tmp05Id;	
	private String tglInvoice;
	private String noInvoice;
	private String noCreditMemo;
	private String tipeDok;
	private String custName;
	private String noFakturPajak;
	private String noFakturPajakOri;
	private String dpp;
	private String ppn;
	private String noInvoiceCancel;
	private String tglInvoiceCancel;
	private String noFakturPajakCancel;
	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public Tmp05navfaktur(String tglInvoice, String noInvoice,
			String noCreditMemo, String tipeDok, String custName,
			String noFakturPajak, String noFakturPajakOri, String dpp, String ppn,
			String noInvoiceCancel, String tglInvoiceCancel,
			String noFakturPajakCancel, String prosesId) {

		this.tglInvoice = tglInvoice;
		this.noInvoice = noInvoice;
		this.noCreditMemo = noCreditMemo;
		this.tipeDok = tipeDok;
		this.custName = custName;
		this.noFakturPajak = noFakturPajak;
		this.noFakturPajakOri = noFakturPajakOri;
		this.dpp = dpp;
		this.ppn = ppn;
		this.noInvoiceCancel = noInvoiceCancel;
		this.tglInvoiceCancel = tglInvoiceCancel;
		this.noFakturPajakCancel = noFakturPajakCancel;
		this.prosesId = prosesId;
	}

	public int getTmp05Id() {
		return tmp05Id;
	}

	public void setTmp05Id(int tmp05Id) {
		this.tmp05Id = tmp05Id;
	}

	public String getTglInvoice() {
		return tglInvoice;
	}

	public void setTglInvoice(String tglInvoice) {
		this.tglInvoice = tglInvoice;
	}

	public String getNoInvoice() {
		return noInvoice;
	}

	public void setNoInvoice(String noInvoice) {
		this.noInvoice = noInvoice;
	}

	public String getNoCreditMemo() {
		return noCreditMemo;
	}

	public void setNoCreditMemo(String noCreditMemo) {
		this.noCreditMemo = noCreditMemo;
	}

	public String getTipeDok() {
		return tipeDok;
	}

	public void setTipeDok(String tipeDok) {
		this.tipeDok = tipeDok;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getNoFakturPajak() {
		return noFakturPajak;
	}

	public void setNoFakturPajak(String noFakturPajak) {
		this.noFakturPajak = noFakturPajak;
	}
	
	public String getNoFakturPajakOri() {
		return noFakturPajakOri;
	}

	public void setNoFakturPajakOri(String noFakturPajakOri) {
		this.noFakturPajakOri = noFakturPajakOri;
	}

	public String getDpp() {
		return dpp;
	}

	public void setDpp(String dpp) {
		this.dpp = dpp;
	}

	public String getPpn() {
		return ppn;
	}

	public void setPpn(String ppn) {
		this.ppn = ppn;
	}

	public String getNoInvoiceCancel() {
		return noInvoiceCancel;
	}

	public void setNoInvoiceCancel(String noInvoiceCancel) {
		this.noInvoiceCancel = noInvoiceCancel;
	}

	public String getTglInvoiceCancel() {
		return tglInvoiceCancel;
	}

	public void setTglInvoiceCancel(String tglInvoiceCancel) {
		this.tglInvoiceCancel = tglInvoiceCancel;
	}

	public String getNoFakturPajakCancel() {
		return noFakturPajakCancel;
	}

	public void setNoFakturPajakCancel(String noFakturPajakCancel) {
		this.noFakturPajakCancel = noFakturPajakCancel;
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
