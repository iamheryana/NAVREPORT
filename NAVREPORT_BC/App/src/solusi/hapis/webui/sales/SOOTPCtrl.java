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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SOOTPCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglUpto;
 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	
//	protected Radiogroup rdgSave;	 
//	protected Radio rdPDF;
//	protected Radio rdXLS;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	
	protected Radiogroup rdgJenisSO;	 
	protected Radio rdOTP;
	protected Radio rdBOOKING;
	
	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = "ALL";

		
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
 //   	rdPDF.setSelected(true); 
    	
    	rdOTP.setSelected(true); 
    	
    	rdSUM.setSelected(true); 
    	
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
	
		
    	Bandpopup popup2 = new Bandpopup();
			listSales = new Listbox();
			listSales.setMold("paging");
			listSales.setAutopaging(true);
			listSales.setWidth("250px");
			listSales.addEventListener(Events.ON_SELECT, selectSales());
			listSales.setParent(popup2);
		popup2.setParent(cmbSales);
	        
		listSales.appendItem("ALL", "ALL");
		
		List<Object[]> vResultSales = selectQueryService.QuerySalesman();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResultSales){
				listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbSales.setValue(listSales.getItemAtIndex(0).getLabel());
		listSales.setSelectedItem(listSales.getItemAtIndex(0));
	

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
	
	private EventListener selectSales() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbSales.setValue(listSales.getSelectedItem().getLabel());
				vSales = listSales.getSelectedItem().getValue().toString();
				cmbSales.close();
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
		

		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenis = "SUM";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 
		
		String vJenisSO = "OTP";
		if (StringUtils.isNotEmpty(rdgJenisSO.getSelectedItem().getValue())) {
			vJenisSO = rdgJenisSO.getSelectedItem().getValue();	
		} 

		
			
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0501003");	
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04004_SOOTP.jasper";

		if (vJenis.equals("SUM")){
			jasperRpt = "/solusi/hapis/webui/reports/sales/04004_SOOTP.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/sales/04005_SOOTPDetail.jasper";
		}		

		param.put("TglUpto",  vTglUpTo); 
		param.put("Cabang",  vCabang); 
		param.put("Sales",  vSales); 		
		param.put("Company",  vCompany); 
		param.put("Jenis",  vJenisSO); 
		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
//		String vSaveAs = "PDF";
//		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
//			vSaveAs = rdgSave.getSelectedItem().getValue();	
//		} 
//		
//		if(vSaveAs.equals("PDF")){
//			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
//		} else {
//			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
//		}
//		
		 
		
	}
 
}