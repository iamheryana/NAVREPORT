package solusi.hapis.webui.markom;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ContactProspectCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Combobox  cmbChannel;
	protected Combobox  cmbCampaign;
	protected Combobox  cmbTipeProspek;
	
	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = "ALL";
	
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
	 	
	 	
		cmbChannel.setSelectedIndex(0);
		cmbCampaign.setSelectedIndex(0);
		cmbTipeProspek.setSelectedIndex(0);
    	    	

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
		
		String vChannel = "ALL";
		if (cmbChannel.getSelectedItem().getValue() != null){
			vChannel = (String) cmbChannel.getSelectedItem().getValue();
		}
		
		String vCampaign = "ALL";
		if (cmbCampaign.getSelectedItem().getValue() != null){
			vCampaign = (String) cmbCampaign.getSelectedItem().getValue();
		}
		
		String vTipeProspek = "ALL";
		if (cmbTipeProspek.getSelectedItem().getValue() != null){
			vTipeProspek = (String) cmbTipeProspek.getSelectedItem().getValue();
		}
		
		

		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0701004");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06002_ContactProspect.jasper";
		
		
		param.put("Channel",  vChannel);
		param.put("Campaign",  vCampaign); 		
		param.put("TipeProspect",  vTipeProspek);
		
		param.put("tglFrom",  vTglFrom); 
		param.put("tglUpto",  vTglUpTo); 
		
		param.put("Sales",  vSales); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}