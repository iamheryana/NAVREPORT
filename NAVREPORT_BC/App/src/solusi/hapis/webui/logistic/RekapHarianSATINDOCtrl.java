package solusi.hapis.webui.logistic;


import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class RekapHarianSATINDOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	
	protected Combobox  cmbJenis;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  


		dbTglFrom.setValue(new Date());  
		
		dbTglUpto.setValue(new Date());  
	
    	cmbJenis.setSelectedIndex(0);

 
	

	}
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {		
		String vJenis = "ALL";
		if (cmbJenis.getSelectedItem().getValue() != null){
			vJenis = (String) cmbJenis.getSelectedItem().getValue();
		}
		
		
		
		
		Date vTglFrom  = new Date();		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   

		Date vTglUpto  =  new Date();				
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}   		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0305008");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03033_RekapHarianSATINDO.jasper";
		
		
		param.put("JenisSO",  vJenis); 
		param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglUpto); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		 
		
	}
 
}