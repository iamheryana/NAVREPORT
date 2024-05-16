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
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ChangeLogPoBpoCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	protected Datebox dbTglUbahFrom;
	protected Datebox dbTglUbahUpto;
	
	protected Textbox txtNoPO;
	protected Textbox txtUserID; 
	protected Textbox txtNoSupp;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		

    	
    	dbTglUpto.setValue((new Date()));   
    	dbTglUbahUpto.setValue((new Date()));   
    	

    	
    	rdALL.setSelected(true); 
    	
    	
		
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
		
		Date vTglUbahFrom = vTanggal;  
		if(CommonUtils.isNotEmpty(dbTglUbahFrom.getValue()) == true){  
			vTglUbahFrom = dbTglUbahFrom.getValue();
		}   
		
		
		
		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}
		
		Date vTglUbahUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUbahUpto.getValue()) == true){  
			vTglUbahUpto = dbTglUbahUpto.getValue();
		}
		
						
		String vNoPO = "ALL";
		if (StringUtils.isNotEmpty(txtNoPO.getValue())) {
			vNoPO = txtNoPO.getValue();
		} 
		
		String vUserID = "ALL";
		if (StringUtils.isNotEmpty(txtUserID.getValue())) {
			vUserID = txtUserID.getValue();
		} 
		
		String vSupp = "ALL";
		if (StringUtils.isNotEmpty(txtNoSupp.getValue())) {
			vSupp = txtNoSupp.getValue();
		} 
		
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0104007");			
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01061_ChangeLogPO.jasper";
		

		
    	param.put("tglPOFrom",  vTglFrom); 
		param.put("tglPOUpto",  vTglUpto);  
		
		param.put("tglChangeFrom",  vTglUbahFrom); 
		param.put("tglChangeUpto",  vTglUbahUpto); 
		
		
		param.put("Company",  vCompany); 
		param.put("noPO",  vNoPO);  
		
		param.put("userID",  vUserID);
		param.put("Supp",  vSupp);
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		 
		
	}
 
}