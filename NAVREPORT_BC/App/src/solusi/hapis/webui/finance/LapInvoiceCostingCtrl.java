package solusi.hapis.webui.finance;

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

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapInvoiceCostingCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;

	protected Textbox txtSalesFrom;
	protected Textbox txtSalesUpto;
	
//	protected Textbox txtTahun;
//	protected Combobox cmbPeriode;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	
    	rdALL.setSelected(true); 

    	txtSalesUpto.setValue("ZZZ");
    	
    	
    	Calendar cTglCurr = Calendar.getInstance();		
  		cTglCurr.setTime(new Date());
  		
  		int monthTglCurr = cTglCurr.get(Calendar.MONTH);
  		int yearTglCurr = cTglCurr.get(Calendar.YEAR);
  		
  		monthTglCurr = monthTglCurr+1;
  		
  		String dRFrom = "1/"+monthTglCurr+"/"+yearTglCurr;
  		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
  		Date vTglFrom  = dfRFrom.parse(dRFrom);
  		
  		
  		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(vTglFrom);
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
		
		
  		dbTglFrom.setValue(vTglFrom);
  		dbTglTo.setValue(vTglUpto); 
  		
//		Calendar cTgl = Calendar.getInstance();		
//		cTgl.setTime(new Date());
//		int yearTglCurr = cTgl.get(Calendar.YEAR);
//		int monthTglCurr = cTgl.get(Calendar.MONTH);
//		
//		monthTglCurr = monthTglCurr+1;
//		
//		txtTahun.setValue(String.valueOf(yearTglCurr));
//		
//		cmbPeriode.setSelectedIndex(monthTglCurr-1);
    }
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
//		SimpleDateFormat frmTgl= new SimpleDateFormat("dd/MM/yyyy");
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date vTanggal = null;
//			try {
//				vTanggal = formatter.parse("1900-01-01");			
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}			
//		Date vTglFrom = vTanggal;   
//
//		
//		Calendar cTgl = Calendar.getInstance();		
//		cTgl.setTime(new Date());
//		int yearTglCurr = cTgl.get(Calendar.YEAR);
//		int monthTglCurr = cTgl.get(Calendar.MONTH);
//		
//		monthTglCurr = monthTglCurr+1;
//				
//		String vTahun = String.valueOf(yearTglCurr);				
//		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == true){  
//			vTahun = txtTahun.getValue();
//		}
//		
//		String vMasa = String.valueOf(monthTglCurr);	
//		if (cmbPeriode.getSelectedIndex() != -1) {
//			vMasa =  (String) cmbPeriode.getSelectedItem().getValue();
//		}
//		
//		String vStrTglFrom = "1/"+vMasa+"/"+vTahun;
//		vTglFrom = frmTgl.parse(vStrTglFrom);
//		
//		
//		Date vTglTo = vTanggal;  
//		
//		Calendar cRUpto = Calendar.getInstance();
//		cRUpto.setTime(vTglFrom);
//		cRUpto.set(Calendar.DAY_OF_MONTH, cRUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
//		vTglTo = cRUpto.getTime();
//		
		

    	Calendar cTglCurr = Calendar.getInstance();		
  		cTglCurr.setTime(new Date());
  		
  		int monthTglCurr = cTglCurr.get(Calendar.MONTH);
  		int yearTglCurr = cTglCurr.get(Calendar.YEAR);
  		
  		monthTglCurr = monthTglCurr+1;
  		
  		String dRFrom = "1/"+monthTglCurr+"/"+yearTglCurr;
  		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
  		Date vTglFrom  = dfRFrom.parse(dRFrom);
  		
  		
  		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(vTglFrom);
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
  		
  		
	
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
  
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglUpto = dbTglTo.getValue();
		}
				

		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
			
		String vSalesFrom = ".";
		if (StringUtils.isNotEmpty(txtSalesFrom.getValue())) {
			vSalesFrom = txtSalesFrom.getValue();
		} 
		
		String vSalesUpto = "ZZZ";
		if (StringUtils.isNotEmpty(txtSalesUpto.getValue())) {
			vSalesUpto = txtSalesUpto.getValue();
		} 
				
	
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0204001");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02034_InvoiceCosting.jasper";
		
		
    	param.put("TglInvFrom",  vTglFrom); 
    	param.put("TglInvUpto",  vTglUpto);  
				
		param.put("SalesFrom",  vSalesFrom); 
		param.put("SalesUpto",  vSalesUpto); 
		
		param.put("Company",  vCompany); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	 
		
	}
 
}

