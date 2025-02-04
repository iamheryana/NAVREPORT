package solusi.hapis.webui.general.crossModule.M07UserroleCosting;

import java.io.IOException;
import java.io.Serializable;

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

import solusi.hapis.backend.navbi.model.M07UserroleCostingH;
import solusi.hapis.backend.navbi.service.M07UserroleCostingHService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 16-01-2025
 */

public class M07UserroleCostingMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowM07UserroleCostingMain; 

    // Tabs
    protected Tabbox tabbox_M07UserroleCostingMain;
    protected Tab tabM07UserroleCostingList;
    protected Tab tabM07UserroleCostingDetail;
    protected Tabpanel tabPanelM07UserroleCostingList;
    protected Tabpanel tabPanelM07UserroleCostingDetail;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_M07UserroleCosting_";
    private ButtonStatusCtrl btnCtrlM07UserroleCosting;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private M07UserroleCostingListCtrl M07UserroleCostingListCtrl;
    private M07UserroleCostingDetailCtrl M07UserroleCostingDetailCtrl;


    // Databinding
    private M07UserroleCostingH selectedM07UserroleCostingH;
    private M07UserroleCostingH postedM07UserroleCostingH;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private M07UserroleCostingHService M07UserroleCostingHService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/general/crossModule/M07UserroleCosting/M07UserroleCostingDetail.zul";
	private String zulPageList = "/WEB-INF/pages/general/crossModule/M07UserroleCosting/M07UserroleCostingList.zul";

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
    public M07UserroleCostingMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowM07UserroleCostingMain(Event event) throws Exception {
        windowM07UserroleCostingMain.setContentStyle("padding:0px;");

        btnCtrlM07UserroleCosting = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlM07UserroleCosting.setInitInquiryButton();

        tabM07UserroleCostingList.setSelected(true);

        if (tabPanelM07UserroleCostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM07UserroleCostingList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabM07UserroleCostingList(Event event) throws IOException, InterruptedException {
    	if (tabPanelM07UserroleCostingList.getFirstChild() != null) {

    		//RESTOREVALUE
    		//doResetToInitValues();
    		
            tabM07UserroleCostingList.setSelected(true);
        	btnCtrlM07UserroleCosting.setInitInquiryButton();
        	getM07UserroleCostingListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelM07UserroleCostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM07UserroleCostingList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabM07UserroleCostingDetail(Event event) throws IOException {
        if (tabPanelM07UserroleCostingDetail.getFirstChild() != null) {
        	tabM07UserroleCostingDetail.setSelected(true);            
        	
        	//RESTOREVALUE
    		//doResetToInitValues();
        	getM07UserroleCostingDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getM07UserroleCostingDetailCtrl().doRenderMode("View");   
            
            btnCtrlM07UserroleCosting.setInitInquiryButton();

            if (getM07UserroleCostingDetailCtrl().getBinder() != null) {
            	getM07UserroleCostingDetailCtrl().getBinder().loadAll();  
            }
            
            getM07UserroleCostingDetailCtrl().displayDetail(getM07UserroleCostingListCtrl().getList_M07UserroleCostingDetailList());
			
			return;
		}

		if (tabPanelM07UserroleCostingDetail != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelM07UserroleCostingDetail,
					this, "ModuleMainController", zulPageDetail);
		}
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getM07UserroleCostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabM07UserroleCostingDetail, null));
        } else if (getM07UserroleCostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabM07UserroleCostingDetail, null));
        }

        M07UserroleCostingH anM07UserroleCostingH = new M07UserroleCostingH();

        // Set Default Value Di sini ------------------------------------- start
       
        //anM07UserroleCostingH.setTarget(new BigDecimal(0));
        //anM07UserroleCostingH.setRolename("XXX");
        
        setSelectedM07UserroleCostingH(anM07UserroleCostingH);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlM07UserroleCosting.setInitFormButton();
        
        // select tab Detail
        tabM07UserroleCostingDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getM07UserroleCostingDetailCtrl().getBinder() != null) {
        	getM07UserroleCostingDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getM07UserroleCostingDetailCtrl().doRenderMode("New");
        getM07UserroleCostingDetailCtrl().doRenderCombo();
        
        getM07UserroleCostingDetailCtrl().displayDetail(null);
        //getM07UserroleCostingDetailCtrl().doRenderCombo();
    }
    
//    public void doStoreInitValues() {
//
//        if (getSelectedM07UserroleCosting() != null) {
//
//            try {
//            	
//                setPostedM07UserroleCosting((M07UserroleCosting) ZksampleBeanUtils.cloneBean(getSelectedM07UserroleCosting()));
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
//			postedM07UserroleCosting =(M07UserroleCosting) ZksampleBeanUtils.cloneBean(getSelectedM07UserroleCosting());
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
        
		if(getSelectedM07UserroleCostingH() != null){
	    	if (tabPanelM07UserroleCostingDetail.getFirstChild() != null) {
	        	getM07UserroleCostingDetailCtrl().getBinder().loadAll();
	        }
	        
	        if (getM07UserroleCostingDetailCtrl() == null) {
	            Events.sendEvent(new Event("onSelect", tabM07UserroleCostingDetail, null));
	        } else if (getM07UserroleCostingDetailCtrl().getBinder() == null) {
	            Events.sendEvent(new Event("onSelect", tabM07UserroleCostingDetail, null));
	        }
	
	        //doStoreInitValues();
	        
	        // set button
	        btnCtrlM07UserroleCosting.setInitFormButton();
	
	        // select tab Detail
	        tabM07UserroleCostingDetail.setSelected(true);
	        renderTabAndTitle("Edit");
	        
	        state = 2;
	        
	        // set render layar
	        getM07UserroleCostingDetailCtrl().doRenderCombo();
	        getM07UserroleCostingDetailCtrl().doRenderMode("Edit");
	        
	        getM07UserroleCostingDetailCtrl().displayDetail(getM07UserroleCostingListCtrl().getList_M07UserroleCostingDetailList());
        
  
		}
	}
    
