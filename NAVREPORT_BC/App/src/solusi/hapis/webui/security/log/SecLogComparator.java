package solusi.hapis.webui.security.log;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.model.SecLog;

public class SecLogComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_ACTIVITY = 1;
	public static int COMPARE_BY_PERFORMED_BY = 2;
	public static int COMPARE_BY_PERFORMED_ON = 3;
	public static int COMPARE_BY_IP = 4;

	private boolean asc = true;
	private int type = 0;

	public SecLogComparator(boolean asc, int type) {
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
		SecLog obj1 = (SecLog) o1;
		SecLog obj2 = (SecLog) o2;
		
        
		switch (type) {
		case 1: // Compare Activity
			return obj1.getLogActivity().compareTo(obj2.getLogActivity()) * (asc ? 1 : -1);
		case 2: // Compare Performed By
			return obj1.getPerformedBy().compareTo(obj2.getPerformedBy()) * (asc ? 1 : -1);
		case 3: // Compare Performed On
			return obj1.getPerformedOn().compareTo(obj2.getPerformedOn()) * (asc ? 1 : -1);
		case 4: // Compare IP
			return obj1.getIp().compareTo(obj2.getIp()) * (asc ? 1 : -1);
		default: // Compare Activity
			return obj1.getLogActivity().compareTo(obj2.getLogActivity()) * (asc ? 1 : -1);
		}

	}

}
