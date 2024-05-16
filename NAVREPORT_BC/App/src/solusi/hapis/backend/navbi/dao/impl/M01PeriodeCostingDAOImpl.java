package solusi.hapis.backend.navbi.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.M01PeriodeCostingDAO;
import solusi.hapis.backend.navbi.model.M01PeriodeCosting;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;


public class M01PeriodeCostingDAOImpl extends BasisNAVBIDAO<M01PeriodeCosting> implements M01PeriodeCostingDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<M01PeriodeCosting> getListM01PeriodeCosting(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M01PeriodeCosting.class);
		
		if (parameterInput != null) {
			if (parameterInput != null) {
				
			}
				if (parameterInput.get("masa") != null) {
					criteria.add(Restrictions.ilike("masa", parameterInput.get("masa").toString(), MatchMode.ANYWHERE));
				}
				
				if (parameterInput.get("tahun") != null) {
					criteria.add(Restrictions.ilike("tahun", parameterInput.get("tahun").toString(), MatchMode.ANYWHERE));
				}
				
				if (parameterInput.get("closeKomisi") != null) {
					criteria.add(Restrictions.ilike("closeKomisi", parameterInput.get("closeKomisi").toString(), MatchMode.ANYWHERE));
				}
				
				if (parameterInput.get("closeTqs") != null) {
					criteria.add(Restrictions.ilike("closeTqs", parameterInput.get("closeTqs").toString(), MatchMode.ANYWHERE));
				}
				
				
		
		}
		
		criteria.addOrder(Order.desc("tahun"));
		criteria.addOrder(Order.desc("masa"));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}


}
