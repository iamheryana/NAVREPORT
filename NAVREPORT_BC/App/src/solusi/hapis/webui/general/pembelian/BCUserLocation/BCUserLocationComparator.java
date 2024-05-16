package solusi.hapis.webui.general.pembelian.BCUserLocation;


import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.BCUserLocation;


public class BCUserLocationComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;
	

	public static int COMPARE_BY_USER_ID = 1;
	public static int COMPARE_BY_BRANCHES = 2;
	public static int COMPARE_BY_GROUP_NAME = 3;
	
	
	
	
	private boolean asc = true;
	private int type = 0;

	public BCUserLocationComparator(boolean asc, int type) {
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
		BCUserLocation obj1 = (BCUserLocation) o1;
		BCUserLocation obj2 = (BCUserLocation) o2;
		switch (type) {
		case 1: 
			return obj1.getUserId().compareTo(obj2.getUserId())
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getBranches().compareTo(obj2.getBranches())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getGroupName().compareTo(obj2.getGroupName())
					* (asc ? 1 : -1);
					
		default: 
			return obj1.getUserId().compareTo(obj2.getUserId()) * (asc ? 1 : -1);
		}

	}

}
