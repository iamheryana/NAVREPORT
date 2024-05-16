package solusi.hapis.webui.accounting;

public class PNLList {
	private String noUrut;
	private String keterangan;
	private String amount;
	
	
	
	
	public PNLList(String noUrut, String keterangan, String amount) {
		this.noUrut = noUrut;
		this.keterangan = keterangan;
		this.amount = amount;
	}
	
	
	public String getNoUrut() {
		return noUrut;
	}
	public void setNoUrut(String noUrut) {
		this.noUrut = noUrut;
	}
	public String getKeterangan() {
		return keterangan;
	}
	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
	
}
