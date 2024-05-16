package solusi.hapis.webui.markom.T05WebinarEvent;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.backend.navbi.service.T05WebinarEventService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 13-Juli-2020
 */

public class T05WebinarEventMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT05WebinarEventMain; 

    // Tabs
    protected Tabbox tabbox_T05WebinarEventMain;
    protected Tab tabT05WebinarEventList;
    protected Tab tabT05WebinarEventDetail;
    //protected Tab tabT05WebinarEventPrint;
    
    protected Tabpanel tabPanelT05WebinarEventList;
    protected Tabpanel tabPanelT05WebinarEventDetail;
    //protected Tabpanel tabPanelT05WebinarEventPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T05WebinarEvent_";
    private ButtonStatusCtrl btnCtrlT05WebinarEvent;
    protected Button btnDelete;
 //   protected Button btnSave;
    protected Button btnCancel;
    protected Button btnSearch;
    protected Button btnClear;
    protected Button btnView;
    protected Button btnFeedbackForm;

    // Tab-Controllers for getting the binders
    private T05WebinarEventListCtrl T05WebinarEventListCtrl;
    private T05WebinarEventDetailCtrl T05WebinarEventDetailCtrl;
    //private T05WebinarEventPrintCtrl T05WebinarEventPrintCtrl;

    // Databinding
    private T05WebinarEvent selectedT05WebinarEvent;
    private T05WebinarEvent postedT05WebinarEvent;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T05WebinarEventService T05WebinarEventService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/markom/T05WebinarEvent/T05WebinarEventDetail.zul";
	private String zulPageList = "/WEB-INF/pages/markom/T05WebinarEvent/T05WebinarEventList.zul";
//	private String zulPagePrint = "/WEB-INF/pages/tabel/T05WebinarEvent/T05WebinarEventPrint.zul";

    /**
     * Form State <br/>
     * 0 = inquiry <br/>
     * 1 = new <br/>
     * 2 = edit <br/>
     * 3 = delete <br/>
     */
    private int state = 0;
    
    /**
     * default constructor.<br>
     */
    public T05WebinarEventMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT05WebinarEventMain(Event event) throws Exception {
        windowT05WebinarEventMain.setContentStyle("padding:0px;");

        btnCtrlT05WebinarEvent = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, null, null, btnDelete, null, null, null, btnCancel, btnSearch, btnClear);
        btnCtrlT05WebinarEvent.addButtonOther1(btnView, "View");
        btnCtrlT05WebinarEvent.addButtonOther2(btnFeedbackForm, "Feedback-Forms");
        
        btnCtrlT05WebinarEvent.setInitInquiryButton();

        tabT05WebinarEventList.setSelected(true);

        if (tabPanelT05WebinarEventList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT05WebinarEventList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT05WebinarEventList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT05WebinarEventList.getFirstChild() != null) {

    		//RESTOREVALUE
    		doResetToInitValues();
    		
            tabT05WebinarEventList.setSelected(true);
        	btnCtrlT05WebinarEvent.setInitInquiryButton();
        	getT05WebinarEventListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT05WebinarEventList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT05WebinarEventList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT05WebinarEventDetail(Event event) throws IOException {
        if (tabPanelT05WebinarEventDetail.getFirstChild() != null) {
        	tabT05WebinarEventDetail.setSelected(true);            
        	
        	//RESTOREVALUE
    		doResetToInitValues();
    		
        	// Render Inisialisasi posisi awal
            getT05WebinarEventDetailCtrl().doRenderMode("View");   
            
            btnCtrlT05WebinarEvent.setInitInquiryButton();

            if (getT05WebinarEventDetailCtrl().getBinder() != null) {
            	getT05WebinarEventDetailCtrl().getBinder().loadAll();  
            }
            
            getT05WebinarEventDetailCtrl().displayDetail(getT05WebinarEventListCtrl().getList_T05WebinarEventDetailList());
			
			return;
		}

		if (tabPanelT05WebinarEventDetail != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelT05WebinarEventDetail,
					this, "ModuleMainController", zulPageDetail);
		}
    }

