package solusi.hapis.webui.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.tabel.model.T04paramKomisi;
import solusi.hapis.backend.tabel.service.T04paramKomisiService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SlipTransferBCACtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Radiogroup rdgCetak;	 
	protected Radio rdSLIP;
	protected Radio rdCEK;

	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Textbox txtNoVoucher;
	protected Textbox txtNoCek;
	protected Textbox txtBerita;
//	protected Textbox txtNamaPIC;
//	protected Textbox txtHpPIC;
	
	protected Datebox dbTglTrans;
	
	protected Decimalbox dcmKurs;
//	protected Decimalbox dcmProvisiUSDtoUSD;
//	protected Decimalbox dcmChargeUSDtoUSD;
//	protected Decimalbox dcmProvisiIDRtoUSD;
//	protected Decimalbox dcmChargeIDRtoUSD;
//	protected Decimalbox dcmProvisiIDRtoIDRNonBCABawah;
//	protected Decimalbox dcmProvisiIDRtoIDRNonBCAAtas;
	
	
	String vNamaPIC = "-";
	String vHpPIC = "-";

	BigDecimal vProvisiUSDtoUSD = new BigDecimal(1);
	BigDecimal vChargeUSDtoUSD = new BigDecimal(1);
	BigDecimal vProvisiIDRtoUSD = new BigDecimal(1);
	BigDecimal vChargeIDRtoUSD = new BigDecimal(1);
	BigDecimal vProvisiIDRtoIDRNonBCABawah = new BigDecimal(1);
	BigDecimal vProvisiIDRtoIDRNonBCAAtas = new BigDecimal(1);	
		
    private T04paramKomisi t4paramKomisi;
    
    private T04paramKomisiService t04paramKomisiService = (T04paramKomisiService) SpringUtil.getBean("t04paramKomisiService");
    
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
    	rdAJ.setSelected(true); 
    	
    	rdSLIP.setSelected(true);
    	
    	dbTglTrans.setValue(new Date());
    	dcmKurs.setValue(new BigDecimal(0));
    	
    	
    	if (t04paramKomisiService != null){
    		t4paramKomisi = t04paramKomisiService.getT04paramKomisiByKode("01");   
    	}
         
        if (t4paramKomisi != null){
        
        	txtBerita.setValue(t4paramKomisi.getBerita());

        	vNamaPIC = t4paramKomisi.getNamapic();
        	vHpPIC = t4paramKomisi.getHppic();

        	vProvisiUSDtoUSD = t4paramKomisi.getProvisiusdtousd();
        	vChargeUSDtoUSD = t4paramKomisi.getChargeusdtousd();
        	vProvisiIDRtoUSD = t4paramKomisi.getProvisiidrtousd();
        	vChargeIDRtoUSD = t4paramKomisi.getChargeidrtousd();
        	vProvisiIDRtoIDRNonBCABawah = t4paramKomisi.getProvisiidrtoidrnonbcabawah();
        	vProvisiIDRtoIDRNonBCAAtas = t4paramKomisi.getProvisiidrtoidrnonbcaatas();	

        }
    	
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		Date vTglTrans = new Date();   
		if(CommonUtils.isNotEmpty(dbTglTrans.getValue()) == true){  
			vTglTrans = dbTglTrans.getValue();
		} 
		
		String vNoVoucher = "-";
		if (StringUtils.isNotEmpty(txtNoVoucher.getValue())) {
			vNoVoucher = txtNoVoucher.getValue();
		} 
		
		String vNoCek = "-";
		if (StringUtils.isNotEmpty(txtNoCek.getValue())) {
			vNoCek = txtNoCek.getValue();
		} 
		
		String vBerita = "-";
		if (StringUtils.isNotEmpty(txtBerita.getValue())) {
			vBerita = txtBerita.getValue();
		} 
		
		BigDecimal vKurs = new BigDecimal(1);
		if (CommonUtils.isNotEmpty(dcmKurs.getValue())) {
			vKurs = dcmKurs.getValue();
		} 
		
		
