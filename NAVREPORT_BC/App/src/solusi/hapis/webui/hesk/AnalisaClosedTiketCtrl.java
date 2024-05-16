package solusi.hapis.webui.hesk;

import java.io.Serializable;
import java.text.ParseException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;

import solusi.hapis.backend.parameter.service.SelectQueryHeskService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;


public class AnalisaClosedTiketCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
    private SelectQueryHeskService selectQueryHeskService;
    
	protected Combobox  cmbStatus;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		cmbStatus.setSelectedIndex(0);
	}
	

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		

		String vStatus = "ALL";
		if (cmbStatus.getSelectedItem().getValue() != null){
			vStatus = (String) cmbStatus.getSelectedItem().getValue();
		}
		
		
		String timeStamp = String.valueOf(System.currentTimeMillis());
		// Call Proses ----------------------------------------------------------------------------------------------------
		String vResultDownload = selectQueryHeskService.callGetHistory(timeStamp);		 
		//-----------------------------------------------------------------------------------------------------------------  
		if (vResultDownload.equals("OK") == true) {
			String jasperRpt = "/solusi/hapis/webui/reports/hesk/07001_AnalisaClosedTiket.jasper";
			
			param.put("processId",  timeStamp); 	
			param.put("status",  vStatus); 
			
			
			new JReportGeneratorWindow(param, jasperRpt, "XLS", 1.0); 
			
		} else {
			ZksampleMessageUtils.showInformationMessage(vResultDownload);
			return;
		}
		
		
	}
 
}
