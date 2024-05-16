package solusi.hapis.webui.accounting;

import java.io.Serializable;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class InfoPurchaseInvoiceCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Textbox txtNoBPO; 
	protected Textbox txtNoPO; 
	protected Textbox txtNoGR; 
	protected Textbox txtNoInv; 
	protected Textbox txtNoInvSupp; 
	

	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	

	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		

    	rdPDF.setSelected(true);   	
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		String  vNoBPO = "*";
		if(CommonUtils.isNotEmpty(txtNoBPO.getValue()) == true){  
			vNoBPO = txtNoBPO.getValue();
		}
		
		String  vNoPO = "*";
		if(CommonUtils.isNotEmpty(txtNoPO.getValue()) == true){  
			vNoPO = txtNoPO.getValue();
		}
		
		String  vNoGR = "*";
		if(CommonUtils.isNotEmpty(txtNoGR.getValue()) == true){  
			vNoGR = txtNoGR.getValue();
		}
		
		String  vNoInv = "*";
		if(CommonUtils.isNotEmpty(txtNoInv.getValue()) == true){  
			vNoInv = txtNoInv.getValue();
		}
		
		String  vNoInvSupp = "*";
		if(CommonUtils.isNotEmpty(txtNoInvSupp.getValue()) == true){  
			vNoInvSupp = txtNoInvSupp.getValue();
		}
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0104004");		
				
				
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01033_InfoPurchaseInvoice.jasper";

		
		param.put("NoBPO",  vNoBPO); 
		param.put("NoPO",  vNoPO); 
		param.put("NoGR",  vNoGR); 
		param.put("NoInv",  vNoInv); 
		param.put("NoInvSupp",  vNoInvSupp); 
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}
		
	}
 
}