//    public void doResetToInitValues() {
//
//        if (getPostedM07UserroleCosting() != null) {
//
//            try {
//            	setSelectedM07UserroleCosting((M07UserroleCosting) ZksampleBeanUtils.cloneBean(getPostedM07UserroleCosting()));
//            	
//            	
//                windowM07UserroleCostingMain.invalidate();
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
    	if (getSelectedM07UserroleCostingH() != null) {
	        final M07UserroleCostingH anM07UserroleCostingH = getSelectedM07UserroleCostingH();
	        if (anM07UserroleCostingH != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anM07UserroleCostingH.getUsername();
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
	                        getM07UserroleCostingHService().delete(anM07UserroleCostingH);
	                        setSelectedM07UserroleCostingH(null);
	                        
	                        // refresh the list
	                        getM07UserroleCostingListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlM07UserroleCosting.setInitInquiryButton();
	        setSelectedM07UserroleCostingH(null);
	        
	        // refresh the list
	        getM07UserroleCostingListCtrl().doFillListbox();
	        tabM07UserroleCostingList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getM07UserroleCostingDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getM07UserroleCostingDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getM07UserroleCostingHService().insert(getSelectedM07UserroleCostingH());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getM07UserroleCostingHService().update(getSelectedM07UserroleCostingH(), getM07UserroleCostingDetailCtrl().getList_DeletedM07UserroleCostingDetailList());
	            // refresh the list
	            btnCtrlM07UserroleCosting.setInitInquiryButton();
	            getM07UserroleCostingListCtrl().doFillListbox();
                
                tabM07UserroleCostingList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlM07UserroleCosting.setInitInquiryButton();
        
       	//RESTOREVALUE
       	//doResetToInitValues();
       	
        tabM07UserroleCostingList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getM07UserroleCostingListCtrl().doFillListbox();
        }
        
    	
    	if(getM07UserroleCostingDetailCtrl().binder != null){
    		getM07UserroleCostingDetailCtrl().binder.loadAll();
    	} 
    }
    



    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getM07UserroleCostingListCtrl() != null) {
        	getM07UserroleCostingListCtrl().getSearchData();        	
        	tabM07UserroleCostingList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getM07UserroleCostingListCtrl() != null) {
        	getM07UserroleCostingListCtrl().clearSearchBox();
        	getM07UserroleCostingListCtrl().getSearchData();        	
        	tabM07UserroleCostingList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabM07UserroleCostingDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabM07UserroleCostingList.setDisabled(true);

    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabM07UserroleCostingDetail.setLabel(Labels.getLabel("common.Details"));
	        tabM07UserroleCostingList.setDisabled(false);

    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedM07UserroleCostingH(M07UserroleCostingH selectedM07UserroleCostingH) {
        this.selectedM07UserroleCostingH = selectedM07UserroleCostingH;
    }

    public M07UserroleCostingH getSelectedM07UserroleCostingH() {
        return this.selectedM07UserroleCostingH;
    }


	public void setM07UserroleCostingHService(M07UserroleCostingHService M07UserroleCostingHService) {
        this.M07UserroleCostingHService = M07UserroleCostingHService;
    }

    public M07UserroleCostingHService getM07UserroleCostingHService() {
        return this.M07UserroleCostingHService;
    }

    public void setM07UserroleCostingListCtrl(M07UserroleCostingListCtrl M07UserroleCostingListCtrl) {
        this.M07UserroleCostingListCtrl = M07UserroleCostingListCtrl;
    }

    public M07UserroleCostingListCtrl getM07UserroleCostingListCtrl() {
        return this.M07UserroleCostingListCtrl;
    }

    public void setM07UserroleCostingDetailCtrl(M07UserroleCostingDetailCtrl M07UserroleCostingDetailCtrl) {
        this.M07UserroleCostingDetailCtrl = M07UserroleCostingDetailCtrl;
    }

    public M07UserroleCostingDetailCtrl getM07UserroleCostingDetailCtrl() {
        return this.M07UserroleCostingDetailCtrl;
    }

	public void setPostedM07UserroleCostingH(M07UserroleCostingH postedM07UserroleCostingH) {
		this.postedM07UserroleCostingH = postedM07UserroleCostingH;
	}

	public M07UserroleCostingH getPostedM07UserroleCostingH() {
		return postedM07UserroleCostingH;
	}

}
