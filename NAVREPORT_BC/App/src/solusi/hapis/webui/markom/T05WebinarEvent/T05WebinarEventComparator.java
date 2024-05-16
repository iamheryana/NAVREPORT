package solusi.hapis.webui.markom.T05WebinarEvent;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.common.CommonUtils;

public class T05WebinarEventComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_WEBINARID = 1;
	public static int COMPARE_BY_TOPIC = 2;
	public static int COMPARE_BY_ACTUALSTARTTIME= 3;
	public static int COMPARE_BY_ACTUALDURATION = 4;
	public static int COMPARE_BY_NUMREGISTERED = 5;
		
	private boolean asc = true;
	private int type = 0;

	public T05WebinarEventComparator(boolean asc, int type) {
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
		T05WebinarEvent obj1 = (T05WebinarEvent) o1;
		T05WebinarEvent obj2 = (T05WebinarEvent) o2;
		switch (type) {
		case 1: 
			return obj1.getWebinarId().compareTo(obj2.getWebinarId()) 
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getTopic().compareTo(obj2.getTopic())
					* (asc ? 1 : -1);
		case 3: 
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
		

			if(CommonUtils.isNotEmpty(obj1.getActualStartTime())){
				vTglFrom = obj1.getActualStartTime();
			}
				
			if(CommonUtils.isNotEmpty(obj2.getActualStartTime())){
				vTglUpto = obj2.getActualStartTime();
			}
			
			return vTglFrom.compareTo(vTglUpto) 
					* (asc ? 1 : -1);	
		case 4: 
			return obj1.getActualDuration().compareTo(obj2.getActualDuration())
					* (asc ? 1 : -1);
		case 5: 
			return obj1.getNumRegistered().compareTo(obj2.getNumRegistered())
					* (asc ? 1 : -1);
		
		default: 
			return obj1.getWebinarId().compareTo(obj2.getWebinarId()) * (asc ? 1 : -1);
		}

	}

}
