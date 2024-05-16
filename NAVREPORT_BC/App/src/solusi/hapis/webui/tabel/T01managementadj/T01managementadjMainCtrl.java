package solusi.hapis.webui.tabel.T01managementadj;

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

import solusi.hapis.backend.tabel.model.T01managementadj;
import solusi.hapis.backend.tabel.service.T01managementadjService;
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
 * Date: 07-09-2016
 */

public class T01managementadjMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT01managementadjMain; 

    // Tabs
    protected Tabbox tabbox_T01managementadjMain;
    protected Tab tabT01managementadjList;
    protected Tab tabT01managementadjDetail;
    protected Tab tabT01managementadjPrint;
    protected Tabpanel tabPanelT01managementadjList;
    protected Tabpanel tabPanelT01managementadjDetail;
    protected Tabpanel tabPanelT01managementadjPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T01managementadj_";
    private ButtonStatusCtrl btnCtrlT01managementadj;
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
    private T01managementadjListCtrl T01managementadjListCtrl;
    private T01managementadjDetailCtrl T01managementadjDetailCtrl;
    private T01managementadjPrintCtrl T01managementadjPrintCtrl;

    // Databinding
    private T01managementadj selectedT01managementadj;
    private T01managementadj postedT01managementadj;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T01managementadjService T01managementadjService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/tabel/T01managementadj/T01managementadjDetail.zul";
	private String zulPageList = "/WEB-INF/pages/tabel/T01managementadj/T01managementadjList.zul";
	private String zulPagePrint = "/WEB-INF/pages/tabel/T01managementadj/T01managementadjPrint.zul";

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
    public T01managementadjMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT01managementadjMain(Event event) throws Exception {
        windowT01managementadjMain.setContentStyle("padding:0px;");

        btnCtrlT01managementadj = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnListing, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT01managementadj.setInitInquiryButton();

        tabT01managementadjList.setSelected(true);

        if (tabPanelT01managementadjList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT01managementadjList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT01managementadjList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT01managementadjList.getFirstChild() != null) {

            tabT01managementadjList.setSelected(true);
        	btnCtrlT01managementadj.setInitInquiryButton();
        	getT01managementadjListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT01managementadjList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT01managementadjList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT01managementadjDetail(Event event) throws IOException {
        if (tabPanelT01managementadjDetail.getFirstChild() != null) {
        	tabT01managementadjDetail.setSelected(true);            
        	
        	getT01managementadjDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT01managementadjDetailCtrl().doRenderMode("View");   
            
            btnCtrlT01managementadj.setInitInquiryButton();

            if (getT01managementadjDetailCtrl().getBinder() != null) {
            	getT01managementadjDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT01managementadjDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT01managementadjDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    public void onSelect$tabT01managementadjPrint(Event event) throws IOException, ParseException {
    	if (tabPanelT01managementadjPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT01managementadjPrint, this, "ModuleMainController", zulPagePrint);
        }
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelT01managementadjPrint.getFirstChild() != null) {
            tabT01managementadjPrint.setSelected(true);        

            getT01managementadjPrintCtrl().doReadOnlyMode(true);
            
            return;
        }

        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT01managementadjDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT01managementadjDetail, null));
        } else if (getT01managementadjDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT01managementadjDetail, null));
        }

        T01managementadj anT01managementadj = new T01managementadj();

        // Set Default Value Di sini ------------------------------------- start
        anT01managementadj.setCabang("10");
        anT01managementadj.setAmountHw01(new BigDecimal(0));
        anT01managementadj.setAmountPs01(new BigDecimal(0));
        anT01managementadj.setAmountPs02(new BigDecimal(0));
        anT01managementadj.setAmountPs03(new BigDecimal(0));
        anT01managementadj.setAmountPs04(new BigDecimal(0));
        anT01managementadj.setAmountPs05(new BigDecimal(0));
        
        setSelectedT01managementadj(anT01managementadj);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT01managementadj.setInitFormButton();
        
        // select tab Detail
        tabT01managementadjDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT01managementadjDetailCtrl().getBinder() != null) {
        	getT01managementadjDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT01managementadjDetailCtrl().doRenderMode("New");
        getT01managementadjDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT01managementadj =(T01managementadj) ZksampleBeanUtils.cloneBean(getSelectedT01managementadj());
			
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
        
    	if (tabPanelT01managementadjDetail.getFirstChild() != null) {
        	getT01managementadjDetailCtrl().getBinder().loadAll();
        }
        
        if (getT01managementadjDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT01managementadjDetail, null));
        } else if (getT01managementadjDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT01managementadjDetail, null));
        }

        // set button
        btnCtrlT01managementadj.setInitFormButton();

        // select tab Detail
        tabT01managementadjDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getT01managementadjDetailCtrl().doRenderCombo();
        getT01managementadjDetailCtrl().doRenderMode("Edit");
    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT01managementadj() != null) {
	        final T01managementadj anT01managementadj = getSelectedT01managementadj();
	        if (anT01managementadj != null) {
	
	            // Show a confirm box
	        	String deleteRecord = "";//anT01managementadj.getM12hgol().getKode() +" - "+anT01managementadj.getM12hgol().getKeterangan();
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
	                        getT01managementadjService().delete(anT01managementadj);
	                        setSelectedT01managementadj(null);
	                        
	                        // refresh the list
	                        getT01managementadjListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT01managementadj.setInitInquiryButton();
	        setSelectedT01managementadj(null);
	        
	        // refresh the list
	        getT01managementadjListCtrl().doFillListbox();
	        tabT01managementadjList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT01managementadjDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT01managementadjDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT01managementadjService().save(getSelectedT01managementadj());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT01managementadjService().update(getSelectedT01managementadj());
	            // refresh the list
	            btnCtrlT01managementadj.setInitInquiryButton();
	            getT01managementadjListCtrl().doFillListbox();
                
                tabT01managementadjList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT01managementadj.setInitInquiryButton();
        
        tabT01managementadjList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT01managementadjListCtrl().doFillListbox();
        }
        
    	if(postedT01managementadj != null){
           	setSelectedT01managementadj(postedT01managementadj);
           	
           	//Databinding LOV

           	
    	}
    	if(getT01managementadjDetailCtrl().binder != null){
    		getT01managementadjDetailCtrl().binder.loadAll();
    	} 
    }
    
    // Saat Button Listing di klik
    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
    	
    	if (getT01managementadjPrintCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT01managementadjPrint, null));
        }

        if (tabPanelT01managementadjPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT01managementadjPrint, this, "ModuleMainController", zulPagePrint);
        }
        
        getT01managementadjPrintCtrl().doReadOnlyMode(false);
        
        btnCtrlT01managementadj.setInitPrintButton();
        tabT01managementadjPrint.setSelected(true);

    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/l_t01managementadj.jasper";
    	
    	new JReportGeneratorWindow(getT01managementadjPrintCtrl().getParameterReport(), jasperRpt, "PDF", 1);
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT01managementadjListCtrl() != null) {
        	getT01managementadjListCtrl().getSearchData();        	
        	tabT01managementadjList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT01managementadjListCtrl() != null) {
        	getT01managementadjListCtrl().clearSearchBox();
        	getT01managementadjListCtrl().getSearchData();        	
        	tabT01managementadjList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT01managementadjDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT01managementadjList.setDisabled(true);
	        tabT01managementadjPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT01managementadjDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT01managementadjList.setDisabled(false);
	        tabT01managementadjPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT01managementadj(T01managementadj selectedT01managementadj) {
        this.selectedT01managementadj = selectedT01managementadj;
    }

    public T01managementadj getSelectedT01managementadj() {
        return this.selectedT01managementadj;
    }


	public void setT01managementadjService(T01managementadjService T01managementadjService) {
        this.T01managementadjService = T01managementadjService;
    }

    public T01managementadjService getT01managementadjService() {
        return this.T01managementadjService;
    }

    public void setT01managementadjListCtrl(T01managementadjListCtrl T01managementadjListCtrl) {
        this.T01managementadjListCtrl = T01managementadjListCtrl;
    }

    public T01managementadjListCtrl getT01managementadjListCtrl() {
        return this.T01managementadjListCtrl;
    }

    public void setT01managementadjDetailCtrl(T01managementadjDetailCtrl T01managementadjDetailCtrl) {
        this.T01managementadjDetailCtrl = T01managementadjDetailCtrl;
    }

    public T01managementadjDetailCtrl getT01managementadjDetailCtrl() {
        return this.T01managementadjDetailCtrl;
    }

	public void setT01managementadjPrintCtrl(T01managementadjPrintCtrl T01managementadjPrintCtrl) {
		this.T01managementadjPrintCtrl = T01managementadjPrintCtrl;
	}

	public T01managementadjPrintCtrl getT01managementadjPrintCtrl() {
		return this.T01managementadjPrintCtrl;
	}

	public void setPostedT01managementadj(T01managementadj postedT01managementadj) {
		this.postedT01managementadj = postedT01managementadj;
	}

	public T01managementadj getPostedT01managementadj() {
		return postedT01managementadj;
	}

}
