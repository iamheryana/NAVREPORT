package solusi.hapis.webui.security.roleright;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.model.SecRole;



public class RoleComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;


	public static int COMPARE_BY_KETERANGAN = 1;

	private boolean asc = true;
	private int type = 0;

	public RoleComparator(boolean asc, int type) {
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
		case 1: // Short Desc
			return obj1.getRolShortdescription().compareTo(obj2.getRolShortdescription()) * (asc ? 1 : -1);
		default: // Short Desc
			return obj1.getRolShortdescription().compareTo(obj2.getRolShortdescription()) * (asc ? 1 : -1);
		}

	}

}
