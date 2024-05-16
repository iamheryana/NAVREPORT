package solusi.hapis.webui.sales;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class RevenueByItemCategoryCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowRevenueByItemCategory;

	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;

	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdSR;
	protected Radio rdSR2;
		

	private SelectQueryService selectQueryService;
	

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
    	
    	rdSR.setSelected(true); 
    	
		
		Bandpopup popup2 = new Bandpopup();
			listPrincipal = new Listbox();
			listPrincipal.setMold("paging");
			listPrincipal.setAutopaging(false);
			listPrincipal.setWidth("400px");
			listPrincipal.addEventListener(Events.ON_SELECT, selectPrincipal());
			listPrincipal.setParent(popup2);
		popup2.setParent(cmbPrincipal);
	        
		listPrincipal.appendItem("ALL", "ALL");
		
		List<Object[]> vResultPrincipal = selectQueryService.QueryPrincipal();
		if(CommonUtils.isNotEmpty(vResultPrincipal)){
			for(Object[] aRslt : vResultPrincipal){
				listPrincipal.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbPrincipal.setValue(listPrincipal.getItemAtIndex(0).getLabel());
		listPrincipal.setSelectedItem(listPrincipal.getItemAtIndex(0));
    	
	}
	
	private EventListener selectPrincipal() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbPrincipal.setValue(listPrincipal.getSelectedItem().getLabel());
				vPrincipal = listPrincipal.getSelectedItem().getValue().toString();
				cmbPrincipal.close();
			}
		};
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
		
		
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		String vJenis = "SR";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
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
		cTglUptoCurrent.setTime(vTglUpTo);
		
		int yearTglUptoCurrent = cTglUptoCurrent.get(Calendar.YEAR);
		int monthTglUptoCurrent = cTglUptoCurrent.get(Calendar.MONTH) + 1;
		int dayTglUptoCurrent = cTglUptoCurrent.get(Calendar.DAY_OF_MONTH);
		
		String dR2PrevTglUpto = dayTglUptoCurrent+"/"+monthTglUptoCurrent+"/"+(yearTglUptoCurrent-1);
		SimpleDateFormat dfR2PrevTglUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vPrevTglUpto = dfR2PrevTglUpto.parse(dR2PrevTglUpto);
		
		
//		System.out.println("vTglFrom : "+vTglFrom);
//		System.out.println("vTglUpTo : "+vTglUpTo);
//
//		
//		System.out.println("vPrevTglFrom : "+vPrevTglFrom);
//		System.out.println("vPrevTglUpto : "+vPrevTglUpto);

		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04024_RevenueByItemCategoryBySales.jasper";
		
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507009");
		
		
		
		
		if(vJenis.equals("SR")){
			jasperRpt = "/solusi/hapis/webui/reports/sales/04024_RevenueByItemCategoryBySales.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/sales/04025_RevenueByItemCategoryByBranch.jasper";
		}
		
		BigDecimal vPembagi = new BigDecimal(String.valueOf("1000000"));
		
		PathReport pathreport = new PathReport();
		
		
		param.put("SUBREPORT_DIR",  pathreport.getSubRptSales());
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);

		param.put("PrevYearTglFrom",  vPrevTglFrom);
		param.put("PrevYearTglUpto",  vPrevTglUpto);
		
		param.put("Principal",  vPrincipal); 
		param.put("Pembagi",  vPembagi); 		
		
		
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