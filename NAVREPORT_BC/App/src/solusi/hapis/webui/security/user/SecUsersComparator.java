package solusi.hapis.webui.security.user;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import solusi.hapis.backend.model.SecUser;

public class SecUsersComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_LOGINNAME = 1;
	public static int COMPARE_BY_FIRSTNAME = 2;
	public static int COMPARE_BY_LASTNAME = 3;
	public static int COMPARE_BY_ACC_CABANG = 4;
	public static int COMPARE_BY_ACC_GOLONGAN = 5;
	public static int COMPARE_BY_EMAIL = 6;
	public static int COMPARE_BY_EXPIREDDATE = 7;
	public static int COMPARE_BY_STATUS = 8;

	private boolean asc = true;
	private int type = 0;

	public SecUsersComparator(boolean asc, int type) {
		this.asc = asc;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int compare(Object o1, Object o2) {
		SecUser obj1 = (SecUser) o1;
		SecUser obj2 = (SecUser) o2;
		
		Date vTglExpired = null;  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
        	vTglExpired = formatter.parse("1900/01/01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        
		switch (type) {
		case 1: // Compare Login Name
			return obj1.getUsrLoginname().compareTo(obj2.getUsrLoginname())
					* (asc ? 1 : -1);
		case 2: // Compare First Name
			String firstNameObj1 = obj1.getUsrFirstname() != null ? obj1
					.getUsrFirstname() : " ";
			String firstNameObj2 = obj2.getUsrFirstname() != null ? obj2
					.getUsrFirstname() : " ";
			return firstNameObj1.compareTo(firstNameObj2) * (asc ? 1 : -1);
		case 3: // Compare Last Name
			String lastNameObj1 = obj1.getUsrLastname() != null ? obj1
					.getUsrLastname() : " ";
			String lastNamerObj2 = obj2.getUsrLastname() != null ? obj2
					.getUsrLastname() : " ";
			return lastNameObj1.compareTo(lastNamerObj2) * (asc ? 1 : -1);
		case 4: // Compare Access Cabang
			String accCablObj1 = obj1.getAccessCabang() != null ? obj1.getAccessCabang()
					: " ";
			String accCabObj2 = obj2.getAccessCabang() != null ? obj2.getAccessCabang()
					: " ";
			return accCablObj1.compareTo(accCabObj2) * (asc ? 1 : -1);
		case 5: // Compare Access Golongan
			String accGolObj1 = obj1.getAccessGolongan() != null ? obj1.getAccessGolongan()
					: " ";
			String accGolObj2 = obj2.getAccessGolongan() != null ? obj2.getAccessGolongan()
					: " ";
			return accGolObj1.compareTo(accGolObj2) * (asc ? 1 : -1);
		case 6: // Compare Email
			String emailObj1 = obj1.getUsrEmail() != null ? obj1.getUsrEmail()
					: " ";
			String emailObj2 = obj2.getUsrEmail() != null ? obj2.getUsrEmail()
					: " ";
			return emailObj1.compareTo(emailObj2) * (asc ? 1 : -1);
		case 7: // Compare Expired Date
			Date vTglExpiredObj1 = obj1.getExpiredDate()!= null?obj1.getExpiredDate():vTglExpired;
			Date vTglExpiredObj2 = obj2.getExpiredDate()!= null?obj2.getExpiredDate():vTglExpired;
			return vTglExpiredObj1.compareTo(vTglExpiredObj2)
					* (asc ? 1 : -1);
		case 8: // Compare Status
			return obj1.getFlagActiv().compareTo(obj2.getFlagActiv())
					* (asc ? 1 : -1);
		default: // Login Name
			return obj1.getUsrLoginname().compareTo(obj2.getUsrLoginname())
					* (asc ? 1 : -1);
		}

	}

}
