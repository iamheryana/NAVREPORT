package solusi.hapis.webui.sales.Costing.T29Costing;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.common.CommonUtils;

public class T29CostingComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_TGLCOSTING = 1;
	public static int COMPARE_BY_NOCOSTING = 2;
	public static int COMPARE_BY_SALES = 3;
	public static int COMPARE_BY_NOBSO = 4;
	public static int COMPARE_BY_NOSO = 5;
	public static int COMPARE_BY_NOPOCUSTOMER = 6;
	public static int COMPARE_BY_CUSTOMER = 7;
	
	private boolean asc = true;
	private int type = 0;

	public T29CostingComparator(boolean asc, int type) {
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
		T29CostingH obj1 = (T29CostingH) o1;
		T29CostingH obj2 = (T29CostingH) o2;
		switch (type) {
		case 1: 
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
		

			if(CommonUtils.isNotEmpty(obj1.getTglCosting())){
				vTglFrom = obj1.getTglCosting();
			}
				
			if(CommonUtils.isNotEmpty(obj2.getTglCosting())){
				vTglUpto = obj2.getTglCosting();
			}
			
			return vTglFrom.compareTo(vTglUpto) 
					* (asc ? 1 : -1);	
			
		case 2: 			
			return obj1.getNoCosting().compareTo(obj2.getNoCosting()) 
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getSalesman().compareTo(obj2.getSalesman())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getNoBso().compareTo(obj2.getNoBso())
					* (asc ? 1 : -1);
		case 5: 
			return obj1.getNoSo().compareTo(obj2.getNoSo())
					* (asc ? 1 : -1);		
		case 6: 
			return obj1.getNoPoCustomer().compareTo(obj2.getNoPoCustomer())
					* (asc ? 1 : -1);	
		case 7: 
			return obj1.getCustomer().compareTo(obj2.getCustomer())
					* (asc ? 1 : -1);
		default: 
			return obj1.getNoCosting().compareTo(obj2.getNoCosting()) * (asc ? 1 : -1);
		}

	}

}
