package solusi.hapis.backend.navbi.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.Temp36DuedateArDAO;
import solusi.hapis.backend.navbi.model.Temp36DuedateAr;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class Temp36DuedateArDAOImpl extends BasisNAVBIDAO<Temp36DuedateAr> implements Temp36DuedateArDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<Temp36DuedateAr> getListTemp36DuedateAr(
			Map<Object, Object> parameterInput) {
DetachedCriteria criteria = DetachedCriteria.forClass(Temp36DuedateAr.class);
		
		if (parameterInput != null) {
			
			if (parameterInput.get("prosesId") != null) {
				criteria.add(Restrictions.eq("prosesId", parameterInput.get("prosesId").toString()));
			}		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}