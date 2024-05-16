package solusi.hapis.webui.logistic.penjualan.T26CetakSroso;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T26CetakSroso;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.T26CetakSrosoService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 02-11-2023
 */

public class T26CetakSrosoMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT26CetakSrosoMain; 

    // Tabs
    protected Tabbox tabbox_T26CetakSrosoMain;
    protected Tab tabT26CetakSrosoList;
//   protected Tab tabT26CetakSrosoDetail;
//    protected Tab tabT26CetakSrosoPrint;
    protected Tabpanel tabPanelT26CetakSrosoList;
    protected Tabpanel tabPanelT26CetakSrosoDetail;
//    protected Tabpanel tabPanelT26CetakSrosoPrint;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T26CetakSroso_";
    private ButtonStatusCtrl btnCtrlT26CetakSroso;
//    protected Button btnNew;
//    protected Button btnEdit;
//    protected Button btnDelete;
//    protected Button btnListing;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnOK;
    protected Button btnSearch;
    protected Button btnClear;

    
    protected Button btnPrint;
    protected Button btnProses;
    // Tab-Controllers for getting the binders
    private T26CetakSrosoListCtrl t26CetakSrosoListCtrl;
//    private T26CetakSrosoDetailCtrl T26CetakSrosoDetailCtrl;

    // Databinding
    private T26CetakSroso selectedT26CetakSroso;
    private T26CetakSroso postedT26CetakSroso;
    
    //Databinding LOV 


    // ServiceDAOs / Domain Classes
    
    private T26CetakSrosoService t26CetakSrosoService;

    private CallStoreProcOrFuncService callStoreProcOrFuncService;
    // Zul
//	private String zulPageDetail = "/WEB-INF/pages/logistic/penjualan/T26CetakSroso/T26CetakSrosoDetail.zul";
	private String zulPageList = "/WEB-INF/pages/logistic/penjualan/T26CetakSroso/T26CetakSrosoList.zul";


    /**
     * Form State <br/>
     * 0 = inquiry <br/>
     * 1 = new <br/>
     * 2 = edit <br/>
     * 3 = delete <br/>
     */
    // private int state = 0;
    
    /**
     * default constructor.<br>
     */
    public T26CetakSrosoMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT26CetakSrosoMain(Event event) throws Exception {
        windowT26CetakSrosoMain.setContentStyle("padding:0px;");

        btnCtrlT26CetakSroso = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, null, null, null, null, btnOK, btnSave, btnCancel, btnSearch, btnClear);
        btnCtrlT26CetakSroso.addButtonOther1(btnPrint, "Print");
        btnCtrlT26CetakSroso.addButtonOther2(btnProses, "Proses");
        
        
        btnCtrlT26CetakSroso.setInitInquiryButton();
        
        
        

        tabT26CetakSrosoList.setSelected(true);

        if (tabPanelT26CetakSrosoList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT26CetakSrosoList, this, "ModuleMainController", zulPageList);
        }
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    //Saat Tab Inquiry di select
    public void onSelect$tabT26CetakSrosoList(Event event) throws IOException, InterruptedException {
    	if (tabPanelT26CetakSrosoList.getFirstChild() != null) {

            tabT26CetakSrosoList.setSelected(true);
        	btnCtrlT26CetakSroso.setInitInquiryButton();
        	getT26CetakSrosoListCtrl().doFillListbox();
            
            return;
        }

        if (tabPanelT26CetakSrosoList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT26CetakSrosoList, this, "ModuleMainController", zulPageList);
        }
    }

    //Saat Tab Detil di select
