package solusi.hapis.webui.general.T15SatindoAdj;


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

import solusi.hapis.backend.navbi.model.T15SatindoAdj;
import solusi.hapis.backend.navbi.service.T15SatindoAdjService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 07-04-2021
 */

public class T15SatindoAdjMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT15SatindoAdjMain; 

    // Tabs
    protected Tabbox tabbox_T15SatindoAdjMain;
    protected Tab tabT15SatindoAdjList;
    protected Tab tabT15SatindoAdjDetail;
    
    protected Tabpanel tabPanelT15SatindoAdjList;
    protected Tabpanel tabPanelT15SatindoAdjDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T15SatindoAdj_";
    private ButtonStatusCtrl btnCtrlT15SatindoAdj;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;

    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T15SatindoAdjListCtrl T15SatindoAdjListCtrl;
    private T15SatindoAdjDetailCtrl T15SatindoAdjDetailCtrl;

    // Databinding
    private T15SatindoAdj selectedT15SatindoAdj;
    private T15SatindoAdj postedT15SatindoAdj;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T15SatindoAdjService t15SatindoAdjService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/general/T15SatindoAdj/T15SatindoAdjDetail.zul";
	private String zulPageList = "/WEB-INF/pages/general/T15SatindoAdj/T15SatindoAdjList.zul";



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
    public T15SatindoAdjMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT15SatindoAdjMain(Event event) throws Exception {
        windowT15SatindoAdjMain.setContentStyle("padding:0px;");

        btnCtrlT15SatindoAdj = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT15SatindoAdj.setInitInquiryButton();

        tabT15SatindoAdjList.setSelected(true);

        if (tabPanelT15SatindoAdjList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT15SatindoAdjList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT15SatindoAdjList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT15SatindoAdjList.getFirstChild() != null) {

            tabT15SatindoAdjList.setSelected(true);
        	btnCtrlT15SatindoAdj.setInitInquiryButton();
        	getT15SatindoAdjListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT15SatindoAdjList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT15SatindoAdjList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT15SatindoAdjDetail(Event event) throws IOException {
        if (tabPanelT15SatindoAdjDetail.getFirstChild() != null) {
        	tabT15SatindoAdjDetail.setSelected(true);            
        	
        	// Render Inisialisasi posisi awal
            getT15SatindoAdjDetailCtrl().doRenderMode("View");   
            
            btnCtrlT15SatindoAdj.setInitInquiryButton();

            if (getT15SatindoAdjDetailCtrl().getBinder() != null) {
            	getT15SatindoAdjDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT15SatindoAdjDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT15SatindoAdjDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT15SatindoAdjDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT15SatindoAdjDetail, null));
        } else if (getT15SatindoAdjDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT15SatindoAdjDetail, null));
        }

        T15SatindoAdj anT15SatindoAdj = new T15SatindoAdj();

        // Set Default Value Di sini ------------------------------------- start

        
        setSelectedT15SatindoAdj(anT15SatindoAdj);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT15SatindoAdj.setInitFormButton();
        
        // select tab Detail
        tabT15SatindoAdjDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT15SatindoAdjDetailCtrl().getBinder() != null) {
        	getT15SatindoAdjDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT15SatindoAdjDetailCtrl().doRenderMode("New");

    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT15SatindoAdj =(T15SatindoAdj) ZksampleBeanUtils.cloneBean(getSelectedT15SatindoAdj());
			
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
        
    	if (tabPanelT15SatindoAdjDetail.getFirstChild() != null) {
        	getT15SatindoAdjDetailCtrl().getBinder().loadAll();
        }
        
        if (getT15SatindoAdjDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT15SatindoAdjDetail, null));
        } else if (getT15SatindoAdjDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT15SatindoAdjDetail, null));
        }

        // set button
        btnCtrlT15SatindoAdj.setInitFormButton();

        // select tab Detail
        tabT15SatindoAdjDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar

        getT15SatindoAdjDetailCtrl().doRenderMode("Edit");
    }
    

	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT15SatindoAdj() != null) {
	        final T15SatindoAdj anT15SatindoAdj = getSelectedT15SatindoAdj();
	        if (anT15SatindoAdj != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anT15SatindoAdj.getNoInvoice()+" - "+ CommonUtils.convertDateToString(anT15SatindoAdj.getOrderDate());
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
	                        getT15SatindoAdjService().delete(anT15SatindoAdj);
	                        setSelectedT15SatindoAdj(null);
	                        
	                        // refresh the list
	                        getT15SatindoAdjListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT15SatindoAdj.setInitInquiryButton();
	        setSelectedT15SatindoAdj(null);
	        
	        // refresh the list
	        getT15SatindoAdjListCtrl().doFillListbox();
	        tabT15SatindoAdjList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT15SatindoAdjDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT15SatindoAdjDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT15SatindoAdjService().save(getSelectedT15SatindoAdj());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT15SatindoAdjService().update(getSelectedT15SatindoAdj());
	            // refresh the list
	            btnCtrlT15SatindoAdj.setInitInquiryButton();
	            getT15SatindoAdjListCtrl().doFillListbox();
                
                tabT15SatindoAdjList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT15SatindoAdj.setInitInquiryButton();
        
        tabT15SatindoAdjList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT15SatindoAdjListCtrl().doFillListbox();
        }
        
    	if(postedT15SatindoAdj != null){
           	setSelectedT15SatindoAdj(postedT15SatindoAdj);
           	
           	//Databinding LOV

           	
    	}
    	if(getT15SatindoAdjDetailCtrl().binder != null){
    		getT15SatindoAdjDetailCtrl().binder.loadAll();
    	} 
    }
    
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT15SatindoAdjListCtrl() != null) {
        	getT15SatindoAdjListCtrl().getSearchData();        	
        	tabT15SatindoAdjList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT15SatindoAdjListCtrl() != null) {
        	getT15SatindoAdjListCtrl().clearSearchBox();
        	getT15SatindoAdjListCtrl().getSearchData();        	
        	tabT15SatindoAdjList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT15SatindoAdjDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT15SatindoAdjList.setDisabled(true);
//	        tabT15SatindoAdjPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT15SatindoAdjDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT15SatindoAdjList.setDisabled(false);
//	        tabT15SatindoAdjPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT15SatindoAdj(T15SatindoAdj selectedT15SatindoAdj) {
        this.selectedT15SatindoAdj = selectedT15SatindoAdj;
    }

    public T15SatindoAdj getSelectedT15SatindoAdj() {
        return this.selectedT15SatindoAdj;
    }


	public void setT15SatindoAdjService(T15SatindoAdjService t15SatindoAdjService) {
        this.t15SatindoAdjService = t15SatindoAdjService;
    }

    public T15SatindoAdjService getT15SatindoAdjService() {
        return this.t15SatindoAdjService;
    }

    public void setT15SatindoAdjListCtrl(T15SatindoAdjListCtrl T15SatindoAdjListCtrl) {
        this.T15SatindoAdjListCtrl = T15SatindoAdjListCtrl;
    }

    public T15SatindoAdjListCtrl getT15SatindoAdjListCtrl() {
        return this.T15SatindoAdjListCtrl;
    }

    public void setT15SatindoAdjDetailCtrl(T15SatindoAdjDetailCtrl T15SatindoAdjDetailCtrl) {
        this.T15SatindoAdjDetailCtrl = T15SatindoAdjDetailCtrl;
    }

    public T15SatindoAdjDetailCtrl getT15SatindoAdjDetailCtrl() {
        return this.T15SatindoAdjDetailCtrl;
    }

	public void setPostedT15SatindoAdj(T15SatindoAdj postedT15SatindoAdj) {
		this.postedT15SatindoAdj = postedT15SatindoAdj;
	}

	public T15SatindoAdj getPostedT15SatindoAdj() {
		return postedT15SatindoAdj;
	}

}
