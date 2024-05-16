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
package solusi.hapis.backend.security.service.impl;


import org.apache.commons.lang.StringUtils;

import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.dao.UserDAO;
import solusi.hapis.backend.security.service.LoginService;

/**
 * EN: Service implementation for methods that depends on <b>login</b>.<br>
 * DE: Service Methoden Implementierung betreffend <b>login</b>.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
public class LoginServiceImpl implements LoginService {
    private UserDAO userDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public SecUser getLoginUser(String usrLoginName, String usrPassword) {

        if (StringUtils.isBlank(usrLoginName)) {
            return null;
        }
        if (StringUtils.isBlank(usrPassword)) {
            return null;
        }

        return getUserDAO().getUserByNameAndPassword(usrLoginName, usrPassword);
    }

}
