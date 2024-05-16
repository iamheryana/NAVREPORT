package solusi.hapis.webui.tabel.T02rekapcosting;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.tabel.model.T02rekapcosting;

public class T02rekapcostingComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

				
	public static int COMPARE_BY_SALES = 1;
	public static int COMPARE_BY_NOSO = 2;
	public static int COMPARE_BY_CUSTOMER = 3;
	public static int COMPARE_BY_NOPOCUSTOMER = 4;
	public static int COMPARE_BY_NOINVOICE = 5;
	public static int COMPARE_BY_TGLINVOICE = 6;
	public static int COMPARE_BY_TGLLUNAS = 7;
	public static int COMPARE_BY_AMOUNT = 8;
	public static int COMPARE_BY_FLAGKOMISI = 9;
	public static int COMPARE_BY_FLAGTQS = 10;
	
	
	private boolean asc = true;
	private int type = 0;

	public T02rekapcostingComparator(boolean asc, int type) {
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
		T02rekapcosting obj1 = (T02rekapcosting) o1;
		T02rekapcosting obj2 = (T02rekapcosting) o2;
		switch (type) {
		case 1: 
			return obj1.getSales().compareTo(obj2.getSales())
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getNoSo().compareTo(obj2.getNoSo())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getCustomer().compareTo(obj2.getCustomer())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getNoPoCust().compareTo(obj2.getNoPoCust())
					* (asc ? 1 : -1);
		case 5: 
			return obj1.getNoInvoice().compareTo(obj2.getNoInvoice())
					* (asc ? 1 : -1);
		case 6: 
			return obj1.getTglInvoice().compareTo(obj2.getTglInvoice()) 
					* (asc ? 1 : -1);
		case 7: 
			return obj1.getTglLunas().compareTo(obj2.getTglLunas()) 
					* (asc ? 1 : -1);	
		case 8: 
			BigDecimal amtObj1 = obj1.getAmount()!= null?obj1.getAmount():new BigDecimal(0);
			BigDecimal amtObj2 = obj2.getAmount()!= null?obj2.getAmount():new BigDecimal(0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);			
		case 9: 
			return obj1.getFlagKomisi().compareTo(obj2.getFlagKomisi())
					* (asc ? 1 : -1);
		case 10: 
			return obj1.getFlagTqs().compareTo(obj2.getFlagTqs())
					* (asc ? 1 : -1);
				
		default: 
			return obj1.getSales().compareTo(obj2.getSales()) * (asc ? 1 : -1);
		}

	}

}
