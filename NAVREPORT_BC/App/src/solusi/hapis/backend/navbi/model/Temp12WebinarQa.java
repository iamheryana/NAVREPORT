package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class Temp12WebinarQa {
	private static final long serialVersionUID = 1L;
	private long temp12Id;
	private String webinarId;
	private String noQst;
	private String question;
	private String askerName;
	private String askerEmail;
	private String answer01;
	private String answer02;
	private String answer03;
	private String answer04;
	private String answer05;
	private String answer06;
	private String answer07;
	private String answer08;
	private String answer09;
	private String answer10;
	private String prosesId;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public Temp12WebinarQa(){
		
	}



	public Temp12WebinarQa(String webinarId, String noQst, String question,
			String askerName, String askerEmail, String answer01,
			String answer02, String answer03, String answer04, String answer05,
			String answer06, String answer07, String answer08, String answer09,
			String answer10, String prosesId) {
		super();
		this.webinarId = webinarId;
		this.noQst = noQst;
		this.question = question;
		this.askerName = askerName;
		this.askerEmail = askerEmail;
		this.answer01 = answer01;
		this.answer02 = answer02;
		this.answer03 = answer03;
		this.answer04 = answer04;
		this.answer05 = answer05;
		this.answer06 = answer06;
		this.answer07 = answer07;
		this.answer08 = answer08;
		this.answer09 = answer09;
		this.answer10 = answer10;
		this.prosesId = prosesId;
	}



	public long getTemp12Id() {
		return temp12Id;
	}

	public void setTemp12Id(long temp12Id) {
		this.temp12Id = temp12Id;
	}

	public String getWebinarId() {
		return webinarId;
	}

	public void setWebinarId(String webinarId) {
		this.webinarId = webinarId;
	}

	public String getNoQst() {
		return noQst;
	}

	public void setNoQst(String noQst) {
		this.noQst = noQst;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAskerName() {
		return askerName;
	}

	public void setAskerName(String askerName) {
		this.askerName = askerName;
	}

	public String getAskerEmail() {
		return askerEmail;
	}

	public void setAskerEmail(String askerEmail) {
		this.askerEmail = askerEmail;
	}

	public String getAnswer01() {
		return answer01;
	}

	public void setAnswer01(String answer01) {
		this.answer01 = answer01;
	}

	public String getAnswer02() {
		return answer02;
	}

	public void setAnswer02(String answer02) {
		this.answer02 = answer02;
	}

	public String getAnswer03() {
		return answer03;
	}

	public void setAnswer03(String answer03) {
		this.answer03 = answer03;
	}

	public String getAnswer04() {
		return answer04;
	}

	public void setAnswer04(String answer04) {
		this.answer04 = answer04;
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



	public String getAnswer05() {
		return answer05;
	}



	public void setAnswer05(String answer05) {
		this.answer05 = answer05;
	}



	public String getAnswer06() {
		return answer06;
	}



	public void setAnswer06(String answer06) {
		this.answer06 = answer06;
	}



	public String getAnswer07() {
		return answer07;
	}



	public void setAnswer07(String answer07) {
		this.answer07 = answer07;
	}



	public String getAnswer08() {
		return answer08;
	}



	public void setAnswer08(String answer08) {
		this.answer08 = answer08;
	}



	public String getAnswer09() {
		return answer09;
	}



	public void setAnswer09(String answer09) {
		this.answer09 = answer09;
	}



	public String getAnswer10() {
		return answer10;
	}



	public void setAnswer10(String answer10) {
		this.answer10 = answer10;
	}
	
	
}
