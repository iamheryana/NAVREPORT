package solusi.hapis.webui.logistic.T01SoAdj;

import java.io.Serializable;
import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T01SoAdj;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

/**
 * User: Heryana
 * Date: 28-08-2019
 */

public class T01SoAdjDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT01SoAdjDetail;
	
	protected Borderlayout borderlayout_T01SoAdjDetail;
	
	// Data Entry Component
	

	protected Textbox txtNoSo;	
	protected Bandbox cmbJenisPayment;
	
	protected Checkbox chbUseQty;
	protected Textbox txtUseQty;
	
	protected Decimalbox intQty;	
	protected Datebox dbEstRealisasi;
	
	protected Checkbox chbUseCCLDate;
	protected Intbox intAddDays;	
	protected Textbox txtUseCCLDate;
	
	protected Textbox txtKeteranganDp;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T01SoAdjMainCtrl T01SoAdjMainCtrl;

	protected Listbox list_JenisPayment;
	

//	private SelectQueryNavReportService selectQueryNavReportService;
	
	/**
	 * default constructor.<br>
	 */
	public T01SoAdjDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT01SoAdjMainCtrl((T01SoAdjMainCtrl) arg
					.get("ModuleMainController"));

			getT01SoAdjMainCtrl().setT01SoAdjDetailCtrl(this);
		}
		
		windowT01SoAdjDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT01SoAdjDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doRenderCombo();
		doFitSize(event);
	}

	public void doRenderCombo() {
		ListBoxUtil.resetList(list_JenisPayment);
		
		T01SoAdj anT01 = getT01SoAdj();

		Listitem vListJenisPayment = null;
		
		Listitem vListW = list_JenisPayment.appendItem("Installment (Monthly)", "M");
		Listitem vListQ = list_JenisPayment.appendItem("Installment (Quarterly)", "Q");
		Listitem vListS = list_JenisPayment.appendItem("Installment (Semesterly)", "S");
		Listitem vListY = list_JenisPayment.appendItem("Installment (Yearly)", "Y");
		Listitem vListD = list_JenisPayment.appendItem("Down Payment", "D");
			

		if (anT01 != null) {
			if (anT01.getJenisPayment() != null) {
				if (anT01.getJenisPayment().equals("M")) {
					vListJenisPayment = vListW;
				} else if (anT01.getJenisPayment().equals("Q")) {
					vListJenisPayment = vListQ;
				} else if (anT01.getJenisPayment().equals("S")) {
					vListJenisPayment = vListS;
				} else if (anT01.getJenisPayment().equals("Y")) {
					vListJenisPayment = vListY;
				} else if (anT01.getJenisPayment().equals("D")) {
					vListJenisPayment = vListD;
				}
			}
		}
		
		if(vListJenisPayment != null){
			cmbJenisPayment.setValue(vListJenisPayment.getLabel());
			list_JenisPayment.setSelectedItem(vListJenisPayment);
		} else {
			cmbJenisPayment.setValue("");
		}
		
		
		//
		if (anT01 != null) {
			if(CommonUtils.isNotEmpty(anT01.getUseCclDate()) == true){
				if(anT01.getUseCclDate().equals("Y") == true)
					chbUseCCLDate.setChecked(true);
				else
					chbUseCCLDate.setChecked(false);
			} else {
				chbUseCCLDate.setChecked(false);
			}
			
			
			if(CommonUtils.isNotEmpty(anT01.getUseQty()) == true){
				if(anT01.getUseQty().equals("Y") == true)
					chbUseQty.setChecked(true);
				else
					chbUseQty.setChecked(false);
			} else {
				chbUseQty.setChecked(false);
			}
		}
	}
	
	
	public void onSelect$list_JenisPayment(Event event) throws Exception {
		T01SoAdj data = getT01SoAdj();

		data.setJenisPayment(list_JenisPayment.getSelectedItem().getValue().toString());
		setT01SoAdj(data);
	}
	
	
	public void onCheck$chbUseCCLDate(Event event) throws Exception {
		T01SoAdj data = getT01SoAdj();
		data.setUseCclDate(chbUseCCLDate.isChecked()?"Y":"N");
		
		txtUseCCLDate.setValue(data.getUseCclDate());
		
		setT01SoAdj(data);
	}
	
	
	public void onCheck$chbUseQty(Event event) throws Exception {
		T01SoAdj data = getT01SoAdj();
		data.setUseQty(chbUseQty.isChecked()?"Y":"N");
		if(chbUseQty.isChecked() == true){
			
			
			intQty.setReadonly(false);
		} else {
			data.setQty(new BigDecimal(0));
			intQty.setValue(data.getQty());
			intQty.setReadonly(true);
		}

		txtUseQty.setValue(data.getUseQty());
		
		setT01SoAdj(data);
		
		
	}
	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T01SoAdjDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT01SoAdjDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				

				cmbJenisPayment.setDisabled(true);
					
				txtNoSo.setReadonly(true);	
				dbEstRealisasi.setDisabled(true);				
				
				chbUseQty.setDisabled(true);
				intQty.setReadonly(true);		
				
				chbUseCCLDate.setDisabled(true);
				
				intAddDays.setReadonly(true);	
				txtKeteranganDp.setReadonly(true);	
			}
			
			if(pMode.equals("New")){
				cmbJenisPayment.setDisabled(false);
				
				txtNoSo.setReadonly(false);	
				dbEstRealisasi.setDisabled(false);	
				
				chbUseQty.setDisabled(false);
				intQty.setReadonly(false);		
				
				chbUseCCLDate.setDisabled(false);
				
				intAddDays.setReadonly(false);	
				
				txtKeteranganDp.setReadonly(false);	
				
				// set focus 
				txtNoSo.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				cmbJenisPayment.setDisabled(false);
				
				txtNoSo.setReadonly(false);	
				dbEstRealisasi.setDisabled(false);	
				
				chbUseQty.setDisabled(false);
				
				intQty.setReadonly(false);		
				
				chbUseCCLDate.setDisabled(false);
				
				intAddDays.setReadonly(false);	
				
				txtKeteranganDp.setReadonly(false);	
				
				// set focus 
				txtNoSo.setFocus(true);					
			}

		}
	}
	

	
	public String validasiBusinessRules(){
		if(txtNoSo.getValue() == null){
			return "Silahkan masukan No. Sales Order (SO).";			
		}
		
		if(	(CommonUtils.isNotEmpty(list_JenisPayment.getSelectedItem()) == true) ){
			String vJnsPayment = list_JenisPayment.getSelectedItem().getValue().toString();
			
			if(vJnsPayment.equals("D") == true){
				if (chbUseCCLDate.isChecked() == true){
					if (intAddDays.getValue() == null){
						return "Untuk Jenis Payment : Down Payment Dan Ingin Menggunakan Tgl. CCL Sebagai Acuan, Silahkan tentukan ingin ditambah berapa hari dari Tgl CCL untuk menentukan Tanggal Estimasi Realisasi Invoice / Penagihan nya.";
					}					
				} else {
					if (dbEstRealisasi.getValue() == null){
						return "Untuk Jenis Payment : Down Payment, silahkan tentukan Tanggal Estimasi Realisasi Invoice / Penagihan nya.";
					}
				}
				
				if (chbUseQty.isChecked() == true){
					if(intQty.getValue() == null){
						return "Silahkan masukan Persentase Down Payment.";
					}
				}
				
			} else {
				if (chbUseQty.isChecked() == false){
					return "Selain Jenis Payment : Down Payment, tidak boleh di Unchecked / Un-Centang.";
				}
				
				
				if(intQty.getValue() == null){
					return "Silahkan masukan Jumlah Installment.";
				}				
			}
		} else {
			return "Silahkan pilih Jenis Pembayaran.";
		}
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT01SoAdjMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T01SoAdj getT01SoAdj() {
		return getT01SoAdjMainCtrl().getSelectedT01SoAdj();
	}

	public void setT01SoAdj(T01SoAdj selectedT01SoAdj) {
		getT01SoAdjMainCtrl().setSelectedT01SoAdj(selectedT01SoAdj);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT01SoAdjMainCtrl(T01SoAdjMainCtrl T01SoAdjMainCtrl) {
		this.T01SoAdjMainCtrl = T01SoAdjMainCtrl;
	}

	public T01SoAdjMainCtrl getT01SoAdjMainCtrl() {
		return this.T01SoAdjMainCtrl;
	}
	
	
}
