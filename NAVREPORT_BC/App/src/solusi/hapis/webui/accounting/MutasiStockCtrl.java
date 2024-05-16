package solusi.hapis.webui.accounting;


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
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class MutasiStockCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
 	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
		
	protected Textbox txtItemNo;
	protected Textbox txtLocation;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());

		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);


		dbTglFrom.setValue(vTglFrom);
		
		dbTglUpto.setValue((new Date()));   
    	    	
    	rdAJ.setSelected(true); 
    	

    	
	}
	
	
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		

		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());

		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTanggal  = dfRFrom.parse(dRFrom);


		Date vTglFrom = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}
			
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		
		String vItemNo = ".";
		if (StringUtils.isNotEmpty(txtItemNo.getValue())) {
			vItemNo = txtItemNo.getValue();
		} 
		
		String vLocation = ".";
		if (StringUtils.isNotEmpty(txtLocation.getValue())) {
			vLocation = txtLocation.getValue();
		} 
		
		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0107007");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01045_MutasiStock.jasper";
		
		PathReport pathReport = new PathReport();
		
		param.put("SUBREPORT_DIR",  pathReport.getSubRptAccounting());
		
		param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglUpTo); 
		
		param.put("Location",  vLocation.toUpperCase()); 
		param.put("ItemNo",  vItemNo.toUpperCase());
		
		param.put("Company",  vCompany); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}