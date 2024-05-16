package solusi.hapis.webui.sales.T21DaftarPenawaranSales;



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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
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

import solusi.hapis.backend.navbi.model.T21DaftarPenawaranSales;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 16-11-2021
 */

public class T21DaftarPenawaranSalesListCtrl extends GFCBaseListCtrl<T21DaftarPenawaranSales> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT21DaftarPenawaranSalesList; 
    protected Panel panelT21DaftarPenawaranSalesList;
 	
	// Search Box Components 
    

	protected Textbox txtSales;
	protected Textbox txtTglPenawaran;
	protected Textbox txtNoPenawaran;
	protected Textbox txtCustomer;
	protected Textbox txtNilai;
	
	protected Combobox cmbCompany;
	protected Combobox cmbCabang;
	protected Combobox cmbStatus;
			
		
	// Paging and list
    protected Borderlayout borderLayout_T21DaftarPenawaranSalesList;
    protected Paging paging_T21DaftarPenawaranSalesList;
    private int startT21DaftarPenawaranSalesList;
	private List<T21DaftarPenawaranSales> list_T21DaftarPenawaranSalesList = new ArrayList<T21DaftarPenawaranSales>();
    private ListModelList modelList_T21DaftarPenawaranSalesList = new ListModelList();
    protected Listbox listBoxT21DaftarPenawaranSales;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T21DaftarPenawaranSalesMainCtrl T21DaftarPenawaranSalesMainCtrl;

    // List Header		
    protected Listheader listheader_T21DaftarPenawaranSalesList_Company;
    protected Listheader listheader_T21DaftarPenawaranSalesList_Cabang;			
    protected Listheader listheader_T21DaftarPenawaranSalesList_Sales;
    protected Listheader listheader_T21DaftarPenawaranSalesList_TglPenarawan;
    protected Listheader listheader_T21DaftarPenawaranSalesList_NoPenawaran;
    protected Listheader listheader_T21DaftarPenawaranSalesList_Customer;
    protected Listheader listheader_T21DaftarPenawaranSalesList_SektorIndustri;
    protected Listheader listheader_T21DaftarPenawaranSalesList_Nilai;
    protected Listheader listheader_T21DaftarPenawaranSalesList_Status;

    
    protected Bandbox  cmbSektorIndustri;
	protected Listbox listSektorIndustri;
	protected String vSektorIndustri = "99999";
	
	private SelectQueryService selectQueryService;
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T21DaftarPenawaranSalesListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T21DaftarPenawaranSalesList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T21DaftarPenawaranSalesList.setDetailed(true);
		paging_T21DaftarPenawaranSalesList.addEventListener("onPaging", onPaging_T21DaftarPenawaranSalesList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT21DaftarPenawaranSalesMainCtrl((T21DaftarPenawaranSalesMainCtrl) arg.get("ModuleMainController"));
		
		    getT21DaftarPenawaranSalesMainCtrl().setT21DaftarPenawaranSalesListCtrl(this);
		}
        
		
		Bandpopup popup2 = new Bandpopup();
			listSektorIndustri = new Listbox();
			listSektorIndustri.setMold("paging");
			listSektorIndustri.setAutopaging(false);
			listSektorIndustri.setWidth("400px");
			listSektorIndustri.addEventListener(Events.ON_SELECT, selectSektorIndustri());
			listSektorIndustri.setParent(popup2);
		popup2.setParent(cmbSektorIndustri);
	        
		listSektorIndustri.appendItem("All", "99999");
		
		List<Object[]> vResultSektorIndustri = selectQueryService.QuerySektorIndustri();
		if(CommonUtils.isNotEmpty(vResultSektorIndustri)){
			for(Object[] aRslt : vResultSektorIndustri){
				listSektorIndustri.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbSektorIndustri.setValue(listSektorIndustri.getItemAtIndex(0).getLabel());
		listSektorIndustri.setSelectedItem(listSektorIndustri.getItemAtIndex(0));
    }    
    
    private EventListener selectSektorIndustri() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbSektorIndustri.setValue(listSektorIndustri.getSelectedItem().getLabel());
				vSektorIndustri = listSektorIndustri.getSelectedItem().getValue().toString();
				cmbSektorIndustri.close();
				
				getSearchData();
			}
		};
	}
    
    public void onCreate$windowT21DaftarPenawaranSalesList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
      
        listheader_T21DaftarPenawaranSalesList_Company.setSortAscending(new T21DaftarPenawaranSalesComparator(true, T21DaftarPenawaranSalesComparator.COMPARE_BY_COMPANY));
        listheader_T21DaftarPenawaranSalesList_Company.setSortDescending(new T21DaftarPenawaranSalesComparator(false, T21DaftarPenawaranSalesComparator.COMPARE_BY_COMPANY));        
         
        listheader_T21DaftarPenawaranSalesList_Cabang.setSortAscending(new T21DaftarPenawaranSalesComparator(true, T21DaftarPenawaranSalesComparator.COMPARE_BY_CABANG));
        listheader_T21DaftarPenawaranSalesList_Cabang.setSortDescending(new T21DaftarPenawaranSalesComparator(false, T21DaftarPenawaranSalesComparator.COMPARE_BY_CABANG));        
       
        listheader_T21DaftarPenawaranSalesList_Sales.setSortAscending(new T21DaftarPenawaranSalesComparator(true, T21DaftarPenawaranSalesComparator.COMPARE_BY_SALES));
        listheader_T21DaftarPenawaranSalesList_Sales.setSortDescending(new T21DaftarPenawaranSalesComparator(false, T21DaftarPenawaranSalesComparator.COMPARE_BY_SALES));        
       
        listheader_T21DaftarPenawaranSalesList_TglPenarawan.setSortAscending(new T21DaftarPenawaranSalesComparator(true, T21DaftarPenawaranSalesComparator.COMPARE_BY_TGLPENAWARAN));
        listheader_T21DaftarPenawaranSalesList_TglPenarawan.setSortDescending(new T21DaftarPenawaranSalesComparator(false, T21DaftarPenawaranSalesComparator.COMPARE_BY_TGLPENAWARAN));        
       
        listheader_T21DaftarPenawaranSalesList_NoPenawaran.setSortAscending(new T21DaftarPenawaranSalesComparator(true, T21DaftarPenawaranSalesComparator.COMPARE_BY_NOPENAWARAN));
        listheader_T21DaftarPenawaranSalesList_NoPenawaran.setSortDescending(new T21DaftarPenawaranSalesComparator(false, T21DaftarPenawaranSalesComparator.COMPARE_BY_NOPENAWARAN));        
       
        listheader_T21DaftarPenawaranSalesList_Customer.setSortAscending(new T21DaftarPenawaranSalesComparator(true, T21DaftarPenawaranSalesComparator.COMPARE_BY_CUSTOMER));
        listheader_T21DaftarPenawaranSalesList_Customer.setSortDescending(new T21DaftarPenawaranSalesComparator(false, T21DaftarPenawaranSalesComparator.COMPARE_BY_CUSTOMER));        
       
        listheader_T21DaftarPenawaranSalesList_SektorIndustri.setSortAscending(new T21DaftarPenawaranSalesComparator(true, T21DaftarPenawaranSalesComparator.COMPARE_BY_SEKTORINDUSTRI));
        listheader_T21DaftarPenawaranSalesList_SektorIndustri.setSortDescending(new T21DaftarPenawaranSalesComparator(false, T21DaftarPenawaranSalesComparator.COMPARE_BY_SEKTORINDUSTRI));        
       
        listheader_T21DaftarPenawaranSalesList_Nilai.setSortAscending(new T21DaftarPenawaranSalesComparator(true, T21DaftarPenawaranSalesComparator.COMPARE_BY_NILAI));
        listheader_T21DaftarPenawaranSalesList_Nilai.setSortDescending(new T21DaftarPenawaranSalesComparator(false, T21DaftarPenawaranSalesComparator.COMPARE_BY_NILAI));        
       
        listheader_T21DaftarPenawaranSalesList_Status.setSortAscending(new T21DaftarPenawaranSalesComparator(true, T21DaftarPenawaranSalesComparator.COMPARE_BY_STATUS));
        listheader_T21DaftarPenawaranSalesList_Status.setSortDescending(new T21DaftarPenawaranSalesComparator(false, T21DaftarPenawaranSalesComparator.COMPARE_BY_STATUS));        
        
        getSearchData();
        
        listBoxT21DaftarPenawaranSales.setItemRenderer(renderTable());
        
        windowT21DaftarPenawaranSalesList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		if (cmbCompany.getSelectedIndex() != 0) {
			parameterInput.put("company", (String) cmbCompany.getSelectedItem().getValue());
		}

		if (cmbCabang.getSelectedIndex() != 0) {
			parameterInput.put("cabang", (String) cmbCabang.getSelectedItem().getValue());
		}		

		if (CommonUtils.isNotEmpty(txtSales.getValue())) {
			parameterInput.put("salesCode", txtSales.getValue());
		}
		
		if (CommonUtils.isValidDateFormat(txtTglPenawaran.getValue())) {
			Date tglFrom = CommonUtils.convertStringToDate(txtTglPenawaran.getValue());
			Date tglTo = CommonUtils.createSecondParameterForSearch(txtTglPenawaran.getValue());
			parameterInput.put("tglPenawaranFrom", tglFrom);
			parameterInput.put("tglPenawaranTo", tglTo);
		}
		
		if (CommonUtils.isNotEmpty(txtNoPenawaran.getValue())) {
			parameterInput.put("noPenawaran", txtNoPenawaran.getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtCustomer.getValue())) {
			parameterInput.put("namaCustomer", txtCustomer.getValue());
		}
		
		if (CommonUtils.isNotEmpty(vSektorIndustri)) {
			
			if (vSektorIndustri.equals("99999") == false){
				parameterInput.put("sektorIndustri", vSektorIndustri);
			}
		}
		
		if (CommonUtils.isNotEmpty(txtNilai.getValue())) {
			parameterInput.put("nilai", txtNilai.getValue());
		}
		
		if (cmbStatus.getSelectedIndex() != 0) {
			parameterInput.put("statusPenawaran", (String) cmbStatus.getSelectedItem().getValue());
		}
		
		
		list_T21DaftarPenawaranSalesList.clear();
		
		List<T21DaftarPenawaranSales> tempListT21DaftarPenawaranSales = getT21DaftarPenawaranSalesMainCtrl().getT21DaftarPenawaranSalesService().getListT21DaftarPenawaranSales(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT21DaftarPenawaranSales)) {
			list_T21DaftarPenawaranSalesList.addAll(tempListT21DaftarPenawaranSales);
			startT21DaftarPenawaranSalesList = 0;
			paging_T21DaftarPenawaranSalesList.setActivePage(0);
		}

		setModelT21DaftarPenawaranSalesList();
	}

	// Pembagian data untuk paging
	private void setModelT21DaftarPenawaranSalesList() {		
		modelList_T21DaftarPenawaranSalesList.clear();
		
		T21DaftarPenawaranSales selectedData = null;
		if (CommonUtils.isNotEmpty(list_T21DaftarPenawaranSalesList)){
			
			int endT21DaftarPenawaranSalesList = 0;
			if(startT21DaftarPenawaranSalesList + paging_T21DaftarPenawaranSalesList.getPageSize() < list_T21DaftarPenawaranSalesList.size()) {
				endT21DaftarPenawaranSalesList = startT21DaftarPenawaranSalesList + paging_T21DaftarPenawaranSalesList.getPageSize();
			} else {
				endT21DaftarPenawaranSalesList = list_T21DaftarPenawaranSalesList.size();
			}
			
			if (startT21DaftarPenawaranSalesList > endT21DaftarPenawaranSalesList) {
				startT21DaftarPenawaranSalesList = 0;
				paging_T21DaftarPenawaranSalesList.setActivePage(0);
			}
			
			modelList_T21DaftarPenawaranSalesList.addAll(list_T21DaftarPenawaranSalesList.subList(startT21DaftarPenawaranSalesList, endT21DaftarPenawaranSalesList));

			paging_T21DaftarPenawaranSalesList.setDetailed(true);
			paging_T21DaftarPenawaranSalesList.setTotalSize(list_T21DaftarPenawaranSalesList.size());
			
			listBoxT21DaftarPenawaranSales.setModel(modelList_T21DaftarPenawaranSalesList);
			listBoxT21DaftarPenawaranSales.setSelectedIndex(0);
	
			selectedData = list_T21DaftarPenawaranSalesList.get(startT21DaftarPenawaranSalesList);
		} else {
			paging_T21DaftarPenawaranSalesList.setDetailed(false);
			listBoxT21DaftarPenawaranSales.setModel(modelList_T21DaftarPenawaranSalesList);
			paging_T21DaftarPenawaranSalesList.setTotalSize(0);
		}
		
		getT21DaftarPenawaranSalesMainCtrl().setSelectedT21DaftarPenawaranSales(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T21DaftarPenawaranSales rec = (T21DaftarPenawaranSales) data;

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
                info.setParent(windowT21DaftarPenawaranSalesList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(rec.getCompany());
                lc.setParent(item);
                
                lc = new Listcell(rec.getCabang());
                lc.setParent(item);
                
                lc = new Listcell(rec.getSalesCode());
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTglPenawaran()));
	            lc.setParent(item);	                 	                         
                
	            lc = new Listcell(rec.getNoPenawaran());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNamaCustomer());
                lc.setParent(item);
                
                lc = new Listcell(rec.getSektorIndustri());
                lc.setParent(item);
	            
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getNilai(), "#,###,###"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				String vStatus = "Submitted";
				if (rec.getStatusPenawaran().equals("0") == true){
					vStatus = "Submitted";
				} else {
					if (rec.getStatusPenawaran().equals("1") == true){
						vStatus = "Awarded";
					} else {
						if (rec.getStatusPenawaran().equals("2") == true){
							vStatus = "Lost";
						} else {
							if (rec.getStatusPenawaran().equals("3") == true){
								vStatus = "Postponed";
							} else {
								if (rec.getStatusPenawaran().equals("4") == true){
									vStatus = "Canceled";
								} else {
									if (rec.getStatusPenawaran().equals("5") == true){
										vStatus = "Closed";
									}
								}
							}
						}
					}
				}
				
				lc = new Listcell(vStatus);
                lc.setParent(item);
	            
				
				
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT21DaftarPenawaranSalesItem");
              }
        };
    }
    
	private EventListener onPaging_T21DaftarPenawaranSalesList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT21DaftarPenawaranSalesList = pageNo * paging_T21DaftarPenawaranSalesList.getPageSize();
		         setModelT21DaftarPenawaranSalesList();
			}
		};
	}


    public void onDoubleClickedT21DaftarPenawaranSalesItem(Event event) {
		if (listBoxT21DaftarPenawaranSales.getSelectedItem() != null) {
			T21DaftarPenawaranSales selectedData = (T21DaftarPenawaranSales) listBoxT21DaftarPenawaranSales.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT21DaftarPenawaranSales(selectedData);
	
	            if (getT21DaftarPenawaranSalesMainCtrl().getT21DaftarPenawaranSalesDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT21DaftarPenawaranSalesMainCtrl().tabT21DaftarPenawaranSalesDetail, null));
	            } else if (getT21DaftarPenawaranSalesMainCtrl().getT21DaftarPenawaranSalesDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT21DaftarPenawaranSalesMainCtrl().tabT21DaftarPenawaranSalesDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT21DaftarPenawaranSalesMainCtrl().tabT21DaftarPenawaranSalesDetail, selectedData));
	            
	            getT21DaftarPenawaranSalesMainCtrl().getT21DaftarPenawaranSalesDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT21DaftarPenawaranSales(Event event) {
		if (listBoxT21DaftarPenawaranSales.getSelectedItem() != null) {
			T21DaftarPenawaranSales selectedData = (T21DaftarPenawaranSales) listBoxT21DaftarPenawaranSales.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT21DaftarPenawaranSalesMainCtrl().getT21DaftarPenawaranSalesDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT21DaftarPenawaranSalesMainCtrl().tabT21DaftarPenawaranSalesDetail, null));
		
				} else if (getT21DaftarPenawaranSalesMainCtrl().getT21DaftarPenawaranSalesDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT21DaftarPenawaranSalesMainCtrl().tabT21DaftarPenawaranSalesDetail, null));
				}
				
				getT21DaftarPenawaranSalesMainCtrl().setSelectedT21DaftarPenawaranSales(selectedData);
				
			}
		}	
    }    
    
    
  
    
    
    
    public void onSort$listheader_T21DaftarPenawaranSalesList_Company(Event event) {
    	sortingData(listheader_T21DaftarPenawaranSalesList_Company, T21DaftarPenawaranSalesComparator.COMPARE_BY_COMPANY);
    }
    
    public void onSort$listheader_T21DaftarPenawaranSalesList_Cabang(Event event) {
    	sortingData(listheader_T21DaftarPenawaranSalesList_Cabang, T21DaftarPenawaranSalesComparator.COMPARE_BY_CABANG);
    }
    
    public void onSort$listheader_T21DaftarPenawaranSalesList_Sales(Event event) {
    	sortingData(listheader_T21DaftarPenawaranSalesList_Sales, T21DaftarPenawaranSalesComparator.COMPARE_BY_SALES);
    }
       
    public void onSort$listheader_T21DaftarPenawaranSalesList_TglPenarawan(Event event) {
    	sortingData(listheader_T21DaftarPenawaranSalesList_TglPenarawan, T21DaftarPenawaranSalesComparator.COMPARE_BY_TGLPENAWARAN);
    }
    
    public void onSort$listheader_T21DaftarPenawaranSalesList_NoPenawaran(Event event) {
    	sortingData(listheader_T21DaftarPenawaranSalesList_NoPenawaran, T21DaftarPenawaranSalesComparator.COMPARE_BY_NOPENAWARAN);
    }
    
    public void onSort$listheader_T21DaftarPenawaranSalesList_Customer(Event event) {
    	sortingData(listheader_T21DaftarPenawaranSalesList_Customer, T21DaftarPenawaranSalesComparator.COMPARE_BY_CUSTOMER);
    }
    
    public void onSort$listheader_T21DaftarPenawaranSalesList_SektorIndustri(Event event) {
    	sortingData(listheader_T21DaftarPenawaranSalesList_SektorIndustri, T21DaftarPenawaranSalesComparator.COMPARE_BY_SEKTORINDUSTRI);
    }
    
    public void onSort$listheader_T21DaftarPenawaranSalesList_Nilai(Event event) {
    	sortingData(listheader_T21DaftarPenawaranSalesList_Nilai, T21DaftarPenawaranSalesComparator.COMPARE_BY_NILAI);
    }
    
    public void onSort$listheader_T21DaftarPenawaranSalesList_Status(Event event) {
    	sortingData(listheader_T21DaftarPenawaranSalesList_Status, T21DaftarPenawaranSalesComparator.COMPARE_BY_STATUS);
    }
       
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T21DaftarPenawaranSalesList, new T21DaftarPenawaranSalesComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T21DaftarPenawaranSalesList, new T21DaftarPenawaranSalesComparator(true, sortBy));
    		}
    	}
    	
    	setModelT21DaftarPenawaranSalesList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T21DaftarPenawaranSalesList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT21DaftarPenawaranSalesList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
    	cmbCompany.setSelectedIndex(0);
    	cmbCabang.setSelectedIndex(0);
    	txtSales.setValue(null);
    	txtTglPenawaran.setValue(null);
    	txtNoPenawaran.setValue(null);
    	txtCustomer.setValue(null);
    	txtNilai.setValue(null);
    	cmbStatus.setSelectedIndex(0);
    	
    	cmbSektorIndustri.setValue(listSektorIndustri.getItemAtIndex(0).getLabel());
		listSektorIndustri.setSelectedItem(listSektorIndustri.getItemAtIndex(0));
    	vSektorIndustri ="99999";

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


    public void setSelectedT21DaftarPenawaranSales(T21DaftarPenawaranSales selectedT21DaftarPenawaranSales) {
        getT21DaftarPenawaranSalesMainCtrl().setSelectedT21DaftarPenawaranSales(selectedT21DaftarPenawaranSales);
    }

    public T21DaftarPenawaranSales getSelectedT21DaftarPenawaranSales() {
        return getT21DaftarPenawaranSalesMainCtrl().getSelectedT21DaftarPenawaranSales();
    }

    public void setT21DaftarPenawaranSalesMainCtrl(T21DaftarPenawaranSalesMainCtrl T21DaftarPenawaranSalesMainCtrl) {
        this.T21DaftarPenawaranSalesMainCtrl = T21DaftarPenawaranSalesMainCtrl;
    }

    public T21DaftarPenawaranSalesMainCtrl getT21DaftarPenawaranSalesMainCtrl() {
        return this.T21DaftarPenawaranSalesMainCtrl;
    }

}

