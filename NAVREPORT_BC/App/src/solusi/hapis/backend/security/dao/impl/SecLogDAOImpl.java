package solusi.hapis.backend.security.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.model.SecLog;
import solusi.hapis.backend.security.dao.SecLogDAO;

public class SecLogDAOImpl extends BasisDAO<SecLog> implements SecLogDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<SecLog> getListSecLog(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecLog.class);

		if (parameterInput != null) {
			if (parameterInput.get("logActivity") != null) {
				criteria.add(Restrictions.like("logActivity", parameterInput
						.get("logActivity").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("performedBy") != null) {
				criteria.add(Restrictions.like("performedBy", parameterInput
						.get("performedBy").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("performedOnFrom") != null) {
				criteria.add(Restrictions.ge("performedOn",
						parameterInput.get("performedOnFrom")));
			}
			if (parameterInput.get("performedOnTo") != null) {
				criteria.add(Restrictions.lt("performedOn",
						parameterInput.get("performedOnTo")));
			}
			
			if (parameterInput.get("ip") != null) {
				criteria.add(Restrictions.like("ip", parameterInput
						.get("ip").toString(), MatchMode.ANYWHERE));
			}

		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

}
