package solusi.hapis.backend.navbi.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.P05ParamPreprintInvoiceDAO;
import solusi.hapis.backend.navbi.model.P05ParamPreprintInvoice;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class P05ParamPreprintInvoiceDAOImpl extends BasisNAVBIDAO<P05ParamPreprintInvoice> implements P05ParamPreprintInvoiceDAO  {
	@SuppressWarnings("unchecked")
	@Override
	public List<P05ParamPreprintInvoice> getListP05ParamPreprintInvoice(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P05ParamPreprintInvoice.class);
		
		if (parameterInput != null) {
			if (parameterInput != null) {
				
			}
				if (parameterInput.get("kode") != null) {
					criteria.add(Restrictions.ilike("kode", parameterInput.get("kode").toString(), MatchMode.ANYWHERE));
				}
				
				
		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public P05ParamPreprintInvoice getP05ParamPreprintInvoiceByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(P05ParamPreprintInvoice.class);
		
		
		criteria.add(Restrictions.eq("kode", kode));

		return getUniqueResult(criteria);
	}
}
