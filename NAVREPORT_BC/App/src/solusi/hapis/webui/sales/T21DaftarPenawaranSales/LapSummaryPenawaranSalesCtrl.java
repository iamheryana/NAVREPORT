package solusi.hapis.webui.sales.T21DaftarPenawaranSales;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapSummaryPenawaranSalesCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Intbox  intTahun;

	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	
	protected Combobox cmbCabang;
	
	protected Textbox txtSales;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 

		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);
		
		
		rdALL.setSelected(true); 
		

		cmbCabang.setSelectedIndex(0);
    	
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
		
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String vCabang = "ALL";
		if (cmbCabang.getSelectedIndex() != 0) {
			vCabang= (String) cmbCabang.getSelectedItem().getValue();
		}
		
		
		String vSales = "ALL";
		if (StringUtils.isNotEmpty(txtSales.getValue())) {
			vSales = txtSales.getValue();	
		} 
		
				
		String jasperRpt = "/solusi/hapis/webui/reports/sales/NoPenawaran/040502_SummaryNoPenawaran.jasper";
		
	
		
		param.put("Tahun",  vTahun);
		param.put("Company",  vCompany); 
		param.put("Cabang",  vCabang); 
		param.put("Sales",  vSales); 
		
	
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	}
 
}
