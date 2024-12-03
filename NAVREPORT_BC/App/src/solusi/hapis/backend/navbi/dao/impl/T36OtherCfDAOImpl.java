package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T36OtherCfDAO;
import solusi.hapis.backend.navbi.model.T36OtherCf;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T36OtherCfDAOImpl extends BasisNAVBIDAO<T36OtherCf> implements T36OtherCfDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T36OtherCf> getListT36OtherCf(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T36OtherCf.class);
		
		if (parameterInput != null) {
	
			if (parameterInput.get("company") != null) {
				criteria.add(Restrictions.ilike("company", parameterInput.get("company").toString(), MatchMode.ANYWHERE));
			}
				
			
			if (parameterInput.get("reg") != null) {
				criteria.add(Restrictions.ilike("reg", parameterInput.get("reg").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("keterangan") != null) {
				criteria.add(Restrictions.ilike("keterangan", parameterInput.get("keterangan").toString(), MatchMode.ANYWHERE));
			}
				
			if (parameterInput.get("amount") != null) {
				criteria.add(Restrictions.eq("amount", parameterInput.get("amount")));
			}
			
			if (parameterInput.get("tipe") != null) {
				criteria.add(Restrictions.ilike("tipe", parameterInput.get("tipe").toString(), MatchMode.ANYWHERE));
			}
			
			
			if (parameterInput.get("dueDateFrom") != null) {
				criteria.add(Restrictions.ge("dueDate", parameterInput.get("dueDateFrom")));
			}
			
			if (parameterInput.get("dueDateTo") != null) {
				criteria.add(Restrictions.lt("dueDate", parameterInput.get("dueDateTo")));
			}
			
			
			if (parameterInput.get("basis") != null) {
				criteria.add(Restrictions.ilike("basis", parameterInput.get("basis").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("every") != null) {
				criteria.add(Restrictions.eq("every", parameterInput.get("every")));
			}
			
			if (parameterInput.get("fromDateFrom") != null) {
				criteria.add(Restrictions.ge("fromDate", parameterInput.get("fromDateFrom")));
			}
			
			if (parameterInput.get("fromDateTo") != null) {
				criteria.add(Restrictions.lt("fromDate", parameterInput.get("fromDateTo")));
			}
			
			if (parameterInput.get("uptoDateFrom") != null) {
				criteria.add(Restrictions.ge("uptoDate", parameterInput.get("uptoDateFrom")));
			}
			
			if (parameterInput.get("uptoDateTo") != null) {
				criteria.add(Restrictions.lt("uptoDate", parameterInput.get("uptoDateTo")));
			}		
		
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
