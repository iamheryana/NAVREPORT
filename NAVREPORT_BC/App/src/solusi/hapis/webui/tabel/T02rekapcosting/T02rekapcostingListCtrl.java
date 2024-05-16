package solusi.hapis.webui.tabel.T02rekapcosting;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.zkoss.zul.Button;
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

import solusi.hapis.backend.tabel.model.T02rekapcosting;
import solusi.hapis.backend.tabel.model.T03salesperson;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.T03salespersonLOV;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 13-03-2018
 */

public class T02rekapcostingListCtrl extends GFCBaseListCtrl<T02rekapcosting> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT02rekapcostingList; 
    protected Panel panelT02rekapcostingList;
 	
	// Search Box Components 
    
    
	protected Textbox txtSales;
	protected Textbox txtNoSO;
	protected Textbox txtCustomer;
	protected Textbox txtNoPOCust;
	protected Textbox txtNoInvoice;	
	protected Textbox txtInvoice;
	protected Textbox txtLunas;
	protected Textbox txtAmount;
	protected Combobox cmbFlagKomisi;
	protected Combobox cmbFlagTQS;
	
	protected Button btnSearchSalesLOV;
//	protected Bandbox  cmbSales;
//	protected Listbox listSales;
//	protected String vSales = "ALL";
	
	// Paging and list
    protected Borderlayout borderLayout_T02rekapcostingList;
    protected Paging paging_T02rekapcostingList;
    private int startT02rekapcostingList;
	private List<T02rekapcosting> list_T02rekapcostingList = new ArrayList<T02rekapcosting>();
    private ListModelList modelList_T02rekapcostingList = new ListModelList();
    protected Listbox listBoxT02rekapcosting;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T02rekapcostingMainCtrl T02rekapcostingMainCtrl;

    // List Header		
    protected Listheader listheader_T02rekapcostingList_Sales;
    protected Listheader listheader_T02rekapcostingList_NoSO;
    protected Listheader listheader_T02rekapcostingList_Customer;
    protected Listheader listheader_T02rekapcostingList_NoPoCust;
    protected Listheader listheader_T02rekapcostingList_NoInvoice;
    protected Listheader listheader_T02rekapcostingList_TglInvoice;
    protected Listheader listheader_T02rekapcostingList_TglLunas;
    protected Listheader listheader_T02rekapcostingList_Amount;
    protected Listheader listheader_T02rekapcostingList_FlagKomisi;
    protected Listheader listheader_T02rekapcostingList_FlagTqs;
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T02rekapcostingListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T02rekapcostingList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T02rekapcostingList.setDetailed(true);
		paging_T02rekapcostingList.addEventListener("onPaging", onPaging_T02rekapcostingList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT02rekapcostingMainCtrl((T02rekapcostingMainCtrl) arg.get("ModuleMainController"));
		
		    getT02rekapcostingMainCtrl().setT02rekapcostingListCtrl(this);
		}
        
		
//	Bandpopup popup1 = new Bandpopup();
//		listSales = new Listbox();
//		listSales.setMold("paging");
//		listSales.setAutopaging(true);
//		listSales.setWidth("250px");
//		listSales.addEventListener(Events.ON_SELECT, selectSales());
//		listSales.setParent(popup1);
//	popup1.setParent(cmbSales);
//        
//	listSales.appendItem("ALL", "ALL");
//	
//	vResultSales = selectQueryNavReportService.QuerySalesperson();
//	if(CommonUtils.isNotEmpty(vResultSales)){
//		for(Object[] aRslt : vResultSales){
//			listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
//		}
//	}
//	
//	
//	cmbSales.setValue(listSales.getItemAtIndex(0).getLabel());
//	listSales.setSelectedItem(listSales.getItemAtIndex(0));

    }    
    

