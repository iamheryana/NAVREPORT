package solusi.hapis.webui.sales.Costing.T29Costing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.media.Media;
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

import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.backend.navbi.service.T29CostingHService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.general.DisplayNoDocumentWindow;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 24-07-2024
 */

public class T29CostingMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT29CostingMain; 

    // Tabs
    protected Tabbox tabbox_T29CostingMain;
    protected Tab tabT29CostingList;
    protected Tab tabT29CostingDetail;
    protected Tabpanel tabPanelT29CostingList;
    protected Tabpanel tabPanelT29CostingDetail;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T29Costing_";
    private ButtonStatusCtrl btnCtrlT29Costing;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;
    
    protected Button btnDownloadCosting;
    protected Button btnSubmitToSAO;
    protected Button btnSubmitToLogistic;
    protected Button btnBackToSales1;
    protected Button btnSubmitToFinance;
    protected Button btnBackToSales2;
    protected Button btnSubmitToSM;
    protected Button btnBackToLogistic;
    protected Button btnSubmitToFinance2;
    	

    // Tab-Controllers for getting the binders
    private T29CostingListCtrl T29CostingListCtrl;
    private T29CostingDetailCtrl T29CostingDetailCtrl;


    // Databinding
    private T29CostingH selectedT29Costing;
    private T29CostingH postedT29Costing;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T29CostingHService T29CostingHService;
    private SelectQueryService selectQueryService;
    
    
    private String PATH_FILE_UPLOAD   = new PathReport().getPathFileCosting();
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/sales/Costing/T29Costing/T29CostingDetail.zul";
	private String zulPageList = "/WEB-INF/pages/sales/Costing/T29Costing/T29CostingList.zul";

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
    public T29CostingMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT29CostingMain(Event event) throws Exception {
        windowT29CostingMain.setContentStyle("padding:0px;");

        btnCtrlT29Costing = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT29Costing.setInitInquiryButton();

        renderButtonActionbyRole(true);        
        btnDownloadCosting.setVisible(true);
        
        tabT29CostingList.setSelected(true);

        if (tabPanelT29CostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT29CostingList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT29CostingList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT29CostingList.getFirstChild() != null) {

    		//RESTOREVALUE
    		//doResetToInitValues();
    		
            tabT29CostingList.setSelected(true);
        	btnCtrlT29Costing.setInitInquiryButton();
        	
        	renderButtonActionbyRole(true);
        	btnDownloadCosting.setVisible(true);
        	
        	getT29CostingListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT29CostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT29CostingList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT29CostingDetail(Event event) throws IOException {
        if (tabPanelT29CostingDetail.getFirstChild() != null) {
        	tabT29CostingDetail.setSelected(true);            
        	
        	//RESTOREVALUE
    		//doResetToInitValues();
    		getT29CostingDetailCtrl().doRenderCombo();
        	
        	// Render Inisialisasi posisi awal
            getT29CostingDetailCtrl().doRenderMode("View", "NA");   
            
            btnCtrlT29Costing.setInitInquiryButton();
            renderButtonActionbyRole(true);
            btnDownloadCosting.setVisible(true);
            
            
            if (getT29CostingDetailCtrl().getBinder() != null) {
            	getT29CostingDetailCtrl().getBinder().loadAll();  
            }
            
            getT29CostingDetailCtrl().displayDetail(
            		getT29CostingListCtrl().getList_T30CostingDHw3psList(), 
            		getT29CostingListCtrl().getList_T31CostingDAcspsList(), 
            		getT29CostingListCtrl().getList_T32CostingDOwnswList(), 
            		getT29CostingListCtrl().getList_T33CostingDOtherList(),
            		getT29CostingListCtrl().getList_T34CostingDPaymentList()
            		);
            
			
			return;
		}

		if (tabPanelT29CostingDetail != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelT29CostingDetail,
					this, "ModuleMainController", zulPageDetail);
		}
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT29CostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT29CostingDetail, null));
        } else if (getT29CostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT29CostingDetail, null));
        }

        T29CostingH anT29CostingH = new T29CostingH();

        // Set Default Value Di sini ------------------------------------- start
       
        anT29CostingH.setCompany("AUTOJAYA");
        anT29CostingH.setTglCosting(new Date());
        //anT29CostingH.setSalesman(SecurityContextHolder.getContext().getAuthentication().getName());
        anT29CostingH.setSalesman("HRY");
        
        anT29CostingH.setNoCosting(String.valueOf(System.currentTimeMillis()));
        
        anT29CostingH.setFlagStatus("SALES");
        anT29CostingH.setFlagInvoice("N");
        anT29CostingH.setFlagLunas("N");
        
		anT29CostingH.setSalesHw3ps(new BigDecimal(0));
		anT29CostingH.setSalesAcsps(new BigDecimal(0));
		anT29CostingH.setSalesOwnsw(new BigDecimal(0));
		anT29CostingH.setCogsHw3ps(new BigDecimal(0));
		anT29CostingH.setCogsOthers(new BigDecimal(0));
		
		anT29CostingH.setTotalSales(new BigDecimal(0));
		anT29CostingH.setTotalCogs(new BigDecimal(0));
		anT29CostingH.setMarginPcn(new BigDecimal(0));
		
		anT29CostingH.setIncentiveFormulaHw3ps(new BigDecimal(0));
		anT29CostingH.setIncentiveFormulaAcsps(new BigDecimal(0));
		anT29CostingH.setIncentiveFormulaOwnsw(new BigDecimal(0));
		anT29CostingH.setIncentiveApproveHw3ps(new BigDecimal(0));
		anT29CostingH.setIncentiveApproveAcsps(new BigDecimal(0));
		anT29CostingH.setIncentiveApproveOwnsw(new BigDecimal(0));
		
		anT29CostingH.setIncentiveNonsalesHw3ps(new BigDecimal(0));
		anT29CostingH.setIncentiveNonsalesAcsps(new BigDecimal(0));
		anT29CostingH.setIncentiveNonsalesOwnsw(new BigDecimal(0));		
		anT29CostingH.setAmountNonsalesHw3ps(new BigDecimal(0));
		anT29CostingH.setAmountNonsalesAcsps(new BigDecimal(0));
		anT29CostingH.setAmountNonsalesOwnsw(new BigDecimal(0));	
		
		anT29CostingH.setIncentiveKomisiHw3ps(new BigDecimal(0));
		anT29CostingH.setIncentiveKomisiAcsps(new BigDecimal(0));
		anT29CostingH.setIncentiveKomisiOwnsw(new BigDecimal(0));	
		anT29CostingH.setAmountKomisiHw3ps(new BigDecimal(0));
		anT29CostingH.setAmountKomisiAcsps(new BigDecimal(0));
		anT29CostingH.setAmountKomisiOwnsw(new BigDecimal(0));	
		
		anT29CostingH.setIncentiveSbonusHw3ps(new BigDecimal(0));
		anT29CostingH.setIncentiveSbonusAcsps(new BigDecimal(0));
		anT29CostingH.setIncentiveSbonusOwnsw(new BigDecimal(0));	
		anT29CostingH.setAmountSbonusHw3ps(new BigDecimal(0));
		anT29CostingH.setAmountSbonusAcsps(new BigDecimal(0));
		anT29CostingH.setAmountSbonusOwnsw(new BigDecimal(0));	
		
		anT29CostingH.setAmountKomisi(new BigDecimal(0));
		anT29CostingH.setAmountSbonus(new BigDecimal(0));
		anT29CostingH.setAmountSalesTqs(new BigDecimal(0));
		anT29CostingH.setAmountNonsales(new BigDecimal(0));
    	
		anT29CostingH.setAmountTqsHw3ps(new BigDecimal(0));
		anT29CostingH.setAmountTqsAcsps(new BigDecimal(0));
		anT29CostingH.setAmountTqsOwnsw(new BigDecimal(0));
		
		
        setSelectedT29Costing(anT29CostingH);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT29Costing.setInitFormButton();
        renderButtonActionbyRole(false);
        btnDownloadCosting.setVisible(false);
        
        // select tab Detail
        tabT29CostingDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT29CostingDetailCtrl().getBinder() != null) {
        	getT29CostingDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT29CostingDetailCtrl().doRenderMode("New", "NA");
        
        getT29CostingDetailCtrl().displayDetail(null, null, null, null, null);
        getT29CostingDetailCtrl().doRenderCombo();
    }
    
