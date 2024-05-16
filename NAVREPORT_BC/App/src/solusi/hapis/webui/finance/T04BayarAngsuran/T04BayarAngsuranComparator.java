package solusi.hapis.webui.finance.T04BayarAngsuran;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T04BayarAngsuran;


public class T04BayarAngsuranComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;


    
	public static int COMPARE_BY_COMPANY = 1;			
	public static int COMPARE_BY_SUPPCODE = 2;
	public static int COMPARE_BY_NOPO = 3;
	public static int COMPARE_BY_VALUTAPO = 4;
	public static int COMPARE_BY_NILAIPO = 5;
	public static int COMPARE_BY_JMLGIRO = 6;
	public static int COMPARE_BY_TMT = 7;
	public static int COMPARE_BY_PRINTCOUNT = 8;
	
	
	private boolean asc = true;
	private int type = 0;

	public T04BayarAngsuranComparator(boolean asc, int type) {
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
		T04BayarAngsuran obj1 = (T04BayarAngsuran) o1;
		T04BayarAngsuran obj2 = (T04BayarAngsuran) o2;
		switch (type) {
		case 1: 
			return obj1.getCompany().compareTo(obj2.getCompany())
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getSuppCode().compareTo(obj2.getSuppCode())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getNoPo().compareTo(obj2.getNoPo())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getValutaPo().compareTo(obj2.getValutaPo())
					* (asc ? 1 : -1);		
		case 5: 
			BigDecimal amtObj1 = obj1.getNilaiPo()!= null?obj1.getNilaiPo():new BigDecimal(0);
			BigDecimal amtObj2 = obj2.getNilaiPo()!= null?obj2.getNilaiPo():new BigDecimal(0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);
		case 6: 
			Integer jmlGiroObj1 = obj1.getJmlGiro()!= 0?obj1.getJmlGiro():new Integer(0);
			Integer jmlGiroObj2 = obj2.getJmlGiro()!= 0?obj2.getJmlGiro():new Integer(0);			
			return jmlGiroObj1.compareTo(jmlGiroObj2)
					* (asc ? 1 : -1);
		case 7: 
			return obj1.getTmt().compareTo(obj2.getTmt())
					* (asc ? 1 : -1);
		case 8: 
			return obj1.getPrintCount().compareTo(obj2.getPrintCount())
					* (asc ? 1 : -1);
		default: 
			return obj1.getSuppCode().compareTo(obj2.getSuppCode()) * (asc ? 1 : -1);
		}

	}

}
