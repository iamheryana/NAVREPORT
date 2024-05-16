package solusi.hapis.webui.logistic.penjualan.T23AdjTopCust;


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

import solusi.hapis.backend.navbi.model.T23AdjTopCust;
import solusi.hapis.backend.navbi.service.T23AdjTopCustService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 29-08-2022
 */

public class T23AdjTopCustMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT23AdjTopCustMain; 

    // Tabs
    protected Tabbox tabbox_T23AdjTopCustMain;
    protected Tab tabT23AdjTopCustList;
    protected Tab tabT23AdjTopCustDetail;
//    protected Tab tabT23AdjTopCustPrint;
    protected Tabpanel tabPanelT23AdjTopCustList;
    protected Tabpanel tabPanelT23AdjTopCustDetail;
//    protected Tabpanel tabPanelT23AdjTopCustPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T23AdjTopCust_";
    private ButtonStatusCtrl btnCtrlT23AdjTopCust;
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
    private T23AdjTopCustListCtrl T23AdjTopCustListCtrl;
    private T23AdjTopCustDetailCtrl T23AdjTopCustDetailCtrl;

    // Databinding
    private T23AdjTopCust selectedT23AdjTopCust;
    private T23AdjTopCust postedT23AdjTopCust;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T23AdjTopCustService t23AdjTopCustService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/logistic/penjualan/T23AdjTopCust/T23AdjTopCustDetail.zul";
	private String zulPageList = "/WEB-INF/pages/logistic/penjualan/T23AdjTopCust/T23AdjTopCustList.zul";


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
    public T23AdjTopCustMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT23AdjTopCustMain(Event event) throws Exception {
        windowT23AdjTopCustMain.setContentStyle("padding:0px;");

        btnCtrlT23AdjTopCust = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT23AdjTopCust.setInitInquiryButton();

        tabT23AdjTopCustList.setSelected(true);

        if (tabPanelT23AdjTopCustList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT23AdjTopCustList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT23AdjTopCustList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT23AdjTopCustList.getFirstChild() != null) {

            tabT23AdjTopCustList.setSelected(true);
        	btnCtrlT23AdjTopCust.setInitInquiryButton();
        	getT23AdjTopCustListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT23AdjTopCustList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT23AdjTopCustList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT23AdjTopCustDetail(Event event) throws IOException {
        if (tabPanelT23AdjTopCustDetail.getFirstChild() != null) {
        	tabT23AdjTopCustDetail.setSelected(true);            
        	
        	//getT23AdjTopCustDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT23AdjTopCustDetailCtrl().doRenderMode("View");   
            
            btnCtrlT23AdjTopCust.setInitInquiryButton();

            if (getT23AdjTopCustDetailCtrl().getBinder() != null) {
            	getT23AdjTopCustDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT23AdjTopCustDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT23AdjTopCustDetail, this, "ModuleMainController", zulPageDetail);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT23AdjTopCustDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT23AdjTopCustDetail, null));
        } else if (getT23AdjTopCustDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT23AdjTopCustDetail, null));
        }

        T23AdjTopCust anT23AdjTopCust = new T23AdjTopCust();

        // Set Default Value Di sini ------------------------------------- start

        
        setSelectedT23AdjTopCust(anT23AdjTopCust);
        
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT23AdjTopCust.setInitFormButton();
        
        // select tab Detail
        tabT23AdjTopCustDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT23AdjTopCustDetailCtrl().getBinder() != null) {
        	getT23AdjTopCustDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT23AdjTopCustDetailCtrl().doRenderMode("New");
        //getT23AdjTopCustDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT23AdjTopCust =(T23AdjTopCust) ZksampleBeanUtils.cloneBean(getSelectedT23AdjTopCust());
			
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
        
    	if (tabPanelT23AdjTopCustDetail.getFirstChild() != null) {
        	getT23AdjTopCustDetailCtrl().getBinder().loadAll();
        }
        
        if (getT23AdjTopCustDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT23AdjTopCustDetail, null));
        } else if (getT23AdjTopCustDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT23AdjTopCustDetail, null));
        }

        // set button
        btnCtrlT23AdjTopCust.setInitFormButton();

        // select tab Detail
        tabT23AdjTopCustDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        //getT23AdjTopCustDetailCtrl().doRenderCombo();
        getT23AdjTopCustDetailCtrl().doRenderMode("Edit");
    }
    
	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT23AdjTopCust() != null) {
	        final T23AdjTopCust anT23AdjTopCust = getSelectedT23AdjTopCust();
	        if (anT23AdjTopCust != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anT23AdjTopCust.getCustNo() ;
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
	                        getT23AdjTopCustService().delete(anT23AdjTopCust);
	                        setSelectedT23AdjTopCust(null);
	                        
	                        // refresh the list
	                        getT23AdjTopCustListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT23AdjTopCust.setInitInquiryButton();
	        setSelectedT23AdjTopCust(null);
	        
	        // refresh the list
	        getT23AdjTopCustListCtrl().doFillListbox();
	        tabT23AdjTopCustList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT23AdjTopCustDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT23AdjTopCustDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT23AdjTopCustService().save(getSelectedT23AdjTopCust());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT23AdjTopCustService().update(getSelectedT23AdjTopCust());
	            // refresh the list
	            btnCtrlT23AdjTopCust.setInitInquiryButton();
	            getT23AdjTopCustListCtrl().doFillListbox();
                
                tabT23AdjTopCustList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT23AdjTopCust.setInitInquiryButton();
        
        tabT23AdjTopCustList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT23AdjTopCustListCtrl().doFillListbox();
        }
        
    	if(postedT23AdjTopCust != null){
           	setSelectedT23AdjTopCust(postedT23AdjTopCust);
           	
           	//Databinding LOV

           	
    	}
    	if(getT23AdjTopCustDetailCtrl().binder != null){
    		getT23AdjTopCustDetailCtrl().binder.loadAll();
    	} 
    }
    

    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT23AdjTopCustListCtrl() != null) {
        	getT23AdjTopCustListCtrl().getSearchData();        	
        	tabT23AdjTopCustList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT23AdjTopCustListCtrl() != null) {
        	getT23AdjTopCustListCtrl().clearSearchBox();
        	getT23AdjTopCustListCtrl().getSearchData();        	
        	tabT23AdjTopCustList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT23AdjTopCustDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT23AdjTopCustList.setDisabled(true);

    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT23AdjTopCustDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT23AdjTopCustList.setDisabled(false);

    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT23AdjTopCust(T23AdjTopCust selectedT23AdjTopCust) {
        this.selectedT23AdjTopCust = selectedT23AdjTopCust;
    }

    public T23AdjTopCust getSelectedT23AdjTopCust() {
        return this.selectedT23AdjTopCust;
    }


	public void setT23AdjTopCustService(T23AdjTopCustService t23AdjTopCustService) {
        this.t23AdjTopCustService = t23AdjTopCustService;
    }

    public T23AdjTopCustService getT23AdjTopCustService() {
        return this.t23AdjTopCustService;
    }

    public void setT23AdjTopCustListCtrl(T23AdjTopCustListCtrl T23AdjTopCustListCtrl) {
        this.T23AdjTopCustListCtrl = T23AdjTopCustListCtrl;
    }

    public T23AdjTopCustListCtrl getT23AdjTopCustListCtrl() {
        return this.T23AdjTopCustListCtrl;
    }

    public void setT23AdjTopCustDetailCtrl(T23AdjTopCustDetailCtrl T23AdjTopCustDetailCtrl) {
        this.T23AdjTopCustDetailCtrl = T23AdjTopCustDetailCtrl;
    }

    public T23AdjTopCustDetailCtrl getT23AdjTopCustDetailCtrl() {
        return this.T23AdjTopCustDetailCtrl;
    }

	public void setPostedT23AdjTopCust(T23AdjTopCust postedT23AdjTopCust) {
		this.postedT23AdjTopCust = postedT23AdjTopCust;
	}

	public T23AdjTopCust getPostedT23AdjTopCust() {
		return postedT23AdjTopCust;
	}

}
