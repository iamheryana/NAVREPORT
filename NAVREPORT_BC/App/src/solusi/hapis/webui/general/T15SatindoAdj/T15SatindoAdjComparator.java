package solusi.hapis.webui.general.T15SatindoAdj;


import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T15SatindoAdj;


public class T15SatindoAdjComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;
	

	public static int COMPARE_BY_NOINVOICE = 1;
	public static int COMPARE_BY_ORDERDATE = 2;
	
	
	private boolean asc = true;
	private int type = 0;

	public T15SatindoAdjComparator(boolean asc, int type) {
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
		T15SatindoAdj obj1 = (T15SatindoAdj) o1;
		T15SatindoAdj obj2 = (T15SatindoAdj) o2;
		switch (type) {
		case 1: 
			return obj1.getNoInvoice().compareTo(obj2.getNoInvoice())
					* (asc ? 1 : -1);
			
		case 2: 
			return obj1.getOrderDate().compareTo(obj2.getOrderDate())
					* (asc ? 1 : -1);
			
		default: 
			return obj1.getNoInvoice().compareTo(obj2.getNoInvoice()) * (asc ? 1 : -1);
		}

	}

}
