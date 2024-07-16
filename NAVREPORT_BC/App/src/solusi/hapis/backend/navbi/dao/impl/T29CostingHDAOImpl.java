package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T29CostingHDAO;
import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T29CostingHDAOImpl extends BasisNAVBIDAO<T29CostingH> implements T29CostingHDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T29CostingH> getListT29CostingH(
			Map<Object, Object> parameterInput) {
DetachedCriteria criteria = DetachedCriteria.forClass(T29CostingH.class);

		if (parameterInput != null) {

			if (parameterInput.get("noCosting") != null) {
				criteria.add(Restrictions.ilike("noCosting", parameterInput.get("noCosting").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noBso") != null) {
				criteria.add(Restrictions.ilike("noBso", parameterInput.get("noBso").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noSo") != null) {
				criteria.add(Restrictions.ilike("noSo", parameterInput.get("noSo").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noPoCustomer") != null) {
				criteria.add(Restrictions.ilike("noPoCustomer", parameterInput.get("noPoCustomer").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("customer") != null) {
				criteria.add(Restrictions.ilike("customer", parameterInput.get("customer").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("tglCostingfrom") != null) {
				criteria.add(Restrictions.ge("tglCosting", parameterInput.get("tglCostingfrom")));
			}
			
			if (parameterInput.get("tglCostingto") != null) {
				criteria.add(Restrictions.lt("tglCosting", parameterInput.get("tglCostingto")));
			}
				

		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
