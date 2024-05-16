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
package solusi.hapis;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import solusi.hapis.policy.model.UserImpl;

/**
 * Workspace for the user. One workspace per userSession. <br>
 * <br>
 * Every logged in user have his own workspace. <br>
 * Here are stored several properties for the user. <br>
 * <br>
 * 1. Access the rights that the user have. <br>
 * 2. The office for that the user are logged in. <br>
 *
 * @author bbruhns
 * @author sgerth
 */
public class UserWorkspace implements Serializable, DisposableBean {

    private static final long serialVersionUID = -3936210543827830197L;
    private final static Logger logger = Logger.getLogger(UserWorkspace.class);

    static private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Get a logged-in users WorkSpace which holds all necessary vars. <br>
     *
     * @return the users WorkSpace
     * @deprecated Sollte gegen Spring getauscht werden also Konfiguriert und
     *             nicht über diese Methode!
     */
    @Deprecated
    public static UserWorkspace getInstance() {
        return SpringUtil.getBean("userWorkspace", UserWorkspace.class);
    }

    private String userLanguage;
    private String browserType;
//    private Office office;
    private Set<String> grantedAuthoritySet = null;

    /**
     * Default Constructor
     */
    public UserWorkspace() {
        if (logger.isDebugEnabled()) {
            logger.debug("create new Workspace [" + this + "]");
        }

        // speed up the ModalDialogs while disabling the animation
        Window.setDefaultActionOnShow("");
    }

    /**
     * Logout with the spring-security logout action-URL.<br>
     * Therefore we make a sendRedirect() to the logout uri we <br>
     * have configured in the spring-config.br>
     */
    public void doLogout() {
        destroy();

        /* ++++++ Kills the Http session ++++++ */
        // HttpSession s = (HttpSession)
        // Sessions.getCurrent().getNativeSession();
        // s.invalidate();
        /* ++++++ Kills the zk session +++++ */
        // Sessions.getCurrent().invalidate();

        Executions.sendRedirect("/j_spring_logout");

    }

    private Set<String> getGrantedAuthoritySet() {
        if (this.grantedAuthoritySet == null) {
            final Collection<GrantedAuthority> list = getAuthentication().getAuthorities();
            this.grantedAuthoritySet = new HashSet<String>(list.size());
            for (final GrantedAuthority grantedAuthority : list) {
                this.grantedAuthoritySet.add(grantedAuthority.getAuthority());
            }
        }
        return this.grantedAuthoritySet;
    }

    public boolean isAllowed(String rightName) {    	
    	// Hari 2012-09-13, Only admin can bypass security
    	if (((UserImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSecUser().isAdmin()) {
    		return true;
    	}
        return getGrantedAuthoritySet().contains(rightName);
    }


    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getUserLanguage() {
        return this.userLanguage;
    }

    public Properties getUserLanguageProperty() {
        return null;
    }

    @Override
    public void destroy() {
        this.grantedAuthoritySet = null;
        SecurityContextHolder.clearContext();

        if (logger.isDebugEnabled()) {
            logger.debug("destroy Workspace [" + this + "]");
        }
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getBrowserType() {
        return this.browserType;
    }

}
