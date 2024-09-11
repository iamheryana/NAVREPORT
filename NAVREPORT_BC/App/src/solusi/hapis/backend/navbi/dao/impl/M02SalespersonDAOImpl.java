package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.dao.M02SalespersonDAO;
import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class M02SalespersonDAOImpl extends BasisNAVBIDAO<M02Salesperson> implements M02SalespersonDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<M02Salesperson> getListM02Salesperson(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M02Salesperson.class);
		
		if (parameterInput != null) {

			if (parameterInput.get("sales") != null) {
				criteria.add(Restrictions.ilike("sales", parameterInput.get("sales").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("salesName") != null) {
				criteria.add(Restrictions.ilike("salesName", parameterInput.get("salesName").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("nik") != null) {
				criteria.add(Restrictions.ilike("nik", parameterInput.get("nik").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("spv") != null) {
				criteria.add(Restrictions.ilike("spv", parameterInput.get("spv").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("target") != null) {
				criteria.add(Restrictions.eq("target", parameterInput.get("target")));
			}
			
			
			if (parameterInput.get("periodeResignfrom") != null) {
				criteria.add(Restrictions.ge("periodeResign", parameterInput.get("periodeResignfrom")));
			}
			
			if (parameterInput.get("periodeResignto") != null) {
				criteria.add(Restrictions.lt("periodeResign", parameterInput.get("periodeResignto")));
			}
				

		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public ResultObject getListM02SalespersonLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M02Salesperson.class);
		if (parameterInput != null) {
			if (parameterInput.get("sales") != null) {
				criteria.add(Restrictions.ilike("sales", parameterInput.get("sales").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("salesName") != null) {
				criteria.add(Restrictions.ilike("salesName", parameterInput.get("salesName").toString(), MatchMode.ANYWHERE));
			}
			
			
		}
		
		int totalCount = getHibernateTemplate().findByCriteria(criteria).size();
		
		@SuppressWarnings("unchecked")
		List<M02Salesperson> list = getHibernateTemplate().findByCriteria(criteria, start, pageSize);
		return new  ResultObject(list, totalCount);
	}

	@Override
	public ResultObject getListM02SalespersonLOVFilter(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M02Salesperson.class);
		if (parameterInput != null) {
			Conjunction myQueryConjunc = Restrictions.conjunction();
			if (parameterInput.get("sales") != null) {
				myQueryConjunc.add(Restrictions.ilike("sales", parameterInput.get("sales").toString(), MatchMode.ANYWHERE));
			}

			if (parameterInput.get("salesName") != null) {
				myQueryConjunc.add(Restrictions.ilike("salesName", parameterInput.get("salesName").toString(), MatchMode.ANYWHERE));
			}
			
			criteria.add(myQueryConjunc);
		}
		
		Disjunction myQueryDisjunc = Restrictions.disjunction();
		myQueryDisjunc.add(Restrictions.like("sales", SecurityContextHolder.getContext().getAuthentication().getName(), MatchMode.ANYWHERE));
		myQueryDisjunc.add(Restrictions.like("spv", SecurityContextHolder.getContext().getAuthentication().getName(), MatchMode.ANYWHERE));

		
		criteria.add(myQueryDisjunc);
		
		int totalCount = getHibernateTemplate().findByCriteria(criteria).size();
		
		@SuppressWarnings("unchecked")
		List<M02Salesperson> list = getHibernateTemplate().findByCriteria(criteria, start, pageSize);
		return new  ResultObject(list, totalCount);
	}

	@Override
	public M02Salesperson getM02SalespersonBySales(String sales) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M02Salesperson.class);

		criteria.add(Restrictions.eq("sales", sales));

		return getUniqueResult(criteria);
	}

}
