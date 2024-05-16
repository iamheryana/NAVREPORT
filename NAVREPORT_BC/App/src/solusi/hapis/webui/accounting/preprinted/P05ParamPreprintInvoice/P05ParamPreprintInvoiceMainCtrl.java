package solusi.hapis.webui.accounting.preprinted.P05ParamPreprintInvoice;

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

import solusi.hapis.backend.navbi.model.P05ParamPreprintInvoice;
import solusi.hapis.backend.navbi.service.P05ParamPreprintInvoiceService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

import com.itextpdf.text.DocumentException;

/**
 * User: Heryana
 * Date: 27-11-2023
 */

public class P05ParamPreprintInvoiceMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowP05ParamPreprintInvoiceMain; 

    // Tabs
    protected Tabbox tabbox_P05ParamPreprintInvoiceMain;

    protected Tab tabP05ParamPreprintInvoiceDetail;
    protected Tabpanel tabPanelP05ParamPreprintInvoiceDetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_P05ParamPreprintInvoice_";
    private ButtonStatusCtrl btnCtrlP05ParamPreprintInvoice;

    protected Button btnSave;
    protected Button btnCancel;

    // Tab-Controllers for getting the binders
    private P05ParamPreprintInvoiceDetailCtrl P05ParamPreprintInvoiceDetailCtrl;

    // Databinding
    private P05ParamPreprintInvoice selectedP05ParamPreprintInvoice;
    private P05ParamPreprintInvoice postedP05ParamPreprintInvoice;

    // ServiceDAOs / Domain Classes
    private P05ParamPreprintInvoiceService P05ParamPreprintInvoiceService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/accounting/preprinted/P05ParamPreprintInvoice/P05ParamPreprintInvoiceDetail.zul";
    
    /**
     * default constructor.<br>
     */
    public P05ParamPreprintInvoiceMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowP05ParamPreprintInvoiceMain(Event event) throws Exception {
        windowP05ParamPreprintInvoiceMain.setContentStyle("padding:0px;");

        btnCtrlP05ParamPreprintInvoice = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, null, null, null, null, null, btnSave, btnCancel, null, null);

        
        btnCtrlP05ParamPreprintInvoice.setInitFormButton();
      
        Map<Object, Object> parameterInput = new HashMap<Object, Object>();	
        
        List<P05ParamPreprintInvoice> tempList = getP05ParamPreprintInvoiceService().getListP05ParamPreprintInvoice(parameterInput);
        if(tempList != null){
			setSelectedP05ParamPreprintInvoice(tempList.get(0));    
		}
		
		
       
        tabP05ParamPreprintInvoiceDetail.setSelected(true);
      

        renderTabAndTitle("Edit");

        
        // set render layar


        if (tabPanelP05ParamPreprintInvoiceDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP05ParamPreprintInvoiceDetail, this, "ModuleMainController", zulPageDetail);
        }
        
        
        getP05ParamPreprintInvoiceDetailCtrl().doRenderMode("Edit");
        getP05ParamPreprintInvoiceDetailCtrl().doRenderCheckBox();
        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    

    //Saat Tab Detil di select
    public void onSelect$tabP05ParamPreprintInvoiceDetail(Event event) throws IOException {
        if (tabPanelP05ParamPreprintInvoiceDetail.getFirstChild() != null) {
        	tabP05ParamPreprintInvoiceDetail.setSelected(true);            
        	
        	// Render Inisialisasi posisi awal
            getP05ParamPreprintInvoiceDetailCtrl().doRenderMode("View");   
            getP05ParamPreprintInvoiceDetailCtrl().doRenderCheckBox();
            btnCtrlP05ParamPreprintInvoice.setInitInquiryButton();
            
		
            
            
            
            if (getP05ParamPreprintInvoiceDetailCtrl().getBinder() != null) {
            	getP05ParamPreprintInvoiceDetailCtrl().getBinder().loadAll();  
            }
             
            return;
        }
       
        if (tabPanelP05ParamPreprintInvoiceDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelP05ParamPreprintInvoiceDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getP05ParamPreprintInvoiceDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getP05ParamPreprintInvoiceDetailCtrl().getBinder().saveAll();
    	
    	try {

        		getP05ParamPreprintInvoiceService().update(getSelectedP05ParamPreprintInvoice());

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
    	//String jasperRpt = "/solusi/hapis/webui/crossmoduletabel/listing/l_P05ParamPreprintInvoice.jasper";
    	
    }
    


   // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabP05ParamPreprintInvoiceDetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabP05ParamPreprintInvoiceDetail.setLabel(Labels.getLabel("common.Details"));
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedP05ParamPreprintInvoice(P05ParamPreprintInvoice selectedP05ParamPreprintInvoice) {
        this.selectedP05ParamPreprintInvoice = selectedP05ParamPreprintInvoice;
    }

    public P05ParamPreprintInvoice getSelectedP05ParamPreprintInvoice() {
        return this.selectedP05ParamPreprintInvoice;
    }


	public void setP05ParamPreprintInvoiceService(P05ParamPreprintInvoiceService P05ParamPreprintInvoiceService) {
        this.P05ParamPreprintInvoiceService = P05ParamPreprintInvoiceService;
    }

    public P05ParamPreprintInvoiceService getP05ParamPreprintInvoiceService() {
        return this.P05ParamPreprintInvoiceService;
    }

    public void setP05ParamPreprintInvoiceDetailCtrl(P05ParamPreprintInvoiceDetailCtrl P05ParamPreprintInvoiceDetailCtrl) {
        this.P05ParamPreprintInvoiceDetailCtrl = P05ParamPreprintInvoiceDetailCtrl;
    }

    public P05ParamPreprintInvoiceDetailCtrl getP05ParamPreprintInvoiceDetailCtrl() {
        return this.P05ParamPreprintInvoiceDetailCtrl;
    }

	public void setPostedP05ParamPreprintInvoice(P05ParamPreprintInvoice postedP05ParamPreprintInvoice) {
		this.postedP05ParamPreprintInvoice = postedP05ParamPreprintInvoice;
	}

	public P05ParamPreprintInvoice getPostedP05ParamPreprintInvoice() {
		return postedP05ParamPreprintInvoice;
	}

}
