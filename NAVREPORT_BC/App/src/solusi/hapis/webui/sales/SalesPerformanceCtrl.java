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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SalesPerformanceCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
			
	protected Intbox  intTahun;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Decimalbox dcmNilai;
	
	protected Combobox  cmbAkhirSem1;
	

	protected Datebox dbTglInvUpto;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
	 
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);
    	
		rdXLS.setSelected(true); 
		
		
		dcmNilai.setValue(new BigDecimal (500));
		cmbAkhirSem1.setSelectedIndex(9);
		
		
		Calendar cRTglInvUpto = Calendar.getInstance();
		cRTglInvUpto.setTime(new Date());
		cRTglInvUpto.set(Calendar.DAY_OF_MONTH, cRTglInvUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglInvUpto = cRTglInvUpto.getTime();		

		dbTglInvUpto.setValue(vTglInvUpto);
	
    	
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		
		int vTahun = yearTglCurr;
		if(CommonUtils.isNotEmpty(intTahun.getValue())){
			vTahun = intTahun.getValue();
		}
		
		String dRTglInvFrom = "1/1/"+vTahun;
		SimpleDateFormat dfRTglInvFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglInvFrom  = dfRTglInvFrom.parse(dRTglInvFrom);
		
		
		Calendar cRTglInvUpto = Calendar.getInstance();
		cRTglInvUpto.setTime(new Date());
		cRTglInvUpto.set(Calendar.DAY_OF_MONTH, cRTglInvUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglInvTo = cRTglInvUpto.getTime();	
		
		if(CommonUtils.isNotEmpty(dbTglInvUpto.getValue()) == true){  
			vTglInvTo = dbTglInvUpto.getValue();
		} 
		
		
		
				
		BigDecimal vNilai = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
	
		int vAkhirSem1 = 10;
		if (cmbAkhirSem1.getSelectedItem().getValue() != null){
			vAkhirSem1 = Integer.valueOf((String) cmbAkhirSem1.getSelectedItem().getValue());
		}
		
		String vStrTglFrom = vTahun+"/01/01";
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglUpto  = frmTgl.format(vTglInvTo);  
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507013");
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", "SRF");


		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04056_SalesPerformance.jasper";
		

		param.put("ProsesId",  vProsesId);
		param.put("Tahun",  vTahun);
		param.put("AmtBig",  vNilai);
		param.put("SemAkhir",  vAkhirSem1);		
		param.put("TglInvFrom",  vTglInvFrom); 
		param.put("TglInvTo",  vTglInvTo); 
		
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
		String vDelete = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", "DELETE");

		
	}
 
}

//