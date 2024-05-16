package solusi.hapis.webui.security.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import solusi.hapis.backend.model.SecLog;
import solusi.hapis.backend.model.SecUser;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

public class SecLogListCtrl extends GFCBaseListCtrl<SecUser> implements
		Serializable {
	private static final long serialVersionUID = 8769978331085363954L;

	protected Window windowSecLogList;

	protected Borderlayout borderLayout_SecLogList;
	protected Paging paging_SecLoglist;
	protected Listbox listBoxSecLog;
    private int startSecLogList;
    private List<SecLog> list_SecLogList = new ArrayList<SecLog>();
    private ListModelList modelList_SecLogList = new ListModelList();
   
    
	protected transient AnnotateDataBinder binder;

	private SecLogMainCtrl SecLogMainCtrl;
	
	protected Listheader listheader_UserList_activity;
	protected Listheader listheader_UserList_performedBy;
	protected Listheader listheader_UserList_PerformedOn;
	protected Listheader listheader_UserList_Ip;
	
	// filter components
	protected Textbox txtActivity;
	protected Textbox txtPerformedBy;
	protected Textbox txtPerformedOn;
	protected Textbox txtIP;

	public SecLogListCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);

		paging_SecLoglist.setPageSize(CommonUtils.PAGE_SIZE);
		paging_SecLoglist.setDetailed(true);
		paging_SecLoglist.addEventListener("onPaging", onPaging_SecLoglist());


		if (arg.containsKey("ModuleMainController")) {
			setSecLogMainCtrl((SecLogMainCtrl) arg.get("ModuleMainController"));
		
		    getSecLogMainCtrl().setSecLogListCtrl(this);
		}
	}

	
	private EventListener onPaging_SecLoglist(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startSecLogList = pageNo * paging_SecLoglist.getPageSize();
		         setModelSecLogList();
			}
		};
	}
	
	private void setModelSecLogList() {		
		modelList_SecLogList.clear();
		
		if (CommonUtils.isNotEmpty(list_SecLogList)){
			
			int endSecLogList = 0;
			if(startSecLogList + paging_SecLoglist.getPageSize() < list_SecLogList.size()) {
				endSecLogList = startSecLogList + paging_SecLoglist.getPageSize();
			} else {
				endSecLogList = list_SecLogList.size();
			}
			
			if (startSecLogList > endSecLogList) {
				startSecLogList = 0;
				paging_SecLoglist.setActivePage(0);
			}
			
			modelList_SecLogList.addAll(list_SecLogList.subList(startSecLogList, endSecLogList));

			paging_SecLoglist.setDetailed(true);
			paging_SecLoglist.setTotalSize(list_SecLogList.size());
			
			listBoxSecLog.setModel(modelList_SecLogList);
			listBoxSecLog.setSelectedIndex(0);

		} else {
			paging_SecLoglist.setDetailed(false);
			listBoxSecLog.setModel(modelList_SecLogList);
			paging_SecLoglist.setTotalSize(0);
		}
		

	}
	
	public void onCreate$windowSecLogList(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		doFillListbox();
		binder.loadAll();
	}

	public void doFillListbox() {
		doFitSize();
	
		 // Sebagai inisialisai awal supaya sorting bisa dilakukan
		listheader_UserList_activity.setSortAscending(new SecLogComparator(true, SecLogComparator.COMPARE_BY_ACTIVITY));
		listheader_UserList_activity.setSortDescending(new SecLogComparator(false, SecLogComparator.COMPARE_BY_ACTIVITY));        
        
		listheader_UserList_performedBy.setSortAscending(new SecLogComparator(true, SecLogComparator.COMPARE_BY_PERFORMED_BY));
		listheader_UserList_performedBy.setSortDescending(new SecLogComparator(false, SecLogComparator.COMPARE_BY_PERFORMED_BY));        
        
		listheader_UserList_PerformedOn.setSortAscending(new SecLogComparator(true, SecLogComparator.COMPARE_BY_PERFORMED_ON));
		listheader_UserList_PerformedOn.setSortDescending(new SecLogComparator(false, SecLogComparator.COMPARE_BY_PERFORMED_ON));        
	
		listheader_UserList_Ip.setSortAscending(new SecLogComparator(true, SecLogComparator.COMPARE_BY_IP));
		listheader_UserList_Ip.setSortDescending(new SecLogComparator(false, SecLogComparator.COMPARE_BY_IP));        
	
		
		searchTable();

		listBoxSecLog.setItemRenderer(renderTable());

		windowSecLogList.addEventListener(Events.ON_OK, onSubmitForm());
	}

	private ListitemRenderer renderTable() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {
				final SecLog log = (SecLog) data;

				Listcell lc;
				
				lc = new Listcell(log.getLogActivity());
				lc.setParent(item);
				
				lc = new Listcell(log.getPerformedBy());
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.convertDateToString(log.getPerformedOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
				lc.setParent(item);
				
				lc = new Listcell(log.getIp());
				lc.setParent(item);				
				
                item.setValue(data);
				item.setAttribute("data", data);
				ComponentsCtrl.applyForward(item,
						"onDoubleClick=onUserListItemDoubleClicked");
			}
		};
	}

	public void doFitSize() {
		final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_SecLogList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		windowSecLogList.invalidate();
	}

	public void searchTable() {
		
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (CommonUtils.isNotEmpty(txtActivity.getValue())) {
			parameterInput.put("logActivity", txtActivity.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtPerformedBy.getValue())) {
			parameterInput.put("performedBy", txtPerformedBy.getValue());
		}
		
		if (CommonUtils.isValidDateFormat(txtPerformedOn.getValue())) {
			Date tanggalFrom = CommonUtils.convertStringToDate(txtPerformedOn.getValue());
			Date tanggalTo = CommonUtils.createSecondParameterForSearch(txtPerformedOn.getValue());
			parameterInput.put("performedOnFrom", tanggalFrom);
			parameterInput.put("performedOnTo", tanggalTo);
		}
		
		if (CommonUtils.isNotEmpty(txtIP.getValue())) {
			parameterInput.put("ip", txtIP.getValue());
		}
		
		
		list_SecLogList.clear();
		
		List<SecLog> tempList = getSecLogMainCtrl().getSecurityService().getListSecLog(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempList)) {
			list_SecLogList.addAll(tempList);
			startSecLogList = 0;
			paging_SecLoglist.setActivePage(0);
		}

		setModelSecLogList();
	}

	public void clearSearchBox() {
		txtActivity.setValue(null);
		txtPerformedBy.setValue(null);
		txtPerformedOn.setValue(null);
	}
	

    public void onSort$listheader_UserList_activity(Event event) {
    	sortingData(listheader_UserList_activity, SecLogComparator.COMPARE_BY_ACTIVITY);
    }
    
    public void onSort$listheader_UserList_performedBy(Event event) {
    	sortingData(listheader_UserList_performedBy, SecLogComparator.COMPARE_BY_PERFORMED_BY);
    }
    
    public void onSort$listheader_UserList_PerformedOn(Event event) {
    	sortingData(listheader_UserList_PerformedOn, SecLogComparator.COMPARE_BY_PERFORMED_ON);
    }
    
    public void onSort$listheader_UserList_Ip(Event event) {
    	sortingData(listheader_UserList_Ip, SecLogComparator.COMPARE_BY_IP);
    }
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_SecLogList, new SecLogComparator(false, sortBy));
    		} else {
    			Collections.sort(list_SecLogList, new SecLogComparator(true, sortBy));
    		}
    	}
    	
    	setModelSecLogList();    
    }
    
    
	private EventListener onSubmitForm() {
		return new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				searchTable();
			}
		};
	}

	public SecLogMainCtrl getSecLogMainCtrl() {
		return SecLogMainCtrl;
	}

	public void setSecLogMainCtrl(SecLogMainCtrl SecLogMainCtrl) {
		this.SecLogMainCtrl = SecLogMainCtrl;
	}

}
