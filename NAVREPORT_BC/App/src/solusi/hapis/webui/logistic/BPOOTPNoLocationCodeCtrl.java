package solusi.hapis.webui.logistic;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class BPOOTPNoLocationCodeCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
    	    	
    	rdALL.setSelected(true); 
 	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0306003");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03018_BPOOTPNoLocationCode.jasper";
		
		
		param.put("Company",  vCompany); 

		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 	
		 
		
	}
 
}