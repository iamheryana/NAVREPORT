package solusi.hapis.webui.logistic;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.model.Vendor;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.VendorLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class PIRebateHoneywellFormatCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowPIRebateHoneywellFormat;

	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Textbox txtVendorNo;

	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	
	protected Decimalbox decFreightH001;
	protected Decimalbox decDutyH001;	
	protected Decimalbox decFreightH002;
	protected Decimalbox decDutyH002;	
	protected Decimalbox decFreightH003;
	protected Decimalbox decDutyH003;	
	protected Decimalbox decFreightH004;
	protected Decimalbox decDutyH004;
	protected Decimalbox decFreightH013;
	protected Decimalbox decDutyH013;

	
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
    	
    	decFreightH001.setValue(new BigDecimal(3));
    	decDutyH001.setValue(new BigDecimal(0));
    	decFreightH002.setValue(new BigDecimal(4));
    	decDutyH002.setValue(new BigDecimal(0));
    	decFreightH003.setValue(new BigDecimal(5));
    	decDutyH003.setValue(new BigDecimal(5));
    	decFreightH004.setValue(new BigDecimal(3));
    	decDutyH004.setValue(new BigDecimal(0));
    	decFreightH013.setValue(new BigDecimal(3));
    	decDutyH013.setValue(new BigDecimal(5));
    	 	    	
		
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
		Vendor cust = VendorLOV.show(windowPIRebateHoneywellFormat);

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

		
		
		BigDecimal vH001Freight = new BigDecimal(3);
		if(CommonUtils.isNotEmpty(decFreightH001.getValue()) == true){  
			vH001Freight = decFreightH001.getValue();
		}
		
		BigDecimal vH001Duty = new BigDecimal(0);
		if(CommonUtils.isNotEmpty(decDutyH001.getValue()) == true){  
			vH001Duty = decDutyH001.getValue();
		}
	
		BigDecimal vH002Freight = new BigDecimal(4);
		if(CommonUtils.isNotEmpty(decFreightH002.getValue()) == true){  
			vH002Freight = decFreightH002.getValue();
		}
		
		BigDecimal vH002Duty = new BigDecimal(0);
		if(CommonUtils.isNotEmpty(decDutyH002.getValue()) == true){  
			vH002Duty = decDutyH002.getValue();
		}

		BigDecimal vH003Freight = new BigDecimal(5);
		if(CommonUtils.isNotEmpty(decFreightH003.getValue()) == true){  
			vH003Freight = decFreightH003.getValue();
		}
		
		BigDecimal vH003Duty = new BigDecimal(5);
		if(CommonUtils.isNotEmpty(decDutyH003.getValue()) == true){  
			vH003Duty = decDutyH003.getValue();
		}

		BigDecimal vH004Freight = new BigDecimal(3);
		if(CommonUtils.isNotEmpty(decFreightH004.getValue()) == true){  
			vH004Freight = decFreightH004.getValue();
		}
		
		BigDecimal vH004Duty = new BigDecimal(0);
		if(CommonUtils.isNotEmpty(decDutyH004.getValue()) == true){  
			vH004Duty = decDutyH004.getValue();
		}
		
		BigDecimal vH013Freight = new BigDecimal(3);
		if(CommonUtils.isNotEmpty(decFreightH013.getValue()) == true){  
			vH013Freight = decFreightH013.getValue();
		}
		
		BigDecimal vH013Duty = new BigDecimal(5);
		if(CommonUtils.isNotEmpty(decDutyH013.getValue()) == true){  
			vH013Duty = decDutyH013.getValue();
		}
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0302001");
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03007_PIRebateHoneywellFormat.jasper";
		
		param.put("H001Freight",  vH001Freight);
		param.put("H001Duty",  vH001Duty);
		param.put("H002Freight",  vH002Freight);
		param.put("H002Duty",  vH002Duty);
		param.put("H003Freight",  vH003Freight);
		param.put("H003Duty",  vH003Duty);
		param.put("H004Freight",  vH004Freight);
		param.put("H004Duty",  vH004Duty);
		param.put("H013Freight",  vH013Freight);
		param.put("H013Duty",  vH013Duty);
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);

		param.put("Vendor",  vVendorNo);
		
		param.put("Principal",  vPrincipal); 
		
		
	
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		
	}
 
}