package solusi.hapis.backend.navbi.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.zkoss.util.media.Media;

public class T29CostingH {
	private static final long serialVersionUID = 1L;
	private long t29Id;	
	private String company;
	private String noCosting;
	private Date tglCosting;		
	private String noBso;	
	private String noSo;	
	private String salesman;	
	private String noPoCustomer;	
	private String customer;	
	private String noPenawaran;
	private String fileBso;
	private String fileInfoPrice;
	private String filePoCustomer;
	
	private BigDecimal salesHw3ps;
	private BigDecimal salesAcsps;
	private BigDecimal salesOwnsw;
	private BigDecimal cogsHw3ps;
	private BigDecimal cogsOthers;
	
	private BigDecimal totalSales;
	private BigDecimal totalCogs;
	private BigDecimal marginPcn;
	private BigDecimal marginPcnNonacsps;
	
	private BigDecimal incentiveFormulaHw3ps;
	private BigDecimal incentiveFormulaAcsps;
	private BigDecimal incentiveFormulaOwnsw;
	private BigDecimal incentiveApproveHw3ps;
	private BigDecimal incentiveApproveAcsps;
	private BigDecimal incentiveApproveOwnsw;
	
	private BigDecimal incentiveNonsalesHw3ps;
	private BigDecimal incentiveNonsalesAcsps;
	private BigDecimal incentiveNonsalesOwnsw;		
	private BigDecimal amountNonsalesHw3ps;
	private BigDecimal amountNonsalesAcsps;
	private BigDecimal amountNonsalesOwnsw;	
	
	private BigDecimal incentiveKomisiHw3ps;
	private BigDecimal incentiveKomisiAcsps;
	private BigDecimal incentiveKomisiOwnsw;	
	private BigDecimal amountKomisiHw3ps;
	private BigDecimal amountKomisiAcsps;
	private BigDecimal amountKomisiOwnsw;	
	
	private BigDecimal incentiveSbonusHw3ps;
	private BigDecimal incentiveSbonusAcsps;
	private BigDecimal incentiveSbonusOwnsw;	
	private BigDecimal amountSbonusHw3ps;
	private BigDecimal amountSbonusAcsps;
	private BigDecimal amountSbonusOwnsw;	
	
	
	private BigDecimal amountTqsHw3ps;
	private BigDecimal amountTqsAcsps;
	private BigDecimal amountTqsOwnsw;
	
	
	private BigDecimal amountKomisi;
	private BigDecimal amountSbonus;
	private BigDecimal amountSalesTqs;
	private BigDecimal amountNonsales;
	
	private Media uploadBSO;
	private Media uploadInfoPrice;
	private Media uploadPoCustomer;
	
	private BigDecimal amountInvoice;
	private BigDecimal amountLunas;
	
	private String flagInvoice;
	private String flagLunas;
	
	private String flagStatus;	
	private Integer version;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
		
	private Set<T30CostingDHw3ps> t30CostingDHw3pss = new HashSet<T30CostingDHw3ps>(0);
	private Set<T31CostingDAcsps> t31CostingDAcspss = new HashSet<T31CostingDAcsps>(0);
	private Set<T32CostingDOwnsw> t32CostingDOwnsws = new HashSet<T32CostingDOwnsw>(0);
	private Set<T33CostingDOther> t33CostingDOthers = new HashSet<T33CostingDOther>(0);
	private Set<T34CostingDPayment> t34CostingDPayments = new HashSet<T34CostingDPayment>(0);
	
	
	public T29CostingH(){
    	
    }

	public long getT29Id() {
		return t29Id;
	}

