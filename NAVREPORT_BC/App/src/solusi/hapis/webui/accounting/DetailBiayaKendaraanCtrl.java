package solusi.hapis.webui.accounting;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class DetailBiayaKendaraanCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Bandbox  cmbKendaraan;
	protected Listbox listKendaraan;
	protected String vKendaraan = ".";

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
    	
    	


    	
		Bandpopup popup2 = new Bandpopup();
			listKendaraan = new Listbox();
			listKendaraan.setMold("paging");
			listKendaraan.setAutopaging(false);
			listKendaraan.setWidth("400px");
			listKendaraan.addEventListener(Events.ON_SELECT, selectKendaraan());
			listKendaraan.setParent(popup2);
		popup2.setParent(cmbKendaraan);

		listKendaraan.appendItem("<<<Please Select>>>", ".");
		
		List<Object[]> vResultKendaraan = selectQueryService.QueryKendaraan();
		if(CommonUtils.isNotEmpty(vResultKendaraan)){
			for(Object[] aRslt : vResultKendaraan){
				listKendaraan.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbKendaraan.setValue(listKendaraan.getItemAtIndex(0).getLabel());
		listKendaraan.setSelectedItem(listKendaraan.getItemAtIndex(0));
    	
	}
	
	private EventListener selectKendaraan() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbKendaraan.setValue(listKendaraan.getSelectedItem().getLabel());
				vKendaraan = listKendaraan.getSelectedItem().getValue().toString();
				cmbKendaraan.close();
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
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0102004");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01072_DetailBiayaKendaraan.jasper";
		
		
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpto);
		
		param.put("Kendaraan",  vKendaraan); 

		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
 
}