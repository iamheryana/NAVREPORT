package solusi.hapis.webui.security.role;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import solusi.hapis.backend.model.SecRole;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;



public class SecRolesListCtrl extends GFCBaseListCtrl<SecRole> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowsecRolesList; 
    protected Panel panelsecRolesList;
 	
	// Search Box Components 
	protected Textbox txtShort;
	protected Textbox txtLong;
	
	// Paging and list
    protected Borderlayout borderLayout_secRolesList;
    protected Paging paging_secRolesList;
    private int startsecRolesList;
	private List<SecRole> list_secRolesList = new ArrayList<SecRole>();
    private ListModelList modelList_secRolesList = new ListModelList();
    protected Listbox listBoxsecRoles;
    
    // Databinding
    private AnnotateDataBinder binder;
    private SecRolesMainCtrl SecRolesMainCtrl;

    // List Header
    protected Listheader listheader_secRolesList_short;
    protected Listheader listheader_secRolesList_long;
    
    
    public SecRolesListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_secRolesList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_secRolesList.setDetailed(true);
		paging_secRolesList.addEventListener("onPaging", onPaging_secRolesList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setSecRolesMainCtrl((SecRolesMainCtrl) arg.get("ModuleMainController"));
		
		    getSecRolesMainCtrl().setSecRolesListCtrl(this);
		}
        
		
    }    
    
    public void onCreate$windowsecRolesList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
        listheader_secRolesList_short.setSortAscending(new SecRolesComparator(true, SecRolesComparator.COMPARE_BY_SHORT));
        listheader_secRolesList_short.setSortDescending(new SecRolesComparator(false, SecRolesComparator.COMPARE_BY_SHORT));        
        
        listheader_secRolesList_long.setSortAscending(new SecRolesComparator(true, SecRolesComparator.COMPARE_BY_LONG));
        listheader_secRolesList_long.setSortDescending(new SecRolesComparator(false, SecRolesComparator.COMPARE_BY_LONG));        
        
             
        getSearchData();
        
        listBoxsecRoles.setItemRenderer(renderTable());
        
        windowsecRolesList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
								
		if (CommonUtils.isNotEmpty(txtShort.getValue())) {
			parameterInput.put("rolShortdescription", txtShort.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtLong.getValue())) {
			parameterInput.put("rolLongdescription", txtLong.getValue());
		}
		
				
		list_secRolesList.clear();
		
		List<SecRole> tempListsecRoles = getSecRolesMainCtrl().getSecurityService().getListSecRoles(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListsecRoles)) {
			list_secRolesList.addAll(tempListsecRoles);
			startsecRolesList = 0;
			paging_secRolesList.setActivePage(0);
		}

		setModelsecRolesList();
	}

	// Pembagian data untuk paging
	private void setModelsecRolesList() {		
		modelList_secRolesList.clear();
		
		SecRole selectedData = null;
		if (CommonUtils.isNotEmpty(list_secRolesList)){
			
			int endsecRolesList = 0;
			if(startsecRolesList + paging_secRolesList.getPageSize() < list_secRolesList.size()) {
				endsecRolesList = startsecRolesList + paging_secRolesList.getPageSize();
			} else {
				endsecRolesList = list_secRolesList.size();
			}
			
			if (startsecRolesList > endsecRolesList) {
				startsecRolesList = 0;
				paging_secRolesList.setActivePage(0);
			}
			
			modelList_secRolesList.addAll(list_secRolesList.subList(startsecRolesList, endsecRolesList));

			paging_secRolesList.setDetailed(true);
			paging_secRolesList.setTotalSize(list_secRolesList.size());
			
			listBoxsecRoles.setModel(modelList_secRolesList);
			listBoxsecRoles.setSelectedIndex(0);
	
			selectedData = list_secRolesList.get(startsecRolesList);
		} else {
			paging_secRolesList.setDetailed(false);
			listBoxsecRoles.setModel(modelList_secRolesList);
			paging_secRolesList.setTotalSize(0);
		}
		
		getSecRolesMainCtrl().setSelectedSecRole(selectedData);

	}
	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final SecRole rec = (SecRole) data;

                Listcell lc;

                // Audit Trail
                lc = new Listcell();
                lc.setImage("/images/icons/information_icon.jpg");
                lc.setStyle("cursor: help");
                Popup info = new Popup();
                Vbox vbox = new Vbox();
