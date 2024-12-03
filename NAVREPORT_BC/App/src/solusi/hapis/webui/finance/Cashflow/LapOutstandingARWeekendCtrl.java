package solusi.hapis.webui.finance.Cashflow;


import java.io.Serializable;
import java.text.ParseException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapOutstandingARWeekendCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	

	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	
	
	}
	
	


		
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
	
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0201004");
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/Cashflow/0102001_Weekend_AR.jasper";
		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
	
	
//	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
//		
//		@SuppressWarnings("unused")
//		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0201004");
//		
//		Messagebox.show("Sync Sudah Selesai");
//	}
 
}