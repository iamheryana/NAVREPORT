package solusi.hapis.webui.sales;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.tabel.service.T01managementadjService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapDetailSRCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglInvFrom;  
	protected Datebox dbTglInvTo;

	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	private T01managementadjService T01managementadjService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglInvFrom.setValue(vTglFrom);  
    	dbTglInvTo.setValue((new Date()));   

    	dbTglFrom.setValue(vTglFrom);  
    	
		
		String dRUpto = "31/12/"+yearTglFrom;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		dbTglUpto.setValue(vTglUpto);  
		

	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglInvFrom  = dfRFrom.parse(dRFrom);
		
		
		
		
		
		if(CommonUtils.isNotEmpty(dbTglInvFrom.getValue()) == true){  
			vTglInvFrom = dbTglInvFrom.getValue();
		}   
		
		Date vTglInvTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglInvTo.getValue()) == true){  
			vTglInvTo = dbTglInvTo.getValue();
		}
				
		
		
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		
		String dRUpto = "31/12/"+yearTglFrom;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}   
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507015");

		String jasperRpt = "/solusi/hapis/webui/reports/sales/04062_LapDetailSR.jasper";
	
		param.put("TglInvFrom",  vTglInvFrom); 
		param.put("TglInvTo",  vTglInvTo);  
		param.put("EstRealFrom",  vTglFrom); 
		param.put("EstRealUpto",  vTglUpto);  
	
		new JReportGeneratorWindow(param, jasperRpt, "CSV"); 
					
		
		
	}

	public T01managementadjService getT01managementadjService() {
		return T01managementadjService;
	}


	public void setT01managementadjService(
			T01managementadjService t01managementadjService) {
		T01managementadjService = t01managementadjService;
	}
 
	
}

