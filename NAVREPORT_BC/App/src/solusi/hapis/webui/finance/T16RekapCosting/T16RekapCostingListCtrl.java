package solusi.hapis.webui.finance.T16RekapCosting;

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

import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.backend.navbi.model.T16RekapCosting;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.M02SalespersonLOV;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 19-04-2021
 */

public class T16RekapCostingListCtrl extends GFCBaseListCtrl<T16RekapCosting> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT16RekapCostingList; 
    protected Panel panelT16RekapCostingList;
 	
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
    protected Borderlayout borderLayout_T16RekapCostingList;
    protected Paging paging_T16RekapCostingList;
    private int startT16RekapCostingList;
	private List<T16RekapCosting> list_T16RekapCostingList = new ArrayList<T16RekapCosting>();
    private ListModelList modelList_T16RekapCostingList = new ListModelList();
    protected Listbox listBoxT16RekapCosting;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T16RekapCostingMainCtrl T16RekapCostingMainCtrl;

    // List Header		
    protected Listheader listheader_T16RekapCostingList_Sales;
    protected Listheader listheader_T16RekapCostingList_NoSO;
    protected Listheader listheader_T16RekapCostingList_Customer;
    protected Listheader listheader_T16RekapCostingList_NoPoCust;
    protected Listheader listheader_T16RekapCostingList_NoInvoice;
    protected Listheader listheader_T16RekapCostingList_TglInvoice;
    protected Listheader listheader_T16RekapCostingList_TglLunas;
    protected Listheader listheader_T16RekapCostingList_Amount;
    protected Listheader listheader_T16RekapCostingList_FlagKomisi;
    protected Listheader listheader_T16RekapCostingList_FlagTqs;
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T16RekapCostingListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T16RekapCostingList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T16RekapCostingList.setDetailed(true);
		paging_T16RekapCostingList.addEventListener("onPaging", onPaging_T16RekapCostingList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT16RekapCostingMainCtrl((T16RekapCostingMainCtrl) arg.get("ModuleMainController"));
		
		    getT16RekapCostingMainCtrl().setT16RekapCostingListCtrl(this);
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
    public void onCreate$windowT16RekapCostingList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
        listheader_T16RekapCostingList_Sales.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_SALES));
        listheader_T16RekapCostingList_Sales.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_SALES));        
        
        listheader_T16RekapCostingList_NoSO.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_NOSO));
        listheader_T16RekapCostingList_NoSO.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_NOSO));        
         
        listheader_T16RekapCostingList_Customer.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_CUSTOMER));
        listheader_T16RekapCostingList_Customer.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_CUSTOMER));        
       
        listheader_T16RekapCostingList_NoPoCust.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_NOPOCUSTOMER));
        listheader_T16RekapCostingList_NoPoCust.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_NOPOCUSTOMER));        
       
        listheader_T16RekapCostingList_NoInvoice.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_NOINVOICE));
        listheader_T16RekapCostingList_NoInvoice.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_NOINVOICE));        
          
        listheader_T16RekapCostingList_TglInvoice.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_TGLINVOICE));
        listheader_T16RekapCostingList_TglInvoice.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_TGLINVOICE));        
       
        listheader_T16RekapCostingList_TglLunas.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_TGLLUNAS));
        listheader_T16RekapCostingList_TglLunas.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_TGLLUNAS));        
       
        listheader_T16RekapCostingList_Amount.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_AMOUNT));
        listheader_T16RekapCostingList_Amount.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_AMOUNT));        
    
        listheader_T16RekapCostingList_FlagKomisi.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_FLAGKOMISI));
        listheader_T16RekapCostingList_FlagKomisi.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_FLAGKOMISI));        
       
        listheader_T16RekapCostingList_FlagTqs.setSortAscending(new T16RekapCostingComparator(true, T16RekapCostingComparator.COMPARE_BY_FLAGTQS));
        listheader_T16RekapCostingList_FlagTqs.setSortDescending(new T16RekapCostingComparator(false, T16RekapCostingComparator.COMPARE_BY_FLAGTQS));        
       
              
        getSearchData();
        
        listBoxT16RekapCosting.setItemRenderer(renderTable());
        
        windowT16RekapCostingList.addEventListener(Events.ON_OK, onSubmitForm());
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
				
		
		list_T16RekapCostingList.clear();
		
		List<T16RekapCosting> tempListT16RekapCosting = getT16RekapCostingMainCtrl().getT16RekapCostingService().getListT16RekapCosting(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT16RekapCosting)) {
			list_T16RekapCostingList.addAll(tempListT16RekapCosting);
			startT16RekapCostingList = 0;
			paging_T16RekapCostingList.setActivePage(0);
		}

		setModelT16RekapCostingList();
	}

	// Pembagian data untuk paging
	private void setModelT16RekapCostingList() {		
		modelList_T16RekapCostingList.clear();
		
		T16RekapCosting selectedData = null;
		if (CommonUtils.isNotEmpty(list_T16RekapCostingList)){
			
			int endT16RekapCostingList = 0;
			if(startT16RekapCostingList + paging_T16RekapCostingList.getPageSize() < list_T16RekapCostingList.size()) {
				endT16RekapCostingList = startT16RekapCostingList + paging_T16RekapCostingList.getPageSize();
			} else {
				endT16RekapCostingList = list_T16RekapCostingList.size();
			}
			
			if (startT16RekapCostingList > endT16RekapCostingList) {
				startT16RekapCostingList = 0;
				paging_T16RekapCostingList.setActivePage(0);
			}
			
			modelList_T16RekapCostingList.addAll(list_T16RekapCostingList.subList(startT16RekapCostingList, endT16RekapCostingList));

			paging_T16RekapCostingList.setDetailed(true);
			paging_T16RekapCostingList.setTotalSize(list_T16RekapCostingList.size());
			
			listBoxT16RekapCosting.setModel(modelList_T16RekapCostingList);
			listBoxT16RekapCosting.setSelectedIndex(0);
	
			selectedData = list_T16RekapCostingList.get(startT16RekapCostingList);
		} else {
			paging_T16RekapCostingList.setDetailed(false);
			listBoxT16RekapCosting.setModel(modelList_T16RekapCostingList);
			paging_T16RekapCostingList.setTotalSize(0);
		}
		
		getT16RekapCostingMainCtrl().setSelectedT16RekapCosting(selectedData);

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
		M02Salesperson sales = M02SalespersonLOV.show(windowT16RekapCostingList);

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
                
            	final T16RekapCosting rec = (T16RekapCosting) data;

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
                info.setParent(windowT16RekapCostingList);
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
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT16RekapCostingItem");
              }
        };
    }
    
	private EventListener onPaging_T16RekapCostingList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT16RekapCostingList = pageNo * paging_T16RekapCostingList.getPageSize();
		         setModelT16RekapCostingList();
			}
		};
	}


    public void onDoubleClickedT16RekapCostingItem(Event event) {
		if (listBoxT16RekapCosting.getSelectedItem() != null) {
			T16RekapCosting selectedData = (T16RekapCosting) listBoxT16RekapCosting.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT16RekapCosting(selectedData);
	
	            if (getT16RekapCostingMainCtrl().getT16RekapCostingDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT16RekapCostingMainCtrl().tabT16RekapCostingDetail, null));
	            } else if (getT16RekapCostingMainCtrl().getT16RekapCostingDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT16RekapCostingMainCtrl().tabT16RekapCostingDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT16RekapCostingMainCtrl().tabT16RekapCostingDetail, selectedData));
	            
	            getT16RekapCostingMainCtrl().getT16RekapCostingDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT16RekapCosting(Event event) {
		if (listBoxT16RekapCosting.getSelectedItem() != null) {
			T16RekapCosting selectedData = (T16RekapCosting) listBoxT16RekapCosting.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT16RekapCostingMainCtrl().getT16RekapCostingDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT16RekapCostingMainCtrl().tabT16RekapCostingDetail, null));
		
				} else if (getT16RekapCostingMainCtrl().getT16RekapCostingDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT16RekapCostingMainCtrl().tabT16RekapCostingDetail, null));
				}
				
				getT16RekapCostingMainCtrl().setSelectedT16RekapCosting(selectedData);
				
			}
		}	
    }
    
    public void onSort$listheader_T16RekapCostingList_Sales(Event event) {
    	sortingData(listheader_T16RekapCostingList_Sales, T16RekapCostingComparator.COMPARE_BY_SALES);
    }
    
    public void onSort$listheader_T16RekapCostingList_NoSO(Event event) {
    	sortingData(listheader_T16RekapCostingList_NoSO, T16RekapCostingComparator.COMPARE_BY_NOSO);
    }

    public void onSort$listheader_T16RekapCostingList_Customer(Event event) {
    	sortingData(listheader_T16RekapCostingList_Customer, T16RekapCostingComparator.COMPARE_BY_CUSTOMER);
    }

    public void onSort$listheader_T16RekapCostingList_NoPoCust(Event event) {
    	sortingData(listheader_T16RekapCostingList_NoPoCust, T16RekapCostingComparator.COMPARE_BY_NOPOCUSTOMER);
    }

    public void onSort$listheader_T16RekapCostingList_NoInvoice(Event event) {
    	sortingData(listheader_T16RekapCostingList_NoInvoice, T16RekapCostingComparator.COMPARE_BY_NOINVOICE);
    }

    public void onSort$listheader_T16RekapCostingList_TglInvoice(Event event) {
    	sortingData(listheader_T16RekapCostingList_TglInvoice, T16RekapCostingComparator.COMPARE_BY_TGLINVOICE);
    }

    public void onSort$listheader_T16RekapCostingList_TglLunas(Event event) {
    	sortingData(listheader_T16RekapCostingList_TglLunas, T16RekapCostingComparator.COMPARE_BY_TGLLUNAS);
    }

    public void onSort$listheader_T16RekapCostingList_Amount(Event event) {
    	sortingData(listheader_T16RekapCostingList_Amount, T16RekapCostingComparator.COMPARE_BY_AMOUNT);
    }

    public void onSort$listheader_T16RekapCostingList_FlagKomisi(Event event) {
    	sortingData(listheader_T16RekapCostingList_FlagKomisi, T16RekapCostingComparator.COMPARE_BY_FLAGKOMISI);
    }

    public void onSort$listheader_T16RekapCostingList_FlagTqs(Event event) {
    	sortingData(listheader_T16RekapCostingList_FlagTqs, T16RekapCostingComparator.COMPARE_BY_FLAGTQS);
    }
     
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T16RekapCostingList, new T16RekapCostingComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T16RekapCostingList, new T16RekapCostingComparator(true, sortBy));
    		}
    	}
    	
    	setModelT16RekapCostingList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T16RekapCostingList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT16RekapCostingList.invalidate();
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
    	
    	
    	//startT16RekapCostingList = 0;
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


    public void setSelectedT16RekapCosting(T16RekapCosting selectedT16RekapCosting) {
        getT16RekapCostingMainCtrl().setSelectedT16RekapCosting(selectedT16RekapCosting);
    }

    public T16RekapCosting getSelectedT16RekapCosting() {
        return getT16RekapCostingMainCtrl().getSelectedT16RekapCosting();
    }

    public void setT16RekapCostingMainCtrl(T16RekapCostingMainCtrl T16RekapCostingMainCtrl) {
        this.T16RekapCostingMainCtrl = T16RekapCostingMainCtrl;
    }

    public T16RekapCostingMainCtrl getT16RekapCostingMainCtrl() {
        return this.T16RekapCostingMainCtrl;
    }

}
