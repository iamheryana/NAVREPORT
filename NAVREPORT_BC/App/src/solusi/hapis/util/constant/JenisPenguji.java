package solusi.hapis.util.constant;

import org.apache.commons.lang.StringUtils;

public enum JenisPenguji {
	
	MODERATOR("1", "Moderator"), 
	KETUA("2", "Ketua"), 
	PENGUJI1("3", "Penguji1"), 
	PENGUJI2("4", "Penguji2"), 
	OTHERS("5", "Others");
		
	private String code;
	private String label;
	
	private JenisPenguji(String code, String label){
		this.code = code;
		this.label = label;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static String getLabelByCode(String code){
		if(StringUtils.isNotEmpty(code))
		for (JenisPenguji jenisPenguji : JenisPenguji.values()) {
			if(code.equals(jenisPenguji.getCode()))
				return jenisPenguji.getLabel();
		}
		
		return null;
	}

}
