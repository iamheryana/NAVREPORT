package solusi.hapis.backend.model;

import java.util.Date;

public class SecLog {
	private long id = Long.MIN_VALUE;
	private String logActivity;
	private String performedBy;
	private Date performedOn;
	private String ip;

	public SecLog() {
	}

	public SecLog(String logActivity, String performedBy, Date performedOn, String ip) {
		this.logActivity = logActivity;
		this.performedBy = performedBy;
		this.performedOn = performedOn;
		this.ip = ip;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogActivity() {
		return logActivity;
	}

	public void setLogActivity(String logActivity) {
		this.logActivity = logActivity;
	}

	public String getPerformedBy() {
		return performedBy;
	}

	public void setPerformedBy(String performedBy) {
		this.performedBy = performedBy;
	}

	public Date getPerformedOn() {
		return performedOn;
	}

	public void setPerformedOn(Date performedOn) {
		this.performedOn = performedOn;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
}
