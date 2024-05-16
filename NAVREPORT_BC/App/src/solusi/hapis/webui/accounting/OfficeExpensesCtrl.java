package solusi.hapis.webui.accounting;



import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class OfficeExpensesCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Intbox  intTahun;

	
	protected Radiogroup rdgCompany;	 
	protected Radio rdALL;
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdJN1;
	protected Radio rdJN2;
	protected Radio rdJN3;
	
	protected Combobox cmbPeriode;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 

		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
    	
		
    	    	
		intTahun.setValue(yearTglCurr);
		
		rdALL.setSelected(true);
		
		rdJN1.setSelected(true);
	
		monthTglCurr = monthTglCurr+1;		
		cmbPeriode.setSelectedIndex(monthTglCurr-1);

    	
	}
	


	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		monthTglCurr = monthTglCurr+1;
	
		
		int vTahun = yearTglCurr;
		if(CommonUtils.isNotEmpty(intTahun.getValue())){
			vTahun = intTahun.getValue();
		}
		
		int vMasa = monthTglCurr;	
		if (cmbPeriode.getSelectedIndex() != -1) {
			String vStrMasa =  (String) cmbPeriode.getSelectedItem().getValue();
			vMasa = Integer.valueOf(vStrMasa);
		}
		
		String dRFrom="1/1/"+vTahun;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom = dfRFrom.parse(dRFrom);
		
		
		String dRUpto="1/"+vMasa+"/"+vTahun;
		SimpleDateFormat dfRUpto= new SimpleDateFormat("dd/MM/yyyy");
		Date vUpto = dfRUpto.parse(dRUpto);
		
		Calendar cUpto = Calendar.getInstance();
		cUpto.setTime(vUpto);
		cUpto.set(Calendar.DAY_OF_MONTH, cUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cUpto.getTime();
		
			
		String dRFromLY="1/1/"+(vTahun-1);
		SimpleDateFormat dfRFromLY= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFromLY = dfRFromLY.parse(dRFromLY);
		
		
		String dRUptoLY="1/"+vMasa+"/"+(vTahun-1);
		SimpleDateFormat dfRUptoLY = new SimpleDateFormat("dd/MM/yyyy");
		Date vUptoLY = dfRUptoLY.parse(dRUptoLY);
		
		Calendar cUptoLY = Calendar.getInstance();
		cUptoLY.setTime(vUptoLY);
		cUptoLY.set(Calendar.DAY_OF_MONTH, cUptoLY.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUptoLY = cUptoLY.getTime();

		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJnsLap = "NASIONAL";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0102003");
				
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01069_OfficeExpenses.jasper";

		
		if (vJnsLap.equals("SUMMARY") == true ){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01069_OfficeExpenses_02.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01069_OfficeExpenses.jasper";
			param.put("JnsLap",  vJnsLap);
		}
		
		param.put("Tahun",  vTahun);
		param.put("Company",  vCompany);
		param.put("PeriodeUpto",  vMasa);
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpto);		
		param.put("TglLYFrom",  vTglFromLY);
		param.put("TglLYUpto",  vTglUptoLY);
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		

	}
 
}

