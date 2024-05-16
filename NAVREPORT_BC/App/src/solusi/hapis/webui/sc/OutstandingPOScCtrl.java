package solusi.hapis.webui.sc;

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

public class OutstandingPOScCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	protected Radiogroup rdgJenisPO;	 
	protected Radio rdPON;
	protected Radio rdPOT;
	protected Radio rdPOS;
	protected Radio rdPOTP;
	protected Radio rdPALL;
	
	
	protected Datebox dbTglUpto;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
    	dbTglUpto.setValue((new Date()));   
    	    	
    	rdALL.setSelected(true); 
    	rdPALL.setSelected(true); 
	}
	
	

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		String dRFrom = "1/1/2015";
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		
		
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String vJenisPO = "ALL";
		if (StringUtils.isNotEmpty(rdgJenisPO.getSelectedItem().getValue())) {
			vJenisPO = rdgJenisPO.getSelectedItem().getValue();	
		} 

		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0104005");	
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03026_OutstandingPO.jasper";
		
		
		//String xxx = SecurityContextHolder.getContext().getAuthentication().getName();
		//System.out.println("xxx : "+xxx);
		
		
		param.put("TglUpto",  vTglUpTo); 
		param.put("TglFrom",  vTglFrom); 
		param.put("JenisPO",  vJenisPO); 
		param.put("Company",  vCompany); 
		param.put("POOwn",  "Service Center"); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
//		String vSaveAs = "PDF";
//		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
//			vSaveAs = rdgSave.getSelectedItem().getValue();	
//		} 
//		
//		if(vSaveAs.equals("PDF")){
//			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
//		} else {
//			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
//		}
//		
		 
		
	}
 
}