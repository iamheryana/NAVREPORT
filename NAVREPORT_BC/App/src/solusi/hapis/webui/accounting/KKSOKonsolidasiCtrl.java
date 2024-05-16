package solusi.hapis.webui.accounting;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class KKSOKonsolidasiCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglUpto;  
	protected Textbox txtLocation;    
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
		
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	dbTglUpto.setValue((new Date()));   
    
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}			


		Date vTglUpTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
						
		String vLocation = ".";
		if (StringUtils.isNotEmpty(txtLocation.getValue())) {
			vLocation = txtLocation.getValue();
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0107001");
		
			
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01027_KKSORekonsiliasi.jasper";
			
		param.put("Tanggal",  vTglUpTo); 
		param.put("Location",  vLocation.toUpperCase()); 

		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
				
	}
 
}