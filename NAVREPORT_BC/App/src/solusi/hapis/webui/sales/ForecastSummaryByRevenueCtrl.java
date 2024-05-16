package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ForecastSummaryByRevenueCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Intbox  intTahun;

	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Radiogroup rdgPot;	 
	protected Radio rdALL;
	protected Radio rdQUA;
	
//	protected Combobox  cmbPotensialReal;
	protected Combobox  cmbProjectCat;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 

		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);
    	
		rdPDF.setSelected(true); 
		
		rdALL.setSelected(true); 
		
//		cmbPotensialReal.setSelectedIndex(0);
		cmbProjectCat.setSelectedIndex(0);
    	
	}
	


	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		
		int vTahun = yearTglCurr;
		if(CommonUtils.isNotEmpty(intTahun.getValue())){
			vTahun = intTahun.getValue();
		}
		
		String vStrTglFrom = vTahun+"/01/01";
		String vStrTglUpto = vTahun+"/12/31";
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0503004");
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", "SRF");


		
				
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04040_ForecastSummaryByRevenue.jasper";
				
		String vPot = "ALL";
		if (StringUtils.isNotEmpty(rdgPot.getSelectedItem().getValue())) {
			vPot = rdgPot.getSelectedItem().getValue();	
		} 
		
		if(vPot.equals("ALL")){
			jasperRpt = "/solusi/hapis/webui/reports/sales/04040_ForecastSummaryByRevenue_02.jasper";
		} else{
			jasperRpt = "/solusi/hapis/webui/reports/sales/04040_ForecastSummaryByRevenue.jasper";
		}
		
		String vPotensialReal = "ALL";
//		if (cmbPotensialReal.getSelectedItem().getValue() != null){
//			vPotensialReal = (String) cmbPotensialReal.getSelectedItem().getValue();
//		}
		
		String vProjectCat = "ALL";
		if (cmbProjectCat.getSelectedItem().getValue() != null){
			vProjectCat = (String) cmbProjectCat.getSelectedItem().getValue();
		}
		
		param.put("Tahun",  vTahun);
		param.put("Potensi",  vPotensialReal); 
		param.put("Size",  vProjectCat); 
		param.put("ProsesId",  vProsesId);
		
		
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
			
		}
		
		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", "DELETE");
		
	}
 
}

