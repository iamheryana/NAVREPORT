package solusi.hapis.webui.general.pembelian.T20PiVendor;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T20PiVendor;


public class T20PiVendorComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;
	

	public static int COMPARE_BY_TGL_MULAI = 1;
	public static int COMPARE_BY_PRINCIPAL_CODE = 2;
	public static int COMPARE_BY_VENDOR_CODE = 3;
	public static int COMPARE_BY_BERLAKU = 4;
	
	
	
	
	private boolean asc = true;
	private int type = 0;

	public T20PiVendorComparator(boolean asc, int type) {
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
		T20PiVendor obj1 = (T20PiVendor) o1;
		T20PiVendor obj2 = (T20PiVendor) o2;
		switch (type) {
		case 1: 
			return obj1.getTglMulai().compareTo(obj2.getTglMulai())
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getPrincipalCode().compareTo(obj2.getPrincipalCode())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getVendorCode().compareTo(obj2.getVendorCode())
					* (asc ? 1 : -1);
			
		case 4: 
			return obj1.getBerlaku().compareTo(obj2.getBerlaku())
					* (asc ? 1 : -1);
			
		default: 
			return obj1.getTglMulai().compareTo(obj2.getTglMulai()) * (asc ? 1 : -1);
		}

	}

}
