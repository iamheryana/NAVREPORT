package solusi.hapis.webui.finance.T03CetakSlip;

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

import solusi.hapis.backend.navbi.model.P01BiayaTrans;
import solusi.hapis.backend.navbi.model.T03CetakSlip;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.P01BiayaTransService;
import solusi.hapis.backend.navbi.service.T03CetakSlipService;
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
 * Date: 23-01-2020
 */

public class T03CetakSlipMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT03CetakSlipMain; 

    // Tabs
    protected Tabbox tabbox_T03CetakSlipMain;
    protected Tab tabT03CetakSlipList;
    protected Tab tabT03CetakSlipDetail;
//    protected Tab tabT03CetakSlipPrint;
    protected Tabpanel tabPanelT03CetakSlipList;
    protected Tabpanel tabPanelT03CetakSlipDetail;
//    protected Tabpanel tabPanelT03CetakSlipPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T03CetakSlip_";
    private ButtonStatusCtrl btnCtrlT03CetakSlip;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
 //   protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;
    
    protected Button btnPrintCheque;
    protected Button btnPrintChequeMandiri;
    
    protected Button btnPrintSlip;
    protected Button btnPrintSlipMandiri;
    
    
    protected Button btnResetPrintCheque;
    
    private CallStoreProcOrFuncService callStoreProcOrFuncService;
	

    // Tab-Controllers for getting the binders
    private T03CetakSlipListCtrl T03CetakSlipListCtrl;
    private T03CetakSlipDetailCtrl T03CetakSlipDetailCtrl;
    private T03CetakSlipPrintCtrl T03CetakSlipPrintCtrl;

    // Databinding
    private T03CetakSlip selectedT03CetakSlip;
    private T03CetakSlip postedT03CetakSlip;
    
    
    private P01BiayaTrans p01BiayaTrans ;
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T03CetakSlipService t03CetakSlipService;

    private P01BiayaTransService p01BiayaTransService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/T03CetakSlip/T03CetakSlipDetail.zul";
	private String zulPageList = "/WEB-INF/pages/finance/T03CetakSlip/T03CetakSlipList.zul";
