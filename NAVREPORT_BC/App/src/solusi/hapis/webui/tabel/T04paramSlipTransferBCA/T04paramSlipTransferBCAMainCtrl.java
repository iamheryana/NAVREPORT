package solusi.hapis.webui.tabel.T04paramSlipTransferBCA;

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

import solusi.hapis.backend.tabel.model.T04paramKomisi;
import solusi.hapis.backend.tabel.service.T04paramKomisiService;
import solusi.hapis.backend.util.CustomErrorDB;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

import com.itextpdf.text.DocumentException;

/**
 * User: Heryana
 * Date: 05-12-2019
 */

public class T04paramSlipTransferBCAMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowT04paramSlipTransferBCAMain; 

    // Tabs
    protected Tabbox tabbox_T04paramSlipTransferBCAMain;

    protected Tab tabT04paramSlipTransferBCADetail;
    protected Tabpanel tabPanelT04paramSlipTransferBCADetail;

    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_T04paramSlipTransferBCA_";
    private ButtonStatusCtrl btnCtrlT04paramSlipTransferBCA;

    protected Button btnSave;
    protected Button btnCancel;

    // Tab-Controllers for getting the binders
    private T04paramSlipTransferBCADetailCtrl T04paramSlipTransferBCADetailCtrl;

    // Databinding
    private T04paramKomisi selectedT04paramKomisi;
    private T04paramKomisi postedT04paramKomisi;

    // ServiceDAOs / Domain Classes
    private T04paramKomisiService T04paramKomisiService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/tabel/T04paramSlipTransferBCA/T04paramSlipTransferBCADetail.zul";
    
    /**
     * default constructor.<br>
     */
    public T04paramSlipTransferBCAMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowT04paramSlipTransferBCAMain(Event event) throws Exception {
        windowT04paramSlipTransferBCAMain.setContentStyle("padding:0px;");

        btnCtrlT04paramSlipTransferBCA = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, null, null, null, null, null, btnSave, btnCancel, null, null);

        
        btnCtrlT04paramSlipTransferBCA.setInitFormButton();
      
        Map<Object, Object> parameterInput = new HashMap<Object, Object>();	
        
        List<T04paramKomisi> tempList = getT04paramKomisiService().getListT04paramKomisi(parameterInput);
        if(tempList != null){
			setSelectedT04paramKomisi(tempList.get(0));    
		}
		
		
        

        tabT04paramSlipTransferBCADetail.setSelected(true);
      

        renderTabAndTitle("Edit");

        
        // set render layar


        if (tabPanelT04paramSlipTransferBCADetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT04paramSlipTransferBCADetail, this, "ModuleMainController", zulPageDetail);
        }
        
        
        getT04paramSlipTransferBCADetailCtrl().doRenderMode("Edit");
        getT04paramSlipTransferBCADetailCtrl().doRenderCheckBox();
        
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    

    //Saat Tab Detil di select
    public void onSelect$tabT04paramSlipTransferBCADetail(Event event) throws IOException {
        if (tabPanelT04paramSlipTransferBCADetail.getFirstChild() != null) {
        	tabT04paramSlipTransferBCADetail.setSelected(true);            
        	
        	// Render Inisialisasi posisi awal
            getT04paramSlipTransferBCADetailCtrl().doRenderMode("View");   
            getT04paramSlipTransferBCADetailCtrl().doRenderCheckBox();
            btnCtrlT04paramSlipTransferBCA.setInitInquiryButton();
            
		
            
            
            
            if (getT04paramSlipTransferBCADetailCtrl().getBinder() != null) {
            	getT04paramSlipTransferBCADetailCtrl().getBinder().loadAll();  
            }
             
            return;
        }
       
        if (tabPanelT04paramSlipTransferBCADetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelT04paramSlipTransferBCADetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getT04paramSlipTransferBCADetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getT04paramSlipTransferBCADetailCtrl().getBinder().saveAll();
    	
    	try {

        		getT04paramKomisiService().update(getSelectedT04paramKomisi());

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
    	//String jasperRpt = "/solusi/hapis/webui/crossmoduletabel/listing/l_T04paramSlipTransferBCA.jasper";
    	
    }
    


   // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    private void renderTabAndTitle(String requestStatus){
    	if (requestStatus.equals("New") || requestStatus.equals("Edit")){
	    	tabT04paramSlipTransferBCADetail.setLabel(Labels.getLabel("common.Details")+ " - "+requestStatus);
    	}
    	
    	if (requestStatus.equals("Back") || requestStatus.equals("Save")){
	    	tabT04paramSlipTransferBCADetail.setLabel(Labels.getLabel("common.Details"));
    	}
    }
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedT04paramKomisi(T04paramKomisi selectedT04paramKomisi) {
        this.selectedT04paramKomisi = selectedT04paramKomisi;
    }

    public T04paramKomisi getSelectedT04paramKomisi() {
        return this.selectedT04paramKomisi;
    }


	public void setT04paramKomisiService(T04paramKomisiService T04paramKomisiService) {
        this.T04paramKomisiService = T04paramKomisiService;
    }

    public T04paramKomisiService getT04paramKomisiService() {
        return this.T04paramKomisiService;
    }

    public void setT04paramSlipTransferBCADetailCtrl(T04paramSlipTransferBCADetailCtrl T04paramSlipTransferBCADetailCtrl) {
        this.T04paramSlipTransferBCADetailCtrl = T04paramSlipTransferBCADetailCtrl;
    }

    public T04paramSlipTransferBCADetailCtrl getT04paramSlipTransferBCADetailCtrl() {
        return this.T04paramSlipTransferBCADetailCtrl;
    }

	public void setPostedT04paramKomisi(T04paramKomisi postedT04paramKomisi) {
		this.postedT04paramKomisi = postedT04paramKomisi;
	}

	public T04paramKomisi getPostedT04paramKomisi() {
		return postedT04paramKomisi;
	}

}
