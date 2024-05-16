package solusi.hapis.webui.accounting;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class InvMidiCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Textbox txtInvFrom;
	protected Textbox txtInvUpto;
	
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;		

	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	rdAJ.setSelected(true); 
    	rdPDF.setSelected(true); 
    	
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		

		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vInvFrom = ".";
		if (StringUtils.isNotEmpty(txtInvFrom.getValue())) {
			vInvFrom = txtInvFrom.getValue();
		} 
		
		String vInvUpto = "ZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtInvUpto.getValue())) {
			vInvUpto = txtInvUpto.getValue();
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108001");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01026_InvMidi.jasper";
		
		
    	param.put("InvFrom",  vInvFrom); 
		param.put("InvUpto",  vInvUpto);  
		param.put("Company",  vCompany); 
		
		
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

