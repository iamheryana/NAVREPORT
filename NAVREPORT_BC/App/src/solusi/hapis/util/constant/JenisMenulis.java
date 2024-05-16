package solusi.hapis.util.constant;

public enum JenisMenulis {
	
	THESIS("1", "Thesis"),
	PROPOSAL("2", "Proposal"),
	MAKALAH("3", "Makalah");
	
	private String code;
	private String label;
	
	private JenisMenulis(String code, String label){
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
