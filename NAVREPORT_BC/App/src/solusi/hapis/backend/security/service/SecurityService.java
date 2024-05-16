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


import java.util.List;
import java.util.Map;

import solusi.hapis.backend.model.SecGroup;
import solusi.hapis.backend.model.SecGroupright;
import solusi.hapis.backend.model.SecLog;
import solusi.hapis.backend.model.SecParam;
import solusi.hapis.backend.model.SecRight;
import solusi.hapis.backend.model.SecRole;
import solusi.hapis.backend.model.SecRolegroup;
import solusi.hapis.backend.model.SecRoleright;
import solusi.hapis.backend.model.SecTyp;
import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.model.SecUserrole;

/**
 * EN: Service methods Interface for working with <b>Security data</b> dependend
 * DAOs.<br>
 * DE: Service Methoden Implementierung fuer die <b>Security data</b>
 * betreffenden DAOs.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
public interface SecurityService {

    /* +++++ Security: Userroles +++++++ */
    public int getCountAllSecUserroles();

    public SecUserrole getNewSecUserrole();

    public void saveOrUpdate(SecUserrole userRole);

    public void delete(SecUserrole userRole);

    public List<SecUserrole> getAllUserRoles();

    public SecUserrole getUserroleByUserAndRole(SecUser user, SecRole role);

    public boolean isUserInRole(SecUser user, SecRole role);

    /* +++++ Security: Roles +++++++ */
    public int getCountAllSecRoles();

    public SecRole getNewSecRole();

    public List<SecRole> getAllRoles();

    public void saveOrUpdate(SecRole role);

    public void delete(SecRole role);
    
    public List<SecRole> getListSecRoles(Map<Object, Object> parameterInput);

    /* +++++ Security: RoleGroups +++++++ */
    public int getCountAllSecRolegroups();

    public SecRolegroup getNewSecRolegroup();

    public void saveOrUpdate(SecRolegroup secRolegroup);

    public void delete(SecRolegroup roleGroup);

    public List<SecRolegroup> getAllRolegroups();

    public SecRolegroup getRolegroupByRoleAndGroup(SecRole role, SecGroup group);

    public boolean isGroupInRole(SecGroup group, SecRole role);

    /* +++++ Security: Groups +++++++ */

    public int getCountAllSecGroups();

    public List<SecGroup> getAllGroups();

    public List<SecGroup> getListSecGroup(Map<Object, Object> parameterInput);

    public SecGroup getNewSecGroup();

    public void saveOrUpdate(SecGroup secGroup);

    public void delete(SecGroup group);

    /* +++++ Security: Rights +++++++ */
    public int getCountAllSecRights();

    public SecRight getNewSecRight();

    public void delete(SecRight right);

    public void saveOrUpdate(SecRight right);
    
    public List<SecRight> getListSecRight(Map<Object, Object> parameterInput);
    
    /* +++++ Security: Parameters +++++++ */
    
    public SecParam getSecParamByID(Long paramId);

	public void saveOrUpdate(SecParam entity);


    /**
     * Get all rights. The result can limited by the type.<br>
     * <br>
     * <p/>
     * Int | Type <br>
     * --------------------------<br>
     * -1 | All (no filter) <br>
     * 0 | Page <br>
     * 1 | Menu Category <br>
     * 2 | Menu Item <br>
     * 3 | Method <br>
     * 4 | DomainObject/Property <br>
     * 5 | Tab <br>
     * 6 | Components <br>
     */
    public List<SecRight> getAllRights(int type);

    public List<SecRight> getAllRights(List<Integer> list);

    public List<SecRight> getAllRights();

    public boolean isRightinGroup(SecRight right, SecGroup group);

    /* +++++ Security: Grouprights +++++++ */

    public int getCountAllSecGrouprights();

    public List<SecGroupright> getAllGroupRights();

    public SecGroupright getNewSecGroupright();

    public void delete(SecGroupright groupRight);

    public void saveOrUpdate(SecGroupright groupRight);

    /* +++++ Security: Security Typs +++++++ */
    public List<SecTyp> getAllTypes();

    public SecTyp getTypById(int typ_id);

    /* +++++++++++++++++++++++++++++++++++++++++++ */

    public SecGroupright getGroupRightByGroupAndRight(SecGroup group, SecRight right);

    public List<SecRight> getRightsLikeRightName(String value);

    public List<SecRight> getRightsLikeRightNameAndType(String value, int type);

    public List<SecGroup> getGroupsLikeGroupName(String value);

    public List<SecRole> getRolesLikeRoleName(String value);

    public List<SecRight> getGroupRightsByGroup(SecGroup group);

    public List<SecRight> getRightsLikeRightNameAndTypes(String value, List<Integer> list);

    public List<SecRight> getGrantedSecRight(Long id);
    
    /* +++++ Security: Log +++++++ */
    
    public void save(SecLog entity);

	public List<SecLog> getListSecLog(Map<Object, Object> parameterInput);
	
	
	/* +++++ Security: Role Right +++++++ */
	
	public void save(SecRoleright entity);

	public void delete(SecRoleright entity);
	
	public List<SecRoleright> getListSecRoleright(Map<Object, Object> parameterInput);


}

