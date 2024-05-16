package solusi.hapis.backend.parameter.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import solusi.hapis.backend.parameter.dao.SelectQueryDAO;

public class SelectQueryDAOImpl  extends HibernateDaoSupport implements SelectQueryDAO {

	@Override
	public List<Object[]> QueryCabang() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT CAST(([Code] + ' - ' +[Name]) AS VARCHAR) AS LABEL ,CAST([Name] AS VARCHAR) AS NAME FROM [dbo].[BC_AUTOJAYA$Responsibility Center] ORDER BY [Code]");
		
				return (List<Object[]>)query.list();
			}
		});
	}
	
	@Override
	public List<Object[]> QueryProductGroup() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT CAST(([Code] + ' - '+[Description]) AS VARCHAR(100)) AS LABEL "+
														",CAST([Code] AS VARCHAR) AS VALUE "+
														"FROM [dbo].[BC_AUTOJAYA$Product Group] "+
														"WHERE [Item Category Code] <> ''");
		
				return (List<Object[]>)query.list();
			}
		});
	}
	
	@Override
	public List<Object[]> QueryPrincipal() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	"SELECT CAST(X.[Code] +' - '+ MAX(X.[Name]) AS VARCHAR(100)) AS LABEL, CAST(X.[Code] AS VARCHAR) AS VALUE "+
															"FROM	("+
															"			SELECT [Code]"+
															"				  ,[Name]"+
															"			FROM [dbo].[BC_AUTOJAYA$Dimension Value]"+
															"			WHERE [Dimension Code] = 'PRINCIPAL' AND"+
															"				  ([Name] NOT LIKE '%UNUSED%' OR [Blocked] = 0) "+
															"			UNION ALL"+
															"			SELECT [Code]"+
															"				  ,[Name]"+
															"			FROM [dbo].[BC_SOLUSI$Dimension Value]"+
															"			WHERE [Dimension Code] = 'PRINCIPAL' AND"+
															"				  ([Name] NOT LIKE '%UNUSED%' OR [Blocked] = 0) "+
															"		)X "+
															" GROUP BY X.[Code]");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryCabang2() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT CAST(([Code] + ' - ' +[Name]) AS VARCHAR) AS LABEL ,CAST([Code] AS VARCHAR) AS VALUE FROM [dbo].[BC_AUTOJAYA$Responsibility Center] ORDER BY [Code]");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryItemCategory() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT	CAST([Code]+' - '+[Description] AS VARCHAR(100)) AS LABEL, "
						+ "CAST([Code] AS VARCHAR) AS VALUE "
						+ "FROM [dbo].[BC_AUTOJAYA$Item Category] "
						+ "ORDER BY [Code]" );
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryLocation() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT CAST([Name] AS VARCHAR(100)) AS LABEL, CAST([Code] AS VARCHAR) AS VALUE FROM [dbo].[BC_AUTOJAYA$Location] WHERE	([Code] LIKE '%TD%' OR [Code] LIKE '%SC%' OR [Code] LIKE '%SPB%' OR [Code] LIKE '%ALK%' ) AND ([Code] NOT IN ('SMR-SC','SC-MC', 'MIP-SC')) ORDER BY [Code]");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QuerySalesman() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
															"SELECT CAST(([Code] + ' - '+[Name]) AS VARCHAR(100)) AS LABEL "+
															" ,CAST([Code] AS VARCHAR) AS VALUE "+
															" FROM [dbo].[BC_AUTOJAYA$Salesperson_Purchaser]"
														);
				
				
				return (List<Object[]>)query.list();
			}
		});
	}
	
	public List<Object[]> QuerySalesmanName() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT CAST(([Name]) AS VARCHAR(100)) AS LABEL "+
														" ,CAST([Code] AS VARCHAR) AS VALUE "+
														" FROM [dbo].[BC_AUTOJAYA$Salesperson_Purchaser]");		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryPrincipalInfo() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
						"SELECT CAST(X.[Code] + ' - ' + MAX(X.[Name]) AS VARCHAR(100)) AS LABEL, CAST(X.[Code] AS VARCHAR) AS VALUE "+
						"FROM	( "+
						"			SELECT [Code] "+
						"				  ,[Name] "+
						"			FROM [dbo].[BC_AUTOJAYA$Dimension Value] "+
						"			WHERE [Dimension Code] = 'PRINCIPAL' AND "+
						"				  [Name] NOT LIKE '%UNUSED%' AND "+
						"   			  [Code] IN ( "+
						"							SELECT DISTINCT [PRINCIPAL_CODE] "+
						"							FROM [dbo].[T19_PI_ITEM] "+
						"							WHERE [BERLAKU]=  'Y' "+
						"						) "+
						"			UNION ALL "+
						"			SELECT [Code] "+
						"				  ,[Name] "+
						"			FROM [dbo].[BC_SOLUSI$Dimension Value] "+
						"			WHERE [Dimension Code] = 'PRINCIPAL' AND "+
						"				  [Name] NOT LIKE '%UNUSED%' AND "+
						"  			  [Code] IN ( "+
						"							SELECT DISTINCT [PRINCIPAL_CODE] "+
						"							FROM [dbo].[T19_PI_ITEM] "+
						"							WHERE [BERLAKU]=  'Y'						 "+
						"						) "+
						"		)X  "+
						" GROUP BY X.[Code] "
						);	
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryProductGroupLeadTime() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT CAST(([Code] + ' - '+[Description]) AS VARCHAR(100)) AS LABEL "+
														",CAST([Code] AS VARCHAR) AS VALUE "+
														"FROM [dbo].[BC_AUTOJAYA$Product Group] "+
														"WHERE [Item Category Code] <> '' AND"+
														"[Code] IN ('H001', 'H002', 'H003', 'H004', 'H006', 'H008', "+
														"'H022', 'H013', 'H014', 'S101', 'S102', 'S104', "+
														"'S401', 'S402', 'H001', 'H002', 'H003', 'H004', "+
														"'H006', 'H008', 'H011', 'H013', 'H014', 'S101', "+
														"'S102', 'S104', 'S401', 'S402') "
														);
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryVendorOTP() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(
				"SELECT	CAST(([No_]+' - '+MAX([Name])) AS VARCHAR(200)) AS LABEL "+ 
				"		,CAST([No_] AS VARCHAR) AS VALUE "+
				"FROM	( "+
				"			SELECT [No_] "+
				"				  ,[Name] "+
				"			FROM [dbo].[BC_AUTOJAYA$Vendor] "+
				"			UNION ALL "+
				"			SELECT [No_] "+
				"				  ,[Name] "+
				"			FROM [dbo].[BC_SOLUSI$Vendor] "+
				"		) X "+
				"WHERE	[No_] IN	( "+
				"						SELECT	DISTINCT POH.[Pay-to Vendor No_] AS VENDOR_NO "+
				"						FROM [dbo].[BC_AUTOJAYA$Purchase Header] POH "+
				"						WHERE POH.[Document Type] = 1 AND SUBSTRING(POH.[No_],5,1) = 'P' "+
				"						UNION ALL "+
				"						SELECT	DISTINCT POH.[Pay-to Vendor No_] AS VENDOR_NO "+
				"						FROM [dbo].[BC_SOLUSI$Purchase Header] POH "+
				"						WHERE POH.[Document Type] = 1 AND SUBSTRING(POH.[No_],5,1) = 'P' "+
				"					) "+
				"GROUP BY [No_]"
						);
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryLocationSPB() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT CAST([Name] AS VARCHAR) AS LABEL, CAST([Code] AS VARCHAR) AS NAME FROM [dbo].[BC_AUTOJAYA$Location] WHERE [Code] LIKE '%SPB%'");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryResponsibilityTO() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(					
						"SELECT CAST(LABEL AS VARCHAR) AS LABEL, CAST(NAME AS VARCHAR) AS NAME "+
						"FROM	(  "+
						"			SELECT 'JAKARTA' AS LABEL, 'JAKARTA' AS NAME "+
						"			UNION ALL "+
						"			SELECT 'CIKARANG' AS LABEL, 'CIKARANG' AS NAME "+
						"			UNION ALL "+
						"			SELECT 'SEMARANG' AS LABEL, 'SEMARANG' AS NAME "+
						"			UNION ALL "+
						"			SELECT 'SURABAYA' AS LABEL, 'SURABAYA' AS NAME "+
						"			UNION ALL "+
						"			SELECT 'BALI' AS LABEL, 'BALI' AS NAME "+
						"			UNION ALL "+
						"			SELECT 'SERVICE CENTER' AS LABEL, 'SERVICE CENTER' AS NAME "+
						"			UNION ALL "+
						"			SELECT 'ONLINE' AS LABEL, 'ONLINE' AS NAME "+
						"			UNION ALL "+
						"			SELECT 'OTHERS' AS LABEL, 'OTHERS' AS NAME "+
						"		) X ");
						return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryLocationFA() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT CAST(([Code] + ' - ' +[Name]) AS VARCHAR) AS LABEL ,CAST([Code] AS VARCHAR) AS NAME FROM [dbo].[BC_AUTOJAYA$FA Location] ORDER BY [Code]");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryProject() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
						"SELECT CAST(X.[Code] +' - '+ MAX(X.[Name]) AS VARCHAR(100)) AS LABEL, CAST(X.[Code] AS VARCHAR) AS VALUE "+
						"FROM	("+
						"			SELECT [Code]"+
						"				  ,[Name]"+
						"			FROM [dbo].[BC_AUTOJAYA$Dimension Value]"+
						"			WHERE [Dimension Code] = 'PROJECT' AND"+
						"				  ([Name] NOT LIKE '%UNUSED%' OR [Blocked] = 0) "+
						"			UNION ALL"+
						"			SELECT [Code]"+
						"				  ,[Name]"+
						"			FROM [dbo].[BC_SOLUSI$Dimension Value]"+
						"			WHERE [Dimension Code] = 'PROJECT' AND"+
						"				  ([Name] NOT LIKE '%UNUSED%' OR [Blocked] = 0) "+
						"		)X "+
						"WHERE X.[Code] NOT IN ('NONE', 'PROJECT')"+
						"GROUP BY X.[Code]"
						);		
				return (List<Object[]>)query.list();
			}
		});

	}

	@Override
	public List<Object[]> QueryFAClass() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
						"SELECT CAST(X.[Code] +' - '+ MAX(X.[Name]) AS VARCHAR(100)) AS LABEL, CAST(X.[Code] AS VARCHAR) AS VALUE"+
						" FROM"+
						" ("+
						" SELECT [Code]"+
						"       ,[Name]"+
						" FROM [dbo].[BC_AUTOJAYA$FA Class]"+
						" UNION ALL"+
						" SELECT [Code]"+
						"       ,[Name]"+
						" FROM [dbo].[BC_SOLUSI$FA Class]"+
						" ) X"+
						" GROUP BY X.[Code]"
						);		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QuerySalesmanActive() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
															"SELECT X.LABEL, X.VALUE  "+
															"FROM 	(  "+
															"			SELECT	DISTINCT CAST((CMP.SLS_OR_CAB + ' - '+SLS.[Name] ) AS VARCHAR(100)) AS LABEL    "+
															"					,CAST(CMP.SLS_OR_CAB AS VARCHAR) AS VALUE     "+
															"			FROM [dbo].[M06_TARGET_PIPELINE]  CMP   "+
															"			LEFT JOIN [dbo].[BC_AUTOJAYA$Salesperson_Purchaser] SLS ON SLS.[Code] = CMP.[SLS_OR_CAB]    "+
															"			WHERE CMP.JENIS = 'SALES' AND CMP.STATUS = 'ACTIVE' AND SLS.[Code] IS NOT NULL  "+
															"		) X  "+
															"ORDER BY X.LABEL"
														);
				
				
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QuerySalesmanCustomer() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
				
															"SELECT	CAST((SLS.[Code] + ' - '+SLS.[Name]) AS VARCHAR(100)) AS LABEL "+
															"		 ,CAST(SLS.[Code] AS VARCHAR) AS VALUE "+
															"FROM [dbo].[BC_AUTOJAYA$Salesperson_Purchaser] SLS "+
															"WHERE SLS.[Code] IN (	 "+
															"						SELECT DISTINCT CUS.[Salesperson Code] AS SALES "+
															"						FROM [dbo].[BC_AUTOJAYA$Customer] CUS "+
															"						WHERE	CUS.[Blocked] = 0 AND "+
															"								CUS.[Salesperson Code] <> '' "+
															"					) "	+
															"ORDER BY SLS.[Code]"
														);
				
				
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QuerySalesmanForContact() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
															"SELECT	CAST(([Code] + ' - '+[Name]) AS VARCHAR(100)) AS LABEL "+
															"		 ,CAST([Code] AS VARCHAR) AS VALUE "+ 
															"FROM [dbo].[BC_AUTOJAYA$Salesperson_Purchaser] "+ 
															"WHERE [Code] IN (	SELECT  DISTINCT [Salesperson Code] "+
															"					FROM [dbo].[BC_AUTOJAYA$Contact] "+
															"					WHERE  [Type] = 1 AND [Salesperson Code] <> '' ) "+
															"ORDER BY [Code]"
														);
				
				
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryFeedbackPollingQst(final long webinarID) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(					
										"SELECT CAST(X.QUESTION AS VARCHAR(320)) AS LABEL, "+
										"		CAST(X.QST_ID AS VARCHAR) AS VALUE   "+
										"FROM	( "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '01' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_01 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_01 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '02' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_02 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_02 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '03' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_03 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_03 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '04' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_04 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_04 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '05' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_05 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_05 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '06' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_06 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_06 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '07' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_07 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_07 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '08' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_08 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_08 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '09' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_09 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_09 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '10' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_10 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_10 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '11' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_11 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_11 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '12' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_12 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_12 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '13' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_13 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_13 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '14' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_14 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_14 IS NOT NULL "+
										"UNION ALL "+
										"SELECT  DISTINCT T05_ID "+
										"		, RIGHT(('000'+SESI), 3) + '15' AS QST_ID "+
										"		, 'Pooling - Sesi-'+SESI+' - '+QST_15 AS QUESTION   "+
										"FROM [dbo].[T11_WEBINAR_POLLING]  "+
										"WHERE QST_15 IS NOT NULL "+
										"UNION ALL "+
										"SELECT T05_ID "+
										"	  , 'F'+CAST(T07_ID AS VARCHAR) AS QST_ID "+
										"      ,'Feedback - '+ QUESTION AS QUESTION "+
										"FROM [dbo].[T07_FEEDBACK_QST] "+
										"WHERE SOURCES = '1' "+
										"UNION ALL "+
										"SELECT T05_ID "+
										"	  , 'F'+CAST(T07_ID AS VARCHAR) AS QST_ID "+
										"      ,'Survey - '+ QUESTION AS QUESTION "+
										"FROM [dbo].[T07_FEEDBACK_QST] "+
										"WHERE SOURCES = '2' "+
										"		) X "+
										"WHERE X.T05_ID = "+webinarID+
										"ORDER BY X.QST_ID " 
														);
				
				
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryKendaraan() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
						
															"SELECT CAST(X.[Code] +' - '+ MAX(X.[Name]) AS VARCHAR(100)) AS LABEL, CAST(X.[Code] AS VARCHAR) AS VALUE "+
															"FROM	( "+
															"			SELECT [Code], [Name] "+
															"			FROM [dbo].[BC_AUTOJAYA$Dimension Value]  "+
															"			WHERE [Dimension Code] = 'KENDARAAN' AND [Blocked] = 0 "+
															"			UNION ALL "+
															"			SELECT [Code], [Name] "+
															"			FROM [dbo].[BC_SOLUSI$Dimension Value] DV "+
															"			WHERE [Dimension Code] = 'KENDARAAN' AND [Blocked] = 0 "+
															"		) X "+
															"GROUP BY X.[Code] "+
															"ORDER BY X.[Code]");
								
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryBankAccount() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {


				SQLQuery query = session.createSQLQuery(	
						
															"SELECT	CAST((X.BANK_CODE+ ' - '+X.BANK_NAME) AS VARCHAR(100)) AS LABEL "+
															"		,CAST(X.BANK_CODE AS VARCHAR(20)) AS VALUE "+
															"FROM	( "+
															"			SELECT 'ALL' AS BANK_CODE, 'ALL' AS BANK_NAME "+
															"			UNION ALL "+
															"			SELECT [No_] AS BANK_CODE, [Name] AS BANK_NAME "+
															"			FROM [dbo].[BC_AUTOJAYA$Bank Account]		 "+
															"		) X "+
															"ORDER BY X.BANK_CODE"						
														);								
		
				return (List<Object[]>)query.list();
			}
		});
	}
		
		
		
	@Override
	public List<Object[]> QuerySalepersonCosting() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {	
	



			SQLQuery query = session.createSQLQuery(	
														"SELECT CAST(([SALES] + ' - '+[SALES_NAME]) AS VARCHAR(100)) AS LABEL "+
														" ,CAST([SALES] AS VARCHAR) AS VALUE "+
														" FROM [dbo].[M02_SALESPERSON] WHERE [SALES] IS NOT NULL"
													);
			
			
			return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryBulanForCosting() {
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
	public List<Object[]> QueryBulanForTQS() {
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
	public List<Object[]> QueryPeriodeClosingForCosting(final String masa,
			final String tahun) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT CAST([CLOSE_KOMISI] AS VARCHAR(1)) AS CLOSE_KOMISI, CAST([CLOSE_TQS] AS VARCHAR(1)) AS CLOSE_TQS FROM [dbo].[M01_PERIODE_COSTING] WHERE MASA = '"+masa+"'  AND TAHUN = '"+tahun+"'");
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryAssignUserIdPO() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(

						"SELECT CAST(XX.LOGIN_ID +' - '+XX.NAMA AS VARCHAR) AS LABEL "+
						"		,CAST(XX.LOGIN_ID AS VARCHAR) AS VALUE "+
						"FROM	( "+
						"			SELECT	DISTINCT X.[Assigned User ID] AS LOGIN_ID,  "+
						"					ISNULL(U.[Full Name], 'UNUSED') AS NAMA  "+
						"			FROM	( "+
						"						SELECT DISTINCT PH.[Assigned User ID] "+
						"						FROM [dbo].[BC_AUTOJAYA$Purchase Header] PH "+
						"						WHERE  PH.[Assigned User ID] <> '' "+
						"						UNION ALL "+
						"						SELECT DISTINCT PH.[Assigned User ID] "+
						"						FROM [dbo].[BC_SOLUSI$Purchase Header] PH "+
						"						WHERE  PH.[Assigned User ID] <> '' "+
						"					)	X "+
						"			LEFT JOIN  [dbo].[User] U ON U.[User Name] = X.[Assigned User ID] "+
						"		) XX "
						);
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryNamaReportGeneratorPNL() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(
							"SELECT CAST('('+[Name]+') - '+ [Description] AS VARCHAR(200)) AS LABEL "+
							"		,CAST([Name] AS VARCHAR) AS VALUE "+
							"FROM [dbo].[BC_AUTOJAYA$Acc_ Schedule Name] "+
							"WHERE [Name] LIKE 'PNR%' "	
						);
		
				return (List<Object[]>)query.list();
			}
		});
		
		
		
	
	}

	@Override
	public List<Object[]> QueryNamaReportGeneratorNERACA() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(
							"SELECT CAST('('+[Name]+') - '+ [Description] AS VARCHAR(200)) AS LABEL "+
							"		,CAST([Name] AS VARCHAR) AS VALUE "+
							"FROM [dbo].[BC_AUTOJAYA$Acc_ Schedule Name] "+
							"WHERE [Name] LIKE 'NNR%' "	
						);
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryNamaKaryawan() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
															" SELECT CAST('('+X.[Code] +') '+ MAX(X.[Name]) AS VARCHAR(100)) AS LABEL, CAST(X.[Code] AS VARCHAR) AS VALUE "+
															" FROM	( "+
															" SELECT [Code] "+
															" 		,[Name] "+
															" FROM [dbo].[BC_AUTOJAYA$Dimension Value] "+
															" WHERE	[Dimension Code] = 'KARYAWAN' AND "+
															" 		([Name] NOT LIKE '%UNUSED%' OR [Blocked] = 0) "+
															" UNION ALL "+
															" SELECT [Code] "+
															" 		,[Name] "+
															" FROM [dbo].[BC_SOLUSI$Dimension Value] "+
															" WHERE	[Dimension Code] = 'KARYAWAN' AND "+
															" 		([Name] NOT LIKE '%UNUSED%' OR [Blocked] = 0) "+
															" 		) X "+
															" GROUP BY X.[Code] "															
														);
		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QuerySektorIndustri() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	"SELECT CAST(X.[Code] +' - '+ MAX(X.[Name]) AS VARCHAR(100)) AS LABEL, CAST(X.[Code] AS VARCHAR) AS VALUE "+
															"FROM	("+
															"			SELECT [Code]"+
															"				  ,[Name]"+
															"			FROM [dbo].[BC_AUTOJAYA$Dimension Value]"+
															"			WHERE [Dimension Code] = 'SECTOR' AND"+
															"				  ([Blocked] = 0 OR [Name] NOT LIKE '%UNUSED%')"+
															"		)X "+
															" GROUP BY X.[Code]");	
				return (List<Object[]>)query.list();
			}
		});

	}

	@Override
	public List<Object[]> QueryApplication() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	"SELECT CAST(X.[Code] +' - '+ MAX(X.[Name]) AS VARCHAR(100)) AS LABEL, CAST(X.[Code] AS VARCHAR) AS VALUE "+
															"FROM	("+
															"			SELECT [Code]"+
															"				  ,[Name]"+
															"			FROM [dbo].[BC_AUTOJAYA$Dimension Value]"+
															"			WHERE [Dimension Code] = 'APPLICATION' AND"+
															"				  ([Blocked] = 0 OR [Name] NOT LIKE '%UNUSED%')"+
															"		)X "+
															" GROUP BY X.[Code] ORDER BY [Code]");	
				return (List<Object[]>)query.list();
			}
		});

	}

	@Override
	public List<Object[]> QueryStaffFinanceUntukCosting() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(
						"SELECT CAST(FINANCE AS VARCHAR) AS LABEL ,CAST(FINANCE AS VARCHAR) AS NAME FROM [dbo].[P04_PARAM_KOMISI] WHERE FINANCE <> '' "+
						"UNION ALL "+
						"SELECT CAST(FINANCE2 AS VARCHAR) AS LABEL ,CAST(FINANCE2 AS VARCHAR) AS NAME FROM [dbo].[P04_PARAM_KOMISI] WHERE FINANCE2 <> '' "+
						"UNION ALL "+
						"SELECT CAST(FINANCE3 AS VARCHAR) AS LABEL ,CAST(FINANCE3 AS VARCHAR) AS NAME FROM [dbo].[P04_PARAM_KOMISI] WHERE FINANCE3 <> '' "+
						"UNION ALL "+
						"SELECT CAST(FINANCE4 AS VARCHAR) AS LABEL ,CAST(FINANCE4 AS VARCHAR) AS NAME FROM [dbo].[P04_PARAM_KOMISI] WHERE FINANCE4 <> '' "+
						"UNION ALL "+
						"SELECT CAST(FINANCE5 AS VARCHAR) AS LABEL ,CAST(FINANCE5 AS VARCHAR) AS NAME FROM [dbo].[P04_PARAM_KOMISI] WHERE FINANCE5 <> ''"
						 );		
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryDCName() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
						"SELECT  CAST([No_] AS VARCHAR) AS LABEL, CAST([No_] AS VARCHAR) AS VALUE "+
						"FROM  [dbo].[BC_AUTOJAYA$Customer] "+
						"WHERE [No_] LIKE 'DC %' "
														);	
				return (List<Object[]>)query.list();
			}
		});
	}

	@Override
	public List<Object[]> QueryCustGroup() {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery(	
						"SELECT  CAST([Description] AS VARCHAR) AS LABEL, CAST([Code] AS VARCHAR) AS VALUE "+
						"FROM [dbo].[BC_AUTOJAYA$Customer Posting Group] "+
						"WHERE [Description] <> '' "
														);	
				return (List<Object[]>)query.list();
			}
		});
	}



}
