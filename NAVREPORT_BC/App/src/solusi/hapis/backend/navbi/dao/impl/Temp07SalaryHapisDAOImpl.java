package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.Temp07SalaryHapisDAO;
import solusi.hapis.backend.navbi.model.Temp07SalaryHapis;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class Temp07SalaryHapisDAOImpl extends BasisNAVBIDAO<Temp07SalaryHapis> implements Temp07SalaryHapisDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<Temp07SalaryHapis> getListTemp07SalaryHapis(
			Map<Object, Object> parameterInput) {
DetachedCriteria criteria = DetachedCriteria.forClass(Temp07SalaryHapis.class);
		
		if (parameterInput != null) {
			
			if (parameterInput.get("prosesId") != null) {
				criteria.add(Restrictions.eq("prosesId", parameterInput.get("prosesId").toString()));
			}		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}