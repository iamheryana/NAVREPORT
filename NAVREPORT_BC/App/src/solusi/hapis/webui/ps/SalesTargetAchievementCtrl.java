package solusi.hapis.webui.ps;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SalesTargetAchievementCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	
	
	protected Datebox dbTglUpto;

	protected Intbox  intTahun;

			
			
	protected Radiogroup rdgJnsLap;	 

	protected Radio rdDTL;
	protected Radio rdDTL2;
	protected Radio rdSUM1;
	protected Radio rdSUM2;

	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Decimalbox dcmNilai;
	
	protected Decimalbox dcmHigh;
	protected Decimalbox dcmMed;
	protected Decimalbox dcmLow;
	
	protected Combobox  cmbAkhirSem1;
	
//	protected Decimalbox dcmTargetPS;

	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	private String vProsesId;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
	
		rdDTL.setSelected(true); 
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);
    	
		rdXLS.setSelected(true); 
				

		Calendar cRTglUpto = Calendar.getInstance();
		cRTglUpto.setTime(new Date());
		cRTglUpto.set(Calendar.DAY_OF_MONTH, cRTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cRTglUpto.getTime();
		dbTglUpto.setValue(vTglUpto);   
		
		
		dcmNilai.setValue(new BigDecimal (500));
		
		dcmHigh.setValue(new BigDecimal (90));
		dcmMed.setValue(new BigDecimal (60));
		dcmLow.setValue(new BigDecimal (30));
		
//		dcmTargetPS.setValue(new BigDecimal (20));
		
		cmbAkhirSem1.setSelectedIndex(9);
		
		
		
    	
	}
	
	

	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		
		int vTahun = yearTglCurr;
		if(CommonUtils.isNotEmpty(intTahun.getValue())){
			vTahun = intTahun.getValue();
		}
		
				
//		BigDecimal vTargetPS = new BigDecimal(20);
//		if (CommonUtils.isNotEmpty(dcmTargetPS.getValue())) {
//			vTargetPS = dcmTargetPS.getValue();
//		}
		
		BigDecimal vNilai = new BigDecimal(500);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
		
		BigDecimal vHigh = new BigDecimal(90);
		if (CommonUtils.isNotEmpty(dcmHigh.getValue())) {
			vHigh = dcmHigh.getValue();
		} 
		
		BigDecimal vMed = new BigDecimal(60);
		if (CommonUtils.isNotEmpty(dcmMed.getValue())) {
			vMed = dcmMed.getValue();
		} 
		
		BigDecimal vLow = new BigDecimal(30);
		if (CommonUtils.isNotEmpty(dcmLow.getValue())) {
			vLow = dcmLow.getValue();
		} 
		
		
	
		int vSemAkhir = 10;
		if (cmbAkhirSem1.getSelectedItem().getValue() != null){
			vSemAkhir = Integer.valueOf((String) cmbAkhirSem1.getSelectedItem().getValue());
		}
		
		
		
		Calendar cRTglUpto = Calendar.getInstance();
		cRTglUpto.setTime(new Date());
		cRTglUpto.set(Calendar.DAY_OF_MONTH, cRTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpTo = cRTglUpto.getTime();
  
		String vStrTglTo =  "1900-01-01";
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
			
			vStrTglTo  = frmTgl.format(vTglUpTo);  
		}
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTglUpTo);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String vStrTglFrom = yearTglFrom+"/01/01";
		
		
		
		
	
		
		String vJnsLap = "DTL";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0601005");
		
		
		
		vProsesId = String.valueOf(System.currentTimeMillis());
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglTo, "ALL", "SRF");
		
				
		
		String jasperRpt = "/solusi/hapis/webui/reports/ps/05009_SalesTargetAchievement.jasper";
		
		if(vJnsLap.equals("DTL2")){			
			jasperRpt = "/solusi/hapis/webui/reports/ps/05009_SalesTargetAchievement_02.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/ps/05009_SalesTargetAchievement.jasper";
			
			param.put("JenisLap",  vJnsLap);
		}
		
		param.put("Tahun",  vTahun);
		param.put("AmtBig",  vNilai);
		param.put("SemAkhir",  vSemAkhir);			
		param.put("TglUpto",  vTglUpTo);		
		param.put("weightH",  vHigh);
		param.put("weightM",  vMed);
		param.put("weightL",  vLow);
		param.put("TargetPS",  0);//vTargetPS);				
		
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
		String vDelete = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglTo, "ALL", "DELETE");

	}
 
}

//