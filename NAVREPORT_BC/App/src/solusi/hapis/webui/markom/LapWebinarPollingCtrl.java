package solusi.hapis.webui.markom;



import java.io.Serializable;
import java.text.ParseException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.webui.markom.T05WebinarEvent.T05WebinarEventLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class LapWebinarPollingCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	protected Window windowLapWebinarPolling;
	

	
	protected Textbox txtWebinarID;	
	protected Label lblWebinar;	

	
	private long vT05Id;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		vT05Id = 0;
	}
	
	public void onClick$btnSearchWebinarID(Event event)  {
		T05WebinarEvent webId = T05WebinarEventLOV.show(windowLapWebinarPolling);
		
		if(webId != null){
			txtWebinarID.setValue(webId.getWebinarId());
			lblWebinar.setValue(webId.getTopic());
			
			vT05Id = webId.getT05Id();
		}
		
		
	}	

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
	
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06012_ReportPolling.jasper";
		
		
		

		param.put("T05Id",  vT05Id); 
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}