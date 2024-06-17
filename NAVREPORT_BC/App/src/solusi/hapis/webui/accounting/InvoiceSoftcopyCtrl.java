package solusi.hapis.webui.accounting;


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

public class InvoiceSoftcopyCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtInvFrom;
	protected Textbox txtInvUpto;

	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;		
	
	
	protected Radiogroup rdgMaterai;	 
	protected Radio rdYes;
	protected Radio rdNo;		

	protected Radiogroup rdgNoPO;	 
	protected Radio rdYesPO;
	protected Radio rdNoPO;
	
	protected Radiogroup rdgFlagPPN;	 
	protected Radio rdYesPPN;
	protected Radio rdNoPPN;
	



	private CallStoreProcOrFuncService callStoreProcOrFuncService;


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

		
    	rdPDF.setSelected(true);     	
    	rdYes.setSelected(true); 
    	rdYesPO.setSelected(true); 
    	rdYesPPN.setSelected(true); 
    	
	}
	
	
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0108009");
		
		Messagebox.show("Sync Sudah Selesai");
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnKwitansi(Event event) throws InterruptedException {
		
		

		
		String vInvFrom = ".";
		if (StringUtils.isNotEmpty(txtInvFrom.getValue())) {
			vInvFrom = txtInvFrom.getValue();
		} 
		
		String vInvUpto = "ZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtInvUpto.getValue())) {
			vInvUpto = txtInvUpto.getValue();
		} 
		
		
		String vPrintMaterai = "Y";
		if (StringUtils.isNotEmpty(rdgMaterai.getSelectedItem().getValue())) {
			vPrintMaterai = rdgMaterai.getSelectedItem().getValue();	
		} 
				
		
		String vFlagPPN = "Y";
		if (StringUtils.isNotEmpty(rdgFlagPPN.getSelectedItem().getValue())) {
			vFlagPPN = rdgFlagPPN.getSelectedItem().getValue();	
		} 
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108009");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/preprinted/010805_00_KwitansiSoftcopy.jasper";
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptAccountingPreprinted());
		
    	param.put("InvoiceFrom",  vInvFrom); 
		param.put("InvoiceUpto",  vInvUpto);  
		param.put("PrintMaterai",  vPrintMaterai); 
		param.put("FlagPPN",  vFlagPPN); 
		
		
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
	
	@SuppressWarnings("unchecked")
	public void onClick$btnKwitansi2(Event event) throws InterruptedException {
		
		

		
		String vInvFrom = ".";
		if (StringUtils.isNotEmpty(txtInvFrom.getValue())) {
			vInvFrom = txtInvFrom.getValue();
		} 
		
		String vInvUpto = "ZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtInvUpto.getValue())) {
			vInvUpto = txtInvUpto.getValue();
		} 
		
		
		String vPrintMaterai = "Y";
		if (StringUtils.isNotEmpty(rdgMaterai.getSelectedItem().getValue())) {
			vPrintMaterai = rdgMaterai.getSelectedItem().getValue();	
		} 
				
		

		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108009");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/preprinted/010805_00_KwitansiSoftcopy_02.jasper";
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptAccountingPreprinted());
		
    	param.put("InvoiceFrom",  vInvFrom); 
		param.put("InvoiceUpto",  vInvUpto);  
		param.put("PrintMaterai",  vPrintMaterai); 
		
		
		
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
		
		
		String vPrintMaterai = "Y";
		if (StringUtils.isNotEmpty(rdgMaterai.getSelectedItem().getValue())) {
			vPrintMaterai = rdgMaterai.getSelectedItem().getValue();	
		} 	
		

		String vPrintPO = "Y";
		if (StringUtils.isNotEmpty(rdgNoPO.getSelectedItem().getValue())) {
			vPrintPO = rdgNoPO.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108003");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01059_00_InvoiceSoftcopy.jasper";
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptAccounting());
		
    	param.put("InvoiceFrom",  vInvFrom); 
		param.put("InvoiceUpto",  vInvUpto);  
		param.put("PrintMaterai",  vPrintMaterai); 
		param.put("PrintPO",  vPrintPO); 
		
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
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK2(Event event) throws InterruptedException {
		
		

		
		String vInvFrom = ".";
		if (StringUtils.isNotEmpty(txtInvFrom.getValue())) {
			vInvFrom = txtInvFrom.getValue();
		} 
		
		String vInvUpto = "ZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtInvUpto.getValue())) {
			vInvUpto = txtInvUpto.getValue();
		} 
		
		
		String vPrintMaterai = "Y";
		if (StringUtils.isNotEmpty(rdgMaterai.getSelectedItem().getValue())) {
			vPrintMaterai = rdgMaterai.getSelectedItem().getValue();	
		} 	
		

		String vPrintPO = "Y";
		if (StringUtils.isNotEmpty(rdgNoPO.getSelectedItem().getValue())) {
			vPrintPO = rdgNoPO.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108003");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01059_00_InvoiceSoftcopy_02.jasper";
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptAccounting());
		
    	param.put("InvoiceFrom",  vInvFrom); 
		param.put("InvoiceUpto",  vInvUpto);  
		param.put("PrintMaterai",  vPrintMaterai); 
		param.put("PrintPO",  vPrintPO); 
		
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

