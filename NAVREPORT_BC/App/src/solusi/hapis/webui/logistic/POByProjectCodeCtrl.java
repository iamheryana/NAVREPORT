package solusi.hapis.webui.logistic;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class POByProjectCodeCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowPOByProjectCode;

	protected Radiogroup rdgLaporan;	 
	protected Radio rdDTL;
	protected Radio rdSUM;
	


	protected Bandbox  cmbProject;
	protected Listbox listProject;
	protected String vProject = "N/A";
	

	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		rdSUM.setSelected(true); 
		
		
 	
		
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
			
	
		
		String vJnsReport = "SUM";
		if (StringUtils.isNotEmpty(rdgLaporan.getSelectedItem().getValue())) {
			vJnsReport = rdgLaporan.getSelectedItem().getValue();	
		} 

			
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0306010");	
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03043_POProject.jasper";
		


		
		param.put("ProjectCode",  vProject); 
		
		param.put("JnsRpt",  vJnsReport); 
		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}
