package solusi.hapis.webui.general.T15SatindoAdj;


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

import solusi.hapis.backend.navbi.model.T15SatindoAdj;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 07-04-2021
 */

public class T15SatindoAdjListCtrl extends GFCBaseListCtrl<T15SatindoAdj> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT15SatindoAdjList; 
    protected Panel panelT15SatindoAdjList;
 	
	// Search Box Components 
    
	protected Textbox txtNoInvoice;
	protected Textbox txtOrderDate;
	
	// Paging and list
    protected Borderlayout borderLayout_T15SatindoAdjList;
    protected Paging paging_T15SatindoAdjList;
    private int startT15SatindoAdjList;
	private List<T15SatindoAdj> list_T15SatindoAdjList = new ArrayList<T15SatindoAdj>();
    private ListModelList modelList_T15SatindoAdjList = new ListModelList();
    protected Listbox listBoxT15SatindoAdj;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T15SatindoAdjMainCtrl T15SatindoAdjMainCtrl;

    // List Header		
    protected Listheader listheader_T15SatindoAdjList_NoInvoice;
    protected Listheader listheader_T15SatindoAdjList_OrderDate;	
		
		
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T15SatindoAdjListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T15SatindoAdjList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T15SatindoAdjList.setDetailed(true);
		paging_T15SatindoAdjList.addEventListener("onPaging", onPaging_T15SatindoAdjList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT15SatindoAdjMainCtrl((T15SatindoAdjMainCtrl) arg.get("ModuleMainController"));
		
		    getT15SatindoAdjMainCtrl().setT15SatindoAdjListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowT15SatindoAdjList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
        
        
        listheader_T15SatindoAdjList_NoInvoice.setSortAscending(new T15SatindoAdjComparator(true, T15SatindoAdjComparator.COMPARE_BY_NOINVOICE));
        listheader_T15SatindoAdjList_NoInvoice.setSortDescending(new T15SatindoAdjComparator(false, T15SatindoAdjComparator.COMPARE_BY_NOINVOICE));        
         
        listheader_T15SatindoAdjList_OrderDate.setSortAscending(new T15SatindoAdjComparator(true, T15SatindoAdjComparator.COMPARE_BY_ORDERDATE));
        listheader_T15SatindoAdjList_OrderDate.setSortDescending(new T15SatindoAdjComparator(false, T15SatindoAdjComparator.COMPARE_BY_ORDERDATE));        
       

        getSearchData();
        
        listBoxT15SatindoAdj.setItemRenderer(renderTable());
        
        windowT15SatindoAdjList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (CommonUtils.isValidDateFormat(txtOrderDate.getValue())) {
			Date tglFrom = CommonUtils.convertStringToDate(txtOrderDate.getValue());
			Date tglTo = CommonUtils.createSecondParameterForSearch(txtOrderDate.getValue());
			parameterInput.put("orderDateFrom", tglFrom);
			parameterInput.put("orderDateTo", tglTo);
		}
		
		
		if (CommonUtils.isNotEmpty(txtNoInvoice.getValue())) {
			parameterInput.put("noInvoice", txtNoInvoice.getValue());
		}
		
		
		list_T15SatindoAdjList.clear();
		
		List<T15SatindoAdj> tempListT15SatindoAdj = getT15SatindoAdjMainCtrl().getT15SatindoAdjService().getListT15SatindoAdj(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT15SatindoAdj)) {
			list_T15SatindoAdjList.addAll(tempListT15SatindoAdj);
			startT15SatindoAdjList = 0;
			paging_T15SatindoAdjList.setActivePage(0);
		}

		setModelT15SatindoAdjList();
	}

	// Pembagian data untuk paging
	private void setModelT15SatindoAdjList() {		
		modelList_T15SatindoAdjList.clear();
		
		T15SatindoAdj selectedData = null;
		if (CommonUtils.isNotEmpty(list_T15SatindoAdjList)){
			
			int endT15SatindoAdjList = 0;
			if(startT15SatindoAdjList + paging_T15SatindoAdjList.getPageSize() < list_T15SatindoAdjList.size()) {
				endT15SatindoAdjList = startT15SatindoAdjList + paging_T15SatindoAdjList.getPageSize();
			} else {
				endT15SatindoAdjList = list_T15SatindoAdjList.size();
			}
			
			if (startT15SatindoAdjList > endT15SatindoAdjList) {
				startT15SatindoAdjList = 0;
				paging_T15SatindoAdjList.setActivePage(0);
			}
			
			modelList_T15SatindoAdjList.addAll(list_T15SatindoAdjList.subList(startT15SatindoAdjList, endT15SatindoAdjList));

			paging_T15SatindoAdjList.setDetailed(true);
			paging_T15SatindoAdjList.setTotalSize(list_T15SatindoAdjList.size());
			
			listBoxT15SatindoAdj.setModel(modelList_T15SatindoAdjList);
			listBoxT15SatindoAdj.setSelectedIndex(0);
	
			selectedData = list_T15SatindoAdjList.get(startT15SatindoAdjList);
		} else {
			paging_T15SatindoAdjList.setDetailed(false);
			listBoxT15SatindoAdj.setModel(modelList_T15SatindoAdjList);
			paging_T15SatindoAdjList.setTotalSize(0);
		}
		
		getT15SatindoAdjMainCtrl().setSelectedT15SatindoAdj(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T15SatindoAdj rec = (T15SatindoAdj) data;

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
                info.setParent(windowT15SatindoAdjList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoInvoice());
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getOrderDate()));
	            lc.setParent(item);	                 	                         
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT15SatindoAdjItem");
              }
        };
    }
    
	private EventListener onPaging_T15SatindoAdjList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT15SatindoAdjList = pageNo * paging_T15SatindoAdjList.getPageSize();
		         setModelT15SatindoAdjList();
			}
		};
	}


    public void onDoubleClickedT15SatindoAdjItem(Event event) {
		if (listBoxT15SatindoAdj.getSelectedItem() != null) {
			T15SatindoAdj selectedData = (T15SatindoAdj) listBoxT15SatindoAdj.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT15SatindoAdj(selectedData);
	
	            if (getT15SatindoAdjMainCtrl().getT15SatindoAdjDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT15SatindoAdjMainCtrl().tabT15SatindoAdjDetail, null));
	            } else if (getT15SatindoAdjMainCtrl().getT15SatindoAdjDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT15SatindoAdjMainCtrl().tabT15SatindoAdjDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT15SatindoAdjMainCtrl().tabT15SatindoAdjDetail, selectedData));
	            
	            getT15SatindoAdjMainCtrl().getT15SatindoAdjDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT15SatindoAdj(Event event) {
		if (listBoxT15SatindoAdj.getSelectedItem() != null) {
			T15SatindoAdj selectedData = (T15SatindoAdj) listBoxT15SatindoAdj.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT15SatindoAdjMainCtrl().getT15SatindoAdjDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT15SatindoAdjMainCtrl().tabT15SatindoAdjDetail, null));
		
				} else if (getT15SatindoAdjMainCtrl().getT15SatindoAdjDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT15SatindoAdjMainCtrl().tabT15SatindoAdjDetail, null));
				}
				
				getT15SatindoAdjMainCtrl().setSelectedT15SatindoAdj(selectedData);
				
			}
		}	
    }    
    
    
    
    public void onSort$listheader_T15SatindoAdjList_NoInvoice(Event event) {
    	sortingData(listheader_T15SatindoAdjList_NoInvoice, T15SatindoAdjComparator.COMPARE_BY_NOINVOICE);
    }
    
    public void onSort$listheader_T15SatindoAdjList_OrderDate(Event event) {
    	sortingData(listheader_T15SatindoAdjList_OrderDate, T15SatindoAdjComparator.COMPARE_BY_ORDERDATE);
    }

       
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T15SatindoAdjList, new T15SatindoAdjComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T15SatindoAdjList, new T15SatindoAdjComparator(true, sortBy));
    		}
    	}
    	
    	setModelT15SatindoAdjList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T15SatindoAdjList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT15SatindoAdjList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
    	txtNoInvoice.setValue(null);
    	txtOrderDate.setValue(null);
    	
   
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


    public void setSelectedT15SatindoAdj(T15SatindoAdj selectedT15SatindoAdj) {
        getT15SatindoAdjMainCtrl().setSelectedT15SatindoAdj(selectedT15SatindoAdj);
    }

    public T15SatindoAdj getSelectedT15SatindoAdj() {
        return getT15SatindoAdjMainCtrl().getSelectedT15SatindoAdj();
    }

    public void setT15SatindoAdjMainCtrl(T15SatindoAdjMainCtrl T15SatindoAdjMainCtrl) {
        this.T15SatindoAdjMainCtrl = T15SatindoAdjMainCtrl;
    }

    public T15SatindoAdjMainCtrl getT15SatindoAdjMainCtrl() {
        return this.T15SatindoAdjMainCtrl;
    }

}
