package solusi.hapis.util.constant;

import org.apache.commons.lang.StringUtils;

public enum LintasProdi {
	LINTAS("1", "LINTAS PRODI"), 
	NONLINTAS("0", "NON LINTAS PRODI");
	
	private String code;
	private String label;
	
	private LintasProdi(String code, String label){
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
		for (LintasProdi lintasProdi : LintasProdi.values()) {
			if(code.equals(lintasProdi.getCode()))
				return lintasProdi.getLabel();
		}
		
		return null;
	}
}
