package solusi.hapis.webui.index;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecLog;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.menu.util.BarMenuFactory;
import solusi.hapis.policy.model.UserImpl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;


public class IndexBarCtrl extends GFCBaseCtrl implements Serializable {

    private static final long serialVersionUID = -3407055074703929527L;

    protected Menubar mainMenuBar;             // autowired
    protected Label label_AppName;             // autowired
    protected Intbox currentDesktopHeight;     // autowired
    protected Intbox currentDesktopWidth;     // autowired
    protected Tabs tabsIndexCenter;         // autowired
    protected Menubar menubar;

    private final int centerAreaHeightOffset = 50;

    private SecurityService securityService = (SecurityService) SpringUtil.getBean("securityService");
	
    public IndexBarCtrl() {
        super();
    }

    public void onCreate$outerIndexWindow(Event event) throws Exception {
        this.mainMenuBar.setVisible(false);
        createMainBarMenu(event);

        final String userName = ((UserImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        EventQueues.lookup("userNameEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeUser", null, userName));
    }

    public void onClientInfo(ClientInfoEvent event) throws Exception {
        setCurrentDesktopHeight(event.getDesktopHeight() - this.centerAreaHeightOffset);
        setCurrentDesktopWidth(event.getDesktopWidth());

    }

    public String doGetLoggedInUser() {
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userName;
    }

    public void onClick$btnLogout() throws IOException {
    	SecLog newLog = new SecLog("Logout", doGetLoggedInUser(), new Date(), CommonUtils.convertClientAddress(SecurityContextHolder.getContext().getAuthentication()));
		securityService.save(newLog);
		
        getUserWorkspace().doLogout();
    }

    public void onClick$btnCloseAllTabs() throws IOException, InterruptedException {
        @SuppressWarnings("unchecked")
		List<AbstractComponent> list = tabsIndexCenter.getChildren();
        try {
            while (!list.isEmpty()) {
                int i = list.size();
                if (list.get(i - 1) instanceof Tab) {
                    if (StringUtils.equals(((Tab) list.get(i - 1)).getId(), "tab_menu_Item_Home")) {
                        break;
                    } else {
                        ((Tab) list.get(i - 1)).onClose();
                    }
                }
            }
        } catch (Exception e) {
            ZksampleMessageUtils.showErrorMessage(e.toString());
        }
    }

    private void createMainBarMenu(Event event) throws InterruptedException {
        
        @SuppressWarnings("unused")
		BarMenuFactory BarMenu = new BarMenuFactory(menubar);
        showPage("/WEB-INF/pages/dashboard.zul", "menu_Item_Home", Labels.getLabel("menu_Item_Home"));
        
    }

    public void showWelcomePage() throws InterruptedException {
        Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("/WEB-INF/pages/welcome.zul", center, null);
    }

    public void onClick$btnIndexMySettings() throws IOException, InterruptedException {
        Window win = null;
        Window parentWin = (Window) Path.getComponent("/outerIndexWindow");
        try {
            win = (Window) Executions.createComponents("/WEB-INF/pages/security/user/mySettings.zul", parentWin, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            ZksampleMessageUtils.showErrorMessage(e.getLocalizedMessage());
            win.detach();
        }
    }


	private void showPage(String zulFilePathName, String tabID, String tabLabel) throws InterruptedException {

        try {
            int workWithTabs =  CommonUtils.ENABLED_TAB;;
            if (workWithTabs == 1) {
                Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
                Center center = bl.getCenter();
                Tabs tabs = (Tabs) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter");

                Tab checkTab = null;
                try {
                    checkTab = (Tab) tabs.getFellow("tab_" + tabID.trim());
                    checkTab.setSelected(true);
                } catch (final ComponentNotFoundException ex) {
                    // Ignore if can not get tab.
                }
                
                if (checkTab == null) {
                    Tab tab = new Tab();
                    tab.setId("tab_" + tabID.trim());
                    if (tabLabel != null) {
                        tab.setLabel(tabLabel.trim());
                    } 
                    tab.setClosable(true);
                    tab.setParent(tabs);
                    
                    Tabpanels tabpanels = (Tabpanels) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter").getFellow("tabpanelsBoxIndexCenter");
                    Tabpanel tabpanel = new Tabpanel();
                    tabpanel.setHeight("100%");
                    tabpanel.setStyle("padding: 0px;");
                    tabpanel.setParent(tabpanels);

                    try {
                        Executions.createComponents(zulFilePathName, tabpanel, null);
                        tab.setSelected(true);
                    } catch (final Exception e) {
                        tab.detach();
                    }
                    
                }
            } else {
                final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
                final Center center = bl.getCenter();
                center.getChildren().clear();
                Executions.createComponents(zulFilePathName, center, null);
            }
        } catch (final Exception e) {
            Messagebox.show(e.toString());
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // ++++++++++++++++++ getter / setter +++++++++++++++++++//
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//


    public void setCurrentDesktopHeight(int desktopHeight) {
        this.currentDesktopHeight.setValue(Integer.valueOf(desktopHeight - 30));
    }

    public int getCurrentDesktopHeight() {
        return this.currentDesktopHeight.getValue().intValue();
    }

    public void setCurrentDesktopWidth(int currentDesktopWidth) {
        this.currentDesktopWidth.setValue(Integer.valueOf(currentDesktopWidth));
    }

    public int getCurrentDesktopWidth() {
        return this.currentDesktopWidth.getValue().intValue();
    }

}