package solusi.hapis.webui.tabel.T03salesperson;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
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

import solusi.hapis.backend.tabel.model.T03salesperson;
import solusi.hapis.backend.tabel.model.T08targetsales;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 16-03-2018
 */

public class T03salespersonListCtrl extends GFCBaseListCtrl<T03salesperson> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT03salespersonList; 
    
    protected Paging paging_T03salespersonList;
    protected Listbox listBoxT03salesperson;
    private int startT03salespersonList;
   	private List<T03salesperson> list_T03salespersonList = new ArrayList<T03salesperson>();
    private ListModelList modelList_T03salespersonList = new ListModelList();
     
    // Databinding
    private AnnotateDataBinder binder;
    private T03salespersonMainCtrl T03salespersonMainCtrl;

    // List Header
    protected Listheader listheader_T03salespersonList_Sales;
    protected Listheader listheader_T03salespersonList_Name;
    protected Listheader listheader_T03salespersonList_Nik;
    protected Listheader listheader_T03salespersonList_Spv;
    protected Listheader listheader_T03salespersonList_Target;
    protected Listheader listheader_T03salespersonList_PeriodeResign;
 
    // Search Box Components 
 	protected Textbox txtSales;
 	protected Textbox txtSalesName;
 	protected Textbox txtNIK;
 	protected Textbox txtSPV;
 	protected Textbox txtTarget;
 	protected Textbox txtPeriodeResign;

 	// Paging and list Detail 
 	protected Listheader listheader_T03salespersonDetailList_Tahun;
 	protected Listheader listheader_T03salespersonDetailList_Target;
 	
    
 	protected Paging paging_T03salespersonDetailList;
	private int startT03salespersonDetailList;
	private List<T08targetsales> list_T03salespersonDetailList = new ArrayList<T08targetsales>();
	private ListModelList modelList_T03salespersonDetailList = new ListModelList();
	
	protected Listbox listBoxT03salespersonDetail;
     
   
     
    
    public T03salespersonListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T03salespersonList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T03salespersonList.setDetailed(true);
		paging_T03salespersonList.addEventListener("onPaging", onPaging_T03salespersonList());
	
		paging_T03salespersonDetailList.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_T03salespersonDetailList.setDetailed(true);
		paging_T03salespersonDetailList.addEventListener("onPaging", onPaging_T03salespersonDetailList());
		listBoxT03salespersonDetail.setItemRenderer(renderTableDetail());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT03salespersonMainCtrl((T03salespersonMainCtrl) arg.get("ModuleMainController"));
		
		    getT03salespersonMainCtrl().setT03salespersonListCtrl(this);
		}
        
		
    }    
    
	private EventListener onPaging_T03salespersonList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT03salespersonList = pageNo * paging_T03salespersonList.getPageSize();
		         setModelT03salespersonList();
			}
		};
	}

	private EventListener onPaging_T03salespersonDetailList() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startT03salespersonDetailList = pageNo
						* paging_T03salespersonDetailList.getPageSize();
				setModelT03salespersonDetailList();
			}
		};
	}
	
	private void setModelT03salespersonDetailList() {
		modelList_T03salespersonDetailList.clear();

		if (CommonUtils.isNotEmpty(list_T03salespersonDetailList)) {

			int endUserDetailCabList = 0;
			if (startT03salespersonDetailList
					+ paging_T03salespersonDetailList.getPageSize() < list_T03salespersonDetailList
						.size()) {
				endUserDetailCabList = startT03salespersonDetailList
						+ paging_T03salespersonDetailList.getPageSize();
			} else {
				endUserDetailCabList = list_T03salespersonDetailList.size();
			}

			if (startT03salespersonDetailList > endUserDetailCabList) {
				startT03salespersonDetailList = 0;
				paging_T03salespersonDetailList.setActivePage(0);
			}

			modelList_T03salespersonDetailList.addAll(list_T03salespersonDetailList.subList(
					startT03salespersonDetailList, endUserDetailCabList));

			paging_T03salespersonDetailList.setDetailed(true);
			paging_T03salespersonDetailList.setTotalSize(list_T03salespersonDetailList
					.size());

			listBoxT03salespersonDetail.setModel(modelList_T03salespersonDetailList);
			listBoxT03salespersonDetail.setSelectedIndex(0);

		} else {
			paging_T03salespersonDetailList.setDetailed(false);
			listBoxT03salespersonDetail.setModel(modelList_T03salespersonDetailList);
			paging_T03salespersonDetailList.setTotalSize(0);
		}

	}
	
	// Pembagian data untuk paging
	private void setModelT03salespersonList() {		
		modelList_T03salespersonList.clear();
		
		T03salesperson selectedData = null;
		if (CommonUtils.isNotEmpty(list_T03salespersonList)){
			
			int endT03salespersonList = 0;
			if(startT03salespersonList + paging_T03salespersonList.getPageSize() < list_T03salespersonList.size()) {
				endT03salespersonList = startT03salespersonList + paging_T03salespersonList.getPageSize();
			} else {
				endT03salespersonList = list_T03salespersonList.size();
			}
			
			if (startT03salespersonList > endT03salespersonList) {
				startT03salespersonList = 0;
				paging_T03salespersonList.setActivePage(0);
			}
			
			modelList_T03salespersonList.addAll(list_T03salespersonList.subList(startT03salespersonList, endT03salespersonList));

			paging_T03salespersonList.setDetailed(true);
			paging_T03salespersonList.setTotalSize(list_T03salespersonList.size());
			
			listBoxT03salesperson.setModel(modelList_T03salespersonList);
			listBoxT03salesperson.setSelectedIndex(0);
	
			selectedData = list_T03salespersonList.get(startT03salespersonList);
		} else {
			paging_T03salespersonList.setDetailed(false);
			listBoxT03salesperson.setModel(modelList_T03salespersonList);
			paging_T03salespersonList.setTotalSize(0);
		}
		
		getT03salespersonMainCtrl().setSelectedT03salesperson(selectedData);
		getSearchDataDetail(selectedData);

	}
	
    public void onCreate$windowT03salespersonList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }  

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
 
      
        listheader_T03salespersonList_Sales.setSortAscending(new T03salespersonComparator(true, T03salespersonComparator.COMPARE_BY_SALES));
        listheader_T03salespersonList_Sales.setSortDescending(new T03salespersonComparator(false, T03salespersonComparator.COMPARE_BY_SALES));        
        
        listheader_T03salespersonList_Name.setSortAscending(new T03salespersonComparator(true, T03salespersonComparator.COMPARE_BY_SALESNAME));
        listheader_T03salespersonList_Name.setSortDescending(new T03salespersonComparator(false, T03salespersonComparator.COMPARE_BY_SALESNAME));        
       
        listheader_T03salespersonList_Nik.setSortAscending(new T03salespersonComparator(true, T03salespersonComparator.COMPARE_BY_NIK));
        listheader_T03salespersonList_Nik.setSortDescending(new T03salespersonComparator(false, T03salespersonComparator.COMPARE_BY_NIK));        
       
        listheader_T03salespersonList_Spv.setSortAscending(new T03salespersonComparator(true, T03salespersonComparator.COMPARE_BY_SPV));
        listheader_T03salespersonList_Spv.setSortDescending(new T03salespersonComparator(false, T03salespersonComparator.COMPARE_BY_SPV));        
       
        listheader_T03salespersonList_Target.setSortAscending(new T03salespersonComparator(true, T03salespersonComparator.COMPARE_BY_TARGET));
        listheader_T03salespersonList_Target.setSortDescending(new T03salespersonComparator(false, T03salespersonComparator.COMPARE_BY_TARGET));        
       
        
        listheader_T03salespersonList_PeriodeResign.setSortAscending(new T03salespersonComparator(true, T03salespersonComparator.COMPARE_BY_PERIODERESIGN));
        listheader_T03salespersonList_PeriodeResign.setSortDescending(new T03salespersonComparator(false, T03salespersonComparator.COMPARE_BY_PERIODERESIGN));        
       
        // Detail
     	listheader_T03salespersonDetailList_Tahun.setSortAscending(new T08targetsalesComparator(true, T08targetsalesComparator.COMPARE_BY_TAHUN));
     	listheader_T03salespersonDetailList_Tahun.setSortDescending(new T08targetsalesComparator(false, T08targetsalesComparator.COMPARE_BY_TAHUN));        
        
     	listheader_T03salespersonDetailList_Target.setSortAscending(new T08targetsalesComparator(true, T08targetsalesComparator.COMPARE_BY_TARGET));
     	listheader_T03salespersonDetailList_Target.setSortDescending(new T08targetsalesComparator(false, T08targetsalesComparator.COMPARE_BY_TARGET));        
      
        
        getSearchData();
        
        listBoxT03salesperson.setItemRenderer(renderTable());
        
        windowT03salespersonList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (CommonUtils.isNotEmpty(txtSales.getValue())) {
			parameterInput.put("sales", txtSales.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtSalesName.getValue())) {
			parameterInput.put("salesName", txtSalesName.getValue());
		}

		if (CommonUtils.isNotEmpty(txtNIK.getValue())) {
			parameterInput.put("nik", txtNIK.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtSPV.getValue())) {
			parameterInput.put("spv", txtSPV.getValue());
		}

		if (CommonUtils.isNotEmpty(txtTarget.getValue())) {
			parameterInput.put("target", new BigDecimal(txtTarget.getValue()));
		}
		
		if (CommonUtils.isValidDateFormat(txtPeriodeResign.getValue())) {
			Date tglInv1 = CommonUtils.convertStringToDate(txtPeriodeResign.getValue());
			Date tglInv2 = CommonUtils.createSecondParameterForSearch(txtPeriodeResign.getValue());
			parameterInput.put("periodeResignfrom", tglInv1);
			parameterInput.put("periodeResignto", tglInv2);
		}
		
		list_T03salespersonList.clear();
		
		List<T03salesperson> tempListT03salesperson = getT03salespersonMainCtrl().getT03salespersonService().getListT03salesperson(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT03salesperson)) {
			list_T03salespersonList.addAll(tempListT03salesperson);
			startT03salespersonList = 0;
			paging_T03salespersonList.setActivePage(0);
		}

		setModelT03salespersonList();
	}

  
	public void getSearchDataDetail(T03salesperson t03salesperson) {
		list_T03salespersonDetailList.clear();
		
		if (t03salesperson != null) {
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();		
			
			if (CommonUtils.isNotEmpty(t03salesperson.getT03Id())) {
				parameterInputDetail.put("t03Id", t03salesperson.getT03Id());
			}
			
			
			List<T08targetsales> tempT08targetsales = getT03salespersonMainCtrl().getT03salespersonService().getListT08targetsales(parameterInputDetail); 
			if (CommonUtils.isNotEmpty(tempT08targetsales)) {
				list_T03salespersonDetailList.addAll(tempT08targetsales);
				startT03salespersonDetailList = 0;
				
				paging_T03salespersonDetailList.setActivePage(0);
			}
			
		}
		
		setModelT03salespersonDetailList();
	}
	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T03salesperson rec = (T03salesperson) data;

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
                info.setParent(windowT03salespersonList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(rec.getSales());
                lc.setParent(item);
                
                lc = new Listcell(rec.getSalesName());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNik());
                lc.setParent(item);
                
                lc = new Listcell(rec.getSpv());
                lc.setParent(item);
             
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getTarget(), "#,###,###.##"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
      
				lc = new Listcell(CommonUtils.convertDateToString(rec.getPeriodeResign()));
				lc.setParent(item);
	                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT03salespersonItem");
              }
        };
    }
    
    private ListitemRenderer renderTableDetail() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final T08targetsales rec = (T08targetsales) data;

				Listcell lc;

				lc = new Listcell(rec.getTahun());
				lc.setParent(item);

								
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getTarget(),"#,###,###.###"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}

    public void onDoubleClickedT03salespersonItem(Event event) {
//    	if (getSelectedT03salesperson() != null) {
//			Events.sendEvent(new Event("onSelect", getT03salespersonMainCtrl().tabT03salespersonDetail, getSelectedT03salesperson()));
//		}
		
		if (listBoxT03salesperson.getSelectedItem() != null) {
			T03salesperson selectedData = (T03salesperson) listBoxT03salesperson.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT03salesperson(selectedData);
	
	            if (getT03salespersonMainCtrl().getT03salespersonDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT03salespersonMainCtrl().tabT03salespersonDetail, null));
	            } else if (getT03salespersonMainCtrl().getT03salespersonDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT03salespersonMainCtrl().tabT03salespersonDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT03salespersonMainCtrl().tabT03salespersonDetail, selectedData));
	            
	            getT03salespersonMainCtrl().getT03salespersonDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT03salesperson(Event event) {
    	if (listBoxT03salesperson.getSelectedItem() != null) {
			T03salesperson selectedData = (T03salesperson) listBoxT03salesperson.getSelectedItem().getValue();
			if (selectedData != null) {
				
				if (getT03salespersonMainCtrl().getT03salespersonDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT03salespersonMainCtrl().tabT03salespersonDetail, null));
		
				} else if (getT03salespersonMainCtrl().getT03salespersonDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT03salespersonMainCtrl().tabT03salespersonDetail, null));
				}
				
				getT03salespersonMainCtrl().setSelectedT03salesperson(selectedData);
				getSearchDataDetail(selectedData);
				
			}
		}	
    }
    
    public void doFitSize() {
		windowT03salespersonList.invalidate();
	}
    
    
    
    
    
    
    public void onSort$listheader_T03salespersonList_Sales(Event event) { 
    	sortingData(listheader_T03salespersonList_Sales, T03salespersonComparator.COMPARE_BY_SALES);
    }
    
    public void onSort$listheader_T03salespersonList_Name(Event event) { 
    	sortingData(listheader_T03salespersonList_Name, T03salespersonComparator.COMPARE_BY_SALESNAME);
    }
    
    public void onSort$listheader_T03salespersonList_Nik(Event event) { 
    	sortingData(listheader_T03salespersonList_Nik, T03salespersonComparator.COMPARE_BY_NIK);
    }
    
    public void onSort$listheader_T03salespersonList_Spv(Event event) { 
    	sortingData(listheader_T03salespersonList_Spv, T03salespersonComparator.COMPARE_BY_SPV);
    }
    
    public void onSort$listheader_T03salespersonList_Target(Event event) { 
    	sortingData(listheader_T03salespersonList_Target, T03salespersonComparator.COMPARE_BY_TARGET);
    }
    
    public void onSort$listheader_T03salespersonList_PeriodeResign(Event event) { 
    	sortingData(listheader_T03salespersonList_PeriodeResign, T03salespersonComparator.COMPARE_BY_PERIODERESIGN);
    }
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T03salespersonList, new T03salespersonComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T03salespersonList, new T03salespersonComparator(true, sortBy));
    		}
    	}
    	
    	setModelT03salespersonList();    
    }
    
    //=============================================================================================================================================
    
 	
    public void onSort$listheader_T03salespersonDetailList_Tahun(Event event) {
    	sortingDataDetail(listheader_T03salespersonDetailList_Tahun, T08targetsalesComparator.COMPARE_BY_TAHUN);
    }

    
    public void onSort$listheader_T03salespersonDetailList_Target(Event event) {
    	sortingDataDetail(listheader_T03salespersonDetailList_Target, T08targetsalesComparator.COMPARE_BY_TARGET);
    }

    private void sortingDataDetail(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T03salespersonDetailList, new T08targetsalesComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T03salespersonDetailList, new T08targetsalesComparator(true, sortBy));
    		}
    	}
    	
    	setModelT03salespersonDetailList();    
    }
    
    
    

    public void clearSearchBox(){
        // empty the text search boxes

    	txtSales.setValue(null);
    	txtSalesName.setValue(null);
    	txtNIK.setValue(null);
    	txtSPV.setValue(null);
    	txtTarget.setValue(null);
    	txtPeriodeResign.setValue(null);

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


    public void setSelectedT03salesperson(T03salesperson selectedT03salesperson) {
        getT03salespersonMainCtrl().setSelectedT03salesperson(selectedT03salesperson);
    }

    public T03salesperson getSelectedT03salesperson() {
        return getT03salespersonMainCtrl().getSelectedT03salesperson();
    }

    public void setT03salespersonMainCtrl(T03salespersonMainCtrl T03salespersonMainCtrl) {
        this.T03salespersonMainCtrl = T03salespersonMainCtrl;
    }

    public T03salespersonMainCtrl getT03salespersonMainCtrl() {
        return this.T03salespersonMainCtrl;
    }
    
    public List<T08targetsales> getList_T03salespersonDetailList() {
		return list_T03salespersonDetailList;
	}

	public void setList_T03salespersonDetailList(List<T08targetsales> list_T03salespersonDetailList) {
		this.list_T03salespersonDetailList = list_T03salespersonDetailList;
	}


}
