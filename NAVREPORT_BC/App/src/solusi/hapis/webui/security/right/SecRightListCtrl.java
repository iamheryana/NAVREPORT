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
package solusi.hapis.webui.security.right;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecRight;
import solusi.hapis.backend.util.HibernateSearchObject;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.util.Codec.RightType;
import solusi.hapis.webui.util.GFCBaseListCtrl;

import com.trg.search.Filter;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/secRightList.zul file.<br>
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 *
 * @author bbruhns
 * @author sgerth
 * @changes 05/15/2009: sge Migrating the list models for paging. <br>
 * 07/24/2009: sge changes for clustering.<br>
 * 10/12/2009: sge changings in the saving routine.<br>
 * 11/07/2009: bbr changed to extending from GFCBaseCtrl<br>
 * (GenericForwardComposer) for spring-managed creation.<br>
 * 03/09/2009: sge changed for allow repainting after resizing.<br>
 * with the refresh button.<br>
 */
public class SecRightListCtrl extends GFCBaseListCtrl<SecRight> implements Serializable {

    private static final long serialVersionUID = -6139454778139881103L;

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowSecRightList; // autowired
    protected Panel panel_SecRightList; // autowired

    // filter components
    protected Textbox txtb_rightName; // aurowired
    protected Combobox cmb_RightType; // aurowired

    // listbox secRightList
    protected Borderlayout borderLayout_SecRightList; // autowired
    protected Paging paging_SecRightList; // aurowired
    protected Listbox listBoxSecRight; // aurowired
    protected Listheader listheader_rightName; // autowired
    protected Listheader listheader_rightType; // autowired

    // Databinding
    private AnnotateDataBinder binder;
    private SecRightMainCtrl secRightMainCtrl;

    /**
     * default constructor.<br>
     */
    public SecRightListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        /**
         * 1. Set an 'alias' for this composer name to access it in the
         * zul-file.<br>
         * 2. Set the parameter 'recurse' to 'false' to avoid problems with
         * managing more than one zul-file in one page. Otherwise it would be
         * overridden and can ends in curious error messages.
         */
        this.self.setAttribute("controller", this, false);

