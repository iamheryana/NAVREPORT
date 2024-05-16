package solusi.hapis.webui.finance.P02VendorNonnav;


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

import solusi.hapis.backend.navbi.model.P02VendorNonnav;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 30-01-2020
 */

public class P02VendorNonnavListCtrl extends GFCBaseListCtrl<P02VendorNonnav> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowP02VendorNonnavList; 
    protected Panel panelP02VendorNonnavList;
 	
	// Search Box Components 



	protected Textbox txtKode;
	protected Textbox txtValutaTrans;	
	protected Textbox txtNamaPenerima;
	protected Textbox txtNamaBank;
	protected Textbox txtNoRekPenerima;
	
	
	
	// Paging and list
    protected Borderlayout borderLayout_P02VendorNonnavList;
    protected Paging paging_P02VendorNonnavList;
    private int startP02VendorNonnavList;
	private List<P02VendorNonnav> list_P02VendorNonnavList = new ArrayList<P02VendorNonnav>();
    private ListModelList modelList_P02VendorNonnavList = new ListModelList();
    protected Listbox listBoxP02VendorNonnav;
    
    // Databinding
    private AnnotateDataBinder binder;
    private P02VendorNonnavMainCtrl P02VendorNonnavMainCtrl;

    // List Header		
    protected Listheader listheader_P02VendorNonnavList_Kode;
    protected Listheader listheader_P02VendorNonnavList_ValutaTrans;
    protected Listheader listheader_P02VendorNonnavList_NamaPenerima;
    protected Listheader listheader_P02VendorNonnavList_NamaBank;
    protected Listheader listheader_P02VendorNonnavList_NoRekPenerima;
		
		
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public P02VendorNonnavListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_P02VendorNonnavList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_P02VendorNonnavList.setDetailed(true);
		paging_P02VendorNonnavList.addEventListener("onPaging", onPaging_P02VendorNonnavList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setP02VendorNonnavMainCtrl((P02VendorNonnavMainCtrl) arg.get("ModuleMainController"));
		
		    getP02VendorNonnavMainCtrl().setP02VendorNonnavListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowP02VendorNonnavList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
        
        
        listheader_P02VendorNonnavList_Kode.setSortAscending(new P02VendorNonnavComparator(true, P02VendorNonnavComparator.COMPARE_BY_KODE));
        listheader_P02VendorNonnavList_Kode.setSortDescending(new P02VendorNonnavComparator(false, P02VendorNonnavComparator.COMPARE_BY_KODE));        
         
        listheader_P02VendorNonnavList_ValutaTrans.setSortAscending(new P02VendorNonnavComparator(true, P02VendorNonnavComparator.COMPARE_BY_VALUTATRANS));
        listheader_P02VendorNonnavList_ValutaTrans.setSortDescending(new P02VendorNonnavComparator(false, P02VendorNonnavComparator.COMPARE_BY_VALUTATRANS));        
       
        listheader_P02VendorNonnavList_NamaPenerima.setSortAscending(new P02VendorNonnavComparator(true, P02VendorNonnavComparator.COMPARE_BY_NAMAPENERIMA));
        listheader_P02VendorNonnavList_NamaPenerima.setSortDescending(new P02VendorNonnavComparator(false, P02VendorNonnavComparator.COMPARE_BY_NAMAPENERIMA));        
       
        listheader_P02VendorNonnavList_NamaBank.setSortAscending(new P02VendorNonnavComparator(true, P02VendorNonnavComparator.COMPARE_BY_NAMABANK));
        listheader_P02VendorNonnavList_NamaBank.setSortDescending(new P02VendorNonnavComparator(false, P02VendorNonnavComparator.COMPARE_BY_NAMABANK));        
                
        listheader_P02VendorNonnavList_NoRekPenerima.setSortAscending(new P02VendorNonnavComparator(true, P02VendorNonnavComparator.COMPARE_BY_NOREKPENERIMA));
        listheader_P02VendorNonnavList_NoRekPenerima.setSortDescending(new P02VendorNonnavComparator(false, P02VendorNonnavComparator.COMPARE_BY_NOREKPENERIMA));        
       
        
        
        
        getSearchData();
        
        listBoxP02VendorNonnav.setItemRenderer(renderTable());
        
        windowP02VendorNonnavList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		
		
		
		if (CommonUtils.isNotEmpty(txtKode.getValue())) {
			parameterInput.put("kode", txtKode.getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtValutaTrans.getValue())) {
			parameterInput.put("valutaTrans", txtValutaTrans.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtNamaPenerima.getValue())) {
			parameterInput.put("namaPenerima", txtNamaPenerima.getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtNamaBank.getValue())) {
			parameterInput.put("namaBank", txtNamaBank.getValue());
		}
		
	
		if (CommonUtils.isNotEmpty(txtNoRekPenerima.getValue())) {
			parameterInput.put("noRekPenerima", txtNoRekPenerima.getValue());
		}
		
		
		
		
	
		list_P02VendorNonnavList.clear();
		
		List<P02VendorNonnav> tempListP02VendorNonnav = getP02VendorNonnavMainCtrl().getP02VendorNonnavService().getListP02VendorNonnav(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListP02VendorNonnav)) {
			list_P02VendorNonnavList.addAll(tempListP02VendorNonnav);
			startP02VendorNonnavList = 0;
			paging_P02VendorNonnavList.setActivePage(0);
		}

		setModelP02VendorNonnavList();
	}

	// Pembagian data untuk paging
	private void setModelP02VendorNonnavList() {		
		modelList_P02VendorNonnavList.clear();
		
		P02VendorNonnav selectedData = null;
		if (CommonUtils.isNotEmpty(list_P02VendorNonnavList)){
			
			int endP02VendorNonnavList = 0;
			if(startP02VendorNonnavList + paging_P02VendorNonnavList.getPageSize() < list_P02VendorNonnavList.size()) {
				endP02VendorNonnavList = startP02VendorNonnavList + paging_P02VendorNonnavList.getPageSize();
			} else {
				endP02VendorNonnavList = list_P02VendorNonnavList.size();
			}
			
			if (startP02VendorNonnavList > endP02VendorNonnavList) {
				startP02VendorNonnavList = 0;
				paging_P02VendorNonnavList.setActivePage(0);
			}
			
			modelList_P02VendorNonnavList.addAll(list_P02VendorNonnavList.subList(startP02VendorNonnavList, endP02VendorNonnavList));

			paging_P02VendorNonnavList.setDetailed(true);
			paging_P02VendorNonnavList.setTotalSize(list_P02VendorNonnavList.size());
			
			listBoxP02VendorNonnav.setModel(modelList_P02VendorNonnavList);
			listBoxP02VendorNonnav.setSelectedIndex(0);
	
			selectedData = list_P02VendorNonnavList.get(startP02VendorNonnavList);
		} else {
			paging_P02VendorNonnavList.setDetailed(false);
			listBoxP02VendorNonnav.setModel(modelList_P02VendorNonnavList);
			paging_P02VendorNonnavList.setTotalSize(0);
		}
		
		getP02VendorNonnavMainCtrl().setSelectedP02VendorNonnav(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final P02VendorNonnav rec = (P02VendorNonnav) data;

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
                info.setParent(windowP02VendorNonnavList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                
     

                
                lc = new Listcell(rec.getKode());
                lc.setParent(item);
                
                lc = new Listcell(rec.getValutaTrans());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNamaPenerima());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNamaBank());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoRekPenerima());
                lc.setParent(item);
                
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedP02VendorNonnavItem");
              }
        };
    }
    
	private EventListener onPaging_P02VendorNonnavList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startP02VendorNonnavList = pageNo * paging_P02VendorNonnavList.getPageSize();
		         setModelP02VendorNonnavList();
			}
		};
	}


    public void onDoubleClickedP02VendorNonnavItem(Event event) {
		if (listBoxP02VendorNonnav.getSelectedItem() != null) {
			P02VendorNonnav selectedData = (P02VendorNonnav) listBoxP02VendorNonnav.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedP02VendorNonnav(selectedData);
	
	            if (getP02VendorNonnavMainCtrl().getP02VendorNonnavDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getP02VendorNonnavMainCtrl().tabP02VendorNonnavDetail, null));
	            } else if (getP02VendorNonnavMainCtrl().getP02VendorNonnavDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getP02VendorNonnavMainCtrl().tabP02VendorNonnavDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getP02VendorNonnavMainCtrl().tabP02VendorNonnavDetail, selectedData));
	            
	            getP02VendorNonnavMainCtrl().getP02VendorNonnavDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxP02VendorNonnav(Event event) {
		if (listBoxP02VendorNonnav.getSelectedItem() != null) {
			P02VendorNonnav selectedData = (P02VendorNonnav) listBoxP02VendorNonnav.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getP02VendorNonnavMainCtrl().getP02VendorNonnavDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getP02VendorNonnavMainCtrl().tabP02VendorNonnavDetail, null));
		
				} else if (getP02VendorNonnavMainCtrl().getP02VendorNonnavDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getP02VendorNonnavMainCtrl().tabP02VendorNonnavDetail, null));
				}
				
				getP02VendorNonnavMainCtrl().setSelectedP02VendorNonnav(selectedData);
				
			}
		}	
    }
    
    

    
    
    public void onSort$listheader_P02VendorNonnavList_Kode(Event event) {
    	sortingData(listheader_P02VendorNonnavList_Kode, P02VendorNonnavComparator.COMPARE_BY_KODE);
    }
    
    public void onSort$listheader_P02VendorNonnavList_ValutaTrans(Event event) {
    	sortingData(listheader_P02VendorNonnavList_ValutaTrans, P02VendorNonnavComparator.COMPARE_BY_VALUTATRANS);
    }

    public void onSort$listheader_P02VendorNonnavList_NamaPenerima(Event event) {
    	sortingData(listheader_P02VendorNonnavList_NamaPenerima, P02VendorNonnavComparator.COMPARE_BY_NAMAPENERIMA);
    }
    
    public void onSort$listheader_P02VendorNonnavList_NamaBank(Event event) {
    	sortingData(listheader_P02VendorNonnavList_NamaBank, P02VendorNonnavComparator.COMPARE_BY_NAMABANK);
    }

    public void onSort$listheader_P02VendorNonnavList_NoRekPenerima(Event event) {
    	sortingData(listheader_P02VendorNonnavList_NoRekPenerima, P02VendorNonnavComparator.COMPARE_BY_NOREKPENERIMA);
    }
    
      
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_P02VendorNonnavList, new P02VendorNonnavComparator(false, sortBy));
    		} else {
    			Collections.sort(list_P02VendorNonnavList, new P02VendorNonnavComparator(true, sortBy));
    		}
    	}
    	
    	setModelP02VendorNonnavList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_P02VendorNonnavList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowP02VendorNonnavList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
    	

    	txtKode.setValue(null);    	
    	txtValutaTrans.setValue(null);
    	txtNamaPenerima.setValue(null);
    	txtNamaBank.setValue(null);
    	txtNoRekPenerima.setValue(null);
    	
    	//startP02VendorNonnavList = 0;
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


    public void setSelectedP02VendorNonnav(P02VendorNonnav selectedP02VendorNonnav) {
        getP02VendorNonnavMainCtrl().setSelectedP02VendorNonnav(selectedP02VendorNonnav);
    }

    public P02VendorNonnav getSelectedP02VendorNonnav() {
        return getP02VendorNonnavMainCtrl().getSelectedP02VendorNonnav();
    }

    public void setP02VendorNonnavMainCtrl(P02VendorNonnavMainCtrl P02VendorNonnavMainCtrl) {
        this.P02VendorNonnavMainCtrl = P02VendorNonnavMainCtrl;
    }

    public P02VendorNonnavMainCtrl getP02VendorNonnavMainCtrl() {
        return this.P02VendorNonnavMainCtrl;
    }

}


