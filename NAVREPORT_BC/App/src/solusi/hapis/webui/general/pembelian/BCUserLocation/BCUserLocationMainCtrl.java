package solusi.hapis.webui.general.pembelian.BCUserLocation;


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

import solusi.hapis.backend.navbi.model.BCUserLocation;
import solusi.hapis.backend.navbi.service.BCUserLocationService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 07-09-2023
 */

public class BCUserLocationMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowBCUserLocationMain; 

    // Tabs
    protected Tabbox tabbox_BCUserLocationMain;
    protected Tab tabBCUserLocationList;
    protected Tab tabBCUserLocationDetail;
    
    protected Tabpanel tabPanelBCUserLocationList;
    protected Tabpanel tabPanelBCUserLocationDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_BCUserLocation_";
    private ButtonStatusCtrl btnCtrlBCUserLocation;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;

    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private BCUserLocationListCtrl BCUserLocationListCtrl;
    private BCUserLocationDetailCtrl BCUserLocationDetailCtrl;

    // Databinding
    private BCUserLocation selectedBCUserLocation;
    private BCUserLocation postedBCUserLocation;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private BCUserLocationService BCUserLocationService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/general/pembelian/BCUserLocation/BCUserLocationDetail.zul";
	private String zulPageList = "/WEB-INF/pages/general/pembelian/BCUserLocation/BCUserLocationList.zul";



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
    public BCUserLocationMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowBCUserLocationMain(Event event) throws Exception {
        windowBCUserLocationMain.setContentStyle("padding:0px;");

        btnCtrlBCUserLocation = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlBCUserLocation.setInitInquiryButton();

        tabBCUserLocationList.setSelected(true);

        if (tabPanelBCUserLocationList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelBCUserLocationList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabBCUserLocationList(Event event) throws IOException, InterruptedException {
    	if (tabPanelBCUserLocationList.getFirstChild() != null) {

            tabBCUserLocationList.setSelected(true);
        	btnCtrlBCUserLocation.setInitInquiryButton();
        	getBCUserLocationListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelBCUserLocationList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelBCUserLocationList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabBCUserLocationDetail(Event event) throws IOException {
        if (tabPanelBCUserLocationDetail.getFirstChild() != null) {
        	tabBCUserLocationDetail.setSelected(true);            
        	
        	getBCUserLocationDetailCtrl().doRenderCombo();
        	
        	// Render Inisialisasi posisi awal
            getBCUserLocationDetailCtrl().doRenderMode("View");   
            
            
            btnCtrlBCUserLocation.setInitInquiryButton();

            if (getBCUserLocationDetailCtrl().getBinder() != null) {
            	getBCUserLocationDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelBCUserLocationDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelBCUserLocationDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getBCUserLocationDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabBCUserLocationDetail, null));
        } else if (getBCUserLocationDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabBCUserLocationDetail, null));
        }

        BCUserLocation anBCUserLocation = new BCUserLocation();

        // Set Default Value Di sini ------------------------------------- start
       
        
        
        setSelectedBCUserLocation(anBCUserLocation);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlBCUserLocation.setInitFormButton();
        
        // select tab Detail
        tabBCUserLocationDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getBCUserLocationDetailCtrl().getBinder() != null) {
        	getBCUserLocationDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getBCUserLocationDetailCtrl().doRenderMode("New");
        getBCUserLocationDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedBCUserLocation =(BCUserLocation) ZksampleBeanUtils.cloneBean(getSelectedBCUserLocation());
			
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
        
    	if (tabPanelBCUserLocationDetail.getFirstChild() != null) {
        	getBCUserLocationDetailCtrl().getBinder().loadAll();
        }
        
        if (getBCUserLocationDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabBCUserLocationDetail, null));
        } else if (getBCUserLocationDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabBCUserLocationDetail, null));
        }

        // set button
        btnCtrlBCUserLocation.setInitFormButton();

        // select tab Detail
        tabBCUserLocationDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getBCUserLocationDetailCtrl().doRenderCombo();
        getBCUserLocationDetailCtrl().doRenderMode("Edit");
    }
    

	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedBCUserLocation() != null) {
	        final BCUserLocation anBCUserLocation = getSelectedBCUserLocation();
	        if (anBCUserLocation != null) {
	
	            // Show a confirm box
	        	String deleteRecord =anBCUserLocation.getUserId();
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
	                        getBCUserLocationService().delete(anBCUserLocation);
	                        setSelectedBCUserLocation(null);
	                        
	                        // refresh the list
	                        getBCUserLocationListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlBCUserLocation.setInitInquiryButton();
	        setSelectedBCUserLocation(null);
	        
	        // refresh the list
	        getBCUserLocationListCtrl().doFillListbox();
	        tabBCUserLocationList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getBCUserLocationDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getBCUserLocationDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getBCUserLocationService().save(getSelectedBCUserLocation());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getBCUserLocationService().update(getSelectedBCUserLocation());
	            // refresh the list
	            btnCtrlBCUserLocation.setInitInquiryButton();
	            getBCUserLocationListCtrl().doFillListbox();
                
                tabBCUserLocationList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlBCUserLocation.setInitInquiryButton();
        
        tabBCUserLocationList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getBCUserLocationListCtrl().doFillListbox();
        }
        
    	if(postedBCUserLocation != null){
           	setSelectedBCUserLocation(postedBCUserLocation);
           	
           	//Databinding LOV

           	
    	}
    	if(getBCUserLocationDetailCtrl().binder != null){
    		getBCUserLocationDetailCtrl().binder.loadAll();
    	} 
    }
    
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getBCUserLocationListCtrl() != null) {
        	getBCUserLocationListCtrl().getSearchData();        	
        	tabBCUserLocationList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getBCUserLocationListCtrl() != null) {
        	getBCUserLocationListCtrl().clearSearchBox();
        	getBCUserLocationListCtrl().getSearchData();        	
        	tabBCUserLocationList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabBCUserLocationDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabBCUserLocationList.setDisabled(true);
//	        tabBCUserLocationPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabBCUserLocationDetail.setLabel(Labels.getLabel("common.Details"));
	        tabBCUserLocationList.setDisabled(false);
//	        tabBCUserLocationPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedBCUserLocation(BCUserLocation selectedBCUserLocation) {
        this.selectedBCUserLocation = selectedBCUserLocation;
    }

    public BCUserLocation getSelectedBCUserLocation() {
        return this.selectedBCUserLocation;
    }


	public void setBCUserLocationService(BCUserLocationService BCUserLocationService) {
        this.BCUserLocationService = BCUserLocationService;
    }

    public BCUserLocationService getBCUserLocationService() {
        return this.BCUserLocationService;
    }

    public void setBCUserLocationListCtrl(BCUserLocationListCtrl BCUserLocationListCtrl) {
        this.BCUserLocationListCtrl = BCUserLocationListCtrl;
    }

    public BCUserLocationListCtrl getBCUserLocationListCtrl() {
        return this.BCUserLocationListCtrl;
    }

    public void setBCUserLocationDetailCtrl(BCUserLocationDetailCtrl BCUserLocationDetailCtrl) {
        this.BCUserLocationDetailCtrl = BCUserLocationDetailCtrl;
    }

    public BCUserLocationDetailCtrl getBCUserLocationDetailCtrl() {
        return this.BCUserLocationDetailCtrl;
    }

	public void setPostedBCUserLocation(BCUserLocation postedBCUserLocation) {
		this.postedBCUserLocation = postedBCUserLocation;
	}

	public BCUserLocation getPostedBCUserLocation() {
		return postedBCUserLocation;
	}

}
