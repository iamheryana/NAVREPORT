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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class POOTPReceiptCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowPOOTPReceipt;

	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Bandbox  cmbVendor;
	protected Listbox listVendor;
	protected String vVendor = "ALL";


	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Button btnSearchVendorLOV;
	
//	protected Radiogroup rdgAmount;	 
//	protected Radio rdAmt1;
//	protected Radio rdAmt2;
//	protected Radio rdAmt3;
		
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
    	    	

    	
    	rdPDF.setSelected(true); 

//    	rdAmt1.setSelected(true); 
    	

		Bandpopup popup2 = new Bandpopup();
			listVendor = new Listbox();
			listVendor.setMold("paging");
			listVendor.setAutopaging(false);
			listVendor.setWidth("400px");
			listVendor.addEventListener(Events.ON_SELECT, selectVendor());
			listVendor.setParent(popup2);
		popup2.setParent(cmbVendor);
	        
		listVendor.appendItem("ALL", "ALL");
		
		List<Object[]> vResultVendor = selectQueryService.QueryVendorOTP();
		if(CommonUtils.isNotEmpty(vResultVendor)){
			for(Object[] aRslt : vResultVendor){
				listVendor.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbVendor.setValue(listVendor.getItemAtIndex(0).getLabel());
		listVendor.setSelectedItem(listVendor.getItemAtIndex(0));
		
	}
	
	private EventListener selectVendor() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbVendor.setValue(listVendor.getSelectedItem().getLabel());
				vVendor = listVendor.getSelectedItem().getValue().toString();
				cmbVendor.close();
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
		
		

//		String vAmountIn = "1";
//		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
//			vAmountIn = rdgAmount.getSelectedItem().getValue();	
//		} 
		
		BigDecimal vPembagi = new BigDecimal(String.valueOf("1000000"));
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0306005");
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03023_POOTPReceipt.jasper";		
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);

		param.put("VendorNo",  vVendor);
		
		param.put("Pembagi",  vPembagi);
		
		
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