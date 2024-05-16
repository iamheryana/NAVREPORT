package solusi.hapis.webui.finance.T04BayarAngsuran;

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

import solusi.hapis.backend.navbi.model.T04BayarAngsuran;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 17-03-2020
 */

public class T04BayarAngsuranListCtrl extends GFCBaseListCtrl<T04BayarAngsuran> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT04BayarAngsuranList; 
    protected Panel panelT04BayarAngsuranList;
 	
	// Search Box Components 
	protected Textbox txtSuppCode;
	protected Textbox txtNoPo;
	protected Textbox txtValutaPo;
	protected Textbox txtNilaiPo;
	protected Textbox txtJmlGiro;
	protected Textbox txtTMT;
	protected Textbox txtPrintCount;
	
	protected Combobox cmbCompany;
	
	
	// Paging and list
    protected Borderlayout borderLayout_T04BayarAngsuranList;
    protected Paging paging_T04BayarAngsuranList;
    private int startT04BayarAngsuranList;
	private List<T04BayarAngsuran> list_T04BayarAngsuranList = new ArrayList<T04BayarAngsuran>();
    private ListModelList modelList_T04BayarAngsuranList = new ListModelList();
    protected Listbox listBoxT04BayarAngsuran;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T04BayarAngsuranMainCtrl T04BayarAngsuranMainCtrl;
		
		
    // List Header		
    protected Listheader listheader_T04BayarAngsuranList_Company;
    protected Listheader listheader_T04BayarAngsuranList_SuppCode;
    protected Listheader listheader_T04BayarAngsuranList_NoPo;
    protected Listheader listheader_T04BayarAngsuranList_ValutaPo;
    protected Listheader listheader_T04BayarAngsuranList_NilaiPo;
    protected Listheader listheader_T04BayarAngsuranList_JmlGiro;    
    protected Listheader listheader_T04BayarAngsuranList_TMT;    
    protected Listheader listheader_T04BayarAngsuranList_PrintCount;
		
		
    
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T04BayarAngsuranListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T04BayarAngsuranList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T04BayarAngsuranList.setDetailed(true);
		paging_T04BayarAngsuranList.addEventListener("onPaging", onPaging_T04BayarAngsuranList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT04BayarAngsuranMainCtrl((T04BayarAngsuranMainCtrl) arg.get("ModuleMainController"));
		
		    getT04BayarAngsuranMainCtrl().setT04BayarAngsuranListCtrl(this);
		}
        
		

    }    
    

    public void onCreate$windowT04BayarAngsuranList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan

        
        
        listheader_T04BayarAngsuranList_Company.setSortAscending(new T04BayarAngsuranComparator(true, T04BayarAngsuranComparator.COMPARE_BY_COMPANY));
        listheader_T04BayarAngsuranList_Company.setSortDescending(new T04BayarAngsuranComparator(false, T04BayarAngsuranComparator.COMPARE_BY_COMPANY));        
        
        
        listheader_T04BayarAngsuranList_SuppCode.setSortAscending(new T04BayarAngsuranComparator(true, T04BayarAngsuranComparator.COMPARE_BY_SUPPCODE));
        listheader_T04BayarAngsuranList_SuppCode.setSortDescending(new T04BayarAngsuranComparator(false, T04BayarAngsuranComparator.COMPARE_BY_SUPPCODE));        
         
        listheader_T04BayarAngsuranList_NoPo.setSortAscending(new T04BayarAngsuranComparator(true, T04BayarAngsuranComparator.COMPARE_BY_NOPO));
        listheader_T04BayarAngsuranList_NoPo.setSortDescending(new T04BayarAngsuranComparator(false, T04BayarAngsuranComparator.COMPARE_BY_NOPO));        
       
        listheader_T04BayarAngsuranList_ValutaPo.setSortAscending(new T04BayarAngsuranComparator(true, T04BayarAngsuranComparator.COMPARE_BY_VALUTAPO));
        listheader_T04BayarAngsuranList_ValutaPo.setSortDescending(new T04BayarAngsuranComparator(false, T04BayarAngsuranComparator.COMPARE_BY_VALUTAPO));        
       
        listheader_T04BayarAngsuranList_NilaiPo.setSortAscending(new T04BayarAngsuranComparator(true, T04BayarAngsuranComparator.COMPARE_BY_NILAIPO));
        listheader_T04BayarAngsuranList_NilaiPo.setSortDescending(new T04BayarAngsuranComparator(false, T04BayarAngsuranComparator.COMPARE_BY_NILAIPO));        
               
        listheader_T04BayarAngsuranList_JmlGiro.setSortAscending(new T04BayarAngsuranComparator(true, T04BayarAngsuranComparator.COMPARE_BY_JMLGIRO));
        listheader_T04BayarAngsuranList_JmlGiro.setSortDescending(new T04BayarAngsuranComparator(false, T04BayarAngsuranComparator.COMPARE_BY_JMLGIRO));        
        
        listheader_T04BayarAngsuranList_TMT.setSortAscending(new T04BayarAngsuranComparator(true, T04BayarAngsuranComparator.COMPARE_BY_TMT));
        listheader_T04BayarAngsuranList_TMT.setSortDescending(new T04BayarAngsuranComparator(false, T04BayarAngsuranComparator.COMPARE_BY_TMT));        
        
        
        listheader_T04BayarAngsuranList_PrintCount.setSortAscending(new T04BayarAngsuranComparator(true, T04BayarAngsuranComparator.COMPARE_BY_PRINTCOUNT));
        listheader_T04BayarAngsuranList_PrintCount.setSortDescending(new T04BayarAngsuranComparator(false, T04BayarAngsuranComparator.COMPARE_BY_PRINTCOUNT));        
        
        
        
        getSearchData();
        
        listBoxT04BayarAngsuran.setItemRenderer(renderTable());
        
        windowT04BayarAngsuranList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		if (cmbCompany.getSelectedIndex() != 0) {
			parameterInput.put("company", (String) cmbCompany.getSelectedItem().getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtSuppCode.getValue())) {
			parameterInput.put("suppCode", txtSuppCode.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtNoPo.getValue())) {
			parameterInput.put("noPo", txtNoPo.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtValutaPo.getValue())) {
			parameterInput.put("valutaPo", txtValutaPo.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtNilaiPo.getValue())) {
			parameterInput.put("nilaiPo", new BigDecimal(txtNilaiPo.getValue()));
		}
		
		if (CommonUtils.isNotEmpty(txtJmlGiro.getValue())) {
			int vJmlGiro = Integer.valueOf(txtJmlGiro.getValue());
			parameterInput.put("jmlGiro", vJmlGiro);
		}		
			
		if (CommonUtils.isValidDateFormat(txtTMT.getValue())) {
			Date tmt1 = CommonUtils.convertStringToDate(txtTMT.getValue());
			Date tmt2 = CommonUtils.createSecondParameterForSearch(txtTMT.getValue());
			parameterInput.put("tmtfrom", tmt1);
			parameterInput.put("tmtto", tmt2);
		}
		
		if (CommonUtils.isNotEmpty(txtPrintCount.getValue())) {
			int vPrintCount = Integer.valueOf(txtPrintCount.getValue());
			parameterInput.put("printCount", vPrintCount);
		}		
		
		list_T04BayarAngsuranList.clear();
		
		List<T04BayarAngsuran> tempListT04BayarAngsuran = getT04BayarAngsuranMainCtrl().getT04BayarAngsuranService().getListT04BayarAngsuran(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT04BayarAngsuran)) {
			list_T04BayarAngsuranList.addAll(tempListT04BayarAngsuran);
			startT04BayarAngsuranList = 0;
			paging_T04BayarAngsuranList.setActivePage(0);
		}

		setModelT04BayarAngsuranList();
	}

	// Pembagian data untuk paging
	private void setModelT04BayarAngsuranList() {		
		modelList_T04BayarAngsuranList.clear();
		
		T04BayarAngsuran selectedData = null;
		if (CommonUtils.isNotEmpty(list_T04BayarAngsuranList)){
			
			int endT04BayarAngsuranList = 0;
			if(startT04BayarAngsuranList + paging_T04BayarAngsuranList.getPageSize() < list_T04BayarAngsuranList.size()) {
				endT04BayarAngsuranList = startT04BayarAngsuranList + paging_T04BayarAngsuranList.getPageSize();
			} else {
				endT04BayarAngsuranList = list_T04BayarAngsuranList.size();
			}
			
			if (startT04BayarAngsuranList > endT04BayarAngsuranList) {
				startT04BayarAngsuranList = 0;
				paging_T04BayarAngsuranList.setActivePage(0);
			}
			
			modelList_T04BayarAngsuranList.addAll(list_T04BayarAngsuranList.subList(startT04BayarAngsuranList, endT04BayarAngsuranList));

			paging_T04BayarAngsuranList.setDetailed(true);
			paging_T04BayarAngsuranList.setTotalSize(list_T04BayarAngsuranList.size());
			
			listBoxT04BayarAngsuran.setModel(modelList_T04BayarAngsuranList);
			listBoxT04BayarAngsuran.setSelectedIndex(0);
	
			selectedData = list_T04BayarAngsuranList.get(startT04BayarAngsuranList);
		} else {
			paging_T04BayarAngsuranList.setDetailed(false);
			listBoxT04BayarAngsuran.setModel(modelList_T04BayarAngsuranList);
			paging_T04BayarAngsuranList.setTotalSize(0);
		}
		
		getT04BayarAngsuranMainCtrl().setSelectedT04BayarAngsuran(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T04BayarAngsuran rec = (T04BayarAngsuran) data;

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
                info.setParent(windowT04BayarAngsuranList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(rec.getCompany());
                lc.setParent(item);
            	 
                lc = new Listcell(rec.getSuppCode());
                lc.setParent(item);
                
                
                lc = new Listcell(rec.getNoPo());
                lc.setParent(item);
                
                lc = new Listcell(rec.getValutaPo());
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getNilaiPo(), "#,###,###.##"));				
				lc.setStyle("text-align:right");
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getJmlGiro(), "#,###,###"));				
				lc.setStyle("text-align:right");
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getTmt()));
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getPrintCount(), "#,###,###"));				
				lc.setStyle("text-align:right");
                lc.setParent(item);
                
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT04BayarAngsuranItem");
              }
        };
    }
    
	private EventListener onPaging_T04BayarAngsuranList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT04BayarAngsuranList = pageNo * paging_T04BayarAngsuranList.getPageSize();
		         setModelT04BayarAngsuranList();
			}
		};
	}


    public void onDoubleClickedT04BayarAngsuranItem(Event event) {
		if (listBoxT04BayarAngsuran.getSelectedItem() != null) {
			T04BayarAngsuran selectedData = (T04BayarAngsuran) listBoxT04BayarAngsuran.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT04BayarAngsuran(selectedData);
	
	            if (getT04BayarAngsuranMainCtrl().getT04BayarAngsuranDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT04BayarAngsuranMainCtrl().tabT04BayarAngsuranDetail, null));
	            } else if (getT04BayarAngsuranMainCtrl().getT04BayarAngsuranDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT04BayarAngsuranMainCtrl().tabT04BayarAngsuranDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT04BayarAngsuranMainCtrl().tabT04BayarAngsuranDetail, selectedData));
	            
	            getT04BayarAngsuranMainCtrl().getT04BayarAngsuranDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT04BayarAngsuran(Event event) {
		if (listBoxT04BayarAngsuran.getSelectedItem() != null) {
			T04BayarAngsuran selectedData = (T04BayarAngsuran) listBoxT04BayarAngsuran.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT04BayarAngsuranMainCtrl().getT04BayarAngsuranDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT04BayarAngsuranMainCtrl().tabT04BayarAngsuranDetail, null));
		
				} else if (getT04BayarAngsuranMainCtrl().getT04BayarAngsuranDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT04BayarAngsuranMainCtrl().tabT04BayarAngsuranDetail, null));
				}
				
				getT04BayarAngsuranMainCtrl().setSelectedT04BayarAngsuran(selectedData);
				
			}
		}	
    }
    
    public void onSort$listheader_T04BayarAngsuranList_Company(Event event) {
    	sortingData(listheader_T04BayarAngsuranList_Company, T04BayarAngsuranComparator.COMPARE_BY_COMPANY);
    }
    
    public void onSort$listheader_T04BayarAngsuranList_SuppCode(Event event) {
    	sortingData(listheader_T04BayarAngsuranList_SuppCode, T04BayarAngsuranComparator.COMPARE_BY_SUPPCODE);
    }
    
    public void onSort$listheader_T04BayarAngsuranList_NoPo(Event event) {
    	sortingData(listheader_T04BayarAngsuranList_NoPo, T04BayarAngsuranComparator.COMPARE_BY_NOPO);
    }

    public void onSort$listheader_T04BayarAngsuranList_ValutaPo(Event event) {
    	sortingData(listheader_T04BayarAngsuranList_ValutaPo, T04BayarAngsuranComparator.COMPARE_BY_VALUTAPO);
    }
    
    public void onSort$listheader_T04BayarAngsuranList_NilaiPo(Event event) {
    	sortingData(listheader_T04BayarAngsuranList_NilaiPo, T04BayarAngsuranComparator.COMPARE_BY_NILAIPO);
    }

    public void onSort$listheader_T04BayarAngsuranList_JmlGiro(Event event) {
    	sortingData(listheader_T04BayarAngsuranList_JmlGiro, T04BayarAngsuranComparator.COMPARE_BY_JMLGIRO);
    }
    
    public void onSort$listheader_T04BayarAngsuranList_TMT(Event event) {
    	sortingData(listheader_T04BayarAngsuranList_TMT, T04BayarAngsuranComparator.COMPARE_BY_TMT);
    }
    
    public void onSort$listheader_T04BayarAngsuranList_PrintCount(Event event) {
    	sortingData(listheader_T04BayarAngsuranList_PrintCount, T04BayarAngsuranComparator.COMPARE_BY_PRINTCOUNT);
    }
    
    
      
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T04BayarAngsuranList, new T04BayarAngsuranComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T04BayarAngsuranList, new T04BayarAngsuranComparator(true, sortBy));
    		}
    	}
    	
    	setModelT04BayarAngsuranList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T04BayarAngsuranList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT04BayarAngsuranList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes

    	cmbCompany.setSelectedIndex(0);
      	txtSuppCode.setValue(null);    	
    	txtNoPo.setValue(null);
    	txtValutaPo.setValue(null);
    	txtNilaiPo.setValue(null);
    	txtJmlGiro.setValue(null);
    	txtTMT.setValue(null);
    	txtPrintCount.setValue(null);
   
    	//startT04BayarAngsuranList = 0;
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


    public void setSelectedT04BayarAngsuran(T04BayarAngsuran selectedT04BayarAngsuran) {
        getT04BayarAngsuranMainCtrl().setSelectedT04BayarAngsuran(selectedT04BayarAngsuran);
    }

    public T04BayarAngsuran getSelectedT04BayarAngsuran() {
        return getT04BayarAngsuranMainCtrl().getSelectedT04BayarAngsuran();
    }

    public void setT04BayarAngsuranMainCtrl(T04BayarAngsuranMainCtrl T04BayarAngsuranMainCtrl) {
        this.T04BayarAngsuranMainCtrl = T04BayarAngsuranMainCtrl;
    }

    public T04BayarAngsuranMainCtrl getT04BayarAngsuranMainCtrl() {
        return this.T04BayarAngsuranMainCtrl;
    }

}
