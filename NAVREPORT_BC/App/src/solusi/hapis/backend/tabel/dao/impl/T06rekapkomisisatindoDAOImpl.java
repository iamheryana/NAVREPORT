package solusi.hapis.backend.tabel.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.security.dao.impl.BasisDAO;
import solusi.hapis.backend.tabel.dao.T06rekapkomisisatindoDAO;
import solusi.hapis.backend.tabel.model.T06rekapkomisisatindo;

public class T06rekapkomisisatindoDAOImpl extends BasisDAO<T06rekapkomisisatindo> implements T06rekapkomisisatindoDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T06rekapkomisisatindo> getListT06rekapkomisisatindo(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T06rekapkomisisatindo.class);
		
		if (parameterInput != null) {

			if (parameterInput.get("noInvoice") != null) {
				criteria.add(Restrictions.ilike("noInvoice", parameterInput.get("noInvoice").toString(), MatchMode.ANYWHERE));
			}
						
			if (parameterInput.get("tanggalInvoicefrom") != null) {
				criteria.add(Restrictions.ge("tglInvoice", parameterInput.get("tanggalInvoicefrom")));
			}
			
			if (parameterInput.get("tanggalInvoiceto") != null) {
				criteria.add(Restrictions.lt("tglInvoice", parameterInput.get("tanggalInvoiceto")));
			}
			
			if (parameterInput.get("noPoCust") != null) {
				criteria.add(Restrictions.ilike("noPoCust", parameterInput.get("noPoCust").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("sellToName") != null) {
				criteria.add(Restrictions.ilike("sellToName", parameterInput.get("sellToName").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("billToName") != null) {
				criteria.add(Restrictions.ilike("billToName", parameterInput.get("billToName").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("noItem") != null) {
				criteria.add(Restrictions.ilike("noItem", parameterInput.get("noItem").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("qty") != null) {
				criteria.add(Restrictions.eq("qty", parameterInput.get("qty")));
			}
			
			if (parameterInput.get("amountKomisi") != null) {
				criteria.add(Restrictions.eq("amountKomisi", parameterInput.get("amountKomisi")));
			}
			
			if (parameterInput.get("tanggalLunasfrom") != null) {
				criteria.add(Restrictions.ge("tglLunas", parameterInput.get("tanggalLunasfrom")));
			}
			
			if (parameterInput.get("tanggalLunasto") != null) {
				criteria.add(Restrictions.lt("tglLunas", parameterInput.get("tanggalLunasto")));
			}
			
			
			if (parameterInput.get("masaKomisi") != null) {
				criteria.add(Restrictions.ilike("masaKomisi", parameterInput.get("masaKomisi").toString(), MatchMode.ANYWHERE));
			}
			
			if (parameterInput.get("tahunKomisi") != null) {
				criteria.add(Restrictions.ilike("tahunKomisi", parameterInput.get("tahunKomisi").toString(), MatchMode.ANYWHERE));
			}
			
			
			if (parameterInput.get("flagKomisi") != null) {
				criteria.add(Restrictions.eq("flagKomisi", parameterInput.get("flagKomisi")));
			}
				
		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}


}
