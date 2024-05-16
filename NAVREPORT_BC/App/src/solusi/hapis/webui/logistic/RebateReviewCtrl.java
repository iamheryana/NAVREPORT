package solusi.hapis.webui.logistic;




import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class RebateReviewCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	
	
	protected Textbox txtVendorFrom;
	protected Textbox txtVendorUpto;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdPO;
	protected Radio rdINV;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
    	dbTglUpto.setValue((new Date()));   
    	    	
    	rdALL.setSelected(true);     	

    	rdPO.setSelected(true);     
    	
    	txtVendorUpto.setValue("ZZZZZZZ");
    	
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		

		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		

		String vVendorFrom = ".";
		if (StringUtils.isNotEmpty(txtVendorFrom.getValue())) {
			vVendorFrom = txtVendorFrom.getValue();
		} 
		
		String vVendorUpto = "ZZZZZZZ";
		if (StringUtils.isNotEmpty(txtVendorUpto.getValue())) {
			vVendorUpto = txtVendorUpto.getValue();
		} 
		
		
		
		String vJenis = "PO";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		}
		
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/RebateByPO.jasper";
		
		if(vJenis.equals("PO")){
			jasperRpt = "/solusi/hapis/webui/reports/logistic/RebateByPO.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/logistic/RebateByInvoice.jasper";
		}
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo); 
		
		param.put("VendorFrom",  vVendorFrom); 
		param.put("VendorUpto",  vVendorUpto); 		
		
		param.put("Company",  vCompany); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}