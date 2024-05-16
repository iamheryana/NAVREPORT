package solusi.hapis.webui.lov;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.North;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.backend.navbi.service.M02SalespersonService;
import solusi.hapis.common.CommonUtils;

public class M02SalespersonLOV extends Window implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8670130834282295623L;
	private static final Logger logger =Logger.getLogger(M02SalespersonLOV.class);
	
    private Textbox txtKode;
    private Textbox txtNama;
    
    // button for sending the ServiceMethod if searchParameters are used
    private Button _searchButton;

    // the paging component
    private Paging _paging;

    // pageSize
    private int pageSize = CommonUtils.PAGE_SIZE;

    // the data listbox
    private Listbox listbox;

    // the model for the listbox
    private ListModelList listModelList;

    // the windows title
    private final String _title = "Search Sales";

    // the windows height
    private final int _height = 500;

    // the windows width
    private final int _width = 600;

    // the returned bean object
    private M02Salesperson obj = null;

    // The service from which we get the data
    private M02SalespersonService objService;

	/**
     * The Call method.
     *
     * @param parent The parent component
     * @return a BeanObject from the listBox or null.
     *     public static M01area show(Component parent) {
        return new M02SalespersonLOV(parent).getObj();
    }
     */
    
    public static M02Salesperson show(Component parent){
    	return new M02SalespersonLOV(parent).getObj();
    }

    private M02SalespersonLOV(Component parent) {
        super();

        setParent(parent);

        createBox();
	}
    
    @SuppressWarnings("unchecked")
    private void createBox() {

    	// Window
        this.setWidth(String.valueOf(this._width) + "px");
        this.setHeight(String.valueOf(this._height) + "px");
        this.setTitle(this._title);
        this.setVisible(true);
        this.setClosable(true);
        this.addEventListener(Events.ON_OK, new OnSearchListener());
        // Borderlayout
        Borderlayout bl = new Borderlayout();
        bl.setHeight("100%");
        bl.setWidth("100%");
        bl.setParent(this);
        
        North north = new North();
        north.setBorder("none");
        north.setStyle("background: #C4EAFF");
        north.setFlex(true);
        north.setParent(bl);
        
        
        // hbox for holding the Textbox + SearchButton
        Hbox hbox = new Hbox();
        hbox.setPack("stretch");
        hbox.setStyle("padding-left: 5px");
        hbox.setWidth("100%");
        hbox.setHeight("27px");
        hbox.setParent(north);
        
        // serachButton
        Div divSouth = new Div();
        divSouth.setWidth("100%");
        divSouth.setHeight("100%");
        divSouth.setParent(hbox);
        
        this._searchButton = new Button();
        this._searchButton.setLabel(Labels.getLabel("common.button.search"));
        this._searchButton.addEventListener("onClick", new OnSearchListener());
        this._searchButton.setParent(divSouth);
        
        // Button
        Button btnClear = new Button();
        btnClear.setLabel("Clear");
        btnClear.addEventListener(Events.ON_CLICK, new OnClearListener());
        btnClear.setParent(divSouth);
        
        new Space().setParent(divSouth);
        
        Button btnSelect = new Button();
        btnSelect.setLabel("Select");
        btnSelect.addEventListener("onClick", new OnCloseListener());
        btnSelect.setParent(divSouth);
        

        Center center = new Center();
        center.setBorder("none");
        center.setFlex(true);
        center.setParent(bl);

        // Borderlayout
        Borderlayout bl2 = new Borderlayout();
        bl2.setHeight("100%");
        bl2.setWidth("100%");
        bl2.setParent(center);

        North north2 = new North();
        north2.setBorder("none");
        north2.setHeight("26px");
        north2.setParent(bl2);
        // Paging
        this._paging = new Paging();
        this._paging.setDetailed(true);
        this._paging.addEventListener("onPaging", new OnPagingEventListener());
        this._paging.setPageSize(getPageSize());
        this._paging.setParent(north2);

        Center center2 = new Center();
        center2.setBorder("none");
        center2.setFlex(true);
        center2.setParent(bl2);
        // Div Center area
        Div divCenter2 = new Div();
        divCenter2.setWidth("100%");
        divCenter2.setHeight("100%");
        divCenter2.setParent(center2);

        // Listbox
        this.listbox = new Listbox();
        // listbox.setStyle("border: none;");
        this.listbox.setHeight("290px");
        this.listbox.setVisible(true);
        this.listbox.setParent(divCenter2);
        this.listbox.setItemRenderer(new SearchBoxItemRenderer());

        Listhead listhead = new Listhead();
        listhead.setParent(this.listbox);
        
        Listheader listheaderKode = new Listheader();
        listheaderKode.setSclass("FDListBoxHeader1");
        listheaderKode.setParent(listhead);
        listheaderKode.setWidth("20%");
        listheaderKode.setLabel("Sales");
        listheaderKode.setSortAscending(new FieldComparator("sales", true));
        listheaderKode.setSortDescending(new FieldComparator("sales", false));
        listheaderKode.setSort("auto");
        
        Listheader listheaderDesc = new Listheader();
        listheaderDesc.setSclass("FDListBoxHeader1");
        listheaderDesc.setParent(listhead);
        listheaderDesc.setWidth("50%");
        listheaderDesc.setLabel("Sales Name");
        listheaderDesc.setSortAscending(new FieldComparator("salesName", true));
        listheaderDesc.setSortDescending(new FieldComparator("salesName", false));
        listheaderDesc.setSort("auto");
        
              
        //Aux
        Auxhead auxhead = new Auxhead();
        Auxheader auxKode = new Auxheader();
        this.txtKode = new Textbox();
        this.txtKode.setWidth("95%");
        this.txtKode.setParent(auxKode);
        auxKode.setParent(auxhead);
        
        Auxheader auxKet = new Auxheader();
        this.txtNama = new Textbox();
        this.txtNama.setWidth("95%");
        this.txtNama.setParent(auxKet);
        auxKet.setParent(auxhead);
        
              
        auxhead.setParent(this.listbox);
	

        /**
         * init the model.<br>
         * The ResultObject is a helper class that holds the generic list and
         * the totalRecord count as int value.
         */
        ResultObject ro = getObjService().getListM02SalespersonLOV(null, 0, getPageSize());
        List<M02Salesperson> resultList = (List<M02Salesperson>) ro.getList();
        this._paging.setTotalSize(ro.getTotalCount());

        // set the model
        setListModelList(new ListModelList(resultList));
        this.listbox.setModel(getListModelList());

        try {
            doModal();
        } catch (final SuspendNotAllowedException e) {
            logger.fatal("", e);
            this.detach();
        } catch (final InterruptedException e) {
            logger.fatal("", e);
            this.detach();
        }
    }


    final class OnClearListener implements EventListener{
    	@Override
    	public void onEvent(Event event) throws Exception {
    		txtKode.setValue("");
    		txtNama.setValue("");
    		refreshModel(0);
    	}
    }
    /**
     * Inner ListItemRenderer class.<br>
     */
    final class SearchBoxItemRenderer implements ListitemRenderer {

        @Override
        public void render(Listitem item, Object data) throws Exception {

        	M02Salesperson rec = (M02Salesperson) data;

            Listcell lc1 = new Listcell(rec.getSales());
            lc1.setParent(item);

            Listcell lc2 = new Listcell(rec.getSalesName());
            lc2.setParent(item);
            
                        
            item.setAttribute("data", data);
            ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClicked");
        }
    }

    /**
     * If a DoubleClick appears on a listItem. <br>
     * This method is forwarded in the renderer.<br>
     *
     * @param event
     */
    public void onDoubleClicked(Event event) {

        if (this.listbox.getSelectedItem() != null) {
            Listitem li = this.listbox.getSelectedItem();
            M02Salesperson rec = (M02Salesperson) li.getAttribute("data");

            setObj(rec);
            this.onClose();
        }
    }

    /**
     * "onPaging" EventListener for the paging component. <br>
     * <br>
     * Calculates the next page by currentPage and pageSize values. <br>
     * Calls the method for refreshing the data with the new rowStart and
     * pageSize. <br>
     */
    public final class OnPagingEventListener implements EventListener {
        @Override
        public void onEvent(Event event) throws Exception {

            PagingEvent pe = (PagingEvent) event;
            int pageNo = pe.getActivePage();
            int start = pageNo * getPageSize();

            // refresh the list
            refreshModel(start);
        }
    }

    /**
     * Refreshes the list by calling the DAO methode with the modified search
     * object. <br>
     *
     * @param searchText SearchObject, holds the entity and properties to search. <br>
     * @param start      Row to start. <br>
     */
    @SuppressWarnings("unchecked")
    void refreshModel(int start) {

        // clear old data
        getListModelList().clear();
        
        
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		if (CommonUtils.isNotEmpty(txtKode.getValue())) {
			parameterInput.put("sales", txtKode.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtNama.getValue())) {
			parameterInput.put("salesName", txtNama.getValue());
		}
		

        // init the model
        ResultObject ro = getObjService().getListM02SalespersonLOV(parameterInput, start, getPageSize());
        List<M02Salesperson> resultList = (List<M02Salesperson>) ro.getList();
        this._paging.setTotalSize(ro.getTotalCount());

        // set the model
        setListModelList(new ListModelList(resultList));
        this.listbox.setModel(getListModelList());
    }

    /**
     * Inner OnSearchListener class.<br>
     */
    final class OnSearchListener implements EventListener {
        @Override
        public void onEvent(Event event) throws Exception {

            // we start new
            refreshModel(0);
        }
    }

    /**
     * Inner OnCloseListener class.<br>
     */
    final class OnCloseListener implements EventListener {
        @Override
        public void onEvent(Event event) throws Exception {

            if (M02SalespersonLOV.this.listbox.getSelectedItem() != null) {
                Listitem li = M02SalespersonLOV.this.listbox.getSelectedItem();
                M02Salesperson rec = (M02Salesperson) li.getAttribute("data");

                setObj(rec);
            }
            onClose();
        }

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    
	public void setObj(M02Salesperson obj) {
		this.obj = obj;
	}

	public M02Salesperson getObj() {
		return obj;
	}

	public void setObjService(M02SalespersonService objService) {
		this.objService = objService;
	}

	public M02SalespersonService getObjService() {
		if (this.objService == null){
			this.objService = (M02SalespersonService) SpringUtil.getBean("m02SalespersonService");
		}
		return objService;
	}

	public void setListModelList(ListModelList listModelList) {
		this.listModelList = listModelList;
	}

	public ListModelList getListModelList() {
		return listModelList;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

}
