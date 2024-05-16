package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.BCUserLocationDAO;
import solusi.hapis.backend.navbi.model.BCUserLocation;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class BCUserLocationDAOImpl extends BasisNAVBIDAO<BCUserLocation> implements BCUserLocationDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<BCUserLocation> getListBCUserLocation(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BCUserLocation.class);
		
		if (parameterInput != null) {
	
			if (parameterInput.get("userId") != null) {
				criteria.add(Restrictions.ilike("userId", parameterInput.get("userId").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("branches") != null) {
				criteria.add(Restrictions.ilike("branches", parameterInput.get("branches").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("groupName") != null) {
				criteria.add(Restrictions.ilike("groupName", parameterInput.get("groupName").toString(), MatchMode.ANYWHERE));
			}
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
