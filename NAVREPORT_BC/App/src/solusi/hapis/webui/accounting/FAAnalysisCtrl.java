package solusi.hapis.webui.accounting;

import java.io.Serializable;
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

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class FAAnalysisCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgModel;	 
	protected Radio rdModel1;
	protected Radio rdModel2;
	
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
		
	protected Bandbox  cmbFAClass;
	protected Listbox listFAClass;
	protected String vFAClass = "ALL";
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	

	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		
    	rdPDF.setSelected(true); 
    	    	
    	rdAJ.setSelected(true); 
    	
    	rdModel1.setSelected(true); 
    	
    	
    	Calendar cTglCurr = Calendar.getInstance();		
  		cTglCurr.setTime(new Date());
  		
  		int yearTglCurr = cTglCurr.get(Calendar.YEAR);
  		  		
  		String dRFrom = "1/1/"+yearTglCurr;
  		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
  		Date vTglFrom  = dfRFrom.parse(dRFrom);
  		  		
  		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(new Date());
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
				
  		dbTglFrom.setValue(vTglFrom);
  		dbTglTo.setValue(vTglUpto); 
  		
  		
  		Bandpopup popup1 = new Bandpopup();
			listFAClass = new Listbox();
			listFAClass.setMold("paging");
			listFAClass.setAutopaging(true);
			listFAClass.setWidth("250px");
			listFAClass.addEventListener(Events.ON_SELECT, selectFAClass());
			listFAClass.setParent(popup1);
		popup1.setParent(cmbFAClass);
	        
		listFAClass.appendItem("ALL", "ALL");
		
		List<Object[]> vResult = selectQueryService.QueryFAClass();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listFAClass.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbFAClass.setValue(listFAClass.getItemAtIndex(0).getLabel());
		listFAClass.setSelectedItem(listFAClass.getItemAtIndex(0));
		
		
    	Bandpopup popup2 = new Bandpopup();
			listCabang = new Listbox();
			listCabang.setMold("paging");
			listCabang.setAutopaging(true);
			listCabang.setWidth("250px");
			listCabang.addEventListener(Events.ON_SELECT, selectCabang());
			listCabang.setParent(popup2);
			popup2.setParent(cmbCab);
	        
		listCabang.appendItem("ALL", "ALL");
		
		List<Object[]> vResultCabang = selectQueryService.QueryCabang();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResultCabang){
				listCabang.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbCab.setValue(listCabang.getItemAtIndex(0).getLabel());
		listCabang.setSelectedItem(listCabang.getItemAtIndex(0));
		
		

	}
	
	private EventListener selectFAClass() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbFAClass.setValue(listFAClass.getSelectedItem().getLabel());
				vFAClass = listFAClass.getSelectedItem().getValue().toString();
				cmbFAClass.close();
			}
		};
	}		
	
	private EventListener selectCabang() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCab.setValue(listCabang.getSelectedItem().getLabel());
				vCabang = listCabang.getSelectedItem().getValue().toString();
				cmbCab.close();
			}
		};
	}	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		Calendar cTglCurr = Calendar.getInstance();		
  		cTglCurr.setTime(new Date());
  		
  		int yearTglCurr = cTglCurr.get(Calendar.YEAR);
  		
  		
  		String dRFrom = "1/1/"+yearTglCurr;
  		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
  		Date vTglFrom  = dfRFrom.parse(dRFrom);
  		
  		
  		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(new Date());
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
  		  		
	
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
  
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglUpto = dbTglTo.getValue();
		}
		
		String vModel = "M1";
		if (StringUtils.isNotEmpty(rdgModel.getSelectedItem().getValue())) {
			vModel = rdgModel.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0106002");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01052_FAAnalysis.jasper";
		
		if (vModel.equals("M1") == true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01052_FAAnalysis.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01052_02_FAAnalysis.jasper";
		}
		
		
		param.put("FAClass",  vFAClass);
		param.put("TglFrom",  vTglFrom);	
		param.put("TglUpto",  vTglUpto);	
		param.put("Company",  vCompany); 
		param.put("Cabang",  vCabang); 
		
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