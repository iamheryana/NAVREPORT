package solusi.hapis.webui.finance.Cashflow.T36OtherCf;

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

import solusi.hapis.backend.navbi.model.T36OtherCf;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

/**
 * User: Heryana
 * Date: 28-11-2024
 */

public class T36OtherCfListCtrl extends GFCBaseListCtrl<T36OtherCf> implements Serializable {
    private static final long serialVersionUID = -2170565288232491362L;
	
    
    protected Window windowT36OtherCfList; 
    protected Panel panelT36OtherCfList;
 	
	// Search Box Components 
    

	protected Textbox txtKeterangan;
	protected Textbox txtAmount;
	protected Textbox txtDuedate;
	protected Textbox txtEvery;
	protected Textbox txtFromDate;
	protected Textbox txtUptoDate;
	

	protected Combobox cmbCompany;
	protected Combobox cmbReg;
	protected Combobox cmbTipe;
	protected Combobox cmbBasis;
			
		
	// Paging and list
    protected Borderlayout borderLayout_T36OtherCfList;
    protected Paging paging_T36OtherCfList;
    private int startT36OtherCfList;
	private List<T36OtherCf> list_T36OtherCfList = new ArrayList<T36OtherCf>();
    private ListModelList modelList_T36OtherCfList = new ListModelList();
    protected Listbox listBoxT36OtherCf;
    
    // Databinding
    private AnnotateDataBinder binder;
    private T36OtherCfMainCtrl T36OtherCfMainCtrl;

    // List Header		
    protected Listheader listheader_T36OtherCfList_Company;
    protected Listheader listheader_T36OtherCfList_Reg;			
    protected Listheader listheader_T36OtherCfList_Keterangan;
    protected Listheader listheader_T36OtherCfList_Amount;
    protected Listheader listheader_T36OtherCfList_Tipe;
    protected Listheader listheader_T36OtherCfList_Duedate;
    protected Listheader listheader_T36OtherCfList_Basis;
    protected Listheader listheader_T36OtherCfList_Every;
    protected Listheader listheader_T36OtherCfList_FromDate;
    protected Listheader listheader_T36OtherCfList_UptoDate;
    
