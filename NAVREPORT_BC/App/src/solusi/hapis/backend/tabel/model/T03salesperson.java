package solusi.hapis.backend.tabel.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import solusi.hapis.common.CommonUtils;

public class T03salesperson {
	private static final long serialVersionUID = 1L;
	private int t03Id;
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
	
	private Set<T08targetsales> t08targetsaless = new HashSet<T08targetsales>(0);
	
	
	public T03salesperson(){
    	
    }

//	public T03salesperson(String nik, String sales, String salesName, String spv,  BigDecimal target, Date periodeResign) {
//		super();
//		this.nik = nik;
//		this.sales = sales;
//		this.salesName = salesName;
//		this.spv = spv;
//		this.target  = target;
//		this.periodeResign = periodeResign;
//	}

	public int getT03Id() {
		return t03Id;
	}

	public void setT03Id(int t03Id) {
		this.t03Id = t03Id;
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
		this.sales = CommonUtils.toUpperCase(sales);
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
		this.spv = CommonUtils.toUpperCase(spv);
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<T08targetsales> getT08targetsaless() {
		return t08targetsaless;
	}

	public void setT08targetsaless(Set<T08targetsales> t08targetsaless) {
		this.t08targetsaless = t08targetsaless;
	}



	
	
}
