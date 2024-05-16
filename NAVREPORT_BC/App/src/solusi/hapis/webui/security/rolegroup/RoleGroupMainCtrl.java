package solusi.hapis.webui.security.rolegroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import solusi.hapis.backend.model.SecGroup;
import solusi.hapis.backend.model.SecRole;
import solusi.hapis.backend.model.SecRolegroup;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class RoleGroupMainCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8500861096895901199L;
	
	protected Window windowRoleGroupMain;
	
	// Granted Role
	protected Paging pagingSecRole;
	protected Listbox listboxSecRole;
	protected Listheader listheaderRoleName;	
	protected Textbox txtb_rolename;
	private int startRole;
	private ListModelList modelSecRole = new ListModelList();
	private List<SecRole> listSecRole = new ArrayList<SecRole>();
	
	// Groups
	protected Paging pagingSecGroup;
	protected Listbox listboxSecGroup;
	protected Listheader listheaderGroupName;
	protected Textbox txtb_groupname;
	private int startGroup;
	private ListModelList modelSecGroup = new ListModelList();
	private List<SecGroup> listSecGroup = new ArrayList<SecGroup>();
	
	private transient SecurityService securityService;
	
	protected Button btnSave;
	protected Button btnSearch;
	protected Button btnClear;

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		listheaderRoleName.setSortAscending(new FieldComparator("rolShortdescription", true));
		listheaderRoleName.setSortDescending(new FieldComparator("rolShortdescription", false));
		
		listboxSecRole.setItemRenderer(renderDataRole());
		
		pagingSecRole.setPageSize(15);
		pagingSecRole.addEventListener("onPaging", onPagingSecRole());
		
		listheaderGroupName.setSortAscending(new FieldComparator("grpLongdescription", true));
		listheaderGroupName.setSortDescending(new FieldComparator("grpLongdescription", false));
		
		listboxSecGroup.setItemRenderer(renderDataGroup());
		
		pagingSecGroup.setPageSize(15);
		pagingSecGroup.addEventListener("onPaging", onPagingSecGroup());
		
		listSecRole.clear();
		List<SecRole> tempListRole = securityService.getAllRoles();
		if (tempListRole != null && tempListRole.size() > 0) {
			listSecRole.addAll(tempListRole);
		}
		refreshModelSecRole();
		
		listSecGroup.clear();
		//List<SecGroup> tempList = securityService.getGrantedSecRight(selectHeader.getId());
		List<SecGroup> tempListGroup = securityService.getAllGroups();
		if (tempListGroup != null && tempListGroup.size() > 0) {
			listSecGroup.addAll(tempListGroup);
		}
		refreshModelSecGroup();
		
		windowRoleGroupMain.addEventListener(Events.ON_OK, onSubmitForm());
	}	
	
	private EventListener onSubmitForm() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (txtb_rolename.getValue() != null && txtb_rolename.getValue().equals("") == false) {
					listSecRole.clear();
					List<SecRole> tempListRole = securityService.getRolesLikeRoleName(txtb_rolename.getValue());
					if (tempListRole != null && tempListRole.size() > 0) {
						listSecRole.addAll(tempListRole);
					}
					refreshModelSecRole();
				}
				if (txtb_groupname.getValue() != null && txtb_groupname.getValue().equals("") == false) {
					listSecGroup.clear();
					Map<Object, Object> parameterInput = new HashMap<Object, Object>();
					if (txtb_groupname.getValue() != null && txtb_groupname.getValue().equals("") == false) {
						parameterInput.put("grpLongdescription", txtb_groupname.getValue());
					}
					
					List<SecGroup> tempListGroup = securityService.getListSecGroup(parameterInput);
					if (tempListGroup != null && tempListGroup.size() > 0) {
						listSecGroup.addAll(tempListGroup);
					}
					refreshModelSecGroup();
				}
			}
		};
	}
	
	private ListitemRenderer renderDataRole() {
		return new ListitemRenderer() {
			@Override
			public void render(Listitem item, Object data) {
				SecRole detail = (SecRole) data;
		        
				Listcell lc = new Listcell(detail.getRolShortdescription());
                lc.setParent(item);
							
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}
	
	private ListitemRenderer renderDataGroup() {
		return new ListitemRenderer() {
			@Override
			public void render(Listitem item, Object data) {
				SecGroup detail = (SecGroup) data;

		        // get the role for which we pull the data
				Listcell lc = new Listcell();
		        final Checkbox cb = new Checkbox();
				if (listboxSecRole.getSelectedItem() != null && listboxSecRole.getSelectedItem().getValue() != null) {
					SecRole role = (SecRole) listboxSecRole.getSelectedItem().getValue();
					if (role != null) {
						if (securityService.isGroupInRole(detail, role)) {
			                cb.setChecked(true);
			            } else {
			                cb.setChecked(false);
			            }
					} else {
			            cb.setChecked(false);
			        }

			        lc.appendChild(cb);
			        lc.setParent(item);
				}

				lc = new Listcell(detail.getGrpLongdescription());
                lc.setParent(item);
							
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}
	
	private EventListener onPagingSecGroup(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startGroup = pageNo * pagingSecGroup.getPageSize();
		         refreshModelSecGroup();
			}
		};
	}
	
	private EventListener onPagingSecRole(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startRole = pageNo * pagingSecRole.getPageSize();
		         refreshModelSecGroup();
			}
		};
	}
	
	private void refreshModelSecRole() {
		modelSecRole.clear();
//		SecRole selectHeader = null;
		if (listSecRole.size() > 0) {
			int end = 0;
			if(startRole + pagingSecRole.getPageSize() < listSecRole.size()) {
				end = startRole + pagingSecRole.getPageSize();
			} else {
				end = listSecRole.size();
			}
			
			modelSecRole.addAll(listSecRole.subList(startRole, end));
			pagingSecRole.setDetailed(true);
			pagingSecRole.setTotalSize(listSecRole.size());
			
			listboxSecRole.setModel(modelSecRole);
			listboxSecRole.setSelectedIndex(0);
//			selectHeader = listSecRole.get(startRole);
		} else {
			pagingSecRole.setDetailed(false);
			listboxSecRole.setModel(modelSecRole);
		}
//		searchSecGroup(selectHeader);
	}
	
	private void refreshModelSecGroup() {
		modelSecGroup.clear();
		if (listSecGroup.size() > 0) {
			int end = 0;
			if(startGroup + pagingSecGroup.getPageSize() < listSecGroup.size()) {
				end = startGroup + pagingSecGroup.getPageSize();
			} else {
				end = listSecGroup.size();
			}
			
			modelSecGroup.addAll(listSecGroup.subList(startGroup, end));
			pagingSecGroup.setDetailed(true);
			pagingSecGroup.setTotalSize(listSecGroup.size());
			
			listboxSecGroup.setModel(modelSecGroup);
			listboxSecGroup.setSelectedIndex(0);
		} else {
			pagingSecGroup.setDetailed(false);
			listboxSecGroup.setModel(modelSecGroup);
		}
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	public void onClick$btnSave(Event event) throws InterruptedException {
		List<Listitem> li = this.listboxSecGroup.getItems();

        for (Listitem listitem : li) {

            Listcell lc = (Listcell) listitem.getFirstChild();
            Checkbox cb = (Checkbox) lc.getFirstChild();

            if (cb != null) {

                if (cb.isChecked() == true) {

                    // Get the group object by casting
                    SecGroup aGroup = (SecGroup) listitem.getAttribute("data");
                    
                    // get the role
                    SecRole aRole = null;
                    if (listboxSecRole.getSelectedItem() != null && listboxSecRole.getSelectedItem().getValue() != null) {
                    	aRole = (SecRole) listboxSecRole.getSelectedItem().getValue();
                    }

                    // check if the item is newly checked. If so we cannot found
                    // it in the SecGroupRight-table
                    SecRolegroup aRoleGroup = getSecurityService().getRolegroupByRoleAndGroup(aRole, aGroup);

                    // if new, we make a newly Object
                    if (aRoleGroup == null) {
                        aRoleGroup = getSecurityService().getNewSecRolegroup();
                        aRoleGroup.setSecGroup(aGroup);
                        aRoleGroup.setSecRole(aRole);
                    }

                    try {
                        // save to DB
                        getSecurityService().saveOrUpdate(aRoleGroup);
                    } catch (DataAccessException e) {
                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
                    }

                } else if (cb.isChecked() == false) {

                    // Get the group object by casting
                    SecGroup aGroup = (SecGroup) listitem.getAttribute("data");
                    // get the role
                    SecRole aRole = null;
                    if (listboxSecRole.getSelectedItem() != null && listboxSecRole.getSelectedItem().getValue() != null) {
                    	aRole = (SecRole) listboxSecRole.getSelectedItem().getValue();
                    }

                    // check if the item is newly unChecked. If so we must
                    // found it in the SecRolegroup-table
                    SecRolegroup aRoleGroup = getSecurityService().getRolegroupByRoleAndGroup(aRole, aGroup);

                    if (aRoleGroup != null) {
                        // delete from DB
                        getSecurityService().delete(aRoleGroup);
                    }
                }
            }
        }
    }
	
	public void onSelect$listboxSecRole(Event event) throws InterruptedException {
		if (listboxSecRole.getSelectedItem() != null && listboxSecRole.getSelectedItem().getValue() != null) {
			listSecGroup.clear();
			//List<SecGroup> tempList = securityService.getGrantedSecRight(selectHeader.getId());
			List<SecGroup> tempListGroup = securityService.getAllGroups();
			if (tempListGroup != null && tempListGroup.size() > 0) {
				listSecGroup.addAll(tempListGroup);
			}
			refreshModelSecGroup();
		}
	}
	
	public void onClick$btnSearch(Event event) throws InterruptedException {
		if (txtb_rolename.getValue() != null && txtb_rolename.getValue().equals("") == false) {
			listSecRole.clear();
			List<SecRole> tempListRole = securityService.getRolesLikeRoleName(txtb_rolename.getValue());
			if (tempListRole != null && tempListRole.size() > 0) {
				listSecRole.addAll(tempListRole);
			}
			refreshModelSecRole();
		}
		if (txtb_groupname.getValue() != null && txtb_groupname.getValue().equals("") == false) {
			listSecGroup.clear();
			Map<Object, Object> parameterInput = new HashMap<Object, Object>();
			if (txtb_groupname.getValue() != null && txtb_groupname.getValue().equals("") == false) {
				parameterInput.put("grpLongdescription", txtb_groupname.getValue());
			}
			
			List<SecGroup> tempListGroup = securityService.getListSecGroup(parameterInput);
			if (tempListGroup != null && tempListGroup.size() > 0) {
				listSecGroup.addAll(tempListGroup);
			}
			refreshModelSecGroup();
		}
	}
	
	public void onClick$btnClear(Event event) throws InterruptedException {
		txtb_groupname.setValue(null);
		txtb_rolename.setValue(null);
		
		listSecRole.clear();
		List<SecRole> tempListRole = securityService.getAllRoles();
		if (tempListRole != null && tempListRole.size() > 0) {
			listSecRole.addAll(tempListRole);
		}
		refreshModelSecRole();
		
		List<SecGroup> tempListGroup = securityService.getAllGroups();
		if (tempListGroup != null && tempListGroup.size() > 0) {
			listSecGroup.addAll(tempListGroup);
		}
		refreshModelSecGroup();
	}
	
}
