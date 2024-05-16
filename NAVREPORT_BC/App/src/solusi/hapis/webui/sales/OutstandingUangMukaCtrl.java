package solusi.hapis.webui.sales;

import java.io.Serializable;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class OutstandingUangMukaCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;

	
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Radiogroup rdgAdj;	 
	protected Radio rdYES;
	protected Radio rdNO;
	
	protected Radiogroup rdgUMFin;	 
	protected Radio rdAll;
	protected Radio rdThisYear;


	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	private SelectQueryService selectQueryService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	
		rdXLS.setSelected(true); 
		
		rdNO.setSelected(true); 
		rdAll.setSelected(true); 
		
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
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
	
		String vMgmAdj = "NO";
		if (StringUtils.isNotEmpty(rdgAdj.getSelectedItem().getValue())) {
			vMgmAdj = rdgAdj.getSelectedItem().getValue();	
		} 
		
		String vThisYearOnly = "N";
		if (StringUtils.isNotEmpty(rdgUMFin.getSelectedItem().getValue())) {
			vThisYearOnly = rdgUMFin.getSelectedItem().getValue();	
		} 
		
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		
		Calendar cTgl= Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTgl = cTgl.get(Calendar.YEAR);
		
		String dRFrom = "1/1/"+yearTgl;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		
		String dRUpto = "31/12/"+(yearTgl+1);
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglUpto  = dfRUpto.parse(dRUpto);
		
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom); 
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0507012");
		
		@SuppressWarnings("unused")
		String vResultOutUM = callStoreProcOrFuncService.callOutstandingUM(vProsesId, vCabang, "CETAK");
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", vCabang, "CETAK");

		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04055_OutstandingUangMuka.jasper";
		
		if(vMgmAdj.equals("NO")){
			jasperRpt = "/solusi/hapis/webui/reports/sales/04055_OutstandingUangMuka.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/sales/04055_OutstandingUangMuka_02.jasper";
		}
		
		
		param.put("Cabang",  vCabang); 		
		param.put("ProsesId",  vProsesId); 
		param.put("ThisYearOnly",  vThisYearOnly); 
		
		
		String vSaveAs = "XLS";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
			
		}
		
		@SuppressWarnings("unused")
		String vDeleteUM = callStoreProcOrFuncService.callOutstandingUM(vProsesId, vCabang, "DELETE");
		
		
		@SuppressWarnings("unused")
		String vDeleteSO = callStoreProcOrFuncService.callOutstandingSO(vProsesId, vStrTglFrom, vStrTglUpto, "ALL", vCabang, "DELETE");
	}
 
}

//