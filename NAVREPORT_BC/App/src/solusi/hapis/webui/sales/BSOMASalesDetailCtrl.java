package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class BSOMASalesDetailCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowBSOMASalesDetail;
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	

	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	

	protected Combobox  cmbProjectCat;
	
	
	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = "ALL";
	
	private SelectQueryService selectQueryService;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	

	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
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
		
		rdALL.setSelected(true);    
    	

    	cmbProjectCat.setSelectedIndex(0);

    	

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
		int vSalesIndex = 0;
		List<Object[]> vResultSales = selectQueryService.QuerySalesmanActive();
		if(CommonUtils.isNotEmpty(vResultSales)){
			int vIndex = 1;
			
			String vUserName = SecurityContextHolder.getContext().getAuthentication().getName();
			for(Object[] aRslt : vResultSales){
				listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
				
				
				if((aRslt[1].toString()).equals(vUserName) == true){
					vSalesIndex = vIndex;
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
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
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
		
		
		String vProjectCat = "ALL";
		if (cmbProjectCat.getSelectedItem().getValue() != null){
			vProjectCat = (String) cmbProjectCat.getSelectedItem().getValue();
		}
		

		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		if(vTglFrom.compareTo(vTglUpto) > 0) {
			ZksampleMessageUtils.showErrorMessage("Tanggal Akhir Perkiraan Realisasi harus lebih besar dari Tanggal Awal");
			
		} else {
			
			Date vCurrDateTrunc = DateUtils.truncate(new Date(), Calendar.DATE);
			Date vTglUptoTrunc = DateUtils.truncate(vTglUpto, Calendar.DATE);
						
			
			if(vTglUptoTrunc.compareTo(vCurrDateTrunc)  < 0 ){
				ZksampleMessageUtils.showErrorMessage("Tanggal Akhir Perkiraan Realisasi harus lebih besar dari Tanggal Hari ini");
				
			} else {
					@SuppressWarnings("unused")
					String vSync = callStoreProcOrFuncService.callSyncAReport("0503007");
				
				
					String jasperRpt = "/solusi/hapis/webui/reports/sales/04054_BSOMASalesDetail.jasper";
					
					
					param.put("Size",  vProjectCat); 
					param.put("Company",  vCompany); 
					param.put("Sales",  vSales); 
					param.put("Cabang",  vCabang); 
					param.put("TglEstFrom",  vTglFrom); 
					param.put("TglEstUpto",  vTglUpto); 
					
					new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
			}
		}
		
		
		

		
		
		
	}
 
}