//                Label lblCreateBy = new Label("Dibuat Oleh = " + rec.getCreatedBy());
//                Label lblCreateOn = new Label("Dibuat Pada = " + CommonUtils.convertDateToString(rec.getCreatedOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
//                Label lblUpdateBy = new Label("Dimodifikasi Oleh = " + rec.getUpdatedBy());
//                Label lblUpdateOn = new Label("Dimodifikasi Pada = " + CommonUtils.convertDateToString(rec.getUpdatedOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                Label lblVersion = new Label("Version = " + rec.getVersion());
//                lblCreateBy.setParent(vbox);
//                lblCreateOn.setParent(vbox);
//                lblUpdateBy.setParent(vbox);
//                lblUpdateOn.setParent(vbox);
                lblVersion.setParent(vbox);
                vbox.setParent(info);
                info.setParent(windowsecRolesList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(rec.getRolShortdescription());
                lc.setParent(item);
                
                lc = new Listcell(rec.getRolLongdescription());
                lc.setParent(item);
                
      
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedsecRolesItem");
              }
        };
    }
    
	private EventListener onPaging_secRolesList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startsecRolesList = pageNo * paging_secRolesList.getPageSize();
		         setModelsecRolesList();
			}
		};
	}


    public void onDoubleClickedsecRolesItem(Event event) {
		if (listBoxsecRoles.getSelectedItem() != null) {
			SecRole selectedData = (SecRole) listBoxsecRoles.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedSecRole(selectedData);
	
	            if (getSecRolesMainCtrl().getSecRolesDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getSecRolesMainCtrl().tabsecRolesDetail, null));
	            } else if (getSecRolesMainCtrl().getSecRolesDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getSecRolesMainCtrl().tabsecRolesDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getSecRolesMainCtrl().tabsecRolesDetail, selectedData));
	            
	            getSecRolesMainCtrl().getSecRolesDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxsecRoles(Event event) {
		if (listBoxsecRoles.getSelectedItem() != null) {
			SecRole selectedData = (SecRole) listBoxsecRoles.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getSecRolesMainCtrl().getSecRolesDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getSecRolesMainCtrl().tabsecRolesDetail, null));
		
				} else if (getSecRolesMainCtrl().getSecRolesDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getSecRolesMainCtrl().tabsecRolesDetail, null));
				}
				
				getSecRolesMainCtrl().setSelectedSecRole(selectedData);
				
			}
		}	
    }


    public void onSort$listheader_secRolesList_short(Event event) {
    	sortingData(listheader_secRolesList_short, SecRolesComparator.COMPARE_BY_SHORT);
    }
    
    public void onSort$listheader_secRolesList_long(Event event) { 
    	sortingData(listheader_secRolesList_long, SecRolesComparator.COMPARE_BY_LONG);
    }
    

    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_secRolesList, new SecRolesComparator(false, sortBy));
    		} else {
    			Collections.sort(list_secRolesList, new SecRolesComparator(true, sortBy));
    		}
    	}
    	
    	setModelsecRolesList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_secRolesList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowsecRolesList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	txtShort.setValue(null);
    	txtLong.setValue(null);
    	
    	//startsecRolesList = 0;
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


    public void setSelectedSecRole(SecRole selectedSecRole) {
        getSecRolesMainCtrl().setSelectedSecRole(selectedSecRole);
    }

    public SecRole getSelectedSecRole() {
        return getSecRolesMainCtrl().getSelectedSecRole();
    }

    public void setSecRolesMainCtrl(SecRolesMainCtrl SecRolesMainCtrl) {
        this.SecRolesMainCtrl = SecRolesMainCtrl;
    }

    public SecRolesMainCtrl getSecRolesMainCtrl() {
        return this.SecRolesMainCtrl;
    }

}
