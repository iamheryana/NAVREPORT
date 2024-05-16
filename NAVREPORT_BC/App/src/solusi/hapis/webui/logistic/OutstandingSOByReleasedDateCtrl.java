package solusi.hapis.webui.logistic;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Combobox;
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

public class OutstandingSOByReleasedDateCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
 
	
	protected Textbox txtTimeFrom;
	protected Textbox txtTimeUpto;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	
	protected Textbox txtSalesFrom;
	protected Textbox txtSalesUpto;
	
	protected Combobox  cmbJenis;
	
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	private SelectQueryService selectQueryService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		dbTglFrom.setValue((new Date()));  
    	dbTglUpto.setValue((new Date()));   
    	    	

    	txtTimeFrom.setValue("00:00");
    	txtTimeUpto.setValue("24:00");
    	
    	rdALL.setSelected(true); 
    	
    	txtSalesUpto.setValue("ZZZ");

    	cmbJenis.setSelectedIndex(0);

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
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		Date vTglFrom = new Date();   
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}
			
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		
		String vTimeFrom = "00:00";
		if (StringUtils.isNotEmpty(txtTimeFrom.getValue())) {
			vTimeFrom = txtTimeFrom.getValue();
		} 
		
		String vTimeUpto = "24:00";
		if (StringUtils.isNotEmpty(txtTimeUpto.getValue())) {
			vTimeUpto = txtTimeUpto.getValue();
		} 
		
		String vSalesFrom = ".";
		if (StringUtils.isNotEmpty(txtSalesFrom.getValue())) {
			vSalesFrom = txtSalesFrom.getValue();
		} 
		
		String vSalesUpto = "ZZZ";
		if (StringUtils.isNotEmpty(txtSalesUpto.getValue())) {
			vSalesUpto = txtSalesUpto.getValue();
		} 
		
		
		
		String vJenisSO = "ALL";
		if (cmbJenis.getSelectedItem().getValue() != null){
			vJenisSO = (String) cmbJenis.getSelectedItem().getValue();
		}
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vStrTglFrom  = "2022-01-01"; 
		String vStrTglUpto  = "2999-12-31";  
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0305005");
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, vCabang, "CETAK");
	
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03027_SOReleaseByDate.jasper";
		
		param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglUpTo); 
		
		param.put("JamFrom",  vTimeFrom); 
		param.put("JamUpto",  vTimeUpto); 
		
		param.put("ProsesId",  vProsesId); 
		param.put("Company",  vCompany);
		param.put("Cabang",  vCabang);
		param.put("JenisSO",  vJenisSO);
		param.put("SalesFrom",  vSalesFrom);
		param.put("SalesUpto",  vSalesUpto);
		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, "ALL", "DELETE");

		 
		
	}
 
}