//    private EventListener selectSales() {
//		return new EventListener() {
//	
//			@Override
//			public void onEvent(Event event) throws Exception {
//				
//				cmbSales.setValue(listSales.getSelectedItem().getLabel());
//				vSales = listSales.getSelectedItem().getValue().toString();
//				cmbSales.close();
//			}
//		};
//	}
//    
    public void onCreate$windowT02rekapcostingList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
        listheader_T02rekapcostingList_Sales.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_SALES));
        listheader_T02rekapcostingList_Sales.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_SALES));        
        
        listheader_T02rekapcostingList_NoSO.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_NOSO));
        listheader_T02rekapcostingList_NoSO.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_NOSO));        
         
        listheader_T02rekapcostingList_Customer.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_CUSTOMER));
        listheader_T02rekapcostingList_Customer.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_CUSTOMER));        
       
        listheader_T02rekapcostingList_NoPoCust.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_NOPOCUSTOMER));
        listheader_T02rekapcostingList_NoPoCust.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_NOPOCUSTOMER));        
       
        listheader_T02rekapcostingList_NoInvoice.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_NOINVOICE));
        listheader_T02rekapcostingList_NoInvoice.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_NOINVOICE));        
          
        listheader_T02rekapcostingList_TglInvoice.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_TGLINVOICE));
        listheader_T02rekapcostingList_TglInvoice.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_TGLINVOICE));        
       
        listheader_T02rekapcostingList_TglLunas.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_TGLLUNAS));
        listheader_T02rekapcostingList_TglLunas.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_TGLLUNAS));        
       
        listheader_T02rekapcostingList_Amount.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_AMOUNT));
        listheader_T02rekapcostingList_Amount.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_AMOUNT));        
    
        listheader_T02rekapcostingList_FlagKomisi.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_FLAGKOMISI));
        listheader_T02rekapcostingList_FlagKomisi.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_FLAGKOMISI));        
       
        listheader_T02rekapcostingList_FlagTqs.setSortAscending(new T02rekapcostingComparator(true, T02rekapcostingComparator.COMPARE_BY_FLAGTQS));
        listheader_T02rekapcostingList_FlagTqs.setSortDescending(new T02rekapcostingComparator(false, T02rekapcostingComparator.COMPARE_BY_FLAGTQS));        
       
              
        getSearchData();
        
        listBoxT02rekapcosting.setItemRenderer(renderTable());
        
        windowT02rekapcostingList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		


//		if (vSales.equals("ALL") != true) {
//			parameterInput.put("sales", vSales);
//		}
		
		if (CommonUtils.isNotEmpty(txtSales.getValue())) {
			parameterInput.put("sales", txtSales.getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtNoSO.getValue())) {
			parameterInput.put("noSo", txtNoSO.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtCustomer.getValue())) {
			parameterInput.put("customer", txtCustomer.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtNoPOCust.getValue())) {
			parameterInput.put("noPoCust", txtNoPOCust.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtNoInvoice.getValue())) {
			parameterInput.put("noInvoice", txtNoInvoice.getValue());
		}
		
		
	
		
		if (CommonUtils.isValidDateFormat(txtInvoice.getValue())) {
			Date tglInv1 = CommonUtils.convertStringToDate(txtInvoice.getValue());
			Date tglInv2 = CommonUtils.createSecondParameterForSearch(txtInvoice.getValue());
			parameterInput.put("tanggalInvoicefrom", tglInv1);
			parameterInput.put("tanggalInvoiceto", tglInv2);
		}
		
		if (CommonUtils.isValidDateFormat(txtLunas.getValue())) {
			Date tglLunasl = CommonUtils.convertStringToDate(txtLunas.getValue());
			Date tglLunas2 = CommonUtils.createSecondParameterForSearch(txtLunas.getValue());
			
//			System.out.println("tglLunasl : "+CommonUtils.convertStringToDate(txtLunas.getValue()));
//			System.out.println("tglLunasl : "+CommonUtils.createSecondParameterForSearch(txtLunas.getValue()));
			parameterInput.put("tanggalLunasfrom", tglLunasl);
			parameterInput.put("tanggalLunasto", tglLunas2);
		}
		
		if (CommonUtils.isNotEmpty(txtAmount.getValue())) {
			parameterInput.put("amount", new BigDecimal(txtAmount.getValue()));
		}
		
		if (cmbFlagKomisi.getSelectedIndex() != 0) {
			parameterInput.put("flagKomisi", (String) cmbFlagKomisi.getSelectedItem().getValue());
		}
		
		if (cmbFlagTQS.getSelectedIndex() != 0) {
			parameterInput.put("flagTqs", (String) cmbFlagTQS.getSelectedItem().getValue());
		}
				
		
		list_T02rekapcostingList.clear();
		
		List<T02rekapcosting> tempListT02rekapcosting = getT02rekapcostingMainCtrl().getT02rekapcostingService().getListT02rekapcosting(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT02rekapcosting)) {
			list_T02rekapcostingList.addAll(tempListT02rekapcosting);
			startT02rekapcostingList = 0;
			paging_T02rekapcostingList.setActivePage(0);
		}

		setModelT02rekapcostingList();
	}

	// Pembagian data untuk paging
	private void setModelT02rekapcostingList() {		
		modelList_T02rekapcostingList.clear();
		
		T02rekapcosting selectedData = null;
		if (CommonUtils.isNotEmpty(list_T02rekapcostingList)){
			
			int endT02rekapcostingList = 0;
			if(startT02rekapcostingList + paging_T02rekapcostingList.getPageSize() < list_T02rekapcostingList.size()) {
				endT02rekapcostingList = startT02rekapcostingList + paging_T02rekapcostingList.getPageSize();
			} else {
				endT02rekapcostingList = list_T02rekapcostingList.size();
			}
			
			if (startT02rekapcostingList > endT02rekapcostingList) {
				startT02rekapcostingList = 0;
				paging_T02rekapcostingList.setActivePage(0);
			}
			
			modelList_T02rekapcostingList.addAll(list_T02rekapcostingList.subList(startT02rekapcostingList, endT02rekapcostingList));

			paging_T02rekapcostingList.setDetailed(true);
			paging_T02rekapcostingList.setTotalSize(list_T02rekapcostingList.size());
			
			listBoxT02rekapcosting.setModel(modelList_T02rekapcostingList);
			listBoxT02rekapcosting.setSelectedIndex(0);
	
			selectedData = list_T02rekapcostingList.get(startT02rekapcostingList);
		} else {
			paging_T02rekapcostingList.setDetailed(false);
			listBoxT02rekapcosting.setModel(modelList_T02rekapcostingList);
			paging_T02rekapcostingList.setTotalSize(0);
		}
		
		getT02rekapcostingMainCtrl().setSelectedT02rekapcosting(selectedData);

	}
	
