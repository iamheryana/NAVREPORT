package solusi.hapis.webui.security.groupright;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
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
import solusi.hapis.backend.model.SecRight;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.util.Codec.TipeSecurity;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SecGroupRightMainCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8560324855527106220L;
	
	protected Window windowsecGroupRightMain;
	
	// Groups
	protected Paging pagingSecGroup;
	protected Listbox listboxSecGroup;	
	protected Listheader listheaderGroupName;	
	protected Textbox txtb_groupname;
	private int startGroup;
	private ListModelList modelSecGroup = new ListModelList();
	private List<SecGroup> listSecGroup = new ArrayList<SecGroup>();
	
	// Granted Right
	protected Paging pagingSecGrantedRight;
	protected Listbox listboxSecGrantedRight;
	private int startGrantedRight;
	private ListModelList modelGrantedRight = new ListModelList();
	private List<SecRight> listGrantedRight = new ArrayList<SecRight>();
	
	private transient SecurityService securityService;
	
	public SecGroupRightMainCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		listheaderGroupName.setSortAscending(new FieldComparator("grpLongdescription", true));
		listheaderGroupName.setSortDescending(new FieldComparator("grpLongdescription", false));
		
		listboxSecGroup.setItemRenderer(renderDataGroup());
		pagingSecGroup.setPageSize(15);
		pagingSecGroup.addEventListener("onPaging", onPagingSecGroup());
		
		listSecGroup.clear();
		List<SecGroup> tempListGroup = securityService.getAllGroups();
		if (tempListGroup != null && tempListGroup.size() > 0) {
			listSecGroup.addAll(tempListGroup);
		}
		refreshModelSecGroup();
		
		listboxSecGrantedRight.setItemRenderer(renderDataRight());
		pagingSecGrantedRight.setPageSize(15);
		pagingSecGrantedRight.addEventListener("onPaging", onPagingSecGrantedRight());
		
		windowsecGroupRightMain.addEventListener(Events.ON_OK, onSubmitForm());
	}
	
	private ListitemRenderer renderDataGroup() {
		return new ListitemRenderer() {
			@Override
			public void render(Listitem item, Object data) {
				SecGroup detail = (SecGroup) data;

				Listcell lc = new Listcell(detail.getGrpLongdescription());
                lc.setParent(item);
							
				item.setValue(data);
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
	
	private void refreshModelSecGroup() {
		modelSecGroup.clear();
		SecGroup selectHeader = null;
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
			selectHeader = listSecGroup.get(startGroup);
		} else {
			pagingSecGroup.setDetailed(false);
			listboxSecGroup.setModel(modelSecGroup);
		}
		searchSecRight(selectHeader);
	}
	
	private ListitemRenderer renderDataRight() {
		return new ListitemRenderer() {
			@Override
			public void render(Listitem item, Object data) {
				SecRight detail = (SecRight) data;

				Listcell lc = new Listcell(detail.getRigName());
                lc.setParent(item);

				lc = new Listcell(TipeSecurity.getLabelByCode(detail.getRigType()));
                lc.setParent(item);
							
				item.setValue(data);
			}
		};
	}
	
	private EventListener onPagingSecGrantedRight(){
		return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				 PagingEvent pe = (PagingEvent) event;
		         int pageNo = pe.getActivePage();
		         startGrantedRight = pageNo * pagingSecGrantedRight.getPageSize();
		         refreshModelSecGrantedRight();
			}
		};
	}
	
	private void refreshModelSecGrantedRight() {
		modelGrantedRight.clear();
		if (listGrantedRight.size() > 0) {
			int end = 0;
			if(startGrantedRight + pagingSecGrantedRight.getPageSize() < listGrantedRight.size()) {
				end = startGrantedRight + pagingSecGrantedRight.getPageSize();
			} else {
				end = listGrantedRight.size();
			}
			
			modelGrantedRight.addAll(listGrantedRight.subList(startGrantedRight, end));
			pagingSecGrantedRight.setDetailed(true);
			pagingSecGrantedRight.setTotalSize(listGrantedRight.size());
			
			listboxSecGrantedRight.setModel(modelGrantedRight);
			listboxSecGrantedRight.setSelectedIndex(0);
		} else {
			pagingSecGrantedRight.setDetailed(false);
			listboxSecGrantedRight.setModel(modelGrantedRight);
		}
	}
	
	public void searchSecRight(SecGroup selectHeader) {
		listGrantedRight.clear();
		if (selectHeader != null) {
			List<SecRight> tempListGrantedRight = securityService.getGrantedSecRight(selectHeader.getId());
			if (tempListGrantedRight != null && tempListGrantedRight.size() > 0) {
				listGrantedRight.addAll(tempListGrantedRight);
			}
		}
		refreshModelSecGrantedRight();
	}
	
	public void onSelect$listboxSecGroup(Event event) {		
		if (listboxSecGroup.getSelectedItem() != null) {
			SecGroup groupSelect = (SecGroup) listboxSecGroup.getSelectedItem().getValue();
			
			if (groupSelect != null) {
				searchSecRight(groupSelect);				
			}
		}
	}	
	
	private EventListener onSubmitForm() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
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
		};
	}
	
	public void onClick$btnSearch(Event event) throws InterruptedException {
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
	
	public void onClick$btnClear(Event event) throws InterruptedException {
		txtb_groupname.setValue(null);
		listSecGroup.clear();
		
		List<SecGroup> tempListGroup = securityService.getListSecGroup(null);
		if (tempListGroup != null && tempListGroup.size() > 0) {
			listSecGroup.addAll(tempListGroup);
		}
		refreshModelSecGroup();
    }

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}
