package solusi.hapis.webui.security.user;

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
import org.zkoss.zul.Combobox;
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

import solusi.hapis.backend.model.SecUser;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

public class SecUsersListCtrl extends GFCBaseListCtrl<SecUser> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8769978331085363954L;

	protected Window windowSecUsersList;

	protected Paging paging_SecUserslist;
	protected Listbox listBoxSecUser;
    private int startSecUsersList;
    private List<SecUser> list_SecUsersList = new ArrayList<SecUser>();
    private ListModelList modelList_SecUsersList = new ListModelList();
   
    
	protected transient AnnotateDataBinder binder;

	private SecUsersMainCtrl secUsersMainCtrl;
	
	protected Listheader listheader_UserList_usrLoginname;
	protected Listheader listheader_UserList_usrFirstName;
	protected Listheader listheader_UserList_usrLastName;
	//protected Listheader listheader_UserList_accCabang;
	//protected Listheader listheader_UserList_accGolongan;
	protected Listheader listheader_UserList_usrEmail;
	protected Listheader listheader_UserList_expiredDate;
	protected Listheader listheader_UserList_status;
	
	// filter components
	protected Textbox txtLoginName;
	protected Textbox txtFirstName;
	protected Textbox txtLastName;
//	protected Combobox cmbFlagAccCabang;
//	protected Combobox cmbFlagAccGolongan;
	protected Textbox txtEmailName;
	protected Textbox txtTglSuspend;
	protected Combobox cmbFlagActiv;
	

	public SecUsersListCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);

		paging_SecUserslist.setPageSize(CommonUtils.PAGE_SIZE);
		paging_SecUserslist.setDetailed(true);
		paging_SecUserslist.addEventListener("onPaging", onPaging_SecUserslist());

		
		if (arg.containsKey("ModuleMainController")) {
			setSecUsersMainCtrl((SecUsersMainCtrl) arg.get("ModuleMainController"));
		
		    getSecUsersMainCtrl().setSecUsersListCtrl(this);
		}
	}

	
	
	private EventListener onPaging_SecUserslist(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startSecUsersList = pageNo * paging_SecUserslist.getPageSize();
		         setModelSecUsersList();
			}
		};
	}
	

	
	
	private void setModelSecUsersList() {		
		modelList_SecUsersList.clear();
		
		SecUser selectedData = null;
		if (CommonUtils.isNotEmpty(list_SecUsersList)){
			
			int endSecUsersList = 0;
			if(startSecUsersList + paging_SecUserslist.getPageSize() < list_SecUsersList.size()) {
				endSecUsersList = startSecUsersList + paging_SecUserslist.getPageSize();
			} else {
				endSecUsersList = list_SecUsersList.size();
			}
			
			if (startSecUsersList > endSecUsersList) {
				startSecUsersList = 0;
				paging_SecUserslist.setActivePage(0);
			}
			
			modelList_SecUsersList.addAll(list_SecUsersList.subList(startSecUsersList, endSecUsersList));

			paging_SecUserslist.setDetailed(true);
			paging_SecUserslist.setTotalSize(list_SecUsersList.size());
			
			listBoxSecUser.setModel(modelList_SecUsersList);
			listBoxSecUser.setSelectedIndex(0);
	
			selectedData = list_SecUsersList.get(startSecUsersList);
		} else {
			paging_SecUserslist.setDetailed(false);
			listBoxSecUser.setModel(modelList_SecUsersList);
			paging_SecUserslist.setTotalSize(0);
		}
		
		getSecUsersMainCtrl().setSelectedSecUser(selectedData);
	}
	
	public void onCreate$windowSecUsersList(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		doFillListbox();
		binder.loadAll();
	}

	public void doFillListbox() {
		doFitSize();
	
		 // Sebagai inisialisai awal supaya sorting bisa dilakukan
		listheader_UserList_usrLoginname.setSortAscending(new SecUsersComparator(true, SecUsersComparator.COMPARE_BY_LOGINNAME));
		listheader_UserList_usrLoginname.setSortDescending(new SecUsersComparator(false, SecUsersComparator.COMPARE_BY_LOGINNAME));        
        
		listheader_UserList_usrFirstName.setSortAscending(new SecUsersComparator(true, SecUsersComparator.COMPARE_BY_FIRSTNAME));
		listheader_UserList_usrFirstName.setSortDescending(new SecUsersComparator(false, SecUsersComparator.COMPARE_BY_FIRSTNAME));        
        
		listheader_UserList_usrLastName.setSortAscending(new SecUsersComparator(true, SecUsersComparator.COMPARE_BY_LASTNAME));
		listheader_UserList_usrLastName.setSortDescending(new SecUsersComparator(false, SecUsersComparator.COMPARE_BY_LASTNAME));   
		
		//listheader_UserList_accCabang.setSortAscending(new SecUsersComparator(true, SecUsersComparator.COMPARE_BY_ACC_CABANG));
		//listheader_UserList_accCabang.setSortDescending(new SecUsersComparator(false, SecUsersComparator.COMPARE_BY_ACC_CABANG));   

		//listheader_UserList_accGolongan.setSortAscending(new SecUsersComparator(true, SecUsersComparator.COMPARE_BY_ACC_GOLONGAN));
		//listheader_UserList_accGolongan.setSortDescending(new SecUsersComparator(false, SecUsersComparator.COMPARE_BY_ACC_GOLONGAN));   
		

		listheader_UserList_usrEmail.setSortAscending(new SecUsersComparator(true, SecUsersComparator.COMPARE_BY_EMAIL));
		listheader_UserList_usrEmail.setSortDescending(new SecUsersComparator(false, SecUsersComparator.COMPARE_BY_EMAIL)); 
		
		listheader_UserList_expiredDate.setSortAscending(new SecUsersComparator(true, SecUsersComparator.COMPARE_BY_EXPIREDDATE));
		listheader_UserList_expiredDate.setSortDescending(new SecUsersComparator(false, SecUsersComparator.COMPARE_BY_EXPIREDDATE));   
        
		listheader_UserList_status.setSortAscending(new SecUsersComparator(true, SecUsersComparator.COMPARE_BY_STATUS));
		listheader_UserList_status.setSortDescending(new SecUsersComparator(false, SecUsersComparator.COMPARE_BY_STATUS));   
	    
		searchTable();

		listBoxSecUser.setItemRenderer(renderTable());

		windowSecUsersList.addEventListener(Events.ON_OK, onSubmitForm());
	}

	private ListitemRenderer renderTable() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {
				final SecUser user = (SecUser) data;

				Listcell lc;
				
				// Audit Trail
                lc = new Listcell();
                lc.setImage("/images/icons/information_icon.jpg");
                lc.setStyle("cursor: help");
                Popup info = new Popup();
                Vbox vbox = new Vbox();
                Label lblCreateBy = new Label("Dibuat Oleh = " + user.getCreatedBy());
                Label lblCreateOn = new Label("Dibuat Pada = " + CommonUtils.convertDateToString(user.getCreatedOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                Label lblUpdateBy = new Label("Dimodifikasi Oleh = " + user.getUpdatedBy());
                Label lblUpdateOn = new Label("Dimodifikasi Pada = " + CommonUtils.convertDateToString(user.getUpdatedOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                Label lblVersion = new Label("Version = " + user.getVersion());
                lblCreateBy.setParent(vbox);
                lblCreateOn.setParent(vbox);
                lblUpdateBy.setParent(vbox);
                lblUpdateOn.setParent(vbox);
                lblVersion.setParent(vbox);
                vbox.setParent(info);
                info.setParent(windowSecUsersList);
                lc.setTooltip(info);
                lc.setParent(item);
                
				lc = new Listcell(user.getUsrLoginname());
				lc.setParent(item);
				
				lc = new Listcell(user.getUsrFirstname());
				lc.setParent(item);
				
				lc = new Listcell(user.getUsrLastname());
				lc.setParent(item);
				
//				String vAccCabang = user.getAccessCabang().equals("A")?"Ya (Checked)":"Tidak (Unchecked)";
//                lc = new Listcell(vAccCabang);
//                lc.setParent(item); 
//                
//                String vAccGolongan = user.getAccessGolongan().equals("A")?"Ya (Checked)":"Tidak (Unchecked)";
//                lc = new Listcell(vAccGolongan);
//                lc.setParent(item); 
				
				lc = new Listcell(user.getUsrEmail());
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.convertDateToString(user.getExpiredDate(), CommonUtils.DATE_FORMAT_DDMMYYYY));
                lc.setParent(item);
                
                String vStatus = user.getFlagActiv().equals("Y")?"Aktif":"Suspend";
                lc = new Listcell(vStatus);
                lc.setParent(item); 
              
                item.setValue(data);
				item.setAttribute("data", data);
				ComponentsCtrl.applyForward(item,
						"onDoubleClick=onUserListItemDoubleClicked");
			}
		};
	}

	
	public void onUserListItemDoubleClicked(Event event) {
		if (getSelectedSecUser() != null) {
			Events.sendEvent(new Event("onSelect", getSecUsersMainCtrl().tabSecUsersDetail, getSelectedSecUser()));
		}
		
		if (listBoxSecUser.getSelectedItem() != null) {
			SecUser selectedData = (SecUser) listBoxSecUser.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedSecUser(selectedData);
	
	            if (getSecUsersMainCtrl().getSecUsersDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getSecUsersMainCtrl().tabSecUsersDetail, null));
	            } else if (getSecUsersMainCtrl().getSecUsersDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getSecUsersMainCtrl().tabSecUsersDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getSecUsersMainCtrl().tabSecUsersDetail, selectedData));
	            
	            getSecUsersMainCtrl().getSecUsersDetailCtrl().doRenderMode("View"); 
			}
		}
	}

	public void onSelect$listBoxSecUser(Event event) {
		if (listBoxSecUser.getSelectedItem() != null) {
			SecUser selectedData = (SecUser) listBoxSecUser.getSelectedItem().getValue();
			if (selectedData != null) {
				
				if (getSecUsersMainCtrl().getSecUsersDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getSecUsersMainCtrl().tabSecUsersDetail, null));
		
				} else if (getSecUsersMainCtrl().getSecUsersDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getSecUsersMainCtrl().tabSecUsersDetail, null));
				}
				
				getSecUsersMainCtrl().setSelectedSecUser(selectedData);
				
			}
		}	
	}

	public void doFitSize() {
		windowSecUsersList.invalidate();
	}

	public void searchTable() {
		
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (CommonUtils.isNotEmpty(txtLoginName.getValue())) {
			parameterInput.put("usrLoginname", txtLoginName.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtFirstName.getValue())) {
			parameterInput.put("usrFirstname", txtFirstName.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtLastName.getValue())) {
			parameterInput.put("usrLastname", txtLastName.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtEmailName.getValue())) {
			parameterInput.put("usrEmail", txtEmailName.getValue());
		}
		if (CommonUtils.isValidDateFormat(txtTglSuspend.getValue())) {
			Date tanggalFrom = CommonUtils.convertStringToDate(txtTglSuspend.getValue());
			Date tanggalTo = CommonUtils.createSecondParameterForSearch(txtTglSuspend.getValue());
			parameterInput.put("expiredDateFrom", tanggalFrom);
			parameterInput.put("expiredDateTo", tanggalTo);
		}
		
		if (cmbFlagActiv.getSelectedIndex() != 0) {
			parameterInput.put("flagActiv", (String) cmbFlagActiv.getSelectedItem().getValue());
		}
		
//		if (cmbFlagAccCabang.getSelectedIndex() != 0) {
//			parameterInput.put("accessCabang", (String) cmbFlagAccCabang.getSelectedItem().getValue());
//		}
//		
//		if (cmbFlagAccGolongan.getSelectedIndex() != 0) {
//			parameterInput.put("accessGolongan", (String) cmbFlagAccGolongan.getSelectedItem().getValue());
//		}
		
		list_SecUsersList.clear();
		
		List<SecUser> tempList = getSecUsersMainCtrl().getUserService().getListSecUser(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempList)) {
			list_SecUsersList.addAll(tempList);
			startSecUsersList = 0;
			paging_SecUserslist.setActivePage(0);
		}

		setModelSecUsersList();
	}

	public void clearSearchBox() {
		txtLoginName.setValue(null);
		txtFirstName.setValue(null);
		txtLastName.setValue(null);
		txtEmailName.setValue(null);
		txtTglSuspend.setValue(null);
    	cmbFlagActiv.setSelectedIndex(0);
	}
	
    public void onSort$listheader_UserList_usrLoginname(Event event) {
    	sortingData(listheader_UserList_usrLoginname, SecUsersComparator.COMPARE_BY_LOGINNAME);
    }
    
    public void onSort$listheader_UserList_usrFirstName(Event event) {
    	sortingData(listheader_UserList_usrFirstName, SecUsersComparator.COMPARE_BY_FIRSTNAME);
    }
    
    public void onSort$listheader_UserList_usrLastName(Event event) {
    	sortingData(listheader_UserList_usrLastName, SecUsersComparator.COMPARE_BY_LASTNAME);
    }
    
