package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T34CostingDPaymentDAO;
import solusi.hapis.backend.navbi.model.T34CostingDPayment;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T34CostingDPaymentDAOImpl extends BasisNAVBIDAO<T34CostingDPayment> implements T34CostingDPaymentDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T34CostingDPayment> getListT34CostingDPayment(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T34CostingDPayment.class);

		criteria.createCriteria("t29CostingH", "t29", CriteriaSpecification.LEFT_JOIN);

		
		if (parameterInput != null) {
			if (parameterInput.get("noInvoice") != null) {
				criteria.add(Restrictions.ilike("noInvoice", parameterInput.get("noInvoice").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noLunas") != null) {
				criteria.add(Restrictions.ilike("noLunas", parameterInput.get("noLunas").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("t29Id") != null) {
				criteria.add(Restrictions.eq("t29.t29Id", parameterInput.get("t29Id")));
			}
		}
		
		return getHibernateTemplate().findByCriteria(criteria);

	}
}
