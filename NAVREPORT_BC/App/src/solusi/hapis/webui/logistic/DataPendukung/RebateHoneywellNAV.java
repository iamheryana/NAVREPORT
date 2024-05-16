package solusi.hapis.webui.logistic.DataPendukung;


public class RebateHoneywellNAV {
	private String tanggal;
	private String noInvoice;
	private String noPO;
	private String itemNo;
	private String itemDesc;
	private String qty;
	private String keterangan;
	private double amount;
	private String keterangan2;
	
	public RebateHoneywellNAV(String tanggal, String noInvoice, String noPO,
			String itemNo, String itemDesc, String qty, String keterangan, double amount,
			String keterangan2) {
		this.tanggal = tanggal;
		this.noInvoice = noInvoice;
		this.noPO = noPO;
		this.itemNo = itemNo;
		this.itemDesc = itemDesc;
		this.qty = qty;
		this.keterangan = keterangan;
		this.amount = amount;
		this.keterangan2 = keterangan2;
	}
	
	
	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getNoInvoice() {
		return noInvoice;
	}

	public void setNoInvoice(String noInvoice) {
		this.noInvoice = noInvoice;
	}

	public String getNoPO() {
		return noPO;
	}

	public void setNoPO(String noPO) {
		this.noPO = noPO;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}


	public String getKeterangan() {
		return keterangan;
	}


	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getKeterangan2() {
		return keterangan2;
	}


	public void setKeterangan2(String keterangan2) {
		this.keterangan2 = keterangan2;
	}
	
	

}