    //protected Bandbox  cmbSektorIndustri;
	//protected Listbox listSektorIndustri;
	//protected String vSektorIndustri = "99999";
	
//	private SelectQueryService selectQueryService;
//    private SelectQueryNavReportService selectQueryNavReportService;
//    private List<Object[]> vResultSales;
    
    
    public T36OtherCfListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);

        paging_T36OtherCfList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_T36OtherCfList.setDetailed(true);
		paging_T36OtherCfList.addEventListener("onPaging", onPaging_T36OtherCfList());
	
		
		if (arg.containsKey("ModuleMainController")) {
			setT36OtherCfMainCtrl((T36OtherCfMainCtrl) arg.get("ModuleMainController"));
		
		    getT36OtherCfMainCtrl().setT36OtherCfListCtrl(this);
		}
        
    }    
    
    
    public void onCreate$windowT36OtherCfList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // Sebagai inisialisai awal supaya sorting bisa dilakukan
        
      
        listheader_T36OtherCfList_Company.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_COMPANY));
        listheader_T36OtherCfList_Company.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_COMPANY));        
         
        listheader_T36OtherCfList_Reg.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_REG));
        listheader_T36OtherCfList_Reg.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_REG));        
       
        listheader_T36OtherCfList_Keterangan.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_KETERANGAN));
        listheader_T36OtherCfList_Keterangan.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_KETERANGAN));        
       
        listheader_T36OtherCfList_Amount.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_AMOUNT));
        listheader_T36OtherCfList_Amount.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_AMOUNT));        
       
        listheader_T36OtherCfList_Tipe.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_TIPE));
        listheader_T36OtherCfList_Tipe.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_TIPE));        
       
        listheader_T36OtherCfList_Duedate.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_DUEDATE));
        listheader_T36OtherCfList_Duedate.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_DUEDATE));        
       
        listheader_T36OtherCfList_Basis.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_BASIS));
        listheader_T36OtherCfList_Basis.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_BASIS));        
       
        listheader_T36OtherCfList_Every.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_EVERY));
        listheader_T36OtherCfList_Every.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_EVERY));        
       
        listheader_T36OtherCfList_FromDate.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_FROMDATE));
        listheader_T36OtherCfList_FromDate.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_FROMDATE));        
        
        listheader_T36OtherCfList_UptoDate.setSortAscending(new T36OtherCfComparator(true, T36OtherCfComparator.COMPARE_BY_UPTODATE));
        listheader_T36OtherCfList_UptoDate.setSortDescending(new T36OtherCfComparator(false, T36OtherCfComparator.COMPARE_BY_UPTODATE));        
    
        
        getSearchData();
        
        listBoxT36OtherCf.setItemRenderer(renderTable());
        
        windowT36OtherCfList.addEventListener(Events.ON_OK, onSubmitForm());
    }
    
    // Query data ke database berdasarkan search kriteria
	public void getSearchData() {
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		if (cmbCompany.getSelectedIndex() != 0) {
			parameterInput.put("company", (String) cmbCompany.getSelectedItem().getValue());
		}

		if (cmbReg.getSelectedIndex() != 0) {
			parameterInput.put("reg", (String) cmbReg.getSelectedItem().getValue());
		}		

		if (CommonUtils.isNotEmpty(txtKeterangan.getValue())) {
			parameterInput.put("keterangan", txtKeterangan.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtAmount.getValue())) {
			parameterInput.put("amount", txtAmount.getValue());
		}
		
		if (cmbTipe.getSelectedIndex() != 0) {
			parameterInput.put("tipe", (String) cmbTipe.getSelectedItem().getValue());
		}
		
			
		if (CommonUtils.isValidDateFormat(txtDuedate.getValue())) {
			Date dueDateFrom = CommonUtils.convertStringToDate(txtDuedate.getValue());
			Date dueDateTo = CommonUtils.createSecondParameterForSearch(txtDuedate.getValue());
			parameterInput.put("dueDateFrom", dueDateFrom);
			parameterInput.put("dueDateTo", dueDateTo);
		}
		
		if (cmbBasis.getSelectedIndex() != 0) {
			parameterInput.put("basis", (String) cmbBasis.getSelectedItem().getValue());
		}
		
		
		if (CommonUtils.isNotEmpty(txtEvery.getValue())) {
		
			if (txtEvery.getValue().equals("SENIN") == true){
				parameterInput.put("every", 1);
				parameterInput.put("basis", "W");
			} else {
				if (txtEvery.getValue().equals("SELASA") == true){
					parameterInput.put("every", 2);
					parameterInput.put("basis", "W");
				} else {
					if (txtEvery.getValue().equals("RABU") == true){
						parameterInput.put("every", 3);
						parameterInput.put("basis", "W");
					} else {
						if (txtEvery.getValue().equals("KAMIS") == true){
							parameterInput.put("every", 4);
							parameterInput.put("basis", "W");
						} else {
							if (txtEvery.getValue().equals("JUMAT") == true){
								parameterInput.put("every", 5);
								parameterInput.put("basis", "W");
							} else  {
								if (txtEvery.getValue().equals("SABTU") == true){
									parameterInput.put("every", 6);
									parameterInput.put("basis", "W");
								} else {
									if (txtEvery.getValue().equals("MINGGU") == true){
										parameterInput.put("every", 7);
										parameterInput.put("basis", "W");
									} else {
										parameterInput.put("every", Integer.valueOf(txtEvery.getValue()));
										parameterInput.put("basis", "M");
									}
								}
							}
						}
					}
				}
			}
			
			
		}
		
		if (CommonUtils.isValidDateFormat(txtFromDate.getValue())) {
			Date fromDateFrom = CommonUtils.convertStringToDate(txtFromDate.getValue());
			Date fromDateTo = CommonUtils.createSecondParameterForSearch(txtFromDate.getValue());
			parameterInput.put("fromDateFrom", fromDateFrom);
			parameterInput.put("fromDateTo", fromDateTo);
		}
		
		if (CommonUtils.isValidDateFormat(txtUptoDate.getValue())) {
			Date uptoDateFrom = CommonUtils.convertStringToDate(txtUptoDate.getValue());
			Date uptoDateTo = CommonUtils.createSecondParameterForSearch(txtUptoDate.getValue());
			parameterInput.put("uptoDateFrom", uptoDateFrom);
			parameterInput.put("uptoDateTo", uptoDateTo);
		}
		
		
		list_T36OtherCfList.clear();
		
		List<T36OtherCf> tempListT36OtherCf = getT36OtherCfMainCtrl().getT36OtherCfService().getListT36OtherCf(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListT36OtherCf)) {
			list_T36OtherCfList.addAll(tempListT36OtherCf);
			startT36OtherCfList = 0;
			paging_T36OtherCfList.setActivePage(0);
		}

		setModelT36OtherCfList();
	}

	// Pembagian data untuk paging
	private void setModelT36OtherCfList() {		
		modelList_T36OtherCfList.clear();
		
		T36OtherCf selectedData = null;
		if (CommonUtils.isNotEmpty(list_T36OtherCfList)){
			
			int endT36OtherCfList = 0;
			if(startT36OtherCfList + paging_T36OtherCfList.getPageSize() < list_T36OtherCfList.size()) {
				endT36OtherCfList = startT36OtherCfList + paging_T36OtherCfList.getPageSize();
			} else {
				endT36OtherCfList = list_T36OtherCfList.size();
			}
			
			if (startT36OtherCfList > endT36OtherCfList) {
				startT36OtherCfList = 0;
				paging_T36OtherCfList.setActivePage(0);
			}
			
			modelList_T36OtherCfList.addAll(list_T36OtherCfList.subList(startT36OtherCfList, endT36OtherCfList));

			paging_T36OtherCfList.setDetailed(true);
			paging_T36OtherCfList.setTotalSize(list_T36OtherCfList.size());
			
			listBoxT36OtherCf.setModel(modelList_T36OtherCfList);
			listBoxT36OtherCf.setSelectedIndex(0);
	
			selectedData = list_T36OtherCfList.get(startT36OtherCfList);
		} else {
			paging_T36OtherCfList.setDetailed(false);
			listBoxT36OtherCf.setModel(modelList_T36OtherCfList);
			paging_T36OtherCfList.setTotalSize(0);
		}
		
		getT36OtherCfMainCtrl().setSelectedT36OtherCf(selectedData);

	}
	

	
    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
              @Override
              public void render(Listitem item, Object data) throws Exception {
                
            	final T36OtherCf rec = (T36OtherCf) data;

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
                info.setParent(windowT36OtherCfList);
                lc.setTooltip(info);
                lc.setParent(item);
                
                lc = new Listcell(rec.getCompany());
                lc.setParent(item);
                
                String vReg = "AR Installment";
				if (rec.getReg().equals("ARI") == true){
					vReg = "AR Installment";
				} else {
					if (rec.getReg().equals("API") == true){
						vReg = "AP Installment";
					} else {
						if (rec.getReg().equals("PIB") == true){
							vReg = "PIB Consignment";
						} else {
							if (rec.getReg().equals("OSO") == true){
								vReg = "Outstanding SO";
							} else {								
								if (rec.getReg().equals("OTH") == true){
									vReg = "Other Expenses";
								}
							}
						}
					}
				}				
                lc = new Listcell(vReg);
                lc.setParent(item);
                
                lc = new Listcell(rec.getKeterangan());
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.formatNumberManual(rec.getAmount(), "#,###,###"));				
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				
				String vTipe = "Tetap/Rutin";
				if (rec.getTipe().equals("F") == true){
					vTipe = "Tetap/Rutin";
				} else {
					if (rec.getTipe().equals("NF") == true){
						vTipe = "Tidak Tetap";
					} 
				}				
                lc = new Listcell(vTipe);
                lc.setParent(item);
	                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getDueDate()));
	            lc.setParent(item);	      
	            
	            String vBasis = "";
	            if (CommonUtils.isNotEmpty(rec.getBasis())){
	            	if (rec.getBasis().equals("W") == true){
						vBasis = "Mingguan";
					} else {
						if (rec.getBasis().equals("M") == true){
							vBasis = "Bulanan";
						} 
					}		
	            }						
                lc = new Listcell(vBasis);
                lc.setParent(item);
                
                String vEvery ="";
                if (CommonUtils.isNotEmpty(rec.getEvery())){               	
	                if (rec.getBasis().equals("W") == true){
		                if (rec.getEvery() == 1){
		                	vEvery = "SENIN";
						} else {
							if (rec.getEvery() == 2){
								vEvery = "SELASA";
							} else {
								if (rec.getEvery() == 3){
									vEvery = "RABU";
								} else {
									if (rec.getEvery() == 4){
										vEvery = "KAMIS";
									} else {
										if (rec.getEvery() == 5){
											vEvery = "JUMAT";
										} else {
											if (rec.getEvery() == 6){
												vEvery = "SABTU";
											} else {
												if (rec.getEvery() == 7){
													vEvery = "MINGGU";
												} 
											}
										}
									}
								}
							}
						}
	                } else {
	                	if (CommonUtils.isNotEmpty(rec.getBasis())){
	                		vEvery = String.valueOf(rec.getEvery());
	                	} 
	                }
                }                
	            lc = new Listcell(vEvery);
                lc.setParent(item);
                
                lc = new Listcell(CommonUtils.convertDateToString(rec.getFromDate()));
	            lc.setParent(item);	  
	            
	            lc = new Listcell(CommonUtils.convertDateToString(rec.getUptoDate()));
	            lc.setParent(item);	  
	            				
				
                item.setValue(data);
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedT36OtherCfItem");
              }
        };
    }
    
	private EventListener onPaging_T36OtherCfList(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startT36OtherCfList = pageNo * paging_T36OtherCfList.getPageSize();
		         setModelT36OtherCfList();
			}
		};
	}


    public void onDoubleClickedT36OtherCfItem(Event event) {
		if (listBoxT36OtherCf.getSelectedItem() != null) {
			T36OtherCf selectedData = (T36OtherCf) listBoxT36OtherCf.getSelectedItem().getValue();
			if (selectedData != null) {
				setSelectedT36OtherCf(selectedData);
	
	            if (getT36OtherCfMainCtrl().getT36OtherCfDetailCtrl() == null) {
	            	Events.sendEvent(new Event("onSelect", getT36OtherCfMainCtrl().tabT36OtherCfDetail, null));
	            } else if (getT36OtherCfMainCtrl().getT36OtherCfDetailCtrl().getBinder() == null) {
	                Events.sendEvent(new Event("onSelect", getT36OtherCfMainCtrl().tabT36OtherCfDetail, null));
	            }
	            
	            Events.sendEvent(new Event("onSelect", getT36OtherCfMainCtrl().tabT36OtherCfDetail, selectedData));
	            
	            getT36OtherCfMainCtrl().getT36OtherCfDetailCtrl().doRenderMode("View"); 
			}
		}
    }


    public void onSelect$listBoxT36OtherCf(Event event) {
		if (listBoxT36OtherCf.getSelectedItem() != null) {
			T36OtherCf selectedData = (T36OtherCf) listBoxT36OtherCf.getSelectedItem().getValue();
			
			if (selectedData != null) {
				
				if (getT36OtherCfMainCtrl().getT36OtherCfDetailCtrl() == null) {
					Events.sendEvent(new Event("onSelect",
							getT36OtherCfMainCtrl().tabT36OtherCfDetail, null));
		
				} else if (getT36OtherCfMainCtrl().getT36OtherCfDetailCtrl().getBinder() == null) {
					Events.sendEvent(new Event("onSelect",
							getT36OtherCfMainCtrl().tabT36OtherCfDetail, null));
				}
				
				getT36OtherCfMainCtrl().setSelectedT36OtherCf(selectedData);
				
			}
		}	
    }    
    
    
  
    
    
    
    public void onSort$listheader_T36OtherCfList_Company(Event event) {
    	sortingData(listheader_T36OtherCfList_Company, T36OtherCfComparator.COMPARE_BY_COMPANY);
    }
    
    public void onSort$listheader_T36OtherCfList_Reg(Event event) {
    	sortingData(listheader_T36OtherCfList_Reg, T36OtherCfComparator.COMPARE_BY_REG);
    }
    
    public void onSort$listheader_T36OtherCfList_Keterangan(Event event) {
    	sortingData(listheader_T36OtherCfList_Keterangan, T36OtherCfComparator.COMPARE_BY_KETERANGAN);
    }
    
    public void onSort$listheader_T36OtherCfList_Amount(Event event) {
    	sortingData(listheader_T36OtherCfList_Amount, T36OtherCfComparator.COMPARE_BY_AMOUNT);
    }

    public void onSort$listheader_T36OtherCfList_Tipe(Event event) {
    	sortingData(listheader_T36OtherCfList_Tipe, T36OtherCfComparator.COMPARE_BY_TIPE);
    }
    
    public void onSort$listheader_T36OtherCfList_Duedate(Event event) {
    	sortingData(listheader_T36OtherCfList_Duedate, T36OtherCfComparator.COMPARE_BY_DUEDATE);
    }
    
    public void onSort$listheader_T36OtherCfList_Basis(Event event) {
    	sortingData(listheader_T36OtherCfList_Basis, T36OtherCfComparator.COMPARE_BY_BASIS);
    }
    
    public void onSort$listheader_T36OtherCfList_Every(Event event) {
    	sortingData(listheader_T36OtherCfList_Every, T36OtherCfComparator.COMPARE_BY_EVERY);
    }

    public void onSort$listheader_T36OtherCfList_FromDate(Event event) {
    	sortingData(listheader_T36OtherCfList_FromDate, T36OtherCfComparator.COMPARE_BY_FROMDATE);
    }
    
    public void onSort$listheader_T36OtherCfList_UptoDate(Event event) {
    	sortingData(listheader_T36OtherCfList_UptoDate, T36OtherCfComparator.COMPARE_BY_UPTODATE);
    }
       
    private void sortingData(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T36OtherCfList, new T36OtherCfComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T36OtherCfList, new T36OtherCfComparator(true, sortBy));
    		}
    	}
    	
    	setModelT36OtherCfList();    
    }
    
    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_T36OtherCfList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowT36OtherCfList.invalidate();
    }

    public void clearSearchBox(){
        // empty the text search boxes
    	
    	cmbCompany.setSelectedIndex(0);
    	cmbReg.setSelectedIndex(0);
    	txtKeterangan.setValue(null);
    	txtAmount.setValue(null);
    	cmbTipe.setSelectedIndex(0);
    	txtDuedate.setValue(null);
    	cmbBasis.setSelectedIndex(0);
    	
    	txtEvery.setValue(null);
    	txtFromDate.setValue(null);
    	txtUptoDate.setValue(null);

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


    public void setSelectedT36OtherCf(T36OtherCf selectedT36OtherCf) {
        getT36OtherCfMainCtrl().setSelectedT36OtherCf(selectedT36OtherCf);
    }

    public T36OtherCf getSelectedT36OtherCf() {
        return getT36OtherCfMainCtrl().getSelectedT36OtherCf();
    }

    public void setT36OtherCfMainCtrl(T36OtherCfMainCtrl T36OtherCfMainCtrl) {
        this.T36OtherCfMainCtrl = T36OtherCfMainCtrl;
    }

    public T36OtherCfMainCtrl getT36OtherCfMainCtrl() {
        return this.T36OtherCfMainCtrl;
    }

}

