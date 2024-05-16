package solusi.hapis.webui.general.pembelian.T19PiItem;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T19PiItem;


public class T19PiItemComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;
	

	public static int COMPARE_BY_TGL_MULAI = 1;
	public static int COMPARE_BY_PRINCIPAL_CODE = 2;
	public static int COMPARE_BY_ITEM_CAT_CODE = 3;
	public static int COMPARE_BY_PRODUCT_CODE = 4;
	public static int COMPARE_BY_BERLAKU = 5;
	
	
	
	
	private boolean asc = true;
	private int type = 0;

	public T19PiItemComparator(boolean asc, int type) {
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
		T19PiItem obj1 = (T19PiItem) o1;
		T19PiItem obj2 = (T19PiItem) o2;
		switch (type) {
		case 1: 
			return obj1.getTglMulai().compareTo(obj2.getTglMulai())
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getPrincipalCode().compareTo(obj2.getPrincipalCode())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getItemCatCode().compareTo(obj2.getItemCatCode())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getProductCode().compareTo(obj2.getProductCode())
					* (asc ? 1 : -1);			
		case 5: 
			return obj1.getBerlaku().compareTo(obj2.getBerlaku())
					* (asc ? 1 : -1);
			
		default: 
			return obj1.getTglMulai().compareTo(obj2.getTglMulai()) * (asc ? 1 : -1);
		}

	}

}
