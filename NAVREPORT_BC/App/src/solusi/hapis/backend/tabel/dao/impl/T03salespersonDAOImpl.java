package solusi.hapis.backend.tabel.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.security.dao.impl.BasisDAO;
import solusi.hapis.backend.tabel.dao.T03salespersonDAO;
import solusi.hapis.backend.tabel.model.T03salesperson;

public class T03salespersonDAOImpl extends BasisDAO<T03salesperson> implements T03salespersonDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T03salesperson> getListT03salesperson(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T03salesperson.class);
		
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
	public ResultObject getListT03salespersonLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T03salesperson.class);
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
		List<T03salesperson> list = getHibernateTemplate().findByCriteria(criteria, start, pageSize);
		return new  ResultObject(list, totalCount);
	}

	@Override
	public ResultObject getListT03salespersonLOVFilter(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T03salesperson.class);
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
		List<T03salesperson> list = getHibernateTemplate().findByCriteria(criteria, start, pageSize);
		return new  ResultObject(list, totalCount);
	}

}
