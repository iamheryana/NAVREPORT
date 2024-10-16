package solusi.hapis.webui.finance.CostingSatindo;



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

public class ProsesTQSSatindoCtrl extends GFCBaseCtrl implements Serializable {

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

		txtTahun.setValue(String.valueOf(yearTglFrom));
		
		cmbPeriode.setSelectedIndex(0);

    }
	


	public void onClick$btnOK(Event event) throws InterruptedException {
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglFrom = cTgl.get(Calendar.YEAR);

		
		String vTahun = String.valueOf(yearTglFrom);				
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == true){  
			vTahun = txtTahun.getValue();
		}
		
		String vMasa = "01";
		
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
        // Call Proses ----------------------------------------------------------------------------------------------------
		String vResultDownload = callStoreProcOrFuncService.callProsesTQSSatindo(vProsesId, vMasa, vTahun, "PROSES");
		
		ZksampleMessageUtils.showInformationMessage(vResultDownload);
		return;
         
       //-----------------------------------------------------------------------------------------------------------------  
	 
		
	}
 
	
	public void onClick$btnCANCEL(Event event) throws InterruptedException {
		
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglFrom = cTgl.get(Calendar.YEAR);

		
		String vTahun = String.valueOf(yearTglFrom);				
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == true){  
			vTahun = txtTahun.getValue();
		}
		
		String vMasa = "01";
		
		
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
        // Call Proses ----------------------------------------------------------------------------------------------------
		String vResultDownload =  callStoreProcOrFuncService.callProsesTQSSatindo(vProsesId, vMasa, vTahun, "UNPROSES");
		
		ZksampleMessageUtils.showInformationMessage(vResultDownload);
		return;
         
       //-----------------------------------------------------------------------------------------------------------------  
  		

	 
		
	}
}

