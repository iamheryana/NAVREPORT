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


import java.util.Collection;
import java.util.List;
import java.util.Map;

import solusi.hapis.backend.model.Language;
import solusi.hapis.backend.model.SecGroup;
import solusi.hapis.backend.model.SecRight;
import solusi.hapis.backend.model.SecRole;
import solusi.hapis.backend.model.SecUser;

/**
 * EN: Service methods Interface for working with <b>User data</b> dependend
 * DAOs.<br>
 * DE: Service Methoden Implementierung fuer die <b>User Daten</b> betreffenden
 * DAOs.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
public interface UserService {

    /**
     * EN: Get a new User object.<br>
     * DE: Gibt ein neues User Objekt zurueck.<br>
     *
     * @return SecUser
     */
    public SecUser getNewUser();

    /**
     * EN: Get the count of all Users.<br>
     * DE: Gibt die Anzahl aller User zurueck.<br>
     *
     * @return int
     */
    public int getCountAllSecUser();

    /**
     * EN: Get a list of all Users.<br>
     * DE: Gibt eine Liste alle User zurueck.<br>
     *
     * @return List of Users / Liste aus Usern
     */
    public List<SecUser> getAllUsers();

    /**
     * EN: Get an User by its LoginName.<br>
     * DE: Gibt einen User anhand seines Login Namens zurueck.<br>
     *
     * @param userName UserName / User Name
     * @return User/ User
     */
    public SecUser getUserByLoginname(final String userName);

    /**
     * EN: Gets a list of Users where the LoginName contains the %string% .<br>
     * DE: Gibt eine Liste aller Users zurueck bei denen der LoginName %string%
     * enthaelt.<br>
     *
     * @param string LoginName / LoginName
     * @return List of Users / Liste of Users
     */
    public List<SecUser> getUsersLikeLoginname(String value);

    /**
     * EN: Gets a list of Users where the LastName name contains the %string% .<br>
     * DE: Gibt eine Liste aller Users zurueck bei denen der LastName %string%
     * enthaelt.<br>
     *
     * @param string LastName / LastName
     * @return List of Users / Liste of Users
     */
    public List<SecUser> getUsersLikeLastname(String value);

    /**
     * EN: Get a list of Users by its emailaddress with the like SQL operator.<br>
     * DE: Gibt eine Liste von Usern anhand ihrer Emailadresse mittels SQL like
     * Operator zurueck.<br>
     *
     * @param email Email Address / Email Adresse
     * @return List of Users
     */
    public List<SecUser> getUsersLikeEmail(String value);

    public List<SecRole> getRolesByUser(SecUser user);

    public List<SecRole> getAllRoles();

    public Collection<SecRight> getRightsByUser(SecUser user);

    public List<SecGroup> getGroupsByUser(SecUser user);

    public List<Language> getAllLanguages();

    public Language getLanguageByLocale(String lan_locale);

    public List<SecUser> getListSecUser(Map<Object, Object> parameterInput);
	
    /**
     * EN: Saves new or updates an User.<br>
     * DE: Speichert neu oder aktualisiert einen User.<br>
     */
    public void saveOrUpdate(SecUser user);

    /**
     * EN: Deletes an User.<br>
     * DE: Loescht einen User.<br>
     */
    public void delete(SecUser user);
    
    public void update(SecUser user);
    
	public void save(SecUser user);
	


	public void insertUser(SecUser user);

	public void deleteUser(SecUser user);
	
	public SecUser getUserByLoginId(String loginId);
	
}
