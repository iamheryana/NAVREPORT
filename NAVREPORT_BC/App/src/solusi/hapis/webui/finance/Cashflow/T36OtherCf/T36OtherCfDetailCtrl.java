package solusi.hapis.webui.finance.Cashflow.T36OtherCf;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T36OtherCf;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

/**
 * User: Heryana
 * Date: 28-11-2024
 */

public class T36OtherCfDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT36OtherCfDetail;
	
	protected Borderlayout borderlayout_T36OtherCfDetail;
	
	// Data Entry Component
	
	protected Bandbox cmbCompany;
	protected Listbox list_Company;
	
	protected Bandbox cmbReg;	
	protected Listbox list_Reg;
	
	protected Bandbox cmbTipe;	
	protected Listbox list_Tipe;

	protected Bandbox cmbBasis;	
	protected Listbox list_Basis;
	
	protected Bandbox cmbEvery;	
	protected Listbox list_Every;
	
	
	protected Textbox txtKeterangan;
	protected Decimalbox dcbAmount;	
	protected Datebox dbDuedate;
	
	protected Intbox intEvery;	

	protected Datebox dbFromDate;
	protected Datebox dbUptoDate;
	
	
	protected Label lblTgl;
	protected Label lblHari;
	
	protected Row rowDueDate;
	protected Row rowBasis;
	protected Row rowEvery;
	protected Row rowBerlakuMulai;
	protected Row rowBerlakuSd;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T36OtherCfMainCtrl T36OtherCfMainCtrl;
