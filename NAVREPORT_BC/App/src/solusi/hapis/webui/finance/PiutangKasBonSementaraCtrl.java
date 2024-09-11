package solusi.hapis.webui.finance;


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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class PiutangKasBonSementaraCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	
	
	protected Bandbox  cmbKaryawan;
	protected Listbox listKaryawan;
	protected String vKaryawan = "ALL";
	protected String vKaryawanName = "ALL";
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/"+(monthTglFrom+1)+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
		
		dbTglUpto.setValue((new Date()));   
    	
    	rdAJ.setSelected(true); 
    	rdSUM.setSelected(true); 
    	
    	Bandpopup popup2 = new Bandpopup();
			listKaryawan = new Listbox();
			listKaryawan.setMold("paging");
			listKaryawan.setAutopaging(false);
			listKaryawan.setWidth("400px");
			listKaryawan.addEventListener(Events.ON_SELECT, selectKaryawan());
			listKaryawan.setParent(popup2);
		popup2.setParent(cmbKaryawan);
	        
		listKaryawan.appendItem("ALL", "ALL");
		
		List<Object[]> vResultKaryawan = selectQueryService.QueryNamaKaryawan();
		if(CommonUtils.isNotEmpty(vResultKaryawan)){
			for(Object[] aRslt : vResultKaryawan){
				listKaryawan.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbKaryawan.setValue(listKaryawan.getItemAtIndex(0).getLabel());
		listKaryawan.setSelectedItem(listKaryawan.getItemAtIndex(0));
    

	}

	private EventListener selectKaryawan() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbKaryawan.setValue(listKaryawan.getSelectedItem().getLabel());
				vKaryawan = listKaryawan.getSelectedItem().getValue().toString();
				vKaryawanName = listKaryawan.getSelectedItem().getLabel().toString();
				cmbKaryawan.close();
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		String dRFrom = "1/"+(monthTglFrom+1)+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		

		Date vTglUpto = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpto = dbTglUpto.getValue();
		}
				
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenisLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenisLap = rdgJenis.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0207008");
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02055_PiutangKasBonSementara.jasper";
		
		if (vJenisLap.equals("SUM") == true) {
			jasperRpt = "/solusi/hapis/webui/reports/finance/02055_PiutangKasBonSementara.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/finance/02055_PiutangKasBonSementara_Detail.jasper";
		}
		
    	param.put("TglFrom",  vTglFrom); 
		param.put("TglUpto",  vTglUpto);  
		param.put("Company",  vCompany); 
		param.put("Karyawan",  vKaryawan); 
		param.put("KaryawanName",  vKaryawanName); 	
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		 
		
	}
 
}

