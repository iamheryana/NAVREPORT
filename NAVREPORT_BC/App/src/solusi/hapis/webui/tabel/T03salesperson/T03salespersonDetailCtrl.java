package solusi.hapis.webui.tabel.T03salesperson;

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

import solusi.hapis.backend.tabel.model.T03salesperson;
import solusi.hapis.backend.tabel.model.T08targetsales;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.lov.T03salespersonLOV;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;

/**
 * User: Heryana
 * Date: 16-03-2018
 */

public class T03salespersonDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT03salespersonDetail;
	
	protected Borderlayout borderlayout_T03salespersonDetail;
	
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
	protected Listheader listheader_T03salespersonDetailList_Tahun;
	protected Listheader listheader_T03salespersonDetailList_Target;
	
	
	protected Paging paging_T03salespersonDetailList;
	private int startT03salespersonDetailList;
	private List<T08targetsales> list_T03salespersonDetailList = new ArrayList<T08targetsales>();
	private ListModelList modelList_T03salespersonDetailList = new ListModelList();
	protected Listbox listBoxT03salespersonDetail;
	private List<T08targetsales> list_DeletedT03salespersonDetailList = new ArrayList<T08targetsales>();

	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T03salespersonMainCtrl T03salespersonMainCtrl;

	/**
	 * default constructor.<br>
	 */
	public T03salespersonDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);
		
		paging_T03salespersonDetailList.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_T03salespersonDetailList.setDetailed(true);
		paging_T03salespersonDetailList.addEventListener("onPaging",
				onPaging_T03salespersonDetailList());
		listBoxT03salespersonDetail.setItemRenderer(renderTableDetail());



		if (arg.containsKey("ModuleMainController")) {
			setT03salespersonMainCtrl((T03salespersonMainCtrl) arg
					.get("ModuleMainController"));

			getT03salespersonMainCtrl().setT03salespersonDetailCtrl(this);
		}
		
		windowT03salespersonDetail.addEventListener(Events.ON_OK, onEnterForm());
	}

	private EventListener onPaging_T03salespersonDetailList() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startT03salespersonDetailList = pageNo
						* paging_T03salespersonDetailList.getPageSize();
				setModelT03salespersonDetailList();
			}
		};
	}


	private void setModelT03salespersonDetailList() {
		modelList_T03salespersonDetailList.clear();

		if (CommonUtils.isNotEmpty(list_T03salespersonDetailList)) {

			int endT03salespersonDetailList = 0;
			if (startT03salespersonDetailList + paging_T03salespersonDetailList.getPageSize() < list_T03salespersonDetailList
					.size()) {
				endT03salespersonDetailList = startT03salespersonDetailList
						+ paging_T03salespersonDetailList.getPageSize();
			} else {
				endT03salespersonDetailList = list_T03salespersonDetailList.size();
			}

			if (startT03salespersonDetailList > endT03salespersonDetailList) {
				startT03salespersonDetailList = 0;
				paging_T03salespersonDetailList.setActivePage(0);
			}

			modelList_T03salespersonDetailList.addAll(list_T03salespersonDetailList.subList(
					startT03salespersonDetailList, endT03salespersonDetailList));

			paging_T03salespersonDetailList.setDetailed(true);
			paging_T03salespersonDetailList
					.setTotalSize(list_T03salespersonDetailList.size());

			listBoxT03salespersonDetail.setModel(modelList_T03salespersonDetailList);
			listBoxT03salespersonDetail.setSelectedIndex(0);

			getT03salesperson().setT08targetsaless(new HashSet<T08targetsales>(list_T03salespersonDetailList));
		} else {
			paging_T03salespersonDetailList.setDetailed(false);
			listBoxT03salespersonDetail.setModel(modelList_T03salespersonDetailList);
			paging_T03salespersonDetailList.setTotalSize(0);
			getT03salesperson().setT08targetsaless(null);
		}

	}


	private ListitemRenderer renderTableDetail() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final T08targetsales rec = (T08targetsales) data;

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
				getT03salespersonMainCtrl().onClick$btnSave(event);
			}
		};
    }
    
	public void onCreate$windowT03salespersonDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		
		doFillListbox();
		
		binder.loadAll();
	}

	public void doFillListbox() {
		doFitSize();
		
		//Detail 
		listheader_T03salespersonDetailList_Tahun.setSortAscending(new T08targetsalesComparator(true, T08targetsalesComparator.COMPARE_BY_TAHUN));
		listheader_T03salespersonDetailList_Tahun.setSortDescending(new T08targetsalesComparator(false, T08targetsalesComparator.COMPARE_BY_TAHUN));        
        
		listheader_T03salespersonDetailList_Target.setSortAscending(new T08targetsalesComparator(true, T08targetsalesComparator.COMPARE_BY_TARGET));
		listheader_T03salespersonDetailList_Target.setSortDescending(new T08targetsalesComparator(false, T08targetsalesComparator.COMPARE_BY_TARGET));        
    

	}

	
	public void doFitSize() {
		windowT03salespersonDetail.invalidate();
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
		T03salesperson sales = T03salespersonLOV.show(windowT03salespersonDetail);

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
    

	public void displayDetail(List<T08targetsales> dataDetail) {
		list_DeletedT03salespersonDetailList.clear();
		list_T03salespersonDetailList.clear();

		if (CommonUtils.isNotEmpty(dataDetail)) {
			list_T03salespersonDetailList.addAll(dataDetail);
		}

		setModelT03salespersonDetailList();
	}

	public void onClick$btnNew(Event event) {
		T08targetsales newDetail = new T08targetsales();
		newDetail.setT03salesperson(getT03salesperson());
		newDetail.setTarget(new BigDecimal(0));

		T08targetsales newValue = T03salespersonAddDetailWindow.show(
				windowT03salespersonDetail, newDetail, getT03salesperson());

		if (newValue != null) {			
			list_T03salespersonDetailList.add(newValue);
			setModelT03salespersonDetailList();
			onClick$btnNew(event);
		}
	}
	
	public void onClick$btnEdit(Event event) {
		if (listBoxT03salespersonDetail.getSelectedItem() != null) {
			T08targetsales selectedItem = (T08targetsales) listBoxT03salespersonDetail
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T03salespersonDetailList.indexOf(selectedItem);

				T08targetsales editValue = T03salespersonAddDetailWindow.show(
						windowT03salespersonDetail, selectedItem, getT03salesperson());
				if (editValue != null) {
					list_T03salespersonDetailList.set(index, editValue);
					setModelT03salespersonDetailList();
				}
			}
		}
	}
	
	public void onClick$btnDelete(Event event) throws InterruptedException {
		if (listBoxT03salespersonDetail.getSelectedItem() != null) {
			final T08targetsales selectedItem = (T08targetsales) listBoxT03salespersonDetail
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
								if (selectedItem.getT08Id() > 0) {
									list_DeletedT03salespersonDetailList
											.add(selectedItem);
								}
								list_T03salespersonDetailList.remove(selectedItem);
								setModelT03salespersonDetailList();
							}
						}) == MultiLineMessageBox.YES) {
				}

			}
		}
	}
	
    public void onSort$listheader_T03salespersonDetailList_Tahun(Event event) {
    	sortingDataDetail(listheader_T03salespersonDetailList_Tahun, T08targetsalesComparator.COMPARE_BY_TAHUN);
    }

    public void onSort$listheader_T03salespersonDetailList_Target(Event event) {
    	sortingDataDetail(listheader_T03salespersonDetailList_Target, T08targetsalesComparator.COMPARE_BY_TARGET);
    }

    private void sortingDataDetail(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T03salespersonDetailList, new T08targetsalesComparator(false, sortBy));
    		} else {
    			Collections.sort(list_T03salespersonDetailList, new T08targetsalesComparator(true, sortBy));
    		}
    	}
    	
    	setModelT03salespersonDetailList();    
    }
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T03salesperson getT03salesperson() {
		return getT03salespersonMainCtrl().getSelectedT03salesperson();
	}

	public void setT03salesperson(T03salesperson selectedT03salesperson) {
		getT03salespersonMainCtrl().setSelectedT03salesperson(selectedT03salesperson);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT03salespersonMainCtrl(T03salespersonMainCtrl T03salespersonMainCtrl) {
		this.T03salespersonMainCtrl = T03salespersonMainCtrl;
	}

	public T03salespersonMainCtrl getT03salespersonMainCtrl() {
		return this.T03salespersonMainCtrl;
	}

	public List<T08targetsales> getList_DeletedT03salespersonDetailList() {
		return list_DeletedT03salespersonDetailList;
	}

	public void setList_DeletedT03salespersonDetailList(
			List<T08targetsales> list_DeletedT03salespersonDetailList) {
		this.list_DeletedT03salespersonDetailList = list_DeletedT03salespersonDetailList;
	}

	
	
}
