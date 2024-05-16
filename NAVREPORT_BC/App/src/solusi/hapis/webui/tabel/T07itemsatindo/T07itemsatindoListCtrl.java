package solusi.hapis.webui.tabel.T07itemsatindo;

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

import solusi.hapis.backend.tabel.model.T07itemsatindo;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 05-07-2018
 */

public class T07itemsatindoListCtrl extends GFCBaseListCtrl<T07itemsatindo> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT07itemsatindoList; 
    protected Panel panelT07itemsatindoList;
 	
	// Search Box Components 
	protected Textbox txtBerlaku;
	protected Textbox txtNoItem;
	protected Textbox txtSatAmountKomisi;
	protected Textbox txtIdmrAmountKomisi;
	protected Textbox txtSatAmountBns;
	protected Textbox txtIdmrAmountBns;


	

	// Paging and list
    protected Borderlayout borderLayout_T07itemsatindoList;
    protected Paging paging_T07itemsatindoList;
    private int startT07itemsatindoList;
	private List<T07itemsatindo> list_T07itemsatindoList = new ArrayList<T07itemsatindo>();
    private ListModelList modelList_T07itemsatindoList = new ListModelList();
    protected Listbox listBoxT07itemsatindo;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T07itemsatindoMainCtrl T07itemsatindoMainCtrl;

    // List Header
    protected Listheader listheader_T07itemsatindoList_TglBerlaku;
    protected Listheader listheader_T07itemsatindoList_NoItem;
    protected Listheader listheader_T07itemsatindoList_SatAmountKomisi;
    protected Listheader listheader_T07itemsatindoList_IdmrAmountKomisi;
    protected Listheader listheader_T07itemsatindoList_SatAmountBns;
    protected Listheader listheader_T07itemsatindoList_IdmrAmountBns;
    
    public T07itemsatindoListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T07itemsatindoList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T07itemsatindoList.setDetailed(true);
		paging_T07itemsatindoList.addEventListener("onPaging", onPaging_T07itemsatindoList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT07itemsatindoMainCtrl((T07itemsatindoMainCtrl) arg.get("ModuleMainController"));
		
		    getT07itemsatindoMainCtrl().setT07itemsatindoListCtrl(this);
		}
        
		
    }    
    

    public void onCreate$windowT07itemsatindoList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
        listheader_T07itemsatindoList_TglBerlaku.setSortAscending(new T07itemsatindoComparator(true, T07itemsatindoComparator.COMPARE_BY_TGLBERLAKU));
        listheader_T07itemsatindoList_TglBerlaku.setSortDescending(new T07itemsatindoComparator(false, T07itemsatindoComparator.COMPARE_BY_TGLBERLAKU));        
        
        listheader_T07itemsatindoList_NoItem.setSortAscending(new T07itemsatindoComparator(true, T07itemsatindoComparator.COMPARE_BY_NOITEM));
        listheader_T07itemsatindoList_NoItem.setSortDescending(new T07itemsatindoComparator(false, T07itemsatindoComparator.COMPARE_BY_NOITEM));        
       
        listheader_T07itemsatindoList_SatAmountKomisi.setSortAscending(new T07itemsatindoComparator(true, T07itemsatindoComparator.COMPARE_BY_SATAMOUNTKOMISI));
        listheader_T07itemsatindoList_SatAmountKomisi.setSortDescending(new T07itemsatindoComparator(false, T07itemsatindoComparator.COMPARE_BY_SATAMOUNTKOMISI));        
       
        listheader_T07itemsatindoList_IdmrAmountKomisi.setSortAscending(new T07itemsatindoComparator(true, T07itemsatindoComparator.COMPARE_BY_IDMRAMOUNTKOMISI));
        listheader_T07itemsatindoList_IdmrAmountKomisi.setSortDescending(new T07itemsatindoComparator(false, T07itemsatindoComparator.COMPARE_BY_IDMRAMOUNTKOMISI));        
       
        listheader_T07itemsatindoList_SatAmountBns.setSortAscending(new T07itemsatindoComparator(true, T07itemsatindoComparator.COMPARE_BY_SATAMOUNTBNS));
        listheader_T07itemsatindoList_SatAmountBns.setSortDescending(new T07itemsatindoComparator(false, T07itemsatindoComparator.COMPARE_BY_SATAMOUNTBNS));        
       
        listheader_T07itemsatindoList_IdmrAmountBns.setSortAscending(new T07itemsatindoComparator(true, T07itemsatindoComparator.COMPARE_BY_IDMRAMOUNTBNS));
        listheader_T07itemsatindoList_IdmrAmountBns.setSortDescending(new T07itemsatindoComparator(false, T07itemsatindoComparator.COMPARE_BY_IDMRAMOUNTBNS));        
       
        
        getSearchData();
        
        listBoxT07itemsatindo.setItemRenderer(renderTable());
        
        windowT07itemsatindoList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		if (CommonUtils.isValidDateFormat(txtBerlaku.getValue())) {
			Date tglInv1 = CommonUtils.convertStringToDate(txtBerlaku.getValue());
			Date tglInv2 = CommonUtils.createSecondParameterForSearch(txtBerlaku.getValue());
			parameterInput.put("tanggalBerlakufrom", tglInv1);
			parameterInput.put("tanggalBerlakuto", tglInv2);
		}
		
		if (CommonUtils.isNotEmpty(txtNoItem.getValue())) {
			parameterInput.put("noItem", txtNoItem.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtSatAmountKomisi.getValue())) {
			parameterInput.put("satAmountKomisi", new BigDecimal(txtSatAmountKomisi.getValue()));
		}

		if (CommonUtils.isNotEmpty(txtIdmrAmountKomisi.getValue())) {
			parameterInput.put("idmrAmountKomisi", new BigDecimal(txtIdmrAmountKomisi.getValue()));
		}
		
		if (CommonUtils.isNotEmpty(txtSatAmountBns.getValue())) {
			parameterInput.put("satAmountBns", new BigDecimal(txtSatAmountBns.getValue()));
		}
		
		if (CommonUtils.isNotEmpty(txtIdmrAmountBns.getValue())) {
			parameterInput.put("idmrAmountBns", new BigDecimal(txtIdmrAmountBns.getValue()));
		}
		
		
		list_T07itemsatindoList.clear();
		
		List<T07itemsatindo> tempListT07itemsatindo = getT07itemsatindoMainCtrl().getT07itemsatindoService().getListT07itemsatindo(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT07itemsatindo)) {
			list_T07itemsatindoList.addAll(tempListT07itemsatindo);
			startT07itemsatindoList = 0;
			paging_T07itemsatindoList.setActivePage(0);
		}

		setModelT07itemsatindoList();
	}

	// Pembagian data untuk paging
	private void setModelT07itemsatindoList() {		
		modelList_T07itemsatindoList.clear();
		
		T07itemsatindo selectedData = null;
		if (CommonUtils.isNotEmpty(list_T07itemsatindoList)){
			
			int endT07itemsatindoList = 0;
			if(startT07itemsatindoList + paging_T07itemsatindoList.getPageSize() < list_T07itemsatindoList.size()) {
				endT07itemsatindoList = startT07itemsatindoList + paging_T07itemsatindoList.getPageSize();
			} else {
				endT07itemsatindoList = list_T07itemsatindoList.size();
			}
			
			if (startT07itemsatindoList > endT07itemsatindoList) {
				startT07itemsatindoList = 0;
				paging_T07itemsatindoList.setActivePage(0);
			}
			
			modelList_T07itemsatindoList.addAll(list_T07itemsatindoList.subList(startT07itemsatindoList, endT07itemsatindoList));

			paging_T07itemsatindoList.setDetailed(true);
			paging_T07itemsatindoList.setTotalSize(list_T07itemsatindoList.size());
			
			listBoxT07itemsatindo.setModel(modelList_T07itemsatindoList);
			listBoxT07itemsatindo.setSelectedIndex(0);
	
			selectedData = list_T07itemsatindoList.get(startT07itemsatindoList);
		} else {
			paging_T07itemsatindoList.setDetailed(false);
			listBoxT07itemsatindo.setModel(modelList_T07itemsatindoList);
			paging_T07itemsatindoList.setTotalSize(0);
		}
		
		getT07itemsatindoMainCtrl().setSelectedT07itemsatindo(selectedData);

	}
	

    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T07itemsatindo rec = (T07itemsatindo) data;

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
                info.setParent(windowT07itemsatindoList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTglBerlaku()));
                lc.setParent(item);
                
                lc = new Listcell(rec.getNoItem());
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getSatAmountKomisi(), "#,###,###"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getIdmrAmountKomisi(), "#,###,###"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getSatAmountBns(), "#,###,###"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getIdmrAmountBns(), "#,###,###"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);

				
      
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT07itemsatindoItem");
              }
        };
    }
    
	private EventListener onPaging_T07itemsatindoList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT07itemsatindoList = pageNo * paging_T07itemsatindoList.getPageSize();
		         setModelT07itemsatindoList();
			}
		};
	}


    public void onDoubleClickedT07itemsatindoItem(Event event) {
		if (listBoxT07itemsatindo.getSelectedItem() != null) {
			T07itemsatindo selectedData = (T07itemsatindo) listBoxT07itemsatindo.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT07itemsatindo(selectedData);
	
	            if (getT07itemsatindoMainCtrl().getT07itemsatindoDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT07itemsatindoMainCtrl().tabT07itemsatindoDetail, null));
	            } else if (getT07itemsatindoMainCtrl().getT07itemsatindoDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT07itemsatindoMainCtrl().tabT07itemsatindoDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT07itemsatindoMainCtrl().tabT07itemsatindoDetail, selectedData));
	            
	            getT07itemsatindoMainCtrl().getT07itemsatindoDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT07itemsatindo(Event event) {
		if (listBoxT07itemsatindo.getSelectedItem() != null) {
			T07itemsatindo selectedData = (T07itemsatindo) listBoxT07itemsatindo.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT07itemsatindoMainCtrl().getT07itemsatindoDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT07itemsatindoMainCtrl().tabT07itemsatindoDetail, null));
		
				} else if (getT07itemsatindoMainCtrl().getT07itemsatindoDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT07itemsatindoMainCtrl().tabT07itemsatindoDetail, null));
				}
				
				getT07itemsatindoMainCtrl().setSelectedT07itemsatindo(selectedData);
				
			}
		}	
    }
    

    public void onSort$listheader_T07itemsatindoList_TglBerlaku(Event event) { 
    	sortingData(listheader_T07itemsatindoList_TglBerlaku, T07itemsatindoComparator.COMPARE_BY_TGLBERLAKU);
    }
    
    public void onSort$listheader_T07itemsatindoList_NoItem(Event event) { 
    	sortingData(listheader_T07itemsatindoList_NoItem, T07itemsatindoComparator.COMPARE_BY_NOITEM);
    }
    
    public void onSort$listheader_T07itemsatindoList_SatAmountKomisi(Event event) { 
    	sortingData(listheader_T07itemsatindoList_SatAmountKomisi, T07itemsatindoComparator.COMPARE_BY_SATAMOUNTKOMISI);
    }
    
    public void onSort$listheader_T07itemsatindoList_IdmrAmountKomisi(Event event) { 
    	sortingData(listheader_T07itemsatindoList_IdmrAmountKomisi, T07itemsatindoComparator.COMPARE_BY_IDMRAMOUNTKOMISI);
    }
    
    public void onSort$listheader_T07itemsatindoList_SatAmountBns(Event event) { 
    	sortingData(listheader_T07itemsatindoList_SatAmountBns, T07itemsatindoComparator.COMPARE_BY_SATAMOUNTBNS);
    }
    
    public void onSort$listheader_T07itemsatindoList_IdmrAmountBns(Event event) { 
    	sortingData(listheader_T07itemsatindoList_IdmrAmountBns, T07itemsatindoComparator.COMPARE_BY_IDMRAMOUNTBNS);
    }
    
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T07itemsatindoList, new T07itemsatindoComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T07itemsatindoList, new T07itemsatindoComparator(true, sortBy));
    		}
    	}
    	
    	setModelT07itemsatindoList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T07itemsatindoList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT07itemsatindoList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes

    	txtBerlaku.setValue(null);
    	txtNoItem.setValue(null);
    	txtSatAmountKomisi.setValue(null);
    	txtIdmrAmountKomisi.setValue(null);
    	txtSatAmountBns.setValue(null);
    	txtIdmrAmountBns.setValue(null);

    	//startT07itemsatindoList = 0;
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


    public void setSelectedT07itemsatindo(T07itemsatindo selectedT07itemsatindo) {
        getT07itemsatindoMainCtrl().setSelectedT07itemsatindo(selectedT07itemsatindo);
    }

    public T07itemsatindo getSelectedT07itemsatindo() {
        return getT07itemsatindoMainCtrl().getSelectedT07itemsatindo();
    }

    public void setT07itemsatindoMainCtrl(T07itemsatindoMainCtrl T07itemsatindoMainCtrl) {
        this.T07itemsatindoMainCtrl = T07itemsatindoMainCtrl;
    }

    public T07itemsatindoMainCtrl getT07itemsatindoMainCtrl() {
        return this.T07itemsatindoMainCtrl;
    }

}
