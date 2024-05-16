package solusi.hapis.backend.parameter.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import solusi.hapis.backend.parameter.dao.SelectQueryHeskDAO;

public class SelectQueryHeskDAOImpl extends HibernateDaoSupport implements SelectQueryHeskDAO {


	@Override
	public String callGetHistory(final String processId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery("call get_history(:p1)");
				query.setString("p1", processId);

				return ((String)query.uniqueResult());
			}
		});
	}
}
