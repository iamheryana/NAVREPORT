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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ForecastOutSOBCACtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	protected Intbox intYearFrom;
	protected Intbox intYearUpto;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdJns1;
	protected Radio rdJns2;
	protected Radio rdJns3;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Row rowCompany;
	protected Row rowTahun;
	protected Row rowTanggal;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	private String vProsesId;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
    	
    	rdALL.setSelected(true); 
    	
    	
    	
    	Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		
		intYearFrom.setValue(yearTgl);
		intYearUpto.setValue(yearTgl);
		
		String dRFrom = "1/1/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		dbTglUpto.setValue(vTglUpto);  
    	    	
		rdJns1.setSelected(true); 
		
		rowCompany.setVisible(true);
		rowTahun.setVisible(false);		
		rowTanggal.setVisible(true);
	}
	
	public void onCheck$rdgJenis(Event event){
		if(rdJns1.isChecked() == true) {
			
			rowCompany.setVisible(true);
			rowTahun.setVisible(false);		
			rowTanggal.setVisible(true);
			
		} else {
			if(rdJns2.isChecked() == true) {
				rowCompany.setVisible(true);
				rowTahun.setVisible(true);		
				rowTanggal.setVisible(false);
			} else {
				if(rdJns3.isChecked() == true) {
					rowCompany.setVisible(false);
					rowTahun.setVisible(true);		
					rowTanggal.setVisible(false);
				} 
			}
		}
			
		
	}
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String vJenis = "JNS1";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 
		
		

		Calendar cTgl= Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		
		int vYearFrom = yearTgl;
		if (CommonUtils.isNotEmpty(intYearFrom.getValue())) {
			vYearFrom =intYearFrom.getValue();
		} 
		
		int vYearUpto = yearTgl;
		if (CommonUtils.isNotEmpty(intYearUpto.getValue())) {
			vYearUpto =intYearUpto.getValue();
		} 
		
		
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
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02044_OutSOBCA.jasper";
		
		if(vJenis.equals("JNS1") == true){
			param.put("Company",  vCompany);	
			
			param.put("EstRealFrom",  vTglFrom); 
			param.put("EstRealUpto",  vTglUpto); 
			
			param.put("ProsesId",  vProsesId); 
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0207004");
			
			@SuppressWarnings("unused")
			String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, "ALL", "CETAK");

			
			
			jasperRpt = "/solusi/hapis/webui/reports/finance/02044_OutSOBCA.jasper";
		} else if(vJenis.equals("JNS2") == true){
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0207005");
			
			
			param.put("Company",  vCompany);	
			
			param.put("YearFrom",  vYearFrom); 
			param.put("YearUpto",  vYearUpto); 
			
			jasperRpt = "/solusi/hapis/webui/reports/finance/02045_ForecastBCA.jasper";
		} else if(vJenis.equals("JNS3") == true){
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0207006");
			
			param.put("YearFrom",  vYearFrom); 
			param.put("YearUpto",  vYearUpto); 
			
			jasperRpt = "/solusi/hapis/webui/reports/finance/02046_Forecast4PillarBCA.jasper";
		}
		

				
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		if(vJenis.equals("JNS1") == true){
			@SuppressWarnings("unused")
			String vDelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, "ALL", "DELETE");

		}
	}
 
}