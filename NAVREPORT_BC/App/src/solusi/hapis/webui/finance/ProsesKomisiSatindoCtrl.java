package solusi.hapis.webui.finance;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class ProsesKomisiSatindoCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Textbox txtTahun;

	protected Combobox cmbPeriode;
	
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
	
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglFrom = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		monthTglCurr = monthTglCurr+1;
		
		txtTahun.setValue(String.valueOf(yearTglFrom));
		
		cmbPeriode.setSelectedIndex(monthTglCurr-1);

    }
	


	public void onClick$btnOK(Event event) throws InterruptedException {
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglFrom = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		
		monthTglCurr = monthTglCurr+1;
		
		String vTahun = String.valueOf(yearTglFrom);				
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == true){  
			vTahun = txtTahun.getValue();
		}
		
		String vMasa = String.valueOf(monthTglCurr);	
		if (cmbPeriode.getSelectedIndex() != -1) {
			vMasa =  (String) cmbPeriode.getSelectedItem().getValue();
		}
		
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
        // Call Proses ----------------------------------------------------------------------------------------------------
		String vResultDownload = callStoreProcOrFuncService.callProsesKomisiSatindo(vProsesId, vMasa, vTahun, "PROSES");
		
		ZksampleMessageUtils.showInformationMessage(vResultDownload);
		return;
         
       //-----------------------------------------------------------------------------------------------------------------  
	 
		
	}
 
	
	public void onClick$btnCANCEL(Event event) throws InterruptedException {
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglFrom = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		
		monthTglCurr = monthTglCurr+1;
		
		String vTahun = String.valueOf(yearTglFrom);				
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == true){  
			vTahun = txtTahun.getValue();
		}
		
		String vMasa = String.valueOf(monthTglCurr);	
		if (cmbPeriode.getSelectedIndex() != -1) {
			vMasa =  (String) cmbPeriode.getSelectedItem().getValue();
		}
		
		
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
        // Call Proses ----------------------------------------------------------------------------------------------------
		String vResultDownload =  callStoreProcOrFuncService.callProsesKomisiSatindo(vProsesId, vMasa, vTahun, "UNPROSES");
		
		ZksampleMessageUtils.showInformationMessage(vResultDownload);
		return;
         
       //-----------------------------------------------------------------------------------------------------------------  
  		

	 
		
	}
}

