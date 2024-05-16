package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Listbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapCustomerActivityBySalesCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = ".";
	
	private SelectQueryService selectQueryService;

	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
			
   	    	
    	Bandpopup popup2 = new Bandpopup();
		listSales = new Listbox();
		listSales.setMold("paging");
		listSales.setAutopaging(false);
		listSales.setWidth("400px");
		listSales.addEventListener(Events.ON_SELECT, selectSales());
		listSales.setParent(popup2);
		popup2.setParent(cmbSales);
	        
		listSales.appendItem("<Please Select>", ".");
			
		List<Object[]> vResultSales = selectQueryService.QuerySalesmanCustomer();
		int vSalesIndex = 0;
		if(CommonUtils.isNotEmpty(vResultSales)){
			int vIndex = 1;
			
			String vUserName = SecurityContextHolder.getContext().getAuthentication().getName();
			for(Object[] aRslt : vResultSales){
				listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
				
				
				if((aRslt[1].toString()).equals(vUserName) == true){
					vSalesIndex = vIndex;
					vSales = vUserName;
				}
				
				vIndex = vIndex + 1;
				
			}
		}
		
				
		
		cmbSales.setValue(listSales.getItemAtIndex(vSalesIndex).getLabel());
		listSales.setSelectedItem(listSales.getItemAtIndex(vSalesIndex));
    	
		
	}
	
	private EventListener selectSales() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbSales.setValue(listSales.getSelectedItem().getLabel());
				vSales = listSales.getSelectedItem().getValue().toString();
				cmbSales.close();
			}
		};
	}

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
	
		

		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507014");
		
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04058_LapCustomerActivityBySales.jasper";
		
		
		
		param.put("Sales",  vSales);
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		

		 
		
	}
 
}