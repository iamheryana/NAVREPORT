package solusi.hapis.webui.markom;


import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.markom.T05WebinarEvent.T05WebinarEventLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class FeedbackReviewCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
 
	protected Window windowFeedbackReview;
	

	
	protected Label lblWebinar;	
	protected Textbox txtWebinarID;	
	
	protected Radiogroup rdgAttendee;	 
	protected Radio rdYES;
	protected Radio rdNO;
	protected Radio rdALL;
	

	protected Radiogroup rdgChart;	 
	protected Radio rdPie;
	protected Radio rdBar;
	protected Radio rdNon;
	
	
	
	private long vT05Id;
	
	
	protected Bandbox  cmbQuestion;
	protected Listbox listQuestion;
	protected String vQuestion = "00000";

	
	private SelectQueryService selectQueryService;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		rdPie.setSelected(true);
		rdYES.setSelected(true);
		vT05Id = 0;
		
		Bandpopup popup2 = new Bandpopup();
			listQuestion = new Listbox();
			listQuestion.setMold("paging");
			listQuestion.setAutopaging(false);
			listQuestion.setWidth("800px");
			listQuestion.addEventListener(Events.ON_SELECT, selectQuestion());
			listQuestion.setParent(popup2);
		popup2.setParent(cmbQuestion);
    
	}
	
	public void onClick$btnSearchWebinarID(Event event)  {
		

		
		T05WebinarEvent webId = T05WebinarEventLOV.show(windowFeedbackReview);
		
		if(webId != null){
			txtWebinarID.setValue(webId.getWebinarId());
			lblWebinar.setValue(webId.getTopic());
			vT05Id = webId.getT05Id();
			
			
			
			
			if (CommonUtils.isNotEmpty(listQuestion)){
				listQuestion.getItems().clear();
			}
			
			List<Object[]> vResulQuestion = selectQueryService.QueryFeedbackPollingQst(vT05Id);
			if(CommonUtils.isNotEmpty(vResulQuestion)){
				for(Object[] aRslt : vResulQuestion){
					listQuestion.appendItem(aRslt[0].toString(),aRslt[1].toString());
				}
			}
		}
	}	

	private EventListener selectQuestion() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbQuestion.setValue(listQuestion.getSelectedItem().getLabel());
				vQuestion = listQuestion.getSelectedItem().getValue().toString();
				cmbQuestion.close();
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {

		
		int vOption = 8;
		if (StringUtils.isNotEmpty(rdgAttendee.getSelectedItem().getValue())) {
			vOption = Integer.valueOf(rdgAttendee.getSelectedItem().getValue());	
		} 
		
		String vJnsChart = "PIE";
		if (StringUtils.isNotEmpty(rdgChart.getSelectedItem().getValue())) {
			vJnsChart = rdgChart.getSelectedItem().getValue();	
		}
		long vQstId =  0;		
		int vSesi = 0;
		
		if (vQuestion.equals("00000")==false){
			if (vQuestion.substring(0, 1).equals("F") == true){
				String vFeedbackID = vQuestion.substring(1, vQuestion.length());
				vQstId =  Long.valueOf(vFeedbackID);
				
//				System.out.println("vFeedbackID : "+ vFeedbackID);
//				System.out.println("vQstId : "+ vQstId);
			} else {
				String vStrSesi = vQuestion.substring(0, 3);
				String vStrQst = vQuestion.substring(3, vQuestion.length());
				
				vQstId =  Long.valueOf(vStrQst);
				vSesi = Integer.valueOf(vStrSesi);
				
//				System.out.println("vStrSesi : "+ vStrSesi);
//				System.out.println("vStrQst : "+ vStrQst);
//				
//				System.out.println("vQstId : "+ vQstId);
//				System.out.println("vSesi : "+ vSesi);
			}
		}
		

		
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06014_FeedbackReview.jasper";
		
		
		
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptMarkom());
		
		
		
		param.put("QstId",  vQstId); 
		param.put("Sesi",  vSesi); 
		param.put("RegisteredEmail",  vOption); 
		param.put("JnsChart",  vJnsChart); 
		param.put("T05Id",  vT05Id); 
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}
