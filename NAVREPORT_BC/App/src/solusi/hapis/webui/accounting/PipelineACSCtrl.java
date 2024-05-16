package solusi.hapis.webui.accounting;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class PipelineACSCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Intbox  intTahun;
		
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Decimalbox dcmNilai;
	
	protected Decimalbox dcmHigh;
	protected Decimalbox dcmMed;
	protected Decimalbox dcmLow;
	
	protected Combobox  cmbAkhirSem1;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);
    	
		rdSUM.setSelected(true); 
	
		
		dcmNilai.setValue(new BigDecimal (500));
		
		dcmHigh.setValue(new BigDecimal (90));
		dcmMed.setValue(new BigDecimal (60));
		dcmLow.setValue(new BigDecimal (30));
		
		
		
		cmbAkhirSem1.setSelectedIndex(3);
		
		
    	
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
		
				
		BigDecimal vNilai = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
		
		BigDecimal vHigh = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmHigh.getValue())) {
			vHigh = dcmHigh.getValue();
		} 
		
		BigDecimal vMed = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmMed.getValue())) {
			vMed = dcmMed.getValue();
		} 
		
		BigDecimal vLow = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmLow.getValue())) {
			vLow = dcmLow.getValue();
		} 
	
		int vAkhirSem1 = 4;
		if (cmbAkhirSem1.getSelectedItem().getValue() != null){
			vAkhirSem1 = Integer.valueOf((String) cmbAkhirSem1.getSelectedItem().getValue());
		}
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0103013");		
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01056_PipelineACS.jasper";
		
		String vJnsRpt = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsRpt = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		param.put("Tahun",  vTahun);
		param.put("AmtBig",  vNilai);
		param.put("Sem1Akhir",  vAkhirSem1);
		param.put("weightH",  vHigh);
		param.put("weightM",  vMed);
		param.put("weightL",  vLow);
		param.put("JnsRpt",  vJnsRpt);		

		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 		
	}
 
}

