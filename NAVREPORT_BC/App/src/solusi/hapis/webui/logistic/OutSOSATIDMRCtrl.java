package solusi.hapis.webui.logistic;



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

public class OutSOSATIDMRCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	

	protected Radiogroup rdgCompany;	 
	protected Radio rdALL;
	protected Radio rdSP;
	protected Radio rdAJ;
	
	
	protected Radiogroup rdgJnsTrans;	 
	protected Radio rdJTALL;
	protected Radio rdJTSAT;
	protected Radio rdJTINDOMARCO;
	
	
	protected Radiogroup rdgJnsPending;	 
	protected Radio rdJPALL;
	protected Radio rdJPNPWP;
	protected Radio rdJPSPEX;

	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
		
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		dbTglUpto.setValue((new Date())); 
    	    	
    	   	
		rdALL.setSelected(true); 
		rdJTALL.setSelected(true); 
		rdJPALL.setSelected(true); 
    	
    	
    	
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		

		
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
		

		String vJnsTrans = "ALL";
		if (StringUtils.isNotEmpty(rdgJnsTrans.getSelectedItem().getValue())) {
			vJnsTrans = rdgJnsTrans.getSelectedItem().getValue();	
		} 
		
		
		String vJnsPending = "ALL";
		if (StringUtils.isNotEmpty(rdgJnsPending.getSelectedItem().getValue())) {
			vJnsPending = rdgJnsPending.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0305007");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03040_OutSO-SAT-IDMR.jasper";
		


		
		
		param.put("TglOrderFrom",  vTglFrom); 
		param.put("TglOrderUpto",  vTglUpto); 
		
		param.put("Company",  vCompany); 
		param.put("JnsTrans",  vJnsTrans); 
		param.put("JnsPending",  vJnsPending); 
		
		
			
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}