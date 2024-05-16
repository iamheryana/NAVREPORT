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


public class PrincipalInfoRebateCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowPrincipalInfoRebate;

	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Textbox txtVendorNo;

	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdM1;
	protected Radio rdM2;
	
	protected Radiogroup rdgProduct;	 
	protected Radio rdALL;
	protected Radio rdMN;
	protected Radio rdNMC;
		
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

//	protected Radiogroup rdgJenis;	 
//	protected Radio rdNR;
//	protected Radio rdKR;
		
	
		
	protected Button btnSearchVendorLOV;
	
	private SelectQueryService selectQueryService;
	
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
    	
    	rdPDF.setSelected(true); 
    	rdM1.setSelected(true); 
//    	rdNR.setSelected(true); 

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
		Vendor cust = VendorLOV.show(windowPrincipalInfoRebate);

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

		
//		String vJenis = "NR";
//		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
//			vJenis = rdgJenis.getSelectedItem().getValue();	
//		} 
//		
		
		String vJenisLap = "M1";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJenisLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		String vProduct = "ALL";
		if (StringUtils.isNotEmpty(rdgProduct.getSelectedItem().getValue())) {
			vProduct = rdgProduct.getSelectedItem().getValue();	
		} 
		

		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0302001");
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03008_PrincipalInfoRebate.jasper";
		
		if(vJenisLap.equals("M1")){
			jasperRpt = "/solusi/hapis/webui/reports/logistic/03008_PrincipalInfoRebate.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/logistic/03008_PrincipalInfoRebate_model2.jasper";
		}
	
		
		
//		if(vJenis.equals("NR")){
//			jasperRpt = "/solusi/hapis/webui/reports/logistic/PrincipalInfoRebate.jasper";
//		} else {
//			jasperRpt = "/solusi/hapis/webui/reports/logistic/PrincipalInfoRebateV2.jasper";
//		}
		
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);

		param.put("Vendor",  vVendorNo);
		
		param.put("Principal",  vPrincipal); 
		param.put("Product",  vProduct); 
		
		
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