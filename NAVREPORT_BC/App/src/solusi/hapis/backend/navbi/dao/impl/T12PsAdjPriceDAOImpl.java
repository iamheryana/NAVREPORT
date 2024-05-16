package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T12PsAdjPriceDAO;
import solusi.hapis.backend.navbi.model.T12PsAdjPrice;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T12PsAdjPriceDAOImpl extends BasisNAVBIDAO<T12PsAdjPrice> implements T12PsAdjPriceDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T12PsAdjPrice> getListT12PsAdjPrice(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T12PsAdjPrice.class);
		
		if (parameterInput != null) {
	
			if (parameterInput.get("custNo") != null) {
				criteria.add(Restrictions.ilike("custNo", parameterInput.get("custNo").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("itemNo") != null) {
				criteria.add(Restrictions.ilike("itemNo", parameterInput.get("itemNo").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("currCode") != null) {
				criteria.add(Restrictions.ilike("currCode", parameterInput.get("currCode").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("adjPrice") != null) {
				criteria.add(Restrictions.eq("adjPrice", parameterInput.get("adjPrice")));
			}			
			
			if (parameterInput.get("tglBerlakuFrom") != null) {
				criteria.add(Restrictions.ge("tglBerlaku", parameterInput.get("tglBerlakuFrom")));
			}
			
			if (parameterInput.get("tglBerlakuTo") != null) {
				criteria.add(Restrictions.lt("tglBerlaku", parameterInput.get("tglBerlakuTo")));
			}
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
