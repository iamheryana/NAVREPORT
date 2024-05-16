package solusi.hapis.webui.tabel.T05periodecosting;


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

import solusi.hapis.backend.tabel.model.T05periodecosting;
import solusi.hapis.backend.tabel.service.T05periodecostingService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 25-05-2018
 */

public class T05periodecostingMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT05periodecostingMain; 

    // Tabs
    protected Tabbox tabbox_T05periodecostingMain;
    protected Tab tabT05periodecostingList;
    protected Tab tabT05periodecostingDetail;
//    protected Tab tabT05periodecostingPrint;
    protected Tabpanel tabPanelT05periodecostingList;
    protected Tabpanel tabPanelT05periodecostingDetail;
//    protected Tabpanel tabPanelT05periodecostingPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T05periodecosting_";
    private ButtonStatusCtrl btnCtrlT05periodecosting;
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
    private T05periodecostingListCtrl T05periodecostingListCtrl;
    private T05periodecostingDetailCtrl T05periodecostingDetailCtrl;
//    private T05periodecostingPrintCtrl T05periodecostingPrintCtrl;

    // Databinding
    private T05periodecosting selectedT05periodecosting;
    private T05periodecosting postedT05periodecosting;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T05periodecostingService T05periodecostingService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/tabel/T05periodecosting/T05periodecostingDetail.zul";
	private String zulPageList = "/WEB-INF/pages/tabel/T05periodecosting/T05periodecostingList.zul";
//	private String zulPagePrint = "/WEB-INF/pages/tabel/T05periodecosting/T05periodecostingPrint.zul";

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
    public T05periodecostingMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT05periodecostingMain(Event event) throws Exception {
        windowT05periodecostingMain.setContentStyle("padding:0px;");

        btnCtrlT05periodecosting = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT05periodecosting.setInitInquiryButton();

        tabT05periodecostingList.setSelected(true);

        if (tabPanelT05periodecostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT05periodecostingList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT05periodecostingList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT05periodecostingList.getFirstChild() != null) {

            tabT05periodecostingList.setSelected(true);
        	btnCtrlT05periodecosting.setInitInquiryButton();
        	getT05periodecostingListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT05periodecostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT05periodecostingList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT05periodecostingDetail(Event event) throws IOException {
        if (tabPanelT05periodecostingDetail.getFirstChild() != null) {
        	tabT05periodecostingDetail.setSelected(true);            
        	
        	getT05periodecostingDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT05periodecostingDetailCtrl().doRenderMode("View");   
            
            btnCtrlT05periodecosting.setInitInquiryButton();

            if (getT05periodecostingDetailCtrl().getBinder() != null) {
            	getT05periodecostingDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT05periodecostingDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT05periodecostingDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

//    public void onSelect$tabT05periodecostingPrint(Event event) throws IOException, ParseException {
//    	if (tabPanelT05periodecostingPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT05periodecostingPrint, this, "ModuleMainController", zulPagePrint);
//        }
//    	
//    	// Check if the tabpanel is already loaded
//        if (tabPanelT05periodecostingPrint.getFirstChild() != null) {
//            tabT05periodecostingPrint.setSelected(true);        
//
//            getT05periodecostingPrintCtrl().doReadOnlyMode(true);
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
    	
    	if (getT05periodecostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT05periodecostingDetail, null));
        } else if (getT05periodecostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT05periodecostingDetail, null));
        }

        T05periodecosting anT05periodecosting = new T05periodecosting();

        // Set Default Value Di sini ------------------------------------- start
       
        anT05periodecosting.setCloseKomisi("N");
        anT05periodecosting.setCloseTqs("N");
        
        setSelectedT05periodecosting(anT05periodecosting);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT05periodecosting.setInitFormButton();
        
        // select tab Detail
        tabT05periodecostingDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT05periodecostingDetailCtrl().getBinder() != null) {
        	getT05periodecostingDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT05periodecostingDetailCtrl().doRenderMode("New");
        getT05periodecostingDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT05periodecosting =(T05periodecosting) ZksampleBeanUtils.cloneBean(getSelectedT05periodecosting());
			
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
        
    	if (tabPanelT05periodecostingDetail.getFirstChild() != null) {
        	getT05periodecostingDetailCtrl().getBinder().loadAll();
        }
        
        if (getT05periodecostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT05periodecostingDetail, null));
        } else if (getT05periodecostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT05periodecostingDetail, null));
        }

        // set button
        btnCtrlT05periodecosting.setInitFormButton();

        // select tab Detail
        tabT05periodecostingDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getT05periodecostingDetailCtrl().doRenderCombo();
        getT05periodecostingDetailCtrl().doRenderMode("Edit");
    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT05periodecosting() != null) {
	        final T05periodecosting anT05periodecosting = getSelectedT05periodecosting();
	        if (anT05periodecosting != null) {
	
	            // Show a confirm box
	        	String deleteRecord = "Periode Closing : "+anT05periodecosting.getMasa() +" - "+anT05periodecosting.getTahun();
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
	                        getT05periodecostingService().delete(anT05periodecosting);
	                        setSelectedT05periodecosting(null);
	                        
	                        // refresh the list
	                        getT05periodecostingListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT05periodecosting.setInitInquiryButton();
	        setSelectedT05periodecosting(null);
	        
	        // refresh the list
	        getT05periodecostingListCtrl().doFillListbox();
	        tabT05periodecostingList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT05periodecostingDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT05periodecostingDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT05periodecostingService().save(getSelectedT05periodecosting());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT05periodecostingService().update(getSelectedT05periodecosting());
	            // refresh the list
	            btnCtrlT05periodecosting.setInitInquiryButton();
	            getT05periodecostingListCtrl().doFillListbox();
                
                tabT05periodecostingList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT05periodecosting.setInitInquiryButton();
        
        tabT05periodecostingList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT05periodecostingListCtrl().doFillListbox();
        }
        
    	if(postedT05periodecosting != null){
           	setSelectedT05periodecosting(postedT05periodecosting);
           	
           	//Databinding LOV

           	
    	}
    	if(getT05periodecostingDetailCtrl().binder != null){
    		getT05periodecostingDetailCtrl().binder.loadAll();
    	} 
    }
    
    // Saat Button Listing di klik
