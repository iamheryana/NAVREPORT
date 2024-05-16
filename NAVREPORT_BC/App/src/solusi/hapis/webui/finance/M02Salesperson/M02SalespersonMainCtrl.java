package solusi.hapis.webui.finance.M02Salesperson;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

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

import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.backend.navbi.service.M02SalespersonService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 14-04-2018
 */

public class M02SalespersonMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowM02SalespersonMain; 

    // Tabs
    protected Tabbox tabbox_M02SalespersonMain;
    protected Tab tabM02SalespersonList;
    protected Tab tabM02SalespersonDetail;
    protected Tabpanel tabPanelM02SalespersonList;
    protected Tabpanel tabPanelM02SalespersonDetail;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_M02Salesperson_";
    private ButtonStatusCtrl btnCtrlM02Salesperson;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private M02SalespersonListCtrl M02SalespersonListCtrl;
    private M02SalespersonDetailCtrl M02SalespersonDetailCtrl;


    // Databinding
    private M02Salesperson selectedM02Salesperson;
    private M02Salesperson postedM02Salesperson;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private M02SalespersonService M02SalespersonService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/M02Salesperson/M02SalespersonDetail.zul";
	private String zulPageList = "/WEB-INF/pages/finance/M02Salesperson/M02SalespersonList.zul";

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
    public M02SalespersonMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowM02SalespersonMain(Event event) throws Exception {
        windowM02SalespersonMain.setContentStyle("padding:0px;");

        btnCtrlM02Salesperson = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlM02Salesperson.setInitInquiryButton();

        tabM02SalespersonList.setSelected(true);

        if (tabPanelM02SalespersonList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM02SalespersonList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabM02SalespersonList(Event event) throws IOException, InterruptedException {
    	if (tabPanelM02SalespersonList.getFirstChild() != null) {

    		//RESTOREVALUE
    		//doResetToInitValues();
    		
            tabM02SalespersonList.setSelected(true);
        	btnCtrlM02Salesperson.setInitInquiryButton();
        	getM02SalespersonListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelM02SalespersonList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM02SalespersonList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabM02SalespersonDetail(Event event) throws IOException {
        if (tabPanelM02SalespersonDetail.getFirstChild() != null) {
        	tabM02SalespersonDetail.setSelected(true);            
        	
        	//RESTOREVALUE
    		//doResetToInitValues();
    		
        	// Render Inisialisasi posisi awal
            getM02SalespersonDetailCtrl().doRenderMode("View");   
            
            btnCtrlM02Salesperson.setInitInquiryButton();

            if (getM02SalespersonDetailCtrl().getBinder() != null) {
            	getM02SalespersonDetailCtrl().getBinder().loadAll();  
            }
            
            getM02SalespersonDetailCtrl().displayDetail(getM02SalespersonListCtrl().getList_M02SalespersonDetailList());
			
			return;
		}

		if (tabPanelM02SalespersonDetail != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelM02SalespersonDetail,
					this, "ModuleMainController", zulPageDetail);
		}
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getM02SalespersonDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabM02SalespersonDetail, null));
        } else if (getM02SalespersonDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabM02SalespersonDetail, null));
        }

        M02Salesperson anM02Salesperson = new M02Salesperson();

        // Set Default Value Di sini ------------------------------------- start
       
        anM02Salesperson.setTarget(new BigDecimal(0));
        setSelectedM02Salesperson(anM02Salesperson);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlM02Salesperson.setInitFormButton();
        
        // select tab Detail
        tabM02SalespersonDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getM02SalespersonDetailCtrl().getBinder() != null) {
        	getM02SalespersonDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getM02SalespersonDetailCtrl().doRenderMode("New");
        
        getM02SalespersonDetailCtrl().displayDetail(null);
        //getM02SalespersonDetailCtrl().doRenderCombo();
    }
    
//    public void doStoreInitValues() {
//
//        if (getSelectedM02Salesperson() != null) {
//
//            try {
//            	
//                setPostedM02Salesperson((M02Salesperson) ZksampleBeanUtils.cloneBean(getSelectedM02Salesperson()));
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
//			postedM02Salesperson =(M02Salesperson) ZksampleBeanUtils.cloneBean(getSelectedM02Salesperson());
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
        
		if(getSelectedM02Salesperson() != null){
	    	if (tabPanelM02SalespersonDetail.getFirstChild() != null) {
	        	getM02SalespersonDetailCtrl().getBinder().loadAll();
	        }
	        
	        if (getM02SalespersonDetailCtrl() == null) {
	            Events.sendEvent(new Event("onSelect", tabM02SalespersonDetail, null));
	        } else if (getM02SalespersonDetailCtrl().getBinder() == null) {
	            Events.sendEvent(new Event("onSelect", tabM02SalespersonDetail, null));
	        }
	
	        //doStoreInitValues();
	        
	        // set button
	        btnCtrlM02Salesperson.setInitFormButton();
	
	        // select tab Detail
	        tabM02SalespersonDetail.setSelected(true);
	        renderTabAndTitle("Edit");
	        
	        state = 2;
	        
	        // set render layar
	        //getM02SalespersonDetailCtrl().doRenderCombo();
	        getM02SalespersonDetailCtrl().doRenderMode("Edit");
	        
	        getM02SalespersonDetailCtrl().displayDetail(getM02SalespersonListCtrl().getList_M02SalespersonDetailList());
        
  
		}
	}
    
