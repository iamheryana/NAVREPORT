package solusi.hapis.backend.navbi.model;


public class V01CustomerNav {
	private static final long serialVersionUID = 1L;
	
	private String custCode;
	private String custName;
	private String noNpwp;
	private String sectorCode;	
	private String sectorName;
	
	public V01CustomerNav(){
		
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getNoNpwp() {
		return noNpwp;
	}

	public void setNoNpwp(String noNpwp) {
		this.noNpwp = noNpwp;
	}

	public String getSectorCode() {
		return sectorCode;
	}

	public void setSectorCode(String sectorCode) {
		this.sectorCode = sectorCode;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
