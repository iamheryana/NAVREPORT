package solusi.hapis.webui.accounting.managementReporting;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class OutstandingAdjustmentCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Datebox dbTglUpto;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdALL;
	protected Radio rdAJ;
	protected Radio rdSP;
	
	protected Radiogroup rdgJnsOut;	 
	protected Radio rdUM;
	protected Radio rdSJ;
	
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdLap1;
	protected Radio rdLap2;
	protected Radio rdLap3;
	
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
	
	
    	dbTglUpto.setValue((new Date()));   
    	

		rdLap1.setSelected(true); 
		rdALL.setSelected(true); 
		rdUM.setSelected(true); 
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		
		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenisOut = "UM";
		if (StringUtils.isNotEmpty(rdgJnsOut.getSelectedItem().getValue())) {
			vJenisOut = rdgJnsOut.getSelectedItem().getValue();	
		} 		
		
		String vJenisLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJenisLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010105_OutUangMukaAdj.jasper";
		
		if  (vJenisOut.equals("UM") == true ){
			
			if  (vJenisLap.equals("DIM") == true ){
				jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010105_OutUangMukaAdj_GroupByDimension.jasper";
			} else {
				jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010105_OutUangMukaAdj.jasper";
				
				param.put("JnsRpt",  vJenisLap); 
				
			}		
			
			param.put("TglUmUpto",  vTglUpto); 
			
		} else {			
			if  (vJenisLap.equals("DIM") == true ){
				jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010106_OutSuratJalanAdj_GroupByDimension.jasper";
				param.put("TglSjUpto",  vTglUpto); 
			} else {
				jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010106_OutSuratJalanAdj.jasper";
				
				param.put("JnsRpt",  vJenisLap); 
				
			}	
			
			param.put("TglSjUpto",  vTglUpto); 
			
		}
		

		param.put("Company",  vCompany); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
//		if(vSaveAs.equals("PDF")){
//			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
//		} else {
//			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
//		}
		
	
		
	}
 
}