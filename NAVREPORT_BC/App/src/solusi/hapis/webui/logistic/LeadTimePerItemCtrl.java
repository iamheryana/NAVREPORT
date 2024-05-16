package solusi.hapis.webui.logistic;

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


public class LeadTimePerItemCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Radiogroup rdgJenisPO;	 
	protected Radio rdNONOTP;
	protected Radio rdOTP;
	protected Radio rdALL;
	
	protected Radiogroup rdgLaporan;	 
	protected Radio rdDTL;
	protected Radio rdSUM;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Textbox txtItemNo;
	

	protected Bandbox  cmbProd;
	protected Listbox listProd;
	protected String vProd = "ALL";
	
	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	private SelectQueryService selectQueryService;
	

	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	
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
    	
    	rdNONOTP.setSelected(true); 
    	
    	rdSUM.setSelected(true); 

    	rdPDF.setSelected(true); 
    	
		Bandpopup popup1 = new Bandpopup();
			listProd = new Listbox();
			listProd.setMold("paging");
			listProd.setAutopaging(false);
			listProd.setWidth("250px");
			listProd.addEventListener(Events.ON_SELECT, selectProd());
			listProd.setParent(popup1);
		popup1.setParent(cmbProd);
	        
		listProd.appendItem("ALL", "ALL");
		
		List<Object[]> vResultProd = selectQueryService.QueryProductGroupLeadTime();
		if(CommonUtils.isNotEmpty(vResultProd)){
			for(Object[] aRslt : vResultProd){
				listProd.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
	
	
		cmbProd.setValue(listProd.getItemAtIndex(0).getLabel());
		listProd.setSelectedItem(listProd.getItemAtIndex(0));

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
	
	private EventListener selectProd() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbProd.setValue(listProd.getSelectedItem().getLabel());
				vProd = listProd.getSelectedItem().getValue().toString();
				cmbProd.close();
			}
		};
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
		
		String vItemNo = "ALL";
		if (StringUtils.isNotEmpty(txtItemNo.getValue())) {
			vItemNo = txtItemNo.getValue();
		} 
		
		
		String vJnsLap = "SUM";
		if (StringUtils.isNotEmpty(rdgLaporan.getSelectedItem().getValue())) {
			vJnsLap = rdgLaporan.getSelectedItem().getValue();	
		} 
		
		String vJenisPO = "NON-OTP";
		if (StringUtils.isNotEmpty(rdgJenisPO.getSelectedItem().getValue())) {
			vJenisPO = rdgJenisPO.getSelectedItem().getValue();	
		} 
		
		

		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0303002");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03010_LeadTimePerItem.jasper";
		
		if(vJnsLap.equals("SUM")){
			jasperRpt = "/solusi/hapis/webui/reports/logistic/03010_LeadTimePerItem.jasper";
			
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/logistic/03010_LeadTimePerItem_DetailPO.jasper";
			
		}
		
		param.put("JenisPO",  vJenisPO);
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);
		param.put("Item",  vItemNo.toUpperCase()); 
		param.put("Product",  vProd);
		param.put("Principal",  vPrincipal); 
		
		
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