package solusi.hapis.webui.finance;



import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class UploadDataPayrollCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
protected Textbox txtSales;

	

	protected Textbox txtTahun;
	protected Combobox cmbPeriode;
		
	protected Radiogroup rdgKdPdpt;	 
	protected Radio rdKOMS;
	protected Radio rdBNSA;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		

		rdKOMS.setSelected(true); 

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
		
		String vNamaFile = "";
		
		String vMasa = String.valueOf(monthTglCurr);	
		if (cmbPeriode.getSelectedIndex() != -1) {
			vMasa =  (String) cmbPeriode.getSelectedItem().getValue();
			vNamaFile =  (String) cmbPeriode.getSelectedItem().getLabel();
		}
		

		String vKodePdpt = "KOMS";
		if (StringUtils.isNotEmpty(rdgKdPdpt.getSelectedItem().getValue())) {
			vKodePdpt = rdgKdPdpt.getSelectedItem().getValue();	
		} 
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02039_UploadDataPayroll_V2.jasper";
		

		param.put("masa",  vMasa); 
		param.put("tahun",  vTahun); 
		param.put("kodepdpt",  vKodePdpt);  
		
		vNamaFile = vNamaFile.substring(0, 3);
		vNamaFile = vNamaFile+"-"+vTahun+"_";
		
		if (vKodePdpt.equals("KOMS") == true){
			vNamaFile = vNamaFile+"KOMS-Komisi_Sales";
		} else {
			vNamaFile = vNamaFile+"BNSL-Sales_Bonus";
		}

		
		
		//new JReportGeneratorWindow(param, jasperRpt, "XLS",1); 
		new JReportGeneratorWindow(param, jasperRpt, "XLS", vNamaFile,"TEMP"); 
			 
		
	}
 
}

