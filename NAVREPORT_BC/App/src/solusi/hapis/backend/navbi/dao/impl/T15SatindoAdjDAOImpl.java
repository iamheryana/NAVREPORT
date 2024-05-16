package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T15SatindoAdjDAO;
import solusi.hapis.backend.navbi.model.T15SatindoAdj;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T15SatindoAdjDAOImpl extends BasisNAVBIDAO<T15SatindoAdj> implements T15SatindoAdjDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T15SatindoAdj> getListT15SatindoAdj(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T15SatindoAdj.class);
		
		if (parameterInput != null) {
	
			if (parameterInput.get("noInvoice") != null) {
				criteria.add(Restrictions.ilike("noInvoice", parameterInput.get("noInvoice").toString(), MatchMode.ANYWHERE));
			}
					
			
			if (parameterInput.get("orderDateFrom") != null) {
				criteria.add(Restrictions.ge("orderDate", parameterInput.get("orderDateFrom")));
			}
			
			if (parameterInput.get("orderDateTo") != null) {
				criteria.add(Restrictions.lt("orderDate", parameterInput.get("orderDateTo")));
			}
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
