package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapSalesRevenueByCustomerCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;

	protected Decimalbox dcmNilai;	
	
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdJN1;
	protected Radio rdJN2;
	protected Radio rdJN3;
	protected Radio rdJN4;
	protected Radio rdJN5;
	
	
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
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
    	dbTglTo.setValue((new Date()));  
    	
    	dcmNilai.setValue(new BigDecimal (200));
    	
    	rdJN1.setSelected(true); 
    	rdXLS.setSelected(true); 
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		
		String vStrTglFrom =  "1900-01-01";
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
			
			vStrTglFrom  = frmTgl.format(vTglFrom);  
		}   
		
		Date vTglTo = new Date();   
		String vStrTglTo =  "1900-01-01";
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
			
			vStrTglTo  = frmTgl.format(vTglTo);  
		}
				

		BigDecimal vNilai = new BigDecimal(200);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507006");
		
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglTo, "ALL", "SRCS");
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04057_01_LapSalesRevenueByCustomer.jasper";

		String vJnsLap = "_01_";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
				
		
		
		
		
		
		param.put("TglInvFrom",  vTglFrom); 
		param.put("TglInvTo",  vTglTo);  
		param.put("ParamAmt",  vNilai);  
		param.put("ProsesId",  vProsesId);  
		
		jasperRpt = "/solusi/hapis/webui/reports/sales/04057"+vJnsLap+"LapSalesRevenueByCustomer.jasper";
		

		
		String vSaveAs = "XLS";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		

		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}
		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglTo, "ALL", "DELETE");

	}


 
	
}

