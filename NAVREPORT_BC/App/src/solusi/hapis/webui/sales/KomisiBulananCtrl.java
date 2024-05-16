package solusi.hapis.webui.sales;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.tabel.model.T03salesperson;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.T03salespersonLOVFilter;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class KomisiBulananCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtTahun;
	protected Combobox cmbPeriode;
			
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Textbox txtSales;
	
	protected Button btnSearchSalesLOV;
	protected Window windowKomisiBulanan;
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		rdPDF.setSelected(true); 
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		
		monthTglCurr = monthTglCurr+1;
		
		txtTahun.setValue(String.valueOf(yearTglCurr));
		
		cmbPeriode.setSelectedIndex(monthTglCurr-1);
		String vUserId = SecurityContextHolder.getContext().getAuthentication().getName();
		if (vUserId.length() > 3 ){
			txtSales.setValue(vUserId.substring(0, 3));
		} else {
			txtSales.setValue(vUserId);
		}
    }
	
	public void onClick$btnSearchSalesLOV(Event event) {
		T03salesperson sales = T03salespersonLOVFilter.show(windowKomisiBulanan);

		if (sales != null) {
			txtSales.setValue(sales.getSales());
		
		} else {
			txtSales.setValue(null);
		}
		
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
		
		
		String vSales = "ALL";
		if(CommonUtils.isNotEmpty(txtSales.getValue()) == true){  
			vSales = txtSales.getValue();
		}
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04032_KomisiBulanan.jasper";
		
		

		param.put("masa",  vMasa); 
		param.put("tahun",  vTahun); 
		param.put("Sales",  vSales.toUpperCase());  
		param.put("UserId",  SecurityContextHolder.getContext().getAuthentication().getName());  
		
		
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

