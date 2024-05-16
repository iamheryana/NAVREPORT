package solusi.hapis.backend.parameter.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import solusi.hapis.backend.parameter.dao.SelectQueryNavReportDAO;

public class SelectQueryNavReportDAOImpl  extends HibernateDaoSupport implements SelectQueryNavReportDAO {

	@Override
	public List<Object[]> QuerySalesperson() {
		
				
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT (sales||' - '||sales_name) as LABEL, sales as VALUE FROM t03salesperson");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public String callDownloadInvoiceLunas(final String processId) {
			
			return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
				@Override
				public String doInHibernate(Session session) throws HibernateException, SQLException {

					SQLQuery query = session.createSQLQuery(" select p_download_invoice_lunas(:p1) ");
					query.setString("p1", processId);
					
					return ((String)query.uniqueResult());
				}
			});
	}

	@Override
	public List<Object[]> QueryBulan() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT X.LABEL, X.VALUE "+
															"FROM	( "+
															"SELECT CAST('<blank>' AS character varying(15)) AS LABEL, CAST('' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('JANUARI' AS character varying(15)) AS LABEL, CAST('01' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('FEBRUARI' AS character varying(15)) AS LABEL, CAST('02' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('MARET' AS character varying(15)) AS LABEL, CAST('03' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('APRIL' AS character varying(15)) AS LABEL, CAST('04' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('MEI' AS character varying(15)) AS LABEL, CAST('05' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('JUNI' AS character varying(15)) AS LABEL, CAST('06' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('JULI' AS character varying(15)) AS LABEL, CAST('07' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('AGUSTUS' AS character varying(15)) AS LABEL, CAST('08' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('SEPTEMBER' AS character varying(15)) AS LABEL, CAST('09' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('OKTOBER' AS character varying(15)) AS LABEL, CAST('10' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('NOVEMBER' AS character varying(15)) AS LABEL, CAST('11' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('DESEMBER' AS character varying(15)) AS LABEL, CAST('12' AS character varying(2)) AS VALUE "+
															") X");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryBulanTQS() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT X.LABEL, X.VALUE "+
															"FROM	( "+
															"SELECT CAST('<blank>' AS character varying(15)) AS LABEL, CAST('' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('JANUARI' AS character varying(15)) AS LABEL, CAST('01' AS character varying(2)) AS VALUE "+
															"UNION ALL "+
															"SELECT CAST('MARET' AS character varying(15)) AS LABEL, CAST('03' AS character varying(2)) AS VALUE "+
															") X");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> CekPeriodeClosingCosting(final String masa, final String tahun) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT close_komisi, close_tqs FROM t05periodeCosting WHERE masa = '"+masa+"'  AND tahun = '"+tahun+"'");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public String callDeleteInvoiceLunasTemp(final String processId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(" select p_delete_invoice_lunas_temp(:p1) ");
				query.setString("p1", processId);
				
				return ((String)query.uniqueResult());
			}
		});
	}

	@Override
	public String callDownloadInvoiceLunasSatindo(final String processId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(" select p_download_invoice_lunas_satindo(:p1) ");
				query.setString("p1", processId);
				
				return ((String)query.uniqueResult());
			}
		});
	}

	@Override
	public String callProsesKomisiSatindo(final String masa, final String tahun,
			final String status) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(" select p_proses_komisi_satindo(:p1,:p2,:p3) ");
				query.setString("p1", masa);
				query.setString("p2", tahun);
				query.setString("p3", status);
				
				return ((String)query.uniqueResult());
			}
		});
	}

	@Override
	public String callDeleteInvoiceSatindoTemp(final String processId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(" select p_delete_invoice_satindo_temp(:p1) ");
				query.setString("p1", processId);
				
				return ((String)query.uniqueResult());
			}
		});
	}

	@Override
	public String callCekPeriodeClosing(final Date pTanggal) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(" select p_cek_periode_closing(:p1) ");
				query.setDate("p1", pTanggal);
				
				return ((String)query.uniqueResult());
			}
		});
	}
	
}
