package solusi.hapis.webui.sales.T21DaftarPenawaranSales;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
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

import solusi.hapis.backend.navbi.model.T21DaftarPenawaranSales;
import solusi.hapis.backend.navbi.service.T21DaftarPenawaranSalesService;
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
 * Date: 16-11-2021
 */

public class T21DaftarPenawaranSalesMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT21DaftarPenawaranSalesMain; 

    // Tabs
    protected Tabbox tabbox_T21DaftarPenawaranSalesMain;
    protected Tab tabT21DaftarPenawaranSalesList;
    protected Tab tabT21DaftarPenawaranSalesDetail;
    protected Tab tabT21DaftarPenawaranSalesPrint;
    
    protected Tabpanel tabPanelT21DaftarPenawaranSalesList;
    protected Tabpanel tabPanelT21DaftarPenawaranSalesDetail;
    protected Tabpanel tabPanelT21DaftarPenawaranSalesPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T21DaftarPenawaranSales_";
    private ButtonStatusCtrl btnCtrlT21DaftarPenawaranSales;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;
    
    protected Button btnAwarded;
    protected Button btnClosed;
    protected Button btnLost;
    protected Button btnPostponed;
    protected Button btnCanceled;
   

    // Tab-Controllers for getting the binders
    private T21DaftarPenawaranSalesListCtrl T21DaftarPenawaranSalesListCtrl;
    private T21DaftarPenawaranSalesDetailCtrl T21DaftarPenawaranSalesDetailCtrl;
    private T21DaftarPenawaranSalesPrintCtrl T21DaftarPenawaranSalesPrintCtrl;

    // Databinding
    private T21DaftarPenawaranSales selectedT21DaftarPenawaranSales;
    private T21DaftarPenawaranSales postedT21DaftarPenawaranSales;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T21DaftarPenawaranSalesService t21DaftarPenawaranSalesService;

    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/sales/T21DaftarPenawaranSales/T21DaftarPenawaranSalesDetail.zul";
	private String zulPageList = "/WEB-INF/pages/sales/T21DaftarPenawaranSales/T21DaftarPenawaranSalesList.zul";
	private String zulPagePrint = "/WEB-INF/pages/sales/T21DaftarPenawaranSales/T21DaftarPenawaranSalesPrint.zul";



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
    public T21DaftarPenawaranSalesMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT21DaftarPenawaranSalesMain(Event event) throws Exception {
        windowT21DaftarPenawaranSalesMain.setContentStyle("padding:0px;");

        btnCtrlT21DaftarPenawaranSales = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnListing, btnOK, btnSave, btnCancel, btnSearch, btnClear);
       
        btnCtrlT21DaftarPenawaranSales.addButtonOther1(btnAwarded, "Awarded");
        btnCtrlT21DaftarPenawaranSales.addButtonOther2(btnLost, "Lost");
        btnCtrlT21DaftarPenawaranSales.addButtonOther3(btnPostponed, "Postponed");
        btnCtrlT21DaftarPenawaranSales.addButtonOther4(btnCanceled, "Canceled");
        btnCtrlT21DaftarPenawaranSales.addButtonOther5(btnClosed, "Closed");
        
        
        btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();

        tabT21DaftarPenawaranSalesList.setSelected(true);

        if (tabPanelT21DaftarPenawaranSalesList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT21DaftarPenawaranSalesList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT21DaftarPenawaranSalesList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT21DaftarPenawaranSalesList.getFirstChild() != null) {

            tabT21DaftarPenawaranSalesList.setSelected(true);
        	btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();
        	getT21DaftarPenawaranSalesListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT21DaftarPenawaranSalesList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT21DaftarPenawaranSalesList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT21DaftarPenawaranSalesDetail(Event event) throws IOException {
        if (tabPanelT21DaftarPenawaranSalesDetail.getFirstChild() != null) {
        	tabT21DaftarPenawaranSalesDetail.setSelected(true);            
        	
        	getT21DaftarPenawaranSalesDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT21DaftarPenawaranSalesDetailCtrl().doRenderMode("View");   
            
            btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();

            if (getT21DaftarPenawaranSalesDetailCtrl().getBinder() != null) {
            	getT21DaftarPenawaranSalesDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT21DaftarPenawaranSalesDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT21DaftarPenawaranSalesDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    public void onSelect$tabT21DaftarPenawaranSalesPrint(Event event) throws IOException, ParseException {
    	if (tabPanelT21DaftarPenawaranSalesPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT21DaftarPenawaranSalesPrint, this, "ModuleMainController", zulPagePrint);
        }
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelT21DaftarPenawaranSalesPrint.getFirstChild() != null) {
            tabT21DaftarPenawaranSalesPrint.setSelected(true);        

            getT21DaftarPenawaranSalesPrintCtrl().doReadOnlyMode(true);
            
            return;
        }

        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button Edit di klik
 	public void onClick$btnAwarded(Event event) throws InterruptedException {
         try {
 			postedT21DaftarPenawaranSales =(T21DaftarPenawaranSales) ZksampleBeanUtils.cloneBean(getSelectedT21DaftarPenawaranSales());
 			
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
         
     	if (tabPanelT21DaftarPenawaranSalesDetail.getFirstChild() != null) {
         	getT21DaftarPenawaranSalesDetailCtrl().getBinder().loadAll();
         }
         
         if (getT21DaftarPenawaranSalesDetailCtrl() == null) {
             Events.sendEvent(new Event("onSelect", tabT21DaftarPenawaranSalesDetail, null));
         } else if (getT21DaftarPenawaranSalesDetailCtrl().getBinder() == null) {
             Events.sendEvent(new Event("onSelect", tabT21DaftarPenawaranSalesDetail, null));
         }

         // set button
         btnCtrlT21DaftarPenawaranSales.setInitFormButton();

         // select tab Detail
         tabT21DaftarPenawaranSalesDetail.setSelected(true);
         renderTabAndTitle("Edit");
         
         state = 3;
         getT21DaftarPenawaranSalesDetailCtrl().getT21DaftarPenawaranSales().setStatusPenawaran("1");
         // set render layar
         getT21DaftarPenawaranSalesDetailCtrl().doRenderCombo();
         getT21DaftarPenawaranSalesDetailCtrl().doRenderMode("Awarded");
     }
    
	
	
	public void onClick$btnLost(Event event) throws InterruptedException {
		if(getSelectedT21DaftarPenawaranSales() != null) {
	    	try {
	    					    		
	    		getSelectedT21DaftarPenawaranSales().setStatusPenawaran("2");
	    		
	        	getT21DaftarPenawaranSalesService().update(getSelectedT21DaftarPenawaranSales());
	            // refresh the list
	            btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();
	            getT21DaftarPenawaranSalesListCtrl().doFillListbox();
                
                tabT21DaftarPenawaranSalesList.setSelected(true);
                
                renderTabAndTitle("Save");
                

	        } catch (DataAccessException e) {
	        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	            return;
	        }
	
		}		 
	} 	
	
	
	
	
	
	
	public void onClick$btnPostponed(Event event) throws InterruptedException {
		if(getSelectedT21DaftarPenawaranSales() != null) {
	    	try {
	    					    		
	    		getSelectedT21DaftarPenawaranSales().setStatusPenawaran("3");
	    		
	        	getT21DaftarPenawaranSalesService().update(getSelectedT21DaftarPenawaranSales());
	            // refresh the list
	            btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();
	            getT21DaftarPenawaranSalesListCtrl().doFillListbox();
                
                tabT21DaftarPenawaranSalesList.setSelected(true);
                
                renderTabAndTitle("Save");
                

	        } catch (DataAccessException e) {
	        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	            return;
	        }
	
		}		 
	} 
	
	public void onClick$btnCanceled(Event event) throws InterruptedException {
		if(getSelectedT21DaftarPenawaranSales() != null) {
	    	try {
	    					    		
	    		getSelectedT21DaftarPenawaranSales().setStatusPenawaran("4");
	    		
	        	getT21DaftarPenawaranSalesService().update(getSelectedT21DaftarPenawaranSales());
	            // refresh the list
	            btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();
	            getT21DaftarPenawaranSalesListCtrl().doFillListbox();
                
                tabT21DaftarPenawaranSalesList.setSelected(true);
                
                renderTabAndTitle("Save");
                

	        } catch (DataAccessException e) {
	        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	            return;
	        }
	
		}		 
	} 
	
	public void onClick$btnClosed(Event event) throws InterruptedException {
		if(getSelectedT21DaftarPenawaranSales() != null) {
	    	try {
	    					    		
	    		getSelectedT21DaftarPenawaranSales().setStatusPenawaran("5");
	    		
	        	getT21DaftarPenawaranSalesService().update(getSelectedT21DaftarPenawaranSales());
	            // refresh the list
	            btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();
	            getT21DaftarPenawaranSalesListCtrl().doFillListbox();
                
                tabT21DaftarPenawaranSalesList.setSelected(true);
                
                renderTabAndTitle("Save");
                

	        } catch (DataAccessException e) {
	        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	            return;
	        }
	
		}		 
	}
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT21DaftarPenawaranSalesDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT21DaftarPenawaranSalesDetail, null));
        } else if (getT21DaftarPenawaranSalesDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT21DaftarPenawaranSalesDetail, null));
        }

        T21DaftarPenawaranSales anT21DaftarPenawaranSales = new T21DaftarPenawaranSales();

        // Set Default Value Di sini ------------------------------------- start
        anT21DaftarPenawaranSales.setCompany("AJ");
        anT21DaftarPenawaranSales.setCabang("JKT");
        anT21DaftarPenawaranSales.setSalesCode(SecurityContextHolder.getContext().getAuthentication().getName());  
        anT21DaftarPenawaranSales.setNoPenawaran(String.valueOf(System.currentTimeMillis()));
        anT21DaftarPenawaranSales.setTglPenawaran(new Date());
        
        anT21DaftarPenawaranSales.setNilai(new BigDecimal(0));
        anT21DaftarPenawaranSales.setStatusPenawaran("0");
        
        setSelectedT21DaftarPenawaranSales(anT21DaftarPenawaranSales);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT21DaftarPenawaranSales.setInitFormButton();
        
        // select tab Detail
        tabT21DaftarPenawaranSalesDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT21DaftarPenawaranSalesDetailCtrl().getBinder() != null) {
        	getT21DaftarPenawaranSalesDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT21DaftarPenawaranSalesDetailCtrl().doRenderMode("New");
        getT21DaftarPenawaranSalesDetailCtrl().doRenderCombo();
    	
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
        try {
			postedT21DaftarPenawaranSales =(T21DaftarPenawaranSales) ZksampleBeanUtils.cloneBean(getSelectedT21DaftarPenawaranSales());
			
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
        
    	if (tabPanelT21DaftarPenawaranSalesDetail.getFirstChild() != null) {
        	getT21DaftarPenawaranSalesDetailCtrl().getBinder().loadAll();
        }
        
        if (getT21DaftarPenawaranSalesDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT21DaftarPenawaranSalesDetail, null));
        } else if (getT21DaftarPenawaranSalesDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT21DaftarPenawaranSalesDetail, null));
        }

        // set button
        btnCtrlT21DaftarPenawaranSales.setInitFormButton();

        // select tab Detail
        tabT21DaftarPenawaranSalesDetail.setSelected(true);
        renderTabAndTitle("Edit");
        
        state = 2;
        
        // set render layar
        getT21DaftarPenawaranSalesDetailCtrl().doRenderCombo();
        getT21DaftarPenawaranSalesDetailCtrl().doRenderMode("Edit");
    }
    

	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT21DaftarPenawaranSales() != null) {
	        final T21DaftarPenawaranSales anT21DaftarPenawaranSales = getSelectedT21DaftarPenawaranSales();
	        if (anT21DaftarPenawaranSales != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anT21DaftarPenawaranSales.getNoPenawaran();
	            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n ==>> " + deleteRecord+" <<== ";
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
	                        getT21DaftarPenawaranSalesService().delete(anT21DaftarPenawaranSales);
	                        setSelectedT21DaftarPenawaranSales(null);
	                        
	                        // refresh the list
	                        getT21DaftarPenawaranSalesListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();
	        setSelectedT21DaftarPenawaranSales(null);
	        
	        // refresh the list
	        getT21DaftarPenawaranSalesListCtrl().doFillListbox();
	        tabT21DaftarPenawaranSalesList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = null ;
    	if(state == 3) {
    		vErrMsg = getT21DaftarPenawaranSalesDetailCtrl().validasiBusinessRulesAwarded();
    	} else {
    		vErrMsg = getT21DaftarPenawaranSalesDetailCtrl().validasiBusinessRules();
    	}
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT21DaftarPenawaranSalesDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		
        		String vNoPen =  getT21DaftarPenawaranSalesService().insert(getSelectedT21DaftarPenawaranSales());
        	
        		onClick$btnCancel(event);
        		
        		
        		@SuppressWarnings("unused")
				String newValue = DisplayNoPenawaranWindow.show(
        				windowT21DaftarPenawaranSalesMain, vNoPen);
        		
        		
        		
        		//StringSelection stringSelection = new StringSelection(vNoPen);
        		//Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        		//clipboard.setContents(stringSelection, null);
        		
        		//MultiLineMessageBox.show("\n"+vNoPen+"\n\n\n", "N o .  P e n a w a r a n", MultiLineMessageBox.OK, "");		
        		
        		
        		//ZksampleMessageUtils.showInformationMessage(vNoPen);

        	} else if(state == 2 || state == 3) {
        		

        		getT21DaftarPenawaranSalesService().update(getSelectedT21DaftarPenawaranSales());
	            // refresh the list
	            btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();
	            getT21DaftarPenawaranSalesListCtrl().doFillListbox();
                
                tabT21DaftarPenawaranSalesList.setSelected(true);
                
                renderTabAndTitle("Save");
        	} 
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT21DaftarPenawaranSales.setInitInquiryButton();
        
        tabT21DaftarPenawaranSalesList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT21DaftarPenawaranSalesListCtrl().doFillListbox();
        }
        
    	if(postedT21DaftarPenawaranSales != null){
           	setSelectedT21DaftarPenawaranSales(postedT21DaftarPenawaranSales);
           	
           	//Databinding LOV

           	
    	}
    	if(getT21DaftarPenawaranSalesDetailCtrl().binder != null){
    		getT21DaftarPenawaranSalesDetailCtrl().binder.loadAll();
    	} 
    }
    
    
    // Saat Button Listing di klik
    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
    	
    	if (getT21DaftarPenawaranSalesPrintCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT21DaftarPenawaranSalesPrint, null));
        }

        if (tabPanelT21DaftarPenawaranSalesPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT21DaftarPenawaranSalesPrint, this, "ModuleMainController", zulPagePrint);
        }
        
        getT21DaftarPenawaranSalesPrintCtrl().doReadOnlyMode(false);
        
        btnCtrlT21DaftarPenawaranSales.setInitPrintButton();
        tabT21DaftarPenawaranSalesPrint.setSelected(true);

    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
    	String jasperRpt = "/solusi/hapis/webui/reports/sales/NoPenawaran/040501_ListingNoPenawaran.jasper";
    	
    	new JReportGeneratorWindow(getT21DaftarPenawaranSalesPrintCtrl().getParameterReport(), jasperRpt, "XLS");
    }
    

    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT21DaftarPenawaranSalesListCtrl() != null) {
        	getT21DaftarPenawaranSalesListCtrl().getSearchData();        	
        	tabT21DaftarPenawaranSalesList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT21DaftarPenawaranSalesListCtrl() != null) {
        	getT21DaftarPenawaranSalesListCtrl().clearSearchBox();
        	getT21DaftarPenawaranSalesListCtrl().getSearchData();        	
        	tabT21DaftarPenawaranSalesList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT21DaftarPenawaranSalesDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT21DaftarPenawaranSalesList.setDisabled(true);
//	        tabT21DaftarPenawaranSalesPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT21DaftarPenawaranSalesDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT21DaftarPenawaranSalesList.setDisabled(false);
//	        tabT21DaftarPenawaranSalesPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT21DaftarPenawaranSales(T21DaftarPenawaranSales selectedT21DaftarPenawaranSales) {
        this.selectedT21DaftarPenawaranSales = selectedT21DaftarPenawaranSales;
    }

    public T21DaftarPenawaranSales getSelectedT21DaftarPenawaranSales() {
        return this.selectedT21DaftarPenawaranSales;
    }


	public void setT21DaftarPenawaranSalesService(T21DaftarPenawaranSalesService t21DaftarPenawaranSalesService) {
        this.t21DaftarPenawaranSalesService = t21DaftarPenawaranSalesService;
    }

    public T21DaftarPenawaranSalesService getT21DaftarPenawaranSalesService() {
        return this.t21DaftarPenawaranSalesService;
    }

    public void setT21DaftarPenawaranSalesListCtrl(T21DaftarPenawaranSalesListCtrl T21DaftarPenawaranSalesListCtrl) {
        this.T21DaftarPenawaranSalesListCtrl = T21DaftarPenawaranSalesListCtrl;
    }

    public T21DaftarPenawaranSalesListCtrl getT21DaftarPenawaranSalesListCtrl() {
        return this.T21DaftarPenawaranSalesListCtrl;
    }

    public void setT21DaftarPenawaranSalesDetailCtrl(T21DaftarPenawaranSalesDetailCtrl T21DaftarPenawaranSalesDetailCtrl) {
        this.T21DaftarPenawaranSalesDetailCtrl = T21DaftarPenawaranSalesDetailCtrl;
    }

    public T21DaftarPenawaranSalesDetailCtrl getT21DaftarPenawaranSalesDetailCtrl() {
        return this.T21DaftarPenawaranSalesDetailCtrl;
    }
    
    public void setT21DaftarPenawaranSalesPrintCtrl(T21DaftarPenawaranSalesPrintCtrl T21DaftarPenawaranSalesPrintCtrl) {
        this.T21DaftarPenawaranSalesPrintCtrl = T21DaftarPenawaranSalesPrintCtrl;
    }

    public T21DaftarPenawaranSalesPrintCtrl getT21DaftarPenawaranSalesPrintCtrl() {
        return this.T21DaftarPenawaranSalesPrintCtrl;
    }

	public void setPostedT21DaftarPenawaranSales(T21DaftarPenawaranSales postedT21DaftarPenawaranSales) {
		this.postedT21DaftarPenawaranSales = postedT21DaftarPenawaranSales;
	}

	public T21DaftarPenawaranSales getPostedT21DaftarPenawaranSales() {
		return postedT21DaftarPenawaranSales;
	}

}
