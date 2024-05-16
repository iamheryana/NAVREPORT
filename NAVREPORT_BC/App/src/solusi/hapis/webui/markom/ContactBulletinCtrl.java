package solusi.hapis.webui.markom;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ContactBulletinCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Checkbox  ch1;
	protected Checkbox  ch2;
	protected Checkbox  ch3;
	protected Checkbox  ch4;
	protected Checkbox  ch5;
	protected Checkbox  ch6;
	protected Checkbox  ch7;
	protected Checkbox  ch8;
	
	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = "ALL";
	
	private SelectQueryService selectQueryService;
	
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	protected Radiogroup rdgJnsCnt;	 
	protected Radio rdALL;
	protected Radio rdCUS;
	protected Radio rdNCUS;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
  	
		rdALL.setSelected(true);
		
	   	ch1.setChecked(false);
	   	ch2.setChecked(false);
	   	ch3.setChecked(false);
	   	ch4.setChecked(false);
	   	ch5.setChecked(false);
	   	ch6.setChecked(false);
	   	ch7.setChecked(false);
	   	ch8.setChecked(false);

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
		listSales.setAutopaging(false);
		listSales.setWidth("400px");
		listSales.addEventListener(Events.ON_SELECT, selectSales());
		listSales.setParent(popup2);
		popup2.setParent(cmbSales);
	        
		listSales.appendItem("ALL", "ALL");
		listSales.appendItem("<SALES ACTIVE>", "ACTIVE");
			
		List<Object[]> vResultSales = selectQueryService.QuerySalesmanForContact();
		int vSalesIndex = 0;
		if(CommonUtils.isNotEmpty(vResultSales)){
			int vIndex = 1;
			
			String vUserName = SecurityContextHolder.getContext().getAuthentication().getName();
			for(Object[] aRslt : vResultSales){
				listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
				
				
				if((aRslt[1].toString()).equals(vUserName) == true){
					vSalesIndex = vIndex;
					vSalesIndex = vSalesIndex + 1;
					vSales = vUserName;
				}
				
				vIndex = vIndex + 1;
				
			}
		}
		
				
		
		cmbSales.setValue(listSales.getItemAtIndex(vSalesIndex).getLabel());
		listSales.setSelectedItem(listSales.getItemAtIndex(vSalesIndex));
    	
		
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

		String vBulletin = ".";
		if(ch1.isChecked() == true){
			vBulletin ="X";
		} else {
			vBulletin =".";
		}
		
		String vCalender = ".";
		if(ch2.isChecked() == true){
			vCalender ="X";
		} else {
			vCalender =".";
		}
		
		String vEBlast = ".";
		if(ch3.isChecked() == true){
			vEBlast ="X";
		} else {
			vEBlast =".";
		}
		
		String vIdulFitri = ".";
		if(ch4.isChecked() == true){
			vIdulFitri ="X";
		} else {
			vIdulFitri =".";
		}
		
		String vNatal = ".";
		if(ch5.isChecked() == true){
			vNatal ="X";
		} else {
			vNatal =".";
		}
		
		String vTahunBaru = ".";
		if(ch6.isChecked() == true){
			vTahunBaru ="X";
		} else {
			vTahunBaru =".";
		}
		
		String vImlek = ".";
		if(ch7.isChecked() == true){
			vImlek ="X";
		} else {
			vImlek =".";
		}
		
		String vHindu = ".";
		if(ch8.isChecked() == true){
			vHindu ="X";
		} else {
			vHindu =".";
		}
		
		String vJnsCnt = "ALL";
		if (StringUtils.isNotEmpty(rdgJnsCnt.getSelectedItem().getValue())) {
			vJnsCnt = rdgJnsCnt.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0701003");
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06001_ContactBulletin.jasper";
		
		
		
		param.put("JenisCnt",  vJnsCnt);
		param.put("Bulletin",  vBulletin); 
		param.put("Calender",  vCalender); 
		param.put("EBlast",  vEBlast); 
		param.put("Hindu",  vHindu);

		param.put("IdulFitri",  vIdulFitri); 
		param.put("Imlek",  vImlek); 
		param.put("TahunBaru",  vTahunBaru); 
		param.put("Natal",  vNatal);
		param.put("Sales",  vSales);
		param.put("Cabang",  vCabang);
		
		
	
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		

		 
		
	}
 
}