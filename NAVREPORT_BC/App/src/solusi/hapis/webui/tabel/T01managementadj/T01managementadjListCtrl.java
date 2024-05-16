package solusi.hapis.webui.tabel.T01managementadj;

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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
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

import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.backend.tabel.model.T01managementadj;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 07-09-2016
 */

public class T01managementadjListCtrl extends GFCBaseListCtrl<T01managementadj> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT01managementadjList; 
    protected Panel panelT01managementadjList;
 	
	// Search Box Components 
	protected Textbox txtTanggal;
	protected Textbox txtSales;
	protected Textbox txtKeterangan;
	protected Textbox txtAmountHw01;
	protected Textbox txtAmountPs01;
	protected Textbox txtAmountPs02;
	protected Textbox txtAmountPs03;
	protected Textbox txtAmountPs04;
	protected Textbox txtAmountPs05;
	
	//protected Combobox cmbCabang;
	
	protected Bandbox  cmbCabang;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	
	// Paging and list
    protected Borderlayout borderLayout_T01managementadjList;
    protected Paging paging_T01managementadjList;
    private int startT01managementadjList;
	private List<T01managementadj> list_T01managementadjList = new ArrayList<T01managementadj>();
    private ListModelList modelList_T01managementadjList = new ListModelList();
    protected Listbox listBoxT01managementadj;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T01managementadjMainCtrl T01managementadjMainCtrl;

    // List Header
    protected Listheader listheader_T01managementadjList_Tanggal;
    protected Listheader listheader_T01managementadjList_Cabang;
    protected Listheader listheader_T01managementadjList_Sales;
    protected Listheader listheader_T01managementadjList_Keterangan;
    protected Listheader listheader_T01managementadjList_AmountHw01;
    protected Listheader listheader_T01managementadjList_AmountPs01;
    protected Listheader listheader_T01managementadjList_AmountPs02;
    protected Listheader listheader_T01managementadjList_AmountPs03;
    protected Listheader listheader_T01managementadjList_AmountPs04;
    protected Listheader listheader_T01managementadjList_AmountPs05;
    
    private SelectQueryService selectQueryService;
    private List<Object[]> vResultCabang;
    
    public T01managementadjListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T01managementadjList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T01managementadjList.setDetailed(true);
		paging_T01managementadjList.addEventListener("onPaging", onPaging_T01managementadjList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT01managementadjMainCtrl((T01managementadjMainCtrl) arg.get("ModuleMainController"));
		
		    getT01managementadjMainCtrl().setT01managementadjListCtrl(this);
		}
        
		
		
    	Bandpopup popup1 = new Bandpopup();
			listCabang = new Listbox();
			listCabang.setMold("paging");
			listCabang.setAutopaging(true);
			listCabang.setWidth("250px");
			listCabang.addEventListener(Events.ON_SELECT, selectCabang());
			listCabang.setParent(popup1);
		popup1.setParent(cmbCabang);
	        
		listCabang.appendItem("ALL", "ALL");
		
		vResultCabang = selectQueryService.QueryCabang2();
		if(CommonUtils.isNotEmpty(vResultCabang)){
			for(Object[] aRslt : vResultCabang){
				listCabang.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbCabang.setValue(listCabang.getItemAtIndex(0).getLabel());
		listCabang.setSelectedItem(listCabang.getItemAtIndex(0));
    }    
    
	private EventListener selectCabang() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCabang.setValue(listCabang.getSelectedItem().getLabel());
				vCabang = listCabang.getSelectedItem().getValue().toString();
				cmbCabang.close();
			}
		};
	}
    public void onCreate$windowT01managementadjList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
        
      
        listheader_T01managementadjList_Tanggal.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_TANGGAL));
        listheader_T01managementadjList_Tanggal.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_TANGGAL));        
        
        listheader_T01managementadjList_Cabang.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_CABANG));
        listheader_T01managementadjList_Cabang.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_CABANG));        
       
        listheader_T01managementadjList_Sales.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_SALES));
        listheader_T01managementadjList_Sales.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_SALES));        
               
        listheader_T01managementadjList_Keterangan.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_KETERANGAN));
        listheader_T01managementadjList_Keterangan.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_KETERANGAN));  
        
        listheader_T01managementadjList_AmountHw01.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_AMOUNTHW01));
        listheader_T01managementadjList_AmountHw01.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_AMOUNTHW01));                     

        listheader_T01managementadjList_AmountPs01.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_AMOUNTPS01));
        listheader_T01managementadjList_AmountPs01.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_AMOUNTPS01));

        listheader_T01managementadjList_AmountPs02.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_AMOUNTPS02));
        listheader_T01managementadjList_AmountPs02.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_AMOUNTPS02));

        listheader_T01managementadjList_AmountPs03.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_AMOUNTPS03));
        listheader_T01managementadjList_AmountPs03.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_AMOUNTPS03));
        
        listheader_T01managementadjList_AmountPs04.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_AMOUNTPS04));
        listheader_T01managementadjList_AmountPs04.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_AMOUNTPS04));
        
        listheader_T01managementadjList_AmountPs05.setSortAscending(new T01managementadjComparator(true, T01managementadjComparator.COMPARE_BY_AMOUNTPS05));
        listheader_T01managementadjList_AmountPs05.setSortDescending(new T01managementadjComparator(false, T01managementadjComparator.COMPARE_BY_AMOUNTPS05));

        getSearchData();
        
        listBoxT01managementadj.setItemRenderer(renderTable());
        
        windowT01managementadjList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		if (CommonUtils.isValidDateFormat(txtTanggal.getValue())) {
			Date tanggal = CommonUtils.convertStringToDate(txtTanggal.getValue());
			Date tanggal2 = CommonUtils.createSecondParameterForSearch(txtTanggal.getValue());
			parameterInput.put("tanggalfrom", tanggal);
			parameterInput.put("tanggalto", tanggal2);
		}
		
		if (vCabang.equals("ALL") != true) {
			parameterInput.put("cabang", vCabang);
		}
		
		if (CommonUtils.isNotEmpty(txtSales.getValue())) {
			parameterInput.put("sales", txtSales.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtKeterangan.getValue())) {
			parameterInput.put("keterangan", txtKeterangan.getValue());
		}

		if (CommonUtils.isNotEmpty(txtAmountHw01.getValue())) {
			parameterInput.put("amountHw01", new BigDecimal(txtAmountHw01.getValue()));
		}
		
		if (CommonUtils.isNotEmpty(txtAmountPs01.getValue())) {
			parameterInput.put("amountPs01", new BigDecimal(txtAmountPs01.getValue()));
		}
		
		if (CommonUtils.isNotEmpty(txtAmountPs02.getValue())) {
			parameterInput.put("amountPs02", new BigDecimal(txtAmountPs02.getValue()));
		}
		
		if (CommonUtils.isNotEmpty(txtAmountPs03.getValue())) {
			parameterInput.put("amountPs03", new BigDecimal(txtAmountPs03.getValue()));
		}
		
		if (CommonUtils.isNotEmpty(txtAmountPs04.getValue())) {
			parameterInput.put("amountPs04", new BigDecimal(txtAmountPs04.getValue()));
		}
		
		if (CommonUtils.isNotEmpty(txtAmountPs05.getValue())) {
			parameterInput.put("amountPs05", new BigDecimal(txtAmountPs05.getValue()));
		}
				
		list_T01managementadjList.clear();
		
		List<T01managementadj> tempListT01managementadj = getT01managementadjMainCtrl().getT01managementadjService().getListT01managementadj(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT01managementadj)) {
			list_T01managementadjList.addAll(tempListT01managementadj);
			startT01managementadjList = 0;
			paging_T01managementadjList.setActivePage(0);
		}

		setModelT01managementadjList();
	}

	// Pembagian data untuk paging
	private void setModelT01managementadjList() {		
		modelList_T01managementadjList.clear();
		
		T01managementadj selectedData = null;
		if (CommonUtils.isNotEmpty(list_T01managementadjList)){
			
			int endT01managementadjList = 0;
			if(startT01managementadjList + paging_T01managementadjList.getPageSize() < list_T01managementadjList.size()) {
				endT01managementadjList = startT01managementadjList + paging_T01managementadjList.getPageSize();
			} else {
				endT01managementadjList = list_T01managementadjList.size();
			}
			
			if (startT01managementadjList > endT01managementadjList) {
				startT01managementadjList = 0;
				paging_T01managementadjList.setActivePage(0);
			}
			
			modelList_T01managementadjList.addAll(list_T01managementadjList.subList(startT01managementadjList, endT01managementadjList));

			paging_T01managementadjList.setDetailed(true);
			paging_T01managementadjList.setTotalSize(list_T01managementadjList.size());
			
			listBoxT01managementadj.setModel(modelList_T01managementadjList);
			listBoxT01managementadj.setSelectedIndex(0);
	
			selectedData = list_T01managementadjList.get(startT01managementadjList);
		} else {
			paging_T01managementadjList.setDetailed(false);
			listBoxT01managementadj.setModel(modelList_T01managementadjList);
			paging_T01managementadjList.setTotalSize(0);
		}
		
		getT01managementadjMainCtrl().setSelectedT01managementadj(selectedData);

	}
	
	private String getLabelCabang(String kodeCabang){
		String vLabelCabang = "";
		if(CommonUtils.isNotEmpty(kodeCabang)){
			if(CommonUtils.isNotEmpty(vResultCabang)){
				for(Object[] aRslt : vResultCabang){
					if (kodeCabang.equals(aRslt[1].toString())){
						vLabelCabang = aRslt[0].toString();
						break;
					}
				}
			}
		}
		
		return vLabelCabang;
	}
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T01managementadj rec = (T01managementadj) data;

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
                info.setParent(windowT01managementadjList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTanggal()));
                lc.setParent(item);
                
                lc = new Listcell(getLabelCabang(rec.getCabang()));
                lc.setParent(item);
                
                lc = new Listcell(rec.getSales());
                lc.setParent(item);
                
                lc = new Listcell(rec.getKeterangan());
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberNonDecimal(rec.getAmountHw01()));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberNonDecimal(rec.getAmountPs01()));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberNonDecimal(rec.getAmountPs02()));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberNonDecimal(rec.getAmountPs03()));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberNonDecimal(rec.getAmountPs04()));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberNonDecimal(rec.getAmountPs05()));
                lc.setParent(item);
             
      
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT01managementadjItem");
              }
        };
    }
    
	private EventListener onPaging_T01managementadjList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT01managementadjList = pageNo * paging_T01managementadjList.getPageSize();
		         setModelT01managementadjList();
			}
		};
	}


    public void onDoubleClickedT01managementadjItem(Event event) {
		if (listBoxT01managementadj.getSelectedItem() != null) {
			T01managementadj selectedData = (T01managementadj) listBoxT01managementadj.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT01managementadj(selectedData);
	
	            if (getT01managementadjMainCtrl().getT01managementadjDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT01managementadjMainCtrl().tabT01managementadjDetail, null));
	            } else if (getT01managementadjMainCtrl().getT01managementadjDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT01managementadjMainCtrl().tabT01managementadjDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT01managementadjMainCtrl().tabT01managementadjDetail, selectedData));
	            
	            getT01managementadjMainCtrl().getT01managementadjDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT01managementadj(Event event) {
		if (listBoxT01managementadj.getSelectedItem() != null) {
			T01managementadj selectedData = (T01managementadj) listBoxT01managementadj.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT01managementadjMainCtrl().getT01managementadjDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT01managementadjMainCtrl().tabT01managementadjDetail, null));
		
				} else if (getT01managementadjMainCtrl().getT01managementadjDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT01managementadjMainCtrl().tabT01managementadjDetail, null));
				}
				
				getT01managementadjMainCtrl().setSelectedT01managementadj(selectedData);
				
			}
		}	
    }
    
    public void onSort$listheader_T01managementadjList_Tanggal(Event event) {
    	sortingData(listheader_T01managementadjList_Tanggal, T01managementadjComparator.COMPARE_BY_TANGGAL);
    }
    
    public void onSort$listheader_T01managementadjList_Cabang(Event event) {
    	sortingData(listheader_T01managementadjList_Cabang, T01managementadjComparator.COMPARE_BY_CABANG);
    }
    
    public void onSort$listheader_T01managementadjList_Sales(Event event) { 
    	sortingData(listheader_T01managementadjList_Sales, T01managementadjComparator.COMPARE_BY_SALES);
    }
    
    public void onSort$listheader_T01managementadjList_Keterangan(Event event) {
    	sortingData(listheader_T01managementadjList_Keterangan, T01managementadjComparator.COMPARE_BY_KETERANGAN);
    }
    
    public void onSort$listheader_T01managementadjList_AmountHw01(Event event) {
    	sortingData(listheader_T01managementadjList_AmountHw01, T01managementadjComparator.COMPARE_BY_AMOUNTHW01);
    }
    
    public void onSort$listheader_T01managementadjList_AmountPs01(Event event) {
    	sortingData(listheader_T01managementadjList_AmountPs01, T01managementadjComparator.COMPARE_BY_AMOUNTPS01);
    }
    
    public void onSort$listheader_T01managementadjList_AmountPs02(Event event) {
    	sortingData(listheader_T01managementadjList_AmountPs02, T01managementadjComparator.COMPARE_BY_AMOUNTPS02);
    }
    
    public void onSort$listheader_T01managementadjList_AmountPs03(Event event) {
    	sortingData(listheader_T01managementadjList_AmountPs03, T01managementadjComparator.COMPARE_BY_AMOUNTPS03);
    }
    
    public void onSort$listheader_T01managementadjList_AmountPs04(Event event) {
    	sortingData(listheader_T01managementadjList_AmountPs04, T01managementadjComparator.COMPARE_BY_AMOUNTPS04);
    }
    
    public void onSort$listheader_T01managementadjList_AmountPs05(Event event) {
    	sortingData(listheader_T01managementadjList_AmountPs05, T01managementadjComparator.COMPARE_BY_AMOUNTPS05);
    }
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T01managementadjList, new T01managementadjComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T01managementadjList, new T01managementadjComparator(true, sortBy));
    		}
    	}
    	
    	setModelT01managementadjList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T01managementadjList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT01managementadjList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	txtTanggal.setValue(null);
    	txtSales.setValue(null);
    	txtKeterangan.setValue(null);
    	txtAmountHw01.setValue(null);
    	txtAmountPs01.setValue(null);
    	txtAmountPs02.setValue(null);
    	txtAmountPs03.setValue(null);
    	txtAmountPs04.setValue(null);
    	txtAmountPs05.setValue(null);
    	
    	cmbCabang.setValue(listCabang.getItemAtIndex(0).getLabel());
		listCabang.setSelectedItem(listCabang.getItemAtIndex(0));
    	vCabang="ALL";
    	//startT01managementadjList = 0;
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


    public void setSelectedT01managementadj(T01managementadj selectedT01managementadj) {
        getT01managementadjMainCtrl().setSelectedT01managementadj(selectedT01managementadj);
    }

    public T01managementadj getSelectedT01managementadj() {
        return getT01managementadjMainCtrl().getSelectedT01managementadj();
    }

    public void setT01managementadjMainCtrl(T01managementadjMainCtrl T01managementadjMainCtrl) {
        this.T01managementadjMainCtrl = T01managementadjMainCtrl;
    }

    public T01managementadjMainCtrl getT01managementadjMainCtrl() {
        return this.T01managementadjMainCtrl;
    }

}
