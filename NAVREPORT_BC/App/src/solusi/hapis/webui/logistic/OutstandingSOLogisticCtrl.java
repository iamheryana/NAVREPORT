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

public class OutstandingSOLogisticCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	protected Radiogroup rdgLaporan;	 
	protected Radio rdDTL;
	protected Radio rdSUM;
	
	protected Textbox txtNoSOFrom;
	protected Textbox txtNoSOUpto;
		
	protected Textbox txtNoItem;
	
	protected Textbox txtSalesFrom;
	protected Textbox txtSalesUpto;
	
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	
	protected Combobox  cmbJenis;
	protected Combobox  cmbJenisPO;
	protected Combobox  cmbStatus;
	protected Combobox  cmbCritical;
	
	protected Bandbox  cmbLocation;
	protected Listbox listLocation;
	protected String vLocation = "ALL";
		
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	protected Radiogroup rdgJnsPending;	 
	protected Radio rdJPALL;
	protected Radio rdJPNPWP;
	protected Radio rdJPSPEX;
	protected Radio rdJPGRXX;
	protected Radio rdJPBTBX;
	protected Radio rdJPINSX;
	

	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	    	
    	rdALL.setSelected(true); 
    	
    	rdSUM.setSelected(true); 
    	
    	rdJPALL.setSelected(true); 
    	
    	Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		int monthTgl = cTgl.get(Calendar.MONTH) + 1;
		
		
		String dRFrom = "1/"+monthTgl+"/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		dbTglUpto.setValue(vTglUpto);  
		
    	txtSalesUpto.setValue("ZZZ");
    	
    	txtNoSOUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
    	
    	cmbJenis.setSelectedIndex(0);

    	cmbJenisPO.setSelectedIndex(0);
    	
    	cmbStatus.setSelectedIndex(2);
    	cmbCritical.setSelectedIndex(0);

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
			listLocation = new Listbox();
			listLocation.setMold("paging");
			listLocation.setAutopaging(true);
			listLocation.setWidth("250px");
			listLocation.addEventListener(Events.ON_SELECT, selectLocation());
			listLocation.setParent(popup2);
		popup2.setParent(cmbLocation);
	        
		listLocation.appendItem("ALL", "ALL");
		
		List<Object[]> vResultLocation = selectQueryService.QueryLocation();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResultLocation){
				listLocation.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbLocation.setValue(listLocation.getItemAtIndex(0).getLabel());
		listLocation.setSelectedItem(listLocation.getItemAtIndex(0));
	

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
	
	private EventListener selectLocation() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbLocation.setValue(listLocation.getSelectedItem().getLabel());
				vLocation = listLocation.getSelectedItem().getValue().toString();
				cmbLocation.close();
			}
		};
	}	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		String vSalesFrom = ".";
		if (StringUtils.isNotEmpty(txtSalesFrom.getValue())) {
			vSalesFrom = txtSalesFrom.getValue();
		} 
		
		String vSalesUpto = "ZZZ";
		if (StringUtils.isNotEmpty(txtSalesUpto.getValue())) {
			vSalesUpto = txtSalesUpto.getValue();
		} 
		
		
		String vNoSOFrom = ".";
		if (StringUtils.isNotEmpty(txtNoSOFrom.getValue())) {
			vNoSOFrom = txtNoSOFrom.getValue();
		} 
		
		String vNoSOUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtNoSOUpto.getValue())) {
			vNoSOUpto = txtNoSOUpto.getValue();
		} 
		
		String vJenis = "ALL";
		if (cmbJenis.getSelectedItem().getValue() != null){
			vJenis = (String) cmbJenis.getSelectedItem().getValue();
		}
		
		String vJenisPO = "ALL";
		if (cmbJenisPO.getSelectedItem().getValue() != null){
			vJenisPO = (String) cmbJenisPO.getSelectedItem().getValue();
		}
		
		
		String vStatus = "ALL";
		if (cmbStatus.getSelectedItem().getValue() != null){
			vStatus = (String) cmbStatus.getSelectedItem().getValue();
		}
		
		
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String vJnsLap = "Y";
		if (StringUtils.isNotEmpty(rdgLaporan.getSelectedItem().getValue())) {
			vJnsLap = rdgLaporan.getSelectedItem().getValue();	
		} 
		
		
		Calendar cTgl= Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		int monthTgl = cTgl.get(Calendar.MONTH) + 1;
		
		
		String dRFrom = "1/"+monthTgl+"/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}   
		
		
		
		String vCritical = "ALL";
		if (cmbCritical.getSelectedItem().getValue() != null){
			vCritical = (String) cmbCritical.getSelectedItem().getValue();
		}
		
		String vJnsPending = "ALL";
		if (StringUtils.isNotEmpty(rdgJnsPending.getSelectedItem().getValue())) {
			vJnsPending = rdgJnsPending.getSelectedItem().getValue();	
		} 
		
		
		String vNoItem = "ALL";
		if (StringUtils.isNotEmpty(txtNoItem.getValue())) {
			vNoItem = txtNoItem.getValue();
		} 
		
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom); 
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0305004");
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, vCabang, "CETAK");
		

		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03031_OutstandingSOLogistic.jasper";
		
		
		param.put("Cabang",  vCabang); 
		param.put("SalesFrom",  vSalesFrom); 
		param.put("SalesUpto",  vSalesUpto); 
		param.put("LocCode",  vLocation); 
		

		param.put("NoSOFrom",  vNoSOFrom); 
		param.put("NoSOUpto",  vNoSOUpto); 
		
		
		param.put("JenisSO",  vJenis); 
		param.put("JenisPO",  vJenisPO); 
		param.put("Status",  vStatus); 
		
		param.put("Company",  vCompany); 

		param.put("Critical",  vCritical); 
		
		param.put("Detail",  vJnsLap); 
		param.put("EstRealFrom",  vTglFrom); 
		param.put("EstRealUpto",  vTglUpto); 
		
		
		param.put("JnsPending",  vJnsPending); 
		
		param.put("NoItem",  vNoItem); 
		
		param.put("ProsesId",  vProsesId); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		 
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, "ALL", "DELETE");

	}
 
}