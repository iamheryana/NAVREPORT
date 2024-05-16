package solusi.hapis.webui.sales;


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
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class StockAvailabilityCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	

	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
		
	protected Textbox txtItemNo;
	
	protected Bandbox  cmbCat;
	protected Listbox listCat;
	protected String vCat = "ALL";
	
	protected Bandbox  cmbProd;
	protected Listbox listProd;
	protected String vProd = "ALL";
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	rdAJ.setSelected(true); 
    	rdSUM.setSelected(true); 
    	rdPDF.setSelected(true); 
    	
    	Bandpopup popup1 = new Bandpopup();
			listCat = new Listbox();
			listCat.setMold("paging");
			listCat.setAutopaging(true);
			listCat.setWidth("250px");
			listCat.addEventListener(Events.ON_SELECT, selectCat());
			listCat.setParent(popup1);
		popup1.setParent(cmbCat);
	        
		listCat.appendItem("ALL", "ALL");
		
		List<Object[]> vResultCat = selectQueryService.QueryItemCategory();
		if(CommonUtils.isNotEmpty(vResultCat)){
			for(Object[] aRslt : vResultCat){
				listCat.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbCat.setValue(listCat.getItemAtIndex(0).getLabel());
		listCat.setSelectedItem(listCat.getItemAtIndex(0));
		
		
		Bandpopup popup2 = new Bandpopup();
			listProd = new Listbox();
			listProd.setMold("paging");
			listProd.setAutopaging(false);
			listProd.setWidth("250px");
			listProd.addEventListener(Events.ON_SELECT, selectProd());
			listProd.setParent(popup2);
		popup2.setParent(cmbProd);
	        
		listProd.appendItem("ALL", "ALL");
		
		List<Object[]> vResultProd = selectQueryService.QueryProductGroup();
		if(CommonUtils.isNotEmpty(vResultProd)){
			for(Object[] aRslt : vResultProd){
				listProd.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
	
	
		cmbProd.setValue(listProd.getItemAtIndex(0).getLabel());
		listProd.setSelectedItem(listProd.getItemAtIndex(0));


	}
	

	private EventListener selectCat() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCat.setValue(listCat.getSelectedItem().getLabel());
				vCat = listCat.getSelectedItem().getValue().toString();
				cmbCat.close();
			}
		};
	}	
	
	private EventListener selectProd() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbProd.setValue(listProd.getSelectedItem().getLabel());
				vProd = listProd.getSelectedItem().getValue().toString();
				cmbProd.close();
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		
		String vItemNo = "ALL";
		if (StringUtils.isNotEmpty(txtItemNo.getValue())) {
			vItemNo = txtItemNo.getValue();
		} 
		
//		String vCat= "ALL";
//		if (cmbCat.getSelectedItem().getValue() != null){
//			vCat = (String) cmbCat.getSelectedItem().getValue();
//		}
		
//		String vProd= "ALL";
//		if (cmbProd.getSelectedItem().getValue() != null){
//			vProd = (String) cmbProd.getSelectedItem().getValue();
//		}
			
		String vCompany = "AJ";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenis = "SUM";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 

		

//		String jasperRpt = "/solusi/hapis/webui/reports/sales/04012_AJStockAvailability.jasper";
//		
//		if(vCompany.equals("AJ")){
//			if (vJenis.equals("SUM")){
//				jasperRpt = "/solusi/hapis/webui/reports/sales/04012_AJStockAvailability.jasper";
//			} else {
//				jasperRpt = "/solusi/hapis/webui/reports/sales/04012_StockAvailability.jasper";
//			}
//		} else {
//			if (vJenis.equals("SUM")){
//				jasperRpt = "/solusi/hapis/webui/reports/sales/04014_SPStockAvailability.jasper";
//			} else {
//				jasperRpt = "/solusi/hapis/webui/reports/sales/04015_SPStockAvailabilityDetail.jasper";
//			}
//		}
//		
//		if (vJenis.equals("DTL")){
//			PathReport pathReport = new PathReport();
//			param.put("SUBREPORT_DIR",  pathReport.getSubRptSales());
//		}
		
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507003");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04012_StockAvailability.jasper";
		
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptSales());
		
		
		param.put("Item",  vItemNo.toUpperCase()); 
		param.put("ItemCategory",  vCat);
		param.put("ProdGroup",  vProd);
		
		param.put("Company",  vCompany);
		param.put("JenisRpt",  vJenis);
		
		

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
