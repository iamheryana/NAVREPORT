package solusi.hapis.webui.logistic;

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

public class PrintPORevisiCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtInvNo;
	protected Textbox txtEndUser;
	protected Textbox txtRMA;
	protected Textbox txtVersion;
	protected Textbox txtComment;
	protected Textbox txtTTD;
	protected Textbox txtNoPO;
	protected Textbox txtPEKurs;
	
	
	protected Radiogroup rdgJns;	 
	protected Radio rdBPO;
	protected Radio rdPO;		

	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;		

	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

		txtTTD.setValue("INDRA TJAHJADI");
		
		rdBPO.setSelected(true); 
		
    	rdPDF.setSelected(true); 
    	
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {

	
		String vNoPo = "ALL";
		if (StringUtils.isNotEmpty(txtNoPO.getValue())) {
			vNoPo = txtNoPO.getValue();
		}
		
		
		String vVersion = ".";
		if (StringUtils.isNotEmpty(txtVersion.getValue())) {
			vVersion = txtVersion.getValue();
		}
		
		String vEndUser = "";
		if (StringUtils.isNotEmpty(txtEndUser.getValue())) {
			vEndUser = txtEndUser.getValue();
		} 
		
		String vRMA = "";
		if (StringUtils.isNotEmpty(txtRMA.getValue())) {
			vRMA = txtRMA.getValue();
		}
		
		
		String vComment = "";
		if (StringUtils.isNotEmpty(txtComment.getValue())) {
			vComment = txtComment.getValue();
		}
		
	
		
		String vTTD= "INDRA TJAHJADI";
		if (StringUtils.isNotEmpty(txtTTD.getValue())) {
			vTTD = txtTTD.getValue();
		} 
		
		String vJns = "BPO";
		if (StringUtils.isNotEmpty(rdgJns.getSelectedItem().getValue())) {
			vJns = rdgJns.getSelectedItem().getValue();	
		} 
		
		
		String vPeKurs = "";
		if (StringUtils.isNotEmpty(txtPEKurs.getValue())) {
			vPeKurs = txtPEKurs.getValue();
		}
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0306006");	
		
	
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03028_PrintPORevisi.jasper";
		
		param.put("Reg",  vJns); 
    	param.put("Comment",  vComment); 
		param.put("Revisi",  vVersion);  
		param.put("RMA",  vRMA); 
		param.put("EndUser",  vEndUser); 
		param.put("NoPO",  vNoPo.toUpperCase()); 
		
		param.put("TTD",  vTTD); 
		param.put("PeKurs",  vPeKurs); 
		
		
		
		
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

