package solusi.hapis.webui.general.crossModule.M07UserroleCosting;

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
import org.zkoss.zul.Div;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.North;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.M07UserroleCostingH;
import solusi.hapis.backend.navbi.model.M08UserroleCostingD;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class M07UserroleCostingAddDetailWindow extends Window implements Serializable {
	private static final long serialVersionUID = 7826099855146654896L;

	// binding
	private M08UserroleCostingD initValue;
	private M08UserroleCostingD resultValue;
	
	private M07UserroleCostingH initM07UserroleCostingH;
	
	// input text
	private Textbox txtFilteruser;
	

	private Listbox listboxDetailItem;
	private ListModelList listModelDetailItem = new ListModelList();

	
	public static M08UserroleCostingD show(Component parent, M08UserroleCostingD m08UserroleCostingD, M07UserroleCostingH m07UserroleCostingH) {
		return new M07UserroleCostingAddDetailWindow(parent, m08UserroleCostingD, m07UserroleCostingH).resultValue;
	}

	private M07UserroleCostingAddDetailWindow(Component parent, M08UserroleCostingD m08UserroleCostingD, M07UserroleCostingH m07UserroleCostingH) {
		super();
		this.initValue = m08UserroleCostingD;
		this.initM07UserroleCostingH = m07UserroleCostingH;
		setParent(parent);
		createBox();
	}

	private void createBox() {
		this.setBorder("none");
		this.setWidth("700px");
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
		caption.setLabel("Sales Target");
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
				new Label("Men-Supervisi").setParent(hboxRow1_1);
				
				Label starQty = new Label("*");
				starQty.setStyle("color:red;font-size:10px");
				starQty.setParent(hboxRow1_1);		
			hboxRow1_1.setParent(row1);
	
			this.txtFilteruser = new Textbox();
			this.txtFilteruser.setCols(5);
			this.txtFilteruser.setMaxlength(4);
			this.txtFilteruser.setParent(row1);		
		row1.setParent(rows);
		
		
	
		rows.setParent(grid);
		
		grid.setParent(groupbox);
		
		groupbox.setParent(panelchildren);	
		
		createDetailItem(panelchildren);
		
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
		if (CommonUtils.isNotEmpty(initValue.getFilteruser())) {
			txtFilteruser.setValue(initValue.getFilteruser());
		}
		
		

	}
	
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

		if (CommonUtils.isNotEmpty(initM07UserroleCostingH.getM08UserroleCostingDs())) {
			listModelDetailItem.addAll(initM07UserroleCostingH.getM08UserroleCostingDs());
		}

		

		listboxDetailItem.setModel(listModelDetailItem);

		
		Listhead listheadDetailItem = new Listhead();
		listheadDetailItem.setSizable(true);

		Listheader listheaderDetailItem = new Listheader();
		listheaderDetailItem.setSclass("FDListBoxHeader1");
		listheaderDetailItem.setSort("auto");
		listheaderDetailItem.setLabel("Men-Supervisi");
		listheaderDetailItem.setSortAscending(new FieldComparator("filteruser", true));
		listheaderDetailItem.setSortDescending(new FieldComparator("filteruser", false));
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
				renderData(item, (M08UserroleCostingD) obj);
			}

			private void renderData(Listitem item, M08UserroleCostingD rec) {
				Listcell lc ;
				
			
				lc = new Listcell(rec.getFilteruser());
				lc.setParent(item);


							
				item.setValue(rec);
			}
		};
	}
	
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

		if (CommonUtils.isNotEmpty(txtFilteruser.getValue()) == false) {
			return "Kode Sales "
					+ Labels.getLabel("message.error.mandatory");
		}
		
		//System.out.println("id t08 -->"+initValue.getT08Id());
		if (CommonUtils.isNotEmpty(initM07UserroleCostingH.getM08UserroleCostingDs())) {
	
			for(M08UserroleCostingD anM08 : initM07UserroleCostingH.getM08UserroleCostingDs()){
				if(CommonUtils.isNotEmpty(anM08)){
					if(		anM08.getFilteruser().equals(txtFilteruser.getValue())
								&&
							anM08.getM08Id() != initValue.getM08Id()
							
						){
						return "Kode Sales tidak boleh duplikat  !";	
					}
				}
			}
			
			
		}
		

		return null;
	}

	private void doSave() {
		initValue.setFilteruser(txtFilteruser.getValue());	
		
		this.resultValue = initValue;
	}

	

	private Window getWindow() {
		return this;
	}

}

