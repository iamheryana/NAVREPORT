package solusi.hapis.webui.tabel.T07itemsatindo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.tabel.model.T07itemsatindo;

public class T07itemsatindoComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_TGLBERLAKU = 1;
	public static int COMPARE_BY_NOITEM = 2;
	public static int COMPARE_BY_SATAMOUNTKOMISI = 3;
	public static int COMPARE_BY_IDMRAMOUNTKOMISI = 4;
	public static int COMPARE_BY_SATAMOUNTBNS = 5;
	public static int COMPARE_BY_IDMRAMOUNTBNS = 6;
	
	private boolean asc = true;
	private int type = 0;

	public T07itemsatindoComparator(boolean asc, int type) {
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
		T07itemsatindo obj1 = (T07itemsatindo) o1;
		T07itemsatindo obj2 = (T07itemsatindo) o2;
		switch (type) {
		case 1: 
			return obj1.getTglBerlaku().compareTo(obj2.getTglBerlaku()) 
					* (asc ? 1 : -1);	
		case 2: 
			return obj1.getNoItem().compareTo(obj2.getNoItem()) 
					* (asc ? 1 : -1);
	
		case 3: 
			BigDecimal amtSatKomisiObj1 = obj1.getSatAmountKomisi()!= null?obj1.getSatAmountKomisi():new BigDecimal(0);
			BigDecimal amtSatKomisiObj2 = obj2.getSatAmountKomisi()!= null?obj2.getSatAmountKomisi():new BigDecimal(0);
			return amtSatKomisiObj1.compareTo(amtSatKomisiObj2)
					* (asc ? 1 : -1);
		case 4: 
			BigDecimal amtIdmrKomisiObj1 = obj1.getIdmrAmountKomisi()!= null?obj1.getIdmrAmountKomisi():new BigDecimal(0);
			BigDecimal amtIdmrKomisiObj2 = obj2.getIdmrAmountKomisi()!= null?obj2.getIdmrAmountKomisi():new BigDecimal(0);
			return amtIdmrKomisiObj1.compareTo(amtIdmrKomisiObj2)
					* (asc ? 1 : -1);
		case 5: 
			BigDecimal amtSatBnsObj1 = obj1.getSatAmountBns()!= null?obj1.getSatAmountBns():new BigDecimal(0);
			BigDecimal amtSatBnsObj2 = obj2.getSatAmountBns()!= null?obj2.getSatAmountBns():new BigDecimal(0);
			return amtSatBnsObj1.compareTo(amtSatBnsObj2)
					* (asc ? 1 : -1);
		case 6: 
			BigDecimal amtIdmrBnsObj1 = obj1.getIdmrAmountBns()!= null?obj1.getIdmrAmountBns():new BigDecimal(0);
			BigDecimal amtIdmrBnsObj2 = obj2.getIdmrAmountBns()!= null?obj2.getIdmrAmountBns():new BigDecimal(0);
			return amtIdmrBnsObj1.compareTo(amtIdmrBnsObj2)
					* (asc ? 1 : -1);
		default: 
			return obj1.getNoItem().compareTo(obj2.getNoItem()) * (asc ? 1 : -1);
		}

	}

}
