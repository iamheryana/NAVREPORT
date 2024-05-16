package solusi.hapis.webui.finance.P02VendorNonnav;


import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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

import solusi.hapis.backend.navbi.model.P02VendorNonnav;
import solusi.hapis.backend.navbi.service.P02VendorNonnavService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 30-01-2020
 */

public class P02VendorNonnavMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowP02VendorNonnavMain; 

    // Tabs
    protected Tabbox tabbox_P02VendorNonnavMain;
    protected Tab tabP02VendorNonnavList;
    protected Tab tabP02VendorNonnavDetail;
//    protected Tab tabP02VendorNonnavPrint;
    protected Tabpanel tabPanelP02VendorNonnavList;
    protected Tabpanel tabPanelP02VendorNonnavDetail;
//    protected Tabpanel tabPanelP02VendorNonnavPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_P02VendorNonnav_";
    private ButtonStatusCtrl btnCtrlP02VendorNonnav;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
 //   protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;
    

	

    // Tab-Controllers for getting the binders
    private P02VendorNonnavListCtrl P02VendorNonnavListCtrl;
    private P02VendorNonnavDetailCtrl P02VendorNonnavDetailCtrl;
    private P02VendorNonnavPrintCtrl P02VendorNonnavPrintCtrl;

    // Databinding
    private P02VendorNonnav selectedP02VendorNonnav;
    private P02VendorNonnav postedP02VendorNonnav;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private P02VendorNonnavService p02VendorNonnavService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/P02VendorNonnav/P02VendorNonnavDetail.zul";
	private String zulPageList = "/WEB-INF/pages/finance/P02VendorNonnav/P02VendorNonnavList.zul";
//	private String zulPagePrint = "/WEB-INF/pages/finance/P02VendorNonnav/P02VendorNonnavPrint.zul";

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
    public P02VendorNonnavMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowP02VendorNonnavMain(Event event) throws Exception {
        windowP02VendorNonnavMain.setContentStyle("padding:0px;");

        btnCtrlP02VendorNonnav = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);

        
       
        btnCtrlP02VendorNonnav.setInitInquiryButton();

        tabP02VendorNonnavList.setSelected(true);

        if (tabPanelP02VendorNonnavList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP02VendorNonnavList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabP02VendorNonnavList(Event event) throws IOException, InterruptedException {
    	if (tabPanelP02VendorNonnavList.getFirstChild() != null) {

            tabP02VendorNonnavList.setSelected(true);
        	btnCtrlP02VendorNonnav.setInitInquiryButton();
        	getP02VendorNonnavListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelP02VendorNonnavList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP02VendorNonnavList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabP02VendorNonnavDetail(Event event) throws IOException {
        if (tabPanelP02VendorNonnavDetail.getFirstChild() != null) {
        	tabP02VendorNonnavDetail.setSelected(true);            
        	
        	getP02VendorNonnavDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getP02VendorNonnavDetailCtrl().doRenderMode("View");   
            
            btnCtrlP02VendorNonnav.setInitInquiryButton();

            if (getP02VendorNonnavDetailCtrl().getBinder() != null) {
            	getP02VendorNonnavDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelP02VendorNonnavDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP02VendorNonnavDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

//    public void onSelect$tabP02VendorNonnavPrint(Event event) throws IOException, ParseException {
//    	if (tabPanelP02VendorNonnavPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelP02VendorNonnavPrint, this, "ModuleMainController", zulPagePrint);
//        }
//    	
//    	// Check if the tabpanel is already loaded
//        if (tabPanelP02VendorNonnavPrint.getFirstChild() != null) {
//            tabP02VendorNonnavPrint.setSelected(true);        
//
//            getP02VendorNonnavPrintCtrl().doReadOnlyMode(true);
//            
//            return;
//        }        
//    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    
    
    

    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getP02VendorNonnavDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabP02VendorNonnavDetail, null));
        } else if (getP02VendorNonnavDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabP02VendorNonnavDetail, null));
        }

        P02VendorNonnav anP02VendorNonnav = new P02VendorNonnav();

        // Set Default Value Di sini ------------------------------------- start


        
        
        
        
        setSelectedP02VendorNonnav(anP02VendorNonnav);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlP02VendorNonnav.setInitFormButton();
        
        // select tab Detail
        tabP02VendorNonnavDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getP02VendorNonnavDetailCtrl().getBinder() != null) {
        	getP02VendorNonnavDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getP02VendorNonnavDetailCtrl().doRenderMode("New");
        getP02VendorNonnavDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedP02VendorNonnav =(P02VendorNonnav) ZksampleBeanUtils.cloneBean(getSelectedP02VendorNonnav());
			
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
        
    	if (tabPanelP02VendorNonnavDetail.getFirstChild() != null) {
        	getP02VendorNonnavDetailCtrl().getBinder().loadAll();
        }
        
        if (getP02VendorNonnavDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabP02VendorNonnavDetail, null));
        } else if (getP02VendorNonnavDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabP02VendorNonnavDetail, null));
        }

        // set button
        btnCtrlP02VendorNonnav.setInitFormButton();

        // select tab Detail
        tabP02VendorNonnavDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getP02VendorNonnavDetailCtrl().doRenderCombo();
        getP02VendorNonnavDetailCtrl().doRenderMode("Edit");

    }
    
	public String getDescJenisTrans(String jenisTrans){
		String vResult = "";
		if(jenisTrans.equals("VR-NAV") == true){
			vResult = "Vendor NAV";
		} else if(jenisTrans.equals("VR-NNAV") == true){
			vResult = "Vendor NON-NAV";
		} else if(jenisTrans.equals("CASH") == true){
			vResult = "NON Vendor / Cash";
		}
		
		return vResult;
	}
	
	public String getDescCompany(String company){
		String vResult = "";
		if(company.equals("AUTOJAYA") == true){
			vResult = "Autojaya";
		} else if(company.equals("SOLUSI") == true){
			vResult = "Solusi";
		} 
		
		return vResult;
	}
	
		
	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedP02VendorNonnav() != null) {
	        final P02VendorNonnav anP02VendorNonnav = getSelectedP02VendorNonnav();
	        if (anP02VendorNonnav != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anP02VendorNonnav.getKode();
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
	                        getP02VendorNonnavService().delete(anP02VendorNonnav);
	                        setSelectedP02VendorNonnav(null);
	                        
	                        // refresh the list
	                        getP02VendorNonnavListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlP02VendorNonnav.setInitInquiryButton();
	        setSelectedP02VendorNonnav(null);
	        
	        // refresh the list
	        getP02VendorNonnavListCtrl().doFillListbox();
	        tabP02VendorNonnavList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getP02VendorNonnavDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getP02VendorNonnavDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getP02VendorNonnavService().save(getSelectedP02VendorNonnav());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getP02VendorNonnavService().update(getSelectedP02VendorNonnav());
	            // refresh the list
	            btnCtrlP02VendorNonnav.setInitInquiryButton();
	            getP02VendorNonnavListCtrl().doFillListbox();
                
                tabP02VendorNonnavList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlP02VendorNonnav.setInitInquiryButton();
        
        tabP02VendorNonnavList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getP02VendorNonnavListCtrl().doFillListbox();
        }
        
    	if(postedP02VendorNonnav != null){
           	setSelectedP02VendorNonnav(postedP02VendorNonnav);
           	
           	//Databinding LOV

           	
    	}
    	if(getP02VendorNonnavDetailCtrl().binder != null){
    		getP02VendorNonnavDetailCtrl().binder.loadAll();
    	} 
    }
    
