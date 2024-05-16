package solusi.hapis.backend.temp.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.security.dao.impl.BasisDAO;
import solusi.hapis.backend.temp.dao.Z01ptglDAO;
import solusi.hapis.backend.temp.model.Z01ptgl;

public class Z01ptglDAOImpl extends BasisDAO<Z01ptgl> implements Z01ptglDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<Z01ptgl> getListZ01ptgl(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Z01ptgl.class);

//		criteria.createCriteria("m15pega", "m15", CriteriaSpecification.LEFT_JOIN);
//		criteria.createCriteria("m40curr", "m40", CriteriaSpecification.LEFT_JOIN);
//		criteria.createCriteria("m03dppt", "m03", CriteriaSpecification.LEFT_JOIN);

		if (parameterInput != null) {
			
			if (parameterInput.get("kdcabang") != null) {
				criteria.add(Restrictions.ilike("kdcabang", parameterInput.get("kdcabang").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("ketcabang") != null) {
				criteria.add(Restrictions.ilike("ketcabang", parameterInput.get("ketcabang").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("document") != null) {
				criteria.add(Restrictions.ilike("document", parameterInput.get("document").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("accountcode") != null) {
				criteria.add(Restrictions.ilike("accountcode", parameterInput.get("accountcode").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("applydatefrom") != null) {
				criteria.add(Restrictions.ge("applydate", parameterInput.get("applydatefrom")));
			}
			
			if (parameterInput.get("applydateto") != null) {
				criteria.add(Restrictions.lt("applydate", parameterInput.get("applydateto")));
			}
			
			if (parameterInput.get("debit") != null) {
				criteria.add(Restrictions.ilike("debit", parameterInput.get("debit").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("credit") != null) {
				criteria.add(Restrictions.ilike("credit", parameterInput.get("credit").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("captureflg") != null) {
				criteria.add(Restrictions.eq("captureflg", parameterInput.get("captureflg")));
			}
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}


}
