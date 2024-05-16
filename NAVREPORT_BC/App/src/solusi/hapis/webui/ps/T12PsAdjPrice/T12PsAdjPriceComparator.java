package solusi.hapis.webui.ps.T12PsAdjPrice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T12PsAdjPrice;
import solusi.hapis.common.CommonUtils;


public class T12PsAdjPriceComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;
	
	public static int COMPARE_BY_TGLBERLAKU = 1;
	public static int COMPARE_BY_CUSTNO = 2;
	public static int COMPARE_BY_ITEMNO = 3;
	public static int COMPARE_BY_CURRCODE = 4;
	public static int COMPARE_BY_ADJPRICE = 5;
	
	
	private boolean asc = true;
	private int type = 0;

	public T12PsAdjPriceComparator(boolean asc, int type) {
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
		T12PsAdjPrice obj1 = (T12PsAdjPrice) o1;
		T12PsAdjPrice obj2 = (T12PsAdjPrice) o2;
		switch (type) {
		case 1: 
			return obj1.getTglBerlaku().compareTo(obj2.getTglBerlaku())
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getCustNo().compareTo(obj2.getCustNo())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getItemNo().compareTo(obj2.getItemNo())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getCurrCode().compareTo(obj2.getCurrCode())
					* (asc ? 1 : -1);
		case 5: 
			BigDecimal amtObj1 = (BigDecimal) (CommonUtils.isNotEmpty(obj1.getAdjPrice())?obj1.getAdjPrice():0);
			BigDecimal amtObj2 = (BigDecimal) (CommonUtils.isNotEmpty(obj2.getAdjPrice())?obj2.getAdjPrice():0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);			
				
		default: 
			return obj1.getTglBerlaku().compareTo(obj2.getTglBerlaku()) * (asc ? 1 : -1);
		}

	}

}
