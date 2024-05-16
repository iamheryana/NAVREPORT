package solusi.hapis.webui.security.roleright;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecRight;
import solusi.hapis.backend.model.SecRole;
import solusi.hapis.backend.model.SecRoleright;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class RightNewSelect extends Window implements Serializable {

	private static final long serialVersionUID = 8203411057060005445L;

	private SecurityService securityService = (SecurityService) SpringUtil.getBean("securityService");

	
	private String selected;
	private String type;
	private SecRoleright roleRight;
	private SecRole role;
	
	
	private List<SecRoleright> listRoleRight;

	private Listbox listboxSecRight;
//	private ListModelList modelListSecRight = new ListModelList();
	private List<SecRight> listSecRight = new ArrayList<SecRight>();


	
	public static String show(Component parent, SecRole secRole, SecRoleright secRoleright, List<SecRoleright> listSecRoleRight, String type) {
		return new RightNewSelect(parent, secRole, secRoleright, listSecRoleRight, type).getSelected();
	}

	private RightNewSelect(Component parent, SecRole secRole, SecRoleright secRoleright, List<SecRoleright> listSecRoleRight, String type) {
		super();
		setSelected("Y");
		setRoleRight(secRoleright);
		setType(type);
		setRole(secRole);
		setListRoleRight(listSecRoleRight);
		
		setParent(parent);
		createBox();
	}

	private void createBox() {
		this.setBorder("none");
		this.setWidth("800px");
		this.setHeight("550px");

		Div div = new Div();
		div.setSclass("z-toolbar");
		div.setStyle("padding:0");
		Hbox hbox = new Hbox();
		hbox.setPack("stretch");
		hbox.setSclass("hboxRemoveWhiteStrips");
		hbox.setWidth("100%");
		Toolbar toolbar = new Toolbar();
		toolbar.setAlign("end");
		toolbar.setStyle("float:right; border-style: none;");
		toolbar.setHeight("28px");

		
		toolbar.setParent(hbox);
		hbox.setParent(div);
		div.setParent(this);

		Panel panel = new Panel();
		String vTitle = "Right";
		if(CommonUtils.isNotEmpty(getType())){
			vTitle = vTitle+" - "+getType();
			
			if(getType().equals("Category") == false){
				if(CommonUtils.isNotEmpty(getRoleRight())){
					vTitle = vTitle+" - "+getRoleRight().getSecRight().getRigDesc();
				}
			}
		}
		
		panel.setTitle(vTitle);
		panel.setBorder("none");
		panel.setParent(this);

		createListBox(this);

		try {
			this.doModal();
		} catch (final SuspendNotAllowedException e) {
			this.detach();
		} catch (final InterruptedException e) {
			this.detach();
		}
	}

	private void createListBox(Component parent) {
		Groupbox groupbox = new Groupbox();
		groupbox.setMold("3d");
		groupbox.setClosable(false);
		groupbox.setWidth("100%");
		groupbox.setHeight("100%");

		Grid grid = new Grid();

		Rows rows = new Rows();

		Row row1 = new Row();
			Div div = new Div();
			div.setSclass("z-toolbar");
			div.setStyle("padding:0");
	
				Hbox hbox = new Hbox();
				hbox.setPack("stretch");
				hbox.setSclass("hboxRemoveWhiteStrips");
				hbox.setWidth("100%");
				
					Toolbar toolbar = new Toolbar();
					toolbar.setAlign("end");
					toolbar.setHeight("26px");
			
					Button btnSave = new Button();
					btnSave.setHeight("24px");
					btnSave.setLabel(Labels.getLabel("common.button.save"));
					btnSave.addEventListener(Events.ON_CLICK, onClickBtnSave());
					btnSave.setParent(toolbar);
					
					Button btnBack = new Button();
					btnBack.setHeight("24px");
					btnBack.setLabel(Labels.getLabel("common.button.cancel"));
					btnBack.addEventListener(Events.ON_CLICK, onClickBtnBack());
					btnBack.setParent(toolbar);
						
					Space spc = new Space();
					spc.setWidth("10px");
					spc.setParent(toolbar);
					
					toolbar.setParent(hbox);
				hbox.setParent(div);
			div.setParent(row1);
		row1.setParent(rows);
		
		Row row2 = new Row();

			listboxSecRight = new Listbox();
			listboxSecRight
					.setStyle("border-top-width: 0px; border-left-width: 0px; border-right-width: 0px; border-bottom-width: 1px;");
			listboxSecRight.setVflex(true);
			listboxSecRight.setHeight("250px");
			listboxSecRight.setWidth("100%");
			listboxSecRight.setMultiple(true);
			listboxSecRight.setCheckmark(true);
			listboxSecRight.setItemRenderer(itemRenderSecRight());
			
			listSecRight.clear();
			
			Map<Object, Object> parameterInputDetail = new HashMap<Object, Object>();

			if(getType().equals("Category") == true){
				parameterInputDetail.put("rigType", 1);
				parameterInputDetail.put("parentId", new Long(0));
			} else {
				if (getRoleRight() != null){
					parameterInputDetail.put("parentId", getRoleRight().getSecRight().getId());
				} else {
					parameterInputDetail.put("parentId",  new Long(-1));
				}
				
				if(getType().equals("Sub Category") == true){
					parameterInputDetail.put("rigType", 2);
				} else {
					if(getType().equals("Item") == true){
						parameterInputDetail.put("rigType", 3);
					} else {
						parameterInputDetail.put("rigType", 4);
					}
				}
			}
			

			List<SecRight> tempListSecRight = new ArrayList<SecRight>();
			tempListSecRight = securityService.getListSecRight(parameterInputDetail);
			
			if (tempListSecRight != null && tempListSecRight.size() > 0) {
				listSecRight.addAll(tempListSecRight);
			}
	
			ListModelList modelList = new ListModelList();
			
			modelList.addAll(listSecRight);
			listboxSecRight.setModel(modelList);
			
			
			Listhead listhead = new Listhead();
			listhead.setSizable(true);
	
			Listheader listheader = new Listheader();
			listheader.setSclass("FDListBoxHeader1");
			listheader.setSort("auto");
			listheader.setLabel("Nama");
			listheader.setSortAscending(new FieldComparator("rigDesc", true));
			listheader.setSortDescending(new FieldComparator("rigDesc", false));
			listheader.setParent(listhead);
		
			listhead.setParent(listboxSecRight);
			listboxSecRight.setParent(row2);
		row2.setParent(rows);

		rows.setParent(grid);

		grid.setParent(groupbox);

		groupbox.setParent(parent);
	}


	
	
	private ListitemRenderer itemRenderSecRight() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object obj) throws Exception {
				renderData(item, (SecRight) obj);
			}

			private void renderData(Listitem item, SecRight entity) {
				Listcell lc;
				
				
				lc = new Listcell(entity.getRigDesc());
				lc.setParent(item);

				item.setAttribute("data", entity);
				item.setValue(entity);
			}
		};
	}

	
	private EventListener onClickBtnSave() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				@SuppressWarnings("unchecked")
				Set<Listitem>  vSelectedItems = listboxSecRight.getSelectedItems();
			
				if (vSelectedItems != null){

					for (Listitem vEachItem : vSelectedItems){
						SecRight right = (SecRight) vEachItem.getAttribute("data");
						boolean vSkippedInsert = false;
						
						if(right != null){
							for (SecRoleright aSecRoleRight : getListRoleRight()){
								
								if(aSecRoleRight != null){
									if(aSecRoleRight.getSecRight() != null){
										if (aSecRoleRight.getSecRight().getId() == right.getId()){
											vSkippedInsert = true;
											break;
										}
									}
								}
							}
							
							if(vSkippedInsert != true){
								SecRoleright aNewSecRoleRight = new SecRoleright();
								
								
								aNewSecRoleRight.setSecRight(right);
								if(getType().equals("Category")){
									aNewSecRoleRight.setSecRole(getRole());
									aNewSecRoleRight.setParentRlrId(new Long(0));
								} else {
									aNewSecRoleRight.setSecRole(getRoleRight().getSecRole());
									aNewSecRoleRight.setParentRlrId(getRoleRight().getId());
								}
								
								
								try {
									securityService.save(aNewSecRoleRight);									
								} catch (DataAccessException e) {
									ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
									return;
								}
								
								
							}
						}
						
					}
					getWindow().onClose();
				}
			}
		};
	}


	
	private EventListener onClickBtnBack() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				setSelected(null);
				getWindow().onClose();
			}
		};
	}


	private Window getWindow() {
		return this;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public SecRoleright getRoleRight() {
		return roleRight;
	}

	public void setRoleRight(SecRoleright roleRight) {
		this.roleRight = roleRight;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SecRoleright> getListRoleRight() {
		return listRoleRight;
	}

	public void setListRoleRight(List<SecRoleright> listRoleRight) {
		this.listRoleRight = listRoleRight;
	}

	public SecRole getRole() {
		return role;
	}

	public void setRole(SecRole role) {
		this.role = role;
	}
	
	

}


