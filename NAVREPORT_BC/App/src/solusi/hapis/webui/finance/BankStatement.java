package solusi.hapis.webui.finance;

import java.util.Date;


public class BankStatement {
	private Date tanggal;
	private String keterangan;
	private Double amount;
	
	
	public BankStatement (Date tanggal, String keterangan, Double amount){
		this.tanggal = tanggal;
		this.keterangan = keterangan;
		this.amount = amount;
	}
		
	public Date getTanggal() {
		return tanggal;
	}
	
	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}
	
	public String getKeterangan() {
		return keterangan;
	}
	
	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
