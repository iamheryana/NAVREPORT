package solusi.hapis.backend.navbi.model;

import java.util.Date;

public class T26CetakSroso {
	private static final long serialVersionUID = 1L;
	private long t26Id;	
	private Date dicetakPada;
	private String keterangan;
	private Date tglAwalTahun;	
	private Date tglAkhirTahun;
	private Date tglAwalBulanPeriod;
	private Date tglAkhirBulanPeriod;
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public T26CetakSroso(){
		
	}

	public long getT26Id() {
		return t26Id;
	}

	public void setT26Id(long t26Id) {
		this.t26Id = t26Id;
	}

	public Date getDicetakPada() {
		return dicetakPada;
	}

	public void setDicetakPada(Date dicetakPada) {
		this.dicetakPada = dicetakPada;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public Date getTglAwalTahun() {
		return tglAwalTahun;
	}

	public void setTglAwalTahun(Date tglAwalTahun) {
		this.tglAwalTahun = tglAwalTahun;
	}

	public Date getTglAkhirTahun() {
		return tglAkhirTahun;
	}

	public void setTglAkhirTahun(Date tglAkhirTahun) {
		this.tglAkhirTahun = tglAkhirTahun;
	}

	public Date getTglAwalBulanPeriod() {
		return tglAwalBulanPeriod;
	}

	public void setTglAwalBulanPeriod(Date tglAwalBulanPeriod) {
		this.tglAwalBulanPeriod = tglAwalBulanPeriod;
	}

	public Date getTglAkhirBulanPeriod() {
		return tglAkhirBulanPeriod;
	}

	public void setTglAkhirBulanPeriod(Date tglAkhirBulanPeriod) {
		this.tglAkhirBulanPeriod = tglAkhirBulanPeriod;
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
