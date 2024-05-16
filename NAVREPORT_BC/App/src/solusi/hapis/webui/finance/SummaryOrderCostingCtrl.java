package solusi.hapis.webui.finance;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.M02SalespersonLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SummaryOrderCostingCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowSummaryOrderCosting;
	
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
		M02Salesperson sales = M02SalespersonLOV.show(windowSummaryOrderCosting);

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
			
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02037_SummaryOrderCosting.jasper";
		

		param.put("Tahun",  vTahun); 
		param.put("Sales",  vSales.toUpperCase());  
		param.put("masa", vMasa);
		
		
		//new JReportGeneratorWindow(param, jasperRpt, "XLS",1); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	 
		
	}
 
}

