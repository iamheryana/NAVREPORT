package solusi.hapis.backend.tabel.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.security.dao.impl.BasisDAO;
import solusi.hapis.backend.tabel.dao.T01managementadjDAO;
import solusi.hapis.backend.tabel.model.T01managementadj;

public class T01managementadjDAOImpl extends BasisDAO<T01managementadj> implements T01managementadjDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T01managementadj> getListT01managementadj(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T01managementadj.class);
		
		if (parameterInput != null) {
			
			if (parameterInput.get("tanggalfrom") != null) {
				criteria.add(Restrictions.ge("tanggal", parameterInput.get("tanggalfrom")));
			}
			
			if (parameterInput.get("tanggalto") != null) {
				criteria.add(Restrictions.le("tanggal", parameterInput.get("tanggalto")));
			}

			if (parameterInput.get("cabang") != null) {
				criteria.add(Restrictions.eq("cabang", parameterInput.get("cabang")));
			}
			if (parameterInput.get("sales") != null) {
				criteria.add(Restrictions.ilike("sales", parameterInput.get("sales").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("keterangan") != null) {
				criteria.add(Restrictions.ilike("keterangan", parameterInput.get("keterangan").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("amountHw01") != null) {
				criteria.add(Restrictions.eq("amountHw01", parameterInput.get("amountHw01")));
			}
			
			if (parameterInput.get("amountPs01") != null) {
				criteria.add(Restrictions.eq("amountPs01", parameterInput.get("amountPs01")));
			}
			
			if (parameterInput.get("amountPs02") != null) {
				criteria.add(Restrictions.eq("amountPs02", parameterInput.get("amountPs02")));
			}
			
			if (parameterInput.get("amountPs03") != null) {
				criteria.add(Restrictions.eq("amountPs03", parameterInput.get("amountPs03")));
			}
						
			if (parameterInput.get("amountPs04") != null) {
				criteria.add(Restrictions.eq("amountPs04", parameterInput.get("amountPs04")));
			}
			
			if (parameterInput.get("amountPs05") != null) {
				criteria.add(Restrictions.eq("amountPs05", parameterInput.get("amountPs05")));
			}
			

		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
}
