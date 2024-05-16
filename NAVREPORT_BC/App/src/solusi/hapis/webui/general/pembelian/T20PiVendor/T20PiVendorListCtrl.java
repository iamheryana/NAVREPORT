package solusi.hapis.webui.general.pembelian.T20PiVendor;

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

import solusi.hapis.backend.navbi.model.T20PiVendor;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 02-07-2021
 */

public class T20PiVendorListCtrl extends GFCBaseListCtrl<T20PiVendor> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT20PiVendorList; 
    protected Panel panelT20PiVendorList;
 	
	// Search Box Components 
    
	protected Textbox txtTglMulai;
	protected Textbox txtPrincipalCode;
	protected Textbox txtVendorCode;
	protected Combobox cmbBerlaku;
	
	
	// Paging and list
    protected Borderlayout borderLayout_T20PiVendorList;
    protected Paging paging_T20PiVendorList;
    private int startT20PiVendorList;
	private List<T20PiVendor> list_T20PiVendorList = new ArrayList<T20PiVendor>();
    private ListModelList modelList_T20PiVendorList = new ListModelList();
    protected Listbox listBoxT20PiVendor;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T20PiVendorMainCtrl T20PiVendorMainCtrl;

    // List Header		
    protected Listheader listheader_T20PiVendorList_TglMulai;
    protected Listheader listheader_T20PiVendorList_PrincipalCode;	
    protected Listheader listheader_T20PiVendorList_VendorCode;	
    protected Listheader listheader_T20PiVendorList_Berlaku;	
		
    
    
    public T20PiVendorListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T20PiVendorList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T20PiVendorList.setDetailed(true);
		paging_T20PiVendorList.addEventListener("onPaging", onPaging_T20PiVendorList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT20PiVendorMainCtrl((T20PiVendorMainCtrl) arg.get("ModuleMainController"));
		
		    getT20PiVendorMainCtrl().setT20PiVendorListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowT20PiVendorList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan

        
        listheader_T20PiVendorList_TglMulai.setSortAscending(new T20PiVendorComparator(true, T20PiVendorComparator.COMPARE_BY_TGL_MULAI));
        listheader_T20PiVendorList_TglMulai.setSortDescending(new T20PiVendorComparator(false, T20PiVendorComparator.COMPARE_BY_TGL_MULAI));        
         
        listheader_T20PiVendorList_PrincipalCode.setSortAscending(new T20PiVendorComparator(true, T20PiVendorComparator.COMPARE_BY_PRINCIPAL_CODE));
        listheader_T20PiVendorList_PrincipalCode.setSortDescending(new T20PiVendorComparator(false, T20PiVendorComparator.COMPARE_BY_PRINCIPAL_CODE));        
       
        listheader_T20PiVendorList_VendorCode.setSortAscending(new T20PiVendorComparator(true, T20PiVendorComparator.COMPARE_BY_VENDOR_CODE));
        listheader_T20PiVendorList_VendorCode.setSortDescending(new T20PiVendorComparator(false, T20PiVendorComparator.COMPARE_BY_VENDOR_CODE));        
       
      
        listheader_T20PiVendorList_Berlaku.setSortAscending(new T20PiVendorComparator(true, T20PiVendorComparator.COMPARE_BY_BERLAKU));
        listheader_T20PiVendorList_Berlaku.setSortDescending(new T20PiVendorComparator(false, T20PiVendorComparator.COMPARE_BY_BERLAKU));        
       
        
        getSearchData();
        
        listBoxT20PiVendor.setItemRenderer(renderTable());
        
        windowT20PiVendorList.addEventListener(Events.ON_OK, onSubmitForm());
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
		
		
		if (CommonUtils.isNotEmpty(txtVendorCode.getValue())) {
			parameterInput.put("vendorCode", txtVendorCode.getValue());
		}
		
		
		if (cmbBerlaku.getSelectedIndex() != 0) {
			parameterInput.put("berlaku", (String) cmbBerlaku.getSelectedItem().getValue());
		}
		
		list_T20PiVendorList.clear();
		
		List<T20PiVendor> tempListT20PiVendor = getT20PiVendorMainCtrl().getT20PiVendorService().getListT20PiVendor(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT20PiVendor)) {
			list_T20PiVendorList.addAll(tempListT20PiVendor);
			startT20PiVendorList = 0;
			paging_T20PiVendorList.setActivePage(0);
		}

		setModelT20PiVendorList();
	}

	// Pembagian data untuk paging
	private void setModelT20PiVendorList() {		
		modelList_T20PiVendorList.clear();
		
		T20PiVendor selectedData = null;
		if (CommonUtils.isNotEmpty(list_T20PiVendorList)){
			
			int endT20PiVendorList = 0;
			if(startT20PiVendorList + paging_T20PiVendorList.getPageSize() < list_T20PiVendorList.size()) {
				endT20PiVendorList = startT20PiVendorList + paging_T20PiVendorList.getPageSize();
			} else {
				endT20PiVendorList = list_T20PiVendorList.size();
			}
			
			if (startT20PiVendorList > endT20PiVendorList) {
				startT20PiVendorList = 0;
				paging_T20PiVendorList.setActivePage(0);
			}
			
			modelList_T20PiVendorList.addAll(list_T20PiVendorList.subList(startT20PiVendorList, endT20PiVendorList));

			paging_T20PiVendorList.setDetailed(true);
			paging_T20PiVendorList.setTotalSize(list_T20PiVendorList.size());
			
			listBoxT20PiVendor.setModel(modelList_T20PiVendorList);
			listBoxT20PiVendor.setSelectedIndex(0);
	
			selectedData = list_T20PiVendorList.get(startT20PiVendorList);
		} else {
			paging_T20PiVendorList.setDetailed(false);
			listBoxT20PiVendor.setModel(modelList_T20PiVendorList);
			paging_T20PiVendorList.setTotalSize(0);
		}
		
		getT20PiVendorMainCtrl().setSelectedT20PiVendor(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T20PiVendor rec = (T20PiVendor) data;

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
                info.setParent(windowT20PiVendorList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTglMulai()));
	            lc.setParent(item);	                 	                         
                
	            lc = new Listcell(rec.getPrincipalCode());
                lc.setParent(item);
                
                lc = new Listcell(rec.getVendorCode());
                lc.setParent(item);
                
                
                String vBerlaku = rec.getBerlaku().equals("Y")?"Ya (Checked)":"Tidak (Unchecked)";
                lc = new Listcell(vBerlaku);
                lc.setParent(item);                 
                
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT20PiVendorItem");
              }
        };
    }
    
	private EventListener onPaging_T20PiVendorList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT20PiVendorList = pageNo * paging_T20PiVendorList.getPageSize();
		         setModelT20PiVendorList();
			}
		};
	}


    public void onDoubleClickedT20PiVendorItem(Event event) {
		if (listBoxT20PiVendor.getSelectedItem() != null) {
			T20PiVendor selectedData = (T20PiVendor) listBoxT20PiVendor.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT20PiVendor(selectedData);
	
	            if (getT20PiVendorMainCtrl().getT20PiVendorDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT20PiVendorMainCtrl().tabT20PiVendorDetail, null));
	            } else if (getT20PiVendorMainCtrl().getT20PiVendorDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT20PiVendorMainCtrl().tabT20PiVendorDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT20PiVendorMainCtrl().tabT20PiVendorDetail, selectedData));
	            
	            getT20PiVendorMainCtrl().getT20PiVendorDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT20PiVendor(Event event) {
		if (listBoxT20PiVendor.getSelectedItem() != null) {
			T20PiVendor selectedData = (T20PiVendor) listBoxT20PiVendor.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT20PiVendorMainCtrl().getT20PiVendorDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT20PiVendorMainCtrl().tabT20PiVendorDetail, null));
		
				} else if (getT20PiVendorMainCtrl().getT20PiVendorDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT20PiVendorMainCtrl().tabT20PiVendorDetail, null));
				}
				
				getT20PiVendorMainCtrl().setSelectedT20PiVendor(selectedData);
				
			}
		}	
    }    
    
    
    public void onSort$listheader_T20PiVendorList_TglMulai(Event event) {
    	sortingData(listheader_T20PiVendorList_TglMulai, T20PiVendorComparator.COMPARE_BY_TGL_MULAI);
    }
    
    public void onSort$listheader_T20PiVendorList_PrincipalCode(Event event) {
    	sortingData(listheader_T20PiVendorList_PrincipalCode, T20PiVendorComparator.COMPARE_BY_PRINCIPAL_CODE);
    }
    
    public void onSort$listheader_T20PiVendorList_VendorCode(Event event) {
    	sortingData(listheader_T20PiVendorList_VendorCode, T20PiVendorComparator.COMPARE_BY_VENDOR_CODE);
    }
    
    public void onSort$listheader_T20PiVendorList_Berlaku(Event event) {
    	sortingData(listheader_T20PiVendorList_Berlaku, T20PiVendorComparator.COMPARE_BY_BERLAKU);
    }
    
       
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T20PiVendorList, new T20PiVendorComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T20PiVendorList, new T20PiVendorComparator(true, sortBy));
    		}
    	}
    	
    	setModelT20PiVendorList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T20PiVendorList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT20PiVendorList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	

    	
    	txtTglMulai.setValue(null);
    	txtPrincipalCode.setValue(null);
    	txtVendorCode.setValue(null);    	
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


    public void setSelectedT20PiVendor(T20PiVendor selectedT20PiVendor) {
        getT20PiVendorMainCtrl().setSelectedT20PiVendor(selectedT20PiVendor);
    }

    public T20PiVendor getSelectedT20PiVendor() {
        return getT20PiVendorMainCtrl().getSelectedT20PiVendor();
    }

    public void setT20PiVendorMainCtrl(T20PiVendorMainCtrl T20PiVendorMainCtrl) {
        this.T20PiVendorMainCtrl = T20PiVendorMainCtrl;
    }

    public T20PiVendorMainCtrl getT20PiVendorMainCtrl() {
        return this.T20PiVendorMainCtrl;
    }

}
