package solusi.hapis.webui.logistic.penjualan.T26CetakSroso;


import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T26CetakSroso;


public class T26CetakSrosoComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

				
	public static int COMPARE_BY_DICETAKPADA = 1;
	public static int COMPARE_BY_KETERANGAN = 2;

	
	
	private boolean asc = true;
	private int type = 0;

	public T26CetakSrosoComparator(boolean asc, int type) {
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
		T26CetakSroso obj1 = (T26CetakSroso) o1;
		T26CetakSroso obj2 = (T26CetakSroso) o2;
		switch (type) {
		case 1: 
			return obj1.getDicetakPada().compareTo(obj2.getDicetakPada()) 
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getKeterangan().compareTo(obj2.getKeterangan())
					* (asc ? 1 : -1);
				
		default: 
			return obj1.getDicetakPada().compareTo(obj2.getDicetakPada()) * (asc ? 1 : -1);
		}

	}

}
