package solusi.hapis.webui.finance.P04ParamKomisi;


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

import solusi.hapis.backend.navbi.model.P04ParamKomisi;
import solusi.hapis.backend.navbi.service.P04ParamKomisiService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

import com.itextpdf.text.DocumentException;

/**
 * User: Heryana
 * Date: 13-04-2021
 */

public class P04ParamKomisiMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowP04ParamKomisiMain; 

    // Tabs
    protected Tabbox tabbox_P04ParamKomisiMain;

    protected Tab tabP04ParamKomisiDetail;
    protected Tabpanel tabPanelP04ParamKomisiDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_P04ParamKomisi_";
    private ButtonStatusCtrl btnCtrlP04ParamKomisi;

    protected Button btnSave;
    protected Button btnCancel;

    // Tab-Controllers for getting the binders
    private P04ParamKomisiDetailCtrl P04ParamKomisiDetailCtrl;

    // Databinding
    private P04ParamKomisi selectedP04ParamKomisi;
    private P04ParamKomisi postedP04ParamKomisi;

    // ServiceDAOs / Domain Classes
    private P04ParamKomisiService P04ParamKomisiService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/finance/P04ParamKomisi/P04ParamKomisiDetail.zul";
    
    /**
     * default constructor.<br>
     */
    public P04ParamKomisiMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowP04ParamKomisiMain(Event event) throws Exception {
        windowP04ParamKomisiMain.setContentStyle("padding:0px;");

        btnCtrlP04ParamKomisi = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, null, null, null, null, null, btnSave, btnCancel, null, null);

        
        btnCtrlP04ParamKomisi.setInitFormButton();
      
        Map<Object, Object> parameterInput = new HashMap<Object, Object>();	
        
        List<P04ParamKomisi> tempList = getP04ParamKomisiService().getListP04ParamKomisi(parameterInput);
        if(tempList != null){
			setSelectedP04ParamKomisi(tempList.get(0));    
		}
		
		
        

        tabP04ParamKomisiDetail.setSelected(true);
      

        renderTabAndTitle("Edit");

        
        // set render layar


        if (tabPanelP04ParamKomisiDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP04ParamKomisiDetail, this, "ModuleMainController", zulPageDetail);
        }
        
        
        getP04ParamKomisiDetailCtrl().doRenderMode("Edit");
        getP04ParamKomisiDetailCtrl().doRenderCheckBox();
        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    

    //Saat Tab Detil di select
    public void onSelect$tabP04ParamKomisiDetail(Event event) throws IOException {
        if (tabPanelP04ParamKomisiDetail.getFirstChild() != null) {
        	tabP04ParamKomisiDetail.setSelected(true);            
        	
        	// Render Inisialisasi posisi awal
            getP04ParamKomisiDetailCtrl().doRenderMode("View");   
            getP04ParamKomisiDetailCtrl().doRenderCheckBox();
            btnCtrlP04ParamKomisi.setInitInquiryButton();
            
		
            
            
            
            if (getP04ParamKomisiDetailCtrl().getBinder() != null) {
            	getP04ParamKomisiDetailCtrl().getBinder().loadAll();  
            }
             
            return;
        }
       
        if (tabPanelP04ParamKomisiDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP04ParamKomisiDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getP04ParamKomisiDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getP04ParamKomisiDetailCtrl().getBinder().saveAll();
    	
    	try {

        		getP04ParamKomisiService().update(getSelectedP04ParamKomisi());

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
    	//String jasperRpt = "/solusi/hapis/webui/crossmoduletabel/listing/l_P04ParamKomisi.jasper";
    	
    }
    


   // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabP04ParamKomisiDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabP04ParamKomisiDetail.setLabel(Labels.getLabel("common.Details"));
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedP04ParamKomisi(P04ParamKomisi selectedP04ParamKomisi) {
        this.selectedP04ParamKomisi = selectedP04ParamKomisi;
    }

    public P04ParamKomisi getSelectedP04ParamKomisi() {
        return this.selectedP04ParamKomisi;
    }


	public void setP04ParamKomisiService(P04ParamKomisiService P04ParamKomisiService) {
        this.P04ParamKomisiService = P04ParamKomisiService;
    }

    public P04ParamKomisiService getP04ParamKomisiService() {
        return this.P04ParamKomisiService;
    }

    public void setP04ParamKomisiDetailCtrl(P04ParamKomisiDetailCtrl P04ParamKomisiDetailCtrl) {
        this.P04ParamKomisiDetailCtrl = P04ParamKomisiDetailCtrl;
    }

    public P04ParamKomisiDetailCtrl getP04ParamKomisiDetailCtrl() {
        return this.P04ParamKomisiDetailCtrl;
    }

	public void setPostedP04ParamKomisi(P04ParamKomisi postedP04ParamKomisi) {
		this.postedP04ParamKomisi = postedP04ParamKomisi;
	}

	public P04ParamKomisi getPostedP04ParamKomisi() {
		return postedP04ParamKomisi;
	}

}
