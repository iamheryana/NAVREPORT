package solusi.hapis.webui.ps;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Combobox;
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

public class OutstandingSODetailOprCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;
	
	
	protected Textbox txtSalesFrom;
	protected Textbox txtSalesUpto;
	
	protected Radiogroup  rdgLaporan;
	protected Radio rdDTL;
	protected Radio rdSUM;
	
	protected Combobox  cmbJenis;
	protected Combobox  cmbStatus;
	protected Combobox  cmbJenisItem;
	
	protected Bandbox  cmbLocation;
	protected Listbox listLocation;
	protected String vLocation = "ALL";
		
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	protected Decimalbox dcmNilai;
	protected Decimalbox dcmNilaiOri;
	
	private SelectQueryService selectQueryService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 	
    	dcmNilai.setValue(new BigDecimal (0));
    	dcmNilaiOri.setValue(new BigDecimal (0));
    	    	
    	rdALL.setSelected(true); 
    	
    	rdDTL.setSelected(true); 

    	txtSalesUpto.setValue("ZZZ");
    	
    	cmbJenis.setSelectedIndex(0);
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
	public void onClick$btnOK(Event event) throws InterruptedException {
		
	
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
		
		
		String vStatus = "ALL";
		if (cmbStatus.getSelectedItem().getValue() != null){
			vStatus = (String) cmbStatus.getSelectedItem().getValue();
		}
		

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
		
		BigDecimal vNilaiOri = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcmNilaiOri.getValue())) {
			vNilaiOri = dcmNilaiOri.getValue();
		} 
		
		
		String vStrTglFrom  = "2000-01-01";
		String vStrTglUpto  = "2999-12-31";
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0601002");
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, vCabang, "CETAK-DTL");

		
		
		String jasperRpt = "/solusi/hapis/webui/reports/ps/05003_OutstandingSODetail.jasper";
		

	
		param.put("Cabang",  vCabang); 
		param.put("SalesFrom",  vSalesFrom); 
		param.put("SalesUpto",  vSalesUpto); 
		param.put("LocCode",  vLocation); 		
		
		param.put("JenisSO",  vJenis); 
		param.put("Status",  vStatus); 
		
		param.put("AdaPS",  vJenisItem); 
		param.put("Detail",  vLap); 

		param.put("Nilai",  vNilai); 
		param.put("NilaiOri",  vNilaiOri); 
		
		
		param.put("Company",  vCompany); 
		
		param.put("ProsesId",  vProsesId); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, vCompany, vCabang, "DELETE");


		 
		
	}
 
}



