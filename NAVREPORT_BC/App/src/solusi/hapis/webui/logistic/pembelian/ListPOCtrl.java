package solusi.hapis.webui.logistic.pembelian;


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
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ListPOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	
	protected Textbox txtNoBPOFrom; 
	protected Textbox txtNoBPOUpto; 
	
	protected Textbox txtNoPOFrom; 
	protected Textbox txtNoPOUpto; 
	
	protected Textbox txtVendorNoFrom; 
	protected Textbox txtVendorNoUpto;
	
	
	protected Datebox dbTglPOFrom;	
	protected Datebox dbTglPOUpto;
	
	protected Bandbox  cmbAssignUserID;
	protected Listbox listAssignUserID;
	protected String vAssignUserID = "ALL";
	
	private SelectQueryService selectQueryService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		

		rdALL.setSelected(true);   

		txtNoBPOUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
		txtNoPOUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
		txtVendorNoUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
		
		
		dbTglPOFrom.setValue(new Date());
		dbTglPOUpto.setValue(new Date());
		
		Bandpopup popup2 = new Bandpopup();
			listAssignUserID = new Listbox();
			listAssignUserID.setMold("paging");
			listAssignUserID.setAutopaging(false);
			listAssignUserID.setWidth("400px");
			listAssignUserID.addEventListener(Events.ON_SELECT, selectAssignUserID());
			listAssignUserID.setParent(popup2);
		popup2.setParent(cmbAssignUserID);
	        
		listAssignUserID.appendItem("ALL", "ALL");
		
		List<Object[]> vResultAssignUserID = selectQueryService.QueryAssignUserIdPO();
		
		if(CommonUtils.isNotEmpty(vResultAssignUserID)){
			for(Object[] aRslt : vResultAssignUserID){
				listAssignUserID.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
	
	
		cmbAssignUserID.setValue(listAssignUserID.getItemAtIndex(0).getLabel());
		listAssignUserID.setSelectedItem(listAssignUserID.getItemAtIndex(0));
		
	}

	private EventListener selectAssignUserID() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbAssignUserID.setValue(listAssignUserID.getSelectedItem().getLabel());
				vAssignUserID = listAssignUserID.getSelectedItem().getValue().toString();
				cmbAssignUserID.close();
			}
		};
	}
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
	
		
		
		String  vNoBPOFrom = ".";
		if(CommonUtils.isNotEmpty(txtNoBPOFrom.getValue()) == true){  
			vNoBPOFrom = txtNoBPOFrom.getValue();
		}
		
		String  vNoBPOUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if(CommonUtils.isNotEmpty(txtNoBPOUpto.getValue()) == true){  
			vNoBPOUpto = txtNoBPOUpto.getValue();
		}
		
		
		String  vNoPOFrom = ".";
		if(CommonUtils.isNotEmpty(txtNoPOFrom.getValue()) == true){  
			vNoPOFrom = txtNoPOFrom.getValue();
		}
		
		String  vNoPOUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if(CommonUtils.isNotEmpty(txtNoPOUpto.getValue()) == true){  
			vNoPOUpto = txtNoPOUpto.getValue();
		}
		
		
		String  vVendorNoFrom = ".";
		if(CommonUtils.isNotEmpty(txtVendorNoFrom.getValue()) == true){  
			vVendorNoFrom = txtVendorNoFrom.getValue();
		}
		
		String  vVendorNoUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if(CommonUtils.isNotEmpty(txtVendorNoUpto.getValue()) == true){  
			vVendorNoUpto = txtVendorNoUpto.getValue();
		}
		
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		Date vTglPOFrom= new Date();   
		if(CommonUtils.isNotEmpty(dbTglPOFrom.getValue()) == true){  
			vTglPOFrom = dbTglPOFrom.getValue();
		}
		
		Date vTglPOUpto= new Date();   
		if(CommonUtils.isNotEmpty(dbTglPOUpto.getValue()) == true){  
			vTglPOUpto = dbTglPOUpto.getValue();
		}
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0306011");	
		
				
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/pembelian/030501_ListPO.jasper";

	
		param.put("Company",  vCompany); 
		param.put("NoPOFrom",  vNoPOFrom); 
		param.put("NoPOUpto",  vNoPOUpto); 
		param.put("NoBPOFrom",  vNoBPOFrom); 
		param.put("NoBPOUpto",  vNoBPOUpto); 
		
		param.put("AssignUserID",  vAssignUserID); 
		
		param.put("TglPOFrom",  vTglPOFrom); 
		param.put("TglPOUpto",  vTglPOUpto); 
		param.put("VendorFrom",  vVendorNoFrom); 
		param.put("VendorUpto",  vVendorNoUpto); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}