//    public void onSelect$tabT26CetakSrosoDetail(Event event) throws IOException {
//        if (tabPanelT26CetakSrosoDetail.getFirstChild() != null) {
//        	tabT26CetakSrosoDetail.setSelected(true);            
//        	
//        	//getT26CetakSrosoDetailCtrl().doRenderCombo();
//        	// Render Inisialisasi posisi awal
//            getT26CetakSrosoDetailCtrl().doRenderMode("View");   
//            
//            btnCtrlT26CetakSroso.setInitInquiryButton();
//
//            if (getT26CetakSrosoDetailCtrl().getBinder() != null) {
//            	getT26CetakSrosoDetailCtrl().getBinder().loadAll();  
//            }
//                        
//            return;
//        }
//       
//        if (tabPanelT26CetakSrosoDetail != null) {
//            ZksampleCommonUtils.createTabPanelContent(tabPanelT26CetakSrosoDetail, this, "ModuleMainController", zulPageDetail);
//        }
//    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button New di klik
//    public void onClick$btnNew(Event event) throws InterruptedException {
//    	
//    	if (getT26CetakSrosoDetailCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabT26CetakSrosoDetail, null));
//        } else if (getT26CetakSrosoDetailCtrl().getBinder() == null) {
//            Events.sendEvent(new Event("onSelect", tabT26CetakSrosoDetail, null));
//        }
//
//        T26CetakSroso anT26CetakSroso = new T26CetakSroso();
//
//        // Set Default Value Di sini ------------------------------------- start
//
//        
//        setSelectedT26CetakSroso(anT26CetakSroso);
//        
//        // --------------------------------------------------------------- end
//                       
//        // set button
//        btnCtrlT26CetakSroso.setInitFormButton();
//        
//        // select tab Detail
//        tabT26CetakSrosoDetail.setSelected(true);
//        renderTabAndTitle("New");
//               
//        state = 1;
//
//        if (getT26CetakSrosoDetailCtrl().getBinder() != null) {
//        	getT26CetakSrosoDetailCtrl().getBinder().loadAll();  
//        }
//        
//        // set render layar
//        getT26CetakSrosoDetailCtrl().doRenderMode("New");
//        //getT26CetakSrosoDetailCtrl().doRenderCombo();
//    }
    
    // Saat Button Edit di klik
//	public void onClick$btnEdit(Event event) throws InterruptedException {
//        try {
//			postedT26CetakSroso =(T26CetakSroso) ZksampleBeanUtils.cloneBean(getSelectedT26CetakSroso());
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
//        
//    	if (tabPanelT26CetakSrosoDetail.getFirstChild() != null) {
//        	getT26CetakSrosoDetailCtrl().getBinder().loadAll();
//        }
//        
//        if (getT26CetakSrosoDetailCtrl() == null) {
//            Events.sendEvent(new Event("onSelect", tabT26CetakSrosoDetail, null));
//        } else if (getT26CetakSrosoDetailCtrl().getBinder() == null) {
//            Events.sendEvent(new Event("onSelect", tabT26CetakSrosoDetail, null));
//        }
//
//        // set button
//        btnCtrlT26CetakSroso.setInitFormButton();
//
//        // select tab Detail
//        tabT26CetakSrosoDetail.setSelected(true);
//        renderTabAndTitle("Edit");
//        
//        state = 2;
//        
//        // set render layar
//        //getT26CetakSrosoDetailCtrl().doRenderCombo();
//        getT26CetakSrosoDetailCtrl().doRenderMode("Edit");
//    }
//    
	
    // Saat Button Delete di klik
//    public void onClick$btnDelete(Event event) throws InterruptedException {
//    	if (getSelectedT26CetakSroso() != null) {
//	        final T26CetakSroso anT26CetakSroso = getSelectedT26CetakSroso();
//	        if (anT26CetakSroso != null) {
//	
//	            // Show a confirm box
//	        	String deleteRecord = anT26CetakSroso.getCustNo() ;
//	            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + deleteRecord;
//	            final String title = Labels.getLabel("message.Deleting.Record");
//	
//	            MultiLineMessageBox.doSetTemplate();
//	            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
//	                @Override
//	                public void onEvent(Event evt) {
//	                    switch (((Integer) evt.getData()).intValue()) {
//	                        case MultiLineMessageBox.YES:
//	                            try {
//	                                deleteBean();
//	                            } catch (InterruptedException e) {
//	                                e.printStackTrace();
//	                            }
//	                            break;
//	                        case MultiLineMessageBox.NO:
//	                            break;
//	                    }
//	                }
//	
//	                private void deleteBean() throws InterruptedException {
//	                	try {
//	                        getT26CetakSrosoService().delete(anT26CetakSroso);
//	                        setSelectedT26CetakSroso(null);
//	                        
//	                        // refresh the list
//	                        getT26CetakSrosoListCtrl().doFillListbox();
//	                    } catch (DataAccessException e) {
//	                    	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
//	                    }
//	                }
//	            }
//	
//	            ) == MultiLineMessageBox.YES) {
//	            }
//	
//	        }
//	        
//	        btnCtrlT26CetakSroso.setInitInquiryButton();
//	        setSelectedT26CetakSroso(null);
//	        
//	        // refresh the list
//	        getT26CetakSrosoListCtrl().doFillListbox();
//	        tabT26CetakSrosoList.setSelected(true);
//    	}
//    }
    
    
    // Saat Button Save di klik
