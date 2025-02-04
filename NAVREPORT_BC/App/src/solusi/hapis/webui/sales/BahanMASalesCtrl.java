package solusi.hapis.webui.sales;

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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class BahanMASalesCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowBahanMASales;
	
	
	protected Radiogroup rdgPeriode;	 
	protected Radio rdM;
	protected Radio rdQ;


	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
//	protected Combobox  cmbItemCat;
//	protected Combobox  cmbProjectCat;
//	protected Combobox  cmbPotensialReal;
	
	//protected Textbox txtSales;
	
	//protected Button btnSearchSalesLOV;
	
	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSalesALL = "ALL";
	protected String [] vSales = new String[50];

	private SelectQueryService selectQueryService;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	protected Bandbox  cmbApplication;
	protected Listbox listApplication;
	protected String vApplication = "ALL";
	

	
	protected Bandbox  cmbProjectCat;
	protected Listbox listProjectCat;
	protected String vProjectCatALL = "ALL";
	protected String [] vProjectCat = new String[5];
	
	
	
//	protected Checkbox ch1;
//	protected Checkbox ch2;
//	protected Checkbox ch3;
//	protected Checkbox ch4;
//	protected Checkbox ch5;
//	protected Checkbox ch6;
	
	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
//		ch1.setChecked(true);
//		ch2.setChecked(true);
//		ch3.setChecked(true);
//		ch4.setChecked(true);
//		ch5.setChecked(true);
//		ch6.setChecked(true);
		 
		rdQ.setSelected(true);    
    	rdALL.setSelected(true);    
    	
//    	cmbItemCat.setSelectedIndex(0);
//    	cmbProjectCat.setSelectedIndex(0);
//    	cmbPotensialReal.setSelectedIndex(0);
    	
    	//txtSales.setValue("ALL");
    	
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
		listSales.setCheckmark(true);
		listSales.setMultiple(true);
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
					vSales[0] = vUserName;
					vSalesALL = " ";
					for (int x = 1 ; x < 50; x++){
		      			vSales[x] = " ";
			      	}
				}
				
				vIndex = vIndex + 1;
				
			}
		}
		
		
		cmbSales.setValue(listSales.getItemAtIndex(vSalesIndex).getLabel());
		listSales.setSelectedItem(listSales.getItemAtIndex(vSalesIndex));
		
		
		
		Bandpopup popup3 = new Bandpopup();
		listPrincipal = new Listbox();
		listPrincipal.setMold("paging");
		listPrincipal.setAutopaging(false);
		listPrincipal.setWidth("400px");
		listPrincipal.addEventListener(Events.ON_SELECT, selectPrincipal());
		listPrincipal.setParent(popup3);
		popup3.setParent(cmbPrincipal);
	        
		listPrincipal.appendItem("ALL", "ALL");
			
		List<Object[]> vResultPrincipal = selectQueryService.QueryPrincipal();
		if(CommonUtils.isNotEmpty(vResultPrincipal)){
			for(Object[] aRslt : vResultPrincipal){
				listPrincipal.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbPrincipal.setValue(listPrincipal.getItemAtIndex(0).getLabel());
		listPrincipal.setSelectedItem(listPrincipal.getItemAtIndex(0));
    	
		Bandpopup popup4 = new Bandpopup();
			listApplication = new Listbox();
			listApplication.setMold("paging");
			listApplication.setAutopaging(false);
			listApplication.setWidth("400px");
			listApplication.addEventListener(Events.ON_SELECT, selectApplication());
			listApplication.setParent(popup4);
		popup4.setParent(cmbApplication);
	        
		listApplication.appendItem("ALL", "ALL");
			
		List<Object[]> vResultApplication = selectQueryService.QueryApplication();
		if(CommonUtils.isNotEmpty(vResultApplication)){
			for(Object[] aRslt : vResultApplication){
				listApplication.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbApplication.setValue(listApplication.getItemAtIndex(0).getLabel());
		listApplication.setSelectedItem(listApplication.getItemAtIndex(0));
		
		
		Bandpopup popup5 = new Bandpopup();
			listProjectCat = new Listbox();
			listProjectCat.setCheckmark(true);
			listProjectCat.setMultiple(true);
			listProjectCat.setMold("paging");
			listProjectCat.setAutopaging(false);
			listProjectCat.setWidth("400px");
			listProjectCat.addEventListener(Events.ON_SELECT, selectProjectCat());
			listProjectCat.setParent(popup5);
		popup5.setParent(cmbProjectCat);
	        
		listProjectCat.appendItem("ALL", "ALL");
		listProjectCat.appendItem("Awarded (A)", "A");
		listProjectCat.appendItem("High Potential (H)", "H");
		listProjectCat.appendItem("Medium Potential (M)", "M");
		listProjectCat.appendItem("Low Potential (L)", "L");
//		listProjectCat.appendItem("Regular Project (R)", "R");
		
		
		
		cmbProjectCat.setValue(listProjectCat.getItemAtIndex(0).getLabel());
		listProjectCat.setSelectedItem(listProjectCat.getItemAtIndex(0));

	}
	
	
	private EventListener selectApplication() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbApplication.setValue(listApplication.getSelectedItem().getLabel());
				vApplication = listApplication.getSelectedItem().getValue().toString();
				cmbApplication.close();
			}
		};
	}
	
	
	
	private EventListener selectPrincipal() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbPrincipal.setValue(listPrincipal.getSelectedItem().getLabel());
				vPrincipal = listPrincipal.getSelectedItem().getValue().toString();
				cmbPrincipal.close();
			}
		};
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
				
				//cmbSales.setValue(listSales.getSelectedItem().getLabel());
				//vSales = listSales.getSelectedItem().getValue().toString();
		        int vJmlSelected = listSales.getSelectedCount();
		        String vStringSelected = "";
		        vSalesALL = " ";
		        int i = 0;
		      	for (Object object : listSales.getSelectedItems()) {
		      	    Listitem item = (Listitem) object;
		      	    
		      	    if (item.getValue().equals("ALL") == true){
		      	    	vSalesALL = "ALL";
		      	    	i = i - 1;
		      	    } else {
		      	    	vSales[i] = (String) item.getValue();
		      	    }
		      	    
		      	    vStringSelected = vStringSelected+" "+item.getValue() ;
		      	  
		      	  
		      	    i = i + 1;
		      	}
		      	
		      	cmbSales.setValue(vStringSelected);
		      	if (vSalesALL.equals("ALL") == true){
			      	if (vJmlSelected > 0){	
				      	for (int x = vJmlSelected - 1 ; x < 50; x++){
				      		vSales[x] = " ";
				      	}
			      	}
		      	} else {
		      		for (int x = vJmlSelected ; x < 50; x++){
		      			vSales[x] = " ";
			      	}
		      	}
		      	
		      	
