package solusi.hapis.webui.sales.SalesForecast.M06TargetPipeline;

import java.io.Serializable;
import java.math.BigDecimal;
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

import solusi.hapis.backend.navbi.model.M06TargetPipeline;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 09-02-2023
 */


public class M06TargetPipelineListCtrl extends GFCBaseListCtrl<M06TargetPipeline> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowM06TargetPipelineList; 
    protected Panel panelM06TargetPipelineList;
 	
	// Search Box Components 
	protected Textbox txtTahun;
	protected Textbox txtSlsOrCab;
	protected Textbox txtTargetAmount;
	protected Textbox txtTargetPsAmount;

	
	protected Combobox cmbJenis;
	protected Combobox cmbStatus;


	

	// Paging and list
    protected Borderlayout borderLayout_M06TargetPipelineList;
    protected Paging paging_M06TargetPipelineList;
    private int startM06TargetPipelineList;
	private List<M06TargetPipeline> list_M06TargetPipelineList = new ArrayList<M06TargetPipeline>();
    private ListModelList modelList_M06TargetPipelineList = new ListModelList();
    protected Listbox listBoxM06TargetPipeline;
    
    // Databinding
    private AnnotateDataBinder binder;
    private M06TargetPipelineMainCtrl M06TargetPipelineMainCtrl;

    // List Header
    protected Listheader listheader_M06TargetPipelineList_Tahun;
    protected Listheader listheader_M06TargetPipelineList_Jenis;
    protected Listheader listheader_M06TargetPipelineList_SlsOrCab;
    protected Listheader listheader_M06TargetPipelineList_Target;
    protected Listheader listheader_M06TargetPipelineList_TargetPs;
    protected Listheader listheader_M06TargetPipelineList_Status;
	
	
    public M06TargetPipelineListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_M06TargetPipelineList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_M06TargetPipelineList.setDetailed(true);
		paging_M06TargetPipelineList.addEventListener("onPaging", onPaging_M06TargetPipelineList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setM06TargetPipelineMainCtrl((M06TargetPipelineMainCtrl) arg.get("ModuleMainController"));
		
		    getM06TargetPipelineMainCtrl().setM06TargetPipelineListCtrl(this);
		}
        
		
    }    
    

    public void onCreate$windowM06TargetPipelineList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
  
        
        listheader_M06TargetPipelineList_Tahun.setSortAscending(new M06TargetPipelineComparator(true, M06TargetPipelineComparator.COMPARE_BY_TAHUN));
        listheader_M06TargetPipelineList_Tahun.setSortDescending(new M06TargetPipelineComparator(false, M06TargetPipelineComparator.COMPARE_BY_TAHUN));        
        
        listheader_M06TargetPipelineList_Jenis.setSortAscending(new M06TargetPipelineComparator(true, M06TargetPipelineComparator.COMPARE_BY_JENIS));
        listheader_M06TargetPipelineList_Jenis.setSortDescending(new M06TargetPipelineComparator(false, M06TargetPipelineComparator.COMPARE_BY_JENIS));        
       
        listheader_M06TargetPipelineList_SlsOrCab.setSortAscending(new M06TargetPipelineComparator(true, M06TargetPipelineComparator.COMPARE_BY_SLS_OR_CAB));
        listheader_M06TargetPipelineList_SlsOrCab.setSortDescending(new M06TargetPipelineComparator(false, M06TargetPipelineComparator.COMPARE_BY_SLS_OR_CAB));        
       
        listheader_M06TargetPipelineList_Target.setSortAscending(new M06TargetPipelineComparator(true, M06TargetPipelineComparator.COMPARE_BY_TARGET));
        listheader_M06TargetPipelineList_Target.setSortDescending(new M06TargetPipelineComparator(false, M06TargetPipelineComparator.COMPARE_BY_TARGET));        
       
        listheader_M06TargetPipelineList_TargetPs.setSortAscending(new M06TargetPipelineComparator(true, M06TargetPipelineComparator.COMPARE_BY_TARGET_PS));
        listheader_M06TargetPipelineList_TargetPs.setSortDescending(new M06TargetPipelineComparator(false, M06TargetPipelineComparator.COMPARE_BY_TARGET_PS)); 
        
        listheader_M06TargetPipelineList_Status.setSortAscending(new M06TargetPipelineComparator(true, M06TargetPipelineComparator.COMPARE_BY_STATUS));
        listheader_M06TargetPipelineList_Status.setSortDescending(new M06TargetPipelineComparator(false, M06TargetPipelineComparator.COMPARE_BY_STATUS));        
       
          
        getSearchData();
        
        listBoxM06TargetPipeline.setItemRenderer(renderTable());
        
        windowM06TargetPipelineList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		if (cmbJenis.getSelectedIndex() != 0) {
			parameterInput.put("jenis", (String) cmbJenis.getSelectedItem().getValue());
		}
		
		if (cmbStatus.getSelectedIndex() != 0) {
			parameterInput.put("status", (String) cmbStatus.getSelectedItem().getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtTahun.getValue())) {
			parameterInput.put("tahun", txtTahun.getValue());
		}
		

		if (CommonUtils.isNotEmpty(txtSlsOrCab.getValue())) {
			parameterInput.put("slsOrCab", txtSlsOrCab.getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtTargetAmount.getValue())) {
			parameterInput.put("target", new BigDecimal(txtTargetAmount.getValue()));
		}
		
		if (CommonUtils.isNotEmpty(txtTargetPsAmount.getValue())) {
			parameterInput.put("targetPs", new BigDecimal(txtTargetPsAmount.getValue()));
		}
		
		
		
		list_M06TargetPipelineList.clear();
		
		List<M06TargetPipeline> tempListM06TargetPipeline = getM06TargetPipelineMainCtrl().getM06TargetPipelineService().getListM06TargetPipeline(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListM06TargetPipeline)) {
			list_M06TargetPipelineList.addAll(tempListM06TargetPipeline);
			startM06TargetPipelineList = 0;
			paging_M06TargetPipelineList.setActivePage(0);
		}

		setModelM06TargetPipelineList();
	}

	// Pembagian data untuk paging
	private void setModelM06TargetPipelineList() {		
		modelList_M06TargetPipelineList.clear();
		
		M06TargetPipeline selectedData = null;
		if (CommonUtils.isNotEmpty(list_M06TargetPipelineList)){
			
			int endM06TargetPipelineList = 0;
			if(startM06TargetPipelineList + paging_M06TargetPipelineList.getPageSize() < list_M06TargetPipelineList.size()) {
				endM06TargetPipelineList = startM06TargetPipelineList + paging_M06TargetPipelineList.getPageSize();
			} else {
				endM06TargetPipelineList = list_M06TargetPipelineList.size();
			}
			
			if (startM06TargetPipelineList > endM06TargetPipelineList) {
				startM06TargetPipelineList = 0;
				paging_M06TargetPipelineList.setActivePage(0);
			}
			
			modelList_M06TargetPipelineList.addAll(list_M06TargetPipelineList.subList(startM06TargetPipelineList, endM06TargetPipelineList));

			paging_M06TargetPipelineList.setDetailed(true);
			paging_M06TargetPipelineList.setTotalSize(list_M06TargetPipelineList.size());
			
			listBoxM06TargetPipeline.setModel(modelList_M06TargetPipelineList);
			listBoxM06TargetPipeline.setSelectedIndex(0);
	
			selectedData = list_M06TargetPipelineList.get(startM06TargetPipelineList);
		} else {
			paging_M06TargetPipelineList.setDetailed(false);
			listBoxM06TargetPipeline.setModel(modelList_M06TargetPipelineList);
			paging_M06TargetPipelineList.setTotalSize(0);
		}
		
		getM06TargetPipelineMainCtrl().setSelectedM06TargetPipeline(selectedData);

	}
	

    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final M06TargetPipeline rec = (M06TargetPipeline) data;

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
                info.setParent(windowM06TargetPipelineList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                  
                lc = new Listcell(rec.getTahun());
                lc.setParent(item);
                
                lc = new Listcell(rec.getJenis());
                lc.setParent(item);
                
                lc = new Listcell(rec.getSlsOrCab());
                lc.setParent(item);
                
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getTarget(), "#,###,###"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getTargetPs(), "#,###,###"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(rec.getStatus());
                lc.setParent(item);
                				
      
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedM06TargetPipelineItem");
              }
        };
    }
    
	private EventListener onPaging_M06TargetPipelineList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startM06TargetPipelineList = pageNo * paging_M06TargetPipelineList.getPageSize();
		         setModelM06TargetPipelineList();
			}
		};
	}


    public void onDoubleClickedM06TargetPipelineItem(Event event) {
		if (listBoxM06TargetPipeline.getSelectedItem() != null) {
			M06TargetPipeline selectedData = (M06TargetPipeline) listBoxM06TargetPipeline.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedM06TargetPipeline(selectedData);
	
	            if (getM06TargetPipelineMainCtrl().getM06TargetPipelineDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getM06TargetPipelineMainCtrl().tabM06TargetPipelineDetail, null));
	            } else if (getM06TargetPipelineMainCtrl().getM06TargetPipelineDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getM06TargetPipelineMainCtrl().tabM06TargetPipelineDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getM06TargetPipelineMainCtrl().tabM06TargetPipelineDetail, selectedData));
	            
	            getM06TargetPipelineMainCtrl().getM06TargetPipelineDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxM06TargetPipeline(Event event) {
		if (listBoxM06TargetPipeline.getSelectedItem() != null) {
			M06TargetPipeline selectedData = (M06TargetPipeline) listBoxM06TargetPipeline.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getM06TargetPipelineMainCtrl().getM06TargetPipelineDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getM06TargetPipelineMainCtrl().tabM06TargetPipelineDetail, null));
		
				} else if (getM06TargetPipelineMainCtrl().getM06TargetPipelineDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getM06TargetPipelineMainCtrl().tabM06TargetPipelineDetail, null));
				}
				
				getM06TargetPipelineMainCtrl().setSelectedM06TargetPipeline(selectedData);
				
			}
		}	
    }
    
   
    
    public void onSort$listheader_M06TargetPipelineList_Tahun(Event event) { 
    	sortingData(listheader_M06TargetPipelineList_Tahun, M06TargetPipelineComparator.COMPARE_BY_TAHUN);
    }
    
    public void onSort$listheader_M06TargetPipelineList_Jenis(Event event) { 
    	sortingData(listheader_M06TargetPipelineList_Jenis, M06TargetPipelineComparator.COMPARE_BY_JENIS);
    }
    
    public void onSort$listheader_M06TargetPipelineList_SlsOrCab(Event event) { 
    	sortingData(listheader_M06TargetPipelineList_SlsOrCab, M06TargetPipelineComparator.COMPARE_BY_SLS_OR_CAB);
    }
    
    public void onSort$listheader_M06TargetPipelineList_Target(Event event) { 
    	sortingData(listheader_M06TargetPipelineList_Target, M06TargetPipelineComparator.COMPARE_BY_TARGET);
    }
    
    public void onSort$listheader_M06TargetPipelineList_TargetPs(Event event) { 
    	sortingData(listheader_M06TargetPipelineList_TargetPs, M06TargetPipelineComparator.COMPARE_BY_TARGET_PS);
    }
    
    public void onSort$listheader_M06TargetPipelineList_Status(Event event) { 
    	sortingData(listheader_M06TargetPipelineList_Status, M06TargetPipelineComparator.COMPARE_BY_STATUS);
    }
    
   
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_M06TargetPipelineList, new M06TargetPipelineComparator(false, sortBy));
    		} else {
    			Collections.sort(list_M06TargetPipelineList, new M06TargetPipelineComparator(true, sortBy));
    		}
    	}
    	
    	setModelM06TargetPipelineList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_M06TargetPipelineList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowM06TargetPipelineList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes

    	txtTahun.setValue(null);
    	txtSlsOrCab.setValue(null);
    	txtTargetAmount.setValue(null);

    	
    	cmbJenis.setSelectedIndex(0);
    	cmbStatus.setSelectedIndex(0);
    	    	
    	
    	//startM06TargetPipelineList = 0;
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


    public void setSelectedM06TargetPipeline(M06TargetPipeline selectedM06TargetPipeline) {
        getM06TargetPipelineMainCtrl().setSelectedM06TargetPipeline(selectedM06TargetPipeline);
    }

    public M06TargetPipeline getSelectedM06TargetPipeline() {
        return getM06TargetPipelineMainCtrl().getSelectedM06TargetPipeline();
    }

    public void setM06TargetPipelineMainCtrl(M06TargetPipelineMainCtrl M06TargetPipelineMainCtrl) {
        this.M06TargetPipelineMainCtrl = M06TargetPipelineMainCtrl;
    }

    public M06TargetPipelineMainCtrl getM06TargetPipelineMainCtrl() {
        return this.M06TargetPipelineMainCtrl;
    }

}
