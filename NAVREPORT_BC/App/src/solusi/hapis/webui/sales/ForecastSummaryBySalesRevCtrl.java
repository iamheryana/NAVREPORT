package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ForecastSummaryBySalesRevCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Row rowSales;
	protected Row rowSemester;
	protected Row rowAmount;
	protected Row rowNote;
	protected Row rowNote1;
	protected Row rowNote2;	
	protected Row rowBatasSem1;
	protected Row rowBatasSem2;
	
	protected Intbox  intTahun;

	protected Radiogroup rdgSem;	 
	protected Radio rdSemALL;
	protected Radio rdSem1;
	protected Radio rdSem2;
	
		
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSUM;
	//protected Radio rdSUM2;
	protected Radio rdSUM3;
	protected Radio rdDTL;
	protected Radio rdDTL2;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Combobox  cmbPotensialReal;
	protected Combobox  cmbProjectCat;
	
	private SelectQueryService selectQueryService;
	
	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = "ALL";
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	protected Decimalbox dcmNilai;
	
	protected Combobox  cmbAkhirSem1;
	protected Combobox  cmbAkhirSem2;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		cmbAkhirSem1.setSelectedIndex(5);
		
		cmbAkhirSem2.setSelectedIndex(11);
		
		rowSales.setVisible(false);
		rowSemester.setVisible(false);
		rowAmount.setVisible(false);
		rowNote.setVisible(true);
		rowNote1.setVisible(true);
		rowNote2.setVisible(true);
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);
    	
		rdPDF.setSelected(true); 
				
		rdSemALL.setSelected(true); 
		
		rdSUM.setSelected(true); 
		
		dcmNilai.setValue(new BigDecimal (0));
		
		cmbPotensialReal.setSelectedIndex(0);
		cmbProjectCat.setSelectedIndex(0);
		
		
		Bandpopup popup1 = new Bandpopup();
			listCabang = new Listbox();
			listCabang.setMold("paging");
			listCabang.setAutopaging(true);
			listCabang.setWidth("250px");
			listCabang.addEventListener(Events.ON_SELECT, selectCabang());
			listCabang.setParent(popup1);
		popup1.setParent(cmbCab);
	        
		listCabang.appendItem("ALL", "ALL");
		
		List<Object[]> vResult = selectQueryService.QueryCabang();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listCabang.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
	
	
		cmbCab.setValue(listCabang.getItemAtIndex(0).getLabel());
		listCabang.setSelectedItem(listCabang.getItemAtIndex(0));
	


		Bandpopup popup2 = new Bandpopup();
			listSales = new Listbox();
			listSales.setMold("paging");
			listSales.setAutopaging(false);
			listSales.setWidth("400px");
			listSales.addEventListener(Events.ON_SELECT, selectSales());
			listSales.setParent(popup2);
		popup2.setParent(cmbSales);
	        
		listSales.appendItem("ALL", "ALL");
	
		List<Object[]> vResultSales = selectQueryService.QuerySalesmanActive();
		if(CommonUtils.isNotEmpty(vResultSales)){
			for(Object[] aRsltSales : vResultSales){
				listSales.appendItem(aRsltSales[0].toString(),aRsltSales[1].toString());
			}
		}
		
		cmbSales.setValue(listSales.getItemAtIndex(0).getLabel());
		listSales.setSelectedItem(listSales.getItemAtIndex(0));

	
	
	
    	
	}
	

	private EventListener selectCabang() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCab.setValue(listCabang.getSelectedItem().getLabel());
				vCabang = listCabang.getSelectedItem().getValue().toString();
				cmbCab.close();
			}
		};
	}	
		
	private EventListener selectSales() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbSales.setValue(listSales.getSelectedItem().getLabel());
				vSales = listSales.getSelectedItem().getValue().toString();
				cmbSales.close();
			}
		};
	}

	
	public void onCheck$rdgJnsLap(Event event){
		if(rdSUM.isChecked() == true){
			rowSales.setVisible(false);
			rowSemester.setVisible(false);
			rowAmount.setVisible(false);
			rowNote.setVisible(true);
			rowNote1.setVisible(true);
			rowNote2.setVisible(true);
			
			rowBatasSem1.setVisible(true);
			rowBatasSem2.setVisible(false);
			
			
			
		} else {
			//if(rdSUM2.isChecked() == true){
			//	rowSales.setVisible(false);
			//	rowSemester.setVisible(true);				
			//	rowAmount.setVisible(false);
			//	rowNote.setVisible(false);
			//	rowNote1.setVisible(false);
			//	rowNote2.setVisible(false);
			//} else {
				if(rdSUM3.isChecked() == true){						
					rowSales.setVisible(true);
					rowSemester.setVisible(false);				
					rowAmount.setVisible(false);
					rowNote.setVisible(false);
					rowNote1.setVisible(false);
					rowNote2.setVisible(false);
					
					rowBatasSem1.setVisible(false);
					rowBatasSem2.setVisible(true);
				} else {				
					rowSales.setVisible(true);
					rowSemester.setVisible(false);
					rowAmount.setVisible(true);
					rowNote.setVisible(false);
					rowNote1.setVisible(false);
					rowNote2.setVisible(false);
					
					rowBatasSem1.setVisible(true);
					rowBatasSem2.setVisible(false);
				}
			//}
		}
		
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
		
				
		BigDecimal vNilai = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
		String vPotensialReal = "ALL";
		if (cmbPotensialReal.getSelectedItem().getValue() != null){
			vPotensialReal = (String) cmbPotensialReal.getSelectedItem().getValue();
		}
		
		String vProjectCat = "ALL";
		if (cmbProjectCat.getSelectedItem().getValue() != null){
			vProjectCat = (String) cmbProjectCat.getSelectedItem().getValue();
		}
		
		@SuppressWarnings("unused")
		String vSem = "ALL";
		if (StringUtils.isNotEmpty(rdgSem.getSelectedItem().getValue())) {
			vSem = rdgSem.getSelectedItem().getValue();	
		} 
		
		int vAkhirSem1 = 6;
		if (cmbAkhirSem1.getSelectedItem().getValue() != null){
			vAkhirSem1 = Integer.valueOf((String) cmbAkhirSem1.getSelectedItem().getValue());
		}
		
		int vAkhirSem2 = 12;
		if (cmbAkhirSem2.getSelectedItem().getValue() != null){
			vAkhirSem2 = Integer.valueOf((String) cmbAkhirSem2.getSelectedItem().getValue());
		}
		

		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04042_ForecastSummaryBySalesRev.jasper";
		
		String vJnsLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		

		String vStrTglFrom = vTahun+"/01/01";
		String vStrTglUpto = vTahun+"/12/31";
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		if(vJnsLap.equals("SUM")){
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0503005-01");
			
			
			@SuppressWarnings("unused")
			String vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", "SRF");

		}
	
		if(vJnsLap.equals("SUM3")){
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0503005-01");
			
			@SuppressWarnings("unused")
			String vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", "SRF-P");
		}
		
		
		
		param.put("Tahun",  vTahun);
		param.put("Potensi",  vPotensialReal); 
		param.put("Size",  vProjectCat); 
		param.put("Cabang",  vCabang); 
	
		
		if(vJnsLap.equals("SUM")){
			param.put("ProsesId",  vProsesId);
			param.put("Sem1Akhir",  vAkhirSem1);
			jasperRpt = "/solusi/hapis/webui/reports/sales/04042_ForecastSummaryBySalesRev.jasper";
		} else {
			if(vJnsLap.equals("SUM2")){
				//param.put("Sem",  vSem);
				
				//jasperRpt = "/solusi/hapis/webui/reports/sales/04045_ForecastSummaryBySalesRevPillar.jasper";
			} else { 
				if(vJnsLap.equals("SUM3")){
					param.put("Sales",  vSales);
					param.put("Sem1Akhir",  vAkhirSem2);
					param.put("ProsesId",  vProsesId);
					
					
					jasperRpt = "/solusi/hapis/webui/reports/sales/04046_ForecastSummaryBySalesRevPillar_2.jasper";
				} else {
					param.put("Sales",  vSales);
					param.put("Amt",  vNilai);
					param.put("Sem1Akhir",  vAkhirSem1);
					
					@SuppressWarnings("unused")
					String vSync = callStoreProcOrFuncService.callSyncAReport("0503005-02");
					
					if(vJnsLap.equals("DTL")){ 
						jasperRpt = "/solusi/hapis/webui/reports/sales/04043_ForecastDetailBySalesRev.jasper";
					} else {
						jasperRpt = "/solusi/hapis/webui/reports/sales/04044_ForecastDetailBySalesRev_2.jasper";
					}
				}
			}
			
		}
		
		
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
			
		}
		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", "DELETE");

		
	}
 
}

//