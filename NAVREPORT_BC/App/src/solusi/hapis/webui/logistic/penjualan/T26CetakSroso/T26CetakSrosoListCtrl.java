package solusi.hapis.webui.logistic.penjualan.T26CetakSroso;


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

import solusi.hapis.backend.navbi.model.T26CetakSroso;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 02-11-2023
 */

public class T26CetakSrosoListCtrl extends GFCBaseListCtrl<T26CetakSroso> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT26CetakSrosoList; 
    protected Panel panelT26CetakSrosoList;
 	
	// Search Box Components 
    


	protected Textbox txtDicetakPada;
	protected Textbox txtKeterangan;
	
	// Paging and list
    protected Borderlayout borderLayout_T26CetakSrosoList;
    protected Paging paging_T26CetakSrosoList;
    private int startT26CetakSrosoList;
	private List<T26CetakSroso> list_T26CetakSrosoList = new ArrayList<T26CetakSroso>();
    private ListModelList modelList_T26CetakSrosoList = new ListModelList();
    protected Listbox listBoxT26CetakSroso;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T26CetakSrosoMainCtrl T26CetakSrosoMainCtrl;

    // List Header		
    protected Listheader listheader_T26CetakSrosoList_DicetakPada;
    protected Listheader listheader_T26CetakSrosoList_Keterangan;
		
		
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T26CetakSrosoListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T26CetakSrosoList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T26CetakSrosoList.setDetailed(true);
		paging_T26CetakSrosoList.addEventListener("onPaging", onPaging_T26CetakSrosoList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT26CetakSrosoMainCtrl((T26CetakSrosoMainCtrl) arg.get("ModuleMainController"));
		
		    getT26CetakSrosoMainCtrl().setT26CetakSrosoListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowT26CetakSrosoList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan

        
        listheader_T26CetakSrosoList_DicetakPada.setSortAscending(new T26CetakSrosoComparator(true, T26CetakSrosoComparator.COMPARE_BY_DICETAKPADA));
        listheader_T26CetakSrosoList_DicetakPada.setSortDescending(new T26CetakSrosoComparator(false, T26CetakSrosoComparator.COMPARE_BY_DICETAKPADA));        
         
        listheader_T26CetakSrosoList_Keterangan.setSortAscending(new T26CetakSrosoComparator(true, T26CetakSrosoComparator.COMPARE_BY_KETERANGAN));
        listheader_T26CetakSrosoList_Keterangan.setSortDescending(new T26CetakSrosoComparator(false, T26CetakSrosoComparator.COMPARE_BY_KETERANGAN));        
            
               
        getSearchData();
        
        listBoxT26CetakSroso.setItemRenderer(renderTable());
        
        windowT26CetakSrosoList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (CommonUtils.isValidDateFormat(txtDicetakPada.getValue())) {
			Date tglInv1 = CommonUtils.convertStringToDate(txtDicetakPada.getValue());
			Date tglInv2 = CommonUtils.createSecondParameterForSearch(txtDicetakPada.getValue());
			parameterInput.put("dicetakPadaFrom", tglInv1);
			parameterInput.put("dicetakPadaTo", tglInv2);
		}
		
		
		if (CommonUtils.isNotEmpty(txtKeterangan.getValue())) {
			parameterInput.put("keterangan", txtKeterangan.getValue());
		}
		
		
		
		list_T26CetakSrosoList.clear();
		
		List<T26CetakSroso> tempListT26CetakSroso = getT26CetakSrosoMainCtrl().getT26CetakSrosoService().getListT26CetakSroso(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT26CetakSroso)) {
			list_T26CetakSrosoList.addAll(tempListT26CetakSroso);
			startT26CetakSrosoList = 0;
			paging_T26CetakSrosoList.setActivePage(0);
		}

		setModelT26CetakSrosoList();
	}

	// Pembagian data untuk paging
	private void setModelT26CetakSrosoList() {		
		modelList_T26CetakSrosoList.clear();
		
		T26CetakSroso selectedData = null;
		if (CommonUtils.isNotEmpty(list_T26CetakSrosoList)){
			
			int endT26CetakSrosoList = 0;
			if(startT26CetakSrosoList + paging_T26CetakSrosoList.getPageSize() < list_T26CetakSrosoList.size()) {
				endT26CetakSrosoList = startT26CetakSrosoList + paging_T26CetakSrosoList.getPageSize();
			} else {
				endT26CetakSrosoList = list_T26CetakSrosoList.size();
			}
			
			if (startT26CetakSrosoList > endT26CetakSrosoList) {
				startT26CetakSrosoList = 0;
				paging_T26CetakSrosoList.setActivePage(0);
			}
			
			modelList_T26CetakSrosoList.addAll(list_T26CetakSrosoList.subList(startT26CetakSrosoList, endT26CetakSrosoList));

			paging_T26CetakSrosoList.setDetailed(true);
			paging_T26CetakSrosoList.setTotalSize(list_T26CetakSrosoList.size());
			
			listBoxT26CetakSroso.setModel(modelList_T26CetakSrosoList);
			listBoxT26CetakSroso.setSelectedIndex(0);
	
			selectedData = list_T26CetakSrosoList.get(startT26CetakSrosoList);
		} else {
			paging_T26CetakSrosoList.setDetailed(false);
			listBoxT26CetakSroso.setModel(modelList_T26CetakSrosoList);
			paging_T26CetakSrosoList.setTotalSize(0);
		}
		
		getT26CetakSrosoMainCtrl().setSelectedT26CetakSroso(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T26CetakSroso rec = (T26CetakSroso) data;

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
                info.setParent(windowT26CetakSrosoList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getDicetakPada(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                lc.setParent(item);
                
                lc = new Listcell(rec.getKeterangan());
                lc.setParent(item);
                
				                	                         
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT26CetakSrosoItem");
              }
        };
    }
    
	private EventListener onPaging_T26CetakSrosoList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT26CetakSrosoList = pageNo * paging_T26CetakSrosoList.getPageSize();
		         setModelT26CetakSrosoList();
			}
		};
	}


