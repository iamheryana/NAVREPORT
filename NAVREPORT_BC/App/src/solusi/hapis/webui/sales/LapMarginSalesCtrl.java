package solusi.hapis.webui.sales;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapMarginSalesCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	


	protected Decimalbox dcmNilai;
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
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
			
		
		String dRFrom = "1/"+monthTglFrom+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
		
		Calendar cRTglTo = Calendar.getInstance();
    	cRTglTo.setTime(new Date());
    	cRTglTo.set(Calendar.DAY_OF_MONTH, cRTglTo.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglTo = cRTglTo.getTime();
    	dbTglTo.setValue(vTglTo); 
		
    	//dbTglTo.setValue((new Date()));   
    	
    	
    	dcmNilai.setValue(new BigDecimal (200));
    	
    	
    	rdXLS.setSelected(true); 
	}
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		
		String dRFromAwalTahun = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFromAwalTahun= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFromAwalTahun  = dfRFromAwalTahun.parse(dRFromAwalTahun);
		
				
		String dRFrom = "1/"+monthTglFrom+"/"+yearTglFrom;
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
				
		Calendar calTglFrom = Calendar.getInstance();		
		calTglFrom.setTime(vTglTo);
		int yearParamTglFrom = calTglFrom.get(Calendar.YEAR);
		int monthParamTglFrom = calTglFrom.get(Calendar.MONTH) + 1;
		
		String dRFromParamAwalTahun = "1/1/"+yearParamTglFrom;
		SimpleDateFormat dfRParamFromAwalTahun= new SimpleDateFormat("dd/MM/yyyy");
		vTglFromAwalTahun  = dfRParamFromAwalTahun.parse(dRFromParamAwalTahun);
		

		BigDecimal vNilai = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
		
		vProsesId = String.valueOf(System.currentTimeMillis());
		String vProsesId_2 = String.valueOf(System.currentTimeMillis()) + 1;
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglAwalTahun  = frmTgl.format(vTglFromAwalTahun);  
		String vStrTglFrom  = frmTgl.format(vTglFrom);  
		String vStrTglTo  = frmTgl.format(vTglTo);  
		
		String vStrTglStartParam2  = yearParamTglFrom+"-04-01";
		
		
		if (	monthParamTglFrom == 1 || monthParamTglFrom == 2 || monthParamTglFrom == 3 ||
				monthParamTglFrom == 4 || monthParamTglFrom == 5 || monthParamTglFrom == 6
			) {
			vStrTglStartParam2 = yearParamTglFrom+"-04-01";
		} else {
			if (monthParamTglFrom == 7 || monthParamTglFrom == 8 || monthParamTglFrom == 9 ) {
				vStrTglStartParam2 = yearParamTglFrom+"-07-01";
			} else {
				vStrTglStartParam2 = yearParamTglFrom+"-10-01";
			}
		}
			
		//System.out.println("monthParamTglFrom : " + monthParamTglFrom);
		//System.out.println("vStrTglStartParam2 : " + vStrTglStartParam2);
		//Date vTglStartParam2  = frmTgl.parse(vStrTglStartParam2);		
		

		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507001");
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callGrossSalesMargin(vProsesId, vStrTglAwalTahun, vStrTglFrom, vStrTglTo, "CETAK");


		if (monthParamTglFrom >= 4){	
			
			@SuppressWarnings("unused")
			String vResult_2 = callStoreProcOrFuncService.callGrossSalesMargin(vProsesId_2, vStrTglAwalTahun, vStrTglStartParam2, vStrTglTo, "CETAK");
		
		}

		
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04061_LapGrossSalesMargin.jasper";
		
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptSales());
		
		param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglTo);  
		param.put("ParamValue",  vNilai); 
		param.put("ProsesId",  vProsesId);
		param.put("ProsesId_2",  vProsesId_2);

		
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
		String vDelete = callStoreProcOrFuncService.callGrossSalesMargin(vProsesId, vStrTglAwalTahun, vStrTglFrom, vStrTglTo, "DELETE");
		
		if (monthParamTglFrom >= 4){						
			
		
			@SuppressWarnings("unused")
			String vDelete_2 = callStoreProcOrFuncService.callGrossSalesMargin(vProsesId_2, vStrTglAwalTahun, vStrTglStartParam2, vStrTglTo, "DELETE");
		
		}
		
		
	}
 
}

