package solusi.hapis.webui.sales;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class OutSPBCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
		
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Bandbox  cmbLocation;
	protected Listbox listLocation;
	protected String vLocation = "ALL";

	protected Intbox intHari;
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		intHari.setValue(0);
    	    	
    	rdALL.setSelected(true); 
    	rdPDF.setSelected(true); 
    	

    	
  	
    	Bandpopup popup2 = new Bandpopup();
			listLocation = new Listbox();
			listLocation.setMold("paging");
			listLocation.setAutopaging(true);
			listLocation.setWidth("250px");
			listLocation.addEventListener(Events.ON_SELECT, selectLocation());
			listLocation.setParent(popup2);
		popup2.setParent(cmbLocation);
	        
		listLocation.appendItem("ALL", "ALL");
		
		List<Object[]> vResultLocation = selectQueryService.QueryLocationSPB();
		if(CommonUtils.isNotEmpty(vResultLocation)){
			for(Object[] aRslt : vResultLocation){
				listLocation.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbLocation.setValue(listLocation.getItemAtIndex(0).getLabel());
		listLocation.setSelectedItem(listLocation.getItemAtIndex(0));
	

	}
	
	private EventListener selectLocation() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbLocation.setValue(listLocation.getSelectedItem().getLabel());
				vLocation = listLocation.getSelectedItem().getValue().toString();
				cmbLocation.close();
			}
		};
	}	
   	
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		

		int vHari = 0;
		if(CommonUtils.isNotEmpty(intHari.getValue()) == true){
			vHari = intHari.getValue();
		}
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0501004");	
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04006_OutSPB.jasper";


		param.put("LocationCode",  vLocation); 	
		param.put("Company",  vCompany); 
		param.put("hari",  new BigDecimal(vHari)); 

		//new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
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