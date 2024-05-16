package solusi.hapis.backend.navbi.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T06WebinarAttendeeDAO;
import solusi.hapis.backend.navbi.model.T06WebinarAttendee;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T06WebinarAttendeeDAOImpl extends BasisNAVBIDAO<T06WebinarAttendee> implements T06WebinarAttendeeDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T06WebinarAttendee> getListT06WebinarAttendee(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T06WebinarAttendee.class);
		
		criteria.createCriteria("t05WebinarEvent", "t05", CriteriaSpecification.LEFT_JOIN);
		
		if (parameterInput != null) {
			
			if (parameterInput.get("attended") != null) {
				criteria.add(Restrictions.ilike("attended", parameterInput.get("attended").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("firstName") != null) {
				criteria.add(Restrictions.ilike("firstName", parameterInput.get("firstName").toString(), MatchMode.ANYWHERE));
			}			
			
			if (parameterInput.get("lastName") != null) {
				criteria.add(Restrictions.ilike("lastName", parameterInput.get("lastName").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("email") != null) {
				criteria.add(Restrictions.ilike("email", parameterInput.get("email").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("organization") != null) {
				criteria.add(Restrictions.ilike("organization", parameterInput.get("organization").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("timeInSession") != null) {
				criteria.add(Restrictions.eq("timeInSession", parameterInput.get("timeInSession")));
			}
							
				
			if (parameterInput.get("t05Id") != null) {
				criteria.add(Restrictions.eq("t05.t05Id", parameterInput.get("t05Id")));
			}
		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}


}
