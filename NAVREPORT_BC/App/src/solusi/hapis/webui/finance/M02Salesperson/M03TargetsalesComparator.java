package solusi.hapis.webui.finance.M02Salesperson;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.M03Targetsales;

public class M03TargetsalesComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_TAHUN = 1;
	public static int COMPARE_BY_TARGET = 2;
	
	private boolean asc = true;
	private int type = 0;

	public M03TargetsalesComparator(boolean asc, int type) {
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
		M03Targetsales obj1 = (M03Targetsales) o1;
		M03Targetsales obj2 = (M03Targetsales) o2;
		switch (type) {
		case 1: 
			return obj1.getTahun().compareTo(obj2.getTahun()) 
					* (asc ? 1 : -1);
		case 2: 
			BigDecimal amtObj1 = obj1.getTarget()!= null?obj1.getTarget():new BigDecimal(0);
			BigDecimal amtObj2 = obj2.getTarget()!= null?obj2.getTarget():new BigDecimal(0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);			
		
		default: 
			return obj1.getTahun().compareTo(obj2.getTahun()) * (asc ? 1 : -1);
		}

	}

}
