package solusi.hapis.webui.accounting.managementReporting;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ThirdPartyPSCostControlCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	

	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	protected Textbox txtNoSO;
	protected Textbox txtNoBSO;

	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		
		
		String dRFrom = "1/"+(monthTglFrom+1)+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
    	dbTglTo.setValue((new Date()));   
    	    	
    	
    	
    	
	}

	

	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		//SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/"+(monthTglFrom+1)+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		

		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();			
		}   
		
		Date vTglTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
		
		
		String  vNoSO = "ALL";
		if(CommonUtils.isNotEmpty(txtNoSO.getValue()) == true){  
			vNoSO = txtNoSO.getValue();
		}
		
		String  vNoBSO = "ALL";
		if(CommonUtils.isNotEmpty(txtNoBSO.getValue()) == true){  
			vNoBSO = txtNoBSO.getValue();
		}
				
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101006");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010108_3rdPartyPSCostControl.jasper";
		
		param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglTo);  
		param.put("NoSO",  vNoSO); 
		param.put("NoBSO",  vNoBSO); 

		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		

		
	}
 
}

