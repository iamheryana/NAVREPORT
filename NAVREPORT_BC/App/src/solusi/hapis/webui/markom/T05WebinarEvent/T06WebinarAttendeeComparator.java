package solusi.hapis.webui.markom.T05WebinarEvent;

import java.io.Serializable;
import java.util.Comparator;

import solusi.hapis.backend.navbi.model.T06WebinarAttendee;

public class T06WebinarAttendeeComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = -2127053833562854322L;

	public static int COMPARE_BY_ATTENDED = 1;
	public static int COMPARE_BY_FIRSTNAME = 2;
	public static int COMPARE_BY_LASTNAME= 3;
	public static int COMPARE_BY_ORGANIZATION = 4;
	public static int COMPARE_BY_JOBTITLE = 5;
	public static int COMPARE_BY_EMAIL = 6;
	public static int COMPARE_BY_JOINTIME = 7;
	
		
	private boolean asc = true;
	private int type = 0;

	public T06WebinarAttendeeComparator(boolean asc, int type) {
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
		T06WebinarAttendee obj1 = (T06WebinarAttendee) o1;
		T06WebinarAttendee obj2 = (T06WebinarAttendee) o2;
		switch (type) {
		case 1: 
			return obj1.getAttended().compareTo(obj2.getAttended()) 
					* (asc ? 1 : -1);
		case 2: 
			return obj1.getFirstName().compareTo(obj2.getFirstName())
					* (asc ? 1 : -1);
		case 3: 
			return obj1.getLastName().compareTo(obj2.getLastName())
					* (asc ? 1 : -1);
		case 4: 
			return obj1.getOrganization().compareTo(obj2.getOrganization())
					* (asc ? 1 : -1);
		case 5: 
			return obj1.getJobTitle().compareTo(obj2.getJobTitle())
					* (asc ? 1 : -1);
		case 6: 
			return obj1.getEmail().compareTo(obj2.getEmail())
					* (asc ? 1 : -1);
		case 7: 
			return obj1.getTimeInSession().compareTo(obj2.getTimeInSession())
					* (asc ? 1 : -1);
		
		default: 
			return obj1.getAttended().compareTo(obj2.getAttended()) * (asc ? 1 : -1);
		}

	}

}
