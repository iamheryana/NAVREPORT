package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class CclSOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	protected Radiogroup rdgStatusInvoice;	 
	protected Radio rdALLInv;
	protected Radio rdYES;
	protected Radio rdNO;

	protected Textbox txtSales;
	
	protected Textbox txtSOFrom;
	protected Textbox txtSOUpto;
	
	protected Textbox txtBSOFrom;
	protected Textbox txtBSOUpto;
	
	protected Textbox txtCustomer;
	
	
	protected Textbox txtNoPOCust;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	
	protected Bandbox  cmbDCName;
	protected Listbox listDCName;
	protected String vDCName = "X";
	
	
	protected Datebox dbOrderFrom;
	protected Datebox dbOrderUpto;
	
	
	private SelectQueryService selectQueryService;
	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
     	    	
    	rdALL.setSelected(true); 
    	rdNO.setSelected(true); 

    	txtSales.setValue("ALL");
    	
    	txtSOUpto.setValue("ZZZZZZZZZZZZ");    	
    	
    	txtBSOUpto.setValue("ZZZZZZZZZZZZ");
    	
    	txtCustomer.setValue("ALL");
    	
    	txtNoPOCust.setValue("ALL");

    	dbOrderUpto.setValue(new Date());

    	rdPDF.setSelected(true); 
    	
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
			listDCName = new Listbox();
			listDCName.setMold("paging");
			listDCName.setAutopaging(true);
			listDCName.setWidth("250px");
			listDCName.addEventListener(Events.ON_SELECT, selectDCName());
			listDCName.setParent(popup2);
		popup2.setParent(cmbDCName);
	        
		listDCName.appendItem("ALL", "X");
		
		List<Object[]> vResultDCName = selectQueryService.QueryDCName();
		if(CommonUtils.isNotEmpty(vResultDCName)){
			for(Object[] aRslt : vResultDCName){
				listDCName.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbDCName.setValue(listDCName.getItemAtIndex(0).getLabel());
		listDCName.setSelectedItem(listDCName.getItemAtIndex(0));

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
	
	private EventListener selectDCName() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbDCName.setValue(listDCName.getSelectedItem().getLabel());
				vDCName = listDCName.getSelectedItem().getValue().toString();
				cmbDCName.close();
			}
		};
	}			
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
//		String vCabang = "ALL";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCabang = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		
		String vSales = "ALL";
		if (StringUtils.isNotEmpty(txtSales.getValue())) {
			vSales = txtSales.getValue();
		} 
		
		String vSOFrom = ".";
		if (StringUtils.isNotEmpty(txtSOFrom.getValue())) {
			vSOFrom = txtSOFrom.getValue();
		} 
		
		String vSOUpto = "ZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtSOUpto.getValue())) {
			vSOUpto = txtSOUpto.getValue();
		} 
		
		
		String vBSOFrom = ".";
		if (StringUtils.isNotEmpty(txtBSOFrom.getValue())) {
			vBSOFrom = txtBSOFrom.getValue();
		} 
		
		String vBSOUpto = "ZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtBSOUpto.getValue())) {
			vBSOUpto = txtBSOUpto.getValue();
		} 
		
		String vCustomer = "ALL";
		if (StringUtils.isNotEmpty(txtCustomer.getValue())) {
			vCustomer = txtCustomer.getValue();
		} 
		
		String vNoPOCust = "ALL";
		if (StringUtils.isNotEmpty(txtNoPOCust.getValue())) {
			vNoPOCust = txtNoPOCust.getValue();
		} 
		
		
		String dRFrom = "1/1/2000";
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglOrderFrom  = dfRFrom.parse(dRFrom);
		
		if(CommonUtils.isNotEmpty(dbOrderFrom.getValue()) == true){  
			vTglOrderFrom = dbOrderFrom.getValue();
		}   
		
		
		
		Date vTglOrderUpto  = new Date();		
		if(CommonUtils.isNotEmpty(dbOrderUpto.getValue()) == true){  
			vTglOrderUpto = dbOrderUpto.getValue();
		}   
		
		
		
				
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String vSudahInvoice = "ALL";
		if (StringUtils.isNotEmpty(rdgStatusInvoice.getSelectedItem().getValue())) {
			vSudahInvoice = rdgStatusInvoice.getSelectedItem().getValue();	
		} 
		
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507004");
		
		
				
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04016_CclSO.jasper";
		
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptSales());
		
		
		param.put("Cabang",  vCabang); 
		param.put("Company",  vCompany); 
		
		param.put("Sales",  vSales.toUpperCase()); 	
		
		param.put("Customer",  vCustomer.toUpperCase()); 
		
		param.put("NoSOFrom",  vSOFrom.toUpperCase()); 
		param.put("NoSOUpto",  vSOUpto.toUpperCase()); 
		
		param.put("NoBSOFrom",  vBSOFrom.toUpperCase()); 
		param.put("NoBSOUpto",  vBSOUpto.toUpperCase()); 	
		
		param.put("DCName",  vDCName); 		
		
		param.put("NoPOCust",  vNoPOCust.toUpperCase()); 		

		param.put("TglOrderFrom",  vTglOrderFrom); 		
		param.put("TglOrderUpto",  vTglOrderUpto); 		
		
		param.put("SudahInvoice",  vSudahInvoice); 		
		
		
		//new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
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