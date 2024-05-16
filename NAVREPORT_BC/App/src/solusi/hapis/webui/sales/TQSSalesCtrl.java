package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.tabel.model.T03salesperson;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.T03salespersonLOVFilter;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class TQSSalesCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowTQSSales;
	
	protected Textbox txtTahun;
	protected Textbox txtSales;
		
	//protected Bandbox  cmbSales;
	//protected Listbox listSales;
	//protected String vSales = ".";
	protected Combobox cmbPeriode;
	
	protected Button btnSearchSalesLOV;
		
//	private SelectQueryNavReportService selectQueryNavReportService;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		

    	
//    	Bandpopup popup1 = new Bandpopup();
//			listSales = new Listbox();
//			listSales.setMold("paging");
//			listSales.setAutopaging(true);
//			listSales.setWidth("250px");
//			listSales.addEventListener(Events.ON_SELECT, selectSales());
//			listSales.setParent(popup1);
//		popup1.setParent(cmbSales);
//	        
//		listSales.appendItem("<Please Select>", ".");
//		
//		List<Object[]> vResult = selectQueryNavReportService.QuerySalesperson();
//		if(CommonUtils.isNotEmpty(vResult)){
//			for(Object[] aRslt : vResult){
//				listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
//			}
//		}
//		
//		
//		cmbSales.setValue(listSales.getItemAtIndex(0).getLabel());
//		listSales.setSelectedItem(listSales.getItemAtIndex(0));
	
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglFrom = cTgl.get(Calendar.YEAR);
		
		txtTahun.setValue(String.valueOf(yearTglFrom));
		
		cmbPeriode.setSelectedIndex(0);
		
		
		String vUserId = SecurityContextHolder.getContext().getAuthentication().getName();
		if (vUserId.length() > 3 ){
			txtSales.setValue(vUserId.substring(0, 3));
		} else {
			txtSales.setValue(vUserId);
		}

    }
	
//	private EventListener selectSales() {
//		return new EventListener() {
//
//			@Override
//			public void onEvent(Event event) throws Exception {
//				
//				cmbSales.setValue(listSales.getSelectedItem().getLabel());
//				vSales = listSales.getSelectedItem().getValue().toString();
//				cmbSales.close();
//			}
//		};
//	}
	
	public void onClick$btnSearchSalesLOV(Event event) {
		T03salesperson sales = T03salespersonLOVFilter.show(windowTQSSales);

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
		int yearTglFrom = cTgl.get(Calendar.YEAR);
				
		String vTahun = String.valueOf(yearTglFrom);				
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == true){  
			vTahun = txtTahun.getValue();
		}
		
		String vMasa = "01";
		if (cmbPeriode.getSelectedIndex() != -1) {
			vMasa =  (String) cmbPeriode.getSelectedItem().getValue();
		}
			
		String vSales = "ALL";
		if(CommonUtils.isNotEmpty(txtSales.getValue()) == true){  
			vSales = txtSales.getValue();
		}
			
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04035_TQSSales.jasper";
		

		param.put("Tahun",  vTahun); 
		param.put("Sales",  vSales.toUpperCase());  
		param.put("masa", vMasa);
		param.put("UserId",  SecurityContextHolder.getContext().getAuthentication().getName());  
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	 
		
	}
 
}

