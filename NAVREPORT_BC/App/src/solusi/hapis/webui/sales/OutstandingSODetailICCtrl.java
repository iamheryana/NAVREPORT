package solusi.hapis.webui.sales;


import java.io.Serializable;
import java.math.BigDecimal;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class OutstandingSODetailICCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	//protected Datebox dbTglUpto;
 
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	
//	protected Radiogroup rdgSave;	 
//	protected Radio rdPDF;
//	protected Radio rdXLS;
	
//	protected Intbox intYearFrom;
//	protected Intbox intYearUpto;

	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Textbox txtSalesFrom;
	protected Textbox txtSalesUpto;
	
	protected Radiogroup  rdgLaporan;
	protected Radio rdDTL;
	protected Radio rdSUM;
	
	protected Combobox  cmbJenis;
	protected Combobox  cmbJenisPO;
	protected Combobox  cmbStatus;
	protected Combobox  cmbJenisItem;
	
	protected Bandbox  cmbLocation;
	protected Listbox listLocation;
	protected String vLocation = "ALL";
		
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	protected Decimalbox dcmNilai;
	
	private SelectQueryService selectQueryService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
    	//dbTglUpto.setValue((new Date()));   
    	
//    	Calendar cTglFrom = Calendar.getInstance();		
//		cTglFrom.setTime(new Date());
//		int yearTgl = cTglFrom.get(Calendar.YEAR);		
//		
//		intYearFrom.setValue(yearTgl);
//		intYearUpto.setValue(yearTgl);
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		dbTglUpto.setValue(vTglUpto);  
		
		
    	dcmNilai.setValue(new BigDecimal (0));
    	    	
    	rdALL.setSelected(true); 
 //   	rdPDF.setSelected(true); 
    	
    	rdDTL.setSelected(true); 

    	txtSalesUpto.setValue("ZZZ");
    	
    	cmbJenis.setSelectedIndex(0);
    	cmbJenisPO.setSelectedIndex(0);
    	cmbStatus.setSelectedIndex(2);
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
	
		
    	Bandpopup popup2 = new Bandpopup();
			listLocation = new Listbox();
			listLocation.setMold("paging");
			listLocation.setAutopaging(true);
			listLocation.setWidth("250px");
			listLocation.addEventListener(Events.ON_SELECT, selectLocation());
			listLocation.setParent(popup2);
		popup2.setParent(cmbLocation);
	        
		listLocation.appendItem("ALL", "ALL");
		
		List<Object[]> vResultLocation = selectQueryService.QueryLocation();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResultLocation){
				listLocation.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbLocation.setValue(listLocation.getItemAtIndex(0).getLabel());
		listLocation.setSelectedItem(listLocation.getItemAtIndex(0));
	

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
	
	private EventListener selectLocation() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbLocation.setValue(listLocation.getSelectedItem().getLabel());
				vLocation = listLocation.getSelectedItem().getValue().toString();
				cmbLocation.close();
			}
		};
	}	
   	
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date vTanggal = null;
//			try {
//				vTanggal = formatter.parse("1900-01-01");			
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}			

//		Date vTglUpTo = vTanggal;   
//		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
//			vTglUpTo = dbTglUpto.getValue();
//		}
		
		
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
		
		String vJenisPO = "ALL";
		if (cmbJenisPO.getSelectedItem().getValue() != null){
			vJenisPO = (String) cmbJenisPO.getSelectedItem().getValue();
		}
		
		String vJenisItem = "ALL";
		if (cmbJenisItem.getSelectedItem().getValue() != null){
			vJenisItem = (String) cmbJenisItem.getSelectedItem().getValue();
		}
		
		
		String vStatus = "ALL";
		if (cmbStatus.getSelectedItem().getValue() != null){
			vStatus = (String) cmbStatus.getSelectedItem().getValue();
		}
		
//		String vLocation = "ALL";
//		if (cmbLocation.getSelectedItem().getValue() != null){
//			vLocation = (String) cmbLocation.getSelectedItem().getValue();
//		}
		
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String vLap = "Y";
		if (StringUtils.isNotEmpty(rdgLaporan.getSelectedItem().getValue())) {
			vLap = rdgLaporan.getSelectedItem().getValue();	
		} 
		
		
		BigDecimal vNilai = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
//		Calendar cTglFrom = Calendar.getInstance();		
//		cTglFrom.setTime(new Date());
//		int yearTgl = cTglFrom.get(Calendar.YEAR);	
//		
//		
//		int vYearFrom = yearTgl;
//		if (CommonUtils.isNotEmpty(intYearFrom.getValue())) {
//			vYearFrom =intYearFrom.getValue();
//		} 
//		
//		int vYearUpto = yearTgl;
//		if (CommonUtils.isNotEmpty(intYearUpto.getValue())) {
//			vYearUpto =intYearUpto.getValue();
//		} 
		
		Calendar cTgl= Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		
		String dRUpto = "31/12/"+yearTgl;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}   
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom); 
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507002");
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, vCabang, "CETAK-DTL");

		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04027_OutstandingSODetail.jasper";
		

		//param.put("TglUpto",  vTglUpTo); 
//		param.put("YearFrom",  vYearFrom); 
//		param.put("YearUpto",  vYearUpto); 

		param.put("EstRealFrom",  vTglFrom); 
		param.put("EstRealUpto",  vTglUpto); 
		
		param.put("Cabang",  vCabang); 
		param.put("SalesFrom",  vSalesFrom); 
		param.put("SalesUpto",  vSalesUpto); 
		param.put("LocCode",  vLocation); 
		
		
		param.put("JenisSO",  vJenis); 
		param.put("JenisPO",  vJenisPO);
		param.put("Status",  vStatus); 
		
		param.put("AdaPS",  vJenisItem); 
		param.put("Detail",  vLap); 
		//System.out.println("vNilai : "+vNilai);
		param.put("Nilai",  vNilai); 
		
		
		param.put("Company",  vCompany); 
		
		param.put("ProsesId",  vProsesId); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, vCabang, "DELETE");

		

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



