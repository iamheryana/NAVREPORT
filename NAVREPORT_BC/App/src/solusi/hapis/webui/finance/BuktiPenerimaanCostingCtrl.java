package solusi.hapis.webui.finance;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.M02SalespersonLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class BuktiPenerimaanCostingCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowBuktiPenerimaanCosting;
	

	protected Textbox txtSales;

	
	//protected Datebox dbTgl;
	protected Textbox txtTahun;
	protected Combobox cmbPeriode;
		
//	protected Bandbox  cmbSales;
//	protected Listbox listSales;
//	protected String vSales = "ALL";
	
//	protected Radiogroup rdgStaff;	 
//	protected Radio rdStaff1;
//	protected Radio rdStaff2;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Button btnSearchSalesLOV;
	
	protected Bandbox  cmbStaff;
	protected Listbox listStaff;
	protected String vStaff = "XXX";
	
	private SelectQueryService selectQueryService;
	
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
//		listSales.appendItem("ALL", "ALL");
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
	
		
//		rdStaff1.setSelected(true); 

		rdPDF.setSelected(true); 
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		
		monthTglCurr = monthTglCurr+1;
		
		txtTahun.setValue(String.valueOf(yearTglCurr));
		
		cmbPeriode.setSelectedIndex(monthTglCurr-1);
		
		
		Bandpopup popup2 = new Bandpopup();
			listStaff = new Listbox();
			listStaff.setMold("paging");
			listStaff.setAutopaging(false);
			listStaff.setWidth("400px");
			listStaff.addEventListener(Events.ON_SELECT, selectStaff());
			listStaff.setParent(popup2);
		popup2.setParent(cmbStaff);
	        
		
		List<Object[]> vResultStaff = selectQueryService.QueryStaffFinanceUntukCosting();
		if(CommonUtils.isNotEmpty(vResultStaff)){
			for(Object[] aRslt : vResultStaff){
				listStaff.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbStaff.setValue(listStaff.getItemAtIndex(0).getLabel());
		listStaff.setSelectedItem(listStaff.getItemAtIndex(0));
		vStaff = listStaff.getSelectedItem().getValue().toString();
		
	}
	
	private EventListener selectStaff() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbStaff.setValue(listStaff.getSelectedItem().getLabel());
				vStaff = listStaff.getSelectedItem().getValue().toString();
				cmbStaff.close();
			}
		};
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
		M02Salesperson sales = M02SalespersonLOV.show(windowBuktiPenerimaanCosting);

		if (sales != null) {
			txtSales.setValue(sales.getSales());
		
		} else {
			txtSales.setValue(null);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
	
//				
//		Date vTgl = new Date();   
//		if(CommonUtils.isNotEmpty(dbTgl.getValue()) == true){  
//			vTgl = dbTgl.getValue();
//		}
//				
		
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
		
//		String vStaff = "S1";
//		if (StringUtils.isNotEmpty(rdgStaff.getSelectedItem().getValue())) {
//			vStaff = rdgStaff.getSelectedItem().getValue();	
//		} 
		
		String vSales = "ALL";
		if(CommonUtils.isNotEmpty(txtSales.getValue()) == true){  
			vSales = txtSales.getValue();
		}
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02036_BuktiPenerimaanCosting.jasper";
		//System.out.println("vStaff : "+vStaff);

		param.put("masa",  vMasa); 
		param.put("tahun",  vTahun); 
		param.put("Sales",  vSales.toUpperCase());  
		param.put("Staff",  vStaff);  
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vSaveAs.equals("PDF")){
			//new JReportGeneratorWindow(param, jasperRpt, "PDF",1); 
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
			
		} else {
			//new JReportGeneratorWindow(param, jasperRpt, "XLS",1); 
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}
		
		
		
	 
		
	}
 
}

