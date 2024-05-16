package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.dao.V01CustomerNavDAO;
import solusi.hapis.backend.navbi.model.V01CustomerNav;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class V01CustomerNavDAOImpl extends BasisNAVBIDAO<V01CustomerNav> implements V01CustomerNavDAO{
	


	@Override
	public V01CustomerNav getV01CustomerNavByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(V01CustomerNav.class);
		
		
		criteria.add(Restrictions.eq("custCode", kode));
	
		return getUniqueResult(criteria);
	}


	@Override
	public ResultObject getListV01CustomerNavLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(V01CustomerNav.class);
		if (parameterInput != null) {
			if (parameterInput.get("custCode") != null) {
				criteria.add(Restrictions.ilike("custCode", parameterInput.get("custCode").toString(), MatchMode.ANYWHERE));
			}
	
			if (parameterInput.get("custName") != null) {
				criteria.add(Restrictions.ilike("custName", parameterInput.get("custName").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noNpwp") != null) {
				criteria.add(Restrictions.ilike("noNpwp", parameterInput.get("noNpwp").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("sectorCode") != null) {
				criteria.add(Restrictions.ilike("sectorCode", parameterInput.get("sectorCode").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("sectorName") != null) {
				criteria.add(Restrictions.ilike("sectorName", parameterInput.get("sectorName").toString(), MatchMode.ANYWHERE));
			}
		}
		
		int totalCount = getHibernateTemplate().findByCriteria(criteria).size();
		
		@SuppressWarnings("unchecked")
		List<V01CustomerNav> list = getHibernateTemplate().findByCriteria(criteria, start, pageSize);
		return new  ResultObject(list, totalCount);
	}

}
