package solusi.hapis.webui.accounting;

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

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class OutstandingInvoiceRentalCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;

	protected Datebox dbTglTo;
 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		
		
		String dRFrom = "1/"+monthTglFrom+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		//dbTglFrom.setValue(vTglFrom);
		
		
		Calendar cRTglUpto = Calendar.getInstance();
		cRTglUpto.setTime(vTglFrom);
		cRTglUpto.set(Calendar.DAY_OF_MONTH, cRTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cRTglUpto.getTime();
    	dbTglTo.setValue(vTglUpto);   
   
    	rdAJ.setSelected(true); 
    	rdPDF.setSelected(true); 
    
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		
		
		String dRFrom = "1/"+monthTglFrom+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);

		
		Calendar cRTglUpto = Calendar.getInstance();
		cRTglUpto.setTime(vTglFrom);
		cRTglUpto.set(Calendar.DAY_OF_MONTH, cRTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglTo = cRTglUpto.getTime();

				

		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
		
			
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0103008");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01022_OustandingInvoiceRental.jasper";
		
		param.put("TglInvTo",  vTglTo);  
		
		param.put("Company",  vCompany); 


		
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