package solusi.hapis.webui.finance;


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
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.model.Vendor;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.VendorLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class LapPelunasanAPRebateCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowLapPelunasanAPRebate;

	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Textbox txtVendorNo;

	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	protected Radiogroup rdgStatus;	 
	protected Radio rdALL;
	protected Radio rdLNS;
	protected Radio rdBLNS;
	
	
	protected Button btnSearchVendorLOV;
	
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
    	    	

    	txtVendorNo.setValue("ALL");
    	
    	rdALL.setSelected(true);
    	
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
	
	
	public void onClick$btnSearchVendorLOV(Event event) {
		Vendor cust = VendorLOV.show(windowLapPelunasanAPRebate);

		if (cust != null) {
			txtVendorNo.setValue(cust.getCode());
		} else {
			txtVendorNo.setValue(null);
		}

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
		
		

		String vVendorNo = "ALL";
		if (StringUtils.isNotEmpty(txtVendorNo.getValue())) {
			vVendorNo = txtVendorNo.getValue();
		}

		String vStatus = "ALL";
		if (StringUtils.isNotEmpty(rdgStatus.getSelectedItem().getValue())) {
			vStatus = rdgStatus.getSelectedItem().getValue();	
		} 
			
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0207003");
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02033_PelunasanAPRebate.jasper";
		

		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);

		param.put("Vendor",  vVendorNo);
		
		param.put("Principal",  vPrincipal); 
		param.put("Status",  vStatus); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}
