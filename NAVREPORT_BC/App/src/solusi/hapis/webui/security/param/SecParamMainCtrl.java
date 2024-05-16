package solusi.hapis.webui.security.param;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecLog;
import solusi.hapis.backend.model.SecParam;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 03-09-2013
 */

public class SecParamMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;


    protected Window windowSecParamMain; 

    // Tabs
    protected Tabbox tabbox_SecParamMain;
    protected Tab tabSecParamDetail;
    protected Tabpanel tabPanelSecParamDetail;
    
    // Button controller standart
    private final String btnCtroller_ClassPrefix = "button_SecParam_";
    private ButtonStatusCtrl btnCtrlSecParam;
    protected Button btnSave;

    // Tab-Controllers for getting the binders
    private SecParamDetailCtrl SecParamDetailCtrl;

    // Databinding
    private SecParam selectedSecParam;
    private SecParam postedSecParam;

    // ServiceDAOs / Domain Classes
    private SecurityService securityService;
    
    // Zul
	private String zulPageDetail = "/WEB-INF/pages/security/param/SecParamDetail.zul";
	

    /**
     * default constructor.<br>
     */
    public SecParamMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        this.self.setAttribute("controller", this, false);
    }

   
    public void onCreate$windowSecParamMain(Event event) throws Exception {
        windowSecParamMain.setContentStyle("padding:0px;");
        
        SecParam param = getSecurityService().getSecParamByID(new Long(1));

        if(param != null){

        	setSelectedSecParam(param);
        }
        

        btnCtrlSecParam = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, null, null, null, null, null, btnSave, null, null, null);
        btnCtrlSecParam.setInitFormButton();

        tabSecParamDetail.setLabel("Parameter");
        
        tabSecParamDetail.setSelected(true);

        if (tabSecParamDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecParamDetail, this, "ModuleMainController", zulPageDetail);
        }

    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++++ Tab Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
     //Saat Tab Detil di select
    public void onSelect$tabSecParamDetail(Event event) throws IOException {
        if (tabPanelSecParamDetail.getFirstChild() != null) {
        	tabSecParamDetail.setSelected(true);            
            
            btnCtrlSecParam.setInitInquiryButton();

            if (getSecParamDetailCtrl().getBinder() != null) {
            	getSecParamDetailCtrl().getBinder().loadAll();  
            }
                        
            return;
        }
       
        if (tabPanelSecParamDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSecParamDetail, this, "ModuleMainController", zulPageDetail);
        }
    }

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++++ Button Events +++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ // 
    
    // Saat Button Save di klik
    public void onClick$btnSave(Event event) throws InterruptedException {
    	String vErrMsg = getSecParamDetailCtrl().validasiBusinessRules();
    	
    	if(vErrMsg != null){
    		ZksampleMessageUtils.showErrorMessage(vErrMsg);
    		return;
    	}
    	
    	getSecParamDetailCtrl().getBinder().saveAll();
    	
    	try {
        		getSecurityService().saveOrUpdate(getSelectedSecParam());
        		
        		String vUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        		
				SecLog newLog = new SecLog("Change Parameter Security", vUserLogin, new Date(), CommonUtils.convertClientAddress(SecurityContextHolder.getContext().getAuthentication()));
				getSecurityService().save(newLog);
			
    	} catch (DataAccessException e) {
        	ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
            return;
        }
    }
    

 
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedSecParam(SecParam selectedSecParam) {
        this.selectedSecParam = selectedSecParam;
    }

    public SecParam getSelectedSecParam() {
        return this.selectedSecParam;
    }


    public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setSecParamDetailCtrl(SecParamDetailCtrl SecParamDetailCtrl) {
        this.SecParamDetailCtrl = SecParamDetailCtrl;
    }

    public SecParamDetailCtrl getSecParamDetailCtrl() {
        return this.SecParamDetailCtrl;
    }

  
	public void setPostedSecParam(SecParam postedSecParam) {
		this.postedSecParam = postedSecParam;
	}

	public SecParam getPostedSecParam() {
		return postedSecParam;
	}

}
