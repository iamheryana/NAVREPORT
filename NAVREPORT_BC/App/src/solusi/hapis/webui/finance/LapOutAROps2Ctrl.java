package solusi.hapis.webui.finance;

import java.io.Serializable;
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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapOutAROps2Ctrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Datebox dbTglFrom;
		
	protected Textbox txtKodeCustFrom;
	protected Textbox txtKodeCustUpto;
	
	protected Combobox  cmbCurrency;
	
	protected Intbox intPeriode;
	protected Radiogroup rdgPeriode;	 
	protected Radio rdHari;
	protected Radio rdMinggu;
	protected Radio rdBulan;
	protected Radio rdTahun;
	
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	

	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		dbTglFrom.setValue((new Date())); 
		
    	txtKodeCustUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
    	
    	rdAJ.setSelected(true); 
    	
    	cmbCurrency.setSelectedIndex(0);

    	intPeriode.setValue(1);
    	
    	rdBulan.setSelected(true); 
    	
        
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
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		
/*		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}		*/	
			
		
		String vKodeCustFrom = ".";
		if (StringUtils.isNotEmpty(txtKodeCustFrom.getValue())) {
			vKodeCustFrom = txtKodeCustFrom.getValue();
		} 
		
		String vKodeCustUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtKodeCustUpto.getValue())) {
			vKodeCustUpto = txtKodeCustUpto.getValue();
		} 
		
		String vCurrency = "ALL";
		if (cmbCurrency.getSelectedItem().getValue() != null){
			vCurrency = (String) cmbCurrency.getSelectedItem().getValue();
		}
		
//		String vCabang = "ALL";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCabang = (String) cmbCab.getSelectedItem().getValue();
//		}
//		
		
//		System.out.println("vCompany : "+vCompany);
//		System.out.println("vTglFrom : "+vTglFrom);
//		System.out.println("vPeriodeLenghth : "+vPeriodeLenghth);
//		System.out.println("vR1From : "+vR1From);
//		System.out.println("vR1Upto : "+vR1Upto);
		
		Date vTglFrom = new Date();   
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		int vPeriodeLenghth = 2;
		if(CommonUtils.isNotEmpty(intPeriode.getValue()) == true){
			vPeriodeLenghth = intPeriode.getValue();
		}
		
		String vPeriode = "W";
		if (StringUtils.isNotEmpty(rdgPeriode.getSelectedItem().getValue())) {
			vPeriode = rdgPeriode.getSelectedItem().getValue();	
		} 

		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTglFrom);
		
		Date vR1From = new Date();
		Date vR1Upto = new Date();
		Date vR2From = new Date();
		Date vR2Upto = new Date();
		Date vR3From = new Date();
		Date vR3Upto = new Date();
		Date vR4From = new Date();
		Date vR4Upto = new Date();
		Date vR5From = new Date();
		Date vR5Upto = new Date();
		Date vR6From = new Date();
		Date vR6Upto = new Date();
		if (vPeriode.equals("D")){
			vR1From = cTglFrom.getTime();
			
			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR2From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR3From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR4From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR5From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR6From= cTglFrom.getTime();
			
			Calendar cTglUpto = Calendar.getInstance();		
			cTglUpto.setTime(vR2From);
			
			cTglUpto.add(Calendar.DAY_OF_YEAR, -1);
			vR1Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR2Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR3Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR4Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR5Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
			vR6Upto = cTglUpto.getTime();
		}
		
		if (vPeriode.equals("W")){
			vR1From = cTglFrom.getTime();
			
			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR2From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR3From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR4From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR5From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR6From= cTglFrom.getTime();
			
			Calendar cTglUpto = Calendar.getInstance();		
			cTglUpto.setTime(vR2From);
			
			cTglUpto.add(Calendar.DAY_OF_YEAR, -1);
			vR1Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR2Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR3Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR4Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR5Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
			vR6Upto = cTglUpto.getTime();
		}
		
		if (vPeriode.equals("M")){
			vR1From = cTglFrom.getTime();
			
			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
			vR2From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
			vR3From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
			vR4From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
			vR5From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
			vR6From= cTglFrom.getTime();
			
			Calendar cTglUpto = Calendar.getInstance();		
			cTglUpto.setTime(vR2From);
			
			cTglUpto.add(Calendar.DAY_OF_YEAR, -1);
			vR1Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
			vR2Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
			vR3Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
			vR4Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
			vR5Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
			vR6Upto = cTglUpto.getTime();
		}
		
		if (vPeriode.equals("Y")){
			vR1From = cTglFrom.getTime();
			
			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
			vR2From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
			vR3From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
			vR4From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
			vR5From= cTglFrom.getTime();
			
			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
			vR6From= cTglFrom.getTime();
			
			Calendar cTglUpto = Calendar.getInstance();		
			cTglUpto.setTime(vR2From);
			
			cTglUpto.add(Calendar.DAY_OF_YEAR, -1);
			vR1Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
			vR2Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
			vR3Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
			vR4Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
			vR5Upto = cTglUpto.getTime();
			
			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
			vR6Upto = cTglUpto.getTime();
		}
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0201004");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02017_OutAROperational.jasper";
		

		param.put("Company",  vCompany); 
		
		
		param.put("R1From",  vR1From); 
		param.put("R1Upto",  vR1Upto); 
		
		param.put("R2From",  vR2From); 
		param.put("R2Upto",  vR2Upto); 
		
		param.put("R3From",  vR3From); 
		param.put("R3Upto",  vR3Upto); 
		
		param.put("R4From",  vR4From); 
		param.put("R4Upto",  vR4Upto); 
		
		param.put("R5From",  vR5From); 
		param.put("R5Upto",  vR5Upto); 
		
		param.put("R6From",  vR6From); 
		param.put("R6Upto",  vR6Upto); 
		
		
		param.put("CustFrom",  vKodeCustFrom); 
		param.put("CustUpto",  vKodeCustUpto); 
		
		param.put("Currency",  vCurrency); 
		

		param.put("Cabang",  vCabang); 
		
		param.put("EndingDate",  new Date());
		
			
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}