//    public void doResetToInitValues() {
//
//        if (getPostedM02Salesperson() != null) {
//
//            try {
//            	setSelectedM02Salesperson((M02Salesperson) ZksampleBeanUtils.cloneBean(getPostedM02Salesperson()));
//            	
//            	
//                windowM02SalespersonMain.invalidate();
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
    	if (getSelectedM02Salesperson() != null) {
	        final M02Salesperson anM02Salesperson = getSelectedM02Salesperson();
	        if (anM02Salesperson != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anM02Salesperson.getSales() +" - "+anM02Salesperson.getSalesName();
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
	                        getM02SalespersonService().delete(anM02Salesperson);
	                        setSelectedM02Salesperson(null);
	                        
	                        // refresh the list
	                        getM02SalespersonListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlM02Salesperson.setInitInquiryButton();
	        setSelectedM02Salesperson(null);
	        
	        // refresh the list
	        getM02SalespersonListCtrl().doFillListbox();
	        tabM02SalespersonList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getM02SalespersonDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getM02SalespersonDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getM02SalespersonService().insert(getSelectedM02Salesperson());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getM02SalespersonService().update(getSelectedM02Salesperson(), getM02SalespersonDetailCtrl().getList_DeletedM02SalespersonDetailList());
	            // refresh the list
	            btnCtrlM02Salesperson.setInitInquiryButton();
	            getM02SalespersonListCtrl().doFillListbox();
                
                tabM02SalespersonList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlM02Salesperson.setInitInquiryButton();
        
       	//RESTOREVALUE
       	//doResetToInitValues();
       	
        tabM02SalespersonList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getM02SalespersonListCtrl().doFillListbox();
        }
        
    	
    	if(getM02SalespersonDetailCtrl().binder != null){
    		getM02SalespersonDetailCtrl().binder.loadAll();
    	} 
    }
    



    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getM02SalespersonListCtrl() != null) {
        	getM02SalespersonListCtrl().getSearchData();        	
        	tabM02SalespersonList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getM02SalespersonListCtrl() != null) {
        	getM02SalespersonListCtrl().clearSearchBox();
        	getM02SalespersonListCtrl().getSearchData();        	
        	tabM02SalespersonList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabM02SalespersonDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabM02SalespersonList.setDisabled(true);

    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabM02SalespersonDetail.setLabel(Labels.getLabel("common.Details"));
	        tabM02SalespersonList.setDisabled(false);

    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedM02Salesperson(M02Salesperson selectedM02Salesperson) {
        this.selectedM02Salesperson = selectedM02Salesperson;
    }

    public M02Salesperson getSelectedM02Salesperson() {
        return this.selectedM02Salesperson;
    }


	public void setM02SalespersonService(M02SalespersonService M02SalespersonService) {
        this.M02SalespersonService = M02SalespersonService;
    }

    public M02SalespersonService getM02SalespersonService() {
        return this.M02SalespersonService;
    }

    public void setM02SalespersonListCtrl(M02SalespersonListCtrl M02SalespersonListCtrl) {
        this.M02SalespersonListCtrl = M02SalespersonListCtrl;
    }

    public M02SalespersonListCtrl getM02SalespersonListCtrl() {
        return this.M02SalespersonListCtrl;
    }

    public void setM02SalespersonDetailCtrl(M02SalespersonDetailCtrl M02SalespersonDetailCtrl) {
        this.M02SalespersonDetailCtrl = M02SalespersonDetailCtrl;
    }

    public M02SalespersonDetailCtrl getM02SalespersonDetailCtrl() {
        return this.M02SalespersonDetailCtrl;
    }

	public void setPostedM02Salesperson(M02Salesperson postedM02Salesperson) {
		this.postedM02Salesperson = postedM02Salesperson;
	}

	public M02Salesperson getPostedM02Salesperson() {
		return postedM02Salesperson;
	}

}
