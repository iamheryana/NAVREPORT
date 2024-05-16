package solusi.hapis.backend.navbi.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.M06TargetPipelineDAO;
import solusi.hapis.backend.navbi.model.M06TargetPipeline;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class M06TargetPipelineDAOImpl extends BasisNAVBIDAO<M06TargetPipeline> implements M06TargetPipelineDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<M06TargetPipeline> getListM06TargetPipeline(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M06TargetPipeline.class);
		
		if (parameterInput != null) {

			
			if (parameterInput.get("tahun") != null) {
				criteria.add(Restrictions.ilike("tahun", parameterInput.get("tahun").toString(), MatchMode.ANYWHERE));
			}
			
			
			if (parameterInput.get("jenis") != null) {
				criteria.add(Restrictions.ilike("jenis", parameterInput.get("jenis").toString(), MatchMode.ANYWHERE));
			}
						
			if (parameterInput.get("status") != null) {
				criteria.add(Restrictions.ilike("status", parameterInput.get("status").toString(), MatchMode.ANYWHERE));
			}
			
			
			if (parameterInput.get("slsOrCab") != null) {
				criteria.add(Restrictions.ilike("slsOrCab", parameterInput.get("slsOrCab").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("target") != null) {
				criteria.add(Restrictions.eq("target", parameterInput.get("target")));
			}
			
			if (parameterInput.get("targetPs") != null) {
				criteria.add(Restrictions.eq("targetPs", parameterInput.get("targetPs")));
			}
			
							
		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}


}
