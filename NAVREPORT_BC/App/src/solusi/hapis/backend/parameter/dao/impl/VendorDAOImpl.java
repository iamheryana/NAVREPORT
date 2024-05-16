package solusi.hapis.backend.parameter.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.parameter.dao.VendorDAO;
import solusi.hapis.backend.parameter.model.Vendor;
import solusi.hapis.backend.security.dao.impl.BasisDAO;

public class VendorDAOImpl extends BasisDAO<Vendor> implements VendorDAO{

	@Override
	public ResultObject getListVendorLOV(Map<Object, Object> parameterInput,
			int start, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Vendor.class);
		if (parameterInput != null) {
			if (parameterInput.get("Code") != null) {
				criteria.add(Restrictions.ilike("Code", parameterInput.get("Code").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("Name") != null) {
				criteria.add(Restrictions.ilike("Name", parameterInput.get("Name").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("Contact") != null) {
				criteria.add(Restrictions.ilike("Contact", parameterInput.get("Contact").toString(), MatchMode.ANYWHERE));
			}
		}
		
		int totalCount = getHibernateTemplate().findByCriteria(criteria).size();
		
		@SuppressWarnings("unchecked")
		List<Vendor> list = getHibernateTemplate().findByCriteria(criteria, start, pageSize);
		return new  ResultObject(list, totalCount);
	}

}
