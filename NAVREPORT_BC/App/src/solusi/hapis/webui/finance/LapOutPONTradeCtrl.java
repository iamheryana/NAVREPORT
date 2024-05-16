package solusi.hapis.webui.finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
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

public class LapOutPONTradeCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtKodeVendorFrom;
	protected Textbox txtKodeVendorTo;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;


	protected Combobox  cmbCurrency;
	
	protected Radiogroup rdgAmountIDR;	 
	protected Radio rdNormal;
	protected Radio rdSeribu;
	
	protected Bandbox  cmbProject;
	protected Listbox listProject;
	protected String vProject = "NON";
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	    	
    	txtKodeVendorTo.setValue("ZZZZZZZZZZZZZZZZZZZZ");
    	
    	rdAJ.setSelected(true); 
    	rdSeribu.setSelected(true); 
    	
   	
    	cmbCurrency.setSelectedIndex(0);
    	
    	
    	Bandpopup popup1 = new Bandpopup();
			listProject = new Listbox();
			listProject.setMold("paging");
			listProject.setAutopaging(true);
			listProject.setWidth("350px");
			listProject.addEventListener(Events.ON_SELECT, selectProject());
			listProject.setParent(popup1);
		popup1.setParent(cmbProject);
	        
		listProject.appendItem("ALL", "ALL");
		listProject.appendItem("TANPA KODE PROJECT", "NON");
		
		
		List<Object[]> vResult = selectQueryService.QueryProject();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listProject.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbProject.setValue(listProject.getItemAtIndex(1).getLabel());
		listProject.setSelectedItem(listProject.getItemAtIndex(1));
    	
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
	
	public void onClick$btnRumusan(Event event) throws FileNotFoundException{
		PathReport pathReport = new PathReport();
		
		File outputFileExcel = new File(pathReport.getRumusanOutPOCashFlow());
		
		
		InputStream mediais = new FileInputStream(outputFileExcel);
		AMedia amedia = new AMedia("RumusanOutPOCashFlow.xls", "xls", "application/vnd.ms-excel", mediais);
		
		Filedownload.save(amedia);
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		String vCompany = "AJ";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		

		String vCurrency = "ALL";
		if (cmbCurrency.getSelectedItem().getValue() != null){
			vCurrency = (String) cmbCurrency.getSelectedItem().getValue();
		}
		
		String vKodeVendorFrom = ".";
		if (StringUtils.isNotEmpty(txtKodeVendorFrom.getValue())) {
			vKodeVendorFrom = txtKodeVendorFrom.getValue();
		} 
		
		String vKodeVendorTo = "ZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtKodeVendorTo.getValue())) {
			vKodeVendorTo = txtKodeVendorTo.getValue();
		} 
		
		
		String vAmountIDR = "S";
		BigDecimal vIdrAmtIn = new BigDecimal (1000);

		if (StringUtils.isNotEmpty(rdgAmountIDR.getSelectedItem().getValue())) {
			vAmountIDR = rdgAmountIDR.getSelectedItem().getValue();	
		} 

		if(vAmountIDR.equals("S") == true){
			vIdrAmtIn = new BigDecimal (1000);
		} else {
			vIdrAmtIn = new BigDecimal (1);
		}
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0201002");
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02047_OutPONTrade.jasper";
		
		param.put("Currency",  vCurrency); 
		
		param.put("VendorFrom",  vKodeVendorFrom); 
		param.put("VendorUpto",  vKodeVendorTo); 
		
		
		param.put("Company",  vCompany); 
		param.put("IdrAmtIn",  vIdrAmtIn); 
		
		param.put("KodeProject",  vProject); 		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}