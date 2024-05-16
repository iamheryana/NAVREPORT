package solusi.hapis.webui.finance.T03CetakSlip;


import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T03CetakSlip;


public class T03CetakSlipComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

				
	public static int COMPARE_BY_JENISTRANS = 1;
	public static int COMPARE_BY_COMPANY = 2;
	public static int COMPARE_BY_NOVOUCHER = 3;
	public static int COMPARE_BY_NOCHEQUE = 4;
	public static int COMPARE_BY_PRINTCOUNT = 5;
	
	
	private boolean asc = true;
	private int type = 0;

	public T03CetakSlipComparator(boolean asc, int type) {
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
		T03CetakSlip obj1 = (T03CetakSlip) o1;
		T03CetakSlip obj2 = (T03CetakSlip) o2;
		switch (type) {
		case 1: 
			return obj1.getJenisTrans().compareTo(obj2.getJenisTrans())
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getCompany().compareTo(obj2.getCompany())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getNoVoucher().compareTo(obj2.getNoVoucher())
					* (asc ? 1 : -1);		
		case 4: 
			return obj1.getNoVoucher().compareTo(obj2.getNoVoucher())
					* (asc ? 1 : -1);
		case 5: 
			return obj1.getPrintCount().compareTo(obj2.getPrintCount())
					* (asc ? 1 : -1);
		default: 
			return obj1.getJenisTrans().compareTo(obj2.getJenisTrans()) * (asc ? 1 : -1);
		}

	}

}
