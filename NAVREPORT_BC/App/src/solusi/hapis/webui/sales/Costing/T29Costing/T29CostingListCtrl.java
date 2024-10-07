package solusi.hapis.webui.sales.Costing.T29Costing;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
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

import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.backend.navbi.model.T30CostingDHw3ps;
import solusi.hapis.backend.navbi.model.T31CostingDAcsps;
import solusi.hapis.backend.navbi.model.T32CostingDOwnsw;
import solusi.hapis.backend.navbi.model.T33CostingDOther;
import solusi.hapis.backend.navbi.model.T34CostingDPayment;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 24-07-2024
 */

public class T29CostingListCtrl extends GFCBaseListCtrl<T29CostingH> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT29CostingList; 
    
    protected Paging paging_T29CostingList;
    protected Listbox listBoxT29Costing;
    private int startT29CostingList;
   	private List<T29CostingH> list_T29CostingList = new ArrayList<T29CostingH>();
   	
   	private List<T30CostingDHw3ps> list_T30CostingDHw3psList = new ArrayList<T30CostingDHw3ps>();
	private List<T31CostingDAcsps> list_T31CostingDAcspsList = new ArrayList<T31CostingDAcsps>();
	private List<T32CostingDOwnsw> list_T32CostingDOwnswList = new ArrayList<T32CostingDOwnsw>();
	private List<T33CostingDOther> list_T33CostingDOtherList = new ArrayList<T33CostingDOther>();
	private List<T34CostingDPayment> list_T34CostingDPaymentList = new ArrayList<T34CostingDPayment>();
	  
   	
    private ListModelList modelList_T29CostingList = new ListModelList();
     
    // Databinding
    private AnnotateDataBinder binder;
    private T29CostingMainCtrl T29CostingMainCtrl;

    // List Header
    protected Listheader listheader_T29CostingList_TglCosting;
    protected Listheader listheader_T29CostingList_NoCosting;
    protected Listheader listheader_T29CostingList_Salesman;
    protected Listheader listheader_T29CostingList_NoBSO;
    protected Listheader listheader_T29CostingList_NoSO;
    protected Listheader listheader_T29CostingList_NoPOCustomer;
    protected Listheader listheader_T29CostingList_Customer;
    protected Listheader listheader_T29CostingList_FlagInvoice;
    protected Listheader listheader_T29CostingList_FlagLunas;
    
 
    // Search Box Components 
 	protected Textbox txtTglCosting;
 	protected Textbox txtNoCosting;
 	protected Textbox txtSales;
 	protected Textbox txtNoBSO;
 	protected Textbox txtNoSO;
 	protected Textbox txtNoPOCustomer;
 	protected Textbox txtCustomer;

 	protected Combobox cmbPosisi;
 	protected Combobox cmbFlagInvoice;
 	protected Combobox cmbFlagLunas;
 	
 	private SelectQueryService selectQueryService;
     
    
    public T29CostingListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T29CostingList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T29CostingList.setDetailed(true);
		paging_T29CostingList.addEventListener("onPaging", onPaging_T29CostingList());
	
			
		if (arg.containsKey("ModuleMainController")) {
			setT29CostingMainCtrl((T29CostingMainCtrl) arg.get("ModuleMainController"));
		
		    getT29CostingMainCtrl().setT29CostingListCtrl(this);
		}
        
		
    }    
    
	private EventListener onPaging_T29CostingList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT29CostingList = pageNo * paging_T29CostingList.getPageSize();
		         setModelT29CostingList();
			}
		};
	}
	
	// Pembagian data untuk paging
	private void setModelT29CostingList() {		
		modelList_T29CostingList.clear();
		
		T29CostingH selectedData = null;
		if (CommonUtils.isNotEmpty(list_T29CostingList)){
			
			int endT29CostingList = 0;
			if(startT29CostingList + paging_T29CostingList.getPageSize() < list_T29CostingList.size()) {
				endT29CostingList = startT29CostingList + paging_T29CostingList.getPageSize();
			} else {
				endT29CostingList = list_T29CostingList.size();
			}
			
			if (startT29CostingList > endT29CostingList) {
				startT29CostingList = 0;
				paging_T29CostingList.setActivePage(0);
			}
			
			modelList_T29CostingList.addAll(list_T29CostingList.subList(startT29CostingList, endT29CostingList));

			paging_T29CostingList.setDetailed(true);
			paging_T29CostingList.setTotalSize(list_T29CostingList.size());
			
			listBoxT29Costing.setModel(modelList_T29CostingList);
			listBoxT29Costing.setSelectedIndex(0);
	
			selectedData = list_T29CostingList.get(startT29CostingList);
		} else {
			paging_T29CostingList.setDetailed(false);
			listBoxT29Costing.setModel(modelList_T29CostingList);
			paging_T29CostingList.setTotalSize(0);
		}
		
		getT29CostingMainCtrl().setSelectedT29Costing(selectedData);
		getSearchDataDetail(selectedData);

	}
	
    public void onCreate$windowT29CostingList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }  

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
 
     
        
        listheader_T29CostingList_TglCosting.setSortAscending(new T29CostingComparator(true, T29CostingComparator.COMPARE_BY_TGLCOSTING));
        listheader_T29CostingList_TglCosting.setSortDescending(new T29CostingComparator(false, T29CostingComparator.COMPARE_BY_TGLCOSTING));        
        
        listheader_T29CostingList_NoCosting.setSortAscending(new T29CostingComparator(true, T29CostingComparator.COMPARE_BY_NOCOSTING));
        listheader_T29CostingList_NoCosting.setSortDescending(new T29CostingComparator(false, T29CostingComparator.COMPARE_BY_NOCOSTING));        
       
        listheader_T29CostingList_Salesman.setSortAscending(new T29CostingComparator(true, T29CostingComparator.COMPARE_BY_SALES));
        listheader_T29CostingList_Salesman.setSortDescending(new T29CostingComparator(false, T29CostingComparator.COMPARE_BY_SALES));        
       
        listheader_T29CostingList_NoBSO.setSortAscending(new T29CostingComparator(true, T29CostingComparator.COMPARE_BY_NOBSO));
        listheader_T29CostingList_NoBSO.setSortDescending(new T29CostingComparator(false, T29CostingComparator.COMPARE_BY_NOBSO));        
             
        listheader_T29CostingList_NoSO.setSortAscending(new T29CostingComparator(true, T29CostingComparator.COMPARE_BY_NOSO));
        listheader_T29CostingList_NoSO.setSortDescending(new T29CostingComparator(false, T29CostingComparator.COMPARE_BY_NOSO));        
       
        listheader_T29CostingList_NoPOCustomer.setSortAscending(new T29CostingComparator(true, T29CostingComparator.COMPARE_BY_NOPOCUSTOMER));
        listheader_T29CostingList_NoPOCustomer.setSortDescending(new T29CostingComparator(false, T29CostingComparator.COMPARE_BY_NOPOCUSTOMER));        
       
        listheader_T29CostingList_Customer.setSortAscending(new T29CostingComparator(true, T29CostingComparator.COMPARE_BY_CUSTOMER));
        listheader_T29CostingList_Customer.setSortDescending(new T29CostingComparator(false, T29CostingComparator.COMPARE_BY_CUSTOMER));        
              
        listheader_T29CostingList_FlagInvoice.setSortAscending(new T29CostingComparator(true, T29CostingComparator.COMPARE_BY_FLAGINVOICE));
        listheader_T29CostingList_FlagInvoice.setSortDescending(new T29CostingComparator(false, T29CostingComparator.COMPARE_BY_FLAGINVOICE));        
        
        listheader_T29CostingList_FlagLunas.setSortAscending(new T29CostingComparator(true, T29CostingComparator.COMPARE_BY_FLAGLUNAS));
        listheader_T29CostingList_FlagLunas.setSortDescending(new T29CostingComparator(false, T29CostingComparator.COMPARE_BY_FLAGLUNAS));        
       
        
        getSearchData();
        
        listBoxT29Costing.setItemRenderer(renderTable());
        
        windowT29CostingList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		Collection<String> filterSales = new HashSet<String>();
		String vUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (vUser.equals("admin") == true){
		} else 
		{
			String vALL = "XXX";
			List<Object[]> vResultFilterUser = selectQueryService.QueryFilterUserCosting(vUser);
			if(CommonUtils.isNotEmpty(vResultFilterUser)){
				for(Object[] aRslt : vResultFilterUser){
					String vTemp = aRslt[0].toString();
					if (vTemp.equals("ALL") == true){
						vALL = vTemp;
						break;					
					} else  {
						filterSales.add(vTemp);
					}				
				}
			}
			
			if (vALL.equals("ALL") == false){
				parameterInput.put("FilterSales", filterSales);
			}
			
			String vRoleUser = selectQueryService.QueryRoleUserCosting(vUser);
			if(CommonUtils.isNotEmpty(vRoleUser)){
				parameterInput.put("flagStatus", vRoleUser);
				
				if (vRoleUser.equals("FINANCE") == true){
					if (cmbPosisi.getSelectedIndex() != 0) {
						String vPosisi = (String) cmbPosisi.getSelectedItem().getValue();
						if (vPosisi.equals("FINANCE 1") == true || vPosisi.equals("FINANCE 2") == true ){
							parameterInput.put("flagStatus", vPosisi);
						} else {
							parameterInput.put("flagStatus", "XXX");
						}
					}
				} else {
					if (cmbPosisi.getSelectedIndex() != 0) {
						String vPosisi = (String) cmbPosisi.getSelectedItem().getValue();
						if (vPosisi.equals(vRoleUser) == true){
							parameterInput.put("flagStatus", vPosisi);
						} else {
							parameterInput.put("flagStatus", "XXX");
						}
					}
				}
			}
		
		}
			
		
	 	if (CommonUtils.isValidDateFormat(txtTglCosting.getValue())) {
			Date tglInv1 = CommonUtils.convertStringToDate(txtTglCosting.getValue());
			Date tglInv2 = CommonUtils.createSecondParameterForSearch(txtTglCosting.getValue());
			parameterInput.put("tglCostingfrom", tglInv1);
			parameterInput.put("tglCostingto", tglInv2);
		}
	 	
	 	
		if (CommonUtils.isNotEmpty(txtNoCosting.getValue())) {
			parameterInput.put("noCosting", txtNoCosting.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtSales.getValue())) {
			parameterInput.put("salesman", txtSales.getValue());
		}

		if (CommonUtils.isNotEmpty(txtNoBSO.getValue())) {
			parameterInput.put("noBso", txtNoBSO.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtNoSO.getValue())) {
			parameterInput.put("noSo", txtNoSO.getValue());
		}

		if (CommonUtils.isNotEmpty(txtNoPOCustomer.getValue())) {
			parameterInput.put("noPoCustomer", txtNoPOCustomer.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtCustomer.getValue())) {
			parameterInput.put("customer", txtCustomer.getValue());
		}
		
		
		if (cmbFlagInvoice.getSelectedIndex() != 0) {
			parameterInput.put("flagInvoice", (String) cmbFlagInvoice.getSelectedItem().getValue());
		}
		
		if (cmbFlagLunas.getSelectedIndex() != 0) {
			parameterInput.put("flagLunas", (String) cmbFlagLunas.getSelectedItem().getValue());
		}
		
		
		list_T29CostingList.clear();
		
		List<T29CostingH> tempListT29Costing = getT29CostingMainCtrl().getT29CostingHService().getListT29CostingH(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT29Costing)) {
			list_T29CostingList.addAll(tempListT29Costing);
			startT29CostingList = 0;
			paging_T29CostingList.setActivePage(0);
		}

		
		
		setModelT29CostingList();
	}

  
	public void getSearchDataDetail(T29CostingH t29CostingH) {
		list_T30CostingDHw3psList.clear();
		list_T31CostingDAcspsList.clear();
		list_T32CostingDOwnswList.clear();
		list_T33CostingDOtherList.clear();
		list_T34CostingDPaymentList.clear();
		
		if (t29CostingH != null) {
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();		
			
			if (CommonUtils.isNotEmpty(t29CostingH.getT29Id())) {
				parameterInputDetail.put("t29Id", t29CostingH.getT29Id());
			}
			
			
			List<T30CostingDHw3ps> tempT30CostingDHw3ps = getT29CostingMainCtrl().getT29CostingHService().getListT30CostingDHw3ps(parameterInputDetail); 
			if (CommonUtils.isNotEmpty(tempT30CostingDHw3ps)) {
				list_T30CostingDHw3psList.addAll(tempT30CostingDHw3ps);
				//startT30CostingDHw3psList = 0;
				
				//paging_T30CostingDHw3psList.setActivePage(0);
			}
			
			List<T31CostingDAcsps> tempT31CostingDAcsps = getT29CostingMainCtrl().getT29CostingHService().getListT31CostingDAcsps(parameterInputDetail); 
			if (CommonUtils.isNotEmpty(tempT31CostingDAcsps)) {
				list_T31CostingDAcspsList.addAll(tempT31CostingDAcsps);
				//startT30CostingDHw3psList = 0;
				
				//paging_T30CostingDHw3psList.setActivePage(0);
			}
			
			List<T32CostingDOwnsw> tempT32CostingDOwnsw = getT29CostingMainCtrl().getT29CostingHService().getListT32CostingDOwnsw(parameterInputDetail); 
			if (CommonUtils.isNotEmpty(tempT32CostingDOwnsw)) {
				list_T32CostingDOwnswList.addAll(tempT32CostingDOwnsw);
				//startT30CostingDHw3psList = 0;
				
				//paging_T30CostingDHw3psList.setActivePage(0);
			}
			
			List<T33CostingDOther> tempT33CostingDOther = getT29CostingMainCtrl().getT29CostingHService().getListT33CostingDOther(parameterInputDetail); 
			if (CommonUtils.isNotEmpty(tempT33CostingDOther)) {
				list_T33CostingDOtherList.addAll(tempT33CostingDOther);
				//startT30CostingDHw3psList = 0;
				
				//paging_T30CostingDHw3psList.setActivePage(0);
			}
			
			
			List<T34CostingDPayment> tempT34CostingDPayment = getT29CostingMainCtrl().getT29CostingHService().getListT34CostingDPayment(parameterInputDetail); 
			if (CommonUtils.isNotEmpty(tempT34CostingDPayment)) {
				list_T34CostingDPaymentList.addAll(tempT34CostingDPayment);
				//startT30CostingDHw3psList = 0;
				
				//paging_T30CostingDHw3psList.setActivePage(0);
			}
			
		}
		
		//setModelT30CostingDHw3psList();
	}
	
	
	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T29CostingH rec = (T29CostingH) data;

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
                info.setParent(windowT29CostingList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTglCosting()));
				lc.setParent(item);	                
				
                lc = new Listcell(rec.getNoCosting());
                lc.setParent(item);
                
                lc = new Listcell(rec.getSalesman());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoBso());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoSo());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoPoCustomer());
                lc.setParent(item);
             
                lc = new Listcell(rec.getCustomer());
                lc.setParent(item);
      
                lc = new Listcell(rec.getFlagStatus());
                lc.setParent(item);
                
                String vFlagInvoice = rec.getFlagInvoice().equals("Y")?"Sudah":"Belum";
                lc = new Listcell(vFlagInvoice);
                lc.setParent(item); 
                
                String vFlagLunas = rec.getFlagLunas().equals("Y")?"Sudah":"Belum";
                lc = new Listcell(vFlagLunas);
                lc.setParent(item); 
	                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT29CostingItem");
              }
        };
    }
    
  

    public void onDoubleClickedT29CostingItem(Event event) {
//    	if (getSelectedT29Costing() != null) {
//			Events.sendEvent(new Event("onSelect", getT29CostingMainCtrl().tabT29CostingDetail, getSelectedT29Costing()));
//		}
		
		if (listBoxT29Costing.getSelectedItem() != null) {
			T29CostingH selectedData = (T29CostingH) listBoxT29Costing.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT29Costing(selectedData);
	
	            if (getT29CostingMainCtrl().getT29CostingDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT29CostingMainCtrl().tabT29CostingDetail, null));
	            } else if (getT29CostingMainCtrl().getT29CostingDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT29CostingMainCtrl().tabT29CostingDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT29CostingMainCtrl().tabT29CostingDetail, selectedData));
	            
	            getT29CostingMainCtrl().getT29CostingDetailCtrl().doRenderMode("View", "NA"); 
			}
		}
    }


    public void onSelect$listBoxT29Costing(Event event) {
    	if (listBoxT29Costing.getSelectedItem() != null) {
			T29CostingH selectedData = (T29CostingH) listBoxT29Costing.getSelectedItem().getValue();
			if (selectedData != null) {
				
				if (getT29CostingMainCtrl().getT29CostingDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT29CostingMainCtrl().tabT29CostingDetail, null));
		
				} else if (getT29CostingMainCtrl().getT29CostingDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT29CostingMainCtrl().tabT29CostingDetail, null));
				}
				
				getT29CostingMainCtrl().setSelectedT29Costing(selectedData);
				getSearchDataDetail(selectedData);
			}
		}	
    }
    
    public void doFitSize() {
		windowT29CostingList.invalidate();
	}
    
    
   
    
    
    public void onSort$listheader_T29CostingList_TglCosting(Event event) { 
    	sortingData(listheader_T29CostingList_TglCosting, T29CostingComparator.COMPARE_BY_TGLCOSTING);
    }
    
    public void onSort$listheader_T29CostingList_NoCosting(Event event) { 
    	sortingData(listheader_T29CostingList_NoCosting, T29CostingComparator.COMPARE_BY_NOCOSTING);
    }
    
    public void onSort$listheader_T29CostingList_Salesman(Event event) { 
    	sortingData(listheader_T29CostingList_Salesman, T29CostingComparator.COMPARE_BY_SALES);
    }
    
    public void onSort$listheader_T29CostingList_NoBSO(Event event) { 
    	sortingData(listheader_T29CostingList_NoBSO, T29CostingComparator.COMPARE_BY_NOBSO);
    }
    
    public void onSort$listheader_T29CostingList_NoSO(Event event) { 
    	sortingData(listheader_T29CostingList_NoSO, T29CostingComparator.COMPARE_BY_NOSO);
    }
    
    public void onSort$listheader_T29CostingList_NoPOCustomer(Event event) { 
    	sortingData(listheader_T29CostingList_NoPOCustomer, T29CostingComparator.COMPARE_BY_NOPOCUSTOMER);
    }
    
    public void onSort$listheader_T29CostingList_Customer(Event event) { 
    	sortingData(listheader_T29CostingList_Customer, T29CostingComparator.COMPARE_BY_CUSTOMER);
    }
    
    public void onSort$listheader_T29CostingList_FlagInvoice(Event event) { 
    	sortingData(listheader_T29CostingList_FlagInvoice, T29CostingComparator.COMPARE_BY_FLAGINVOICE);
    }
    
    public void onSort$listheader_T29CostingList_FlagLunas(Event event) { 
    	sortingData(listheader_T29CostingList_FlagLunas, T29CostingComparator.COMPARE_BY_FLAGLUNAS);
    }
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T29CostingList, new T29CostingComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T29CostingList, new T29CostingComparator(true, sortBy));
    		}
    	}
    	
    	setModelT29CostingList();    
    }
    
    
    

    public void clearSearchBox(){
        // empty the text search boxes

    	txtTglCosting.setValue(null);
    	txtNoCosting.setValue(null);
    	txtSales.setValue(null);
    	txtNoBSO.setValue(null);
    	txtNoSO.setValue(null);
    	txtNoPOCustomer.setValue(null);
    	txtCustomer.setValue(null);

    
    	cmbPosisi.setSelectedIndex(0);
    	cmbFlagInvoice.setSelectedIndex(0);
    	cmbFlagLunas.setSelectedIndex(0);
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


    public void setSelectedT29Costing(T29CostingH selectedT29Costing) {
        getT29CostingMainCtrl().setSelectedT29Costing(selectedT29Costing);
    }

    public T29CostingH getSelectedT29Costing() {
        return getT29CostingMainCtrl().getSelectedT29Costing();
    }

    public void setT29CostingMainCtrl(T29CostingMainCtrl T29CostingMainCtrl) {
        this.T29CostingMainCtrl = T29CostingMainCtrl;
    }

    public T29CostingMainCtrl getT29CostingMainCtrl() {
        return this.T29CostingMainCtrl;
    }

	public List<T30CostingDHw3ps> getList_T30CostingDHw3psList() {
		return list_T30CostingDHw3psList;
	}

	public void setList_T30CostingDHw3psList(
			List<T30CostingDHw3ps> list_T30CostingDHw3psList) {
		this.list_T30CostingDHw3psList = list_T30CostingDHw3psList;
	}

	public List<T31CostingDAcsps> getList_T31CostingDAcspsList() {
		return list_T31CostingDAcspsList;
	}

	public void setList_T31CostingDAcspsList(
			List<T31CostingDAcsps> list_T31CostingDAcspsList) {
		this.list_T31CostingDAcspsList = list_T31CostingDAcspsList;
	}

	public List<T32CostingDOwnsw> getList_T32CostingDOwnswList() {
		return list_T32CostingDOwnswList;
	}

	public void setList_T32CostingDOwnswList(
			List<T32CostingDOwnsw> list_T32CostingDOwnswList) {
		this.list_T32CostingDOwnswList = list_T32CostingDOwnswList;
	}

	public List<T33CostingDOther> getList_T33CostingDOtherList() {
		return list_T33CostingDOtherList;
	}

	public void setList_T33CostingDOtherList(
			List<T33CostingDOther> list_T33CostingDOtherList) {
		this.list_T33CostingDOtherList = list_T33CostingDOtherList;
	}

	public List<T34CostingDPayment> getList_T34CostingDPaymentList() {
		return list_T34CostingDPaymentList;
	}

	public void setList_T34CostingDPaymentList(
			List<T34CostingDPayment> list_T34CostingDPaymentList) {
		this.list_T34CostingDPaymentList = list_T34CostingDPaymentList;
	}
    
	


}
