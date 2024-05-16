package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class Temp01WebinarEvent {
	private static final long serialVersionUID = 1L;
	private long temp01Id;
	private String topic;
	private String webinarId;
	private Date actualStartTime;
	private Integer actualDuration;
	private Integer numRegistered;
	private Integer numCancelled;
	private Integer uniqueViewers;
	private Integer totalUsers;
	private Integer maxConcurrentViews;
	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public Temp01WebinarEvent(){
		
	}

	public Temp01WebinarEvent(String topic, String webinarId,
			Date actualStartTime, Integer actualDuration,
			Integer numRegistered, Integer numCancelled, Integer uniqueViewers,
			Integer totalUsers, Integer maxConcurrentViews, String prosesId) {
		super();
		this.topic = topic;
		this.webinarId = webinarId;
		this.actualStartTime = actualStartTime;
		this.actualDuration = actualDuration;
		this.numRegistered = numRegistered;
		this.numCancelled = numCancelled;
		this.uniqueViewers = uniqueViewers;
		this.totalUsers = totalUsers;
		this.maxConcurrentViews = maxConcurrentViews;
		this.prosesId = prosesId;
	}

	
	public long getTemp01Id() {
		return temp01Id;
	}

	public void setTemp01Id(long temp01Id) {
		this.temp01Id = temp01Id;
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

	public String getProsesId() {
		return prosesId;
	}

	public void setProsesId(String prosesId) {
		this.prosesId = prosesId;
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
	
	


	
	
}
