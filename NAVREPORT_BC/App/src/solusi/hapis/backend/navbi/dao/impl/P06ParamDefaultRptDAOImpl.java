package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.P06ParamDefaultRptDAO;
import solusi.hapis.backend.navbi.model.P06ParamDefaultRpt;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class P06ParamDefaultRptDAOImpl extends BasisNAVBIDAO<P06ParamDefaultRpt> implements P06ParamDefaultRptDAO  {
	@SuppressWarnings("unchecked")
	@Override
	public List<P06ParamDefaultRpt> getListP06ParamDefaultRpt(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P06ParamDefaultRpt.class);
		
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
	public P06ParamDefaultRpt getP06ParamDefaultRptByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P06ParamDefaultRpt.class);
		
		
		criteria.add(Restrictions.eq("kode", kode));

		return getUniqueResult(criteria);
	}
}
