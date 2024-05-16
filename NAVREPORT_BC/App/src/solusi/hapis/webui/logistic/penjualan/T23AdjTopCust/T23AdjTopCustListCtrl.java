package solusi.hapis.webui.logistic.penjualan.T23AdjTopCust;



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

import solusi.hapis.backend.navbi.model.T23AdjTopCust;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 29-08-2022
 */

public class T23AdjTopCustListCtrl extends GFCBaseListCtrl<T23AdjTopCust> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT23AdjTopCustList; 
    protected Panel panelT23AdjTopCustList;
 	
	// Search Box Components 
    


	protected Textbox txtCustNo;
	protected Textbox txtTopAdj;
	
	// Paging and list
    protected Borderlayout borderLayout_T23AdjTopCustList;
    protected Paging paging_T23AdjTopCustList;
    private int startT23AdjTopCustList;
	private List<T23AdjTopCust> list_T23AdjTopCustList = new ArrayList<T23AdjTopCust>();
    private ListModelList modelList_T23AdjTopCustList = new ListModelList();
    protected Listbox listBoxT23AdjTopCust;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T23AdjTopCustMainCtrl T23AdjTopCustMainCtrl;

    // List Header		
    protected Listheader listheader_T23AdjTopCustList_CustNo;
    protected Listheader listheader_T23AdjTopCustList_TopAdj;
		
		
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T23AdjTopCustListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T23AdjTopCustList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T23AdjTopCustList.setDetailed(true);
		paging_T23AdjTopCustList.addEventListener("onPaging", onPaging_T23AdjTopCustList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT23AdjTopCustMainCtrl((T23AdjTopCustMainCtrl) arg.get("ModuleMainController"));
		
		    getT23AdjTopCustMainCtrl().setT23AdjTopCustListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowT23AdjTopCustList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  

        
        
        listheader_T23AdjTopCustList_CustNo.setSortAscending(new T23AdjTopCustComparator(true, T23AdjTopCustComparator.COMPARE_BY_CUSTNO));
        listheader_T23AdjTopCustList_CustNo.setSortDescending(new T23AdjTopCustComparator(false, T23AdjTopCustComparator.COMPARE_BY_CUSTNO));        
         
        listheader_T23AdjTopCustList_TopAdj.setSortAscending(new T23AdjTopCustComparator(true, T23AdjTopCustComparator.COMPARE_BY_TOPADJ));
        listheader_T23AdjTopCustList_TopAdj.setSortDescending(new T23AdjTopCustComparator(false, T23AdjTopCustComparator.COMPARE_BY_TOPADJ));        
            
               
        getSearchData();
        
        listBoxT23AdjTopCust.setItemRenderer(renderTable());
        
        windowT23AdjTopCustList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (CommonUtils.isNotEmpty(txtCustNo.getValue())) {
			parameterInput.put("custNo", txtCustNo.getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtTopAdj.getValue())) {
			parameterInput.put("topAdj", txtTopAdj.getValue());
		}
		
		
		
		
		list_T23AdjTopCustList.clear();
		
		List<T23AdjTopCust> tempListT23AdjTopCust = getT23AdjTopCustMainCtrl().getT23AdjTopCustService().getListT23AdjTopCust(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT23AdjTopCust)) {
			list_T23AdjTopCustList.addAll(tempListT23AdjTopCust);
			startT23AdjTopCustList = 0;
			paging_T23AdjTopCustList.setActivePage(0);
		}

		setModelT23AdjTopCustList();
	}

	// Pembagian data untuk paging
	private void setModelT23AdjTopCustList() {		
		modelList_T23AdjTopCustList.clear();
		
		T23AdjTopCust selectedData = null;
		if (CommonUtils.isNotEmpty(list_T23AdjTopCustList)){
			
			int endT23AdjTopCustList = 0;
			if(startT23AdjTopCustList + paging_T23AdjTopCustList.getPageSize() < list_T23AdjTopCustList.size()) {
				endT23AdjTopCustList = startT23AdjTopCustList + paging_T23AdjTopCustList.getPageSize();
			} else {
				endT23AdjTopCustList = list_T23AdjTopCustList.size();
			}
			
			if (startT23AdjTopCustList > endT23AdjTopCustList) {
				startT23AdjTopCustList = 0;
				paging_T23AdjTopCustList.setActivePage(0);
			}
			
			modelList_T23AdjTopCustList.addAll(list_T23AdjTopCustList.subList(startT23AdjTopCustList, endT23AdjTopCustList));

			paging_T23AdjTopCustList.setDetailed(true);
			paging_T23AdjTopCustList.setTotalSize(list_T23AdjTopCustList.size());
			
			listBoxT23AdjTopCust.setModel(modelList_T23AdjTopCustList);
			listBoxT23AdjTopCust.setSelectedIndex(0);
	
			selectedData = list_T23AdjTopCustList.get(startT23AdjTopCustList);
		} else {
			paging_T23AdjTopCustList.setDetailed(false);
			listBoxT23AdjTopCust.setModel(modelList_T23AdjTopCustList);
			paging_T23AdjTopCustList.setTotalSize(0);
		}
		
		getT23AdjTopCustMainCtrl().setSelectedT23AdjTopCust(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T23AdjTopCust rec = (T23AdjTopCust) data;

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
                info.setParent(windowT23AdjTopCustList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                
                lc = new Listcell(rec.getCustNo());
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getTopAdj(), "#,##0"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				                	                         
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT23AdjTopCustItem");
              }
        };
    }
    
	private EventListener onPaging_T23AdjTopCustList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT23AdjTopCustList = pageNo * paging_T23AdjTopCustList.getPageSize();
		         setModelT23AdjTopCustList();
			}
		};
	}


    public void onDoubleClickedT23AdjTopCustItem(Event event) {
		if (listBoxT23AdjTopCust.getSelectedItem() != null) {
			T23AdjTopCust selectedData = (T23AdjTopCust) listBoxT23AdjTopCust.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT23AdjTopCust(selectedData);
	
	            if (getT23AdjTopCustMainCtrl().getT23AdjTopCustDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT23AdjTopCustMainCtrl().tabT23AdjTopCustDetail, null));
	            } else if (getT23AdjTopCustMainCtrl().getT23AdjTopCustDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT23AdjTopCustMainCtrl().tabT23AdjTopCustDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT23AdjTopCustMainCtrl().tabT23AdjTopCustDetail, selectedData));
	            
	            getT23AdjTopCustMainCtrl().getT23AdjTopCustDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT23AdjTopCust(Event event) {
		if (listBoxT23AdjTopCust.getSelectedItem() != null) {
			T23AdjTopCust selectedData = (T23AdjTopCust) listBoxT23AdjTopCust.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT23AdjTopCustMainCtrl().getT23AdjTopCustDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT23AdjTopCustMainCtrl().tabT23AdjTopCustDetail, null));
		
				} else if (getT23AdjTopCustMainCtrl().getT23AdjTopCustDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT23AdjTopCustMainCtrl().tabT23AdjTopCustDetail, null));
				}
				
				getT23AdjTopCustMainCtrl().setSelectedT23AdjTopCust(selectedData);
				
			}
		}	
    }
    


    
    public void onSort$listheader_T23AdjTopCustList_CustNo(Event event) {
    	sortingData(listheader_T23AdjTopCustList_CustNo, T23AdjTopCustComparator.COMPARE_BY_CUSTNO);
    }
    
    public void onSort$listheader_T23AdjTopCustList_TopAdj(Event event) {
    	sortingData(listheader_T23AdjTopCustList_TopAdj, T23AdjTopCustComparator.COMPARE_BY_TOPADJ);
    }


    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T23AdjTopCustList, new T23AdjTopCustComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T23AdjTopCustList, new T23AdjTopCustComparator(true, sortBy));
    		}
    	}
    	
    	setModelT23AdjTopCustList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T23AdjTopCustList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT23AdjTopCustList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
    	
    	txtCustNo.setValue(null);
    	txtTopAdj.setValue(null);

    	
   
    	//startT23AdjTopCustList = 0;
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


    public void setSelectedT23AdjTopCust(T23AdjTopCust selectedT23AdjTopCust) {
        getT23AdjTopCustMainCtrl().setSelectedT23AdjTopCust(selectedT23AdjTopCust);
    }

    public T23AdjTopCust getSelectedT23AdjTopCust() {
        return getT23AdjTopCustMainCtrl().getSelectedT23AdjTopCust();
    }

    public void setT23AdjTopCustMainCtrl(T23AdjTopCustMainCtrl T23AdjTopCustMainCtrl) {
        this.T23AdjTopCustMainCtrl = T23AdjTopCustMainCtrl;
    }

    public T23AdjTopCustMainCtrl getT23AdjTopCustMainCtrl() {
        return this.T23AdjTopCustMainCtrl;
    }

}
