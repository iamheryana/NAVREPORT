package solusi.hapis.webui.security.group;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
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

import solusi.hapis.backend.model.SecGroup;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class SecGroupMainCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7324315601434199193L;

    protected Window windowSecGroupMain; // autowired

    // Tabs
    protected Tabbox tabbox_SecGroupMain; // autowired
    protected Tab tabSecGroupList; // autowired
    protected Tab tabSecGroupDetail; // autowired
    protected Tab tabSecGroupPrint; // autowired
    protected Tabpanel tabPanelSecGroupList; // autowired
    protected Tabpanel tabPanelSecGroupDetail; // autowired
    protected Tabpanel tabPanelSecGroupPrint; // autowired

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
    private SecGroupListCtrl secGroupListCtrl;
    private SecGroupDetailCtrl secGroupDetailCtrl;
    private SecGroupPrintCtrl secGroupPrintCtrl;

    // Databinding
    private SecGroup selectedSecGroup;
    private BindingListModelList modelSecGroup;

    // ServiceDAOs / Domain Classes
    private SecurityService securityService;

    // always a copy from the bean before modifying. Used for reseting
    private SecGroup originalSecGroup;
    /**
     * Form State <br/>
     * 0 = inquiry <br/>
     * 1 = new <br/>
     * 2 = edit <br/>
     * 3 = delete <br/>
     * @editby Rachmat 24/09/2012 14:06
     */
    private int state = 0;
    /**
     * default constructor.<br>
     */
    public SecGroupMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        /**
         * 1. Set an 'alias' for this composer name to access it in the
         * zul-file.<br>
         * 2. Set the parameter 'recurse' to 'false' to avoid problems with
         * managing more than one zul-file in one page. Otherwise it would be
         * overridden and can ends in curious error messages.
         */
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
    public void onCreate$windowSecGroupMain(Event event) throws Exception {
        windowSecGroupMain.setContentStyle("padding:0px;");

        /**
         * Initiate the first loading by selecting the customerList tab and
         * create the components from the zul-file.
         */
        tabSecGroupList.setSelected(true);
//        btnNew.setVisible(true);
//    	btnEdit.setVisible(true);
//    	btnDelete.setVisible(true);
//    	btnSave.setVisible(false);
    	btnCancel.setVisible(false);
    	btnSearch.setVisible(true);
    	btnClear.setVisible(true);
    	btnOK.setVisible(false);
		btnListing.setVisible(true);

        if (tabPanelSecGroupList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecGroupList, this, "ModuleMainController", "/WEB-INF/pages/security/group/SecGroupList.zul");
        }
    }

    /**
     * When the tab 'tabM19subunitList' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void onSelect$tabSecGroupList(Event event) throws IOException {
//    	btnNew.setVisible(true);
//    	btnEdit.setVisible(true);
//    	btnDelete.setVisible(true);
//    	btnSave.setVisible(false);
    	btnCancel.setVisible(false);
    	btnSearch.setVisible(true);
    	btnClear.setVisible(true);
    	btnOK.setVisible(false);
		btnListing.setVisible(true);
        // Check if the tabpanel is already loaded
        if (tabPanelSecGroupList.getFirstChild() != null) {
            doResetToInitValues();
            tabSecGroupList.setSelected(true);
            getSecGroupListCtrl().doFillListbox();

            return;
        }

        if (tabPanelSecGroupList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecGroupList, this, "ModuleMainController", "/WEB-INF/pages/security/group/SecGroupList.zul");
        }

    }

    /**
     * When the tab 'tabPanelM19subunitDetail' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws IOException
     */
    public void onSelect$tabSecGroupDetail(Event event) throws IOException {
    	
        // Check if the tabpanel is already loaded
        if (tabPanelSecGroupDetail.getFirstChild() != null) {
            tabSecGroupDetail.setSelected(true);
            doStoreInitValues();
            getSecGroupDetailCtrl().doReadOnlyMode(true);
            getSecGroupDetailCtrl().getBinder().loadAll();
            
            return;
        }

        if (tabPanelSecGroupDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecGroupDetail, this, "ModuleMainController", "/WEB-INF/pages/security/group/SecGroupDetail.zul");
        }
    }

    
    public void onSelect$tabSecGroupPrint(Event event) throws IOException {
    	
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelSecGroupPrint!= null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecGroupPrint, 
            		this, "ModuleMainController", 
            		"/WEB-INF/pages/security/group/SecGroupPrint.zul");
        }
        
        if (tabPanelSecGroupPrint.getFirstChild() != null) {
            tabSecGroupPrint.setSelected(true);            
            getSecGroupPrintCtrl().doReadOnlyMode(true);
        }
    }
	
	public void onClick$btnOK(Event event) throws InterruptedException {
    	final Window win = (Window) Path.getComponent("/outerIndexWindow");
//		new CRreportWindow(win, getSecGroupPrintCtrl().getParameterReport());
    }
	
    public void onClick$btnListing(Event event) throws InterruptedException {
    	// check first, if the tabs are created, if not than create it
        if (getSecGroupPrintCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabSecGroupPrint, null));
        }

        if (tabPanelSecGroupPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecGroupPrint, 
            		this, "ModuleMainController", 
            		"/WEB-INF/pages/security/group/SecGroupPrint.zul");
        }

		doStoreInitValues();
		tabSecGroupPrint.setSelected(true);   
        getSecGroupPrintCtrl().doReadOnlyMode(false);
