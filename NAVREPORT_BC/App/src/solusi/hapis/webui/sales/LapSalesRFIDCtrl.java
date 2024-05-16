package solusi.hapis.webui.sales;



import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Intbox;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapSalesRFIDCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
		
	protected Intbox  intTahun;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);		
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
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04059_LapSalesRFID.jasper";
		
		param.put("Tahun",  vTahun);
			
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	}
 
}