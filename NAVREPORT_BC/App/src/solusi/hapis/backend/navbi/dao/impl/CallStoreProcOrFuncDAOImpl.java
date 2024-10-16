package solusi.hapis.backend.navbi.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import solusi.hapis.backend.navbi.dao.CallStoreProcOrFuncDAO;

public class CallStoreProcOrFuncDAOImpl extends HibernateDaoSupport implements CallStoreProcOrFuncDAO{

	@Override
	public String callUploadWebinarEvent(final String processId, final String UserId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_WEBINAR_EVENT :p1, :p2 ");
				query.setString("p1", processId);
				query.setString("p2", UserId);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p3");
				queryHasil.setString("p3", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callUploadWebinarFeedback(final String processId, final String webinarId,
			final String UserId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_WEBINAR_FEEDBACK :p1, :p2, :p3 ");
				query.setString("p1", processId);
				query.setString("p2", webinarId);
				query.setString("p3", UserId);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p4");
				queryHasil.setString("p4", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callUploadWebinarPolling(final String processId, final String UserId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_WEBINAR_POLLING :p1, :p2 ");
				query.setString("p1", processId);
				query.setString("p2", UserId);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p3");
				queryHasil.setString("p3", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callEFakturRekon(final String processId, final String Company,
			final String JnsPPN, final String TglFrom, final String TglUpto, final String UserId, final String Action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_EFAKTUR_REKON :p1, :p2, :p3, :p4, :p5, :p6, :p7 ");
				query.setString("p1", processId);
				query.setString("p2", Company);
				query.setString("p3", JnsPPN);
				query.setString("p4", TglFrom);
				query.setString("p5", TglUpto);
				query.setString("p6", UserId);
				query.setString("p7", Action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p8");
				queryHasil.setString("p8", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callSalesRevenue(final String processId, final String tglFrom,
			final String tglUpto, final String company, final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("EXEC P_BC_SALES_REVENUE :p1, :p2, :p3, :p4, :p5 ");	            
//				SQLQuery query = session.createSQLQuery("EXEC P_BC_SALES_REVENUE_PILLAR :p1, :p2, :p3, :p4, :p5 ");	            

//				SQLQuery query = session.createSQLQuery("EXEC P_SALES_REVENUE :p1, :p2, :p3, :p4, :p5 ");	            
//				SQLQuery query = session.createSQLQuery("EXEC P_SALES_REVENUE_NEW :p1, :p2, :p3, :p4, :p5 ");
				query.setString("p1", processId);
				query.setString("p2", tglFrom);
				query.setString("p3", tglUpto);
				query.setString("p4", company);
				query.setString("p5", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callUploadWebinarSurvey(final String processId, final String webinarId,
			final String UserId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_WEBINAR_SURVEY :p1, :p2, :p3 ");
				query.setString("p1", processId);
				query.setString("p2", webinarId);
				query.setString("p3", UserId);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p4");
				queryHasil.setString("p4", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callGetPNLForNeraca(final String processId, final int yearPeriode,
			final int monthPeriode, final String cabang, final BigDecimal pembagi, final String UserId, final String action){
			return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
				@Override
				public String doInHibernate(Session session) throws HibernateException, SQLException {


//					SQLQuery query = session.createSQLQuery("EXEC P_GET_PNL_FOR_NERACA_NEW :p1, :p2, :p3 , :p4, :p5, :p6, :p7");		            
					SQLQuery query = session.createSQLQuery("EXEC P_BC_GET_PNL_FOR_NERACA :p1, :p2, :p3 , :p4, :p5, :p6, :p7");
					query.setString("p1", processId);
					query.setInteger("p2", yearPeriode);
					query.setInteger("p3", monthPeriode);
					query.setString("p4", cabang);
					query.setBigDecimal("p5", pembagi);
					query.setString("p6", UserId);
					query.setString("p7", action);
					
					query.executeUpdate();
					
					SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p8");
					queryHasil.setString("p8", processId);

					
					return ((String)queryHasil.uniqueResult());
					
					
				}
			});
	}

	@Override
	public String callSalesCOGSCorrection(final String processId, final String cabang,
			final String tglFrom, final String tglUpto, final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


//				SQLQuery query = session.createSQLQuery("EXEC P_SALES_COGS_CORRECTION_NEW :p1, :p2, :p3, :p4, :p5 ");	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_SALES_COGS_CORRECTION :p1, :p2, :p3, :p4, :p5 ");
				query.setString("p1", processId);
				query.setString("p2", cabang);
				query.setString("p3", tglFrom);
				query.setString("p4", tglUpto);				
				query.setString("p5", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callGrossSalesMargin(final String processId, final String tglAwalTahun,
			final String tglFrom, final String tglUpto, final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


//				SQLQuery query = session.createSQLQuery("EXEC P_GROSS_SALES_MARGIN_NEW :p1, :p2, :p3, :p4, :p5 ");
				SQLQuery query = session.createSQLQuery("EXEC P_BC_GROSS_SALES_MARGIN :p1, :p2, :p3, :p4, :p5 ");
				
//				SQLQuery query = session.createSQLQuery("EXEC P_GROSS_SALES_MARGIN :p1, :p2, :p3, :p4, :p5 ");
				query.setString("p1", processId);
				query.setString("p2", tglAwalTahun);
				query.setString("p3", tglFrom);
				query.setString("p4", tglUpto);				
				query.setString("p5", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callUploadWebinarQA(final String processId, final String UserId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_WEBINAR_QA :p1, :p2 ");
				query.setString("p1", processId);
				query.setString("p2", UserId);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p3");
				queryHasil.setString("p3", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callDownloadInvoiceLunas(final String processId, final String tglFrom,
			final String tglUpto, final String jenisCosting, final String UserId) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_DOWNLOAD_INVOICE_LUNAS :p1, :p2, :p3, :p4, :p5 ");
				query.setString("p1", processId);
				query.setString("p2", tglFrom);
				query.setString("p3", tglUpto);
				query.setString("p4", jenisCosting);				
				query.setString("p5", UserId);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callProsesKomisiSatindo(final String processId, final String masa,
			final String tahun, final String status) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_PROSES_KOMISI_SATINDO :p1, :p2, :p3, :p4");
				query.setString("p1", processId);
				query.setString("p2", masa);
				query.setString("p3", tahun);
				query.setString("p4", status);	
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p5");
				queryHasil.setString("p5", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callProsesTQSSatindo(final String processId, final String masa,
			final String tahun, final String status) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_PROSES_TQS_SATINDO :p1, :p2, :p3, :p4");
				query.setString("p1", processId);
				query.setString("p2", masa);
				query.setString("p3", tahun);
				query.setString("p4", status);	
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p5");
				queryHasil.setString("p5", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}
	@Override
	public String callArusKasPiutang(final String processId, final String tglFrom,
			final String tglUpto, final String company, final String action) {
	return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
		@Override
		public String doInHibernate(Session session) throws HibernateException, SQLException {


            
			SQLQuery query = session.createSQLQuery("EXEC P_BC_ARUS_KAS_PIUTANG :p1, :p2, :p3, :p4, :p5 ");
			query.setString("p1", processId);
			query.setString("p2", tglFrom);
			query.setString("p3", tglUpto);
			query.setString("p4", company);				
			query.setString("p5", action);
			query.executeUpdate();
			
			SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
			queryHasil.setString("p6", processId);

			
			return ((String)queryHasil.uniqueResult());
			
			
		}
	});
	}


	@Override
	public String callReportPNLManagement(final String processId, final String tglFrom,
			final String tglUpto, final String cabang, final BigDecimal pembagi,  final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


//				SQLQuery query = session.createSQLQuery("EXEC P_REPORT_PNL_MANAGEMENT_NEW :p1, :p2, :p3, :p4, :p5 , :p6 ");			 
//				SQLQuery query = session.createSQLQuery("EXEC P_BC_REPORT_PNL_MANAGEMENT :p1, :p2, :p3, :p4, :p5 , :p6 ");
				SQLQuery query = session.createSQLQuery("EXEC P_BC_REPORT_PNL_MANAGEMENT_2 :p1, :p2, :p3, :p4, :p5 , :p6 ");
				query.setString("p1", processId);
				query.setString("p2", tglFrom);
				query.setString("p3", tglUpto);
				query.setString("p4", cabang);		
				query.setBigDecimal("p5", pembagi);
				query.setString("p6", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callReportNERACAManagement(final String processId, final String tglFrom,
			final String tglUpto, final String cabang, final BigDecimal pembagi, final String userID,
			final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


//				SQLQuery query = session.createSQLQuery("EXEC P_REPORT_NERACA_MANAGEMENT_NEW :p1, :p2, :p3, :p4, :p5 , :p6 , :p7 ");				            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_REPORT_NERACA_MANAGEMENT :p1, :p2, :p3, :p4, :p5 , :p6 , :p7 ");
				query.setString("p1", processId);
				query.setString("p2", tglFrom);
				query.setString("p3", tglUpto);
				query.setString("p4", cabang);		
				query.setBigDecimal("p5", pembagi);
				query.setString("p6", userID);
				query.setString("p7", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p8");
				queryHasil.setString("p8", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callCOGSAnalysis(final String processId, final String cabang,
			final String tglFrom, final String tglUpto, final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


//				SQLQuery query = session.createSQLQuery("EXEC P_SALES_COGS_ANALYSIS_NEW :p1, :p2, :p3, :p4, :p5 ");
	            SQLQuery query = session.createSQLQuery("EXEC P_BC_SALES_COGS_ANALYSIS :p1, :p2, :p3, :p4, :p5 ");
				
	            query.setString("p1", processId);
				query.setString("p2", cabang);
				query.setString("p3", tglFrom);
				query.setString("p4", tglUpto);				
				query.setString("p5", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callReportGeneratorPNL(final String processId, final String company,
			final String tglFrom, final String tglUpto, final String namaReport,
			final String tipeKolom, final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_REPORT_GENERATOR :p1, :p2, :p3, :p4, :p5 , :p6 , :p7 ");
				query.setString("p1", processId);
				query.setString("p2", company);
				query.setString("p3", tglFrom);
				query.setString("p4", tglUpto);		
				query.setString("p5", namaReport);
				query.setString("p6", tipeKolom);
				query.setString("p7", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callReportGeneratorNERACA(final String processId, final String company,
			final String tglFrom, final String tglUpto, final String namaReport,
			final String tipeKolom, final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_REPORT_GENERATOR_NERACA :p1, :p2, :p3, :p4, :p5 , :p6 , :p7 ");
				query.setString("p1", processId);
				query.setString("p2", company);
				query.setString("p3", tglFrom);
				query.setString("p4", tglUpto);		
				query.setString("p5", namaReport);
				query.setString("p6", tipeKolom);
				query.setString("p7", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callCekStatusKomisi(final String processId, final String sales,
			final String extDocNo, final String customer, final String noBSO, final String noInvoice,
			final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_CEK_STATUS_KOMISI :p1, :p2, :p3, :p4, :p5 , :p6 , :p7 ");
				query.setString("p1", processId);
				query.setString("p2", sales);
				query.setString("p3", extDocNo);
				query.setString("p4", customer);		
				query.setString("p5", noBSO);
				query.setString("p6", noInvoice);
				query.setString("p7", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callSalesMarginAnalysis(final String processId,final String periodeFrom,
			final String periodeUpto, final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_SALES_MARGIN_ANALYSIS :p1, :p2, :p3, :p4 ");
				query.setString("p1", processId);
				query.setString("p2", periodeFrom);
				query.setString("p3", periodeUpto);
				query.setString("p4", action);	
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p5");
				queryHasil.setString("p5", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callOutstandingSO(final String processId, final String tglFrom,
			final String tglUpto, final String company, final String cabang, final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


				SQLQuery query = session.createSQLQuery("EXEC P_BC_OUTSTANDING_SO :p1, :p2, :p3, :p4, :p5, :p6 ");	            
				query.setString("p1", processId);
				query.setString("p2", tglFrom);
				query.setString("p3", tglUpto);
				query.setString("p4", company);
				query.setString("p5", cabang);
				query.setString("p6", action);	
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p1");
				queryHasil.setString("p1", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callOutstandingUM(final String processId, final String cabang,
			final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_OUTSTANDING_UM :p1, :p2, :p3 ");
				query.setString("p1", processId);
				query.setString("p2", cabang);
				query.setString("p3", action);	
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p1");
				queryHasil.setString("p1", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callSyncAReport(final String kodeReport) {

		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC A_SYNC_A_REPORT :p1 ");
				query.setString("p1", kodeReport);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p1");
				queryHasil.setString("p1", "XXX");

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callProsesGenerateSROSO(final String modeReport) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC P_BC_AUTOGENERATE_SROSO :p1 ");
				query.setString("p1", modeReport);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p1");
				queryHasil.setString("p1", "XXX");

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callGrossMarginSalesman(final String processId, final String tglFrom,
			final String tglUpto, final String action) {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


				SQLQuery query = session.createSQLQuery("EXEC P_BC_GROSS_MARGIN_SALESMAN :p1, :p2, :p3, :p4");
				
				query.setString("p1", processId);
				query.setString("p2", tglFrom);
				query.setString("p3", tglUpto);				
				query.setString("p4", action);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p6");
				queryHasil.setString("p6", processId);

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

	@Override
	public String callSyncAReportManual(final String kodeReport) {

		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {


	            
				SQLQuery query = session.createSQLQuery("EXEC A_SYNC_A_REPORT_MANUAL :p1 ");
				query.setString("p1", kodeReport);
				query.executeUpdate();
				
				SQLQuery queryHasil = session.createSQLQuery("SELECT CAST (RESULT_STRING AS character varying(100))FROM TEMP00_UPLOAD_RESULT WHERE PROSES_ID = :p1");
				queryHasil.setString("p1", "XXX");

				
				return ((String)queryHasil.uniqueResult());
				
				
			}
		});
	}

}
