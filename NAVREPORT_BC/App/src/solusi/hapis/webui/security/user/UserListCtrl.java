/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package solusi.hapis.webui.security.user;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.service.UserService;
import solusi.hapis.backend.util.HibernateSearchObject;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.policy.model.UserImpl;
import solusi.hapis.webui.security.user.model.UserListModelItemRenderer;
import solusi.hapis.webui.util.GFCBaseListCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;

import com.trg.search.Filter;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the /WEB-INF/pages/sec_user/userList.zul
 * file.<br>
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * 
 * @author bbruhns
 * @author sgerth
 * @changes 05/15/2009: sge Migrating the list models for paging. <br>
 *          07/24/2009: sge changes for clustering.<br>
 *          10/12/2009: sge changings in the saving routine.<br>
 *          11/07/2009: bbr changed to extending from GFCBaseCtrl<br>
 *          (GenericForwardComposer) for spring-managed creation.<br>
 *          * 03/09/2009: sge changed for allow repainting after resizing.<br>
 *          with the refresh button.<br>
 */
public class UserListCtrl extends GFCBaseListCtrl<SecUser> implements Serializable {

	private static final long serialVersionUID = 2038742641853727975L;

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window userListWindow; // autowired

	// filter components
	protected Textbox txtLoginName;
	protected Textbox txtLastName;
	protected Textbox txtEmail;
	protected Combobox cmbEnabled;
	protected Combobox cmbAccountExpired;
	protected Combobox cmbCredentialExpired;
	protected Combobox cmbLocked;

	// listbox userList
	protected Borderlayout borderLayout_secUserList; // autowired
	protected Paging paging_UserList; // autowired
	protected Listbox listBoxUser; // aurowired
	protected Listheader listheader_UserList_usrLoginname; // autowired
	protected Listheader listheader_UserList_usrLastname; // autowired
	protected Listheader listheader_UserList_usrEmail; // autowired
	protected Listheader listheader_UserList_usrEnabled; // autowired
	protected Listheader listheader_UserList_usrAccountnonexpired; // autowired
	protected Listheader listheader_UserList_usrCredentialsnonexpired; // autowired
	protected Listheader listheader_UserList_usrAccountnonlocked; // autowired

	// checkRights
	protected Button btnSearch;
	protected Button button_UserList_NewUser; // autowired

	// ServiceDAOs / Domain Classes
	private transient UserService userService;

	/**
	 * default constructor.<br>
	 */
	public UserListCtrl() {
		super();
	}

	public void onCreate$userListWindow(Event event) {
		/**
		 * Calculate how many rows have been place in the listbox. Get the
		 * currentDesktopHeight from a hidden Intbox from the index.zul that are
		 * filled by onClientInfo() in the indexCtroller
		 */

		int panelHeight = 25;
		int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		height = height + panelHeight;
		final int maxListBoxHeight = height - 147;
		
		borderLayout_secUserList.setHeight(String.valueOf(maxListBoxHeight)
				+ "px");

		listheader_UserList_usrLoginname.setSortAscending(new FieldComparator(
				"usrLoginname", true));
		listheader_UserList_usrLoginname.setSortDescending(new FieldComparator(
				"usrLoginname", false));
		listheader_UserList_usrLoginname.setSortDirection("ascending");
		listheader_UserList_usrLastname.setSortAscending(new FieldComparator(
				"usrLastname", true));
		listheader_UserList_usrLastname.setSortDescending(new FieldComparator(
				"usrLastname", false));
		listheader_UserList_usrEmail.setSortAscending(new FieldComparator(
				"usrEmail", true));
		listheader_UserList_usrEmail.setSortDescending(new FieldComparator(
				"usrEmail", false));
		listheader_UserList_usrEnabled.setSortAscending(new FieldComparator(
				"usrEnabled", true));
		listheader_UserList_usrEnabled.setSortDescending(new FieldComparator(
				"usrEnabled", false));
		listheader_UserList_usrAccountnonexpired
				.setSortAscending(new FieldComparator("usrAccountnonexpired",
						true));
		listheader_UserList_usrAccountnonexpired
				.setSortDescending(new FieldComparator("usrAccountnonexpired",
						false));
		listheader_UserList_usrCredentialsnonexpired
				.setSortAscending(new FieldComparator(
						"usrCredentialsnonexpired", true));
		listheader_UserList_usrCredentialsnonexpired
				.setSortDescending(new FieldComparator(
						"usrCredentialsnonexpired", false));
		listheader_UserList_usrAccountnonlocked
				.setSortAscending(new FieldComparator("usrAccountnonlocked",
						true));
		listheader_UserList_usrAccountnonlocked
				.setSortDescending(new FieldComparator("usrAccountnonlocked",
						false));

		// set the paging params
		paging_UserList.setPageSize(CommonUtils.PAGE_SIZE);
		paging_UserList.setDetailed(true);

		onClick$btnSearch(event);
		
		// set the itemRenderer
		listBoxUser.setItemRenderer(new UserListModelItemRenderer());

	}

