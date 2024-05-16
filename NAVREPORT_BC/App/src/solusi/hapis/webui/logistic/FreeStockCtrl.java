package solusi.hapis.webui.logistic;


import java.io.Serializable;
import java.text.ParseException;
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
import org.zkoss.zul.Row;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class FreeStockCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	
	protected Radiogroup rdgShow;	 
	protected Radio rdYes;
	protected Radio rdNo;
	
	
	protected Radiogroup rdgProduct;	 
	protected Radio rdALLProduct;
	protected Radio rdRFIDOnly;
	
	
	protected Row RowPrincipal;

	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		rdALLProduct.setSelected(true); 

		rdPDF.setSelected(true); 
		rdYes.setSelected(true); 		
		
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
		
	
	public void onCheck$rdgProduct(Event event){
		if(rdALLProduct.isChecked() == true ) {			
			RowPrincipal.setVisible(true);
		} else {
			RowPrincipal.setVisible(false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		String vShow = "Y";
		if (StringUtils.isNotEmpty(rdgShow.getSelectedItem().getValue())) {
			vShow = rdgShow.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0307002");
					
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03024_FreeStock.jasper";
		
		
		String vProduct = "ALL";
		if (StringUtils.isNotEmpty(rdgProduct.getSelectedItem().getValue())) {
			vProduct = rdgProduct.getSelectedItem().getValue();	
		} 
		
		if (vProduct.equals("ALL") == true){
			jasperRpt = "/solusi/hapis/webui/reports/logistic/03024_FreeStock.jasper";
			
			param.put("Principal",  vPrincipal); 
			param.put("ShowAmount",  vShow); 	
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/logistic/03024_02_FreeStock_RFID.jasper";
			
			param.put("ShowAmount",  vShow); 	
		}
		
		

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
