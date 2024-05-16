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
import solusi.hapis.backend.security.dao.SecGroupDAO;
import solusi.hapis.backend.security.dao.SecGrouprightDAO;
import solusi.hapis.backend.security.dao.SecLogDAO;
import solusi.hapis.backend.security.dao.SecParamDAO;
import solusi.hapis.backend.security.dao.SecRightDAO;
import solusi.hapis.backend.security.dao.SecRoleDAO;
import solusi.hapis.backend.security.dao.SecRolegroupDAO;
import solusi.hapis.backend.security.dao.SecRolerightDAO;
import solusi.hapis.backend.security.dao.SecTypDAO;
import solusi.hapis.backend.security.dao.SecUserroleDAO;
import solusi.hapis.backend.security.dao.UserDAO;
import solusi.hapis.backend.security.service.SecurityService;

/**
 * EN: Service implementation for methods that depends on <b>Security</b>.<br>
 * DE: Service Methoden Implementierung betreffend <b>Zugriffskonzept</b>.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
public class SecurityServiceImpl implements SecurityService {

    private UserDAO userDAO;
    private SecUserroleDAO secUserroleDAO;
    private SecRoleDAO secRoleDAO;
    private SecRolegroupDAO secRolegroupDAO;
    private SecGrouprightDAO secGrouprightDAO;
    private SecGroupDAO secGroupDAO;
    private SecRightDAO secRightDAO;
    private SecTypDAO secTypDAO;
    private SecParamDAO secParamDAO;
    private SecLogDAO secLogDAO;
    private SecRolerightDAO secRolerightDAO ;

    public SecTypDAO getSecTypDAO() {
        return secTypDAO;
    }

    public void setSecTypDAO(SecTypDAO secTypDAO) {
        this.secTypDAO = secTypDAO;
    }

    public SecRightDAO getSecRightDAO() {
        return secRightDAO;
    }

    public void setSecRightDAO(SecRightDAO secRightDAO) {
        this.secRightDAO = secRightDAO;
    }

    public SecGroupDAO getSecGroupDAO() {
        return secGroupDAO;
    }

    public void setSecGroupDAO(SecGroupDAO secGroupDAO) {
        this.secGroupDAO = secGroupDAO;
    }

    public SecGrouprightDAO getSecGrouprightDAO() {
        return secGrouprightDAO;
    }

    public void setSecGrouprightDAO(SecGrouprightDAO secGrouprightDAO) {
        this.secGrouprightDAO = secGrouprightDAO;
    }

    public SecRolegroupDAO getSecRolegroupDAO() {
        return secRolegroupDAO;
    }

    public void setSecRolegroupDAO(SecRolegroupDAO secRolegroupDAO) {
        this.secRolegroupDAO = secRolegroupDAO;
    }

    public SecUserroleDAO getSecUserroleDAO() {
        return secUserroleDAO;
    }

    public void setSecUserroleDAO(SecUserroleDAO secUserroleDAO) {
        this.secUserroleDAO = secUserroleDAO;
    }

    public SecRoleDAO getSecRoleDAO() {
        return secRoleDAO;
    }

    public void setSecRoleDAO(SecRoleDAO secRoleDAO) {
        this.secRoleDAO = secRoleDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public SecParamDAO getSecParamDAO() {
		return secParamDAO;
	}

	public void setSecParamDAO(SecParamDAO secParamDAO) {
		this.secParamDAO = secParamDAO;
	}

	public SecLogDAO getSecLogDAO() {
		return secLogDAO;
	}

	public void setSecLogDAO(SecLogDAO secLogDAO) {
		this.secLogDAO = secLogDAO;
	}

	public SecRolerightDAO getSecRolerightDAO() {
		return secRolerightDAO;
	}

	public void setSecRolerightDAO(SecRolerightDAO secRolerightDAO) {
		this.secRolerightDAO = secRolerightDAO;
	}

	@Override
    public List<SecRole> getAllRoles() {
        return getSecRoleDAO().getAllRoles();
    }

    @Override
    public void saveOrUpdate(SecRole role) {
        getSecRoleDAO().saveOrUpdate(role);
    }

    @Override
    public void delete(SecRole role) {
        getSecRoleDAO().delete(role);
    }

    @Override
    public List<SecGroup> getAllGroups() {
        return getSecGroupDAO().getAllGroups();
    }

    @Override
    public List<SecRight> getAllRights(int type) {
        return getSecRightDAO().getAllRights(type);
    }

    @Override
    public SecGroup getNewSecGroup() {
        return getSecGroupDAO().getNewSecGroup();
    }

    @Override
    public SecRight getNewSecRight() {
        return getSecRightDAO().getNewSecRight();
    }

    @Override
    public SecRole getNewSecRole() {
        return getSecRoleDAO().getNewSecRole();
    }

    @Override
    public List<SecGroupright> getAllGroupRights() {
        return getSecGrouprightDAO().getAllGroupRights();
    }

    @Override
    public SecGroupright getNewSecGroupright() {
        return getSecGrouprightDAO().getNewSecGroupright();
    }

    @Override
    public List<SecUserrole> getAllUserRoles() {
        return getSecUserroleDAO().getAllUserRoles();
    }

    @Override
    public SecUserrole getNewSecUserrole() {
        return getSecUserroleDAO().getNewSecUserrole();
    }

    @Override
    public SecRolegroup getNewSecRolegroup() {
        return getSecRolegroupDAO().getNewSecRolegroup();
    }

    @Override
    public List<SecRolegroup> getAllRolegroups() {
        return getSecRolegroupDAO().getAllRolegroups();
    }

    @Override
    public List<SecTyp> getAllTypes() {
        return getSecTypDAO().getAllTypes();
    }

    @Override
    public void delete(SecRight right) {
        getSecRightDAO().delete(right);
    }

    @Override
    public boolean isRightinGroup(SecRight right, SecGroup group) {
        return getSecGrouprightDAO().isRightInGroup(right, group);
    }

    @Override
    public void saveOrUpdate(SecRight right) {
        getSecRightDAO().saveOrUpdate(right);
    }

    @Override
    public SecTyp getTypById(int typ_id) {
        return getSecTypDAO().getTypById(typ_id);
    }

    @Override
    public void saveOrUpdate(SecUserrole userRole) {
        getSecUserroleDAO().saveOrUpdate(userRole);
    }

    @Override
    public void saveOrUpdate(SecRolegroup roleGroup) {
        getSecRolegroupDAO().saveOrUpdate(roleGroup);
    }

    @Override
    public void saveOrUpdate(SecGroup group) {
        getSecGroupDAO().saveOrUpdate(group);
    }

    @Override
    public void saveOrUpdate(SecGroupright groupRight) {
        getSecGrouprightDAO().saveOrUpdate(groupRight);
    }

    @Override
    public void delete(SecGroupright groupRight) {
        getSecGrouprightDAO().delete(groupRight);
    }

    @Override
    public SecGroupright getGroupRightByGroupAndRight(SecGroup group, SecRight right) {
        return getSecGrouprightDAO().getGroupRightByGroupAndRight(group, right);
    }

    @Override
    public SecRolegroup getRolegroupByRoleAndGroup(SecRole role, SecGroup group) {
        return getSecRolegroupDAO().getRolegroupByRoleAndGroup(role, group);
    }

    @Override
    public void delete(SecRolegroup roleGroup) {
        getSecRolegroupDAO().delete(roleGroup);
    }

    @Override
    public boolean isGroupInRole(SecGroup group, SecRole role) {
        return getSecRolegroupDAO().isGroupInRole(group, role);
    }

    @Override
    public SecUserrole getUserroleByUserAndRole(SecUser user, SecRole role) {
        return getSecUserroleDAO().getUserroleByUserAndRole(user, role);
    }

    @Override
    public void delete(SecUserrole userRole) {
        getSecUserroleDAO().delete(userRole);
    }

    @Override
    public boolean isUserInRole(SecUser user, SecRole role) {
        return getSecUserroleDAO().isUserInRole(user, role);

    }

    @Override
    public List<SecRight> getAllRights(List<Integer> list) {
        return getSecRightDAO().getAllRights(list);
    }

    @Override
    public List<SecRight> getAllRights() {
        return getSecRightDAO().getAllRights();
    }

    @Override
    public List<SecRight> getRightsLikeRightName(String value) {
        return getSecRightDAO().getRightsLikeRightName(value);
    }

    @Override
    public List<SecRight> getRightsLikeRightNameAndType(String value, int type) {
        return getSecRightDAO().getRightsLikeRightNameAndType(value, type);
    }

    @Override
    public List<SecGroup> getGroupsLikeGroupName(String value) {
        return getSecGroupDAO().getGroupsLikeGroupName(value);
    }

    @Override
    public List<SecRole> getRolesLikeRoleName(String value) {
        return getSecRoleDAO().getRolesLikeRoleName(value);
    }

    @Override
    public void delete(SecGroup group) {
        getSecGroupDAO().delete(group);
    }

    @Override
    public List<SecRight> getGroupRightsByGroup(SecGroup group) {
        return getSecGrouprightDAO().getGroupRightsByGroup(group);
    }

    @Override
    public List<SecRight> getRightsLikeRightNameAndTypes(String value, List<Integer> list) {
        return getSecRightDAO().getRightsLikeRightNameAndTypes(value, list);
    }

    @Override
    public int getCountAllSecGroups() {
        return getSecGroupDAO().getCountAllSecGroups();
    }

    @Override
    public int getCountAllSecGrouprights() {
        return getSecGrouprightDAO().getCountAllSecGrouprights();
    }

    @Override
    public int getCountAllSecRights() {
        return getSecRightDAO().getCountAllSecRights();
    }

    @Override
    public int getCountAllSecRoles() {
        return getSecRoleDAO().getCountAllSecRoles();
    }

    @Override
    public int getCountAllSecRolegroups() {
        return getSecRolegroupDAO().getCountAllSecRolegroups();
    }

    @Override
    public int getCountAllSecUserroles() {
        return getSecUserroleDAO().getCountAllSecUserroles();
    }

	@Override
	public List<SecRight> getGrantedSecRight(Long id) {
		return getSecGrouprightDAO().getGrantedRight(id);
	}

	@Override
	public List<SecGroup> getListSecGroup(Map<Object, Object> parameterInput) {
		return secGroupDAO.getListSecGroup(parameterInput);
	}

	@Override
	public SecParam getSecParamByID(Long paramId) {
		return secParamDAO.getSecParamByID(paramId);
	}

	@Override
	public void saveOrUpdate(SecParam entity) {
		secParamDAO.saveOrUpdate(entity);
	}

	@Override
	public void save(SecLog entity) {
		secLogDAO.save(entity);
	}

	@Override
	public List<SecLog> getListSecLog(Map<Object, Object> parameterInput) {
		return secLogDAO.getListSecLog(parameterInput);
	}
	
	@Override
	public void save(SecRoleright entity) {
		secRolerightDAO.save(entity);
		
	}

	@Override
	public List<SecRoleright> getListSecRoleright(
			Map<Object, Object> parameterInput) {
		return  secRolerightDAO.getListSecRoleright(parameterInput);
	}

	@Override
	public List<SecRight> getListSecRight(Map<Object, Object> parameterInput) {
		return secRightDAO.getListSecRight(parameterInput);
	}

	@Override
	public void delete(SecRoleright entity) {
		secRolerightDAO.delete(entity);		
	}

	@Override
	public List<SecRole> getListSecRoles(Map<Object, Object> parameterInput) {
		return secRoleDAO.getListSecRoles(parameterInput);
	}


}
