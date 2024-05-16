package solusi.hapis.backend.tabel.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.security.dao.impl.BasisDAO;
import solusi.hapis.backend.tabel.dao.T07itemsatindoDAO;
import solusi.hapis.backend.tabel.model.T07itemsatindo;

public class T07itemsatindoDAOImpl extends BasisDAO<T07itemsatindo> implements T07itemsatindoDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<T07itemsatindo> getListT07itemsatindo(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(T07itemsatindo.class);
		
		if (parameterInput != null) {

				
			if (parameterInput.get("tanggalBerlakufrom") != null) {
				criteria.add(Restrictions.ge("tglBerlaku", parameterInput.get("tanggalBerlakufrom")));
			}
			
			if (parameterInput.get("tanggalBerlakuto") != null) {
				criteria.add(Restrictions.lt("tglBerlaku", parameterInput.get("tanggalBerlakuto")));
			}
			
			if (parameterInput.get("noItem") != null) {
				criteria.add(Restrictions.ilike("noItem", parameterInput.get("noItem").toString(), MatchMode.ANYWHERE));
			}
								
			if (parameterInput.get("satAmountKomisi") != null) {
				criteria.add(Restrictions.eq("satAmountKomisi", parameterInput.get("satAmountKomisi")));
			}
			
			if (parameterInput.get("satAmountBns") != null) {
				criteria.add(Restrictions.eq("satAmountBns", parameterInput.get("satAmountBns")));
			}
			
			if (parameterInput.get("idmrAmountKomisi") != null) {
				criteria.add(Restrictions.eq("idmrAmountKomisi", parameterInput.get("idmrAmountKomisi")));
			}
			
			if (parameterInput.get("idmrAmountBns") != null) {
				criteria.add(Restrictions.eq("idmrAmountBns", parameterInput.get("idmrAmountBns")));
			}
			
							
		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}


}