//    public void doStoreInitValues() {
//
//        if (getSelectedT29Costing() != null) {
//
//            try {
//            	
//                setPostedT29Costing((T29Costing) ZksampleBeanUtils.cloneBean(getSelectedT29Costing()));
//                
//                     
//            } catch (final IllegalAccessException e) {
//                throw new RuntimeException(e);
//            } catch (final InstantiationException e) {
//                throw new RuntimeException(e);
//            } catch (final InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (final NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
//        try {
//			postedT29Costing =(T29Costing) ZksampleBeanUtils.cloneBean(getSelectedT29Costing());
//			
//			//Databinding LOV
//			
//        } catch (final IllegalAccessException e) {
//            throw new RuntimeException(e);
//        } catch (final InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (final InvocationTargetException e) {
//            throw new RuntimeException(e);
//        } catch (final NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
        
		if(getSelectedT29Costing() != null){
	    	if (tabPanelT29CostingDetail.getFirstChild() != null) {
	        	getT29CostingDetailCtrl().getBinder().loadAll();
	        }
	        
	        if (getT29CostingDetailCtrl() == null) {
	            Events.sendEvent(new Event("onSelect", tabT29CostingDetail, null));
	        } else if (getT29CostingDetailCtrl().getBinder() == null) {
	            Events.sendEvent(new Event("onSelect", tabT29CostingDetail, null));
	        }
	
	        //doStoreInitValues();
	        
	        // set button
	        btnCtrlT29Costing.setInitFormButton();
	        renderButtonActionbyRole(false);
	        btnDownloadCosting.setVisible(true);
	
	        // select tab Detail
	        tabT29CostingDetail.setSelected(true);
	        renderTabAndTitle("Edit");
	        
	        state = 2;
	        
	        // set render layar
	        getT29CostingDetailCtrl().doRenderCombo();
	        getT29CostingDetailCtrl().doRenderMode("Edit", getSelectedT29Costing().getFlagStatus());
	        
	        getT29CostingDetailCtrl().displayDetail(
	        		getT29CostingListCtrl().getList_T30CostingDHw3psList(), 
	        		getT29CostingListCtrl().getList_T31CostingDAcspsList(), 
	        		getT29CostingListCtrl().getList_T32CostingDOwnswList(), 
	        		getT29CostingListCtrl().getList_T33CostingDOtherList(),
	        		getT29CostingListCtrl().getList_T34CostingDPaymentList());
        
  
		}
	}
    
