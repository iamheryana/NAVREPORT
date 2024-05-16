package solusi.hapis.webui.general.pembelian.T19PiItem;


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

import solusi.hapis.backend.navbi.model.T19PiItem;
import solusi.hapis.backend.navbi.service.T19PiItemService;
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

public class T19PiItemMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT19PiItemMain; 

    // Tabs
    protected Tabbox tabbox_T19PiItemMain;
    protected Tab tabT19PiItemList;
    protected Tab tabT19PiItemDetail;
    
    protected Tabpanel tabPanelT19PiItemList;
    protected Tabpanel tabPanelT19PiItemDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T19PiItem_";
    private ButtonStatusCtrl btnCtrlT19PiItem;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;

    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T19PiItemListCtrl T19PiItemListCtrl;
    private T19PiItemDetailCtrl T19PiItemDetailCtrl;

    // Databinding
    private T19PiItem selectedT19PiItem;
    private T19PiItem postedT19PiItem;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T19PiItemService t19PiItemService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/general/pembelian/T19PiItem/T19PiItemDetail.zul";
	private String zulPageList = "/WEB-INF/pages/general/pembelian/T19PiItem/T19PiItemList.zul";



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
    public T19PiItemMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT19PiItemMain(Event event) throws Exception {
        windowT19PiItemMain.setContentStyle("padding:0px;");

        btnCtrlT19PiItem = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT19PiItem.setInitInquiryButton();

        tabT19PiItemList.setSelected(true);

        if (tabPanelT19PiItemList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT19PiItemList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT19PiItemList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT19PiItemList.getFirstChild() != null) {

            tabT19PiItemList.setSelected(true);
        	btnCtrlT19PiItem.setInitInquiryButton();
        	getT19PiItemListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT19PiItemList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT19PiItemList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT19PiItemDetail(Event event) throws IOException {
        if (tabPanelT19PiItemDetail.getFirstChild() != null) {
        	tabT19PiItemDetail.setSelected(true);            
        	
        	getT19PiItemDetailCtrl().doRenderCombo();
        	
        	// Render Inisialisasi posisi awal
            getT19PiItemDetailCtrl().doRenderMode("View");   
            
            
            btnCtrlT19PiItem.setInitInquiryButton();

            if (getT19PiItemDetailCtrl().getBinder() != null) {
            	getT19PiItemDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT19PiItemDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT19PiItemDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT19PiItemDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT19PiItemDetail, null));
        } else if (getT19PiItemDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT19PiItemDetail, null));
        }

        T19PiItem anT19PiItem = new T19PiItem();

        // Set Default Value Di sini ------------------------------------- start
        anT19PiItem.setBerlaku("N");
        
        setSelectedT19PiItem(anT19PiItem);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT19PiItem.setInitFormButton();
        
        // select tab Detail
        tabT19PiItemDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT19PiItemDetailCtrl().getBinder() != null) {
        	getT19PiItemDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT19PiItemDetailCtrl().doRenderMode("New");
        getT19PiItemDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT19PiItem =(T19PiItem) ZksampleBeanUtils.cloneBean(getSelectedT19PiItem());
			
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
        
    	if (tabPanelT19PiItemDetail.getFirstChild() != null) {
        	getT19PiItemDetailCtrl().getBinder().loadAll();
        }
        
        if (getT19PiItemDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT19PiItemDetail, null));
        } else if (getT19PiItemDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT19PiItemDetail, null));
        }

        // set button
        btnCtrlT19PiItem.setInitFormButton();

        // select tab Detail
        tabT19PiItemDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getT19PiItemDetailCtrl().doRenderCombo();
        getT19PiItemDetailCtrl().doRenderMode("Edit");
    }
    

	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT19PiItem() != null) {
	        final T19PiItem anT19PiItem = getSelectedT19PiItem();
	        if (anT19PiItem != null) {
	
	            // Show a confirm box
	        	String deleteRecord = CommonUtils.convertDateToString(anT19PiItem.getTglMulai()) +" - "+ anT19PiItem.getPrincipalCode() +" - "+ anT19PiItem.getItemCatCode() +" - "+ anT19PiItem.getProductCode();
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
	                        getT19PiItemService().delete(anT19PiItem);
	                        setSelectedT19PiItem(null);
	                        
	                        // refresh the list
	                        getT19PiItemListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT19PiItem.setInitInquiryButton();
	        setSelectedT19PiItem(null);
	        
	        // refresh the list
	        getT19PiItemListCtrl().doFillListbox();
	        tabT19PiItemList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT19PiItemDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT19PiItemDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT19PiItemService().save(getSelectedT19PiItem());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT19PiItemService().update(getSelectedT19PiItem());
	            // refresh the list
	            btnCtrlT19PiItem.setInitInquiryButton();
	            getT19PiItemListCtrl().doFillListbox();
                
                tabT19PiItemList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT19PiItem.setInitInquiryButton();
        
        tabT19PiItemList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT19PiItemListCtrl().doFillListbox();
        }
        
    	if(postedT19PiItem != null){
           	setSelectedT19PiItem(postedT19PiItem);
           	
           	//Databinding LOV

           	
    	}
    	if(getT19PiItemDetailCtrl().binder != null){
    		getT19PiItemDetailCtrl().binder.loadAll();
    	} 
    }
    
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT19PiItemListCtrl() != null) {
        	getT19PiItemListCtrl().getSearchData();        	
        	tabT19PiItemList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT19PiItemListCtrl() != null) {
        	getT19PiItemListCtrl().clearSearchBox();
        	getT19PiItemListCtrl().getSearchData();        	
        	tabT19PiItemList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT19PiItemDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT19PiItemList.setDisabled(true);
//	        tabT19PiItemPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT19PiItemDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT19PiItemList.setDisabled(false);
//	        tabT19PiItemPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT19PiItem(T19PiItem selectedT19PiItem) {
        this.selectedT19PiItem = selectedT19PiItem;
    }

    public T19PiItem getSelectedT19PiItem() {
        return this.selectedT19PiItem;
    }


	public void setT19PiItemService(T19PiItemService t19PiItemService) {
        this.t19PiItemService = t19PiItemService;
    }

    public T19PiItemService getT19PiItemService() {
        return this.t19PiItemService;
    }

    public void setT19PiItemListCtrl(T19PiItemListCtrl T19PiItemListCtrl) {
        this.T19PiItemListCtrl = T19PiItemListCtrl;
    }

    public T19PiItemListCtrl getT19PiItemListCtrl() {
        return this.T19PiItemListCtrl;
    }

    public void setT19PiItemDetailCtrl(T19PiItemDetailCtrl T19PiItemDetailCtrl) {
        this.T19PiItemDetailCtrl = T19PiItemDetailCtrl;
    }

    public T19PiItemDetailCtrl getT19PiItemDetailCtrl() {
        return this.T19PiItemDetailCtrl;
    }

	public void setPostedT19PiItem(T19PiItem postedT19PiItem) {
		this.postedT19PiItem = postedT19PiItem;
	}

	public T19PiItem getPostedT19PiItem() {
		return postedT19PiItem;
	}

}
