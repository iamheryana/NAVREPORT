package solusi.hapis.webui.finance.Cashflow.T36OtherCf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T36OtherCf;



public class T36OtherCfComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;
	


	public static int COMPARE_BY_COMPANY = 1;
	public static int COMPARE_BY_REG = 2;
	public static int COMPARE_BY_KETERANGAN = 3;
	public static int COMPARE_BY_AMOUNT = 4;
	public static int COMPARE_BY_TIPE = 5;
	public static int COMPARE_BY_DUEDATE = 6;
	public static int COMPARE_BY_BASIS = 7;
	public static int COMPARE_BY_EVERY = 8;
	public static int COMPARE_BY_FROMDATE = 9;
	public static int COMPARE_BY_UPTODATE = 10;
	
	private boolean asc = true;
	private int type = 0;

	public T36OtherCfComparator(boolean asc, int type) {
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
		T36OtherCf obj1 = (T36OtherCf) o1;
		T36OtherCf obj2 = (T36OtherCf) o2;
		switch (type) {
		case 1: 
			return obj1.getCompany().compareTo(obj2.getCompany())
					* (asc ? 1 : -1);			
		case 2: 
			return obj1.getReg().compareTo(obj2.getReg())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getKeterangan().compareTo(obj2.getKeterangan())
					* (asc ? 1 : -1);
		case 4: 
			BigDecimal amtObj1 = obj1.getAmount()!= null?obj1.getAmount():new BigDecimal(0);
			BigDecimal amtObj2 = obj2.getAmount()!= null?obj2.getAmount():new BigDecimal(0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);		
		case 5: 
			return obj1.getTipe().compareTo(obj2.getTipe())
					* (asc ? 1 : -1);
		case 6: 
			return obj1.getDueDate().compareTo(obj2.getDueDate())
					* (asc ? 1 : -1);
		case 7: 
			return obj1.getBasis().compareTo(obj2.getBasis())
					* (asc ? 1 : -1);		
		case 8: 
			return obj1.getEvery().compareTo(obj2.getEvery())
					* (asc ? 1 : -1);			
		case 9: 
			return obj1.getFromDate().compareTo(obj2.getFromDate())
					* (asc ? 1 : -1);
		case 10: 
			return obj1.getUptoDate().compareTo(obj2.getUptoDate())
					* (asc ? 1 : -1);
			
		default: 
			return obj1.getCompany().compareTo(obj2.getCompany()) 
					* (asc ? 1 : -1);
		}

	}

}
