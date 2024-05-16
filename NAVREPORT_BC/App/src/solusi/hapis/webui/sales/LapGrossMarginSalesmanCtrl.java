package solusi.hapis.webui.sales;

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

public class LapGrossMarginSalesmanCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	


	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	private String vProsesId;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
//		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
			
		
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
		
		Calendar cRTglTo = Calendar.getInstance();
    	cRTglTo.setTime(new Date());
    	cRTglTo.set(Calendar.DAY_OF_MONTH, cRTglTo.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglTo = cRTglTo.getTime();
    	dbTglTo.setValue(vTglTo); 
		
    	//dbTglTo.setValue((new Date()));   
    	
    	
    	
    	
    	rdXLS.setSelected(true); 
	}
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
//		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		
				
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		
		Calendar cRTglTo = Calendar.getInstance();
    	cRTglTo.setTime(new Date());
    	cRTglTo.set(Calendar.DAY_OF_MONTH, cRTglTo.getActualMaximum(Calendar.DAY_OF_MONTH));
    	Date vTglTo = cRTglTo.getTime();
    	
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		//Date vTglTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
				
		
		vProsesId = String.valueOf(System.currentTimeMillis());
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  
		String vStrTglTo  = frmTgl.format(vTglTo);  

		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507016");
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callGrossMarginSalesman(vProsesId, vStrTglFrom, vStrTglTo, "CETAK");


		String jasperRpt = "/solusi/hapis/webui/reports/sales/04065_LapGrossMarginSalesman.jasper";
		
		param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglTo);
		param.put("JnsReport",  "SUM");
		
		param.put("ProsesId",  vProsesId);

		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}
		
		 
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callGrossMarginSalesman(vProsesId, vStrTglFrom, vStrTglTo, "DELETE");
		
		
		
	}
 
}


