package solusi.hapis.webui.general.crossModule.M07UserroleCosting;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.M07UserroleCostingH;

public class M07UserroleCostingHComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_USERNAME = 1;
	public static int COMPARE_BY_ROLENAME = 2;
	public static int COMPARE_BY_FILTERUSER = 3;
	
	private boolean asc = true;
	private int type = 0;

	public M07UserroleCostingHComparator(boolean asc, int type) {
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
		M07UserroleCostingH obj1 = (M07UserroleCostingH) o1;
		M07UserroleCostingH obj2 = (M07UserroleCostingH) o2;
		switch (type) {
		case 1: 
			return obj1.getUsername().compareTo(obj2.getUsername()) 
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getRolename().compareTo(obj2.getRolename())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getFilteruser().compareTo(obj2.getFilteruser())
					* (asc ? 1 : -1);
		default: 
			return obj1.getUsername().compareTo(obj2.getUsername()) * (asc ? 1 : -1);
		}

	}

}
