package solusi.hapis.webui.finance.M04ItemSatindo;



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

import solusi.hapis.backend.navbi.model.M04ItemSatindo;
import solusi.hapis.backend.navbi.service.M04ItemSatindoService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 29-04-2021
 */

public class M04ItemSatindoMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowM04ItemSatindoMain; 

    // Tabs
    protected Tabbox tabbox_M04ItemSatindoMain;
    protected Tab tabM04ItemSatindoList;
    protected Tab tabM04ItemSatindoDetail;
    protected Tabpanel tabPanelM04ItemSatindoList;
    protected Tabpanel tabPanelM04ItemSatindoDetail;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_M04ItemSatindo_";
    private ButtonStatusCtrl btnCtrlM04ItemSatindo;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;

    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private M04ItemSatindoListCtrl M04ItemSatindoListCtrl;
    private M04ItemSatindoDetailCtrl M04ItemSatindoDetailCtrl;


    // Databinding
    private M04ItemSatindo selectedM04ItemSatindo;
    private M04ItemSatindo postedM04ItemSatindo;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private M04ItemSatindoService M04ItemSatindoService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/M04ItemSatindo/M04ItemSatindoDetail.zul";
	private String zulPageList = "/WEB-INF/pages/finance/M04ItemSatindo/M04ItemSatindoList.zul";

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
    public M04ItemSatindoMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowM04ItemSatindoMain(Event event) throws Exception {
        windowM04ItemSatindoMain.setContentStyle("padding:0px;");

        btnCtrlM04ItemSatindo = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlM04ItemSatindo.setInitInquiryButton();

        tabM04ItemSatindoList.setSelected(true);

        if (tabPanelM04ItemSatindoList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM04ItemSatindoList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabM04ItemSatindoList(Event event) throws IOException, InterruptedException {
    	if (tabPanelM04ItemSatindoList.getFirstChild() != null) {

            tabM04ItemSatindoList.setSelected(true);
        	btnCtrlM04ItemSatindo.setInitInquiryButton();
        	getM04ItemSatindoListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelM04ItemSatindoList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM04ItemSatindoList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabM04ItemSatindoDetail(Event event) throws IOException {
        if (tabPanelM04ItemSatindoDetail.getFirstChild() != null) {
        	tabM04ItemSatindoDetail.setSelected(true);            
        	
        	getM04ItemSatindoDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getM04ItemSatindoDetailCtrl().doRenderMode("View");   
            
            btnCtrlM04ItemSatindo.setInitInquiryButton();

            if (getM04ItemSatindoDetailCtrl().getBinder() != null) {
            	getM04ItemSatindoDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelM04ItemSatindoDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelM04ItemSatindoDetail, this, "ModuleMainController", zulPageDetail);
        }
    }


    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getM04ItemSatindoDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabM04ItemSatindoDetail, null));
        } else if (getM04ItemSatindoDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabM04ItemSatindoDetail, null));
        }

        M04ItemSatindo anM04ItemSatindo = new M04ItemSatindo();

        // Set Default Value Di sini ------------------------------------- start
        anM04ItemSatindo.setSatAmountKomisi(new BigDecimal(0));
        anM04ItemSatindo.setIdmrAmountKomisi(new BigDecimal(0));
        
        anM04ItemSatindo.setSatAmountBns(new BigDecimal(0));
        anM04ItemSatindo.setIdmrAmountBns(new BigDecimal(0));
        
        setSelectedM04ItemSatindo(anM04ItemSatindo);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlM04ItemSatindo.setInitFormButton();
        
        // select tab Detail
        tabM04ItemSatindoDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getM04ItemSatindoDetailCtrl().getBinder() != null) {
        	getM04ItemSatindoDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getM04ItemSatindoDetailCtrl().doRenderMode("New");
        getM04ItemSatindoDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedM04ItemSatindo =(M04ItemSatindo) ZksampleBeanUtils.cloneBean(getSelectedM04ItemSatindo());
			
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
        
    	if (tabPanelM04ItemSatindoDetail.getFirstChild() != null) {
        	getM04ItemSatindoDetailCtrl().getBinder().loadAll();
        }
        
        if (getM04ItemSatindoDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabM04ItemSatindoDetail, null));
        } else if (getM04ItemSatindoDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabM04ItemSatindoDetail, null));
        }

        // set button
        btnCtrlM04ItemSatindo.setInitFormButton();

        // select tab Detail
        tabM04ItemSatindoDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getM04ItemSatindoDetailCtrl().doRenderCombo();
        getM04ItemSatindoDetailCtrl().doRenderMode("Edit");
    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedM04ItemSatindo() != null) {
	        final M04ItemSatindo anM04ItemSatindo = getSelectedM04ItemSatindo();
	        if (anM04ItemSatindo != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anM04ItemSatindo.getNoItem() +" - "+anM04ItemSatindo.getNoItem();
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
	                        getM04ItemSatindoService().delete(anM04ItemSatindo);
	                        setSelectedM04ItemSatindo(null);
	                        
	                        // refresh the list
	                        getM04ItemSatindoListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlM04ItemSatindo.setInitInquiryButton();
	        setSelectedM04ItemSatindo(null);
	        
	        // refresh the list
	        getM04ItemSatindoListCtrl().doFillListbox();
	        tabM04ItemSatindoList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getM04ItemSatindoDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getM04ItemSatindoDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getM04ItemSatindoService().save(getSelectedM04ItemSatindo());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getM04ItemSatindoService().update(getSelectedM04ItemSatindo());
	            // refresh the list
	            btnCtrlM04ItemSatindo.setInitInquiryButton();
	            getM04ItemSatindoListCtrl().doFillListbox();
                
                tabM04ItemSatindoList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlM04ItemSatindo.setInitInquiryButton();
        
        tabM04ItemSatindoList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getM04ItemSatindoListCtrl().doFillListbox();
        }
        
    	if(postedM04ItemSatindo != null){
           	setSelectedM04ItemSatindo(postedM04ItemSatindo);
           	
           	//Databinding LOV

           	
    	}
    	if(getM04ItemSatindoDetailCtrl().binder != null){
    		getM04ItemSatindoDetailCtrl().binder.loadAll();
    	} 
    }
    
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
   
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getM04ItemSatindoListCtrl() != null) {
        	getM04ItemSatindoListCtrl().getSearchData();        	
        	tabM04ItemSatindoList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getM04ItemSatindoListCtrl() != null) {
        	getM04ItemSatindoListCtrl().clearSearchBox();
        	getM04ItemSatindoListCtrl().getSearchData();        	
        	tabM04ItemSatindoList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabM04ItemSatindoDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabM04ItemSatindoList.setDisabled(true);
	        
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabM04ItemSatindoDetail.setLabel(Labels.getLabel("common.Details"));
	        tabM04ItemSatindoList.setDisabled(false);
	    
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedM04ItemSatindo(M04ItemSatindo selectedM04ItemSatindo) {
        this.selectedM04ItemSatindo = selectedM04ItemSatindo;
    }

    public M04ItemSatindo getSelectedM04ItemSatindo() {
        return this.selectedM04ItemSatindo;
    }


	public void setM04ItemSatindoService(M04ItemSatindoService M04ItemSatindoService) {
        this.M04ItemSatindoService = M04ItemSatindoService;
    }

    public M04ItemSatindoService getM04ItemSatindoService() {
        return this.M04ItemSatindoService;
    }

    public void setM04ItemSatindoListCtrl(M04ItemSatindoListCtrl M04ItemSatindoListCtrl) {
        this.M04ItemSatindoListCtrl = M04ItemSatindoListCtrl;
    }

    public M04ItemSatindoListCtrl getM04ItemSatindoListCtrl() {
        return this.M04ItemSatindoListCtrl;
    }

    public void setM04ItemSatindoDetailCtrl(M04ItemSatindoDetailCtrl M04ItemSatindoDetailCtrl) {
        this.M04ItemSatindoDetailCtrl = M04ItemSatindoDetailCtrl;
    }

    public M04ItemSatindoDetailCtrl getM04ItemSatindoDetailCtrl() {
        return this.M04ItemSatindoDetailCtrl;
    }

	public void setPostedM04ItemSatindo(M04ItemSatindo postedM04ItemSatindo) {
		this.postedM04ItemSatindo = postedM04ItemSatindo;
	}

	public M04ItemSatindo getPostedM04ItemSatindo() {
		return postedM04ItemSatindo;
	}

}
