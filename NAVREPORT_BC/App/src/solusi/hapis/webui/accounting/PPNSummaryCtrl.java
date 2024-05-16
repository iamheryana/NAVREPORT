package solusi.hapis.webui.accounting;


import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class PPNSummaryCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Textbox txtTahun; 
	

	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdSUM;
	protected Radio rdSUB;
	protected Radio rdDTL;
	

	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		
		
		txtTahun.setValue(String.valueOf(yearTgl));
		
		rdAJ.setSelected(true); 
		rdSUM.setSelected(true); 
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		
		
		String  vTahun = String.valueOf(yearTgl);
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == true){  
			vTahun = txtTahun.getValue();
		}
		
		
		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		}
		
		String vJenis = "SUM";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0105006");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01062_PPNSummary.jasper";

		if (vJenis.equals("SUM") == true) {
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01062_PPNSummary.jasper";
			
			param.put("Tahun",  vTahun); 
		} else {
			if (vJenis.equals("SUB") == true) {
				jasperRpt = "/solusi/hapis/webui/reports/accounting/01063_PPNSummarySubDetail.jasper";
			} else {
				jasperRpt = "/solusi/hapis/webui/reports/accounting/01064_PPNSummaryDetail.jasper";
			}
			
			param.put("Tahun",  Integer.valueOf(vTahun)); 
		}
		
		
		param.put("Company",  vCompany); 
		


		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		
	}
 
}
