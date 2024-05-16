package solusi.hapis.backend.tabel.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.security.dao.impl.BasisDAO;
import solusi.hapis.backend.tabel.dao.T08targetsalesDAO;
import solusi.hapis.backend.tabel.model.T08targetsales;

public class T08targetsalesDAOImpl extends BasisDAO<T08targetsales> implements T08targetsalesDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T08targetsales> getListT08targetsales(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T08targetsales.class);

		criteria.createCriteria("t03salesperson", "t03", CriteriaSpecification.LEFT_JOIN);

		if (parameterInput != null) {
			if (parameterInput.get("tahun") != null) {
				criteria.add(Restrictions.ilike("tahun", parameterInput.get("tahun").toString(), MatchMode.ANYWHERE));
			}
			
			
			if (parameterInput.get("target") != null) {
				criteria.add(Restrictions.eq("target", parameterInput.get("target")));
			}

			if (parameterInput.get("t03Id") != null) {
				criteria.add(Restrictions.eq("t03.t03Id", parameterInput.get("t03Id")));
			}
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

}
