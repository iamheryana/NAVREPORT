package solusi.hapis.webui.finance;


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

public class LapPelunasanAR_SATINDOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	
	protected Datebox dbTglLunasFrom;  
	protected Datebox dbTglLunasTo;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgGroup;	 
	protected Radio rdSAT;
	protected Radio rdIND;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
		
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	dbTglTo.setValue((new Date()));   
    	dbTglLunasTo.setValue((new Date()));   
    	
    	rdAJ.setSelected(true); 
    	
    	rdSAT.setSelected(true); 

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
		
				
		Date vTglTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
				
		
		Date vTglLunasFrom = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglLunasFrom.getValue()) == true){  
			vTglLunasFrom = dbTglLunasFrom.getValue();
		}   
		
				
		Date vTglLunasTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglLunasTo.getValue()) == true){  
			vTglLunasTo = dbTglLunasTo.getValue();
		}

		
		String vCompany = "AJ";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vCustGroup= "SAT";
		if (StringUtils.isNotEmpty(rdgGroup.getSelectedItem().getValue())) {
			vCustGroup = rdgGroup.getSelectedItem().getValue();	
		} 
		
			
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0207001");

		String jasperRpt = "/solusi/hapis/webui/reports/finance/02028_AJPelunasanAR_SATINDO.jasper";
		
		if(vCompany.equals("AJ")){
			jasperRpt = "/solusi/hapis/webui/reports/finance/02028_AJPelunasanAR_SATINDO.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/finance/02029_SPPelunasanAR_SATINDO.jasper";
		}
		
    	param.put("TglInvFrom",  vTglFrom); 
		param.put("TglInvUpto",  vTglTo);  
		
		param.put("TglLunasFrom",  vTglLunasFrom); 
		param.put("TglLunasUpto",  vTglLunasTo);  
		param.put("CustGroup",  vCustGroup);  
		
		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	 
		
	}
 
}

