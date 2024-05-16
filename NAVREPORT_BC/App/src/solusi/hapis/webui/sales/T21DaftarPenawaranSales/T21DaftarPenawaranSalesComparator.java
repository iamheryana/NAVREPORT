package solusi.hapis.webui.sales.T21DaftarPenawaranSales;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T21DaftarPenawaranSales;


public class T21DaftarPenawaranSalesComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;
	


	public static int COMPARE_BY_COMPANY = 1;
	public static int COMPARE_BY_CABANG = 2;
	public static int COMPARE_BY_TGLPENAWARAN = 3;
	public static int COMPARE_BY_NOPENAWARAN = 4;
	public static int COMPARE_BY_CUSTOMER = 5;
	public static int COMPARE_BY_SEKTORINDUSTRI = 6;
	public static int COMPARE_BY_SALES = 7;
	public static int COMPARE_BY_NILAI = 8;
	public static int COMPARE_BY_STATUS = 9;
	
	private boolean asc = true;
	private int type = 0;

	public T21DaftarPenawaranSalesComparator(boolean asc, int type) {
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
		T21DaftarPenawaranSales obj1 = (T21DaftarPenawaranSales) o1;
		T21DaftarPenawaranSales obj2 = (T21DaftarPenawaranSales) o2;
		switch (type) {
		case 1: 
			return obj1.getCompany().compareTo(obj2.getCompany())
					* (asc ? 1 : -1);			
		case 2: 
			return obj1.getCabang().compareTo(obj2.getCabang())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getTglPenawaran().compareTo(obj2.getTglPenawaran())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getNoPenawaran().compareTo(obj2.getNoPenawaran())
					* (asc ? 1 : -1);
		case 5: 
			return obj1.getNamaCustomer().compareTo(obj2.getNamaCustomer())
					* (asc ? 1 : -1);
		case 6: 
			return obj1.getSektorIndustri().compareTo(obj2.getSektorIndustri())
					* (asc ? 1 : -1);
		case 7: 
			return obj1.getSalesCode().compareTo(obj2.getSalesCode())
					* (asc ? 1 : -1);		
		case 8: 
			BigDecimal amtObj1 = obj1.getNilai()!= null?obj1.getNilai():new BigDecimal(0);
			BigDecimal amtObj2 = obj2.getNilai()!= null?obj2.getNilai():new BigDecimal(0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);		
		case 9: 
			return obj1.getStatusPenawaran().compareTo(obj2.getStatusPenawaran())
					* (asc ? 1 : -1);
			
		default: 
			return obj1.getNoPenawaran().compareTo(obj2.getNoPenawaran()) 
					* (asc ? 1 : -1);
		}

	}

}
