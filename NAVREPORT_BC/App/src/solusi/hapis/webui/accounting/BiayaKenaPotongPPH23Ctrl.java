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

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class BiayaKenaPotongPPH23Ctrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdAJ;
	protected Radio rdSP;

	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSum;
	protected Radio rdDtl;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/"+(monthTglFrom+1)+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
    	dbTglUpto.setValue((new Date()));    
    	
    	
    	rdAJ.setSelected(true); 
    	
    	rdSum.setSelected(true);    	
 
	
	}


	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
			

		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/"+(monthTglFrom+1)+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}
		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenisLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJenisLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0105008");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01075_01_BiayaKenaPotongPPH23Sum.jasper";
		if (vJenisLap.equals("SUM") == true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01075_01_BiayaKenaPotongPPH23Sum.jasper";
		
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01075_02_BiayaKenaPotongPPH23Detail.jasper";
	
		}
		
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpto);
		
		param.put("Company",  vCompany); 

		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}