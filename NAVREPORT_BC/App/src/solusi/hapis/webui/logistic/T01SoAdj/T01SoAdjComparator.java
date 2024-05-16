package solusi.hapis.webui.logistic.T01SoAdj;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import solusi.hapis.backend.navbi.model.T01SoAdj;
import solusi.hapis.common.CommonUtils;


public class T01SoAdjComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

				
	public static int COMPARE_BY_NOSO = 1;
	public static int COMPARE_BY_JENISPAYMENT = 2;
	public static int COMPARE_BY_QTY = 3;
	public static int COMPARE_BY_ESTREALISASI = 4;
	public static int COMPARE_BY_USECCLDATE = 5;
	public static int COMPARE_BY_ADDDAYS = 6;
	public static int COMPARE_BY_KETERANGANDP = 7;
	public static int COMPARE_BY_USEQTY = 8;
	
	
	private boolean asc = true;
	private int type = 0;

	public T01SoAdjComparator(boolean asc, int type) {
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
		T01SoAdj obj1 = (T01SoAdj) o1;
		T01SoAdj obj2 = (T01SoAdj) o2;
		switch (type) {
		case 1: 
			return obj1.getNoSo().compareTo(obj2.getNoSo())
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getJenisPayment().compareTo(obj2.getJenisPayment())
					* (asc ? 1 : -1);
		case 3: 
			BigDecimal amtObj1 = (BigDecimal) (CommonUtils.isNotEmpty(obj1.getQty())?obj1.getQty():0);
			BigDecimal amtObj2 = (BigDecimal) (CommonUtils.isNotEmpty(obj2.getQty())?obj2.getQty():0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);			
		case 4: 
			String dROld = "01/01/1900";		
			SimpleDateFormat dfROld= new SimpleDateFormat("dd/MM/yyyy");
			Date vTglOld = new Date();
			try {
				vTglOld  = dfROld.parse(dROld);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			Date tglEst1 = (obj1.getEstRealisasi() != null)? obj1.getEstRealisasi() : vTglOld;
			Date tglEst2 = (obj2.getEstRealisasi() != null)? obj2.getEstRealisasi() : vTglOld;
			return tglEst1.compareTo(tglEst2)
					* (asc ? 1 : -1);
		case 5: 
			return obj1.getUseCclDate().compareTo(obj2.getUseCclDate())
					* (asc ? 1 : -1);	
		case 6: 
			Integer addDaysObj1 = (Integer) (CommonUtils.isNotEmpty(obj1.getAddDays())?obj1.getAddDays():0);
			Integer addDaysObj2 = (Integer) (CommonUtils.isNotEmpty(obj2.getAddDays())?obj2.getAddDays():0);
			return addDaysObj1.compareTo(addDaysObj2)
					* (asc ? 1 : -1);	
		case 7: 
			String ket1 =  (obj1.getKeteranganDp() != null)? obj1.getKeteranganDp() : ".";
			String ket2 =  (obj2.getKeteranganDp() != null)? obj2.getKeteranganDp() : ".";
			return ket1.compareTo(ket2)
					* (asc ? 1 : -1);
		case 8: 
			return obj1.getUseQty().compareTo(obj2.getUseQty())
					* (asc ? 1 : -1);		
		default: 
			return obj1.getNoSo().compareTo(obj2.getNoSo()) * (asc ? 1 : -1);
		}

	}

}
