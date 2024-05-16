package solusi.hapis.webui.security.roleright;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
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

import solusi.hapis.backend.model.SecRole;
import solusi.hapis.backend.model.SecRoleright;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class RoleRightMainCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8500861096895901199L;

	protected Window windowRoleRightMain;

	// Role
	protected Paging pagingSecRole;
	private int startSecRoleList;
	protected Listbox listboxSecRole;
	protected Listheader listheaderRoleName;
	protected Textbox txtb_rolename;
	private ListModelList modelSecRole = new ListModelList();
	private List<SecRole> listSecRole = new ArrayList<SecRole>();

	// Right - Category
	protected Paging pagingRightCategory;
	private int startRightCategoryList;

	protected Listbox listboxRightCategory;
	private ListModelList modelRightCategory = new ListModelList();
	private List<SecRoleright> listRightCategory = new ArrayList<SecRoleright>();

	protected Listheader listheaderRightNameCategory;
	
	// Right - Sub Category
	protected Paging pagingRightSubCategory;
	private int startRightSubCategoryList;

	protected Listbox listboxRightSubCategory;
	private ListModelList modelRightSubCategory = new ListModelList();
	private List<SecRoleright> listRightSubCategory = new ArrayList<SecRoleright>();

	protected Listheader listheaderRightNameSubCategory;	

	// Right - Item
	protected Paging pagingRightItem;
	private int startRightItemList;

	protected Listbox listboxRightItem;
	private ListModelList modelRightItem = new ListModelList();
	private List<SecRoleright> listRightItem = new ArrayList<SecRoleright>();

	protected Listheader listheaderRightNameItem;

	// Right - Item
	protected Paging pagingRightButton;
	private int startRightButtonList;

	protected Listbox listboxRightButton;
	private ListModelList modelRightButton = new ListModelList();
	private List<SecRoleright> listRightButton = new ArrayList<SecRoleright>();

	protected Listheader listheaderRightNameButton;

	private transient SecurityService securityService;

	protected Button btnSearch;
	protected Button btnClear;
	
	protected Button btnNewCategory;
	protected Button btnDeleteCategory;
	
	protected Button btnNewSubCategory;
	protected Button btnDeleteSubCategory;
	
	protected Button btnNewItem;
	protected Button btnDeleteItem;
	
	protected Button btnNewButton;
	protected Button btnDeleteButton;
	

	private SecRole roleSelected;
	private SecRoleright roleRightCategory;
	private SecRoleright roleRightSubCategory;
	private SecRoleright roleRightItem;
	
	protected Borderlayout borderLayout_RoleRight;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		pagingSecRole.setPageSize(CommonUtils.PAGE_SIZE);
		pagingSecRole.setDetailed(true);
		pagingSecRole.addEventListener("onPaging", onPagingSecRole());

		pagingRightCategory.setPageSize(CommonUtils.PAGE_SIZE);
		pagingRightCategory.setDetailed(true);
		pagingRightCategory.addEventListener("onPaging",
				onPagingRightCategory());
		listboxRightCategory.setItemRenderer(renderTableRightCategory());
		
		pagingRightSubCategory.setPageSize(CommonUtils.PAGE_SIZE);
		pagingRightSubCategory.setDetailed(true);
		pagingRightSubCategory.addEventListener("onPaging",
				onPagingRightSubCategory());
		listboxRightSubCategory.setItemRenderer(renderTableRightSubCategory());
		

		pagingRightItem.setPageSize(CommonUtils.PAGE_SIZE);
		pagingRightItem.setDetailed(true);
		pagingRightItem.addEventListener("onPaging", onPagingRightItem());
		listboxRightItem.setItemRenderer(renderTableRightItem());

		pagingRightButton.setPageSize(CommonUtils.PAGE_SIZE);
		pagingRightButton.setDetailed(true);
		pagingRightButton.addEventListener("onPaging", onPagingRightButton());
		listboxRightButton.setItemRenderer(renderTableRightButton());

		windowRoleRightMain.addEventListener(Events.ON_OK, onSubmitForm());
	}

	public void onCreate$windowRoleRightMain(Event event) throws Exception {
		doFillListbox();
		
		btnNewCategory.setVisible(getUserWorkspace().isAllowed("button_roleRights_btnNew"));
		btnDeleteCategory.setVisible(getUserWorkspace().isAllowed("button_roleRights_btnDelete"));
		btnNewSubCategory.setVisible(getUserWorkspace().isAllowed("button_roleRights_btnNew"));
		btnDeleteSubCategory.setVisible(getUserWorkspace().isAllowed("button_roleRights_btnDelete"));
		btnNewItem.setVisible(getUserWorkspace().isAllowed("button_roleRights_btnNew"));
		btnDeleteItem.setVisible(getUserWorkspace().isAllowed("button_roleRights_btnDelete"));
		btnNewButton.setVisible(getUserWorkspace().isAllowed("button_roleRights_btnNew"));
		btnDeleteButton.setVisible(getUserWorkspace().isAllowed("button_roleRights_btnDelete"));
		
		
	}

	public void doFillListbox() {
		doFitSize();

		listheaderRoleName.setSortAscending(new RoleComparator(true,
				RoleComparator.COMPARE_BY_KETERANGAN));
		listheaderRoleName.setSortDescending(new RoleComparator(false,
				RoleComparator.COMPARE_BY_KETERANGAN));

		listheaderRightNameCategory.setSortAscending(new RoleRightComparator(
				true, RoleRightComparator.COMPARE_BY_NAMARIGHT));
		listheaderRightNameCategory.setSortDescending(new RoleRightComparator(
				false, RoleRightComparator.COMPARE_BY_NAMARIGHT));

		listheaderRightNameItem.setSortAscending(new RoleRightComparator(true,
				RoleRightComparator.COMPARE_BY_NAMARIGHT));
		listheaderRightNameItem.setSortDescending(new RoleRightComparator(
				false, RoleRightComparator.COMPARE_BY_NAMARIGHT));

		listheaderRightNameButton.setSortAscending(new RoleRightComparator(
				true, RoleRightComparator.COMPARE_BY_NAMARIGHT));
		listheaderRightNameButton.setSortDescending(new RoleRightComparator(
				false, RoleRightComparator.COMPARE_BY_NAMARIGHT));

		getSearchData();

		listboxSecRole.setItemRenderer(renderTable());

		windowRoleRightMain.addEventListener(Events.ON_OK, onSubmitForm());
	}

	public void getSearchData() {

		listSecRole.clear();

		List<SecRole> tempListRole = null;

		if (CommonUtils.isNotEmpty(txtb_rolename.getValue())) {
			listSecRole.clear();
			tempListRole = securityService.getRolesLikeRoleName(txtb_rolename
					.getValue());
		} else {
			tempListRole = securityService.getAllRoles();
		}

		if (CommonUtils.isNotEmpty(tempListRole)) {
			listSecRole.addAll(tempListRole);
			startSecRoleList = 0;
			pagingSecRole.setActivePage(0);
		}

		setModelSecRole();

	}

	private void setModelSecRole() {
		modelSecRole.clear();

		SecRole selectedRole = null;
		if (CommonUtils.isNotEmpty(listSecRole)) {

			int endSecRoleList = 0;
			if (startSecRoleList + pagingSecRole.getPageSize() < listSecRole
					.size()) {
				endSecRoleList = startSecRoleList + pagingSecRole.getPageSize();
			} else {
				endSecRoleList = listSecRole.size();
			}

			if (startSecRoleList > endSecRoleList) {
				startSecRoleList = 0;
				pagingSecRole.setActivePage(0);
			}

			modelSecRole.addAll(listSecRole.subList(startSecRoleList,
					endSecRoleList));

			pagingSecRole.setDetailed(true);
			pagingSecRole.setTotalSize(listSecRole.size());

			listboxSecRole.setModel(modelSecRole);
			listboxSecRole.setSelectedIndex(0);

			selectedRole = listSecRole.get(startSecRoleList);
		} else {
			pagingSecRole.setDetailed(false);
			listboxSecRole.setModel(modelSecRole);
			pagingSecRole.setTotalSize(0);
		}

		setRoleSelected(selectedRole);
		getSearchRightCategory(selectedRole);

	}

	public void getSearchRightCategory(SecRole selectedRole) {
		listRightCategory.clear();

		if (selectedRole != null) {
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();

			parameterInputDetail.put("parentRlrId", new Long(0));

			if (CommonUtils.isNotEmpty(selectedRole.getId())) {
				parameterInputDetail.put("roleId", selectedRole.getId());
			}

			List<SecRoleright> tempListSecRoleright = securityService
					.getListSecRoleright(parameterInputDetail);
			if (CommonUtils.isNotEmpty(tempListSecRoleright)) {
				listRightCategory.addAll(tempListSecRoleright);
				startRightCategoryList = 0;

				pagingRightCategory.setActivePage(0);
			}

		}

		setModelRightCategory();
	}

	private void setModelRightCategory() {
		modelRightCategory.clear();

		SecRoleright selectedRoleRightCategory = null;
		if (CommonUtils.isNotEmpty(listRightCategory)) {

			int endRightCategoryList = 0;
			if (startRightCategoryList + pagingRightCategory.getPageSize() < listRightCategory
					.size()) {
				endRightCategoryList = startRightCategoryList
						+ pagingRightCategory.getPageSize();
			} else {
				endRightCategoryList = listRightCategory.size();
			}

			if (startRightCategoryList > endRightCategoryList) {
				startRightCategoryList = 0;
				pagingRightCategory.setActivePage(0);
			}

			modelRightCategory.addAll(listRightCategory.subList(
					startRightCategoryList, endRightCategoryList));

			pagingRightCategory.setDetailed(true);
			pagingRightCategory.setTotalSize(listRightCategory.size());

			listboxRightCategory.setModel(modelRightCategory);
			listboxRightCategory.setSelectedIndex(0);

			selectedRoleRightCategory = listRightCategory
					.get(startRightCategoryList);
		} else {
			pagingRightCategory.setDetailed(false);
			listboxRightCategory.setModel(modelRightCategory);
			pagingRightCategory.setTotalSize(0);
		}

		
		setRoleRightCategory(selectedRoleRightCategory);
		getSearchRightSubCategory(selectedRoleRightCategory);

	}
	
	public void getSearchRightSubCategory(SecRoleright selectedRoleRightCategory) {
		listRightSubCategory.clear();

		if (selectedRoleRightCategory != null) {
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();

			if (CommonUtils.isNotEmpty(selectedRoleRightCategory.getId())) {
				parameterInputDetail.put("parentRlrId",
						selectedRoleRightCategory.getId());
			}

			if (CommonUtils.isNotEmpty(selectedRoleRightCategory.getSecRole()
					.getId())) {
				parameterInputDetail.put("roleId", selectedRoleRightCategory
						.getSecRole().getId());
			}

			List<SecRoleright> tempListSecRoleright = securityService
					.getListSecRoleright(parameterInputDetail);
			if (CommonUtils.isNotEmpty(tempListSecRoleright)) {
				listRightSubCategory.addAll(tempListSecRoleright);
				startRightSubCategoryList = 0;

				pagingRightSubCategory.setActivePage(0);
			}

		}

		setModelRightSubCategory();
	}

	
	private void setModelRightSubCategory() {
		modelRightSubCategory.clear();

		SecRoleright selectedRoleRightSubCategory = null;
		if (CommonUtils.isNotEmpty(listRightSubCategory)) {

			int endRightSubCategoryList = 0;
			if (startRightSubCategoryList + pagingRightSubCategory.getPageSize() < listRightSubCategory
					.size()) {
				endRightSubCategoryList = startRightSubCategoryList
						+ pagingRightSubCategory.getPageSize();
			} else {
				endRightSubCategoryList = listRightSubCategory.size();
			}

			if (startRightSubCategoryList > endRightSubCategoryList) {
				startRightSubCategoryList = 0;
				pagingRightSubCategory.setActivePage(0);
			}

			modelRightSubCategory.addAll(listRightSubCategory.subList(
					startRightSubCategoryList, endRightSubCategoryList));

			pagingRightSubCategory.setDetailed(true);
			pagingRightSubCategory.setTotalSize(listRightSubCategory.size());

			listboxRightSubCategory.setModel(modelRightSubCategory);
			listboxRightSubCategory.setSelectedIndex(0);

			selectedRoleRightSubCategory = listRightSubCategory
					.get(startRightSubCategoryList);
		} else {
			pagingRightSubCategory.setDetailed(false);
			listboxRightSubCategory.setModel(modelRightSubCategory);
			pagingRightSubCategory.setTotalSize(0);
		}

		
		setRoleRightSubCategory(selectedRoleRightSubCategory);
		getSearchRightItem(selectedRoleRightSubCategory);

	}

	public void getSearchRightItem(SecRoleright selectedRoleRightCategory) {
		listRightItem.clear();

		if (selectedRoleRightCategory != null) {
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();

			if (CommonUtils.isNotEmpty(selectedRoleRightCategory.getId())) {
				parameterInputDetail.put("parentRlrId",
						selectedRoleRightCategory.getId());
			}

			if (CommonUtils.isNotEmpty(selectedRoleRightCategory.getSecRole()
					.getId())) {
				parameterInputDetail.put("roleId", selectedRoleRightCategory
						.getSecRole().getId());
			}

			List<SecRoleright> tempListSecRoleright = securityService
					.getListSecRoleright(parameterInputDetail);
			if (CommonUtils.isNotEmpty(tempListSecRoleright)) {
				listRightItem.addAll(tempListSecRoleright);
				startRightItemList = 0;

				pagingRightItem.setActivePage(0);
			}

		}

		setModelRightItem();
	}

	private void setModelRightItem() {
		modelRightItem.clear();

		SecRoleright selectedRoleRightItem = null;
		if (CommonUtils.isNotEmpty(listRightItem)) {

			int endRightItemList = 0;
			if (startRightItemList + pagingRightItem.getPageSize() < listRightItem
					.size()) {
				endRightItemList = startRightItemList
						+ pagingRightItem.getPageSize();
			} else {
				endRightItemList = listRightItem.size();
			}

			if (startRightItemList > endRightItemList) {
				startRightItemList = 0;
				pagingRightItem.setActivePage(0);
			}

			modelRightItem.addAll(listRightItem.subList(startRightItemList,
					endRightItemList));

			pagingRightItem.setDetailed(true);
			pagingRightItem.setTotalSize(listRightItem.size());

			listboxRightItem.setModel(modelRightItem);
			listboxRightItem.setSelectedIndex(0);

			selectedRoleRightItem = listRightItem.get(startRightItemList);
		} else {
			pagingRightItem.setDetailed(false);
			listboxRightItem.setModel(modelRightItem);
			pagingRightItem.setTotalSize(0);
		}

		setRoleRightItem(selectedRoleRightItem);
		getSearchRightButton(selectedRoleRightItem);

	}

	public void getSearchRightButton(SecRoleright selectedRoleRightItem) {
		listRightButton.clear();

		if (selectedRoleRightItem != null) {
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();

			if (CommonUtils.isNotEmpty(selectedRoleRightItem.getId())) {
				parameterInputDetail.put("parentRlrId",
						selectedRoleRightItem.getId());
			}

			if (CommonUtils.isNotEmpty(selectedRoleRightItem.getSecRole()
					.getId())) {
				parameterInputDetail.put("roleId", selectedRoleRightItem
						.getSecRole().getId());
			}

			List<SecRoleright> tempListSecRoleright = securityService
					.getListSecRoleright(parameterInputDetail);
			if (CommonUtils.isNotEmpty(tempListSecRoleright)) {
				listRightButton.addAll(tempListSecRoleright);
				startRightButtonList = 0;

				pagingRightButton.setActivePage(0);
			}

		}

		setModelRightButton();
	}

	private void setModelRightButton() {
		modelRightButton.clear();

		SecRoleright selectedRoleRightButton = null;
		if (CommonUtils.isNotEmpty(listRightButton)) {

			int endRightButtonList = 0;
			if (startRightButtonList + pagingRightButton.getPageSize() < listRightButton
					.size()) {
				endRightButtonList = startRightButtonList
						+ pagingRightButton.getPageSize();
			} else {
				endRightButtonList = listRightButton.size();
			}

			if (startRightButtonList > endRightButtonList) {
				startRightButtonList = 0;
				pagingRightButton.setActivePage(0);
			}

			modelRightButton.addAll(listRightButton.subList(
					startRightButtonList, endRightButtonList));

			pagingRightButton.setDetailed(true);
			pagingRightButton.setTotalSize(listRightButton.size());

			listboxRightButton.setModel(modelRightButton);
			listboxRightButton.setSelectedIndex(0);

			selectedRoleRightButton = listRightButton.get(startRightButtonList);
		} else {
			pagingRightButton.setDetailed(false);
			listboxRightButton.setModel(modelRightButton);
			pagingRightButton.setTotalSize(0);
		}

	}

	public void doFitSize() {
		
		final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_RoleRight.setHeight(String.valueOf(maxListBoxHeight) + "px");

		windowRoleRightMain.invalidate();
	}

	private EventListener onSubmitForm() {
		return new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				getSearchData();
			}
		};
	}

	private ListitemRenderer renderTable() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				SecRole detail = (SecRole) data;

				Listcell lc = new Listcell(detail.getRolShortdescription());
				lc.setParent(item);

				item.setValue(data);
				item.setAttribute("data", data);

			}
		};
	}

	private ListitemRenderer renderTableRightCategory() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				SecRoleright detail = (SecRoleright) data;

				Listcell lc = new Listcell(detail.getSecRight().getRigDesc());
				lc.setParent(item);

				item.setValue(data);
				item.setAttribute("data", data);

			}
		};
	}
	
	private ListitemRenderer renderTableRightSubCategory() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				SecRoleright detail = (SecRoleright) data;

				Listcell lc = new Listcell(detail.getSecRight().getRigDesc());
				lc.setParent(item);

				item.setValue(data);
				item.setAttribute("data", data);

			}
		};
	}

	private ListitemRenderer renderTableRightItem() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				SecRoleright detail = (SecRoleright) data;

				Listcell lc = new Listcell(detail.getSecRight().getRigDesc());
				lc.setParent(item);

				item.setValue(data);
				item.setAttribute("data", data);

			}
		};
	}

	private ListitemRenderer renderTableRightButton() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				SecRoleright detail = (SecRoleright) data;

				Listcell lc = new Listcell(detail.getSecRight().getRigDesc());
				lc.setParent(item);

				item.setValue(data);
				item.setAttribute("data", data);

			}
		};
	}

	private EventListener onPagingSecRole() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startSecRoleList = pageNo * pagingSecRole.getPageSize();
				setModelSecRole();
			}
		};
	}

	private EventListener onPagingRightCategory() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startRightCategoryList = pageNo
						* pagingRightCategory.getPageSize();
				setModelRightCategory();
			}
		};
	}
	
	private EventListener onPagingRightSubCategory() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startRightSubCategoryList = pageNo
						* pagingRightSubCategory.getPageSize();
				setModelRightSubCategory();
			}
		};
	}

	private EventListener onPagingRightItem() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startRightItemList = pageNo * pagingRightItem.getPageSize();
				setModelRightItem();
			}
		};
	}

	private EventListener onPagingRightButton() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				startRightButtonList = pageNo * pagingRightButton.getPageSize();
				setModelRightButton();
			}
		};
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void onSelect$listboxSecRole(Event event)
			throws InterruptedException {

		if (listboxSecRole.getSelectedItem() != null) {
			SecRole selectedData = (SecRole) listboxSecRole.getSelectedItem()
					.getValue();

			if (selectedData != null) {
				setRoleSelected(selectedData);
				getSearchRightCategory(selectedData);
			}
		}

	}
	
	public void onSelect$listboxRightCategory(Event event)
			throws InterruptedException {

		if (listboxRightCategory.getSelectedItem() != null) {
			SecRoleright selectedData = (SecRoleright) listboxRightCategory.getSelectedItem()
					.getValue();

			if (selectedData != null) {
				setRoleRightCategory(selectedData);
				getSearchRightSubCategory(selectedData);
			}
		}

	}
	
	public void onSelect$listboxRightSubCategory(Event event)
			throws InterruptedException {

		if (listboxRightSubCategory.getSelectedItem() != null) {
			SecRoleright selectedData = (SecRoleright) listboxRightSubCategory.getSelectedItem()
					.getValue();

			if (selectedData != null) {
				setRoleRightSubCategory(selectedData);
				getSearchRightItem(selectedData);
			}
		}

	}
	
	public void onSelect$listboxRightItem(Event event)
			throws InterruptedException {

		if (listboxRightItem.getSelectedItem() != null) {
			SecRoleright selectedData = (SecRoleright) listboxRightItem.getSelectedItem()
					.getValue();

			if (selectedData != null) {
				setRoleRightItem(selectedData);
				getSearchRightButton(selectedData);
			}
		}

	}

	public void onClick$btnSearch(Event event) throws InterruptedException {
		getSearchData();
	}

	public void onClick$btnClear(Event event) throws InterruptedException {
		txtb_rolename.setValue(null);

		getSearchData();

	}
	
	public void onClick$btnNewCategory(Event event) throws InterruptedException {
		String vNewCategory = RightNewSelect.show(windowRoleRightMain, getRoleSelected(),  null, listRightCategory, "Category");
		
		if(vNewCategory != null){
			getSearchRightCategory(getRoleSelected());
		}
	}
	
	public void onClick$btnDeleteCategory(Event event) throws InterruptedException {
		if (listboxRightCategory.getSelectedItem() != null) {
			final SecRoleright selectedData = (SecRoleright) listboxRightCategory.getSelectedItem()
					.getValue();

			if (selectedData != null) {
	            // Show a confirm box
	        	String deleteRecord = selectedData.getSecRight().getRigDesc();
	            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + deleteRecord;
	            final String title = Labels.getLabel("message.Deleting.Record");
	
	            MultiLineMessageBox.doSetTemplate();
	            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
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
	
	                private void deleteBean() throws InterruptedException {
	                	try {
	                		if (CommonUtils.isNotEmpty(listRightSubCategory)) {
	                			
	                			for (SecRoleright anSecRoleRightSubCategory : listRightSubCategory){
	                				
	                				if (anSecRoleRightSubCategory != null){
	                				
		                				Map<Object, Object> paramSecRoleRightItem = new HashMap<Object, Object>();
		                				
	                					if (CommonUtils.isNotEmpty(anSecRoleRightSubCategory.getId())) {
	                						paramSecRoleRightItem.put("parentRlrId",
	                								anSecRoleRightSubCategory.getId());
	                					}
	
	                					if (CommonUtils.isNotEmpty(anSecRoleRightSubCategory.getSecRole()
	                							.getId())) {
	                						paramSecRoleRightItem.put("roleId", anSecRoleRightSubCategory
	                								.getSecRole().getId());
	                					}
	                					
	                					List<SecRoleright> deleteListSecRolerightItem = securityService
	                							.getListSecRoleright(paramSecRoleRightItem);
	                					
	                					if (CommonUtils.isNotEmpty(deleteListSecRolerightItem)) {
	    			                		for (SecRoleright anSecRoleRightItem : deleteListSecRolerightItem){
	    			                			if(anSecRoleRightItem != null){
	    			                				Map<Object, Object> paramSecRoleRightButton = new HashMap<Object, Object>();
	    		
	    		                					if (CommonUtils.isNotEmpty(anSecRoleRightItem.getId())) {
	    		                						paramSecRoleRightButton.put("parentRlrId",
	    		                								anSecRoleRightItem.getId());
	    		                					}
	    		
	    		                					if (CommonUtils.isNotEmpty(anSecRoleRightItem.getSecRole()
	    		                							.getId())) {
	    		                						paramSecRoleRightButton.put("roleId", anSecRoleRightItem
	    		                								.getSecRole().getId());
	    		                					}
	    		
	    		                					List<SecRoleright> deleteListSecRolerightButton = securityService
	    		                							.getListSecRoleright(paramSecRoleRightButton);
	    		                					if (CommonUtils.isNotEmpty(deleteListSecRolerightButton)) {
	    		                						for (SecRoleright anSecRoleRightButton : deleteListSecRolerightButton){
	    		                							securityService.delete(anSecRoleRightButton);
	    		                						}
	    		                						
	    		                					}
	    		                					securityService.delete(anSecRoleRightItem);
	    			                			}
	    			                		}
	                						
	                					}
	                					securityService.delete(anSecRoleRightSubCategory);
	                				}
	                			}	
	                		}
	                		
	                		securityService.delete(selectedData);
		                	getSearchRightCategory(getRoleSelected());
		                	
	                    } catch (DataAccessException e) {
	                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
			}
		}
	}
	
	public void onClick$btnNewSubCategory(Event event) throws InterruptedException {
		String vNewSubCategory = RightNewSelect.show(windowRoleRightMain, null,  getRoleRightCategory(), listRightSubCategory, "Sub Category");
		
		if(vNewSubCategory != null){
			getSearchRightSubCategory(getRoleRightCategory());
		}
	}
	
	public void onClick$btnDeleteSubCategory(Event event) throws InterruptedException {
		if (listboxRightSubCategory.getSelectedItem() != null) {
			final SecRoleright selectedData = (SecRoleright) listboxRightSubCategory.getSelectedItem()
					.getValue();

			if (selectedData != null) {
	            // Show a confirm box
	        	String deleteRecord = selectedData.getSecRight().getRigDesc();
	            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + deleteRecord;
	            final String title = Labels.getLabel("message.Deleting.Record");
	
	            MultiLineMessageBox.doSetTemplate();
	            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
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
	
	                private void deleteBean() throws InterruptedException {
	                	try {
	                		if (CommonUtils.isNotEmpty(listRightItem)) {
		                		for (SecRoleright anSecRoleRightItem : listRightItem){
		                			if(anSecRoleRightItem != null){
		                				Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();
	
	                					if (CommonUtils.isNotEmpty(anSecRoleRightItem.getId())) {
	                						parameterInputDetail.put("parentRlrId",
	                								anSecRoleRightItem.getId());
	                					}
	
	                					if (CommonUtils.isNotEmpty(anSecRoleRightItem.getSecRole()
	                							.getId())) {
	                						parameterInputDetail.put("roleId", anSecRoleRightItem
	                								.getSecRole().getId());
	                					}
	
	                					List<SecRoleright> deleteListSecRolerightButton = securityService
	                							.getListSecRoleright(parameterInputDetail);
	                					if (CommonUtils.isNotEmpty(deleteListSecRolerightButton)) {
	                						for (SecRoleright anSecRoleRightButton : deleteListSecRolerightButton){
	                							securityService.delete(anSecRoleRightButton);
	                						}
	                						
	                					}
	                					securityService.delete(anSecRoleRightItem);
		                			}
		                			
		                			
		                		}
	                		}	
	                		
		                	securityService.delete(selectedData);   
		                	getSearchRightSubCategory(getRoleRightCategory());
		                	
	                    } catch (DataAccessException e) {
	                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
			}
		}
	}
	
	public void onClick$btnNewItem(Event event) throws InterruptedException {
		String vNewItem = RightNewSelect.show(windowRoleRightMain, null,  getRoleRightSubCategory(), listRightItem, "Item");
		
		if(vNewItem != null){
			getSearchRightItem(getRoleRightSubCategory());
		}
	}
	
	public void onClick$btnDeleteItem(Event event) throws InterruptedException {
		if (listboxRightItem.getSelectedItem() != null) {
			final SecRoleright selectedData = (SecRoleright) listboxRightItem.getSelectedItem()
					.getValue();

			if (selectedData != null) {
	            // Show a confirm box
	        	String deleteRecord = selectedData.getSecRight().getRigDesc();
	            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + deleteRecord;
	            final String title = Labels.getLabel("message.Deleting.Record");
	
	            MultiLineMessageBox.doSetTemplate();
	            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
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
	
	                private void deleteBean() throws InterruptedException {
	                	try {
	                		if (CommonUtils.isNotEmpty(listRightButton)) {
		                		for (SecRoleright anSecRoleRightButton : listRightButton){
		                			if(anSecRoleRightButton != null){
		                				securityService.delete(anSecRoleRightButton);
		                			}
		                		}
	                		}

	                		securityService.delete(selectedData);
	                		getSearchRightItem(getRoleRightSubCategory());
	                		
	                    } catch (DataAccessException e) {
	                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
			}
		}
	}
	
	public void onClick$btnNewButton(Event event) throws InterruptedException {
		String vNewButton = RightNewSelect.show(windowRoleRightMain, null,  getRoleRightItem(), listRightButton, "Button");
		
		if(vNewButton != null){
			getSearchRightButton(getRoleRightItem());
		}
	}
	
	public void onClick$btnDeleteButton(Event event) throws InterruptedException {
		if (listboxRightButton.getSelectedItem() != null) {
			final SecRoleright selectedData = (SecRoleright) listboxRightButton.getSelectedItem()
					.getValue();

			if (selectedData != null) {
	            // Show a confirm box
	        	String deleteRecord = selectedData.getSecRight().getRigDesc();
	            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + deleteRecord;
	            final String title = Labels.getLabel("message.Deleting.Record");
	
	            MultiLineMessageBox.doSetTemplate();
	            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
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
	
	                private void deleteBean() throws InterruptedException {
	                	try {
	                		securityService.delete(selectedData);
	                        
	                		listRightButton.remove(selectedData);
	                		setModelRightButton();
	                		
	                    } catch (DataAccessException e) {
	                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
	                    }
	                }
	            }
	
	            ) == MultiLineMessageBox.YES) {
	            }
			}
		}
	}

	public void onSort$listheaderRoleName(Event event) {
		sortingData(listheaderRoleName, RoleComparator.COMPARE_BY_KETERANGAN);
	}

	private void sortingData(Listheader listHeader, int sortBy) {
		if (CommonUtils.isNotEmpty(listHeader.getSortDirection())) {
			if (listHeader.getSortDirection().equals("ascending")) {
				Collections
						.sort(listSecRole, new RoleComparator(false, sortBy));
			} else {
				Collections.sort(listSecRole, new RoleComparator(true, sortBy));
			}
		}

		setModelSecRole();
	}

	public void onSort$listheaderRightNameCategory(Event event) {
		sortingDataCategory(listheaderRightNameCategory,
				RoleRightComparator.COMPARE_BY_NAMARIGHT);
	}

	private void sortingDataCategory(Listheader listHeader, int sortBy) {
		if (CommonUtils.isNotEmpty(listHeader.getSortDirection())) {
			if (listHeader.getSortDirection().equals("ascending")) {
				Collections.sort(listRightCategory, new RoleRightComparator(
						false, sortBy));
			} else {
				Collections.sort(listRightCategory, new RoleRightComparator(
						true, sortBy));
			}
		}

		setModelRightCategory();
	}

	public void onSort$listheaderRightNameItem(Event event) {
		sortingDataItem(listheaderRightNameItem,
				RoleRightComparator.COMPARE_BY_NAMARIGHT);
	}

	private void sortingDataItem(Listheader listHeader, int sortBy) {
		if (CommonUtils.isNotEmpty(listHeader.getSortDirection())) {
			if (listHeader.getSortDirection().equals("ascending")) {
				Collections.sort(listRightItem, new RoleRightComparator(false,
						sortBy));
			} else {
				Collections.sort(listRightItem, new RoleRightComparator(true,
						sortBy));
			}
		}

		setModelRightItem();
	}
	
	public void onSort$listheaderRightNameButton(Event event) {
		sortingDataButton(listheaderRightNameButton,
				RoleRightComparator.COMPARE_BY_NAMARIGHT);
	}

	private void sortingDataButton(Listheader listHeader, int sortBy) {
		if (CommonUtils.isNotEmpty(listHeader.getSortDirection())) {
			if (listHeader.getSortDirection().equals("ascending")) {
				Collections.sort(listRightButton, new RoleRightComparator(false,
						sortBy));
			} else {
				Collections.sort(listRightButton, new RoleRightComparator(true,
						sortBy));
			}
		}

		setModelRightButton();
	}

	public SecRoleright getRoleRightCategory() {
		return roleRightCategory;
	}

	public void setRoleRightCategory(SecRoleright roleRightCategory) {
		this.roleRightCategory = roleRightCategory;
	}

	public SecRoleright getRoleRightItem() {
		return roleRightItem;
	}

	public void setRoleRightItem(SecRoleright roleRightItem) {
		this.roleRightItem = roleRightItem;
	}

	public SecRole getRoleSelected() {
		return roleSelected;
	}

	public void setRoleSelected(SecRole roleSelected) {
		this.roleSelected = roleSelected;
	}

	public SecRoleright getRoleRightSubCategory() {
		return roleRightSubCategory;
	}

	public void setRoleRightSubCategory(SecRoleright roleRightSubCategory) {
		this.roleRightSubCategory = roleRightSubCategory;
	}

	
	
	
}
