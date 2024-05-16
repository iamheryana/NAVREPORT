package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.dao.P02VendorNonnavDAO;
import solusi.hapis.backend.navbi.model.P02VendorNonnav;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class P02VendorNonnavDAOImpl extends BasisNAVBIDAO<P02VendorNonnav> implements P02VendorNonnavDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<P02VendorNonnav> getListP02VendorNonnav(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P02VendorNonnav.class);
		
		if (parameterInput != null) {
			
			
			if (parameterInput.get("kode") != null) {
				criteria.add(Restrictions.ilike("kode", parameterInput.get("kode").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("valutaTrans") != null) {
				criteria.add(Restrictions.ilike("valutaTrans", parameterInput.get("valutaTrans").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("namaPenerima") != null) {
				criteria.add(Restrictions.ilike("namaPenerima", parameterInput.get("namaPenerima").toString(), MatchMode.ANYWHERE));
			}
			
		
			if (parameterInput.get("namaBank") != null) {
				criteria.add(Restrictions.ilike("namaBank", parameterInput.get("namaBank").toString(), MatchMode.ANYWHERE));
			}
				
			
			if (parameterInput.get("noRekPenerima") != null) {
				criteria.add(Restrictions.ilike("noRekPenerima", parameterInput.get("noRekPenerima").toString(), MatchMode.ANYWHERE));
			}
	
			
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	
	public ResultObject getListP02VendorNonnavLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P02VendorNonnav.class);
		if (parameterInput != null) {
			if (parameterInput.get("kode") != null) {
				criteria.add(Restrictions.ilike("kode", parameterInput.get("kode").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("valutaTrans") != null) {
				criteria.add(Restrictions.ilike("valutaTrans", parameterInput.get("valutaTrans").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("namaPenerima") != null) {
				criteria.add(Restrictions.ilike("namaPenerima", parameterInput.get("namaPenerima").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("namaBank") != null) {
				criteria.add(Restrictions.ilike("namaBank", parameterInput.get("namaBank").toString(), MatchMode.ANYWHERE));
			}
		}
		
		int totalCount = getHibernateTemplate().findByCriteria(criteria).size();
		
		@SuppressWarnings("unchecked")
		List<P02VendorNonnav> list = getHibernateTemplate().findByCriteria(criteria, start, pageSize);
		return new  ResultObject(list, totalCount);
	}
	
	
	@Override
	public P02VendorNonnav getP02VendorNonnavByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P02VendorNonnav.class);
		
		
		criteria.add(Restrictions.eq("kode", kode));

		return getUniqueResult(criteria);
	}
}
