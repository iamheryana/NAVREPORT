package solusi.hapis.webui.ps;


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

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class DataSalesRevenueDanOutSOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	

	protected Radiogroup rdgJnsData;	 
	protected Radio rdSR;
	protected Radio rdOUTSO;

	protected Datebox dbTglInvFrom;
	protected Datebox dbTglInvUpto;
	
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Row rowSR;
	protected Row rowOUTSO;
	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		rdSR.setSelected(true);
		rowSR.setVisible(true);
		rowOUTSO.setVisible(false);
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		int monthTgl = cTgl.get(Calendar.MONTH) + 1;
		
		
		String dRFrom = "1/"+monthTgl+"/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);		
		dbTglFrom.setValue(vTglFrom);  
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);		
		dbTglUpto.setValue(vTglUpto);    
		

		
		
		String dRInvFrom = "1/1/"+yearTgl;
		SimpleDateFormat dfRInvFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglInvFrom  = dfRInvFrom.parse(dRInvFrom);		
		dbTglInvFrom.setValue(vTglInvFrom);  


    	Calendar cRTglInvUpto = Calendar.getInstance();
    	cRTglInvUpto.setTime(new Date());
    	cRTglInvUpto.set(Calendar.DAY_OF_MONTH, cRTglInvUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglInvUpto = cRTglInvUpto.getTime();
		dbTglInvUpto.setValue(vTglInvUpto); 

	}
	
		
	public void onCheck$rdgJnsData(Event event){
		if(rdSR.isChecked() == true ) {
			rowSR.setVisible(true);
			rowOUTSO.setVisible(false);
		} else {
			rowSR.setVisible(false);
			rowOUTSO.setVisible(true);
		}
	}
   	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		int monthTgl = cTgl.get(Calendar.MONTH) + 1;
		
		
		String dRFrom = "1/"+monthTgl+"/"+yearTgl;
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
		
		
		String dRInvFrom = "1/1/"+yearTgl;
		SimpleDateFormat dfRInvFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglInvFrom  = dfRInvFrom.parse(dRInvFrom);		
		if(CommonUtils.isNotEmpty(dbTglInvFrom.getValue()) == true){  
			vTglInvFrom = dbTglInvFrom.getValue();
		}   

    	Calendar cRTglInvUpto = Calendar.getInstance();
    	cRTglInvUpto.setTime(new Date());
    	cRTglInvUpto.set(Calendar.DAY_OF_MONTH, cRTglInvUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglInvUpto = cRTglInvUpto.getTime();
		if(CommonUtils.isNotEmpty(dbTglInvUpto.getValue()) == true){  
			vTglInvUpto = dbTglInvUpto.getValue();
		}   
		
		
		
		
		
		
		String vJnsData = "SR";
		if (StringUtils.isNotEmpty(rdgJnsData.getSelectedItem().getValue())) {
			vJnsData = rdgJnsData.getSelectedItem().getValue();	
		} 
		
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom); 
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/ps/05011_SalesRevenueDataBI.jasper";
		
		
		if(vJnsData.equals("SR") == true){
			jasperRpt = "/solusi/hapis/webui/reports/ps/05011_SalesRevenueDataBI.jasper";
			
			param.put("TglFrom",  vTglInvFrom); 
			param.put("TglUpto",  vTglInvUpto); 
			
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/ps/05012_OutstandingSOData.jasper";
			

			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0601002");
			
			
			@SuppressWarnings("unused")
			String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", "ALL", "CETAK-RAW");

			param.put("EstRealFrom",  vTglFrom); 
			param.put("EstRealUpto",  vTglUpto); 
			
			param.put("ProsesId",  vProsesId); 
		}
		

	
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		
		if(vJnsData.equals("OUTSO") == true){
			@SuppressWarnings("unused")
			String vDelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", "ALL", "DELETE");
		}
	

 
		
	}
 
}



