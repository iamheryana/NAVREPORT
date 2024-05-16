package solusi.hapis.webui.finance;


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
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class DetailKomisiSatindoCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
		
	protected Radiogroup rdgGroup;
	protected Radio rdALL;
	protected Radio rdSAT;
	protected Radio rdIND;


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
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		
		monthTglCurr = monthTglCurr+1;
		
		txtTahun.setValue(String.valueOf(yearTglCurr));
		
		cmbPeriode.setSelectedIndex(monthTglCurr-1);
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
		
		String vMasa = String.valueOf(monthTglCurr);	
		if (cmbPeriode.getSelectedIndex() != -1) {
			vMasa =  (String) cmbPeriode.getSelectedItem().getValue();
		}
	
		String vReg = "ALL";
		if (StringUtils.isNotEmpty(rdgGroup.getSelectedItem().getValue())) {
			vReg = rdgGroup.getSelectedItem().getValue();	
		} 
		
		
		PathReport pathReport = new PathReport();
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02041_DetailKomisiSatindo.jasper";
		
		param.put("SUBREPORT_DIR",  pathReport.getSubRptFinance());
		param.put("masa",  vMasa); 
		param.put("tahun",  vTahun); 
		param.put("reg",  vReg);  
	
		
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

