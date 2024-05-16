package solusi.hapis.backend.parameter.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.parameter.dao.CabangDAO;
import solusi.hapis.backend.parameter.model.Cabang;
import solusi.hapis.backend.security.dao.impl.BasisNAVDAO;

public class CabangDAOImpl extends BasisNAVDAO<Cabang> implements CabangDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<Cabang> getListCabang(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cabang.class);
		if (parameterInput != null) {
			
			if (parameterInput.get("Code") != null) {
				criteria.add(Restrictions.eq("Code", parameterInput.get("Code")));
			}
			
			if (parameterInput.get("Name") != null) {
				criteria.add(Restrictions.eq("Name", parameterInput.get("Name")));
			}
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
