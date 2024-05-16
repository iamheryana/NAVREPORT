package solusi.hapis.webui.logistic.T01SoAdj;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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

import solusi.hapis.backend.navbi.model.T01SoAdj;
import solusi.hapis.backend.navbi.service.T01SoAdjService;
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
 * Date: 28-08-2019
 */

public class T01SoAdjMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT01SoAdjMain; 

    // Tabs
    protected Tabbox tabbox_T01SoAdjMain;
    protected Tab tabT01SoAdjList;
    protected Tab tabT01SoAdjDetail;
//    protected Tab tabT01SoAdjPrint;
    protected Tabpanel tabPanelT01SoAdjList;
    protected Tabpanel tabPanelT01SoAdjDetail;
//    protected Tabpanel tabPanelT01SoAdjPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T01SoAdj_";
    private ButtonStatusCtrl btnCtrlT01SoAdj;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
 //   protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T01SoAdjListCtrl T01SoAdjListCtrl;
    private T01SoAdjDetailCtrl T01SoAdjDetailCtrl;
    private T01SoAdjPrintCtrl T01SoAdjPrintCtrl;

    // Databinding
    private T01SoAdj selectedT01SoAdj;
    private T01SoAdj postedT01SoAdj;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T01SoAdjService t01SoAdjService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/logistic/T01SoAdj/T01SoAdjDetail.zul";
	private String zulPageList = "/WEB-INF/pages/logistic/T01SoAdj/T01SoAdjList.zul";
//	private String zulPagePrint = "/WEB-INF/pages/logistic/T01SoAdj/T01SoAdjPrint.zul";

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
    public T01SoAdjMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT01SoAdjMain(Event event) throws Exception {
        windowT01SoAdjMain.setContentStyle("padding:0px;");

        btnCtrlT01SoAdj = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT01SoAdj.setInitInquiryButton();

        tabT01SoAdjList.setSelected(true);

        if (tabPanelT01SoAdjList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT01SoAdjList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT01SoAdjList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT01SoAdjList.getFirstChild() != null) {

            tabT01SoAdjList.setSelected(true);
        	btnCtrlT01SoAdj.setInitInquiryButton();
        	getT01SoAdjListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT01SoAdjList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT01SoAdjList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT01SoAdjDetail(Event event) throws IOException {
        if (tabPanelT01SoAdjDetail.getFirstChild() != null) {
        	tabT01SoAdjDetail.setSelected(true);            
        	
        	getT01SoAdjDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT01SoAdjDetailCtrl().doRenderMode("View");   
            
            btnCtrlT01SoAdj.setInitInquiryButton();

            if (getT01SoAdjDetailCtrl().getBinder() != null) {
            	getT01SoAdjDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT01SoAdjDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT01SoAdjDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

//    public void onSelect$tabT01SoAdjPrint(Event event) throws IOException, ParseException {
//    	if (tabPanelT01SoAdjPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT01SoAdjPrint, this, "ModuleMainController", zulPagePrint);
//        }
//    	
//    	// Check if the tabpanel is already loaded
//        if (tabPanelT01SoAdjPrint.getFirstChild() != null) {
//            tabT01SoAdjPrint.setSelected(true);        
//
//            getT01SoAdjPrintCtrl().doReadOnlyMode(true);
//            
//            return;
//        }        
//    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT01SoAdjDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT01SoAdjDetail, null));
        } else if (getT01SoAdjDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT01SoAdjDetail, null));
        }

        T01SoAdj anT01SoAdj = new T01SoAdj();

        // Set Default Value Di sini ------------------------------------- start
        anT01SoAdj.setAddDays(0);
        anT01SoAdj.setUseCclDate("N");
        anT01SoAdj.setUseQty("Y");
        
        setSelectedT01SoAdj(anT01SoAdj);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT01SoAdj.setInitFormButton();
        
        // select tab Detail
        tabT01SoAdjDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT01SoAdjDetailCtrl().getBinder() != null) {
        	getT01SoAdjDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT01SoAdjDetailCtrl().doRenderMode("New");
        getT01SoAdjDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT01SoAdj =(T01SoAdj) ZksampleBeanUtils.cloneBean(getSelectedT01SoAdj());
			
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
        
    	if (tabPanelT01SoAdjDetail.getFirstChild() != null) {
        	getT01SoAdjDetailCtrl().getBinder().loadAll();
        }
        
        if (getT01SoAdjDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT01SoAdjDetail, null));
        } else if (getT01SoAdjDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT01SoAdjDetail, null));
        }

        // set button
        btnCtrlT01SoAdj.setInitFormButton();

        // select tab Detail
        tabT01SoAdjDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getT01SoAdjDetailCtrl().doRenderCombo();
        getT01SoAdjDetailCtrl().doRenderMode("Edit");
    }
    
	public String getDescJnsPayment(String jnsPayment){
		String vResult = "";
		if(jnsPayment.equals("D") == true){
			vResult = "Down Payment";
		} else if(jnsPayment.equals("M") == true){
			vResult = "Installment (Monthly)";
		} else if(jnsPayment.equals("Q") == true){
			vResult = "Installment (Quarterly)";
		} else if(jnsPayment.equals("S") == true){
			vResult = "Installment (Semesterly)";
		} else if(jnsPayment.equals("Y") == true){
			vResult = "Installment (Yearly)";
		}
		
		return vResult;
	}
	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT01SoAdj() != null) {
	        final T01SoAdj anT01SoAdj = getSelectedT01SoAdj();
	        if (anT01SoAdj != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anT01SoAdj.getNoSo() +" - "+getDescJnsPayment(anT01SoAdj.getJenisPayment());
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
	                        getT01SoAdjService().delete(anT01SoAdj);
	                        setSelectedT01SoAdj(null);
	                        
	                        // refresh the list
	                        getT01SoAdjListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT01SoAdj.setInitInquiryButton();
	        setSelectedT01SoAdj(null);
	        
	        // refresh the list
	        getT01SoAdjListCtrl().doFillListbox();
	        tabT01SoAdjList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT01SoAdjDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT01SoAdjDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT01SoAdjService().save(getSelectedT01SoAdj());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT01SoAdjService().update(getSelectedT01SoAdj());
	            // refresh the list
	            btnCtrlT01SoAdj.setInitInquiryButton();
	            getT01SoAdjListCtrl().doFillListbox();
                
                tabT01SoAdjList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT01SoAdj.setInitInquiryButton();
        
        tabT01SoAdjList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT01SoAdjListCtrl().doFillListbox();
        }
        
    	if(postedT01SoAdj != null){
           	setSelectedT01SoAdj(postedT01SoAdj);
           	
           	//Databinding LOV

           	
    	}
    	if(getT01SoAdjDetailCtrl().binder != null){
    		getT01SoAdjDetailCtrl().binder.loadAll();
    	} 
    }
    
