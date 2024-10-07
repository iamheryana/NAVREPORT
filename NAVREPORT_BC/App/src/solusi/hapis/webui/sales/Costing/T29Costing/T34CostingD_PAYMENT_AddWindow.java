package solusi.hapis.webui.sales.Costing.T29Costing;


import java.io.Serializable;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Center;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.North;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.backend.navbi.model.T34CostingDPayment;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class T34CostingD_PAYMENT_AddWindow extends Window implements Serializable {
	private static final long serialVersionUID = 7826099855146654896L;
	
	// binding
	private T34CostingDPayment initValue;
	private T34CostingDPayment resultValue;
	
	private T29CostingH initT29CostingH;
	
	
	
	private Textbox txtNoInvoice;
	private Textbox txtNoLunas;

	private Decimalbox decNilaiInvoice;
	private Decimalbox decNilaiLunas;
	
	private Datebox dtbTglInvoice;
	private Datebox dtbTglLunas;

	
	private String modeTitle;
	
	@SuppressWarnings("unused")
	private SelectQueryService selectQueryService;
	
	public static T34CostingDPayment show(Component parent, T34CostingDPayment t34CostingDPayment, T29CostingH t29CostingH, String modeTitle, SelectQueryService selectQueryService) {
		return new T34CostingD_PAYMENT_AddWindow(parent, t34CostingDPayment, t29CostingH, modeTitle, selectQueryService).resultValue;
	}

	private T34CostingD_PAYMENT_AddWindow(Component parent, T34CostingDPayment t34CostingDPayment, T29CostingH t29CostingH, String modeTitle, SelectQueryService selectQueryService) {
		super();
		this.initValue = t34CostingDPayment;
		this.initT29CostingH = t29CostingH;
		this.modeTitle = modeTitle;
		this.selectQueryService = selectQueryService;
		setParent(parent);
		createBox();
	}
	
	private void createBox() {
		this.setBorder("none");
		this.setWidth("800px");
		this.setHeight("400px");
		this.addEventListener(Events.ON_OK, onClickSave() );
		

		Borderlayout borderLayoutAddDetail = new Borderlayout();

		North north1 = new North();
		north1.setBorder("none");
		Div divToolbar = new Div();
		divToolbar.setSclass("z-toolbar");
		divToolbar.setStyle("padding:0");
		Hbox hbox = new Hbox();
		hbox.setPack("stretch");
		hbox.setSclass("hboxRemoveWhiteStrips");
		hbox.setWidth("100%");
		Toolbar toolbar = new Toolbar();
		toolbar.setAlign("end");
		toolbar.setStyle("float:right; border-style: none;");
		toolbar.setHeight("28px");
		Button btnSave = new Button();
		btnSave.setHeight("24px");
		btnSave.setLabel(Labels.getLabel("common.button.save"));
		btnSave.addEventListener(Events.ON_CLICK, onClickSave());
		if (modeTitle.equals("View")){
			btnSave.setVisible(false);
		}
		btnSave.setParent(toolbar);

		Button btnClose = new Button();
		btnClose.setHeight("24px");
		btnClose.setLabel(Labels.getLabel("common.button.close"));
		btnClose.addEventListener(Events.ON_CLICK, closeWindow());
		btnClose.setParent(toolbar);
		toolbar.setParent(hbox);
		hbox.setParent(divToolbar);
		divToolbar.setParent(north1);
		north1.setParent(borderLayoutAddDetail);

		Center center = new Center();
		center.setBorder("none");
		center.setAutoscroll(true);
		Panel panel = new Panel();
		panel.setBorder("none");
		Panelchildren panelchildren = new Panelchildren();
		Groupbox groupbox = new Groupbox();
		groupbox.setMold("3d");
		groupbox.setClosable(false);
		Caption caption = new Caption();
		caption.setLabel("Other Project Expenses - "+modeTitle);
		caption.setParent(groupbox);
		Grid grid = new Grid();
		grid.setSclass("GridLayoutNoBorder");
		grid.setStyle("border:0px; padding-left:5px; padding-right:5px;");

		Columns columns = new Columns();

		Column column1 = new Column();
		column1.setWidth("100px");
		column1.setParent(columns);

		Column column2 = new Column();
		column2.setWidth("165px");
		column2.setParent(columns);
		
		Column column3 = new Column();
		column3.setWidth("100px");
		column3.setParent(columns);
		
		Column column4 = new Column();
		column4.setParent(columns);

		columns.setParent(grid);

		Rows rows = new Rows();

		Row row1 = new Row();
			new Label("No. Invoice").setParent(row1);
		
		
			this.txtNoInvoice = new Textbox();
			this.txtNoInvoice.setCols(20);
			this.txtNoInvoice.setMaxlength(20);
			if (modeTitle.equals("View")){
				this.txtNoInvoice.setReadonly(true);
			}
			this.txtNoInvoice.setParent(row1);		
			
			
			new Label("No. Pelunasan").setParent(row1);
			
			this.txtNoLunas = new Textbox();
			this.txtNoLunas.setCols(20);
			this.txtNoLunas.setMaxlength(20);
			if (modeTitle.equals("View")){
				this.txtNoLunas.setReadonly(true);
			}
			this.txtNoLunas.setParent(row1);		
		row1.setParent(rows);
		
	
		Row row2 = new Row();
			new Label("Tgl. Invoice").setParent(row2);
				
	
			this.dtbTglInvoice = new Datebox();
			this.dtbTglInvoice.setFormat("dd-MM-yyyy");
			if (modeTitle.equals("View")){
				this.dtbTglInvoice.setDisabled(true);
			}
			this.dtbTglInvoice.setParent(row2);		
			
			new Label("Tgl. Pelunasan").setParent(row2);
			
			this.dtbTglLunas = new Datebox();
			this.dtbTglLunas.setFormat("dd-MM-yyyy");
			if (modeTitle.equals("View")){
				this.dtbTglLunas.setDisabled(true);
			}
			this.dtbTglLunas.setParent(row2);	
			
		row2.setParent(rows);
		
		Row row3 = new Row();	
			new Label("Nilai Invoice").setParent(row3);
			
			this.decNilaiInvoice= new Decimalbox();
			this.decNilaiInvoice.setFormat("#,##0");
			this.decNilaiInvoice.setStyle("text-align:right;");		
			this.decNilaiInvoice.setCols(20);
			this.decNilaiInvoice.setMaxlength(38);
			if (modeTitle.equals("View")){
				this.decNilaiInvoice.setReadonly(true);
			}
			this.decNilaiInvoice.setParent(row3);	
			
			
			new Label("Nilai Payment").setParent(row3);
			
			this.decNilaiLunas= new Decimalbox();
			this.decNilaiLunas.setFormat("#,##0");
			this.decNilaiLunas.setStyle("text-align:right;");		
			this.decNilaiLunas.setCols(20);
			this.decNilaiLunas.setMaxlength(38);
			if (modeTitle.equals("View")){
				this.decNilaiLunas.setReadonly(true);
			}
			this.decNilaiLunas.setParent(row3);	
			
		row3.setParent(rows);
		
		rows.setParent(grid);
		
		grid.setParent(groupbox);
		
		groupbox.setParent(panelchildren);	
		
		//createDetailItem(panelchildren);
		
		panelchildren.setParent(panel);
		panel.setParent(center);
		center.setParent(borderLayoutAddDetail);

		borderLayoutAddDetail.setParent(this);

		init();

		try {
			this.doModal();
		} catch (final SuspendNotAllowedException e) {
			this.detach();
		} catch (final InterruptedException e) {
			this.detach();
		}

	}
	
	

	
	private void init() {
		if (CommonUtils.isNotEmpty(initValue.getNoInvoice())) {
			txtNoInvoice.setValue(initValue.getNoInvoice());
		}
		
		if (CommonUtils.isNotEmpty(initValue.getTglInvoice())) {
			dtbTglInvoice.setValue(initValue.getTglInvoice());
		}
		

		if (CommonUtils.isNotEmpty(initValue.getNilaiInvoice())) {
			decNilaiInvoice.setValue(initValue.getNilaiInvoice());
		}
		
		if (CommonUtils.isNotEmpty(initValue.getNoLunas())) {
			txtNoLunas.setValue(initValue.getNoLunas());
		}
		
		if (CommonUtils.isNotEmpty(initValue.getTglLunas())) {
			dtbTglLunas.setValue(initValue.getTglLunas());
		}

		if (CommonUtils.isNotEmpty(initValue.getNilaiLunas())) {
			decNilaiLunas.setValue(initValue.getNilaiLunas());
		}
				

	}

	/*
	private void createDetailItem(Component panel) {
		
		Groupbox detailItemBox = new Groupbox();
		detailItemBox.setMold("3d");
		detailItemBox.setClosable(false);
		
		Grid gridDetailItem = new Grid();
		gridDetailItem.setParent(detailItemBox);
		
		Rows rowsDetailItem = new Rows();
		rowsDetailItem.setParent(gridDetailItem);

		
		
		
		Row rowDetailItem2 = new Row();
		listboxDetailItem = new Listbox();
		listboxDetailItem.setStyle("border-top-width: 0px; border-left-width: 0px; border-right-width: 0px; border-bottom-width: 1px;");
		listboxDetailItem.setVflex(true);
		listboxDetailItem.setHeight("99%");
		listboxDetailItem.setWidth("100%");
		listboxDetailItem.setMultiple(false);
		listboxDetailItem.setItemRenderer(itemRenderDetailItem());

		if (CommonUtils.isNotEmpty(initM02Salesperson.getM03Targetsaless())) {
			listModelDetailItem.addAll(initM02Salesperson.getM03Targetsaless());
		}

		

		listboxDetailItem.setModel(listModelDetailItem);

		
		Listhead listheadDetailItem = new Listhead();
		listheadDetailItem.setSizable(true);

		Listheader listheaderDetailItem = new Listheader();
		listheaderDetailItem.setSclass("FDListBoxHeader1");
		listheaderDetailItem.setSort("auto");
		listheaderDetailItem.setLabel("Tahun");
		listheaderDetailItem.setSortAscending(new FieldComparator("tahun", true));
		listheaderDetailItem.setSortDescending(new FieldComparator("tahun", false));
		listheaderDetailItem.setParent(listheadDetailItem);
		
		
		listheaderDetailItem = new Listheader();
		listheaderDetailItem.setSclass("FDListBoxHeader1");
		listheaderDetailItem.setSort("auto");
		listheaderDetailItem.setLabel("Target");
		listheaderDetailItem.setAlign("right");
		listheaderDetailItem.setSortAscending(new FieldComparator("target", true));
		listheaderDetailItem.setSortDescending(new FieldComparator("target", false));
		listheaderDetailItem.setParent(listheadDetailItem);
		
		
		listheadDetailItem.setParent(listboxDetailItem);
		listboxDetailItem.setParent(rowDetailItem2);
		rowDetailItem2.setParent(rowsDetailItem);

		detailItemBox.setParent(panel);
	}


	private ListitemRenderer itemRenderDetailItem() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object obj) throws Exception {
				renderData(item, (M03Targetsales) obj);
			}

			private void renderData(Listitem item, M03Targetsales rec) {
				Listcell lc ;
				
			
				lc = new Listcell(rec.getTahun());
				lc.setParent(item);

				lc = new Listcell(CommonUtils.formatNumberManual(rec.getTarget(),"#,###,###"));
				lc.setStyle("text-align:right");
				lc.setParent(item);

							
				item.setValue(rec);
			}
		};
	}
*/
	
	private EventListener closeWindow() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				resultValue = null;
				getWindow().onClose();
			}
		};
	}
	
	

	private EventListener onClickSave() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				String vErrMsg = validationBussiness();
				if (vErrMsg != null) {
					ZksampleMessageUtils.showErrorMessage(vErrMsg);
				} else {
					doSave();
					getWindow().onClose();
				}
			}
		};
	}

	
	public String validationBussiness() {

//		if (CommonUtils.isNotEmpty(txtItemDesc.getValue()) == false) {
//			return "Product Description "
//					+ Labels.getLabel("message.error.mandatory");
//		}
		
//		
//		//System.out.println("id t08 -->"+initValue.getT08Id());
//		if (CommonUtils.isNotEmpty(initM02Salesperson.getM03Targetsaless())) {
//	
//			for(M03Targetsales anM03 : initM02Salesperson.getM03Targetsaless()){
//				if(CommonUtils.isNotEmpty(anM03)){
//					if(		anM03.getTahun().equals(txtTahun.getValue())
//								&&
//							anM03.getM03Id() != initValue.getM03Id()
//							
//						){
//						return "Tahun tidak boleh duplikat  !";	
//					}
//				}
//			}
//			
//			
//		}
		
		
		
		
		
		
		return null;
	}

	private void doSave() {
		initValue.setNoInvoice(txtNoInvoice.getValue());
		initValue.setTglInvoice(dtbTglInvoice.getValue());
		initValue.setNilaiInvoice(decNilaiInvoice.getValue());
		
		initValue.setNoLunas(txtNoLunas.getValue());
		initValue.setTglLunas(dtbTglLunas.getValue());
		initValue.setNilaiLunas(decNilaiLunas.getValue());
		
		this.resultValue = initValue;
	}

	

	private Window getWindow() {
		return this;
	}
	

}
