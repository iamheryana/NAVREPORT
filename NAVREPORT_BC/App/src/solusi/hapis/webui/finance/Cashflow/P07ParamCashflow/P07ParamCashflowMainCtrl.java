package solusi.hapis.webui.finance.Cashflow.P07ParamCashflow;


import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.P07ParamCashflow;
import solusi.hapis.backend.navbi.service.P07ParamCashflowService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

import com.itextpdf.text.DocumentException;

/**
 * User: Heryana
 * Date: 25-11-2024
 */

public class P07ParamCashflowMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowP07ParamCashflowMain; 

    // Tabs
    protected Tabbox tabbox_P07ParamCashflowMain;

    protected Tab tabP07ParamCashflowDetail;
    protected Tabpanel tabPanelP07ParamCashflowDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_P07ParamCashflow_";
    private ButtonStatusCtrl btnCtrlP07ParamCashflow;

    protected Button btnSave;
    protected Button btnCancel;

    // Tab-Controllers for getting the binders
    private P07ParamCashflowDetailCtrl P07ParamCashflowDetailCtrl;

    // Databinding
    private P07ParamCashflow selectedP07ParamCashflow;
    private P07ParamCashflow postedP07ParamCashflow;

    // ServiceDAOs / Domain Classes
    private P07ParamCashflowService P07ParamCashflowService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/Cashflow/P07ParamCashflow/P07ParamCashflowDetail.zul";
    
    /**
     * default constructor.<br>
     */
    public P07ParamCashflowMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowP07ParamCashflowMain(Event event) throws Exception {
        windowP07ParamCashflowMain.setContentStyle("padding:0px;");

        btnCtrlP07ParamCashflow = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, null, null, null, null, null, btnSave, btnCancel, null, null);

        
        btnCtrlP07ParamCashflow.setInitFormButton();
      
        Map<Object, Object> parameterInput = new HashMap<Object, Object>();	
        
        List<P07ParamCashflow> tempList = getP07ParamCashflowService().getListP07ParamCashflow(parameterInput);
        if(tempList != null){
			setSelectedP07ParamCashflow(tempList.get(0));    
		}
		
		
       
        tabP07ParamCashflowDetail.setSelected(true);
      

        renderTabAndTitle("Edit");

        
        // set render layar


        if (tabPanelP07ParamCashflowDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP07ParamCashflowDetail, this, "ModuleMainController", zulPageDetail);
        }
        
        
        getP07ParamCashflowDetailCtrl().doRenderMode("Edit");
        getP07ParamCashflowDetailCtrl().doRenderCheckBox();
        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    

    //Saat Tab Detil di select
    public void onSelect$tabP07ParamCashflowDetail(Event event) throws IOException {
        if (tabPanelP07ParamCashflowDetail.getFirstChild() != null) {
        	tabP07ParamCashflowDetail.setSelected(true);            
        	
        	// Render Inisialisasi posisi awal
            getP07ParamCashflowDetailCtrl().doRenderMode("View");   
            getP07ParamCashflowDetailCtrl().doRenderCheckBox();
            btnCtrlP07ParamCashflow.setInitInquiryButton();
            
		
            
            
            
            if (getP07ParamCashflowDetailCtrl().getBinder() != null) {
            	getP07ParamCashflowDetailCtrl().getBinder().loadAll();  
            }
             
            return;
        }
       
        if (tabPanelP07ParamCashflowDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP07ParamCashflowDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getP07ParamCashflowDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getP07ParamCashflowDetailCtrl().getBinder().saveAll();
    	
    	try {

        		getP07ParamCashflowService().update(getSelectedP07ParamCashflow());

        } catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(CustomErrorDB.getErrorMsg(e.getRootCause().toString()));
            return;
        }
    }
    
    // Saat Button Back di klik
    public void onClick$btnCancel(Event event) throws InterruptedException {
      	
    }
    
    
    // Saat Button OK di klik
    public void onClick$btnOK(Event event) throws InterruptedException, IOException, DocumentException, JRException {
    	// Nama dan lokasi Report
    	//String jasperRpt = "/solusi/hapis/webui/crossmoduletabel/listing/l_P07ParamCashflow.jasper";
    	
    }
    


   // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabP07ParamCashflowDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabP07ParamCashflowDetail.setLabel(Labels.getLabel("common.Details"));
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedP07ParamCashflow(P07ParamCashflow selectedP07ParamCashflow) {
        this.selectedP07ParamCashflow = selectedP07ParamCashflow;
    }

    public P07ParamCashflow getSelectedP07ParamCashflow() {
        return this.selectedP07ParamCashflow;
    }


	public void setP07ParamCashflowService(P07ParamCashflowService P07ParamCashflowService) {
        this.P07ParamCashflowService = P07ParamCashflowService;
    }

    public P07ParamCashflowService getP07ParamCashflowService() {
        return this.P07ParamCashflowService;
    }

    public void setP07ParamCashflowDetailCtrl(P07ParamCashflowDetailCtrl P07ParamCashflowDetailCtrl) {
        this.P07ParamCashflowDetailCtrl = P07ParamCashflowDetailCtrl;
    }

    public P07ParamCashflowDetailCtrl getP07ParamCashflowDetailCtrl() {
        return this.P07ParamCashflowDetailCtrl;
    }

	public void setPostedP07ParamCashflow(P07ParamCashflow postedP07ParamCashflow) {
		this.postedP07ParamCashflow = postedP07ParamCashflow;
	}

	public P07ParamCashflow getPostedP07ParamCashflow() {
		return postedP07ParamCashflow;
	}

}
