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


public class SummaryEventCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	protected Window windowSummaryEvent;
	
	
	protected Textbox txtWebinarID;	
	protected Label lblWebinar;	
		
	private long vT05Id;
	
	
	protected Radiogroup rdgJnsRpt;
	protected Radio rd1;
	protected Radio rd2;
	protected Radio rd3;
	protected Radio rd4;
	
//	protected Radiogroup rdgSave;	 
//	protected Radio rdPDF;
//	protected Radio rdXLS;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
//		rdPDF.setSelected(true); 
		rd1.setSelected(true); 
		vT05Id = 0;
	}
	
	public void onClick$btnSearchWebinarID(Event event)  {
		T05WebinarEvent webId = T05WebinarEventLOV.show(windowSummaryEvent);
		
		if(webId != null){
			txtWebinarID.setValue(webId.getWebinarId());
			lblWebinar.setValue(webId.getTopic());
			
			vT05Id = webId.getT05Id();
		}
	}	

		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		
		String vJnsRpt = "SUM";
		if (StringUtils.isNotEmpty(rdgJnsRpt.getSelectedItem().getValue())) {
			vJnsRpt = rdgJnsRpt.getSelectedItem().getValue();	
		} 
		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06013_SummaryEvent.jasper";
		
		if (vJnsRpt.equals("SUM") == true){
			jasperRpt = "/solusi/hapis/webui/reports/markom/06013_SummaryEvent.jasper";
		} else {
			if (vJnsRpt.equals("DTL1") == true){
				jasperRpt = "/solusi/hapis/webui/reports/markom/06013_02_NamaPerusahaan.jasper";
			} else {
				if (vJnsRpt.equals("DTL2") == true){
					jasperRpt = "/solusi/hapis/webui/reports/markom/06013_03_AttendeeByTimeDuration.jasper";
				} else {
					jasperRpt = "/solusi/hapis/webui/reports/markom/06013_04_AttendeeByArea.jasper";
				}
			}
		}
		
		
		
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptMarkom());
	
		param.put("T05Id",  vT05Id); 
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		
//		String vSaveAs = "PDF";
//		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
//			vSaveAs = rdgSave.getSelectedItem().getValue();	
//		} 
//		
//		if(vSaveAs.equals("PDF")){
//			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
//		} else {
//			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
//		}
		
		
		
	}
 
}