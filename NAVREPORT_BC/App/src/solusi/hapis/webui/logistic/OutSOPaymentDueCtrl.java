package solusi.hapis.webui.logistic;


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

public class OutSOPaymentDueCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglParamFrom;  
	
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	protected Textbox txtNoSOFrom;
	protected Textbox txtNoSOUpto;
		
	
	protected Textbox txtSalesFrom;
	protected Textbox txtSalesUpto;
	
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	
	protected Combobox  cmbJenis;
	protected Combobox  cmbJenisPO;
	protected Combobox  cmbCritical;
	
	protected Bandbox  cmbLocation;
	protected Listbox listLocation;
	protected String vLocation = "ALL";
		
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	protected Radiogroup rdgJnsRpt;	 
	protected Radio rdN;
	protected Radio rdW;
	protected Radio rdC;
	protected Radio rdM;
	
	protected Decimalbox dcmNilai;
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	dbTglParamFrom.setValue((new Date()));  
    	
    	rdALL.setSelected(true); 
    	rdC.setSelected(true); 
    
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
		
		txtSalesUpto.setValue("ZZZ");
    	
    	txtNoSOUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
    	
    	cmbJenis.setSelectedIndex(0);

    	cmbJenisPO.setSelectedIndex(0);
    	
    	cmbCritical.setSelectedIndex(0);

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

		dcmNilai.setValue(new BigDecimal (200));
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
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJnsRpt = "W";
		if (StringUtils.isNotEmpty(rdgJnsRpt.getSelectedItem().getValue())) {
			vJnsRpt = rdgJnsRpt.getSelectedItem().getValue();	
		} 
		
		
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
		Date vR7From = new Date();
		Date vR7Upto = new Date();
		Date vR8From = new Date();
		Date vR8Upto = new Date();
		Date vR9From = new Date();
		Date vR9Upto = new Date();
		Date vR10From = new Date();
		Date vR10Upto = new Date();		
		Date vR11From = new Date();
		Date vR11Upto = new Date();		
		Date vR12From = new Date();
		Date vR12Upto = new Date();
		
				
		Date vTglParamFrom = new Date();   
		if(CommonUtils.isNotEmpty(dbTglParamFrom.getValue()) == true){  
			vTglParamFrom = dbTglParamFrom.getValue();
		}   
		
		if (vJnsRpt.equals("N") == true)	{
		
			Calendar cTglFrom = Calendar.getInstance();		
			cTglFrom.setTime(vTglParamFrom);
			
	
			int yearTglFrom = cTglFrom.get(Calendar.YEAR);
			int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
			int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
			    
			vR1From = cTglFrom.getTime();
			String dR1Upto="1/1/1900";
			String dR2From="1/1/1900";
			String dR2Upto="1/1/1900";		
			String dR3From="1/1/1900";
			String dR3Upto="1/1/1900";		
			String dR4From="1/1/1900";
			String dR4Upto="1/1/1900";		
			String dR5From="1/1/1900";
			String dR5Upto="1/1/1900";					
			String dR6From="1/1/1900";
			String dR6Upto="1/1/1900";		
			String dR7From="1/1/1900";
			String dR7Upto="1/1/1900";
			
			if (dayTglFrom <= 15 ){
	
				//vR1Upto
				Calendar cR1Upto = Calendar.getInstance();
				cR1Upto.setTime(vR1From);
				cR1Upto.set(Calendar.DAY_OF_MONTH, cR1Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
				vR1Upto = cR1Upto.getTime();
				
				if(monthTglFrom+1 <=12){
					dR2From = "1/"+(monthTglFrom+1)+"/"+yearTglFrom;
					dR2Upto = "15/"+(monthTglFrom+1)+"/"+yearTglFrom;
					
					dR3From = "16/"+(monthTglFrom+1)+"/"+yearTglFrom;
					
					if(monthTglFrom+2 <=12){
						dR4From = "1/"+(monthTglFrom+2)+"/"+yearTglFrom;
						dR4Upto = "15/"+(monthTglFrom+2)+"/"+yearTglFrom;
						
						dR5From = "16/"+(monthTglFrom+2)+"/"+yearTglFrom;
						
						if(monthTglFrom+3 <=12){
							dR6From = "1/"+(monthTglFrom+3)+"/"+yearTglFrom;
							dR6Upto = "15/"+(monthTglFrom+3)+"/"+yearTglFrom;
							
							dR7From = "16/"+(monthTglFrom+3)+"/"+yearTglFrom;
							
							if(monthTglFrom+4 <=12){
								dR7Upto = "15/"+(monthTglFrom+4)+"/"+yearTglFrom;
							} else {
								dR7Upto = "15/1/"+(yearTglFrom+1);
							}
						} else {
							dR6From = "1/1/"+(yearTglFrom+1);
							dR6Upto = "15/1/"+(yearTglFrom+1);
							
							dR7From = "16/1/"+(yearTglFrom+1);
							dR7Upto = "15/2/"+(yearTglFrom+1);
						}
					} else {
						dR4From = "1/1/"+(yearTglFrom+1);
						dR4Upto = "15/1/"+(yearTglFrom+1);
						
						dR5From = "16/1/"+(yearTglFrom+1);
						
						dR6From = "1/2/"+(yearTglFrom+1);
						dR6Upto = "15/2/"+(yearTglFrom+1);
						
						dR7From = "16/2/"+(yearTglFrom+1);
						dR7Upto = "15/3/"+(yearTglFrom+1);
					}
				} else {
					dR2From = "1/1/"+(yearTglFrom+1);
					dR2Upto = "15/1/"+(yearTglFrom+1);
					
					dR3From = "16/1/"+(yearTglFrom+1);
					
					dR4From = "1/2/"+(yearTglFrom+1);
					dR4Upto = "15/2/"+(yearTglFrom+1);
					
					dR5From = "16/2/"+(yearTglFrom+1);
					
					dR6From = "1/3/"+(yearTglFrom+1);
					dR6Upto = "15/3/"+(yearTglFrom+1);
					
					dR7From = "16/3/"+(yearTglFrom+1);
					dR7Upto = "15/4/"+(yearTglFrom+1);
					
				}
				
				//vR2From
				SimpleDateFormat dfR2From= new SimpleDateFormat("dd/MM/yyyy");
				vR2From = dfR2From.parse(dR2From);
				
				//vR2Upto
				SimpleDateFormat dfR2Upto= new SimpleDateFormat("dd/MM/yyyy");
				vR2Upto = dfR2Upto.parse(dR2Upto);
					
				//vR3From
				SimpleDateFormat dfR3From= new SimpleDateFormat("dd/MM/yyyy");
				vR3From = dfR3From.parse(dR3From);
				
				//vR3Upto
				Calendar cR3Upto = Calendar.getInstance();
				cR3Upto.setTime(vR3From);
				cR3Upto.set(Calendar.DAY_OF_MONTH, cR3Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
				vR3Upto = cR3Upto.getTime();
			
				//vR4From
				SimpleDateFormat dfR4From= new SimpleDateFormat("dd/MM/yyyy");
				vR4From = dfR4From.parse(dR4From);
				
				//vR4Upto
				SimpleDateFormat dfR4Upto= new SimpleDateFormat("dd/MM/yyyy");
				vR4Upto = dfR4Upto.parse(dR4Upto);
				
				//vR5From
				SimpleDateFormat dfR5From= new SimpleDateFormat("dd/MM/yyyy");
				vR5From = dfR5From.parse(dR5From);
				
				//vR5Upto
				Calendar cR5Upto = Calendar.getInstance();
				cR5Upto.setTime(vR5From);
				cR5Upto.set(Calendar.DAY_OF_MONTH, cR5Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
				vR5Upto = cR5Upto.getTime();
				
				//vR6From
				SimpleDateFormat dfR6From= new SimpleDateFormat("dd/MM/yyyy");
				vR6From = dfR6From.parse(dR6From);
				
				//vR6Upto
				SimpleDateFormat dfR6Upto= new SimpleDateFormat("dd/MM/yyyy");
				vR6Upto = dfR6Upto.parse(dR6Upto);
				
				//vR7From
				SimpleDateFormat dfR7From= new SimpleDateFormat("dd/MM/yyyy");
				vR7From = dfR7From.parse(dR7From);
				
				//vR7Upto
				SimpleDateFormat dfR7Upto= new SimpleDateFormat("dd/MM/yyyy");
				vR7Upto = dfR7Upto.parse(dR7Upto);
				
						
				
			} else {
						
				if(monthTglFrom+1 <=12){
					dR1Upto = "15/"+(monthTglFrom+1)+"/"+yearTglFrom;
					
					dR2From = "16/"+(monthTglFrom+1)+"/"+yearTglFrom;
						
					if(monthTglFrom+2 <=12){
						dR3From = "1/"+(monthTglFrom+2)+"/"+yearTglFrom;
						dR3Upto = "15/"+(monthTglFrom+2)+"/"+yearTglFrom;
						
						dR4From = "16/"+(monthTglFrom+2)+"/"+yearTglFrom;
						
						if(monthTglFrom+3 <=12){
							dR5From = "1/"+(monthTglFrom+3)+"/"+yearTglFrom;
							dR5Upto = "15/"+(monthTglFrom+3)+"/"+yearTglFrom;
							
							dR6From = "16/"+(monthTglFrom+3)+"/"+yearTglFrom;
							
							if(monthTglFrom+4 <=12){
								dR7From = "1/"+(monthTglFrom+4)+"/"+yearTglFrom;
							} else {
								dR7From = "1/1/"+(yearTglFrom+1);
							}
						} else {
							dR5From = "1/1/"+(yearTglFrom+1);
							dR5Upto = "15/1/"+(yearTglFrom+1);
							
							dR6From = "16/1/"+(yearTglFrom+1);
							
							dR7From = "1/2/"+(yearTglFrom+1);
						}
					
					} else {
						dR3From = "1/1/"+(yearTglFrom+1);
						dR3Upto = "15/1/"+(yearTglFrom+1);
						
						dR4From = "16/1/"+(yearTglFrom+1);
			
						dR5From = "1/2/"+(yearTglFrom+1);
						dR5Upto = "15/2/"+(yearTglFrom+1);
						
						dR6From = "16/2/"+(yearTglFrom+1);
						
						dR7From = "1/3/"+(yearTglFrom+1);
					}
				} else {
					dR1Upto = "15/1/"+(yearTglFrom+1);
					
					dR2From = "16/1/"+(yearTglFrom+1);
					
					dR3From = "1/2/"+(yearTglFrom+1);
					dR3Upto = "15/2/"+(yearTglFrom+1);
					
					dR4From = "16/2/"+(yearTglFrom+1);
		
					dR5From = "1/3/"+(yearTglFrom+1);
					dR5Upto = "15/3/"+(yearTglFrom+1);
					
					dR6From = "16/3/"+(yearTglFrom+1);
					
					dR7From = "1/4/"+(yearTglFrom+1);
				}
				
				//vR1Upto
				SimpleDateFormat dfR1Upto= new SimpleDateFormat("dd/MM/yyyy");
				vR1Upto = dfR1Upto.parse(dR1Upto);
				
				//vR2From
				SimpleDateFormat dfR2From= new SimpleDateFormat("dd/MM/yyyy");
				vR2From = dfR2From.parse(dR2From);
				
				//vR2Upto
				Calendar cR2Upto = Calendar.getInstance();
				cR2Upto.setTime(vR2From);
				cR2Upto.set(Calendar.DAY_OF_MONTH, cR2Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
				vR2Upto = cR2Upto.getTime();
				
				//vR3From
				SimpleDateFormat dfR3From= new SimpleDateFormat("dd/MM/yyyy");
				vR3From = dfR3From.parse(dR3From);
				
				//vR3Upto
				SimpleDateFormat dfR3Upto= new SimpleDateFormat("dd/MM/yyyy");
				vR3Upto = dfR3Upto.parse(dR3Upto);
				
				//vR4From
				SimpleDateFormat dfR4From= new SimpleDateFormat("dd/MM/yyyy");
				vR4From = dfR4From.parse(dR4From);
				
				//vR4Upto
				Calendar cR4Upto = Calendar.getInstance();
				cR4Upto.setTime(vR4From);
				cR4Upto.set(Calendar.DAY_OF_MONTH, cR4Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
				vR4Upto = cR4Upto.getTime();
				
				//vR5From
				SimpleDateFormat dfR5From= new SimpleDateFormat("dd/MM/yyyy");
				vR5From = dfR5From.parse(dR5From);
				
				//vR5Upto
				SimpleDateFormat dfR5Upto= new SimpleDateFormat("dd/MM/yyyy");
				vR5Upto = dfR5Upto.parse(dR5Upto);
				
				//vR6From
				SimpleDateFormat dfR6From= new SimpleDateFormat("dd/MM/yyyy");
				vR6From = dfR6From.parse(dR6From);
				
				//vR6Upto
				Calendar cR6Upto = Calendar.getInstance();
				cR6Upto.setTime(vR6From);
				cR6Upto.set(Calendar.DAY_OF_MONTH, cR6Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
				vR6Upto = cR6Upto.getTime();
				
				//vR7From
				SimpleDateFormat dfR7From= new SimpleDateFormat("dd/MM/yyyy");
				vR7From = dfR7From.parse(dR7From);
				
				//vR7Upto
				Calendar cR7Upto = Calendar.getInstance();
				cR7Upto.setTime(vR7From);
				cR7Upto.set(Calendar.DAY_OF_MONTH, cR7Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
				vR7Upto = cR7Upto.getTime();
				
				
			}
		} else {
			if (vJnsRpt.equals("W") == true)	{
				Calendar cTglFrom = Calendar.getInstance();		
				cTglFrom.setTime(vTglParamFrom);
				
				vR1From = cTglFrom.getTime();
	
				//vR1Upto
				Calendar cR1Upto = Calendar.getInstance();
				cR1Upto.setTime(vR1From);
				cR1Upto.set(Calendar.DAY_OF_WEEK, cR1Upto.getActualMaximum(Calendar.DAY_OF_WEEK));
				cR1Upto.add(Calendar.DAY_OF_MONTH, 1); 
				vR1Upto = cR1Upto.getTime();
							
				Calendar cR2From = Calendar.getInstance();
				cR2From.setTime(vR1Upto);			
				cR2From.add(Calendar.DAY_OF_MONTH, 1);  
				vR2From = cR2From.getTime();
				
				Calendar cR2Upto = Calendar.getInstance();
				cR2Upto.setTime(vR2From);			
				cR2Upto.add(Calendar.DAY_OF_MONTH, 6);  
				vR2Upto = cR2Upto.getTime();
				
				Calendar cR3From = Calendar.getInstance();
				cR3From.setTime(vR2Upto);			
				cR3From.add(Calendar.DAY_OF_MONTH, 1);  
				vR3From = cR3From.getTime();
				
				Calendar cR3Upto = Calendar.getInstance();
				cR3Upto.setTime(vR3From);			
				cR3Upto.add(Calendar.DAY_OF_MONTH, 6);  
				vR3Upto = cR3Upto.getTime();
				
				Calendar cR4From = Calendar.getInstance();
				cR4From.setTime(vR3Upto);			
				cR4From.add(Calendar.DAY_OF_MONTH, 1);  
				vR4From = cR4From.getTime();
				
				Calendar cR4Upto = Calendar.getInstance();
				cR4Upto.setTime(vR4From);			
				cR4Upto.add(Calendar.DAY_OF_MONTH, 6);  
				vR4Upto = cR4Upto.getTime();
				
				Calendar cR5From = Calendar.getInstance();
				cR5From.setTime(vR4Upto);			
				cR5From.add(Calendar.DAY_OF_MONTH, 1);  
				vR5From = cR5From.getTime();
				
				Calendar cR5Upto = Calendar.getInstance();
				cR5Upto.setTime(vR5From);			
				cR5Upto.add(Calendar.DAY_OF_MONTH, 6);  
				vR5Upto = cR5Upto.getTime();
				
				Calendar cR6From = Calendar.getInstance();
				cR6From.setTime(vR5Upto);			
				cR6From.add(Calendar.DAY_OF_MONTH, 1);  
				vR6From = cR6From.getTime();
				
				Calendar cR6Upto = Calendar.getInstance();
				cR6Upto.setTime(vR6From);			
				cR6Upto.add(Calendar.DAY_OF_MONTH, 6);  
				vR6Upto = cR6Upto.getTime();
				
				Calendar cR7From = Calendar.getInstance();
				cR7From.setTime(vR6Upto);			
				cR7From.add(Calendar.DAY_OF_MONTH, 1);  
				vR7From = cR7From.getTime();
				
				Calendar cR7Upto = Calendar.getInstance();
				cR7Upto.setTime(vR7From);			
				cR7Upto.add(Calendar.DAY_OF_MONTH, 6);  
				vR7Upto = cR7Upto.getTime();
				
				Calendar cR8From = Calendar.getInstance();
				cR8From.setTime(vR7Upto);			
				cR8From.add(Calendar.DAY_OF_MONTH, 1);  
				vR8From = cR8From.getTime();
				
				Calendar cR8Upto = Calendar.getInstance();
				cR8Upto.setTime(vR8From);			
				cR8Upto.add(Calendar.DAY_OF_MONTH, 6);  
				vR8Upto = cR8Upto.getTime();
				
				Calendar cR9From = Calendar.getInstance();
				cR9From.setTime(vR8Upto);			
				cR9From.add(Calendar.DAY_OF_MONTH, 1);  
				vR9From = cR9From.getTime();
				
				Calendar cR9Upto = Calendar.getInstance();
				cR9Upto.setTime(vR9From);			
				cR9Upto.add(Calendar.DAY_OF_MONTH, 13);  
				vR9Upto = cR9Upto.getTime();
			} else {
				if (vJnsRpt.equals("C") == true)	{
					Calendar cTglFrom = Calendar.getInstance();		
					cTglFrom.setTime(vTglParamFrom);
					
					vR1From = cTglFrom.getTime();
		
					//vR1Upto
					Calendar cR1Upto = Calendar.getInstance();
					cR1Upto.setTime(vR1From);
					cR1Upto.set(Calendar.DAY_OF_WEEK, cR1Upto.getActualMaximum(Calendar.DAY_OF_WEEK));
					cR1Upto.add(Calendar.DAY_OF_MONTH, 1); 
					vR1Upto = cR1Upto.getTime();
								
					Calendar cR2From = Calendar.getInstance();
					cR2From.setTime(vR1Upto);			
					cR2From.add(Calendar.DAY_OF_MONTH, 1);  
					vR2From = cR2From.getTime();
					
					Calendar cR2Upto = Calendar.getInstance();
					cR2Upto.setTime(vR2From);			
					cR2Upto.add(Calendar.DAY_OF_MONTH, 6);  
					vR2Upto = cR2Upto.getTime();
					
					Calendar cR3From = Calendar.getInstance();
					cR3From.setTime(vR2Upto);			
					cR3From.add(Calendar.DAY_OF_MONTH, 1);  
					vR3From = cR3From.getTime();
					
					Calendar cR3Upto = Calendar.getInstance();
					cR3Upto.setTime(vR3From);			
					cR3Upto.add(Calendar.DAY_OF_MONTH, 6);  
					vR3Upto = cR3Upto.getTime();
					
					Calendar cR4From = Calendar.getInstance();
					cR4From.setTime(vR3Upto);			
					cR4From.add(Calendar.DAY_OF_MONTH, 1);  
					vR4From = cR4From.getTime();
					
					Calendar cR4Upto = Calendar.getInstance();
					cR4Upto.setTime(vR4From);			
					cR4Upto.add(Calendar.DAY_OF_MONTH, 6);  
					vR4Upto = cR4Upto.getTime();
					
					Calendar cR5From = Calendar.getInstance();
					cR5From.setTime(vR4Upto);			
					cR5From.add(Calendar.DAY_OF_MONTH, 1);  
					vR5From = cR5From.getTime();
					
					Calendar cR5Upto = Calendar.getInstance();
					cR5Upto.setTime(vR5From);			
					cR5Upto.add(Calendar.DAY_OF_MONTH, 13);  
					vR5Upto = cR5Upto.getTime();
					
					Calendar cR6From = Calendar.getInstance();
					cR6From.setTime(vR5Upto);			
					cR6From.add(Calendar.DAY_OF_MONTH, 1);  
					vR6From = cR6From.getTime();
					
					Calendar cR6Upto = Calendar.getInstance();
					cR6Upto.setTime(vR6From);			
					cR6Upto.add(Calendar.DAY_OF_MONTH, 13);  
					vR6Upto = cR6Upto.getTime();
					
					Calendar cR7From = Calendar.getInstance();
					cR7From.setTime(vR6Upto);			
					cR7From.add(Calendar.DAY_OF_MONTH, 1);  
					vR7From = cR7From.getTime();
					
					Calendar cR7Upto = Calendar.getInstance();
					cR7Upto.setTime(vR7From);			
					cR7Upto.add(Calendar.DAY_OF_MONTH, 13);  
					vR7Upto = cR7Upto.getTime();
					
					Calendar cR8From = Calendar.getInstance();
					cR8From.setTime(vR7Upto);			
					cR8From.add(Calendar.DAY_OF_MONTH, 1);  
					vR8From = cR8From.getTime();
					
					Calendar cR8Upto = Calendar.getInstance();
					cR8Upto.setTime(vR8From);			
					cR8Upto.add(Calendar.DAY_OF_MONTH, 13);  
					vR8Upto = cR8Upto.getTime();
					
					Calendar cR9From = Calendar.getInstance();
					cR9From.setTime(vR8Upto);			
					cR9From.add(Calendar.DAY_OF_MONTH, 1);  
					vR9From = cR9From.getTime();
					
					Calendar cR9Upto = Calendar.getInstance();
					cR9Upto.setTime(vR9From);			
					cR9Upto.add(Calendar.MONTH, 1);  
					vR9Upto = cR9Upto.getTime();
					
					Calendar cR10From = Calendar.getInstance();
					cR10From.setTime(vR9Upto);			
					cR10From.add(Calendar.DAY_OF_MONTH, 1);  
					vR10From = cR10From.getTime();
					
					Calendar cR10Upto = Calendar.getInstance();
					cR10Upto.setTime(vR10From);			
					cR10Upto.add(Calendar.MONTH, 1);  
					vR10Upto = cR10Upto.getTime();
					
					
					Calendar cR11From = Calendar.getInstance();
					cR11From.setTime(vR10Upto);			
					cR11From.add(Calendar.DAY_OF_MONTH, 1);  
					vR11From = cR11From.getTime();
					
					Calendar cR11Upto = Calendar.getInstance();
					cR11Upto.setTime(vR11From);			
					cR11Upto.add(Calendar.MONTH, 1);  
					vR11Upto = cR11Upto.getTime();
					
				} else {
					Calendar cTglFrom = Calendar.getInstance();		
					cTglFrom.setTime(vTglParamFrom);
					
			
					int yearTglFrom = cTglFrom.get(Calendar.YEAR);
					int monthTglFrom = cTglFrom.get(Calendar.MONTH);
					
					String dR1From = "1/"+(monthTglFrom+1)+"/"+yearTglFrom;
					SimpleDateFormat dfR1From= new SimpleDateFormat("dd/MM/yyyy");
					vR1From = dfR1From.parse(dR1From);
					
					
					Calendar cR1Upto = Calendar.getInstance();
					cR1Upto.setTime(vR1From);		
					cR1Upto.set(Calendar.DAY_OF_MONTH, cR1Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR1Upto = cR1Upto.getTime();
					
					Calendar cR2From = Calendar.getInstance();
					cR2From.setTime(vR1Upto);		
					cR2From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR2From = cR2From.getTime();
					
					Calendar cR2Upto = Calendar.getInstance();
					cR2Upto.setTime(vR2From);		
					cR2Upto.set(Calendar.DAY_OF_MONTH, cR2Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR2Upto = cR2Upto.getTime();
					
					
					Calendar cR3From = Calendar.getInstance();
					cR3From.setTime(vR2Upto);		
					cR3From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR3From = cR3From.getTime();
					
					Calendar cR3Upto = Calendar.getInstance();
					cR3Upto.setTime(vR3From);		
					cR3Upto.set(Calendar.DAY_OF_MONTH, cR3Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR3Upto = cR3Upto.getTime();
					
					Calendar cR4From = Calendar.getInstance();
					cR4From.setTime(vR3Upto);		
					cR4From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR4From = cR4From.getTime();
					
					Calendar cR4Upto = Calendar.getInstance();
					cR4Upto.setTime(vR4From);		
					cR4Upto.set(Calendar.DAY_OF_MONTH, cR4Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR4Upto = cR4Upto.getTime();
					
					Calendar cR5From = Calendar.getInstance();
					cR5From.setTime(vR4Upto);		
					cR5From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR5From = cR5From.getTime();
					
					Calendar cR5Upto = Calendar.getInstance();
					cR5Upto.setTime(vR5From);		
					cR5Upto.set(Calendar.DAY_OF_MONTH, cR5Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR5Upto = cR5Upto.getTime();
					
					Calendar cR6From = Calendar.getInstance();
					cR6From.setTime(vR5Upto);		
					cR6From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR6From = cR6From.getTime();
					
					Calendar cR6Upto = Calendar.getInstance();
					cR6Upto.setTime(vR6From);		
					cR6Upto.set(Calendar.DAY_OF_MONTH, cR6Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR6Upto = cR6Upto.getTime();
					
					
					Calendar cR7From = Calendar.getInstance();
					cR7From.setTime(vR6Upto);		
					cR7From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR7From = cR7From.getTime();
					
					Calendar cR7Upto = Calendar.getInstance();
					cR7Upto.setTime(vR7From);		
					cR7Upto.set(Calendar.DAY_OF_MONTH, cR7Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR7Upto = cR7Upto.getTime();
					
					Calendar cR8From = Calendar.getInstance();
					cR8From.setTime(vR7Upto);		
					cR8From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR8From = cR8From.getTime();
					
					Calendar cR8Upto = Calendar.getInstance();
					cR8Upto.setTime(vR8From);		
					cR8Upto.set(Calendar.DAY_OF_MONTH, cR8Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR8Upto = cR8Upto.getTime();
					
					Calendar cR9From = Calendar.getInstance();
					cR9From.setTime(vR8Upto);		
					cR9From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR9From = cR9From.getTime();
					
					Calendar cR9Upto = Calendar.getInstance();
					cR9Upto.setTime(vR9From);		
					cR9Upto.set(Calendar.DAY_OF_MONTH, cR9Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR9Upto = cR9Upto.getTime();
					
					Calendar cR10From = Calendar.getInstance();
					cR10From.setTime(vR9Upto);		
					cR10From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR10From = cR10From.getTime();
					
					Calendar cR10Upto = Calendar.getInstance();
					cR10Upto.setTime(vR10From);		
					cR10Upto.set(Calendar.DAY_OF_MONTH, cR10Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR10Upto = cR10Upto.getTime();
					
					Calendar cR11From = Calendar.getInstance();
					cR11From.setTime(vR10Upto);		
					cR11From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR11From = cR11From.getTime();
					
					Calendar cR11Upto = Calendar.getInstance();
					cR11Upto.setTime(vR11From);		
					cR11Upto.set(Calendar.DAY_OF_MONTH, cR11Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR11Upto = cR11Upto.getTime();
					
					Calendar cR12From = Calendar.getInstance();
					cR12From.setTime(vR11Upto);		
					cR12From.add(Calendar.DAY_OF_MONTH, 1); 			
					vR12From = cR12From.getTime();
					
					Calendar cR12Upto = Calendar.getInstance();
					cR12Upto.setTime(vR12From);		
					cR12Upto.set(Calendar.DAY_OF_MONTH, cR12Upto.getActualMaximum(Calendar.DAY_OF_MONTH));				
					vR12Upto = cR12Upto.getTime();
				}
			}
		}
		
		
		
		String vSalesFrom = ".";
		if (StringUtils.isNotEmpty(txtSalesFrom.getValue())) {
			vSalesFrom = txtSalesFrom.getValue();
		} 
		
		String vSalesUpto = "ZZZ";
		if (StringUtils.isNotEmpty(txtSalesUpto.getValue())) {
			vSalesUpto = txtSalesUpto.getValue();
		} 
		
		
		String vNoSOFrom = ".";
		if (StringUtils.isNotEmpty(txtNoSOFrom.getValue())) {
			vNoSOFrom = txtNoSOFrom.getValue();
		} 
		
		String vNoSOUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtNoSOUpto.getValue())) {
			vNoSOUpto = txtNoSOUpto.getValue();
		} 
		
		String vJenis = "ALL";
		if (cmbJenis.getSelectedItem().getValue() != null){
			vJenis = (String) cmbJenis.getSelectedItem().getValue();
		}
		
		String vJenisPO = "ALL";
		if (cmbJenisPO.getSelectedItem().getValue() != null){
			vJenisPO = (String) cmbJenisPO.getSelectedItem().getValue();
		}
		
		
		
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
		
		
		
		String vCritical = "ALL";
		if (cmbCritical.getSelectedItem().getValue() != null){
			vCritical = (String) cmbCritical.getSelectedItem().getValue();
		}
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom); 
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0305010");
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, "ALL", "CETAK");

		BigDecimal vNilai = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03035_OutSOPaymentDue.jasper";
		param.put("AmtShow",  vNilai);
		
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
		
		param.put("R7From",  vR7From); 
		param.put("R7Upto",  vR7Upto);
		
		param.put("R8From",  vR8From); 
		param.put("R8Upto",  vR8Upto); 
		
		param.put("R9From",  vR9From); 
		param.put("R9Upto",  vR9Upto); 
		
		param.put("R10From",  vR10From); 
		param.put("R10Upto",  vR10Upto);
		
		param.put("R11From",  vR11From); 
		param.put("R11Upto",  vR11Upto);
		
		param.put("R12From",  vR12From); 
		param.put("R12Upto",  vR12Upto);
		
		
		param.put("JnsRpt",  vJnsRpt); 
		
		param.put("Cabang",  vCabang); 
		param.put("SalesFrom",  vSalesFrom); 
		param.put("SalesUpto",  vSalesUpto); 
		param.put("LocCode",  vLocation); 
		param.put("NoSOFrom",  vNoSOFrom); 
		param.put("NoSOUpto",  vNoSOUpto); 		
		param.put("JenisSO",  vJenis); 
		param.put("JenisPO",  vJenisPO); 		
		param.put("Critical",  vCritical);		
		param.put("EstRealFrom",  vTglFrom); 
		param.put("EstRealUpto",  vTglUpto);
		
		param.put("ProsesId",  vProsesId); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, "ALL", "DELETE");

		 
		
	}
 
}