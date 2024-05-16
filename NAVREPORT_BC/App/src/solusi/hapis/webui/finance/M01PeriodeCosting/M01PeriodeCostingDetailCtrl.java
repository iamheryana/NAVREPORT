package solusi.hapis.webui.finance.M01PeriodeCosting;

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

import solusi.hapis.backend.navbi.model.M01PeriodeCosting;
import solusi.hapis.backend.parameter.service.SelectQueryNavReportService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

/**
 * User: Heryana
 * Date: 12-04-2018
 */

public class M01PeriodeCostingDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowM01PeriodeCostingDetail;
	
	protected Borderlayout borderlayout_M01PeriodeCostingDetail;
	
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
	private M01PeriodeCostingMainCtrl M01PeriodeCostingMainCtrl;

	
	/**
	 * default constructor.<br>
	 */
	public M01PeriodeCostingDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setM01PeriodeCostingMainCtrl((M01PeriodeCostingMainCtrl) arg
					.get("ModuleMainController"));

			getM01PeriodeCostingMainCtrl().setM01PeriodeCostingDetailCtrl(this);
		}
		
		windowM01PeriodeCostingDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowM01PeriodeCostingDetail(Event event) throws Exception {
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

		borderlayout_M01PeriodeCostingDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowM01PeriodeCostingDetail.invalidate();
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
		
		M01PeriodeCosting anT05 = getM01PeriodeCosting();

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
				
				if (getM01PeriodeCosting() != null) {
					if (getM01PeriodeCosting().getMasa() != null) {
						if (getM01PeriodeCosting().getMasa().equals(aRsltMasa[1].toString())) {
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
		M01PeriodeCosting data = getM01PeriodeCosting();
		data.setCloseKomisi(chbCloseKomisi.isChecked()?"Y":"N");
		
		txtCloseKomisi.setValue(data.getCloseKomisi());
		
		setM01PeriodeCosting(data);
	}
	
	public void onCheck$chbCloseTqs(Event event) throws Exception {
		M01PeriodeCosting data = getM01PeriodeCosting();
		data.setCloseTqs(chbCloseTqs.isChecked()?"Y":"N");
		
		txtCloseTqs.setValue(data.getCloseTqs());
		
		setM01PeriodeCosting(data);
	}
	
	public void onSelect$list_Masa(Event event) throws Exception {
		M01PeriodeCosting data = getM01PeriodeCosting();

		data.setMasa(list_Masa.getSelectedItem().getValue().toString());
		setM01PeriodeCosting(data);
		
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
				getM01PeriodeCostingMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public M01PeriodeCosting getM01PeriodeCosting() {
		return getM01PeriodeCostingMainCtrl().getSelectedM01PeriodeCosting();
	}

	public void setM01PeriodeCosting(M01PeriodeCosting selectedM01PeriodeCosting) {
		getM01PeriodeCostingMainCtrl().setSelectedM01PeriodeCosting(selectedM01PeriodeCosting);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setM01PeriodeCostingMainCtrl(M01PeriodeCostingMainCtrl M01PeriodeCostingMainCtrl) {
		this.M01PeriodeCostingMainCtrl = M01PeriodeCostingMainCtrl;
	}

	public M01PeriodeCostingMainCtrl getM01PeriodeCostingMainCtrl() {
		return this.M01PeriodeCostingMainCtrl;
	}


	
	
}
