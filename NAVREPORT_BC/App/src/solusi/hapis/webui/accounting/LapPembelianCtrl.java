package solusi.hapis.webui.accounting;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapPembelianCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
		
	protected Radiogroup rdgTipe;	 
	protected Radio rdAll;
	protected Radio rdTrd;
	protected Radio rdNTrd;
	protected Radio rdTrdic;
	
	protected Textbox txtKodeVendorFrom;
	protected Textbox txtKodeVendorUpto;
		
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	dbTglTo.setValue((new Date()));   
    	
    	rdAJ.setSelected(true); 
    	rdSUM.setSelected(true); 
    	
    	txtKodeVendorUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
    	
    	rdAll.setSelected(true); 
    	rdPDF.setSelected(true); 
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		Date vTglFrom = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
				

		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenis = "Summary";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 
		
		String vTipe = "ALL";
		if (StringUtils.isNotEmpty(rdgTipe.getSelectedItem().getValue())) {
			vTipe = rdgTipe.getSelectedItem().getValue();	
		} 
			
		String vKodeVendorFrom = ".";
		if (StringUtils.isNotEmpty(txtKodeVendorFrom.getValue())) {
			vKodeVendorFrom = txtKodeVendorFrom.getValue();
		} 
		
		String vKodeVendorUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtKodeVendorUpto.getValue())) {
			vKodeVendorUpto = txtKodeVendorUpto.getValue();
		} 
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
				
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0104001");				
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01012_LapRegPembelian.jasper";
		
		
		
		
		PathReport  pathReport = new PathReport();
		
    	param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglTo);  
		param.put("Tipe",  vTipe);
		param.put("VendorFrom",  vKodeVendorFrom); 
		param.put("VendorTo",  vKodeVendorUpto); 
		param.put("SUBREPORT_DIR",  pathReport.getSubRptAccounting());
		param.put("Company",  vCompany); 
		param.put("JnsRpt",  vJenis); 
		param.put("Layout",  vSaveAs); 
		
		
	
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}
		
		 
		
	}
 
}