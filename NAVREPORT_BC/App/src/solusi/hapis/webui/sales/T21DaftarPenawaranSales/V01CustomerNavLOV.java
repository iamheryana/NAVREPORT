package solusi.hapis.webui.sales.T21DaftarPenawaranSales;


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
import solusi.hapis.backend.navbi.model.V01CustomerNav;
import solusi.hapis.backend.navbi.service.V01CustomerNavService;
import solusi.hapis.common.CommonUtils;

public class V01CustomerNavLOV extends Window implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8670130834282295623L;
	private static final Logger logger =Logger.getLogger(V01CustomerNavLOV.class);
	
    private Textbox txtCustCode;
    private Textbox txtCustName;
    private Textbox txtNoNpwp;
    private Textbox txtSectorCode;
    private Textbox txtSectorName;
    

	
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
    private final String _title = "Search Customer";

    // the windows height
    private final int _height = 600;

    // the windows width
    private final int _width = 750;

    // the returned bean object
    private V01CustomerNav obj = null;

    // The service from which we get the data
    private V01CustomerNavService objService;

	/**
     * The Call method.
     *
     * @param parent The parent component
     * @return a BeanObject from the listBox or null.
     *     public static M01area show(Component parent) {
        return new V01CustomerNavLOV(parent).getObj();
    }
     */
    
    public static V01CustomerNav show(Component parent){
    	return new V01CustomerNavLOV(parent).getObj();
    }

    private V01CustomerNavLOV(Component parent) {
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
        

        
        
        Listheader listheaderCustCode = new Listheader();
        listheaderCustCode.setSclass("FDListBoxHeader1");
        listheaderCustCode.setParent(listhead);
        listheaderCustCode.setWidth("15%");
        listheaderCustCode.setLabel("Code");
        listheaderCustCode.setSortAscending(new FieldComparator("custCode", true));
        listheaderCustCode.setSortDescending(new FieldComparator("custCode", false));
        listheaderCustCode.setSort("auto");
        
        Listheader listheaderCustName = new Listheader();
        listheaderCustName.setSclass("FDListBoxHeader1");
        listheaderCustName.setParent(listhead);
        listheaderCustName.setWidth("25%");
        listheaderCustName.setLabel("Customer Name");
        listheaderCustName.setSortAscending(new FieldComparator("custName", true));
        listheaderCustName.setSortDescending(new FieldComparator("custName", false));
        listheaderCustName.setSort("auto");
        
        Listheader listheaderNoNpwp = new Listheader();
        listheaderNoNpwp.setSclass("FDListBoxHeader1");
        listheaderNoNpwp.setParent(listhead);
        listheaderNoNpwp.setWidth("20%");
        listheaderNoNpwp.setLabel("NPWP");
        listheaderNoNpwp.setSortAscending(new FieldComparator("noNpwp", true));
        listheaderNoNpwp.setSortDescending(new FieldComparator("noNpwp", false));
        listheaderNoNpwp.setSort("auto");
        
        Listheader listheaderSectorCode = new Listheader();
        listheaderSectorCode.setSclass("FDListBoxHeader1");
        listheaderSectorCode.setParent(listhead);
        listheaderSectorCode.setWidth("15%");
        listheaderSectorCode.setLabel("Sektor Code");
        listheaderSectorCode.setSortAscending(new FieldComparator("sectorCode", true));
        listheaderSectorCode.setSortDescending(new FieldComparator("sectorCode", false));
        listheaderSectorCode.setSort("auto");
        
        Listheader listheaderSectorName = new Listheader();
        listheaderSectorName.setSclass("FDListBoxHeader1");
        listheaderSectorName.setParent(listhead);
        listheaderSectorName.setWidth("25%");
        listheaderSectorName.setLabel("Sektor Industri");
        listheaderSectorName.setSortAscending(new FieldComparator("sectorName", true));
        listheaderSectorName.setSortDescending(new FieldComparator("sectorName", false));
        listheaderSectorName.setSort("auto");
        
        
       
        //Aux
        Auxhead auxhead = new Auxhead();
        Auxheader auxCustCode = new Auxheader();
        this.txtCustCode = new Textbox();
        this.txtCustCode.setWidth("95%");
        this.txtCustCode.setParent(auxCustCode);
        auxCustCode.setParent(auxhead);
        
        
        Auxheader auxCustName = new Auxheader();
        this.txtCustName = new Textbox();
        this.txtCustName.setWidth("95%");
        this.txtCustName.setParent(auxCustName);
        auxCustName.setParent(auxhead);
        
        Auxheader auxNoNPWP = new Auxheader();
        this.txtNoNpwp = new Textbox();
        this.txtNoNpwp.setWidth("95%");
        this.txtNoNpwp.setParent(auxNoNPWP);
        auxNoNPWP.setParent(auxhead);
        
        Auxheader auxSectorCode= new Auxheader();
        this.txtSectorCode = new Textbox();
        this.txtSectorCode.setWidth("95%");
        this.txtSectorCode.setParent(auxSectorCode);
        auxSectorCode.setParent(auxhead);
        
        Auxheader auxSectorName= new Auxheader();
        this.txtSectorName = new Textbox();
        this.txtSectorName.setWidth("95%");
        this.txtSectorName.setParent(auxSectorName);
        auxSectorName.setParent(auxhead);
        
        
        auxhead.setParent(this.listbox);
	

        /**
         * init the model.<br>
         * The ResultObject is a helper class that holds the generic list and
         * the totalRecord count as int value.
         */
        ResultObject ro = getObjService().getListV01CustomerNavLOV(null, 0, getPageSize());
        List<V01CustomerNav> resultList = (List<V01CustomerNav>) ro.getList();
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
    		
    		
    		txtCustCode.setValue("");
    		txtCustName.setValue("");
    		txtNoNpwp.setValue("");
    		txtSectorCode.setValue("");
    		txtSectorName.setValue("");
    	  
    		refreshModel(0);
    	}
    }
    /**
     * Inner ListItemRenderer class.<br>
     */
    final class SearchBoxItemRenderer implements ListitemRenderer {

        @Override
        public void render(Listitem item, Object data) throws Exception {

        	V01CustomerNav rec = (V01CustomerNav) data;

            Listcell lc1 = new Listcell(rec.getCustCode());
            lc1.setParent(item);

            Listcell lc2 = new Listcell(rec.getCustName());
            lc2.setParent(item);
            
            Listcell lc3 = new Listcell(rec.getNoNpwp());
            lc3.setParent(item);
            
            Listcell lc4 = new Listcell(rec.getSectorCode());
            lc4.setParent(item);
                        
            Listcell lc5 = new Listcell(rec.getSectorName());
            lc5.setParent(item);
            
            
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
            V01CustomerNav rec = (V01CustomerNav) li.getAttribute("data");

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
		
		if (CommonUtils.isNotEmpty(txtCustCode.getValue())) {
			parameterInput.put("custCode", txtCustCode.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtCustName.getValue())) {
			parameterInput.put("custName", txtCustName.getValue());
		}
	    
		if (CommonUtils.isNotEmpty(txtNoNpwp.getValue())) {
			parameterInput.put("noNpwp", txtNoNpwp.getValue());
		}
				
		if (CommonUtils.isNotEmpty(txtSectorCode.getValue())) {
			parameterInput.put("sectorCode", txtSectorCode.getValue());
		}
		
		if (CommonUtils.isNotEmpty(txtSectorName.getValue())) {
			parameterInput.put("sectorName", txtSectorName.getValue());
		}
		
        // init the model
        ResultObject ro = getObjService().getListV01CustomerNavLOV(parameterInput, start, getPageSize());
        List<V01CustomerNav> resultList = (List<V01CustomerNav>) ro.getList();
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

            if (V01CustomerNavLOV.this.listbox.getSelectedItem() != null) {
                Listitem li = V01CustomerNavLOV.this.listbox.getSelectedItem();
                V01CustomerNav rec = (V01CustomerNav) li.getAttribute("data");

                setObj(rec);
            }
            onClose();
        }

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    
	public void setObj(V01CustomerNav obj) {
		this.obj = obj;
	}

	public V01CustomerNav getObj() {
		return obj;
	}

	public void setObjService(V01CustomerNavService objService) {
		this.objService = objService;
	}

	public V01CustomerNavService getObjService() {
		if (this.objService == null){
			this.objService = (V01CustomerNavService) SpringUtil.getBean("v01CustomerNavService");
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
