package solusi.hapis.webui.finance.M02Salesperson;


import java.io.Serializable;
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

import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.backend.navbi.model.M03Targetsales;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 14-04-2018
 */

public class M02SalespersonListCtrl extends GFCBaseListCtrl<M02Salesperson> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowM02SalespersonList; 
    
    protected Paging paging_M02SalespersonList;
    protected Listbox listBoxM02Salesperson;
    private int startM02SalespersonList;
   	private List<M02Salesperson> list_M02SalespersonList = new ArrayList<M02Salesperson>();
    private ListModelList modelList_M02SalespersonList = new ListModelList();
     
    // Databinding
    private AnnotateDataBinder binder;
    private M02SalespersonMainCtrl M02SalespersonMainCtrl;

    // List Header
    protected Listheader listheader_M02SalespersonList_Sales;
    protected Listheader listheader_M02SalespersonList_Name;
    protected Listheader listheader_M02SalespersonList_Nik;
    protected Listheader listheader_M02SalespersonList_Spv;
    protected Listheader listheader_M02SalespersonList_PeriodeResign;
 
    // Search Box Components 
 	protected Textbox txtSales;
 	protected Textbox txtSalesName;
 	protected Textbox txtNIK;
 	protected Textbox txtSPV;
 	protected Textbox txtPeriodeResign;

 	// Paging and list Detail 
 	protected Listheader listheader_M02SalespersonDetailList_Tahun;
 	protected Listheader listheader_M02SalespersonDetailList_Target;
 	
    
 	protected Paging paging_M02SalespersonDetailList;
	private int startM02SalespersonDetailList;
	private List<M03Targetsales> list_M02SalespersonDetailList = new ArrayList<M03Targetsales>();
	private ListModelList modelList_M02SalespersonDetailList = new ListModelList();
	
	protected Listbox listBoxM02SalespersonDetail;
     
   
     
    
    public M02SalespersonListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_M02SalespersonList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_M02SalespersonList.setDetailed(true);
		paging_M02SalespersonList.addEventListener("onPaging", onPaging_M02SalespersonList());
	
		paging_M02SalespersonDetailList.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_M02SalespersonDetailList.setDetailed(true);
		paging_M02SalespersonDetailList.addEventListener("onPaging", onPaging_M02SalespersonDetailList());
		listBoxM02SalespersonDetail.setItemRenderer(renderTableDetail());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setM02SalespersonMainCtrl((M02SalespersonMainCtrl) arg.get("ModuleMainController"));
		
		    getM02SalespersonMainCtrl().setM02SalespersonListCtrl(this);
		}
        
		
    }    
    
	private EventListener onPaging_M02SalespersonList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startM02SalespersonList = pageNo * paging_M02SalespersonList.getPageSize();
		         setModelM02SalespersonList();
			}
		};
	}

	private EventListener onPaging_M02SalespersonDetailList() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startM02SalespersonDetailList = pageNo
						* paging_M02SalespersonDetailList.getPageSize();
				setModelM02SalespersonDetailList();
			}
		};
	}
	
	private void setModelM02SalespersonDetailList() {
		modelList_M02SalespersonDetailList.clear();

		if (CommonUtils.isNotEmpty(list_M02SalespersonDetailList)) {

			int endUserDetailCabList = 0;
			if (startM02SalespersonDetailList
					+ paging_M02SalespersonDetailList.getPageSize() < list_M02SalespersonDetailList
						.size()) {
				endUserDetailCabList = startM02SalespersonDetailList
						+ paging_M02SalespersonDetailList.getPageSize();
			} else {
				endUserDetailCabList = list_M02SalespersonDetailList.size();
			}

			if (startM02SalespersonDetailList > endUserDetailCabList) {
				startM02SalespersonDetailList = 0;
				paging_M02SalespersonDetailList.setActivePage(0);
			}

			modelList_M02SalespersonDetailList.addAll(list_M02SalespersonDetailList.subList(
					startM02SalespersonDetailList, endUserDetailCabList));

			paging_M02SalespersonDetailList.setDetailed(true);
			paging_M02SalespersonDetailList.setTotalSize(list_M02SalespersonDetailList
					.size());

			listBoxM02SalespersonDetail.setModel(modelList_M02SalespersonDetailList);
			listBoxM02SalespersonDetail.setSelectedIndex(0);

		} else {
			paging_M02SalespersonDetailList.setDetailed(false);
			listBoxM02SalespersonDetail.setModel(modelList_M02SalespersonDetailList);
			paging_M02SalespersonDetailList.setTotalSize(0);
		}

	}
	
	// Pembagian data untuk paging
	private void setModelM02SalespersonList() {		
		modelList_M02SalespersonList.clear();
		
		M02Salesperson selectedData = null;
		if (CommonUtils.isNotEmpty(list_M02SalespersonList)){
			
			int endM02SalespersonList = 0;
			if(startM02SalespersonList + paging_M02SalespersonList.getPageSize() < list_M02SalespersonList.size()) {
				endM02SalespersonList = startM02SalespersonList + paging_M02SalespersonList.getPageSize();
			} else {
				endM02SalespersonList = list_M02SalespersonList.size();
			}
			
			if (startM02SalespersonList > endM02SalespersonList) {
				startM02SalespersonList = 0;
				paging_M02SalespersonList.setActivePage(0);
			}
			
			modelList_M02SalespersonList.addAll(list_M02SalespersonList.subList(startM02SalespersonList, endM02SalespersonList));

			paging_M02SalespersonList.setDetailed(true);
			paging_M02SalespersonList.setTotalSize(list_M02SalespersonList.size());
			
			listBoxM02Salesperson.setModel(modelList_M02SalespersonList);
			listBoxM02Salesperson.setSelectedIndex(0);
	
			selectedData = list_M02SalespersonList.get(startM02SalespersonList);
		} else {
			paging_M02SalespersonList.setDetailed(false);
			listBoxM02Salesperson.setModel(modelList_M02SalespersonList);
			paging_M02SalespersonList.setTotalSize(0);
		}
		
		getM02SalespersonMainCtrl().setSelectedM02Salesperson(selectedData);
		getSearchDataDetail(selectedData);

	}
	
    public void onCreate$windowM02SalespersonList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }  

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
 
      
        listheader_M02SalespersonList_Sales.setSortAscending(new M02SalespersonComparator(true, M02SalespersonComparator.COMPARE_BY_SALES));
        listheader_M02SalespersonList_Sales.setSortDescending(new M02SalespersonComparator(false, M02SalespersonComparator.COMPARE_BY_SALES));        
        
        listheader_M02SalespersonList_Name.setSortAscending(new M02SalespersonComparator(true, M02SalespersonComparator.COMPARE_BY_SALESNAME));
        listheader_M02SalespersonList_Name.setSortDescending(new M02SalespersonComparator(false, M02SalespersonComparator.COMPARE_BY_SALESNAME));        
       
        listheader_M02SalespersonList_Nik.setSortAscending(new M02SalespersonComparator(true, M02SalespersonComparator.COMPARE_BY_NIK));
        listheader_M02SalespersonList_Nik.setSortDescending(new M02SalespersonComparator(false, M02SalespersonComparator.COMPARE_BY_NIK));        
       
        listheader_M02SalespersonList_Spv.setSortAscending(new M02SalespersonComparator(true, M02SalespersonComparator.COMPARE_BY_SPV));
        listheader_M02SalespersonList_Spv.setSortDescending(new M02SalespersonComparator(false, M02SalespersonComparator.COMPARE_BY_SPV));        
         
        
        listheader_M02SalespersonList_PeriodeResign.setSortAscending(new M02SalespersonComparator(true, M02SalespersonComparator.COMPARE_BY_PERIODERESIGN));
        listheader_M02SalespersonList_PeriodeResign.setSortDescending(new M02SalespersonComparator(false, M02SalespersonComparator.COMPARE_BY_PERIODERESIGN));        
       
        // Detail
     	listheader_M02SalespersonDetailList_Tahun.setSortAscending(new M03TargetsalesComparator(true, M03TargetsalesComparator.COMPARE_BY_TAHUN));
     	listheader_M02SalespersonDetailList_Tahun.setSortDescending(new M03TargetsalesComparator(false, M03TargetsalesComparator.COMPARE_BY_TAHUN));        
        
     	listheader_M02SalespersonDetailList_Target.setSortAscending(new M03TargetsalesComparator(true, M03TargetsalesComparator.COMPARE_BY_TARGET));
     	listheader_M02SalespersonDetailList_Target.setSortDescending(new M03TargetsalesComparator(false, M03TargetsalesComparator.COMPARE_BY_TARGET));        
      
        
        getSearchData();
        
        listBoxM02Salesperson.setItemRenderer(renderTable());
        
        windowM02SalespersonList.addEventListener(Events.ON_OK, onSubmitForm());
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

		
		if (CommonUtils.isValidDateFormat(txtPeriodeResign.getValue())) {
			Date tglInv1 = CommonUtils.convertStringToDate(txtPeriodeResign.getValue());
			Date tglInv2 = CommonUtils.createSecondParameterForSearch(txtPeriodeResign.getValue());
			parameterInput.put("periodeResignfrom", tglInv1);
			parameterInput.put("periodeResignto", tglInv2);
		}
		
		list_M02SalespersonList.clear();
		
		List<M02Salesperson> tempListM02Salesperson = getM02SalespersonMainCtrl().getM02SalespersonService().getListM02Salesperson(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListM02Salesperson)) {
			list_M02SalespersonList.addAll(tempListM02Salesperson);
			startM02SalespersonList = 0;
			paging_M02SalespersonList.setActivePage(0);
		}

		setModelM02SalespersonList();
	}

  
	public void getSearchDataDetail(M02Salesperson m02Salesperson) {
		list_M02SalespersonDetailList.clear();
		
		if (m02Salesperson != null) {
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();		
			
			if (CommonUtils.isNotEmpty(m02Salesperson.getM02Id())) {
				parameterInputDetail.put("m02Id", m02Salesperson.getM02Id());
			}
			
			
			List<M03Targetsales> tempM03Targetsales = getM02SalespersonMainCtrl().getM02SalespersonService().getListM03Targetsales(parameterInputDetail); 
			if (CommonUtils.isNotEmpty(tempM03Targetsales)) {
				list_M02SalespersonDetailList.addAll(tempM03Targetsales);
				startM02SalespersonDetailList = 0;
				
				paging_M02SalespersonDetailList.setActivePage(0);
			}
			
		}
		
		setModelM02SalespersonDetailList();
	}
	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final M02Salesperson rec = (M02Salesperson) data;

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
                info.setParent(windowM02SalespersonList);
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
             
      
				lc = new Listcell(CommonUtils.convertDateToString(rec.getPeriodeResign()));
				lc.setParent(item);
	                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedM02SalespersonItem");
              }
        };
    }
    
    private ListitemRenderer renderTableDetail() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final M03Targetsales rec = (M03Targetsales) data;

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

    public void onDoubleClickedM02SalespersonItem(Event event) {
//    	if (getSelectedM02Salesperson() != null) {
//			Events.sendEvent(new Event("onSelect", getM02SalespersonMainCtrl().tabM02SalespersonDetail, getSelectedM02Salesperson()));
//		}
		
		if (listBoxM02Salesperson.getSelectedItem() != null) {
			M02Salesperson selectedData = (M02Salesperson) listBoxM02Salesperson.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedM02Salesperson(selectedData);
	
	            if (getM02SalespersonMainCtrl().getM02SalespersonDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getM02SalespersonMainCtrl().tabM02SalespersonDetail, null));
	            } else if (getM02SalespersonMainCtrl().getM02SalespersonDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getM02SalespersonMainCtrl().tabM02SalespersonDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getM02SalespersonMainCtrl().tabM02SalespersonDetail, selectedData));
	            
	            getM02SalespersonMainCtrl().getM02SalespersonDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxM02Salesperson(Event event) {
    	if (listBoxM02Salesperson.getSelectedItem() != null) {
			M02Salesperson selectedData = (M02Salesperson) listBoxM02Salesperson.getSelectedItem().getValue();
			if (selectedData != null) {
				
				if (getM02SalespersonMainCtrl().getM02SalespersonDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getM02SalespersonMainCtrl().tabM02SalespersonDetail, null));
		
				} else if (getM02SalespersonMainCtrl().getM02SalespersonDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getM02SalespersonMainCtrl().tabM02SalespersonDetail, null));
				}
				
				getM02SalespersonMainCtrl().setSelectedM02Salesperson(selectedData);
				getSearchDataDetail(selectedData);
				
			}
		}	
    }
    
    public void doFitSize() {
		windowM02SalespersonList.invalidate();
	}
    
    
    
    
    
    
    public void onSort$listheader_M02SalespersonList_Sales(Event event) { 
    	sortingData(listheader_M02SalespersonList_Sales, M02SalespersonComparator.COMPARE_BY_SALES);
    }
    
    public void onSort$listheader_M02SalespersonList_Name(Event event) { 
    	sortingData(listheader_M02SalespersonList_Name, M02SalespersonComparator.COMPARE_BY_SALESNAME);
    }
    
    public void onSort$listheader_M02SalespersonList_Nik(Event event) { 
    	sortingData(listheader_M02SalespersonList_Nik, M02SalespersonComparator.COMPARE_BY_NIK);
    }
    
    public void onSort$listheader_M02SalespersonList_Spv(Event event) { 
    	sortingData(listheader_M02SalespersonList_Spv, M02SalespersonComparator.COMPARE_BY_SPV);
    }
    
    public void onSort$listheader_M02SalespersonList_PeriodeResign(Event event) { 
    	sortingData(listheader_M02SalespersonList_PeriodeResign, M02SalespersonComparator.COMPARE_BY_PERIODERESIGN);
    }
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_M02SalespersonList, new M02SalespersonComparator(false, sortBy));
    		} else {
    			Collections.sort(list_M02SalespersonList, new M02SalespersonComparator(true, sortBy));
    		}
    	}
    	
    	setModelM02SalespersonList();    
    }
    
    //=============================================================================================================================================
    
 	
    public void onSort$listheader_M02SalespersonDetailList_Tahun(Event event) {
    	sortingDataDetail(listheader_M02SalespersonDetailList_Tahun, M03TargetsalesComparator.COMPARE_BY_TAHUN);
    }

    
    public void onSort$listheader_M02SalespersonDetailList_Target(Event event) {
    	sortingDataDetail(listheader_M02SalespersonDetailList_Target, M03TargetsalesComparator.COMPARE_BY_TARGET);
    }

    private void sortingDataDetail(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_M02SalespersonDetailList, new M03TargetsalesComparator(false, sortBy));
    		} else {
    			Collections.sort(list_M02SalespersonDetailList, new M03TargetsalesComparator(true, sortBy));
    		}
    	}
    	
    	setModelM02SalespersonDetailList();    
    }
    
    
    

    public void clearSearchBox(){
        // empty the text search boxes

    	txtSales.setValue(null);
    	txtSalesName.setValue(null);
    	txtNIK.setValue(null);
    	txtSPV.setValue(null);
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


    public void setSelectedM02Salesperson(M02Salesperson selectedM02Salesperson) {
        getM02SalespersonMainCtrl().setSelectedM02Salesperson(selectedM02Salesperson);
    }

    public M02Salesperson getSelectedM02Salesperson() {
        return getM02SalespersonMainCtrl().getSelectedM02Salesperson();
    }

    public void setM02SalespersonMainCtrl(M02SalespersonMainCtrl M02SalespersonMainCtrl) {
        this.M02SalespersonMainCtrl = M02SalespersonMainCtrl;
    }

    public M02SalespersonMainCtrl getM02SalespersonMainCtrl() {
        return this.M02SalespersonMainCtrl;
    }
    
    public List<M03Targetsales> getList_M02SalespersonDetailList() {
		return list_M02SalespersonDetailList;
	}

	public void setList_M02SalespersonDetailList(List<M03Targetsales> list_M02SalespersonDetailList) {
		this.list_M02SalespersonDetailList = list_M02SalespersonDetailList;
	}


}
