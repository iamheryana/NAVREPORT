package solusi.hapis.webui.accounting.managementReporting;


import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapDraftPNLManagementCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	
	protected Radiogroup rdgAmount;	 
	protected Radio rdAmt1;
	protected Radio rdAmt2;
	protected Radio rdAmt3;
	
	protected Datebox dbPeriode;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	protected String vCabangLabel = "ALL";
	
	
	private SelectQueryService selectQueryService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
	
		
    	rdAmt2.setSelected(true); 
    	
    	dbPeriode.setValue((new Date()));   
    	
    	Bandpopup popup1 = new Bandpopup();
			listCabang = new Listbox();
			listCabang.setMold("paging");
			listCabang.setAutopaging(true);
			listCabang.setWidth("250px");
			listCabang.addEventListener(Events.ON_SELECT, selectCabang());
			listCabang.setParent(popup1);
		popup1.setParent(cmbCab);
	        
		listCabang.appendItem("ALL", "ALL");
		
		List<Object[]> vResult = selectQueryService.QueryCabang2();
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
				vCabangLabel = listCabang.getSelectedItem().getLabel().toString();
				cmbCab.close();
			}
		};
	}
	



	@SuppressWarnings({ "unchecked", "unused" })
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException, IOException {
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
		Calendar cTglUptoPrevMonth = Calendar.getInstance(); 
		cTglUptoPrevMonth.setTime(vTglUpto);
		cTglUptoPrevMonth.add(Calendar.MONTH, -1);
		Date vTglUptoPrevMonth = cTglUptoPrevMonth.getTime();
		
		Calendar cTglUptoPrevYear = Calendar.getInstance(); 
		cTglUptoPrevYear.setTime(vTglUpto);
		cTglUptoPrevYear.add(Calendar.YEAR, -1);
		Date vTglUptoPrevYear = cTglUptoPrevYear.getTime();
		
		
			
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		BigDecimal vPembagi_report = new BigDecimal(String.valueOf(vAmountIn));
		   
		
		String ProsesID_PNLProcess = String.valueOf(System.currentTimeMillis());
		String vSync = callStoreProcOrFuncService.callSyncAReport("0101001");
		String vCetakPNL = callStoreProcOrFuncService.callReportPNLManagement(ProsesID_PNLProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, "CETAK");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/managementReporting/010101_LapPNLManagement_Draft.jasper";
		 
	
	    
		param.put("ProsesId",  ProsesID_PNLProcess);		
	    param.put("TglInvUpto",  vTglUpto); 
	    param.put("TglInvUptoPrevMonth", vTglUptoPrevMonth );  
	    param.put("TglInvUptoPrevYear", vTglUptoPrevYear );  	
	    param.put("Cabang",  vCabangLabel); 
	    param.put("Pembagi",  vPembagi_report);		
	
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		String vDeletePNL =  callStoreProcOrFuncService.callReportPNLManagement(ProsesID_PNLProcess, vStrTglFrom, vStrTglUpto, vCabang, vPembagi_report, "DELETE");
		


	}
	
	

	
}