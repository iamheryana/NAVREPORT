package solusi.hapis.webui.sales.Historical;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class TrackingSOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	
	protected Textbox txtBSO;
	protected Textbox txtSO;	
	protected Textbox txtNoPOCustomer;

	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
     	

	}
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		

		
		String vNoBSO = ".";
		if (StringUtils.isNotEmpty(txtBSO.getValue())) {
			vNoBSO = txtBSO.getValue();
		} 
		
		String vNoSO = ".";
		if (StringUtils.isNotEmpty(txtSO.getValue())) {
			vNoSO = txtSO.getValue();
		} 
		
		String vNoPOCustomer = "ALL";
		if (StringUtils.isNotEmpty(txtNoPOCustomer.getValue())) {
			vNoPOCustomer = txtNoPOCustomer.getValue();
		} 
		
	
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0504002");
		
		
				
		String jasperRpt = "/solusi/hapis/webui/reports/sales/Historical/040401_TrackingSO.jasper";
		
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptSalesHistorical());
		
		
		param.put("NoBSO",  vNoBSO); 
		param.put("NoSO",  vNoSO); 		
		param.put("NoPOCustomer",  vNoPOCustomer); 	

		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		 
		
	}
 
}