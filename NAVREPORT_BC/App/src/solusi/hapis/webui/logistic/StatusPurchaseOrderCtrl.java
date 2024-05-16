package solusi.hapis.webui.logistic;


import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class StatusPurchaseOrderCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
		
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	protected Radiogroup rdgStatusPO;	 
	protected Radio rdStatus1;
	protected Radio rdStatus2;
		
	protected Datebox dbTglUpto;
	
	protected Combobox  cmbJenisPO;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
    	dbTglUpto.setValue((new Date()));   
    	    	
    	rdALL.setSelected(true); 
    	
    	rdStatus1.setSelected(true); 
    	
    	cmbJenisPO.setSelectedIndex(0);
	}
	
	

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenisPO = "ALL";
		if (cmbJenisPO.getSelectedItem().getValue() != null){
			vJenisPO = (String) cmbJenisPO.getSelectedItem().getValue();
		}
		
		String vStatusPO = "Not Yet Received";
		if (StringUtils.isNotEmpty(rdgStatusPO.getSelectedItem().getValue())) {
			vStatusPO = rdgStatusPO.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0306003");	
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03038_StatusPurchaseOrder.jasper";
		

		param.put("TglUpto", vTglUpTo); 
		param.put("JenisPO", vJenisPO); 
		param.put("Company", vCompany); 
		param.put("StatusPO", vStatusPO); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	
		 
		
	}
 
}