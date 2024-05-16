package solusi.hapis.backend.navbi.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.M03TargetsalesDAO;
import solusi.hapis.backend.navbi.model.M03Targetsales;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class M03TargetsalesDAOImpl extends BasisNAVBIDAO<M03Targetsales> implements M03TargetsalesDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<M03Targetsales> getListM03Targetsales(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M03Targetsales.class);

		criteria.createCriteria("m02Salesperson", "m02", CriteriaSpecification.LEFT_JOIN);

		if (parameterInput != null) {
			if (parameterInput.get("tahun") != null) {
				criteria.add(Restrictions.ilike("tahun", parameterInput.get("tahun").toString(), MatchMode.ANYWHERE));
			}
			
			
			if (parameterInput.get("target") != null) {
				criteria.add(Restrictions.eq("target", parameterInput.get("target")));
			}

			if (parameterInput.get("m02Id") != null) {
				criteria.add(Restrictions.eq("m02.m02Id", parameterInput.get("m02Id")));
			}
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

}
