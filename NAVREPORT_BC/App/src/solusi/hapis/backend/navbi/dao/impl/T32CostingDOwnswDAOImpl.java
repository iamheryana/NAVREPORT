package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T32CostingDOwnswDAO;
import solusi.hapis.backend.navbi.model.T32CostingDOwnsw;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T32CostingDOwnswDAOImpl extends BasisNAVBIDAO<T32CostingDOwnsw> implements T32CostingDOwnswDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T32CostingDOwnsw> getListT32CostingDOwnsw(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T32CostingDOwnsw.class);

		criteria.createCriteria("t29CostingH", "t29", CriteriaSpecification.LEFT_JOIN);

		
		if (parameterInput != null) {
			if (parameterInput.get("itemDesc") != null) {
				criteria.add(Restrictions.ilike("itemDesc", parameterInput.get("itemDesc").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("itemNo") != null) {
				criteria.add(Restrictions.ilike("itemNo", parameterInput.get("itemNo").toString(), MatchMode.ANYWHERE));
			}
			
		}
		
		return getHibernateTemplate().findByCriteria(criteria);

	}

}
