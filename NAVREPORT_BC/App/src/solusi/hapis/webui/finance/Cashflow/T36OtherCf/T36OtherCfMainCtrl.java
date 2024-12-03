package solusi.hapis.webui.finance.Cashflow.T36OtherCf;


import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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

import solusi.hapis.backend.navbi.model.T36OtherCf;
import solusi.hapis.backend.navbi.service.T36OtherCfService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 28-11-2024
 */

public class T36OtherCfMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT36OtherCfMain; 

    // Tabs
    protected Tabbox tabbox_T36OtherCfMain;
    protected Tab tabT36OtherCfList;
    protected Tab tabT36OtherCfDetail;
    protected Tabpanel tabPanelT36OtherCfList;
    protected Tabpanel tabPanelT36OtherCfDetail;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T36OtherCf_";
    private ButtonStatusCtrl btnCtrlT36OtherCf;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;

    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T36OtherCfListCtrl T36OtherCfListCtrl;
    private T36OtherCfDetailCtrl T36OtherCfDetailCtrl;


    // Databinding
    private T36OtherCf selectedT36OtherCf;
    private T36OtherCf postedT36OtherCf;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T36OtherCfService T36OtherCfService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/Cashflow/T36OtherCf/T36OtherCfDetail.zul";
	private String zulPageList = "/WEB-INF/pages/finance/Cashflow/T36OtherCf/T36OtherCfList.zul";

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
    public T36OtherCfMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT36OtherCfMain(Event event) throws Exception {
        windowT36OtherCfMain.setContentStyle("padding:0px;");

        btnCtrlT36OtherCf = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT36OtherCf.setInitInquiryButton();

        tabT36OtherCfList.setSelected(true);

        if (tabPanelT36OtherCfList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT36OtherCfList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT36OtherCfList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT36OtherCfList.getFirstChild() != null) {

            tabT36OtherCfList.setSelected(true);
        	btnCtrlT36OtherCf.setInitInquiryButton();
        	getT36OtherCfListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT36OtherCfList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT36OtherCfList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT36OtherCfDetail(Event event) throws IOException {
        if (tabPanelT36OtherCfDetail.getFirstChild() != null) {
        	tabT36OtherCfDetail.setSelected(true);            
        	
        	getT36OtherCfDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT36OtherCfDetailCtrl().doRenderMode("View");   
            
            btnCtrlT36OtherCf.setInitInquiryButton();

            if (getT36OtherCfDetailCtrl().getBinder() != null) {
            	getT36OtherCfDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT36OtherCfDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT36OtherCfDetail, this, "ModuleMainController", zulPageDetail);
        }
    }


    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT36OtherCfDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT36OtherCfDetail, null));
        } else if (getT36OtherCfDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT36OtherCfDetail, null));
        }

        T36OtherCf anT36OtherCf = new T36OtherCf();

        // Set Default Value Di sini ------------------------------------- start
        anT36OtherCf.setCompany("AUTOJAYA");
        anT36OtherCf.setReg("OTH");
        anT36OtherCf.setTipe("NF");
        anT36OtherCf.setAmount(new BigDecimal(0));
        anT36OtherCf.setBasis("X");
        
        setSelectedT36OtherCf(anT36OtherCf);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT36OtherCf.setInitFormButton();
        
        // select tab Detail
        tabT36OtherCfDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT36OtherCfDetailCtrl().getBinder() != null) {
        	getT36OtherCfDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT36OtherCfDetailCtrl().doRenderMode("New");
        getT36OtherCfDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT36OtherCf =(T36OtherCf) ZksampleBeanUtils.cloneBean(getSelectedT36OtherCf());
			
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
        
    	if (tabPanelT36OtherCfDetail.getFirstChild() != null) {
        	getT36OtherCfDetailCtrl().getBinder().loadAll();
        }
        
        if (getT36OtherCfDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT36OtherCfDetail, null));
        } else if (getT36OtherCfDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT36OtherCfDetail, null));
        }

        // set button
        btnCtrlT36OtherCf.setInitFormButton();

        // select tab Detail
        tabT36OtherCfDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getT36OtherCfDetailCtrl().doRenderCombo();
        getT36OtherCfDetailCtrl().doRenderMode("Edit");
    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT36OtherCf() != null) {
	        final T36OtherCf anT36OtherCf = getSelectedT36OtherCf();
	        if (anT36OtherCf != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anT36OtherCf.getKeterangan();
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
	                        getT36OtherCfService().delete(anT36OtherCf);
	                        setSelectedT36OtherCf(null);
	                        
	                        // refresh the list
	                        getT36OtherCfListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT36OtherCf.setInitInquiryButton();
	        setSelectedT36OtherCf(null);
	        
	        // refresh the list
	        getT36OtherCfListCtrl().doFillListbox();
	        tabT36OtherCfList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT36OtherCfDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT36OtherCfDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT36OtherCfService().save(getSelectedT36OtherCf());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT36OtherCfService().update(getSelectedT36OtherCf());
	            // refresh the list
	            btnCtrlT36OtherCf.setInitInquiryButton();
	            getT36OtherCfListCtrl().doFillListbox();
                
                tabT36OtherCfList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT36OtherCf.setInitInquiryButton();
        
        tabT36OtherCfList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT36OtherCfListCtrl().doFillListbox();
        }
        
    	if(postedT36OtherCf != null){
           	setSelectedT36OtherCf(postedT36OtherCf);
           	
           	//Databinding LOV

           	
    	}
    	if(getT36OtherCfDetailCtrl().binder != null){
    		getT36OtherCfDetailCtrl().binder.loadAll();
    	} 
    }
    
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
   
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT36OtherCfListCtrl() != null) {
        	getT36OtherCfListCtrl().getSearchData();        	
        	tabT36OtherCfList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT36OtherCfListCtrl() != null) {
        	getT36OtherCfListCtrl().clearSearchBox();
        	getT36OtherCfListCtrl().getSearchData();        	
        	tabT36OtherCfList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT36OtherCfDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT36OtherCfList.setDisabled(true);
	        
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT36OtherCfDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT36OtherCfList.setDisabled(false);
	    
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT36OtherCf(T36OtherCf selectedT36OtherCf) {
        this.selectedT36OtherCf = selectedT36OtherCf;
    }

    public T36OtherCf getSelectedT36OtherCf() {
        return this.selectedT36OtherCf;
    }


	public void setT36OtherCfService(T36OtherCfService T36OtherCfService) {
        this.T36OtherCfService = T36OtherCfService;
    }

    public T36OtherCfService getT36OtherCfService() {
        return this.T36OtherCfService;
    }

    public void setT36OtherCfListCtrl(T36OtherCfListCtrl T36OtherCfListCtrl) {
        this.T36OtherCfListCtrl = T36OtherCfListCtrl;
    }

    public T36OtherCfListCtrl getT36OtherCfListCtrl() {
        return this.T36OtherCfListCtrl;
    }

    public void setT36OtherCfDetailCtrl(T36OtherCfDetailCtrl T36OtherCfDetailCtrl) {
        this.T36OtherCfDetailCtrl = T36OtherCfDetailCtrl;
    }

    public T36OtherCfDetailCtrl getT36OtherCfDetailCtrl() {
        return this.T36OtherCfDetailCtrl;
    }

	public void setPostedT36OtherCf(T36OtherCf postedT36OtherCf) {
		this.postedT36OtherCf = postedT36OtherCf;
	}

	public T36OtherCf getPostedT36OtherCf() {
		return postedT36OtherCf;
	}

}
