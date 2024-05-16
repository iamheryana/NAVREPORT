package solusi.hapis.webui.finance.M02Salesperson;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.backend.navbi.model.M03Targetsales;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.M02SalespersonLOV;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;

/**
 * User: Heryana
 * Date: 14-04-2018
 */

public class M02SalespersonDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowM02SalespersonDetail;
	
	protected Borderlayout borderlayout_M02SalespersonDetail;
	
	// Data Entry Component

	protected Textbox txtSales;
	protected Textbox txtSalesName;	
	protected Textbox txtNIK;	
	protected Textbox txtSPV;
//	protected Decimalbox dcbTarget; 
	protected Datebox dbTglResign;
	
	
	// Button tabel Detail
	protected Button btnNew;
	protected Button btnEdit;
	protected Button btnDelete;
	
	// Paging and list Detail
	protected Listheader listheader_M02SalespersonDetailList_Tahun;
	protected Listheader listheader_M02SalespersonDetailList_Target;
	
	
	protected Paging paging_M02SalespersonDetailList;
	private int startM02SalespersonDetailList;
	private List<M03Targetsales> list_M02SalespersonDetailList = new ArrayList<M03Targetsales>();
	private ListModelList modelList_M02SalespersonDetailList = new ListModelList();
	protected Listbox listBoxM02SalespersonDetail;
	private List<M03Targetsales> list_DeletedM02SalespersonDetailList = new ArrayList<M03Targetsales>();

	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private M02SalespersonMainCtrl M02SalespersonMainCtrl;

	/**
	 * default constructor.<br>
	 */
	public M02SalespersonDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);
		
		paging_M02SalespersonDetailList.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_M02SalespersonDetailList.setDetailed(true);
		paging_M02SalespersonDetailList.addEventListener("onPaging",
				onPaging_M02SalespersonDetailList());
		listBoxM02SalespersonDetail.setItemRenderer(renderTableDetail());



		if (arg.containsKey("ModuleMainController")) {
			setM02SalespersonMainCtrl((M02SalespersonMainCtrl) arg
					.get("ModuleMainController"));

			getM02SalespersonMainCtrl().setM02SalespersonDetailCtrl(this);
		}
		
		windowM02SalespersonDetail.addEventListener(Events.ON_OK, onEnterForm());
	}

	private EventListener onPaging_M02SalespersonDetailList() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startM02SalespersonDetailList = pageNo
						* paging_M02SalespersonDetailList.getPageSize();
				setModelM02SalespersonDetailList();
			}
		};
	}


	private void setModelM02SalespersonDetailList() {
		modelList_M02SalespersonDetailList.clear();

		if (CommonUtils.isNotEmpty(list_M02SalespersonDetailList)) {

			int endM02SalespersonDetailList = 0;
			if (startM02SalespersonDetailList + paging_M02SalespersonDetailList.getPageSize() < list_M02SalespersonDetailList
					.size()) {
				endM02SalespersonDetailList = startM02SalespersonDetailList
						+ paging_M02SalespersonDetailList.getPageSize();
			} else {
				endM02SalespersonDetailList = list_M02SalespersonDetailList.size();
			}

			if (startM02SalespersonDetailList > endM02SalespersonDetailList) {
				startM02SalespersonDetailList = 0;
				paging_M02SalespersonDetailList.setActivePage(0);
			}

			modelList_M02SalespersonDetailList.addAll(list_M02SalespersonDetailList.subList(
					startM02SalespersonDetailList, endM02SalespersonDetailList));

			paging_M02SalespersonDetailList.setDetailed(true);
			paging_M02SalespersonDetailList
					.setTotalSize(list_M02SalespersonDetailList.size());

			listBoxM02SalespersonDetail.setModel(modelList_M02SalespersonDetailList);
			listBoxM02SalespersonDetail.setSelectedIndex(0);

			getM02Salesperson().setM03Targetsaless(new HashSet<M03Targetsales>(list_M02SalespersonDetailList));
		} else {
			paging_M02SalespersonDetailList.setDetailed(false);
			listBoxM02SalespersonDetail.setModel(modelList_M02SalespersonDetailList);
			paging_M02SalespersonDetailList.setTotalSize(0);
			getM02Salesperson().setM03Targetsaless(null);
		}

	}


	private ListitemRenderer renderTableDetail() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final M03Targetsales rec = (M03Targetsales) data;

				Listcell lc;

				lc = new Listcell(rec.getTahun());
				lc.setParent(item);

				lc = new Listcell(CommonUtils.formatNumberManual(rec.getTarget(),"#,###,###.###"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}
	
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getM02SalespersonMainCtrl().onClick$btnSave(event);
			}
		};
    }
    
	public void onCreate$windowM02SalespersonDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		
		doFillListbox();
		
		binder.loadAll();
	}

	public void doFillListbox() {
		doFitSize();
		
		//Detail 
		listheader_M02SalespersonDetailList_Tahun.setSortAscending(new M03TargetsalesComparator(true, M03TargetsalesComparator.COMPARE_BY_TAHUN));
		listheader_M02SalespersonDetailList_Tahun.setSortDescending(new M03TargetsalesComparator(false, M03TargetsalesComparator.COMPARE_BY_TAHUN));        
        
		listheader_M02SalespersonDetailList_Target.setSortAscending(new M03TargetsalesComparator(true, M03TargetsalesComparator.COMPARE_BY_TARGET));
		listheader_M02SalespersonDetailList_Target.setSortDescending(new M03TargetsalesComparator(false, M03TargetsalesComparator.COMPARE_BY_TARGET));        
    

	}

	
	public void doFitSize() {
		windowM02SalespersonDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
			
				txtSales.setReadonly(true);
				txtSalesName.setReadonly(true);
				txtNIK.setReadonly(true);
				txtSPV.setReadonly(true);
//				dcbTarget.setReadonly(true);
				dbTglResign.setDisabled(true);
				
				btnNew.setVisible(false);
				btnEdit.setVisible(false);
				btnDelete.setVisible(false);

			}
			
			if(pMode.equals("New")){
				txtSales.setReadonly(false);
				txtSalesName.setReadonly(false);
				txtNIK.setReadonly(false);
				txtSPV.setReadonly(false);
//				dcbTarget.setReadonly(false);
				dbTglResign.setDisabled(false);
				
				// set focus 
				txtSales.setFocus(true);	
				
				btnNew.setVisible(true);
				btnEdit.setVisible(true);
				btnDelete.setVisible(true);
			}
			
			if(pMode.equals("Edit")){				
				// set read only key
				txtSales.setReadonly(false);
				txtSalesName.setReadonly(false);
				txtNIK.setReadonly(false);
				txtSPV.setReadonly(false);
//				dcbTarget.setReadonly(false);
				dbTglResign.setDisabled(false);
				
				// set focus 
				txtSales.setFocus(true);	
				
				btnNew.setVisible(true);
				btnEdit.setVisible(true);
				btnDelete.setVisible(true);
			}
		}
	}

	
	public void onClick$btnSearchSalesLOV(Event event) {
		M02Salesperson sales = M02SalespersonLOV.show(windowM02SalespersonDetail);

		if (sales != null) {
			txtSPV.setValue(sales.getSales());
		
		} 
		
	}
	
	public String validasiBusinessRules(){
		
		if(CommonUtils.isNotEmpty(txtSales.getValue()) == false){
			return "Sales "+Labels.getLabel("message.error.mandatory");
		}	
		
		

		
		
		return null;
	}
    

	public void displayDetail(List<M03Targetsales> dataDetail) {
		list_DeletedM02SalespersonDetailList.clear();
		list_M02SalespersonDetailList.clear();

		if (CommonUtils.isNotEmpty(dataDetail)) {
			list_M02SalespersonDetailList.addAll(dataDetail);
		}

		setModelM02SalespersonDetailList();
	}

	public void onClick$btnNew(Event event) {
		M03Targetsales newDetail = new M03Targetsales();
		newDetail.setM02Salesperson(getM02Salesperson());
		newDetail.setTarget(new BigDecimal(0));

		M03Targetsales newValue = M02SalespersonAddDetailWindow.show(
				windowM02SalespersonDetail, newDetail, getM02Salesperson());

		if (newValue != null) {			
			list_M02SalespersonDetailList.add(newValue);
			setModelM02SalespersonDetailList();
			onClick$btnNew(event);
		}
	}
	
	public void onClick$btnEdit(Event event) {
		if (listBoxM02SalespersonDetail.getSelectedItem() != null) {
			M03Targetsales selectedItem = (M03Targetsales) listBoxM02SalespersonDetail
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_M02SalespersonDetailList.indexOf(selectedItem);

				M03Targetsales editValue = M02SalespersonAddDetailWindow.show(
						windowM02SalespersonDetail, selectedItem, getM02Salesperson());
				if (editValue != null) {
					list_M02SalespersonDetailList.set(index, editValue);
					setModelM02SalespersonDetailList();
				}
			}
		}
	}
	
	public void onClick$btnDelete(Event event) throws InterruptedException {
		if (listBoxM02SalespersonDetail.getSelectedItem() != null) {
			final M03Targetsales selectedItem = (M03Targetsales) listBoxM02SalespersonDetail
					.getSelectedItem().getValue();
			if (selectedItem != null) {

				// Show a confirm box
				String deleteRecord = selectedItem.getTahun();
				
				final String msg = Labels
						.getLabel("message.Question.Are_you_sure_to_delete_this_record")
						+ "\n\n --> " + deleteRecord;
				final String title = Labels.getLabel("message.Deleting.Record");

				MultiLineMessageBox.doSetTemplate();
				if (MultiLineMessageBox.show(msg, title, Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION, true,
						new EventListener() {
							@Override
							public void onEvent(Event evt) {
								switch (((Integer) evt.getData()).intValue()) {
								case MultiLineMessageBox.YES:
									try {
										deleteBean();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									break;
								case MultiLineMessageBox.NO:
									break;
								}
							}

							private void deleteBean()
									throws InterruptedException {
								if (selectedItem.getM03Id() > 0) {
									list_DeletedM02SalespersonDetailList
											.add(selectedItem);
								}
								list_M02SalespersonDetailList.remove(selectedItem);
								setModelM02SalespersonDetailList();
							}
						}) == MultiLineMessageBox.YES) {
				}

			}
		}
	}
	
    public void onSort$listheader_M02SalespersonDetailList_Tahun(Event event) {
    	sortingDataDetail(listheader_M02SalespersonDetailList_Tahun, M03TargetsalesComparator.COMPARE_BY_TAHUN);
    }

    public void onSort$listheader_M02SalespersonDetailList_Target(Event event) {
    	sortingDataDetail(listheader_M02SalespersonDetailList_Target, M03TargetsalesComparator.COMPARE_BY_TARGET);
    }

    private void sortingDataDetail(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_M02SalespersonDetailList, new M03TargetsalesComparator(false, sortBy));
    		} else {
    			Collections.sort(list_M02SalespersonDetailList, new M03TargetsalesComparator(true, sortBy));
    		}
    	}
    	
    	setModelM02SalespersonDetailList();    
    }
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public M02Salesperson getM02Salesperson() {
		return getM02SalespersonMainCtrl().getSelectedM02Salesperson();
	}

	public void setM02Salesperson(M02Salesperson selectedM02Salesperson) {
		getM02SalespersonMainCtrl().setSelectedM02Salesperson(selectedM02Salesperson);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setM02SalespersonMainCtrl(M02SalespersonMainCtrl M02SalespersonMainCtrl) {
		this.M02SalespersonMainCtrl = M02SalespersonMainCtrl;
	}

	public M02SalespersonMainCtrl getM02SalespersonMainCtrl() {
		return this.M02SalespersonMainCtrl;
	}

	public List<M03Targetsales> getList_DeletedM02SalespersonDetailList() {
		return list_DeletedM02SalespersonDetailList;
	}

	public void setList_DeletedM02SalespersonDetailList(
			List<M03Targetsales> list_DeletedM02SalespersonDetailList) {
		this.list_DeletedM02SalespersonDetailList = list_DeletedM02SalespersonDetailList;
	}

	
	
}
