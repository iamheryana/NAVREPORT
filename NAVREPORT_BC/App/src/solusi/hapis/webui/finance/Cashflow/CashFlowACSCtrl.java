package solusi.hapis.webui.finance.Cashflow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.model.P06ParamDefaultRpt;
import solusi.hapis.backend.navbi.model.P07ParamCashflow;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.P06ParamDefaultRptService;
import solusi.hapis.backend.navbi.service.P07ParamCashflowService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class CashFlowACSCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglFrom;  
	
	protected Radiogroup rdgJnsRpt;	 
	protected Radio rdN;
	protected Radio rdW;
	protected Radio rdC;
	protected Radio rdM;
	
	
	protected Decimalbox dcmKursUSD;
	protected Decimalbox dcmKursEUR;
	protected Decimalbox dcmKursSGD;	
	protected Decimalbox dcmKursCNY;	
	
	protected Decimalbox dcmPIBAJ;	
	protected Decimalbox dcmPIBSP;	

	protected Decimalbox dcmSaldoAwalAJ;
	protected Decimalbox dcmSaldoAwalSP;
	
	
	private P06ParamDefaultRptService p06ParamDefaultRptService;
	private P07ParamCashflowService p07ParamCashflowService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	dbTglFrom.setValue((new Date()));  


    	P06ParamDefaultRpt aParam =	p06ParamDefaultRptService.getP06ParamDefaultRptByKode("01");
    	if (aParam != null){
    		String vDefaultKolomCf = aParam.getPeriodeKolomCf();
    		if (vDefaultKolomCf.equals("N") == true){
				rdN.setSelected(true);
			} else {
				if (vDefaultKolomCf.equals("W") == true){
					rdW.setSelected(true);
				} else {
					if (vDefaultKolomCf.equals("C") == true){
						rdC.setSelected(true);
					} else {
						if (vDefaultKolomCf.equals("M") == true){
							rdM.setSelected(true);
						}
					}
				}
			}
    	}
    	
    	P07ParamCashflow aParam07 =	p07ParamCashflowService.getP07ParamCashflowByKode("01");
    	if (aParam07 != null){
    		dcmKursUSD.setValue(aParam07.getKursUsd());
    		dcmKursEUR.setValue(aParam07.getKursEur());
    		dcmKursSGD.setValue(aParam07.getKursSgd());
    		dcmKursCNY.setValue(aParam07.getKursCny());
    		
    		dcmPIBAJ.setValue(aParam07.getPibAj());
    		dcmPIBSP.setValue(aParam07.getPibSp());
    	}
    
    	dcmSaldoAwalAJ.setValue(new BigDecimal(0));
    	dcmSaldoAwalSP.setValue(new BigDecimal(0));

    	
	
	}
	
	

		
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
			
		String vJnsRpt = "W";
		if (StringUtils.isNotEmpty(rdgJnsRpt.getSelectedItem().getValue())) {
			vJnsRpt = rdgJnsRpt.getSelectedItem().getValue();	
		} 


		Date vTglMulai = new Date();
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglMulai = dbTglFrom.getValue();
		}   
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglMulai  = frmTgl.format(vTglMulai);  	
		
		BigDecimal vSaldoAwalAJ = new BigDecimal(0);
		if (dcmSaldoAwalAJ.getValue() != null) {
			vSaldoAwalAJ = dcmSaldoAwalAJ.getValue();	
		} 
		
		BigDecimal vSaldoAwalSP = new BigDecimal(0);
		if (dcmSaldoAwalSP.getValue() != null) {
			vSaldoAwalSP = dcmSaldoAwalSP.getValue();	
		} 
		
		
		BigDecimal vPIBAJ = new BigDecimal(0);
		if (dcmPIBAJ.getValue() != null) {
			vPIBAJ = dcmPIBAJ.getValue();	
		} 
		
		BigDecimal vPIBSP = new BigDecimal(0);
		if (dcmPIBSP.getValue() != null) {
			vPIBSP = dcmPIBSP.getValue();	
		} 
		
		BigDecimal vKursUSD = new BigDecimal(0);
		if (dcmKursUSD.getValue() != null) {
			vKursUSD = dcmKursUSD.getValue();	
		} 
		
		BigDecimal vKursEUR = new BigDecimal(0);
		if (dcmKursEUR.getValue() != null) {
			vKursEUR = dcmKursEUR.getValue();	
		} 
		
		BigDecimal vKursSGD = new BigDecimal(0);
		if (dcmKursSGD.getValue() != null) {
			vKursSGD = dcmKursSGD.getValue();	
		} 
		
		BigDecimal vKursCNY = new BigDecimal(0);
		if (dcmKursCNY.getValue() != null) {
			vKursCNY = dcmKursCNY.getValue();	
		} 
		
		//@SuppressWarnings("unused")
		//String vSync = callStoreProcOrFuncService.callSyncAReport("0201004");
		
		String vProsesID = String.valueOf(System.currentTimeMillis());
		String vCetak = callStoreProcOrFuncService.callCashFlowACS(vProsesID, vStrTglMulai, vJnsRpt, vSaldoAwalAJ, vSaldoAwalSP, vPIBAJ, vPIBSP, vKursUSD, vKursSGD, vKursEUR, vKursCNY, "CETAK");
		
		List<Map> params = new ArrayList<Map>();
		List<String> jasperRpts = new ArrayList<String>();
		String [] vSheetName = new String[10]; 
		String [] vProsesID_PNLCabang = new String[5];
		
		jasperRpts.add("/solusi/hapis/webui/reports/finance/Cashflow/0102002_CashFlowACS.jasper");
		 
	    Map paramCF_1 = new HashMap();	
	

	    paramCF_1.put("KursUSD",  vKursUSD); 
	    paramCF_1.put("KursEUR",  vKursEUR);  		
	    paramCF_1.put("KursSGD",  vKursSGD); 
	    paramCF_1.put("KursCNY",  vKursCNY); 
	    paramCF_1.put("ProsesId", vProsesID);

	    params.add(paramCF_1);
	    vSheetName[0] = "CASH FLOW";
	    
	    jasperRpts.add("/solusi/hapis/webui/reports/finance/Cashflow/0102003_CF_AR.jasper");
		 
	    Map paramCF_2 = new HashMap();	
	
	    paramCF_2.put("ProsesId", vProsesID);

	    params.add(paramCF_2);
	    vSheetName[1] = "AR";
	    
		
	    jasperRpts.add("/solusi/hapis/webui/reports/finance/Cashflow/0102004_CF_AP_IDR.jasper");
		 
	    Map paramCF_3 = new HashMap();	
	
	    paramCF_3.put("ProsesId", vProsesID);

	    params.add(paramCF_3);
	    vSheetName[2] = "AP-IDR";
	    
	    
	    jasperRpts.add("/solusi/hapis/webui/reports/finance/Cashflow/0102005_CF_AP_VAL.jasper");
		 
	    Map paramCF_4 = new HashMap();	
	
	    paramCF_4.put("ProsesId", vProsesID);

	    params.add(paramCF_4);
	    vSheetName[3] = "AP-VAL";
	    
	    
		
	    jasperRpts.add("/solusi/hapis/webui/reports/finance/Cashflow/0102006_CF_Installment.jasper");
		 
	    Map paramCF_5 = new HashMap();	
	
	    paramCF_5.put("ProsesId", vProsesID);

	    params.add(paramCF_5);
	    vSheetName[4] = "INSTALLMENT-AR-AP";
	    
		
	    jasperRpts.add("/solusi/hapis/webui/reports/finance/Cashflow/0102007_CF_PO.jasper");
		 
	    Map paramCF_6 = new HashMap();	
	
	    paramCF_6.put("ProsesId", vProsesID);

	    params.add(paramCF_6);
	    vSheetName[5] = "BPO-PO";
	    
	    jasperRpts.add("/solusi/hapis/webui/reports/finance/Cashflow/0102008_CF_SALDO_HARIAN.jasper");
		 
	    Map paramCF_7 = new HashMap();	
	
	    paramCF_7.put("ProsesId", vProsesID);
	    paramCF_7.put("JenisRpt", "SUM");
	    
	    params.add(paramCF_7);
	    vSheetName[6] = "SALDO-HARIAN-SUM";
	    
	    jasperRpts.add("/solusi/hapis/webui/reports/finance/Cashflow/0102008_CF_SALDO_HARIAN.jasper");
		 
	    Map paramCF_8 = new HashMap();	
	
	    paramCF_8.put("ProsesId", vProsesID);
	    paramCF_8.put("JenisRpt", "DTL");

	    params.add(paramCF_8);
	    vSheetName[7] = "SALDO-HARIAN-DTL";
		
		new JReportGeneratorWindow(params, jasperRpts, vSheetName); 
		String vDelete = callStoreProcOrFuncService.callCashFlowACS(vProsesID, vStrTglMulai, vJnsRpt, vSaldoAwalAJ, vSaldoAwalSP, vPIBAJ, vPIBSP, vKursUSD, vKursSGD, vKursEUR, vKursCNY, "DELETE");
		
		 
		
	}
	
	
//	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
//		
//		@SuppressWarnings("unused")
//		//String vSync = callStoreProcOrFuncService.callSyncAReportManual("0201004");
//		
//		Messagebox.show("Sync Sudah Selesai");
//	}
// 
}