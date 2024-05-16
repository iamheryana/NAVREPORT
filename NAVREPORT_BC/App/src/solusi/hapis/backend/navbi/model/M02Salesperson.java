package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class M02Salesperson {
	private static final long serialVersionUID = 1L;
	private long m02Id;	
	private String nik;
	private String sales;
	private String salesName;	
	private String spv;	
	private BigDecimal target;
	private Date periodeResign;	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	private Set<M03Targetsales> m03Targetsaless = new HashSet<M03Targetsales>(0);
	
	public M02Salesperson(){
    	
    }

	public long getM02Id() {
		return m02Id;
	}

	public void setM02Id(long m02Id) {
		this.m02Id = m02Id;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSpv() {
		return spv;
	}

	public void setSpv(String spv) {
		this.spv = spv;
	}

	public BigDecimal getTarget() {
		return target;
	}

	public void setTarget(BigDecimal target) {
		this.target = target;
	}

	public Date getPeriodeResign() {
		return periodeResign;
	}

	public void setPeriodeResign(Date periodeResign) {
		this.periodeResign = periodeResign;
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

	public Set<M03Targetsales> getM03Targetsaless() {
		return m03Targetsaless;
	}

	public void setM03Targetsaless(Set<M03Targetsales> m03Targetsaless) {
		this.m03Targetsaless = m03Targetsaless;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
