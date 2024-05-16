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
package solusi.hapis.backend.security.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import solusi.hapis.backend.model.SecRole;
import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.dao.SecRoleDAO;

/**
 * EN: DAO methods implementation for the <b>SecRole</b> model class.<br>
 * DE: DAO Methoden Implementierung fuer die <b>SecRole</b> Model Klasse.<br>
 *
 * @author bbruhns
 * @author Stephan Gerth
 */
@Repository
public class SecRoleDAOImpl extends BasisDAO<SecRole> implements SecRoleDAO {

    @Override
    public SecRole getNewSecRole() {
        return new SecRole();
    }

    @Override
    public List<SecRole> getAllRoles() {
        return getHibernateTemplate().loadAll(SecRole.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRole> getRolesByUser(SecUser aUser) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecRole.class);

        // Aliases are working only on properties
        criteria.createAlias("secUserroles", "rol");
        criteria.add(Restrictions.eq("rol.secUser", aUser));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public SecRole getRoleById(Long role_Id) {
        return get(SecRole.class, role_Id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRole> getRolesLikeRoleName(String aRoleName) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecRole.class);
        criteria.add(Restrictions.ilike("rolShortdescription", aRoleName, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public int getCountAllSecRoles() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from SecRole"));
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SecRole> getListSecRoles(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecRole.class);
		
		
		if (parameterInput != null) {
			if (parameterInput.get("rolShortdescription") != null) {
				criteria.add(Restrictions.ilike("rolShortdescription", parameterInput.get("rolShortdescription").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("rolLongdescription") != null) {
				criteria.add(Restrictions.ilike("rolLongdescription", parameterInput.get("rolLongdescription").toString(), MatchMode.ANYWHERE));
			}
			
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
