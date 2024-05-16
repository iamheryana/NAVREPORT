package solusi.hapis.webui.security.right;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecRight;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class SecRightMainCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7951162794273907968L;
    protected Window windowSecRightMain; // autowired

    // Tabs
    protected Tabbox tabbox_SecRightMain; // autowired
    protected Tab tabSecRightList; // autowired
    protected Tab tabSecRightDetail; // autowired
    protected Tab tabSecRightPrint; // autowired
    protected Tabpanel tabPanelSecRightList; // autowired
    protected Tabpanel tabPanelSecRightDetail; // autowired
    protected Tabpanel tabPanelSecRightPrint; // autowired
    
    // Button controller for the CRUD buttons
    protected Button btnNew; // autowired
    protected Button btnEdit; // autowired
    protected Button btnDelete; // autowired
    protected Button btnSave; // autowired
    protected Button btnCancel; // autowired
    protected Button btnSearch; // autowired
    protected Button btnClear; // autowired
    protected Button btnListing; // autowired
    protected Button btnOK; // autowired

    // Tab-Controllers for getting the binders
    private SecRightListCtrl secRightListCtrl;
    private SecRightDetailCtrl secRightDetailCtrl;
    private SecRightPrintCtrl secRightPrintCtrl;

    // Databinding
    private SecRight selectedSecRight;
    private BindingListModelList modelSecRight;

    // ServiceDAOs / Domain Classes
    private SecurityService securityService;

    // always a copy from the bean before modifying. Used for reseting
    private SecRight originalSecRight;
    
    private int state = 0;
    
    /**
     * default constructor.<br>
     */
    public SecRightMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++ Component Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Automatically called method from zk.
     *
     * @param event
     * @throws Exception
     */
    public void onCreate$windowSecRightMain(Event event) throws Exception {
        windowSecRightMain.setContentStyle("padding:0px;");
        
        /**
         * Initiate the first loading by selecting the List tab and
         * create the components from the zul-file.
         */
		setButtonMode(true);
        tabSecRightList.setSelected(true);

        if (tabPanelSecRightList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecRightList, this, "ModuleMainController", "/WEB-INF/pages/security/right/SecRightList.zul");
        }
    }

    /**
     * When the tab 'tabSecRightList' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws java.io.IOException
     * @throws InterruptedException 
     */
    public void onSelect$tabSecRightList(Event event) throws IOException, InterruptedException {
        // Check if the tabpanel is already loaded
    	if (tabPanelSecRightList.getFirstChild() != null) {
            doResetToInitValues();
            tabSecRightList.setSelected(true);
    		setButtonMode(true);
        	getSecRightListCtrl().doFillListbox();
        	getSecRightListCtrl().doRenderRightType();
            
            return;
        }

        if (tabPanelSecRightList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecRightList, this, "ModuleMainController", "/WEB-INF/pages/security/right/SecRightList.zul");
        }
    }

    /**
     * When the tab 'tabPanelSecRightDetail' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws IOException
     */
    public void onSelect$tabSecRightDetail(Event event) throws IOException {
        // Check if the tabpanel is already loaded
        if (tabPanelSecRightDetail.getFirstChild() != null) {
        	tabSecRightDetail.setSelected(true);
        	doResetToInitValues();
    		setButtonMode(true);
            getSecRightDetailCtrl().doReadOnlyMode(true);            
            getSecRightDetailCtrl().doRenderComboBox();
            getSecRightDetailCtrl().binder.loadAll();
            
            return;
        }

        if (tabPanelSecRightDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecRightDetail, this, "ModuleMainController", "/WEB-INF/pages/security/right/SecRightDetail.zul");
        }
    }
	    
    public void onSelect$tabSecRightPrint(Event event) throws IOException {
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelSecRightPrint!= null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecRightPrint, 
            		this, "ModuleMainController", 
            		"/WEB-INF/pages/security/right/SecRightPrint.zul");
        }
        
        if (tabPanelSecRightPrint.getFirstChild() != null) {
            tabSecRightPrint.setSelected(true);            
            getSecRightPrintCtrl().doReadOnlyMode(true);
        }
    }
	
	public void onClick$btnOK(Event event) throws InterruptedException {
//    	final Window win = (Window) Path.getComponent("/outerIndexWindow");
//		new CRreportWindow(win, getSecRightPrintCtrl().getParameterReport());
    }
	
    public void onClick$btnListing(Event event) throws InterruptedException {
    	// check first, if the tabs are created, if not than create it
        if (getSecRightPrintCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabSecRightPrint, null));
        }

        if (tabPanelSecRightPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecRightPrint, 
            		this, "ModuleMainController", 
            		"/WEB-INF/pages/security/right/SecRightPrint.zul");
        }

		doStoreInitValues();
		tabSecRightPrint.setSelected(true);   
        getSecRightPrintCtrl().doReadOnlyMode(false);
		setButtonMode(false);
		btnOK.setVisible(true);
    }
	
	public void setButtonMode(boolean b) {
//		btnDelete.setVisible(b);
//		btnEdit.setVisible(b);
//		btnNew.setVisible(b);
		btnCancel.setVisible(!b);
//		btnSave.setVisible(false);
		btnClear.setVisible(b);
		btnSearch.setVisible(b);
		btnListing.setVisible(b);
		btnOK.setVisible(false);
	}

    /**
     * When the "new" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnNew(Event event) throws InterruptedException {
    	doNew(event);
    }
    
    /**
     * When the "save" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnSave(Event event) throws InterruptedException {
        doSave(event);
    }

    /**
     * When the "cancel" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnEdit(Event event) throws InterruptedException {
    	doEdit(event);
    }

    /**
     * When the "delete" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnDelete(Event event) throws InterruptedException {
        doDelete(event);
    }

    /**
     * When the "cancel" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnCancel(Event event) throws InterruptedException {
        doCancel(event);
    }

    /**
     * When the "Search" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getSecRightListCtrl() != null) {
        	getSecRightListCtrl().searchTable();        	
        	tabSecRightList.setSelected(true);
        }
    }

    /**
     * When the "Refresh" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getSecRightListCtrl() != null) {
        	getSecRightListCtrl().clearSearchBox();
        	getSecRightListCtrl().searchTable();
        	tabSecRightList.setSelected(true);
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++ Business Logic ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * 1. Cancel the current action.<br/>
     * 2. Back to inquiry screen.<br/>
     *
     * @param event
     * @throws InterruptedException
     * @author hari
     */
    private void doCancel(Event event) throws InterruptedException {
    	doResetToInitValues();
    	setButtonMode(true);
        tabSecRightList.setSelected(true);
    	getSecRightListCtrl().doFillListbox();        
    }

    /**
     * Sets all UI-components to writable-mode. Sets the buttons to edit-Mode.
     * Checks first, if the NEEDED TABS with its contents are created. If not,
     * than create it by simulate an onSelect() with calling Events.sendEvent()
     *
     * @param event
     * @throws InterruptedException
     */
    private void doEdit(Event event) {
        // get the current Tab for later checking if we must change it
        Tab currentTab = tabbox_SecRightMain.getSelectedTab();
        if (tabPanelSecRightDetail.getFirstChild() != null) {
        	getSecRightDetailCtrl().getBinder().loadAll();
        }
        // check first, if the tabs are created, if not than create it
        if (getSecRightDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabSecRightDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getSecRightDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabSecRightDetail, null));
        }

        // check if the tab is one of the Detail tabs. If so do not change the
        // selection of it
        if (!currentTab.equals(tabSecRightDetail)) {
            tabSecRightDetail.setSelected(true);
        } else {
            currentTab.setSelected(true);
        }
 
        // remember the old vars
        doStoreInitValues();
        getSecRightDetailCtrl().doReadOnlyMode(false);
        getSecRightDetailCtrl().doRenderComboBox();
        state = 2;
    }

    /**
     * Deletes the selected Bean from the DB.
     *
     * @param event
     * @throws InterruptedException
     * @throws InterruptedException
     */
    private void doDelete(Event event) throws InterruptedException {
    	if (getSelectedSecRight() != null) {
	        final SecRight anSecRight = getSelectedSecRight();
	        if (anSecRight != null) {
	
	            // Show a confirm box
	            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + anSecRight.getRigName();
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
	                            break; //
	                        case MultiLineMessageBox.NO:
	                            break; //
	                    }
	                }
	
	                private void deleteBean() throws InterruptedException {
	                	try {
	                        getSecurityService().delete(anSecRight);
	                        setSelectedSecRight(null);
	            	        tabSecRightList.setSelected(true);
	                    } catch (DataAccessException e) {
	                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
    	}
    }
    
    /**
     * Saves all involved Beans to the DB.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doSave(Event event) throws InterruptedException {
    	getSecRightDetailCtrl().getBinder().saveAll();
        try {
            
        	getSecurityService().saveOrUpdate(getSelectedSecRight());
            // if saving is successfully than actualize the beans as
            // origins.
            doStoreInitValues();

            // Hari 2012-09-04, Jika save dari form New maka kembali ke layar New
        	if(state == 1) {
        		doNew(event);
        		// Hari 2012-09-04, Jika save dari form Edit maka kembali ke layar Inquiry
        	} else if(state == 2) {
	            // refresh the list
        		getSecRightListCtrl().doFillListbox();
                tabSecRightList.setSelected(true);
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
            return;
        }
    }

    /**
     * Sets all UI-components to writable-mode. Stores the current Beans as
     * originBeans and get new Objects from the backend.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doNew(Event event) {
    	// check first, if the tabs are created
        if (getSecRightDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabSecRightDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getSecRightDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabSecRightDetail, null));
        }

        // remember the current object
        doStoreInitValues();

        /** !!! DO NOT BREAK THE TIERS !!! */
        // We don't create a new DomainObject() in the frontend.
        // We GET it from the backend.
        SecRight anSecRight = new SecRight();
        
        setSelectedSecRight(anSecRight);
        
        getSecRightDetailCtrl().doRenderComboBox();
        getSecRightDetailCtrl().doReadOnlyMode(false);
        tabSecRightDetail.setSelected(true);
        
        state = 1;

        if (getSecRightDetailCtrl().getBinder() != null) {
            getSecRightDetailCtrl().getBinder().loadAll();  
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Resizes the container from the selected Tab.
     *
     * @param event
     */
    public void doResizeSelectedTab(Event event) {
        // logger.debug(event.toString());
        if (tabbox_SecRightMain.getSelectedTab() == tabSecRightDetail) {
            getSecRightDetailCtrl().doFitSize(event);
        } else if (tabbox_SecRightMain.getSelectedTab() == tabSecRightList) {
            // resize and fill Listbox new
            getSecRightListCtrl().doFillListbox();
        }
    }

    /**
     * Saves the selected object's current properties. We can get them back if a
     * modification is canceled.
     *
     * @see
     */
    public void doStoreInitValues() {

        if (getSelectedSecRight() != null) {

            try {
                setOriginalSecRight((SecRight) ZksampleBeanUtils.cloneBean(getSelectedSecRight()));
            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (final InstantiationException e) {
                throw new RuntimeException(e);
            } catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Reset the selected object to its origin property values.
     *
     * @see
     */
    public void doResetToInitValues() {

        if (getOriginalSecRight() != null) {

            try {
                setSelectedSecRight((SecRight) ZksampleBeanUtils.cloneBean(getOriginalSecRight()));
                // TODO Bug in DataBinder??
                windowSecRightMain.invalidate();

            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (final InstantiationException e) {
                throw new RuntimeException(e);
            } catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setOriginalSecRight(SecRight originalSecRight) {
        this.originalSecRight = originalSecRight;
    }

    public SecRight getOriginalSecRight() {
        return this.originalSecRight;
    }

    public void setSelectedSecRight(SecRight selectedSecRight) {
        this.selectedSecRight = selectedSecRight;
    }

    public SecRight getSelectedSecRight() {
        return this.selectedSecRight;
    }

	public SecRightListCtrl getSecRightListCtrl() {
		return secRightListCtrl;
	}

	public void setSecRightListCtrl(SecRightListCtrl secRightListCtrl) {
		this.secRightListCtrl = secRightListCtrl;
	}

	public SecRightDetailCtrl getSecRightDetailCtrl() {
		return secRightDetailCtrl;
	}

	public void setSecRightDetailCtrl(SecRightDetailCtrl secRightDetailCtrl) {
		this.secRightDetailCtrl = secRightDetailCtrl;
	}

	public BindingListModelList getModelSecRight() {
		return modelSecRight;
	}

	public void setModelSecRight(BindingListModelList modelSecRight) {
		this.modelSecRight = modelSecRight;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setSecRightPrintCtrl(SecRightPrintCtrl secRightPrintCtrl) {
		this.secRightPrintCtrl = secRightPrintCtrl;
	}

	public SecRightPrintCtrl getSecRightPrintCtrl() {
		return secRightPrintCtrl;
	}

}