//	private String zulPagePrint = "/WEB-INF/pages/finance/T03CetakSlip/T03CetakSlipPrint.zul";

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
    public T03CetakSlipMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT03CetakSlipMain(Event event) throws Exception {
        windowT03CetakSlipMain.setContentStyle("padding:0px;");

        btnCtrlT03CetakSlip = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT03CetakSlip.addButtonOther1(btnPrintCheque, "Print-Cheque-BCA");
        btnCtrlT03CetakSlip.addButtonOther2(btnPrintSlip, "Print-Slip-BCA");
        
        btnCtrlT03CetakSlip.addButtonOther1(btnPrintChequeMandiri, "Print-Cheque-Mandiri");
        btnCtrlT03CetakSlip.addButtonOther2(btnPrintSlipMandiri, "Print-Slip-Mandiri");
        
        
        btnCtrlT03CetakSlip.addButtonOther3(btnResetPrintCheque, "Reset-Print-Cheque");
        
       
        btnCtrlT03CetakSlip.setInitInquiryButton();

        tabT03CetakSlipList.setSelected(true);

        if (tabPanelT03CetakSlipList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT03CetakSlipList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT03CetakSlipList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT03CetakSlipList.getFirstChild() != null) {

            tabT03CetakSlipList.setSelected(true);
        	btnCtrlT03CetakSlip.setInitInquiryButton();
        	getT03CetakSlipListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT03CetakSlipList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT03CetakSlipList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT03CetakSlipDetail(Event event) throws IOException {
        if (tabPanelT03CetakSlipDetail.getFirstChild() != null) {
        	tabT03CetakSlipDetail.setSelected(true);            
        	
        	getT03CetakSlipDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT03CetakSlipDetailCtrl().doRenderMode("View");   
            
            btnCtrlT03CetakSlip.setInitInquiryButton();

            if (getT03CetakSlipDetailCtrl().getBinder() != null) {
            	getT03CetakSlipDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT03CetakSlipDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT03CetakSlipDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

//    public void onSelect$tabT03CetakSlipPrint(Event event) throws IOException, ParseException {
//    	if (tabPanelT03CetakSlipPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT03CetakSlipPrint, this, "ModuleMainController", zulPagePrint);
//        }
//    	
//    	// Check if the tabpanel is already loaded
//        if (tabPanelT03CetakSlipPrint.getFirstChild() != null) {
//            tabT03CetakSlipPrint.setSelected(true);        
//
//            getT03CetakSlipPrintCtrl().doReadOnlyMode(true);
//            
//            return;
//        }        
//    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    
    
    
	public void onClick$btnResetPrintCheque(Event event) throws InterruptedException {
		if(getSelectedT03CetakSlip() != null) {
			int vPrintCount = 0;
			if(	CommonUtils.isNotEmpty(getSelectedT03CetakSlip().getPrintCount())){
				vPrintCount = getSelectedT03CetakSlip().getPrintCount();
				if (vPrintCount == 0){
					if (getSelectedT03CetakSlip().getReprintBy() == null){
						ZksampleMessageUtils.showErrorMessage("Cheque belum pernah di cetak");
					} else {
						ZksampleMessageUtils.showErrorMessage("Print Counter - Cheque sudah pernah di Reset oleh : "+
															getSelectedT03CetakSlip().getReprintBy() +
															" pada : "+
															CommonUtils.convertDateToString(getSelectedT03CetakSlip().getReprintOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
						
					}					
				} else {
			    	try {
			    					    		
			    		getSelectedT03CetakSlip().setPrintCount(0);
			    		getSelectedT03CetakSlip().setReprintBy(SecurityContextHolder.getContext().getAuthentication().getName());
			    		getSelectedT03CetakSlip().setReprintOn(new Date());
			    		
			        	getT03CetakSlipService().update(getSelectedT03CetakSlip());
			            // refresh the list
			            btnCtrlT03CetakSlip.setInitInquiryButton();
			            getT03CetakSlipListCtrl().doFillListbox();
		                
		                tabT03CetakSlipList.setSelected(true);
		                
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
	public void onClick$btnPrintCheque(Event event) throws InterruptedException {
		if(getSelectedT03CetakSlip() != null) {
			int vPrintCount = 0;
			if(	CommonUtils.isNotEmpty(getSelectedT03CetakSlip().getPrintCount())){
				vPrintCount = getSelectedT03CetakSlip().getPrintCount();
				if (vPrintCount > 0){
					ZksampleMessageUtils.showErrorMessage("Cheque sudah pernah dicetak oleh : "+
															getSelectedT03CetakSlip().getPrintBy() +
															" pada : "+
															CommonUtils.convertDateToString(getSelectedT03CetakSlip().getPrintOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
				} else {
			    	try {
			    		vPrintCount = vPrintCount+ 1;
			    		
			    		long vT03CsId = getSelectedT03CetakSlip().getT03CsId();
			    		
			    		getSelectedT03CetakSlip().setPrintCount(vPrintCount);
			    		getSelectedT03CetakSlip().setPrintBy(SecurityContextHolder.getContext().getAuthentication().getName());
			    		getSelectedT03CetakSlip().setPrintOn(new Date());
			    		
			        	getT03CetakSlipService().update(getSelectedT03CetakSlip());
			            // refresh the list
			            btnCtrlT03CetakSlip.setInitInquiryButton();
			            getT03CetakSlipListCtrl().doFillListbox();
		                
		                tabT03CetakSlipList.setSelected(true);
		                
		                renderTabAndTitle("Save");
		                
		                @SuppressWarnings("unused")
		        		String vSync = callStoreProcOrFuncService.callSyncAReport("0206001");
		                
		                
		        		String jasperRpt = "/solusi/hapis/webui/reports/finance/02050_SlipTransferBCAAntarBank.jasper";
		    			
		        		param.put("T03CsId",  vT03CsId); 		
		        		param.put("JenisCetak",  "CEK"); 
		        			
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
	public void onClick$btnPrintChequeMandiri(Event event) throws InterruptedException {
		if(getSelectedT03CetakSlip() != null) {
			int vPrintCount = 0;
			if(	CommonUtils.isNotEmpty(getSelectedT03CetakSlip().getPrintCount())){
				vPrintCount = getSelectedT03CetakSlip().getPrintCount();
				if (vPrintCount > 0){
					ZksampleMessageUtils.showErrorMessage("Cheque sudah pernah dicetak oleh : "+
															getSelectedT03CetakSlip().getPrintBy() +
															" pada : "+
															CommonUtils.convertDateToString(getSelectedT03CetakSlip().getPrintOn(), CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
				} else {
			    	try {
			    		vPrintCount = vPrintCount+ 1;
			    		
			    		long vT03CsId = getSelectedT03CetakSlip().getT03CsId();
			    		
			    		getSelectedT03CetakSlip().setPrintCount(vPrintCount);
			    		getSelectedT03CetakSlip().setPrintBy(SecurityContextHolder.getContext().getAuthentication().getName());
			    		getSelectedT03CetakSlip().setPrintOn(new Date());
			    		
			        	getT03CetakSlipService().update(getSelectedT03CetakSlip());
			            // refresh the list
			            btnCtrlT03CetakSlip.setInitInquiryButton();
			            getT03CetakSlipListCtrl().doFillListbox();
		                
		                tabT03CetakSlipList.setSelected(true);
		                
		                renderTabAndTitle("Save");
		                
		                @SuppressWarnings("unused")
		         		String vSync = callStoreProcOrFuncService.callSyncAReport("0206001");
		                
		        		String jasperRpt = "/solusi/hapis/webui/reports/finance/02054_SlipTransferMandiri.jasper";
		    			
		        		param.put("T03CsId",  vT03CsId); 		
		        		param.put("JenisCetak",  "CEK"); 
		        			
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
	public void onClick$btnPrintSlip(Event event) throws InterruptedException {
		if(getSelectedT03CetakSlip() != null) {
			
			    		
			long vT03CsId = getSelectedT03CetakSlip().getT03CsId();
			
			@SuppressWarnings("unused")
     		String vSync = callStoreProcOrFuncService.callSyncAReport("0206001");
			 
			String jasperRpt = "/solusi/hapis/webui/reports/finance/02050_SlipTransferBCAAntarBank.jasper";
			
			param.put("T03CsId",  vT03CsId); 		
			param.put("JenisCetak",  "SLIP"); 
				
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		        		
		}
	}
    
	@SuppressWarnings("unchecked")
	public void onClick$btnPrintSlipMandiri(Event event) throws InterruptedException {
		if(getSelectedT03CetakSlip() != null) {
			
			    		
			long vT03CsId = getSelectedT03CetakSlip().getT03CsId();
			
			@SuppressWarnings("unused")
     		String vSync = callStoreProcOrFuncService.callSyncAReport("0206001");
			
			String jasperRpt = "/solusi/hapis/webui/reports/finance/02054_SlipTransferMandiri.jasper";
			
			param.put("T03CsId",  vT03CsId); 		
			param.put("JenisCetak",  "SLIP"); 
				
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		        		
		}
	}
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT03CetakSlipDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT03CetakSlipDetail, null));
        } else if (getT03CetakSlipDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT03CetakSlipDetail, null));
        }

        T03CetakSlip anT03CetakSlip = new T03CetakSlip();

        // Set Default Value Di sini ------------------------------------- start

      
        if (p01BiayaTransService != null){
    		p01BiayaTrans = p01BiayaTransService.getP01BiayaTransByKode("01");   
    		
    		
    		anT03CetakSlip.setBerita(p01BiayaTrans.getBerita());
    	}
        
        
        anT03CetakSlip.setJenisTrans("VR-NAV");
        anT03CetakSlip.setCompany("AUTOJAYA");
        anT03CetakSlip.setPrintCount(0);
        
        
        
        
        setSelectedT03CetakSlip(anT03CetakSlip);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT03CetakSlip.setInitFormButton();
        
        // select tab Detail
        tabT03CetakSlipDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT03CetakSlipDetailCtrl().getBinder() != null) {
        	getT03CetakSlipDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT03CetakSlipDetailCtrl().doRenderMode("New");
        getT03CetakSlipDetailCtrl().doRenderCombo();
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
		if(getSelectedT03CetakSlip() != null) {
			int vPrintCount = 0;
			if(	CommonUtils.isNotEmpty(getSelectedT03CetakSlip().getPrintCount())){
				vPrintCount = getSelectedT03CetakSlip().getPrintCount();
				if (vPrintCount > 0){
					ZksampleMessageUtils.showErrorMessage("Tidak bisa di-Edit karena Cheque sudah pernah di Cetak");
				} else {

					
			        try {
						postedT03CetakSlip =(T03CetakSlip) ZksampleBeanUtils.cloneBean(getSelectedT03CetakSlip());
						
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
			        
			    	if (tabPanelT03CetakSlipDetail.getFirstChild() != null) {
			        	getT03CetakSlipDetailCtrl().getBinder().loadAll();
			        }
			        
			        if (getT03CetakSlipDetailCtrl() == null) {
			            Events.sendEvent(new Event("onSelect", tabT03CetakSlipDetail, null));
			        } else if (getT03CetakSlipDetailCtrl().getBinder() == null) {
			            Events.sendEvent(new Event("onSelect", tabT03CetakSlipDetail, null));
			        }
			
			        // set button
			        btnCtrlT03CetakSlip.setInitFormButton();
			
			        // select tab Detail
			        tabT03CetakSlipDetail.setSelected(true);
			        renderTabAndTitle("Edit");
			        
			        state = 2;
			        
			        // set render layar
			        getT03CetakSlipDetailCtrl().doRenderCombo();
			        getT03CetakSlipDetailCtrl().doRenderMode("Edit");
				}
			}
		}
    }
    
	public String getDescJenisTrans(String jenisTrans){
		String vResult = "";
		if(jenisTrans.equals("VR-NAV") == true){
			vResult = "Vendor NAV";
		} else if(jenisTrans.equals("VR-NNAV") == true){
			vResult = "Vendor NON-NAV";
		} else if(jenisTrans.equals("CASH") == true){
			vResult = "No Slip / Cheque Only";
		}
		
		return vResult;
	}
	
	public String getDescCompany(String company){
		String vResult = "";
		if(company.equals("AUTOJAYA") == true){
			vResult = "Autojaya";
		} else if(company.equals("SOLUSI") == true){
			vResult = "Solusi";
		} 
		
		return vResult;
	}
	
		
	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT03CetakSlip() != null) {
	        final T03CetakSlip anT03CetakSlip = getSelectedT03CetakSlip();
	        int vPrintCount = 0;
	        if (anT03CetakSlip != null) {
	        	vPrintCount = anT03CetakSlip.getPrintCount();
	        	if (vPrintCount > 0){
					ZksampleMessageUtils.showErrorMessage("Tidak bisa di-Delete karena Cheque sudah pernah di Cetak");
				} else {
	        	
		            // Show a confirm box
		        	String deleteRecord = getDescCompany(anT03CetakSlip.getCompany()) +" - "+anT03CetakSlip.getNoVoucher();
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
		                        getT03CetakSlipService().delete(anT03CetakSlip);
		                        setSelectedT03CetakSlip(null);
		                        
		                        // refresh the list
		                        getT03CetakSlipListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
		
		        }
		        
		        btnCtrlT03CetakSlip.setInitInquiryButton();
		        setSelectedT03CetakSlip(null);
		        
		        // refresh the list
		        getT03CetakSlipListCtrl().doFillListbox();
		        tabT03CetakSlipList.setSelected(true);
	        }
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT03CetakSlipDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT03CetakSlipDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT03CetakSlipService().save(getSelectedT03CetakSlip());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT03CetakSlipService().update(getSelectedT03CetakSlip());
	            // refresh the list
	            btnCtrlT03CetakSlip.setInitInquiryButton();
	            getT03CetakSlipListCtrl().doFillListbox();
                
                tabT03CetakSlipList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT03CetakSlip.setInitInquiryButton();
        
        tabT03CetakSlipList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT03CetakSlipListCtrl().doFillListbox();
        }
        
    	if(postedT03CetakSlip != null){
           	setSelectedT03CetakSlip(postedT03CetakSlip);
           	
           	//Databinding LOV

           	
    	}
    	if(getT03CetakSlipDetailCtrl().binder != null){
    		getT03CetakSlipDetailCtrl().binder.loadAll();
    	} 
    }
    
//    // Saat Button Listing di klik
//    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
//    	
//    	if (getT03CetakSlipPrintCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabT03CetakSlipPrint, null));
//        }
//
//        if (tabPanelT03CetakSlipPrint != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT03CetakSlipPrint, this, "ModuleMainController", zulPagePrint);
//        }
//        
//        getT03CetakSlipPrintCtrl().doReadOnlyMode(false);
//        
//        btnCtrlT03CetakSlip.setInitPrintButton();
//        tabT03CetakSlipPrint.setSelected(true);
//
//    }
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
//    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listT03CetakSlip.jasper";
    	
//    	new JReportGeneratorWindow(getT03CetakSlipPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT03CetakSlipListCtrl() != null) {
        	getT03CetakSlipListCtrl().getSearchData();        	
        	tabT03CetakSlipList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT03CetakSlipListCtrl() != null) {
        	getT03CetakSlipListCtrl().clearSearchBox();
        	getT03CetakSlipListCtrl().getSearchData();        	
        	tabT03CetakSlipList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT03CetakSlipDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT03CetakSlipList.setDisabled(true);
//	        tabT03CetakSlipPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT03CetakSlipDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT03CetakSlipList.setDisabled(false);
//	        tabT03CetakSlipPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT03CetakSlip(T03CetakSlip selectedT03CetakSlip) {
        this.selectedT03CetakSlip = selectedT03CetakSlip;
    }

    public T03CetakSlip getSelectedT03CetakSlip() {
        return this.selectedT03CetakSlip;
    }


	public void setT03CetakSlipService(T03CetakSlipService t03CetakSlipService) {
        this.t03CetakSlipService = t03CetakSlipService;
    }

    public T03CetakSlipService getT03CetakSlipService() {
        return this.t03CetakSlipService;
    }

    public void setT03CetakSlipListCtrl(T03CetakSlipListCtrl T03CetakSlipListCtrl) {
        this.T03CetakSlipListCtrl = T03CetakSlipListCtrl;
    }

    public T03CetakSlipListCtrl getT03CetakSlipListCtrl() {
        return this.T03CetakSlipListCtrl;
    }

    public void setT03CetakSlipDetailCtrl(T03CetakSlipDetailCtrl T03CetakSlipDetailCtrl) {
        this.T03CetakSlipDetailCtrl = T03CetakSlipDetailCtrl;
    }

    public T03CetakSlipDetailCtrl getT03CetakSlipDetailCtrl() {
        return this.T03CetakSlipDetailCtrl;
    }

	public void setT03CetakSlipPrintCtrl(T03CetakSlipPrintCtrl T03CetakSlipPrintCtrl) {
		this.T03CetakSlipPrintCtrl = T03CetakSlipPrintCtrl;
	}

	public T03CetakSlipPrintCtrl getT03CetakSlipPrintCtrl() {
		return this.T03CetakSlipPrintCtrl;
	}

	public void setPostedT03CetakSlip(T03CetakSlip postedT03CetakSlip) {
		this.postedT03CetakSlip = postedT03CetakSlip;
	}

	public T03CetakSlip getPostedT03CetakSlip() {
		return postedT03CetakSlip;
	}

	public P01BiayaTransService getP01BiayaTransService() {
		return p01BiayaTransService;
	}

	public void setP01BiayaTransService(P01BiayaTransService p01BiayaTransService) {
		this.p01BiayaTransService = p01BiayaTransService;
	}

	
}
