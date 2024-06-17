package solusi.hapis.backend.navbi.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import solusi.hapis.backend.navbi.dao.M04ItemSatindoDAO;
import solusi.hapis.backend.navbi.model.M04ItemSatindo;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class M04ItemSatindoDAOImpl extends BasisNAVBIDAO<M04ItemSatindo> implements M04ItemSatindoDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<M04ItemSatindo> getListM04ItemSatindo(
			Map<Object, Object> parameterInput) {
		DetachedCriteria criteria = DetachedCriteria.forClass(M04ItemSatindo.class);
		
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
			
			if (parameterInput.get("satAmountBnsSales") != null) {
				criteria.add(Restrictions.eq("satAmountBnsSales", parameterInput.get("satAmountBnsSales")));
			}
			
			if (parameterInput.get("idmrAmountKomisi") != null) {
				criteria.add(Restrictions.eq("idmrAmountKomisi", parameterInput.get("idmrAmountKomisi")));
			}
			
			if (parameterInput.get("idmrAmountBns") != null) {
				criteria.add(Restrictions.eq("idmrAmountBns", parameterInput.get("idmrAmountBns")));
			}
			
			if (parameterInput.get("idmrAmountBnsSales") != null) {
				criteria.add(Restrictions.eq("idmrAmountBnsSales", parameterInput.get("idmrAmountBnsSales")));
			}
			
							
		
		}
		
		
		return getHibernateTemplate().findByCriteria(criteria);
	}


}
