package solusi.hapis.common.config.report;

import java.util.ArrayList;
import java.util.List;

public class ReportDTO {

	public static String REPORT_FORMAT_PDF = "pdf";
	private String rptLocation;
	private String rptFormat;
	private String rptJudul;
	private List<ParameterReportDTO> paramReports;

	public ReportDTO() {
		this.paramReports = new ArrayList<ParameterReportDTO>();
	}

	public String getRptLocation() {
		return rptLocation;
	}

	public void setRptLocation(String rptLocation) {
		this.rptLocation = rptLocation;
	}

	public String getRptFormat() {
		return rptFormat;
	}

	public void setRptFormat(String rptFormat) {
		this.rptFormat = rptFormat;
	}

	public String getRptJudul() {
		return rptJudul;
	}

	public void setRptJudul(String rptJudul) {
		this.rptJudul = rptJudul;
	}

	public List<ParameterReportDTO> getParamReports() {
		return paramReports;
	}

	public void setParamReports(List<ParameterReportDTO> paramReports) {
		this.paramReports = paramReports;
	}
	
	public String getExportFile(){
		return getRptJudul()+"."+getRptFormat();
	}

}
