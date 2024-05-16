package solusi.hapis.webui.sales;

import java.io.Serializable;
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
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SearchSOByItemPrincipalCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	
	protected Textbox txtItemNo;
	
	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	protected Datebox dbTglInvFrom;
	protected Datebox dbTglInvUpto;
	
	protected Textbox txtCustNo;
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		
		dbTglInvFrom.setValue((new Date()));   
		
		dbTglInvUpto.setValue((new Date()));   
		
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
	
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		Date vTglInvFrom = new Date();   
		if(CommonUtils.isNotEmpty(dbTglInvFrom.getValue()) == true){  
			vTglInvFrom = dbTglInvFrom.getValue();
		}
		
		
		Date vTglInvUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglInvUpto.getValue()) == true){  
			vTglInvUpto = dbTglInvUpto.getValue();
		}
		
		String vCustNo = "ALL";
		if (StringUtils.isNotEmpty(txtCustNo.getValue())) {
			vCustNo = txtCustNo.getValue();
		}
		
		String vItemNo = "ALL";
		if (StringUtils.isNotEmpty(txtItemNo.getValue())) {
			vItemNo = txtItemNo.getValue();
		} 
		

			
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507011");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04029_SearchSOByItemPrincipal.jasper";
		
		param.put("PN",  vItemNo.toUpperCase());
		param.put("Principal",  vPrincipal); 
		
		param.put("TglInvFrom",  vTglInvFrom); 
		param.put("TglInvUpto",  vTglInvUpto); 
		param.put("CustNo",  vCustNo); 
		
		

		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		
	}
 
}