//    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
//    	
//    	if (getT05periodecostingPrintCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabT05periodecostingPrint, null));
//        }
//
//        if (tabPanelT05periodecostingPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT05periodecostingPrint, this, "ModuleMainController", zulPagePrint);
//        }
//        
//        getT05periodecostingPrintCtrl().doReadOnlyMode(false);
//        
//        btnCtrlT05periodecosting.setInitPrintButton();
//        tabT05periodecostingPrint.setSelected(true);
//
//    }
    
    // Saat Button OK di klik
//    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
//    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listSalesperson.jasper";
//    	
//    	new JReportGeneratorWindow(getT05periodecostingPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
//    }
//    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT05periodecostingListCtrl() != null) {
        	getT05periodecostingListCtrl().getSearchData();        	
        	tabT05periodecostingList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT05periodecostingListCtrl() != null) {
        	getT05periodecostingListCtrl().clearSearchBox();
        	getT05periodecostingListCtrl().getSearchData();        	
        	tabT05periodecostingList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT05periodecostingDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT05periodecostingList.setDisabled(true);
//	        tabT05periodecostingPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT05periodecostingDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT05periodecostingList.setDisabled(false);
//	        tabT05periodecostingPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT05periodecosting(T05periodecosting selectedT05periodecosting) {
        this.selectedT05periodecosting = selectedT05periodecosting;
    }

    public T05periodecosting getSelectedT05periodecosting() {
        return this.selectedT05periodecosting;
    }


	public void setT05periodecostingService(T05periodecostingService T05periodecostingService) {
        this.T05periodecostingService = T05periodecostingService;
    }

    public T05periodecostingService getT05periodecostingService() {
        return this.T05periodecostingService;
    }

    public void setT05periodecostingListCtrl(T05periodecostingListCtrl T05periodecostingListCtrl) {
        this.T05periodecostingListCtrl = T05periodecostingListCtrl;
    }

    public T05periodecostingListCtrl getT05periodecostingListCtrl() {
        return this.T05periodecostingListCtrl;
    }

    public void setT05periodecostingDetailCtrl(T05periodecostingDetailCtrl T05periodecostingDetailCtrl) {
        this.T05periodecostingDetailCtrl = T05periodecostingDetailCtrl;
    }

    public T05periodecostingDetailCtrl getT05periodecostingDetailCtrl() {
        return this.T05periodecostingDetailCtrl;
    }

//	public void setT05periodecostingPrintCtrl(T05periodecostingPrintCtrl T05periodecostingPrintCtrl) {
//		this.T05periodecostingPrintCtrl = T05periodecostingPrintCtrl;
//	}
//
//	public T05periodecostingPrintCtrl getT05periodecostingPrintCtrl() {
//		return this.T05periodecostingPrintCtrl;
//	}

	public void setPostedT05periodecosting(T05periodecosting postedT05periodecosting) {
		this.postedT05periodecosting = postedT05periodecosting;
	}

	public T05periodecosting getPostedT05periodecosting() {
		return postedT05periodecosting;
	}

}
