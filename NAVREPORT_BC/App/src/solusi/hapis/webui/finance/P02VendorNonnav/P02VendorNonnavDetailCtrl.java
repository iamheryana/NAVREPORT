package solusi.hapis.webui.finance.P02VendorNonnav;


import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.P02VendorNonnav;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 30-01-2020
 */

public class P02VendorNonnavDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowP02VendorNonnavDetail;
	
	protected Borderlayout borderlayout_P02VendorNonnavDetail;
	
	// Data Entry Component
	

	
	protected Textbox txtKodeVendorNonnav;
	protected Textbox txtKodeValuta;
	
	protected Textbox txtNoRekPenerima;
	protected Textbox txtNamaPenerima;
	protected Textbox txtAlamatPenerima1;
	protected Textbox txtAlamatPenerima2;
	protected Textbox txtCountryPenerima;
	
	protected Textbox txtNamaBank;
	protected Textbox txtAlamatBank1;
	protected Textbox txtAlamatBank2;
	protected Textbox txtCountryBank;
	protected Textbox txtSwiftCode;
	

	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private P02VendorNonnavMainCtrl P02VendorNonnavMainCtrl;

	
//	private SelectQueryNavReportService selectQueryNavReportService;
	
	/**
	 * default constructor.<br>
	 */
	public P02VendorNonnavDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setP02VendorNonnavMainCtrl((P02VendorNonnavMainCtrl) arg
					.get("ModuleMainController"));

			getP02VendorNonnavMainCtrl().setP02VendorNonnavDetailCtrl(this);
		}
		
		windowP02VendorNonnavDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowP02VendorNonnavDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doRenderCombo();
		doFitSize(event);
	}


	
	public void doRenderCombo() {
		
		

	}
	
	
	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_P02VendorNonnavDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowP02VendorNonnavDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
				
				txtKodeVendorNonnav.setReadonly(true);	
				txtKodeValuta.setReadonly(true);	
				txtNoRekPenerima.setReadonly(true);	
				txtNamaPenerima.setReadonly(true);	
				txtAlamatPenerima1.setReadonly(true);	
				txtAlamatPenerima2.setReadonly(true);	
				txtCountryPenerima.setReadonly(true);	
				txtNamaBank.setReadonly(true);	
				txtAlamatBank1.setReadonly(true);	
				txtAlamatBank2.setReadonly(true);	
				txtCountryBank.setReadonly(true);	
				txtSwiftCode.setReadonly(true);
				
				
			}
			
			if(pMode.equals("New")){
			
				
				txtKodeVendorNonnav.setReadonly(false);	
				txtKodeValuta.setReadonly(false);	
				txtNoRekPenerima.setReadonly(false);	
				txtNamaPenerima.setReadonly(false);	
				txtAlamatPenerima1.setReadonly(false);	
				txtAlamatPenerima2.setReadonly(false);	
				txtCountryPenerima.setReadonly(false);	
				txtNamaBank.setReadonly(false);	
				txtAlamatBank1.setReadonly(false);	
				txtAlamatBank2.setReadonly(false);	
				txtCountryBank.setReadonly(false);	
				txtSwiftCode.setReadonly(false);
				
				// set focus 
				txtKodeVendorNonnav.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				
				
				txtKodeVendorNonnav.setReadonly(true);	
				txtKodeValuta.setReadonly(false);	
				txtNoRekPenerima.setReadonly(false);	
				txtNamaPenerima.setReadonly(false);	
				txtAlamatPenerima1.setReadonly(false);	
				txtAlamatPenerima2.setReadonly(false);	
				txtCountryPenerima.setReadonly(false);	
				txtNamaBank.setReadonly(false);	
				txtAlamatBank1.setReadonly(false);	
				txtAlamatBank2.setReadonly(false);	
				txtCountryBank.setReadonly(false);	
				txtSwiftCode.setReadonly(false);
				
				// set focus 
				txtKodeValuta.setFocus(true);							
			}

		}
	}
	

	
	public String validasiBusinessRules(){
		//System.out.println("Right here : ---> "+txtKodeVendorNonnav.getValue());
		if(CommonUtils.isNotEmpty(txtKodeVendorNonnav.getValue()) == false){
			
			return "Silahkan masukan Kode Vendor Non NAV";			
		}
		
		if(CommonUtils.isNotEmpty(txtKodeValuta.getValue()) == false){
			return "Silahkan masukan Kode Valuta Transaksi";			
		}
		
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getP02VendorNonnavMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public P02VendorNonnav getP02VendorNonnav() {
		return getP02VendorNonnavMainCtrl().getSelectedP02VendorNonnav();
	}

	public void setP02VendorNonnav(P02VendorNonnav selectedP02VendorNonnav) {
		getP02VendorNonnavMainCtrl().setSelectedP02VendorNonnav(selectedP02VendorNonnav);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setP02VendorNonnavMainCtrl(P02VendorNonnavMainCtrl P02VendorNonnavMainCtrl) {
		this.P02VendorNonnavMainCtrl = P02VendorNonnavMainCtrl;
	}

	public P02VendorNonnavMainCtrl getP02VendorNonnavMainCtrl() {
		return this.P02VendorNonnavMainCtrl;
	}
	
	
}
