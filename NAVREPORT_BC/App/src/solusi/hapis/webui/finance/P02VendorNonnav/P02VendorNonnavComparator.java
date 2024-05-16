package solusi.hapis.webui.finance.P02VendorNonnav;


import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.P02VendorNonnav;


public class P02VendorNonnavComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

				
	public static int COMPARE_BY_KODE = 1;
	public static int COMPARE_BY_VALUTATRANS = 2;
	public static int COMPARE_BY_NAMAPENERIMA = 3;
	public static int COMPARE_BY_NAMABANK = 4;
	public static int COMPARE_BY_NOREKPENERIMA = 5;
	
	private boolean asc = true;
	private int type = 0;

	public P02VendorNonnavComparator(boolean asc, int type) {
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
		P02VendorNonnav obj1 = (P02VendorNonnav) o1;
		P02VendorNonnav obj2 = (P02VendorNonnav) o2;
		switch (type) {
		case 1: 
			return obj1.getKode().compareTo(obj2.getKode())
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getValutaTrans().compareTo(obj2.getValutaTrans())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getNamaPenerima().compareTo(obj2.getNamaPenerima())
					* (asc ? 1 : -1);		
		case 4: 
			return obj1.getNamaBank().compareTo(obj2.getNamaBank())
					* (asc ? 1 : -1);
		case 5: 
			return obj1.getNoRekPenerima().compareTo(obj2.getNoRekPenerima())
					* (asc ? 1 : -1);
		default: 
			return obj1.getKode().compareTo(obj2.getKode()) * (asc ? 1 : -1);
		}

	}

}
