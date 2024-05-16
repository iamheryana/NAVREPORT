package solusi.hapis.common.menu.util;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import solusi.hapis.UserWorkspace;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.menu.domain.IMenuDomain;
import solusi.hapis.common.menu.domain.MenuDomain;
import solusi.hapis.common.menu.domain.MetaMenuFactory;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class BarMenuFactory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserWorkspace workspace;
	private  Menubar menubar;
	
	@SuppressWarnings("deprecation")
	public BarMenuFactory(Menubar menubar) {
		this.workspace = UserWorkspace.getInstance();
		this.menubar = menubar;
		this.menubar.setScrollable(true);
		
		//this.menubar.setOrient("vertical");
		List<IMenuDomain> items = MetaMenuFactory.getRootMenuDomain()
				.getItems();

		if (CommonUtils.isNotEmpty(items)) {
		
			createMenu(items, null);
		}

	}

	private void createMenu(List<IMenuDomain> items, Menupopup subMenuTab) {
		if (items.isEmpty()) {
			return;
		}
		for (final IMenuDomain menuDomain : items) {

			if (menuDomain instanceof MenuDomain) {
				final MenuDomain menu = (MenuDomain) menuDomain;
				if(isAllowed(menuDomain)){
					Menu menuTab = new Menu();
					menuTab.setLabel(Labels.getLabel(menuDomain.getId()));
					
					
					if(menuDomain.getId().substring(0, 3).equals("cat")){					
						menuTab.setParent(this.menubar);
					} else {
						menuTab.setParent(subMenuTab);
					}
					
					Menupopup subMenu = new Menupopup();
					subMenu.setParent(menuTab);					
					
					createMenu(menu.getItems(), subMenu);
				}
			} else {
				if(isAllowed(menuDomain)){
					
					if(menuDomain.getId().substring(0, 3).equals("sep")){		
						Menuseparator sep = new Menuseparator();
						
						if(subMenuTab != null){
							sep.setParent(subMenuTab);
						}else{
							sep.setParent(this.menubar);
						}
						
					} else {
						Menuitem item = new Menuitem();
						item.setLabel(Labels.getLabel(menuDomain.getId()));
						
						if(subMenuTab != null){
							item.setParent(subMenuTab);
						}else{
							item.setParent(this.menubar);
						}
						item.addEventListener(Events.ON_CLICK, new EventListener() {
							@Override
							public void onEvent(Event event) throws Exception {
								showPage(menuDomain.getZulNavigation(), menuDomain.getId(), Labels.getLabel(menuDomain.getId()));
							}
						});
					}
							
					
				}
			}
		}
	}
	
    private void showPage(String zulFilePathName, String tabID, String tabLabel) throws InterruptedException {

        try {
        	int workWithTabs = CommonUtils.ENABLED_TAB;
            if (workWithTabs == 1) {
                Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
                Center center = bl.getCenter();
                Tabs tabs = (Tabs) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter");

                int tabCount = tabs.getChildren().size();
                if (tabCount <= CommonUtils.MAX_TAB) {
                	
                	
                
	                Tab checkTab = null;
	                try {
	                    checkTab = (Tab) tabs.getFellow("tab_" + tabID.trim());
	                    checkTab.setSelected(true);
	                } catch (final ComponentNotFoundException ex) {
	                    // Ignore if can not get tab.
	                }
	
	                if (checkTab == null) {
	                    final Tab tab = new Tab();
	                    tab.setId("tab_" + tabID.trim());
	                    if (tabLabel != null) {
	                        tab.setLabel(tabLabel.trim());
	                    } 
	                    tab.setClosable(true);
	                    tab.setParent(tabs);
	
	                    final Tabpanels tabpanels = (Tabpanels) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter").getFellow("tabpanelsBoxIndexCenter");
	                    final Tabpanel tabpanel = new Tabpanel();
	                    tabpanel.setHeight("100%");
	                    tabpanel.setStyle("padding: 0px;");
	                    tabpanel.setParent(tabpanels);
	
	                    Executions.createComponents(zulFilePathName, tabpanel, null);
	                    tab.setSelected(true);
	                }
                } else {
                	ZksampleMessageUtils.showErrorMessage("Jumlah Tab Maksimal " + CommonUtils.MAX_TAB);
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
	private boolean isAllowed(IMenuDomain treecellValue) {
		return isAllowed(treecellValue.getRightName());
	}

	private boolean isAllowed(String rightName) {
		if (StringUtils.isEmpty(rightName)) {
			return true;
		}
		return getWorkspace().isAllowed(rightName);
	}

    private UserWorkspace getWorkspace() {
        return this.workspace;
    }
}