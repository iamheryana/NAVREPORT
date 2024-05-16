package solusi.hapis.webui.markom.T05WebinarEvent;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.backend.navbi.model.T06WebinarAttendee;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 13-Juli-2020
 */

public class T05WebinarEventDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT05WebinarEventDetail;
	
	protected Borderlayout borderlayout_T05WebinarEventDetail;
	
	// Data Entry Component

//	protected Textbox txtWebinarId;
//	protected Textbox txtTopic;	
//	protected Intbox intActualDuration;	
	protected Intbox intNumRegistered;
	protected Intbox intNumCancelled;
	protected Intbox intUniqueViewers;
	protected Intbox intTotalUsers;
	protected Intbox intMaxConcurrentViews;
//	protected Datebox dbActualStartTime;
	
	
	// Button tabel Detail
	protected Button btnView;
	protected Button btnFeedback;
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
	private List<T06WebinarAttendee> list_DeletedT05WebinarEventDetailList = new ArrayList<T06WebinarAttendee>();

	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T05WebinarEventMainCtrl T05WebinarEventMainCtrl;
	
	
	public final static int PAGE_SIZE_WEBINAR_HEADER = 5;
	public final static int PAGE_SIZE_WEBINAR_DETAIL = 10;

	/**
	 * default constructor.<br>
	 */
	public T05WebinarEventDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);
		
		paging_T05WebinarEventDetailList.setPageSize(PAGE_SIZE_WEBINAR_DETAIL);
		paging_T05WebinarEventDetailList.setDetailed(true);
		paging_T05WebinarEventDetailList.addEventListener("onPaging",
				onPaging_T05WebinarEventDetailList());
		listBoxT05WebinarEventDetail.setItemRenderer(renderTableDetail());



		if (arg.containsKey("ModuleMainController")) {
			setT05WebinarEventMainCtrl((T05WebinarEventMainCtrl) arg
					.get("ModuleMainController"));

			getT05WebinarEventMainCtrl().setT05WebinarEventDetailCtrl(this);
		}
		
		windowT05WebinarEventDetail.addEventListener(Events.ON_OK, onEnterForm());
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

			int endT05WebinarEventDetailList = 0;
			if (startT05WebinarEventDetailList + paging_T05WebinarEventDetailList.getPageSize() < list_T05WebinarEventDetailList
					.size()) {
				endT05WebinarEventDetailList = startT05WebinarEventDetailList
						+ paging_T05WebinarEventDetailList.getPageSize();
			} else {
				endT05WebinarEventDetailList = list_T05WebinarEventDetailList.size();
			}

			if (startT05WebinarEventDetailList > endT05WebinarEventDetailList) {
				startT05WebinarEventDetailList = 0;
				paging_T05WebinarEventDetailList.setActivePage(0);
			}

			modelList_T05WebinarEventDetailList.addAll(list_T05WebinarEventDetailList.subList(
					startT05WebinarEventDetailList, endT05WebinarEventDetailList));

			paging_T05WebinarEventDetailList.setDetailed(true);
			paging_T05WebinarEventDetailList
					.setTotalSize(list_T05WebinarEventDetailList.size());

			listBoxT05WebinarEventDetail.setModel(modelList_T05WebinarEventDetailList);
			listBoxT05WebinarEventDetail.setSelectedIndex(0);

			getT05WebinarEvent().setT06WebinarAttendees(new HashSet<T06WebinarAttendee>(list_T05WebinarEventDetailList));
		} else {
			paging_T05WebinarEventDetailList.setDetailed(false);
			listBoxT05WebinarEventDetail.setModel(modelList_T05WebinarEventDetailList);
			paging_T05WebinarEventDetailList.setTotalSize(0);
			getT05WebinarEvent().setT06WebinarAttendees(null);
		}

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
	
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
//				getT05WebinarEventMainCtrl().onClick$btnSave(event);
			}
		};
    }
    
	public void onCreate$windowT05WebinarEventDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		
		doFillListbox();
		
		binder.loadAll();
	}

	public void doFillListbox() {
		doFitSize();
		
		//Detail 
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
      


	}

	
	public void doFitSize() {
		windowT05WebinarEventDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){			
//				txtWebinarId.setReadonly(true);
//				txtTopic.setReadonly(true);
//				intActualDuration.setReadonly(true);
				intNumRegistered.setReadonly(true);
				intNumCancelled.setReadonly(true);
				intUniqueViewers.setReadonly(true);
				intTotalUsers.setReadonly(true);
				intMaxConcurrentViews.setReadonly(true);


//				dbActualStartTime.setDisabled(true);
				
			}
			
			
		}
	}

	
//	public void onClick$btnSearchSalesLOV(Event event) {
//		T05WebinarEvent sales = T05WebinarEventLOV.show(windowT05WebinarEventDetail);
//
//		if (sales != null) {
//			txtSPV.setValue(sales.getSales());
//		
//		} 
//		
//	}
//	
//	public String validasiBusinessRules(){
//		
//		if(CommonUtils.isNotEmpty(txtSales.getValue()) == false){
//			return "Sales "+Labels.getLabel("message.error.mandatory");
//		}	
//		
//		
//
//		
//		
//		return null;
//	}
    

	public void displayDetail(List<T06WebinarAttendee> dataDetail) {
		list_DeletedT05WebinarEventDetailList.clear();
		list_T05WebinarEventDetailList.clear();

		if (CommonUtils.isNotEmpty(dataDetail)) {
			list_T05WebinarEventDetailList.addAll(dataDetail);
		}

		setModelT05WebinarEventDetailList();
	}

