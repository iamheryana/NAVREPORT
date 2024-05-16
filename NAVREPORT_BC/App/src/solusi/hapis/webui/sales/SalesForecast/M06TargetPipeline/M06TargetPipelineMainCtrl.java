package solusi.hapis.webui.sales.SalesForecast.M06TargetPipeline;


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

import solusi.hapis.backend.navbi.model.M06TargetPipeline;
import solusi.hapis.backend.navbi.service.M06TargetPipelineService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 09-02-2023
 */

public class M06TargetPipelineMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowM06TargetPipelineMain; 

    // Tabs
    protected Tabbox tabbox_M06TargetPipelineMain;
    protected Tab tabM06TargetPipelineList;
    protected Tab tabM06TargetPipelineDetail;
    protected Tabpanel tabPanelM06TargetPipelineList;
    protected Tabpanel tabPanelM06TargetPipelineDetail;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_M06TargetPipeline_";
    private ButtonStatusCtrl btnCtrlM06TargetPipeline;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;

    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private M06TargetPipelineListCtrl M06TargetPipelineListCtrl;
    private M06TargetPipelineDetailCtrl M06TargetPipelineDetailCtrl;


    // Databinding
    private M06TargetPipeline selectedM06TargetPipeline;
    private M06TargetPipeline postedM06TargetPipeline;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private M06TargetPipelineService M06TargetPipelineService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/sales/SalesForecast/M06TargetPipeline/M06TargetPipelineDetail.zul";
	private String zulPageList = "/WEB-INF/pages/sales/SalesForecast/M06TargetPipeline/M06TargetPipelineList.zul";

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
    public M06TargetPipelineMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowM06TargetPipelineMain(Event event) throws Exception {
        windowM06TargetPipelineMain.setContentStyle("padding:0px;");

        btnCtrlM06TargetPipeline = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlM06TargetPipeline.setInitInquiryButton();

        tabM06TargetPipelineList.setSelected(true);

        if (tabPanelM06TargetPipelineList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM06TargetPipelineList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabM06TargetPipelineList(Event event) throws IOException, InterruptedException {
    	if (tabPanelM06TargetPipelineList.getFirstChild() != null) {

            tabM06TargetPipelineList.setSelected(true);
        	btnCtrlM06TargetPipeline.setInitInquiryButton();
        	getM06TargetPipelineListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelM06TargetPipelineList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM06TargetPipelineList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabM06TargetPipelineDetail(Event event) throws IOException {
        if (tabPanelM06TargetPipelineDetail.getFirstChild() != null) {
        	tabM06TargetPipelineDetail.setSelected(true);            
        	
        	getM06TargetPipelineDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getM06TargetPipelineDetailCtrl().doRenderMode("View");   
            
            btnCtrlM06TargetPipeline.setInitInquiryButton();

            if (getM06TargetPipelineDetailCtrl().getBinder() != null) {
            	getM06TargetPipelineDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelM06TargetPipelineDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM06TargetPipelineDetail, this, "ModuleMainController", zulPageDetail);
        }
    }


    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getM06TargetPipelineDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabM06TargetPipelineDetail, null));
        } else if (getM06TargetPipelineDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabM06TargetPipelineDetail, null));
        }

        M06TargetPipeline anM06TargetPipeline = new M06TargetPipeline();

        // Set Default Value Di sini ------------------------------------- start
        anM06TargetPipeline.setStatus("ACTIVE");
        anM06TargetPipeline.setTarget(new BigDecimal(0));
        anM06TargetPipeline.setTargetPs(new BigDecimal(0));
        
        setSelectedM06TargetPipeline(anM06TargetPipeline);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlM06TargetPipeline.setInitFormButton();
        
        // select tab Detail
        tabM06TargetPipelineDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getM06TargetPipelineDetailCtrl().getBinder() != null) {
        	getM06TargetPipelineDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getM06TargetPipelineDetailCtrl().doRenderMode("New");
        getM06TargetPipelineDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedM06TargetPipeline =(M06TargetPipeline) ZksampleBeanUtils.cloneBean(getSelectedM06TargetPipeline());
			
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
        
    	if (tabPanelM06TargetPipelineDetail.getFirstChild() != null) {
        	getM06TargetPipelineDetailCtrl().getBinder().loadAll();
        }
        
        if (getM06TargetPipelineDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabM06TargetPipelineDetail, null));
        } else if (getM06TargetPipelineDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabM06TargetPipelineDetail, null));
        }

        // set button
        btnCtrlM06TargetPipeline.setInitFormButton();

        // select tab Detail
        tabM06TargetPipelineDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getM06TargetPipelineDetailCtrl().doRenderCombo();
        getM06TargetPipelineDetailCtrl().doRenderMode("Edit");
    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedM06TargetPipeline() != null) {
	        final M06TargetPipeline anM06TargetPipeline = getSelectedM06TargetPipeline();
	        if (anM06TargetPipeline != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anM06TargetPipeline.getTahun() +" - "+anM06TargetPipeline.getJenis() +" - "+ anM06TargetPipeline.getSlsOrCab();
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
	                        getM06TargetPipelineService().delete(anM06TargetPipeline);
	                        setSelectedM06TargetPipeline(null);
	                        
	                        // refresh the list
	                        getM06TargetPipelineListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlM06TargetPipeline.setInitInquiryButton();
	        setSelectedM06TargetPipeline(null);
	        
	        // refresh the list
	        getM06TargetPipelineListCtrl().doFillListbox();
	        tabM06TargetPipelineList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getM06TargetPipelineDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getM06TargetPipelineDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getM06TargetPipelineService().save(getSelectedM06TargetPipeline());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getM06TargetPipelineService().update(getSelectedM06TargetPipeline());
	            // refresh the list
	            btnCtrlM06TargetPipeline.setInitInquiryButton();
	            getM06TargetPipelineListCtrl().doFillListbox();
                
                tabM06TargetPipelineList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlM06TargetPipeline.setInitInquiryButton();
        
        tabM06TargetPipelineList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getM06TargetPipelineListCtrl().doFillListbox();
        }
        
    	if(postedM06TargetPipeline != null){
           	setSelectedM06TargetPipeline(postedM06TargetPipeline);
           	
           	//Databinding LOV

           	
    	}
    	if(getM06TargetPipelineDetailCtrl().binder != null){
    		getM06TargetPipelineDetailCtrl().binder.loadAll();
    	} 
    }
    
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
   
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getM06TargetPipelineListCtrl() != null) {
        	getM06TargetPipelineListCtrl().getSearchData();        	
        	tabM06TargetPipelineList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getM06TargetPipelineListCtrl() != null) {
        	getM06TargetPipelineListCtrl().clearSearchBox();
        	getM06TargetPipelineListCtrl().getSearchData();        	
        	tabM06TargetPipelineList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabM06TargetPipelineDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabM06TargetPipelineList.setDisabled(true);
	        
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabM06TargetPipelineDetail.setLabel(Labels.getLabel("common.Details"));
	        tabM06TargetPipelineList.setDisabled(false);
	    
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedM06TargetPipeline(M06TargetPipeline selectedM06TargetPipeline) {
        this.selectedM06TargetPipeline = selectedM06TargetPipeline;
    }

    public M06TargetPipeline getSelectedM06TargetPipeline() {
        return this.selectedM06TargetPipeline;
    }


	public void setM06TargetPipelineService(M06TargetPipelineService M06TargetPipelineService) {
        this.M06TargetPipelineService = M06TargetPipelineService;
    }

    public M06TargetPipelineService getM06TargetPipelineService() {
        return this.M06TargetPipelineService;
    }

    public void setM06TargetPipelineListCtrl(M06TargetPipelineListCtrl M06TargetPipelineListCtrl) {
        this.M06TargetPipelineListCtrl = M06TargetPipelineListCtrl;
    }

    public M06TargetPipelineListCtrl getM06TargetPipelineListCtrl() {
        return this.M06TargetPipelineListCtrl;
    }

    public void setM06TargetPipelineDetailCtrl(M06TargetPipelineDetailCtrl M06TargetPipelineDetailCtrl) {
        this.M06TargetPipelineDetailCtrl = M06TargetPipelineDetailCtrl;
    }

    public M06TargetPipelineDetailCtrl getM06TargetPipelineDetailCtrl() {
        return this.M06TargetPipelineDetailCtrl;
    }

	public void setPostedM06TargetPipeline(M06TargetPipeline postedM06TargetPipeline) {
		this.postedM06TargetPipeline = postedM06TargetPipeline;
	}

	public M06TargetPipeline getPostedM06TargetPipeline() {
		return postedM06TargetPipeline;
	}

}
