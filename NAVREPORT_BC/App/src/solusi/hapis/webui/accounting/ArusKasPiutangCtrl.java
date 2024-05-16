package solusi.hapis.webui.accounting;


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
import org.zkoss.zul.Row;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class ArusKasPiutangCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdAJ;
	protected Radio rdSP;

	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSum;
	protected Radio rdDtl;
	
	protected Radiogroup rdgJnsDtl;	 
	protected Radio rdB1;
	protected Radio rdB2;
	protected Radio rdB31;
	protected Radio rdB32;
	protected Radio rdB41;
	protected Radio rdB42;
	protected Radio rdB43;
	protected Radio rdB44;
	
	protected Radiogroup rdgJnsPeredaran;	 
	protected Radio rdPALL;
	protected Radio rdPUsaha;
	protected Radio rdPUsaha2;
	protected Radio rdPNonUsaha;
	
	protected Row RowJenisDetail;
	protected Row RowBankAccount;
	protected Row RowPeredaran;
	
	
	protected Bandbox  cmbBankAcc;
	protected Listbox listBankAcc;
	protected String vBankAcc = "ALL";

	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	private String vProsesId;

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
    	
    	rdSum.setSelected(true); 
    	
    	rdB1.setSelected(true);

    	RowJenisDetail.setVisible(false);
    	RowBankAccount.setVisible(false);
    	RowPeredaran.setVisible(false);
    	
    	rdPALL.setSelected(true);
    	
    	Bandpopup popup2 = new Bandpopup();
			listBankAcc = new Listbox();
			listBankAcc.setMold("paging");
			listBankAcc.setAutopaging(false);
			listBankAcc.setWidth("400px");
			listBankAcc.addEventListener(Events.ON_SELECT, selectBankAcc());
			listBankAcc.setParent(popup2);
		popup2.setParent(cmbBankAcc);
	
			
		List<Object[]> vResultBankAcc = selectQueryService.QueryBankAccount();
		if(CommonUtils.isNotEmpty(vResultBankAcc)){
			for(Object[] aRslt : vResultBankAcc){
				listBankAcc.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbBankAcc.setValue(listBankAcc.getItemAtIndex(0).getLabel());
		listBankAcc.setSelectedItem(listBankAcc.getItemAtIndex(0));
	
	}

	private EventListener selectBankAcc() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbBankAcc.setValue(listBankAcc.getSelectedItem().getLabel());
				vBankAcc = listBankAcc.getSelectedItem().getValue().toString();
				cmbBankAcc.close();
			}
		};
	}
	
	public void onCheck$rdgJnsLap(Event event){
		if(rdDtl.isChecked() == true ) {
			RowJenisDetail.setVisible(true);
			rdB1.setSelected(true);
			
			RowBankAccount.setVisible(true);			
			RowPeredaran.setVisible(true);
		} else {
			RowJenisDetail.setVisible(false);
			RowBankAccount.setVisible(false);			
			RowPeredaran.setVisible(false);
		}
	}
	
	public void onCheck$rdgJnsDtl(Event event){
		if(rdB1.isChecked() == true ) {
			RowBankAccount.setVisible(true);			
			RowPeredaran.setVisible(true);
		} else {
			RowBankAccount.setVisible(false);			
			RowPeredaran.setVisible(false);
		}
		
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
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJenisLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		String vJenisDetail = "B1";
		if (StringUtils.isNotEmpty(rdgJnsDtl.getSelectedItem().getValue())) {
			vJenisDetail = rdgJnsDtl.getSelectedItem().getValue();	
		} 
	
		String vPeredaran = "ALL";
		if (StringUtils.isNotEmpty(rdgJnsPeredaran.getSelectedItem().getValue())) {
			vPeredaran = rdgJnsPeredaran.getSelectedItem().getValue();	
		} 
		
		
		if (vJenisDetail.equals("B1") == false ){
			vPeredaran = "ALL";
			vBankAcc = "ALL";
		}
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0105007");
		
		
		
		vProsesId = String.valueOf(System.currentTimeMillis());
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  
		String vStrTglTo  = frmTgl.format(vTglUpto); 
		
		if (vJenisLap.equals("SUM") == true){
			@SuppressWarnings("unused")
			String vResult = callStoreProcOrFuncService.callArusKasPiutang(vProsesId, vStrTglFrom, vStrTglTo, vCompany, "CETAK");
		}

		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01073_ArusKasPiutangV3.jasper";
		if (vJenisLap.equals("SUM") == true){
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01073_ArusKasPiutangV3.jasper";
			param.put("ProsesId",  vProsesId); 	
			
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/accounting/01074_ArusKasPiutangDetailV3.jasper";
			
			param.put("JenisDetail",  vJenisDetail); 			
			param.put("KodeGroup",  vBankAcc);
			param.put("KodeSubGroup",  vPeredaran);
			
			
		}
		
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpto);
		
		param.put("Company",  vCompany); 

		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callArusKasPiutang(vProsesId, vStrTglFrom, vStrTglTo, vCompany,  "DELETE");

		
	}
 
}