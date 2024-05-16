package solusi.hapis.webui.logistic;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Combobox;
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

public class SONeedToPurchaseCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Radiogroup rdgItem;	 
	protected Radio rdNTO;
	protected Radio rdAll;
	
	protected Radiogroup rdgTOP;	 
	protected Radio rdTOPAll;
	protected Radio rdTOPNONCIA;
	protected Radio rdTOPCIA;
	

	
	protected Textbox txtSalesFrom;
	protected Textbox txtSalesUpto;
	

	protected Combobox  cmbJenis;
	//protected Combobox  cmbLocation;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
    	    	
    	rdALL.setSelected(true); 
    	rdPDF.setSelected(true); 
    	
    	rdNTO.setSelected(true); 
    	rdTOPAll.setSelected(true); 

    	txtSalesUpto.setValue("ZZZ");
    	

    	cmbJenis.setSelectedIndex(0);
    	//cmbLocation.setSelectedIndex(0);
    	
        
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
		
//		String vCabang = "ALL";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCabang = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		String vSalesFrom = ".";
		if (StringUtils.isNotEmpty(txtSalesFrom.getValue())) {
			vSalesFrom = txtSalesFrom.getValue();
		} 
		
		String vSalesUpto = "ZZZ";
		if (StringUtils.isNotEmpty(txtSalesUpto.getValue())) {
			vSalesUpto = txtSalesUpto.getValue();
		} 
		
		
		
		String vJenis = "ALL";
		if (cmbJenis.getSelectedItem().getValue() != null){
			vJenis = (String) cmbJenis.getSelectedItem().getValue();
		}
		
		
		String vLocation = "ALL";
//		if (cmbLocation.getSelectedItem().getValue() != null){
//			vLocation = (String) cmbLocation.getSelectedItem().getValue();
//		}
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
	
		String vItem = "N";
		if (StringUtils.isNotEmpty(rdgItem.getSelectedItem().getValue())) {
			vItem = rdgItem.getSelectedItem().getValue();	
		} 
		
		
		String vTOP = "ALL";
		if (StringUtils.isNotEmpty(rdgTOP.getSelectedItem().getValue())) {
			vTOP = rdgTOP.getSelectedItem().getValue();	
		} 
				
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0305002");
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03013_NeedToPurchase.jasper";
		
		PathReport pathreport = new PathReport();
		param.put("SUBREPORT_DIR",  pathreport.getSubRptLogitic());

		param.put("Cabang",  vCabang); 
		param.put("SalesFrom",  vSalesFrom); 
		param.put("SalesUpto",  vSalesUpto); 
		param.put("LocCode",  vLocation); 
		
		param.put("AllItem",  vItem); 
		param.put("CiaDP",  vTOP); 
		
		param.put("JenisSO",  vJenis); 
		
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