//    // Saat Button Listing di klik
//    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
//    	
//    	if (getT01SoAdjPrintCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabT01SoAdjPrint, null));
//        }
//
//        if (tabPanelT01SoAdjPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT01SoAdjPrint, this, "ModuleMainController", zulPagePrint);
//        }
//        
//        getT01SoAdjPrintCtrl().doReadOnlyMode(false);
//        
//        btnCtrlT01SoAdj.setInitPrintButton();
//        tabT01SoAdjPrint.setSelected(true);
//
//    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listT01SoAdj.jasper";
    	
    	new JReportGeneratorWindow(getT01SoAdjPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT01SoAdjListCtrl() != null) {
        	getT01SoAdjListCtrl().getSearchData();        	
        	tabT01SoAdjList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT01SoAdjListCtrl() != null) {
        	getT01SoAdjListCtrl().clearSearchBox();
        	getT01SoAdjListCtrl().getSearchData();        	
        	tabT01SoAdjList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT01SoAdjDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT01SoAdjList.setDisabled(true);
//	        tabT01SoAdjPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT01SoAdjDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT01SoAdjList.setDisabled(false);
//	        tabT01SoAdjPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT01SoAdj(T01SoAdj selectedT01SoAdj) {
        this.selectedT01SoAdj = selectedT01SoAdj;
    }

    public T01SoAdj getSelectedT01SoAdj() {
        return this.selectedT01SoAdj;
    }


	public void setT01SoAdjService(T01SoAdjService t01SoAdjService) {
        this.t01SoAdjService = t01SoAdjService;
    }

    public T01SoAdjService getT01SoAdjService() {
        return this.t01SoAdjService;
    }

    public void setT01SoAdjListCtrl(T01SoAdjListCtrl T01SoAdjListCtrl) {
        this.T01SoAdjListCtrl = T01SoAdjListCtrl;
    }

    public T01SoAdjListCtrl getT01SoAdjListCtrl() {
        return this.T01SoAdjListCtrl;
    }

    public void setT01SoAdjDetailCtrl(T01SoAdjDetailCtrl T01SoAdjDetailCtrl) {
        this.T01SoAdjDetailCtrl = T01SoAdjDetailCtrl;
    }

    public T01SoAdjDetailCtrl getT01SoAdjDetailCtrl() {
        return this.T01SoAdjDetailCtrl;
    }

	public void setT01SoAdjPrintCtrl(T01SoAdjPrintCtrl T01SoAdjPrintCtrl) {
		this.T01SoAdjPrintCtrl = T01SoAdjPrintCtrl;
	}

	public T01SoAdjPrintCtrl getT01SoAdjPrintCtrl() {
		return this.T01SoAdjPrintCtrl;
	}

	public void setPostedT01SoAdj(T01SoAdj postedT01SoAdj) {
		this.postedT01SoAdj = postedT01SoAdj;
	}

	public T01SoAdj getPostedT01SoAdj() {
		return postedT01SoAdj;
	}

}
