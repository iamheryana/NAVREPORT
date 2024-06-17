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

public class PrePrintedProformaInvoiceCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtSONo;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;		
	
	
	protected Radiogroup rdgMaterai;	 
	protected Radio rdYes;
	protected Radio rdNo;		

	protected Radiogroup rdgInvoice;	 
	protected Radio rdInv;
	protected Radio rdProforma;
	
	protected Radiogroup rdgNoPO;	 
	protected Radio rdYesPO;
	protected Radio rdNoPO;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

		
    	rdPDF.setSelected(true);     	
    	rdYes.setSelected(true); 
    	
    	rdProforma.setSelected(true); 
    	
    	rdYesPO.setSelected(true); 
	}
	
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0108008");
		
		Messagebox.show("Sync Sudah Selesai");
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		

		
		String vSONo = ".";
		if (StringUtils.isNotEmpty(txtSONo.getValue())) {
			vSONo = txtSONo.getValue();
		} 
		
		
		
		String vPrintMaterai = "Y";
		if (StringUtils.isNotEmpty(rdgMaterai.getSelectedItem().getValue())) {
			vPrintMaterai = rdgMaterai.getSelectedItem().getValue();	
		} 
		
		String vPrintAs = "PROFORMA INVOICE";
		if (StringUtils.isNotEmpty(rdgInvoice.getSelectedItem().getValue())) {
			vPrintAs = rdgInvoice.getSelectedItem().getValue();	
		} 
				
		
		String vPrintPO = "Y";
		if (StringUtils.isNotEmpty(rdgNoPO.getSelectedItem().getValue())) {
			vPrintPO = rdgNoPO.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108008");		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/preprinted/010804_00_InvoiceSoftcopy.jasper";
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptAccountingPreprinted());
		
    	param.put("InvoiceFrom",  vSONo); 
		param.put("InvoiceUpto",  vSONo);  
		param.put("PrintMaterai",  vPrintMaterai); 
		param.put("Proforma",  vPrintAs); 
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