//    public void onSelect$tabT05WebinarEventPrint(Event event) throws IOException, ParseException {
//    	if (tabPanelT05WebinarEventPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT05WebinarEventPrint, this, "ModuleMainController", zulPagePrint);
//        }
//    	
//    	// Check if the tabpanel is already loaded
//        if (tabPanelT05WebinarEventPrint.getFirstChild() != null) {
//            tabT05WebinarEventPrint.setSelected(true);        
//
//            getT05WebinarEventPrintCtrl().doReadOnlyMode(true);
//            
//            return;
//        }
//
//        
//    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
//    	if (getT05WebinarEventDetailCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabT05WebinarEventDetail, null));
//        } else if (getT05WebinarEventDetailCtrl().getBinder() == null) {
//            Events.sendEvent(new Event("onSelect", tabT05WebinarEventDetail, null));
//        }
//
//        T05WebinarEvent anT05WebinarEvent = new T05WebinarEvent();
//
//        // Set Default Value Di sini ------------------------------------- start
//       
//        anT05WebinarEvent.setTarget(new BigDecimal(0));
//        setSelectedT05WebinarEvent(anT05WebinarEvent);
//        // --------------------------------------------------------------- end
//                       
//        // set button
//        btnCtrlT05WebinarEvent.setInitFormButton();
//        
//        // select tab Detail
//        tabT05WebinarEventDetail.setSelected(true);
//        renderTabAndTitle("New");
//               
//        state = 1;
//
//        if (getT05WebinarEventDetailCtrl().getBinder() != null) {
//        	getT05WebinarEventDetailCtrl().getBinder().loadAll();  
//        }
//        
//        // set render layar
//        getT05WebinarEventDetailCtrl().doRenderMode("New");
//        
//        getT05WebinarEventDetailCtrl().displayDetail(null);
//        //getT05WebinarEventDetailCtrl().doRenderCombo();
    }
    
    public void doStoreInitValues() {

        if (getSelectedT05WebinarEvent() != null) {

            try {
            	
                setPostedT05WebinarEvent((T05WebinarEvent) ZksampleBeanUtils.cloneBean(getSelectedT05WebinarEvent()));
                
                     
            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (final InstantiationException e) {
                throw new RuntimeException(e);
            } catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Saat Button View di klik
	public void onClick$btnView(Event event) throws InterruptedException {
		if(getSelectedT05WebinarEvent() != null){
	    	if (tabPanelT05WebinarEventDetail.getFirstChild() != null) {
	        	getT05WebinarEventDetailCtrl().getBinder().loadAll();
	        }
	        
	        if (getT05WebinarEventDetailCtrl() == null) {
	            Events.sendEvent(new Event("onSelect", tabT05WebinarEventDetail, null));
	        } else if (getT05WebinarEventDetailCtrl().getBinder() == null) {
	            Events.sendEvent(new Event("onSelect", tabT05WebinarEventDetail, null));
	        }
	
	        doStoreInitValues();
	        
	        // set button
	        btnCtrlT05WebinarEvent.setInitFormButton();
	
	        // select tab Detail
	        tabT05WebinarEventDetail.setSelected(true);
	        renderTabAndTitle("View");
	        
	        //state = 2;
	        
	        // set render layar
	        //getT05WebinarEventDetailCtrl().doRenderCombo();
	        getT05WebinarEventDetailCtrl().doRenderMode("View");
	        
	        getT05WebinarEventDetailCtrl().displayDetail(getT05WebinarEventListCtrl().getList_T05WebinarEventDetailList());
        
  
		}
	}
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
//		if(getSelectedT05WebinarEvent() != null){
//	    	if (tabPanelT05WebinarEventDetail.getFirstChild() != null) {
//	        	getT05WebinarEventDetailCtrl().getBinder().loadAll();
//	        }
//	        
//	        if (getT05WebinarEventDetailCtrl() == null) {
//	            Events.sendEvent(new Event("onSelect", tabT05WebinarEventDetail, null));
//	        } else if (getT05WebinarEventDetailCtrl().getBinder() == null) {
//	            Events.sendEvent(new Event("onSelect", tabT05WebinarEventDetail, null));
//	        }
//	
//	        doStoreInitValues();
//	        
//	        // set button
//	        btnCtrlT05WebinarEvent.setInitFormButton();
//	
//	        // select tab Detail
//	        tabT05WebinarEventDetail.setSelected(true);
//	        renderTabAndTitle("Edit");
//	        
//	        state = 2;
//	        
//	        // set render layar
//	        //getT05WebinarEventDetailCtrl().doRenderCombo();
//	        getT05WebinarEventDetailCtrl().doRenderMode("Edit");
//	        
//	        getT05WebinarEventDetailCtrl().displayDetail(getT05WebinarEventListCtrl().getList_T05WebinarEventDetailList());
//        
//  
//		}
	}
    
    public void doResetToInitValues() {

        if (getPostedT05WebinarEvent() != null) {

            try {
            	setSelectedT05WebinarEvent((T05WebinarEvent) ZksampleBeanUtils.cloneBean(getPostedT05WebinarEvent()));
            	
            	
                windowT05WebinarEventMain.invalidate();

            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (final InstantiationException e) {
                throw new RuntimeException(e);
            } catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT05WebinarEvent() != null) {
	        final T05WebinarEvent anT05WebinarEvent = getSelectedT05WebinarEvent();
	        if (anT05WebinarEvent != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anT05WebinarEvent.getWebinarId() +" - "+anT05WebinarEvent.getTopic();
	            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + deleteRecord;
	            final String title = Labels.getLabel("message.Deleting.Record");
	
	            MultiLineMessageBox.doSetTemplate();
	            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
	                @Override
	                public void onEvent(Event evt) {
	                    switch (((Integer) evt.getData()).intValue()) {
	                        case MultiLineMessageBox.YES:
	                            try {
	                                deleteBean();
	                            } catch (InterruptedException e) {
	                                e.printStackTrace();
	                            }
	                            break;
	                        case MultiLineMessageBox.NO:
	                            break;
	                    }
	                }
	
	                private void deleteBean() throws InterruptedException {
	                	try {
	                        getT05WebinarEventService().delete(anT05WebinarEvent);
	                        setSelectedT05WebinarEvent(null);
	                        
	                        // refresh the list
	                        getT05WebinarEventListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT05WebinarEvent.setInitInquiryButton();
	        setSelectedT05WebinarEvent(null);
	        
	        // refresh the list
	        getT05WebinarEventListCtrl().doFillListbox();
	        tabT05WebinarEventList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
//    public void onClick$btnSave(Event event) throws InterruptedException {
//    	String vErrMsg = getT05WebinarEventDetailCtrl().validasiBusinessRules();
//    	
//    	if(vErrMsg != null){
//    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
//    		return;
//    	}
//    	
//    	getT05WebinarEventDetailCtrl().getBinder().saveAll();
//    	
//    	try {
//        	if(state == 1) {
//
//        		getT05WebinarEventService().insert(getSelectedT05WebinarEvent());
//        		onClick$btnNew(event);
//        	} else if(state == 2) {
//        		
//
//        		getT05WebinarEventService().update(getSelectedT05WebinarEvent(), getT05WebinarEventDetailCtrl().getList_DeletedT05WebinarEventDetailList());
//	            // refresh the list
//	            btnCtrlT05WebinarEvent.setInitInquiryButton();
//	            getT05WebinarEventListCtrl().doFillListbox();
//                
//                tabT05WebinarEventList.setSelected(true);
//                
//                renderTabAndTitle("Save");
//        	}
//        } catch (DataAccessException e) {
//        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
//            return;
//        }
//    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT05WebinarEvent.setInitInquiryButton();
        
       	//RESTOREVALUE
       	doResetToInitValues();
       	
        tabT05WebinarEventList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT05WebinarEventListCtrl().doFillListbox();
        }
        
    	
    	if(getT05WebinarEventDetailCtrl().binder != null){
    		getT05WebinarEventDetailCtrl().binder.loadAll();
    	} 
    }
    
    // Saat Button Listing di klik
    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
//    	
//    	if (getT05WebinarEventPrintCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabT05WebinarEventPrint, null));
//        }
//
//        if (tabPanelT05WebinarEventPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT05WebinarEventPrint, this, "ModuleMainController", zulPagePrint);
//        }
//        
//        getT05WebinarEventPrintCtrl().doReadOnlyMode(false);
//        
//        btnCtrlT05WebinarEvent.setInitPrintButton();
//        tabT05WebinarEventPrint.setSelected(true);

    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
//    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listSalesperson.jasper";
//    	
//    	new JReportGeneratorWindow(getT05WebinarEventPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
    }
    

    @SuppressWarnings("unchecked")
	public void onClick$btnFeedbackForm(Event event) {
		String jasperRpt = "/solusi/hapis/webui/reports/markom/06011_FeedbackForms.jasper";
		
		long vT05Id = 0;
		if (CommonUtils.isNotEmpty(getSelectedT05WebinarEvent().getT05Id())){
			vT05Id = getSelectedT05WebinarEvent().getT05Id();
		}
		
		
		param.put("T05Id",  vT05Id); 
				
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS");
    }
    

    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT05WebinarEventListCtrl() != null) {
        	getT05WebinarEventListCtrl().getSearchData();        	
        	tabT05WebinarEventList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT05WebinarEventListCtrl() != null) {
        	getT05WebinarEventListCtrl().clearSearchBox();
        	getT05WebinarEventListCtrl().getSearchData();        	
        	tabT05WebinarEventList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit") || requestStatus.equals("View")){
	    	tabT05WebinarEventDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT05WebinarEventList.setDisabled(true);
	        //tabT05WebinarEventPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT05WebinarEventDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT05WebinarEventList.setDisabled(false);
	        //tabT05WebinarEventPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT05WebinarEvent(T05WebinarEvent selectedT05WebinarEvent) {
        this.selectedT05WebinarEvent = selectedT05WebinarEvent;
    }

    public T05WebinarEvent getSelectedT05WebinarEvent() {
        return this.selectedT05WebinarEvent;
    }


	public void setT05WebinarEventService(T05WebinarEventService T05WebinarEventService) {
        this.T05WebinarEventService = T05WebinarEventService;
    }

    public T05WebinarEventService getT05WebinarEventService() {
        return this.T05WebinarEventService;
    }

    public void setT05WebinarEventListCtrl(T05WebinarEventListCtrl T05WebinarEventListCtrl) {
        this.T05WebinarEventListCtrl = T05WebinarEventListCtrl;
    }

    public T05WebinarEventListCtrl getT05WebinarEventListCtrl() {
        return this.T05WebinarEventListCtrl;
    }

    public void setT05WebinarEventDetailCtrl(T05WebinarEventDetailCtrl T05WebinarEventDetailCtrl) {
        this.T05WebinarEventDetailCtrl = T05WebinarEventDetailCtrl;
    }

    public T05WebinarEventDetailCtrl getT05WebinarEventDetailCtrl() {
        return this.T05WebinarEventDetailCtrl;
    }

//	public void setT05WebinarEventPrintCtrl(T05WebinarEventPrintCtrl T05WebinarEventPrintCtrl) {
//		this.T05WebinarEventPrintCtrl = T05WebinarEventPrintCtrl;
//	}
//
//	public T05WebinarEventPrintCtrl getT05WebinarEventPrintCtrl() {
//		return this.T05WebinarEventPrintCtrl;
//	}

	public void setPostedT05WebinarEvent(T05WebinarEvent postedT05WebinarEvent) {
		this.postedT05WebinarEvent = postedT05WebinarEvent;
	}

	public T05WebinarEvent getPostedT05WebinarEvent() {
		return postedT05WebinarEvent;
	}

}

