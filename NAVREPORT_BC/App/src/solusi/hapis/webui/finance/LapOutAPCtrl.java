package solusi.hapis.webui.finance;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapOutAPCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglUpto;
	protected Textbox txtKodeVendorFrom;
	protected Textbox txtKodeVendorUpto;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	
	protected Combobox  cmbCurrency;
	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		dbTglUpto.setValue((new Date())); 
    	    	
    	txtKodeVendorUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
    	
    	rdAJ.setSelected(true); 
    	
    	cmbCurrency.setSelectedIndex(0);
    	
    	
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		String vCompany = "AJ";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		@SuppressWarnings("unused")
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			

		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		} 
		
		
		String vKodeVendorFrom = ".";
		if (StringUtils.isNotEmpty(txtKodeVendorFrom.getValue())) {
			vKodeVendorFrom = txtKodeVendorFrom.getValue();
		} 
		
		String vKodeVendorUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtKodeVendorUpto.getValue())) {
			vKodeVendorUpto = txtKodeVendorUpto.getValue();
		} 
		
		String vCurrency = "ALL";
		if (cmbCurrency.getSelectedItem().getValue() != null){
			vCurrency = (String) cmbCurrency.getSelectedItem().getValue();
		}
		
		
//		System.out.println("vCompany : "+vCompany);
//		System.out.println("vTglFrom : "+vTglFrom);
//		System.out.println("vPeriodeLenghth : "+vPeriodeLenghth);
//		System.out.println("vR1From : "+vR1From);
//		System.out.println("vR1Upto : "+vR1Upto);
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0202001");
		

		String jasperRpt = "/solusi/hapis/webui/reports/finance/02020_AJOutAP.jasper";
		

		if(vCompany.equals("AJ")){			
			jasperRpt = "/solusi/hapis/webui/reports/finance/02020_AJOutAP.jasper";
			
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/finance/02021_SPOutAP.jasper";
		}

		
		
		
		param.put("TglUpto",  vTglUpto); 
		
		
		param.put("KodeVendorFrom",  vKodeVendorFrom); 
		param.put("KodeVendorUpto",  vKodeVendorUpto); 
		
		param.put("Currency",  vCurrency); 
		
			
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}