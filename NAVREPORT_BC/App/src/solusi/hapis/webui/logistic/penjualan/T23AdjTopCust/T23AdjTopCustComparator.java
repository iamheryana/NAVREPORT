package solusi.hapis.webui.logistic.penjualan.T23AdjTopCust;


import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T23AdjTopCust;
import solusi.hapis.common.CommonUtils;


public class T23AdjTopCustComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

				
	public static int COMPARE_BY_CUSTNO = 1;
	public static int COMPARE_BY_TOPADJ = 2;

	
	
	private boolean asc = true;
	private int type = 0;

	public T23AdjTopCustComparator(boolean asc, int type) {
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
		T23AdjTopCust obj1 = (T23AdjTopCust) o1;
		T23AdjTopCust obj2 = (T23AdjTopCust) o2;
		switch (type) {
		case 1: 
			return obj1.getCustNo().compareTo(obj2.getCustNo())
					* (asc ? 1 : -1);
		case 2: 
			Integer  amtObj1 = (Integer) (CommonUtils.isNotEmpty(obj1.getTopAdj())?obj1.getTopAdj():0);
			Integer amtObj2 = (Integer) (CommonUtils.isNotEmpty(obj2.getTopAdj())?obj2.getTopAdj():0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);			
				
		default: 
			return obj1.getCustNo().compareTo(obj2.getCustNo()) * (asc ? 1 : -1);
		}

	}

}
