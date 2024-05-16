package solusi.hapis.webui.sales;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapPenjualanSATMidiCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtItemNo;

	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	

	protected Radiogroup rdgJnsKel;	 
	protected Radio rdKel1;
	protected Radio rdKel2;
	protected Radio rdKel3;
	protected Radio rdKel4;
	protected Radio rdKel5;
	protected Radio rdKel6;
	
	
	protected Radiogroup rdgJnsRpt;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	
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
    	
    	txtItemNo.setValue("PM75G3Q04BJEAC");
    	
		rdKel1.setSelected(true); 
    	rdDTL.setSelected(true); 
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		

		String vItemNo = "ALL";
		if (StringUtils.isNotEmpty(txtItemNo.getValue())) {
			vItemNo = txtItemNo.getValue();
		} 
		
		
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
		
		String vJenisKel = "SAT-REG";
		if (StringUtils.isNotEmpty(rdgJnsKel.getSelectedItem().getValue())) {
			vJenisKel = rdgJnsKel.getSelectedItem().getValue();	
		} 
		
		String vJenisRpt = "DTL";
		if (StringUtils.isNotEmpty(rdgJnsRpt.getSelectedItem().getValue())) {
			vJenisRpt = rdgJnsRpt.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0504001");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sales/04063_LapPenjualanSATMidi.jasper";
		
		
		
		param.put("ItemNo",  vItemNo); 
		param.put("JenisKelompok",  vJenisKel); 
		
		param.put("TglInvFrom",  vTglFrom); 
		param.put("TglInvUpto",  vTglUpto); 
		param.put("JenisRpt",  vJenisRpt); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	
		
	}
 
}