	/**
	 * Call the User dialog with the selected entry. <br>
	 * <br>
	 * This methode is forwarded from the listboxes item renderer. <br>
	 * see: id.co.club.webui.user.model.UserListModelItemRenderer.java <br>
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onUserListItemDoubleClicked(Event event) throws Exception {

		// UserWorkspace workspace = getUserWorkspace();
		// if (!workspace.isAllowed("UserList_listBoxUser.onDoubleClick")) {
		// return;
		// }

		// get the selected object
		Listitem item = listBoxUser.getSelectedItem();

		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
			SecUser anUser = (SecUser) item.getAttribute("data");

			showDetailView(anUser);
		}
	}

	/**
	 * Call the SecUser dialog with a new empty entry. <br>
	 */
	public void onClick$button_UserList_NewUser(Event event) throws Exception {

		// create a new SecUser object
		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.

		SecUser anUser = getUserService().getNewUser();

		showDetailView(anUser);

	}

	/**
	 * Opens the detail view. <br>
	 * Overhanded some params in a map if needed. <br>
	 * 
	 * @param anUser
	 * @throws Exception
	 */
	private void showDetailView(SecUser anUser) throws Exception {

		/*
		 * We can call our Dialog zul-file with parameters. So we can call them
		 * with a object of the selected item. For handed over these parameter
		 * only a Map is accepted. So we put the object in a HashMap.
		 */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", anUser);

		// call the zul-file with the parameters packed in a map
		try {
			Executions.createComponents(
					"/WEB-INF/pages/sec_user/userDialog.zul", null, map);
		} catch (final Exception e) {
			// Show a error box
			String msg = e.getMessage();
			String title = Labels.getLabel("messag.Error");

			MultiLineMessageBox.doSetTemplate();
			MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
					"ERROR", true);
		}
	}

	/**
	 * when the "refresh" button is clicked. <br>
	 * <br>
	 * Refreshes the view by calling the onCreate event manually.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnRefresh(Event event) throws InterruptedException {

		Events.postEvent("onCreate", userListWindow, event);
		userListWindow.invalidate();
	}
    
	/**
	 * The New Search
	 * 
	 * @author hari
	 * @param event
	 * @throws Exception
	 */
	public void onClick$btnSearch(Event event) {
		HibernateSearchObject<SecUser> hso = new HibernateSearchObject<SecUser>(
				SecUser.class, CommonUtils.PAGE_SIZE);
		
		hso.addFetch("karyawan");
		hso.addFetch("subUnit");

		if (StringUtils.isNotBlank(txtLoginName.getValue())) {
			hso.addFilter(new Filter("usrLoginname", "%"
					+ txtLoginName.getValue() + "%", Filter.OP_ILIKE));
		}

		if (StringUtils.isNotBlank(txtLastName.getValue())) {
			hso.addFilter(new Filter("usrLastname", "%"
					+ txtLastName.getValue() + "%", Filter.OP_ILIKE));
		}

		if (StringUtils.isNotBlank(txtEmail.getValue())) {
			hso.addFilter(new Filter("usrEmail", "%" + txtEmail.getValue()
					+ "%", Filter.OP_ILIKE));
		}

		if (0 != cmbEnabled.getSelectedIndex()) {
			hso.addFilter(new Filter("usrEnabled", Integer.parseInt(((String) cmbEnabled.getSelectedItem().getValue())), Filter.OP_EQUAL));
		}

		if (0 != cmbAccountExpired.getSelectedIndex()) {
			hso.addFilter(new Filter("usrAccountnonexpired", Integer.parseInt(((String) cmbAccountExpired.getSelectedItem().getValue())), Filter.OP_EQUAL));
		}
		
		if (0 != cmbCredentialExpired.getSelectedIndex()) {
			hso.addFilter(new Filter("usrCredentialsnonexpired", Integer.parseInt(((String) cmbCredentialExpired.getSelectedItem().getValue())), Filter.OP_EQUAL));
		}
		
		if (0 != cmbLocked.getSelectedIndex()) {
			hso.addFilter(new Filter("usrAccountnonlocked", Integer.parseInt(((String) cmbLocked.getSelectedItem().getValue())), Filter.OP_EQUAL));
		}
		
		SecUser anUser = ((UserImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSecUser();
		if (anUser != null && anUser.isAdmin()) {
			
		} else {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			hso.addFilter(new Filter("usrLoginname", userName, Filter.OP_EQUAL));
		}
		getPagedListWrapper().init(hso, listBoxUser, paging_UserList);
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// ++++++++++++++++++ getter / setter +++++++++++++++++++//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
