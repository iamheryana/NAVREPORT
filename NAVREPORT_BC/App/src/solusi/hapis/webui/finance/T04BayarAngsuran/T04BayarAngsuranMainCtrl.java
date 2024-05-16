package solusi.hapis.webui.finance.T04BayarAngsuran;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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

import solusi.hapis.backend.navbi.model.T04BayarAngsuran;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.P01BiayaTransService;
import solusi.hapis.backend.navbi.service.T04BayarAngsuranService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.backend.util.ZksampleBeanUtils;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 13-03-2020
 */

public class T04BayarAngsuranMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT04BayarAngsuranMain; 

    // Tabs
    protected Tabbox tabbox_T04BayarAngsuranMain;
    protected Tab tabT04BayarAngsuranList;
    protected Tab tabT04BayarAngsuranDetail;
//    protected Tab tabT04BayarAngsuranPrint;
    protected Tabpanel tabPanelT04BayarAngsuranList;
    protected Tabpanel tabPanelT04BayarAngsuranDetail;
//    protected Tabpanel tabPanelT04BayarAngsuranPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T04BayarAngsuran_";
    private ButtonStatusCtrl btnCtrlT04BayarAngsuran;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
 //   protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;
    
    protected Button btnPrintVoucher;
    protected Button btnPrintGiro;
    protected Button btnResetPrintGiro;
	
    
    private CallStoreProcOrFuncService callStoreProcOrFuncService;

    // Tab-Controllers for getting the binders
    private T04BayarAngsuranListCtrl T04BayarAngsuranListCtrl;
    private T04BayarAngsuranDetailCtrl T04BayarAngsuranDetailCtrl;
    
//    private T04BayarAngsuranPrintCtrl T04BayarAngsuranPrintCtrl;

    // Databinding
    private T04BayarAngsuran selectedT04BayarAngsuran;
    private T04BayarAngsuran postedT04BayarAngsuran;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T04BayarAngsuranService t04BayarAngsuranService;

    private P01BiayaTransService p01BiayaTransService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/T04BayarAngsuran/T04BayarAngsuranDetail.zul";
	private String zulPageList = "/WEB-INF/pages/finance/T04BayarAngsuran/T04BayarAngsuranList.zul";
