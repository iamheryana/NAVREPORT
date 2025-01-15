package solusi.hapis.backend.navbi.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class M07UserroleCostingH {
	private static final long serialVersionUID = 1L;
	private long m07Id;	
	private String username;
	private String rolename;
	private String filteruser;	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	private Set<M08UserroleCostingD> m08UserroleCostingDs = new HashSet<M08UserroleCostingD>(0);
	
	public M07UserroleCostingH(){
    	
    }


	public long getM07Id() {
		return m07Id;
	}


	public void setM07Id(long m07Id) {
		this.m07Id = m07Id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getRolename() {
		return rolename;
	}


	public void setRolename(String rolename) {
		this.rolename = rolename;
	}


	public String getFilteruser() {
		return filteruser;
	}


	public void setFilteruser(String filteruser) {
		this.filteruser = filteruser;
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


	public Set<M08UserroleCostingD> getM08UserroleCostingDs() {
		return m08UserroleCostingDs;
	}


	public void setM08UserroleCostingDs(
			Set<M08UserroleCostingD> m08UserroleCostingDs) {
		this.m08UserroleCostingDs = m08UserroleCostingDs;
	}
	
	
	
}
