package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.P04ParamKomisiDAO;
import solusi.hapis.backend.navbi.model.P04ParamKomisi;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class P04ParamKomisiDAOImpl extends BasisNAVBIDAO<P04ParamKomisi> implements P04ParamKomisiDAO  {
	@SuppressWarnings("unchecked")
	@Override
	public List<P04ParamKomisi> getListP04ParamKomisi(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P04ParamKomisi.class);
		
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
	public P04ParamKomisi getP04ParamKomisiByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P04ParamKomisi.class);
		
		
		criteria.add(Restrictions.eq("kode", kode));

		return getUniqueResult(criteria);
	}
}
