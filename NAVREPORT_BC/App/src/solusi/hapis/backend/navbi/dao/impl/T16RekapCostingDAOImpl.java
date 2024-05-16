package solusi.hapis.backend.navbi.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.T16RekapCostingDAO;
import solusi.hapis.backend.navbi.model.T16RekapCosting;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class T16RekapCostingDAOImpl extends BasisNAVBIDAO<T16RekapCosting> implements T16RekapCostingDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T16RekapCosting> getListT16RekapCosting(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T16RekapCosting.class);
		
		if (parameterInput != null) {
	
			if (parameterInput.get("sales") != null) {
				criteria.add(Restrictions.ilike("sales", parameterInput.get("sales").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noSo") != null) {
				criteria.add(Restrictions.ilike("noSo", parameterInput.get("noSo").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("customer") != null) {
				criteria.add(Restrictions.ilike("customer", parameterInput.get("customer").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noPoCust") != null) {
				criteria.add(Restrictions.ilike("noPoCust", parameterInput.get("noPoCust").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noInvoice") != null) {
				criteria.add(Restrictions.ilike("noInvoice", parameterInput.get("noInvoice").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("amount") != null) {
				criteria.add(Restrictions.eq("amount", parameterInput.get("amount")));
			}
			
			
			if (parameterInput.get("tanggalInvoicefrom") != null) {
				criteria.add(Restrictions.ge("tglInvoice", parameterInput.get("tanggalInvoicefrom")));
			}
			
			if (parameterInput.get("tanggalInvoiceto") != null) {
				criteria.add(Restrictions.lt("tglInvoice", parameterInput.get("tanggalInvoiceto")));
			}
			
			
			if (parameterInput.get("tanggalLunasfrom") != null) {
				criteria.add(Restrictions.ge("tglLunas", parameterInput.get("tanggalLunasfrom")));
			}
			
			if (parameterInput.get("tanggalLunasto") != null) {
				criteria.add(Restrictions.lt("tglLunas", parameterInput.get("tanggalLunasto")));
			}
					

			if (parameterInput.get("flagKomisi") != null) {
				criteria.add(Restrictions.eq("flagKomisi", parameterInput.get("flagKomisi")));
			}
							
			if (parameterInput.get("flagTqs") != null) {
				criteria.add(Restrictions.eq("flagTqs", parameterInput.get("flagTqs")));
			}
	
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
