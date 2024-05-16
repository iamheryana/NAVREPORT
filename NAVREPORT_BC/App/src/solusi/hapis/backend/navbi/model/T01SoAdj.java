package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;


public class T01SoAdj {
	private static final long serialVersionUID = 1L;
	private int soId;
	private String noSo;
	private String jenisPayment;
	private BigDecimal qty;
	private Date estRealisasi;
	private String useCclDate;
	private int addDays;
	private String keteranganDp;
	private String useQty;
	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	
	public T01SoAdj(){
		
	}
	
	public T01SoAdj(String noSo, String jenisPayment, BigDecimal qty,
			Date estRealisasi, String useCclDate, int addDays, String keteranganDp,
			String useQty) {
		super();
		this.noSo = noSo;
		this.jenisPayment = jenisPayment;
		this.qty = qty;
		this.estRealisasi = estRealisasi;
		this.useCclDate = useCclDate;
		this.addDays = addDays;
		this.keteranganDp = keteranganDp;
		this.useQty = useQty;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getSoId() {
		return soId;
	}


	public void setSoId(int soId) {
		this.soId = soId;
	}


	public String getNoSo() {
		return noSo;
	}


	public void setNoSo(String noSo) {
		this.noSo = noSo.toUpperCase();
	}


	public String getJenisPayment() {
		return jenisPayment;
	}


	public void setJenisPayment(String jenisPayment) {
		this.jenisPayment = jenisPayment;
	}


	public BigDecimal getQty() {
		return qty;
	}


	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}


	public Date getEstRealisasi() {
		return estRealisasi;
	}


	public void setEstRealisasi(Date estRealisasi) {
		this.estRealisasi = estRealisasi;
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

	public String getUseCclDate() {
		return useCclDate;
	}

	public void setUseCclDate(String useCclDate) {
		this.useCclDate = useCclDate;
	}

	public int getAddDays() {
		return addDays;
	}

	public void setAddDays(int addDays) {
		this.addDays = addDays;
	}

	public String getKeteranganDp() {
		return keteranganDp;
	}

	public void setKeteranganDp(String keteranganDp) {
		this.keteranganDp = keteranganDp;
	}

	public String getUseQty() {
		return useQty;
	}

	public void setUseQty(String useQty) {
		this.useQty = useQty;
	}
	
	


	
}
