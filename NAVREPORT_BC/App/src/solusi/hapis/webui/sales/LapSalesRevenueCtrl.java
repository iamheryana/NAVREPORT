package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.tabel.model.T01managementadj;
import solusi.hapis.backend.tabel.service.T01managementadjService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapSalesRevenueCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;

		
	protected Radiogroup rdgJenis;	 
	protected Radio rdSR;
	protected Radio rdDTL;
	protected Radio rdSR2;
	protected Radio rdSR3;
	protected Radio rdSR4;
	protected Radio rdSR5;
	protected Radio rdSR6;
	protected Radio rdDTL2;	
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	private T01managementadjService T01managementadjService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	private String vProsesId;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
    	//dbTglTo.setValue((new Date()));   

    	Calendar cRTglTo = Calendar.getInstance();
    	cRTglTo.setTime(new Date());
    	cRTglTo.set(Calendar.DAY_OF_MONTH, cRTglTo.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglTo = cRTglTo.getTime();
    	dbTglTo.setValue(vTglTo); 
    	
    	
    	
    	
    	rdSR.setSelected(true);
    	rdPDF.setSelected(true); 
    	
    	vProsesId = String.valueOf(System.currentTimeMillis());
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOKNew(Event event) throws InterruptedException, ParseException {
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		Calendar cRTglTo = Calendar.getInstance();
    	cRTglTo.setTime(new Date());
    	cRTglTo.set(Calendar.DAY_OF_MONTH, cRTglTo.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglTo = cRTglTo.getTime();

    	
		
		String vStrTglFrom =  "1900-01-01";
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
			
			vStrTglFrom  = frmTgl.format(vTglFrom);  
			
		}   
 
		String vStrTglTo =  "1900-01-01";
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
			
			vStrTglTo  = frmTgl.format(vTglTo);  
		}
		
					

		String jasperRpt = "/solusi/hapis/webui/reports/sales/04017_LapSalesRevenue.jasper";
		String jasperRpt2 = "/solusi/hapis/webui/reports/sales/04018_LapSalesRevenueV2.jasper";
		String jasperRpt3 = "/solusi/hapis/webui/reports/sales/04019_LapSalesRevenueV3.jasper";
		String jasperRpt4 = "/solusi/hapis/webui/reports/sales/04020_LapSalesRevenueV4_2.jasper";
		String jasperRpt5 = "/solusi/hapis/webui/reports/sales/04030_LapSalesRevenueV5.jasper";
		String jasperRpt6 = "/solusi/hapis/webui/reports/sales/04019_LapSalesRevenueV3_PMP.jasper";
		String jasperRptList = "/solusi/hapis/webui/reports/sales/04021_LiatAdjustment.jasper";
		String jasperRptListSO = "/solusi/hapis/webui/reports/sales/04022_OutstandingSOSort.jasper";
			
		
		
		String vJenis = "SR";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vResult = "NON";
		
		if(	vJenis.equals("SR") == true ||
			vJenis.equals("SR2") == true ||
			vJenis.equals("SR3") == true ||
			vJenis.equals("SR4") == true ||
			vJenis.equals("SR5") == true ||
			vJenis.equals("SRPMP") == true 
				){
			
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0507005-01");
			
			vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglTo, "ALL", vJenis);

			param.put("ProsesId",  vProsesId); 
			param.put("TglInvFrom",  vTglFrom); 
			param.put("TglInvTo",  vTglTo);  
			param.put("Company",  "ALL");  
		} else {
			if(vJenis.equals("DTL") == true ){
				@SuppressWarnings("unused")
				String vSync = callStoreProcOrFuncService.callSyncAReport("0507005-02");
				
				
				param.put("l_tglfr",  vTglFrom); 
				param.put("l_tglto",  vTglTo);  
			} else {
				
				Calendar cTgl = Calendar.getInstance();		
				cTgl.setTime(vTglTo);
				int yearTgl = cTgl.get(Calendar.YEAR);
				
				@SuppressWarnings("unused")
				String vSync = callStoreProcOrFuncService.callSyncAReport("0507005-03");
				
				
				vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, (String.valueOf(yearTgl)+"-01-01"), (String.valueOf(yearTgl)+"-12-31"), "ALL", "ALL", "CETAK");

				param.put("TglInvFrom",  vTglFrom); 
				param.put("TglInvTo",  vTglTo);  
				param.put("ProsesId",  vProsesId);  
			}
		}
		
	
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vJenis.equals("SR")){
			if(vSaveAs.equals("PDF")){
				new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
			} else {
				new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
			}
		} else {
			if(vJenis.equals("SR2")) {
				if(vSaveAs.equals("PDF")){
					new JReportGeneratorWindow(param, jasperRpt2, "PDF"); 
				} else {
					new JReportGeneratorWindow(param, jasperRpt2, "XLS"); 
				}
			} else {
				if(vJenis.equals("SR3")){
					if(vSaveAs.equals("PDF")){
						new JReportGeneratorWindow(param, jasperRpt3, "PDF"); 
					} else {
						new JReportGeneratorWindow(param, jasperRpt3, "XLS"); 
					}
				} else {
					if(vJenis.equals("SR4")){
						if(vSaveAs.equals("PDF")){
							new JReportGeneratorWindow(param, jasperRpt4, "PDF"); 
						} else {
							new JReportGeneratorWindow(param, jasperRpt4, "XLS"); 
						}
					} else {
						if(vJenis.equals("SR5")){
							if(vSaveAs.equals("PDF")){
								new JReportGeneratorWindow(param, jasperRpt5, "PDF"); 
							} else {
								new JReportGeneratorWindow(param, jasperRpt5, "XLS"); 
							}
						} else {
							if(vJenis.equals("SRPMP")){
								if(vSaveAs.equals("PDF")){
									new JReportGeneratorWindow(param, jasperRpt6, "PDF"); 
								} else {
									new JReportGeneratorWindow(param, jasperRpt6, "XLS"); 
								}
							} else {
								if(vJenis.equals("DTL2")){
									if(vSaveAs.equals("PDF")){
										new JReportGeneratorWindow(param, jasperRptListSO, "PDF"); 
									} else {
										new JReportGeneratorWindow(param, jasperRptListSO, "XLS"); 
									}
									
									
									@SuppressWarnings("unused")
									String vDelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglTo, "ALL", "ALL", "DELETE");

								} else {
									if(vSaveAs.equals("PDF")){
										new JReportGeneratorWindow(param, jasperRptList, "PDF"); 
									} else {
										new JReportGeneratorWindow(param, jasperRptList, "XLS"); 
									}
								}
							}
						}
					}
				}
			}
		}


		if(	vJenis.equals("SR") == true ||
				vJenis.equals("SR2") == true ||
				vJenis.equals("SR3") == true ||
				vJenis.equals("SR4") == true ||
				vJenis.equals("SR5") == true ||
				vJenis.equals("SRPMP") == true 
					){
			@SuppressWarnings("unused")
			String vDelete = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglTo, "ALL", "DELETE");

		}
		
	}

	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		String dRFromUM = "1/1/1980";
		SimpleDateFormat dfRFromUM= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFromUM  = dfRFromUM.parse(dRFromUM);

		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
				
		/* PARAMETER KOREKSI MANAGEMENT ================================================================= start*/
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		parameterInput.put("tanggalfrom", vTglFrom);
		parameterInput.put("tanggalto", vTglTo);
		
		BigDecimal vJKT01 = new BigDecimal(0);
		BigDecimal vJKT02 = new BigDecimal(0);
		BigDecimal vJKT03 = new BigDecimal(0);
		BigDecimal vJKT04 = new BigDecimal(0);
		BigDecimal vJKT05 = new BigDecimal(0);
		BigDecimal vJKT06 = new BigDecimal(0);
		
		BigDecimal vCKR01 = new BigDecimal(0);
		BigDecimal vCKR02 = new BigDecimal(0);
		BigDecimal vCKR03 = new BigDecimal(0);
		BigDecimal vCKR04 = new BigDecimal(0);
		BigDecimal vCKR05 = new BigDecimal(0);
		BigDecimal vCKR06 = new BigDecimal(0);
		
		BigDecimal vSBY01 = new BigDecimal(0);
		BigDecimal vSBY02 = new BigDecimal(0);
		BigDecimal vSBY03 = new BigDecimal(0);
		BigDecimal vSBY04 = new BigDecimal(0);
		BigDecimal vSBY05 = new BigDecimal(0);
		BigDecimal vSBY06 = new BigDecimal(0);
		
		BigDecimal vSMR01 = new BigDecimal(0);
		BigDecimal vSMR02 = new BigDecimal(0);
		BigDecimal vSMR03 = new BigDecimal(0);
		BigDecimal vSMR04 = new BigDecimal(0);
		BigDecimal vSMR05 = new BigDecimal(0);
		BigDecimal vSMR06 = new BigDecimal(0);
		
		BigDecimal vDPS01 = new BigDecimal(0);
		BigDecimal vDPS02 = new BigDecimal(0);
		BigDecimal vDPS03 = new BigDecimal(0);
		BigDecimal vDPS04 = new BigDecimal(0);
		BigDecimal vDPS05 = new BigDecimal(0);
		BigDecimal vDPS06 = new BigDecimal(0);
		
		List<T01managementadj> tempListT01managementadj = T01managementadjService.getListT01managementadj(parameterInput);
		
		if(CommonUtils.isNotEmpty(tempListT01managementadj)){
			for(T01managementadj anData : tempListT01managementadj){
				if(CommonUtils.isNotEmpty(anData.getCabang())){
					
					if(anData.getCabang().equals("10")){
						if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
							vJKT01 = vJKT01.add(anData.getAmountHw01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
							vJKT02 = vJKT02.add(anData.getAmountPs01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
							vJKT03 = vJKT03.add(anData.getAmountPs02());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
							vJKT04 = vJKT04.add(anData.getAmountPs03());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
							vJKT05 = vJKT05.add(anData.getAmountPs04());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
							vJKT06 = vJKT06.add(anData.getAmountPs05());
						}
					}
					
					if(anData.getCabang().equals("15")){
						if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
							vSBY01 = vSBY01.add(anData.getAmountHw01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
							vSBY02 = vSBY02.add(anData.getAmountPs01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
							vSBY03 = vSBY03.add(anData.getAmountPs02());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
							vSBY04 = vSBY04.add(anData.getAmountPs03());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
							vSBY05 = vSBY05.add(anData.getAmountPs04());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
							vSBY06 = vSBY06.add(anData.getAmountPs05());
						}
					}
					
					if(anData.getCabang().equals("16")){
						if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
							vCKR01 = vCKR01.add(anData.getAmountHw01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
							vCKR02 = vCKR02.add(anData.getAmountPs01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
							vCKR03 = vCKR03.add(anData.getAmountPs02());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
							vCKR04 = vCKR04.add(anData.getAmountPs03());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
							vCKR05 = vCKR05.add(anData.getAmountPs04());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
							vCKR06 = vCKR06.add(anData.getAmountPs05());
						}
					}
					
					if(anData.getCabang().equals("17")){
						if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
							vSMR01 = vSMR01.add(anData.getAmountHw01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
							vSMR02 = vSMR02.add(anData.getAmountPs01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
							vSMR03 = vSMR03.add(anData.getAmountPs02());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
							vSMR04 = vSMR04.add(anData.getAmountPs03());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
							vSMR05 = vSMR05.add(anData.getAmountPs04());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
							vSMR06 = vSMR06.add(anData.getAmountPs05());
						}
					}
					
					if(anData.getCabang().equals("19")){
						if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
							vDPS01 = vDPS01.add(anData.getAmountHw01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
							vDPS02 = vDPS02.add(anData.getAmountPs01());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
							vDPS03 = vDPS03.add(anData.getAmountPs02());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
							vDPS04 = vDPS04.add(anData.getAmountPs03());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
							vDPS05 = vDPS05.add(anData.getAmountPs04());
						}
						
						if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
							vDPS06 = vDPS06.add(anData.getAmountPs05());
						}
					}
					
					
					
				}
				
				
			}
		}
		/* PARAMETER KOREKSI MANAGEMENT ================================================================= END*/
		
		
		/* PARAMETER KOREKSI MANAGEMENT UANG MUKA================================================================= start*/
		Map<Object, Object> parameterInputUM = new HashMap<Object, Object>();		
		
		parameterInputUM.put("tanggalfrom", vTglFromUM);
		parameterInputUM.put("tanggalto", vTglTo);
		
		BigDecimal vJKT01um = new BigDecimal(0);
		BigDecimal vJKT02um = new BigDecimal(0);
		BigDecimal vJKT03um = new BigDecimal(0);
		BigDecimal vJKT04um = new BigDecimal(0);
		BigDecimal vJKT05um = new BigDecimal(0);
		BigDecimal vJKT06um = new BigDecimal(0);
		
		BigDecimal vCKR01um = new BigDecimal(0);
		BigDecimal vCKR02um = new BigDecimal(0);
		BigDecimal vCKR03um = new BigDecimal(0);
		BigDecimal vCKR04um = new BigDecimal(0);
		BigDecimal vCKR05um = new BigDecimal(0);
		BigDecimal vCKR06um = new BigDecimal(0);
		
		BigDecimal vSBY01um = new BigDecimal(0);
		BigDecimal vSBY02um = new BigDecimal(0);
		BigDecimal vSBY03um = new BigDecimal(0);
		BigDecimal vSBY04um = new BigDecimal(0);
		BigDecimal vSBY05um = new BigDecimal(0);
		BigDecimal vSBY06um = new BigDecimal(0);
		
		BigDecimal vSMR01um = new BigDecimal(0);
		BigDecimal vSMR02um = new BigDecimal(0);
		BigDecimal vSMR03um = new BigDecimal(0);
		BigDecimal vSMR04um = new BigDecimal(0);
		BigDecimal vSMR05um = new BigDecimal(0);
		BigDecimal vSMR06um = new BigDecimal(0);
		
		BigDecimal vDPS01um = new BigDecimal(0);
		BigDecimal vDPS02um = new BigDecimal(0);
		BigDecimal vDPS03um = new BigDecimal(0);
		BigDecimal vDPS04um = new BigDecimal(0);
		BigDecimal vDPS05um = new BigDecimal(0);
		BigDecimal vDPS06um = new BigDecimal(0);
		
		List<T01managementadj> tempListT01managementadjUM = T01managementadjService.getListT01managementadj(parameterInputUM);
		
		if(CommonUtils.isNotEmpty(tempListT01managementadjUM)){
			for(T01managementadj anData : tempListT01managementadjUM){
				if(CommonUtils.isNotEmpty(anData.getCabang())){
					
					BigDecimal vSum = new BigDecimal(0);
					BigDecimal vHW01 = new BigDecimal(0);
					BigDecimal vPS01 = new BigDecimal(0);
					BigDecimal vPS02 = new BigDecimal(0);
					BigDecimal vPS03 = new BigDecimal(0);
					BigDecimal vPS04 = new BigDecimal(0);
					BigDecimal vPS05 = new BigDecimal(0);					
					
					if (CommonUtils.isNotEmpty(anData.getAmountHw01())){
						vHW01 = anData.getAmountHw01();
					}
					
					if (CommonUtils.isNotEmpty(anData.getAmountPs01())){
						vPS01 = anData.getAmountPs01();
					}
					
					if (CommonUtils.isNotEmpty(anData.getAmountPs02())){
						vPS02 = anData.getAmountPs02();
					}
					
					if (CommonUtils.isNotEmpty(anData.getAmountPs03())){
						vPS03 = anData.getAmountPs03();
					}
					
					if (CommonUtils.isNotEmpty(anData.getAmountPs04())){
						vPS04 = anData.getAmountPs04();
					}
					
					if (CommonUtils.isNotEmpty(anData.getAmountPs05())){
						vPS05 = anData.getAmountPs05();
					}
					
											
					vSum = vHW01.add(vPS01).add(vPS02).add(vPS03).add(vPS04).add(vPS05);

					BigDecimal vNol = new BigDecimal(0);
					
					if(vSum.equals(vNol) == false){
											
						if(anData.getCabang().equals("10")){
							
							if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
								vJKT01um = vJKT01um.add(anData.getAmountHw01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
								vJKT02um = vJKT02um.add(anData.getAmountPs01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
								vJKT03um = vJKT03um.add(anData.getAmountPs02());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
								vJKT04um = vJKT04um.add(anData.getAmountPs03());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
								vJKT05um = vJKT05um.add(anData.getAmountPs04());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
								vJKT06um = vJKT06um.add(anData.getAmountPs05());
							}
						}
						
						if(anData.getCabang().equals("15")){
							if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
								vSBY01um = vSBY01um.add(anData.getAmountHw01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
								vSBY02um = vSBY02um.add(anData.getAmountPs01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
								vSBY03um = vSBY03um.add(anData.getAmountPs02());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
								vSBY04um = vSBY04um.add(anData.getAmountPs03());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
								vSBY05um = vSBY05um.add(anData.getAmountPs04());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
								vSBY06um = vSBY06um.add(anData.getAmountPs05());
							}
						}
						
						if(anData.getCabang().equals("16")){
							if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
								vCKR01um = vCKR01um.add(anData.getAmountHw01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
								vCKR02um = vCKR02um.add(anData.getAmountPs01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
								vCKR03um = vCKR03um.add(anData.getAmountPs02());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
								vCKR04um = vCKR04um.add(anData.getAmountPs03());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
								vCKR05um = vCKR05um.add(anData.getAmountPs04());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
								vCKR06um = vCKR06um.add(anData.getAmountPs05());
							}
						}
						
						if(anData.getCabang().equals("17")){
							if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
								vSMR01um = vSMR01um.add(anData.getAmountHw01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
								vSMR02um = vSMR02um.add(anData.getAmountPs01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
								vSMR03um = vSMR03um.add(anData.getAmountPs02());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
								vSMR04um = vSMR04um.add(anData.getAmountPs03());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
								vSMR05um = vSMR05um.add(anData.getAmountPs04());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
								vSMR06um = vSMR06um.add(anData.getAmountPs05());
							}
						}
						
						if(anData.getCabang().equals("19")){
							if(CommonUtils.isNotEmpty(anData.getAmountHw01())){
								vDPS01um = vDPS01um.add(anData.getAmountHw01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs01())){
								vDPS02um = vDPS02um.add(anData.getAmountPs01());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs02())){
								vDPS03um = vDPS03um.add(anData.getAmountPs02());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs03())){
								vDPS04um = vDPS04um.add(anData.getAmountPs03());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs04())){
								vDPS05um = vDPS05um.add(anData.getAmountPs04());
							}
							
							if(CommonUtils.isNotEmpty(anData.getAmountPs05())){
								vDPS06um = vDPS06um.add(anData.getAmountPs05());
							}
						}
					
					}
					
				}
				
				
			}
		}
		/* PARAMETER KOREKSI MANAGEMENT UANG MUKA ================================================================= END*/
		String jasperRpt = "/solusi/hapis/webui/reports/sales/LapSalesRevenue.jasper";
		String jasperRpt2 = "/solusi/hapis/webui/reports/sales/LapSalesRevenueV2.jasper";
		String jasperRpt3 = "/solusi/hapis/webui/reports/sales/LapSalesRevenueV3.jasper";
		String jasperRpt4 = "/solusi/hapis/webui/reports/sales/LapSalesRevenueV4_2.jasper";
		String jasperRptList = "/solusi/hapis/webui/reports/tabel/l_t01managementadj.jasper";
		String jasperRptListSO = "/solusi/hapis/webui/reports/sales/OutstandingSOSort.jasper";
		


		
		
		String vJenis = "SR";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 
		
		
		if(vJenis.equals("DTL") == false ){
			param.put("TglInvFrom",  vTglFrom); 
			param.put("TglInvTo",  vTglTo);  
			param.put("Company",  "ALL");  
			
			param.put("aJKT01",  vJKT01);  
			param.put("aJKT02",  vJKT02);  
			param.put("aJKT03",  vJKT03);  
			param.put("aJKT04",  vJKT04);  
			param.put("aJKT05",  vJKT05);  
			param.put("aJKT06",  vJKT06);  
			
			param.put("aSBY01",  vSBY01);  
			param.put("aSBY02",  vSBY02);  
			param.put("aSBY03",  vSBY03);  
			param.put("aSBY04",  vSBY04);  
			param.put("aSBY05",  vSBY05);  
			param.put("aSBY06",  vSBY06);  
			
			param.put("aSMR01",  vSMR01);  
			param.put("aSMR02",  vSMR02);  
			param.put("aSMR03",  vSMR03);  
			param.put("aSMR04",  vSMR04);  
			param.put("aSMR05",  vSMR05);  
			param.put("aSMR06",  vSMR06);  
			
			param.put("aCKR01",  vCKR01);  
			param.put("aCKR02",  vCKR02);  
			param.put("aCKR03",  vCKR03);  
			param.put("aCKR04",  vCKR04);  
			param.put("aCKR05",  vCKR05);  
			param.put("aCKR06",  vCKR06); 
			
			param.put("aDPS01",  vDPS01);  
			param.put("aDPS02",  vDPS02);  
			param.put("aDPS03",  vDPS03);  
			param.put("aDPS04",  vDPS04);  
			param.put("aDPS05",  vDPS05);  
			param.put("aDPS06",  vDPS06); 
			
			
			param.put("aJKT01um",  vJKT01um);  
			param.put("aJKT02um",  vJKT02um);  
			param.put("aJKT03um",  vJKT03um);  
			param.put("aJKT04um",  vJKT04um);  
			param.put("aJKT05um",  vJKT05um);  
			param.put("aJKT06um",  vJKT06um);  
			
			param.put("aSBY01um",  vSBY01um);  
			param.put("aSBY02um",  vSBY02um);  
			param.put("aSBY03um",  vSBY03um);  
			param.put("aSBY04um",  vSBY04um);  
			param.put("aSBY05um",  vSBY05um);  
			param.put("aSBY06um",  vSBY06um);  
			
			param.put("aSMR01um",  vSMR01um);  
			param.put("aSMR02um",  vSMR02um);  
			param.put("aSMR03um",  vSMR03um);  
			param.put("aSMR04um",  vSMR04um);  
			param.put("aSMR05um",  vSMR05um);  
			param.put("aSMR06um",  vSMR06um);  
			
			param.put("aCKR01um",  vCKR01um);  
			param.put("aCKR02um",  vCKR02um);  
			param.put("aCKR03um",  vCKR03um);  
			param.put("aCKR04um",  vCKR04um);  
			param.put("aCKR05um",  vCKR05um);  
			param.put("aCKR06um",  vCKR06um); 
			
			param.put("aDPS01um",  vDPS01um);  
			param.put("aDPS02um",  vDPS02um);  
			param.put("aDPS03um",  vDPS03um);  
			param.put("aDPS04um",  vDPS04um);  
			param.put("aDPS05um",  vDPS05um);  
			param.put("aDPS06um",  vDPS06um);  
			
		} else {
			if(vJenis.equals("DTL2") == true){
				param.put("TglInvFrom",  vTglFrom); 
				param.put("TglInvTo",  vTglTo);  
				param.put("Company",  "ALL");  
			} else {
				param.put("l_tglfr",  vTglFrom); 
				param.put("l_tglto",  vTglTo);  
			}
		}
		
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		if(vJenis.equals("SR")){
			if(vSaveAs.equals("PDF")){
				new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
			} else {
				new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
			}
		} else {
			if(vJenis.equals("SR2")){
				if(vSaveAs.equals("PDF")){
					new JReportGeneratorWindow(param, jasperRpt2, "PDF"); 
				} else {
					new JReportGeneratorWindow(param, jasperRpt2, "XLS"); 
				}
			} else {
				if(vJenis.equals("SR3")){
					if(vSaveAs.equals("PDF")){
						new JReportGeneratorWindow(param, jasperRpt3, "PDF"); 
					} else {
						new JReportGeneratorWindow(param, jasperRpt3, "XLS"); 
					}
				} else {
					if(vJenis.equals("SR4")){
						if(vSaveAs.equals("PDF")){
							new JReportGeneratorWindow(param, jasperRpt4, "PDF"); 
						} else {
							new JReportGeneratorWindow(param, jasperRpt4, "XLS"); 
						}
					} else {
						if(vJenis.equals("DTL2")){
							if(vSaveAs.equals("PDF")){
								new JReportGeneratorWindow(param, jasperRptListSO, "PDF"); 
							} else {
								new JReportGeneratorWindow(param, jasperRptListSO, "XLS"); 
							}
						} else {
							if(vSaveAs.equals("PDF")){
								new JReportGeneratorWindow(param, jasperRptList, "PDF",1); 
							} else {
								new JReportGeneratorWindow(param, jasperRptList, "XLS",1); 
							}
						}					
					}
				}
			}
		}
		
		
	}

	public T01managementadjService getT01managementadjService() {
		return T01managementadjService;
	}


	public void setT01managementadjService(
			T01managementadjService t01managementadjService) {
		T01managementadjService = t01managementadjService;
	}
 
	
}

