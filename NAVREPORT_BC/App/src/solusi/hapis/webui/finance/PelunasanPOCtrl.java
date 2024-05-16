package solusi.hapis.webui.finance;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.model.Vendor;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.VendorLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class PelunasanPOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowPelunasanPO;

	protected Radiogroup rdgLaporan;	 
	protected Radio rdDTL;
	protected Radio rdSUM;
	
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Textbox txtVendorNo;


	protected Bandbox  cmbProject;
	protected Listbox listProject;
	protected String vProject = "ALL";
	
	
	protected Button btnSearchVendorLOV;
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		rdSUM.setSelected(true); 
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
    	dbTglUpto.setValue((new Date()));   
    	    	

    	txtVendorNo.setValue("ALL");
    	
		
    	Bandpopup popup1 = new Bandpopup();
			listProject = new Listbox();
			listProject.setMold("paging");
			listProject.setAutopaging(true);
			listProject.setWidth("350px");
			listProject.addEventListener(Events.ON_SELECT, selectProject());
			listProject.setParent(popup1);
		popup1.setParent(cmbProject);
	        
		listProject.appendItem("ALL", "ALL");
		
		List<Object[]> vResult = selectQueryService.QueryProject();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listProject.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbProject.setValue(listProject.getItemAtIndex(0).getLabel());
		listProject.setSelectedItem(listProject.getItemAtIndex(0));
    	
	}
	
	private EventListener selectProject() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbProject.setValue(listProject.getSelectedItem().getLabel());
				vProject = listProject.getSelectedItem().getValue().toString();
				cmbProject.close();
			}
		};
	}		

	
	public void onClick$btnSearchVendorLOV(Event event) {
		Vendor cust = VendorLOV.show(windowPelunasanPO);

		if (cust != null) {
			txtVendorNo.setValue(cust.getCode());
		} else {
			txtVendorNo.setValue(null);
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
		

		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		

		String vVendorNo = "ALL";
		if (StringUtils.isNotEmpty(txtVendorNo.getValue())) {
			vVendorNo = txtVendorNo.getValue();
		}
		
		String vJnsReport = "SUM";
		if (StringUtils.isNotEmpty(rdgLaporan.getSelectedItem().getValue())) {
			vJnsReport = rdgLaporan.getSelectedItem().getValue();	
		} 

		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0207003");
			
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02049_PelunasanPO.jasper";
		

		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);

		param.put("Vendor",  vVendorNo);
		
		param.put("KodeProject",  vProject); 
		
		param.put("JnsReport",  vJnsReport); 
		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}