//	private String getLabelSales(String kodeSales){
//		String vLabelSales = "";
//		if(CommonUtils.isNotEmpty(kodeSales)){
//			if(CommonUtils.isNotEmpty(vResultSales)){
//				for(Object[] aRslt : vResultSales){
//					if (kodeSales.equals(aRslt[1].toString())){
//						vLabelSales = aRslt[0].toString();
//						break;
//					}
//				}
//			}
//		}
//		
//		return vLabelSales;
//	}
	
	public void onClick$btnSearchSalesLOV(Event event) {
		T03salesperson sales = T03salespersonLOV.show(windowT02rekapcostingList);

		if (sales != null) {
			txtSales.setValue(sales.getSales());
		
		} else {
			txtSales.setValue(null);
		}
		
	}
	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T02rekapcosting rec = (T02rekapcosting) data;

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
                info.setParent(windowT02rekapcostingList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                
                lc = new Listcell(rec.getSales());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoSo());
                lc.setParent(item);
                
                lc = new Listcell(rec.getCustomer());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoPoCust());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoInvoice());
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTglInvoice()));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTglLunas()));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getAmount(), "#,###,###.##"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
                
                String vFlagKomisi = rec.getFlagKomisi().equals("Y")?"Sudah (Checked)":"Belum (Unchecked)";
                lc = new Listcell(vFlagKomisi);
                lc.setParent(item); 
      
                String vFlagTQS = rec.getFlagTqs().equals("Y")?"Sudah (Checked)":"Belum (Unchecked)";
                lc = new Listcell(vFlagTQS);
                lc.setParent(item);
                
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT02rekapcostingItem");
              }
        };
    }
    
	private EventListener onPaging_T02rekapcostingList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT02rekapcostingList = pageNo * paging_T02rekapcostingList.getPageSize();
		         setModelT02rekapcostingList();
			}
		};
	}


    public void onDoubleClickedT02rekapcostingItem(Event event) {
		if (listBoxT02rekapcosting.getSelectedItem() != null) {
			T02rekapcosting selectedData = (T02rekapcosting) listBoxT02rekapcosting.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT02rekapcosting(selectedData);
	
	            if (getT02rekapcostingMainCtrl().getT02rekapcostingDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT02rekapcostingMainCtrl().tabT02rekapcostingDetail, null));
	            } else if (getT02rekapcostingMainCtrl().getT02rekapcostingDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT02rekapcostingMainCtrl().tabT02rekapcostingDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT02rekapcostingMainCtrl().tabT02rekapcostingDetail, selectedData));
	            
	            getT02rekapcostingMainCtrl().getT02rekapcostingDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT02rekapcosting(Event event) {
		if (listBoxT02rekapcosting.getSelectedItem() != null) {
			T02rekapcosting selectedData = (T02rekapcosting) listBoxT02rekapcosting.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT02rekapcostingMainCtrl().getT02rekapcostingDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT02rekapcostingMainCtrl().tabT02rekapcostingDetail, null));
		
				} else if (getT02rekapcostingMainCtrl().getT02rekapcostingDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT02rekapcostingMainCtrl().tabT02rekapcostingDetail, null));
				}
				
				getT02rekapcostingMainCtrl().setSelectedT02rekapcosting(selectedData);
				
			}
		}	
    }
    
    public void onSort$listheader_T02rekapcostingList_Sales(Event event) {
    	sortingData(listheader_T02rekapcostingList_Sales, T02rekapcostingComparator.COMPARE_BY_SALES);
    }
    
    public void onSort$listheader_T02rekapcostingList_NoSO(Event event) {
    	sortingData(listheader_T02rekapcostingList_NoSO, T02rekapcostingComparator.COMPARE_BY_NOSO);
    }

    public void onSort$listheader_T02rekapcostingList_Customer(Event event) {
    	sortingData(listheader_T02rekapcostingList_Customer, T02rekapcostingComparator.COMPARE_BY_CUSTOMER);
    }

    public void onSort$listheader_T02rekapcostingList_NoPoCust(Event event) {
    	sortingData(listheader_T02rekapcostingList_NoPoCust, T02rekapcostingComparator.COMPARE_BY_NOPOCUSTOMER);
    }

    public void onSort$listheader_T02rekapcostingList_NoInvoice(Event event) {
    	sortingData(listheader_T02rekapcostingList_NoInvoice, T02rekapcostingComparator.COMPARE_BY_NOINVOICE);
    }

    public void onSort$listheader_T02rekapcostingList_TglInvoice(Event event) {
    	sortingData(listheader_T02rekapcostingList_TglInvoice, T02rekapcostingComparator.COMPARE_BY_TGLINVOICE);
    }

    public void onSort$listheader_T02rekapcostingList_TglLunas(Event event) {
    	sortingData(listheader_T02rekapcostingList_TglLunas, T02rekapcostingComparator.COMPARE_BY_TGLLUNAS);
    }

    public void onSort$listheader_T02rekapcostingList_Amount(Event event) {
    	sortingData(listheader_T02rekapcostingList_Amount, T02rekapcostingComparator.COMPARE_BY_AMOUNT);
    }

    public void onSort$listheader_T02rekapcostingList_FlagKomisi(Event event) {
    	sortingData(listheader_T02rekapcostingList_FlagKomisi, T02rekapcostingComparator.COMPARE_BY_FLAGKOMISI);
    }

    public void onSort$listheader_T02rekapcostingList_FlagTqs(Event event) {
    	sortingData(listheader_T02rekapcostingList_FlagTqs, T02rekapcostingComparator.COMPARE_BY_FLAGTQS);
    }
     
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T02rekapcostingList, new T02rekapcostingComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T02rekapcostingList, new T02rekapcostingComparator(true, sortBy));
    		}
    	}
    	
    	setModelT02rekapcostingList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T02rekapcostingList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT02rekapcostingList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
