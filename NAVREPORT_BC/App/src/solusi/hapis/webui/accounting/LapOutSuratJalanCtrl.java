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
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapOutSuratJalanCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	protected Datebox dbTglUpto;
	protected Textbox txtKodeCustFrom;
	protected Textbox txtKodeCustTo;    
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Bandbox  cmbCatFrom;
	protected Listbox listCatFrom;
	protected String vCatFrom = ".";
	
	protected Bandbox  cmbCatTo;
	protected Listbox listCatTo;
	protected String vCatTo = "ZZZZZ";
	
	protected Bandbox  cmbCabFrom;
	protected Listbox listCabFrom;
	protected String vCabFrom = ".";
	
	protected Bandbox  cmbCabTo;
	protected Listbox listCabTo;
	protected String vCabTo = "ZZ";
	

	private SelectQueryService selectQueryService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	

	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
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
		
		
    	dbTglTo.setValue((new Date()));   
    	dbTglUpto.setValue((new Date()));   
    	
    	txtKodeCustTo.setValue("ZZZZZZZZZZZZZZZZZZZZ");
    	
    	rdAJ.setSelected(true); 
    	rdPDF.setSelected(true); 
    	
    	//Category From
    	Bandpopup popup1 = new Bandpopup();
			listCatFrom = new Listbox();
			listCatFrom.setMold("paging");
			listCatFrom.setAutopaging(true);
			listCatFrom.setWidth("250px");
			listCatFrom.addEventListener(Events.ON_SELECT, selectCatFrom());
			listCatFrom.setParent(popup1);
		popup1.setParent(cmbCatFrom);
	        
		List<Object[]> vResult = selectQueryService.QueryItemCategory();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listCatFrom.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}		
		
		cmbCatFrom.setValue(listCatFrom.getItemAtIndex(0).getLabel());
		listCatFrom.setSelectedItem(listCatFrom.getItemAtIndex(0));
		vCatFrom = listCatFrom.getItemAtIndex(0).getValue().toString();
		
		//Category Upto
		Bandpopup popup2 = new Bandpopup();
			listCatTo = new Listbox();
			listCatTo.setMold("paging");
			listCatTo.setAutopaging(true);
			listCatTo.setWidth("250px");
			listCatTo.addEventListener(Events.ON_SELECT, selectCatTo());
			listCatTo.setParent(popup2);
		popup2.setParent(cmbCatTo);
	        
		List<Object[]> vResultTo = selectQueryService.QueryItemCategory();
		if(CommonUtils.isNotEmpty(vResultTo)){
			for(Object[] aRslt : vResultTo){
				listCatTo.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}		
		
		cmbCatTo.setValue(listCatTo.getItemAtIndex(vResultTo.size()-1).getLabel());
		listCatTo.setSelectedItem(listCatTo.getItemAtIndex(vResultTo.size()-1));
		vCatTo = listCatTo.getItemAtIndex(vResultTo.size()-1).getValue().toString();
		
		//Cabang From
    	Bandpopup popup3 = new Bandpopup();
			listCabFrom = new Listbox();
			listCabFrom.setMold("paging");
			listCabFrom.setAutopaging(true);
			listCabFrom.setWidth("250px");
			listCabFrom.addEventListener(Events.ON_SELECT, selectCabFrom());
			listCabFrom.setParent(popup3);
		popup3.setParent(cmbCabFrom);
	        
		List<Object[]> vResultCabFrom = selectQueryService.QueryCabang2();
		if(CommonUtils.isNotEmpty(vResultCabFrom)){
			for(Object[] aRslt : vResultCabFrom){
				listCabFrom.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}		
		
		cmbCabFrom.setValue(listCabFrom.getItemAtIndex(0).getLabel());
		listCabFrom.setSelectedItem(listCabFrom.getItemAtIndex(0));
		vCabFrom = listCabFrom.getItemAtIndex(0).getValue().toString();
		
		//Cabang Upto
		Bandpopup popup4 = new Bandpopup();
			listCabTo = new Listbox();
			listCabTo.setMold("paging");
			listCabTo.setAutopaging(true);
			listCabTo.setWidth("250px");
			listCabTo.addEventListener(Events.ON_SELECT, selectCabTo());
			listCabTo.setParent(popup4);
		popup4.setParent(cmbCabTo);
	        
		List<Object[]> vResultCabTo = selectQueryService.QueryCabang2();
		if(CommonUtils.isNotEmpty(vResultCabTo)){
			for(Object[] aRslt : vResultCabTo){
				listCabTo.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}		
		
		cmbCabTo.setValue(listCabTo.getItemAtIndex(vResultCabTo.size()-1).getLabel());
		listCabTo.setSelectedItem(listCabTo.getItemAtIndex(vResultCabTo.size()-1));
		vCabTo= listCabTo.getItemAtIndex(vResultCabTo.size()-1).getValue().toString();
		
	}
	
	private EventListener selectCabFrom() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCabFrom.setValue(listCabFrom.getSelectedItem().getLabel());
				vCabFrom = listCabFrom.getSelectedItem().getValue().toString();
				cmbCabFrom.close();
			}
		};
	}	
	
	private EventListener selectCabTo() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCabTo.setValue(listCabTo.getSelectedItem().getLabel());
				vCabTo = listCabTo.getSelectedItem().getValue().toString();
				cmbCabTo.close();
			}
		};
	}	
	
	private EventListener selectCatFrom() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCatFrom.setValue(listCatFrom.getSelectedItem().getLabel());
				vCatFrom = listCatFrom.getSelectedItem().getValue().toString();
				cmbCatFrom.close();
			}
		};
	}	
	
	private EventListener selectCatTo() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCatTo.setValue(listCatTo.getSelectedItem().getLabel());
				vCatTo = listCatTo.getSelectedItem().getValue().toString();
				cmbCatTo.close();
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		Date vTglFrom = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
		
		Date vTglUpTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
						
		String vKodeCustFrom = ".";
		if (StringUtils.isNotEmpty(txtKodeCustFrom.getValue())) {
			vKodeCustFrom = txtKodeCustFrom.getValue();
		} 
		
		String vKodeCustTo = "ZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtKodeCustTo.getValue())) {
			vKodeCustTo = txtKodeCustTo.getValue();
		} 
		

		
		String vCompany = "AJ";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0103003");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01005_AJOutSuratJalan.jasper";
		
		if(vCompany.equals("AJ")){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01005_AJOutSuratJalan.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01006_SPOutSuratJalan.jasper";
		}
		
    	param.put("From",  vTglFrom); 
		param.put("Upto",  vTglTo);  
		
		param.put("CustUpto",  vKodeCustTo); 
		param.put("CustFrom",  vKodeCustFrom);  
		
		param.put("CategoryUpto",  vCatTo); 
		param.put("CategoryFrom",  vCatFrom);  
		
		
		param.put("CabangUpto",  vCabTo); 
		param.put("CabangFrom",  vCabFrom);  
		

		param.put("NotInvUpto",  vTglUpTo); 

		
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