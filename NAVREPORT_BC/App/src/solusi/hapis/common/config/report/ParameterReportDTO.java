package solusi.hapis.common.config.report;


public class ParameterReportDTO {

	private String paramName;
	private String paramValue;

	public ParameterReportDTO() {

	}
	
	public ParameterReportDTO(String paramName, String paramValue){
		this.paramName = paramName;
		this.paramValue = paramValue;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

}