//    // Saat Button Listing di klik
//    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
//    	
//    	if (getP02VendorNonnavPrintCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabP02VendorNonnavPrint, null));
//        }
//
//        if (tabPanelP02VendorNonnavPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelP02VendorNonnavPrint, this, "ModuleMainController", zulPagePrint);
//        }
//        
//        getP02VendorNonnavPrintCtrl().doReadOnlyMode(false);
//        
//        btnCtrlP02VendorNonnav.setInitPrintButton();
//        tabP02VendorNonnavPrint.setSelected(true);
//
//    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
//    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listP02VendorNonnav.jasper";
    	
//    	new JReportGeneratorWindow(getP02VendorNonnavPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getP02VendorNonnavListCtrl() != null) {
        	getP02VendorNonnavListCtrl().getSearchData();        	
        	tabP02VendorNonnavList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getP02VendorNonnavListCtrl() != null) {
        	getP02VendorNonnavListCtrl().clearSearchBox();
        	getP02VendorNonnavListCtrl().getSearchData();        	
        	tabP02VendorNonnavList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabP02VendorNonnavDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabP02VendorNonnavList.setDisabled(true);
//	        tabP02VendorNonnavPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabP02VendorNonnavDetail.setLabel(Labels.getLabel("common.Details"));
	        tabP02VendorNonnavList.setDisabled(false);
//	        tabP02VendorNonnavPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedP02VendorNonnav(P02VendorNonnav selectedP02VendorNonnav) {
        this.selectedP02VendorNonnav = selectedP02VendorNonnav;
    }

    public P02VendorNonnav getSelectedP02VendorNonnav() {
        return this.selectedP02VendorNonnav;
    }


	public void setP02VendorNonnavService(P02VendorNonnavService p02VendorNonnavService) {
        this.p02VendorNonnavService = p02VendorNonnavService;
    }

    public P02VendorNonnavService getP02VendorNonnavService() {
        return this.p02VendorNonnavService;
    }

    public void setP02VendorNonnavListCtrl(P02VendorNonnavListCtrl P02VendorNonnavListCtrl) {
        this.P02VendorNonnavListCtrl = P02VendorNonnavListCtrl;
    }

    public P02VendorNonnavListCtrl getP02VendorNonnavListCtrl() {
        return this.P02VendorNonnavListCtrl;
    }

    public void setP02VendorNonnavDetailCtrl(P02VendorNonnavDetailCtrl P02VendorNonnavDetailCtrl) {
        this.P02VendorNonnavDetailCtrl = P02VendorNonnavDetailCtrl;
    }

    public P02VendorNonnavDetailCtrl getP02VendorNonnavDetailCtrl() {
        return this.P02VendorNonnavDetailCtrl;
    }

	public void setP02VendorNonnavPrintCtrl(P02VendorNonnavPrintCtrl P02VendorNonnavPrintCtrl) {
		this.P02VendorNonnavPrintCtrl = P02VendorNonnavPrintCtrl;
	}

	public P02VendorNonnavPrintCtrl getP02VendorNonnavPrintCtrl() {
		return this.P02VendorNonnavPrintCtrl;
	}

	public void setPostedP02VendorNonnav(P02VendorNonnav postedP02VendorNonnav) {
		this.postedP02VendorNonnav = postedP02VendorNonnav;
	}

	public P02VendorNonnav getPostedP02VendorNonnav() {
		return postedP02VendorNonnav;
	}

}
