package solusi.hapis.webui.accounting;

import java.io.Serializable;
import java.text.ParseException;
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

public class OutTOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Datebox dbTglUpto; 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
		
	protected Bandbox  cmbRespon;
	protected Listbox listRespon;
	protected String vRespon = "JAKARTA";

	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		 
    	dbTglUpto.setValue((new Date()));   
    	    	
    	rdALL.setSelected(true);     	
  	
    	rdPDF.setSelected(true); 

    	Bandpopup popup1 = new Bandpopup();
			listRespon = new Listbox();
			listRespon.setMold("paging");
			listRespon.setAutopaging(true);
			listRespon.setWidth("250px");
			listRespon.addEventListener(Events.ON_SELECT, selectRespon());
			listRespon.setParent(popup1);
		popup1.setParent(cmbRespon);
		
		List<Object[]> vResult = selectQueryService.QueryResponsibilityTO();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listRespon.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbRespon.setValue(listRespon.getItemAtIndex(0).getLabel());
		listRespon.setSelectedItem(listRespon.getItemAtIndex(0));
    
	}
	
	private EventListener selectRespon() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbRespon.setValue(listRespon.getSelectedItem().getLabel());
				vRespon = listRespon.getSelectedItem().getValue().toString();
				cmbRespon.close();
			}
		};
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}
		

		
				
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0107003");
		
	
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01029_OutTO.jasper";

		
		param.put("TglUpto",  vTglUpto); 
		param.put("Respon",  vRespon); 
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