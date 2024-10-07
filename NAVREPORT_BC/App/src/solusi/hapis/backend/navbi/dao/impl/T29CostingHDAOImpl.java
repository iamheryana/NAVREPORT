package solusi.hapis.backend.navbi.dao.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import solusi.hapis.backend.navbi.dao.T29CostingHDAO;
import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T29CostingHDAOImpl extends BasisNAVBIDAO<T29CostingH> implements T29CostingHDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T29CostingH> getListT29CostingH(
			Map<Object, Object> parameterInput) {
DetachedCriteria criteria = DetachedCriteria.forClass(T29CostingH.class);

		if (parameterInput != null) {

			if (parameterInput.get("noCosting") != null) {
				criteria.add(Restrictions.ilike("noCosting", parameterInput.get("noCosting").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noBso") != null) {
				criteria.add(Restrictions.ilike("noBso", parameterInput.get("noBso").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noSo") != null) {
				criteria.add(Restrictions.ilike("noSo", parameterInput.get("noSo").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noPoCustomer") != null) {
				criteria.add(Restrictions.ilike("noPoCustomer", parameterInput.get("noPoCustomer").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("salesman") != null) {
				criteria.add(Restrictions.ilike("salesman", parameterInput.get("salesman").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("customer") != null) {
				criteria.add(Restrictions.ilike("customer", parameterInput.get("customer").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("tglCostingfrom") != null) {
				criteria.add(Restrictions.ge("tglCosting", parameterInput.get("tglCostingfrom")));
			}
			
			if (parameterInput.get("tglCostingto") != null) {
				criteria.add(Restrictions.lt("tglCosting", parameterInput.get("tglCostingto")));
			}
			
			if (parameterInput.get("FilterSales") != null) {
				criteria.add(Restrictions.in("salesman", (Collection<String>)parameterInput.get("FilterSales")));
			}
				
			if (parameterInput.get("flagStatus") != null) {
				criteria.add(Restrictions.ilike("flagStatus", parameterInput.get("flagStatus").toString(), MatchMode.START));
			}
			
			if (parameterInput.get("flagInvoice") != null) {
				criteria.add(Restrictions.eq("flagInvoice", parameterInput.get("flagInvoice").toString()));
			}
			
			if (parameterInput.get("flagLunas") != null) {
				criteria.add(Restrictions.eq("flagLunas", parameterInput.get("flagLunas").toString()));
			}
		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public String callGetNoCosting(final String noCosting) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_GET_NO_COSTING :p1 ");
				query.setString("p1", noCosting);
				query.executeUpdate();
							
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (NO_PENAWARAN AS character varying(30))FROM T22_NO_PENAWARAN WHERE NO_PENAWARAN_LAMA = :p2");
				queryHasil.setString("p2", noCosting);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public T29CostingH getT29CostingHByNoCosting(String noCosting) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(T29CostingH.class);

		criteria.add(Restrictions.eq("noCosting", noCosting));

		return getUniqueResult(criteria);

	}


}
