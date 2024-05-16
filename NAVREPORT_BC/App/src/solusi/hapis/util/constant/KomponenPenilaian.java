package solusi.hapis.util.constant;

public enum KomponenPenilaian {
	ABSEN("1", "Komponen ABsen"), 
	UTS("2", "Komponen UTS"),
	TUGAS("3", "Komponen Tugas"), 
	UAS("4", "Komponen UAS");
	
	
	private String code;
	private String label;
	
	private KomponenPenilaian(String code, String label){
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
