package solusi.hapis.webui.markom;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ContactBulletinPaketCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	
	protected Radiogroup rdgPaket;	 
	protected Radio rdC;
	protected Radio rdB;
	protected Radio rdALL;
	
	protected Radiogroup rdgFormat;	 
	protected Radio rdNormal;
	protected Radio rdAlfatrex;


	protected Radiogroup rdgJnsCnt;	 
	protected Radio rdALLJNS;
	protected Radio rdCUS;
	protected Radio rdNCUS;
	
	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = "ALL";
	
	private SelectQueryService selectQueryService;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	rdALL.setSelected(true);    
    	rdNormal.setSelected(true);    
    	rdALLJNS.setSelected(true);  
    	
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
			
		List<Object[]> vResultSales = selectQueryService.QuerySalesmanForContact();
		if(CommonUtils.isNotEmpty(vResultSales)){
			for(Object[] aRslt : vResultSales){
				listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		cmbSales.setValue(listSales.getItemAtIndex(0).getLabel());
		listSales.setSelectedItem(listSales.getItemAtIndex(0));
    	
		
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
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		String vPaket = "ALL";
		if (StringUtils.isNotEmpty(rdgPaket.getSelectedItem().getValue())) {
			vPaket = rdgPaket.getSelectedItem().getValue();	
		} 
			
		String vFormat = "Normal";
		if (StringUtils.isNotEmpty(rdgFormat.getSelectedItem().getValue())) {
			vFormat = rdgFormat.getSelectedItem().getValue();	
		} 
			
		String vJnsCnt = "ALL";
		if (StringUtils.isNotEmpty(rdgJnsCnt.getSelectedItem().getValue())) {
			vJnsCnt = rdgJnsCnt.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0701003");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06005_ContactBulletinPaket.jasper";
		if (vFormat.equals("Normal") == true){
			jasperRpt = "/solusi/hapis/webui/reports/markom/06005_ContactBulletinPaket.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/markom/06005_ContactBulletinPaket_AlfaTrax.jasper";
		}
		
		param.put("JenisCnt",  vJnsCnt);
		
		param.put("Sales",  vSales); 
		param.put("Paket",  vPaket); 
		param.put("Cabang",  vCabang);
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}