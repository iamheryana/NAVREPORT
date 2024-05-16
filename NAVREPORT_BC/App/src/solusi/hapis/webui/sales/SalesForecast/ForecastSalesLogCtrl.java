package solusi.hapis.webui.sales.SalesForecast;


import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ForecastSalesLogCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	dbTglFrom.setValue(new Date());  
    	
		dbTglUpto.setValue(new Date());  
		

	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
	
		
		Date vTglFrom  =new Date();
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		

		Date vTglUpto  = new Date();		
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}   
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0503008");
				

		String jasperRpt = "/solusi/hapis/webui/reports/sales/SalesForecast/040301_ForecastSalesLog.jasper";
	
		param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglUpto);  
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
					
		
		
	}

 
	
}

