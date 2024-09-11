package solusi.hapis.webui.logistic.pembelian;


import java.io.Serializable;
import java.text.ParseException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class CekTOPPOSynnexCtrl extends GFCBaseCtrl implements Serializable {

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
		String vSync = callStoreProcOrFuncService.callSyncAReport("0306013");	
		
				
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/pembelian/030503_CekTOPPOSynnex.jasper";

		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}
