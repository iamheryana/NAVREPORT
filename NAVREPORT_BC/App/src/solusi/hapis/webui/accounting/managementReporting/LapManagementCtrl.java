package solusi.hapis.webui.accounting.managementReporting;


import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapManagementCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	
	protected Radiogroup rdgAmount;	 
	protected Radio rdAmt1;
	protected Radio rdAmt2;
	protected Radio rdAmt3;
	
	protected Datebox dbPeriode;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	protected String vCabangLabel = "ALL";
	
	
	private SelectQueryService selectQueryService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
	
		
    	rdAmt2.setSelected(true); 
    	
    	dbPeriode.setValue((new Date()));   
    	
    	Bandpopup popup1 = new Bandpopup();
			listCabang = new Listbox();
			listCabang.setMold("paging");
			listCabang.setAutopaging(true);
			listCabang.setWidth("250px");
			listCabang.addEventListener(Events.ON_SELECT, selectCabang());
			listCabang.setParent(popup1);
		popup1.setParent(cmbCab);
	        
		listCabang.appendItem("ALL", "ALL");
		
		List<Object[]> vResult = selectQueryService.QueryCabang2();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listCabang.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbCab.setValue(listCabang.getItemAtIndex(0).getLabel());
		listCabang.setSelectedItem(listCabang.getItemAtIndex(0));
    	
    	
    	
    	
	}

	private EventListener selectCabang() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCab.setValue(listCabang.getSelectedItem().getLabel());
				vCabang = listCabang.getSelectedItem().getValue().toString();
				vCabangLabel = listCabang.getSelectedItem().getLabel().toString();
				cmbCab.close();
			}
		};
	}
	
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0101002-02");
		
		
		Messagebox.show("Sync Sudah Selesai");
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public void onClick$btnCOGS(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
	
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
	
//		String vCab = "10";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCab = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		BigDecimal vPembagi = new BigDecimal(String.valueOf(vAmountIn));
		
		String ProsesID_COGSAnalysis = String.valueOf(System.currentTimeMillis());
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101002-01");
		String vCetak = callStoreProcOrFuncService.callCOGSAnalysis(ProsesID_COGSAnalysis, vCabang, vStrTglFrom, vStrTglUpto, "CETAK");
		
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01001_COGSAnalysis.jasper";

    	param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		param.put("Cabang",  vCabang); 
		param.put("Pembagi",  vPembagi); 
		param.put("ProsesId",  ProsesID_COGSAnalysis); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		
		String vDelete =  callStoreProcOrFuncService.callCOGSAnalysis(ProsesID_COGSAnalysis, vCabang, vStrTglFrom, vStrTglUpto, "DELETE");
		
		

	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void onClick$btnCOGS2(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
	
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
	
//		String vCab = "10";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCab = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
	
		
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		
		BigDecimal vPembagi = new BigDecimal(String.valueOf(vAmountIn));
		String ProsesID_COGSAnalysis = String.valueOf(System.currentTimeMillis());
		
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101002-01");
		
		String vCetak = callStoreProcOrFuncService.callCOGSAnalysis(ProsesID_COGSAnalysis, vCabang, vStrTglFrom, vStrTglUpto, "CETAK");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01001_02_COGSAnalysis.jasper";

    	param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		param.put("Cabang",  vCabang); 
		param.put("Pembagi",  vPembagi); 
		param.put("ProsesId",  ProsesID_COGSAnalysis); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		
		String vDelete =  callStoreProcOrFuncService.callCOGSAnalysis(ProsesID_COGSAnalysis, vCabang, vStrTglFrom, vStrTglUpto, "DELETE");
		

		
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void onClick$btnCOGS3(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
	
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
	
//		String vCab = "10";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCab = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		BigDecimal vPembagi = new BigDecimal(String.valueOf(vAmountIn));
		
		String ProsesID_COGSAnalysis = String.valueOf(System.currentTimeMillis());
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101002-01");
		String vCetak = callStoreProcOrFuncService.callCOGSAnalysis(ProsesID_COGSAnalysis, vCabang, vStrTglFrom, vStrTglUpto, "CETAK");
		
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01001_03_COGSAnalysis.jasper";

    	param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		param.put("Cabang",  vCabang); 
		param.put("Pembagi",  vPembagi); 
		param.put("ProsesId",  ProsesID_COGSAnalysis); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		
		String vDelete =  callStoreProcOrFuncService.callCOGSAnalysis(ProsesID_COGSAnalysis, vCabang, vStrTglFrom, vStrTglUpto, "DELETE");
		
		

	}	
	
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void onClick$btnSalesCOGS(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
			
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
	
//		String vCab = "10";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCab = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		String ProsesID_SalesCOGSCorr = String.valueOf(System.currentTimeMillis());
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101002-01");
		String vCetak = callStoreProcOrFuncService.callSalesCOGSCorrection(ProsesID_SalesCOGSCorr, vCabang , vStrTglFrom, vStrTglUpto, "CETAK");

		
		BigDecimal vPembagi = new BigDecimal(String.valueOf(vAmountIn));
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01002_SalesVSCOGSCorrection.jasper";

    	param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		param.put("Cabang",  vCabang); 
		param.put("Pembagi",  vPembagi); 
		param.put("ProsesId",  ProsesID_SalesCOGSCorr); 

		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		 
		String vDelete = callStoreProcOrFuncService.callSalesCOGSCorrection(ProsesID_SalesCOGSCorr, vCabang , vStrTglFrom, vStrTglUpto, "DELETE");

	}


	@SuppressWarnings({ "unchecked", "unused" })
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException, IOException {
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
		Calendar cTglUptoPrevMonth = Calendar.getInstance(); 
		cTglUptoPrevMonth.setTime(vTglUpto);
		cTglUptoPrevMonth.add(Calendar.MONTH, -1);
		Date vTglUptoPrevMonth = cTglUptoPrevMonth.getTime();
		
		Calendar cTglUptoPrevYear = Calendar.getInstance(); 
		cTglUptoPrevYear.setTime(vTglUpto);
		cTglUptoPrevYear.add(Calendar.YEAR, -1);
		Date vTglUptoPrevYear = cTglUptoPrevYear.getTime();
		
		
			
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		BigDecimal vPembagi_report = new BigDecimal(String.valueOf(vAmountIn));
		   
		
		String ProsesID_PNLProcess = String.valueOf(System.currentTimeMillis());
		
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101002-01");
		String vCetakPNL = callStoreProcOrFuncService.callReportPNLManagement(ProsesID_PNLProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, "CETAK");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010101_LapPNLManagement.jasper";
		 
	
	    
		param.put("ProsesId",  ProsesID_PNLProcess);		
	    param.put("TglInvUpto",  vTglUpto); 
	    param.put("TglInvUptoPrevMonth", vTglUptoPrevMonth );  
	    param.put("TglInvUptoPrevYear", vTglUptoPrevYear );  	
	    param.put("Cabang",  vCabangLabel); 
	    param.put("Pembagi",  vPembagi_report);		
	
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		String vDeletePNL =  callStoreProcOrFuncService.callReportPNLManagement(ProsesID_PNLProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, "DELETE");
		


	}
	

	@SuppressWarnings({ "unused", "unchecked" })
	public void onClick$btnOKNeraca(Event event) throws InterruptedException, ParseException, IOException {

		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
				
			
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		BigDecimal vPembagi_report = new BigDecimal(String.valueOf(vAmountIn));
		
		
		String ProsesID_NERACAProcess = String.valueOf(System.currentTimeMillis());
		String vUserID = SecurityContextHolder.getContext().getAuthentication().getName();
		
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101002-02");
		
		String vCetak = callStoreProcOrFuncService.callReportNERACAManagement(ProsesID_NERACAProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, vUserID, "CETAK");
		
				
			
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010102_LapNeracaManagement.jasper";
		
		param.put("ProsesId",  ProsesID_NERACAProcess);		
		
		param.put("TglInvFrom",  vTglFrom); 
		param.put("TglInvUpto",  vTglUpto); 
		param.put("Cabang",  vCabangLabel); 
		param.put("Pembagi",  vPembagi_report);		
	
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		String vDelete =  callStoreProcOrFuncService.callReportNERACAManagement(ProsesID_NERACAProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, vUserID, "DELETE");
		
		

	}
	
	

	@SuppressWarnings("unchecked")
	public void onClick$btnPRODUCT(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
	
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010104_SalesByProduct4PilarFromBI.jasper";

    	param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 


	}
	
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void onClick$btnALL(Event event) throws InterruptedException, ParseException, IOException {
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
		Calendar cTglUptoPrevMonth = Calendar.getInstance(); 
		cTglUptoPrevMonth.setTime(vTglUpto);
		cTglUptoPrevMonth.add(Calendar.MONTH, -1);
		Date vTglUptoPrevMonth = cTglUptoPrevMonth.getTime();
		
		Calendar cTglUptoPrevYear = Calendar.getInstance(); 
		cTglUptoPrevYear.setTime(vTglUpto);
		cTglUptoPrevYear.add(Calendar.YEAR, -1);
		Date vTglUptoPrevYear = cTglUptoPrevYear.getTime();
		
		
			
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		BigDecimal vPembagi_report = new BigDecimal(String.valueOf(vAmountIn));
		List<Map> params = new ArrayList<Map>();
		List<String> jasperRpts = new ArrayList<String>();
		String [] vSheetName = new String[10]; 
		String [] vProsesID_PNLCabang = new String[5];
		
		String ProsesID_COGSAnalysis = String.valueOf(System.currentTimeMillis());
		
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101002-02");
		
		
		String vCetakCOGSAnalysis = callStoreProcOrFuncService.callCOGSAnalysis(ProsesID_COGSAnalysis, vCabang, vStrTglFrom, vStrTglUpto, "CETAK");
		
		jasperRpts.add("/solusi/hapis/webui/reports/accounting/01001_COGSAnalysis.jasper");
		 
	    Map paramCOGS_1 = new HashMap();	
	

	    paramCOGS_1.put("PeriodeFrom",  vTglFrom); 
	    paramCOGS_1.put("PeriodeUpto",  vTglUpto);  		
	    paramCOGS_1.put("Cabang",  vCabang); 
	    paramCOGS_1.put("Pembagi",  vPembagi_report); 
	    paramCOGS_1.put("ProsesId", ProsesID_COGSAnalysis);

	    params.add(paramCOGS_1);
	    vSheetName[0] = "COGS ANALYSIS";
	    
	    jasperRpts.add("/solusi/hapis/webui/reports/accounting/01001_03_COGSAnalysis.jasper");
		 
	    Map paramCOGS_2 = new HashMap();	
	

	    paramCOGS_2.put("PeriodeFrom",  vTglFrom); 
	    paramCOGS_2.put("PeriodeUpto",  vTglUpto);  		
	    paramCOGS_2.put("Cabang",  vCabang); 
	    paramCOGS_2.put("Pembagi",  vPembagi_report); 
	    paramCOGS_2.put("ProsesId", ProsesID_COGSAnalysis);

	    params.add(paramCOGS_2);
	    vSheetName[1] = "COGS ANALYSIS - PRD GRP";
	    
		
	    jasperRpts.add("/solusi/hapis/webui/reports/accounting/01001_02_COGSAnalysis.jasper");
		 
	    Map paramCOGS_3 = new HashMap();	
	    
	    paramCOGS_3.put("PeriodeFrom",  vTglFrom); 
	    paramCOGS_3.put("PeriodeUpto",  vTglUpto);  		
		paramCOGS_3.put("Cabang",  vCabang); 
		paramCOGS_3.put("Pembagi",  vPembagi_report); 
		paramCOGS_3.put("ProsesId", ProsesID_COGSAnalysis);

		params.add(paramCOGS_3);
	    vSheetName[2] = "4 PILLARS ANALYSIS";
	    
	    
	    jasperRpts.add("/solusi/hapis/webui/reports/accounting/managementReporting/010104_SalesByProduct4PilarFromBI.jasper");
		 
	    Map paramSALES_Prd = new HashMap();	

	    paramSALES_Prd.put("PeriodeFrom",  vTglFrom); 
	    paramSALES_Prd.put("PeriodeUpto",  vTglUpto);  		


		params.add(paramSALES_Prd);
	    vSheetName[3] = "PRODUCT BY 4 PILLARS";
	    
	    
		
		String ProsesID_PNLProcess = String.valueOf(System.currentTimeMillis());
		String vCetakPNL = callStoreProcOrFuncService.callReportPNLManagement(ProsesID_PNLProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, "CETAK");
		
		jasperRpts.add("/solusi/hapis/webui/reports/accounting/managementReporting/010101_LapPNLManagement.jasper");
		 
	    Map paramPNL = new HashMap();		
	    
	    paramPNL.put("ProsesId",  ProsesID_PNLProcess);		
	    paramPNL.put("TglInvUpto",  vTglUpto); 
	    paramPNL.put("TglInvUptoPrevMonth", vTglUptoPrevMonth );  
	    paramPNL.put("TglInvUptoPrevYear", vTglUptoPrevYear );  	
	    paramPNL.put("Cabang",  vCabangLabel); 
	    paramPNL.put("Pembagi",  vPembagi_report);		
	    
	    params.add(paramPNL);
	    vSheetName[4] = "PNL";
	    
	    int vIterPNLForCab = 5;
//	    int vIterProsesIDPNLForCab = 0;
//	    List<Object[]> vIterPNLCab = selectQueryService.QueryCabang2();
//		if(CommonUtils.isNotEmpty(vIterPNLCab)){
//			
//			for(Object[] anIter : vIterPNLCab){
//				String vCabCode = anIter[1].toString();
//				String vCabName = anIter[0].toString();
//				if (vCabCode.equals("10")==false){
//					vProsesID_PNLCabang[vIterProsesIDPNLForCab] = String.valueOf(System.currentTimeMillis());
//					String vCetakPNLCab = callStoreProcOrFuncService.callReportPNLManagement(vProsesID_PNLCabang[vIterProsesIDPNLForCab], vStrTglFrom, vStrTglUpto, vCabCode, vPembagi_report, "CETAK");
//					
//					jasperRpts.add("/solusi/hapis/webui/reports/accounting/managementReporting/010101_LapPNLManagement.jasper");
//					 
//				    Map paramPNLCab = new HashMap();		
//				    
//				    paramPNLCab.put("ProsesId",  vProsesID_PNLCabang[vIterProsesIDPNLForCab]);		
//				    paramPNLCab.put("TglInvUpto",  vTglUpto); 
//				    paramPNLCab.put("TglInvUptoPrevMonth", vTglUptoPrevMonth );  
//				    paramPNLCab.put("TglInvUptoPrevYear", vTglUptoPrevYear );  	
//				    paramPNLCab.put("Cabang",  vCabName); 
//				    paramPNLCab.put("Pembagi",  vPembagi_report);		
//				    
//				    params.add(paramPNLCab);
//				    vSheetName[vIterPNLForCab] = "PNL-"+vCabName;
//				    
//				    vIterPNLForCab = vIterPNLForCab + 1;
//				    vIterProsesIDPNLForCab = vIterProsesIDPNLForCab + 1;
//				}
//			    
//				
//				
//			}
//		}
		
	
		String ProsesID_NERACAProcess = String.valueOf(System.currentTimeMillis());
		String vUserID = SecurityContextHolder.getContext().getAuthentication().getName();

		String vCetak = callStoreProcOrFuncService.callReportNERACAManagement(ProsesID_NERACAProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, vUserID, "CETAK");
		jasperRpts.add("/solusi/hapis/webui/reports/accounting/managementReporting/010102_LapNeracaManagement.jasper");
		
		Map paramNeraca = new HashMap();		
		
		paramNeraca.put("ProsesId",  ProsesID_NERACAProcess);		
		paramNeraca.put("TglInvFrom",  vTglFrom); 
		paramNeraca.put("TglInvUpto",  vTglUpto); 
		paramNeraca.put("Cabang",  vCabangLabel); 
		paramNeraca.put("Pembagi",  vPembagi_report);		
		
		params.add(paramNeraca);
		vSheetName[vIterPNLForCab] = "NERACA";
		
		
		
		new JReportGeneratorWindow(params, jasperRpts, vSheetName); 
		String vDelete = callStoreProcOrFuncService.callCOGSAnalysis(ProsesID_COGSAnalysis, vCabang, vStrTglFrom, vStrTglUpto, "DELETE");
		String vDeletePNL =  callStoreProcOrFuncService.callReportPNLManagement(ProsesID_PNLProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, "DELETE");
		String vDeleteNeraca =  callStoreProcOrFuncService.callReportNERACAManagement(ProsesID_NERACAProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, vUserID, "DELETE");

		
//		int vIterDeletePNLForCab = 0;
//	    List<Object[]> vIterDeletePNLCab = selectQueryService.QueryCabang2();
//	    
//		for(int vXX = 0 ; vXX <= vIterProsesIDPNLForCab; vXX++){
//			String vDeletePNLCab = callStoreProcOrFuncService.callReportPNLManagement(vProsesID_PNLCabang[vXX], vStrTglFrom, vStrTglUpto, "ALL", vPembagi_report, "DELETE");
//			
//		}
				
	}
	

	
}