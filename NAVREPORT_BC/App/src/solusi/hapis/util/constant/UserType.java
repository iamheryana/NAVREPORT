package solusi.hapis.util.constant;

public enum UserType {
	
	ALL(0, "All"),
	SEKOLAH(1, "Sekolah"),
	PRODI(2, "Prodi"),
	DOSEN(3, "Dosen"),
	MAHASISWA(4, "Mahasiswa");
	
	private int code;
	private String label;
	
	private UserType(int code, String label){
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
	
	

}
