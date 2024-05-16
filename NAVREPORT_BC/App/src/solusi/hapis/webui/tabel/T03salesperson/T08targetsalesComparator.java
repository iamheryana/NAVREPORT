package solusi.hapis.webui.tabel.T03salesperson;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.tabel.model.T08targetsales;

public class T08targetsalesComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_TAHUN = 1;
	public static int COMPARE_BY_TARGET = 2;
	
	private boolean asc = true;
	private int type = 0;

	public T08targetsalesComparator(boolean asc, int type) {
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
		T08targetsales obj1 = (T08targetsales) o1;
		T08targetsales obj2 = (T08targetsales) o2;
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
