package solusi.hapis.webui.accounting;


import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class KwitansiCicilanCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	
	protected Textbox txtInv;
	protected Textbox txtAmount;
	protected Intbox IntJml;
	
	protected Textbox txtTTD;
	protected Textbox txtJabatan;
	
	protected Textbox txtNoKontrak;
	
	protected Datebox dbTglMulaiCicilan;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

		txtTTD.setValue("ODE PASARIBU");
		txtJabatan.setValue("ACC & FINANCE MANAGER");
		
		dbTglMulaiCicilan.setValue(new Date());   
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		String vInv = ".";
		if (StringUtils.isNotEmpty(txtInv.getValue())) {
			vInv = txtInv.getValue();
		} 
		
		String vTTD= "ODE PASARIBU";
		if (StringUtils.isNotEmpty(txtTTD.getValue())) {
			vTTD = txtTTD.getValue();
		} 
		
		String vJabatan= "ACC & FINANCE MANAGER";
		if (StringUtils.isNotEmpty(txtJabatan.getValue())) {
			vJabatan = txtJabatan.getValue();
		} 
		
		int vJml = 0;
		if (CommonUtils.isNotEmpty(IntJml.getValue())) {
			vJml = IntJml.getValue();
		} 
		
		String vAmount= "0";
		if (StringUtils.isNotEmpty(txtAmount.getValue())) {
			vAmount = txtAmount.getValue();
		}
		
		String vNoKontrak = ".";
		if (StringUtils.isNotEmpty(txtNoKontrak.getValue())) {
			vNoKontrak = txtNoKontrak.getValue();
		} 
		
		Long vSubTotal = Long.valueOf(vAmount);
		Long vTotal = Long.valueOf(0);
		if(vJml > 0){
			vTotal = vSubTotal / vJml;
		}
		
		Date vTglMulaiCicilan = new Date();   
		if(CommonUtils.isNotEmpty(dbTglMulaiCicilan.getValue()) == true){  
			vTglMulaiCicilan = dbTglMulaiCicilan.getValue();
		}
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108002");
		
		String vTerbilang = "*** "+(CommonUtils.info_terbilang(vTotal)).toUpperCase()+ " RUPIAH ***";
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01031_KwitansiCicilan.jasper";

		PathReport pathreport = new PathReport();
		
    	param.put("NoInvoice",  vInv); 
		param.put("Jml",  vJml);  
		param.put("Terbilang",  vTerbilang); 
		param.put("TTD",  vTTD); 
		param.put("Jabatan",  vJabatan); 
		param.put("Total",  vTotal); 
		param.put("NoKontrak",  vNoKontrak); 
		param.put("TglMulaiCicilan", vTglMulaiCicilan);
		param.put("SUBREPORT_DIR",  pathreport.getSubRptAccounting());		
		
		new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		
		 
		
	}
 
	@SuppressWarnings("unchecked")
	public void onClick$btnInv(Event event) throws InterruptedException {
		
		String vInv = ".";
		if (StringUtils.isNotEmpty(txtInv.getValue())) {
			vInv = txtInv.getValue();
		} 
		
		String vTTD= "ODE PASARIBU";
		if (StringUtils.isNotEmpty(txtTTD.getValue())) {
			vTTD = txtTTD.getValue();
		} 
		
		String vJabatan= "ACC & FINANCE MANAGER";
		if (StringUtils.isNotEmpty(txtJabatan.getValue())) {
			vJabatan = txtJabatan.getValue();
		} 
		
		int vJml = 0;
		if (CommonUtils.isNotEmpty(IntJml.getValue())) {
			vJml = IntJml.getValue();
		} 
		
		String vAmount= "0";
		if (StringUtils.isNotEmpty(txtAmount.getValue())) {
			vAmount = txtAmount.getValue();
		}
		
		String vNoKontrak = ".";
		if (StringUtils.isNotEmpty(txtNoKontrak.getValue())) {
			vNoKontrak = txtNoKontrak.getValue();
		} 
		
		Long vSubTotal = Long.valueOf(vAmount);
		Long vTotal = Long.valueOf(0);
		if(vJml > 0){
			vTotal = vSubTotal / vJml;
		}
		
		Date vTglMulaiCicilan = new Date();   
		if(CommonUtils.isNotEmpty(dbTglMulaiCicilan.getValue()) == true){  
			vTglMulaiCicilan = dbTglMulaiCicilan.getValue();
		}
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108002");
		
		String vTerbilang = "*** "+(CommonUtils.info_terbilang(vTotal)).toUpperCase()+ " RUPIAH ***";
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01038_KwitansiInvoiceCicilan.jasper";

		PathReport pathreport = new PathReport();
		
    	param.put("NoInvoice",  vInv); 
		param.put("Jml",  vJml);  
		param.put("Terbilang",  vTerbilang); 
		param.put("TTD",  vTTD); 
		param.put("Jabatan",  vJabatan); 
		param.put("Total",  vTotal); 
		param.put("NoKontrak",  vNoKontrak); 
		param.put("TglMulaiCicilan", vTglMulaiCicilan);
		param.put("SUBREPORT_DIR",  pathreport.getSubRptAccounting());		
		
		new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		
		 
		
	}
}

