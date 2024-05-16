package solusi.hapis.webui.accounting;

public class InvoiceNAVList {

	private String tglInvoice;
	private String noInvoice;
	private String noCreditMemo;
	private String tipeDok;
	private String custName;
	private String noFakturPajak;
	private String dpp;
	private String ppn;
	private String noInvoiceCancel;
	private String tglInvoiceCancel;
	private String noFakturPajakCancel;
	


	public InvoiceNAVList(String tglInvoice, String noInvoice,
			String noCreditMemo, String tipeDok, String custName,
			String noFakturPajak, String dpp, String ppn,
			String noInvoiceCancel, String tglInvoiceCancel,
			String noFakturPajakCancel) {

		this.tglInvoice = tglInvoice;
		this.noInvoice = noInvoice;
		this.noCreditMemo = noCreditMemo;
		this.tipeDok = tipeDok;
		this.custName = custName;
		this.noFakturPajak = noFakturPajak;
		this.dpp = dpp;
		this.ppn = ppn;
		this.noInvoiceCancel = noInvoiceCancel;
		this.tglInvoiceCancel = tglInvoiceCancel;
		this.noFakturPajakCancel = noFakturPajakCancel;
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

	public String getNoCreditMemo() {
		return noCreditMemo;
	}

	public void setNoCreditMemo(String noCreditMemo) {
		this.noCreditMemo = noCreditMemo;
	}

	
}
