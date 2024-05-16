package solusi.hapis.webui.tabel.T07itemsatindo;


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

import solusi.hapis.backend.tabel.model.T07itemsatindo;
import solusi.hapis.backend.tabel.service.T07itemsatindoService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 05-07-2018
 */

public class T07itemsatindoMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT07itemsatindoMain; 

    // Tabs
    protected Tabbox tabbox_T07itemsatindoMain;
    protected Tab tabT07itemsatindoList;
    protected Tab tabT07itemsatindoDetail;
    protected Tab tabT07itemsatindoPrint;
    protected Tabpanel tabPanelT07itemsatindoList;
    protected Tabpanel tabPanelT07itemsatindoDetail;
    protected Tabpanel tabPanelT07itemsatindoPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T07itemsatindo_";
    private ButtonStatusCtrl btnCtrlT07itemsatindo;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T07itemsatindoListCtrl T07itemsatindoListCtrl;
    private T07itemsatindoDetailCtrl T07itemsatindoDetailCtrl;
    private T07itemsatindoPrintCtrl T07itemsatindoPrintCtrl;

    // Databinding
    private T07itemsatindo selectedT07itemsatindo;
    private T07itemsatindo postedT07itemsatindo;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T07itemsatindoService T07itemsatindoService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/tabel/T07itemsatindo/T07itemsatindoDetail.zul";
	private String zulPageList = "/WEB-INF/pages/tabel/T07itemsatindo/T07itemsatindoList.zul";
	private String zulPagePrint = "/WEB-INF/pages/tabel/T07itemsatindo/T07itemsatindoPrint.zul";

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
    public T07itemsatindoMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT07itemsatindoMain(Event event) throws Exception {
        windowT07itemsatindoMain.setContentStyle("padding:0px;");

        btnCtrlT07itemsatindo = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnListing, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT07itemsatindo.setInitInquiryButton();

        tabT07itemsatindoList.setSelected(true);

        if (tabPanelT07itemsatindoList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT07itemsatindoList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT07itemsatindoList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT07itemsatindoList.getFirstChild() != null) {

            tabT07itemsatindoList.setSelected(true);
        	btnCtrlT07itemsatindo.setInitInquiryButton();
        	getT07itemsatindoListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT07itemsatindoList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT07itemsatindoList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT07itemsatindoDetail(Event event) throws IOException {
        if (tabPanelT07itemsatindoDetail.getFirstChild() != null) {
        	tabT07itemsatindoDetail.setSelected(true);            
        	
        	getT07itemsatindoDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT07itemsatindoDetailCtrl().doRenderMode("View");   
            
            btnCtrlT07itemsatindo.setInitInquiryButton();

            if (getT07itemsatindoDetailCtrl().getBinder() != null) {
            	getT07itemsatindoDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT07itemsatindoDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT07itemsatindoDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    public void onSelect$tabT07itemsatindoPrint(Event event) throws IOException, ParseException {
    	if (tabPanelT07itemsatindoPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT07itemsatindoPrint, this, "ModuleMainController", zulPagePrint);
        }
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelT07itemsatindoPrint.getFirstChild() != null) {
            tabT07itemsatindoPrint.setSelected(true);        

            getT07itemsatindoPrintCtrl().doReadOnlyMode(true);
            
            return;
        }

        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT07itemsatindoDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT07itemsatindoDetail, null));
        } else if (getT07itemsatindoDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT07itemsatindoDetail, null));
        }

        T07itemsatindo anT07itemsatindo = new T07itemsatindo();

        // Set Default Value Di sini ------------------------------------- start
        anT07itemsatindo.setSatAmountKomisi(new BigDecimal(0));
        anT07itemsatindo.setIdmrAmountKomisi(new BigDecimal(0));
        
        anT07itemsatindo.setSatAmountBns(new BigDecimal(0));
        anT07itemsatindo.setIdmrAmountBns(new BigDecimal(0));
        
        setSelectedT07itemsatindo(anT07itemsatindo);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT07itemsatindo.setInitFormButton();
        
        // select tab Detail
        tabT07itemsatindoDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT07itemsatindoDetailCtrl().getBinder() != null) {
        	getT07itemsatindoDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT07itemsatindoDetailCtrl().doRenderMode("New");
        getT07itemsatindoDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT07itemsatindo =(T07itemsatindo) ZksampleBeanUtils.cloneBean(getSelectedT07itemsatindo());
			
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
        
    	if (tabPanelT07itemsatindoDetail.getFirstChild() != null) {
        	getT07itemsatindoDetailCtrl().getBinder().loadAll();
        }
        
        if (getT07itemsatindoDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT07itemsatindoDetail, null));
        } else if (getT07itemsatindoDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT07itemsatindoDetail, null));
        }

        // set button
        btnCtrlT07itemsatindo.setInitFormButton();

        // select tab Detail
        tabT07itemsatindoDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getT07itemsatindoDetailCtrl().doRenderCombo();
        getT07itemsatindoDetailCtrl().doRenderMode("Edit");
    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT07itemsatindo() != null) {
	        final T07itemsatindo anT07itemsatindo = getSelectedT07itemsatindo();
	        if (anT07itemsatindo != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anT07itemsatindo.getNoItem() +" - "+anT07itemsatindo.getNoItem();
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
	                        getT07itemsatindoService().delete(anT07itemsatindo);
	                        setSelectedT07itemsatindo(null);
	                        
	                        // refresh the list
	                        getT07itemsatindoListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT07itemsatindo.setInitInquiryButton();
	        setSelectedT07itemsatindo(null);
	        
	        // refresh the list
	        getT07itemsatindoListCtrl().doFillListbox();
	        tabT07itemsatindoList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT07itemsatindoDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT07itemsatindoDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT07itemsatindoService().save(getSelectedT07itemsatindo());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT07itemsatindoService().update(getSelectedT07itemsatindo());
	            // refresh the list
	            btnCtrlT07itemsatindo.setInitInquiryButton();
	            getT07itemsatindoListCtrl().doFillListbox();
                
                tabT07itemsatindoList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT07itemsatindo.setInitInquiryButton();
        
        tabT07itemsatindoList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT07itemsatindoListCtrl().doFillListbox();
        }
        
    	if(postedT07itemsatindo != null){
           	setSelectedT07itemsatindo(postedT07itemsatindo);
           	
           	//Databinding LOV

           	
    	}
    	if(getT07itemsatindoDetailCtrl().binder != null){
    		getT07itemsatindoDetailCtrl().binder.loadAll();
    	} 
    }
    
    // Saat Button Listing di klik
    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
    	
    	if (getT07itemsatindoPrintCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT07itemsatindoPrint, null));
        }

        if (tabPanelT07itemsatindoPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT07itemsatindoPrint, this, "ModuleMainController", zulPagePrint);
        }
        
        getT07itemsatindoPrintCtrl().doReadOnlyMode(false);
        
        btnCtrlT07itemsatindo.setInitPrintButton();
        tabT07itemsatindoPrint.setSelected(true);

    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listT07itemsatindo.jasper";
    	
    	new JReportGeneratorWindow(getT07itemsatindoPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT07itemsatindoListCtrl() != null) {
        	getT07itemsatindoListCtrl().getSearchData();        	
        	tabT07itemsatindoList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT07itemsatindoListCtrl() != null) {
        	getT07itemsatindoListCtrl().clearSearchBox();
        	getT07itemsatindoListCtrl().getSearchData();        	
        	tabT07itemsatindoList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT07itemsatindoDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT07itemsatindoList.setDisabled(true);
	        tabT07itemsatindoPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT07itemsatindoDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT07itemsatindoList.setDisabled(false);
	        tabT07itemsatindoPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT07itemsatindo(T07itemsatindo selectedT07itemsatindo) {
        this.selectedT07itemsatindo = selectedT07itemsatindo;
    }

    public T07itemsatindo getSelectedT07itemsatindo() {
        return this.selectedT07itemsatindo;
    }


	public void setT07itemsatindoService(T07itemsatindoService T07itemsatindoService) {
        this.T07itemsatindoService = T07itemsatindoService;
    }

    public T07itemsatindoService getT07itemsatindoService() {
        return this.T07itemsatindoService;
    }

    public void setT07itemsatindoListCtrl(T07itemsatindoListCtrl T07itemsatindoListCtrl) {
        this.T07itemsatindoListCtrl = T07itemsatindoListCtrl;
    }

    public T07itemsatindoListCtrl getT07itemsatindoListCtrl() {
        return this.T07itemsatindoListCtrl;
    }

    public void setT07itemsatindoDetailCtrl(T07itemsatindoDetailCtrl T07itemsatindoDetailCtrl) {
        this.T07itemsatindoDetailCtrl = T07itemsatindoDetailCtrl;
    }

    public T07itemsatindoDetailCtrl getT07itemsatindoDetailCtrl() {
        return this.T07itemsatindoDetailCtrl;
    }

	public void setT07itemsatindoPrintCtrl(T07itemsatindoPrintCtrl T07itemsatindoPrintCtrl) {
		this.T07itemsatindoPrintCtrl = T07itemsatindoPrintCtrl;
	}

	public T07itemsatindoPrintCtrl getT07itemsatindoPrintCtrl() {
		return this.T07itemsatindoPrintCtrl;
	}

	public void setPostedT07itemsatindo(T07itemsatindo postedT07itemsatindo) {
		this.postedT07itemsatindo = postedT07itemsatindo;
	}

	public T07itemsatindo getPostedT07itemsatindo() {
		return postedT07itemsatindo;
	}

}
