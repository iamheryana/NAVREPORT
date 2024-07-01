package solusi.hapis.webui.sales;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class OutstandingSORealisasiCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	

	protected Radiogroup rdgLaporan;	 
	protected Radio rdDTL;
	protected Radio rdSUM;
	protected Radio rdSUM2;
	protected Radio rdDTLUM;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	

	protected Row rowAmount;	
	protected Row rowTglUpto;
	
	protected Decimalbox dcmNilai;
	protected Datebox dbTglInvUpto;
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	rdSUM2.setSelected(true); 
    	
    	dcmNilai.setValue(new BigDecimal (200));
    	
    	
    	Calendar cRTglInvTo = Calendar.getInstance();
    	cRTglInvTo.setTime(new Date());
    	cRTglInvTo.set(Calendar.DAY_OF_MONTH, cRTglInvTo.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglInvTo = cRTglInvTo.getTime();
		dbTglInvUpto.setValue(vTglInvTo); 
    	


    	Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		int monthTgl = cTgl.get(Calendar.MONTH);
		monthTgl = monthTgl+1;	
				
		String dRFrom = "1/"+monthTgl+"/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		dbTglUpto.setValue(vTglUpto);  
		
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
	
	
	public void onCheck$rdgLaporan(Event event){
		if(rdSUM2.isChecked() == true ) {
			rowAmount.setVisible(true);	
			rowTglUpto.setVisible(true);
		} else {
			rowAmount.setVisible(false);
			rowTglUpto.setVisible(false);
		}		
	}
	
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0507007-01");
		
		Messagebox.show("Sync Sudah Selesai");
	}
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		String vJnsLap = "Y";
		if (StringUtils.isNotEmpty(rdgLaporan.getSelectedItem().getValue())) {
			vJnsLap = rdgLaporan.getSelectedItem().getValue();	
		} 
		
		
		Calendar cTgl= Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		int monthTgl = cTgl.get(Calendar.MONTH);
		monthTgl = monthTgl+1;	
		
		String dRFrom = "1/"+monthTgl+"/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTglFrom);		
		cTglFrom.set(Calendar.DAY_OF_MONTH, cTglFrom.getActualMinimum(Calendar.DAY_OF_MONTH));		
		Date vTglFromParam  = cTglFrom.getTime();
		
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}   
		
		Calendar cTglUpto = Calendar.getInstance();		
		cTglUpto.setTime(vTglUpto);		
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));		
		Date vTglUptoParam  = cTglUpto.getTime();
		
		
		
		Calendar cTglInvFrom= Calendar.getInstance();		
		cTglInvFrom.setTime(vTglFromParam);
		int yearTglInvFrom = cTgl.get(Calendar.YEAR);
		
		String dRTglInvFrom = "1/1/"+yearTglInvFrom;
		SimpleDateFormat dfRTglInvFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglInvFrom  = dfRTglInvFrom.parse(dRTglInvFrom);
		
		
    	Calendar cRTglInvTo = Calendar.getInstance();
    	cRTglInvTo.setTime(new Date());
    	cRTglInvTo.set(Calendar.DAY_OF_MONTH, cRTglInvTo.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglInvTo = cRTglInvTo.getTime();
	
		if(CommonUtils.isNotEmpty(dbTglInvUpto.getValue()) == true){  
			vTglInvTo = dbTglInvUpto.getValue();
		} 
		
		BigDecimal vNilai = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
		
		
		// Checking :
		Calendar cTglCurrDateCheck = Calendar.getInstance();		
		cTglCurrDateCheck.setTime(new Date());		
		cTglCurrDateCheck.set(Calendar.DAY_OF_MONTH, cTglCurrDateCheck.getActualMinimum(Calendar.DAY_OF_MONTH));		
		Date vTglCurrDateCheck  = cTglCurrDateCheck.getTime();
		
		Calendar cTglFromCheck = Calendar.getInstance();		
		cTglFromCheck.setTime(vTglFromParam);		
		cTglFromCheck.set(Calendar.DAY_OF_MONTH, cTglFromCheck.getActualMinimum(Calendar.DAY_OF_MONTH));		
		Date vTglFromCheck  = cTglFromCheck.getTime();
		
		Calendar cTglUptoCheck = Calendar.getInstance();		
		cTglUptoCheck.setTime(vTglUptoParam);		
		cTglUptoCheck.set(Calendar.DAY_OF_MONTH, cTglUptoCheck.getActualMinimum(Calendar.DAY_OF_MONTH));		
		Date vTglUptoCheck  = cTglUptoCheck.getTime();
		
		
		Date vCurrDateTrunc = DateUtils.truncate(vTglCurrDateCheck, Calendar.DATE);
		Date vTglFromCheckTrunc = DateUtils.truncate(vTglFromCheck, Calendar.DATE);
		Date vTglUptoCheckTrunc = DateUtils.truncate(vTglUptoCheck, Calendar.DATE);
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglInvFrom);  
		String vStrTglTo  = frmTgl.format(vTglInvTo);  
		
		if(vTglFromCheckTrunc.compareTo(vCurrDateTrunc)  < 0 ){
			ZksampleMessageUtils.showErrorMessage("Perkiraan Realisasi - Periode Mulai harus lebih besar dari Tanggal Hari ini");
		} else {
			if(vTglFromCheckTrunc.compareTo(vTglUptoCheckTrunc)  > 0 ){
				ZksampleMessageUtils.showErrorMessage("Perkiraan Realisasi - Periode Akhir harus lebih besar atau sama dengan Periode Mulai");
			} else {
				
				
				
				String vStrTglFromParam  = frmTgl.format(vTglFromParam); 
				String vStrTglUptoParam  = frmTgl.format(vTglUptoParam);  
				
				Calendar cTglUptoParamForNextYear= Calendar.getInstance();		
				cTglUptoParamForNextYear.setTime(vTglUptoParam);
				int yearTglUptoParamForNextYear = cTgl.get(Calendar.YEAR);
				
				
				String dRTglUptoParamForNextYear = "31/12/"+(yearTglUptoParamForNextYear+1);
				SimpleDateFormat dfRTglUptoParamForNextYear= new SimpleDateFormat("dd/MM/yyyy");
				Date vTglUptoParamForNextYear  = dfRTglUptoParamForNextYear.parse(dRTglUptoParamForNextYear);
				
				String vStrTglUptoParamForNextYear  = frmTgl.format(vTglUptoParamForNextYear);  
				
				
				
				String jasperRpt = "/solusi/hapis/webui/reports/sales/04053_02_OutstandingSORealisasiSummary.jasper";
				
				
				if (vJnsLap.equals("SUM2") == true) {
					
					@SuppressWarnings("unused")
					String vSync = callStoreProcOrFuncService.callSyncAReport("0507007-03");
					
					@SuppressWarnings("unused")
					String vResultOutSO = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFromParam, vStrTglUptoParamForNextYear, "ALL", vCabang, "CETAK");

					
					@SuppressWarnings("unused")
					String vResultOutUM = callStoreProcOrFuncService.callOutstandingUM(vProsesId, vCabang, "CETAK");
					
					
					@SuppressWarnings("unused")
					String vResult = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglTo, "ALL", "SR");
					
					
					
					jasperRpt = "/solusi/hapis/webui/reports/sales/04053_02_OutstandingSORealisasiSummary.jasper";
					
					
					param.put("TglInvTo",  vTglInvTo); 
					param.put("AmtShow",  vNilai); 
					param.put("EstRealFrom",  vTglFromParam); 
					param.put("EstRealUpto",  vTglUptoParam); 
					
					
				} else {
					if (vJnsLap.equals("DTLUM") == true) {
						
						
						
						@SuppressWarnings("unused")
						String vSync = callStoreProcOrFuncService.callSyncAReport("0507007-01");
						
						
						@SuppressWarnings("unused")
						String vResultOutSO = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFromParam, vStrTglUptoParamForNextYear, "ALL", vCabang, "CETAK");

						
						@SuppressWarnings("unused")
						String vResultOutUM = callStoreProcOrFuncService.callOutstandingUM(vProsesId, vCabang, "CETAK");
						
						jasperRpt = "/solusi/hapis/webui/reports/sales/04053_03_OutstandingSORealisasiUangMuka.jasper";
						PathReport pathReport = new PathReport();
						param.put("SUBREPORT_DIR",  pathReport.getSubRptSales());
						
						
						param.put("EstRealFrom",  vTglFromParam);		
						param.put("EstRealUpto",  vTglUptoParam); 
						
					} else {
					
						@SuppressWarnings("unused")
						String vSync = callStoreProcOrFuncService.callSyncAReport("0507007-02");
						
						@SuppressWarnings("unused")
						String vResultOutSO = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFromParam, vStrTglUptoParam, "ALL", vCabang, "CETAK");

						
						jasperRpt = "/solusi/hapis/webui/reports/sales/04053_OutstandingSORealisasi.jasper";
						
						param.put("Detail",  vJnsLap); 
						param.put("EstRealFrom",  vTglFromParam); 
						param.put("EstRealUpto",  vTglUptoParam); 
					}
					
				}
								
				param.put("ProsesId",  vProsesId); 
				param.put("Cabang",  vCabang); 				
				
				
				new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
				
				@SuppressWarnings("unused")
				String vResultOutSODelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFromParam, vStrTglUptoParam, "ALL", vCabang, "DELETE");

				
				if (vJnsLap.equals("SUM2") == true) {
					@SuppressWarnings("unused")
					String vDelete = callStoreProcOrFuncService.callSalesRevenue(vProsesId, vStrTglFrom, vStrTglTo, "ALL", "DELETE");
					
					@SuppressWarnings("unused")
					String vDeleteUM = callStoreProcOrFuncService.callOutstandingUM(vProsesId, vCabang, "DELETE");
					
				} else {
					if (vJnsLap.equals("DTLUM") == true) {
						@SuppressWarnings("unused")
						String vDeleteUM = callStoreProcOrFuncService.callOutstandingUM(vProsesId, vCabang, "DELETE");
					}
				}
				
			}
			
			
			
				
			
		}
		
		
		
		 
		
	}
 
}