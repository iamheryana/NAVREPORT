package solusi.hapis.webui.finance.T16RekapCosting;


import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

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

import solusi.hapis.backend.navbi.model.T16RekapCosting;
import solusi.hapis.backend.navbi.service.T16RekapCostingService;
import solusi.hapis.backend.parameter.service.SelectQueryNavReportService;
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
 * Date: 19-04-2021
 */

public class T16RekapCostingMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT16RekapCostingMain; 

    // Tabs
    protected Tabbox tabbox_T16RekapCostingMain;
    protected Tab tabT16RekapCostingList;
    protected Tab tabT16RekapCostingDetail;

    protected Tab tabT16RekapCostingDownload;
    protected Tabpanel tabPanelT16RekapCostingList;
    protected Tabpanel tabPanelT16RekapCostingDetail;

    protected Tabpanel tabPanelT16RekapCostingDownload;
    
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T16RekapCosting_";
    private ButtonStatusCtrl btnCtrlT16RekapCosting;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnCopy;
    protected Button btnEditKomisi;
    protected Button btnDownload;
    protected Button btnDelete;

    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T16RekapCostingListCtrl T16RekapCostingListCtrl;
    private T16RekapCostingDetailCtrl T16RekapCostingDetailCtrl;
    private T16RekapCostingDownloadCtrl T16RekapCostingDownloadCtrl;

    // Databinding
    private T16RekapCosting selectedT16RekapCosting;
    private T16RekapCosting postedT16RekapCosting;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    private T16RekapCostingService T16RekapCostingService;
    

    
    private SelectQueryNavReportService selectQueryNavReportService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/T16RekapCosting/T16RekapCostingDetail.zul";
	private String zulPageList = "/WEB-INF/pages/finance/T16RekapCosting/T16RekapCostingList.zul";
	private String zulPageDownload = "/WEB-INF/pages/finance/T16RekapCosting/T16RekapCostingDownload.zul";
	
	
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
    public T16RekapCostingMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT16RekapCostingMain(Event event) throws Exception {
        windowT16RekapCostingMain.setContentStyle("padding:0px;");

        btnCtrlT16RekapCosting = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT16RekapCosting.addButtonOther1(btnEditKomisi, "Edit-Komisi");
        btnCtrlT16RekapCosting.addButtonOther2(btnDownload, "Download-Data");
        btnCtrlT16RekapCosting.addButtonOther3(btnCopy, "Copy");
        
        
        
        btnCtrlT16RekapCosting.setInitInquiryButton();

        tabT16RekapCostingList.setSelected(true);

        if (tabPanelT16RekapCostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT16RekapCostingList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT16RekapCostingList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT16RekapCostingList.getFirstChild() != null) {

            tabT16RekapCostingList.setSelected(true);
        	btnCtrlT16RekapCosting.setInitInquiryButton();
        	getT16RekapCostingListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT16RekapCostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT16RekapCostingList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT16RekapCostingDetail(Event event) throws IOException {
        if (tabPanelT16RekapCostingDetail.getFirstChild() != null) {
        	tabT16RekapCostingDetail.setSelected(true);            
        	
        	getT16RekapCostingDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT16RekapCostingDetailCtrl().doRenderMode("View");   
            
            btnCtrlT16RekapCosting.setInitInquiryButton();

            if (getT16RekapCostingDetailCtrl().getBinder() != null) {
            	getT16RekapCostingDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT16RekapCostingDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT16RekapCostingDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    public void onSelect$tabT16RekapCostingDownload(Event event) throws IOException, ParseException {
    	if (tabPanelT16RekapCostingDownload != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT16RekapCostingDownload, this, "ModuleMainController", zulPageDownload);
        }
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelT16RekapCostingDownload.getFirstChild() != null) {
            tabT16RekapCostingDownload.setSelected(true);        

            btnCtrlT16RekapCosting.setInitCustomButton();
            //getT16RekapCostingPrintCtrl().doReadOnlyMode(true);
            
            return;
        }

        
    }
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
	public void onClick$btnDownload(Event event) throws InterruptedException, IOException, ParseException {
    	
    	if (getT16RekapCostingDownloadCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT16RekapCostingDownload, null));
        }

        if (tabPanelT16RekapCostingDownload != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT16RekapCostingDownload, this, "ModuleMainController", zulPageDownload);
        }
        
        //getT16RekapCostingPrintCtrl().doReadOnlyMode(false);
        
        btnCtrlT16RekapCosting.setInitCustomButton();
        tabT16RekapCostingDownload.setSelected(true);
        
        
    }
	
	
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT16RekapCostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT16RekapCostingDetail, null));
        } else if (getT16RekapCostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT16RekapCostingDetail, null));
        }

        T16RekapCosting anT16RekapCosting = new T16RekapCosting();

        // Set Default Value Di sini ------------------------------------- start
        anT16RekapCosting.setCompany("AUTOJAYA");
        anT16RekapCosting.setAmount(new BigDecimal(0));
        anT16RekapCosting.setPcnKomisi(new BigDecimal(0));
        anT16RekapCosting.setPcnTqs(new BigDecimal(0));
        anT16RekapCosting.setAmountKomisi(new BigDecimal(0));
        anT16RekapCosting.setAmountTqs(new BigDecimal(0));
        
        anT16RekapCosting.setFlagKomisi("N");
        anT16RekapCosting.setFlagTqs("N");
        
        
        setSelectedT16RekapCosting(anT16RekapCosting);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT16RekapCosting.setInitFormButton();
        
        // select tab Detail
        tabT16RekapCostingDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT16RekapCostingDetailCtrl().getBinder() != null) {
        	getT16RekapCostingDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT16RekapCostingDetailCtrl().doRenderMode("New");
        getT16RekapCostingDetailCtrl().doRenderCombo();
    }
    private String msgEdit(String vMasa, String vTahun, String vTipe){
    	String msgString = "";
    	String vBulan = "";
    	if (vMasa.equals("01") == true){
    		vBulan = "JANUARY";
    	} else if (vMasa.equals("02") == true){
    		vBulan = "FEBRUARI";
    	} else if (vMasa.equals("03") == true){
    		vBulan = "MARET";
    	} else if (vMasa.equals("04") == true){
    		vBulan = "APRIL";
    	} else if (vMasa.equals("05") == true){
    		vBulan = "MEI";
    	} else if (vMasa.equals("06") == true){
    		vBulan = "JUNI";
    	} else if (vMasa.equals("07") == true){
    		vBulan = "JULI";
    	} else if (vMasa.equals("08") == true){
    		vBulan = "AGUSTUS";
    	} else if (vMasa.equals("09") == true){
    		vBulan = "SEPTEMBER";
    	} else if (vMasa.equals("10") == true){
    		vBulan = "OKTOBER";
    	} else if (vMasa.equals("11") == true){
    		vBulan = "NOVEMBER";
    	} else if (vMasa.equals("12") == true){
    		vBulan = "DESEMBER";
    	}
    	
    	if(vTipe.equals("KOMISI") == true){
    		msgString = "Komisi sudah di proses pada periode "+vBulan+" "+vTahun+". Dan periode tersebut sudah closing.";
    	} else {
    		msgString = "TQS sudah di proses pada periode "+vBulan+" "+vTahun+". Dan periode tersebut sudah closing.";
    	}
    	
    	
    
		
    	return msgString;
    	
    }
    
    // Saat Button Edit di klik
	public void onClick$btnEdit(Event event) throws InterruptedException {
		if(getSelectedT16RekapCosting() != null) {
			String vCloseKomisi = "N";
			String vCloseTgs = "N";
			
			
			if(	CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getMasaKomisi()) &&
				CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getTahunKomisi())
			  ){
				List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT16RekapCosting().getMasaKomisi(), getSelectedT16RekapCosting().getTahunKomisi());
				
				if(CommonUtils.isNotEmpty(vStatusClose)){
					for(Object[] aRsltStatus : vStatusClose){
						vCloseKomisi = aRsltStatus[0].toString();
						
					}
				}
			}
			
			if(	CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getMasaTqs()) &&
					CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getTahunTqs())
				  ){
					List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT16RekapCosting().getMasaTqs(), getSelectedT16RekapCosting().getTahunTqs());
					
					if(CommonUtils.isNotEmpty(vStatusClose)){
						for(Object[] aRsltStatus : vStatusClose){
							vCloseTgs = aRsltStatus[1].toString();
							
						}
					}
			}
				
			if (vCloseKomisi.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT16RekapCosting().getMasaKomisi(),getSelectedT16RekapCosting().getTahunKomisi(),"KOMISI"));
			} else if (vCloseTgs.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT16RekapCosting().getMasaTqs(),getSelectedT16RekapCosting().getTahunTqs(),"TQS"));
			} else {
		        try {
					postedT16RekapCosting =(T16RekapCosting) ZksampleBeanUtils.cloneBean(getSelectedT16RekapCosting());
					
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
		        
		    	if (tabPanelT16RekapCostingDetail.getFirstChild() != null) {
		        	getT16RekapCostingDetailCtrl().getBinder().loadAll();
		        }
		        
		        if (getT16RekapCostingDetailCtrl() == null) {
		            Events.sendEvent(new Event("onSelect", tabT16RekapCostingDetail, null));
		        } else if (getT16RekapCostingDetailCtrl().getBinder() == null) {
		            Events.sendEvent(new Event("onSelect", tabT16RekapCostingDetail, null));
		        }
		
		        // set button
		        btnCtrlT16RekapCosting.setInitFormButton();
		
		        // select tab Detail
		        tabT16RekapCostingDetail.setSelected(true);
		        renderTabAndTitle("Edit");
		        
		        state = 2;
		        
		        // set render layar
		        getT16RekapCostingDetailCtrl().doRenderCombo();
		        getT16RekapCostingDetailCtrl().doRenderMode("Edit");
			}
		}
    }
	
    // Saat Button Edit Komisi di klik
	public void onClick$btnEditKomisi(Event event) throws InterruptedException {
		if(getSelectedT16RekapCosting() != null) {
			String vCloseKomisi = "N";
			String vCloseTgs = "N";
			
			
			if(	CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getMasaKomisi()) &&
				CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getTahunKomisi())
			  ){
				List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT16RekapCosting().getMasaKomisi(), getSelectedT16RekapCosting().getTahunKomisi());
				
				if(CommonUtils.isNotEmpty(vStatusClose)){
					for(Object[] aRsltStatus : vStatusClose){
						vCloseKomisi = aRsltStatus[0].toString();
						
					}
				}
			}
			
			if(	CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getMasaTqs()) &&
					CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getTahunTqs())
				  ){
					List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT16RekapCosting().getMasaTqs(), getSelectedT16RekapCosting().getTahunTqs());
					
					if(CommonUtils.isNotEmpty(vStatusClose)){
						for(Object[] aRsltStatus : vStatusClose){
							vCloseTgs = aRsltStatus[1].toString();
							
						}
					}
			}
				
			if (vCloseKomisi.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT16RekapCosting().getMasaKomisi(),getSelectedT16RekapCosting().getTahunKomisi(),"KOMISI"));
			} else if (vCloseTgs.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT16RekapCosting().getMasaTqs(),getSelectedT16RekapCosting().getTahunTqs(),"TQS"));
			} else {
					
		        try {
					postedT16RekapCosting =(T16RekapCosting) ZksampleBeanUtils.cloneBean(getSelectedT16RekapCosting());
					
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
		        
		    	if (tabPanelT16RekapCostingDetail.getFirstChild() != null) {
		        	getT16RekapCostingDetailCtrl().getBinder().loadAll();
		        }
		        
		        if (getT16RekapCostingDetailCtrl() == null) {
		            Events.sendEvent(new Event("onSelect", tabT16RekapCostingDetail, null));
		        } else if (getT16RekapCostingDetailCtrl().getBinder() == null) {
		            Events.sendEvent(new Event("onSelect", tabT16RekapCostingDetail, null));
		        }
		
		        // set button
		        btnCtrlT16RekapCosting.setInitFormButton();
		
		        // select tab Detail
		        tabT16RekapCostingDetail.setSelected(true);
		        renderTabAndTitle("Edit Komisi");
		        
		        state = 2;
		        
		        // set render layar
		        getT16RekapCostingDetailCtrl().doRenderCombo();
		        getT16RekapCostingDetailCtrl().doRenderMode("EditKomisi");
		        getT16RekapCostingDetailCtrl().doRenderDefaultEditKomis();				
									
			}
		}
    }
    
  
    // Saat Button Copy di klik
	public void onClick$btnCopy(Event event) throws InterruptedException {
        try {
			postedT16RekapCosting =(T16RekapCosting) ZksampleBeanUtils.cloneBean(getSelectedT16RekapCosting());
			
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
        
    
    	if (getT16RekapCostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT16RekapCostingDetail, null));
        } else if (getT16RekapCostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT16RekapCostingDetail, null));
        }

        T16RekapCosting anT16RekapCosting = new T16RekapCosting();

        // Set Default Value Di sini ------------------------------------- start
        anT16RekapCosting.setCompany(postedT16RekapCosting.getCompany());
        anT16RekapCosting.setNoBso(postedT16RekapCosting.getNoBso());
        anT16RekapCosting.setNoSo(postedT16RekapCosting.getNoSo());
        anT16RekapCosting.setTglSo(postedT16RekapCosting.getTglSo());
        anT16RekapCosting.setNoPoCust(postedT16RekapCosting.getNoPoCust());
        anT16RekapCosting.setCustomer(postedT16RekapCosting.getCustomer());
        anT16RekapCosting.setNoInvoice(postedT16RekapCosting.getNoInvoice());
        anT16RekapCosting.setAmount(postedT16RekapCosting.getAmount());
        anT16RekapCosting.setTglInvoice(postedT16RekapCosting.getTglInvoice());
        anT16RekapCosting.setTglLunas(postedT16RekapCosting.getTglLunas());
        

        anT16RekapCosting.setPcnKomisi(new BigDecimal(0));
        anT16RekapCosting.setPcnTqs(new BigDecimal(0));
        anT16RekapCosting.setAmountKomisi(new BigDecimal(0));
        anT16RekapCosting.setAmountTqs(new BigDecimal(0));
        
        anT16RekapCosting.setFlagKomisi("N");
        anT16RekapCosting.setFlagTqs("N");
        
        setSelectedT16RekapCosting(anT16RekapCosting);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT16RekapCosting.setInitFormButton();
        
        // select tab Detail
        tabT16RekapCostingDetail.setSelected(true);
        renderTabAndTitle("Copy");
               
        state = 5;

        if (getT16RekapCostingDetailCtrl().getBinder() != null) {
        	getT16RekapCostingDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT16RekapCostingDetailCtrl().doRenderMode("New");
        getT16RekapCostingDetailCtrl().doRenderCombo();
    }
	
	
	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT16RekapCosting() != null) {
    		String vCloseKomisi = "N";
			String vCloseTgs = "N";
			
			
			if(	CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getMasaKomisi()) &&
				CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getTahunKomisi())
			  ){
				List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT16RekapCosting().getMasaKomisi(), getSelectedT16RekapCosting().getTahunKomisi());
				
				if(CommonUtils.isNotEmpty(vStatusClose)){
					for(Object[] aRsltStatus : vStatusClose){
						vCloseKomisi = aRsltStatus[0].toString();
						
					}
				}
			}
			
			if(	CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getMasaTqs()) &&
					CommonUtils.isNotEmpty(getSelectedT16RekapCosting().getTahunTqs())
				  ){
					List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT16RekapCosting().getMasaTqs(), getSelectedT16RekapCosting().getTahunTqs());
					
					if(CommonUtils.isNotEmpty(vStatusClose)){
						for(Object[] aRsltStatus : vStatusClose){
							vCloseTgs = aRsltStatus[1].toString();
							
						}
					}
			}
				
			if (vCloseKomisi.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT16RekapCosting().getMasaKomisi(),getSelectedT16RekapCosting().getTahunKomisi(),"KOMISI"));
			} else if (vCloseTgs.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT16RekapCosting().getMasaTqs(),getSelectedT16RekapCosting().getTahunTqs(),"TQS"));
			} else {			
		        final T16RekapCosting anT16RekapCosting = getSelectedT16RekapCosting();
		        if (anT16RekapCosting != null) {
		
		            // Show a confirm box
		        	String deleteRecord = anT16RekapCosting.getNoPoCust();
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
		                        getT16RekapCostingService().delete(anT16RekapCosting);
		                        setSelectedT16RekapCosting(null);
		                        
		                        // refresh the list
		                        getT16RekapCostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
		
		        }
		        
		        btnCtrlT16RekapCosting.setInitInquiryButton();
		        setSelectedT16RekapCosting(null);
		        
		        // refresh the list
		        getT16RekapCostingListCtrl().doFillListbox();
		        tabT16RekapCostingList.setSelected(true);
			}
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT16RekapCostingDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT16RekapCostingDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT16RekapCostingService().save(getSelectedT16RekapCosting());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT16RekapCostingService().update(getSelectedT16RekapCosting());
	            // refresh the list
	            btnCtrlT16RekapCosting.setInitInquiryButton();
	            getT16RekapCostingListCtrl().doFillListbox();
                
                tabT16RekapCostingList.setSelected(true);
                
                renderTabAndTitle("Save");
        	} else if(state == 5){
        		getT16RekapCostingService().save(getSelectedT16RekapCosting());
        		  // refresh the list
	            btnCtrlT16RekapCosting.setInitInquiryButton();
	            getT16RekapCostingListCtrl().doFillListbox();
                
                tabT16RekapCostingList.setSelected(true);
        		
        		
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT16RekapCosting.setInitInquiryButton();
        
        tabT16RekapCostingList.setSelected(true);
        if (getT16RekapCostingListCtrl() != null) {
        	getT16RekapCostingListCtrl().getSearchData();  
        }
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT16RekapCostingListCtrl().doFillListbox();
        }
        
    	if(postedT16RekapCosting != null){
           	setSelectedT16RekapCosting(postedT16RekapCosting);
           	
           	//Databinding LOV

           	
    	}
    	if(getT16RekapCostingDetailCtrl().binder != null){
    		getT16RekapCostingDetailCtrl().binder.loadAll();
    	} 
    }
    

    

    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
 //   	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listT02RekapCosting.jasper";
    	
 //   	new JReportGeneratorWindow(getT16RekapCostingPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT16RekapCostingListCtrl() != null) {
        	getT16RekapCostingListCtrl().getSearchData();        	
        	tabT16RekapCostingList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT16RekapCostingListCtrl() != null) {
        	getT16RekapCostingListCtrl().clearSearchBox();
        	getT16RekapCostingListCtrl().getSearchData();        	
        	tabT16RekapCostingList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Copy") || requestStatus.equals("Edit") || requestStatus.equals("Edit Komisi") || requestStatus.equals("Edit TQS")){
	    	tabT16RekapCostingDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT16RekapCostingList.setDisabled(true);

    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT16RekapCostingDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT16RekapCostingList.setDisabled(false);

    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT16RekapCosting(T16RekapCosting selectedT16RekapCosting) {
        this.selectedT16RekapCosting = selectedT16RekapCosting;
    }

    public T16RekapCosting getSelectedT16RekapCosting() {
        return this.selectedT16RekapCosting;
    }


	public void setT16RekapCostingService(T16RekapCostingService T16RekapCostingService) {
        this.T16RekapCostingService = T16RekapCostingService;
    }

    public T16RekapCostingService getT16RekapCostingService() {
        return this.T16RekapCostingService;
    }

    public void setT16RekapCostingListCtrl(T16RekapCostingListCtrl T16RekapCostingListCtrl) {
        this.T16RekapCostingListCtrl = T16RekapCostingListCtrl;
    }

    public T16RekapCostingListCtrl getT16RekapCostingListCtrl() {
        return this.T16RekapCostingListCtrl;
    }

    public void setT16RekapCostingDetailCtrl(T16RekapCostingDetailCtrl T16RekapCostingDetailCtrl) {
        this.T16RekapCostingDetailCtrl = T16RekapCostingDetailCtrl;
    }

    public T16RekapCostingDetailCtrl getT16RekapCostingDetailCtrl() {
        return this.T16RekapCostingDetailCtrl;
    }

	public void setPostedT16RekapCosting(T16RekapCosting postedT16RekapCosting) {
		this.postedT16RekapCosting = postedT16RekapCosting;
	}

	public T16RekapCosting getPostedT16RekapCosting() {
		return postedT16RekapCosting;
	}

	public T16RekapCostingDownloadCtrl getT16RekapCostingDownloadCtrl() {
		return T16RekapCostingDownloadCtrl;
	}

	public void setT16RekapCostingDownloadCtrl(
			T16RekapCostingDownloadCtrl t16RekapCostingDownloadCtrl) {
		T16RekapCostingDownloadCtrl = t16RekapCostingDownloadCtrl;
	}

	
}
