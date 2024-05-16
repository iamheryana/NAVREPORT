package solusi.hapis.webui.finance.T03CetakSlip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

import solusi.hapis.backend.navbi.model.T03CetakSlip;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 23-01-2020
 */

public class T03CetakSlipListCtrl extends GFCBaseListCtrl<T03CetakSlip> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT03CetakSlipList; 
    protected Panel panelT03CetakSlipList;
 	
	// Search Box Components 



	protected Textbox txtNoVoucher;
	protected Textbox txtNoCheque;
	protected Combobox cmbJenisTrans;
	protected Combobox cmbCompany;
	protected Textbox txtPrintCount;
	
	
	// Paging and list
    protected Borderlayout borderLayout_T03CetakSlipList;
    protected Paging paging_T03CetakSlipList;
    private int startT03CetakSlipList;
	private List<T03CetakSlip> list_T03CetakSlipList = new ArrayList<T03CetakSlip>();
    private ListModelList modelList_T03CetakSlipList = new ListModelList();
    protected Listbox listBoxT03CetakSlip;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T03CetakSlipMainCtrl T03CetakSlipMainCtrl;

    // List Header		
    protected Listheader listheader_T03CetakSlipList_JenisTrans;
    protected Listheader listheader_T03CetakSlipList_Company;
    protected Listheader listheader_T03CetakSlipList_NoVoucher;
    protected Listheader listheader_T03CetakSlipList_NoCheque;
    protected Listheader listheader_T03CetakSlipList_PrintCount;
		
		
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T03CetakSlipListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T03CetakSlipList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T03CetakSlipList.setDetailed(true);
		paging_T03CetakSlipList.addEventListener("onPaging", onPaging_T03CetakSlipList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT03CetakSlipMainCtrl((T03CetakSlipMainCtrl) arg.get("ModuleMainController"));
		
		    getT03CetakSlipMainCtrl().setT03CetakSlipListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowT03CetakSlipList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
        
        listheader_T03CetakSlipList_JenisTrans.setSortAscending(new T03CetakSlipComparator(true, T03CetakSlipComparator.COMPARE_BY_JENISTRANS));
        listheader_T03CetakSlipList_JenisTrans.setSortDescending(new T03CetakSlipComparator(false, T03CetakSlipComparator.COMPARE_BY_JENISTRANS));        
         
        listheader_T03CetakSlipList_Company.setSortAscending(new T03CetakSlipComparator(true, T03CetakSlipComparator.COMPARE_BY_COMPANY));
        listheader_T03CetakSlipList_Company.setSortDescending(new T03CetakSlipComparator(false, T03CetakSlipComparator.COMPARE_BY_COMPANY));        
       
        listheader_T03CetakSlipList_NoVoucher.setSortAscending(new T03CetakSlipComparator(true, T03CetakSlipComparator.COMPARE_BY_NOVOUCHER));
        listheader_T03CetakSlipList_NoVoucher.setSortDescending(new T03CetakSlipComparator(false, T03CetakSlipComparator.COMPARE_BY_NOVOUCHER));        
       
        listheader_T03CetakSlipList_NoCheque.setSortAscending(new T03CetakSlipComparator(true, T03CetakSlipComparator.COMPARE_BY_NOCHEQUE));
        listheader_T03CetakSlipList_NoCheque.setSortDescending(new T03CetakSlipComparator(false, T03CetakSlipComparator.COMPARE_BY_NOCHEQUE));        
               
        listheader_T03CetakSlipList_PrintCount.setSortAscending(new T03CetakSlipComparator(true, T03CetakSlipComparator.COMPARE_BY_PRINTCOUNT));
        listheader_T03CetakSlipList_PrintCount.setSortDescending(new T03CetakSlipComparator(false, T03CetakSlipComparator.COMPARE_BY_PRINTCOUNT));        
        
        
        
        getSearchData();
        
        listBoxT03CetakSlip.setItemRenderer(renderTable());
        
        windowT03CetakSlipList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		if (cmbJenisTrans.getSelectedIndex() != 0) {
			parameterInput.put("jenisTrans", (String) cmbJenisTrans.getSelectedItem().getValue());
		}
		
		if (cmbCompany.getSelectedIndex() != 0) {
			parameterInput.put("company", (String) cmbCompany.getSelectedItem().getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtNoVoucher.getValue())) {
			parameterInput.put("noVoucher", txtNoVoucher.getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtNoCheque.getValue())) {
			parameterInput.put("noCheque", txtNoCheque.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtPrintCount.getValue())) {
			int vPrintCount = Integer.valueOf(txtPrintCount.getValue());
			parameterInput.put("printCount", vPrintCount);
		}		
		
		list_T03CetakSlipList.clear();
		
		List<T03CetakSlip> tempListT03CetakSlip = getT03CetakSlipMainCtrl().getT03CetakSlipService().getListT03CetakSlip(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT03CetakSlip)) {
			list_T03CetakSlipList.addAll(tempListT03CetakSlip);
			startT03CetakSlipList = 0;
			paging_T03CetakSlipList.setActivePage(0);
		}

		setModelT03CetakSlipList();
	}

	// Pembagian data untuk paging
	private void setModelT03CetakSlipList() {		
		modelList_T03CetakSlipList.clear();
		
		T03CetakSlip selectedData = null;
		if (CommonUtils.isNotEmpty(list_T03CetakSlipList)){
			
			int endT03CetakSlipList = 0;
			if(startT03CetakSlipList + paging_T03CetakSlipList.getPageSize() < list_T03CetakSlipList.size()) {
				endT03CetakSlipList = startT03CetakSlipList + paging_T03CetakSlipList.getPageSize();
			} else {
				endT03CetakSlipList = list_T03CetakSlipList.size();
			}
			
			if (startT03CetakSlipList > endT03CetakSlipList) {
				startT03CetakSlipList = 0;
				paging_T03CetakSlipList.setActivePage(0);
			}
			
			modelList_T03CetakSlipList.addAll(list_T03CetakSlipList.subList(startT03CetakSlipList, endT03CetakSlipList));

			paging_T03CetakSlipList.setDetailed(true);
			paging_T03CetakSlipList.setTotalSize(list_T03CetakSlipList.size());
			
			listBoxT03CetakSlip.setModel(modelList_T03CetakSlipList);
			listBoxT03CetakSlip.setSelectedIndex(0);
	
			selectedData = list_T03CetakSlipList.get(startT03CetakSlipList);
		} else {
			paging_T03CetakSlipList.setDetailed(false);
			listBoxT03CetakSlip.setModel(modelList_T03CetakSlipList);
			paging_T03CetakSlipList.setTotalSize(0);
		}
		
		getT03CetakSlipMainCtrl().setSelectedT03CetakSlip(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T03CetakSlip rec = (T03CetakSlip) data;

                Listcell lc;

                // Audit Trail
                lc = new Listcell();
                lc.setImage("/images/icons/information_icon.jpg");
                lc.setStyle("cursor: help");
                Popup info = new Popup();
                Vbox vbox = new Vbox();
                Label lblPrintBy = new Label("Cheque dicetak Oleh = " + rec.getPrintBy());
                Label lblPrintOn = new Label("Cheque dicetak Pada = " + CommonUtils.convertDateToString(rec.getPrintOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                
                Label lblRePrintBy = new Label("Cheque - Print Counter di-Reset ulang Oleh = " + rec.getReprintBy());
                Label lblRePrintOn = new Label("Cheque - Print Counter di-Reset ulang Pada = " + CommonUtils.convertDateToString(rec.getReprintOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                Label lblPrintCount = new Label("Cheque - Print Counter = " + rec.getPrintCount());
                
                Label lblCreateBy = new Label("Dibuat Oleh = " + rec.getCreatedBy());
                Label lblCreateOn = new Label("Dibuat Pada = " + CommonUtils.convertDateToString(rec.getCreatedOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                Label lblUpdateBy = new Label("Dimodifikasi Oleh = " + rec.getUpdatedBy());
                Label lblUpdateOn = new Label("Dimodifikasi Pada = " + CommonUtils.convertDateToString(rec.getUpdatedOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                Label lblVersion = new Label("Version = " + rec.getVersion());
                
                lblPrintBy.setParent(vbox);
                lblPrintOn.setParent(vbox);
                lblPrintCount.setParent(vbox);
                lblRePrintBy.setParent(vbox);
                lblRePrintOn.setParent(vbox);
                
                lblCreateBy.setParent(vbox);
                lblCreateOn.setParent(vbox);
                lblUpdateBy.setParent(vbox);
                lblUpdateOn.setParent(vbox);
                lblVersion.setParent(vbox);
                vbox.setParent(info);
                info.setParent(windowT03CetakSlipList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                
     
                lc = new Listcell(getT03CetakSlipMainCtrl().getDescJenisTrans(rec.getJenisTrans()));
                lc.setParent(item);
                
                lc = new Listcell(getT03CetakSlipMainCtrl().getDescCompany(rec.getCompany()));
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoVoucher());
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoCheque());
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getPrintCount(), "#,###,###"));				
				lc.setStyle("text-align:right");
                lc.setParent(item);
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT03CetakSlipItem");
              }
        };
    }
    
	private EventListener onPaging_T03CetakSlipList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT03CetakSlipList = pageNo * paging_T03CetakSlipList.getPageSize();
		         setModelT03CetakSlipList();
			}
		};
	}


    public void onDoubleClickedT03CetakSlipItem(Event event) {
		if (listBoxT03CetakSlip.getSelectedItem() != null) {
			T03CetakSlip selectedData = (T03CetakSlip) listBoxT03CetakSlip.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT03CetakSlip(selectedData);
	
	            if (getT03CetakSlipMainCtrl().getT03CetakSlipDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT03CetakSlipMainCtrl().tabT03CetakSlipDetail, null));
	            } else if (getT03CetakSlipMainCtrl().getT03CetakSlipDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT03CetakSlipMainCtrl().tabT03CetakSlipDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT03CetakSlipMainCtrl().tabT03CetakSlipDetail, selectedData));
	            
	            getT03CetakSlipMainCtrl().getT03CetakSlipDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT03CetakSlip(Event event) {
		if (listBoxT03CetakSlip.getSelectedItem() != null) {
			T03CetakSlip selectedData = (T03CetakSlip) listBoxT03CetakSlip.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT03CetakSlipMainCtrl().getT03CetakSlipDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT03CetakSlipMainCtrl().tabT03CetakSlipDetail, null));
		
				} else if (getT03CetakSlipMainCtrl().getT03CetakSlipDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT03CetakSlipMainCtrl().tabT03CetakSlipDetail, null));
				}
				
				getT03CetakSlipMainCtrl().setSelectedT03CetakSlip(selectedData);
				
			}
		}	
    }
    
    
    
    public void onSort$listheader_T03CetakSlipList_JenisTrans(Event event) {
    	sortingData(listheader_T03CetakSlipList_JenisTrans, T03CetakSlipComparator.COMPARE_BY_JENISTRANS);
    }
    
    public void onSort$listheader_T03CetakSlipList_Company(Event event) {
    	sortingData(listheader_T03CetakSlipList_Company, T03CetakSlipComparator.COMPARE_BY_COMPANY);
    }

    public void onSort$listheader_T03CetakSlipList_NoVoucher(Event event) {
    	sortingData(listheader_T03CetakSlipList_NoVoucher, T03CetakSlipComparator.COMPARE_BY_NOVOUCHER);
    }
    
    public void onSort$listheader_T03CetakSlipList_NoCheque(Event event) {
    	sortingData(listheader_T03CetakSlipList_NoCheque, T03CetakSlipComparator.COMPARE_BY_NOCHEQUE);
    }

    public void onSort$listheader_T03CetakSlipList_PrintCount(Event event) {
    	sortingData(listheader_T03CetakSlipList_PrintCount, T03CetakSlipComparator.COMPARE_BY_PRINTCOUNT);
    }
    
    
      
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T03CetakSlipList, new T03CetakSlipComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T03CetakSlipList, new T03CetakSlipComparator(true, sortBy));
    		}
    	}
    	
    	setModelT03CetakSlipList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T03CetakSlipList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT03CetakSlipList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
    	cmbJenisTrans.setSelectedIndex(0);
    	cmbCompany.setSelectedIndex(0);
    	txtNoVoucher.setValue(null);    	
    	txtNoCheque.setValue(null);
    	txtPrintCount.setValue(null);
   
    	//startT03CetakSlipList = 0;
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


    public void setSelectedT03CetakSlip(T03CetakSlip selectedT03CetakSlip) {
        getT03CetakSlipMainCtrl().setSelectedT03CetakSlip(selectedT03CetakSlip);
    }

    public T03CetakSlip getSelectedT03CetakSlip() {
        return getT03CetakSlipMainCtrl().getSelectedT03CetakSlip();
    }

    public void setT03CetakSlipMainCtrl(T03CetakSlipMainCtrl T03CetakSlipMainCtrl) {
        this.T03CetakSlipMainCtrl = T03CetakSlipMainCtrl;
    }

    public T03CetakSlipMainCtrl getT03CetakSlipMainCtrl() {
        return this.T03CetakSlipMainCtrl;
    }

}
