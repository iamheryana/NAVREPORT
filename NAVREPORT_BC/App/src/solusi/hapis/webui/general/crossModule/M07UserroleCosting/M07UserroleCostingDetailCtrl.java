package solusi.hapis.webui.general.crossModule.M07UserroleCosting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
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

import solusi.hapis.backend.navbi.model.M07UserroleCostingH;
import solusi.hapis.backend.navbi.model.M08UserroleCostingD;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;
import solusi.hapis.webui.util.MultiLineMessageBox;

/**
 * User: Heryana
 * Date: 17-01-2025
 */

public class M07UserroleCostingDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowM07UserroleCostingDetail;
	
	protected Borderlayout borderlayout_M07UserroleCostingDetail;
	
	// Data Entry Component

	protected Textbox txtUsername;
	protected Bandbox cmbRolename;
	protected Textbox txtFilteruser;	
	
	
	protected Listbox list_Userrole;
	
	// Button tabel Detail
	protected Button btnNew;
	protected Button btnEdit;
	protected Button btnDelete;
	
	// Paging and list Detail
	protected Listheader listheader_M07UserroleCostingDetailList_Filteruser;
	
	
	protected Paging paging_M07UserroleCostingDetailList;
	private int startM07UserroleCostingDetailList;
	private List<M08UserroleCostingD> list_M07UserroleCostingDetailList = new ArrayList<M08UserroleCostingD>();
	private ListModelList modelList_M07UserroleCostingDetailList = new ListModelList();
	protected Listbox listBoxM07UserroleCostingDetail;
	private List<M08UserroleCostingD> list_DeletedM07UserroleCostingDetailList = new ArrayList<M08UserroleCostingD>();

	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private M07UserroleCostingMainCtrl M07UserroleCostingMainCtrl;

	/**
	 * default constructor.<br>
	 */
	public M07UserroleCostingDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);
		
		paging_M07UserroleCostingDetailList.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_M07UserroleCostingDetailList.setDetailed(true);
		paging_M07UserroleCostingDetailList.addEventListener("onPaging",
				onPaging_M07UserroleCostingDetailList());
		listBoxM07UserroleCostingDetail.setItemRenderer(renderTableDetail());



		if (arg.containsKey("ModuleMainController")) {
			setM07UserroleCostingMainCtrl((M07UserroleCostingMainCtrl) arg
					.get("ModuleMainController"));

			getM07UserroleCostingMainCtrl().setM07UserroleCostingDetailCtrl(this);
		}
		
		windowM07UserroleCostingDetail.addEventListener(Events.ON_OK, onEnterForm());
	}

	private EventListener onPaging_M07UserroleCostingDetailList() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startM07UserroleCostingDetailList = pageNo
						* paging_M07UserroleCostingDetailList.getPageSize();
				setModelM07UserroleCostingDetailList();
			}
		};
	}


	private void setModelM07UserroleCostingDetailList() {
		modelList_M07UserroleCostingDetailList.clear();

		if (CommonUtils.isNotEmpty(list_M07UserroleCostingDetailList)) {

			int endM07UserroleCostingDetailList = 0;
			if (startM07UserroleCostingDetailList + paging_M07UserroleCostingDetailList.getPageSize() < list_M07UserroleCostingDetailList
					.size()) {
				endM07UserroleCostingDetailList = startM07UserroleCostingDetailList
						+ paging_M07UserroleCostingDetailList.getPageSize();
			} else {
				endM07UserroleCostingDetailList = list_M07UserroleCostingDetailList.size();
			}

			if (startM07UserroleCostingDetailList > endM07UserroleCostingDetailList) {
				startM07UserroleCostingDetailList = 0;
				paging_M07UserroleCostingDetailList.setActivePage(0);
			}

			modelList_M07UserroleCostingDetailList.addAll(list_M07UserroleCostingDetailList.subList(
					startM07UserroleCostingDetailList, endM07UserroleCostingDetailList));

			paging_M07UserroleCostingDetailList.setDetailed(true);
			paging_M07UserroleCostingDetailList
					.setTotalSize(list_M07UserroleCostingDetailList.size());

			listBoxM07UserroleCostingDetail.setModel(modelList_M07UserroleCostingDetailList);
			listBoxM07UserroleCostingDetail.setSelectedIndex(0);

			getM07UserroleCostingH().setM08UserroleCostingDs(new HashSet<M08UserroleCostingD>(list_M07UserroleCostingDetailList));
		} else {
			paging_M07UserroleCostingDetailList.setDetailed(false);
			listBoxM07UserroleCostingDetail.setModel(modelList_M07UserroleCostingDetailList);
			paging_M07UserroleCostingDetailList.setTotalSize(0);
			getM07UserroleCostingH().setM08UserroleCostingDs(null);
		}

	}


	private ListitemRenderer renderTableDetail() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final M08UserroleCostingD rec = (M08UserroleCostingD) data;

				Listcell lc;

				lc = new Listcell(rec.getFilteruser());
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
				getM07UserroleCostingMainCtrl().onClick$btnSave(event);
			}
		};
    }
    
	public void onCreate$windowM07UserroleCostingDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		
		doFillListbox();
		doRenderCombo();
		doFitSize(event);
		binder.loadAll();
	}

	public void doFillListbox() {
		//Detail 
		listheader_M07UserroleCostingDetailList_Filteruser.setSortAscending(new M08UserroleCostingDComparator(true, M08UserroleCostingDComparator.COMPARE_BY_FILTERUSER));
		listheader_M07UserroleCostingDetailList_Filteruser.setSortDescending(new M08UserroleCostingDComparator(false, M08UserroleCostingDComparator.COMPARE_BY_FILTERUSER));        

	}
	
	public void doRenderCombo() {
		
		ListBoxUtil.resetList(list_Userrole);
		

		Listitem vListUserrole = null;

		Listitem vListBLANK = list_Userrole.appendItem("<<Please Select>>", "");
		Listitem vListSALES = list_Userrole.appendItem("SALES", "SALES");
		Listitem vListSAO = list_Userrole.appendItem("SAO", "SAO");
		Listitem vListLOGISTIC = list_Userrole.appendItem("LOGISTIC", "LOGISTIC");
		Listitem vListFINANCE = list_Userrole.appendItem("FINANCE", "FINANCE");
		Listitem vListSM = list_Userrole.appendItem("SM", "SM");

		M07UserroleCostingH anM07 = getM07UserroleCostingH();

		
		
		if (anM07 != null) {
			if (anM07.getRolename() != null) {
				if (anM07.getRolename().equals("SALES")) {
					vListUserrole = vListSALES;
				} else {
					if (anM07.getRolename().equals("SAO")) {
						vListUserrole = vListSAO;
					} else {
						if (anM07.getRolename().equals("LOGISTIC")) {
							vListUserrole = vListLOGISTIC;
						} else {
							if (anM07.getRolename().equals("FINANCE")) {
								vListUserrole = vListFINANCE;
							} else {
								if (anM07.getRolename().equals("SM")) {
									vListUserrole = vListSM;
								} else {
									vListUserrole = vListBLANK;
								}
							}
						}
					}
				}
			} else {
				vListUserrole = vListBLANK;
			}
		}
		
		
		cmbRolename.setValue(vListUserrole.getLabel());
		list_Userrole.setSelectedItem(vListUserrole);
	}

	
	public void onSelect$list_Userrole(Event event) throws Exception {
		M07UserroleCostingH data = getM07UserroleCostingH();

		data.setRolename(list_Userrole.getSelectedItem().getValue().toString());
		setM07UserroleCostingH(data);
	}
	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_M07UserroleCostingDetail.setHeight(String.valueOf(maxListBoxHeight)
				+ "px");

		windowM07UserroleCostingDetail.invalidate();
	}



	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
			
				txtUsername.setReadonly(true);
				cmbRolename.setDisabled(true);					
				txtFilteruser.setReadonly(true);
			
				
				btnNew.setVisible(false);
				btnEdit.setVisible(false);
				btnDelete.setVisible(false);

			}
			
			if(pMode.equals("New")){
				txtUsername.setReadonly(false);
				cmbRolename.setDisabled(false);					
				txtFilteruser.setReadonly(false);
				
				// set focus 
				txtUsername.setFocus(true);	
				
				btnNew.setVisible(true);
				btnEdit.setVisible(true);
				btnDelete.setVisible(true);
			}
			
			if(pMode.equals("Edit")){				
				txtUsername.setReadonly(false);
				cmbRolename.setDisabled(false);					
				txtFilteruser.setReadonly(false);
				
				// set focus 
				txtUsername.setFocus(true);	
				
				btnNew.setVisible(true);
				btnEdit.setVisible(true);
				btnDelete.setVisible(true);
			}
		}
	}

	
	
	public String validasiBusinessRules(){
		
		if(CommonUtils.isNotEmpty(txtUsername.getValue()) == false){
			return "Username "+Labels.getLabel("message.error.mandatory");
		}	
		
		
		if(CommonUtils.isNotEmpty(list_Userrole.getSelectedItem().getValue()) == false){
			return "Role User "+Labels.getLabel("message.error.mandatory");
		} else {
			if (list_Userrole.getSelectedItem().getValue().toString().equals("") == true){
				return "Role User "+Labels.getLabel("message.error.mandatory");
			}
		}
		
		
		return null;
	}
    

	public void displayDetail(List<M08UserroleCostingD> dataDetail) {
		list_DeletedM07UserroleCostingDetailList.clear();
		list_M07UserroleCostingDetailList.clear();

		if (CommonUtils.isNotEmpty(dataDetail)) {
			list_M07UserroleCostingDetailList.addAll(dataDetail);
		}

		setModelM07UserroleCostingDetailList();
	}

	public void onClick$btnNew(Event event) {
		M08UserroleCostingD newDetail = new M08UserroleCostingD();
		newDetail.setM07UserroleCostingH(getM07UserroleCostingH());
	

		M08UserroleCostingD newValue = M07UserroleCostingAddDetailWindow.show(
				windowM07UserroleCostingDetail, newDetail, getM07UserroleCostingH());

		if (newValue != null) {			
			list_M07UserroleCostingDetailList.add(newValue);
			setModelM07UserroleCostingDetailList();
			onClick$btnNew(event);
		}
	}
	
	public void onClick$btnEdit(Event event) {
		if (listBoxM07UserroleCostingDetail.getSelectedItem() != null) {
			M08UserroleCostingD selectedItem = (M08UserroleCostingD) listBoxM07UserroleCostingDetail
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_M07UserroleCostingDetailList.indexOf(selectedItem);

				M08UserroleCostingD editValue = M07UserroleCostingAddDetailWindow.show(
						windowM07UserroleCostingDetail, selectedItem, getM07UserroleCostingH());
				if (editValue != null) {
					list_M07UserroleCostingDetailList.set(index, editValue);
					setModelM07UserroleCostingDetailList();
				}
			}
		}
	}
	
	public void onClick$btnDelete(Event event) throws InterruptedException {
		if (listBoxM07UserroleCostingDetail.getSelectedItem() != null) {
			final M08UserroleCostingD selectedItem = (M08UserroleCostingD) listBoxM07UserroleCostingDetail
					.getSelectedItem().getValue();
			if (selectedItem != null) {

				// Show a confirm box
				String deleteRecord = selectedItem.getFilteruser();
				
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
								if (selectedItem.getM08Id() > 0) {
									list_DeletedM07UserroleCostingDetailList
											.add(selectedItem);
								}
								list_M07UserroleCostingDetailList.remove(selectedItem);
								setModelM07UserroleCostingDetailList();
							}
						}) == MultiLineMessageBox.YES) {
				}

			}
		}
	}
	
    public void onSort$listheader_M07UserroleCostingDetailList_Filteruser(Event event) {
    	sortingDataDetail(listheader_M07UserroleCostingDetailList_Filteruser, M08UserroleCostingDComparator.COMPARE_BY_FILTERUSER);
    }

    private void sortingDataDetail(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_M07UserroleCostingDetailList, new M08UserroleCostingDComparator(false, sortBy));
    		} else {
    			Collections.sort(list_M07UserroleCostingDetailList, new M08UserroleCostingDComparator(true, sortBy));
    		}
    	}
    	
    	setModelM07UserroleCostingDetailList();    
    }
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public M07UserroleCostingH getM07UserroleCostingH() {
		return getM07UserroleCostingMainCtrl().getSelectedM07UserroleCostingH();
	}

	public void setM07UserroleCostingH(M07UserroleCostingH selectedM07UserroleCostingH) {
		getM07UserroleCostingMainCtrl().setSelectedM07UserroleCostingH(selectedM07UserroleCostingH);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setM07UserroleCostingMainCtrl(M07UserroleCostingMainCtrl M07UserroleCostingMainCtrl) {
		this.M07UserroleCostingMainCtrl = M07UserroleCostingMainCtrl;
	}

	public M07UserroleCostingMainCtrl getM07UserroleCostingMainCtrl() {
		return this.M07UserroleCostingMainCtrl;
	}

	public List<M08UserroleCostingD> getList_DeletedM07UserroleCostingDetailList() {
		return list_DeletedM07UserroleCostingDetailList;
	}

	public void setList_DeletedM07UserroleCostingDetailList(
			List<M08UserroleCostingD> list_DeletedM07UserroleCostingDetailList) {
		this.list_DeletedM07UserroleCostingDetailList = list_DeletedM07UserroleCostingDetailList;
	}

	
	
}
