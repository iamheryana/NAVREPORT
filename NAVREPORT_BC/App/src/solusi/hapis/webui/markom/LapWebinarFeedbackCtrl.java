package solusi.hapis.webui.markom;


import java.io.Serializable;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.webui.markom.T05WebinarEvent.T05WebinarEventLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class LapWebinarFeedbackCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	protected Window windowLapWebinarFeedback;
	

	
	protected Textbox txtWebinarID;	
	protected Label lblWebinar;	
	
	protected Radiogroup rdgAttendee;	 
	protected Radio rdYES;
	protected Radio rdNO;
	protected Radio rdALL;
	
	
	protected Radiogroup rdgFormat;	 
	protected Radio rdNON;
	protected Radio rdGRP;



	
	private long vT05Id;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		rdYES.setSelected(true);
		rdNON.setSelected(true);
		vT05Id = 0;
	}
	
	public void onClick$btnSearchWebinarID(Event event)  {
		T05WebinarEvent webId = T05WebinarEventLOV.show(windowLapWebinarFeedback);
		
		if(webId != null){
			txtWebinarID.setValue(webId.getWebinarId());
			lblWebinar.setValue(webId.getTopic());
			
			vT05Id = webId.getT05Id();
		}
	}	

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		String vFormat = "NON";
		if (StringUtils.isNotEmpty(rdgFormat.getSelectedItem().getValue())) {
			vFormat = rdgFormat.getSelectedItem().getValue();	
		} 
		
		
		int vOption = 8;
		if (StringUtils.isNotEmpty(rdgAttendee.getSelectedItem().getValue())) {
			vOption = Integer.valueOf(rdgAttendee.getSelectedItem().getValue());	
		} 
	
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06011_FeedbackForms.jasper";
		if(vFormat.equals("NON") == true){
			jasperRpt = "/solusi/hapis/webui/reports/markom/06011_FeedbackForms.jasper";
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/markom/06011_FeedbackForms_v2.jasper";
		}
		
		
		param.put("RegisteredEmail",  vOption); 
		param.put("T05Id",  vT05Id); 
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}