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
package solusi.hapis.backend.security.service;

import solusi.hapis.backend.model.SecUser;

/**
 * EN: Service methods Interface for working with <b>User Login</b> dependend
 * DAOs.<br>
 * DE: Service Methoden Implementierung fuer die <b>User Login</b> betreffenden
 * DAOs.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
public interface LoginService {

    SecUser getLoginUser(String usrLoginName, String usrPassword);

}
