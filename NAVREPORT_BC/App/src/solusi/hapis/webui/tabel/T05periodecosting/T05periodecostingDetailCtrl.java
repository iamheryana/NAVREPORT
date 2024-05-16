package solusi.hapis.webui.tabel.T05periodecosting;

import java.io.Serializable;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.parameter.service.SelectQueryNavReportService;
import solusi.hapis.backend.tabel.model.T05periodecosting;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

/**
 * User: Heryana
 * Date: 25-05-2018
 */

public class T05periodecostingDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT05periodecostingDetail;
	
	protected Borderlayout borderlayout_T05periodecostingDetail;
	
	// Data Entry Component

	protected Bandbox cmbMasa;
	
	protected Textbox txtTahun;
	
	protected Textbox txtCloseKomisi;
	protected Checkbox chbCloseKomisi;	
		
	protected Textbox txtCloseTqs;
	protected Checkbox chbCloseTqs;	
	

	protected Listbox list_Masa;
	private SelectQueryNavReportService selectQueryNavReportService;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T05periodecostingMainCtrl T05periodecostingMainCtrl;

	
	/**
	 * default constructor.<br>
	 */
	public T05periodecostingDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT05periodecostingMainCtrl((T05periodecostingMainCtrl) arg
					.get("ModuleMainController"));

			getT05periodecostingMainCtrl().setT05periodecostingDetailCtrl(this);
		}
		
		windowT05periodecostingDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT05periodecostingDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doRenderCombo();
		doFitSize(event);
	}

	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T05periodecostingDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT05periodecostingDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
				cmbMasa.setDisabled(true);
				txtTahun.setReadonly(true);
				chbCloseKomisi.setDisabled(true);
				chbCloseTqs.setDisabled(true);
				

			}
			
			if(pMode.equals("New")){
				
				cmbMasa.setDisabled(false);
				txtTahun.setReadonly(false);
				chbCloseKomisi.setDisabled(false);
				chbCloseTqs.setDisabled(false);
				
				
							
				// set focus 
				cmbMasa.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				// set read only key

				cmbMasa.setDisabled(false);
				txtTahun.setReadonly(false);
				chbCloseKomisi.setDisabled(false);
				chbCloseTqs.setDisabled(false);
				
				// set focus 
				cmbMasa.setFocus(true);							
			}
		}
	}

	public void doRenderCombo() {
		
		T05periodecosting anT05 = getT05periodecosting();

		if (anT05 != null) {
			if(CommonUtils.isNotEmpty(anT05.getCloseKomisi()) == true){
				if(anT05.getCloseKomisi().equals("Y") == true)
					chbCloseKomisi.setChecked(true);
				else
					chbCloseKomisi.setChecked(false);
			} else {
				chbCloseKomisi.setChecked(false);
			}
			
			
			if(CommonUtils.isNotEmpty(anT05.getCloseTqs()) == true){
				if(anT05.getCloseTqs().equals("Y") == true)
					chbCloseTqs.setChecked(true);
				else
					chbCloseTqs.setChecked(false);
			} else {
				chbCloseTqs.setChecked(false);
			}
			
			
		}

			
		//Render Combo Masa 
		ListBoxUtil.resetList(list_Masa);

		Listitem vListMasa = null;

		int aMK = 0;
		List<Object[]> vResultMasa = selectQueryNavReportService.QueryBulan();
		if(CommonUtils.isNotEmpty(vResultMasa)){
			for(Object[] aRsltMasa : vResultMasa){
				
				Listitem vListMK = list_Masa.appendItem(aRsltMasa[0].toString(),aRsltMasa[1].toString());
				if (aMK == 0) {
					vListMasa = vListMK;
					aMK++;
				}
				
				if (getT05periodecosting() != null) {
					if (getT05periodecosting().getMasa() != null) {
						if (getT05periodecosting().getMasa().equals(aRsltMasa[1].toString())) {
							vListMasa = vListMK;
						}
					}
				}
			}
		}
		
		cmbMasa.setValue(vListMasa.getLabel());
		list_Masa.setSelectedItem(vListMasa);
	}

	public void onCheck$chbCloseKomisi(Event event) throws Exception {
		T05periodecosting data = getT05periodecosting();
		data.setCloseKomisi(chbCloseKomisi.isChecked()?"Y":"N");
		
		txtCloseKomisi.setValue(data.getCloseKomisi());
		
		setT05periodecosting(data);
	}
	
	public void onCheck$chbCloseTqs(Event event) throws Exception {
		T05periodecosting data = getT05periodecosting();
		data.setCloseTqs(chbCloseTqs.isChecked()?"Y":"N");
		
		txtCloseTqs.setValue(data.getCloseTqs());
		
		setT05periodecosting(data);
	}
	
	public void onSelect$list_Masa(Event event) throws Exception {
		T05periodecosting data = getT05periodecosting();

		data.setMasa(list_Masa.getSelectedItem().getValue().toString());
		setT05periodecosting(data);
		
	}
	
	
	public String validasiBusinessRules(){
			

		if(CommonUtils.isNotEmpty(cmbMasa.getValue()) == true){
			if(cmbMasa.getValue().equals("<blank>") == true){
				return "Masa "+Labels.getLabel("message.error.mandatory");
				
			}
		} else {
			return "Masa "+Labels.getLabel("message.error.mandatory");
			
		}
		
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == false){
			return "Tahun "+Labels.getLabel("message.error.mandatory");
		}	
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT05periodecostingMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T05periodecosting getT05periodecosting() {
		return getT05periodecostingMainCtrl().getSelectedT05periodecosting();
	}

	public void setT05periodecosting(T05periodecosting selectedT05periodecosting) {
		getT05periodecostingMainCtrl().setSelectedT05periodecosting(selectedT05periodecosting);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT05periodecostingMainCtrl(T05periodecostingMainCtrl T05periodecostingMainCtrl) {
		this.T05periodecostingMainCtrl = T05periodecostingMainCtrl;
	}

	public T05periodecostingMainCtrl getT05periodecostingMainCtrl() {
		return this.T05periodecostingMainCtrl;
	}


	
	
}