//		      	System.out.println("vSalesALL : "+vSalesALL);		          
//		    	for (int u = 0 ; u < 50; u++){		    		
//		    		System.out.println("vSales["+u+"] : "+vSales[u]);		    		
//		      	}
//		    	System.out.println("=========================================");
		      	
		      
				//cmbSales.close();
			}
		};
	}
	
	private EventListener selectProjectCat() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				
		        
		        int vJmlSelected = listProjectCat.getSelectedCount();
		        String vStringSelected = "";
		        vProjectCatALL = " ";
		        int i = 0;
		      	for (Object object : listProjectCat.getSelectedItems()) {
		      	    Listitem item = (Listitem) object;
		      	    
		      	    if (item.getValue().equals("ALL") == true){
		      	    	vProjectCatALL = "ALL";
		      	    	i = i - 1;
		      	    } else {
		      	    	vProjectCat[i] = (String) item.getValue();
		      	    }
		      	    
		      	    vStringSelected = vStringSelected+" "+item.getValue() ;
		      	  
		      	  
		      	    i = i + 1;
		      	}
		      	
		      	cmbProjectCat.setValue(vStringSelected);
		      	if (vProjectCatALL.equals("ALL") == true){
			      	if (vJmlSelected > 0){	
				      	for (int x = vJmlSelected - 1 ; x < 4; x++){
				      		vProjectCat[x] = " ";
				      	}
			      	}
		      	} else {
		      		for (int x = vJmlSelected ; x < 4; x++){
			      		vProjectCat[x] = " ";
			      	}
		      	}
		      	
		      	
