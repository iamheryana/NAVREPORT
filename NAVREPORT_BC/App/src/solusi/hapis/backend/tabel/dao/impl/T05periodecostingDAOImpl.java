package solusi.hapis.backend.tabel.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.security.dao.impl.BasisDAO;
import solusi.hapis.backend.tabel.dao.T05periodecostingDAO;
import solusi.hapis.backend.tabel.model.T05periodecosting;

public class T05periodecostingDAOImpl extends BasisDAO<T05periodecosting> implements T05periodecostingDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T05periodecosting> getListT05periodecosting(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T05periodecosting.class);
		
		if (parameterInput != null) {
			if (parameterInput != null) {
				
			}
				if (parameterInput.get("masa") != null) {
					criteria.add(Restrictions.ilike("masa", parameterInput.get("masa").toString(), MatchMode.ANYWHERE));
				}
				
				if (parameterInput.get("tahun") != null) {
					criteria.add(Restrictions.ilike("tahun", parameterInput.get("tahun").toString(), MatchMode.ANYWHERE));
				}
				
				if (parameterInput.get("closeKomisi") != null) {
					criteria.add(Restrictions.ilike("closeKomisi", parameterInput.get("closeKomisi").toString(), MatchMode.ANYWHERE));
				}
				
				if (parameterInput.get("closeTqs") != null) {
					criteria.add(Restrictions.ilike("closeTqs", parameterInput.get("closeTqs").toString(), MatchMode.ANYWHERE));
				}
				
				
		
		}
		
		criteria.addOrder(Order.desc("tahun"));
		criteria.addOrder(Order.desc("masa"));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}


}
