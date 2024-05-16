/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package solusi.hapis.webui.dashboard;


import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecParam;
import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.backend.security.service.UserService;
import solusi.hapis.webui.dashboard.module.DashboardCalendarCtrl;
import solusi.hapis.webui.dashboard.module.DashboardTableRecordsCounterCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * EN: Controller for the Dashboard .<br>
 * DE: Controller fuer die System Uebersicht.<br>
 * <br>
 * zul-file: /WEB-INF/pages/dashboard.zul.<br>
 * <br>
 *
 * @author Stephan Gerth
 */
public class DashboardMainCtrl extends GFCBaseCtrl implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient static final Logger logger = Logger.getLogger(DashboardMainCtrl.class);

    protected Window windowDashboard;     // autowired
    protected Div divDashboardCenter;     // autowired
    protected Div divDashboardEast;     // autowired
    
    protected Label lblWarning;
    private UserService userService = (UserService) SpringUtil.getBean("userService");
    private SecurityService securityService = (SecurityService) SpringUtil.getBean("securityService");
	
    
    public DashboardMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        self.setAttribute("controller", this, false);
        
        SecUser user = userService.getUserByLoginname(SecurityContextHolder.getContext().getAuthentication().getName());
        SecParam param = securityService.getSecParamByID(new Long(1));
        
        if(param != null){
        	if(user != null){
        		Date vToday = new Date();
        		Date vExpiredDate = user.getExpiredDate();
        		
        		long vTimeToday = vToday.getTime();
        		long vTimeExpiredDate = vExpiredDate.getTime();
        		
        		long vDay = 1000 * 60 * 60 * 24; // milliseconds in a day
        	
        		long vDiffDay = ((vTimeExpiredDate - vTimeToday) / vDay)+1;
        		
        		if(vDiffDay <= new Long(param.getWarningDay())){
        			lblWarning.setValue("Password anda akan Expired dalam "+vDiffDay+" hari");
        		}
        	}
        }
        
        
        // Set the Dashboard unClosable
        try {
            Component cmp = (Component) windowDashboard.getParent().getParent().getParent();
            Tab menuTab = (Tab) cmp.getFellowIfAny("tab_menu_Item_Home", false);
            menuTab.setClosable(false);
        } catch (Exception e) {
            ZksampleMessageUtils.showErrorMessage(e.toString());
            e.printStackTrace();
        }
    }

    public void onCreate$windowDashboard(Event event) throws Exception {
        /* CENTER area */
//        divDashboardCenter.appendChild(DashboardApplicationNewsListCtrl.show(428, true, 600000));
        /* EAST area */
        divDashboardEast.appendChild(DashboardCalendarCtrl.show(146));
//        divDashboardEast.appendChild(DashboardTableRecordsCounterCtrl.show(248, true, 600000));
    }
}
