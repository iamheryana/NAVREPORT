package solusi.hapis.webui.accounting;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapPenjualanCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
		
	protected Textbox txtKodeCustFrom;
	protected Textbox txtKodeCustUpto;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	

	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	dbTglTo.setValue((new Date()));   
    	
    	rdAJ.setSelected(true); 
    	rdSUM.setSelected(true); 
    	
    	txtKodeCustUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
    
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

    	
    	rdPDF.setSelected(true); 
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
	
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		Date vTglFrom = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
				

		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenis = "Summary";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 
		
			
		String vKodeCustFrom = ".";
		if (StringUtils.isNotEmpty(txtKodeCustFrom.getValue())) {
			vKodeCustFrom = txtKodeCustFrom.getValue();
		} 
		
		String vKodeCustUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtKodeCustUpto.getValue())) {
			vKodeCustUpto = txtKodeCustUpto.getValue();
		} 
				

		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0103005");
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01011_LapRegPenjualan.jasper";
		
		
		PathReport pathreport = new PathReport();
		
    	param.put("TglInvFrom",  vTglFrom); 
		param.put("TglInvTo",  vTglTo);  
		param.put("CustFrom",  vKodeCustFrom); 
		param.put("CustUpto",  vKodeCustUpto); 
		param.put("Cabang",  vCabang); 
		param.put("SUBREPORT_DIR",  pathreport.getSubRptAccounting());
		
		param.put("Company",  vCompany); 
		param.put("JnsRpt",  vJenis); 
		param.put("Layout",  vSaveAs); 
		
		
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}
		
		 
		
	}
 
}

