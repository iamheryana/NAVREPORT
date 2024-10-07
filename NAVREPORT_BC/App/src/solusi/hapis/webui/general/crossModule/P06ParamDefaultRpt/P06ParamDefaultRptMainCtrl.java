package solusi.hapis.webui.general.crossModule.P06ParamDefaultRpt;


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

import solusi.hapis.backend.navbi.model.P06ParamDefaultRpt;
import solusi.hapis.backend.navbi.service.P06ParamDefaultRptService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

import com.itextpdf.text.DocumentException;

/**
 * User: Heryana
 * Date: 07-10-2024
 */

public class P06ParamDefaultRptMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowP06ParamDefaultRptMain; 

    // Tabs
    protected Tabbox tabbox_P06ParamDefaultRptMain;

    protected Tab tabP06ParamDefaultRptDetail;
    protected Tabpanel tabPanelP06ParamDefaultRptDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_P06ParamDefaultRpt_";
    private ButtonStatusCtrl btnCtrlP06ParamDefaultRpt;

    protected Button btnSave;
    protected Button btnCancel;

    // Tab-Controllers for getting the binders
    private P06ParamDefaultRptDetailCtrl P06ParamDefaultRptDetailCtrl;

    // Databinding
    private P06ParamDefaultRpt selectedP06ParamDefaultRpt;
    private P06ParamDefaultRpt postedP06ParamDefaultRpt;

    // ServiceDAOs / Domain Classes
    private P06ParamDefaultRptService P06ParamDefaultRptService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/general/crossModule/P06ParamDefaultRpt/P06ParamDefaultRptDetail.zul";
    
    /**
     * default constructor.<br>
     */
    public P06ParamDefaultRptMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowP06ParamDefaultRptMain(Event event) throws Exception {
        windowP06ParamDefaultRptMain.setContentStyle("padding:0px;");

        btnCtrlP06ParamDefaultRpt = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, null, null, null, null, null, btnSave, btnCancel, null, null);

        
        btnCtrlP06ParamDefaultRpt.setInitFormButton();
      
        Map<Object, Object> parameterInput = new HashMap<Object, Object>();	
        
        List<P06ParamDefaultRpt> tempList = getP06ParamDefaultRptService().getListP06ParamDefaultRpt(parameterInput);
        if(tempList != null){
			setSelectedP06ParamDefaultRpt(tempList.get(0));    
		}
		
		
       
        tabP06ParamDefaultRptDetail.setSelected(true);
      

        renderTabAndTitle("Edit");

        
        // set render layar


        if (tabPanelP06ParamDefaultRptDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP06ParamDefaultRptDetail, this, "ModuleMainController", zulPageDetail);
        }
        
        
        getP06ParamDefaultRptDetailCtrl().doRenderMode("Edit");
        getP06ParamDefaultRptDetailCtrl().doRenderCheckBox();
        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    

    //Saat Tab Detil di select
    public void onSelect$tabP06ParamDefaultRptDetail(Event event) throws IOException {
        if (tabPanelP06ParamDefaultRptDetail.getFirstChild() != null) {
        	tabP06ParamDefaultRptDetail.setSelected(true);            
        	
        	// Render Inisialisasi posisi awal
            getP06ParamDefaultRptDetailCtrl().doRenderMode("View");   
            getP06ParamDefaultRptDetailCtrl().doRenderCheckBox();
            btnCtrlP06ParamDefaultRpt.setInitInquiryButton();
            
		
            
            
            
            if (getP06ParamDefaultRptDetailCtrl().getBinder() != null) {
            	getP06ParamDefaultRptDetailCtrl().getBinder().loadAll();  
            }
             
            return;
        }
       
        if (tabPanelP06ParamDefaultRptDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP06ParamDefaultRptDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getP06ParamDefaultRptDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getP06ParamDefaultRptDetailCtrl().getBinder().saveAll();
    	
    	try {

        		getP06ParamDefaultRptService().update(getSelectedP06ParamDefaultRpt());

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
    	//String jasperRpt = "/solusi/hapis/webui/crossmoduletabel/listing/l_P06ParamDefaultRpt.jasper";
    	
    }
    


   // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabP06ParamDefaultRptDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabP06ParamDefaultRptDetail.setLabel(Labels.getLabel("common.Details"));
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedP06ParamDefaultRpt(P06ParamDefaultRpt selectedP06ParamDefaultRpt) {
        this.selectedP06ParamDefaultRpt = selectedP06ParamDefaultRpt;
    }

    public P06ParamDefaultRpt getSelectedP06ParamDefaultRpt() {
        return this.selectedP06ParamDefaultRpt;
    }


	public void setP06ParamDefaultRptService(P06ParamDefaultRptService P06ParamDefaultRptService) {
        this.P06ParamDefaultRptService = P06ParamDefaultRptService;
    }

    public P06ParamDefaultRptService getP06ParamDefaultRptService() {
        return this.P06ParamDefaultRptService;
    }

    public void setP06ParamDefaultRptDetailCtrl(P06ParamDefaultRptDetailCtrl P06ParamDefaultRptDetailCtrl) {
        this.P06ParamDefaultRptDetailCtrl = P06ParamDefaultRptDetailCtrl;
    }

    public P06ParamDefaultRptDetailCtrl getP06ParamDefaultRptDetailCtrl() {
        return this.P06ParamDefaultRptDetailCtrl;
    }

	public void setPostedP06ParamDefaultRpt(P06ParamDefaultRpt postedP06ParamDefaultRpt) {
		this.postedP06ParamDefaultRpt = postedP06ParamDefaultRpt;
	}

	public P06ParamDefaultRpt getPostedP06ParamDefaultRpt() {
		return postedP06ParamDefaultRpt;
	}

}
