package solusi.hapis.webui.accounting.lapKeuanganExternal;

import java.io.Serializable;
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

public class ReportGeneratorCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdAJ;
	protected Radio rdSP;
	
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdLap1;
	protected Radio rdLap2;
	
	protected Bandbox  cmbNamaReport;
	protected Listbox listNamaReport;
	protected String vNamaReport = ".";

	private SelectQueryService selectQueryService;
	
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
		
		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(new Date());
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
		
    	dbTglUpto.setValue(vTglUpto);   
    	

		rdLap1.setSelected(true); 
		rdAJ.setSelected(true); 
		
		
    	Bandpopup popup2 = new Bandpopup();
			listNamaReport = new Listbox();
			listNamaReport.setMold("paging");
			listNamaReport.setAutopaging(false);
			listNamaReport.setWidth("400px");
			listNamaReport.addEventListener(Events.ON_SELECT, selectNamaReport());
			listNamaReport.setParent(popup2);
		popup2.setParent(cmbNamaReport);
	
		listNamaReport.appendItem("<Please Select>", ".");
		
		List<Object[]> vResultNamaReport = selectQueryService.QueryNamaReportGeneratorPNL();
		if(CommonUtils.isNotEmpty(vResultNamaReport)){
			for(Object[] aRslt : vResultNamaReport){
				listNamaReport.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbNamaReport.setValue(listNamaReport.getItemAtIndex(0).getLabel());
		listNamaReport.setSelectedItem(listNamaReport.getItemAtIndex(0));
	
	}

	private EventListener selectNamaReport() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbNamaReport.setValue(listNamaReport.getSelectedItem().getLabel());
				vNamaReport = listNamaReport.getSelectedItem().getValue().toString();
				cmbNamaReport.close();
			}
		};
	}
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(new Date());
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
  		
		
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}
		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String vJenisLap = "1";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJenisLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		
		vProsesId = String.valueOf(System.currentTimeMillis());
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  
		String vStrTglTo  = frmTgl.format(vTglUpto); 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0109003");

		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callReportGeneratorPNL(vProsesId, vCompany, vStrTglFrom, vStrTglTo, vNamaReport, vJenisLap,"CETAK");
		

		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/lapKeuanganExternal/010903_ReportGenerator.jasper";
		


		param.put("Company",  vCompany); 
		param.put("ProsesId",  vProsesId); 
		param.put("TglInvFrom",  vTglFrom); 
		param.put("TglInvUpto",  vTglUpto); 		
		param.put("TipeKolom",  vJenisLap); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		@SuppressWarnings("unused")
		String vDelete =  callStoreProcOrFuncService.callReportGeneratorPNL(vProsesId, vCompany, vStrTglFrom, vStrTglTo, vNamaReport, vJenisLap,"DELETE");
	
		
	}
 
}