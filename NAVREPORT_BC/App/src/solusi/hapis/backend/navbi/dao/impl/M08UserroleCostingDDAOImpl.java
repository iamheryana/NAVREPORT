package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.M08UserroleCostingDDAO;
import solusi.hapis.backend.navbi.model.M08UserroleCostingD;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class M08UserroleCostingDDAOImpl extends BasisNAVBIDAO<M08UserroleCostingD> implements M08UserroleCostingDDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<M08UserroleCostingD> getListM08UserroleCostingD(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M08UserroleCostingD.class);

		criteria.createCriteria("m07UserroleCostingH", "m07", CriteriaSpecification.LEFT_JOIN);

		if (parameterInput != null) {
			if (parameterInput.get("filteruser") != null) {
				criteria.add(Restrictions.ilike("filteruser", parameterInput.get("filteruser").toString(), MatchMode.ANYWHERE));
			}
			

			if (parameterInput.get("m07Id") != null) {
				criteria.add(Restrictions.eq("m07.m07Id", parameterInput.get("m07Id")));
			}
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

}
