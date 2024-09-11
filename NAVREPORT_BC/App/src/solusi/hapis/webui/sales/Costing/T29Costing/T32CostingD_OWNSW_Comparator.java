package solusi.hapis.webui.sales.Costing.T29Costing;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T32CostingDOwnsw;
import solusi.hapis.common.CommonUtils;

public class T32CostingD_OWNSW_Comparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;
	

	public static int COMPARE_BY_ITEMDESC = 1;
	public static int COMPARE_BY_ITEMNO = 2;
	public static int COMPARE_BY_ITEMCATEGORY = 3;
	public static int COMPARE_BY_QTY = 4;
	public static int COMPARE_BY_SALESSATUAN = 5;
	public static int COMPARE_BY_SALESTOTAL = 6;
	public static int COMPARE_BY_CATATAN = 7;
	
	private boolean asc = true;
	private int type = 0;

	public T32CostingD_OWNSW_Comparator(boolean asc, int type) {
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
		T32CostingDOwnsw obj1 = (T32CostingDOwnsw) o1;
		T32CostingDOwnsw obj2 = (T32CostingDOwnsw) o2;
		switch (type) {
		case 1: 
			return obj1.getItemDesc().compareTo(obj2.getItemDesc()) 
					* (asc ? 1 : -1);			
		case 2: 			
			return obj1.getItemNo().compareTo(obj2.getItemNo()) 
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getItemCategory().compareTo(obj2.getItemCategory())
					* (asc ? 1 : -1);
		case 4: 
			BigDecimal amtObj1 = (BigDecimal) (CommonUtils.isNotEmpty(obj1.getQty())?obj1.getQty():0);
			BigDecimal amtObj2 = (BigDecimal) (CommonUtils.isNotEmpty(obj2.getQty())?obj2.getQty():0);
			return amtObj1.compareTo(amtObj2)
					* (asc ? 1 : -1);			
		case 5: 
			BigDecimal amtSalesSatuan1 = (BigDecimal) (CommonUtils.isNotEmpty(obj1.getSalesSatuan())?obj1.getSalesSatuan():0);
			BigDecimal amtSalesSatuan2 = (BigDecimal) (CommonUtils.isNotEmpty(obj2.getSalesSatuan())?obj2.getSalesSatuan():0);
			return amtSalesSatuan1.compareTo(amtSalesSatuan2)
					* (asc ? 1 : -1);	
		case 6: 
			BigDecimal amtSalesTotal1 = (BigDecimal) (CommonUtils.isNotEmpty(obj1.getSalesTotal())?obj1.getSalesTotal():0);
			BigDecimal amtSalesTotal2 = (BigDecimal) (CommonUtils.isNotEmpty(obj2.getSalesTotal())?obj2.getSalesTotal():0);
			return amtSalesTotal1.compareTo(amtSalesTotal2)
					* (asc ? 1 : -1);	
		case 7: 
			return obj1.getCatatan().compareTo(obj2.getCatatan())
					* (asc ? 1 : -1);
		default: 
			return obj1.getItemDesc().compareTo(obj2.getItemDesc()) * (asc ? 1 : -1);
		}

	}

}
