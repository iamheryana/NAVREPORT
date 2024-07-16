package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T30CostingDHw3psDAO;
import solusi.hapis.backend.navbi.model.T30CostingDHw3ps;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T30CostingDHw3psDAOImpl extends BasisNAVBIDAO<T30CostingDHw3ps> implements T30CostingDHw3psDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T30CostingDHw3ps> getListT30CostingDHw3ps(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T30CostingDHw3ps.class);

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
