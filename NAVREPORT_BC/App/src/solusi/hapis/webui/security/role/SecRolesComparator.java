package solusi.hapis.webui.security.role;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.model.SecRole;

public class SecRolesComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_SHORT = 1;
	public static int COMPARE_BY_LONG = 2;

	private boolean asc = true;
	private int type = 0;

	public SecRolesComparator(boolean asc, int type) {
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
		SecRole obj1 = (SecRole) o1;
		SecRole obj2 = (SecRole) o2;
		
        
		switch (type) {
		case 1: // Compare Short Desc
			return obj1.getRolShortdescription().compareTo(obj2.getRolShortdescription()) * (asc ? 1 : -1);
		case 2: // Compare Long Desc
			return obj1.getRolLongdescription().compareTo(obj2.getRolLongdescription()) * (asc ? 1 : -1);
		default: // Compare Short Desc
			return obj1.getRolShortdescription().compareTo(obj2.getRolShortdescription()) * (asc ? 1 : -1);
		}

	}

}
