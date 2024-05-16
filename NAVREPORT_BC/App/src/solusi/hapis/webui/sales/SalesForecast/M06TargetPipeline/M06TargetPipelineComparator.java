package solusi.hapis.webui.sales.SalesForecast.M06TargetPipeline;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.M06TargetPipeline;

public class M06TargetPipelineComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_TAHUN = 1;
	public static int COMPARE_BY_JENIS = 2;
	public static int COMPARE_BY_SLS_OR_CAB = 3;
	public static int COMPARE_BY_TARGET = 4;
	public static int COMPARE_BY_TARGET_PS = 5;
	public static int COMPARE_BY_STATUS = 6;
	
	private boolean asc = true;
	private int type = 0;

	public M06TargetPipelineComparator(boolean asc, int type) {
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
		M06TargetPipeline obj1 = (M06TargetPipeline) o1;
		M06TargetPipeline obj2 = (M06TargetPipeline) o2;
		switch (type) {
		case 1: 
			return obj1.getTahun().compareTo(obj2.getTahun()) 
					* (asc ? 1 : -1);	
		case 2: 
			return obj1.getJenis().compareTo(obj2.getJenis()) 
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getSlsOrCab().compareTo(obj2.getSlsOrCab()) 
					* (asc ? 1 : -1);
		case 4: 
			BigDecimal targetObj1 = obj1.getTarget()!= null?obj1.getTarget():new BigDecimal(0);
			BigDecimal targetObj2 = obj2.getTarget()!= null?obj2.getTarget():new BigDecimal(0);
			return targetObj1.compareTo(targetObj2)
					* (asc ? 1 : -1);
		case 5: 
			BigDecimal targetPsObj1 = obj1.getTargetPs()!= null?obj1.getTargetPs():new BigDecimal(0);
			BigDecimal targetPsObj2 = obj2.getTargetPs()!= null?obj2.getTargetPs():new BigDecimal(0);
			return targetPsObj1.compareTo(targetPsObj2)
					* (asc ? 1 : -1);
		case 6: 
			return obj1.getStatus().compareTo(obj2.getStatus()) 
					* (asc ? 1 : -1);
		default: 
			return obj1.getTahun().compareTo(obj2.getTahun()) * (asc ? 1 : -1);
		}

	}

}
