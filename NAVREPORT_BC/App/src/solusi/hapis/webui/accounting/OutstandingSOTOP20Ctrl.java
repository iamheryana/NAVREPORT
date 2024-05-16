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

public class OutstandingSOTOP20Ctrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	//protected Datebox dbTglUpto;
 
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
//	protected Intbox intYearFrom;
//	protected Intbox intYearUpto;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	private String vProsesId;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
    	//dbTglUpto.setValue((new Date()));   
    	
    	rdALL.setSelected(true); 
    	
    	
//    	Calendar cTglFrom = Calendar.getInstance();		
//		cTglFrom.setTime(new Date());
//		int yearTgl = cTglFrom.get(Calendar.YEAR);		
		
//		intYearFrom.setValue(yearTgl);
//		intYearUpto.setValue(yearTgl);
    	    	

    	Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		dbTglUpto.setValue(vTglUpto);  
		
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date vTanggal = null;
//			try {
//				vTanggal = formatter.parse("1900-01-01");			
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}			

//		Date vTglUpTo = vTanggal;   
//		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
//			vTglUpTo = dbTglUpto.getValue();
//		}
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
//		Calendar cTglFrom = Calendar.getInstance();		
//		cTglFrom.setTime(new Date());
//		int yearTgl = cTglFrom.get(Calendar.YEAR);	
//		
//		
//		int vYearFrom = yearTgl;
//		if (CommonUtils.isNotEmpty(intYearFrom.getValue())) {
//			vYearFrom =intYearFrom.getValue();
//		} 
//		
//		int vYearUpto = yearTgl;
//		if (CommonUtils.isNotEmpty(intYearUpto.getValue())) {
//			vYearUpto =intYearUpto.getValue();
//		} 
		
		
		Calendar cTgl= Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		} 
		
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom); 
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		vProsesId = String.valueOf(System.currentTimeMillis());
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0103006");
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, "ALL", "CETAK");
		


		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01020_OutstandingSOTop20.jasper";
		

//		param.put("TglUpto",  vTglUpTo); 
		param.put("Company",  vCompany);
//		param.put("YearFrom",  vYearFrom); 
//		param.put("YearUpto",  vYearUpto); 
		
		param.put("EstRealFrom",  vTglFrom); 
		param.put("EstRealUpto",  vTglUpto); 
				
		param.put("ProsesId",  vProsesId); 
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, "ALL", "DELETE");
	}
 
}