//    public void onSort$listheader_UserList_accCabang(Event event) {
//    	sortingData(listheader_UserList_accCabang, SecUsersComparator.COMPARE_BY_ACC_CABANG);
//    }
//    
//    public void onSort$listheader_UserList_accGolongan(Event event) {
//    	sortingData(listheader_UserList_accGolongan, SecUsersComparator.COMPARE_BY_ACC_GOLONGAN);
//    }
	
    public void onSort$listheader_UserList_usrEmail(Event event) {
    	sortingData(listheader_UserList_usrEmail, SecUsersComparator.COMPARE_BY_EMAIL);
    }
    
    public void onSort$listheader_UserList_expiredDate(Event event) {
    	sortingData(listheader_UserList_expiredDate, SecUsersComparator.COMPARE_BY_EXPIREDDATE);
    }
    
    public void onSort$listheader_UserList_status(Event event) {
    	sortingData(listheader_UserList_status, SecUsersComparator.COMPARE_BY_STATUS);
    }
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_SecUsersList, new SecUsersComparator(false, sortBy));
    		} else {
    			Collections.sort(list_SecUsersList, new SecUsersComparator(true, sortBy));
    		}
    	}
    	
    	setModelSecUsersList();    
    }
	
	private EventListener onSubmitForm() {
		return new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				searchTable();
			}
		};
	}

	public SecUser getSelectedSecUser() {
		return getSecUsersMainCtrl().getSelectedSecUser();
	}

	public void setSelectedSecUser(SecUser _obj) {
		getSecUsersMainCtrl().setSelectedSecUser(_obj);
	}


	public SecUsersMainCtrl getSecUsersMainCtrl() {
		return secUsersMainCtrl;
	}

	public void setSecUsersMainCtrl(SecUsersMainCtrl secUsersMainCtrl) {
		this.secUsersMainCtrl = secUsersMainCtrl;
	}

	
	
}
