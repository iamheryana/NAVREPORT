package solusi.hapis.webui.general.pembelian.BCUserLocation;


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
import org.zkoss.zul.Combobox;
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

import solusi.hapis.backend.navbi.model.BCUserLocation;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 07-09-2023
 */

public class BCUserLocationListCtrl extends GFCBaseListCtrl<BCUserLocation> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowBCUserLocationList; 
    protected Panel panelBCUserLocationList;
 	
	// Search Box Components 
    
	protected Textbox txtUserId;
	protected Textbox txtBranches;
	protected Textbox txtGroupName;
	
	
	// Paging and list
    protected Borderlayout borderLayout_BCUserLocationList;
    protected Paging paging_BCUserLocationList;
    private int startBCUserLocationList;
	private List<BCUserLocation> list_BCUserLocationList = new ArrayList<BCUserLocation>();
    private ListModelList modelList_BCUserLocationList = new ListModelList();
    protected Listbox listBoxBCUserLocation;
    
    // Databinding
    private AnnotateDataBinder binder;
    private BCUserLocationMainCtrl BCUserLocationMainCtrl;

    // List Header		
    protected Listheader listheader_BCUserLocationList_UserId;
    protected Listheader listheader_BCUserLocationList_Branches;	
    protected Listheader listheader_BCUserLocationList_GroupName;	

		
    
    
    public BCUserLocationListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_BCUserLocationList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_BCUserLocationList.setDetailed(true);
		paging_BCUserLocationList.addEventListener("onPaging", onPaging_BCUserLocationList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setBCUserLocationMainCtrl((BCUserLocationMainCtrl) arg.get("ModuleMainController"));
		
		    getBCUserLocationMainCtrl().setBCUserLocationListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowBCUserLocationList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan

        

        
        listheader_BCUserLocationList_UserId.setSortAscending(new BCUserLocationComparator(true, BCUserLocationComparator.COMPARE_BY_USER_ID));
        listheader_BCUserLocationList_UserId.setSortDescending(new BCUserLocationComparator(false, BCUserLocationComparator.COMPARE_BY_USER_ID));        
         
        listheader_BCUserLocationList_Branches.setSortAscending(new BCUserLocationComparator(true, BCUserLocationComparator.COMPARE_BY_BRANCHES));
        listheader_BCUserLocationList_Branches.setSortDescending(new BCUserLocationComparator(false, BCUserLocationComparator.COMPARE_BY_BRANCHES));        
       
        listheader_BCUserLocationList_GroupName.setSortAscending(new BCUserLocationComparator(true, BCUserLocationComparator.COMPARE_BY_GROUP_NAME));
        listheader_BCUserLocationList_GroupName.setSortDescending(new BCUserLocationComparator(false, BCUserLocationComparator.COMPARE_BY_GROUP_NAME));        
       
            
        getSearchData();
        
        listBoxBCUserLocation.setItemRenderer(renderTable());
        
        windowBCUserLocationList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		
		
		
		if (CommonUtils.isNotEmpty(txtUserId.getValue())) {
			parameterInput.put("userId", txtUserId.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtBranches.getValue())) {
			parameterInput.put("branches", txtBranches.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtGroupName.getValue())) {
			parameterInput.put("groupName", txtGroupName.getValue());
		}
		
	
		
		list_BCUserLocationList.clear();
		
		List<BCUserLocation> tempListBCUserLocation = getBCUserLocationMainCtrl().getBCUserLocationService().getListBCUserLocation(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListBCUserLocation)) {
			list_BCUserLocationList.addAll(tempListBCUserLocation);
			startBCUserLocationList = 0;
			paging_BCUserLocationList.setActivePage(0);
		}

		setModelBCUserLocationList();
	}

	// Pembagian data untuk paging
	private void setModelBCUserLocationList() {		
		modelList_BCUserLocationList.clear();
		
		BCUserLocation selectedData = null;
		if (CommonUtils.isNotEmpty(list_BCUserLocationList)){
			
			int endBCUserLocationList = 0;
			if(startBCUserLocationList + paging_BCUserLocationList.getPageSize() < list_BCUserLocationList.size()) {
				endBCUserLocationList = startBCUserLocationList + paging_BCUserLocationList.getPageSize();
			} else {
				endBCUserLocationList = list_BCUserLocationList.size();
			}
			
			if (startBCUserLocationList > endBCUserLocationList) {
				startBCUserLocationList = 0;
				paging_BCUserLocationList.setActivePage(0);
			}
			
			modelList_BCUserLocationList.addAll(list_BCUserLocationList.subList(startBCUserLocationList, endBCUserLocationList));

			paging_BCUserLocationList.setDetailed(true);
			paging_BCUserLocationList.setTotalSize(list_BCUserLocationList.size());
			
			listBoxBCUserLocation.setModel(modelList_BCUserLocationList);
			listBoxBCUserLocation.setSelectedIndex(0);
	
			selectedData = list_BCUserLocationList.get(startBCUserLocationList);
		} else {
			paging_BCUserLocationList.setDetailed(false);
			listBoxBCUserLocation.setModel(modelList_BCUserLocationList);
			paging_BCUserLocationList.setTotalSize(0);
		}
		
		getBCUserLocationMainCtrl().setSelectedBCUserLocation(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final BCUserLocation rec = (BCUserLocation) data;

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
                info.setParent(windowBCUserLocationList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                         	                         
                
	            lc = new Listcell(rec.getUserId());
                lc.setParent(item);
                
                lc = new Listcell(rec.getBranches());
                lc.setParent(item);
                
                lc = new Listcell(rec.getGroupName());
                lc.setParent(item);
                
          
                
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedBCUserLocationItem");
              }
        };
    }
    
	private EventListener onPaging_BCUserLocationList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startBCUserLocationList = pageNo * paging_BCUserLocationList.getPageSize();
		         setModelBCUserLocationList();
			}
		};
	}


    public void onDoubleClickedBCUserLocationItem(Event event) {
		if (listBoxBCUserLocation.getSelectedItem() != null) {
			BCUserLocation selectedData = (BCUserLocation) listBoxBCUserLocation.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedBCUserLocation(selectedData);
	
	            if (getBCUserLocationMainCtrl().getBCUserLocationDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getBCUserLocationMainCtrl().tabBCUserLocationDetail, null));
	            } else if (getBCUserLocationMainCtrl().getBCUserLocationDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getBCUserLocationMainCtrl().tabBCUserLocationDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getBCUserLocationMainCtrl().tabBCUserLocationDetail, selectedData));
	            
	            getBCUserLocationMainCtrl().getBCUserLocationDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxBCUserLocation(Event event) {
		if (listBoxBCUserLocation.getSelectedItem() != null) {
			BCUserLocation selectedData = (BCUserLocation) listBoxBCUserLocation.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getBCUserLocationMainCtrl().getBCUserLocationDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getBCUserLocationMainCtrl().tabBCUserLocationDetail, null));
		
				} else if (getBCUserLocationMainCtrl().getBCUserLocationDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getBCUserLocationMainCtrl().tabBCUserLocationDetail, null));
				}
				
				getBCUserLocationMainCtrl().setSelectedBCUserLocation(selectedData);
				
			}
		}	
    }    
    
    
    
    
    public void onSort$listheader_BCUserLocationList_UserId(Event event) {
    	sortingData(listheader_BCUserLocationList_UserId, BCUserLocationComparator.COMPARE_BY_USER_ID);
    }
    
    public void onSort$listheader_BCUserLocationList_Branches(Event event) {
    	sortingData(listheader_BCUserLocationList_Branches, BCUserLocationComparator.COMPARE_BY_BRANCHES);
    }
    
    public void onSort$listheader_BCUserLocationList_GroupName(Event event) {
    	sortingData(listheader_BCUserLocationList_GroupName, BCUserLocationComparator.COMPARE_BY_GROUP_NAME);
    }

   
    
       
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_BCUserLocationList, new BCUserLocationComparator(false, sortBy));
    		} else {
    			Collections.sort(list_BCUserLocationList, new BCUserLocationComparator(true, sortBy));
    		}
    	}
    	
    	setModelBCUserLocationList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_BCUserLocationList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowBCUserLocationList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	

    	
    	txtUserId.setValue(null);
    	txtBranches.setValue(null);
    	txtGroupName.setValue(null);
    	
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


    public void setSelectedBCUserLocation(BCUserLocation selectedBCUserLocation) {
        getBCUserLocationMainCtrl().setSelectedBCUserLocation(selectedBCUserLocation);
    }

    public BCUserLocation getSelectedBCUserLocation() {
        return getBCUserLocationMainCtrl().getSelectedBCUserLocation();
    }

    public void setBCUserLocationMainCtrl(BCUserLocationMainCtrl BCUserLocationMainCtrl) {
        this.BCUserLocationMainCtrl = BCUserLocationMainCtrl;
    }

    public BCUserLocationMainCtrl getBCUserLocationMainCtrl() {
        return this.BCUserLocationMainCtrl;
    }

}
