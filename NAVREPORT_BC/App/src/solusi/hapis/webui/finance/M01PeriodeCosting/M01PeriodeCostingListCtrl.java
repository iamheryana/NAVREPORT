package solusi.hapis.webui.finance.M01PeriodeCosting;


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

import solusi.hapis.backend.navbi.model.M01PeriodeCosting;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 12-04-2021
 */

public class M01PeriodeCostingListCtrl extends GFCBaseListCtrl<M01PeriodeCosting> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowM01PeriodeCostingList; 
    protected Panel panelM01PeriodeCostingList;
 	
	// Search Box Components 
	protected Combobox cmbMasa;
	protected Textbox txtTahun;
	protected Combobox cmbCloseKomisi;
	protected Combobox cmbCloseTqs;
	

	// Paging and list
    protected Borderlayout borderLayout_M01PeriodeCostingList;
    protected Paging paging_M01PeriodeCostingList;
    private int startM01PeriodeCostingList;
	private List<M01PeriodeCosting> list_M01PeriodeCostingList = new ArrayList<M01PeriodeCosting>();
    private ListModelList modelList_M01PeriodeCostingList = new ListModelList();
    protected Listbox listBoxM01PeriodeCosting;
    
    // Databinding
    private AnnotateDataBinder binder;
    private M01PeriodeCostingMainCtrl M01PeriodeCostingMainCtrl;

    // List Header
    protected Listheader listheader_M01PeriodeCostingList_masa;
    protected Listheader listheader_M01PeriodeCostingList_tahun;
    protected Listheader listheader_M01PeriodeCostingList_closeKomisi;
    protected Listheader listheader_M01PeriodeCostingList_closeTqs;
    
    
    public M01PeriodeCostingListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_M01PeriodeCostingList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_M01PeriodeCostingList.setDetailed(true);
		paging_M01PeriodeCostingList.addEventListener("onPaging", onPaging_M01PeriodeCostingList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setM01PeriodeCostingMainCtrl((M01PeriodeCostingMainCtrl) arg.get("ModuleMainController"));
		
		    getM01PeriodeCostingMainCtrl().setM01PeriodeCostingListCtrl(this);
		}
        
		
    }    
    

    public void onCreate$windowM01PeriodeCostingList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
 
      
              
        
        listheader_M01PeriodeCostingList_masa.setSortAscending(new M01PeriodeCostingComparator(true, M01PeriodeCostingComparator.COMPARE_BY_MASA));
        listheader_M01PeriodeCostingList_masa.setSortDescending(new M01PeriodeCostingComparator(false, M01PeriodeCostingComparator.COMPARE_BY_MASA));        
        
        listheader_M01PeriodeCostingList_tahun.setSortAscending(new M01PeriodeCostingComparator(true, M01PeriodeCostingComparator.COMPARE_BY_TAHUN));
        listheader_M01PeriodeCostingList_tahun.setSortDescending(new M01PeriodeCostingComparator(false, M01PeriodeCostingComparator.COMPARE_BY_TAHUN));        
        
        listheader_M01PeriodeCostingList_closeKomisi.setSortAscending(new M01PeriodeCostingComparator(true, M01PeriodeCostingComparator.COMPARE_BY_CLOSEKOMISI));
        listheader_M01PeriodeCostingList_closeKomisi.setSortDescending(new M01PeriodeCostingComparator(false, M01PeriodeCostingComparator.COMPARE_BY_CLOSEKOMISI));        
       
        listheader_M01PeriodeCostingList_closeTqs.setSortAscending(new M01PeriodeCostingComparator(true, M01PeriodeCostingComparator.COMPARE_BY_CLOSETQS));
        listheader_M01PeriodeCostingList_closeTqs.setSortDescending(new M01PeriodeCostingComparator(false, M01PeriodeCostingComparator.COMPARE_BY_CLOSETQS));        
       
        getSearchData();
        
        listBoxM01PeriodeCosting.setItemRenderer(renderTable());
        
        windowM01PeriodeCostingList.addEventListener(Events.ON_OK, onSubmitForm());
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
		
		list_M01PeriodeCostingList.clear();
		
		List<M01PeriodeCosting> tempListM01PeriodeCosting = getM01PeriodeCostingMainCtrl().getM01PeriodeCostingService().getListM01PeriodeCosting(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListM01PeriodeCosting)) {
			list_M01PeriodeCostingList.addAll(tempListM01PeriodeCosting);
			startM01PeriodeCostingList = 0;
			paging_M01PeriodeCostingList.setActivePage(0);
		}

		setModelM01PeriodeCostingList();
	}

	// Pembagian data untuk paging
	private void setModelM01PeriodeCostingList() {		
		modelList_M01PeriodeCostingList.clear();
		
		M01PeriodeCosting selectedData = null;
		if (CommonUtils.isNotEmpty(list_M01PeriodeCostingList)){
			
			int endM01PeriodeCostingList = 0;
			if(startM01PeriodeCostingList + paging_M01PeriodeCostingList.getPageSize() < list_M01PeriodeCostingList.size()) {
				endM01PeriodeCostingList = startM01PeriodeCostingList + paging_M01PeriodeCostingList.getPageSize();
			} else {
				endM01PeriodeCostingList = list_M01PeriodeCostingList.size();
			}
			
			if (startM01PeriodeCostingList > endM01PeriodeCostingList) {
				startM01PeriodeCostingList = 0;
				paging_M01PeriodeCostingList.setActivePage(0);
			}
			
			modelList_M01PeriodeCostingList.addAll(list_M01PeriodeCostingList.subList(startM01PeriodeCostingList, endM01PeriodeCostingList));

			paging_M01PeriodeCostingList.setDetailed(true);
			paging_M01PeriodeCostingList.setTotalSize(list_M01PeriodeCostingList.size());
			
			listBoxM01PeriodeCosting.setModel(modelList_M01PeriodeCostingList);
			listBoxM01PeriodeCosting.setSelectedIndex(0);
	
			selectedData = list_M01PeriodeCostingList.get(startM01PeriodeCostingList);
		} else {
			paging_M01PeriodeCostingList.setDetailed(false);
			listBoxM01PeriodeCosting.setModel(modelList_M01PeriodeCostingList);
			paging_M01PeriodeCostingList.setTotalSize(0);
		}
		
		getM01PeriodeCostingMainCtrl().setSelectedM01PeriodeCosting(selectedData);

	}
	

    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final M01PeriodeCosting rec = (M01PeriodeCosting) data;

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
                info.setParent(windowM01PeriodeCostingList);
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
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedM01PeriodeCostingItem");
              }
        };
    }
    
	private EventListener onPaging_M01PeriodeCostingList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startM01PeriodeCostingList = pageNo * paging_M01PeriodeCostingList.getPageSize();
		         setModelM01PeriodeCostingList();
			}
		};
	}


    public void onDoubleClickedM01PeriodeCostingItem(Event event) {
		if (listBoxM01PeriodeCosting.getSelectedItem() != null) {
			M01PeriodeCosting selectedData = (M01PeriodeCosting) listBoxM01PeriodeCosting.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedM01PeriodeCosting(selectedData);
	
	            if (getM01PeriodeCostingMainCtrl().getM01PeriodeCostingDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getM01PeriodeCostingMainCtrl().tabM01PeriodeCostingDetail, null));
	            } else if (getM01PeriodeCostingMainCtrl().getM01PeriodeCostingDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getM01PeriodeCostingMainCtrl().tabM01PeriodeCostingDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getM01PeriodeCostingMainCtrl().tabM01PeriodeCostingDetail, selectedData));
	            
	            getM01PeriodeCostingMainCtrl().getM01PeriodeCostingDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxM01PeriodeCosting(Event event) {
		if (listBoxM01PeriodeCosting.getSelectedItem() != null) {
			M01PeriodeCosting selectedData = (M01PeriodeCosting) listBoxM01PeriodeCosting.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getM01PeriodeCostingMainCtrl().getM01PeriodeCostingDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getM01PeriodeCostingMainCtrl().tabM01PeriodeCostingDetail, null));
		
				} else if (getM01PeriodeCostingMainCtrl().getM01PeriodeCostingDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getM01PeriodeCostingMainCtrl().tabM01PeriodeCostingDetail, null));
				}
				
				getM01PeriodeCostingMainCtrl().setSelectedM01PeriodeCosting(selectedData);
				
			}
		}	
    }
    
    
    
    public void onSort$listheader_M01PeriodeCostingList_masa(Event event) { 
    	sortingData(listheader_M01PeriodeCostingList_masa, M01PeriodeCostingComparator.COMPARE_BY_MASA);
    }
    
    public void onSort$listheader_M01PeriodeCostingList_tahun(Event event) { 
    	sortingData(listheader_M01PeriodeCostingList_tahun, M01PeriodeCostingComparator.COMPARE_BY_TAHUN);
    }
    
    public void onSort$listheader_M01PeriodeCostingList_closeKomisi(Event event) { 
    	sortingData(listheader_M01PeriodeCostingList_closeKomisi, M01PeriodeCostingComparator.COMPARE_BY_CLOSEKOMISI);
    }
    
    public void onSort$listheader_M01PeriodeCostingList_closeTqs(Event event) { 
    	sortingData(listheader_M01PeriodeCostingList_closeTqs, M01PeriodeCostingComparator.COMPARE_BY_CLOSETQS);
    }
    
      
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_M01PeriodeCostingList, new M01PeriodeCostingComparator(false, sortBy));
    		} else {
    			Collections.sort(list_M01PeriodeCostingList, new M01PeriodeCostingComparator(true, sortBy));
    		}
    	}
    	
    	setModelM01PeriodeCostingList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_M01PeriodeCostingList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowM01PeriodeCostingList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes

    	cmbCloseKomisi.setSelectedIndex(0);
    	cmbCloseTqs.setSelectedIndex(0);
    	cmbMasa.setSelectedIndex(0);
    	
    	txtTahun.setValue(null);


    	//startM01PeriodeCostingList = 0;
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


    public void setSelectedM01PeriodeCosting(M01PeriodeCosting selectedM01PeriodeCosting) {
        getM01PeriodeCostingMainCtrl().setSelectedM01PeriodeCosting(selectedM01PeriodeCosting);
    }

    public M01PeriodeCosting getSelectedM01PeriodeCosting() {
        return getM01PeriodeCostingMainCtrl().getSelectedM01PeriodeCosting();
    }

    public void setM01PeriodeCostingMainCtrl(M01PeriodeCostingMainCtrl M01PeriodeCostingMainCtrl) {
        this.M01PeriodeCostingMainCtrl = M01PeriodeCostingMainCtrl;
    }

    public M01PeriodeCostingMainCtrl getM01PeriodeCostingMainCtrl() {
        return this.M01PeriodeCostingMainCtrl;
    }

}
