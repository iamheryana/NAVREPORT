package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T01SoAdjDAO;
import solusi.hapis.backend.navbi.model.T01SoAdj;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T01SoAdjDAOImpl extends BasisNAVBIDAO<T01SoAdj> implements T01SoAdjDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T01SoAdj> getListT01SoAdj(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T01SoAdj.class);
		
		if (parameterInput != null) {
	
			if (parameterInput.get("noSo") != null) {
				criteria.add(Restrictions.ilike("noSo", parameterInput.get("noSo").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("jenisPayment") != null) {
				criteria.add(Restrictions.ilike("jenisPayment", parameterInput.get("jenisPayment").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("useQty") != null) {
				criteria.add(Restrictions.ilike("useQty", parameterInput.get("useQty").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("qty") != null) {
				criteria.add(Restrictions.eq("qty", parameterInput.get("qty")));
			}			
			
			if (parameterInput.get("estRealisasifrom") != null) {
				criteria.add(Restrictions.ge("estRealisasi", parameterInput.get("estRealisasifrom")));
			}
			
			if (parameterInput.get("estRealisasito") != null) {
				criteria.add(Restrictions.lt("estRealisasi", parameterInput.get("estRealisasito")));
			}
			
			if (parameterInput.get("useCclDate") != null) {
				criteria.add(Restrictions.ilike("useCclDate", parameterInput.get("useCclDate").toString(), MatchMode.ANYWHERE));
			}
	
			if (parameterInput.get("addDays") != null) {
				criteria.add(Restrictions.eq("addDays", parameterInput.get("addDays")));
			}		
			
			if (parameterInput.get("keteranganDp") != null) {
				criteria.add(Restrictions.ilike("keteranganDp", parameterInput.get("keteranganDp").toString(), MatchMode.ANYWHERE));
			}
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
