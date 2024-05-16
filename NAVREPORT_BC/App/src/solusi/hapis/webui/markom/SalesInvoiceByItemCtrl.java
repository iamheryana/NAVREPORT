package solusi.hapis.webui.markom;



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
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SalesInvoiceByItemCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtItemNo;
	protected Textbox txtCustNo;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	

	protected Radiogroup rdgJnsRpt;	 
	protected Radio rdSum;
	protected Radio rdDtl;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
	
    	
    	rdSum.setSelected(true); 

    	dbTglUpto.setValue((new Date()));   
    	
    	rdPDF.setSelected(true); 
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		

		String vItemNo = "*****";
		if (StringUtils.isNotEmpty(txtItemNo.getValue())) {
			vItemNo = txtItemNo.getValue();
		} 
		
		String vCustNo = "ALL";
		if (StringUtils.isNotEmpty(txtCustNo.getValue())) {
			vCustNo = txtCustNo.getValue();
		} 
		
		
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
		
		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}
		
		String vJnsRpt = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsRpt.getSelectedItem().getValue())) {
			vJnsRpt = rdgJnsRpt.getSelectedItem().getValue();	
		} 
			
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0701006");
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06004_SalesInvoiceByItemSum.jasper";
		
		if(vJnsRpt.equals("SUM")){
			jasperRpt = "/solusi/hapis/webui/reports/markom/06004_SalesInvoiceByItemSum.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/markom/06003_SalesInvoiceByItem.jasper";
		}
		
		
		
		param.put("NoItem",  vItemNo); 
		param.put("CustNo",  vCustNo); 
		
		param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglUpto); 
		
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