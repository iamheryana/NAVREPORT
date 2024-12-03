package solusi.hapis.webui.sales.POBySales;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SalesPOVSREVCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	
	protected Intbox  intTahun;
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);
		rdSUM.setSelected(true);     	

	}

	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		
		int vTahun = yearTglCurr;
		if(CommonUtils.isNotEmpty(intTahun.getValue())){
			vTahun = intTahun.getValue();
		}
		
		String vJnsLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/POBySales/0406001_Sales_POVSREV.jasper";
		
		if (vJnsLap.equals("SUM") == true){
			jasperRpt = "/solusi/hapis/webui/reports/sales/POBySales/0406001_Sales_POVSREV.jasper";
			
			PathReport pathReport = new PathReport();
			param.put("SUBREPORT_DIR",  pathReport.getSubRptSalesPOBySales());
			
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/sales/POBySales/0406002_Sales_POVSREV_Detail.jasper";
		}
		
		
		
		param.put("Tahun",  vTahun);
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}