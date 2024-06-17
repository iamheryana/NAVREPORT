package solusi.hapis.webui.accounting.preprinted;




import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Checkbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class KwitansiManualSoftcopyCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdAJ;
	protected Radio rdSP;	
	
	
	protected Textbox txtInvoiceNo;
	protected Textbox txtNoFakturPajak;
	protected Textbox txtSales;
	protected Textbox txtPembeli;
	protected Textbox txtUntukPembayaran;
	
	protected Datebox dbTglInvoice;
	protected Datebox dbTglJatuhTempo;
	
	protected Checkbox chbJatuhTempo;
	protected Checkbox chbPPN;
	
	protected Decimalbox dcmNilaiDPP;	
	
	protected Radiogroup rdgMaterai;	 
	protected Radio rdYes;
	protected Radio rdNo;	

	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

		rdAJ.setSelected(true);    
		
		dbTglInvoice.setValue(new Date());
		
		chbJatuhTempo.setChecked(false);
		
		chbPPN.setChecked(true);
		
		dcmNilaiDPP.setValue(new BigDecimal(0));
		
		
    	rdPDF.setSelected(true);     
    	
    	rdYes.setSelected(true); 
    	
    	
	}
	
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0108010");
		
		Messagebox.show("Sync Sudah Selesai");
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		

		
		String vNoInvoice = ".";
		if (StringUtils.isNotEmpty(txtInvoiceNo.getValue())) {
			vNoInvoice = txtInvoiceNo.getValue();
		} 
		
		Date vTglInvoice = new Date();
		if(CommonUtils.isNotEmpty(dbTglInvoice.getValue()) == true){  
			vTglInvoice = dbTglInvoice.getValue();
		}   
		
		
		Date vTglDuedate = new Date();
		if(CommonUtils.isNotEmpty(dbTglJatuhTempo.getValue()) == true){  
			vTglDuedate = dbTglJatuhTempo.getValue();
		}   
		
		
		String vSales = ".";
		if (StringUtils.isNotEmpty(txtSales.getValue())) {
			vSales = txtSales.getValue();
		} 
		
		
		String vPembeli = ".";
		if (StringUtils.isNotEmpty(txtPembeli.getValue())) {
			vPembeli = txtPembeli.getValue();
		} 
		
		
		String vUntukPembayaran = ".";
		if (StringUtils.isNotEmpty(txtUntukPembayaran.getValue())) {
			vUntukPembayaran = txtUntukPembayaran.getValue();
		} 
		
		String vNoFakturPajak = ".";
		if (StringUtils.isNotEmpty(txtNoFakturPajak.getValue())) {
			vNoFakturPajak = txtNoFakturPajak.getValue();
		} 
		
		
		BigDecimal vDPP = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmNilaiDPP.getValue())) {
			vDPP = dcmNilaiDPP.getValue();
		} 
		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		String vPrintMaterai = "Y";
		if (StringUtils.isNotEmpty(rdgMaterai.getSelectedItem().getValue())) {
			vPrintMaterai = rdgMaterai.getSelectedItem().getValue();	
		} 
		
		String vPrintJatuhTempo = "N";
		if(chbJatuhTempo.isChecked() == true){
			vPrintJatuhTempo ="Y";
		} else {
			vPrintJatuhTempo ="N";
		}
	

		String vAdaPPN = "Y";
		if(chbPPN.isChecked() == true){
			vAdaPPN ="Y";
		} else {
			vAdaPPN ="N";
		}
	
		
		

		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108010");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/preprinted/010806_00_KwitansiManualSoftcopy.jasper";
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptAccountingPreprinted());
		
		param.put("PrintMaterai",  vPrintMaterai); 
		param.put("NoInvoice",  vNoInvoice);  
		param.put("TglInvoice",  vTglInvoice);
		param.put("TglDuedate",  vTglDuedate); 		
		param.put("Sales",  vSales); 
		param.put("Pembeli",  vPembeli); 
		param.put("UntukPembayaran",  vUntukPembayaran); 
		param.put("NoFakturPajak",  vNoFakturPajak); 
		param.put("DPP",  vDPP); 
		param.put("Company",  vCompany); 
		param.put("PrintJatuhTempo",  vPrintJatuhTempo); 
		param.put("AdaPPN",  vAdaPPN); 
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {	
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
			}

		
		
		
		 
		
	}
 
}

