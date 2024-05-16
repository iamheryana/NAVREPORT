package solusi.hapis.webui.accounting;


public class EFakturList {
	private String namaCust;
	private String noFakturPajak;	
	private String tanggal;
	private String status;
	private String dpp;
	private String ppn;
	private String referansi;
	private String statusApproval;

	


	public EFakturList(String namaCust, String noFakturPajak, String tanggal,
			String status, String dpp, String ppn, String referansi,
			String statusApproval) {
	
		this.namaCust = namaCust;
		this.noFakturPajak = noFakturPajak;
		this.tanggal = tanggal;
		this.status = status;
		this.dpp = dpp;
		this.ppn = ppn;
		this.referansi = referansi;
		this.statusApproval = statusApproval;
	}


	public String getStatusApproval() {
		return statusApproval;
	}


	public void setStatusApproval(String statusApproval) {
		this.statusApproval = statusApproval;
	}


	public String getNamaCust() {
		return namaCust;
	}


	public void setNamaCust(String namaCust) {
		this.namaCust = namaCust;
	}


	public String getTanggal() {
		return tanggal;
	}


	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
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


	public String getNoFakturPajak() {
		return noFakturPajak;
	}
	
	public void setNoFakturPajak(String noFakturPajak) {
		this.noFakturPajak = noFakturPajak;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}


	public String getReferansi() {
		return referansi;
	}


	public void setReferansi(String referansi) {
		this.referansi = referansi;
	}
	
	
	
}
