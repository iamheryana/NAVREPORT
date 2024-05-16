
CREATE OR REPLACE FUNCTION public.getsummaryorder (in barang bpchar, in tgl date) RETURNS SETOF TES_SUMMARYORDER AS
$BODY$
select * from "TES_SUMMARYORDER" where barang=$1 and tgl <= $2;
$BODY$
LANGUAGE 'sql'
GO
