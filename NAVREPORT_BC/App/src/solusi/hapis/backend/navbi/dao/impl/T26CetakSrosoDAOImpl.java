package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T26CetakSrosoDAO;
import solusi.hapis.backend.navbi.model.T26CetakSroso;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T26CetakSrosoDAOImpl extends BasisNAVBIDAO<T26CetakSroso> implements T26CetakSrosoDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T26CetakSroso> getListT26CetakSroso(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T26CetakSroso.class);
		
		if (parameterInput != null) {
	
			if (parameterInput.get("keterangan") != null) {
				criteria.add(Restrictions.ilike("keterangan", parameterInput.get("keterangan").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("dicetakPadaFrom") != null) {
				criteria.add(Restrictions.ge("dicetakPada", parameterInput.get("dicetakPadaFrom")));
			}
			
			if (parameterInput.get("dicetakPadaTo") != null) {
				criteria.add(Restrictions.lt("dicetakPada", parameterInput.get("dicetakPadaTo")));
			}
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
