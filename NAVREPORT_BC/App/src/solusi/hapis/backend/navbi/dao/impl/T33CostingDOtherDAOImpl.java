package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T33CostingDOtherDAO;
import solusi.hapis.backend.navbi.model.T32CostingDOwnsw;
import solusi.hapis.backend.navbi.model.T33CostingDOther;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T33CostingDOtherDAOImpl extends BasisNAVBIDAO<T33CostingDOther> implements T33CostingDOtherDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T33CostingDOther> getListT33CostingDOther(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T33CostingDOther.class);

		criteria.createCriteria("t29CostingH", "t29", CriteriaSpecification.LEFT_JOIN);

		
		if (parameterInput != null) {
			if (parameterInput.get("itemDesc") != null) {
				criteria.add(Restrictions.ilike("itemDesc", parameterInput.get("itemDesc").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("itemNo") != null) {
				criteria.add(Restrictions.ilike("itemNo", parameterInput.get("itemNo").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("t29Id") != null) {
				criteria.add(Restrictions.eq("t29.t29Id", parameterInput.get("t29Id")));
			}
		}
		
		return getHibernateTemplate().findByCriteria(criteria);

	}

}
