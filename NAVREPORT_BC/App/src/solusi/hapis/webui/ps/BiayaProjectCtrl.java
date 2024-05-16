package solusi.hapis.webui.ps;

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

public class BiayaProjectCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	protected Radiogroup  rdgLaporan;
	protected Radio rdDTL;
	protected Radio rdSUM;
	
		
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglTo; 
		
	protected Bandbox  cmbProject;
	protected Listbox listProject;
	protected String vProject = "N/A";
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
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
    	dbTglTo.setValue((new Date()));   

    	
    	    	
    	rdALL.setSelected(true); 
    	rdPDF.setSelected(true); 
    	
    	rdDTL.setSelected(true); 

    
    	Bandpopup popup1 = new Bandpopup();
			listProject = new Listbox();
			listProject.setMold("paging");
			listProject.setAutopaging(true);
			listProject.setWidth("350px");
			listProject.addEventListener(Events.ON_SELECT, selectProject());
			listProject.setParent(popup1);
		popup1.setParent(cmbProject);
	        
		listProject.appendItem("<<Please Select/Silahkan Pilih>>", "N/A");
		
		
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
		
		Date vTglTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String vJenisLap = "Y";
		if (StringUtils.isNotEmpty(rdgLaporan.getSelectedItem().getValue())) {
			vJenisLap = rdgLaporan.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0601001");
		
		
		
		String jasperRptDetail = "/solusi/hapis/webui/reports/ps/05001_BiayaProjectDetail.jasper";
		String jasperRptSummary = "/solusi/hapis/webui/reports/ps/05002_BiayaProjectSummary.jasper";
		
		String jasperRpt ="";
		
		if(vJenisLap.equals("Y")==true){
			jasperRpt = jasperRptDetail;
		} else {
			jasperRpt = jasperRptSummary;
		}
		
		param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglTo);
		param.put("Project",  vProject); 		
		param.put("Company",  vCompany); 
		
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



