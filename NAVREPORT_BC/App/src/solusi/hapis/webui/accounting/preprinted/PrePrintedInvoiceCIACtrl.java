package solusi.hapis.webui.accounting.preprinted;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class PrePrintedInvoiceCIACtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtInvFrom;
	protected Textbox txtInvUpto;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;		
	protected Radio rdCSV;		
	
	protected Radiogroup rdgJthTempo;	 
	protected Radio rdYes;
	protected Radio rdNo;	

	protected Radiogroup rdgPrintItem;	 
	protected Radio rdIYes;
	protected Radio rdINo;	
	
	protected Radiogroup rdgMaterai;	 
	protected Radio rdMYes;
	protected Radio rdMNo;	
	
	protected Radiogroup rdgNoPO;	 
	protected Radio rdYesPO;
	protected Radio rdNoPO;	
	
	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

		
    	rdPDF.setSelected(true);     	
    	rdNo.setSelected(true);   
    	rdIYes.setSelected(true); 
    	
    	rdMNo.setSelected(true);   
    	rdYesPO.setSelected(true);   
    	
    	
	}
	
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0108005");
		
		@SuppressWarnings("unused")
		String vSync2 = callStoreProcOrFuncService.callSyncAReportManual("0108006");
		
		Messagebox.show("Sync Sudah Selesai");
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		

		
		String vInvFrom = ".";
		if (StringUtils.isNotEmpty(txtInvFrom.getValue())) {
			vInvFrom = txtInvFrom.getValue();
		} 
		
		String vInvUpto = "ZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtInvUpto.getValue())) {
			vInvUpto = txtInvUpto.getValue();
		} 
		
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		String vPrintJatuhTempo = "N";
		if (StringUtils.isNotEmpty(rdgJthTempo.getSelectedItem().getValue())) {
			vPrintJatuhTempo = rdgJthTempo.getSelectedItem().getValue();	
		} 
		
		String vPrintItemNol = "Y";
		if (StringUtils.isNotEmpty(rdgPrintItem.getSelectedItem().getValue())) {
			vPrintItemNol = rdgPrintItem.getSelectedItem().getValue();	
		} 
		
		
		String vPrintMaterai = "Y";
		if (StringUtils.isNotEmpty(rdgMaterai.getSelectedItem().getValue())) {
			vPrintMaterai = rdgMaterai.getSelectedItem().getValue();	
		} 
				
		
		String vPrintPO = "Y";
		if (StringUtils.isNotEmpty(rdgNoPO.getSelectedItem().getValue())) {
			vPrintPO = rdgNoPO.getSelectedItem().getValue();	
		} 
		
		
		if (vSaveAs.equals("CSV") == false){
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0108005");
			
			
			String jasperRpt = "/solusi/hapis/webui/reports/accounting/preprinted/010801_InvoiceManualCIA.jasper";
			PathReport pathReport = new PathReport();
			param.put("SUBREPORT_DIR",  pathReport.getSubRptAccountingPreprinted());
			
	    	param.put("InvoiceFrom",  vInvFrom); 
			param.put("InvoiceUpto",  vInvUpto);  
			param.put("CetakJatuhTempo",  vPrintJatuhTempo);
			param.put("PrintItemNol",  vPrintItemNol); 
			param.put("PrintMaterai",  vPrintMaterai); 
			param.put("PrintPO",  vPrintPO); 
			
			if(vSaveAs.equals("PDF")){
				new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
			} else {	
				new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
			}

		} else {
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0108006");
			
			String jasperRpt = "/solusi/hapis/webui/reports/accounting/preprinted/010802_CSVInvoiceManualCIA.jasper";
			
			param.put("InvoiceFrom",  vInvFrom); 
			param.put("InvoiceUpto",  vInvUpto); 
			param.put("PrintItemNol",  vPrintItemNol); 
	    	
	    	
			new JReportGeneratorWindow(param, jasperRpt, "CSVEF-"+vInvFrom+"-"+vInvUpto); 
		}
		
		
		
		 
		
	}
 
}

