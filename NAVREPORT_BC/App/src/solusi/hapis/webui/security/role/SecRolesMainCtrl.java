package solusi.hapis.webui.security.role;


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

import solusi.hapis.backend.model.SecRole;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 01-05-2013
 */

public class SecRolesMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowsecRolesMain; 

    // Tabs
    protected Tabbox tabbox_secRolesMain;
    protected Tab tabsecRolesList;
    protected Tab tabsecRolesDetail;
    protected Tab tabsecRolesPrint;
    protected Tabpanel tabPanelsecRolesList;
    protected Tabpanel tabPanelsecRolesDetail;
    protected Tabpanel tabPanelsecRolesPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_Account_";
    private ButtonStatusCtrl btnCtrlsecRoles;
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
    private SecRolesListCtrl SecRolesListCtrl;
    private SecRolesDetailCtrl SecRolesDetailCtrl;
    private SecRolesPrintCtrl SecRolesPrintCtrl;

    // Databinding
    private SecRole selectedSecRole;
    private SecRole postedSecRole;

    // ServiceDAOs / Domain Classes
    private SecurityService securityService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/security/role/SecRolesDetail.zul";
	private String zulPageList = "/WEB-INF/pages/security/role/SecRolesList.zul";
	private String zulPagePrint = "/WEB-INF/pages/security/role/SecRolesPrint.zul";

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
    public SecRolesMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowsecRolesMain(Event event) throws Exception {
        windowsecRolesMain.setContentStyle("padding:0px;");

        btnCtrlsecRoles = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnListing, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlsecRoles.setInitInquiryButton();

        tabsecRolesList.setSelected(true);

        if (tabPanelsecRolesList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelsecRolesList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabsecRolesList(Event event) throws IOException, InterruptedException {
    	if (tabPanelsecRolesList.getFirstChild() != null) {

            tabsecRolesList.setSelected(true);
        	btnCtrlsecRoles.setInitInquiryButton();
        	getSecRolesListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelsecRolesList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelsecRolesList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabsecRolesDetail(Event event) throws IOException {
        if (tabPanelsecRolesDetail.getFirstChild() != null) {
        	tabsecRolesDetail.setSelected(true);            
        	
        	// Render Inisialisasi posisi awal
            getSecRolesDetailCtrl().doRenderMode("View");   
            
            btnCtrlsecRoles.setInitInquiryButton();

            if (getSecRolesDetailCtrl().getBinder() != null) {
            	getSecRolesDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelsecRolesDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelsecRolesDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    public void onSelect$tabsecRolesPrint(Event event) throws IOException {
    	if (tabPanelsecRolesPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelsecRolesPrint, this, "ModuleMainController", zulPagePrint);
        }
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelsecRolesPrint.getFirstChild() != null) {
            tabsecRolesPrint.setSelected(true);        

            getSecRolesPrintCtrl().doReadOnlyMode(true);
            
            return;
        }

        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getSecRolesDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabsecRolesDetail, null));
        } else if (getSecRolesDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabsecRolesDetail, null));
        }

        SecRole ansecRoles = new SecRole();

        // Set Default Value Di sini ------------------------------------- start

        
        setSelectedSecRole(ansecRoles);
        // --------------------------------------------------------------- end
        
        
        
       
        // set button
        btnCtrlsecRoles.setInitFormButton();
        
        // select tab Detail
        tabsecRolesDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getSecRolesDetailCtrl().getBinder() != null) {
        	getSecRolesDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getSecRolesDetailCtrl().doRenderMode("New");
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedSecRole =(SecRole) ZksampleBeanUtils.cloneBean(getSelectedSecRole());
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InstantiationException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        
    	if (tabPanelsecRolesDetail.getFirstChild() != null) {
        	getSecRolesDetailCtrl().getBinder().loadAll();
        }
        
        if (getSecRolesDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabsecRolesDetail, null));
        } else if (getSecRolesDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabsecRolesDetail, null));
        }
        
        // set button
        btnCtrlsecRoles.setInitFormButton();

        // select tab Detail
        tabsecRolesDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getSecRolesDetailCtrl().doRenderMode("Edit");

    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedSecRole()!= null) {
	        final SecRole ansecRoles = getSelectedSecRole();
	        if (ansecRoles != null) {
	
	            // Show a confirm box
	        	String deleteRecord = ansecRoles.getRolLongdescription();
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
	                        getSecurityService().delete(ansecRoles);
	                        setSelectedSecRole(null);
	                        
	                        // refresh the list
	                        getSecRolesListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlsecRoles.setInitInquiryButton();
	        setSelectedSecRole(null);
	        
	        // refresh the list
	        getSecRolesListCtrl().doFillListbox();
	        tabsecRolesList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getSecRolesDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getSecRolesDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {
        		getSecurityService().saveOrUpdate(getSelectedSecRole());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		getSecurityService().saveOrUpdate(getSelectedSecRole());
	            // refresh the list
	            btnCtrlsecRoles.setInitInquiryButton();
	            getSecRolesListCtrl().doFillListbox();
                
                tabsecRolesList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlsecRoles.setInitInquiryButton();
        
        tabsecRolesList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getSecRolesListCtrl().doFillListbox();
        }
        
    	if(postedSecRole != null){
           	setSelectedSecRole(postedSecRole);
    	}
    	if(getSecRolesDetailCtrl().binder != null){
    		getSecRolesDetailCtrl().binder.loadAll();
    	}       	
    }
    
    // Saat Button Listing di klik
    public void onClick$btnListing(Event event) throws InterruptedException {
    	
    	if (getSecRolesPrintCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabsecRolesPrint, null));
        }

        if (tabPanelsecRolesPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelsecRolesPrint, this, "ModuleMainController", zulPagePrint);
        }
        
        getSecRolesPrintCtrl().doReadOnlyMode(false);
        
        btnCtrlsecRoles.setInitPrintButton();
        tabsecRolesPrint.setSelected(true);

    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException {
    	// Nama dan lokasi Report
    	//String jasperRpt = "/solusi/hapis/webui/crossmoduletabel/listing/l_m01area.jasper";
    	//new JReportGeneratorWindow(getsecRolesPrintCtrl().getParameterReport(), jasperRpt, "PDF");
    
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getSecRolesListCtrl() != null) {
        	getSecRolesListCtrl().getSearchData();        	
        	tabsecRolesList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getSecRolesListCtrl() != null) {
        	getSecRolesListCtrl().clearSearchBox();
        	getSecRolesListCtrl().getSearchData();        	
        	tabsecRolesList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabsecRolesDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabsecRolesList.setDisabled(true);
	        tabsecRolesPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabsecRolesDetail.setLabel(Labels.getLabel("common.Details"));
	        tabsecRolesList.setDisabled(false);
	        tabsecRolesPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //


    
    
    public SecurityService getSecurityService() {
		return securityService;
	}

	public SecRole getSelectedSecRole() {
		return selectedSecRole;
	}

	public void setSelectedSecRole(SecRole selectedSecRole) {
		this.selectedSecRole = selectedSecRole;
	}

	public SecRole getPostedSecRole() {
		return postedSecRole;
	}

	public void setPostedSecRole(SecRole postedSecRole) {
		this.postedSecRole = postedSecRole;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public SecRolesListCtrl getSecRolesListCtrl() {
		return SecRolesListCtrl;
	}

	public void setSecRolesListCtrl(SecRolesListCtrl secRolesListCtrl) {
		SecRolesListCtrl = secRolesListCtrl;
	}

	public SecRolesDetailCtrl getSecRolesDetailCtrl() {
		return SecRolesDetailCtrl;
	}

	public void setSecRolesDetailCtrl(SecRolesDetailCtrl secRolesDetailCtrl) {
		SecRolesDetailCtrl = secRolesDetailCtrl;
	}

	public SecRolesPrintCtrl getSecRolesPrintCtrl() {
		return SecRolesPrintCtrl;
	}

	public void setSecRolesPrintCtrl(SecRolesPrintCtrl secRolesPrintCtrl) {
		SecRolesPrintCtrl = secRolesPrintCtrl;
	}



}
