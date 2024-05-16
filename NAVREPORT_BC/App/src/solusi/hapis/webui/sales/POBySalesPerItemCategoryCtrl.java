package solusi.hapis.webui.sales;



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
import org.zkoss.zul.Combobox;
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

public class POBySalesPerItemCategoryCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	protected Radiogroup rdgJnsRpt;	 
	protected Radio rdSum;
	protected Radio rdDtl;
	
	
	protected Textbox txtSalesFrom;
	protected Textbox txtSalesUpto;
	

	protected Combobox  cmbJenis;
	protected Combobox  cmbJenisItem;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
    	dbTglUpto.setValue((new Date()));   
    	    	
    	rdALL.setSelected(true);    
    	
    	rdSum.setSelected(true); 

    	txtSalesUpto.setValue("ZZZ");
    	
    	cmbJenis.setSelectedIndex(0);
    	cmbJenisItem.setSelectedIndex(0);
	
    	
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
		
		Date vTglUpTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
		
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
		
		
		String vJenisItem = "ALL";
		if (cmbJenisItem.getSelectedItem().getValue() != null){
			vJenisItem = (String) cmbJenisItem.getSelectedItem().getValue();
		}
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJnsRpt = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsRpt.getSelectedItem().getValue())) {
			vJnsRpt = rdgJnsRpt.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0506001");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04010_POBySalesPerItemCategorySum.jasper";
		
		if(vJnsRpt.equals("SUM")){
			jasperRpt = "/solusi/hapis/webui/reports/sales/04010_POBySalesPerItemCategorySum.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/sales/04011_POBySalesPerItemCategoryDtl.jasper";
		}
		
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo); 
		
		param.put("Cabang",  vCabang); 
		param.put("SalesFrom",  vSalesFrom); 
		param.put("SalesUpto",  vSalesUpto); 
		
		param.put("JenisSO",  vJenis); 
		
		param.put("Company",  vCompany); 
		
		param.put("JnsItem",  vJenisItem); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}