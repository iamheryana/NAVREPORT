package solusi.hapis.webui.accounting.pembelian;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class POReceivedBelumInvoiceCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Datebox dbTgl;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdALL;
	protected Radio rdAJ;
	protected Radio rdSP;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
	

		
    	dbTgl.setValue((new Date()));   
    	

		rdALL.setSelected(true); 
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbTgl.getValue()) == true){  
			vTgl = dbTgl.getValue();
		}
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0104008");			
		

		String jasperRpt = "/solusi/hapis/webui/reports/accounting/pembelian/010401_POReceivedBelumInvoice.jasper";
		

		param.put("Company",  vCompany); 
		
		param.put("TglReceived",  vTgl); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	
		
	}
 
}