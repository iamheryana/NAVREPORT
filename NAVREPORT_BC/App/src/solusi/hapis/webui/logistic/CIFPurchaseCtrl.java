package solusi.hapis.webui.logistic;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.model.Vendor;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.lov.VendorLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class CIFPurchaseCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	protected Radio rdDTLCC;
	protected Radio rdDTLCC2;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Datebox dbTglGRFrom;
	protected Datebox dbTglGRUpto;
	

	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	protected Window windowCIFPurchase;
	
	protected Textbox txtVendorNo;
	
	protected Textbox txtNoPOFrom;
	protected Textbox txtNoPOUpto;
	
	protected Row rowNoPO;
	protected Row rowTglGR;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		rdSUM.setSelected(true); 
		rdALL.setSelected(true); 
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		dbTglGRFrom.setValue(vTglFrom);  
		
		dbTglUpto.setValue((new Date())); 
		dbTglGRUpto.setValue((new Date())); 
    	    	
		txtVendorNo.setValue("ALL");  	
	
	 	txtNoPOUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
   
    	
	}
	
	public void onCheck$rdgJnsLap(Event event){
		if(rdDTLCC2.isChecked() == true ) {			
			rowNoPO.setVisible(true);	
			rowTglGR.setVisible(true);	
		} else {			
			rowNoPO.setVisible(false);
			rowTglGR.setVisible(false);
		}		
	}
	
	public void onClick$btnSearchVendorLOV(Event event) {
		Vendor cust = VendorLOV.show(windowCIFPurchase);

		if (cust != null) {
			txtVendorNo.setValue(cust.getCode());
		} else {
			txtVendorNo.setValue(null);
		}

	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		

		
		String vJnsLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		}
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		

		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglGRFrom  = dfRFrom.parse(dRFrom);		

		if(CommonUtils.isNotEmpty(dbTglGRFrom.getValue()) == true){  
			vTglGRFrom = dbTglGRFrom.getValue();
		}   
		
		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		} 
		
		Date vTglGRUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglGRUpto.getValue()) == true){  
			vTglGRUpto = dbTglGRUpto.getValue();
		} 
		
		String vVendorNo = "ALL";
		if (StringUtils.isNotEmpty(txtVendorNo.getValue())) {
			vVendorNo = txtVendorNo.getValue();
		}


		String vNoPOFrom = ".";
		if (StringUtils.isNotEmpty(txtNoPOFrom.getValue())) {
			vNoPOFrom = txtNoPOFrom.getValue();
		} 
		
		String vNoPOUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtNoPOUpto.getValue())) {
			vNoPOUpto = txtNoPOUpto.getValue();
		} 


		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0306004");
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03020_CIFPurchaseSum.jasper";
		
		if (vJnsLap.equals("SUM") == true){
			jasperRpt = "/solusi/hapis/webui/reports/logistic/03020_CIFPurchaseSum.jasper";
		} else {
			if (vJnsLap.equals("DTL") == true){
				jasperRpt = "/solusi/hapis/webui/reports/logistic/03021_CIFPurchaseDetail.jasper";
			} else {
				if (vJnsLap.equals("DTLCC") == true){
					jasperRpt = "/solusi/hapis/webui/reports/logistic/03022_CIFPurchaseCrossChecked.jasper";
				} else {
					jasperRpt = "/solusi/hapis/webui/reports/logistic/03022_01_CIFPurchaseCrossChecked.jasper";
					param.put("NoPOFrom",  vNoPOFrom); 
					param.put("NoPOUpto",  vNoPOUpto); 
					
					
					param.put("TglGRFrom",  vTglGRFrom); 
					param.put("TglGRUpto",  vTglGRUpto); 
				}
			}
		}
		
		if (vJnsLap.equals("DTLCC")||vJnsLap.equals("DTLCC2")){
			PathReport pathReport = new PathReport();
			param.put("SUBREPORT_DIR",  pathReport.getSubRptLogitic());
		}

		param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglUpto); 
		param.put("Company",  vCompany); 
		param.put("VendorNo",  vVendorNo); 
		
			
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}