//    public void onDoubleClickedT26CetakSrosoItem(Event event) {
//		if (listBoxT26CetakSroso.getSelectedItem() != null) {
//			T26CetakSroso selectedData = (T26CetakSroso) listBoxT26CetakSroso.getSelectedItem().getValue();
//			if (selectedData != null) {
//				setSelectedT26CetakSroso(selectedData);
//	
//	            if (getT26CetakSrosoMainCtrl().getT26CetakSrosoDetailCtrl() == null) {
//	            	Events.sendEvent(new Event("onSelect", getT26CetakSrosoMainCtrl().tabT26CetakSrosoDetail, null));
//	            } else if (getT26CetakSrosoMainCtrl().getT26CetakSrosoDetailCtrl().getBinder() == null) {
//	                Events.sendEvent(new Event("onSelect", getT26CetakSrosoMainCtrl().tabT26CetakSrosoDetail, null));
//	            }
//	            
//	            Events.sendEvent(new Event("onSelect", getT26CetakSrosoMainCtrl().tabT26CetakSrosoDetail, selectedData));
//	            
//	            getT26CetakSrosoMainCtrl().getT26CetakSrosoDetailCtrl().doRenderMode("View"); 
//			}
//		}
//    }


    public void onSelect$listBoxT26CetakSroso(Event event) {
		if (listBoxT26CetakSroso.getSelectedItem() != null) {
			T26CetakSroso selectedData = (T26CetakSroso) listBoxT26CetakSroso.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
//				if (getT26CetakSrosoMainCtrl().getT26CetakSrosoDetailCtrl() == null) {
//					Events.sendEvent(new Event("onSelect",
//							getT26CetakSrosoMainCtrl().tabT26CetakSrosoDetail, null));
//		
//				} else if (getT26CetakSrosoMainCtrl().getT26CetakSrosoDetailCtrl().getBinder() == null) {
//					Events.sendEvent(new Event("onSelect",
//							getT26CetakSrosoMainCtrl().tabT26CetakSrosoDetail, null));
//				}
				
				getT26CetakSrosoMainCtrl().setSelectedT26CetakSroso(selectedData);
				
			}
		}	
    }
    


    public void onSort$listheader_T26CetakSrosoList_DicetakPada(Event event) {
    	sortingData(listheader_T26CetakSrosoList_DicetakPada, T26CetakSrosoComparator.COMPARE_BY_DICETAKPADA);
    }
    
    public void onSort$listheader_T26CetakSrosoList_Keterangan(Event event) {
    	sortingData(listheader_T26CetakSrosoList_Keterangan, T26CetakSrosoComparator.COMPARE_BY_KETERANGAN);
    }


    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T26CetakSrosoList, new T26CetakSrosoComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T26CetakSrosoList, new T26CetakSrosoComparator(true, sortBy));
    		}
    	}
    	
    	setModelT26CetakSrosoList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T26CetakSrosoList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT26CetakSrosoList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
    	
    	txtDicetakPada.setValue(null);
    	txtKeterangan.setValue(null);

    	
   
    	//startT26CetakSrosoList = 0;
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


    public void setSelectedT26CetakSroso(T26CetakSroso selectedT26CetakSroso) {
        getT26CetakSrosoMainCtrl().setSelectedT26CetakSroso(selectedT26CetakSroso);
    }

    public T26CetakSroso getSelectedT26CetakSroso() {
        return getT26CetakSrosoMainCtrl().getSelectedT26CetakSroso();
    }

    public void setT26CetakSrosoMainCtrl(T26CetakSrosoMainCtrl T26CetakSrosoMainCtrl) {
        this.T26CetakSrosoMainCtrl = T26CetakSrosoMainCtrl;
    }

    public T26CetakSrosoMainCtrl getT26CetakSrosoMainCtrl() {
        return this.T26CetakSrosoMainCtrl;
    }

}
