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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ForecastSalesByPrincipalCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Intbox  intMargin;
	protected Combobox  cmbItemCat;
	protected Combobox  cmbProjectCat;
//	protected Combobox  cmbPotensialReal;

	protected Radiogroup rdgAmount;	 
	protected Radio rdIDR;
	protected Radio rdUSD;
	
	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
    	    	
		intMargin.setValue(20);
    	cmbItemCat.setSelectedIndex(0);
    	cmbProjectCat.setSelectedIndex(0);
//    	cmbPotensialReal.setSelectedIndex(0);
    	
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
    	
		rdUSD.setSelected(true);    
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
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		
		int vMargin = 20;
		if(CommonUtils.isNotEmpty(intMargin.getValue())){
			vMargin = intMargin.getValue();
		}
		
		
		String vItemCat = "ALL";
		if (cmbItemCat.getSelectedItem().getValue() != null){
			vItemCat = (String) cmbItemCat.getSelectedItem().getValue();
		}
		
		String vProjectCat = "ALL";
		if (cmbProjectCat.getSelectedItem().getValue() != null){
			vProjectCat = (String) cmbProjectCat.getSelectedItem().getValue();
		}
		
		String vPotensialReal = "ALL";
//		if (cmbPotensialReal.getSelectedItem().getValue() != null){
//			vPotensialReal = (String) cmbPotensialReal.getSelectedItem().getValue();
//		}
		
		String vAmount = "USD";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmount = rdgAmount.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0503002");
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04038_ForecastPrincipal.jasper";
		
		
		param.put("Margin",  vMargin);
		param.put("ItemCategory",  vItemCat);
		param.put("Potensi",  vPotensialReal); 
		param.put("Size",  vProjectCat); 
		param.put("Principal",  vPrincipal); 
		param.put("Curr",  vAmount); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}