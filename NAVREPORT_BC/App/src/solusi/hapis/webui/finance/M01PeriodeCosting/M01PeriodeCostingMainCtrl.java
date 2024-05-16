package solusi.hapis.webui.finance.M01PeriodeCosting;


import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.M01PeriodeCosting;
import solusi.hapis.backend.navbi.service.M01PeriodeCostingService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 12-04-2021
 */

public class M01PeriodeCostingMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowM01PeriodeCostingMain; 

    // Tabs
    protected Tabbox tabbox_M01PeriodeCostingMain;
    protected Tab tabM01PeriodeCostingList;
    protected Tab tabM01PeriodeCostingDetail;
//    protected Tab tabM01PeriodeCostingPrint;
    protected Tabpanel tabPanelM01PeriodeCostingList;
    protected Tabpanel tabPanelM01PeriodeCostingDetail;
//    protected Tabpanel tabPanelM01PeriodeCostingPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_M01PeriodeCosting_";
    private ButtonStatusCtrl btnCtrlM01PeriodeCosting;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
//    protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private M01PeriodeCostingListCtrl M01PeriodeCostingListCtrl;
    private M01PeriodeCostingDetailCtrl M01PeriodeCostingDetailCtrl;
//    private M01PeriodeCostingPrintCtrl M01PeriodeCostingPrintCtrl;

    // Databinding
    private M01PeriodeCosting selectedM01PeriodeCosting;
    private M01PeriodeCosting postedM01PeriodeCosting;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private M01PeriodeCostingService M01PeriodeCostingService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/M01PeriodeCosting/M01PeriodeCostingDetail.zul";
	private String zulPageList = "/WEB-INF/pages/finance/M01PeriodeCosting/M01PeriodeCostingList.zul";
