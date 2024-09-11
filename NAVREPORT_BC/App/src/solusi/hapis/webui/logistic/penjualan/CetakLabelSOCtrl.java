package solusi.hapis.webui.logistic.penjualan;

import java.io.Serializable;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class CetakLabelSOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	

	protected Textbox txtNoSOFrom;
	protected Textbox txtNoSOUpto;
		
	protected Intbox IntMulaiCetak;
	protected Intbox IntJumlahCetak;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	    	
		IntMulaiCetak.setValue(1);
		IntJumlahCetak.setValue(1);

	}
	
	
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
	
		String vNoSOFrom = ".";
		if (StringUtils.isNotEmpty(txtNoSOFrom.getValue())) {
			vNoSOFrom = txtNoSOFrom.getValue();
		} 
		
		String vNoSOUpto = ".";
		if (StringUtils.isNotEmpty(txtNoSOUpto.getValue())) {
			vNoSOUpto = txtNoSOUpto.getValue();
		} 
		
		int vMulaiCetak = 1;
		if(CommonUtils.isNotEmpty(IntMulaiCetak.getValue())){
			vMulaiCetak = IntMulaiCetak.getValue();
		}
		
		int vJumlahCetak = 1;
		if(CommonUtils.isNotEmpty(IntJumlahCetak.getValue())){
			vJumlahCetak = IntJumlahCetak.getValue();
		}
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0305011");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03044_CetakLabelSO.jasper";
		

		param.put("NoSoFrom",  vNoSOFrom); 
		param.put("NoSoUpto",  vNoSOUpto); 
		param.put("MulaiCetak",  vMulaiCetak); 
		param.put("JumlahCetak",  vJumlahCetak); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		 
		
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK2(Event event) throws InterruptedException, ParseException {
		
		
		String vNoSOFrom = ".";
		if (StringUtils.isNotEmpty(txtNoSOFrom.getValue())) {
			vNoSOFrom = txtNoSOFrom.getValue();
		} 
		
		String vNoSOUpto = ".";
		if (StringUtils.isNotEmpty(txtNoSOUpto.getValue())) {
			vNoSOUpto = txtNoSOUpto.getValue();
		} 
		
		
		int vJumlahCetak = 1;
		if(CommonUtils.isNotEmpty(IntJumlahCetak.getValue())){
			vJumlahCetak = IntJumlahCetak.getValue();
		}
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0305011");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03044_CetakLabelSO_2.jasper";
		

		param.put("NoSoFrom",  vNoSOFrom); 
		param.put("NoSoUpto",  vNoSOUpto); 
		param.put("JumlahCetak",  vJumlahCetak); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		 
		
	}
 
}