//		setButtonMode(false);
		btnOK.setVisible(true);
		btnCancel.setVisible(true);
		btnListing.setVisible(false);
    }

    /**
     * When the "new" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnNew(Event event) throws InterruptedException {
    	state = 1;
//    	btnNew.setVisible(false);
//    	btnEdit.setVisible(false);
//    	btnDelete.setVisible(false);
//    	btnSave.setVisible(true);
    	btnCancel.setVisible(true);
    	btnSearch.setVisible(false);
    	btnClear.setVisible(false);
    	btnOK.setVisible(false);
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
//    	btnNew.setVisible(false);
//    	btnEdit.setVisible(false);
//    	btnDelete.setVisible(false);
//    	btnSave.setVisible(true);
    	btnCancel.setVisible(true);
    	btnSearch.setVisible(false);
    	btnClear.setVisible(false);
    	btnOK.setVisible(false);
    	doEdit(event);
    }

    /**
     * When the "delete" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnDelete(Event event) throws InterruptedException {
//    	btnNew.setVisible(false);
//    	btnEdit.setVisible(false);
//    	btnDelete.setVisible(false);
//    	btnSave.setVisible(true);
    	btnCancel.setVisible(true);
    	btnSearch.setVisible(false);
    	btnClear.setVisible(false);
    	btnOK.setVisible(false);
    	doDelete(event);
    }

    /**
     * When the "cancel" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnCancel(Event event) throws InterruptedException {
//    	btnNew.setVisible(true);
//    	btnEdit.setVisible(true);
//    	btnDelete.setVisible(true);
//    	btnSave.setVisible(false);
    	btnCancel.setVisible(false);
    	btnSearch.setVisible(true);
    	btnClear.setVisible(true);
    	btnListing.setVisible(true);
    	btnOK.setVisible(false);
    	doCancel(event);
    }

	/**
     * When the "Search" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getSecGroupListCtrl() != null) {
        	getSecGroupListCtrl().searchTable();        	
        	tabSecGroupList.setSelected(true);
        }
    }

    /**
     * When the "Clear" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getSecGroupListCtrl() != null) {
        	getSecGroupListCtrl().clearSearchBox();
        	getSecGroupListCtrl().searchTable();        	
        	tabSecGroupList.setSelected(true);
        }
    }			
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++ Business Logic ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * 1. Cancel the current action.<br>
     * 2. Reset the values to its origin.<br>
     * 3. Set UI components back to readonly/disable mode.<br>
     * 4. Set the buttons in edit mode.<br>
     *
     * @param event
     * @throws InterruptedException
     */
    private void doCancel(Event event) throws InterruptedException {
        // reset to the original object
        doResetToInitValues();      
        tabSecGroupList.setSelected(true);
        if (state == 1) {
        	getSecGroupListCtrl().doFillListbox();
        }
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
        Tab currentTab = tabbox_SecGroupMain.getSelectedTab();
        
        if (tabPanelSecGroupDetail.getFirstChild() != null) {
        	getSecGroupDetailCtrl().getBinder().loadAll();
        }
        // check first, if the tabs are created, if not than create it
        if (getSecGroupDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabSecGroupDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getSecGroupDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabSecGroupDetail, null));
        }

        // check if the tab is one of the Detail tabs. If so do not change the
        // selection of it
        if (!currentTab.equals(tabSecGroupDetail)) {
            tabSecGroupDetail.setSelected(true);
        } else {
            currentTab.setSelected(true);
        }

        // remember the old vars
        doStoreInitValues();

        getSecGroupDetailCtrl().doReadOnlyMode(false);
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
        // check first, if the tabs are created, if not than create them
        if (getSecGroupDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabSecGroupDetail, null));
        }

        final SecGroup anSecGroup = getSelectedSecGroup();
        if (anSecGroup != null) {

            // Show a confirm box
            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + anSecGroup.getGrpShortdescription();
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
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            break; //
                        case MultiLineMessageBox.NO:
                            break; //
                    }
                }

                private void deleteBean() throws InterruptedException {
                	try {
                        getSecurityService().delete(anSecGroup);
                    } catch (DataAccessException e) {
                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
                    }
                }
            }

            ) == MultiLineMessageBox.YES) {
            }

        }

        setSelectedSecGroup(null);
        // refresh the list
        getSecGroupListCtrl().doFillListbox();
        tabSecGroupList.setSelected(true);
    }

    /**
     * Saves all involved Beans to the DB.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doSave(Event event) throws InterruptedException {
    	// save all components data in the several tabs to the bean
        getSecGroupDetailCtrl().getBinder().saveAll();

        try {
            
        	getSecurityService().saveOrUpdate(getSelectedSecGroup());
            // if saving is successfully than actualize the beans as
            // origins.
            doStoreInitValues();
            if(state == 1) {
        		doNew(event);
        		// Hari 2012-09-04, Jika save dari form Edit maka kembali ke layar Inquiry
        	} else if(state == 2) {
	            getSecGroupListCtrl().doFillListbox();
                tabSecGroupList.setSelected(true);
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
        if (getSecGroupDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabSecGroupDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getSecGroupDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabSecGroupDetail, null));
        }

        // remember the current object
        doStoreInitValues();

        /** !!! DO NOT BREAK THE TIERS !!! */
        // We don't create a new DomainObject() in the frontend.
        // We GET it from the backend.
        final SecGroup anSecGroup = getSecurityService().getNewSecGroup();

        setSelectedSecGroup(anSecGroup);

        if (getSecGroupDetailCtrl().getBinder() != null) {
            getSecGroupDetailCtrl().getBinder().loadAll();
        }   
        
        // set editable Mode
        getSecGroupDetailCtrl().doReadOnlyMode(false);

        tabSecGroupDetail.setSelected(true);
        
        state = 1;

    }

    /**
     * Saves the selected object's current properties. We can get them back if a
     * modification is canceled.
     *
     * @see
     */
    public void doStoreInitValues() {

        if (getSelectedSecGroup() != null) {

            try {
                setOriginalSecGroup((SecGroup) ZksampleBeanUtils.cloneBean(getSelectedSecGroup()));
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

        if (getOriginalSecGroup() != null) {

            try {
                setSelectedSecGroup((SecGroup) ZksampleBeanUtils.cloneBean(getOriginalSecGroup()));
                // TODO Bug in DataBinder??
                windowSecGroupMain.invalidate();

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

	public SecGroupListCtrl getSecGroupListCtrl() {
		return secGroupListCtrl;
	}

	public void setSecGroupListCtrl(SecGroupListCtrl secGroupListCtrl) {
		this.secGroupListCtrl = secGroupListCtrl;
	}

	public SecGroupDetailCtrl getSecGroupDetailCtrl() {
		return secGroupDetailCtrl;
	}

	public void setSecGroupDetailCtrl(SecGroupDetailCtrl secGroupDetailCtrl) {
		this.secGroupDetailCtrl = secGroupDetailCtrl;
	}

	public SecGroup getSelectedSecGroup() {
		return selectedSecGroup;
	}

	public void setSelectedSecGroup(SecGroup selectedSecGroup) {
		this.selectedSecGroup = selectedSecGroup;
	}

	public BindingListModelList getModelSecGroup() {
		return modelSecGroup;
	}

	public void setModelSecGroup(BindingListModelList modelSecGroup) {
		this.modelSecGroup = modelSecGroup;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public SecGroup getOriginalSecGroup() {
		return originalSecGroup;
	}

	public void setOriginalSecGroup(SecGroup originalSecGroup) {
		this.originalSecGroup = originalSecGroup;
	}

	public void setSecGroupPrintCtrl(SecGroupPrintCtrl secGroupPrintCtrl) {
		this.secGroupPrintCtrl = secGroupPrintCtrl;
	}

	public SecGroupPrintCtrl getSecGroupPrintCtrl() {
		return secGroupPrintCtrl;
	}


}
