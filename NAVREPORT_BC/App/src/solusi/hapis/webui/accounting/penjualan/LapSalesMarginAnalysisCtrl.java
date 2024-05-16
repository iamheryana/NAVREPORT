package solusi.hapis.webui.accounting.penjualan;

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
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapSalesMarginAnalysisCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Row rowOption;
	protected Row RowCustomer;
	protected Row RowItem;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	
//	protected Radiogroup rdgTipe;	 
//	protected Radio rdA;
//	protected Radio rdB;
	

	protected Radiogroup rdgKelompokMargin;	 
	protected Radio rdKMD;
	protected Radio rdKMC;
	protected Radio rdKMB;
	protected Radio rdKMA;
	protected Radio rdKMALL;
	
	protected Radiogroup rdgJenisLap;	 
	protected Radio rdJ1;
	protected Radio rdJ2;
	protected Radio rdJ3;
	protected Radio rdJ4;
	
	
	protected Radiogroup rdgInvoiceUM;	 
	protected Radio rdIn;
	protected Radio rdEx;
	
	protected Textbox txtCustNo;
	protected Textbox txtItemNo;
	
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
    	
//    	rdA.setSelected(true); 
    	rdKMALL.setSelected(true); 
    	rdJ2.setSelected(true); 
    	
    	
    	
    	rdEx.setSelected(true); 
    	rowOption.setVisible(false);
    	RowCustomer.setVisible(false);
    	RowItem.setVisible(false);

	}
	
	
	public void onCheck$rdgKelompokMargin(Event event){
		if(rdKMD.isChecked() == true ) {
			rowOption.setVisible(true);
			rdEx.setSelected(true); 
		} else {
			rowOption.setVisible(false);
		}
	}
	
	public void onCheck$rdgJenisLap(Event event){
		if(rdJ1.isChecked() == true ) {
			RowCustomer.setVisible(true);
	    	RowItem.setVisible(true);
		} else {
			RowCustomer.setVisible(false);
	    	RowItem.setVisible(false);

		}
	}
			
			

	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
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
				

		
//		String vTipe = "A";
//		if (StringUtils.isNotEmpty(rdgTipe.getSelectedItem().getValue())) {
//			vTipe = rdgTipe.getSelectedItem().getValue();	
//		} 
		
		String vKelompokMargin = "ALL";
		if (StringUtils.isNotEmpty(rdgKelompokMargin.getSelectedItem().getValue())) {
			vKelompokMargin = rdgKelompokMargin.getSelectedItem().getValue();	
		} 
		
		String vJenisLap = "J2";
		if (StringUtils.isNotEmpty(rdgJenisLap.getSelectedItem().getValue())) {
			vJenisLap = rdgJenisLap.getSelectedItem().getValue();	
		} 
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0103015");				
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callSalesMarginAnalysis(vProsesId, vStrTglFrom, vStrTglTo, "CETAK");
		
		
		String vInvUM = "ALL";
		if (StringUtils.isNotEmpty(rdgInvoiceUM.getSelectedItem().getValue())) {
			vInvUM = rdgInvoiceUM.getSelectedItem().getValue();	
		} 
		

		String vCustNo = "ALL";
		if (StringUtils.isNotEmpty(txtCustNo.getValue())) {
			vCustNo = txtCustNo.getValue();
		} 
		
		String vItemNo = "ALL";
		if (StringUtils.isNotEmpty(txtItemNo.getValue())) {
			vItemNo = txtItemNo.getValue();
		} 
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/penjualan/010301_LapSalesMarginAnalisis.jasper";
		
//		if (vTipe.equals("A") == true){
//			if (vJenisLap.equals("J1") == true) {
//				jasperRpt = "/solusi/hapis/webui/reports/accounting/penjualan/010301_LapSalesMarginAnalisis_00.jasper";
//			}
//			if (vJenisLap.equals("J2") == true) {
//				jasperRpt = "/solusi/hapis/webui/reports/accounting/penjualan/010302_LapSalesMarginAnalisisByBranch_00.jasper";
//			}
//			if (vJenisLap.equals("J3") == true) {
//				jasperRpt = "/solusi/hapis/webui/reports/accounting/penjualan/010303_LapSalesMarginAnalisisByCustomer_00.jasper";
//			}
//		} else {
			if (vJenisLap.equals("J1") == true) {
				jasperRpt = "/solusi/hapis/webui/reports/accounting/penjualan/010301_LapSalesMarginAnalisis.jasper";
				
				param.put("CustNo",  vCustNo); 
				param.put("ItemNo",  vItemNo);  
				
			}
			if (vJenisLap.equals("J2") == true) {
				jasperRpt = "/solusi/hapis/webui/reports/accounting/penjualan/010302_LapSalesMarginAnalisisByBranch.jasper";
			}
			if (vJenisLap.equals("J3") == true) {
				jasperRpt = "/solusi/hapis/webui/reports/accounting/penjualan/010303_LapSalesMarginAnalisisByCustomer.jasper";
			}
			if (vJenisLap.equals("J4") == true) {
				jasperRpt = "/solusi/hapis/webui/reports/accounting/penjualan/010304_LapSalesMarginAnalisisByCustomerDetail.jasper";
			}
//		}

		
		
		param.put("TglInvFrom",  vTglFrom); 
		param.put("TglInvTo",  vTglTo);  
		param.put("GroupMargin",  vKelompokMargin); 
		param.put("ProsesId",  vProsesId); 
		param.put("InvUM",  vInvUM); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callSalesMarginAnalysis(vProsesId, vStrTglFrom, vStrTglTo, "DELETE");
		
		
	}
 
}

