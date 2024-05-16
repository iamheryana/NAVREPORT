package solusi.hapis.util.constant;

public enum SudahBelum {
	SUDAH("Y", "Sudah"), 
	BELUM("N", "Belum");
	
	private String code;
	private String label;
	
	private SudahBelum(String code, String label){
		this.code = code;
		this.label = label;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
}
