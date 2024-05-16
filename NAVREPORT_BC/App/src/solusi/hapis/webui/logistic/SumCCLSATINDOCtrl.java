package solusi.hapis.webui.logistic;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Intbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class SumCCLSATINDOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Intbox intTahun;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Date vTgl = new Date();   
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(vTgl);
		int yearTgl = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTgl);
		
    	
	}
	
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
			
		Date vTgl = new Date();   
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(vTgl);
		int yearTgl = cTgl.get(Calendar.YEAR);
    	    	
		int vTahun = yearTgl;
		if (CommonUtils.isNotEmpty(intTahun.getValue())) {
			vTahun = intTahun.getValue();
		}
		
		intTahun.setValue(yearTgl);

		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507008");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04023_SumCCL-SAT-INDO.jasper";
		

		param.put("Tahun",  vTahun);
	
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}
