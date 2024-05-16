package solusi.hapis.backend.tabel.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.security.dao.impl.BasisDAO;
import solusi.hapis.backend.tabel.dao.T04paramKomisiDAO;
import solusi.hapis.backend.tabel.model.T04paramKomisi;

public class T04paramKomisiDAOImpl extends BasisDAO<T04paramKomisi> implements T04paramKomisiDAO  {
	@SuppressWarnings("unchecked")
	@Override
	public List<T04paramKomisi> getListT04paramKomisi(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T04paramKomisi.class);
		
		if (parameterInput != null) {
			if (parameterInput != null) {
				
			}
				if (parameterInput.get("kode") != null) {
					criteria.add(Restrictions.ilike("kode", parameterInput.get("kode").toString(), MatchMode.ANYWHERE));
				}
				
				
		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public T04paramKomisi getT04paramKomisiByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T04paramKomisi.class);
		
		
		criteria.add(Restrictions.eq("kode", kode));

		return getUniqueResult(criteria);
	}
}
