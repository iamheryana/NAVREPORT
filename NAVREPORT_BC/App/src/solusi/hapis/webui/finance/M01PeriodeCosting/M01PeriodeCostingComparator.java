package solusi.hapis.webui.finance.M01PeriodeCosting;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.M01PeriodeCosting;


public class M01PeriodeCostingComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_MASA = 1;
	public static int COMPARE_BY_TAHUN = 2;
	public static int COMPARE_BY_CLOSEKOMISI = 3;
	public static int COMPARE_BY_CLOSETQS = 4;
	
	private boolean asc = true;
	private int type = 0;

	public M01PeriodeCostingComparator(boolean asc, int type) {
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
		M01PeriodeCosting obj1 = (M01PeriodeCosting) o1;
		M01PeriodeCosting obj2 = (M01PeriodeCosting) o2;
		switch (type) {
		case 1: 
			return obj1.getMasa().compareTo(obj2.getMasa()) 
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getTahun().compareTo(obj2.getTahun())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getCloseKomisi().compareTo(obj2.getCloseKomisi())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getCloseTqs().compareTo(obj2.getCloseTqs())
					* (asc ? 1 : -1);							
		default: 
			return obj1.getMasa().compareTo(obj2.getMasa()) * (asc ? 1 : -1);
		}

	}

}