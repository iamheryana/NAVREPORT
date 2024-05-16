package solusi.hapis.backend.navbi.dao.impl;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import solusi.hapis.backend.navbi.dao.T21DaftarPenawaranSalesDAO;
import solusi.hapis.backend.navbi.model.T21DaftarPenawaranSales;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T21DaftarPenawaranSalesDAOImpl extends BasisNAVBIDAO<T21DaftarPenawaranSales> implements T21DaftarPenawaranSalesDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<T21DaftarPenawaranSales> getListT21DaftarPenawaranSales(Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T21DaftarPenawaranSales.class);
		
		if (parameterInput != null) {
	
			if (parameterInput.get("company") != null) {
				criteria.add(Restrictions.ilike("company", parameterInput.get("company").toString(), MatchMode.ANYWHERE));
			}
				
			if (parameterInput.get("cabang") != null) {
				criteria.add(Restrictions.ilike("cabang", parameterInput.get("cabang").toString(), MatchMode.ANYWHERE));
			}
						
			
			if (parameterInput.get("tglPenawaranFrom") != null) {
				criteria.add(Restrictions.ge("tglPenawaran", parameterInput.get("tglPenawaranFrom")));
			}
			
			if (parameterInput.get("tglPenawaranTo") != null) {
				criteria.add(Restrictions.lt("tglPenawaran", parameterInput.get("tglPenawaranTo")));
			}
			
			
			if (parameterInput.get("noPenawaran") != null) {
				criteria.add(Restrictions.ilike("noPenawaran", parameterInput.get("noPenawaran").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("namaCustomer") != null) {
				criteria.add(Restrictions.ilike("namaCustomer", parameterInput.get("namaCustomer").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("sektorIndustri") != null) {
				criteria.add(Restrictions.ilike("sektorIndustri", parameterInput.get("sektorIndustri").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("keterangan") != null) {
				criteria.add(Restrictions.ilike("keterangan", parameterInput.get("keterangan").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("salesCode") != null) {
				criteria.add(Restrictions.ilike("salesCode", parameterInput.get("salesCode").toString(), MatchMode.ANYWHERE));
			}
			
			
			if (parameterInput.get("nilai") != null) {
				criteria.add(Restrictions.eq("nilai", parameterInput.get("nilai")));
			}
		
			if (parameterInput.get("statusPenawaran") != null) {
				criteria.add(Restrictions.ilike("statusPenawaran", parameterInput.get("statusPenawaran").toString(), MatchMode.ANYWHERE));
			}
			
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	
	public String callGetNoPenawaran(final String noPenawaran) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_GET_NO_PENAWARAN :p1 ");
				query.setString("p1", noPenawaran);
				query.executeUpdate();
							
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (NO_PENAWARAN AS character varying(30))FROM T22_NO_PENAWARAN WHERE NO_PENAWARAN_LAMA = :p2");
				queryHasil.setString("p2", noPenawaran);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}
}
