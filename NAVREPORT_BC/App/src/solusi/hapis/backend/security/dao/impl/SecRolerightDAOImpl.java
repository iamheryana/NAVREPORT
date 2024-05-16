package solusi.hapis.backend.security.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.model.SecRoleright;
import solusi.hapis.backend.security.dao.SecRolerightDAO;

public class SecRolerightDAOImpl  extends BasisDAO<SecRoleright> implements  SecRolerightDAO {
	@SuppressWarnings("unchecked")
	@Override
	public List<SecRoleright> getListSecRoleright(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecRoleright.class);
		criteria.createCriteria("secRole", "srl", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("secRight", "srg", CriteriaSpecification.LEFT_JOIN);
		
		if (parameterInput.get("roleId") != null) {
			criteria.add(Restrictions.eq("srl.id", parameterInput.get("roleId")));
		}

		if (parameterInput.get("parentRlrId") != null) {
			criteria.add(Restrictions.eq("parentRlrId", parameterInput.get("parentRlrId")));
		}
		
		if (parameterInput.get("rigName") != null) {
			criteria.add(Restrictions.eq("srg.rigName", parameterInput.get("rigName")));
		}
		
		if (parameterInput.get("rigType") != null) {
			criteria.add(Restrictions.eq("srg.rigType", parameterInput.get("rigType")));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
