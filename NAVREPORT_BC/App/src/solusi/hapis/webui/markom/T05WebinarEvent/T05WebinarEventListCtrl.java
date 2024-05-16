package solusi.hapis.webui.markom.T05WebinarEvent;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.backend.navbi.model.T06WebinarAttendee;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 13-Juli-2020
 */

public class T05WebinarEventListCtrl extends GFCBaseListCtrl<T05WebinarEvent> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT05WebinarEventList; 
    
    protected Paging paging_T05WebinarEventList;
    protected Listbox listBoxT05WebinarEvent;
    private int startT05WebinarEventList;
   	private List<T05WebinarEvent> list_T05WebinarEventList = new ArrayList<T05WebinarEvent>();
    private ListModelList modelList_T05WebinarEventList = new ListModelList();
     
    // Databinding
    private AnnotateDataBinder binder;
    private T05WebinarEventMainCtrl T05WebinarEventMainCtrl;

    // List Header
    protected Listheader listheader_T05WebinarEventList_WebinarID;
    protected Listheader listheader_T05WebinarEventList_Topic;
    protected Listheader listheader_T05WebinarEventList_ActualStartTime;
    protected Listheader listheader_T05WebinarEventList_ActualDuration;
    protected Listheader listheader_T05WebinarEventList_NumRegistered;

 
    // Search Box Components 
 	protected Textbox txtWebinarID;
 	protected Textbox txtTopic;
 	protected Textbox txtActualStartTime;
 	protected Textbox txtActualDuration;
 	protected Textbox txtNumRegistered;
 	
 	// Paging and list Detail 
 	protected Listheader listheader_T05WebinarEventDetailList_Attended;
 	protected Listheader listheader_T05WebinarEventDetailList_FirstName;
 	protected Listheader listheader_T05WebinarEventDetailList_LastName;
 	protected Listheader listheader_T05WebinarEventDetailList_Organization;
 	protected Listheader listheader_T05WebinarEventDetailList_JobTitle;
 	protected Listheader listheader_T05WebinarEventDetailList_Email;
 	protected Listheader listheader_T05WebinarEventDetailList_JoinTime;
 	
 	protected Paging paging_T05WebinarEventDetailList;
	private int startT05WebinarEventDetailList;
	private List<T06WebinarAttendee> list_T05WebinarEventDetailList = new ArrayList<T06WebinarAttendee>();
	private ListModelList modelList_T05WebinarEventDetailList = new ListModelList();
	
	protected Listbox listBoxT05WebinarEventDetail;
     
   
	public final static int PAGE_SIZE_WEBINAR_HEADER = 5;
	public final static int PAGE_SIZE_WEBINAR_DETAIL = 10;
    
    public T05WebinarEventListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T05WebinarEventList.setPageSize(PAGE_SIZE_WEBINAR_HEADER);
		paging_T05WebinarEventList.setDetailed(true);
		paging_T05WebinarEventList.addEventListener("onPaging", onPaging_T05WebinarEventList());
	
		paging_T05WebinarEventDetailList.setPageSize(PAGE_SIZE_WEBINAR_DETAIL);
		paging_T05WebinarEventDetailList.setDetailed(true);
		paging_T05WebinarEventDetailList.addEventListener("onPaging", onPaging_T05WebinarEventDetailList());
		listBoxT05WebinarEventDetail.setItemRenderer(renderTableDetail());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT05WebinarEventMainCtrl((T05WebinarEventMainCtrl) arg.get("ModuleMainController"));
		
		    getT05WebinarEventMainCtrl().setT05WebinarEventListCtrl(this);
		}
        
		
    }    
    
	private EventListener onPaging_T05WebinarEventList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT05WebinarEventList = pageNo * paging_T05WebinarEventList.getPageSize();
		         setModelT05WebinarEventList();
			}
		};
	}

	private EventListener onPaging_T05WebinarEventDetailList() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startT05WebinarEventDetailList = pageNo
						* paging_T05WebinarEventDetailList.getPageSize();
				setModelT05WebinarEventDetailList();
			}
		};
	}
	
	private void setModelT05WebinarEventDetailList() {
		modelList_T05WebinarEventDetailList.clear();

		if (CommonUtils.isNotEmpty(list_T05WebinarEventDetailList)) {

			int endUserDetailCabList = 0;
			if (startT05WebinarEventDetailList
					+ paging_T05WebinarEventDetailList.getPageSize() < list_T05WebinarEventDetailList
						.size()) {
				endUserDetailCabList = startT05WebinarEventDetailList
						+ paging_T05WebinarEventDetailList.getPageSize();
			} else {
				endUserDetailCabList = list_T05WebinarEventDetailList.size();
			}

			if (startT05WebinarEventDetailList > endUserDetailCabList) {
				startT05WebinarEventDetailList = 0;
				paging_T05WebinarEventDetailList.setActivePage(0);
			}

			modelList_T05WebinarEventDetailList.addAll(list_T05WebinarEventDetailList.subList(
					startT05WebinarEventDetailList, endUserDetailCabList));

			paging_T05WebinarEventDetailList.setDetailed(true);
			paging_T05WebinarEventDetailList.setTotalSize(list_T05WebinarEventDetailList
					.size());

			listBoxT05WebinarEventDetail.setModel(modelList_T05WebinarEventDetailList);
			listBoxT05WebinarEventDetail.setSelectedIndex(0);

		} else {
			paging_T05WebinarEventDetailList.setDetailed(false);
			listBoxT05WebinarEventDetail.setModel(modelList_T05WebinarEventDetailList);
			paging_T05WebinarEventDetailList.setTotalSize(0);
		}

	}
	
	// Pembagian data untuk paging
	private void setModelT05WebinarEventList() {		
		modelList_T05WebinarEventList.clear();
		
		T05WebinarEvent selectedData = null;
		if (CommonUtils.isNotEmpty(list_T05WebinarEventList)){
			
			int endT05WebinarEventList = 0;
			if(startT05WebinarEventList + paging_T05WebinarEventList.getPageSize() < list_T05WebinarEventList.size()) {
				endT05WebinarEventList = startT05WebinarEventList + paging_T05WebinarEventList.getPageSize();
			} else {
				endT05WebinarEventList = list_T05WebinarEventList.size();
			}
			
			if (startT05WebinarEventList > endT05WebinarEventList) {
				startT05WebinarEventList = 0;
				paging_T05WebinarEventList.setActivePage(0);
			}
			
			modelList_T05WebinarEventList.addAll(list_T05WebinarEventList.subList(startT05WebinarEventList, endT05WebinarEventList));

			paging_T05WebinarEventList.setDetailed(true);
			paging_T05WebinarEventList.setTotalSize(list_T05WebinarEventList.size());
			
			listBoxT05WebinarEvent.setModel(modelList_T05WebinarEventList);
			listBoxT05WebinarEvent.setSelectedIndex(0);
	
			selectedData = list_T05WebinarEventList.get(startT05WebinarEventList);
		} else {
			paging_T05WebinarEventList.setDetailed(false);
			listBoxT05WebinarEvent.setModel(modelList_T05WebinarEventList);
			paging_T05WebinarEventList.setTotalSize(0);
		}
		
		getT05WebinarEventMainCtrl().setSelectedT05WebinarEvent(selectedData);
		getSearchDataDetail(selectedData);

	}
	
    public void onCreate$windowT05WebinarEventList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }  

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
 

        
        listheader_T05WebinarEventList_WebinarID.setSortAscending(new T05WebinarEventComparator(true, T05WebinarEventComparator.COMPARE_BY_WEBINARID));
        listheader_T05WebinarEventList_WebinarID.setSortDescending(new T05WebinarEventComparator(false, T05WebinarEventComparator.COMPARE_BY_WEBINARID));        
        
        listheader_T05WebinarEventList_Topic.setSortAscending(new T05WebinarEventComparator(true, T05WebinarEventComparator.COMPARE_BY_TOPIC));
        listheader_T05WebinarEventList_Topic.setSortDescending(new T05WebinarEventComparator(false, T05WebinarEventComparator.COMPARE_BY_TOPIC));        
       
        listheader_T05WebinarEventList_ActualStartTime.setSortAscending(new T05WebinarEventComparator(true, T05WebinarEventComparator.COMPARE_BY_ACTUALSTARTTIME));
        listheader_T05WebinarEventList_ActualStartTime.setSortDescending(new T05WebinarEventComparator(false, T05WebinarEventComparator.COMPARE_BY_ACTUALSTARTTIME));        
       
        listheader_T05WebinarEventList_ActualDuration.setSortAscending(new T05WebinarEventComparator(true, T05WebinarEventComparator.COMPARE_BY_ACTUALDURATION));
        listheader_T05WebinarEventList_ActualDuration.setSortDescending(new T05WebinarEventComparator(false, T05WebinarEventComparator.COMPARE_BY_ACTUALDURATION));        
       
        listheader_T05WebinarEventList_NumRegistered.setSortAscending(new T05WebinarEventComparator(true, T05WebinarEventComparator.COMPARE_BY_NUMREGISTERED));
        listheader_T05WebinarEventList_NumRegistered.setSortDescending(new T05WebinarEventComparator(false, T05WebinarEventComparator.COMPARE_BY_NUMREGISTERED));        
       
        
         
        // Detail
 	
     	listheader_T05WebinarEventDetailList_Attended.setSortAscending(new T06WebinarAttendeeComparator(true, T06WebinarAttendeeComparator.COMPARE_BY_ATTENDED));
     	listheader_T05WebinarEventDetailList_Attended.setSortDescending(new T06WebinarAttendeeComparator(false, T06WebinarAttendeeComparator.COMPARE_BY_ATTENDED));        
        
     	listheader_T05WebinarEventDetailList_FirstName.setSortAscending(new T06WebinarAttendeeComparator(true, T06WebinarAttendeeComparator.COMPARE_BY_FIRSTNAME));
     	listheader_T05WebinarEventDetailList_FirstName.setSortDescending(new T06WebinarAttendeeComparator(false, T06WebinarAttendeeComparator.COMPARE_BY_FIRSTNAME));        
      
     	listheader_T05WebinarEventDetailList_LastName.setSortAscending(new T06WebinarAttendeeComparator(true, T06WebinarAttendeeComparator.COMPARE_BY_LASTNAME));
     	listheader_T05WebinarEventDetailList_LastName.setSortDescending(new T06WebinarAttendeeComparator(false, T06WebinarAttendeeComparator.COMPARE_BY_LASTNAME));        
        
     	listheader_T05WebinarEventDetailList_Organization.setSortAscending(new T06WebinarAttendeeComparator(true, T06WebinarAttendeeComparator.COMPARE_BY_ORGANIZATION));
     	listheader_T05WebinarEventDetailList_Organization.setSortDescending(new T06WebinarAttendeeComparator(false, T06WebinarAttendeeComparator.COMPARE_BY_ORGANIZATION));        
      
     	listheader_T05WebinarEventDetailList_JobTitle.setSortAscending(new T06WebinarAttendeeComparator(true, T06WebinarAttendeeComparator.COMPARE_BY_JOBTITLE));
     	listheader_T05WebinarEventDetailList_JobTitle.setSortDescending(new T06WebinarAttendeeComparator(false, T06WebinarAttendeeComparator.COMPARE_BY_JOBTITLE));        
        
     	listheader_T05WebinarEventDetailList_Email.setSortAscending(new T06WebinarAttendeeComparator(true, T06WebinarAttendeeComparator.COMPARE_BY_EMAIL));
     	listheader_T05WebinarEventDetailList_Email.setSortDescending(new T06WebinarAttendeeComparator(false, T06WebinarAttendeeComparator.COMPARE_BY_EMAIL));        
        
     	listheader_T05WebinarEventDetailList_JoinTime.setSortAscending(new T06WebinarAttendeeComparator(true, T06WebinarAttendeeComparator.COMPARE_BY_JOINTIME));
     	listheader_T05WebinarEventDetailList_JoinTime.setSortDescending(new T06WebinarAttendeeComparator(false, T06WebinarAttendeeComparator.COMPARE_BY_JOINTIME));        
      
        
        getSearchData();
        
        listBoxT05WebinarEvent.setItemRenderer(renderTable());
        
        windowT05WebinarEventList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (CommonUtils.isNotEmpty(txtWebinarID.getValue())) {
			parameterInput.put("webinarId", txtWebinarID.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtTopic.getValue())) {
			parameterInput.put("topic", txtTopic.getValue());
		}

		if (CommonUtils.isNotEmpty(txtActualDuration.getValue())) {
			parameterInput.put("actualDuration", txtActualDuration.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtNumRegistered.getValue())) {
			parameterInput.put("numRegistered", txtNumRegistered.getValue());
		}

		if (CommonUtils.isValidDateFormat(txtActualStartTime.getValue())) {
			Date tglInv1 = CommonUtils.convertStringToDate(txtActualStartTime.getValue());
			Date tglInv2 = CommonUtils.createSecondParameterForSearch(txtActualStartTime.getValue());
			parameterInput.put("actualStartTimeFrom", tglInv1);
			parameterInput.put("actualStartTimeTo", tglInv2);
		}
		
		list_T05WebinarEventList.clear();
		
		List<T05WebinarEvent> tempListT05WebinarEvent = getT05WebinarEventMainCtrl().getT05WebinarEventService().getListT05WebinarEvent(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT05WebinarEvent)) {
			list_T05WebinarEventList.addAll(tempListT05WebinarEvent);
			startT05WebinarEventList = 0;
			paging_T05WebinarEventList.setActivePage(0);
		}

		setModelT05WebinarEventList();
	}

  
	public void getSearchDataDetail(T05WebinarEvent t05WebinarEvent) {
		list_T05WebinarEventDetailList.clear();
		
		if (t05WebinarEvent != null) {
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();		
			
			if (CommonUtils.isNotEmpty(t05WebinarEvent.getT05Id())) {
				parameterInputDetail.put("t05Id", t05WebinarEvent.getT05Id());
			}
			
			
			List<T06WebinarAttendee> tempT06WebinarAttendee = getT05WebinarEventMainCtrl().getT05WebinarEventService().getListT06WebinarAttendee(parameterInputDetail); 
			if (CommonUtils.isNotEmpty(tempT06WebinarAttendee)) {
				list_T05WebinarEventDetailList.addAll(tempT06WebinarAttendee);
				startT05WebinarEventDetailList = 0;
				
				paging_T05WebinarEventDetailList.setActivePage(0);
			}
			
		}
		
		setModelT05WebinarEventDetailList();
	}
	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T05WebinarEvent rec = (T05WebinarEvent) data;

                Listcell lc;

                // Audit Trail
                lc = new Listcell();
                lc.setImage("/images/icons/information_icon.jpg");
                lc.setStyle("cursor: help");
                Popup info = new Popup();
                Vbox vbox = new Vbox();
                Label lblCreateBy = new Label("Dibuat Oleh = " + rec.getCreatedBy());
                Label lblCreateOn = new Label("Dibuat Pada = " + CommonUtils.convertDateToString(rec.getCreatedOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                Label lblUpdateBy = new Label("Dimodifikasi Oleh = " + rec.getUpdatedBy());
                Label lblUpdateOn = new Label("Dimodifikasi Pada = " + CommonUtils.convertDateToString(rec.getUpdatedOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                Label lblVersion = new Label("Version = " + rec.getVersion());
                lblCreateBy.setParent(vbox);
                lblCreateOn.setParent(vbox);
                lblUpdateBy.setParent(vbox);
                lblUpdateOn.setParent(vbox);
                lblVersion.setParent(vbox);
                vbox.setParent(info);
                info.setParent(windowT05WebinarEventList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(rec.getWebinarId());
                lc.setParent(item);
                
                lc = new Listcell(rec.getTopic());
                lc.setParent(item);
                
            	lc = new Listcell(CommonUtils.convertDateToString(rec.getActualStartTime(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getActualDuration(), "#,###"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
             
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getNumRegistered(), "#,###"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
      
				item.setValue(data);    
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT05WebinarEventItem");
              }
        };
    }
    
    private ListitemRenderer renderTableDetail() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final T06WebinarAttendee rec = (T06WebinarAttendee) data;

				Listcell lc;
					
				lc = new Listcell(rec.getAttended());
				lc.setParent(item);
				
				lc = new Listcell(rec.getFirstName());
				lc.setParent(item);
				
				lc = new Listcell(rec.getLastName());
				lc.setParent(item);
				
				lc = new Listcell(rec.getOrganization());
				lc.setParent(item);
				
				lc = new Listcell(rec.getJobTitle());
				lc.setParent(item);
				
				lc = new Listcell(rec.getEmail());
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getTimeInSession(),"#,###"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}

    public void onDoubleClickedT05WebinarEventItem(Event event) {
    	if (getSelectedT05WebinarEvent() != null) {
			Events.sendEvent(new Event("onSelect", getT05WebinarEventMainCtrl().tabT05WebinarEventDetail, getSelectedT05WebinarEvent()));
		}
		
		if (listBoxT05WebinarEvent.getSelectedItem() != null) {
			T05WebinarEvent selectedData = (T05WebinarEvent) listBoxT05WebinarEvent.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT05WebinarEvent(selectedData);
	
	            if (getT05WebinarEventMainCtrl().getT05WebinarEventDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT05WebinarEventMainCtrl().tabT05WebinarEventDetail, null));
	            } else if (getT05WebinarEventMainCtrl().getT05WebinarEventDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT05WebinarEventMainCtrl().tabT05WebinarEventDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT05WebinarEventMainCtrl().tabT05WebinarEventDetail, selectedData));
	            
	            getT05WebinarEventMainCtrl().getT05WebinarEventDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT05WebinarEvent(Event event) {
    	
    	if (listBoxT05WebinarEvent.getSelectedItem() != null) {
			T05WebinarEvent selectedData = (T05WebinarEvent) listBoxT05WebinarEvent.getSelectedItem().getValue();

			if (selectedData != null) {
				
				if (getT05WebinarEventMainCtrl().getT05WebinarEventDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT05WebinarEventMainCtrl().tabT05WebinarEventDetail, null));
		
				} else if (getT05WebinarEventMainCtrl().getT05WebinarEventDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT05WebinarEventMainCtrl().tabT05WebinarEventDetail, null));
				}
				
				getT05WebinarEventMainCtrl().setSelectedT05WebinarEvent(selectedData);
				getSearchDataDetail(selectedData);

			}
		}	
    }
    
    public void doFitSize() {
		windowT05WebinarEventList.invalidate();
	}
       
    
    
   
    public void onSort$listheader_T05WebinarEventList_WebinarID(Event event) { 
    	sortingData(listheader_T05WebinarEventList_WebinarID, T05WebinarEventComparator.COMPARE_BY_WEBINARID);
    }
    
    public void onSort$listheader_T05WebinarEventList_Topic(Event event) { 
    	sortingData(listheader_T05WebinarEventList_Topic, T05WebinarEventComparator.COMPARE_BY_TOPIC);
    }
    
    public void onSort$listheader_T05WebinarEventList_ActualStartTime(Event event) { 
    	sortingData(listheader_T05WebinarEventList_ActualStartTime, T05WebinarEventComparator.COMPARE_BY_ACTUALSTARTTIME);
    }
    
    public void onSort$listheader_T05WebinarEventList_ActualDuration(Event event) { 
    	sortingData(listheader_T05WebinarEventList_ActualDuration, T05WebinarEventComparator.COMPARE_BY_ACTUALDURATION);
    }
    
    public void onSort$listheader_T05WebinarEventList_NumRegistered(Event event) { 
    	sortingData(listheader_T05WebinarEventList_NumRegistered, T05WebinarEventComparator.COMPARE_BY_NUMREGISTERED);
    }
    
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T05WebinarEventList, new T05WebinarEventComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T05WebinarEventList, new T05WebinarEventComparator(true, sortBy));
    		}
    	}
    	
    	setModelT05WebinarEventList();    
    }
    
    //=============================================================================================================================================
    
 	
    // Detail
	
 	
    public void onSort$listheader_T05WebinarEventDetailList_Attended(Event event) {
    	sortingDataDetail(listheader_T05WebinarEventDetailList_Attended, T06WebinarAttendeeComparator.COMPARE_BY_ATTENDED);
    }

    
    public void onSort$listheader_T05WebinarEventDetailList_FirstName(Event event) {
    	sortingDataDetail(listheader_T05WebinarEventDetailList_FirstName, T06WebinarAttendeeComparator.COMPARE_BY_FIRSTNAME);
    }

    public void onSort$listheader_T05WebinarEventDetailList_LastName(Event event) {
    	sortingDataDetail(listheader_T05WebinarEventDetailList_LastName, T06WebinarAttendeeComparator.COMPARE_BY_LASTNAME);
    }
    
    
    public void onSort$listheader_T05WebinarEventDetailList_Organization(Event event) {
    	sortingDataDetail(listheader_T05WebinarEventDetailList_Organization, T06WebinarAttendeeComparator.COMPARE_BY_ORGANIZATION);
    }
    
    public void onSort$listheader_T05WebinarEventDetailList_JobTitle(Event event) {
    	sortingDataDetail(listheader_T05WebinarEventDetailList_JobTitle, T06WebinarAttendeeComparator.COMPARE_BY_JOBTITLE);
    }
    
    public void onSort$listheader_T05WebinarEventDetailList_Email(Event event) {
    	sortingDataDetail(listheader_T05WebinarEventDetailList_Email, T06WebinarAttendeeComparator.COMPARE_BY_EMAIL);
    }
    
    public void onSort$listheader_T05WebinarEventDetailList_JoinTime(Event event) {
    	sortingDataDetail(listheader_T05WebinarEventDetailList_JoinTime, T06WebinarAttendeeComparator.COMPARE_BY_JOINTIME);
    }
    
    private void sortingDataDetail(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T05WebinarEventDetailList, new T06WebinarAttendeeComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T05WebinarEventDetailList, new T06WebinarAttendeeComparator(true, sortBy));
    		}
    	}
    	
    	setModelT05WebinarEventDetailList();    
    }
    
    
    

    public void clearSearchBox(){
        // empty the text search boxes

    	txtWebinarID.setValue(null);
    	txtTopic.setValue(null);
    	txtActualStartTime.setValue(null);
    	txtActualDuration.setValue(null);
    	txtNumRegistered.setValue(null);
     	
     	
    }

    
    private EventListener onSubmitForm(){
    	return new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				getSearchData();
			}
		};
    }
    

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // ++++++++++++++++++ getter / setter +++++++++++++++++++//
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//


    public void setSelectedT05WebinarEvent(T05WebinarEvent selectedT05WebinarEvent) {
        getT05WebinarEventMainCtrl().setSelectedT05WebinarEvent(selectedT05WebinarEvent);
    }

    public T05WebinarEvent getSelectedT05WebinarEvent() {
        return getT05WebinarEventMainCtrl().getSelectedT05WebinarEvent();
    }

    public void setT05WebinarEventMainCtrl(T05WebinarEventMainCtrl T05WebinarEventMainCtrl) {
        this.T05WebinarEventMainCtrl = T05WebinarEventMainCtrl;
    }

    public T05WebinarEventMainCtrl getT05WebinarEventMainCtrl() {
        return this.T05WebinarEventMainCtrl;
    }
    
    public List<T06WebinarAttendee> getList_T05WebinarEventDetailList() {
		return list_T05WebinarEventDetailList;
	}

	public void setList_T05WebinarEventDetailList(List<T06WebinarAttendee> list_T05WebinarEventDetailList) {
		this.list_T05WebinarEventDetailList = list_T05WebinarEventDetailList;
	}


}
