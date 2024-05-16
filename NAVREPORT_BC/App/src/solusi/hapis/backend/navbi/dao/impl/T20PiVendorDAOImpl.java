package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T20PiVendorDAO;
import solusi.hapis.backend.navbi.model.T20PiVendor;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T20PiVendorDAOImpl extends BasisNAVBIDAO<T20PiVendor> implements T20PiVendorDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T20PiVendor> getListT20PiVendor(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T20PiVendor.class);
		
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
			
			if (parameterInput.get("vendorCode") != null) {
				criteria.add(Restrictions.ilike("vendorCode", parameterInput.get("vendorCode").toString(), MatchMode.ANYWHERE));
			}			
						
			if (parameterInput.get("berlaku") != null) {
				criteria.add(Restrictions.ilike("berlaku", parameterInput.get("berlaku").toString(), MatchMode.ANYWHERE));
			}
			
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
