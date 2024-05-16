package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T23AdjTopCustDAO;
import solusi.hapis.backend.navbi.model.T23AdjTopCust;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T23AdjTopCustDAOImpl extends BasisNAVBIDAO<T23AdjTopCust> implements T23AdjTopCustDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T23AdjTopCust> getListT23AdjTopCust(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T23AdjTopCust.class);
		
		if (parameterInput != null) {
	
			if (parameterInput.get("custNo") != null) {
				criteria.add(Restrictions.ilike("custNo", parameterInput.get("custNo").toString(), MatchMode.ANYWHERE));
			}
			
			
			if (parameterInput.get("topAdj") != null) {
				criteria.add(Restrictions.eq("topAdj", parameterInput.get("topAdj")));
			}			
			
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
