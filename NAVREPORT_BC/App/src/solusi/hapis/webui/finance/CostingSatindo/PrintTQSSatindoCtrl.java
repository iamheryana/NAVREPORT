package solusi.hapis.webui.finance.CostingSatindo;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class PrintTQSSatindoCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
		
	protected Radiogroup rdgGroup;
	protected Radio rdALL;
	protected Radio rdSAT;
	protected Radio rdIND;
	
	protected Radiogroup rdgJenis;
	protected Radio rdSUM;
	protected Radio rdDTL;


	protected Textbox txtTahun;
	protected Combobox cmbPeriode;
		
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Button btnSearchSalesLOV;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		
		rdALL.setSelected(true); 

		rdPDF.setSelected(true); 
		
		rdSUM.setSelected(true); 
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);

		
		txtTahun.setValue(String.valueOf(yearTglCurr));
		
		cmbPeriode.setSelectedIndex(0);
    }
	
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {

		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		
		monthTglCurr = monthTglCurr+1;
				
		String vTahun = String.valueOf(yearTglCurr);				
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == true){  
			vTahun = txtTahun.getValue();
		}
	
	
		String vReg = "ALL";
		if (StringUtils.isNotEmpty(rdgGroup.getSelectedItem().getValue())) {
			vReg = rdgGroup.getSelectedItem().getValue();	
		} 
		
		String vJenisLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenisLap = rdgJenis.getSelectedItem().getValue();	
		} 
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/CostingSatindo/0205001_SummaryTQSSatindo.jasper";
		

		if (vJenisLap.equals("SUM") == true){
			jasperRpt = "/solusi/hapis/webui/reports/finance/CostingSatindo/0205001_SummaryTQSSatindo.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/finance/CostingSatindo/0205002_DetailTQSSatindo.jasper";
		}
		
		param.put("Masa",  "01"); 
		param.put("Tahun",  vTahun); 
		param.put("Reg",  vReg);  
	
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}
		
		
		
	 
		
	}
 
}

