package solusi.hapis.webui.general.crossModule.M07UserroleCosting;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.M08UserroleCostingD;

public class M08UserroleCostingDComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_FILTERUSER = 1;
	
	private boolean asc = true;
	private int type = 0;

	public M08UserroleCostingDComparator(boolean asc, int type) {
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
		M08UserroleCostingD obj1 = (M08UserroleCostingD) o1;
		M08UserroleCostingD obj2 = (M08UserroleCostingD) o2;
		switch (type) {
		case 1: 
			return obj1.getFilteruser().compareTo(obj2.getFilteruser()) 
					* (asc ? 1 : -1);

		default: 
			return obj1.getFilteruser().compareTo(obj2.getFilteruser()) * (asc ? 1 : -1);
		}

	}

}
