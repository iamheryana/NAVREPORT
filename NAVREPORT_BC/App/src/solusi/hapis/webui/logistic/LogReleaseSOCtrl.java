package solusi.hapis.webui.logistic;

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

public class LogReleaseSOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Datebox dbTgl;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		dbTgl.setValue((new Date()));   
	}
	
	

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbTgl.getValue()) == true){  
			vTgl = dbTgl.getValue();
		}
		

		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0305009");
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03034_LogReleaseSO.jasper";
		
		param.put("TglLog",  vTgl); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	 
		
	}
 
}