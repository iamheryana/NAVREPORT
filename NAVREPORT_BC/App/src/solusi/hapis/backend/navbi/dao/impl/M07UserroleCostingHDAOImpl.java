package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.M07UserroleCostingHDAO;
import solusi.hapis.backend.navbi.model.M07UserroleCostingH;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class M07UserroleCostingHDAOImpl extends BasisNAVBIDAO<M07UserroleCostingH> implements M07UserroleCostingHDAO {

	@SuppressWarnings("unchecked")
	public List<M07UserroleCostingH> getListM07UserroleCostingH(
			Map<Object, Object> parameterInput) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(M07UserroleCostingH.class);		
		
		if (parameterInput != null) {
			if (parameterInput.get("username") != null) {
				criteria.add(Restrictions.ilike("username", parameterInput.get("username").toString(), MatchMode.ANYWHERE));
			}			
			
			if (parameterInput.get("rolename") != null) {
				criteria.add(Restrictions.ilike("rolename", parameterInput.get("rolename").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("filteruser") != null) {
				criteria.add(Restrictions.ilike("filteruser", parameterInput.get("filteruser").toString(), MatchMode.ANYWHERE));
			}
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public M07UserroleCostingH getM07UserroleCostingHByUsername(String Username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M07UserroleCostingH.class);

		criteria.add(Restrictions.eq("username", Username));

		return getUniqueResult(criteria);
	}
}