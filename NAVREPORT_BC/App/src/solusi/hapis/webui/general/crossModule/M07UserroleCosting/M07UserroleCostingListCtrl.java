package solusi.hapis.webui.general.crossModule.M07UserroleCosting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

import solusi.hapis.backend.navbi.model.M07UserroleCostingH;
import solusi.hapis.backend.navbi.model.M08UserroleCostingD;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 16-01-2025
 */

public class M07UserroleCostingListCtrl extends GFCBaseListCtrl<M07UserroleCostingH> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowM07UserroleCostingList; 
    
    protected Paging paging_M07UserroleCostingList;
    protected Listbox listBoxM07UserroleCosting;
    private int startM07UserroleCostingList;
   	private List<M07UserroleCostingH> list_M07UserroleCostingList = new ArrayList<M07UserroleCostingH>();
    private ListModelList modelList_M07UserroleCostingList = new ListModelList();
     
    // Databinding
    private AnnotateDataBinder binder;
    private M07UserroleCostingMainCtrl M07UserroleCostingMainCtrl;

    // List Header
    protected Listheader listheader_M07UserroleCostingList_Username;
    protected Listheader listheader_M07UserroleCostingList_Rolename;
    protected Listheader listheader_M07UserroleCostingList_Filteruser;
   
    // Search Box Components 
 	protected Textbox txtUsername;
 	protected Combobox cmbRolename;
 	protected Textbox txtFilteruser;


 	// Paging and list Detail 
 	protected Listheader listheader_M07UserroleCostingDetailList_Filteruser;
     
 	protected Paging paging_M07UserroleCostingDetailList;
	private int startM07UserroleCostingDetailList;
	private List<M08UserroleCostingD> list_M07UserroleCostingDetailList = new ArrayList<M08UserroleCostingD>();
	private ListModelList modelList_M07UserroleCostingDetailList = new ListModelList();
	
	protected Listbox listBoxM07UserroleCostingDetail;
     
   
     
    
    public M07UserroleCostingListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_M07UserroleCostingList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_M07UserroleCostingList.setDetailed(true);
		paging_M07UserroleCostingList.addEventListener("onPaging", onPaging_M07UserroleCostingList());
	
		paging_M07UserroleCostingDetailList.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_M07UserroleCostingDetailList.setDetailed(true);
		paging_M07UserroleCostingDetailList.addEventListener("onPaging", onPaging_M07UserroleCostingDetailList());
		listBoxM07UserroleCostingDetail.setItemRenderer(renderTableDetail());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setM07UserroleCostingMainCtrl((M07UserroleCostingMainCtrl) arg.get("ModuleMainController"));
		
		    getM07UserroleCostingMainCtrl().setM07UserroleCostingListCtrl(this);
		}
        
		
    }    
    
	private EventListener onPaging_M07UserroleCostingList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startM07UserroleCostingList = pageNo * paging_M07UserroleCostingList.getPageSize();
		         setModelM07UserroleCostingList();
			}
		};
	}

	private EventListener onPaging_M07UserroleCostingDetailList() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startM07UserroleCostingDetailList = pageNo
						* paging_M07UserroleCostingDetailList.getPageSize();
				setModelM07UserroleCostingDetailList();
			}
		};
	}
	
	private void setModelM07UserroleCostingDetailList() {
		modelList_M07UserroleCostingDetailList.clear();

		if (CommonUtils.isNotEmpty(list_M07UserroleCostingDetailList)) {

			int endUserDetailCabList = 0;
			if (startM07UserroleCostingDetailList
					+ paging_M07UserroleCostingDetailList.getPageSize() < list_M07UserroleCostingDetailList
						.size()) {
				endUserDetailCabList = startM07UserroleCostingDetailList
						+ paging_M07UserroleCostingDetailList.getPageSize();
			} else {
				endUserDetailCabList = list_M07UserroleCostingDetailList.size();
			}

			if (startM07UserroleCostingDetailList > endUserDetailCabList) {
				startM07UserroleCostingDetailList = 0;
				paging_M07UserroleCostingDetailList.setActivePage(0);
			}

			modelList_M07UserroleCostingDetailList.addAll(list_M07UserroleCostingDetailList.subList(
					startM07UserroleCostingDetailList, endUserDetailCabList));

			paging_M07UserroleCostingDetailList.setDetailed(true);
			paging_M07UserroleCostingDetailList.setTotalSize(list_M07UserroleCostingDetailList
					.size());

			listBoxM07UserroleCostingDetail.setModel(modelList_M07UserroleCostingDetailList);
			listBoxM07UserroleCostingDetail.setSelectedIndex(0);

		} else {
			paging_M07UserroleCostingDetailList.setDetailed(false);
			listBoxM07UserroleCostingDetail.setModel(modelList_M07UserroleCostingDetailList);
			paging_M07UserroleCostingDetailList.setTotalSize(0);
		}

	}
	
	// Pembagian data untuk paging
	private void setModelM07UserroleCostingList() {		
		modelList_M07UserroleCostingList.clear();
		
		M07UserroleCostingH selectedData = null;
		if (CommonUtils.isNotEmpty(list_M07UserroleCostingList)){
			
			int endM07UserroleCostingList = 0;
			if(startM07UserroleCostingList + paging_M07UserroleCostingList.getPageSize() < list_M07UserroleCostingList.size()) {
				endM07UserroleCostingList = startM07UserroleCostingList + paging_M07UserroleCostingList.getPageSize();
			} else {
				endM07UserroleCostingList = list_M07UserroleCostingList.size();
			}
			
			if (startM07UserroleCostingList > endM07UserroleCostingList) {
				startM07UserroleCostingList = 0;
				paging_M07UserroleCostingList.setActivePage(0);
			}
			
			modelList_M07UserroleCostingList.addAll(list_M07UserroleCostingList.subList(startM07UserroleCostingList, endM07UserroleCostingList));

			paging_M07UserroleCostingList.setDetailed(true);
			paging_M07UserroleCostingList.setTotalSize(list_M07UserroleCostingList.size());
			
			listBoxM07UserroleCosting.setModel(modelList_M07UserroleCostingList);
			listBoxM07UserroleCosting.setSelectedIndex(0);
	
			selectedData = list_M07UserroleCostingList.get(startM07UserroleCostingList);
		} else {
			paging_M07UserroleCostingList.setDetailed(false);
			listBoxM07UserroleCosting.setModel(modelList_M07UserroleCostingList);
			paging_M07UserroleCostingList.setTotalSize(0);
		}
		
		getM07UserroleCostingMainCtrl().setSelectedM07UserroleCostingH(selectedData);
		getSearchDataDetail(selectedData);

	}
	
    public void onCreate$windowM07UserroleCostingList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }  

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
       
      
        listheader_M07UserroleCostingList_Username.setSortAscending(new M07UserroleCostingHComparator(true, M07UserroleCostingHComparator.COMPARE_BY_USERNAME));
        listheader_M07UserroleCostingList_Username.setSortDescending(new M07UserroleCostingHComparator(false, M07UserroleCostingHComparator.COMPARE_BY_USERNAME));        
        
        listheader_M07UserroleCostingList_Rolename.setSortAscending(new M07UserroleCostingHComparator(true, M07UserroleCostingHComparator.COMPARE_BY_ROLENAME));
        listheader_M07UserroleCostingList_Rolename.setSortDescending(new M07UserroleCostingHComparator(false, M07UserroleCostingHComparator.COMPARE_BY_ROLENAME));        
       
        listheader_M07UserroleCostingList_Filteruser.setSortAscending(new M07UserroleCostingHComparator(true, M07UserroleCostingHComparator.COMPARE_BY_FILTERUSER));
        listheader_M07UserroleCostingList_Filteruser.setSortDescending(new M07UserroleCostingHComparator(false, M07UserroleCostingHComparator.COMPARE_BY_FILTERUSER));        
       

        // Detail
        listheader_M07UserroleCostingDetailList_Filteruser.setSortAscending(new M08UserroleCostingDComparator(true, M08UserroleCostingDComparator.COMPARE_BY_FILTERUSER));
        listheader_M07UserroleCostingDetailList_Filteruser.setSortDescending(new M08UserroleCostingDComparator(false, M08UserroleCostingDComparator.COMPARE_BY_FILTERUSER));        
        
        getSearchData();
        
        listBoxM07UserroleCosting.setItemRenderer(renderTable());
        
        windowM07UserroleCostingList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		

		
		if (CommonUtils.isNotEmpty(txtUsername.getValue())) {
			parameterInput.put("username", txtUsername.getValue());
		}
		
		if (this.cmbRolename.getSelectedIndex() != 0) {
	         parameterInput.put("roleuser", (String)this.cmbRolename.getSelectedItem().getValue());
	    }
		
		
		if (CommonUtils.isNotEmpty(txtFilteruser.getValue())) {
			parameterInput.put("filteruser", txtFilteruser.getValue());
		}

		
		
		list_M07UserroleCostingList.clear();
		
		List<M07UserroleCostingH> tempListM07UserroleCostingH = getM07UserroleCostingMainCtrl().getM07UserroleCostingHService().getListM07UserroleCostingH(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListM07UserroleCostingH)) {
			list_M07UserroleCostingList.addAll(tempListM07UserroleCostingH);
			startM07UserroleCostingList = 0;
			paging_M07UserroleCostingList.setActivePage(0);
		}

		setModelM07UserroleCostingList();
	}

  
	public void getSearchDataDetail(M07UserroleCostingH m07UserroleCostingH) {
		list_M07UserroleCostingDetailList.clear();
		
		if (m07UserroleCostingH != null) {
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();		
			
			if (CommonUtils.isNotEmpty(m07UserroleCostingH.getM07Id())) {
				parameterInputDetail.put("m07Id", m07UserroleCostingH.getM07Id());
			}
			
			
			List<M08UserroleCostingD> tempM08UserroleCostingD = getM07UserroleCostingMainCtrl().getM07UserroleCostingHService().getListM08UserroleCostingD(parameterInputDetail); 
			if (CommonUtils.isNotEmpty(tempM08UserroleCostingD)) {
				list_M07UserroleCostingDetailList.addAll(tempM08UserroleCostingD);
				startM07UserroleCostingDetailList = 0;
				
				paging_M07UserroleCostingDetailList.setActivePage(0);
			}
			
		}
		
		setModelM07UserroleCostingDetailList();
	}
	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final M07UserroleCostingH rec = (M07UserroleCostingH) data;

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
                info.setParent(windowM07UserroleCostingList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(rec.getUsername());
                lc.setParent(item);
                
                lc = new Listcell(rec.getRolename());
                lc.setParent(item);
                
                lc = new Listcell(rec.getFilteruser());
                lc.setParent(item);
	                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedM07UserroleCostingItem");
              }
        };
    }
    
    private ListitemRenderer renderTableDetail() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final M08UserroleCostingD rec = (M08UserroleCostingD) data;

				Listcell lc;

				lc = new Listcell(rec.getFilteruser());
				lc.setParent(item);
				
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}

    public void onDoubleClickedM07UserroleCostingItem(Event event) {
//    	if (getSelectedM07UserroleCosting() != null) {
//			Events.sendEvent(new Event("onSelect", getM07UserroleCostingMainCtrl().tabM07UserroleCostingDetail, getSelectedM07UserroleCosting()));
//		}
		
		if (listBoxM07UserroleCosting.getSelectedItem() != null) {
			M07UserroleCostingH selectedData = (M07UserroleCostingH) listBoxM07UserroleCosting.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedM07UserroleCostingH(selectedData);
	
	            if (getM07UserroleCostingMainCtrl().getM07UserroleCostingDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getM07UserroleCostingMainCtrl().tabM07UserroleCostingDetail, null));
	            } else if (getM07UserroleCostingMainCtrl().getM07UserroleCostingDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getM07UserroleCostingMainCtrl().tabM07UserroleCostingDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getM07UserroleCostingMainCtrl().tabM07UserroleCostingDetail, selectedData));
	            
	            getM07UserroleCostingMainCtrl().getM07UserroleCostingDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxM07UserroleCosting(Event event) {
    	if (listBoxM07UserroleCosting.getSelectedItem() != null) {
			M07UserroleCostingH selectedData = (M07UserroleCostingH) listBoxM07UserroleCosting.getSelectedItem().getValue();
			if (selectedData != null) {
				
				if (getM07UserroleCostingMainCtrl().getM07UserroleCostingDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getM07UserroleCostingMainCtrl().tabM07UserroleCostingDetail, null));
		
				} else if (getM07UserroleCostingMainCtrl().getM07UserroleCostingDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getM07UserroleCostingMainCtrl().tabM07UserroleCostingDetail, null));
				}
				
				getM07UserroleCostingMainCtrl().setSelectedM07UserroleCostingH(selectedData);
				getSearchDataDetail(selectedData);
				
			}
		}	
    }
    
    public void doFitSize() {
		windowM07UserroleCostingList.invalidate();
	}
    
    
    public void onSort$listheader_M07UserroleCostingList_Username(Event event) { 
    	sortingData(listheader_M07UserroleCostingList_Username, M07UserroleCostingHComparator.COMPARE_BY_USERNAME);
    }
    
    public void onSort$listheader_M07UserroleCostingList_Rolename(Event event) { 
    	sortingData(listheader_M07UserroleCostingList_Rolename, M07UserroleCostingHComparator.COMPARE_BY_ROLENAME);
    }
    
    public void onSort$listheader_M07UserroleCostingList_Filteruser(Event event) { 
    	sortingData(listheader_M07UserroleCostingList_Filteruser, M07UserroleCostingHComparator.COMPARE_BY_FILTERUSER);
    }
    
  
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_M07UserroleCostingList, new M07UserroleCostingHComparator(false, sortBy));
    		} else {
    			Collections.sort(list_M07UserroleCostingList, new M07UserroleCostingHComparator(true, sortBy));
    		}
    	}
    	
    	setModelM07UserroleCostingList();    
    }
    
    //=============================================================================================================================================
    
	
    public void onSort$listheader_M07UserroleCostingDetailList_Filteruser(Event event) {
    	sortingDataDetail(listheader_M07UserroleCostingDetailList_Filteruser, M08UserroleCostingDComparator.COMPARE_BY_FILTERUSER);
    }

    private void sortingDataDetail(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_M07UserroleCostingDetailList, new M08UserroleCostingDComparator(false, sortBy));
    		} else {
    			Collections.sort(list_M07UserroleCostingDetailList, new M08UserroleCostingDComparator(true, sortBy));
    		}
    	}
    	
    	setModelM07UserroleCostingDetailList();    
    }
    
    
    public void clearSearchBox(){
        // empty the text search boxes

    	txtUsername.setValue(null);
    	cmbRolename.setSelectedIndex(0);
    	txtFilteruser.setValue(null);
    

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


    public void setSelectedM07UserroleCostingH(M07UserroleCostingH selectedM07UserroleCostingH) {
        getM07UserroleCostingMainCtrl().setSelectedM07UserroleCostingH(selectedM07UserroleCostingH);
    }

    public M07UserroleCostingH getSelectedM07UserroleCostingH() {
        return getM07UserroleCostingMainCtrl().getSelectedM07UserroleCostingH();
    }

    public void setM07UserroleCostingMainCtrl(M07UserroleCostingMainCtrl M07UserroleCostingMainCtrl) {
        this.M07UserroleCostingMainCtrl = M07UserroleCostingMainCtrl;
    }

    public M07UserroleCostingMainCtrl getM07UserroleCostingMainCtrl() {
        return this.M07UserroleCostingMainCtrl;
    }
    
    public List<M08UserroleCostingD> getList_M07UserroleCostingDetailList() {
		return list_M07UserroleCostingDetailList;
	}

	public void setList_M07UserroleCostingDetailList(List<M08UserroleCostingD> list_M07UserroleCostingDetailList) {
		this.list_M07UserroleCostingDetailList = list_M07UserroleCostingDetailList;
	}


}
