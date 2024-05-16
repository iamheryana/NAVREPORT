package solusi.hapis.webui.security.roleright;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.model.SecRoleright;



public class RoleRightComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;


	public static int COMPARE_BY_NAMARIGHT = 1;

	private boolean asc = true;
	private int type = 0;

	public RoleRightComparator(boolean asc, int type) {
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
		SecRoleright obj1 = (SecRoleright) o1;
		SecRoleright obj2 = (SecRoleright) o2;
		String vNamaRight1 = obj1.getSecRight()!=null?(obj1.getSecRight().getRigDesc()!=null?obj1.getSecRight().getRigDesc():" "):" ";
		String vNamaRight2 = obj2.getSecRight()!=null?(obj2.getSecRight().getRigDesc()!=null?obj2.getSecRight().getRigDesc():" "):" ";
		
		switch (type) {
		case 1: // Nama Right
			return vNamaRight1.compareTo(vNamaRight2) * (asc ? 1 : -1);
		default: // Nama Right
			return vNamaRight1.compareTo(vNamaRight2) * (asc ? 1 : -1);
		}

	}

}
