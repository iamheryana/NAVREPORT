package solusi.hapis.webui.security.log;


import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleCommonUtils;

public class SecLogMainCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = 3025232569227182778L;

	protected Window windowSecLogMain;

	protected Tabbox tabbox_SecLogMain;
	protected Tab tabSecLogList;
	protected Tabpanel tabPanelSecLogList;

	protected Button btnSearch;
	protected Button btnClear;


	private ButtonStatusCtrl btnCtrlSecLog;
	private final String btnCtroller_ClassPrefix = "button_SecLog_";


	private transient SecurityService securityService;
	
	private SecLogListCtrl SecLogListCtrl;
	
	// Zul
	private String zulPageList = "/WEB-INF/pages/security/log/SecLogList.zul";
	
	public SecLogMainCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		this.self.setAttribute("controller", this, false);
	}

	public void onCreate$windowSecLogMain(Event event) throws Exception {
		windowSecLogMain.setContentStyle("padding:0px;");

		btnCtrlSecLog = new ButtonStatusCtrl(getUserWorkspace(),
				btnCtroller_ClassPrefix, null, null, null,
				null, null, null, null, btnSearch, btnClear);

		btnCtrlSecLog.setInitInquiryButton();

		tabSecLogList.setSelected(true);

		if (tabPanelSecLogList != null)
			ZksampleCommonUtils.createTabPanelContent(tabPanelSecLogList,
					this, "ModuleMainController", zulPageList);

	}

	public void onClick$btnSearch(Event event) throws InterruptedException {
		if (getSecLogListCtrl() != null) {
			getSecLogListCtrl().searchTable();
			tabSecLogList.setSelected(true);
		}
	}

	public void onClick$btnClear(Event event) throws InterruptedException {
		if (getSecLogListCtrl() != null) {
			getSecLogListCtrl().clearSearchBox();
			getSecLogListCtrl().searchTable();
			tabSecLogList.setSelected(true);
		}
	}


	public SecLogListCtrl getSecLogListCtrl() {
		return SecLogListCtrl;
	}

	public void setSecLogListCtrl(SecLogListCtrl SecLogListCtrl) {
		this.SecLogListCtrl = SecLogListCtrl;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
