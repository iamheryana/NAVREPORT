package solusi.hapis.webui.accounting.pajak;

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

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class PPNPembelianCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	
	protected Radiogroup rdgPembelian;	 
	protected Radio rdPblALL;
	protected Radio rdPbl;
	protected Radio rdPblNon;
	
	protected Radiogroup rdgPPN;	 
	protected Radio rdPPNALL;
	protected Radio rdNonPKP;
	protected Radio rdNonPPN;
	protected Radio rdNonPPN2;
	protected Radio rdPIB;
	protected Radio rdPPN1;
	protected Radio rdPPN10;
	protected Radio rdPPNOthers;

	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
		
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		    	
    	rdAJ.setSelected(true); 
	    	
    	Calendar cTglCurr = Calendar.getInstance();		
  		cTglCurr.setTime(new Date());
  		
  		int yearTglCurr = cTglCurr.get(Calendar.YEAR);
  		  		
  		String dRFrom = "1/1/"+yearTglCurr;
  		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
  		Date vTglFrom  = dfRFrom.parse(dRFrom);
  		  		
  		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(new Date());
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
				
  		dbTglFrom.setValue(vTglFrom);
  		dbTglTo.setValue(vTglUpto); 
  		
  		rdSUM.setSelected(true);
  		rdPblALL.setSelected(true);
  		rdPPNALL.setSelected(true);
  		
  		rdPDF.setSelected(true); 
    }
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		

    	Calendar cTglCurr = Calendar.getInstance();		
  		cTglCurr.setTime(new Date());
  		
  		int yearTglCurr = cTglCurr.get(Calendar.YEAR);
  		
  		
  		String dRFrom = "1/1/"+yearTglCurr;
  		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
  		Date vTglFrom  = dfRFrom.parse(dRFrom);
  		
  		
  		Calendar cTglUpto = Calendar.getInstance();
  		cTglUpto.setTime(new Date());
  		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
  		Date vTglUpto = cTglUpto.getTime();
  		  		
	
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
  
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglUpto = dbTglTo.getValue();
		}
				

		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJnsRpt = "Summary";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJnsRpt = rdgJenis.getSelectedItem().getValue();	
		} 
			
		String vJnsPembelian = "ALL";
		if (StringUtils.isNotEmpty(rdgPembelian.getSelectedItem().getValue())) {
			vJnsPembelian = rdgPembelian.getSelectedItem().getValue();	
		}
		
		String vJnsPPN = "ALL";
		if (StringUtils.isNotEmpty(rdgPPN.getSelectedItem().getValue())) {
			vJnsPPN = rdgPPN.getSelectedItem().getValue();	
		}
	
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0105005");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/pajak/01050_PPNPembelian.jasper";
		
		
    	param.put("TglFrom",  vTglFrom); 
    	param.put("TglUpto",  vTglUpto);  
	
		
		param.put("Company",  vCompany); 
		
		param.put("JnsRpt",  vJnsRpt); 
		param.put("JenisPembelian",  vJnsPembelian); 
		param.put("JenisPPN",  vJnsPPN); 
		
		
		
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