        /**
         * 1. Get the overhanded MainController.<br>
         * 2. Set this controller in the MainController.<br>
         * 3. Check if a 'selectedObject' exists yet in the MainController.<br>
         */
        if (arg.containsKey("ModuleMainController")) {
            setSecRightMainCtrl((SecRightMainCtrl) arg.get("ModuleMainController"));

            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getSecRightMainCtrl().setSecRightListCtrl(this);
        }
        doRenderRightType();
    }
    
    public void doRenderRightType() {
    	
    	Comboitem itemAll = new Comboitem("All");
    	itemAll.setValue("-1");
    	itemAll.setParent(cmb_RightType);
    	for (RightType tipe : RightType.values()) {
    		Comboitem item = new Comboitem(tipe.getLabel());
    		item.setValue(tipe.getValue());
    		item.setParent(cmb_RightType);
		}
    	cmb_RightType.setSelectedIndex(0);
    }

    public void onCreate$windowSecRightList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }    

    public void doFillListbox() {
        doFitSize();

        // set the paging params
        paging_SecRightList.setPageSize(CommonUtils.PAGE_SIZE);
        paging_SecRightList.setDetailed(true);

        listheader_rightType.setSortAscending(new FieldComparator("rigType", true));
        listheader_rightType.setSortDescending(new FieldComparator("rigType", false));
        listheader_rightName.setSortAscending(new FieldComparator("rigDesc", true));
        listheader_rightName.setSortDescending(new FieldComparator("rigDesc", false));

        searchTable();

        listBoxSecRight.setItemRenderer(renderTable());
        
        if (getModelSecRight().getSize() > 0) {
        	setSelectedSecRight((SecRight) getModelSecRight().get(0));
        	
        	listBoxSecRight.setSelectedIndex(0);
        } else {
        	setSelectedSecRight(null);
        }
        
        windowSecRightList.addEventListener(Events.ON_OK, onSubmitForm());
    }

    /**
     * Recalculates the container size for this controller and resize them.
     * <p/>
     * Calculate how many rows have been place in the listbox. Get the
     * currentDesktopHeight from a hidden Intbox from the index.zul that are
     * filled by onClientInfo() in the indexCtroller.
     */
    public void doFitSize() {
        // normally 0 ! Or we have a i.e. a toolBar on top of the listBox.
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_SecRightList.setHeight(String.valueOf(maxListBoxHeight) + "px");
        windowSecRightList.invalidate();
    }

    public void clearSearchBox(){
    	txtb_rightName.setValue(""); // clear
    	cmb_RightType.setSelectedIndex(0);
    }
    
    private EventListener onSubmitForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				searchTable();
			}
		};
    }
    
	public void searchTable() {
		HibernateSearchObject<SecRight> soSecRight = new HibernateSearchObject<SecRight>(SecRight.class, CommonUtils.PAGE_SIZE_DETAIL);

		if (StringUtils.isNotEmpty(txtb_rightName.getValue())) {
			soSecRight.addFilter(new Filter("rigName", "%" + txtb_rightName.getValue() + "%", Filter.OP_ILIKE));
		}
		
		if (cmb_RightType.getSelectedItem().getValue().equals("-1") == false) {
			soSecRight.addFilter(new Filter("rigType", cmb_RightType.getSelectedItem().getValue(), Filter.OP_EQUAL));
		}

		
		getPagedBindingListWrapper().init(soSecRight, listBoxSecRight, paging_SecRightList);
        BindingListModelList lml = (BindingListModelList) listBoxSecRight.getModel();

        setModelSecRight(lml);
	}

    private ListitemRenderer renderTable() {
        return new ListitemRenderer() {
              
        	@Override
            public void render(Listitem item, Object data) throws Exception {
        		final SecRight right = (SecRight) data;

                Listcell lc;
                
                lc = new Listcell(RightType.getLabelByCode(right.getRigType()));
                lc.setParent(item);
                
                lc = new Listcell(right.getRigDesc());
                lc.setParent(item);
      
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedRightItem");
        	}
        };
    }

    /**
     * When a listItem in the corresponding listbox is selected.<br>
     * Event is forwarded in the corresponding listbox.
     *
     * @param event
     */
    public void onSelect$listBoxSecRight(Event event) {
    	if (getSecRightMainCtrl().getSecRightDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", getSecRightMainCtrl().tabSecRightDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getSecRightMainCtrl().getSecRightDetailCtrl().binder == null) {
            Events.sendEvent(new Event("onSelect", getSecRightMainCtrl().tabSecRightDetail, null));
        }
        // INIT ALL RELATED Queries/OBJECTS/LISTS NEW
        getSecRightMainCtrl().setSelectedSecRight(getSelectedSecRight());

        // store the selected bean values as current
        getSecRightMainCtrl().doStoreInitValues();
    }

    /**
     * Call the SecRight dialog with the selected entry. <br>
     * <br>
     * This methode is forwarded from the listboxes item renderer. <br>
     * see: id.co.club.webui.branch.model.BranchListModelItemRenderer.java <br>
     *
     * @param event
     * @throws Exception
     */
    public void onDoubleClickedRightItem(Event event) throws Exception {
    	Events.sendEvent(new Event("onSelect", getSecRightMainCtrl().tabSecRightDetail, getSelectedSecRight()));
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++ Getter / Setter +++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void setModelSecRight(BindingListModelList offices) {
        // STORED IN THE module's MainController
        getSecRightMainCtrl().setModelSecRight(offices);
    }

    public BindingListModelList getModelSecRight() {
        // STORED IN THE module's MainController
        return getSecRightMainCtrl().getModelSecRight();
    }

    public void setSelectedSecRight(SecRight selectedSecRight) {
    	getSecRightMainCtrl().setSelectedSecRight(selectedSecRight);
    }

    public SecRight getSelectedSecRight() {
        return getSecRightMainCtrl().getSelectedSecRight();
    }

	public SecRightMainCtrl getSecRightMainCtrl() {
		return secRightMainCtrl;
	}

	public void setSecRightMainCtrl(SecRightMainCtrl secRightMainCtrl) {
		this.secRightMainCtrl = secRightMainCtrl;
	}

}
