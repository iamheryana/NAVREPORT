package solusi.hapis.webui.accounting;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class TTFIndomarcoHOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Textbox txtInvFrom;
	protected Textbox txtInvUpto;
	
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;		
	
	protected Radiogroup rdgFormat;	 
	protected Radio rdF1;
	protected Radio rdF12;
	protected Radio rdF2;
	protected Radio rdF3;
	protected Radio rdF4;
	protected Radio rdF5;
	
	protected Decimalbox decAmtTambahan1;
	protected Decimalbox decAmtTambahan2;
	protected Decimalbox decAmtTambahan3;
	
	protected Textbox txtTambahan1;
	protected Textbox txtTambahan2;
	protected Textbox txtTambahan3;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	rdAJ.setSelected(true); 
    	rdPDF.setSelected(true); 
    	rdF1.setSelected(true); 
    	
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		

		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vInvFrom = ".";
		if (StringUtils.isNotEmpty(txtInvFrom.getValue())) {
			vInvFrom = txtInvFrom.getValue();
		} 
		
		String vInvUpto = "ZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtInvUpto.getValue())) {
			vInvUpto = txtInvUpto.getValue();
		} 
		
		String vFormat = "HO";
		if (StringUtils.isNotEmpty(rdgFormat.getSelectedItem().getValue())) {
			vFormat = rdgFormat.getSelectedItem().getValue();	
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108004");
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01051_TTFIndomarcoHO.jasper";
		if (vFormat.equals("HO")==true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01051_TTFIndomarcoHO.jasper";
		} else if(vFormat.equals("ICC")==true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01051_02_TTFIndomarcoICC.jasper";
		} else if(vFormat.equals("JKT")==true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01051_03_TTFIndomarcoJKT.jasper";
		} else if(vFormat.equals("BGR")==true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01051_04_TTFIndomarcoBGR.jasper";
		} else if(vFormat.equals("TGR")==true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01051_05_TTFIndomarcoTGR.jasper";
		} else if(vFormat.equals("HOIPP")==true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01051_06_TTFIndomarcoINTI.jasper";
		}
			
		

		if(vFormat.equals("BGR")==true){
			String vTambahan1 = "";
			if (StringUtils.isNotEmpty(txtTambahan1.getValue())) {
				vTambahan1 = txtTambahan1.getValue();
			} 
			
			String vTambahan2 = "";
			if (StringUtils.isNotEmpty(txtTambahan2.getValue())) {
				vTambahan2 = txtTambahan2.getValue();
			} 
			
			String vTambahan3 = "";
			if (StringUtils.isNotEmpty(txtTambahan3.getValue())) {
				vTambahan3 = txtTambahan3.getValue();
			} 
			
			BigDecimal vAmtTambahan1 = new BigDecimal(0);
			if (CommonUtils.isNotEmpty(decAmtTambahan1.getValue())) {
				vAmtTambahan1 = decAmtTambahan1.getValue();
			} 
			
			BigDecimal vAmtTambahan2 = new BigDecimal(0);
			if (CommonUtils.isNotEmpty(decAmtTambahan2.getValue())) {
				vAmtTambahan2 = decAmtTambahan2.getValue();
			} 
			
			BigDecimal vAmtTambahan3 = new BigDecimal(0);
			if (CommonUtils.isNotEmpty(decAmtTambahan3.getValue())) {
				vAmtTambahan3 = decAmtTambahan3.getValue();
			} 
			
			param.put("Tambahan1",  vTambahan1); 
			param.put("Tambahan2",  vTambahan2); 
			param.put("Tambahan3",  vTambahan3); 
			
			param.put("AmtTambahan1",  vAmtTambahan1); 
			param.put("AmtTambahan2",  vAmtTambahan2); 
			param.put("AmtTambahan3",  vAmtTambahan3); 
					
		}
		 
    	param.put("InvFrom",  vInvFrom); 
		param.put("InvUpto",  vInvUpto);  
		param.put("Company",  vCompany); 
		
		
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

