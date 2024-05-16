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
package solusi.hapis.webui.security.group;

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

import solusi.hapis.backend.model.SecGroup;
import solusi.hapis.backend.util.HibernateSearchObject;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseListCtrl;

import com.trg.search.Filter;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_group/secGroupList.zul file.<br>
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
public class SecGroupListCtrl extends GFCBaseListCtrl<SecGroup> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2805556247320495795L;
	protected Window windowSecGroupList; // autowired
    protected Panel panelSecGroupList; // autowired
 	
	// filter components
	protected Textbox txtShortdesc;
	protected Textbox txtLongdesc;

    protected Borderlayout borderLayout_SecGroupList; // autowired
    protected Paging paging_SecGroupList; // autowired
    protected Listbox listBoxSecGroup; // autowired
    protected Listheader listheader_Shortdesc; // autowired
    protected Listheader listheader_Longdesc; // autowired

    // Databinding
    private AnnotateDataBinder binder;
    private SecGroupMainCtrl secGroupMainCtrl;

    /**
     * default constructor.<br>
     */
    public SecGroupListCtrl() {
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
            setSecGroupMainCtrl((SecGroupMainCtrl) arg.get("ModuleMainController"));

            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getSecGroupMainCtrl().setSecGroupListCtrl(this);
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++ Component Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Automatically called method from zk.
     *
     * @param event
     * @throws Exception
     */

    public void onCreate$windowSecGroupList(Event event) throws Exception {
    	binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        doFillListbox();
        binder.loadAll();
    }
    

    public void doFillListbox() {

        doFitSize();

        // set the paging params
        paging_SecGroupList.setPageSize(CommonUtils.PAGE_SIZE);
        paging_SecGroupList.setDetailed(true);
        
        listheader_Shortdesc.setSortAscending(new FieldComparator("grpShortdescription", true));
        listheader_Shortdesc.setSortDescending(new FieldComparator("grpShortdescription", false));
        listheader_Longdesc.setSortAscending(new FieldComparator("grpLongdescription", true));
        listheader_Longdesc.setSortDescending(new FieldComparator("grpLongdescription", false));
        
        searchTable();

        listBoxSecGroup.setItemRenderer(renderTable());
        
        // check if first time opened and init databinding for selectedBean
        if (getSelectedSecGroup() == null) {
            // init the bean with the first record in the List
            if (getModelSecGroup().getSize() > 0) {
                listBoxSecGroup.setSelectedIndex(0);
                setSelectedSecGroup((SecGroup) getModelSecGroup().get(0));
            }
        }
        windowSecGroupList.addEventListener(Events.ON_OK, onSubmitForm());
    }

    private ListitemRenderer renderTable() {
    	return new ListitemRenderer() {
              
    		@Override
            public void render(Listitem item, Object data) throws Exception {
    			final SecGroup sub = (SecGroup) data;

                Listcell lc;
                
                lc = new Listcell(sub.getGrpShortdescription());
                lc.setParent(item);
                
                lc = new Listcell(sub.getGrpLongdescription());
                lc.setParent(item);
                
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedSecGroupItem");
    		}
    	};
    }

    /**
     * When a listItem in the corresponding listbox is selected.<br>
     * Event is forwarded in the corresponding listbox.
     *
     * @param event
     */
    public void onSelect$listBoxSecGroup(Event event) {
    	if (getSecGroupMainCtrl().getSecGroupDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", getSecGroupMainCtrl().tabSecGroupDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getSecGroupMainCtrl().getSecGroupDetailCtrl().binder == null) {
            Events.sendEvent(new Event("onSelect", getSecGroupMainCtrl().tabSecGroupDetail, null));
        }
        // INIT ALL RELATED Queries/OBJECTS/LISTS NEW
    	getSecGroupMainCtrl().setSelectedSecGroup(getSelectedSecGroup());

        // store the selected bean values as current
    	getSecGroupMainCtrl().doStoreInitValues();
    }

    /**
     * Selects the object in the listbox and change the tab.<br>
     * Event is forwarded in the corresponding listbox.
     */
    public void onDoubleClickedSecGroupItem(Event event) {    	
        Events.sendEvent(new Event("onSelect", getSecGroupMainCtrl().tabSecGroupDetail, getSelectedSecGroup()));
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++ Business Logic ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

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
        borderLayout_SecGroupList.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowSecGroupList.invalidate();
    }
    
    public void clearSearchBox() {
    	  // empty the text search boxes    	
    	txtShortdesc.setValue("");
    	txtLongdesc.setValue("");
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
		HibernateSearchObject<SecGroup> searchObj = new HibernateSearchObject<SecGroup>(SecGroup.class, CommonUtils.PAGE_SIZE_DETAIL);
     
		if (StringUtils.isNotBlank(txtShortdesc.getValue())) {
			searchObj.addFilter(new Filter("grpShortdescription","%"+ txtShortdesc.getValue() +"%", Filter.OP_ILIKE));
		}

		if (StringUtils.isNotBlank(txtLongdesc.getValue())) {
			searchObj.addFilter(new Filter("grpLongdescription","%"+ txtLongdesc.getValue() +"%" , Filter.OP_ILIKE));
		}

		getPagedBindingListWrapper().init(searchObj, listBoxSecGroup, paging_SecGroupList);
        BindingListModelList lml = (BindingListModelList) listBoxSecGroup.getModel();

        setModelSecGroup(lml);
	}
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // ++++++++++++++++++ getter / setter +++++++++++++++++++//
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

    /**
     * Best Pratice Hint:<br>
     * The setters/getters for the local annotated data binded Beans/Sets are
     * administered in the module's mainController. Working in this way you have
     * clean line to share this beans/sets with other controllers.
     */

    public void setSelectedSecGroup(SecGroup selectedSecGroup) {
        // STORED IN THE module's MainController
        getSecGroupMainCtrl().setSelectedSecGroup(selectedSecGroup);
    }

    public SecGroup getSelectedSecGroup() {
        // STORED IN THE module's MainController
        return getSecGroupMainCtrl().getSelectedSecGroup();
    }

    public void setModelSecGroup(BindingListModelList offices) {
        // STORED IN THE module's MainController
        getSecGroupMainCtrl().setModelSecGroup(offices);
    }

    public BindingListModelList getModelSecGroup() {
        // STORED IN THE module's MainController
        return getSecGroupMainCtrl().getModelSecGroup();
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public AnnotateDataBinder getBinder() {
        return this.binder;
    }

	public SecGroupMainCtrl getSecGroupMainCtrl() {
		return secGroupMainCtrl;
	}

	public void setSecGroupMainCtrl(SecGroupMainCtrl secGroupMainCtrl) {
		this.secGroupMainCtrl = secGroupMainCtrl;
	}
    
}
