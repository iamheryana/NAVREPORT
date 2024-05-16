package solusi.hapis.backend.navbi.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.dao.T05WebinarEventDAO;
import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T05WebinarEventDAOImpl extends BasisNAVBIDAO<T05WebinarEvent> implements T05WebinarEventDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T05WebinarEvent> getListT05WebinarEvent(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T05WebinarEvent.class);
		
		if (parameterInput != null) {
			
			if (parameterInput.get("topic") != null) {
				criteria.add(Restrictions.ilike("topic", parameterInput.get("topic").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("webinarId") != null) {
				criteria.add(Restrictions.ilike("webinarId", parameterInput.get("webinarId").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("actualDuration") != null) {
				criteria.add(Restrictions.eq("actualDuration", parameterInput.get("actualDuration")));
			}
							
			if (parameterInput.get("numRegistered") != null) {
				criteria.add(Restrictions.eq("numRegistered", parameterInput.get("numRegistered")));
			}			
			
			if (parameterInput.get("actualStartTimeFrom") != null) {
				criteria.add(Restrictions.ge("actualStartTime", parameterInput.get("actualStartTimeFrom")));
			}
			
			if (parameterInput.get("actualStartTimeTo") != null) {
				criteria.add(Restrictions.lt("actualStartTime", parameterInput.get("actualStartTimeTo")));
			}
				

		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public ResultObject getListT05WebinarEventLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T05WebinarEvent.class);
		if (parameterInput != null) {
			if (parameterInput.get("topic") != null) {
				criteria.add(Restrictions.ilike("topic", parameterInput.get("topic").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("webinarId") != null) {
				criteria.add(Restrictions.ilike("webinarId", parameterInput.get("webinarId").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("actualStartTimeFrom") != null) {
				criteria.add(Restrictions.ge("actualStartTime", parameterInput.get("actualStartTimeFrom")));
			}
			
			if (parameterInput.get("actualStartTimeTo") != null) {
				criteria.add(Restrictions.lt("actualStartTime", parameterInput.get("actualStartTimeTo")));
			}		
		}
		
		int totalCount = getHibernateTemplate().findByCriteria(criteria).size();
		
		@SuppressWarnings("unchecked")
		List<T05WebinarEvent> list = getHibernateTemplate().findByCriteria(criteria, start, pageSize);
		return new  ResultObject(list, totalCount);

	}


}