//	private String zulPagePrint = "/WEB-INF/pages/tabel/M01PeriodeCosting/M01PeriodeCostingPrint.zul";

    /**
     * Form State <br/>
     * 0 = inquiry <br/>
     * 1 = new <br/>
     * 2 = edit <br/>
     * 3 = delete <br/>
     */
    private int state = 0;
    
    /**
     * default constructor.<br>
     */
    public M01PeriodeCostingMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowM01PeriodeCostingMain(Event event) throws Exception {
        windowM01PeriodeCostingMain.setContentStyle("padding:0px;");

        btnCtrlM01PeriodeCosting = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlM01PeriodeCosting.setInitInquiryButton();

        tabM01PeriodeCostingList.setSelected(true);

        if (tabPanelM01PeriodeCostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM01PeriodeCostingList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabM01PeriodeCostingList(Event event) throws IOException, InterruptedException {
    	if (tabPanelM01PeriodeCostingList.getFirstChild() != null) {

            tabM01PeriodeCostingList.setSelected(true);
        	btnCtrlM01PeriodeCosting.setInitInquiryButton();
        	getM01PeriodeCostingListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelM01PeriodeCostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM01PeriodeCostingList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabM01PeriodeCostingDetail(Event event) throws IOException {
        if (tabPanelM01PeriodeCostingDetail.getFirstChild() != null) {
        	tabM01PeriodeCostingDetail.setSelected(true);            
        	
        	getM01PeriodeCostingDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getM01PeriodeCostingDetailCtrl().doRenderMode("View");   
            
            btnCtrlM01PeriodeCosting.setInitInquiryButton();

            if (getM01PeriodeCostingDetailCtrl().getBinder() != null) {
            	getM01PeriodeCostingDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelM01PeriodeCostingDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM01PeriodeCostingDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

//    public void onSelect$tabM01PeriodeCostingPrint(Event event) throws IOException, ParseException {
//    	if (tabPanelM01PeriodeCostingPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelM01PeriodeCostingPrint, this, "ModuleMainController", zulPagePrint);
//        }
//    	
//    	// Check if the tabpanel is already loaded
//        if (tabPanelM01PeriodeCostingPrint.getFirstChild() != null) {
//            tabM01PeriodeCostingPrint.setSelected(true);        
//
//            getM01PeriodeCostingPrintCtrl().doReadOnlyMode(true);
//            
//            return;
//        }
//
//        
//    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getM01PeriodeCostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabM01PeriodeCostingDetail, null));
        } else if (getM01PeriodeCostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabM01PeriodeCostingDetail, null));
        }

        M01PeriodeCosting anM01PeriodeCosting = new M01PeriodeCosting();

        // Set Default Value Di sini ------------------------------------- start
       
        anM01PeriodeCosting.setCloseKomisi("N");
        anM01PeriodeCosting.setCloseTqs("N");
        
        setSelectedM01PeriodeCosting(anM01PeriodeCosting);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlM01PeriodeCosting.setInitFormButton();
        
        // select tab Detail
        tabM01PeriodeCostingDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getM01PeriodeCostingDetailCtrl().getBinder() != null) {
        	getM01PeriodeCostingDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getM01PeriodeCostingDetailCtrl().doRenderMode("New");
        getM01PeriodeCostingDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedM01PeriodeCosting =(M01PeriodeCosting) ZksampleBeanUtils.cloneBean(getSelectedM01PeriodeCosting());
			
			//Databinding LOV
			
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InstantiationException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        
    	if (tabPanelM01PeriodeCostingDetail.getFirstChild() != null) {
        	getM01PeriodeCostingDetailCtrl().getBinder().loadAll();
        }
        
        if (getM01PeriodeCostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabM01PeriodeCostingDetail, null));
        } else if (getM01PeriodeCostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabM01PeriodeCostingDetail, null));
        }

        // set button
        btnCtrlM01PeriodeCosting.setInitFormButton();

        // select tab Detail
        tabM01PeriodeCostingDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getM01PeriodeCostingDetailCtrl().doRenderCombo();
        getM01PeriodeCostingDetailCtrl().doRenderMode("Edit");
    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedM01PeriodeCosting() != null) {
	        final M01PeriodeCosting anM01PeriodeCosting = getSelectedM01PeriodeCosting();
	        if (anM01PeriodeCosting != null) {
	
	            // Show a confirm box
	        	String deleteRecord = "Periode Closing : "+anM01PeriodeCosting.getMasa() +" - "+anM01PeriodeCosting.getTahun();
	            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + deleteRecord;
	            final String title = Labels.getLabel("message.Deleting.Record");
	
	            MultiLineMessageBox.doSetTemplate();
	            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
	                @Override
	                public void onEvent(Event evt) {
	                    switch (((Integer) evt.getData()).intValue()) {
	                        case MultiLineMessageBox.YES:
	                            try {
	                                deleteBean();
	                            } catch (InterruptedException e) {
	                                e.printStackTrace();
	                            }
	                            break;
	                        case MultiLineMessageBox.NO:
	                            break;
	                    }
	                }
	
	                private void deleteBean() throws InterruptedException {
	                	try {
	                        getM01PeriodeCostingService().delete(anM01PeriodeCosting);
	                        setSelectedM01PeriodeCosting(null);
	                        
	                        // refresh the list
	                        getM01PeriodeCostingListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlM01PeriodeCosting.setInitInquiryButton();
	        setSelectedM01PeriodeCosting(null);
	        
	        // refresh the list
	        getM01PeriodeCostingListCtrl().doFillListbox();
	        tabM01PeriodeCostingList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getM01PeriodeCostingDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getM01PeriodeCostingDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getM01PeriodeCostingService().save(getSelectedM01PeriodeCosting());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getM01PeriodeCostingService().update(getSelectedM01PeriodeCosting());
	            // refresh the list
	            btnCtrlM01PeriodeCosting.setInitInquiryButton();
	            getM01PeriodeCostingListCtrl().doFillListbox();
                
                tabM01PeriodeCostingList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlM01PeriodeCosting.setInitInquiryButton();
        
        tabM01PeriodeCostingList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getM01PeriodeCostingListCtrl().doFillListbox();
        }
        
    	if(postedM01PeriodeCosting != null){
           	setSelectedM01PeriodeCosting(postedM01PeriodeCosting);
           	
           	//Databinding LOV

           	
    	}
    	if(getM01PeriodeCostingDetailCtrl().binder != null){
    		getM01PeriodeCostingDetailCtrl().binder.loadAll();
    	} 
    }
    
    // Saat Button Listing di klik
//    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
//    	
//    	if (getM01PeriodeCostingPrintCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabM01PeriodeCostingPrint, null));
//        }
//
//        if (tabPanelM01PeriodeCostingPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelM01PeriodeCostingPrint, this, "ModuleMainController", zulPagePrint);
//        }
//        
//        getM01PeriodeCostingPrintCtrl().doReadOnlyMode(false);
//        
//        btnCtrlM01PeriodeCosting.setInitPrintButton();
//        tabM01PeriodeCostingPrint.setSelected(true);
//
//    }
    
    // Saat Button OK di klik
//    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
//    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listSalesperson.jasper";
//    	
//    	new JReportGeneratorWindow(getM01PeriodeCostingPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
//    }
//    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getM01PeriodeCostingListCtrl() != null) {
        	getM01PeriodeCostingListCtrl().getSearchData();        	
        	tabM01PeriodeCostingList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getM01PeriodeCostingListCtrl() != null) {
        	getM01PeriodeCostingListCtrl().clearSearchBox();
        	getM01PeriodeCostingListCtrl().getSearchData();        	
        	tabM01PeriodeCostingList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabM01PeriodeCostingDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabM01PeriodeCostingList.setDisabled(true);
//	        tabM01PeriodeCostingPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabM01PeriodeCostingDetail.setLabel(Labels.getLabel("common.Details"));
	        tabM01PeriodeCostingList.setDisabled(false);
//	        tabM01PeriodeCostingPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedM01PeriodeCosting(M01PeriodeCosting selectedM01PeriodeCosting) {
        this.selectedM01PeriodeCosting = selectedM01PeriodeCosting;
    }

    public M01PeriodeCosting getSelectedM01PeriodeCosting() {
        return this.selectedM01PeriodeCosting;
    }


	public void setM01PeriodeCostingService(M01PeriodeCostingService M01PeriodeCostingService) {
        this.M01PeriodeCostingService = M01PeriodeCostingService;
    }

    public M01PeriodeCostingService getM01PeriodeCostingService() {
        return this.M01PeriodeCostingService;
    }

    public void setM01PeriodeCostingListCtrl(M01PeriodeCostingListCtrl M01PeriodeCostingListCtrl) {
        this.M01PeriodeCostingListCtrl = M01PeriodeCostingListCtrl;
    }

    public M01PeriodeCostingListCtrl getM01PeriodeCostingListCtrl() {
        return this.M01PeriodeCostingListCtrl;
    }

    public void setM01PeriodeCostingDetailCtrl(M01PeriodeCostingDetailCtrl M01PeriodeCostingDetailCtrl) {
        this.M01PeriodeCostingDetailCtrl = M01PeriodeCostingDetailCtrl;
    }

    public M01PeriodeCostingDetailCtrl getM01PeriodeCostingDetailCtrl() {
        return this.M01PeriodeCostingDetailCtrl;
    }

//	public void setM01PeriodeCostingPrintCtrl(M01PeriodeCostingPrintCtrl M01PeriodeCostingPrintCtrl) {
//		this.M01PeriodeCostingPrintCtrl = M01PeriodeCostingPrintCtrl;
//	}
//
//	public M01PeriodeCostingPrintCtrl getM01PeriodeCostingPrintCtrl() {
//		return this.M01PeriodeCostingPrintCtrl;
//	}

	public void setPostedM01PeriodeCosting(M01PeriodeCosting postedM01PeriodeCosting) {
		this.postedM01PeriodeCosting = postedM01PeriodeCosting;
	}

	public M01PeriodeCosting getPostedM01PeriodeCosting() {
		return postedM01PeriodeCosting;
	}

}
