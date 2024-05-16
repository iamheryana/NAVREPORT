package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class POBySalesPMPCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
 
	
	

	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSUM;
	protected Radio rdDTLPO;
	protected Radio rdDTLPI;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		rdSUM.setSelected(true);     	

		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
    	dbTglUpto.setValue((new Date()));   
    	    	
    
	}
	


	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		

		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
	
		
		String vJnsLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04064_POBySalesPMP.jasper";
		
		if(vJnsLap.equals("SUM") == true){
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0506002-01");
			
			jasperRpt = "/solusi/hapis/webui/reports/sales/04064_POBySalesPMP.jasper";
		} else {
			if(vJnsLap.equals("DTLPO") == true){
				
				@SuppressWarnings("unused")
				String vSync = callStoreProcOrFuncService.callSyncAReport("0506002-01");
				
				jasperRpt = "/solusi/hapis/webui/reports/sales/04064_POBySalesPMP_DetailPO.jasper";
			} else {
				
				@SuppressWarnings("unused")
				String vSync = callStoreProcOrFuncService.callSyncAReport("0506002-02");
				
				jasperRpt = "/solusi/hapis/webui/reports/sales/04064_POBySalesPMP_DetailPipeline.jasper";
			}
		}
		

		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}