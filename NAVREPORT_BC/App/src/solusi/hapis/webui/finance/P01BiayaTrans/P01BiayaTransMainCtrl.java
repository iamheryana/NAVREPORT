package solusi.hapis.webui.finance.P01BiayaTrans;


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

import solusi.hapis.backend.navbi.model.P01BiayaTrans;
import solusi.hapis.backend.navbi.service.P01BiayaTransService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

import com.itextpdf.text.DocumentException;

/**
 * User: Heryana
 * Date: 31-01-2020
 */

public class P01BiayaTransMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowP01BiayaTransMain; 

    // Tabs
    protected Tabbox tabbox_P01BiayaTransMain;

    protected Tab tabP01BiayaTransDetail;
    protected Tabpanel tabPanelP01BiayaTransDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_P01BiayaTrans_";
    private ButtonStatusCtrl btnCtrlP01BiayaTrans;

    protected Button btnSave;
    protected Button btnCancel;

    // Tab-Controllers for getting the binders
    private P01BiayaTransDetailCtrl P01BiayaTransDetailCtrl;

    // Databinding
    private P01BiayaTrans selectedP01BiayaTrans;
    private P01BiayaTrans postedP01BiayaTrans;

    // ServiceDAOs / Domain Classes
    private P01BiayaTransService P01BiayaTransService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/P01BiayaTrans/P01BiayaTransDetail.zul";
    
    /**
     * default constructor.<br>
     */
    public P01BiayaTransMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowP01BiayaTransMain(Event event) throws Exception {
        windowP01BiayaTransMain.setContentStyle("padding:0px;");

        btnCtrlP01BiayaTrans = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, null, null, null, null, null, btnSave, btnCancel, null, null);

        
        btnCtrlP01BiayaTrans.setInitFormButton();
      
        Map<Object, Object> parameterInput = new HashMap<Object, Object>();	
        
        List<P01BiayaTrans> tempList = getP01BiayaTransService().getListP01BiayaTrans(parameterInput);
        if(tempList != null){
			setSelectedP01BiayaTrans(tempList.get(0));    
		}
		
		
        

        tabP01BiayaTransDetail.setSelected(true);
      

        renderTabAndTitle("Edit");

        
        // set render layar


        if (tabPanelP01BiayaTransDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP01BiayaTransDetail, this, "ModuleMainController", zulPageDetail);
        }
        
        
        getP01BiayaTransDetailCtrl().doRenderMode("Edit");
        getP01BiayaTransDetailCtrl().doRenderCheckBox();
        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    

    //Saat Tab Detil di select
    public void onSelect$tabP01BiayaTransDetail(Event event) throws IOException {
        if (tabPanelP01BiayaTransDetail.getFirstChild() != null) {
        	tabP01BiayaTransDetail.setSelected(true);            
        	
        	// Render Inisialisasi posisi awal
            getP01BiayaTransDetailCtrl().doRenderMode("View");   
            getP01BiayaTransDetailCtrl().doRenderCheckBox();
            btnCtrlP01BiayaTrans.setInitInquiryButton();
            
		
            
            
            
            if (getP01BiayaTransDetailCtrl().getBinder() != null) {
            	getP01BiayaTransDetailCtrl().getBinder().loadAll();  
            }
             
            return;
        }
       
        if (tabPanelP01BiayaTransDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP01BiayaTransDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getP01BiayaTransDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getP01BiayaTransDetailCtrl().getBinder().saveAll();
    	
    	try {

        		getP01BiayaTransService().update(getSelectedP01BiayaTrans());

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
    	//String jasperRpt = "/solusi/hapis/webui/crossmoduletabel/listing/l_P01BiayaTrans.jasper";
    	
    }
    


   // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabP01BiayaTransDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabP01BiayaTransDetail.setLabel(Labels.getLabel("common.Details"));
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedP01BiayaTrans(P01BiayaTrans selectedP01BiayaTrans) {
        this.selectedP01BiayaTrans = selectedP01BiayaTrans;
    }

    public P01BiayaTrans getSelectedP01BiayaTrans() {
        return this.selectedP01BiayaTrans;
    }


	public void setP01BiayaTransService(P01BiayaTransService P01BiayaTransService) {
        this.P01BiayaTransService = P01BiayaTransService;
    }

    public P01BiayaTransService getP01BiayaTransService() {
        return this.P01BiayaTransService;
    }

    public void setP01BiayaTransDetailCtrl(P01BiayaTransDetailCtrl P01BiayaTransDetailCtrl) {
        this.P01BiayaTransDetailCtrl = P01BiayaTransDetailCtrl;
    }

    public P01BiayaTransDetailCtrl getP01BiayaTransDetailCtrl() {
        return this.P01BiayaTransDetailCtrl;
    }

	public void setPostedP01BiayaTrans(P01BiayaTrans postedP01BiayaTrans) {
		this.postedP01BiayaTrans = postedP01BiayaTrans;
	}

	public P01BiayaTrans getPostedP01BiayaTrans() {
		return postedP01BiayaTrans;
	}

}
