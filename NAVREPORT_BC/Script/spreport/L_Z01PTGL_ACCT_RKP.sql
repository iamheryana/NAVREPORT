DROP FUNCTION l_z01ptgl_acct_rkp(IN l_cbngFr character varying, 
                                                    IN l_cbngTo character varying,
                                                    IN l_applydateFr date, 
                                                    IN l_applydateTo date,
                                                    IN l_accountcodeFr character varying, 
                                                    IN l_accountcodeTo character varying);

CREATE  FUNCTION l_z01ptgl_acct_rkp(IN l_cbngFr character varying, 
                                                    IN l_cbngTo character varying,
                                                    IN l_applydateFr date, 
                                                    IN l_applydateTo date,
                                                    IN l_accountcodeFr character varying, 
                                                    IN l_accountcodeTo character varying)
  RETURNS TABLE(OutCabang character varying,
		        OutDokumen character varying, 
		        OutAccCode character varying,
                OutApplyDate date,
                OutCurrency character varying,
                OutDebit numeric(15,2),
                OutCredit numeric(15,2),
                OutCaptureFlg text,
		        OutVersion integer,
		        OutCreated_by character varying,
		        OutCreated_on timestamp without time zone,
		        OutUpdated_by character varying,
		        OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
 RETURN QUERY
	SELECT	z01.kdcabang as OutCabang,
		    z01.document as OutDokumen, 
		    z01.accountcode as OutAccCode,
            z01.applydate as OutApplyDate,
            z01.currency as OutCurrency,
            sum(z01.debit) as OutDebit,
            sum(z01.credit) as OutCredit,
            (case when z01.captureflg=1 then 'Ya' else 'Tidak' end) as OutCaptureFlg,
		    z01.version as OutVersion,
		    z01.created_by as OutCreated_by,
		    z01.created_on as OutCreated_on,
		    z01.updated_by as OutUpdated_by,
		    z01.updated_on as OutUpdated_on
	FROM z01ptgl z01
	WHERE (z01.kdcabang BETWEEN l_cbngFr AND l_cbngTo) AND
           (z01.applydate BETWEEN l_applydateFr AND l_applydateTo) AND
           (z01.accountcode BETWEEN l_accountcodeFr AND l_accountcodeTo)
    GROUP BY z01.kdcabang, z01.document, z01.accountcode, z01.applydate, z01.currency, z01.captureflg,
              z01.version, z01.created_by, z01.created_on, z01.updated_by, z01.updated_on; 
END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
