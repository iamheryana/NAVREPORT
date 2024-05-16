package solusi.hapis.webui.logistic.T01SoAdj;


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

import solusi.hapis.backend.navbi.model.T01SoAdj;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 28-08-2019
 */

public class T01SoAdjListCtrl extends GFCBaseListCtrl<T01SoAdj> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT01SoAdjList; 
    protected Panel panelT01SoAdjList;
 	
	// Search Box Components 
    


	protected Textbox txtNoSo;
	protected Combobox cmbUseQty;
	protected Textbox txtQty;
	protected Textbox txtEstRealisasi;
	protected Combobox cmbJenisPayment;
	protected Combobox cmbUseCclDate;
	protected Textbox txtAddDays;
	protected Textbox txtKeteranganDp;
	
	// Paging and list
    protected Borderlayout borderLayout_T01SoAdjList;
    protected Paging paging_T01SoAdjList;
    private int startT01SoAdjList;
	private List<T01SoAdj> list_T01SoAdjList = new ArrayList<T01SoAdj>();
    private ListModelList modelList_T01SoAdjList = new ListModelList();
    protected Listbox listBoxT01SoAdj;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T01SoAdjMainCtrl T01SoAdjMainCtrl;

    // List Header		
    protected Listheader listheader_T01SoAdjList_NoSo;
    protected Listheader listheader_T01SoAdjList_JenisPayment;
    protected Listheader listheader_T01SoAdjList_UseQty;
    protected Listheader listheader_T01SoAdjList_Qty;
    protected Listheader listheader_T01SoAdjList_EstRealisasi;
    protected Listheader listheader_T01SoAdjList_UseCclDate;
    protected Listheader listheader_T01SoAdjList_AddDays;
    protected Listheader listheader_T01SoAdjList_KeteranganDp;
			
		
		
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T01SoAdjListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T01SoAdjList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T01SoAdjList.setDetailed(true);
		paging_T01SoAdjList.addEventListener("onPaging", onPaging_T01SoAdjList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT01SoAdjMainCtrl((T01SoAdjMainCtrl) arg.get("ModuleMainController"));
		
		    getT01SoAdjMainCtrl().setT01SoAdjListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowT01SoAdjList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
      
        
        listheader_T01SoAdjList_NoSo.setSortAscending(new T01SoAdjComparator(true, T01SoAdjComparator.COMPARE_BY_NOSO));
        listheader_T01SoAdjList_NoSo.setSortDescending(new T01SoAdjComparator(false, T01SoAdjComparator.COMPARE_BY_NOSO));        
         
        listheader_T01SoAdjList_JenisPayment.setSortAscending(new T01SoAdjComparator(true, T01SoAdjComparator.COMPARE_BY_JENISPAYMENT));
        listheader_T01SoAdjList_JenisPayment.setSortDescending(new T01SoAdjComparator(false, T01SoAdjComparator.COMPARE_BY_JENISPAYMENT));        
       
        
        listheader_T01SoAdjList_UseQty.setSortAscending(new T01SoAdjComparator(true, T01SoAdjComparator.COMPARE_BY_USEQTY));
        listheader_T01SoAdjList_UseQty.setSortDescending(new T01SoAdjComparator(false, T01SoAdjComparator.COMPARE_BY_USEQTY));
        
        
        listheader_T01SoAdjList_Qty.setSortAscending(new T01SoAdjComparator(true, T01SoAdjComparator.COMPARE_BY_QTY));
        listheader_T01SoAdjList_Qty.setSortDescending(new T01SoAdjComparator(false, T01SoAdjComparator.COMPARE_BY_QTY));        
       
        listheader_T01SoAdjList_EstRealisasi.setSortAscending(new T01SoAdjComparator(true, T01SoAdjComparator.COMPARE_BY_ESTREALISASI));
        listheader_T01SoAdjList_EstRealisasi.setSortDescending(new T01SoAdjComparator(false, T01SoAdjComparator.COMPARE_BY_ESTREALISASI));        
               
        listheader_T01SoAdjList_UseCclDate.setSortAscending(new T01SoAdjComparator(true, T01SoAdjComparator.COMPARE_BY_USECCLDATE));
        listheader_T01SoAdjList_UseCclDate.setSortDescending(new T01SoAdjComparator(false, T01SoAdjComparator.COMPARE_BY_USECCLDATE));                
        
        listheader_T01SoAdjList_AddDays.setSortAscending(new T01SoAdjComparator(true, T01SoAdjComparator.COMPARE_BY_ADDDAYS));
        listheader_T01SoAdjList_AddDays.setSortDescending(new T01SoAdjComparator(false, T01SoAdjComparator.COMPARE_BY_ADDDAYS));      
        
        
        listheader_T01SoAdjList_KeteranganDp.setSortAscending(new T01SoAdjComparator(true, T01SoAdjComparator.COMPARE_BY_KETERANGANDP));
        listheader_T01SoAdjList_KeteranganDp.setSortDescending(new T01SoAdjComparator(false, T01SoAdjComparator.COMPARE_BY_KETERANGANDP)); 

        
        getSearchData();
        
        listBoxT01SoAdj.setItemRenderer(renderTable());
        
        windowT01SoAdjList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (CommonUtils.isNotEmpty(txtNoSo.getValue())) {
			parameterInput.put("noSo", txtNoSo.getValue());
		}
		
		if (cmbUseQty.getSelectedIndex() != 0) {
			parameterInput.put("useQty", (String) cmbUseQty.getSelectedItem().getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtQty.getValue())) {
			BigDecimal vQty = new BigDecimal (Integer.valueOf(txtQty.getValue()));
			parameterInput.put("qty", vQty);
		}
		
		if (cmbJenisPayment.getSelectedIndex() != 0) {
			parameterInput.put("jenisPayment", (String) cmbJenisPayment.getSelectedItem().getValue());
		}
		
		
		if (CommonUtils.isValidDateFormat(txtEstRealisasi.getValue())) {
			Date tglInv1 = CommonUtils.convertStringToDate(txtEstRealisasi.getValue());
			Date tglInv2 = CommonUtils.createSecondParameterForSearch(txtEstRealisasi.getValue());
			parameterInput.put("estRealisasifrom", tglInv1);
			parameterInput.put("estRealisasito", tglInv2);
		}
		
		if (cmbUseCclDate.getSelectedIndex() != 0) {
			parameterInput.put("useCclDate", (String) cmbUseCclDate.getSelectedItem().getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtAddDays.getValue())) {
			int vAddDays = Integer.valueOf(txtAddDays.getValue());
			parameterInput.put("addDays", vAddDays);
		}
		
		
		if (CommonUtils.isNotEmpty(txtKeteranganDp.getValue())) {
			parameterInput.put("keteranganDp", txtKeteranganDp.getValue());
		}
		
		list_T01SoAdjList.clear();
		
		List<T01SoAdj> tempListT01SoAdj = getT01SoAdjMainCtrl().getT01SoAdjService().getListT01SoAdj(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT01SoAdj)) {
			list_T01SoAdjList.addAll(tempListT01SoAdj);
			startT01SoAdjList = 0;
			paging_T01SoAdjList.setActivePage(0);
		}

		setModelT01SoAdjList();
	}

	// Pembagian data untuk paging
	private void setModelT01SoAdjList() {		
		modelList_T01SoAdjList.clear();
		
		T01SoAdj selectedData = null;
		if (CommonUtils.isNotEmpty(list_T01SoAdjList)){
			
			int endT01SoAdjList = 0;
			if(startT01SoAdjList + paging_T01SoAdjList.getPageSize() < list_T01SoAdjList.size()) {
				endT01SoAdjList = startT01SoAdjList + paging_T01SoAdjList.getPageSize();
			} else {
				endT01SoAdjList = list_T01SoAdjList.size();
			}
			
			if (startT01SoAdjList > endT01SoAdjList) {
				startT01SoAdjList = 0;
				paging_T01SoAdjList.setActivePage(0);
			}
			
			modelList_T01SoAdjList.addAll(list_T01SoAdjList.subList(startT01SoAdjList, endT01SoAdjList));

			paging_T01SoAdjList.setDetailed(true);
			paging_T01SoAdjList.setTotalSize(list_T01SoAdjList.size());
			
			listBoxT01SoAdj.setModel(modelList_T01SoAdjList);
			listBoxT01SoAdj.setSelectedIndex(0);
	
			selectedData = list_T01SoAdjList.get(startT01SoAdjList);
		} else {
			paging_T01SoAdjList.setDetailed(false);
			listBoxT01SoAdj.setModel(modelList_T01SoAdjList);
			paging_T01SoAdjList.setTotalSize(0);
		}
		
		getT01SoAdjMainCtrl().setSelectedT01SoAdj(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T01SoAdj rec = (T01SoAdj) data;

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
                info.setParent(windowT01SoAdjList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                
                lc = new Listcell(rec.getNoSo());
                lc.setParent(item);
                
                lc = new Listcell(getT01SoAdjMainCtrl().getDescJnsPayment(rec.getJenisPayment()));
                lc.setParent(item);
                
                lc = new Listcell(((rec.getUseQty().equals("Y") ==true)?"Yes":"No"));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getQty(), "#,##0.00000"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.convertDateToString(rec.getEstRealisasi()));
	            lc.setParent(item);
	                	                         
	            lc = new Listcell(((rec.getUseCclDate().equals("Y") ==true)?"Yes":"No"));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getAddDays(), "#,##0"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				
				 lc = new Listcell(rec.getKeteranganDp());
	             lc.setParent(item);
				
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT01SoAdjItem");
              }
        };
    }
    
	private EventListener onPaging_T01SoAdjList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT01SoAdjList = pageNo * paging_T01SoAdjList.getPageSize();
		         setModelT01SoAdjList();
			}
		};
	}


    public void onDoubleClickedT01SoAdjItem(Event event) {
		if (listBoxT01SoAdj.getSelectedItem() != null) {
			T01SoAdj selectedData = (T01SoAdj) listBoxT01SoAdj.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT01SoAdj(selectedData);
	
	            if (getT01SoAdjMainCtrl().getT01SoAdjDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT01SoAdjMainCtrl().tabT01SoAdjDetail, null));
	            } else if (getT01SoAdjMainCtrl().getT01SoAdjDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT01SoAdjMainCtrl().tabT01SoAdjDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT01SoAdjMainCtrl().tabT01SoAdjDetail, selectedData));
	            
	            getT01SoAdjMainCtrl().getT01SoAdjDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT01SoAdj(Event event) {
		if (listBoxT01SoAdj.getSelectedItem() != null) {
			T01SoAdj selectedData = (T01SoAdj) listBoxT01SoAdj.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT01SoAdjMainCtrl().getT01SoAdjDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT01SoAdjMainCtrl().tabT01SoAdjDetail, null));
		
				} else if (getT01SoAdjMainCtrl().getT01SoAdjDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT01SoAdjMainCtrl().tabT01SoAdjDetail, null));
				}
				
				getT01SoAdjMainCtrl().setSelectedT01SoAdj(selectedData);
				
			}
		}	
    }
    

    
    public void onSort$listheader_T01SoAdjList_NoSo(Event event) {
    	sortingData(listheader_T01SoAdjList_NoSo, T01SoAdjComparator.COMPARE_BY_NOSO);
    }
    
    public void onSort$listheader_T01SoAdjList_JenisPayment(Event event) {
    	sortingData(listheader_T01SoAdjList_JenisPayment, T01SoAdjComparator.COMPARE_BY_JENISPAYMENT);
    }
    
    public void onSort$listheader_T01SoAdjList_UseQty(Event event) {
    	sortingData(listheader_T01SoAdjList_UseQty, T01SoAdjComparator.COMPARE_BY_USEQTY);
    }    

    public void onSort$listheader_T01SoAdjList_Qty(Event event) {
    	sortingData(listheader_T01SoAdjList_Qty, T01SoAdjComparator.COMPARE_BY_QTY);
    }
    
    public void onSort$listheader_T01SoAdjList_EstRealisasi(Event event) {
    	sortingData(listheader_T01SoAdjList_EstRealisasi, T01SoAdjComparator.COMPARE_BY_ESTREALISASI);
    }
    
    public void onSort$listheader_T01SoAdjList_UseCclDate(Event event) {
    	sortingData(listheader_T01SoAdjList_UseCclDate, T01SoAdjComparator.COMPARE_BY_USECCLDATE);
    }
    
    public void onSort$listheader_T01SoAdjList_AddDays(Event event) {
    	sortingData(listheader_T01SoAdjList_AddDays, T01SoAdjComparator.COMPARE_BY_ADDDAYS);
    }
    
    public void onSort$listheader_T01SoAdjList_KeteranganDp(Event event) {
    	sortingData(listheader_T01SoAdjList_KeteranganDp, T01SoAdjComparator.COMPARE_BY_KETERANGANDP);
    }
    
    
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T01SoAdjList, new T01SoAdjComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T01SoAdjList, new T01SoAdjComparator(true, sortBy));
    		}
    	}
    	
    	setModelT01SoAdjList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T01SoAdjList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT01SoAdjList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
    	
    	txtNoSo.setValue(null);
    	cmbJenisPayment.setSelectedIndex(0);
    	cmbUseQty.setSelectedIndex(0);
    	txtQty.setValue(null);
    	txtEstRealisasi.setValue(null);
    	cmbUseCclDate.setSelectedIndex(0);
    	txtAddDays.setValue(null);	
    	txtKeteranganDp.setValue(null);

    	
    	
    	//startT01SoAdjList = 0;
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


    public void setSelectedT01SoAdj(T01SoAdj selectedT01SoAdj) {
        getT01SoAdjMainCtrl().setSelectedT01SoAdj(selectedT01SoAdj);
    }

    public T01SoAdj getSelectedT01SoAdj() {
        return getT01SoAdjMainCtrl().getSelectedT01SoAdj();
    }

    public void setT01SoAdjMainCtrl(T01SoAdjMainCtrl T01SoAdjMainCtrl) {
        this.T01SoAdjMainCtrl = T01SoAdjMainCtrl;
    }

    public T01SoAdjMainCtrl getT01SoAdjMainCtrl() {
        return this.T01SoAdjMainCtrl;
    }

}
