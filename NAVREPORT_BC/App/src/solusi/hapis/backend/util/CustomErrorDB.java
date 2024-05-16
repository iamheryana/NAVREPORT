package solusi.hapis.backend.util;

public class CustomErrorDB {
	private final static String uniqueConstraint = "Data tidak boleh duplikat !";
	private final static String fkConstraint = "Data tidak bisa diubah / dihapus karena sudah dipakai tabel/transaksi/summary data lain !";
	private final static String updateByOtherConstraint = "Data sedang di gunakan / di ubah user lain !";
	
	
	public static String getErrorMsg(String errorMsg){		
		if(errorMsg != null){
			if(errorMsg.contains("org.postgresql.util.PSQLException")){
				if(errorMsg.contains("violates foreign key constraint")){
					return fkConstraint;
				} else {
					return errorMsg;
				}
			} else {
				if(errorMsg.contains("java.sql.BatchUpdateException: Batch entry 0")){
					return uniqueConstraint;
				} else {
					if(errorMsg.contains("org.hibernate.StaleObjectStateException: Row was updated or deleted by another")){
						return updateByOtherConstraint;
					} else {
						return errorMsg;
					}
				}
			}
		} else {
			return errorMsg;
		}
		
	}
	
//	public static void main(String[] args) {
//	}

}