//		      	System.out.println("vProjectCatALL : "+vProjectCatALL);		          
//		    	for (int u = 0 ; u < 5; u++){		    		
//		    		System.out.println("vProjectCat["+u+"] : "+vProjectCat[u]);		    		
//		      	}
//		    	System.out.println("=========================================");
				//cmbSales.close();
			}
		};
	}


//	public void onClick$btnSearchSalesLOV(Event event) {
//		T03salesperson sales = T03salespersonLOV.show(windowBahanMASales);
//
//		if (sales != null) {
//			txtSales.setValue(sales.getSales());
//		
//		} else {
//			txtSales.setValue(null);
//		}
//		
//	}
	
	
//	public void onCheck$ch1(Event event){
//		if (ch1.isChecked() == true){
//			ch2.setChecked(true);
//			ch3.setChecked(true);
//			ch4.setChecked(true);
//			ch5.setChecked(true);
//			ch6.setChecked(true);
//		} else {
//			ch2.setChecked(false);
//			ch3.setChecked(false);
//			ch4.setChecked(false);
//			ch5.setChecked(false);
//			ch6.setChecked(false);
//		}
//		
//	}
//	
//	public void onCheck$ch2(Event event){
//		if (ch2.isChecked() == false){
//			ch1.setChecked(false);
//		} else {
//			if (
//					ch3.isChecked() == true &&
//					ch4.isChecked() == true &&
//					ch5.isChecked() == true &&
//					ch6.isChecked() == true
//				){
//				ch1.setChecked(true);
//				
//			}
//		}
//	}
//	
//	public void onCheck$ch3(Event event){
//		if (ch3.isChecked() == false){
//			ch1.setChecked(false);
//		}else {
//			if (
//					ch2.isChecked() == true &&
//					ch4.isChecked() == true &&
//					ch5.isChecked() == true &&
//					ch6.isChecked() == true
//				){
//				ch1.setChecked(true);
//				
//			}
//		}
//	}
//	
//	public void onCheck$ch4(Event event){
//		if (ch4.isChecked() == false){
//			ch1.setChecked(false);
//		} else {
//			if (
//					ch2.isChecked() == true &&
//					ch3.isChecked() == true &&
//					ch5.isChecked() == true &&
//					ch6.isChecked() == true
//				){
//				ch1.setChecked(true);
//				
//			}
//		}
//	}
//	
//	public void onCheck$ch5(Event event){
//		if (ch5.isChecked() == false){
//			ch1.setChecked(false);
//		} else {
//			if (
//					ch2.isChecked() == true &&
//					ch3.isChecked() == true &&
//					ch4.isChecked() == true &&
//					ch6.isChecked() == true
//				){
//				ch1.setChecked(true);
//				
//			}
//		}
//	}
//	
//	public void onCheck$ch6(Event event){
//		if (ch6.isChecked() == false){
//			ch1.setChecked(false);
//		} else {
//			if (
//					ch2.isChecked() == true &&
//					ch3.isChecked() == true &&
//					ch4.isChecked() == true &&
//					ch5.isChecked() == true
//				){
//				ch1.setChecked(true);
//				
//			}
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		

		
		
//		String vSize = "ALL";
//		if (ch1.isChecked() == true){
//			vSize = "ALL";
//		} else {
//			vSize = "X";
//		}
//		
//		String vSizeA = "A";
//		if (ch2.isChecked() == true){
//			vSizeA = "A";
//		} else {
//			vSizeA = "X";
//		}
//		
//		String vSizeH = "H";
//		if (ch3.isChecked() == true){
//			vSizeH = "H";
//		} else {
//			vSizeH = "X";
//		}
//		
//		String vSizeM = "M";
//		if (ch4.isChecked() == true){
//			vSizeM = "M";
//		} else {
//			vSizeM = "X";
//		}
//		
//		String vSizeL = "L";
//		if (ch5.isChecked() == true){
//			vSizeL = "L";
//		} else {
//			vSizeL = "X";
//		}
//		
//		String vSizeR = "R";
//		if (ch6.isChecked() == true){
//			vSizeR = "R";
//		} else {
//			vSizeR = "X";
//		}
		
		
		String vPeriode = "Q";
		if (StringUtils.isNotEmpty(rdgPeriode.getSelectedItem().getValue())) {
			vPeriode = rdgPeriode.getSelectedItem().getValue();	
		} 
			
		String vItemCat = "ALL";