//	private SelectQueryService selectQueryService;
	

	/**
	 * default constructor.<br>
	 */
	public T36OtherCfDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT36OtherCfMainCtrl((T36OtherCfMainCtrl) arg
					.get("ModuleMainController"));

			getT36OtherCfMainCtrl().setT36OtherCfDetailCtrl(this);
		}
		
		windowT36OtherCfDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT36OtherCfDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		
		doFitSize(event);
	}

	
	
	
	public void doRenderCombo() {
		
		ListBoxUtil.resetList(list_Company);
		ListBoxUtil.resetList(list_Reg);
		ListBoxUtil.resetList(list_Tipe);
		ListBoxUtil.resetList(list_Basis);
		ListBoxUtil.resetList(list_Every);
		
		T36OtherCf anT36= getT36OtherCf();

		// Company ============================================================= START
		Listitem vListCompany = null;

		Listitem vListAUTOJAYA = list_Company.appendItem("Autojaya", "AUTOJAYA");
		Listitem vListSOLUSI = list_Company.appendItem("Solusi", "SOLUSI");
		
		if (anT36 != null) {
			if (anT36.getCompany() != null) {
				if (anT36.getCompany().equals("AUTOJAYA")) {
					vListCompany = vListAUTOJAYA;
				} else {
					vListCompany = vListSOLUSI;
				}
			}
		}
		
		if (vListCompany != null){
			cmbCompany.setValue(vListCompany.getLabel());
			list_Company.setSelectedItem(vListCompany);
		}
		// Company =============================================================== END
		
		// Reg ============================================================== START	
		Listitem vListReg = null;

		Listitem vListARI = list_Reg.appendItem("AR Installment", "ARI");
		Listitem vListAPI = list_Reg.appendItem("AP Installment", "API");
		Listitem vListPIB = list_Reg.appendItem("PIB Consignment", "PIB");
		Listitem vListOSO = list_Reg.appendItem("Outstanding SO", "OSO");
		Listitem vListOTH = list_Reg.appendItem("Other Expenses", "OTH");
		
		
		if (anT36 != null) {
			if (anT36.getReg() != null) {
				if (anT36.getReg().equals("ARI")) {
					vListReg = vListARI;
				} else {
					if (anT36.getReg().equals("API")) {
						vListReg = vListAPI;
					} else {
						if (anT36.getReg().equals("PIB")) {
							vListReg = vListPIB;
						} else {
							if (anT36.getReg().equals("OSO")) {
								vListReg = vListOSO;
							} else {
								if (anT36.getReg().equals("OTH")) {
									vListReg = vListOTH;
								} else {
									vListReg = vListARI;
								}
							}
						}
					}
				}
			}
		}
		
		if (vListReg != null){
			cmbReg.setValue(vListReg.getLabel());
			list_Reg.setSelectedItem(vListReg);
		}
		// Reg ================================================================= END
		
		// Tipe ============================================================= START
		Listitem vListTipe = null;

		Listitem vListF = list_Tipe.appendItem("Tetap/Rutin", "F");
		Listitem vListNF = list_Tipe.appendItem("Tidak Tetap", "NF");
		
		if (anT36 != null) {
			if (anT36.getTipe() != null) {
				if (anT36.getTipe().equals("F")) {
					vListTipe = vListF;
					
					rowDueDate.setVisible(false);
					rowBasis.setVisible(true);
					rowEvery.setVisible(true);
					rowBerlakuMulai.setVisible(true);
					rowBerlakuSd.setVisible(true);
					
					
				} else {
					vListTipe = vListNF;
					
					rowDueDate.setVisible(true);
					rowBasis.setVisible(false);
					rowEvery.setVisible(false);
					rowBerlakuMulai.setVisible(false);
					rowBerlakuSd.setVisible(false);
				}
			}
		}
		
		if (vListTipe != null){
			cmbTipe.setValue(vListTipe.getLabel());
			list_Tipe.setSelectedItem(vListTipe);
		}
		// Tipe =============================================================== END
		
		// Basis ============================================================= START
		Listitem vListBasis = null;
		Listitem vListX = list_Basis.appendItem("<<Please Select>>", "X");
		Listitem vListW = list_Basis.appendItem("Mingguan", "W");
		Listitem vListM = list_Basis.appendItem("Bulanan", "M");
		
		if (anT36 != null) {
			if (anT36.getBasis() != null) {
				if (anT36.getBasis().equals("W")) {
					vListBasis = vListW;
					
					lblHari.setVisible(true);
					cmbEvery.setVisible(true);
					
					lblTgl.setVisible(false);
					intEvery.setVisible(false);
					
				} else {
					if (anT36.getBasis().equals("M")) {
						vListBasis = vListM;				
	
						lblHari.setVisible(false);
						cmbEvery.setVisible(false);
						
						lblTgl.setVisible(true);
						intEvery.setVisible(true);
					} else {
						if (anT36.getBasis().equals("X")) {
							
							vListBasis = vListX;				
							
							lblHari.setVisible(false);
							cmbEvery.setVisible(false);
							
							lblTgl.setVisible(true);
							intEvery.setVisible(true);
							
						}
					}
				}
			}
		}
		
		if (vListBasis != null){
			cmbBasis.setValue(vListBasis.getLabel());
			list_Basis.setSelectedItem(vListBasis);
		}
		// Basis =============================================================== END
		
		// Every ============================================================= START
		Listitem vListEvery = null;

		Listitem vListNULL = list_Every.appendItem("<<Please Select>>", "0");
		Listitem vListSENIN = list_Every.appendItem("SENIN", "1");
		Listitem vListSELESA = list_Every.appendItem("SELESA", "2");
		Listitem vListRABU = list_Every.appendItem("RABU", "3");
		Listitem vListKAMIS = list_Every.appendItem("KAMIS", "4");
		Listitem vListJUMAT = list_Every.appendItem("JUMAT", "5");
		Listitem vListSABTU = list_Every.appendItem("SABTU", "6");
		Listitem vListMINGGU = list_Every.appendItem("MINGGU", "7");
		
		if (anT36 != null) {
			if (anT36.getEvery() != null) {
				if (anT36.getEvery() == 1) {
					vListEvery = vListSENIN;
				} else {
					if (anT36.getEvery() == 2) {
						vListEvery = vListSELESA;
					} else {
						if (anT36.getEvery() == 3) {
							vListEvery = vListRABU;
						} else {
							if (anT36.getEvery() == 4) {
								vListEvery = vListKAMIS;
							} else {
								if (anT36.getEvery() == 5) {
									vListEvery = vListJUMAT;
								} else {
									if (anT36.getEvery() == 6) {
										vListEvery = vListSABTU;
									} else {
										if (anT36.getEvery() == 7) {
											vListEvery = vListMINGGU;
										} else {
											vListEvery = vListNULL;
										}
									}
								}
							}
						}
					}
				}
			} else {
				vListEvery = vListNULL;
			}
		}
		
		if (vListEvery != null){
			cmbEvery.setValue(vListEvery.getLabel());
			list_Every.setSelectedItem(vListEvery);
		}
		// Basis =============================================================== END
	
	}
	
	public void onSelect$list_Company(Event event) throws Exception {
		T36OtherCf data = getT36OtherCf();

		data.setCompany(list_Company.getSelectedItem().getValue().toString());
		setT36OtherCf(data);
	}
	
	public void onSelect$list_Reg(Event event) throws Exception {
		T36OtherCf data = getT36OtherCf();

		data.setReg(list_Reg.getSelectedItem().getValue().toString());
		setT36OtherCf(data);
	}
	
	public void onSelect$list_Tipe(Event event) throws Exception {
		T36OtherCf data = getT36OtherCf();

		String vTipe = list_Tipe.getSelectedItem().getValue().toString();
		if (vTipe.equals("F") == true){
		
			rowDueDate.setVisible(false);
			rowBasis.setVisible(true);
			rowEvery.setVisible(true);
			rowBerlakuMulai.setVisible(true);
			rowBerlakuSd.setVisible(true);
		} else {
			rowDueDate.setVisible(true);
			rowBasis.setVisible(false);
			rowEvery.setVisible(false);
			rowBerlakuMulai.setVisible(false);
			rowBerlakuSd.setVisible(false);
		}
		
		
		data.setTipe(list_Tipe.getSelectedItem().getValue().toString());
		setT36OtherCf(data);
	}
	
	public void onSelect$list_Basis(Event event) throws Exception {
		T36OtherCf data = getT36OtherCf();
		
		String vBasis =  list_Basis.getSelectedItem().getValue().toString();
		if (vBasis.equals("W") == true){
			lblHari.setVisible(true);
			cmbEvery.setVisible(true);
			
			lblTgl.setVisible(false);
			intEvery.setVisible(false);
		} else {
			lblHari.setVisible(false);
			cmbEvery.setVisible(false);
			
			lblTgl.setVisible(true);
			intEvery.setVisible(true);
		}

		data.setBasis(list_Basis.getSelectedItem().getValue().toString());
		setT36OtherCf(data);
	}
	
	public void onSelect$list_Every(Event event) throws Exception {
		T36OtherCf data = getT36OtherCf();

		data.setEvery(Integer.valueOf(list_Every.getSelectedItem().getValue().toString()));
		setT36OtherCf(data);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T36OtherCfDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT36OtherCfDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){		
				
				cmbCompany.setDisabled(true);
				cmbReg.setDisabled(true);
				txtKeterangan.setReadonly(true);	
				dcbAmount.setReadonly(true);	
				cmbTipe.setDisabled(true);				
				dbDuedate.setDisabled(true);					
				cmbBasis.setDisabled(true);
				
				intEvery.setReadonly(true);	
				cmbEvery.setDisabled(true);
				
				dbFromDate.setDisabled(true);	
				dbUptoDate.setDisabled(true);	
				
				
				
			}
			
			if(pMode.equals("New")){
				cmbCompany.setDisabled(false);
				cmbReg.setDisabled(false);
				txtKeterangan.setReadonly(false);	
				dcbAmount.setReadonly(false);	
				cmbTipe.setDisabled(false);				
				dbDuedate.setDisabled(false);					
				cmbBasis.setDisabled(false);
				
				intEvery.setReadonly(false);	
				cmbEvery.setDisabled(false);
				
				dbFromDate.setDisabled(false);	
				dbUptoDate.setDisabled(false);	
				
				
				
				// set focus 
				txtKeterangan.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				cmbCompany.setDisabled(false);
				cmbReg.setDisabled(false);
				txtKeterangan.setReadonly(false);	
				dcbAmount.setReadonly(false);	
				cmbTipe.setDisabled(false);				
				dbDuedate.setDisabled(false);					
				cmbBasis.setDisabled(false);
				
				intEvery.setReadonly(false);	
				cmbEvery.setDisabled(false);
				
				dbFromDate.setDisabled(false);	
				dbUptoDate.setDisabled(false);	
				
				// set focus 
				txtKeterangan.setFocus(true);						
			}

		}
	}
	
	
	public String validasiBusinessRules(){
		
		if(CommonUtils.isNotEmpty(list_Company.getSelectedItem().getValue()) == false){
			return "Silahkan masukan Company";		
		}
		
		if(CommonUtils.isNotEmpty(list_Reg.getSelectedItem().getValue()) == false){
			return "Silahkan masukan Jenis Komponen";		
		}
		
		
		if(CommonUtils.isNotEmpty(txtKeterangan.getValue()) == false){
			return "Silahkan masukan Keterangan";			
		}
		
		if(CommonUtils.isNotEmpty(dcbAmount.getValue()) == false){
			return "Silahkan masukan Amount";			
		}
		
		if(CommonUtils.isNotEmpty(list_Tipe.getSelectedItem().getValue()) == false){
			return "Silahkan masukan Tipe";		
		} else {
			
			String vTipe = list_Tipe.getSelectedItem().getValue().toString();
			
			if (vTipe.equals("NF") == true){
				if(CommonUtils.isNotEmpty(dbDuedate.getValue()) == false){
					return "Silahkan masukan Due Date";			
				}
			} else {
				if(CommonUtils.isNotEmpty(list_Basis.getSelectedItem().getValue()) == false){
					return "Silahkan masukan Rutin Setiap";		
				} else {
					String vBasis = list_Basis.getSelectedItem().getValue().toString();
					
					if (vBasis.equals("X") == true){
						return "Silahkan masukan Rutin Setiap";	
					} else {
						if (vBasis.equals("W") == true){
							if(CommonUtils.isNotEmpty(list_Every.getSelectedItem().getValue()) == false){
								return "Silahkan masukan Hari";	
							} else {
								String vHari = list_Every.getSelectedItem().getValue().toString();
								if (vHari.equals("0") == true){
									return "Silahkan masukan Hari";	
								}
							}
						} else {
							if(CommonUtils.isNotEmpty(intEvery.getValue()) == false){
								return "Silahkan masukan Tanggal";			
							}
						}
					}
					
					if(CommonUtils.isNotEmpty(dbFromDate.getValue()) == false){
						return "Silahkan masukan Tgl. Mulai Berlaku";			
					}
					
					if(CommonUtils.isNotEmpty(dbUptoDate.getValue()) == false){
						return "Silahkan masukan Tgl. Berlaku s/d";			
					}
					
				}
			}
			
			
			
			
		}
		
		
		
		
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT36OtherCfMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T36OtherCf getT36OtherCf() {
		return getT36OtherCfMainCtrl().getSelectedT36OtherCf();
	}

	public void setT36OtherCf(T36OtherCf selectedT36OtherCf) {
		getT36OtherCfMainCtrl().setSelectedT36OtherCf(selectedT36OtherCf);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT36OtherCfMainCtrl(T36OtherCfMainCtrl T36OtherCfMainCtrl) {
		this.T36OtherCfMainCtrl = T36OtherCfMainCtrl;
	}

	public T36OtherCfMainCtrl getT36OtherCfMainCtrl() {
		return this.T36OtherCfMainCtrl;
	}
	
	
}
