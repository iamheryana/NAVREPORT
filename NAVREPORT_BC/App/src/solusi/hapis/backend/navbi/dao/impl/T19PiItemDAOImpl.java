package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T19PiItemDAO;
import solusi.hapis.backend.navbi.model.T19PiItem;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T19PiItemDAOImpl extends BasisNAVBIDAO<T19PiItem> implements T19PiItemDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T19PiItem> getListT19PiItem(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T19PiItem.class);
		
		if (parameterInput != null) {
			
			if (parameterInput.get("tglMulaiFrom") != null) {
				criteria.add(Restrictions.ge("tglMulai", parameterInput.get("tglMulaiFrom")));
			}
			
			if (parameterInput.get("tglMulaiTo") != null) {
				criteria.add(Restrictions.lt("tglMulai", parameterInput.get("tglMulaiTo")));
			}
			
			
			if (parameterInput.get("principalCode") != null) {
				criteria.add(Restrictions.ilike("principalCode", parameterInput.get("principalCode").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("itemCatCode") != null) {
				criteria.add(Restrictions.ilike("itemCatCode", parameterInput.get("itemCatCode").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("productCode") != null) {
				criteria.add(Restrictions.ilike("productCode", parameterInput.get("productCode").toString(), MatchMode.ANYWHERE));
			}
			
						
			if (parameterInput.get("berlaku") != null) {
				criteria.add(Restrictions.ilike("berlaku", parameterInput.get("berlaku").toString(), MatchMode.ANYWHERE));
			}
			
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
