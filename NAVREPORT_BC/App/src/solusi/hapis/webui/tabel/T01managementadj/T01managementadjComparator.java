package solusi.hapis.webui.tabel.T01managementadj;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.tabel.model.T01managementadj;

public class T01managementadjComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_TANGGAL = 1;
	public static int COMPARE_BY_CABANG = 2;
	public static int COMPARE_BY_SALES = 3;
	public static int COMPARE_BY_KETERANGAN = 4;
	public static int COMPARE_BY_AMOUNTHW01 = 5;
	public static int COMPARE_BY_AMOUNTPS01 = 6;
	public static int COMPARE_BY_AMOUNTPS02 = 7;
	public static int COMPARE_BY_AMOUNTPS03 = 8;
	public static int COMPARE_BY_AMOUNTPS04 = 9;
	public static int COMPARE_BY_AMOUNTPS05 = 10;
	
	private boolean asc = true;
	private int type = 0;

	public T01managementadjComparator(boolean asc, int type) {
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
		T01managementadj obj1 = (T01managementadj) o1;
		T01managementadj obj2 = (T01managementadj) o2;
		switch (type) {
		case 1: 
			return obj1.getTanggal().compareTo(obj2.getTanggal()) 
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getCabang().compareTo(obj2.getCabang())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getSales().compareTo(obj2.getSales())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getKeterangan().compareTo(obj2.getKeterangan())
					* (asc ? 1 : -1);
		case 5: 
			BigDecimal amtHw01Obj1 = obj1.getAmountHw01()!= null?obj1.getAmountHw01():new BigDecimal(0);
			BigDecimal amtHw01Obj2 = obj2.getAmountHw01()!= null?obj2.getAmountHw01():new BigDecimal(0);
			return amtHw01Obj1.compareTo(amtHw01Obj2)
					* (asc ? 1 : -1);
		case 6: 
			BigDecimal amtPsO1Obj1 = obj1.getAmountPs01()!= null?obj1.getAmountPs01():new BigDecimal(0);
			BigDecimal amtPsO1Obj2 = obj2.getAmountPs01()!= null?obj2.getAmountPs01():new BigDecimal(0);
			return amtPsO1Obj1.compareTo(amtPsO1Obj2)
					* (asc ? 1 : -1);
		case 7: 
			BigDecimal amtPsO2Obj1 = obj1.getAmountPs02()!= null?obj1.getAmountPs02():new BigDecimal(0);
			BigDecimal amtPsO2Obj2 = obj2.getAmountPs02()!= null?obj2.getAmountPs02():new BigDecimal(0);
			return amtPsO2Obj1.compareTo(amtPsO2Obj2)
					* (asc ? 1 : -1);
		case 8: 
			BigDecimal amtPsO3Obj1 = obj1.getAmountPs03()!= null?obj1.getAmountPs03():new BigDecimal(0);
			BigDecimal amtPsO3Obj2 = obj2.getAmountPs03()!= null?obj2.getAmountPs03():new BigDecimal(0);
			return amtPsO3Obj1.compareTo(amtPsO3Obj2)
					* (asc ? 1 : -1);
		case 9: 
			BigDecimal amtPsO4Obj1 = obj1.getAmountPs04()!= null?obj1.getAmountPs04():new BigDecimal(0);
			BigDecimal amtPsO4Obj2 = obj2.getAmountPs04()!= null?obj2.getAmountPs04():new BigDecimal(0);
			return amtPsO4Obj1.compareTo(amtPsO4Obj2)
					* (asc ? 1 : -1);
		case 10: 
			BigDecimal amtPsO5Obj1 = obj1.getAmountPs05()!= null?obj1.getAmountPs05():new BigDecimal(0);
			BigDecimal amtPsO5Obj2 = obj2.getAmountPs05()!= null?obj2.getAmountPs05():new BigDecimal(0);
			return amtPsO5Obj1.compareTo(amtPsO5Obj2)
					* (asc ? 1 : -1);
						
		default: 
			return obj1.getCabang().compareTo(obj2.getCabang()) * (asc ? 1 : -1);
		}

	}

}
