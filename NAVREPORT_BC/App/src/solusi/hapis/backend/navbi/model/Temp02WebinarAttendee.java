package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class Temp02WebinarAttendee {
	private static final long serialVersionUID = 1L;
	private long temp02Id;	
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
	private Date joinTime;
	private Date leaveTime;
	private Integer timeInSession;
	private String nomorGopayOvo;
	private String countryRegionName;
	private String invitedBy ;
	private String salesInvitedBy ;
	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public Temp02WebinarAttendee(){
		
	}

	public Temp02WebinarAttendee(String attended, String userName, String firstName,
			String lastName, String email, String emailCorporate, String countryRegion,
			String stateProvince, String phone, String industry,
			String organization, String jobTitle, String questionsAndComments,
			Date registrationTime, String approvalStatus, Date joinTime,
			Date leaveTime, Integer timeInSession, String nomorGopayOvo,
			String invitedBy, String salesInvitedBy,
			String countryRegionName, String prosesId) {
		super();
		this.attended = attended;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.emailCorporate = emailCorporate;
		this.countryRegion = countryRegion;
		this.stateProvince = stateProvince;
		this.phone = phone;
		this.industry = industry;
		this.organization = organization;
		this.jobTitle = jobTitle;
		this.questionsAndComments = questionsAndComments;
		this.registrationTime = registrationTime;
		this.approvalStatus = approvalStatus;
		this.joinTime = joinTime;
		this.leaveTime = leaveTime;
		this.timeInSession = timeInSession;
		this.nomorGopayOvo = nomorGopayOvo;
		this.invitedBy = invitedBy;
		this.salesInvitedBy = salesInvitedBy;
		this.countryRegionName = countryRegionName;
		this.prosesId = prosesId;
	}

	public long getTemp02Id() {
		return temp02Id;
	}

	public void setTemp02Id(long temp02Id) {
		this.temp02Id = temp02Id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmailCorporate() {
		return emailCorporate;
	}

	public void setEmailCorporate(String emailCorporate) {
		this.emailCorporate = emailCorporate;
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
	
	public String getInvitedBy() {
		return invitedBy;
	}

	public void setInvitedBy(String invitedBy) {
		this.invitedBy = invitedBy;
	}

	public String getSalesInvitedBy() {
		return salesInvitedBy;
	}

	public void setSalesInvitedBy(String salesInvitedBy) {
		this.salesInvitedBy = salesInvitedBy;
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
