package solusi.hapis.webui.accounting;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class DaftarVendorCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
		
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		dbTglUpto.setValue((new Date()));   
    	
    	rdAJ.setSelected(true); 
    	

	}
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		Date vTglFrom = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
			
		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}
		
		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
	
//		String vSaveAs = "PDF";
//		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
//			vSaveAs = rdgSave.getSelectedItem().getValue();	
//		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0104006");			
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01060_DaftarVendor.jasper";
		
		
    	param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglUpto);  
		param.put("Company",  vCompany); 
		
		
		
		//if(vSaveAs.equals("PDF")){
		//	new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		//} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		//}
		
		 
		
	}
 
}

