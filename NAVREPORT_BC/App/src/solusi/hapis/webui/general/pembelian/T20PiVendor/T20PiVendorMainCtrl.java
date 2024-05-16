package solusi.hapis.webui.general.pembelian.T20PiVendor;


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

import solusi.hapis.backend.navbi.model.T20PiVendor;
import solusi.hapis.backend.navbi.service.T20PiVendorService;
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
 * Date: 02-07-2021
 */

public class T20PiVendorMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT20PiVendorMain; 

    // Tabs
    protected Tabbox tabbox_T20PiVendorMain;
    protected Tab tabT20PiVendorList;
    protected Tab tabT20PiVendorDetail;
    
    protected Tabpanel tabPanelT20PiVendorList;
    protected Tabpanel tabPanelT20PiVendorDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T20PiVendor_";
    private ButtonStatusCtrl btnCtrlT20PiVendor;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;

    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T20PiVendorListCtrl T20PiVendorListCtrl;
    private T20PiVendorDetailCtrl T20PiVendorDetailCtrl;

    // Databinding
    private T20PiVendor selectedT20PiVendor;
    private T20PiVendor postedT20PiVendor;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T20PiVendorService t20PiVendorService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/general/pembelian/T20PiVendor/T20PiVendorDetail.zul";
	private String zulPageList = "/WEB-INF/pages/general/pembelian/T20PiVendor/T20PiVendorList.zul";



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
    public T20PiVendorMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT20PiVendorMain(Event event) throws Exception {
        windowT20PiVendorMain.setContentStyle("padding:0px;");

        btnCtrlT20PiVendor = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT20PiVendor.setInitInquiryButton();

        tabT20PiVendorList.setSelected(true);

        if (tabPanelT20PiVendorList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT20PiVendorList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT20PiVendorList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT20PiVendorList.getFirstChild() != null) {

            tabT20PiVendorList.setSelected(true);
        	btnCtrlT20PiVendor.setInitInquiryButton();
        	getT20PiVendorListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT20PiVendorList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT20PiVendorList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT20PiVendorDetail(Event event) throws IOException {
        if (tabPanelT20PiVendorDetail.getFirstChild() != null) {
        	tabT20PiVendorDetail.setSelected(true);            
        	
        	getT20PiVendorDetailCtrl().doRenderCombo();
        	
        	// Render Inisialisasi posisi awal
            getT20PiVendorDetailCtrl().doRenderMode("View");   
            
            
            btnCtrlT20PiVendor.setInitInquiryButton();

            if (getT20PiVendorDetailCtrl().getBinder() != null) {
            	getT20PiVendorDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT20PiVendorDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT20PiVendorDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT20PiVendorDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT20PiVendorDetail, null));
        } else if (getT20PiVendorDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT20PiVendorDetail, null));
        }

        T20PiVendor anT20PiVendor = new T20PiVendor();

        // Set Default Value Di sini ------------------------------------- start
        anT20PiVendor.setBerlaku("N");
        
        setSelectedT20PiVendor(anT20PiVendor);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT20PiVendor.setInitFormButton();
        
        // select tab Detail
        tabT20PiVendorDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT20PiVendorDetailCtrl().getBinder() != null) {
        	getT20PiVendorDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT20PiVendorDetailCtrl().doRenderMode("New");
        getT20PiVendorDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT20PiVendor =(T20PiVendor) ZksampleBeanUtils.cloneBean(getSelectedT20PiVendor());
			
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
        
    	if (tabPanelT20PiVendorDetail.getFirstChild() != null) {
        	getT20PiVendorDetailCtrl().getBinder().loadAll();
        }
        
        if (getT20PiVendorDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT20PiVendorDetail, null));
        } else if (getT20PiVendorDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT20PiVendorDetail, null));
        }

        // set button
        btnCtrlT20PiVendor.setInitFormButton();

        // select tab Detail
        tabT20PiVendorDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getT20PiVendorDetailCtrl().doRenderCombo();
        getT20PiVendorDetailCtrl().doRenderMode("Edit");
    }
    

	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT20PiVendor() != null) {
	        final T20PiVendor anT20PiVendor = getSelectedT20PiVendor();
	        if (anT20PiVendor != null) {
	
	            // Show a confirm box
	        	String deleteRecord = CommonUtils.convertDateToString(anT20PiVendor.getTglMulai()) +" - "+ anT20PiVendor.getPrincipalCode() +" - "+ anT20PiVendor.getVendorCode();
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
	                        getT20PiVendorService().delete(anT20PiVendor);
	                        setSelectedT20PiVendor(null);
	                        
	                        // refresh the list
	                        getT20PiVendorListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT20PiVendor.setInitInquiryButton();
	        setSelectedT20PiVendor(null);
	        
	        // refresh the list
	        getT20PiVendorListCtrl().doFillListbox();
	        tabT20PiVendorList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT20PiVendorDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT20PiVendorDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT20PiVendorService().save(getSelectedT20PiVendor());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT20PiVendorService().update(getSelectedT20PiVendor());
	            // refresh the list
	            btnCtrlT20PiVendor.setInitInquiryButton();
	            getT20PiVendorListCtrl().doFillListbox();
                
                tabT20PiVendorList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT20PiVendor.setInitInquiryButton();
        
        tabT20PiVendorList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT20PiVendorListCtrl().doFillListbox();
        }
        
    	if(postedT20PiVendor != null){
           	setSelectedT20PiVendor(postedT20PiVendor);
           	
           	//Databinding LOV

           	
    	}
    	if(getT20PiVendorDetailCtrl().binder != null){
    		getT20PiVendorDetailCtrl().binder.loadAll();
    	} 
    }
    
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT20PiVendorListCtrl() != null) {
        	getT20PiVendorListCtrl().getSearchData();        	
        	tabT20PiVendorList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT20PiVendorListCtrl() != null) {
        	getT20PiVendorListCtrl().clearSearchBox();
        	getT20PiVendorListCtrl().getSearchData();        	
        	tabT20PiVendorList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT20PiVendorDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT20PiVendorList.setDisabled(true);
//	        tabT20PiVendorPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT20PiVendorDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT20PiVendorList.setDisabled(false);
//	        tabT20PiVendorPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT20PiVendor(T20PiVendor selectedT20PiVendor) {
        this.selectedT20PiVendor = selectedT20PiVendor;
    }

    public T20PiVendor getSelectedT20PiVendor() {
        return this.selectedT20PiVendor;
    }


	public void setT20PiVendorService(T20PiVendorService t20PiVendorService) {
        this.t20PiVendorService = t20PiVendorService;
    }

    public T20PiVendorService getT20PiVendorService() {
        return this.t20PiVendorService;
    }

    public void setT20PiVendorListCtrl(T20PiVendorListCtrl T20PiVendorListCtrl) {
        this.T20PiVendorListCtrl = T20PiVendorListCtrl;
    }

    public T20PiVendorListCtrl getT20PiVendorListCtrl() {
        return this.T20PiVendorListCtrl;
    }

    public void setT20PiVendorDetailCtrl(T20PiVendorDetailCtrl T20PiVendorDetailCtrl) {
        this.T20PiVendorDetailCtrl = T20PiVendorDetailCtrl;
    }

    public T20PiVendorDetailCtrl getT20PiVendorDetailCtrl() {
        return this.T20PiVendorDetailCtrl;
    }

	public void setPostedT20PiVendor(T20PiVendor postedT20PiVendor) {
		this.postedT20PiVendor = postedT20PiVendor;
	}

	public T20PiVendor getPostedT20PiVendor() {
		return postedT20PiVendor;
	}

}
