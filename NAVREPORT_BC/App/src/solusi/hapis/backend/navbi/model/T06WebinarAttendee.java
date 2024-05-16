package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class T06WebinarAttendee {
	private static final long serialVersionUID = 1L;
	private long t06Id;	
	private T05WebinarEvent t05WebinarEvent;	
	private String attended;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String emailCorporate;
	private String countryRegion;
	private String stateProvince;	
	private String phone;
	private String industry;
	private String organization;
	private String jobTitle;
	private String questionsAndComments;
	private Date registrationTime;
	private String approvalStatus;
	private Integer timeInSession;
	private String nomorGopayOvo;
	private String countryRegionName;
	private Date joinTime;
	private Date leaveTime;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	
	public T06WebinarAttendee(){
		
	}


	public long getT06Id() {
		return t06Id;
	}


	public void setT06Id(long t06Id) {
		this.t06Id = t06Id;
	}


	public T05WebinarEvent getT05WebinarEvent() {
		return t05WebinarEvent;
	}


	public void setT05WebinarEvent(T05WebinarEvent t05WebinarEvent) {
		this.t05WebinarEvent = t05WebinarEvent;
	}


	public String getAttended() {
		return attended;
	}


	public void setAttended(String attended) {
		this.attended = attended;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailCorporate() {
		return emailCorporate;
	}


	public void setEmailCorporate(String emailCorporate) {
		this.emailCorporate = emailCorporate;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCountryRegion() {
		return countryRegion;
	}


	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}


	public String getStateProvince() {
		return stateProvince;
	}


	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getIndustry() {
		return industry;
	}


	public void setIndustry(String industry) {
		this.industry = industry;
	}


	public String getOrganization() {
		return organization;
	}


	public void setOrganization(String organization) {
		this.organization = organization;
	}


	public String getJobTitle() {
		return jobTitle;
	}


	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}


	public String getQuestionsAndComments() {
		return questionsAndComments;
	}


	public void setQuestionsAndComments(String questionsAndComments) {
		this.questionsAndComments = questionsAndComments;
	}


	public Date getRegistrationTime() {
		return registrationTime;
	}


	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}


	public String getApprovalStatus() {
		return approvalStatus;
	}


	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}


	public Integer getTimeInSession() {
		return timeInSession;
	}


	public void setTimeInSession(Integer timeInSession) {
		this.timeInSession = timeInSession;
	}


	public String getNomorGopayOvo() {
		return nomorGopayOvo;
	}


	public void setNomorGopayOvo(String nomorGopayOvo) {
		this.nomorGopayOvo = nomorGopayOvo;
	}


	public String getCountryRegionName() {
		return countryRegionName;
	}


	public void setCountryRegionName(String countryRegionName) {
		this.countryRegionName = countryRegionName;
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


	public Date getJoinTime() {
		return joinTime;
	}


	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}


	public Date getLeaveTime() {
		return leaveTime;
	}


	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	
}