//		String vNamaPIC = "-";
//		if (StringUtils.isNotEmpty(txtNamaPIC.getValue())) {
//			vNamaPIC = txtNamaPIC.getValue();
//		} 
//		
//		String vHpPIC = "-";
//		if (StringUtils.isNotEmpty(txtHpPIC.getValue())) {
//			vHpPIC = txtHpPIC.getValue();
//		} 
//				
//		BigDecimal vProvisiUSDtoUSD = new BigDecimal(1);
//		if (CommonUtils.isNotEmpty(dcmProvisiUSDtoUSD.getValue())) {
//			vProvisiUSDtoUSD = dcmProvisiUSDtoUSD.getValue();
//		} 
//
//		BigDecimal vChargeUSDtoUSD = new BigDecimal(1);
//		if (CommonUtils.isNotEmpty(dcmChargeUSDtoUSD.getValue())) {
//			vChargeUSDtoUSD = dcmChargeUSDtoUSD.getValue();
//		} 
//		
//		BigDecimal vProvisiIDRtoUSD = new BigDecimal(1);
//		if (CommonUtils.isNotEmpty(dcmProvisiIDRtoUSD.getValue())) {
//			vProvisiIDRtoUSD = dcmProvisiIDRtoUSD.getValue();
//		} 
//		
//		BigDecimal vChargeIDRtoUSD = new BigDecimal(1);
//		if (CommonUtils.isNotEmpty(dcmChargeIDRtoUSD.getValue())) {
//			vChargeIDRtoUSD = dcmChargeIDRtoUSD.getValue();
//		} 
//		
//		BigDecimal vProvisiIDRtoIDRNonBCABawah = new BigDecimal(1);
//		if (CommonUtils.isNotEmpty(dcmProvisiIDRtoIDRNonBCABawah.getValue())) {
//			vProvisiIDRtoIDRNonBCABawah = dcmProvisiIDRtoIDRNonBCABawah.getValue();
//		} 
//
//		
//		BigDecimal vProvisiIDRtoIDRNonBCAAtas = new BigDecimal(1);
//		if (CommonUtils.isNotEmpty(dcmProvisiIDRtoIDRNonBCAAtas.getValue())) {
//			vProvisiIDRtoIDRNonBCAAtas = dcmProvisiIDRtoIDRNonBCAAtas.getValue();
//		} 
			
		
		String vJenisCetak = "SLIP";
		if (StringUtils.isNotEmpty(rdgCetak.getSelectedItem().getValue())) {
			vJenisCetak = rdgCetak.getSelectedItem().getValue();	
		} 
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02048_SlipTransferBCAAntarBank.jasper";
			
		param.put("Company",  vCompany); 
		param.put("NoVoucher",  vNoVoucher.toUpperCase()); 
		param.put("NoCek",  vNoCek); 
		param.put("Berita",  vBerita); 
		param.put("NamaPIC",  vNamaPIC); 
		
		param.put("HpPIC",  vHpPIC); 		
		param.put("TglTrans",  vTglTrans); 		
		param.put("Kurs",  vKurs); 
		param.put("ProvisiUSDtoUSD",  vProvisiUSDtoUSD); 
		param.put("ChargeUSDtoUSD",  vChargeUSDtoUSD); 
		
		param.put("ProvisiIDRtoUSD",  vProvisiIDRtoUSD); 
		param.put("ChargeIDRtoUSD",  vChargeIDRtoUSD); 
		param.put("ProvisiIDRtoIDRNonBCABawah",  vProvisiIDRtoIDRNonBCABawah); 
		param.put("ProvisiIDRtoIDRNonBCAAtas",  vProvisiIDRtoIDRNonBCAAtas); 		
		
		param.put("JenisCetak",  vJenisCetak); 
		
			
		new JReportGeneratorWindow(param, jasperRpt, "PDF"); 

		 
		
	}
 
}