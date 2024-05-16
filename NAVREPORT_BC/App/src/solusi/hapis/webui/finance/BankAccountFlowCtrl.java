package solusi.hapis.webui.finance;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class BankAccountFlowCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowBankAccountFlow;

	

	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdAJ;
	protected Radio rdSP;
	protected Radio rdALL;
	
//	protected Radiogroup rdgCurr;	 
//	protected Radio rdIDR;
//	protected Radio rdUSD;


	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Textbox txtVendorNo;


	protected Bandbox  cmbProject;
	protected Listbox listProject;
	protected String vProject = "ALL";
	
	
	protected Button btnSearchVendorLOV;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		rdSUM.setSelected(true); 
		rdAJ.setSelected(true); 
//		rdIDR.setSelected(true); 
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		
		
		String dRFrom = "1/"+monthTglFrom+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
    	dbTglUpto.setValue((new Date()));   
    	    	

	}
	
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
			
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
				
		String dRFrom = "1/"+monthTglFrom+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		

		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		

		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
//		String vCurr = "IDR";
//		if (StringUtils.isNotEmpty(rdgCurr.getSelectedItem().getValue())) {
//			vCurr = rdgCurr.getSelectedItem().getValue();	
//		} 

		String vJnsLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0207007");
			
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02053_BankAccountFlowSUM.jasper";
		if (vJnsLap.equals("SUM") == true){
			jasperRpt = "/solusi/hapis/webui/reports/finance/02053_BankAccountFlowSUM.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/finance/02053_BankAccountFlowDTL.jasper";
		}

		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);

		param.put("Company",  vCompany);
		
//		param.put("CurrCode",  vCurr); 
		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}
