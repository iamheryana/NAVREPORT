package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T04BayarAngsuranDAO;
import solusi.hapis.backend.navbi.model.T04BayarAngsuran;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T04BayarAngsuranDAOImpl extends BasisNAVBIDAO<T04BayarAngsuran> implements T04BayarAngsuranDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T04BayarAngsuran> getListT04BayarAngsuran(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T04BayarAngsuran.class);
		
		if (parameterInput != null) {
			
			
			
			if (parameterInput.get("company") != null) {
				criteria.add(Restrictions.ilike("company", parameterInput.get("company").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("suppCode") != null) {
				criteria.add(Restrictions.ilike("suppCode", parameterInput.get("suppCode").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noPo") != null) {
				criteria.add(Restrictions.ilike("noPo", parameterInput.get("noPo").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("valutaPo") != null) {
				criteria.add(Restrictions.ilike("valutaPo", parameterInput.get("valutaPo").toString(), MatchMode.ANYWHERE));
			}
						
			if (parameterInput.get("nilaiPo") != null) {
				criteria.add(Restrictions.eq("nilaiPo", parameterInput.get("nilaiPo")));
			}	
						
			if (parameterInput.get("jmlGiro") != null) {
				criteria.add(Restrictions.eq("jmlGiro", parameterInput.get("jmlGiro")));
			}		
		
			
			if (parameterInput.get("tmtfrom") != null) {
				criteria.add(Restrictions.ge("tmt", parameterInput.get("tmtfrom")));
			}
			
			if (parameterInput.get("tmtto") != null) {
				criteria.add(Restrictions.lt("tmt", parameterInput.get("tmtto")));
			}
			
			
			
			if (parameterInput.get("printCount") != null) {
				criteria.add(Restrictions.eq("printCount", parameterInput.get("printCount")));
			}	
			
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