//	public void onClick$btnNew(Event event) {
//		T06WebinarAttendee newDetail = new T06WebinarAttendee();
//		newDetail.setT05WebinarEvent(getT05WebinarEvent());
//		newDetail.setTarget(new BigDecimal(0));
//
//		T06WebinarAttendee newValue = T05WebinarEventAddDetailWindow.show(
//				windowT05WebinarEventDetail, newDetail, getT05WebinarEvent());
//
//		if (newValue != null) {			
//			list_T05WebinarEventDetailList.add(newValue);
//			setModelT05WebinarEventDetailList();
//			onClick$btnNew(event);
//		}
//	}
//	
	public void onClick$btnView(Event event) {
		if (listBoxT05WebinarEventDetail.getSelectedItem() != null) {
			T06WebinarAttendee selectedItem = (T06WebinarAttendee) listBoxT05WebinarEventDetail
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T05WebinarEventDetailList.indexOf(selectedItem);

				T06WebinarAttendee editValue = T05WebinarEventAddDetailWindow.show(
						windowT05WebinarEventDetail, selectedItem, getT05WebinarEvent());
				if (editValue != null) {
					list_T05WebinarEventDetailList.set(index, editValue);
					setModelT05WebinarEventDetailList();
				}
			}
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnFeedback(Event event) {
		if (listBoxT05WebinarEventDetail.getSelectedItem() != null) {
			T06WebinarAttendee selectedItem = (T06WebinarAttendee) listBoxT05WebinarEventDetail
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				String jasperRpt = "/solusi/hapis/webui/reports/markom/06010_FeedbackPerson.jasper";
				
				long vT08Id = 0;
				if (CommonUtils.isNotEmpty(selectedItem.getT05WebinarEvent().getT05Id())){
					vT08Id = selectedItem.getT05WebinarEvent().getT05Id();
				}
				
				
				param.put("T08Id",  vT08Id); 
				param.put("Email",  selectedItem.getEmail()); 
						
				
				new JReportGeneratorWindow(param, jasperRpt, "XLS");
				
				
			}
		}
	}
//	
//	public void onClick$btnDelete(Event event) throws InterruptedException {
//		if (listBoxT05WebinarEventDetail.getSelectedItem() != null) {
//			final T06WebinarAttendee selectedItem = (T06WebinarAttendee) listBoxT05WebinarEventDetail
//					.getSelectedItem().getValue();
//			if (selectedItem != null) {
//
//				// Show a confirm box
//				String deleteRecord = selectedItem.getTahun();
//				
//				final String msg = Labels
//						.getLabel("message.Question.Are_you_sure_to_delete_this_record")
//						+ "\n\n --> " + deleteRecord;
//				final String title = Labels.getLabel("message.Deleting.Record");
//
//				MultiLineMessageBox.doSetTemplate();
//				if (MultiLineMessageBox.show(msg, title, Messagebox.YES
//						| Messagebox.NO, Messagebox.QUESTION, true,
//						new EventListener() {
//							@Override
//							public void onEvent(Event evt) {
//								switch (((Integer) evt.getData()).intValue()) {
//								case MultiLineMessageBox.YES:
//									try {
//										deleteBean();
//									} catch (InterruptedException e) {
//										e.printStackTrace();
//									}
//									break;
//								case MultiLineMessageBox.NO:
//									break;
//								}
//							}
//
//							private void deleteBean()
//									throws InterruptedException {
//								if (selectedItem.getT06Id() > 0) {
//									list_DeletedT05WebinarEventDetailList
//											.add(selectedItem);
//								}
//								list_T05WebinarEventDetailList.remove(selectedItem);
//								setModelT05WebinarEventDetailList();
//							}
//						}) == MultiLineMessageBox.YES) {
//				}
//
//			}
//		}
//	}
	
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
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T05WebinarEvent getT05WebinarEvent() {
		return getT05WebinarEventMainCtrl().getSelectedT05WebinarEvent();
	}

	public void setT05WebinarEvent(T05WebinarEvent selectedT05WebinarEvent) {
		getT05WebinarEventMainCtrl().setSelectedT05WebinarEvent(selectedT05WebinarEvent);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT05WebinarEventMainCtrl(T05WebinarEventMainCtrl T05WebinarEventMainCtrl) {
		this.T05WebinarEventMainCtrl = T05WebinarEventMainCtrl;
	}

	public T05WebinarEventMainCtrl getT05WebinarEventMainCtrl() {
		return this.T05WebinarEventMainCtrl;
	}

	public List<T06WebinarAttendee> getList_DeletedT05WebinarEventDetailList() {
		return list_DeletedT05WebinarEventDetailList;
	}

	public void setList_DeletedT05WebinarEventDetailList(
			List<T06WebinarAttendee> list_DeletedT05WebinarEventDetailList) {
		this.list_DeletedT05WebinarEventDetailList = list_DeletedT05WebinarEventDetailList;
	}

	
	
}