	public void setT29Id(long t29Id) {
		this.t29Id = t29Id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNoCosting() {
		return noCosting;
	}

	public void setNoCosting(String noCosting) {
		this.noCosting = noCosting;
	}

	public Date getTglCosting() {
		return tglCosting;
	}

	public void setTglCosting(Date tglCosting) {
		this.tglCosting = tglCosting;
	}

	public String getNoBso() {
		return noBso;
	}

	public void setNoBso(String noBso) {
		this.noBso = noBso;
	}

	public String getNoSo() {
		return noSo;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public void setNoSo(String noSo) {
		this.noSo = noSo;
	}

	public String getNoPoCustomer() {
		return noPoCustomer;
	}

	public void setNoPoCustomer(String noPoCustomer) {
		this.noPoCustomer = noPoCustomer;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getFileBso() {
		return fileBso;
	}

	public void setFileBso(String fileBso) {
		this.fileBso = fileBso;
	}

	public String getFileInfoPrice() {
		return fileInfoPrice;
	}

	public void setFileInfoPrice(String fileInfoPrice) {
		this.fileInfoPrice = fileInfoPrice;
	}

	public String getFilePoCustomer() {
		return filePoCustomer;
	}

	public void setFilePoCustomer(String filePoCustomer) {
		this.filePoCustomer = filePoCustomer;
	}

	public BigDecimal getSalesHw3ps() {
		return salesHw3ps;
	}

	public void setSalesHw3ps(BigDecimal salesHw3ps) {
		this.salesHw3ps = salesHw3ps;
	}

	public BigDecimal getSalesAcsps() {
		return salesAcsps;
	}

	public void setSalesAcsps(BigDecimal salesAcsps) {
		this.salesAcsps = salesAcsps;
	}

	public BigDecimal getSalesOwnsw() {
		return salesOwnsw;
	}

	public void setSalesOwnsw(BigDecimal salesOwnsw) {
		this.salesOwnsw = salesOwnsw;
	}

	public BigDecimal getCogsHw3ps() {
		return cogsHw3ps;
	}

	public void setCogsHw3ps(BigDecimal cogsHw3ps) {
		this.cogsHw3ps = cogsHw3ps;
	}

	public BigDecimal getCogsOthers() {
		return cogsOthers;
	}

	public void setCogsOthers(BigDecimal cogsOthers) {
		this.cogsOthers = cogsOthers;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public BigDecimal getTotalCogs() {
		return totalCogs;
	}

	public void setTotalCogs(BigDecimal totalCogs) {
		this.totalCogs = totalCogs;
	}

	public BigDecimal getMarginPcn() {
		return marginPcn;
	}

	public void setMarginPcn(BigDecimal marginPcn) {
		this.marginPcn = marginPcn;
	}

	public BigDecimal getIncentiveFormulaHw3ps() {
		return incentiveFormulaHw3ps;
	}

	public void setIncentiveFormulaHw3ps(BigDecimal incentiveFormulaHw3ps) {
		this.incentiveFormulaHw3ps = incentiveFormulaHw3ps;
	}

	public BigDecimal getIncentiveFormulaAcsps() {
		return incentiveFormulaAcsps;
	}

	public void setIncentiveFormulaAcsps(BigDecimal incentiveFormulaAcsps) {
		this.incentiveFormulaAcsps = incentiveFormulaAcsps;
	}

	public BigDecimal getIncentiveFormulaOwnsw() {
		return incentiveFormulaOwnsw;
	}

	public void setIncentiveFormulaOwnsw(BigDecimal incentiveFormulaOwnsw) {
		this.incentiveFormulaOwnsw = incentiveFormulaOwnsw;
	}

	public BigDecimal getIncentiveApproveHw3ps() {
		return incentiveApproveHw3ps;
	}

	public void setIncentiveApproveHw3ps(BigDecimal incentiveApproveHw3ps) {
		this.incentiveApproveHw3ps = incentiveApproveHw3ps;
	}

	public BigDecimal getIncentiveApproveAcsps() {
		return incentiveApproveAcsps;
	}

	public void setIncentiveApproveAcsps(BigDecimal incentiveApproveAcsps) {
		this.incentiveApproveAcsps = incentiveApproveAcsps;
	}

	public BigDecimal getIncentiveApproveOwnsw() {
		return incentiveApproveOwnsw;
	}

	public void setIncentiveApproveOwnsw(BigDecimal incentiveApproveOwnsw) {
		this.incentiveApproveOwnsw = incentiveApproveOwnsw;
	}

	public BigDecimal getIncentiveNonsalesHw3ps() {
		return incentiveNonsalesHw3ps;
	}

	public void setIncentiveNonsalesHw3ps(BigDecimal incentiveNonsalesHw3ps) {
		this.incentiveNonsalesHw3ps = incentiveNonsalesHw3ps;
	}

	public BigDecimal getIncentiveNonsalesAcsps() {
		return incentiveNonsalesAcsps;
	}

	public void setIncentiveNonsalesAcsps(BigDecimal incentiveNonsalesAcsps) {
		this.incentiveNonsalesAcsps = incentiveNonsalesAcsps;
	}

	public BigDecimal getIncentiveNonsalesOwnsw() {
		return incentiveNonsalesOwnsw;
	}

	public void setIncentiveNonsalesOwnsw(BigDecimal incentiveNonsalesOwnsw) {
		this.incentiveNonsalesOwnsw = incentiveNonsalesOwnsw;
	}

	public BigDecimal getAmountNonsalesHw3ps() {
		return amountNonsalesHw3ps;
	}

	public void setAmountNonsalesHw3ps(BigDecimal amountNonsalesHw3ps) {
		this.amountNonsalesHw3ps = amountNonsalesHw3ps;
	}

	public BigDecimal getAmountNonsalesAcsps() {
		return amountNonsalesAcsps;
	}

	public void setAmountNonsalesAcsps(BigDecimal amountNonsalesAcsps) {
		this.amountNonsalesAcsps = amountNonsalesAcsps;
	}

	public BigDecimal getAmountNonsalesOwnsw() {
		return amountNonsalesOwnsw;
	}

	public void setAmountNonsalesOwnsw(BigDecimal amountNonsalesOwnsw) {
		this.amountNonsalesOwnsw = amountNonsalesOwnsw;
	}

	public BigDecimal getIncentiveKomisiHw3ps() {
		return incentiveKomisiHw3ps;
	}

	public void setIncentiveKomisiHw3ps(BigDecimal incentiveKomisiHw3ps) {
		this.incentiveKomisiHw3ps = incentiveKomisiHw3ps;
	}

	public BigDecimal getIncentiveKomisiAcsps() {
		return incentiveKomisiAcsps;
	}

	public void setIncentiveKomisiAcsps(BigDecimal incentiveKomisiAcsps) {
		this.incentiveKomisiAcsps = incentiveKomisiAcsps;
	}

	public BigDecimal getIncentiveKomisiOwnsw() {
		return incentiveKomisiOwnsw;
	}

	public void setIncentiveKomisiOwnsw(BigDecimal incentiveKomisiOwnsw) {
		this.incentiveKomisiOwnsw = incentiveKomisiOwnsw;
	}

	public BigDecimal getAmountKomisiHw3ps() {
		return amountKomisiHw3ps;
	}

	public void setAmountKomisiHw3ps(BigDecimal amountKomisiHw3ps) {
		this.amountKomisiHw3ps = amountKomisiHw3ps;
	}

	public BigDecimal getAmountKomisiAcsps() {
		return amountKomisiAcsps;
	}

	public void setAmountKomisiAcsps(BigDecimal amountKomisiAcsps) {
		this.amountKomisiAcsps = amountKomisiAcsps;
	}

	public BigDecimal getAmountKomisiOwnsw() {
		return amountKomisiOwnsw;
	}

	public void setAmountKomisiOwnsw(BigDecimal amountKomisiOwnsw) {
		this.amountKomisiOwnsw = amountKomisiOwnsw;
	}

	public BigDecimal getIncentiveSbonusHw3ps() {
		return incentiveSbonusHw3ps;
	}

	public void setIncentiveSbonusHw3ps(BigDecimal incentiveSbonusHw3ps) {
		this.incentiveSbonusHw3ps = incentiveSbonusHw3ps;
	}

	public BigDecimal getIncentiveSbonusAcsps() {
		return incentiveSbonusAcsps;
	}

	public void setIncentiveSbonusAcsps(BigDecimal incentiveSbonusAcsps) {
		this.incentiveSbonusAcsps = incentiveSbonusAcsps;
	}

	public BigDecimal getIncentiveSbonusOwnsw() {
		return incentiveSbonusOwnsw;
	}

	public void setIncentiveSbonusOwnsw(BigDecimal incentiveSbonusOwnsw) {
		this.incentiveSbonusOwnsw = incentiveSbonusOwnsw;
	}

	public BigDecimal getAmountSbonusHw3ps() {
		return amountSbonusHw3ps;
	}

	public void setAmountSbonusHw3ps(BigDecimal amountSbonusHw3ps) {
		this.amountSbonusHw3ps = amountSbonusHw3ps;
	}

	public BigDecimal getAmountSbonusAcsps() {
		return amountSbonusAcsps;
	}

	public void setAmountSbonusAcsps(BigDecimal amountSbonusAcsps) {
		this.amountSbonusAcsps = amountSbonusAcsps;
	}

	public BigDecimal getAmountSbonusOwnsw() {
		return amountSbonusOwnsw;
	}

	public void setAmountSbonusOwnsw(BigDecimal amountSbonusOwnsw) {
		this.amountSbonusOwnsw = amountSbonusOwnsw;
	}

	public BigDecimal getAmountKomisi() {
		return amountKomisi;
	}

	public void setAmountKomisi(BigDecimal amountKomisi) {
		this.amountKomisi = amountKomisi;
	}

	public BigDecimal getAmountSbonus() {
		return amountSbonus;
	}

	public void setAmountSbonus(BigDecimal amountSbonus) {
		this.amountSbonus = amountSbonus;
	}

	public BigDecimal getAmountSalesTqs() {
		return amountSalesTqs;
	}

	public void setAmountSalesTqs(BigDecimal amountSalesTqs) {
		this.amountSalesTqs = amountSalesTqs;
	}

	public BigDecimal getAmountNonsales() {
		return amountNonsales;
	}

	public void setAmountNonsales(BigDecimal amountNonsales) {
		this.amountNonsales = amountNonsales;
	}
	
	public String getFlagInvoice() {
		return flagInvoice;
	}

	public void setFlagInvoice(String flagInvoice) {
		this.flagInvoice = flagInvoice;
	}

	public String getFlagLunas() {
		return flagLunas;
	}

	public void setFlagLunas(String flagLunas) {
		this.flagLunas = flagLunas;
	}

	public String getFlagStatus() {
		return flagStatus;
	}

	public void setFlagStatus(String flagStatus) {
		this.flagStatus = flagStatus;
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

	public Set<T30CostingDHw3ps> getT30CostingDHw3pss() {
		return t30CostingDHw3pss;
	}

	public void setT30CostingDHw3pss(Set<T30CostingDHw3ps> t30CostingDHw3pss) {
		this.t30CostingDHw3pss = t30CostingDHw3pss;
	}

	public Set<T31CostingDAcsps> getT31CostingDAcspss() {
		return t31CostingDAcspss;
	}

	public void setT31CostingDAcspss(Set<T31CostingDAcsps> t31CostingDAcspss) {
		this.t31CostingDAcspss = t31CostingDAcspss;
	}

	public Set<T32CostingDOwnsw> getT32CostingDOwnsws() {
		return t32CostingDOwnsws;
	}

	public void setT32CostingDOwnsws(Set<T32CostingDOwnsw> t32CostingDOwnsws) {
		this.t32CostingDOwnsws = t32CostingDOwnsws;
	}

	public Set<T33CostingDOther> getT33CostingDOthers() {
		return t33CostingDOthers;
	}

	public void setT33CostingDOthers(Set<T33CostingDOther> t33CostingDOthers) {
		this.t33CostingDOthers = t33CostingDOthers;
	}

	public BigDecimal getAmountTqsHw3ps() {
		return amountTqsHw3ps;
	}

	public void setAmountTqsHw3ps(BigDecimal amountTqsHw3ps) {
		this.amountTqsHw3ps = amountTqsHw3ps;
	}

	public BigDecimal getAmountTqsAcsps() {
		return amountTqsAcsps;
	}

	public void setAmountTqsAcsps(BigDecimal amountTqsAcsps) {
		this.amountTqsAcsps = amountTqsAcsps;
	}

	public BigDecimal getAmountTqsOwnsw() {
		return amountTqsOwnsw;
	}

	public void setAmountTqsOwnsw(BigDecimal amountTqsOwnsw) {
		this.amountTqsOwnsw = amountTqsOwnsw;
	}

	public Media getUploadBSO() {
		return uploadBSO;
	}

	public void setUploadBSO(Media uploadBSO) {
		this.uploadBSO = uploadBSO;
	}

	public Media getUploadInfoPrice() {
		return uploadInfoPrice;
	}

	public void setUploadInfoPrice(Media uploadInfoPrice) {
		this.uploadInfoPrice = uploadInfoPrice;
	}

	public Media getUploadPoCustomer() {
		return uploadPoCustomer;
	}

	public void setUploadPoCustomer(Media uploadPoCustomer) {
		this.uploadPoCustomer = uploadPoCustomer;
	}

	public String getNoPenawaran() {
		return noPenawaran;
	}

	public void setNoPenawaran(String noPenawaran) {
		this.noPenawaran = noPenawaran;
	}

	public Set<T34CostingDPayment> getT34CostingDPayments() {
		return t34CostingDPayments;
	}

	public void setT34CostingDPayments(Set<T34CostingDPayment> t34CostingDPayments) {
		this.t34CostingDPayments = t34CostingDPayments;
	}

	public BigDecimal getAmountInvoice() {
		return amountInvoice;
	}

	public void setAmountInvoice(BigDecimal amountInvoice) {
		this.amountInvoice = amountInvoice;
	}

	public BigDecimal getAmountLunas() {
		return amountLunas;
	}

	public void setAmountLunas(BigDecimal amountLunas) {
		this.amountLunas = amountLunas;
	}

	public BigDecimal getMarginPcnNonacsps() {
		return marginPcnNonacsps;
	}

	public void setMarginPcnNonacsps(BigDecimal marginPcnNonacsps) {
		this.marginPcnNonacsps = marginPcnNonacsps;
	}

	

}