//		if (cmbItemCat.getSelectedItem().getValue() != null){
//			vItemCat = (String) cmbItemCat.getSelectedItem().getValue();
//		}
		
//		String vProjectCat = "ALL";
//		if (cmbProjectCat.getSelectedItem().getValue() != null){
//			vProjectCat = (String) cmbProjectCat.getSelectedItem().getValue();
//		}
		
		String vPotensialReal = "ALL";
//		if (cmbPotensialReal.getSelectedItem().getValue() != null){
//			vPotensialReal = (String) cmbPotensialReal.getSelectedItem().getValue();
//		}
		
		
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
//		String vSales = "ALL";
//		if (StringUtils.isNotEmpty(txtSales.getValue())) {
//			vSales =txtSales.getValue();	
//		} 
		

		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0503001");
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04037_BahanMASales.jasper";
		
		PathReport pathReport = new PathReport();
		
		
		param.put("SUBREPORT_DIR",  pathReport.getSubRptSales());
		
		param.put("Periode",  vPeriode);
		param.put("ItemCategory",  vItemCat);
		param.put("Potensi",  vPotensialReal); 
		param.put("Size",  vProjectCatALL); 
		param.put("Size1",  vProjectCat[0]); 
		param.put("Size2",  vProjectCat[1]); 
		param.put("Size3",  vProjectCat[2]); 
		param.put("Size4",  vProjectCat[3]); 

		
		//param.put("Size5",  vProjectCat[4]); 
		param.put("Company",  vCompany); 
		param.put("Sales",  vSalesALL); 
		param.put("Sales1",  vSales[0]); 
		param.put("Sales2",  vSales[1]); 
		param.put("Sales3",  vSales[2]); 
		param.put("Sales4",  vSales[3]); 
		param.put("Sales5",  vSales[4]); 
		param.put("Sales6",  vSales[5]); 
		param.put("Sales7",  vSales[6]); 
		param.put("Sales8",  vSales[7]); 
		param.put("Sales9",  vSales[8]); 
		param.put("Sales10",  vSales[9]); 
		
		param.put("Sales11",  vSales[10]); 
		param.put("Sales12",  vSales[11]); 
		param.put("Sales13",  vSales[12]); 
		param.put("Sales14",  vSales[13]); 
		param.put("Sales15",  vSales[14]); 
		param.put("Sales16",  vSales[15]); 
		param.put("Sales17",  vSales[16]); 
		param.put("Sales18",  vSales[17]); 
		param.put("Sales19",  vSales[18]); 
		param.put("Sales20",  vSales[19]); 
		
		param.put("Sales21",  vSales[20]); 
		param.put("Sales22",  vSales[21]); 
		param.put("Sales23",  vSales[22]); 
		param.put("Sales24",  vSales[23]); 
		param.put("Sales25",  vSales[24]); 
		param.put("Sales26",  vSales[25]); 
		param.put("Sales27",  vSales[26]); 
		param.put("Sales28",  vSales[27]); 
		param.put("Sales29",  vSales[28]); 
		param.put("Sales30",  vSales[29]); 
		
		param.put("Sales31",  vSales[30]); 
		param.put("Sales32",  vSales[31]); 
		param.put("Sales33",  vSales[32]); 
		param.put("Sales34",  vSales[33]); 
		param.put("Sales35",  vSales[34]); 
		param.put("Sales36",  vSales[35]); 
		param.put("Sales37",  vSales[36]); 
		param.put("Sales38",  vSales[37]); 
		param.put("Sales39",  vSales[38]); 
		param.put("Sales40",  vSales[39]); 
		
		param.put("Sales41",  vSales[40]); 
		param.put("Sales42",  vSales[41]); 
		param.put("Sales43",  vSales[42]); 
		param.put("Sales44",  vSales[43]); 
		param.put("Sales45",  vSales[44]); 
		param.put("Sales46",  vSales[45]); 
		param.put("Sales47",  vSales[46]); 
		param.put("Sales48",  vSales[47]); 
		param.put("Sales49",  vSales[48]); 
		param.put("Sales50",  vSales[49]); 
		
		
		param.put("Cabang",  vCabang); 
		param.put("Principal",  vPrincipal); 
		param.put("App",  vApplication); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}