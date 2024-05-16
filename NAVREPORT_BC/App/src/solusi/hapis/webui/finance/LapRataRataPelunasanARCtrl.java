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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapRataRataPelunasanARCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	

	
	protected Radiogroup rdgCompany;
	protected Radio rdALL;
	protected Radio rdSP;
	protected Radio rdAJ;
	
		
	protected Textbox txtCustNo;
	
	protected Bandbox  cmbCustGroup;
	protected Listbox listCustGroup;
	protected String vCustGroup = "ALL";
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		

		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTgl = cTglFrom.get(Calendar.YEAR);
    	    			
		String dTglFrom = "1/1/"+(yearTgl);
		SimpleDateFormat dfTglFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom = dfTglFrom.parse(dTglFrom);
		
		dbTglFrom.setValue(vTglFrom);
		dbTglTo.setValue((new Date()));   
    	
    	
    	rdALL.setSelected(true); 


    	Bandpopup popup1 = new Bandpopup();
			listCustGroup = new Listbox();
			listCustGroup.setMold("paging");
			listCustGroup.setAutopaging(true);
			listCustGroup.setWidth("250px");
			listCustGroup.addEventListener(Events.ON_SELECT, selectCustGroup());
			listCustGroup.setParent(popup1);
		popup1.setParent(cmbCustGroup);
	        
		listCustGroup.appendItem("OTHERS", "ALL");
		
		List<Object[]> vResult = selectQueryService.QueryCustGroup();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listCustGroup.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbCustGroup.setValue(listCustGroup.getItemAtIndex(0).getLabel());
		listCustGroup.setSelectedItem(listCustGroup.getItemAtIndex(0));
	
	
	
		
	}
	
	private EventListener selectCustGroup() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCustGroup.setValue(listCustGroup.getSelectedItem().getLabel());
				vCustGroup = listCustGroup.getSelectedItem().getValue().toString();
				cmbCustGroup.close();
			}
		};
	}
			
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
	
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTgl = cTglFrom.get(Calendar.YEAR);
    	    			
		String dTglFrom = "1/1/"+(yearTgl);
		SimpleDateFormat dfTglFrom= new SimpleDateFormat("dd/MM/yyyy");
		
		Date vTglFrom = dfTglFrom.parse(dTglFrom);		
	
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
		
		
			
		String vCustNo = "ALL";
		if (StringUtils.isNotEmpty(txtCustNo.getValue())) {
			vCustNo = txtCustNo.getValue();
		} 
		
		
	
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0207009");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02056_LapRataRataPelunasanAR.jasper";
		

    	param.put("TglInvFrom",  vTglFrom); 
		param.put("TglInvUpto",  vTglTo);  
		
		param.put("Company",  vCompany); 
		param.put("CustomerNo",  vCustNo); 		
		param.put("CustomerGroup",  vCustGroup); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	 
		
	}
 
}

