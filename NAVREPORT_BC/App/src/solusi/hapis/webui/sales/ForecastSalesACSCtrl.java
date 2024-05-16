package solusi.hapis.webui.sales;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ForecastSalesACSCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	
	
	
	protected Row rowTglUpto;
	protected Row rowBatasSem;
	protected Row rowSemester;
	protected Row rowSpvBy;
	protected Row rowAmount;
	protected Row rowCabang;
	protected Row rowApplication;
	protected Row rowTipeLap;
	
	protected Row rowWeight;
	protected Row rowWeight2;
	protected Row rowWeight3;
	
	protected Row rowSales;
	
	protected Datebox dbTglUpto;
	
	protected Radiogroup rdgSem;	 
	protected Radio rdSemALL;
	protected Radio rdSem1;
	protected Radio rdSem2;
	
	protected Intbox  intTahun;
	protected Textbox txtSPVBy;
			
	protected Radiogroup rdgTipeLap;	 
	protected Radio rdDTL5_1;
	protected Radio rdDTL5_2;	
	//protected Radio rdDTL5_3;
	protected Radio rdDTL5_4;
			
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSUM;
	protected Radio rdDTL1;
	protected Radio rdDTL2;
	protected Radio rdDTL3;
	protected Radio rdDTL4;
	protected Radio rdDTL5;
	protected Radio rdDTL6;
	protected Radio rdSUM2;
	protected Radio rdSUM3;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Decimalbox dcmNilai;
	
	protected Decimalbox dcmHigh;
	protected Decimalbox dcmMed;
	protected Decimalbox dcmLow;
	
	protected Combobox  cmbAkhirSem1;
	
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	
	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = "ALL";
	
	
	protected Bandbox  cmbApplication;
	protected Listbox listApplication;
	protected String vApplication = "ALL";
	
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	private String vProsesId;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		rowTipeLap.setVisible(false);
		
		rowTglUpto.setVisible(true);
		rowBatasSem.setVisible(true);		
		rowSemester.setVisible(false);
		rowSpvBy.setVisible(true);
		rowAmount.setVisible(true);
		
		rowWeight.setVisible(true);
		rowWeight2.setVisible(true);
		rowWeight3.setVisible(true);

		rdDTL5_1.setSelected(true); 
		
		rdSemALL.setSelected(true); 
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);
    	
		rdXLS.setSelected(true); 
				
		rdSUM.setSelected(true); 
		
		
		
		Calendar cRTglUpto = Calendar.getInstance();
		cRTglUpto.setTime(new Date());
		cRTglUpto.set(Calendar.DAY_OF_MONTH, cRTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cRTglUpto.getTime();		
		dbTglUpto.setValue(vTglUpto);   
		
		
		dcmNilai.setValue(new BigDecimal (500));
		
		dcmHigh.setValue(new BigDecimal (90));
		dcmMed.setValue(new BigDecimal (60));
		dcmLow.setValue(new BigDecimal (30));
		
		
		
		cmbAkhirSem1.setSelectedIndex(3);
		
		
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
			listSales.setAutopaging(true);
			listSales.setWidth("250px");
			listSales.addEventListener(Events.ON_SELECT, selectSales());
			listSales.setParent(popup2);
			popup2.setParent(cmbSales);
	        
		listSales.appendItem("ALL", "ALL");
		
		List<Object[]> vResultSales = selectQueryService.QuerySalesmanActive();
		if(CommonUtils.isNotEmpty(vResultSales)){
			for(Object[] aRslt : vResultSales){
				listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
	
	
		cmbSales.setValue(listSales.getItemAtIndex(0).getLabel());
		listSales.setSelectedItem(listSales.getItemAtIndex(0));
		
		Bandpopup popup3 = new Bandpopup();
			listApplication = new Listbox();
			listApplication.setMold("paging");
			listApplication.setAutopaging(false);
			listApplication.setWidth("400px");
			listApplication.addEventListener(Events.ON_SELECT, selectApplication());
			listApplication.setParent(popup3);
			popup3.setParent(cmbApplication);
	        
		listApplication.appendItem("ALL", "ALL");
			
		List<Object[]> vResultApplication = selectQueryService.QueryApplication();
		if(CommonUtils.isNotEmpty(vResultApplication)){
			for(Object[] aRslt : vResultApplication){
				listApplication.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbApplication.setValue(listApplication.getItemAtIndex(0).getLabel());
		listApplication.setSelectedItem(listApplication.getItemAtIndex(0));
	}
	
	private EventListener selectApplication() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbApplication.setValue(listApplication.getSelectedItem().getLabel());
				vApplication = listApplication.getSelectedItem().getValue().toString();
				cmbApplication.close();
			}
		};
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
		if(rdSUM.isChecked() == true ) {
			
			rowTipeLap.setVisible(false);
			
			rowTglUpto.setVisible(true);
			rowBatasSem.setVisible(true);		
			rowSemester.setVisible(false);
			rowSpvBy.setVisible(true);
			rowAmount.setVisible(true);
			rowCabang.setVisible(false);
			
			
			rowWeight.setVisible(true);
			rowWeight2.setVisible(true);
			rowWeight3.setVisible(true);
			
			rowSales.setVisible(false);
			rowApplication.setVisible(false);
			
		} else {
			if(rdDTL1.isChecked() == true) {
				rowTipeLap.setVisible(false);
				
				rowTglUpto.setVisible(false);
				rowBatasSem.setVisible(false);		
				rowSemester.setVisible(false);
				rowSpvBy.setVisible(false);
				rowAmount.setVisible(false);
				rowCabang.setVisible(false);
				
				rowWeight.setVisible(false);
				rowWeight2.setVisible(false);
				rowWeight3.setVisible(false);
				
				rowSales.setVisible(false);
				rowApplication.setVisible(false);
			} else {
				if(rdDTL2.isChecked() == true || rdDTL5.isChecked() == true) {
					if(rdDTL5.isChecked() == true){ 
						
						rowWeight.setVisible(true);
						rowWeight2.setVisible(true);
						rowWeight3.setVisible(true);
						
						
						rowTipeLap.setVisible(true);
					} else {
						
						rowWeight.setVisible(false);
						rowWeight2.setVisible(false);
						rowWeight3.setVisible(false);
						
						
						rowTipeLap.setVisible(false);
					}
					
					
					rowTglUpto.setVisible(false);
					rowBatasSem.setVisible(true);		
					rowSemester.setVisible(false);
					if (rdDTL5_1.isChecked() == true) {
						rowSpvBy.setVisible(false);
					} else {
						if (rdDTL5_2.isChecked() == true) {
							rowSpvBy.setVisible(false);
						} else {
							if (rdDTL5_4.isChecked() == true) {
								rowSpvBy.setVisible(true);
							}
						}
					}
					rowAmount.setVisible(true);
					rowCabang.setVisible(true);

					rowSales.setVisible(false);
					rowApplication.setVisible(true);
				} else {					
					if(rdDTL4.isChecked() == true) {
						rowTipeLap.setVisible(false);
						
						rowTglUpto.setVisible(false);
						rowBatasSem.setVisible(true);		
						rowSemester.setVisible(false);
						rowSpvBy.setVisible(true);
						rowAmount.setVisible(true);
						rowCabang.setVisible(false);
						
						rowWeight.setVisible(true);
						rowWeight2.setVisible(true);
						rowWeight3.setVisible(true);
						
						rowSales.setVisible(true);
						rowApplication.setVisible(true);
					} else {
						if(rdDTL3.isChecked() == true) {
							rowTipeLap.setVisible(false);
							
							rowTglUpto.setVisible(false);
							rowBatasSem.setVisible(true);		
							rowSemester.setVisible(true);
							rowSpvBy.setVisible(false);
							rowAmount.setVisible(true);
							rowCabang.setVisible(true);
							
							rowWeight.setVisible(false);
							rowWeight2.setVisible(false);
							rowWeight3.setVisible(false);
							
							rowSales.setVisible(false);
							rowApplication.setVisible(false);
							
						} else {
							if(rdSUM2.isChecked() == true) {
								rowTipeLap.setVisible(false);
								
								rowTglUpto.setVisible(false);
								rowBatasSem.setVisible(true);		
								rowSemester.setVisible(true);
								rowSpvBy.setVisible(false);
								rowAmount.setVisible(true);
								rowCabang.setVisible(false);
								
								rowWeight.setVisible(false);
								rowWeight2.setVisible(false);
								rowWeight3.setVisible(false);
								
								rowSales.setVisible(false);
								rowApplication.setVisible(false);
							} else {
								if(rdSUM3.isChecked() == true){
									rowTipeLap.setVisible(false);
									
									rowTglUpto.setVisible(true);
									rowBatasSem.setVisible(true);		
									rowSemester.setVisible(false);
									rowSpvBy.setVisible(false);
									rowAmount.setVisible(true);
									rowCabang.setVisible(false);
									
									rowWeight.setVisible(true);
									rowWeight2.setVisible(true);
									rowWeight3.setVisible(true);
									
									rowSales.setVisible(false);
									rowApplication.setVisible(false);
								} else {
									if(rdDTL6.isChecked() == true){
										rowTipeLap.setVisible(false);
										
										rowTglUpto.setVisible(false);
										rowBatasSem.setVisible(true);		
										rowSemester.setVisible(false);
										rowSpvBy.setVisible(true);
										rowAmount.setVisible(true);
										rowCabang.setVisible(false);
										
										rowWeight.setVisible(true);
										rowWeight2.setVisible(true);
										rowWeight3.setVisible(true);
										
										rowSales.setVisible(true);
										rowApplication.setVisible(true);
									}
								}
								
							}
						}
					}
		
				}
			}
		}
			
			
		
	}
	
	public void onCheck$rdgTipeLap(Event event){
		if (rdDTL5_1.isChecked() == true) {
			rowSpvBy.setVisible(false);
		} else {
			if (rdDTL5_2.isChecked() == true) {
				rowSpvBy.setVisible(false);
			} else {
				if (rdDTL5_4.isChecked() == true) {
					rowSpvBy.setVisible(true);
				}
			}
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
		
		
		BigDecimal vHigh = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmHigh.getValue())) {
			vHigh = dcmHigh.getValue();
		} 
		
		BigDecimal vMed = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmMed.getValue())) {
			vMed = dcmMed.getValue();
		} 
		
		BigDecimal vLow = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmLow.getValue())) {
			vLow = dcmLow.getValue();
		} 
		
		
		
		String vSPVBy = "ALL";
		if (StringUtils.isNotEmpty(txtSPVBy.getValue())) {
			vSPVBy = txtSPVBy.getValue();
		} 
		
	
		int vAkhirSem1 = 6;
		if (cmbAkhirSem1.getSelectedItem().getValue() != null){
			vAkhirSem1 = Integer.valueOf((String) cmbAkhirSem1.getSelectedItem().getValue());
		}
		
		String vSem = "ALL";
		if (StringUtils.isNotEmpty(rdgSem.getSelectedItem().getValue())) {
			vSem = rdgSem.getSelectedItem().getValue();	
		} 
		
		
		Calendar cRTglUpto = Calendar.getInstance();
		cRTglUpto.setTime(new Date());
		cRTglUpto.set(Calendar.DAY_OF_MONTH, cRTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpTo = cRTglUpto.getTime();		

		String vStrTglTo =  "1900-01-01";
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
			
			vStrTglTo  = frmTgl.format(vTglUpTo);  
		}
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTglUpTo);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String vStrTglFrom = yearTglFrom+"/01/01";
		
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04047_01_ForecastSalesACS.jasper";
		
		String vJnsLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		String vStrOutSOFrom = String.valueOf(vTahun)+"/01/01";
		String vStrOutSOUpto = String.valueOf(vTahun)+"/12/31";
		
		
		
		param.put("Tahun",  vTahun);
			
		
		if(vJnsLap.equals("SUM")){
			vProsesId = String.valueOf(System.currentTimeMillis());
			
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0503006-01");
			
			@SuppressWarnings("unused")
			String vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglTo, vStrTglTo, "ALL", "SRF");

			
			param.put("SpvBy",  vSPVBy.toUpperCase());
			param.put("AmtBig",  vNilai);
			param.put("Sem1Akhir",  vAkhirSem1);			
			param.put("TglUpto",  vTglUpTo);
			
			param.put("weightH",  vHigh);
			param.put("weightM",  vMed);
			param.put("weightL",  vLow);
			
			param.put("ProsesId",  vProsesId);
			
			jasperRpt = "/solusi/hapis/webui/reports/sales/04047_01_ForecastSalesACS.jasper";
		} else {
			if(vJnsLap.equals("DTL1")){				
				vProsesId = String.valueOf(System.currentTimeMillis());
				
				
				@SuppressWarnings("unused")
				String vSync = callStoreProcOrFuncService.callSyncAReport("0503006-02");
				
				@SuppressWarnings("unused")
				String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrOutSOFrom, vStrOutSOUpto, "ALL", "ALL", "CETAK");
				
				
				param.put("ProsesId",  vProsesId);
				
				jasperRpt = "/solusi/hapis/webui/reports/sales/04047_02_ForecastSalesACSOutSO.jasper";
			} else {
				if(vJnsLap.equals("DTL2")){		
					@SuppressWarnings("unused")
					String vSync = callStoreProcOrFuncService.callSyncAReport("0503006-03");	
					
					
					
					param.put("SpvBy",  vSPVBy.toUpperCase());
					param.put("AmtBig",  vNilai);
					param.put("Sem1Akhir",  vAkhirSem1);	
					param.put("App",  vApplication);	
					
					jasperRpt = "/solusi/hapis/webui/reports/sales/04047_03_ForecastSalesACSPipeline.jasper";
				} else {
					if(vJnsLap.equals("DTL3")){		
						@SuppressWarnings("unused")
						String vSync = callStoreProcOrFuncService.callSyncAReport("0503006-03");	
						
						
						param.put("AmtBig",  vNilai);
						param.put("Sem",  vSem);
						param.put("Sem1Akhir",  vAkhirSem1);
						param.put("Cabang",  vCabang); 
						
						jasperRpt = "/solusi/hapis/webui/reports/sales/04047_04_ForecastSalesACS4Pillar.jasper";
					} else {
						if(vJnsLap.equals("DTL4") || vJnsLap.equals("DTL6")){		
							param.put("SpvBy",  vSPVBy.toUpperCase());
							param.put("AmtBig",  vNilai);
							param.put("Sem1Akhir",  vAkhirSem1);
							
							param.put("Sales",  vSales);
							
							param.put("weightH",  vHigh);
							param.put("weightM",  vMed);
							param.put("weightL",  vLow);
							
							param.put("App",  vApplication);	
							
							@SuppressWarnings("unused")
							String vSync = callStoreProcOrFuncService.callSyncAReport("0503006-03");	
							
							
							if(vJnsLap.equals("DTL4") == true ){
								jasperRpt = "/solusi/hapis/webui/reports/sales/04047_05_ForecastSalesACSDetailBSO.jasper";
							} else {								
								jasperRpt = "/solusi/hapis/webui/reports/sales/04047_08_ForecastSalesACSGlobalBSO.jasper";
							}
						} else {
							if (vJnsLap.equals("DTL5")){		
								
								
								param.put("SpvBy",  vSPVBy.toUpperCase());
								param.put("AmtBig",  vNilai);
								param.put("Sem1Akhir",  vAkhirSem1);	
								param.put("Cabang",  vCabang);	
								
								param.put("weightH",  vHigh);
								param.put("weightM",  vMed);
								param.put("weightL",  vLow);
								
								param.put("App",  vApplication);	
								
								@SuppressWarnings("unused")
								String vSync = callStoreProcOrFuncService.callSyncAReport("0503006-04");	
								
								String vTipeLap = "DTL5_1";
								if (StringUtils.isNotEmpty(rdgTipeLap.getSelectedItem().getValue())) {
									vTipeLap = rdgTipeLap.getSelectedItem().getValue();	
								} 
								
								if (vTipeLap.equals("DTL5_1")){	
									param.put("SpvBy",  "ALL");
									jasperRpt = "/solusi/hapis/webui/reports/sales/04047_03_03_ForecastSalesACSPipeline_BySales.jasper";
								} else if (vTipeLap.equals("DTL5_2")){	
									//jasperRpt = "/solusi/hapis/webui/reports/sales/04047_03_02_ForecastSalesACSPipeline_V2.jasper";
									jasperRpt = "/solusi/hapis/webui/reports/sales/04047_03_02_ForecastSalesACSPipeline_ByCustomerBySales.jasper";
								//} else if (vTipeLap.equals("DTL5_3")){	
								//	jasperRpt = "/solusi/hapis/webui/reports/sales/04047_03_04_ForecastSalesACSPipeline_V4.jasper";
								} else {
									//jasperRpt = "/solusi/hapis/webui/reports/sales/04047_03_05_ForecastSalesACSPipeline_V5.jasper";
									jasperRpt = "/solusi/hapis/webui/reports/sales/04047_03_05_ForecastSalesACSPipeline_ByCustomerBySpvDetail.jasper";
								
								}
							} else {
								if(vJnsLap.equals("SUM2")){		
									@SuppressWarnings("unused")
									String vSync = callStoreProcOrFuncService.callSyncAReport("0503006-03");
									
									param.put("Sem",  vSem);
									param.put("AmtBig",  vNilai);
									param.put("Sem1Akhir",  vAkhirSem1);
									
									jasperRpt = "/solusi/hapis/webui/reports/sales/04047_06_ForecastSalesACSSum.jasper";
								} else {
									if(vJnsLap.equals("SUM3")){
										
										vProsesId = String.valueOf(System.currentTimeMillis());
										
										@SuppressWarnings("unused")
										String vSync = callStoreProcOrFuncService.callSyncAReport("0503006-01");
										
										@SuppressWarnings("unused")
										String vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglTo, "ALL", "SR2");

										
										param.put("SpvBy",  vSPVBy.toUpperCase());
										param.put("AmtBig",  vNilai);
										param.put("Sem1Akhir",  vAkhirSem1);			
										param.put("TglUpto",  vTglUpTo);
										
										param.put("weightH",  vHigh);
										param.put("weightM",  vMed);
										param.put("weightL",  vLow);
										
										param.put("ProsesId",  vProsesId);
										
										jasperRpt = "/solusi/hapis/webui/reports/sales/04047_07_ForecastSalesACSByBranch.jasper";
									}
								}
							}
							
						}
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
		
		if( vJnsLap.equals("SUM") ||
			vJnsLap.equals("SUM3")){
			@SuppressWarnings("unused")
			String vDelete = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglTo, vStrTglTo, "ALL", "DELETE");
		}
		
		if(vJnsLap.equals("DTL1")){	
			@SuppressWarnings("unused")
			String vDelete =  callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrOutSOFrom, vStrOutSOUpto, "ALL", "ALL", "DELETE");
		}

	}
 
}

//