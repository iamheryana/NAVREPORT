package solusi.hapis.webui.accounting.inventory;

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

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class StockByPrincipalCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglUpto;  
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	
	protected Radiogroup rdgDtlLap;	 
	protected Radio rdLoc;
	protected Radio rdNoLoc;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	
	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";	

	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	dbTglUpto.setValue((new Date()));   
    	
    	rdAJ.setSelected(true);
    	rdXLS.setSelected(true);
    	rdLoc.setSelected(true);
    
		Bandpopup popup2 = new Bandpopup();
			listPrincipal = new Listbox();
			listPrincipal.setMold("paging");
			listPrincipal.setAutopaging(false);
			listPrincipal.setWidth("400px");
			listPrincipal.addEventListener(Events.ON_SELECT, selectPrincipal());
			listPrincipal.setParent(popup2);
		popup2.setParent(cmbPrincipal);
	        
		listPrincipal.appendItem("ALL", "ALL");
		
		List<Object[]> vResultPrincipal = selectQueryService.QueryPrincipal();
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
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}			


		Date vTglUpTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		}
		
		String vJenisLap = "LOCATION";
		if (StringUtils.isNotEmpty(rdgDtlLap.getSelectedItem().getValue())) {
			vJenisLap = rdgDtlLap.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0107008");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/inventory/010701_StockByPrincipal.jasper";
			
		param.put("Tanggal",  vTglUpTo); 
		param.put("Company",  vCompany); 
		param.put("Principal",  vPrincipal); 
		param.put("JnsRpt", vJenisLap); 

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