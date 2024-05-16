package solusi.hapis.webui.ps.T12PsAdjPrice;


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

import solusi.hapis.backend.navbi.model.T12PsAdjPrice;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 28-01-2021
 */

public class T12PsAdjPriceListCtrl extends GFCBaseListCtrl<T12PsAdjPrice> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT12PsAdjPriceList; 
    protected Panel panelT12PsAdjPriceList;
 	
	// Search Box Components 
    
	protected Textbox txtTglBerlaku;
	protected Textbox txtCustNo;
	protected Textbox txtItemNo;
	protected Textbox txtCurr;
	protected Textbox txtPrice;
	
	// Paging and list
    protected Borderlayout borderLayout_T12PsAdjPriceList;
    protected Paging paging_T12PsAdjPriceList;
    private int startT12PsAdjPriceList;
	private List<T12PsAdjPrice> list_T12PsAdjPriceList = new ArrayList<T12PsAdjPrice>();
    private ListModelList modelList_T12PsAdjPriceList = new ListModelList();
    protected Listbox listBoxT12PsAdjPrice;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T12PsAdjPriceMainCtrl T12PsAdjPriceMainCtrl;

    // List Header		
    protected Listheader listheader_T12PsAdjPriceList_TglBerlaku;
    protected Listheader listheader_T12PsAdjPriceList_CustNo;
    protected Listheader listheader_T12PsAdjPriceList_ItemNo;
    protected Listheader listheader_T12PsAdjPriceList_CurrecyCode;
    protected Listheader listheader_T12PsAdjPriceList_Price;		
		
		
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T12PsAdjPriceListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T12PsAdjPriceList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T12PsAdjPriceList.setDetailed(true);
		paging_T12PsAdjPriceList.addEventListener("onPaging", onPaging_T12PsAdjPriceList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT12PsAdjPriceMainCtrl((T12PsAdjPriceMainCtrl) arg.get("ModuleMainController"));
		
		    getT12PsAdjPriceMainCtrl().setT12PsAdjPriceListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowT12PsAdjPriceList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
        
        listheader_T12PsAdjPriceList_TglBerlaku.setSortAscending(new T12PsAdjPriceComparator(true, T12PsAdjPriceComparator.COMPARE_BY_TGLBERLAKU));
        listheader_T12PsAdjPriceList_TglBerlaku.setSortDescending(new T12PsAdjPriceComparator(false, T12PsAdjPriceComparator.COMPARE_BY_TGLBERLAKU));        
         
        listheader_T12PsAdjPriceList_CustNo.setSortAscending(new T12PsAdjPriceComparator(true, T12PsAdjPriceComparator.COMPARE_BY_CUSTNO));
        listheader_T12PsAdjPriceList_CustNo.setSortDescending(new T12PsAdjPriceComparator(false, T12PsAdjPriceComparator.COMPARE_BY_CUSTNO));        
       
        listheader_T12PsAdjPriceList_ItemNo.setSortAscending(new T12PsAdjPriceComparator(true, T12PsAdjPriceComparator.COMPARE_BY_ITEMNO));
        listheader_T12PsAdjPriceList_ItemNo.setSortDescending(new T12PsAdjPriceComparator(false, T12PsAdjPriceComparator.COMPARE_BY_ITEMNO));        
       
        listheader_T12PsAdjPriceList_CurrecyCode.setSortAscending(new T12PsAdjPriceComparator(true, T12PsAdjPriceComparator.COMPARE_BY_CURRCODE));
        listheader_T12PsAdjPriceList_CurrecyCode.setSortDescending(new T12PsAdjPriceComparator(false, T12PsAdjPriceComparator.COMPARE_BY_CURRCODE));        
            
        listheader_T12PsAdjPriceList_Price.setSortAscending(new T12PsAdjPriceComparator(true, T12PsAdjPriceComparator.COMPARE_BY_ADJPRICE));
        listheader_T12PsAdjPriceList_Price.setSortDescending(new T12PsAdjPriceComparator(false, T12PsAdjPriceComparator.COMPARE_BY_ADJPRICE));        
       
        getSearchData();
        
        listBoxT12PsAdjPrice.setItemRenderer(renderTable());
        
        windowT12PsAdjPriceList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (CommonUtils.isValidDateFormat(txtTglBerlaku.getValue())) {
			Date tglFrom = CommonUtils.convertStringToDate(txtTglBerlaku.getValue());
			Date tglTo = CommonUtils.createSecondParameterForSearch(txtTglBerlaku.getValue());
			parameterInput.put("tglBerlakuFrom", tglFrom);
			parameterInput.put("tglBerlakuTo", tglTo);
		}
		
		
		if (CommonUtils.isNotEmpty(txtCustNo.getValue())) {
			parameterInput.put("custNo", txtCustNo.getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtItemNo.getValue())) {
			parameterInput.put("itemNo", txtItemNo.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtCurr.getValue())) {
			parameterInput.put("currCode", txtCurr.getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtPrice.getValue())) {
			parameterInput.put("adjPrice", txtPrice.getValue());
		}
		
		
		list_T12PsAdjPriceList.clear();
		
		List<T12PsAdjPrice> tempListT12PsAdjPrice = getT12PsAdjPriceMainCtrl().getT12PsAdjPriceService().getListT12PsAdjPrice(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT12PsAdjPrice)) {
			list_T12PsAdjPriceList.addAll(tempListT12PsAdjPrice);
			startT12PsAdjPriceList = 0;
			paging_T12PsAdjPriceList.setActivePage(0);
		}

		setModelT12PsAdjPriceList();
	}

	// Pembagian data untuk paging
	private void setModelT12PsAdjPriceList() {		
		modelList_T12PsAdjPriceList.clear();
		
		T12PsAdjPrice selectedData = null;
		if (CommonUtils.isNotEmpty(list_T12PsAdjPriceList)){
			
			int endT12PsAdjPriceList = 0;
			if(startT12PsAdjPriceList + paging_T12PsAdjPriceList.getPageSize() < list_T12PsAdjPriceList.size()) {
				endT12PsAdjPriceList = startT12PsAdjPriceList + paging_T12PsAdjPriceList.getPageSize();
			} else {
				endT12PsAdjPriceList = list_T12PsAdjPriceList.size();
			}
			
			if (startT12PsAdjPriceList > endT12PsAdjPriceList) {
				startT12PsAdjPriceList = 0;
				paging_T12PsAdjPriceList.setActivePage(0);
			}
			
			modelList_T12PsAdjPriceList.addAll(list_T12PsAdjPriceList.subList(startT12PsAdjPriceList, endT12PsAdjPriceList));

			paging_T12PsAdjPriceList.setDetailed(true);
			paging_T12PsAdjPriceList.setTotalSize(list_T12PsAdjPriceList.size());
			
			listBoxT12PsAdjPrice.setModel(modelList_T12PsAdjPriceList);
			listBoxT12PsAdjPrice.setSelectedIndex(0);
	
			selectedData = list_T12PsAdjPriceList.get(startT12PsAdjPriceList);
		} else {
			paging_T12PsAdjPriceList.setDetailed(false);
			listBoxT12PsAdjPrice.setModel(modelList_T12PsAdjPriceList);
			paging_T12PsAdjPriceList.setTotalSize(0);
		}
		
		getT12PsAdjPriceMainCtrl().setSelectedT12PsAdjPrice(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T12PsAdjPrice rec = (T12PsAdjPrice) data;

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
                info.setParent(windowT12PsAdjPriceList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTglBerlaku()));
	            lc.setParent(item);
	            
                
                lc = new Listcell(rec.getCustNo());
                lc.setParent(item);
                
                lc = new Listcell(rec.getItemNo());
                lc.setParent(item);
                
                lc = new Listcell(rec.getCurrCode());
                lc.setParent(item);
                
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getAdjPrice(), "#,##0.00"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);                	                         
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT12PsAdjPriceItem");
              }
        };
    }
    
	private EventListener onPaging_T12PsAdjPriceList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT12PsAdjPriceList = pageNo * paging_T12PsAdjPriceList.getPageSize();
		         setModelT12PsAdjPriceList();
			}
		};
	}


    public void onDoubleClickedT12PsAdjPriceItem(Event event) {
		if (listBoxT12PsAdjPrice.getSelectedItem() != null) {
			T12PsAdjPrice selectedData = (T12PsAdjPrice) listBoxT12PsAdjPrice.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT12PsAdjPrice(selectedData);
	
	            if (getT12PsAdjPriceMainCtrl().getT12PsAdjPriceDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT12PsAdjPriceMainCtrl().tabT12PsAdjPriceDetail, null));
	            } else if (getT12PsAdjPriceMainCtrl().getT12PsAdjPriceDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT12PsAdjPriceMainCtrl().tabT12PsAdjPriceDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT12PsAdjPriceMainCtrl().tabT12PsAdjPriceDetail, selectedData));
	            
	            getT12PsAdjPriceMainCtrl().getT12PsAdjPriceDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT12PsAdjPrice(Event event) {
		if (listBoxT12PsAdjPrice.getSelectedItem() != null) {
			T12PsAdjPrice selectedData = (T12PsAdjPrice) listBoxT12PsAdjPrice.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT12PsAdjPriceMainCtrl().getT12PsAdjPriceDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT12PsAdjPriceMainCtrl().tabT12PsAdjPriceDetail, null));
		
				} else if (getT12PsAdjPriceMainCtrl().getT12PsAdjPriceDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT12PsAdjPriceMainCtrl().tabT12PsAdjPriceDetail, null));
				}
				
				getT12PsAdjPriceMainCtrl().setSelectedT12PsAdjPrice(selectedData);
				
			}
		}	
    }    
    
    public void onSort$listheader_T12PsAdjPriceList_TglBerlaku(Event event) {
    	sortingData(listheader_T12PsAdjPriceList_TglBerlaku, T12PsAdjPriceComparator.COMPARE_BY_TGLBERLAKU);
    }
    
    public void onSort$listheader_T12PsAdjPriceList_CustNo(Event event) {
    	sortingData(listheader_T12PsAdjPriceList_CustNo, T12PsAdjPriceComparator.COMPARE_BY_CUSTNO);
    }

    public void onSort$listheader_T12PsAdjPriceList_ItemNo(Event event) {
    	sortingData(listheader_T12PsAdjPriceList_ItemNo, T12PsAdjPriceComparator.COMPARE_BY_ITEMNO);
    }
    
    public void onSort$listheader_T12PsAdjPriceList_CurrecyCode(Event event) {
    	sortingData(listheader_T12PsAdjPriceList_CurrecyCode, T12PsAdjPriceComparator.COMPARE_BY_CURRCODE);
    }
    
    public void onSort$listheader_T12PsAdjPriceList_Price(Event event) {
    	sortingData(listheader_T12PsAdjPriceList_Price, T12PsAdjPriceComparator.COMPARE_BY_ADJPRICE);
    }

    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T12PsAdjPriceList, new T12PsAdjPriceComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T12PsAdjPriceList, new T12PsAdjPriceComparator(true, sortBy));
    		}
    	}
    	
    	setModelT12PsAdjPriceList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T12PsAdjPriceList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT12PsAdjPriceList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
    	txtTglBerlaku.setValue(null);
    	txtCustNo.setValue(null);
    	txtItemNo.setValue(null);
    	txtCurr.setValue(null);
    	txtPrice.setValue(null);
    	
   
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


    public void setSelectedT12PsAdjPrice(T12PsAdjPrice selectedT12PsAdjPrice) {
        getT12PsAdjPriceMainCtrl().setSelectedT12PsAdjPrice(selectedT12PsAdjPrice);
    }

    public T12PsAdjPrice getSelectedT12PsAdjPrice() {
        return getT12PsAdjPriceMainCtrl().getSelectedT12PsAdjPrice();
    }

    public void setT12PsAdjPriceMainCtrl(T12PsAdjPriceMainCtrl T12PsAdjPriceMainCtrl) {
        this.T12PsAdjPriceMainCtrl = T12PsAdjPriceMainCtrl;
    }

    public T12PsAdjPriceMainCtrl getT12PsAdjPriceMainCtrl() {
        return this.T12PsAdjPriceMainCtrl;
    }

}
