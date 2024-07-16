package solusi.hapis.webui.logistic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class PrincipalInfoCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
		
	protected Intbox intTahun;
	protected Datebox dbTglUpto;
	
	
	protected Decimalbox decCharge;
	
	
	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Radiogroup rdgAmount;	 
	protected Radio rdAmt1;
	protected Radio rdAmt2;
	protected Radio rdAmt3;
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Date vTgl = new Date();   
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(vTgl);
		int yearTgl = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTgl);
		
		dbTglUpto.setValue((new Date()));   

    	rdPDF.setSelected(true); 

    	rdSUM.setSelected(true);    
    	
    	rdAmt3.setSelected(true); 
		
		Bandpopup popup2 = new Bandpopup();
			listPrincipal = new Listbox();
			listPrincipal.setMold("paging");
			listPrincipal.setAutopaging(false);
			listPrincipal.setWidth("400px");
			listPrincipal.addEventListener(Events.ON_SELECT, selectPrincipal());
			listPrincipal.setParent(popup2);
		popup2.setParent(cmbPrincipal);
	        
		listPrincipal.appendItem("ALL", "ALL");
		
		List<Object[]> vResultPrincipal = selectQueryService.QueryPrincipalInfo();
		if(CommonUtils.isNotEmpty(vResultPrincipal)){
			for(Object[] aRslt : vResultPrincipal){
				listPrincipal.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbPrincipal.setValue(listPrincipal.getItemAtIndex(0).getLabel());
		listPrincipal.setSelectedItem(listPrincipal.getItemAtIndex(0));
    	
	}
	
	private EventListener selectPrincipal() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbPrincipal.setValue(listPrincipal.getSelectedItem().getLabel());
				vPrincipal = listPrincipal.getSelectedItem().getValue().toString();
				cmbPrincipal.close();
			}
		};
	}
		
	
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0301001");
		
		Messagebox.show("Sync Sudah Selesai");
	}
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
			
		Date vTgl = new Date();   
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(vTgl);
		int yearTgl = cTgl.get(Calendar.YEAR);
    	    	
		int vTahun = yearTgl;
		if (CommonUtils.isNotEmpty(intTahun.getValue())) {
			vTahun = intTahun.getValue();
		}
	

		
		String dTglFrom = "1/1/"+(vTahun);
		SimpleDateFormat dfTglFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom = dfTglFrom.parse(dTglFrom);
		
		
		
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		
		String vJnsLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		}
		
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		BigDecimal vCharge = new BigDecimal(0);
		if(CommonUtils.isNotEmpty(decCharge.getValue()) == true){  
			vCharge = decCharge.getValue();
		}
		
		BigDecimal vPembagi = new BigDecimal(String.valueOf(vAmountIn));
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0301001");
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03001_PrincipalInfoSum.jasper";
		
		if(vJnsLap.equals("SUM") == true){
			jasperRpt = "/solusi/hapis/webui/reports/logistic/03001_PrincipalInfoSum.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/logistic/03002_PrincipalInfoDetail.jasper";
		}
		
		if(vJnsLap.equals("SUM") == true){
			param.put("Pembagi",  vPembagi); 
		}
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);
		
		param.put("Principal",  vPrincipal); 
		
		param.put("Charge",  vCharge); 
		
		
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