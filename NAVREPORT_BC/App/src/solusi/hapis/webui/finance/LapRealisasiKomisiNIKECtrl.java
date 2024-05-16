package solusi.hapis.webui.finance;

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

public class LapRealisasiKomisiNIKECtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto; 
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	
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
    	    	
    	rdPDF.setSelected(true); 
    	
    
	}
	

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
				
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}
		
			
		Calendar cTglFromCurrent = Calendar.getInstance();		
		cTglFromCurrent.setTime(vTglFrom);
		
		int yearTglFromCurrent = cTglFromCurrent.get(Calendar.YEAR);
		int monthTglFromCurrent = cTglFromCurrent.get(Calendar.MONTH) + 1;
		int dayTglFromCurrent = cTglFromCurrent.get(Calendar.DAY_OF_MONTH);
		
		String dR2PrevTglFrom = dayTglFromCurrent+"/"+monthTglFromCurrent+"/"+(yearTglFromCurrent-1);
		SimpleDateFormat dfR2PrevTglFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vPrevTglFrom = dfR2PrevTglFrom.parse(dR2PrevTglFrom);
		
		Calendar cTglUptoCurrent = Calendar.getInstance();		
		cTglUptoCurrent.setTime(vTglUpto);
		
		int yearTglUptoCurrent = cTglUptoCurrent.get(Calendar.YEAR);
		int monthTglUptoCurrent = cTglUptoCurrent.get(Calendar.MONTH) + 1;
		int dayTglUptoCurrent = cTglUptoCurrent.get(Calendar.DAY_OF_MONTH);
		
		String dR2PrevTglUpto = dayTglUptoCurrent+"/"+monthTglUptoCurrent+"/"+(yearTglUptoCurrent-1);
		SimpleDateFormat dfR2PrevTglUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vPrevTglUpto = dfR2PrevTglUpto.parse(dR2PrevTglUpto);
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0203001");
				
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02025_LapRealisasiKomisiNIKE.jasper";
		
			
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpto); 
		
		param.put("TglFromLastYear",  vPrevTglFrom);
		param.put("TglUptoLastYear",  vPrevTglUpto); 
		
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