//    	cmbSales.setValue(listSales.getItemAtIndex(0).getLabel());
//    	listSales.setSelectedItem(listSales.getItemAtIndex(0));
//    	vSales="ALL";

    	txtSales.setValue(null);
    	
    	txtNoSO.setValue(null);
    	
    	txtCustomer.setValue(null);
    	txtNoPOCust.setValue(null);
    	txtNoInvoice.setValue(null);
    	txtInvoice.setValue(null);
    	txtLunas.setValue(null);
    	txtAmount.setValue(null);
    	
    	cmbFlagKomisi.setSelectedIndex(0);
    	cmbFlagTQS.setSelectedIndex(0);
    	
    	
    	//startT02rekapcostingList = 0;
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


    public void setSelectedT02rekapcosting(T02rekapcosting selectedT02rekapcosting) {
        getT02rekapcostingMainCtrl().setSelectedT02rekapcosting(selectedT02rekapcosting);
    }

    public T02rekapcosting getSelectedT02rekapcosting() {
        return getT02rekapcostingMainCtrl().getSelectedT02rekapcosting();
    }

    public void setT02rekapcostingMainCtrl(T02rekapcostingMainCtrl T02rekapcostingMainCtrl) {
        this.T02rekapcostingMainCtrl = T02rekapcostingMainCtrl;
    }

    public T02rekapcostingMainCtrl getT02rekapcostingMainCtrl() {
        return this.T02rekapcostingMainCtrl;
    }

}
