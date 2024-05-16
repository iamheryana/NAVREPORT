package solusi.hapis.backend.navbi.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class T05WebinarEvent {
	private static final long serialVersionUID = 1L;
	private long t05Id;
	private String topic;
	private String webinarId;
	private Date actualStartTime;
	private Integer actualDuration;
	private Integer numRegistered;
	private Integer numCancelled;
	private Integer uniqueViewers;
	private Integer totalUsers;
	private Integer maxConcurrentViews;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	private Set<T06WebinarAttendee> t06WebinarAttendees = new HashSet<T06WebinarAttendee>(0);
	
	
	public T05WebinarEvent(){
		
	}


	public long getT05Id() {
		return t05Id;
	}


	public void setT05Id(long t05Id) {
		this.t05Id = t05Id;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public String getWebinarId() {
		return webinarId;
	}


	public void setWebinarId(String webinarId) {
		this.webinarId = webinarId;
	}


	public Date getActualStartTime() {
		return actualStartTime;
	}


	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}


	public Integer getActualDuration() {
		return actualDuration;
	}


	public void setActualDuration(Integer actualDuration) {
		this.actualDuration = actualDuration;
	}


	public Integer getNumRegistered() {
		return numRegistered;
	}


	public void setNumRegistered(Integer numRegistered) {
		this.numRegistered = numRegistered;
	}


	public Integer getNumCancelled() {
		return numCancelled;
	}


	public void setNumCancelled(Integer numCancelled) {
		this.numCancelled = numCancelled;
	}


	public Integer getUniqueViewers() {
		return uniqueViewers;
	}


	public void setUniqueViewers(Integer uniqueViewers) {
		this.uniqueViewers = uniqueViewers;
	}


	public Integer getTotalUsers() {
		return totalUsers;
	}


	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}


	public Integer getMaxConcurrentViews() {
		return maxConcurrentViews;
	}


	public void setMaxConcurrentViews(Integer maxConcurrentViews) {
		this.maxConcurrentViews = maxConcurrentViews;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Date getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Set<T06WebinarAttendee> getT06WebinarAttendees() {
		return t06WebinarAttendees;
	}


	public void setT06WebinarAttendees(Set<T06WebinarAttendee> t06WebinarAttendees) {
		this.t06WebinarAttendees = t06WebinarAttendees;
	}
	
	
}
