package solusi.hapis.webui.security.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecLog;
import solusi.hapis.backend.model.SecParam;
import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.backend.security.service.UserService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.ButtonStatusCtrl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleCommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class SecUsersMainCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3025232569227182778L;

	protected Window windowSecUsersMain;

	protected Tabbox tabbox_SecUsersMain;
	protected Tab tabSecUsersList;
	protected Tab tabSecUsersDetail;
	protected Tab tabSecUsersPrint;
	protected Tabpanel tabPanelSecUsersList;
	protected Tabpanel tabPanelSecUsersDetail;
	protected Tabpanel tabPanelSecUsersPrint;

	protected Button btnNew;
	protected Button btnEdit;
	protected Button btnDelete;
	protected Button btnSave;
	protected Button btnCancel;
	protected Button btnSearch;
	protected Button btnClear;
	protected Button btnListing;
	protected Button btnOK;

	protected Button btnActivate;
	protected Button btnInactivate;
	protected Button btnReactivePass;

	private ButtonStatusCtrl btnCtrlSecUsers;
	private final String btnCtroller_ClassPrefix = "button_users_";

	private SecUser selectedSecUser;

	private transient UserService userService;
	private SecurityService securityService = (SecurityService) SpringUtil
			.getBean("securityService");

	private SecUsersListCtrl secUsersListCtrl;
	private SecUsersDetailCtrl secUsersDetailCtrl;
	private SecUsersPrintCtrl secUsersPrintCtrl;

	// Zul
	private String zulPageDetail = "/WEB-INF/pages/security/user/SecUsersDetail.zul";
	private String zulPageList = "/WEB-INF/pages/security/user/SecUsersList.zul";
	private String zulPagePrint = "/WEB-INF/pages/security/user/SecUsersPrint.zul";

	/**
	 * Form State <br/>
	 * 0 = inquiry <br/>
	 * 1 = new <br/>
	 * 2 = edit <br/>
	 * 3 = reactivate <br/>
	 * 
	 * @editby Rachmat
	 */
	private int state = 0;

	public SecUsersMainCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		this.self.setAttribute("controller", this, false);
	}

	public void onCreate$windowSecUsersMain(Event event) throws Exception {
		windowSecUsersMain.setContentStyle("padding:0px;");

		btnCtrlSecUsers = new ButtonStatusCtrl(getUserWorkspace(),
				btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete,
				btnListing, btnOK, btnSave, btnCancel, btnSearch, btnClear);

		btnCtrlSecUsers.addButtonOther1(btnActivate, "Activate");
		btnCtrlSecUsers.addButtonOther2(btnInactivate, "Suspend");
		btnCtrlSecUsers.addButtonOther3(btnReactivePass, "Reactivate");

		btnCtrlSecUsers.setInitInquiryButton();

		tabSecUsersList.setSelected(true);

		if (tabPanelSecUsersList != null)
			ZksampleCommonUtils.createTabPanelContent(tabPanelSecUsersList,
					this, "ModuleMainController", zulPageList);

	}

	public void onSelect$tabSecUsersList(Event event) throws IOException {
		if (tabPanelSecUsersList.getFirstChild() != null) {

			tabSecUsersList.setSelected(true);
			btnCtrlSecUsers.setInitInquiryButton();
			getSecUsersListCtrl().doFillListbox();

			return;
		}

		if (tabPanelSecUsersList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelSecUsersList,
					this, "ModuleMainController", zulPageList);
		}

	}

	public void onSelect$tabSecUsersDetail(Event event) throws IOException {
		if (tabPanelSecUsersDetail.getFirstChild() != null) {
			tabSecUsersDetail.setSelected(true);

			// Render Inisialisasi posisi awal
			getSecUsersDetailCtrl().doRenderMode("View");
			getSecUsersDetailCtrl().doRenderCheckBox();

			btnCtrlSecUsers.setInitInquiryButton();

			if (getSecUsersDetailCtrl().getBinder() != null) {
				getSecUsersDetailCtrl().getBinder().loadAll();
			}


			return;
		}

		if (tabPanelSecUsersDetail != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelSecUsersDetail,
					this, "ModuleMainController", zulPageDetail);
		}
	}

	public void onSelect$tabSecUsersPrint(Event event) throws IOException {
		// Check if the tabpanel is already loaded
		if (tabPanelSecUsersPrint != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelSecUsersPrint,
					this, "ModuleMainController", zulPagePrint);
		}

		if (tabPanelSecUsersPrint.getFirstChild() != null) {
			tabSecUsersPrint.setSelected(true);
			getSecUsersPrintCtrl().doReadOnlyMode(true);
		}
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// +++++++++++++++++++ Button Events +++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	public void onClick$btnNew(Event event) throws InterruptedException {

		if (getSecUsersDetailCtrl() == null) {
			Events.sendEvent(new Event("onSelect", tabSecUsersDetail, null));
		} else if (getSecUsersDetailCtrl().binder == null) {
			Events.sendEvent(new Event("onSelect", tabSecUsersDetail, null));
		}

		final SecUser anSesi = new SecUser();

		// Set Default Value Di sini ------------------------------------- start

		getSecUsersDetailCtrl().txt_Password.setValue(getRandomNumbers());

		anSesi.setFlagActiv("Y");
		anSesi.setAccessCabang("A");
		anSesi.setAccessGolongan("A");
		

		SecParam param = securityService.getSecParamByID(new Long(1));
		int vWarningDay = 0;
		if (param != null) {
			if (CommonUtils.isNotEmpty(param.getWarningDay())) {
				vWarningDay = param.getWarningDay();
			}
		}
		Date expiredDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(expiredDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_YEAR, vWarningDay);

		anSesi.setExpiredDate(calendar.getTime());
		anSesi.setAccessCabang("A");
		anSesi.setAccessGolongan("A");
		
		setSelectedSecUser(anSesi);
		// --------------------------------------------------------------- end

		// set button
		btnCtrlSecUsers.setInitFormButton();

		// select tab Detail
		tabSecUsersDetail.setSelected(true);
		renderTabAndTitle("New");

		state = 1;

		if (getSecUsersDetailCtrl().getBinder() != null) {
			getSecUsersDetailCtrl().getBinder().loadAll();
		}

		// set render layar
		getSecUsersDetailCtrl().doRenderMode("New");
		getSecUsersDetailCtrl().doRenderCheckBox();

		
		
	}

	public void onClick$btnEdit(Event event) throws InterruptedException {

		if (tabPanelSecUsersDetail.getFirstChild() != null) {
			getSecUsersDetailCtrl().getBinder().loadAll();
		}

		if (getSecUsersDetailCtrl() == null) {
			Events.sendEvent(new Event("onSelect", tabSecUsersDetail, null));
		} else if (getSecUsersDetailCtrl().getBinder() == null) {
			Events.sendEvent(new Event("onSelect", tabSecUsersDetail, null));
		}

		// set button
		btnCtrlSecUsers.setInitFormButton();

		// select tab Detail
		tabSecUsersDetail.setSelected(true);
		renderTabAndTitle("Edit");

		state = 2;

		// set render layar
		getSecUsersDetailCtrl().doRenderMode("Edit");
		getSecUsersDetailCtrl().doRenderCheckBox();
		
		
		

	}

	public void onClick$btnDelete(Event event) throws InterruptedException {
		if (getSelectedSecUser() != null) {
			final SecUser anSecUser = getSelectedSecUser();
			if (anSecUser != null) {

				// Show a confirm box
				String deleteRecord = anSecUser.getUsrLoginname();
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
								try {
									getUserService().deleteUser(anSecUser);
									setSelectedSecUser(null);

									// refresh the list
									getSecUsersListCtrl().doFillListbox();
								} catch (DataAccessException e) {
									ZksampleMessageUtils.showErrorMessage(e
											.getMostSpecificCause().toString());
								}
							}
						}

				) == MultiLineMessageBox.YES) {
				}

			}

			btnCtrlSecUsers.setInitInquiryButton();
			setSelectedSecUser(null);

			// refresh the list
			getSecUsersListCtrl().doFillListbox();
			tabSecUsersList.setSelected(true);
		}
	}

	public void onClick$btnSave(Event event) throws InterruptedException {
		String vErrMsg = getSecUsersDetailCtrl().validasiBusinessRules();
		String vUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		String vActivity = null;
		if (vErrMsg != null) {
			ZksampleMessageUtils.showErrorMessage(vErrMsg);
			return;
		}

		getSecUsersDetailCtrl().getBinder().saveAll();

		try {
			if (state == 1) {
				
				String vNewUser = getSelectedSecUser().getUsrLoginname();
				vNewUser = vNewUser.trim().toUpperCase();
				
				vActivity = "Create User : "+vNewUser;
				
				getSelectedSecUser().setUsrLoginname(vNewUser);
				
				String passEnc = ".";
				PasswordEncoder pe = new ShaPasswordEncoder();
				passEnc = pe.encodePassword(
						getSecUsersDetailCtrl().txt_Password.getValue(),
						getSelectedSecUser().getUsrLoginname());

				getSelectedSecUser().setUsrPassword(passEnc);
				
				getUserService().insertUser(getSelectedSecUser());
				onClick$btnNew(event);
			} else if (state == 2) {
				vActivity = "Update User : "+getSelectedSecUser().getUsrLoginname();
				
				getUserService().update(getSelectedSecUser());
				
				// refresh the list
				btnCtrlSecUsers.setInitInquiryButton();
				getSecUsersListCtrl().doFillListbox();

				tabSecUsersList.setSelected(true);

				renderTabAndTitle("Save");
			} else if (state == 3) {
				vActivity = "Reactivate User : "+getSelectedSecUser().getUsrLoginname();
				
				String passEnc = ".";
				PasswordEncoder pe = new ShaPasswordEncoder();
				passEnc = pe.encodePassword(
						getSecUsersDetailCtrl().txt_Password.getValue(),
						getSelectedSecUser().getUsrLoginname());

				getSelectedSecUser().setUsrPassword(passEnc);

				getUserService().update(getSelectedSecUser());
				// refresh the list
				btnCtrlSecUsers.setInitInquiryButton();
				getSecUsersListCtrl().doFillListbox();

				tabSecUsersList.setSelected(true);

				renderTabAndTitle("Save");
			}
			
			SecLog newLog = new SecLog(vActivity, vUserLogin, new Date(), CommonUtils.convertClientAddress(SecurityContextHolder.getContext().getAuthentication()));
			securityService.save(newLog);
		} catch (DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause()
					.toString());
			return;
		}
	}

	public void onClick$btnCancel(Event event) throws InterruptedException {
		btnCtrlSecUsers.setInitInquiryButton();

		tabSecUsersList.setSelected(true);

		renderTabAndTitle("Back");

		if (state == 1) {
			getSecUsersListCtrl().doFillListbox();
		}
	}

	public void onClick$btnActivate(Event event) throws InterruptedException {

		if (getSelectedSecUser() != null) {
			if (getSelectedSecUser().getFlagActiv().equals("Y")) {
				ZksampleMessageUtils
						.showErrorMessage("Status User sudah Aktif");
				return;
			} else {
				String vUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
				String vActivity = "Activate User : "+getSelectedSecUser().getUsrLoginname();
				
				
				try {
					getSelectedSecUser().setFlagActiv("Y");
					getUserService().update(getSelectedSecUser());

					SecLog newLog = new SecLog(vActivity, vUserLogin, new Date(), CommonUtils.convertClientAddress(SecurityContextHolder.getContext().getAuthentication()));
					securityService.save(newLog);
					
					getSecUsersListCtrl().doFillListbox();
				} catch (DataAccessException e) {
					ZksampleMessageUtils.showErrorMessage(e
							.getMostSpecificCause().toString());
					return;
				}
			}
		}

	}

	public void onClick$btnInactivate(Event event) throws InterruptedException {

		if (getSelectedSecUser() != null) {
			if (getSelectedSecUser().getFlagActiv().equals("Y")) {
				String vUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
				String vActivity = "Suspend User : "+getSelectedSecUser().getUsrLoginname();
				
				try {
					getSelectedSecUser().setFlagActiv("N");
					getUserService().update(getSelectedSecUser());
					
					SecLog newLog = new SecLog(vActivity, vUserLogin, new Date(), CommonUtils.convertClientAddress(SecurityContextHolder.getContext().getAuthentication()));
					securityService.save(newLog);

					getSecUsersListCtrl().doFillListbox();
				} catch (DataAccessException e) {
					ZksampleMessageUtils.showErrorMessage(e
							.getMostSpecificCause().toString());
					return;
				}
			} else {
				ZksampleMessageUtils
						.showErrorMessage("Status User sudah Inaktif");
				return;
			}
		}

	}

	public void onClick$btnReactivePass(Event event)
			throws InterruptedException {

		if (tabPanelSecUsersDetail.getFirstChild() != null) {
			getSecUsersDetailCtrl().getBinder().loadAll();
		}

		if (getSecUsersDetailCtrl() == null) {
			Events.sendEvent(new Event("onSelect", tabSecUsersDetail, null));
		} else if (getSecUsersDetailCtrl().getBinder() == null) {
			Events.sendEvent(new Event("onSelect", tabSecUsersDetail, null));
		}

		getSecUsersDetailCtrl().txt_Password.setValue(getRandomNumbers());

		SecParam param = securityService.getSecParamByID(new Long(1));
		int vWarningDay = 0;
		if (param != null) {
			if (CommonUtils.isNotEmpty(param.getWarningDay())) {
				vWarningDay = param.getWarningDay();
			}
		}
		Date expiredDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(expiredDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_YEAR, vWarningDay);

		getSelectedSecUser().setExpiredDate(calendar.getTime());

		// set button
		btnCtrlSecUsers.setInitFormButton();

		// select tab Detail
		tabSecUsersDetail.setSelected(true);
		renderTabAndTitle("Reactivate");

		state = 3;

		// set render layar
		getSecUsersDetailCtrl().doRenderMode("Reactivate");
		getSecUsersDetailCtrl().doRenderCheckBox();

	}

	public void onClick$btnOK(Event event) throws InterruptedException {
		final Window win = (Window) Path.getComponent("/outerIndexWindow");
		// new CRreportWindow(win, getSecUsersPrintCtrl().getParameterReport());
	}

	public void onClick$btnListing(Event event) throws InterruptedException {
		// check first, if the tabs are created, if not than create it
		if (getSecUsersPrintCtrl() == null) {
			Events.sendEvent(new Event("onSelect", tabSecUsersPrint, null));
		}

		if (tabPanelSecUsersPrint != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelSecUsersPrint,
					this, "ModuleMainController", zulPagePrint);
		}

		getSecUsersPrintCtrl().doReadOnlyMode(false);

		btnCtrlSecUsers.setInitPrintButton();
		tabSecUsersPrint.setSelected(true);

	}

	public void onClick$btnSearch(Event event) throws InterruptedException {
		if (getSecUsersListCtrl() != null) {
			getSecUsersListCtrl().searchTable();
			tabSecUsersList.setSelected(true);
		}
	}

	public void onClick$btnClear(Event event) throws InterruptedException {
		if (getSecUsersListCtrl() != null) {
			getSecUsersListCtrl().clearSearchBox();
			getSecUsersListCtrl().searchTable();
			tabSecUsersList.setSelected(true);
		}
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	private String getRandomNumbers() {
		Random rand = new Random();
		int tempNum = rand.nextInt();
		int num = 0;
		String value = "";

		if (tempNum < 0) {
			num = tempNum * -1;
		} else {
			num = tempNum;
		}
		Integer intVal = new Integer(num);
		value = intVal.toString().substring(0, 6);
		return value;
	}

	private void renderTabAndTitle(String requestStatus) {
		if (requestStatus.equals("New") || requestStatus.equals("Edit")
				|| requestStatus.equals("Reactivate")) {
			tabSecUsersDetail.setLabel(Labels.getLabel("common.Details")
					+ " - " + requestStatus);
			tabSecUsersList.setDisabled(true);
			tabSecUsersPrint.setDisabled(true);
		}

		if (requestStatus.equals("Back") || requestStatus.equals("Save")) {
			tabSecUsersDetail.setLabel(Labels.getLabel("common.Details"));
			tabSecUsersList.setDisabled(false);
			tabSecUsersPrint.setDisabled(false);
		}
	}

	public SecUser getSelectedSecUser() {
		return selectedSecUser;
	}

	public void setSelectedSecUser(SecUser selectedSecUser) {
		this.selectedSecUser = selectedSecUser;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SecUsersListCtrl getSecUsersListCtrl() {
		return secUsersListCtrl;
	}

	public void setSecUsersListCtrl(SecUsersListCtrl secUsersListCtrl) {
		this.secUsersListCtrl = secUsersListCtrl;
	}

	public SecUsersDetailCtrl getSecUsersDetailCtrl() {
		return secUsersDetailCtrl;
	}

	public void setSecUsersDetailCtrl(SecUsersDetailCtrl secUsersDetailCtrl) {
		this.secUsersDetailCtrl = secUsersDetailCtrl;
	}

	public void setSecUsersPrintCtrl(SecUsersPrintCtrl secUsersPrintCtrl) {
		this.secUsersPrintCtrl = secUsersPrintCtrl;
	}

	public SecUsersPrintCtrl getSecUsersPrintCtrl() {
		return secUsersPrintCtrl;
	}

}
