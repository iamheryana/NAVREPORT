package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.P03UserCreatepoDAO;
import solusi.hapis.backend.navbi.model.P03UserCreatepo;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class P03UserCreatepoDAOImpl extends BasisNAVBIDAO<P03UserCreatepo> implements P03UserCreatepoDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<P03UserCreatepo> getListP03UserCreatepo(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P03UserCreatepo.class);
		
		if (parameterInput != null) {
			
			
			if (parameterInput.get("username") != null) {
				criteria.add(Restrictions.ilike("username", parameterInput.get("username").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("groupname") != null) {
				criteria.add(Restrictions.ilike("groupname", parameterInput.get("groupname").toString(), MatchMode.ANYWHERE));
			}
			
			
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
