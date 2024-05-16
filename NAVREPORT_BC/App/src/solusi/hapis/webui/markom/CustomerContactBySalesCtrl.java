package solusi.hapis.webui.markom;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class CustomerContactBySalesCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = ".";
	
	protected Radiogroup rdgJnsCnt;	 
	protected Radio rdALL;
	protected Radio rdCUS;
	protected Radio rdNCUS;
	
	
	private SelectQueryService selectQueryService;

	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		rdALL.setSelected(true);
			
   	    	
    	Bandpopup popup2 = new Bandpopup();
		listSales = new Listbox();
		listSales.setMold("paging");
		listSales.setAutopaging(false);
		listSales.setWidth("400px");
		listSales.addEventListener(Events.ON_SELECT, selectSales());
		listSales.setParent(popup2);
		popup2.setParent(cmbSales);
	        
		listSales.appendItem("<Please Select>", ".");
			
		List<Object[]> vResultSales = selectQueryService.QuerySalesmanForContact();
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
		
		String vJnsCnt = "ALL";
		if (StringUtils.isNotEmpty(rdgJnsCnt.getSelectedItem().getValue())) {
			vJnsCnt = rdgJnsCnt.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0701001");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06008_CustomerContactBySales.jasper";
		
		
		
		param.put("Sales",  vSales);
		param.put("JenisCnt",  vJnsCnt);
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		

		 
		
	}
 
}