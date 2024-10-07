package solusi.hapis.webui.accounting.managementReporting;


import java.io.Serializable;
import java.text.ParseException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class GrossSalesMarginBySOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtNoSO;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
 	    	
    	
    	
    	
	}

	

	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		
		String  vNoSO = ".";
		if(CommonUtils.isNotEmpty(txtNoSO.getValue()) == true){  
			vNoSO = txtNoSO.getValue();
		}
	
				
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101007");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010109_GrossSalesMarginBySO.jasper";
		
		param.put("NoSO",  vNoSO); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		

		
	}
 
}

