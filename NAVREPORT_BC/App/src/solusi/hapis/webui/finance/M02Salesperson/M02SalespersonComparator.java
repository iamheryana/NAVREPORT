package solusi.hapis.webui.finance.M02Salesperson;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.common.CommonUtils;

public class M02SalespersonComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_SALES = 1;
	public static int COMPARE_BY_SALESNAME = 2;
	public static int COMPARE_BY_NIK = 3;
	public static int COMPARE_BY_SPV = 4;
	public static int COMPARE_BY_TARGET = 5;
	public static int COMPARE_BY_PERIODERESIGN = 6;
	
	private boolean asc = true;
	private int type = 0;

	public M02SalespersonComparator(boolean asc, int type) {
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
		M02Salesperson obj1 = (M02Salesperson) o1;
		M02Salesperson obj2 = (M02Salesperson) o2;
		switch (type) {
		case 1: 
			return obj1.getSales().compareTo(obj2.getSales()) 
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getSalesName().compareTo(obj2.getSalesName())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getNik().compareTo(obj2.getNik())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getSpv().compareTo(obj2.getSpv())
					* (asc ? 1 : -1);
		case 5: 
			BigDecimal amtObj1 = obj1.getTarget()!= null?obj1.getTarget():new BigDecimal(0);
			BigDecimal amtObj2 = obj2.getTarget()!= null?obj2.getTarget():new BigDecimal(0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);			
		
		case 6: 
			String dRFrom = "1/1/1900";
			SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
			Date vTglFrom = new Date();
			Date vTglUpto  = new Date();
	
			try {
				vTglUpto  = dfRFrom.parse(dRFrom);
				vTglFrom  = dfRFrom.parse(dRFrom);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

			if(CommonUtils.isNotEmpty(obj1.getPeriodeResign())){
				vTglFrom = obj1.getPeriodeResign();
			}
				
			if(CommonUtils.isNotEmpty(obj2.getPeriodeResign())){
				vTglUpto = obj2.getPeriodeResign();
			}
			
			return vTglFrom.compareTo(vTglUpto) 
					* (asc ? 1 : -1);	
		default: 
			return obj1.getSales().compareTo(obj2.getSales()) * (asc ? 1 : -1);
		}

	}

}