//    public void onClick$btnSave(Event event) throws InterruptedException {
//    	String vErrMsg = getT26CetakSrosoDetailCtrl().validasiBusinessRules();
//    	
//    	if(vErrMsg != null){
//    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
//    		return;
//    	}
//    	
//    	getT26CetakSrosoDetailCtrl().getBinder().saveAll();
//    	
//    	try {
//        	if(state == 1) {
//
//        		getT26CetakSrosoService().save(getSelectedT26CetakSroso());
//        		onClick$btnNew(event);
//        	} else if(state == 2) {
//        		
//
//        		getT26CetakSrosoService().update(getSelectedT26CetakSroso());
//	            // refresh the list
//	            btnCtrlT26CetakSroso.setInitInquiryButton();
//	            getT26CetakSrosoListCtrl().doFillListbox();
//                
//                tabT26CetakSrosoList.setSelected(true);
//                
//                renderTabAndTitle("Save");
//        	}
//        } catch (DataAccessException e) {
//        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
//            return;
//        }
//    }
    
    // Saat Button Back di klik
//    public void onClick$btnCancel(Event event) throws InterruptedException {
//       	btnCtrlT26CetakSroso.setInitInquiryButton();
//        
//        tabT26CetakSrosoList.setSelected(true);
//        
//        renderTabAndTitle("Back");
//        
//        if (state == 1) {
//        	getT26CetakSrosoListCtrl().doFillListbox();
//        }
//        
//    	if(postedT26CetakSroso != null){
//           	setSelectedT26CetakSroso(postedT26CetakSroso);
//           	
//           	//Databinding LOV
//
//           	
//    	}
//    	if(getT26CetakSrosoDetailCtrl().binder != null){
//    		getT26CetakSrosoDetailCtrl().binder.loadAll();
//    	} 
//    }
//    
//
    
    public void onClick$btnProses(Event event) throws InterruptedException {
    	
    	
    	@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callProsesGenerateSROSO("MANUAL");
    	
    	
    	onClick$btnSearch(event);
    	
    	Messagebox.show("Proses Berhasil");
    	
    }
    
    
    
    
	@SuppressWarnings("unchecked")
	public void onClick$btnPrint(Event event) throws InterruptedException {
		if(getSelectedT26CetakSroso() != null) {
			if(	CommonUtils.isNotEmpty(getSelectedT26CetakSroso().getT26Id())){
			    	try {
			    		long vT26Id =getSelectedT26CetakSroso().getT26Id();
			    		
			    		

			    		
			    		@SuppressWarnings("rawtypes")
						List<Map> params = new ArrayList<Map>();
			    		List<String> jasperRpts = new ArrayList<String>();
			    		String [] vSheetName = new String[10]; 
			    		
			    		
			    		jasperRpts.add("/solusi/hapis/webui/reports/logistic/penjualan/030601_01_LapAutogenerateSR.jasper");
			    		
			    		@SuppressWarnings("rawtypes")
						Map paramSR = new HashMap();		
			    		
			    		paramSR.put("T26Id",  Long.toString(vT26Id));		
			    			
			    		
			    		params.add(paramSR);
			    		vSheetName[0] = "SALES REVENUE";
			    		
			    		
			    		jasperRpts.add("/solusi/hapis/webui/reports/logistic/penjualan/030601_02_LapAutogenerateOSO.jasper");
			    		
			    		@SuppressWarnings("rawtypes")
						Map paramOutSO = new HashMap();		
			    		
			    		paramOutSO.put("T26Id",  Long.toString(vT26Id));		
			    		paramOutSO.put("Detail",  "Y");
			    		
			    		params.add(paramOutSO);
			    		vSheetName[1] = "OUT-SO-DTL";
			    		
			    		
			    		jasperRpts.add("/solusi/hapis/webui/reports/logistic/penjualan/030601_02_LapAutogenerateOSO.jasper");
			    		
			    		@SuppressWarnings("rawtypes")
						Map paramOutSOSum = new HashMap();		
			    		
			    		paramOutSOSum.put("T26Id",  Long.toString(vT26Id));		
			    		paramOutSOSum.put("Detail",  "N");
			    		
			    		params.add(paramOutSOSum);
			    		vSheetName[2] = "OUT-SO-SUM";
			    		
			    		new JReportGeneratorWindow(params, jasperRpts, vSheetName); 
			    		
		        		
			        	
			        } catch (DataAccessException e) {
			        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
			            return;
			        }
			}			
		}		 
	}


    // Saat Button Search di klik
    public void onClick$btnSearch(Event event) throws InterruptedException {
        if (getT26CetakSrosoListCtrl() != null) {
        	getT26CetakSrosoListCtrl().getSearchData();        	
        	tabT26CetakSrosoList.setSelected(true);
        }
    }

    // Saat Button Clear di klik
    public void onClick$btnClear(Event event) throws InterruptedException {
        if (getT26CetakSrosoListCtrl() != null) {
        	getT26CetakSrosoListCtrl().clearSearchBox();
        	getT26CetakSrosoListCtrl().getSearchData();        	
        	tabT26CetakSrosoList.setSelected(true);
        }
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    @SuppressWarnings("unused")
	private void renderTabAndTitle(String requestStatus){
//    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
//	    	tabT26CetakSrosoDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
//	        tabT26CetakSrosoList.setDisabled(true);
//
//    	}
//    	
//    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
//	    	tabT26CetakSrosoDetail.setLabel(Labels.getLabel("common.Details"));
//	        tabT26CetakSrosoList.setDisabled(false);
//
//    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT26CetakSroso(T26CetakSroso selectedT26CetakSroso) {
        this.selectedT26CetakSroso = selectedT26CetakSroso;
    }

    public T26CetakSroso getSelectedT26CetakSroso() {
        return this.selectedT26CetakSroso;
    }


	public void setT26CetakSrosoService(T26CetakSrosoService t26CetakSrosoService) {
        this.t26CetakSrosoService = t26CetakSrosoService;
    }

    public T26CetakSrosoService getT26CetakSrosoService() {
        return this.t26CetakSrosoService;
    }

    public void setT26CetakSrosoListCtrl(T26CetakSrosoListCtrl t26CetakSrosoListCtrl) {
        this.t26CetakSrosoListCtrl = t26CetakSrosoListCtrl;
    }

    public T26CetakSrosoListCtrl getT26CetakSrosoListCtrl() {
        return this.t26CetakSrosoListCtrl;
    }

//    public void setT26CetakSrosoDetailCtrl(T26CetakSrosoDetailCtrl T26CetakSrosoDetailCtrl) {
//        this.T26CetakSrosoDetailCtrl = T26CetakSrosoDetailCtrl;
//    }
//
//    public T26CetakSrosoDetailCtrl getT26CetakSrosoDetailCtrl() {
//        return this.T26CetakSrosoDetailCtrl;
//    }

	public void setPostedT26CetakSroso(T26CetakSroso postedT26CetakSroso) {
		this.postedT26CetakSroso = postedT26CetakSroso;
	}

	public T26CetakSroso getPostedT26CetakSroso() {
		return postedT26CetakSroso;
	}

}
