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
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.markom.T05WebinarEvent.T05WebinarEventLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class LapWebinarAttendeeCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	protected Window windowLapWebinarAttendee;
	
	protected Radiogroup rdgAttendee;	 
	protected Radio rdYES;
	protected Radio rdNO;
	protected Radio rdALL;

	
	protected Textbox txtWebinarID;	
	protected Label lblWebinar;	
		
	private long vT05Id;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 

		vT05Id = 0;
    	rdALL.setSelected(true); 
	}
	
	public void onClick$btnSearchWebinarID(Event event)  {
		T05WebinarEvent webId = T05WebinarEventLOV.show(windowLapWebinarAttendee);
		
		if(webId != null){
			txtWebinarID.setValue(webId.getWebinarId());
			lblWebinar.setValue(webId.getTopic());
			
			vT05Id = webId.getT05Id();
		}
	}	

		
	@SuppressWarnings("unchecked")
	public void onClick$btn1(Event event) throws InterruptedException, ParseException {
		
		String vHadir = "ALL";
		if (StringUtils.isNotEmpty(rdgAttendee.getSelectedItem().getValue())) {
			vHadir = rdgAttendee.getSelectedItem().getValue();	
		} 
		
		
	
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06012_ReportAttendee.jasper";
		
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptMarkom());
	
		param.put("T05Id",  vT05Id); 
		param.put("Hadir",  vHadir); 
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btn2(Event event) throws InterruptedException, ParseException {
		
	
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06012_ReportPolling.jasper";
		
		
		

		param.put("T05Id",  vT05Id); 
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btn3(Event event) throws InterruptedException, ParseException {
		
	
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06016_ReportQA.jasper";
		
		
		

		param.put("T05Id",  vT05Id); 
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}