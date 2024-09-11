package solusi.hapis.webui.accounting.pajak;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class TopNPenjualanPembelianCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdPurchase;
	protected Radio rdSales;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	
	protected Intbox intTopN;
		
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		    	
    	rdAJ.setSelected(true); 
	    	
    	Calendar cTglCurr = Calendar.getInstance();		
  		cTglCurr.setTime(new Date());
  		
  		int yearTglCurr = cTglCurr.get(Calendar.YEAR);
  		  		
  		String dRFrom = "1/1/"+yearTglCurr;
  		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
  		Date vTglFrom  = dfRFrom.parse(dRFrom);
  		  		
  		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(new Date());
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
				
  		dbTglFrom.setValue(vTglFrom);
  		dbTglTo.setValue(vTglUpto); 
  		intTopN.setValue(10);
  		rdSales.setSelected(true);
    }
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		

    	Calendar cTglCurr = Calendar.getInstance();		
  		cTglCurr.setTime(new Date());
  		
  		int yearTglCurr = cTglCurr.get(Calendar.YEAR);
  		
  		
  		String dRFrom = "1/1/"+yearTglCurr;
  		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
  		Date vTglFrom  = dfRFrom.parse(dRFrom);
  		
  		
  		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(new Date());
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
  		  		
	
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
  
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglUpto = dbTglTo.getValue();
		}
				

		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenis = "Penjualan";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 			
	
		int vInt = 10;
		if (CommonUtils.isNotEmpty(intTopN.getValue())) {
			vInt = intTopN.getValue();
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0105005");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/pajak/0105015_TopNPenjualan.jasper";
		
		if (vJenis.equals("Penjualan") == true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/pajak/0105015_TopNPenjualan.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/accounting/pajak/0105016_TopNPembelian.jasper";
		}
		
    	param.put("PeriodeFrom",  vTglFrom); 
    	param.put("PeriodeUpto",  vTglUpto);  
	
		
		param.put("Company",  vCompany); 
		
		param.put("TopN",  vInt); 		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	 
		
	}
 
}


