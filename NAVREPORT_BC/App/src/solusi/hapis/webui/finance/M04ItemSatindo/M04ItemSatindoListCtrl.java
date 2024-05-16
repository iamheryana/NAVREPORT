package solusi.hapis.webui.finance.M04ItemSatindo;

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

import solusi.hapis.backend.navbi.model.M04ItemSatindo;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 29-04-2021
 */

public class M04ItemSatindoListCtrl extends GFCBaseListCtrl<M04ItemSatindo> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowM04ItemSatindoList; 
    protected Panel panelM04ItemSatindoList;
 	
	// Search Box Components 
	protected Textbox txtBerlaku;
	protected Textbox txtNoItem;
	protected Textbox txtSatAmountKomisi;
	protected Textbox txtIdmrAmountKomisi;
	protected Textbox txtSatAmountBns;
	protected Textbox txtIdmrAmountBns;


	

	// Paging and list
    protected Borderlayout borderLayout_M04ItemSatindoList;
    protected Paging paging_M04ItemSatindoList;
    private int startM04ItemSatindoList;
	private List<M04ItemSatindo> list_M04ItemSatindoList = new ArrayList<M04ItemSatindo>();
    private ListModelList modelList_M04ItemSatindoList = new ListModelList();
    protected Listbox listBoxM04ItemSatindo;
    
    // Databinding
    private AnnotateDataBinder binder;
    private M04ItemSatindoMainCtrl M04ItemSatindoMainCtrl;

    // List Header
    protected Listheader listheader_M04ItemSatindoList_TglBerlaku;
    protected Listheader listheader_M04ItemSatindoList_NoItem;
    protected Listheader listheader_M04ItemSatindoList_SatAmountKomisi;
    protected Listheader listheader_M04ItemSatindoList_IdmrAmountKomisi;
    protected Listheader listheader_M04ItemSatindoList_SatAmountBns;
    protected Listheader listheader_M04ItemSatindoList_IdmrAmountBns;
    
    public M04ItemSatindoListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_M04ItemSatindoList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_M04ItemSatindoList.setDetailed(true);
		paging_M04ItemSatindoList.addEventListener("onPaging", onPaging_M04ItemSatindoList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setM04ItemSatindoMainCtrl((M04ItemSatindoMainCtrl) arg.get("ModuleMainController"));
		
		    getM04ItemSatindoMainCtrl().setM04ItemSatindoListCtrl(this);
		}
        
		
    }    
    

    public void onCreate$windowM04ItemSatindoList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
        listheader_M04ItemSatindoList_TglBerlaku.setSortAscending(new M04ItemSatindoComparator(true, M04ItemSatindoComparator.COMPARE_BY_TGLBERLAKU));
        listheader_M04ItemSatindoList_TglBerlaku.setSortDescending(new M04ItemSatindoComparator(false, M04ItemSatindoComparator.COMPARE_BY_TGLBERLAKU));        
        
        listheader_M04ItemSatindoList_NoItem.setSortAscending(new M04ItemSatindoComparator(true, M04ItemSatindoComparator.COMPARE_BY_NOITEM));
        listheader_M04ItemSatindoList_NoItem.setSortDescending(new M04ItemSatindoComparator(false, M04ItemSatindoComparator.COMPARE_BY_NOITEM));        
       
        listheader_M04ItemSatindoList_SatAmountKomisi.setSortAscending(new M04ItemSatindoComparator(true, M04ItemSatindoComparator.COMPARE_BY_SATAMOUNTKOMISI));
        listheader_M04ItemSatindoList_SatAmountKomisi.setSortDescending(new M04ItemSatindoComparator(false, M04ItemSatindoComparator.COMPARE_BY_SATAMOUNTKOMISI));        
       
        listheader_M04ItemSatindoList_IdmrAmountKomisi.setSortAscending(new M04ItemSatindoComparator(true, M04ItemSatindoComparator.COMPARE_BY_IDMRAMOUNTKOMISI));
        listheader_M04ItemSatindoList_IdmrAmountKomisi.setSortDescending(new M04ItemSatindoComparator(false, M04ItemSatindoComparator.COMPARE_BY_IDMRAMOUNTKOMISI));        
       
        listheader_M04ItemSatindoList_SatAmountBns.setSortAscending(new M04ItemSatindoComparator(true, M04ItemSatindoComparator.COMPARE_BY_SATAMOUNTBNS));
        listheader_M04ItemSatindoList_SatAmountBns.setSortDescending(new M04ItemSatindoComparator(false, M04ItemSatindoComparator.COMPARE_BY_SATAMOUNTBNS));        
       
        listheader_M04ItemSatindoList_IdmrAmountBns.setSortAscending(new M04ItemSatindoComparator(true, M04ItemSatindoComparator.COMPARE_BY_IDMRAMOUNTBNS));
        listheader_M04ItemSatindoList_IdmrAmountBns.setSortDescending(new M04ItemSatindoComparator(false, M04ItemSatindoComparator.COMPARE_BY_IDMRAMOUNTBNS));        
       
        
        getSearchData();
        
        listBoxM04ItemSatindo.setItemRenderer(renderTable());
        
        windowM04ItemSatindoList.addEventListener(Events.ON_OK, onSubmitForm());
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
		
		
		list_M04ItemSatindoList.clear();
		
		List<M04ItemSatindo> tempListM04ItemSatindo = getM04ItemSatindoMainCtrl().getM04ItemSatindoService().getListM04ItemSatindo(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListM04ItemSatindo)) {
			list_M04ItemSatindoList.addAll(tempListM04ItemSatindo);
			startM04ItemSatindoList = 0;
			paging_M04ItemSatindoList.setActivePage(0);
		}

		setModelM04ItemSatindoList();
	}

	// Pembagian data untuk paging
	private void setModelM04ItemSatindoList() {		
		modelList_M04ItemSatindoList.clear();
		
		M04ItemSatindo selectedData = null;
		if (CommonUtils.isNotEmpty(list_M04ItemSatindoList)){
			
			int endM04ItemSatindoList = 0;
			if(startM04ItemSatindoList + paging_M04ItemSatindoList.getPageSize() < list_M04ItemSatindoList.size()) {
				endM04ItemSatindoList = startM04ItemSatindoList + paging_M04ItemSatindoList.getPageSize();
			} else {
				endM04ItemSatindoList = list_M04ItemSatindoList.size();
			}
			
			if (startM04ItemSatindoList > endM04ItemSatindoList) {
				startM04ItemSatindoList = 0;
				paging_M04ItemSatindoList.setActivePage(0);
			}
			
			modelList_M04ItemSatindoList.addAll(list_M04ItemSatindoList.subList(startM04ItemSatindoList, endM04ItemSatindoList));

			paging_M04ItemSatindoList.setDetailed(true);
			paging_M04ItemSatindoList.setTotalSize(list_M04ItemSatindoList.size());
			
			listBoxM04ItemSatindo.setModel(modelList_M04ItemSatindoList);
			listBoxM04ItemSatindo.setSelectedIndex(0);
	
			selectedData = list_M04ItemSatindoList.get(startM04ItemSatindoList);
		} else {
			paging_M04ItemSatindoList.setDetailed(false);
			listBoxM04ItemSatindo.setModel(modelList_M04ItemSatindoList);
			paging_M04ItemSatindoList.setTotalSize(0);
		}
		
		getM04ItemSatindoMainCtrl().setSelectedM04ItemSatindo(selectedData);

	}
	

    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final M04ItemSatindo rec = (M04ItemSatindo) data;

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
                info.setParent(windowM04ItemSatindoList);
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
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedM04ItemSatindoItem");
              }
        };
    }
    
	private EventListener onPaging_M04ItemSatindoList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startM04ItemSatindoList = pageNo * paging_M04ItemSatindoList.getPageSize();
		         setModelM04ItemSatindoList();
			}
		};
	}


    public void onDoubleClickedM04ItemSatindoItem(Event event) {
		if (listBoxM04ItemSatindo.getSelectedItem() != null) {
			M04ItemSatindo selectedData = (M04ItemSatindo) listBoxM04ItemSatindo.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedM04ItemSatindo(selectedData);
	
	            if (getM04ItemSatindoMainCtrl().getM04ItemSatindoDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getM04ItemSatindoMainCtrl().tabM04ItemSatindoDetail, null));
	            } else if (getM04ItemSatindoMainCtrl().getM04ItemSatindoDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getM04ItemSatindoMainCtrl().tabM04ItemSatindoDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getM04ItemSatindoMainCtrl().tabM04ItemSatindoDetail, selectedData));
	            
	            getM04ItemSatindoMainCtrl().getM04ItemSatindoDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxM04ItemSatindo(Event event) {
		if (listBoxM04ItemSatindo.getSelectedItem() != null) {
			M04ItemSatindo selectedData = (M04ItemSatindo) listBoxM04ItemSatindo.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getM04ItemSatindoMainCtrl().getM04ItemSatindoDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getM04ItemSatindoMainCtrl().tabM04ItemSatindoDetail, null));
		
				} else if (getM04ItemSatindoMainCtrl().getM04ItemSatindoDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getM04ItemSatindoMainCtrl().tabM04ItemSatindoDetail, null));
				}
				
				getM04ItemSatindoMainCtrl().setSelectedM04ItemSatindo(selectedData);
				
			}
		}	
    }
    

    public void onSort$listheader_M04ItemSatindoList_TglBerlaku(Event event) { 
    	sortingData(listheader_M04ItemSatindoList_TglBerlaku, M04ItemSatindoComparator.COMPARE_BY_TGLBERLAKU);
    }
    
    public void onSort$listheader_M04ItemSatindoList_NoItem(Event event) { 
    	sortingData(listheader_M04ItemSatindoList_NoItem, M04ItemSatindoComparator.COMPARE_BY_NOITEM);
    }
    
    public void onSort$listheader_M04ItemSatindoList_SatAmountKomisi(Event event) { 
    	sortingData(listheader_M04ItemSatindoList_SatAmountKomisi, M04ItemSatindoComparator.COMPARE_BY_SATAMOUNTKOMISI);
    }
    
    public void onSort$listheader_M04ItemSatindoList_IdmrAmountKomisi(Event event) { 
    	sortingData(listheader_M04ItemSatindoList_IdmrAmountKomisi, M04ItemSatindoComparator.COMPARE_BY_IDMRAMOUNTKOMISI);
    }
    
    public void onSort$listheader_M04ItemSatindoList_SatAmountBns(Event event) { 
    	sortingData(listheader_M04ItemSatindoList_SatAmountBns, M04ItemSatindoComparator.COMPARE_BY_SATAMOUNTBNS);
    }
    
    public void onSort$listheader_M04ItemSatindoList_IdmrAmountBns(Event event) { 
    	sortingData(listheader_M04ItemSatindoList_IdmrAmountBns, M04ItemSatindoComparator.COMPARE_BY_IDMRAMOUNTBNS);
    }
    
    
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_M04ItemSatindoList, new M04ItemSatindoComparator(false, sortBy));
    		} else {
    			Collections.sort(list_M04ItemSatindoList, new M04ItemSatindoComparator(true, sortBy));
    		}
    	}
    	
    	setModelM04ItemSatindoList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_M04ItemSatindoList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowM04ItemSatindoList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes

    	txtBerlaku.setValue(null);
    	txtNoItem.setValue(null);
    	txtSatAmountKomisi.setValue(null);
    	txtIdmrAmountKomisi.setValue(null);
    	txtSatAmountBns.setValue(null);
    	txtIdmrAmountBns.setValue(null);

    	//startM04ItemSatindoList = 0;
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


    public void setSelectedM04ItemSatindo(M04ItemSatindo selectedM04ItemSatindo) {
        getM04ItemSatindoMainCtrl().setSelectedM04ItemSatindo(selectedM04ItemSatindo);
    }

    public M04ItemSatindo getSelectedM04ItemSatindo() {
        return getM04ItemSatindoMainCtrl().getSelectedM04ItemSatindo();
    }

    public void setM04ItemSatindoMainCtrl(M04ItemSatindoMainCtrl M04ItemSatindoMainCtrl) {
        this.M04ItemSatindoMainCtrl = M04ItemSatindoMainCtrl;
    }

    public M04ItemSatindoMainCtrl getM04ItemSatindoMainCtrl() {
        return this.M04ItemSatindoMainCtrl;
    }

}
