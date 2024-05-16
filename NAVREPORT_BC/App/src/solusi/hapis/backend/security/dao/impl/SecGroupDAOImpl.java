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


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import solusi.hapis.backend.model.SecGroup;
import solusi.hapis.backend.model.SecGroupright;
import solusi.hapis.backend.model.SecRolegroup;
import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.dao.SecGroupDAO;

import java.util.List;
import java.util.Map;

/**
 * EN: DAO methods implementation for the <b>SecGroup</b> model class.<br>
 * DE: DAO Methoden Implementierung fuer die <b>SecGroup</b> Model Klasse.<br>
 *
 * @author bbruhns
 * @author Stephan Gerth
 */
@Repository
public class SecGroupDAOImpl extends BasisDAO<SecGroup> implements SecGroupDAO {

    @Override
    public SecGroup getNewSecGroup() {
        return new SecGroup();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecGroup> getAllGroups() {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        criteria.addOrder(Order.asc("grpShortdescription"));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public SecGroup getSecGroupById(Long secGroup_id) {
        return get(SecGroup.class, secGroup_id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SecGroup getGroupByGroupRight(SecGroupright secGroupright) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        // Aliases are working only on properties
        criteria.createAlias("secGrouprights", "gr");
        criteria.add(Restrictions.eq("gr.id", Long.valueOf(secGroupright.getId())));

        return (SecGroup) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    @SuppressWarnings("unchecked")
    @Override
    public SecGroup getGroupByRolegroup(SecRolegroup secRolegroup) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        // Aliases are working only on properties
        criteria.createAlias("secRolegroups", "rg");
        criteria.add(Restrictions.eq("rg.id", Long.valueOf(secRolegroup.getId())));

        return (SecGroup) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecGroup> getGroupsByUser(SecUser aUser) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        // Aliases are working only on properties
        criteria.createAlias("secRolegroups", "rg");
        criteria.createAlias("secRoles", "rol");
        criteria.add(Restrictions.eq("rg.rol.secUser", aUser));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecGroup> getGroupsLikeGroupName(String aGroupName) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        criteria.add(Restrictions.ilike("grpShortdescription", aGroupName, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public int getCountAllSecGroups() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from SecGroup"));
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SecGroup> getListSecGroup(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
		
		if (parameterInput != null) {
			if (parameterInput.get("grpShortdescription") != null) {
				criteria.add(Restrictions.like("grpShortdescription", parameterInput.get("grpShortdescription").toString(), MatchMode.ANYWHERE));
			}
			if (parameterInput.get("grpLongdescription") != null) {
				criteria.add(Restrictions.like("grpLongdescription", parameterInput.get("grpLongdescription").toString(), MatchMode.ANYWHERE));
			}
		}
		
		return getHibernateTemplate().findByCriteria(criteria);

	}
}
