package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.P07ParamCashflowDAO;
import solusi.hapis.backend.navbi.model.P07ParamCashflow;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class P07ParamCashflowDAOImpl extends BasisNAVBIDAO<P07ParamCashflow> implements P07ParamCashflowDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<P07ParamCashflow> getListP07ParamCashflow(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P07ParamCashflow.class);
		
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
	public P07ParamCashflow getP07ParamCashflowByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P07ParamCashflow.class);
		
		
		criteria.add(Restrictions.eq("kode", kode));

		return getUniqueResult(criteria);
	}
	
}

