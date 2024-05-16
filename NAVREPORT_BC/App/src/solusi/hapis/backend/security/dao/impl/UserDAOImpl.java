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

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.dao.UserDAO;

/**
 * EN: DAO methods implementation for the <b>SecUser</b> model class.<br>
 * DE: DAO Methoden Implementierung fuer die <b>SecUser</b> Model Klasse.<br>
 * 
 * @author bbruhns
 * @author Stephan Gerth
 */
@Repository
public class UserDAOImpl extends BasisDAO<SecUser> implements UserDAO {

	@Override
	public SecUser getNewSecUser() {
		return new SecUser();
	}

	@Override
	public List<SecUser> getAllUsers() {
		return getHibernateTemplate().loadAll(SecUser.class);
	}

	public SecUser getUserByID(final Long usr_id) {
		return get(SecUser.class, usr_id);
	}

	@SuppressWarnings("unchecked")
	public SecUser getUserByFiluserNr(String usr_nr) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
		criteria.add(Restrictions.eq("usrNr", usr_nr));

		return (SecUser) DataAccessUtils.uniqueResult(getHibernateTemplate()
				.findByCriteria(criteria));
	}

	@SuppressWarnings("unchecked")
	public SecUser getUserByLoginname(final String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
		// nanda, 10072012, start - add filtering
		criteria.setFetchMode("mpegawai", FetchMode.JOIN);
		criteria.setFetchMode("mmahasiswa", FetchMode.JOIN);
		criteria.setFetchMode("msekolah", FetchMode.JOIN);
		criteria.setFetchMode("mprodi", FetchMode.JOIN);
		// nanda, 10072012, end - add filtering
		criteria.add(Restrictions.eq("usrLoginname", userName));
		return (SecUser) DataAccessUtils.uniqueResult(getHibernateTemplate()
				.findByCriteria(criteria));
	}

	@SuppressWarnings("unchecked")
	public SecUser getUserByNameAndPassword(final String userName,
			final String userPassword) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
		criteria.add(Restrictions.eq("usrLoginname", userName));
		criteria.add(Restrictions.eq("usrPassword", userPassword));

		return (SecUser) DataAccessUtils.uniqueResult(getHibernateTemplate()
				.findByCriteria(criteria));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecUser> getUsersLikeLastname(String string) {

		DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
		criteria.add(Restrictions.like("usrLastname", string,
				MatchMode.ANYWHERE));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecUser> getUsersLikeLoginname(String string) {

		DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
		criteria.add(Restrictions.like("usrLoginname", string,
				MatchMode.ANYWHERE));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecUser> getUsersLikeEmail(String email) {

		DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
		criteria.add(Restrictions.like("usrEmail", email, MatchMode.ANYWHERE));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecUser> getUsersByLoginname(String loginName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
		criteria.add(Restrictions.like("usrLoginname", loginName));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int getCountAllSecUser() {
		return DataAccessUtils.intResult(getHibernateTemplate().find(
				"select count(*) from SecUser"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecUser> getListSecUser(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(SecUser.class);

		if (parameterInput != null) {
			if (parameterInput.get("usrLoginname") != null) {
				criteria.add(Restrictions.like("usrLoginname", parameterInput
						.get("usrLoginname").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("usrFirstname") != null) {
				criteria.add(Restrictions.like("usrFirstname", parameterInput
						.get("usrFirstname").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("usrLastname") != null) {
				criteria.add(Restrictions.like("usrLastname", parameterInput
						.get("usrLastname").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("usrEmail") != null) {
				criteria.add(Restrictions.like("usrEmail", parameterInput
						.get("usrEmail").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("expiredDateFrom") != null) {
				criteria.add(Restrictions.ge("expiredDate",
						parameterInput.get("expiredDateFrom")));
			}
			if (parameterInput.get("expiredDateTo") != null) {
				criteria.add(Restrictions.lt("expiredDate",
						parameterInput.get("expiredDateTo")));
			}
			
			if (parameterInput.get("flagActiv") != null) {
				criteria.add(Restrictions.eq("flagActiv",
						parameterInput.get("flagActiv")));
			}
			
			if (parameterInput.get("accessCabang") != null) {
				criteria.add(Restrictions.eq("accessCabang",
						parameterInput.get("accessCabang")));
			}
			
			if (parameterInput.get("accessGolongan") != null) {
				criteria.add(Restrictions.eq("accessGolongan",
						parameterInput.get("accessGolongan")));
			}
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SecUser getUserByLoginId(String loginId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);

		criteria.add(Restrictions.eq("usrLoginname", loginId));
		return (SecUser) DataAccessUtils.uniqueResult(getHibernateTemplate()
				.findByCriteria(criteria));
	}

}
