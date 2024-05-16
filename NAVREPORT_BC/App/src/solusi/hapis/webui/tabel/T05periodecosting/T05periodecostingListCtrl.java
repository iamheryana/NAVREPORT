package solusi.hapis.webui.tabel.T05periodecosting;

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

import solusi.hapis.backend.tabel.model.T05periodecosting;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 25-03-2018
 */

public class T05periodecostingListCtrl extends GFCBaseListCtrl<T05periodecosting> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT05periodecostingList; 
    protected Panel panelT05periodecostingList;
 	
	// Search Box Components 
	protected Combobox cmbMasa;
	protected Textbox txtTahun;
	protected Combobox cmbCloseKomisi;
	protected Combobox cmbCloseTqs;
	

	// Paging and list
    protected Borderlayout borderLayout_T05periodecostingList;
    protected Paging paging_T05periodecostingList;
    private int startT05periodecostingList;
	private List<T05periodecosting> list_T05periodecostingList = new ArrayList<T05periodecosting>();
    private ListModelList modelList_T05periodecostingList = new ListModelList();
    protected Listbox listBoxT05periodecosting;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T05periodecostingMainCtrl T05periodecostingMainCtrl;

    // List Header
    protected Listheader listheader_T05periodecostingList_masa;
    protected Listheader listheader_T05periodecostingList_tahun;
    protected Listheader listheader_T05periodecostingList_closeKomisi;
    protected Listheader listheader_T05periodecostingList_closeTqs;
    
    
    public T05periodecostingListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T05periodecostingList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T05periodecostingList.setDetailed(true);
		paging_T05periodecostingList.addEventListener("onPaging", onPaging_T05periodecostingList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT05periodecostingMainCtrl((T05periodecostingMainCtrl) arg.get("ModuleMainController"));
		
		    getT05periodecostingMainCtrl().setT05periodecostingListCtrl(this);
		}
        
		
    }    
    

    public void onCreate$windowT05periodecostingList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
 
      
              
        
        listheader_T05periodecostingList_masa.setSortAscending(new T05periodecostingComparator(true, T05periodecostingComparator.COMPARE_BY_MASA));
        listheader_T05periodecostingList_masa.setSortDescending(new T05periodecostingComparator(false, T05periodecostingComparator.COMPARE_BY_MASA));        
        
        listheader_T05periodecostingList_tahun.setSortAscending(new T05periodecostingComparator(true, T05periodecostingComparator.COMPARE_BY_TAHUN));
        listheader_T05periodecostingList_tahun.setSortDescending(new T05periodecostingComparator(false, T05periodecostingComparator.COMPARE_BY_TAHUN));        
        
        listheader_T05periodecostingList_closeKomisi.setSortAscending(new T05periodecostingComparator(true, T05periodecostingComparator.COMPARE_BY_CLOSEKOMISI));
        listheader_T05periodecostingList_closeKomisi.setSortDescending(new T05periodecostingComparator(false, T05periodecostingComparator.COMPARE_BY_CLOSEKOMISI));        
       
        listheader_T05periodecostingList_closeTqs.setSortAscending(new T05periodecostingComparator(true, T05periodecostingComparator.COMPARE_BY_CLOSETQS));
        listheader_T05periodecostingList_closeTqs.setSortDescending(new T05periodecostingComparator(false, T05periodecostingComparator.COMPARE_BY_CLOSETQS));        
       
        getSearchData();
        
        listBoxT05periodecosting.setItemRenderer(renderTable());
        
        windowT05periodecostingList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		

		if (cmbMasa.getSelectedIndex() != 0) {
			parameterInput.put("masa", (String) cmbMasa.getSelectedItem().getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtTahun.getValue())) {
			parameterInput.put("tahun", txtTahun.getValue());
		}
		
		if (cmbCloseKomisi.getSelectedIndex() != 0) {
			parameterInput.put("closeKomisi", (String) cmbCloseKomisi.getSelectedItem().getValue());
		}
		
		if (cmbCloseTqs.getSelectedIndex() != 0) {
			parameterInput.put("closetqs", (String) cmbCloseTqs.getSelectedItem().getValue());
		}
		
		list_T05periodecostingList.clear();
		
		List<T05periodecosting> tempListT05periodecosting = getT05periodecostingMainCtrl().getT05periodecostingService().getListT05periodecosting(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT05periodecosting)) {
			list_T05periodecostingList.addAll(tempListT05periodecosting);
			startT05periodecostingList = 0;
			paging_T05periodecostingList.setActivePage(0);
		}

		setModelT05periodecostingList();
	}

	// Pembagian data untuk paging
	private void setModelT05periodecostingList() {		
		modelList_T05periodecostingList.clear();
		
		T05periodecosting selectedData = null;
		if (CommonUtils.isNotEmpty(list_T05periodecostingList)){
			
			int endT05periodecostingList = 0;
			if(startT05periodecostingList + paging_T05periodecostingList.getPageSize() < list_T05periodecostingList.size()) {
				endT05periodecostingList = startT05periodecostingList + paging_T05periodecostingList.getPageSize();
			} else {
				endT05periodecostingList = list_T05periodecostingList.size();
			}
			
			if (startT05periodecostingList > endT05periodecostingList) {
				startT05periodecostingList = 0;
				paging_T05periodecostingList.setActivePage(0);
			}
			
			modelList_T05periodecostingList.addAll(list_T05periodecostingList.subList(startT05periodecostingList, endT05periodecostingList));

			paging_T05periodecostingList.setDetailed(true);
			paging_T05periodecostingList.setTotalSize(list_T05periodecostingList.size());
			
			listBoxT05periodecosting.setModel(modelList_T05periodecostingList);
			listBoxT05periodecosting.setSelectedIndex(0);
	
			selectedData = list_T05periodecostingList.get(startT05periodecostingList);
		} else {
			paging_T05periodecostingList.setDetailed(false);
			listBoxT05periodecosting.setModel(modelList_T05periodecostingList);
			paging_T05periodecostingList.setTotalSize(0);
		}
		
		getT05periodecostingMainCtrl().setSelectedT05periodecosting(selectedData);

	}
	

    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T05periodecosting rec = (T05periodecosting) data;

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
                info.setParent(windowT05periodecostingList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                String vMasa = rec.getMasa();
                String vBulan = "";
                if(vMasa.equals("01")){
                	vBulan = "JANUARI";
                } else if(vMasa.equals("02")){
                	vBulan = "FEBRUARI";
                } else if(vMasa.equals("03")){
                	vBulan = "MARET";
                } else if(vMasa.equals("04")){
                	vBulan = "APRIL";
                } else if(vMasa.equals("05")){
                	vBulan = "MEI";
                } else if(vMasa.equals("06")){
                	vBulan = "JUNI";
                } else if(vMasa.equals("07")){
                	vBulan = "JULI";
                } else if(vMasa.equals("08")){
                	vBulan = "AGUSTUS";
                } else if(vMasa.equals("09")){
                	vBulan = "SEPTEMBER";
                } else if(vMasa.equals("10")){
                	vBulan = "OKTOBER";
                } else if(vMasa.equals("11")){
                	vBulan = "NOVEMBER";
                } else if(vMasa.equals("12")){
                	vBulan = "DESEMBER";
                }
                	                
                lc = new Listcell(vBulan);
                lc.setParent(item); 
                
                lc = new Listcell(rec.getTahun());
                lc.setParent(item);
                
                String vCloseKomisi = rec.getCloseKomisi().equals("Y")?"Close":"Open";
                lc = new Listcell(vCloseKomisi);
                lc.setParent(item); 
                
                String vCloseTqs = rec.getCloseTqs().equals("Y")?"Close":"Open";
                lc = new Listcell(vCloseTqs);
                lc.setParent(item); 
                
      
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT05periodecostingItem");
              }
        };
    }
    
	private EventListener onPaging_T05periodecostingList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT05periodecostingList = pageNo * paging_T05periodecostingList.getPageSize();
		         setModelT05periodecostingList();
			}
		};
	}


    public void onDoubleClickedT05periodecostingItem(Event event) {
		if (listBoxT05periodecosting.getSelectedItem() != null) {
			T05periodecosting selectedData = (T05periodecosting) listBoxT05periodecosting.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT05periodecosting(selectedData);
	
	            if (getT05periodecostingMainCtrl().getT05periodecostingDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT05periodecostingMainCtrl().tabT05periodecostingDetail, null));
	            } else if (getT05periodecostingMainCtrl().getT05periodecostingDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT05periodecostingMainCtrl().tabT05periodecostingDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT05periodecostingMainCtrl().tabT05periodecostingDetail, selectedData));
	            
	            getT05periodecostingMainCtrl().getT05periodecostingDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT05periodecosting(Event event) {
		if (listBoxT05periodecosting.getSelectedItem() != null) {
			T05periodecosting selectedData = (T05periodecosting) listBoxT05periodecosting.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT05periodecostingMainCtrl().getT05periodecostingDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT05periodecostingMainCtrl().tabT05periodecostingDetail, null));
		
				} else if (getT05periodecostingMainCtrl().getT05periodecostingDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT05periodecostingMainCtrl().tabT05periodecostingDetail, null));
				}
				
				getT05periodecostingMainCtrl().setSelectedT05periodecosting(selectedData);
				
			}
		}	
    }
    
    
    
    public void onSort$listheader_T05periodecostingList_masa(Event event) { 
    	sortingData(listheader_T05periodecostingList_masa, T05periodecostingComparator.COMPARE_BY_MASA);
    }
    
    public void onSort$listheader_T05periodecostingList_tahun(Event event) { 
    	sortingData(listheader_T05periodecostingList_tahun, T05periodecostingComparator.COMPARE_BY_TAHUN);
    }
    
    public void onSort$listheader_T05periodecostingList_closeKomisi(Event event) { 
    	sortingData(listheader_T05periodecostingList_closeKomisi, T05periodecostingComparator.COMPARE_BY_CLOSEKOMISI);
    }
    
    public void onSort$listheader_T05periodecostingList_closeTqs(Event event) { 
    	sortingData(listheader_T05periodecostingList_closeTqs, T05periodecostingComparator.COMPARE_BY_CLOSETQS);
    }
    
      
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T05periodecostingList, new T05periodecostingComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T05periodecostingList, new T05periodecostingComparator(true, sortBy));
    		}
    	}
    	
    	setModelT05periodecostingList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T05periodecostingList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT05periodecostingList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes

    	cmbCloseKomisi.setSelectedIndex(0);
    	cmbCloseTqs.setSelectedIndex(0);
    	cmbMasa.setSelectedIndex(0);
    	
    	txtTahun.setValue(null);


    	//startT05periodecostingList = 0;
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


    public void setSelectedT05periodecosting(T05periodecosting selectedT05periodecosting) {
        getT05periodecostingMainCtrl().setSelectedT05periodecosting(selectedT05periodecosting);
    }

    public T05periodecosting getSelectedT05periodecosting() {
        return getT05periodecostingMainCtrl().getSelectedT05periodecosting();
    }

    public void setT05periodecostingMainCtrl(T05periodecostingMainCtrl T05periodecostingMainCtrl) {
        this.T05periodecostingMainCtrl = T05periodecostingMainCtrl;
    }

    public T05periodecostingMainCtrl getT05periodecostingMainCtrl() {
        return this.T05periodecostingMainCtrl;
    }

}
