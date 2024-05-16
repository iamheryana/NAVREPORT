package solusi.hapis.webui.tabel.T03salesperson;


import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;

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

import solusi.hapis.backend.tabel.model.T03salesperson;
import solusi.hapis.backend.tabel.service.T03salespersonService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 16-03-2018
 */

public class T03salespersonMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT03salespersonMain; 

    // Tabs
    protected Tabbox tabbox_T03salespersonMain;
    protected Tab tabT03salespersonList;
    protected Tab tabT03salespersonDetail;
    protected Tab tabT03salespersonPrint;
    protected Tabpanel tabPanelT03salespersonList;
    protected Tabpanel tabPanelT03salespersonDetail;
    protected Tabpanel tabPanelT03salespersonPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T03salesperson_";
    private ButtonStatusCtrl btnCtrlT03salesperson;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T03salespersonListCtrl T03salespersonListCtrl;
    private T03salespersonDetailCtrl T03salespersonDetailCtrl;
    private T03salespersonPrintCtrl T03salespersonPrintCtrl;

    // Databinding
    private T03salesperson selectedT03salesperson;
    private T03salesperson postedT03salesperson;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T03salespersonService T03salespersonService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/tabel/T03salesperson/T03salespersonDetail.zul";
	private String zulPageList = "/WEB-INF/pages/tabel/T03salesperson/T03salespersonList.zul";
	private String zulPagePrint = "/WEB-INF/pages/tabel/T03salesperson/T03salespersonPrint.zul";

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
    public T03salespersonMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT03salespersonMain(Event event) throws Exception {
        windowT03salespersonMain.setContentStyle("padding:0px;");

        btnCtrlT03salesperson = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnListing, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT03salesperson.setInitInquiryButton();

        tabT03salespersonList.setSelected(true);

        if (tabPanelT03salespersonList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT03salespersonList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT03salespersonList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT03salespersonList.getFirstChild() != null) {

    		//RESTOREVALUE
    		//doResetToInitValues();
    		
            tabT03salespersonList.setSelected(true);
        	btnCtrlT03salesperson.setInitInquiryButton();
        	getT03salespersonListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT03salespersonList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT03salespersonList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT03salespersonDetail(Event event) throws IOException {
        if (tabPanelT03salespersonDetail.getFirstChild() != null) {
        	tabT03salespersonDetail.setSelected(true);            
        	
        	//RESTOREVALUE
    		//doResetToInitValues();
    		
        	// Render Inisialisasi posisi awal
            getT03salespersonDetailCtrl().doRenderMode("View");   
            
            btnCtrlT03salesperson.setInitInquiryButton();

            if (getT03salespersonDetailCtrl().getBinder() != null) {
            	getT03salespersonDetailCtrl().getBinder().loadAll();  
            }
            
            getT03salespersonDetailCtrl().displayDetail(getT03salespersonListCtrl().getList_T03salespersonDetailList());
			
			return;
		}

		if (tabPanelT03salespersonDetail != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelT03salespersonDetail,
					this, "ModuleMainController", zulPageDetail);
		}
    }

    public void onSelect$tabT03salespersonPrint(Event event) throws IOException, ParseException {
    	if (tabPanelT03salespersonPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT03salespersonPrint, this, "ModuleMainController", zulPagePrint);
        }
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelT03salespersonPrint.getFirstChild() != null) {
            tabT03salespersonPrint.setSelected(true);        

            getT03salespersonPrintCtrl().doReadOnlyMode(true);
            
            return;
        }

        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT03salespersonDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT03salespersonDetail, null));
        } else if (getT03salespersonDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT03salespersonDetail, null));
        }

        T03salesperson anT03salesperson = new T03salesperson();

        // Set Default Value Di sini ------------------------------------- start
       
        anT03salesperson.setTarget(new BigDecimal(0));
        setSelectedT03salesperson(anT03salesperson);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT03salesperson.setInitFormButton();
        
        // select tab Detail
        tabT03salespersonDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT03salespersonDetailCtrl().getBinder() != null) {
        	getT03salespersonDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT03salespersonDetailCtrl().doRenderMode("New");
        
        getT03salespersonDetailCtrl().displayDetail(null);
        //getT03salespersonDetailCtrl().doRenderCombo();
    }
    
//    public void doStoreInitValues() {
//
//        if (getSelectedT03salesperson() != null) {
//
//            try {
//            	
//                setPostedT03salesperson((T03salesperson) ZksampleBeanUtils.cloneBean(getSelectedT03salesperson()));
//                
//                     
//            } catch (final IllegalAccessException e) {
//                throw new RuntimeException(e);
//            } catch (final InstantiationException e) {
//                throw new RuntimeException(e);
//            } catch (final InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (final NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
//        try {
//			postedT03salesperson =(T03salesperson) ZksampleBeanUtils.cloneBean(getSelectedT03salesperson());
//			
//			//Databinding LOV
//			
//        } catch (final IllegalAccessException e) {
//            throw new RuntimeException(e);
//        } catch (final InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (final InvocationTargetException e) {
//            throw new RuntimeException(e);
//        } catch (final NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
        
		if(getSelectedT03salesperson() != null){
	    	if (tabPanelT03salespersonDetail.getFirstChild() != null) {
	        	getT03salespersonDetailCtrl().getBinder().loadAll();
	        }
	        
	        if (getT03salespersonDetailCtrl() == null) {
	            Events.sendEvent(new Event("onSelect", tabT03salespersonDetail, null));
	        } else if (getT03salespersonDetailCtrl().getBinder() == null) {
	            Events.sendEvent(new Event("onSelect", tabT03salespersonDetail, null));
	        }
	
	        //doStoreInitValues();
	        
	        // set button
	        btnCtrlT03salesperson.setInitFormButton();
	
	        // select tab Detail
	        tabT03salespersonDetail.setSelected(true);
	        renderTabAndTitle("Edit");
	        
	        state = 2;
	        
	        // set render layar
	        //getT03salespersonDetailCtrl().doRenderCombo();
	        getT03salespersonDetailCtrl().doRenderMode("Edit");
	        
	        getT03salespersonDetailCtrl().displayDetail(getT03salespersonListCtrl().getList_T03salespersonDetailList());
        
  
		}
	}
    
