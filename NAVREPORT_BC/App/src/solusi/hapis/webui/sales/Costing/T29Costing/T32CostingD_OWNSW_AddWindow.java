package solusi.hapis.webui.sales.Costing.T29Costing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Center;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.backend.navbi.model.T32CostingDOwnsw;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.ListBoxUtil;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class T32CostingD_OWNSW_AddWindow extends Window implements Serializable {
	private static final long serialVersionUID = 7826099855146654896L;
	
	// binding
	private T32CostingDOwnsw initValue;
	private T32CostingDOwnsw resultValue;
	
	private T29CostingH initT29CostingH;
	
	
	protected Bandbox cmbProductGroup;
	protected Listbox listProductGroup;
	
	
	private Textbox txtItemDesc;
	private Textbox txtItemNo;

	private Textbox txtCatatan;
	private Decimalbox decQty;
	private Decimalbox decSalesSatuan;
	private Decimalbox decSalesTotal;

	
	private String modeTitle;
	
	
	private SelectQueryService selectQueryService;
	
	public static T32CostingDOwnsw show(Component parent, T32CostingDOwnsw t32CostingDOwnsw, T29CostingH t29CostingH, String modeTitle, SelectQueryService selectQueryService) {
		return new T32CostingD_OWNSW_AddWindow(parent, t32CostingDOwnsw, t29CostingH, modeTitle, selectQueryService).resultValue;
	}

	private T32CostingD_OWNSW_AddWindow(Component parent, T32CostingDOwnsw t32CostingDOwnsw, T29CostingH t29CostingH, String modeTitle, SelectQueryService selectQueryService) {
		super();
		this.initValue = t32CostingDOwnsw;
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
		caption.setLabel("ACS Own Sorfware - "+modeTitle);
		caption.setParent(groupbox);
		Grid grid = new Grid();
		grid.setSclass("GridLayoutNoBorder");
		grid.setStyle("border:0px; padding-left:5px; padding-right:5px;");

		Columns columns = new Columns();

		Column column1 = new Column();
		column1.setWidth("150px");
		column1.setParent(columns);

		Column column2 = new Column();
		column2.setParent(columns);

		columns.setParent(grid);

		Rows rows = new Rows();

		Row row1 = new Row();
			Hbox hboxRow1_1 = new Hbox();
				new Label("Product Desc.").setParent(hboxRow1_1);
				
				Label starItemDesc = new Label("*");
				starItemDesc.setStyle("color:red;font-size:10px");
				starItemDesc.setParent(hboxRow1_1);		
			hboxRow1_1.setParent(row1);
		
		
			this.txtItemDesc = new Textbox();
			this.txtItemDesc.setCols(80);
			this.txtItemDesc.setMaxlength(300);
			if (modeTitle.equals("View")){
				this.txtItemDesc.setReadonly(true);
			}
			this.txtItemDesc.setParent(row1);		
		row1.setParent(rows);
		
	
		Row row2 = new Row();
			new Label("Product No.").setParent(row2);
				
	
			this.txtItemNo = new Textbox();
			this.txtItemNo.setCols(25);
			this.txtItemNo.setMaxlength(50);
			if (modeTitle.equals("View")){
				this.txtItemNo.setReadonly(true);
			}
			this.txtItemNo.setParent(row2);		
		row2.setParent(rows);
		
		Row row4 = new Row();
			new Label("Item Product").setParent(row4);
				
	
			cmbProductGroup = new Bandbox();
			cmbProductGroup.setMold("rounded");
			cmbProductGroup.setWidth("450px");
			cmbProductGroup.setAutodrop(true);
				Bandpopup popup2 = new Bandpopup();
					listProductGroup = new Listbox();
					//listProductGroup.setMold("paging");
					//listProductGroup.setPageSize(10);
					listProductGroup.setAutopaging(true);
					listProductGroup.setWidth("450px");
					listProductGroup.addEventListener(Events.ON_SELECT, selectProductGroup());
					listProductGroup.setParent(popup2);
				popup2.setParent(cmbProductGroup);
			if (modeTitle.equals("View")){
				cmbProductGroup.setDisabled(true);
			}
			cmbProductGroup.setParent(row4);		
		row4.setParent(rows);
		
		Row row6= new Row();
			new Space().setParent(row6);
				
			Groupbox groupboxDtl = new Groupbox();
				groupboxDtl.setMold("3d");
				groupboxDtl.setClosable(false);

				Grid gridDtl = new Grid();
					gridDtl.setSclass("GridLayoutNoBorder");
					gridDtl.setStyle("border:0px; padding-left:5px; padding-right:5px;");

					Columns columnsDtl = new Columns();

						Column columnDetail1 = new Column();
						columnDetail1.setWidth("125px");
						columnDetail1.setParent(columnsDtl);

						Column columnDetail2 = new Column();
						columnDetail2.setWidth("200px");
						columnDetail2.setParent(columnsDtl);
						
						
						Column columnDetail3 = new Column();
						columnDetail3.setParent(columnsDtl);
	
					columnsDtl.setParent(gridDtl);
					

					Rows rowsDtl = new Rows();
						Row rowDtl1 = new Row();	
							new Label("Qty").setParent(rowDtl1);
							
							this.decQty= new Decimalbox();
							this.decQty.setFormat("#,##0");
							this.decQty.setStyle("text-align:right;");		
							this.decQty.addEventListener(Events.ON_CHANGE,
									changeNilaiListener());
							this.decQty.setCols(10);
							this.decQty.setMaxlength(10);
							if (modeTitle.equals("View")){
								this.decQty.setReadonly(true);
							}
							this.decQty.setParent(rowDtl1);	
						rowDtl1.setParent(rowsDtl);
						
						Row rowDtl2 = new Row();	
							new Space().setParent(rowDtl2);
							new Label("per Unit").setParent(rowDtl2);
							new Label("Total").setParent(rowDtl2);
						rowDtl2.setParent(rowsDtl);
					
						Row rowDtl3 = new Row();	
							new Label("Selling Price").setParent(rowDtl3);
						
							this.decSalesSatuan= new Decimalbox();
							this.decSalesSatuan.setFormat("#,##0");
							this.decSalesSatuan.setStyle("text-align:right;");	
							this.decSalesSatuan.addEventListener(Events.ON_CHANGE,
									changeNilaiListener());
							this.decSalesSatuan.setCols(20);
							this.decSalesSatuan.setMaxlength(20);
							if (modeTitle.equals("View")){
								this.decSalesSatuan.setReadonly(true);
							}
							this.decSalesSatuan.setParent(rowDtl3);	
							
							this.decSalesTotal= new Decimalbox();
							this.decSalesTotal.setFormat("#,##0");
							this.decSalesTotal.setStyle("text-align:right;");			
							this.decSalesTotal.setCols(20);
							this.decSalesTotal.setMaxlength(20);
							this.decSalesTotal.setReadonly(true);
							this.decSalesTotal.setParent(rowDtl3);							
						rowDtl3.setParent(rowsDtl);
						
					rowsDtl.setParent(gridDtl);
				gridDtl.setParent(groupboxDtl);
			groupboxDtl.setParent(row6);
							
		row6.setParent(rows);
		
		
		Row row9 = new Row();
			new Label("Catatan").setParent(row9);
				
	
			this.txtCatatan = new Textbox();
			this.txtCatatan.setCols(80);
			this.txtCatatan.setMaxlength(200);
			if (modeTitle.equals("View")){
				this.txtCatatan.setReadonly(true);
			}
			this.txtCatatan.setParent(row9);		
		row9.setParent(rows);
	
	
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
	
	
	private EventListener selectProductGroup() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbProductGroup.setValue(listProductGroup.getSelectedItem().getLabel());
				initValue.setProduct((String) listProductGroup.getSelectedItem().getValue());
				
				cmbProductGroup.close();
			}
		};
	}
	public EventListener changeNilaiListener() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				BigDecimal vQty = new BigDecimal(0);
				if (CommonUtils.isNotEmpty(decQty.getValue())) {
					vQty = decQty.getValue();
				}
				
				BigDecimal vSalesSatuan = new BigDecimal(0);
				if (CommonUtils.isNotEmpty(decSalesSatuan.getValue())) {
					vSalesSatuan = decSalesSatuan.getValue();
				}
				
				
				
				
				BigDecimal vSalesTotal = new BigDecimal(0);
				vSalesTotal = vQty.multiply(vSalesSatuan);
				
				
				
				decSalesTotal.setValue(vSalesTotal);
				
			}
		};

	}

	
	private void renderProductGroup() {
		ListBoxUtil.resetList(listProductGroup);
		Listitem vListProductGroup = null;

		Listitem vListBlank2 =  listProductGroup.appendItem("<<<Please Select>>>", "");
		
		
		List<Object[]> vResult2 = selectQueryService.QueryProductGroupCosting(initValue.getItemCategory());
		if(CommonUtils.isNotEmpty(vResult2)){
			for(Object[] aRslt : vResult2){
				
				Listitem vList = listProductGroup.appendItem(aRslt[0].toString(),aRslt[1].toString());
				
				if (CommonUtils.isNotEmpty(initValue.getProduct())) {
					if (initValue.getProduct().equals(aRslt[1].toString())) {
						vListProductGroup = vList;
					}  
				}
			}
		}
		if (vListProductGroup == null){
			vListProductGroup = vListBlank2;
		}
		
		cmbProductGroup.setValue(vListProductGroup.getLabel());
		listProductGroup.setSelectedItem(vListProductGroup);
		
	}
	
	private void init() {
		if (CommonUtils.isNotEmpty(initValue.getItemDesc())) {
			txtItemDesc.setValue(initValue.getItemDesc());
		}
		
		if (CommonUtils.isNotEmpty(initValue.getItemNo())) {
			txtItemNo.setValue(initValue.getItemNo());
		}
		
		renderProductGroup();

		if (CommonUtils.isNotEmpty(initValue.getCatatan())) {
			txtCatatan.setValue(initValue.getCatatan());
		}
		
		if (CommonUtils.isNotEmpty(initValue.getQty())) {
			decQty.setValue(initValue.getQty());
		}
		
		if (CommonUtils.isNotEmpty(initValue.getSalesSatuan())) {
			decSalesSatuan.setValue(initValue.getSalesSatuan());
		}
		
		if (CommonUtils.isNotEmpty(initValue.getSalesTotal())) {
			decSalesTotal.setValue(initValue.getSalesTotal());
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

		if (CommonUtils.isNotEmpty(txtItemDesc.getValue()) == false) {
			return "Product Description "
					+ Labels.getLabel("message.error.mandatory");
		}
		
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
		initValue.setItemDesc(txtItemDesc.getValue());
		initValue.setItemNo(txtItemNo.getValue());
		
		initValue.setCatatan(txtCatatan.getValue());
		
		initValue.setQty(decQty.getValue());
		initValue.setSalesSatuan(decSalesSatuan.getValue());
		initValue.setSalesTotal(decSalesTotal.getValue());
		
		this.resultValue = initValue;
	}

	

	private Window getWindow() {
		return this;
	}
	

}
