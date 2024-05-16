package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.P01BiayaTransDAO;
import solusi.hapis.backend.navbi.model.P01BiayaTrans;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class P01BiayaTransDAOImpl extends BasisNAVBIDAO<P01BiayaTrans> implements P01BiayaTransDAO  {
	@SuppressWarnings("unchecked")
	@Override
	public List<P01BiayaTrans> getListP01BiayaTrans(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P01BiayaTrans.class);
		
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
	public P01BiayaTrans getP01BiayaTransByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P01BiayaTrans.class);
		
		criteria.add(Restrictions.eq("kode", kode));

		return getUniqueResult(criteria);
	}
}
