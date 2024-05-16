package solusi.hapis.webui.markom;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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

public class ContactBulletinVersi2Ctrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = "ALL";
	
	private SelectQueryService selectQueryService;
	
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	
	protected Radiogroup rdgJnsCnt;	 
	protected Radio rdALL;
	protected Radio rdCUS;
	protected Radio rdNCUS;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		dbTglUpto.setValue(new Date());
		
		
		rdALL.setSelected(true);
		
    	Bandpopup popup1 = new Bandpopup();
			listCabang = new Listbox();
			listCabang.setMold("paging");
			listCabang.setAutopaging(true);
			listCabang.setWidth("250px");
			listCabang.addEventListener(Events.ON_SELECT, selectCabang());
			listCabang.setParent(popup1);
		popup1.setParent(cmbCab);
	        
		listCabang.appendItem("ALL", "ALL");
		
		List<Object[]> vResult = selectQueryService.QueryCabang();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listCabang.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbCab.setValue(listCabang.getItemAtIndex(0).getLabel());
		listCabang.setSelectedItem(listCabang.getItemAtIndex(0));
		
    	    	
    	Bandpopup popup2 = new Bandpopup();
		listSales = new Listbox();
		listSales.setMold("paging");
		listSales.setAutopaging(false);
		listSales.setWidth("400px");
		listSales.addEventListener(Events.ON_SELECT, selectSales());
		listSales.setParent(popup2);
		popup2.setParent(cmbSales);
	        
		listSales.appendItem("ALL", "ALL");
		listSales.appendItem("<SALES ACTIVE>", "ACTIVE");
		listSales.appendItem("<KOSONG>", "");
			
		List<Object[]> vResultSales = selectQueryService.QuerySalesmanForContact();
		int vSalesIndex = 0;
		if(CommonUtils.isNotEmpty(vResultSales)){
			int vIndex = 1;
			
			String vUserName = SecurityContextHolder.getContext().getAuthentication().getName();
			for(Object[] aRslt : vResultSales){
				listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
				
				
				if((aRslt[1].toString()).equals(vUserName) == true){
					vSalesIndex = vIndex;
					vSalesIndex = vSalesIndex + 2;
					vSales = vUserName;
				}
				
				vIndex = vIndex + 1;
				
			}
		}
		
				
		
		cmbSales.setValue(listSales.getItemAtIndex(vSalesIndex).getLabel());
		listSales.setSelectedItem(listSales.getItemAtIndex(vSalesIndex));
    	
		
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
	
	private EventListener selectSales() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbSales.setValue(listSales.getSelectedItem().getLabel());
				vSales = listSales.getSelectedItem().getValue().toString();
				cmbSales.close();
			}
		};
	}

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {

		String dRFrom = "1/1/1900";
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);		
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		
		Date vTglUpto  = new Date();
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}   
		
		
		
		String vJnsCnt = "ALL";
		if (StringUtils.isNotEmpty(rdgJnsCnt.getSelectedItem().getValue())) {
			vJnsCnt = rdgJnsCnt.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0701002");
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06007_ContactBulletinConsist.jasper";
		
		
		param.put("JenisCnt",  vJnsCnt);
		param.put("Sales",  vSales);
		param.put("Cabang",  vCabang);
		
		
		param.put("TglCreateFrom",  vTglFrom);
		param.put("TglCreateUpto",  vTglUpto);
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		

		 
		
	}
 
}