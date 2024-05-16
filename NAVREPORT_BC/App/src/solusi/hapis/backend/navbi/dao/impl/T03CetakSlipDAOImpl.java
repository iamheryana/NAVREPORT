package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T03CetakSlipDAO;
import solusi.hapis.backend.navbi.model.T03CetakSlip;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T03CetakSlipDAOImpl extends BasisNAVBIDAO<T03CetakSlip> implements T03CetakSlipDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T03CetakSlip> getListT03CetakSlip(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T03CetakSlip.class);
		
		if (parameterInput != null) {
			if (parameterInput.get("jenisTrans") != null) {
				criteria.add(Restrictions.ilike("jenisTrans", parameterInput.get("jenisTrans").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("company") != null) {
				criteria.add(Restrictions.ilike("company", parameterInput.get("company").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noVoucher") != null) {
				criteria.add(Restrictions.ilike("noVoucher", parameterInput.get("noVoucher").toString(), MatchMode.ANYWHERE));
			}
			
		
			if (parameterInput.get("noCheque") != null) {
				criteria.add(Restrictions.ilike("noCheque", parameterInput.get("noCheque").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("kurs") != null) {
				criteria.add(Restrictions.eq("kurs", parameterInput.get("kurs")));
			}			
			
			if (parameterInput.get("printCount") != null) {
				criteria.add(Restrictions.eq("printCount", parameterInput.get("printCount")));
			}	
			
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