//    public void doResetToInitValues() {
//
//        if (getPostedT03salesperson() != null) {
//
//            try {
//            	setSelectedT03salesperson((T03salesperson) ZksampleBeanUtils.cloneBean(getPostedT03salesperson()));
//            	
//            	
//                windowT03salespersonMain.invalidate();
//
//            } catch (final IllegalAccessException e) {
//                throw new RuntimeException(e);
//            } catch (final InstantiationException e) {
//                throw new RuntimeException(e);
//            } catch (final InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (final NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT03salesperson() != null) {
	        final T03salesperson anT03salesperson = getSelectedT03salesperson();
	        if (anT03salesperson != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anT03salesperson.getSales() +" - "+anT03salesperson.getSalesName();
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
	                        getT03salespersonService().delete(anT03salesperson);
	                        setSelectedT03salesperson(null);
	                        
	                        // refresh the list
	                        getT03salespersonListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT03salesperson.setInitInquiryButton();
	        setSelectedT03salesperson(null);
	        
	        // refresh the list
	        getT03salespersonListCtrl().doFillListbox();
	        tabT03salespersonList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT03salespersonDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT03salespersonDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT03salespersonService().insert(getSelectedT03salesperson());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT03salespersonService().update(getSelectedT03salesperson(), getT03salespersonDetailCtrl().getList_DeletedT03salespersonDetailList());
	            // refresh the list
	            btnCtrlT03salesperson.setInitInquiryButton();
	            getT03salespersonListCtrl().doFillListbox();
                
                tabT03salespersonList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT03salesperson.setInitInquiryButton();
        
       	//RESTOREVALUE
       	//doResetToInitValues();
       	
        tabT03salespersonList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT03salespersonListCtrl().doFillListbox();
        }
        
    	
    	if(getT03salespersonDetailCtrl().binder != null){
    		getT03salespersonDetailCtrl().binder.loadAll();
    	} 
    }
    
    // Saat Button Listing di klik
    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
    	
    	if (getT03salespersonPrintCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT03salespersonPrint, null));
        }

        if (tabPanelT03salespersonPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT03salespersonPrint, this, "ModuleMainController", zulPagePrint);
        }
        
        getT03salespersonPrintCtrl().doReadOnlyMode(false);
        
        btnCtrlT03salesperson.setInitPrintButton();
        tabT03salespersonPrint.setSelected(true);

    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listSalesperson.jasper";
    	
    	new JReportGeneratorWindow(getT03salespersonPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT03salespersonListCtrl() != null) {
        	getT03salespersonListCtrl().getSearchData();        	
        	tabT03salespersonList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT03salespersonListCtrl() != null) {
        	getT03salespersonListCtrl().clearSearchBox();
        	getT03salespersonListCtrl().getSearchData();        	
        	tabT03salespersonList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT03salespersonDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT03salespersonList.setDisabled(true);
	        tabT03salespersonPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT03salespersonDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT03salespersonList.setDisabled(false);
	        tabT03salespersonPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT03salesperson(T03salesperson selectedT03salesperson) {
        this.selectedT03salesperson = selectedT03salesperson;
    }

    public T03salesperson getSelectedT03salesperson() {
        return this.selectedT03salesperson;
    }


	public void setT03salespersonService(T03salespersonService T03salespersonService) {
        this.T03salespersonService = T03salespersonService;
    }

    public T03salespersonService getT03salespersonService() {
        return this.T03salespersonService;
    }

    public void setT03salespersonListCtrl(T03salespersonListCtrl T03salespersonListCtrl) {
        this.T03salespersonListCtrl = T03salespersonListCtrl;
    }

    public T03salespersonListCtrl getT03salespersonListCtrl() {
        return this.T03salespersonListCtrl;
    }

    public void setT03salespersonDetailCtrl(T03salespersonDetailCtrl T03salespersonDetailCtrl) {
        this.T03salespersonDetailCtrl = T03salespersonDetailCtrl;
    }

    public T03salespersonDetailCtrl getT03salespersonDetailCtrl() {
        return this.T03salespersonDetailCtrl;
    }

	public void setT03salespersonPrintCtrl(T03salespersonPrintCtrl T03salespersonPrintCtrl) {
		this.T03salespersonPrintCtrl = T03salespersonPrintCtrl;
	}

	public T03salespersonPrintCtrl getT03salespersonPrintCtrl() {
		return this.T03salespersonPrintCtrl;
	}

	public void setPostedT03salesperson(T03salesperson postedT03salesperson) {
		this.postedT03salesperson = postedT03salesperson;
	}

	public T03salesperson getPostedT03salesperson() {
		return postedT03salesperson;
	}

}