//	private String zulPagePrint = "/WEB-INF/pages/finance/T04BayarAngsuran/T04BayarAngsuranPrint.zul";

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
    public T04BayarAngsuranMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT04BayarAngsuranMain(Event event) throws Exception {
        windowT04BayarAngsuranMain.setContentStyle("padding:0px;");

        btnCtrlT04BayarAngsuran = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT04BayarAngsuran.addButtonOther1(btnPrintGiro, "Print-Giro");
        btnCtrlT04BayarAngsuran.addButtonOther2(btnPrintVoucher, "Print-Voucher");
        btnCtrlT04BayarAngsuran.addButtonOther3(btnResetPrintGiro, "Reset-Print-Giro");
        
       
        btnCtrlT04BayarAngsuran.setInitInquiryButton();

        tabT04BayarAngsuranList.setSelected(true);

        if (tabPanelT04BayarAngsuranList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT04BayarAngsuranList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT04BayarAngsuranList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT04BayarAngsuranList.getFirstChild() != null) {

            tabT04BayarAngsuranList.setSelected(true);
        	btnCtrlT04BayarAngsuran.setInitInquiryButton();
        	getT04BayarAngsuranListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT04BayarAngsuranList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT04BayarAngsuranList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT04BayarAngsuranDetail(Event event) throws IOException {
        if (tabPanelT04BayarAngsuranDetail.getFirstChild() != null) {
        	tabT04BayarAngsuranDetail.setSelected(true);            
        	
        	getT04BayarAngsuranDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT04BayarAngsuranDetailCtrl().doRenderMode("View");   
            
            btnCtrlT04BayarAngsuran.setInitInquiryButton();

            if (getT04BayarAngsuranDetailCtrl().getBinder() != null) {
            	getT04BayarAngsuranDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT04BayarAngsuranDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT04BayarAngsuranDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

//    public void onSelect$tabT04BayarAngsuranPrint(Event event) throws IOException, ParseException {
//    	if (tabPanelT04BayarAngsuranPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT04BayarAngsuranPrint, this, "ModuleMainController", zulPagePrint);
//        }
//    	
//    	// Check if the tabpanel is already loaded
//        if (tabPanelT04BayarAngsuranPrint.getFirstChild() != null) {
//            tabT04BayarAngsuranPrint.setSelected(true);        
//
//            getT04BayarAngsuranPrintCtrl().doReadOnlyMode(true);
//            
//            return;
//        }        
//    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    
    
    
	public void onClick$btnResetPrintGiro(Event event) throws InterruptedException {
		if(getSelectedT04BayarAngsuran() != null) {
			int vPrintCount = 0;
			if(	CommonUtils.isNotEmpty(getSelectedT04BayarAngsuran().getPrintCount())){
				vPrintCount = getSelectedT04BayarAngsuran().getPrintCount();
				if (vPrintCount == 0){
					if (getSelectedT04BayarAngsuran().getReprintBy() == null){
						ZksampleMessageUtils.showErrorMessage("Giro belum pernah di cetak");
					} else {
						ZksampleMessageUtils.showErrorMessage("Print Counter - Giro sudah pernah di Reset oleh : "+
															getSelectedT04BayarAngsuran().getReprintBy() +
															" pada : "+
															CommonUtils.convertDateToString(getSelectedT04BayarAngsuran().getReprintOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
						
					}					
				} else {
			    	try {
			    					    		
			    		getSelectedT04BayarAngsuran().setPrintCount(0);
			    		getSelectedT04BayarAngsuran().setReprintBy(SecurityContextHolder.getContext().getAuthentication().getName());
			    		getSelectedT04BayarAngsuran().setReprintOn(new Date());
			    		
			        	getT04BayarAngsuranService().update(getSelectedT04BayarAngsuran());
			            // refresh the list
			            btnCtrlT04BayarAngsuran.setInitInquiryButton();
			            getT04BayarAngsuranListCtrl().doFillListbox();
		                
		                tabT04BayarAngsuranList.setSelected(true);
		                
		                renderTabAndTitle("Save");
		                
		        		
		        		
			        	
			        } catch (DataAccessException e) {
			        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
			            return;
			        }
				}
			
			}
		}		 
	}    
    
	
	
	

	@SuppressWarnings("unchecked")
	public void onClick$btnPrintGiro(Event event) throws InterruptedException {
		if(getSelectedT04BayarAngsuran() != null) {
			int vPrintCount = 0;
			if(	CommonUtils.isNotEmpty(getSelectedT04BayarAngsuran().getPrintCount())){
				vPrintCount = getSelectedT04BayarAngsuran().getPrintCount();
				if (vPrintCount > 0){
					ZksampleMessageUtils.showErrorMessage("Giro sudah pernah dicetak oleh : "+
															getSelectedT04BayarAngsuran().getPrintBy() +
															" pada : "+
															CommonUtils.convertDateToString(getSelectedT04BayarAngsuran().getPrintOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
				} else {
			    	try {
			    		vPrintCount = vPrintCount+ 1;
			    		
			    		long vT04Id = getSelectedT04BayarAngsuran().getT04Id();
			    		
			    		getSelectedT04BayarAngsuran().setPrintCount(vPrintCount);
			    		getSelectedT04BayarAngsuran().setPrintBy(SecurityContextHolder.getContext().getAuthentication().getName());
			    		getSelectedT04BayarAngsuran().setPrintOn(new Date());
			    		
			        	getT04BayarAngsuranService().update(getSelectedT04BayarAngsuran());
			            // refresh the list
			            btnCtrlT04BayarAngsuran.setInitInquiryButton();
			            getT04BayarAngsuranListCtrl().doFillListbox();
		                
		                tabT04BayarAngsuranList.setSelected(true);
		                
		                renderTabAndTitle("Save");
		                
		                
		                @SuppressWarnings("unused")
		    			String vSync = callStoreProcOrFuncService.callSyncAReport("0206003");
		                
		                
		        		String jasperRpt = "/solusi/hapis/webui/reports/finance/02052_GiroPengeluaranAngsuran.jasper";
		    			
		        		param.put("T04Id",  vT04Id); 		
		        		//param.put("JenisCetak",  "CEK"); 
		        			
		        		new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		        		
			        	
			        } catch (DataAccessException e) {
			        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
			            return;
			        }
				}
			
			}
		}		 
	}
	

	@SuppressWarnings("unchecked")
	public void onClick$btnPrintVoucher(Event event) throws InterruptedException {
		if(getSelectedT04BayarAngsuran() != null) {
			    		
			long vT04Id = getSelectedT04BayarAngsuran().getT04Id();
			
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0206002");
			
	        
			String jasperRpt = "/solusi/hapis/webui/reports/finance/02051_VoucherPengeluaranAngsuran.jasper";
			
			param.put("T04Id",  vT04Id); 	
				
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		        		
		}
	}
	
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT04BayarAngsuranDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT04BayarAngsuranDetail, null));
        } else if (getT04BayarAngsuranDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT04BayarAngsuranDetail, null));
        }

        T04BayarAngsuran anT04BayarAngsuran = new T04BayarAngsuran();

        // Set Default Value Di sini ------------------------------------- start
        anT04BayarAngsuran.setCompany("AUTOJAYA");
        anT04BayarAngsuran.setValutaPo("IDR");
        anT04BayarAngsuran.setInterval(1);
        anT04BayarAngsuran.setIntervalWaktu("BULAN");
        anT04BayarAngsuran.setPrintCount(0);
      
        
        
        
        setSelectedT04BayarAngsuran(anT04BayarAngsuran);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT04BayarAngsuran.setInitFormButton();
        
        // select tab Detail
        tabT04BayarAngsuranDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT04BayarAngsuranDetailCtrl().getBinder() != null) {
        	getT04BayarAngsuranDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT04BayarAngsuranDetailCtrl().doRenderMode("New");
        getT04BayarAngsuranDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
		if(getSelectedT04BayarAngsuran() != null) {
			int vPrintCount = 0;
			if(	CommonUtils.isNotEmpty(getSelectedT04BayarAngsuran().getPrintCount())){
				vPrintCount = getSelectedT04BayarAngsuran().getPrintCount();
				if (vPrintCount > 0){
					ZksampleMessageUtils.showErrorMessage("Tidak bisa di-Edit karena Giro sudah pernah di Cetak");
				} else {

					
			        try {
						postedT04BayarAngsuran =(T04BayarAngsuran) ZksampleBeanUtils.cloneBean(getSelectedT04BayarAngsuran());
						
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
			        
			    	if (tabPanelT04BayarAngsuranDetail.getFirstChild() != null) {
			        	getT04BayarAngsuranDetailCtrl().getBinder().loadAll();
			        }
			        
			        if (getT04BayarAngsuranDetailCtrl() == null) {
			            Events.sendEvent(new Event("onSelect", tabT04BayarAngsuranDetail, null));
			        } else if (getT04BayarAngsuranDetailCtrl().getBinder() == null) {
			            Events.sendEvent(new Event("onSelect", tabT04BayarAngsuranDetail, null));
			        }
			
			        // set button
			        btnCtrlT04BayarAngsuran.setInitFormButton();
			
			        // select tab Detail
			        tabT04BayarAngsuranDetail.setSelected(true);
			        renderTabAndTitle("Edit");
			        
			        state = 2;
			        
			        // set render layar
			        getT04BayarAngsuranDetailCtrl().doRenderCombo();
			        getT04BayarAngsuranDetailCtrl().doRenderMode("Edit");
				}
			}
		}
    }
    

	
/*	public String getDescIntervalWaktu(String company){
		String vResult = "";
		if(company.equals("BULAN") == true){
			vResult = "BULAN";
		} else if(company.equals("TAHUN") == true){
			vResult = "TAHUN";
		} 
		
		return vResult;
	}*/
	
		
	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT04BayarAngsuran() != null) {
	        final T04BayarAngsuran anT04BayarAngsuran = getSelectedT04BayarAngsuran();
	        int vPrintCount = 0;
	        if (anT04BayarAngsuran != null) {
	        	vPrintCount = anT04BayarAngsuran.getPrintCount();
	        	if (vPrintCount > 0){
					ZksampleMessageUtils.showErrorMessage("Tidak bisa di-Delete karena Giro sudah pernah di Cetak");
				} else {
	        	
		            // Show a confirm box
		        	String deleteRecord = anT04BayarAngsuran.getSuppCode() +" - "+anT04BayarAngsuran.getNoPo();
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
		                        getT04BayarAngsuranService().delete(anT04BayarAngsuran);
		                        setSelectedT04BayarAngsuran(null);
		                        
		                        // refresh the list
		                        getT04BayarAngsuranListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
		
		        }
		        
		        btnCtrlT04BayarAngsuran.setInitInquiryButton();
		        setSelectedT04BayarAngsuran(null);
		        
		        // refresh the list
		        getT04BayarAngsuranListCtrl().doFillListbox();
		        tabT04BayarAngsuranList.setSelected(true);
	        }
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT04BayarAngsuranDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT04BayarAngsuranDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT04BayarAngsuranService().save(getSelectedT04BayarAngsuran());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT04BayarAngsuranService().update(getSelectedT04BayarAngsuran());
	            // refresh the list
	            btnCtrlT04BayarAngsuran.setInitInquiryButton();
	            getT04BayarAngsuranListCtrl().doFillListbox();
                
                tabT04BayarAngsuranList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT04BayarAngsuran.setInitInquiryButton();
        
        tabT04BayarAngsuranList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT04BayarAngsuranListCtrl().doFillListbox();
        }
        
    	if(postedT04BayarAngsuran != null){
           	setSelectedT04BayarAngsuran(postedT04BayarAngsuran);
           	
           	//Databinding LOV

           	
    	}
    	if(getT04BayarAngsuranDetailCtrl().binder != null){
    		getT04BayarAngsuranDetailCtrl().binder.loadAll();
    	} 
    }
    
//    // Saat Button Listing di klik
//    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
//    	
//    	if (getT04BayarAngsuranPrintCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabT04BayarAngsuranPrint, null));
//        }
//
//        if (tabPanelT04BayarAngsuranPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT04BayarAngsuranPrint, this, "ModuleMainController", zulPagePrint);
//        }
//        
//        getT04BayarAngsuranPrintCtrl().doReadOnlyMode(false);
//        
//        btnCtrlT04BayarAngsuran.setInitPrintButton();
//        tabT04BayarAngsuranPrint.setSelected(true);
//
//    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
//    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listT04BayarAngsuran.jasper";
    	
//    	new JReportGeneratorWindow(getT04BayarAngsuranPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT04BayarAngsuranListCtrl() != null) {
        	getT04BayarAngsuranListCtrl().getSearchData();        	
        	tabT04BayarAngsuranList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT04BayarAngsuranListCtrl() != null) {
        	getT04BayarAngsuranListCtrl().clearSearchBox();
        	getT04BayarAngsuranListCtrl().getSearchData();        	
        	tabT04BayarAngsuranList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT04BayarAngsuranDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT04BayarAngsuranList.setDisabled(true);
//	        tabT04BayarAngsuranPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT04BayarAngsuranDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT04BayarAngsuranList.setDisabled(false);
//	        tabT04BayarAngsuranPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT04BayarAngsuran(T04BayarAngsuran selectedT04BayarAngsuran) {
        this.selectedT04BayarAngsuran = selectedT04BayarAngsuran;
    }

    public T04BayarAngsuran getSelectedT04BayarAngsuran() {
        return this.selectedT04BayarAngsuran;
    }


	public void setT04BayarAngsuranService(T04BayarAngsuranService t04BayarAngsuranService) {
        this.t04BayarAngsuranService = t04BayarAngsuranService;
    }

    public T04BayarAngsuranService getT04BayarAngsuranService() {
        return this.t04BayarAngsuranService;
    }

    public void setT04BayarAngsuranListCtrl(T04BayarAngsuranListCtrl T04BayarAngsuranListCtrl) {
        this.T04BayarAngsuranListCtrl = T04BayarAngsuranListCtrl;
    }

    public T04BayarAngsuranListCtrl getT04BayarAngsuranListCtrl() {
        return this.T04BayarAngsuranListCtrl;
    }

    public void setT04BayarAngsuranDetailCtrl(T04BayarAngsuranDetailCtrl T04BayarAngsuranDetailCtrl) {
        this.T04BayarAngsuranDetailCtrl = T04BayarAngsuranDetailCtrl;
    }

    public T04BayarAngsuranDetailCtrl getT04BayarAngsuranDetailCtrl() {
        return this.T04BayarAngsuranDetailCtrl;
    }


	public void setPostedT04BayarAngsuran(T04BayarAngsuran postedT04BayarAngsuran) {
		this.postedT04BayarAngsuran = postedT04BayarAngsuran;
	}

	public T04BayarAngsuran getPostedT04BayarAngsuran() {
		return postedT04BayarAngsuran;
	}

	public P01BiayaTransService getP01BiayaTransService() {
		return p01BiayaTransService;
	}

	public void setP01BiayaTransService(P01BiayaTransService p01BiayaTransService) {
		this.p01BiayaTransService = p01BiayaTransService;
	}

	
}