//    public void doResetToInitValues() {
//
//        if (getPostedT29Costing() != null) {
//
//            try {
//            	setSelectedT29Costing((T29Costing) ZksampleBeanUtils.cloneBean(getPostedT29Costing()));
//            	
//            	
//                windowT29CostingMain.invalidate();
//
//            } catch (final IllegalAccessException e) {
//                throw new RuntimeException(e);
//            } catch (final InstantiationException e) {
//                throw new RuntimeException(e);
//            } catch (final InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (final NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT29Costing() != null) {
	        final T29CostingH anT29CostingH = getSelectedT29Costing();
	        if (anT29CostingH != null) {
	
	            // Show a confirm box
	        	String deleteRecord = anT29CostingH.getNoCosting() ;
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
	                		deleteFile(anT29CostingH.getFileBso());
	                		deleteFile(anT29CostingH.getFileInfoPrice());
	                		deleteFile(anT29CostingH.getFilePoCustomer());
	                		
	                        getT29CostingHService().delete(anT29CostingH);
	                        setSelectedT29Costing(null);
	                        
	                        // refresh the list
	                        getT29CostingListCtrl().doFillListbox();
	                    } catch (DataAccessException e) {
	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
	
	        }
	        
	        btnCtrlT29Costing.setInitInquiryButton();
	        renderButtonActionbyRole(true);
	        btnDownloadCosting.setVisible(true);
	        
	        setSelectedT29Costing(null);
	        
	        // refresh the list
	        getT29CostingListCtrl().doFillListbox();
	        tabT29CostingList.setSelected(true);
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT29CostingDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT29CostingDetailCtrl().getBinder().saveAll();
    	
    	try {
    		
    		
    		
    		
    		
        	if(state == 1) {

        		//getT29CostingHService().insert(getSelectedT29Costing());
        		//onClick$btnNew(event);
        		
        		if(getSelectedT29Costing().getUploadBSO() != null){		
        			getSelectedT29Costing().setFileBso(getSelectedT29Costing().getNoCosting() + "_BSO." + getSelectedT29Costing().getUploadBSO().getFormat());
        		}
        		
        		if(getSelectedT29Costing().getUploadInfoPrice() != null){		
        			getSelectedT29Costing().setFileInfoPrice(getSelectedT29Costing().getNoCosting() + "_PRICE." + getSelectedT29Costing().getUploadInfoPrice().getFormat());
        		}
        		
        		if(getSelectedT29Costing().getUploadPoCustomer() != null){		
        			getSelectedT29Costing().setFilePoCustomer(getSelectedT29Costing().getNoCosting() + "_PO." + getSelectedT29Costing().getUploadPoCustomer().getFormat());
        		}
        		        		
        		
        		String vNoCosting =  getT29CostingHService().insert(getSelectedT29Costing());
        		
        		if(getSelectedT29Costing().getUploadBSO() != null){		
        			saveFile(getSelectedT29Costing().getUploadBSO(), vNoCosting, "BSO");
           		}
        		
        		if(getSelectedT29Costing().getUploadInfoPrice() != null){		
        			saveFile(getSelectedT29Costing().getUploadInfoPrice(), vNoCosting, "PRICE");
        		}
        		
        		if(getSelectedT29Costing().getUploadPoCustomer() != null){		
        			saveFile(getSelectedT29Costing().getUploadPoCustomer(), vNoCosting, "PO");
        		}
            	
        		onClick$btnCancel(event);
        		
        		
        		@SuppressWarnings("unused")
				String newValue = DisplayNoDocumentWindow.show(
        				windowT29CostingMain, vNoCosting, "No Costing");
        		
        		
        		
        	} else if(state == 2) {
        		
        		if(getSelectedT29Costing().getUploadBSO() != null){
        			deleteFile(getSelectedT29Costing().getFileBso());
        			saveFile(getSelectedT29Costing().getUploadBSO(), getSelectedT29Costing().getNoCosting(), "BSO");
        			getSelectedT29Costing().setFileBso(getSelectedT29Costing().getNoCosting() + "_BSO." + getSelectedT29Costing().getUploadBSO().getFormat());
            		
        		}
        		
        		if(getSelectedT29Costing().getUploadInfoPrice() != null){
        			deleteFile(getSelectedT29Costing().getFileInfoPrice());
        			saveFile(getSelectedT29Costing().getUploadInfoPrice(), getSelectedT29Costing().getNoCosting(), "PRICE");
        			getSelectedT29Costing().setFileInfoPrice(getSelectedT29Costing().getNoCosting() + "_PRICE." + getSelectedT29Costing().getUploadInfoPrice().getFormat());
        		}
        		
        		if(getSelectedT29Costing().getUploadPoCustomer() != null){
        			deleteFile(getSelectedT29Costing().getFilePoCustomer());
        			saveFile(getSelectedT29Costing().getUploadPoCustomer(), getSelectedT29Costing().getNoCosting(), "PO");
        			getSelectedT29Costing().setFilePoCustomer(getSelectedT29Costing().getNoCosting() + "_PO." + getSelectedT29Costing().getUploadPoCustomer().getFormat());
        		}

        		getT29CostingHService().update(getSelectedT29Costing()
        										,getT29CostingDetailCtrl().getList_Deleted_T30CostingD_HW3PS_List()
        										,getT29CostingDetailCtrl().getList_Deleted_T31CostingD_ACSPS_List()
        										,getT29CostingDetailCtrl().getList_Deleted_T32CostingD_OWNSW_List()
        										,getT29CostingDetailCtrl().getList_Deleted_T33CostingD_OTHER_List()
        										,getT29CostingDetailCtrl().getList_Deleted_T34CostingD_PAYMENT_List()
        										);
	            // refresh the list
	            btnCtrlT29Costing.setInitInquiryButton();
	            renderButtonActionbyRole(true);
	            btnDownloadCosting.setVisible(true);
	            
	            
	            getT29CostingListCtrl().doFillListbox();
                
                tabT29CostingList.setSelected(true);
                
                renderTabAndTitle("Save");
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT29Costing.setInitInquiryButton();
       	renderButtonActionbyRole(true);
       	btnDownloadCosting.setVisible(true);
       	
        
       	//RESTOREVALUE
       	//doResetToInitValues();
       	
        tabT29CostingList.setSelected(true);
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT29CostingListCtrl().doFillListbox();
        }
        
    	
    	if(getT29CostingDetailCtrl().binder != null){
    		getT29CostingDetailCtrl().binder.loadAll();
    	} 
    }
    



    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT29CostingListCtrl() != null) {
        	getT29CostingListCtrl().getSearchData();        	
        	tabT29CostingList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT29CostingListCtrl() != null) {
        	getT29CostingListCtrl().clearSearchBox();
        	getT29CostingListCtrl().getSearchData();        	
        	tabT29CostingList.setSelected(true);
        }
    }
    
    public void onClick$btnSubmitToSAO(Event event) throws InterruptedException {
    	
    	if (getSelectedT29Costing() != null) {
	        final T29CostingH anT29CostingH = getSelectedT29Costing();
	        if (anT29CostingH != null) {
	
	        	String vFlagStatus = anT29CostingH.getFlagStatus();
	        	
	        	if(vFlagStatus.equals("SALES") == false ){
	        		ZksampleMessageUtils.showErrorMessage("Posisi Costing harus \"SALES\" untuk bisa di \"Submit to SAO\"" );
	        		return;
	        	} else {
	        	
	        	
	        	
		            // Show a confirm box
		        	String deleteRecord = anT29CostingH.getNoCosting() ;
		            final String msg = "Apakah anda yakin untuk \"Submit to SAO\" ?" + "\n\n --> " + deleteRecord;
		            final String title = "Submit";
		
		            MultiLineMessageBox.doSetTemplate();
		            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
		                @Override
		                public void onEvent(Event evt) {
		                    switch (((Integer) evt.getData()).intValue()) {
		                        case MultiLineMessageBox.YES:
		                            try {
		                                updateStatusBean();
		                            } catch (InterruptedException e) {
		                                e.printStackTrace();
		                            }
		                            break;
		                        case MultiLineMessageBox.NO:
		                            break;
		                    }
		                }
		
		                private void updateStatusBean() throws InterruptedException {
		                	try {
		                		
		                		anT29CostingH.setFlagStatus("SAO");
		                		
		                        getT29CostingHService().updateSatus(anT29CostingH);
		                        setSelectedT29Costing(null);
		                        
		                        // refresh the list
		                        getT29CostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
	        	}
	
	        }
	        
	        btnCtrlT29Costing.setInitInquiryButton();
	        renderButtonActionbyRole(true);
	        btnDownloadCosting.setVisible(true);
	        
	        setSelectedT29Costing(null);
	        
	        // refresh the list
	        getT29CostingListCtrl().doFillListbox();
	        tabT29CostingList.setSelected(true);
    	}
    }

    public void onClick$btnSubmitToLogistic(Event event) throws InterruptedException {
    	
    	if (getSelectedT29Costing() != null) {
	        final T29CostingH anT29CostingH = getSelectedT29Costing();
	        if (anT29CostingH != null) {
	
	        	String vFlagStatus = anT29CostingH.getFlagStatus();
	        	
	        	if(vFlagStatus.equals("SAO") == false ){
	        		ZksampleMessageUtils.showErrorMessage("Posisi Costing harus \"SAO\" untuk bisa di \"Submit to Logistic\"" );
	        		return;
	        	} else {
	        	
	        	
	        	
		            // Show a confirm box
		        	String deleteRecord = anT29CostingH.getNoCosting() ;
		            final String msg = "Apakah anda yakin untuk \"Submit to Logistic\" ?" + "\n\n --> " + deleteRecord;
		            final String title = "Submit";
		
		            MultiLineMessageBox.doSetTemplate();
		            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
		                @Override
		                public void onEvent(Event evt) {
		                    switch (((Integer) evt.getData()).intValue()) {
		                        case MultiLineMessageBox.YES:
		                            try {
		                                updateStatusBean();
		                            } catch (InterruptedException e) {
		                                e.printStackTrace();
		                            }
		                            break;
		                        case MultiLineMessageBox.NO:
		                            break;
		                    }
		                }
		
		                private void updateStatusBean() throws InterruptedException {
		                	try {
		                		
		                		anT29CostingH.setFlagStatus("LOGISTIC");
		                		
		                        getT29CostingHService().updateSatus(anT29CostingH);
		                        setSelectedT29Costing(null);
		                        
		                        // refresh the list
		                        getT29CostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
	        	}
	
	        }
	        
	        btnCtrlT29Costing.setInitInquiryButton();
	        renderButtonActionbyRole(true);
	        btnDownloadCosting.setVisible(true);
	        
	        setSelectedT29Costing(null);
	        
	        // refresh the list
	        getT29CostingListCtrl().doFillListbox();
	        tabT29CostingList.setSelected(true);
    	}
    }
    
    public void onClick$btnBackToSales1(Event event) throws InterruptedException {
    	
    	if (getSelectedT29Costing() != null) {
	        final T29CostingH anT29CostingH = getSelectedT29Costing();
	        if (anT29CostingH != null) {
	
	        	String vFlagStatus = anT29CostingH.getFlagStatus();
	        	
	        	if(vFlagStatus.equals("SAO") == false ){
	        		ZksampleMessageUtils.showErrorMessage("Posisi Costing harus \"SAO\" untuk bisa di \"Back to Sales\"" );
	        		return;
	        	} else {
	        	
	        	
	        	
		            // Show a confirm box
		        	String deleteRecord = anT29CostingH.getNoCosting() ;
		            final String msg = "Apakah anda yakin untuk \"Back to Sales\" ?" + "\n\n --> " + deleteRecord;
		            final String title = "Submit";
		
		            MultiLineMessageBox.doSetTemplate();
		            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
		                @Override
		                public void onEvent(Event evt) {
		                    switch (((Integer) evt.getData()).intValue()) {
		                        case MultiLineMessageBox.YES:
		                            try {
		                                updateStatusBean();
		                            } catch (InterruptedException e) {
		                                e.printStackTrace();
		                            }
		                            break;
		                        case MultiLineMessageBox.NO:
		                            break;
		                    }
		                }
		
		                private void updateStatusBean() throws InterruptedException {
		                	try {
		                		
		                		anT29CostingH.setFlagStatus("SALES");
		                		
		                        getT29CostingHService().updateSatus(anT29CostingH);
		                        setSelectedT29Costing(null);
		                        
		                        // refresh the list
		                        getT29CostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
	        	}
	
	        }
	        
	        btnCtrlT29Costing.setInitInquiryButton();
	        renderButtonActionbyRole(true);
	        btnDownloadCosting.setVisible(true);
	        
	        setSelectedT29Costing(null);
	        
	        // refresh the list
	        getT29CostingListCtrl().doFillListbox();
	        tabT29CostingList.setSelected(true);
    	}
    }
    
    public void onClick$btnSubmitToFinance(Event event) throws InterruptedException {
    	
    	if (getSelectedT29Costing() != null) {
	        final T29CostingH anT29CostingH = getSelectedT29Costing();
	        if (anT29CostingH != null) {
	
	        	String vFlagStatus = anT29CostingH.getFlagStatus();
	        	
	        	if(vFlagStatus.equals("LOGISTIC") == false ){
	        		ZksampleMessageUtils.showErrorMessage("Posisi Costing harus \"LOGISTIC\" untuk bisa di \"Submit to Finance\"" );
	        		return;
	        	} else {
	        	
	        	
	        	
		            // Show a confirm box
		        	String deleteRecord = anT29CostingH.getNoCosting() ;
		            final String msg = "Apakah anda yakin untuk \"Submit to Finance\" ?" + "\n\n --> " + deleteRecord;
		            final String title = "Submit";
		
		            MultiLineMessageBox.doSetTemplate();
		            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
		                @Override
		                public void onEvent(Event evt) {
		                    switch (((Integer) evt.getData()).intValue()) {
		                        case MultiLineMessageBox.YES:
		                            try {
		                                updateStatusBean();
		                            } catch (InterruptedException e) {
		                                e.printStackTrace();
		                            }
		                            break;
		                        case MultiLineMessageBox.NO:
		                            break;
		                    }
		                }
		
		                private void updateStatusBean() throws InterruptedException {
		                	try {
		                		
		                		anT29CostingH.setFlagStatus("FINANCE 1");
		                		
		                        getT29CostingHService().updateSatus(anT29CostingH);
		                        setSelectedT29Costing(null);
		                        
		                        // refresh the list
		                        getT29CostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
	        	}
	
	        }
	        
	        btnCtrlT29Costing.setInitInquiryButton();
	        renderButtonActionbyRole(true);
	        btnDownloadCosting.setVisible(true);
	        
	        setSelectedT29Costing(null);
	        
	        // refresh the list
	        getT29CostingListCtrl().doFillListbox();
	        tabT29CostingList.setSelected(true);
    	}
    }
    
    public void onClick$btnBackToSales2(Event event) throws InterruptedException {
    	
    	if (getSelectedT29Costing() != null) {
	        final T29CostingH anT29CostingH = getSelectedT29Costing();
	        if (anT29CostingH != null) {
	
	        	String vFlagStatus = anT29CostingH.getFlagStatus();
	        	
	        	if(vFlagStatus.equals("LOGISTIC") == false ){
	        		ZksampleMessageUtils.showErrorMessage("Posisi Costing harus \"LOGISTIC\" untuk bisa di \"Back to Sales\"" );
	        		return;
	        	} else {
	        	
	        	
	        	
		            // Show a confirm box
		        	String deleteRecord = anT29CostingH.getNoCosting() ;
		            final String msg = "Apakah anda yakin untuk \"Back to Sales\" ?" + "\n\n --> " + deleteRecord;
		            final String title = "Submit";
		
		            MultiLineMessageBox.doSetTemplate();
		            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
		                @Override
		                public void onEvent(Event evt) {
		                    switch (((Integer) evt.getData()).intValue()) {
		                        case MultiLineMessageBox.YES:
		                            try {
		                                updateStatusBean();
		                            } catch (InterruptedException e) {
		                                e.printStackTrace();
		                            }
		                            break;
		                        case MultiLineMessageBox.NO:
		                            break;
		                    }
		                }
		
		                private void updateStatusBean() throws InterruptedException {
		                	try {
		                		
		                		anT29CostingH.setFlagStatus("SALES");
		                		
		                        getT29CostingHService().updateSatus(anT29CostingH);
		                        setSelectedT29Costing(null);
		                        
		                        // refresh the list
		                        getT29CostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
	        	}
	
	        }
	        
	        btnCtrlT29Costing.setInitInquiryButton();
	        renderButtonActionbyRole(true);
	        btnDownloadCosting.setVisible(true);
	        
	        setSelectedT29Costing(null);
	        
	        // refresh the list
	        getT29CostingListCtrl().doFillListbox();
	        tabT29CostingList.setSelected(true);
    	}   	
    }
    
    
    public void onClick$btnSubmitToSM(Event event) throws InterruptedException {
    	
    	if (getSelectedT29Costing() != null) {
	        final T29CostingH anT29CostingH = getSelectedT29Costing();
	        if (anT29CostingH != null) {
	
	        	String vFlagStatus = anT29CostingH.getFlagStatus();
	        	
	        	if(vFlagStatus.equals("FINANCE 1") == false ){
	        		ZksampleMessageUtils.showErrorMessage("Posisi Costing harus \"FINANCE 1\" untuk bisa di \"Submit to SM\"" );
	        		return;
	        	} else {
	        	
	        	
	        	
		            // Show a confirm box
		        	String deleteRecord = anT29CostingH.getNoCosting() ;
		            final String msg = "Apakah anda yakin untuk \"Submit to SM\" ?" + "\n\n --> " + deleteRecord;
		            final String title = "Submit";
		
		            MultiLineMessageBox.doSetTemplate();
		            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
		                @Override
		                public void onEvent(Event evt) {
		                    switch (((Integer) evt.getData()).intValue()) {
		                        case MultiLineMessageBox.YES:
		                            try {
		                                updateStatusBean();
		                            } catch (InterruptedException e) {
		                                e.printStackTrace();
		                            }
		                            break;
		                        case MultiLineMessageBox.NO:
		                            break;
		                    }
		                }
		
		                private void updateStatusBean() throws InterruptedException {
		                	try {
		                		
		                		anT29CostingH.setFlagStatus("SM");
		                		
		                        getT29CostingHService().updateSatus(anT29CostingH);
		                        setSelectedT29Costing(null);
		                        
		                        // refresh the list
		                        getT29CostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
	        	}
	
	        }
	        
	        btnCtrlT29Costing.setInitInquiryButton();
	        renderButtonActionbyRole(true);
	        btnDownloadCosting.setVisible(true);
	        
	        setSelectedT29Costing(null);
	        
	        // refresh the list
	        getT29CostingListCtrl().doFillListbox();
	        tabT29CostingList.setSelected(true);
    	}
    }    

    public void onClick$btnBackToLogistic(Event event) throws InterruptedException {
    	
    	if (getSelectedT29Costing() != null) {
	        final T29CostingH anT29CostingH = getSelectedT29Costing();
	        if (anT29CostingH != null) {
	
	        	String vFlagStatus = anT29CostingH.getFlagStatus();
	        	
	        	if(vFlagStatus.equals("FINANCE 1") == false ){
	        		ZksampleMessageUtils.showErrorMessage("Posisi Costing harus \"FINANCE 1\" untuk bisa di \"Back to Logistic\"" );
	        		return;
	        	} else {
	        	
	        	
	        	
		            // Show a confirm box
		        	String deleteRecord = anT29CostingH.getNoCosting() ;
		            final String msg = "Apakah anda yakin untuk \"Back to Logistic\" ?" + "\n\n --> " + deleteRecord;
		            final String title = "Submit";
		
		            MultiLineMessageBox.doSetTemplate();
		            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
		                @Override
		                public void onEvent(Event evt) {
		                    switch (((Integer) evt.getData()).intValue()) {
		                        case MultiLineMessageBox.YES:
		                            try {
		                                updateStatusBean();
		                            } catch (InterruptedException e) {
		                                e.printStackTrace();
		                            }
		                            break;
		                        case MultiLineMessageBox.NO:
		                            break;
		                    }
		                }
		
		                private void updateStatusBean() throws InterruptedException {
		                	try {
		                		
		                		anT29CostingH.setFlagStatus("LOGISTIC");
		                		
		                        getT29CostingHService().updateSatus(anT29CostingH);
		                        setSelectedT29Costing(null);
		                        
		                        // refresh the list
		                        getT29CostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
	        	}
	
	        }
	        
	        btnCtrlT29Costing.setInitInquiryButton();
	        renderButtonActionbyRole(true);
	        btnDownloadCosting.setVisible(true);
	        
	        setSelectedT29Costing(null);
	        
	        // refresh the list
	        getT29CostingListCtrl().doFillListbox();
	        tabT29CostingList.setSelected(true);
    	}   	
    }
    
    public void onClick$btnSubmitToFinance2(Event event) throws InterruptedException {
    	
    	if (getSelectedT29Costing() != null) {
	        final T29CostingH anT29CostingH = getSelectedT29Costing();
	        if (anT29CostingH != null) {
	
	        	String vFlagStatus = anT29CostingH.getFlagStatus();
	        	
	        	if(vFlagStatus.equals("SM") == false ){
	        		ZksampleMessageUtils.showErrorMessage("Posisi Costing harus \"SM\" untuk bisa di \"Final to Finance\"" );
	        		return;
	        	} else {
	        	
	        	
	        	
		            // Show a confirm box
		        	String deleteRecord = anT29CostingH.getNoCosting() ;
		            final String msg = "Apakah anda yakin untuk \"Final to Finance\" ?" + "\n\n --> " + deleteRecord;
		            final String title = "Submit";
		
		            MultiLineMessageBox.doSetTemplate();
		            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
		                @Override
		                public void onEvent(Event evt) {
		                    switch (((Integer) evt.getData()).intValue()) {
		                        case MultiLineMessageBox.YES:
		                            try {
		                                updateStatusBean();
		                            } catch (InterruptedException e) {
		                                e.printStackTrace();
		                            }
		                            break;
		                        case MultiLineMessageBox.NO:
		                            break;
		                    }
		                }
		
		                private void updateStatusBean() throws InterruptedException {
		                	try {
		                		
		                		anT29CostingH.setFlagStatus("FINANCE 2");
		                		
		                        getT29CostingHService().updateSatus(anT29CostingH);
		                        setSelectedT29Costing(null);
		                        
		                        // refresh the list
		                        getT29CostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
	        	}
	
	        }
	        
	        btnCtrlT29Costing.setInitInquiryButton();
	        renderButtonActionbyRole(true);
	        btnDownloadCosting.setVisible(true);
	        
	        setSelectedT29Costing(null);
	        
	        // refresh the list
	        getT29CostingListCtrl().doFillListbox();
	        tabT29CostingList.setSelected(true);
    	}
    } 
    
    
    @SuppressWarnings("unchecked")
	public void onClick$btnDownloadCosting(Event event) throws InterruptedException {
    	
    	if (getSelectedT29Costing() != null) {
    		String jasperRpt = "/solusi/hapis/webui/reports/sales/Costing/040201_FileCosting.jasper";
    		    		
    		param.put("T29Id",  getSelectedT29Costing().getT29Id());
    		
    		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
    	}
    }
    
	public void saveFile(Media media, String namaFile, String tipeDok) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			InputStream fin = media.getStreamData();
			in = new BufferedInputStream(fin);

			File baseDir = new File(PATH_FILE_UPLOAD);

			if (!baseDir.exists()) {
				baseDir.mkdirs();
			}

			String fileName = namaFile + "_" + tipeDok + "." + media.getFormat();
			File file = new File(PATH_FILE_UPLOAD + fileName );

			OutputStream fout = new FileOutputStream(file);
			out = new BufferedOutputStream(fout);
			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while (ch != -1) {
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}

//			if (tipeDok.equals("BSO") == true) {
//				getSelectedT29Costing().setFileBso(fileName);
//			} else {
//				if (tipeDok.equals("PRICE") == true) {
//					getSelectedT29Costing().setFileInfoPrice(fileName);
//				} else {
//					if (tipeDok.equals("PO") == true) {
//						getSelectedT29Costing().setFilePoCustomer(fileName);
//					}
//				}
//			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (out != null)
					out.close();

				if (in != null)
					in.close();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
	
	public void deleteFile(String namaFile){
		File delFile = new File(PATH_FILE_UPLOAD + namaFile);
		if( delFile.exists() )	
			delFile.delete();
	}
	
	
	private void renderButtonActionbyRole (boolean modeRender){
		
		String vUser = SecurityContextHolder.getContext().getAuthentication().getName();
		if (vUser.equals("admin") == true){
			this.btnNew.setVisible(modeRender);
			this.btnEdit.setVisible(modeRender);
			this.btnDelete.setVisible(modeRender);
			this.btnSubmitToSAO.setVisible(modeRender);
		    this.btnSubmitToLogistic.setVisible(modeRender);
		    this.btnBackToSales1.setVisible(modeRender);
		    this.btnSubmitToFinance.setVisible(modeRender);
		    this.btnBackToSales2.setVisible(modeRender);
		    this.btnSubmitToSM.setVisible(modeRender);
		    this.btnBackToLogistic.setVisible(modeRender);
		    this.btnSubmitToFinance2.setVisible(modeRender);
		} else 
		{
			if (modeRender ==  true){
				String vRoleUser = selectQueryService.QueryRoleUserCosting(vUser);
				if(CommonUtils.isNotEmpty(vRoleUser)){
					if (vRoleUser.equals("SALES")){
						this.btnNew.setVisible(modeRender);
						this.btnEdit.setVisible(modeRender);
						this.btnDelete.setVisible(modeRender);
						
						this.btnSubmitToSAO.setVisible(modeRender);
					    this.btnSubmitToLogistic.setVisible(!modeRender);
					    this.btnBackToSales1.setVisible(!modeRender);
					    this.btnSubmitToFinance.setVisible(!modeRender);
					    this.btnBackToSales2.setVisible(!modeRender);
					    this.btnSubmitToSM.setVisible(!modeRender);
					    this.btnBackToLogistic.setVisible(!modeRender);
					    this.btnSubmitToFinance2.setVisible(!modeRender);
					} else {
						if (vRoleUser.equals("SAO")){
							this.btnNew.setVisible(!modeRender);
							this.btnEdit.setVisible(modeRender);
							this.btnDelete.setVisible(!modeRender);
							this.btnSubmitToSAO.setVisible(!modeRender);
						    this.btnSubmitToLogistic.setVisible(modeRender);
						    this.btnBackToSales1.setVisible(modeRender);
						    this.btnSubmitToFinance.setVisible(!modeRender);
						    this.btnBackToSales2.setVisible(!modeRender);
						    this.btnSubmitToSM.setVisible(!modeRender);
						    this.btnBackToLogistic.setVisible(!modeRender);
						    this.btnSubmitToFinance2.setVisible(!modeRender);
						} else {
							if (vRoleUser.equals("LOGISTIC")){
								this.btnNew.setVisible(!modeRender);
								this.btnEdit.setVisible(modeRender);
								this.btnDelete.setVisible(!modeRender);
								this.btnSubmitToSAO.setVisible(!modeRender);
							    this.btnSubmitToLogistic.setVisible(!modeRender);
							    this.btnBackToSales1.setVisible(!modeRender);
							    this.btnSubmitToFinance.setVisible(modeRender);
							    this.btnBackToSales2.setVisible(modeRender);
							    this.btnSubmitToSM.setVisible(!modeRender);
							    this.btnBackToLogistic.setVisible(!modeRender);
							    this.btnSubmitToFinance2.setVisible(!modeRender);
							} else {
								if (vRoleUser.equals("FINANCE")){
									this.btnNew.setVisible(!modeRender);
									this.btnEdit.setVisible(modeRender);
									this.btnDelete.setVisible(!modeRender);
									this.btnSubmitToSAO.setVisible(!modeRender);
								    this.btnSubmitToLogistic.setVisible(!modeRender);
								    this.btnBackToSales1.setVisible(!modeRender);
								    this.btnSubmitToFinance.setVisible(!modeRender);
								    this.btnBackToSales2.setVisible(!modeRender);
								    this.btnSubmitToSM.setVisible(modeRender);
								    this.btnBackToLogistic.setVisible(modeRender);
								    this.btnSubmitToFinance2.setVisible(!modeRender);
								} else {
									if (vRoleUser.equals("SM")){
										this.btnNew.setVisible(!modeRender);
										this.btnEdit.setVisible(modeRender);
										this.btnDelete.setVisible(!modeRender);
										this.btnSubmitToSAO.setVisible(!modeRender);
									    this.btnSubmitToLogistic.setVisible(!modeRender);
									    this.btnBackToSales1.setVisible(!modeRender);
									    this.btnSubmitToFinance.setVisible(!modeRender);
									    this.btnBackToSales2.setVisible(!modeRender);
									    this.btnSubmitToSM.setVisible(!modeRender);
									    this.btnBackToLogistic.setVisible(!modeRender);
									    this.btnSubmitToFinance2.setVisible(modeRender);
									} else {
										this.btnNew.setVisible(!modeRender);
										this.btnEdit.setVisible(modeRender);
										this.btnDelete.setVisible(!modeRender);
										this.btnSubmitToSAO.setVisible(!modeRender);
									    this.btnSubmitToLogistic.setVisible(!modeRender);
									    this.btnBackToSales1.setVisible(!modeRender);
									    this.btnSubmitToFinance.setVisible(!modeRender);
									    this.btnBackToSales2.setVisible(!modeRender);
									    this.btnSubmitToSM.setVisible(!modeRender);
									    this.btnBackToLogistic.setVisible(!modeRender);
									    this.btnSubmitToFinance2.setVisible(!modeRender);
									}
								}
							}
						}
							
					}
				}		
				
			} else {
				this.btnNew.setVisible(modeRender);
				this.btnEdit.setVisible(modeRender);
				this.btnDelete.setVisible(modeRender);
				this.btnSubmitToSAO.setVisible(modeRender);
			    this.btnSubmitToLogistic.setVisible(modeRender);
			    this.btnBackToSales1.setVisible(modeRender);
			    this.btnSubmitToFinance.setVisible(modeRender);
			    this.btnBackToSales2.setVisible(modeRender);
			    this.btnSubmitToSM.setVisible(modeRender);
			    this.btnBackToLogistic.setVisible(modeRender);
			    this.btnSubmitToFinance2.setVisible(modeRender);
			}		
		}
	}
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT29CostingDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT29CostingList.setDisabled(true);

    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT29CostingDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT29CostingList.setDisabled(false);

    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT29Costing(T29CostingH selectedT29Costing) {
        this.selectedT29Costing = selectedT29Costing;
    }

    public T29CostingH getSelectedT29Costing() {
        return this.selectedT29Costing;
    }


	public void setT29CostingHService(T29CostingHService T29CostingHService) {
        this.T29CostingHService = T29CostingHService;
    }

    public T29CostingHService getT29CostingHService() {
        return this.T29CostingHService;
    }

    public void setT29CostingListCtrl(T29CostingListCtrl T29CostingListCtrl) {
        this.T29CostingListCtrl = T29CostingListCtrl;
    }

    public T29CostingListCtrl getT29CostingListCtrl() {
        return this.T29CostingListCtrl;
    }

    public void setT29CostingDetailCtrl(T29CostingDetailCtrl T29CostingDetailCtrl) {
        this.T29CostingDetailCtrl = T29CostingDetailCtrl;
    }

    public T29CostingDetailCtrl getT29CostingDetailCtrl() {
        return this.T29CostingDetailCtrl;
    }

	public void setPostedT29Costing(T29CostingH postedT29Costing) {
		this.postedT29Costing = postedT29Costing;
	}

	public T29CostingH getPostedT29Costing() {
		return postedT29Costing;
	}

}
