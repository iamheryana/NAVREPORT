package solusi.hapis.webui.tabel.T02rekapcosting;

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

import solusi.hapis.backend.parameter.service.SelectQueryNavReportService;
import solusi.hapis.backend.tabel.model.T02rekapcosting;
import solusi.hapis.backend.tabel.service.T02rekapcostingService;
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
 * Date: 13-03-2018
 */

public class T02rekapcostingMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT02rekapcostingMain; 

    // Tabs
    protected Tabbox tabbox_T02rekapcostingMain;
    protected Tab tabT02rekapcostingList;
    protected Tab tabT02rekapcostingDetail;
    protected Tab tabT02rekapcostingPrint;
    protected Tab tabT02rekapcostingDownload;
    protected Tabpanel tabPanelT02rekapcostingList;
    protected Tabpanel tabPanelT02rekapcostingDetail;
    protected Tabpanel tabPanelT02rekapcostingPrint;
    protected Tabpanel tabPanelT02rekapcostingDownload;
    
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T02rekapcosting_";
    private ButtonStatusCtrl btnCtrlT02rekapcosting;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnCopy;
    protected Button btnEditKomisi;
    protected Button btnDownload;
    protected Button btnDelete;
    protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    // Tab-Controllers for getting the binders
    private T02rekapcostingListCtrl T02rekapcostingListCtrl;
    private T02rekapcostingDetailCtrl T02rekapcostingDetailCtrl;
    private T02rekapcostingPrintCtrl T02rekapcostingPrintCtrl;
    private T02rekapcostingDownloadCtrl T02rekapcostingDownloadCtrl;

    // Databinding
    private T02rekapcosting selectedT02rekapcosting;
    private T02rekapcosting postedT02rekapcosting;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    private T02rekapcostingService T02rekapcostingService;
    

    
    private SelectQueryNavReportService selectQueryNavReportService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/tabel/T02rekapcosting/T02rekapcostingDetail.zul";
	private String zulPageList = "/WEB-INF/pages/tabel/T02rekapcosting/T02rekapcostingList.zul";
	private String zulPagePrint = "/WEB-INF/pages/tabel/T02rekapcosting/T02rekapcostingPrint.zul";
	private String zulPageDownload = "/WEB-INF/pages/tabel/T02rekapcosting/T02rekapcostingDownload.zul";
	
	
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
    public T02rekapcostingMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT02rekapcostingMain(Event event) throws Exception {
        windowT02rekapcostingMain.setContentStyle("padding:0px;");

        btnCtrlT02rekapcosting = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnListing, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT02rekapcosting.addButtonOther1(btnEditKomisi, "Edit-Komisi");
        btnCtrlT02rekapcosting.addButtonOther2(btnDownload, "Download-Data");
        btnCtrlT02rekapcosting.addButtonOther3(btnCopy, "Copy");
        
        
        
        btnCtrlT02rekapcosting.setInitInquiryButton();

        tabT02rekapcostingList.setSelected(true);

        if (tabPanelT02rekapcostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT02rekapcostingList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT02rekapcostingList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT02rekapcostingList.getFirstChild() != null) {

            tabT02rekapcostingList.setSelected(true);
        	btnCtrlT02rekapcosting.setInitInquiryButton();
        	getT02rekapcostingListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT02rekapcostingList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT02rekapcostingList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
    public void onSelect$tabT02rekapcostingDetail(Event event) throws IOException {
        if (tabPanelT02rekapcostingDetail.getFirstChild() != null) {
        	tabT02rekapcostingDetail.setSelected(true);            
        	
        	getT02rekapcostingDetailCtrl().doRenderCombo();
        	// Render Inisialisasi posisi awal
            getT02rekapcostingDetailCtrl().doRenderMode("View");   
            
            btnCtrlT02rekapcosting.setInitInquiryButton();

            if (getT02rekapcostingDetailCtrl().getBinder() != null) {
            	getT02rekapcostingDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelT02rekapcostingDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT02rekapcostingDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    public void onSelect$tabT02rekapcostingPrint(Event event) throws IOException, ParseException {
    	if (tabPanelT02rekapcostingPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT02rekapcostingPrint, this, "ModuleMainController", zulPagePrint);
        }
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelT02rekapcostingPrint.getFirstChild() != null) {
            tabT02rekapcostingPrint.setSelected(true);        

            getT02rekapcostingPrintCtrl().doReadOnlyMode(true);
            
            return;
        }

        
    }
    
    public void onSelect$tabT02rekapcostingDownload(Event event) throws IOException, ParseException {
    	if (tabPanelT02rekapcostingDownload != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT02rekapcostingDownload, this, "ModuleMainController", zulPageDownload);
        }
    	
    	// Check if the tabpanel is already loaded
        if (tabPanelT02rekapcostingDownload.getFirstChild() != null) {
            tabT02rekapcostingDownload.setSelected(true);        

            btnCtrlT02rekapcosting.setInitCustomButton();
            //getT02rekapcostingPrintCtrl().doReadOnlyMode(true);
            
            return;
        }

        
    }
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
	public void onClick$btnDownload(Event event) throws InterruptedException, IOException, ParseException {
    	
    	if (getT02rekapcostingDownloadCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT02rekapcostingDownload, null));
        }

        if (tabPanelT02rekapcostingDownload != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT02rekapcostingDownload, this, "ModuleMainController", zulPageDownload);
        }
        
        //getT02rekapcostingPrintCtrl().doReadOnlyMode(false);
        
        btnCtrlT02rekapcosting.setInitCustomButton();
        tabT02rekapcostingDownload.setSelected(true);
        
        
    }
	
	
    
    // Saat Button New di klik
    public void onClick$btnNew(Event event) throws InterruptedException {
    	
    	if (getT02rekapcostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT02rekapcostingDetail, null));
        } else if (getT02rekapcostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT02rekapcostingDetail, null));
        }

        T02rekapcosting anT02rekapcosting = new T02rekapcosting();

        // Set Default Value Di sini ------------------------------------- start
        anT02rekapcosting.setCompany("AUTOJAYA");
        anT02rekapcosting.setAmount(new BigDecimal(0));
        anT02rekapcosting.setPcnKomisi(new BigDecimal(0));
        anT02rekapcosting.setPcnTqs(new BigDecimal(0));
        anT02rekapcosting.setAmountKomisi(new BigDecimal(0));
        anT02rekapcosting.setAmountTqs(new BigDecimal(0));
        
        setSelectedT02rekapcosting(anT02rekapcosting);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT02rekapcosting.setInitFormButton();
        
        // select tab Detail
        tabT02rekapcostingDetail.setSelected(true);
        renderTabAndTitle("New");
               
        state = 1;

        if (getT02rekapcostingDetailCtrl().getBinder() != null) {
        	getT02rekapcostingDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT02rekapcostingDetailCtrl().doRenderMode("New");
        getT02rekapcostingDetailCtrl().doRenderCombo();
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
		if(getSelectedT02rekapcosting() != null) {
			String vCloseKomisi = "N";
			String vCloseTgs = "N";
			
			
			if(	CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getMasaKomisi()) &&
				CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getTahunKomisi())
			  ){
				List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT02rekapcosting().getMasaKomisi(), getSelectedT02rekapcosting().getTahunKomisi());
				
				if(CommonUtils.isNotEmpty(vStatusClose)){
					for(Object[] aRsltStatus : vStatusClose){
						vCloseKomisi = aRsltStatus[0].toString();
						
					}
				}
			}
			
			if(	CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getMasaTqs()) &&
					CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getTahunTqs())
				  ){
					List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT02rekapcosting().getMasaTqs(), getSelectedT02rekapcosting().getTahunTqs());
					
					if(CommonUtils.isNotEmpty(vStatusClose)){
						for(Object[] aRsltStatus : vStatusClose){
							vCloseTgs = aRsltStatus[1].toString();
							
						}
					}
			}
				
			if (vCloseKomisi.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT02rekapcosting().getMasaKomisi(),getSelectedT02rekapcosting().getTahunKomisi(),"KOMISI"));
			} else if (vCloseTgs.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT02rekapcosting().getMasaTqs(),getSelectedT02rekapcosting().getTahunTqs(),"TQS"));
			} else {
		        try {
					postedT02rekapcosting =(T02rekapcosting) ZksampleBeanUtils.cloneBean(getSelectedT02rekapcosting());
					
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
		        
		    	if (tabPanelT02rekapcostingDetail.getFirstChild() != null) {
		        	getT02rekapcostingDetailCtrl().getBinder().loadAll();
		        }
		        
		        if (getT02rekapcostingDetailCtrl() == null) {
		            Events.sendEvent(new Event("onSelect", tabT02rekapcostingDetail, null));
		        } else if (getT02rekapcostingDetailCtrl().getBinder() == null) {
		            Events.sendEvent(new Event("onSelect", tabT02rekapcostingDetail, null));
		        }
		
		        // set button
		        btnCtrlT02rekapcosting.setInitFormButton();
		
		        // select tab Detail
		        tabT02rekapcostingDetail.setSelected(true);
		        renderTabAndTitle("Edit");
		        
		        state = 2;
		        
		        // set render layar
		        getT02rekapcostingDetailCtrl().doRenderCombo();
		        getT02rekapcostingDetailCtrl().doRenderMode("Edit");
			}
		}
    }
	
    // Saat Button Edit Komisi di klik
	public void onClick$btnEditKomisi(Event event) throws InterruptedException {
		if(getSelectedT02rekapcosting() != null) {
			String vCloseKomisi = "N";
			String vCloseTgs = "N";
			
			
			if(	CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getMasaKomisi()) &&
				CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getTahunKomisi())
			  ){
				List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT02rekapcosting().getMasaKomisi(), getSelectedT02rekapcosting().getTahunKomisi());
				
				if(CommonUtils.isNotEmpty(vStatusClose)){
					for(Object[] aRsltStatus : vStatusClose){
						vCloseKomisi = aRsltStatus[0].toString();
						
					}
				}
			}
			
			if(	CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getMasaTqs()) &&
					CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getTahunTqs())
				  ){
					List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT02rekapcosting().getMasaTqs(), getSelectedT02rekapcosting().getTahunTqs());
					
					if(CommonUtils.isNotEmpty(vStatusClose)){
						for(Object[] aRsltStatus : vStatusClose){
							vCloseTgs = aRsltStatus[1].toString();
							
						}
					}
			}
				
			if (vCloseKomisi.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT02rekapcosting().getMasaKomisi(),getSelectedT02rekapcosting().getTahunKomisi(),"KOMISI"));
			} else if (vCloseTgs.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT02rekapcosting().getMasaTqs(),getSelectedT02rekapcosting().getTahunTqs(),"TQS"));
			} else {
					
		        try {
					postedT02rekapcosting =(T02rekapcosting) ZksampleBeanUtils.cloneBean(getSelectedT02rekapcosting());
					
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
		        
		    	if (tabPanelT02rekapcostingDetail.getFirstChild() != null) {
		        	getT02rekapcostingDetailCtrl().getBinder().loadAll();
		        }
		        
		        if (getT02rekapcostingDetailCtrl() == null) {
		            Events.sendEvent(new Event("onSelect", tabT02rekapcostingDetail, null));
		        } else if (getT02rekapcostingDetailCtrl().getBinder() == null) {
		            Events.sendEvent(new Event("onSelect", tabT02rekapcostingDetail, null));
		        }
		
		        // set button
		        btnCtrlT02rekapcosting.setInitFormButton();
		
		        // select tab Detail
		        tabT02rekapcostingDetail.setSelected(true);
		        renderTabAndTitle("Edit Komisi");
		        
		        state = 2;
		        
		        // set render layar
		        getT02rekapcostingDetailCtrl().doRenderCombo();
		        getT02rekapcostingDetailCtrl().doRenderMode("EditKomisi");
		        getT02rekapcostingDetailCtrl().doRenderDefaultEditKomis();				
									
			}
		}
    }
    
  
    // Saat Button Copy di klik
	public void onClick$btnCopy(Event event) throws InterruptedException {
        try {
			postedT02rekapcosting =(T02rekapcosting) ZksampleBeanUtils.cloneBean(getSelectedT02rekapcosting());
			
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
        
    
    	if (getT02rekapcostingDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT02rekapcostingDetail, null));
        } else if (getT02rekapcostingDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabT02rekapcostingDetail, null));
        }

        T02rekapcosting anT02rekapcosting = new T02rekapcosting();

        // Set Default Value Di sini ------------------------------------- start
        anT02rekapcosting.setCompany(postedT02rekapcosting.getCompany());
        anT02rekapcosting.setNoBso(postedT02rekapcosting.getNoBso());
        anT02rekapcosting.setNoSo(postedT02rekapcosting.getNoSo());
        anT02rekapcosting.setTglSo(postedT02rekapcosting.getTglSo());
        anT02rekapcosting.setNoPoCust(postedT02rekapcosting.getNoPoCust());
        anT02rekapcosting.setCustomer(postedT02rekapcosting.getCustomer());
        anT02rekapcosting.setNoInvoice(postedT02rekapcosting.getNoInvoice());
        anT02rekapcosting.setAmount(postedT02rekapcosting.getAmount());
        anT02rekapcosting.setTglInvoice(postedT02rekapcosting.getTglInvoice());
        anT02rekapcosting.setTglLunas(postedT02rekapcosting.getTglLunas());
        

        anT02rekapcosting.setPcnKomisi(new BigDecimal(0));
        anT02rekapcosting.setPcnTqs(new BigDecimal(0));
        anT02rekapcosting.setAmountKomisi(new BigDecimal(0));
        anT02rekapcosting.setAmountTqs(new BigDecimal(0));
        
        setSelectedT02rekapcosting(anT02rekapcosting);
        // --------------------------------------------------------------- end
                       
        // set button
        btnCtrlT02rekapcosting.setInitFormButton();
        
        // select tab Detail
        tabT02rekapcostingDetail.setSelected(true);
        renderTabAndTitle("Copy");
               
        state = 5;

        if (getT02rekapcostingDetailCtrl().getBinder() != null) {
        	getT02rekapcostingDetailCtrl().getBinder().loadAll();  
        }
        
        // set render layar
        getT02rekapcostingDetailCtrl().doRenderMode("New");
        getT02rekapcostingDetailCtrl().doRenderCombo();
    }
	
	
	
    // Saat Button Delete di klik
    public void onClick$btnDelete(Event event) throws InterruptedException {
    	if (getSelectedT02rekapcosting() != null) {
    		String vCloseKomisi = "N";
			String vCloseTgs = "N";
			
			
			if(	CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getMasaKomisi()) &&
				CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getTahunKomisi())
			  ){
				List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT02rekapcosting().getMasaKomisi(), getSelectedT02rekapcosting().getTahunKomisi());
				
				if(CommonUtils.isNotEmpty(vStatusClose)){
					for(Object[] aRsltStatus : vStatusClose){
						vCloseKomisi = aRsltStatus[0].toString();
						
					}
				}
			}
			
			if(	CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getMasaTqs()) &&
					CommonUtils.isNotEmpty(getSelectedT02rekapcosting().getTahunTqs())
				  ){
					List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(getSelectedT02rekapcosting().getMasaTqs(), getSelectedT02rekapcosting().getTahunTqs());
					
					if(CommonUtils.isNotEmpty(vStatusClose)){
						for(Object[] aRsltStatus : vStatusClose){
							vCloseTgs = aRsltStatus[1].toString();
							
						}
					}
			}
				
			if (vCloseKomisi.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT02rekapcosting().getMasaKomisi(),getSelectedT02rekapcosting().getTahunKomisi(),"KOMISI"));
			} else if (vCloseTgs.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage(msgEdit(getSelectedT02rekapcosting().getMasaTqs(),getSelectedT02rekapcosting().getTahunTqs(),"TQS"));
			} else {			
		        final T02rekapcosting anT02rekapcosting = getSelectedT02rekapcosting();
		        if (anT02rekapcosting != null) {
		
		            // Show a confirm box
		        	String deleteRecord = anT02rekapcosting.getNoPoCust();
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
		                        getT02rekapcostingService().delete(anT02rekapcosting);
		                        setSelectedT02rekapcosting(null);
		                        
		                        // refresh the list
		                        getT02rekapcostingListCtrl().doFillListbox();
		                    } catch (DataAccessException e) {
		                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
		                    }
		                }
		            }
		
		            ) == MultiLineMessageBox.YES) {
		            }
		
		        }
		        
		        btnCtrlT02rekapcosting.setInitInquiryButton();
		        setSelectedT02rekapcosting(null);
		        
		        // refresh the list
		        getT02rekapcostingListCtrl().doFillListbox();
		        tabT02rekapcostingList.setSelected(true);
			}
    	}
    }
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT02rekapcostingDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT02rekapcostingDetailCtrl().getBinder().saveAll();
    	
    	try {
        	if(state == 1) {

        		getT02rekapcostingService().save(getSelectedT02rekapcosting());
        		onClick$btnNew(event);
        	} else if(state == 2) {
        		

        		getT02rekapcostingService().update(getSelectedT02rekapcosting());
	            // refresh the list
	            btnCtrlT02rekapcosting.setInitInquiryButton();
	            getT02rekapcostingListCtrl().doFillListbox();
                
                tabT02rekapcostingList.setSelected(true);
                
                renderTabAndTitle("Save");
        	} else if(state == 5){
        		getT02rekapcostingService().save(getSelectedT02rekapcosting());
        		  // refresh the list
	            btnCtrlT02rekapcosting.setInitInquiryButton();
	            getT02rekapcostingListCtrl().doFillListbox();
                
                tabT02rekapcostingList.setSelected(true);
        		
        		
        	}
        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
       	btnCtrlT02rekapcosting.setInitInquiryButton();
        
        tabT02rekapcostingList.setSelected(true);
        if (getT02rekapcostingListCtrl() != null) {
        	getT02rekapcostingListCtrl().getSearchData();  
        }
        
        renderTabAndTitle("Back");
        
        if (state == 1) {
        	getT02rekapcostingListCtrl().doFillListbox();
        }
        
    	if(postedT02rekapcosting != null){
           	setSelectedT02rekapcosting(postedT02rekapcosting);
           	
           	//Databinding LOV

           	
    	}
    	if(getT02rekapcostingDetailCtrl().binder != null){
    		getT02rekapcostingDetailCtrl().binder.loadAll();
    	} 
    }
    
    // Saat Button Listing di klik
    public void onClick$btnListing(Event event) throws InterruptedException, ParseException {
    	
    	if (getT02rekapcostingPrintCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabT02rekapcostingPrint, null));
        }

        if (tabPanelT02rekapcostingPrint != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT02rekapcostingPrint, this, "ModuleMainController", zulPagePrint);
        }
        
        getT02rekapcostingPrintCtrl().doReadOnlyMode(false);
        
        btnCtrlT02rekapcosting.setInitPrintButton();
        tabT02rekapcostingPrint.setSelected(true);

    }
    

    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
    	String jasperRpt = "/solusi/hapis/webui/reports/tabel/listT02RekapCosting.jasper";
    	
    	new JReportGeneratorWindow(getT02rekapcostingPrintCtrl().getParameterReport(), jasperRpt, "XLS", 1);
    }
    


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT02rekapcostingListCtrl() != null) {
        	getT02rekapcostingListCtrl().getSearchData();        	
        	tabT02rekapcostingList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT02rekapcostingListCtrl() != null) {
        	getT02rekapcostingListCtrl().clearSearchBox();
        	getT02rekapcostingListCtrl().getSearchData();        	
        	tabT02rekapcostingList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Copy") || requestStatus.equals("Edit") || requestStatus.equals("Edit Komisi") || requestStatus.equals("Edit TQS")){
	    	tabT02rekapcostingDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
	        tabT02rekapcostingList.setDisabled(true);
	        tabT02rekapcostingPrint.setDisabled(true);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT02rekapcostingDetail.setLabel(Labels.getLabel("common.Details"));
	        tabT02rekapcostingList.setDisabled(false);
	        tabT02rekapcostingPrint.setDisabled(false);
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT02rekapcosting(T02rekapcosting selectedT02rekapcosting) {
        this.selectedT02rekapcosting = selectedT02rekapcosting;
    }

    public T02rekapcosting getSelectedT02rekapcosting() {
        return this.selectedT02rekapcosting;
    }


	public void setT02rekapcostingService(T02rekapcostingService T02rekapcostingService) {
        this.T02rekapcostingService = T02rekapcostingService;
    }

    public T02rekapcostingService getT02rekapcostingService() {
        return this.T02rekapcostingService;
    }

    public void setT02rekapcostingListCtrl(T02rekapcostingListCtrl T02rekapcostingListCtrl) {
        this.T02rekapcostingListCtrl = T02rekapcostingListCtrl;
    }

    public T02rekapcostingListCtrl getT02rekapcostingListCtrl() {
        return this.T02rekapcostingListCtrl;
    }

    public void setT02rekapcostingDetailCtrl(T02rekapcostingDetailCtrl T02rekapcostingDetailCtrl) {
        this.T02rekapcostingDetailCtrl = T02rekapcostingDetailCtrl;
    }

    public T02rekapcostingDetailCtrl getT02rekapcostingDetailCtrl() {
        return this.T02rekapcostingDetailCtrl;
    }

	public void setT02rekapcostingPrintCtrl(T02rekapcostingPrintCtrl T02rekapcostingPrintCtrl) {
		this.T02rekapcostingPrintCtrl = T02rekapcostingPrintCtrl;
	}

	public T02rekapcostingPrintCtrl getT02rekapcostingPrintCtrl() {
		return this.T02rekapcostingPrintCtrl;
	}

	public void setPostedT02rekapcosting(T02rekapcosting postedT02rekapcosting) {
		this.postedT02rekapcosting = postedT02rekapcosting;
	}

	public T02rekapcosting getPostedT02rekapcosting() {
		return postedT02rekapcosting;
	}

	public T02rekapcostingDownloadCtrl getT02rekapcostingDownloadCtrl() {
		return T02rekapcostingDownloadCtrl;
	}

	public void setT02rekapcostingDownloadCtrl(
			T02rekapcostingDownloadCtrl t02rekapcostingDownloadCtrl) {
		T02rekapcostingDownloadCtrl = t02rekapcostingDownloadCtrl;
	}

	
}
