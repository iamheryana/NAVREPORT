package solusi.hapis.backend.navbi.dao.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import solusi.hapis.backend.navbi.dao.BCAccessFromDAO;
import solusi.hapis.backend.navbi.model.BCAccessFrom;
import solusi.hapis.backend.security.dao.impl.BasisNAVBIDAO;

public class BCAccessFromDAOImpl extends BasisNAVBIDAO<BCAccessFrom> implements BCAccessFromDAO{

	@Override
	public BCAccessFrom getLastSync() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BCAccessFrom.class);
		
		Collection<String> myCollection = new ArrayList<String>();
		myCollection.add("BC");
		myCollection.add("NAV");

		criteria.add(Restrictions.in("code", myCollection));
		

		return (BCAccessFrom) DataAccessUtils.uniqueResult(getHibernateTemplate()
				.findByCriteria(criteria));
	}

}
