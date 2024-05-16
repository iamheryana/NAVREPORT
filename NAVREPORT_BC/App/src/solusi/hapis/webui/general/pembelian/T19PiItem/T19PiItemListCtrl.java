package solusi.hapis.webui.general.pembelian.T19PiItem;

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

import solusi.hapis.backend.navbi.model.T19PiItem;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 02-07-2021
 */

public class T19PiItemListCtrl extends GFCBaseListCtrl<T19PiItem> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT19PiItemList; 
    protected Panel panelT19PiItemList;
 	
	// Search Box Components 
    
	protected Textbox txtTglMulai;
	protected Textbox txtPrincipalCode;
	protected Textbox txtItemCatCode;
	protected Textbox txtProductCode;
	protected Combobox cmbBerlaku;
	
	
	// Paging and list
    protected Borderlayout borderLayout_T19PiItemList;
    protected Paging paging_T19PiItemList;
    private int startT19PiItemList;
	private List<T19PiItem> list_T19PiItemList = new ArrayList<T19PiItem>();
    private ListModelList modelList_T19PiItemList = new ListModelList();
    protected Listbox listBoxT19PiItem;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T19PiItemMainCtrl T19PiItemMainCtrl;

    // List Header		
    protected Listheader listheader_T19PiItemList_TglMulai;
    protected Listheader listheader_T19PiItemList_PrincipalCode;	
    protected Listheader listheader_T19PiItemList_ItemCatCode;	
    protected Listheader listheader_T19PiItemList_ProductCode;	
    protected Listheader listheader_T19PiItemList_Berlaku;	
		
    
    
    public T19PiItemListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T19PiItemList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T19PiItemList.setDetailed(true);
		paging_T19PiItemList.addEventListener("onPaging", onPaging_T19PiItemList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT19PiItemMainCtrl((T19PiItemMainCtrl) arg.get("ModuleMainController"));
		
		    getT19PiItemMainCtrl().setT19PiItemListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowT19PiItemList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan

        
        listheader_T19PiItemList_TglMulai.setSortAscending(new T19PiItemComparator(true, T19PiItemComparator.COMPARE_BY_TGL_MULAI));
        listheader_T19PiItemList_TglMulai.setSortDescending(new T19PiItemComparator(false, T19PiItemComparator.COMPARE_BY_TGL_MULAI));        
         
        listheader_T19PiItemList_PrincipalCode.setSortAscending(new T19PiItemComparator(true, T19PiItemComparator.COMPARE_BY_PRINCIPAL_CODE));
        listheader_T19PiItemList_PrincipalCode.setSortDescending(new T19PiItemComparator(false, T19PiItemComparator.COMPARE_BY_PRINCIPAL_CODE));        
       
        listheader_T19PiItemList_ItemCatCode.setSortAscending(new T19PiItemComparator(true, T19PiItemComparator.COMPARE_BY_ITEM_CAT_CODE));
        listheader_T19PiItemList_ItemCatCode.setSortDescending(new T19PiItemComparator(false, T19PiItemComparator.COMPARE_BY_ITEM_CAT_CODE));        
       
        listheader_T19PiItemList_ProductCode.setSortAscending(new T19PiItemComparator(true, T19PiItemComparator.COMPARE_BY_PRODUCT_CODE));
        listheader_T19PiItemList_ProductCode.setSortDescending(new T19PiItemComparator(false, T19PiItemComparator.COMPARE_BY_PRODUCT_CODE));        
       
        listheader_T19PiItemList_Berlaku.setSortAscending(new T19PiItemComparator(true, T19PiItemComparator.COMPARE_BY_BERLAKU));
        listheader_T19PiItemList_Berlaku.setSortDescending(new T19PiItemComparator(false, T19PiItemComparator.COMPARE_BY_BERLAKU));        
       
        
        getSearchData();
        
        listBoxT19PiItem.setItemRenderer(renderTable());
        
        windowT19PiItemList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		
		if (CommonUtils.isValidDateFormat(txtTglMulai.getValue())) {
			Date tglFrom = CommonUtils.convertStringToDate(txtTglMulai.getValue());
			Date tglTo = CommonUtils.createSecondParameterForSearch(txtTglMulai.getValue());
			parameterInput.put("tglMulaiFrom", tglFrom);
			parameterInput.put("tglMulaiTo", tglTo);
		}
		
		
		if (CommonUtils.isNotEmpty(txtPrincipalCode.getValue())) {
			parameterInput.put("principalCode", txtPrincipalCode.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtItemCatCode.getValue())) {
			parameterInput.put("itemCatCode", txtItemCatCode.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtProductCode.getValue())) {
			parameterInput.put("productCode", txtProductCode.getValue());
		}
		
		
		if (cmbBerlaku.getSelectedIndex() != 0) {
			parameterInput.put("berlaku", (String) cmbBerlaku.getSelectedItem().getValue());
		}
		
		list_T19PiItemList.clear();
		
		List<T19PiItem> tempListT19PiItem = getT19PiItemMainCtrl().getT19PiItemService().getListT19PiItem(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT19PiItem)) {
			list_T19PiItemList.addAll(tempListT19PiItem);
			startT19PiItemList = 0;
			paging_T19PiItemList.setActivePage(0);
		}

		setModelT19PiItemList();
	}

	// Pembagian data untuk paging
	private void setModelT19PiItemList() {		
		modelList_T19PiItemList.clear();
		
		T19PiItem selectedData = null;
		if (CommonUtils.isNotEmpty(list_T19PiItemList)){
			
			int endT19PiItemList = 0;
			if(startT19PiItemList + paging_T19PiItemList.getPageSize() < list_T19PiItemList.size()) {
				endT19PiItemList = startT19PiItemList + paging_T19PiItemList.getPageSize();
			} else {
				endT19PiItemList = list_T19PiItemList.size();
			}
			
			if (startT19PiItemList > endT19PiItemList) {
				startT19PiItemList = 0;
				paging_T19PiItemList.setActivePage(0);
			}
			
			modelList_T19PiItemList.addAll(list_T19PiItemList.subList(startT19PiItemList, endT19PiItemList));

			paging_T19PiItemList.setDetailed(true);
			paging_T19PiItemList.setTotalSize(list_T19PiItemList.size());
			
			listBoxT19PiItem.setModel(modelList_T19PiItemList);
			listBoxT19PiItem.setSelectedIndex(0);
	
			selectedData = list_T19PiItemList.get(startT19PiItemList);
		} else {
			paging_T19PiItemList.setDetailed(false);
			listBoxT19PiItem.setModel(modelList_T19PiItemList);
			paging_T19PiItemList.setTotalSize(0);
		}
		
		getT19PiItemMainCtrl().setSelectedT19PiItem(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T19PiItem rec = (T19PiItem) data;

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
                info.setParent(windowT19PiItemList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTglMulai()));
	            lc.setParent(item);	                 	                         
                
	            lc = new Listcell(rec.getPrincipalCode());
                lc.setParent(item);
                
                lc = new Listcell(rec.getItemCatCode());
                lc.setParent(item);
                
                lc = new Listcell(rec.getProductCode());
                lc.setParent(item);
                
                
                String vBerlaku = rec.getBerlaku().equals("Y")?"Ya (Checked)":"Tidak (Unchecked)";
                lc = new Listcell(vBerlaku);
                lc.setParent(item);                 
                
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT19PiItemItem");
              }
        };
    }
    
	private EventListener onPaging_T19PiItemList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT19PiItemList = pageNo * paging_T19PiItemList.getPageSize();
		         setModelT19PiItemList();
			}
		};
	}


    public void onDoubleClickedT19PiItemItem(Event event) {
		if (listBoxT19PiItem.getSelectedItem() != null) {
			T19PiItem selectedData = (T19PiItem) listBoxT19PiItem.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT19PiItem(selectedData);
	
	            if (getT19PiItemMainCtrl().getT19PiItemDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT19PiItemMainCtrl().tabT19PiItemDetail, null));
	            } else if (getT19PiItemMainCtrl().getT19PiItemDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT19PiItemMainCtrl().tabT19PiItemDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT19PiItemMainCtrl().tabT19PiItemDetail, selectedData));
	            
	            getT19PiItemMainCtrl().getT19PiItemDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT19PiItem(Event event) {
		if (listBoxT19PiItem.getSelectedItem() != null) {
			T19PiItem selectedData = (T19PiItem) listBoxT19PiItem.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT19PiItemMainCtrl().getT19PiItemDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT19PiItemMainCtrl().tabT19PiItemDetail, null));
		
				} else if (getT19PiItemMainCtrl().getT19PiItemDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT19PiItemMainCtrl().tabT19PiItemDetail, null));
				}
				
				getT19PiItemMainCtrl().setSelectedT19PiItem(selectedData);
				
			}
		}	
    }    
    
    
    public void onSort$listheader_T19PiItemList_TglMulai(Event event) {
    	sortingData(listheader_T19PiItemList_TglMulai, T19PiItemComparator.COMPARE_BY_TGL_MULAI);
    }
    
    public void onSort$listheader_T19PiItemList_PrincipalCode(Event event) {
    	sortingData(listheader_T19PiItemList_PrincipalCode, T19PiItemComparator.COMPARE_BY_PRINCIPAL_CODE);
    }
    
    public void onSort$listheader_T19PiItemList_ItemCatCode(Event event) {
    	sortingData(listheader_T19PiItemList_ItemCatCode, T19PiItemComparator.COMPARE_BY_ITEM_CAT_CODE);
    }

    public void onSort$listheader_T19PiItemList_ProductCode(Event event) {
    	sortingData(listheader_T19PiItemList_ProductCode, T19PiItemComparator.COMPARE_BY_PRODUCT_CODE);
    }
    
    public void onSort$listheader_T19PiItemList_Berlaku(Event event) {
    	sortingData(listheader_T19PiItemList_Berlaku, T19PiItemComparator.COMPARE_BY_BERLAKU);
    }
    
       
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T19PiItemList, new T19PiItemComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T19PiItemList, new T19PiItemComparator(true, sortBy));
    		}
    	}
    	
    	setModelT19PiItemList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T19PiItemList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT19PiItemList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	

    	
    	txtTglMulai.setValue(null);
    	txtPrincipalCode.setValue(null);
    	txtItemCatCode.setValue(null);
    	txtProductCode.setValue(null);
    	cmbBerlaku.setSelectedIndex(0);
   
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


    public void setSelectedT19PiItem(T19PiItem selectedT19PiItem) {
        getT19PiItemMainCtrl().setSelectedT19PiItem(selectedT19PiItem);
    }

    public T19PiItem getSelectedT19PiItem() {
        return getT19PiItemMainCtrl().getSelectedT19PiItem();
    }

    public void setT19PiItemMainCtrl(T19PiItemMainCtrl T19PiItemMainCtrl) {
        this.T19PiItemMainCtrl = T19PiItemMainCtrl;
    }

    public T19PiItemMainCtrl getT19PiItemMainCtrl() {
        return this.T19PiItemMainCtrl;
    }

}
