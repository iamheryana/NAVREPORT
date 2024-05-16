package solusi.hapis.webui.accounting.pajak;



import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapProyeksiSalesCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	
	protected Radiogroup rdgLap;	 
	protected Radio rdJP1;
	protected Radio rdJP2;	
	
	protected Radiogroup rdgJnsRpt;	 
	protected Radio rdDTL;
	protected Radio rdSUM;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	protected Row rowBatasSem;
	protected Row rowAmount;

	protected Intbox  intTahun;
	
	protected Decimalbox dcmNilai;
	
	protected Combobox  cmbAkhirSem1;
	
	
	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		rowBatasSem.setVisible(false);	
		rowAmount.setVisible(false);		

		rdJP1.setSelected(true); 
		rdSUM.setSelected(true); 
    	dcmNilai.setValue(new BigDecimal (500));

    	Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
    	    	
		intTahun.setValue(yearTglCurr);
		cmbAkhirSem1.setSelectedIndex(3);
		
       	Bandpopup popup1 = new Bandpopup();
			listCabang = new Listbox();
			listCabang.setMold("paging");
			listCabang.setAutopaging(true);
			listCabang.setWidth("250px");
			listCabang.addEventListener(Events.ON_SELECT, selectCabang());
			listCabang.setParent(popup1);
		popup1.setParent(cmbCab);
	        
		listCabang.appendItem("ALL", "ALL");
		
		List<Object[]> vResult = selectQueryService.QueryCabang();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listCabang.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbCab.setValue(listCabang.getItemAtIndex(0).getLabel());
		listCabang.setSelectedItem(listCabang.getItemAtIndex(0));
	

	}
	
	
	private EventListener selectCabang() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCab.setValue(listCabang.getSelectedItem().getLabel());
				vCabang = listCabang.getSelectedItem().getValue().toString();
				cmbCab.close();
			}
		};
	}		
	
	
	public void onCheck$rdgLap(Event event){
		if(rdJP1.isChecked() == true ) {
			rowBatasSem.setVisible(false);	
			rowAmount.setVisible(false);	
		} else {
			rowBatasSem.setVisible(true);	
			rowAmount.setVisible(true);	
		}		
	}


	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		String vJnsLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsRpt.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsRpt.getSelectedItem().getValue();	
		} 
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglCurr = cTgl.get(Calendar.YEAR);
		
		int vTahun = yearTglCurr;
		if(CommonUtils.isNotEmpty(intTahun.getValue())){
			vTahun = intTahun.getValue();
		}
		
		BigDecimal vNilai = new BigDecimal(500);
		if (CommonUtils.isNotEmpty(dcmNilai.getValue())) {
			vNilai = dcmNilai.getValue();
		} 
		
		int vAkhirSem1 = 6;
		if (cmbAkhirSem1.getSelectedItem().getValue() != null){
			vAkhirSem1 = Integer.valueOf((String) cmbAkhirSem1.getSelectedItem().getValue());
		}
		
		
		String vJnsProy = "JP1";
		if (StringUtils.isNotEmpty(rdgLap.getSelectedItem().getValue())) {
			vJnsProy = rdgLap.getSelectedItem().getValue();	
		} 
		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/pajak/0105013_LapProyeksiSalesOutSO.jasper";
				
				
		if (vJnsProy.equals("JP1") == true) {
			
			
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0105009");
			
			jasperRpt = "/solusi/hapis/webui/reports/accounting/pajak/0105013_LapProyeksiSalesOutSO.jasper";
			
		} else {	
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0105010");
			
			
			jasperRpt = "/solusi/hapis/webui/reports/accounting/pajak/0105014_LapProyeksiSalesPipeline.jasper";			
		
			param.put("Sem1Akhir",  vAkhirSem1);
			param.put("AmtBig",  vNilai);
		}
								
				
		param.put("Tahun",  vTahun); 
		param.put("Cabang",  vCabang);
		param.put("JnsRpt",  vJnsLap);
		
		
		
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
	
		
		 
		
	}
 
}