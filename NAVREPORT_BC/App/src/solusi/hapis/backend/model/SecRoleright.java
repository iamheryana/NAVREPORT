package solusi.hapis.backend.model;

public class SecRoleright {

	private long id = Long.MIN_VALUE;
	private int version;
	private SecRole secRole;
	private SecRight secRight;
	private long parentRlrId;

	public SecRoleright(){
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public SecRole getSecRole() {
		return secRole;
	}

	public void setSecRole(SecRole secRole) {
		this.secRole = secRole;
	}

	public SecRight getSecRight() {
		return secRight;
	}

	public void setSecRight(SecRight secRight) {
		this.secRight = secRight;
	}

	public long getParentRlrId() {
		return parentRlrId;
	}

	public void setParentRlrId(long parentRlrId) {
		this.parentRlrId = parentRlrId;
	}

}
