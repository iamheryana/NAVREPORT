package solusi.hapis.webui.sales.SalesForecast.M06TargetPipeline;



import java.io.Serializable;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.M06TargetPipeline;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

/**
 * User: Heryana
 * Date: 09-02-2023
 */


public class M06TargetPipelineDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowM06TargetPipelineDetail;
	
	protected Borderlayout borderlayout_M06TargetPipelineDetail;
	
	// Data Entry Component

	protected Textbox txtTahun;
	protected Textbox txtSlsOrCab;
	
	
	protected Decimalbox dcbTargetAmount; 
	protected Decimalbox dcbTargetPsAmount;

	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private M06TargetPipelineMainCtrl M06TargetPipelineMainCtrl;

	
	protected Bandbox cmbJenis;
	protected Bandbox cmbStatus;
	
	
	protected Listbox list_Jenis;
	protected Listbox list_Status;
	
	
	/**
	 * default constructor.<br>
	 */
	public M06TargetPipelineDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setM06TargetPipelineMainCtrl((M06TargetPipelineMainCtrl) arg
					.get("ModuleMainController"));

			getM06TargetPipelineMainCtrl().setM06TargetPipelineDetailCtrl(this);
		}
		
		windowM06TargetPipelineDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowM06TargetPipelineDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doRenderCombo();
		doFitSize(event);
	}

	public void doRenderCombo() {
		
		ListBoxUtil.resetList(list_Jenis);
		ListBoxUtil.resetList(list_Status);
		
		M06TargetPipeline anM06 = getM06TargetPipeline();

		Listitem vListJenis= null;
		
		Listitem vList1 = list_Jenis.appendItem("Nasional - Semester 1", "S1");
		Listitem vList2 = list_Jenis.appendItem("Nasional - Semester 2", "S2");
		Listitem vList3 = list_Jenis.appendItem("Sales", "SALES");
			

		if (anM06 != null) {
			if (anM06.getJenis() != null) {
				if (anM06.getJenis().equals("S1")) {
					vListJenis = vList1;
				} else if (anM06.getJenis().equals("S2")) {
					vListJenis = vList2;
				} else if (anM06.getJenis().equals("SALES")) {
					vListJenis = vList3;
				} 
			}
		}
		
		if(vListJenis != null){
			cmbJenis.setValue(vListJenis.getLabel());
			list_Jenis.setSelectedItem(vListJenis);
		} else {
			cmbJenis.setValue("");
		}
	
	
		Listitem vListStatus = null;
		
		Listitem vListActive = list_Status.appendItem("Active", "ACTIVE");
		Listitem vListNonActive = list_Status.appendItem("Non Active", "NON ACTIVE");
				

		if (anM06 != null) {
			if (anM06.getStatus() != null) {
				if (anM06.getStatus().equals("ACTIVE")) {
					vListStatus = vListActive;
				} else if (anM06.getStatus().equals("NON ACTIVE")) {
					vListStatus = vListNonActive;
				} 
			}
		}
		
		if(vListStatus != null){
			cmbStatus.setValue(vListStatus.getLabel());
			list_Status.setSelectedItem(vListStatus);
		} else {
			cmbStatus.setValue("");
		}
	
		

	}

	
	public void onSelect$list_Jenis(Event event) throws Exception {
		M06TargetPipeline data = getM06TargetPipeline();

		data.setJenis(list_Jenis.getSelectedItem().getValue().toString());
		setM06TargetPipeline(data);
		
		//doRenderLayout();
	}
	
	public void onSelect$list_Status(Event event) throws Exception {
		M06TargetPipeline data = getM06TargetPipeline();

		data.setStatus(list_Status.getSelectedItem().getValue().toString());
		setM06TargetPipeline(data);
	}
	
	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_M06TargetPipelineDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowM06TargetPipelineDetail.invalidate();
	}

	

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
		
				cmbJenis.setDisabled(true);
				cmbStatus.setDisabled(true);
				
				
				txtTahun.setReadonly(true);								
				txtSlsOrCab.setReadonly(true);

				dcbTargetAmount.setReadonly(true);
				dcbTargetPsAmount.setReadonly(true);

			}
			
			if(pMode.equals("New")){
				
				cmbJenis.setDisabled(false);
				cmbStatus.setDisabled(false);
				
				
				txtTahun.setReadonly(false);					
				txtSlsOrCab.setReadonly(false);
	
				
				dcbTargetAmount.setReadonly(false);
				dcbTargetPsAmount.setReadonly(false);
				
				
				// set focus 
				txtTahun.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				// set read only key
				cmbJenis.setDisabled(false);
				cmbStatus.setDisabled(false);
				
				txtTahun.setReadonly(false);				
				txtSlsOrCab.setReadonly(false);

				
				dcbTargetAmount.setReadonly(false);
				dcbTargetPsAmount.setReadonly(false);
				
				// set focus 
				txtTahun.setFocus(true);					
			}
		}
	}

	
	
	public String validasiBusinessRules(){
		

		
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == false){
			return "Tahun "+Labels.getLabel("message.error.mandatory");
		}	
		
		if(CommonUtils.isNotEmpty(cmbJenis.getValue()) == false){
			return "Jenis Target "+Labels.getLabel("message.error.mandatory");
		}	
		
		if(CommonUtils.isNotEmpty(txtSlsOrCab.getValue()) == false){
			return "Sales / Cabang "+Labels.getLabel("message.error.mandatory");
		}	
		
		if(CommonUtils.isNotEmpty(cmbStatus.getValue()) == false){
			return "Status "+Labels.getLabel("message.error.mandatory");
		}	
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getM06TargetPipelineMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public M06TargetPipeline getM06TargetPipeline() {
		return getM06TargetPipelineMainCtrl().getSelectedM06TargetPipeline();
	}

	public void setM06TargetPipeline(M06TargetPipeline selectedM06TargetPipeline) {
		getM06TargetPipelineMainCtrl().setSelectedM06TargetPipeline(selectedM06TargetPipeline);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setM06TargetPipelineMainCtrl(M06TargetPipelineMainCtrl M06TargetPipelineMainCtrl) {
		this.M06TargetPipelineMainCtrl = M06TargetPipelineMainCtrl;
	}

	public M06TargetPipelineMainCtrl getM06TargetPipelineMainCtrl() {
		return this.M06TargetPipelineMainCtrl;
	}


	
	
}
