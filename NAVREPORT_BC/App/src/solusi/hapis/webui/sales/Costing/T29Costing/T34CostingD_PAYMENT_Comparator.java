package solusi.hapis.webui.sales.Costing.T29Costing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T34CostingDPayment;
import solusi.hapis.common.CommonUtils;

public class T34CostingD_PAYMENT_Comparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;
	

	public static int COMPARE_BY_NOINVOICE = 1;
	public static int COMPARE_BY_TGLINVOICE = 2;
	public static int COMPARE_BY_NILAIINVOICE = 3;
	public static int COMPARE_BY_NOLUNAS = 4;
	public static int COMPARE_BY_TGLLUNAS = 5;
	public static int COMPARE_BY_NILAILUNAS = 6;
	
	private boolean asc = true;
	private int type = 0;

	public T34CostingD_PAYMENT_Comparator(boolean asc, int type) {
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
		T34CostingDPayment obj1 = (T34CostingDPayment) o1;
		T34CostingDPayment obj2 = (T34CostingDPayment) o2;
		switch (type) {
		case 1: 
			return obj1.getNoInvoice().compareTo(obj2.getNoInvoice()) 
					* (asc ? 1 : -1);			
		case 2: 			
			return obj1.getTglInvoice().compareTo(obj2.getTglInvoice()) 
					* (asc ? 1 : -1);
		case 3: 
			BigDecimal amtObj1 = (BigDecimal) (CommonUtils.isNotEmpty(obj1.getNilaiInvoice())?obj1.getNilaiInvoice():0);
			BigDecimal amtObj2 = (BigDecimal) (CommonUtils.isNotEmpty(obj2.getNilaiInvoice())?obj2.getNilaiInvoice():0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);			
		case 4: 
			return obj1.getNoLunas().compareTo(obj2.getNoLunas()) 
					* (asc ? 1 : -1);			
		case 5: 			
			return obj1.getTglLunas().compareTo(obj2.getTglLunas()) 
					* (asc ? 1 : -1);
		case 6: 
			BigDecimal amtObj1_Lunas = (BigDecimal) (CommonUtils.isNotEmpty(obj1.getNilaiLunas())?obj1.getNilaiLunas():0);
			BigDecimal amtObj2_Lunas = (BigDecimal) (CommonUtils.isNotEmpty(obj2.getNilaiLunas())?obj2.getNilaiLunas():0);
			return amtObj1_Lunas.compareTo(amtObj2_Lunas)
					* (asc ? 1 : -1);
		default: 
			return obj1.getNoInvoice().compareTo(obj2.getNoInvoice()) * (asc ? 1 : -1);
		}

	}

}
