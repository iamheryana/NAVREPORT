package solusi.hapis.webui.ps.T12PsAdjPrice;


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

import solusi.hapis.backend.navbi.model.T12PsAdjPrice;
import solusi.hapis.backend.navbi.service.T12PsAdjPriceService;
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
 * Date: 28-01-2021
 */

public class T12PsAdjPriceMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT12PsAdjPriceMain; 

    // Tabs
    protected Tabbox tabbox_T12PsAdjPriceMain;
    protected Tab tabT12PsAdjPriceList;
    protected Tab tabT12PsAdjPriceDetail;
    
    protected Tabpanel tabPanelT12PsAdjPriceList;
    protected Tabpanel tabPanelT12PsAdjPriceDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T12PsAdjPrice_";
    private ButtonStatusCtrl btnCtrlT12PsAdjPrice;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;

    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T12PsAdjPriceListCtrl T12PsAdjPriceListCtrl;
    private T12PsAdjPriceDetailCtrl T12PsAdjPriceDetailCtrl;

    // Databinding
    private T12PsAdjPrice selectedT12PsAdjPrice;
    private T12PsAdjPrice postedT12PsAdjPrice;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T12PsAdjPriceService t12PsAdjPriceService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/ps/T12PsAdjPrice/T12PsAdjPriceDetail.zul";
	private String zulPageList = "/WEB-INF/pages/ps/T12PsAdjPrice/T12PsAdjPriceList.zul";



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
    public T12PsAdjPriceMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT12PsAdjPriceMain(Event event) throws Exception {
        windowT12PsAdjPriceMain.setContentStyle("padding:0px;");

        btnCtrlT12PsAdjPrice = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT12PsAdjPrice.setInitInquiryButton();

        tabT12PsAdjPriceList.setSelected(true);

        if (tabPanelT12PsAdjPriceList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT12PsAdjPriceList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT12PsAdjPriceList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT12PsAdjPriceList.getFirstChild() != null) {

            tabT12PsAdjPriceList.setSelected(true);
        	btnCtrlT12PsAdjPrice.setInitInquiryButton();
        	getT12PsAdjPriceListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT12PsAdjPriceList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT12PsAdjPriceList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT12PsAdjPriceDetail(Event event) throws IOException {
        if (tabPanelT12PsAdjPriceDetail.getFirstChild() != null) {
        	tabT12PsAdjPriceDetail.setSelected(true);            
        	
        	// Render Inisialisasi posisi awal
            getT12PsAdjPriceDetailCtrl().doRenderMode("View");   
            
            btnCtrlT12PsAdjPrice.setInitInquiryButton();

            if (getT12PsAdjPriceDetailCtrl().getBinder() != null) {
            	getT12PsAdjPriceDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT12PsAdjPriceDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT12PsAdjPriceDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT12PsAdjPriceDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT12PsAdjPriceDetail, null));
        } else if (getT12PsAdjPriceDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT12PsAdjPriceDetail, null));
        }

        T12PsAdjPrice anT12PsAdjPrice = new T12PsAdjPrice();

        // Set Default Value Di sini ------------------------------------- start

        
        setSelectedT12PsAdjPrice(anT12PsAdjPrice);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT12PsAdjPrice.setInitFormButton();
        
        // select tab Detail
        tabT12PsAdjPriceDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT12PsAdjPriceDetailCtrl().getBinder() != null) {
        	getT12PsAdjPriceDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT12PsAdjPriceDetailCtrl().doRenderMode("New");

    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT12PsAdjPrice =(T12PsAdjPrice) ZksampleBeanUtils.cloneBean(getSelectedT12PsAdjPrice());
			
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
        
    	if (tabPanelT12PsAdjPriceDetail.getFirstChild() != null) {
        	getT12PsAdjPriceDetailCtrl().getBinder().loadAll();
        }
        
        if (getT12PsAdjPriceDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT12PsAdjPriceDetail, null));
        } else if (getT12PsAdjPriceDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT12PsAdjPriceDetail, null));
        }

        // set button
        btnCtrlT12PsAdjPrice.setInitFormButton();

        // select tab Detail
        tabT12PsAdjPriceDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar

        getT12PsAdjPriceDetailCtrl().doRenderMode("Edit");
    }
    

	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT12PsAdjPrice() != null) {
	        final T12PsAdjPrice anT12PsAdjPrice = getSelectedT12PsAdjPrice();
	        if (anT12PsAdjPrice != null) {
	
	            // Show a confirm box
	        	String deleteRecord = CommonUtils.convertDateToString(anT12PsAdjPrice.getTglBerlaku()) +" - "+anT12PsAdjPrice.getCustNo()+" - "+anT12PsAdjPrice.getItemNo();
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
	                        getT12PsAdjPriceService().delete(anT12PsAdjPrice);
	                        setSelectedT12PsAdjPrice(null);
	                        
	                        // refresh the list
	                        getT12PsAdjPriceListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT12PsAdjPrice.setInitInquiryButton();
	        setSelectedT12PsAdjPrice(null);
	        
	        // refresh the list
	        getT12PsAdjPriceListCtrl().doFillListbox();
	        tabT12PsAdjPriceList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT12PsAdjPriceDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT12PsAdjPriceDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT12PsAdjPriceService().save(getSelectedT12PsAdjPrice());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT12PsAdjPriceService().update(getSelectedT12PsAdjPrice());
	            // refresh the list
	            btnCtrlT12PsAdjPrice.setInitInquiryButton();
	            getT12PsAdjPriceListCtrl().doFillListbox();
                
                tabT12PsAdjPriceList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT12PsAdjPrice.setInitInquiryButton();
        
        tabT12PsAdjPriceList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT12PsAdjPriceListCtrl().doFillListbox();
        }
        
    	if(postedT12PsAdjPrice != null){
           	setSelectedT12PsAdjPrice(postedT12PsAdjPrice);
           	
           	//Databinding LOV

           	
    	}
    	if(getT12PsAdjPriceDetailCtrl().binder != null){
    		getT12PsAdjPriceDetailCtrl().binder.loadAll();
    	} 
    }
    
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT12PsAdjPriceListCtrl() != null) {
        	getT12PsAdjPriceListCtrl().getSearchData();        	
        	tabT12PsAdjPriceList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT12PsAdjPriceListCtrl() != null) {
        	getT12PsAdjPriceListCtrl().clearSearchBox();
        	getT12PsAdjPriceListCtrl().getSearchData();        	
        	tabT12PsAdjPriceList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT12PsAdjPriceDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT12PsAdjPriceList.setDisabled(true);
//	        tabT12PsAdjPricePrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT12PsAdjPriceDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT12PsAdjPriceList.setDisabled(false);
//	        tabT12PsAdjPricePrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT12PsAdjPrice(T12PsAdjPrice selectedT12PsAdjPrice) {
        this.selectedT12PsAdjPrice = selectedT12PsAdjPrice;
    }

    public T12PsAdjPrice getSelectedT12PsAdjPrice() {
        return this.selectedT12PsAdjPrice;
    }


	public void setT12PsAdjPriceService(T12PsAdjPriceService t12PsAdjPriceService) {
        this.t12PsAdjPriceService = t12PsAdjPriceService;
    }

    public T12PsAdjPriceService getT12PsAdjPriceService() {
        return this.t12PsAdjPriceService;
    }

    public void setT12PsAdjPriceListCtrl(T12PsAdjPriceListCtrl T12PsAdjPriceListCtrl) {
        this.T12PsAdjPriceListCtrl = T12PsAdjPriceListCtrl;
    }

    public T12PsAdjPriceListCtrl getT12PsAdjPriceListCtrl() {
        return this.T12PsAdjPriceListCtrl;
    }

    public void setT12PsAdjPriceDetailCtrl(T12PsAdjPriceDetailCtrl T12PsAdjPriceDetailCtrl) {
        this.T12PsAdjPriceDetailCtrl = T12PsAdjPriceDetailCtrl;
    }

    public T12PsAdjPriceDetailCtrl getT12PsAdjPriceDetailCtrl() {
        return this.T12PsAdjPriceDetailCtrl;
    }

	public void setPostedT12PsAdjPrice(T12PsAdjPrice postedT12PsAdjPrice) {
		this.postedT12PsAdjPrice = postedT12PsAdjPrice;
	}

	public T12PsAdjPrice getPostedT12PsAdjPrice() {
		return postedT12PsAdjPrice;
	}

}
