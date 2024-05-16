package solusi.hapis.webui.accounting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SalesVSCOGSCorrectionCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbPeriode;
	

	protected Combobox  cmbCab;
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	protected Radiogroup rdgAmount;	 
	protected Radio rdAmt1;
	protected Radio rdAmt2;
	protected Radio rdAmt3;
	


	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		dbPeriode.setValue((new Date()));   
    	
    	rdPDF.setSelected(true); 
    	
    	rdAmt2.setSelected(true); 
    	
    	cmbCab.setSelectedIndex(0);
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
	
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
	
		String vCab = "10";
		if (cmbCab.getSelectedItem().getValue() != null){
			vCab = (String) cmbCab.getSelectedItem().getValue();
		}
		
		
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		
		BigDecimal vPembagi = new BigDecimal(String.valueOf(vAmountIn));
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/SalesVSCOGSCorrection.jasper";

    	param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		param.put("Cabang",  vCab); 
		param.put("Pembagi",  vPembagi); 

		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}
		
		 
		
	}
 
}