package solusi.hapis.webui.markom.T05WebinarEvent;




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
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.North;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.backend.navbi.model.T06WebinarAttendee;
import solusi.hapis.common.CommonUtils;

public class T05WebinarEventAddDetailWindow extends Window implements Serializable {
	private static final long serialVersionUID = 7826099855146654896L;

	// binding
	private T06WebinarAttendee initValue;
	private T06WebinarAttendee resultValue;
	
	private T05WebinarEvent initT05WebinarEvent;
	
	// input text



	
	public static T06WebinarAttendee show(Component parent, T06WebinarAttendee t06WebinarAttandee, T05WebinarEvent t05WebinarEvent) {
		return new T05WebinarEventAddDetailWindow(parent, t06WebinarAttandee, t05WebinarEvent).resultValue;
	}

	private T05WebinarEventAddDetailWindow(Component parent, T06WebinarAttendee t06WebinarAttandee, T05WebinarEvent t05WebinarEvent) {
		super();
		this.initValue = t06WebinarAttandee;
		this.initT05WebinarEvent = t05WebinarEvent;
		setParent(parent);
		createBox();
	}

	private void createBox() {
		this.setBorder("none");
		this.setWidth("850px");
		this.setHeight("400px");
//		this.addEventListener(Events.ON_OK, onClickSave() );
		

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
		column1.setWidth("175px");
		column1.setParent(columns);
		
		Column column1_2 = new Column();
		column1_2.setWidth("10px");
		column1_2.setParent(columns);

		Column column2 = new Column();
		column2.setWidth("250px");
		column2.setParent(columns);

		Column column3 = new Column();
		column3.setWidth("175px");
		column3.setParent(columns);
		
		Column column3_4 = new Column();
		column3_4.setWidth("10px");
		column3_4.setParent(columns);

		Column column4 = new Column();
		column4.setParent(columns);
		
		
		columns.setParent(grid);

		Rows rows = new Rows();

		Row row1 = new Row();
			new Label("Attended").setParent(row1);
			new Label(":").setParent(row1);
			Label lblRow1_1 = new Label();
			lblRow1_1.setParent(row1);
			if (CommonUtils.isNotEmpty(initValue.getAttended())) {	
				lblRow1_1.setValue(initValue.getAttended());
			}
			
			
			new Label("Registration Time").setParent(row1);
			new Label(":").setParent(row1);
			Datebox dtRow1RegisTime = new Datebox();
			dtRow1RegisTime.setCols(20);
			dtRow1RegisTime.setDisabled(true);
			dtRow1RegisTime.setFormat("dd-MM-yyyy HH:mm");
			dtRow1RegisTime.setParent(row1);
			if (CommonUtils.isNotEmpty(initValue.getRegistrationTime())) {	
				dtRow1RegisTime.setValue(initValue.getRegistrationTime());
			}	
		row1.setParent(rows);

		Row row2 = new Row();
			new Label("First Name").setParent(row2);
			new Label(":").setParent(row2);
			Label lblRow2_1 = new Label();
			lblRow2_1.setParent(row2);
			if (CommonUtils.isNotEmpty(initValue.getFirstName())) {	
				lblRow2_1.setValue(initValue.getFirstName());
			}
			
			
			new Label("Company").setParent(row2);
			new Label(":").setParent(row2);
			Label lblRow2_2 = new Label();
			lblRow2_2.setParent(row2);
			if (CommonUtils.isNotEmpty(initValue.getOrganization())) {	
				lblRow2_2.setValue(initValue.getOrganization());
			}
		row2.setParent(rows);
	
		Row row3 = new Row();
			new Label("Last Name").setParent(row3);
			new Label(":").setParent(row3);
			Label lblRow3_1 = new Label();
			lblRow3_1.setParent(row3);
			if (CommonUtils.isNotEmpty(initValue.getLastName())) {	
				lblRow3_1.setValue(initValue.getLastName());
			}
			
			
			new Label("Job Title").setParent(row3);
			new Label(":").setParent(row3);
			Label lblRow3_2 = new Label();
			lblRow3_2.setParent(row3);
			if (CommonUtils.isNotEmpty(initValue.getJobTitle())) {	
				lblRow3_2.setValue(initValue.getJobTitle());
			}
		row3.setParent(rows);		
		
		Row row4 = new Row();
			new Label("Email").setParent(row4);
			new Label(":").setParent(row4);
			Label lblRow4_1 = new Label();
			lblRow4_1.setParent(row4);
			if (CommonUtils.isNotEmpty(initValue.getEmail())) {	
				lblRow4_1.setValue(initValue.getEmail());
			}
			
			
			new Label("Time in Session (minutes)").setParent(row4);
			new Label(":").setParent(row4);
			Intbox lblRow4_2 = new Intbox();
			lblRow4_2.setFormat("#,##0");
			lblRow4_2.setStyle("text-align:right;");
			lblRow4_2.setCols(5);
			lblRow4_2.setReadonly(true);
			lblRow4_2.setParent(row4);
			if (CommonUtils.isNotEmpty(initValue.getTimeInSession())) {	
				lblRow4_2.setValue(initValue.getTimeInSession());
			}
		row4.setParent(rows);	
		
		Row row5 = new Row();
			new Label("City").setParent(row5);
			new Label(":").setParent(row5);
			Label lblRow5_1 = new Label();
			lblRow5_1.setParent(row5);
			if (CommonUtils.isNotEmpty(initValue.getStateProvince())) {	
				lblRow5_1.setValue(initValue.getStateProvince());
			}
			
			
			new Label("Country/Region Name").setParent(row5);
			new Label(":").setParent(row5);
			Label lblRow5_2 = new Label();
			lblRow5_2.setParent(row5);
			if (CommonUtils.isNotEmpty(initValue.getCountryRegionName())) {	
				lblRow5_2.setValue(initValue.getCountryRegionName());
			}
		row5.setParent(rows);		
		
		
//		Row row6 = new Row();
//
//		row6.setParent(rows);			

		Row row7 = new Row();
			new Label("Whatsapp No.").setParent(row7);
			new Label(":").setParent(row7);
			Label lblRow7_1 = new Label();
			lblRow7_1.setParent(row7);
			if (CommonUtils.isNotEmpty(initValue.getPhone())) {	
				lblRow7_1.setValue(initValue.getPhone());
			}
			
			
			new Label("GOPAY No.").setParent(row7);
			new Label(":").setParent(row7);
			Label lblRow7_2 = new Label();
			lblRow7_2.setParent(row7);
			if (CommonUtils.isNotEmpty(initValue.getNomorGopayOvo())) {	
				lblRow7_2.setValue(initValue.getNomorGopayOvo());
			}
		row7.setParent(rows);	
		
		
		Row row8 = new Row();

			new Label("Approval Status").setParent(row8);
			new Label(":").setParent(row8);
			Label lblRow8_2 = new Label();
			lblRow8_2.setParent(row8);
			if (CommonUtils.isNotEmpty(initValue.getApprovalStatus())) {	
				lblRow8_2.setValue(initValue.getApprovalStatus());
			}
		row8.setParent(rows);	
		
		Row row9 = new Row();
			new Label("Join Time").setParent(row9);
			new Label(":").setParent(row9);
			Datebox dtRow9JoinTime = new Datebox();
			dtRow9JoinTime.setCols(20);
			dtRow9JoinTime.setDisabled(true);
			dtRow9JoinTime.setFormat("dd-MM-yyyy HH:mm");
			dtRow9JoinTime.setParent(row9);
			if (CommonUtils.isNotEmpty(initValue.getJoinTime())) {	
				dtRow9JoinTime.setValue(initValue.getJoinTime());
			}	
			
			
			new Label("Leave Time").setParent(row9);
			new Label(":").setParent(row9);
			Datebox dtRow9LeaveTime = new Datebox();
			dtRow9LeaveTime.setCols(20);
			dtRow9LeaveTime.setDisabled(true);
			dtRow9LeaveTime.setFormat("dd-MM-yyyy HH:mm");
			dtRow9LeaveTime.setParent(row9);
			if (CommonUtils.isNotEmpty(initValue.getLeaveTime())) {	
				dtRow9LeaveTime.setValue(initValue.getLeaveTime());
			}	
		row9.setParent(rows);
	
		rows.setParent(grid);
		
		grid.setParent(groupbox);
		
		groupbox.setParent(panelchildren);	
		
//		createDetailItem(panelchildren);
		
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
//		if (CommonUtils.isNotEmpty(initValue.getTahun())) {
//			txtTahun.setValue(initValue.getTahun());
//		}
//		
//		if (CommonUtils.isNotEmpty(initValue.getTarget())) {
//			decTarget.setValue(initValue.getTarget());
//		}
//		

	}
	
//	private void createDetailItem(Component panel) {
//		
//		Groupbox detailItemBox = new Groupbox();
//		detailItemBox.setMold("3d");
//		detailItemBox.setClosable(false);
//		
//		Grid gridDetailItem = new Grid();
//		gridDetailItem.setParent(detailItemBox);
//		
//		Rows rowsDetailItem = new Rows();
//		rowsDetailItem.setParent(gridDetailItem);
//		
//		
//		Row rowDetailItem2 = new Row();
//		listboxDetailItem = new Listbox();
//		listboxDetailItem.setStyle("border-top-width: 0px; border-left-width: 0px; border-right-width: 0px; border-bottom-width: 1px;");
//		listboxDetailItem.setVflex(true);
//		listboxDetailItem.setHeight("99%");
//		listboxDetailItem.setWidth("100%");
//		listboxDetailItem.setMultiple(false);
//		listboxDetailItem.setItemRenderer(itemRenderDetailItem());
//
//		if (CommonUtils.isNotEmpty(initT05WebinarEvent.getT06WebinarAttendees())) {
//			listModelDetailItem.addAll(initT05WebinarEvent.getT06WebinarAttendees());
//		}
//
//		
//
//		listboxDetailItem.setModel(listModelDetailItem);
//
//		
//		Listhead listheadDetailItem = new Listhead();
//		listheadDetailItem.setSizable(true);
//
//		Listheader listheaderDetailItem = new Listheader();
//		listheaderDetailItem.setSclass("FDListBoxHeader1");
//		listheaderDetailItem.setSort("auto");
//		listheaderDetailItem.setLabel("Tahun");
//		listheaderDetailItem.setSortAscending(new FieldComparator("tahun", true));
//		listheaderDetailItem.setSortDescending(new FieldComparator("tahun", false));
//		listheaderDetailItem.setParent(listheadDetailItem);
//		
//		
//		listheaderDetailItem = new Listheader();
//		listheaderDetailItem.setSclass("FDListBoxHeader1");
//		listheaderDetailItem.setSort("auto");
//		listheaderDetailItem.setLabel("Target");
//		listheaderDetailItem.setAlign("right");
//		listheaderDetailItem.setSortAscending(new FieldComparator("target", true));
//		listheaderDetailItem.setSortDescending(new FieldComparator("target", false));
//		listheaderDetailItem.setParent(listheadDetailItem);
//		
//		
//		listheadDetailItem.setParent(listboxDetailItem);
//		listboxDetailItem.setParent(rowDetailItem2);
//		rowDetailItem2.setParent(rowsDetailItem);
//
//		detailItemBox.setParent(panel);
//	}
//	
//	private ListitemRenderer itemRenderDetailItem() {
//		return new ListitemRenderer() {
//
//			@Override
//			public void render(Listitem item, Object obj) throws Exception {
//				renderData(item, (T06WebinarAttendee) obj);
//			}
//
//			private void renderData(Listitem item, T06WebinarAttendee rec) {
//				Listcell lc ;
//				
//			
//				lc = new Listcell(rec.getTahun());
//				lc.setParent(item);
//
//				lc = new Listcell(CommonUtils.formatNumberManual(rec.getTarget(),"#,###,###"));
//				lc.setStyle("text-align:right");
//				lc.setParent(item);
//
//							
//				item.setValue(rec);
//			}
//		};
//	}
	
	private EventListener closeWindow() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				resultValue = null;
				getWindow().onClose();
			}
		};
	}
	
	

//	private EventListener onClickSave() {
//		return new EventListener() {
//
//			@Override
//			public void onEvent(Event event) throws Exception {
//				String vErrMsg = validationBussiness();
//				if (vErrMsg != null) {
//					ZksampleMessageUtils.showErrorMessage(vErrMsg);
//				} else {
//					doSave();
//					getWindow().onClose();
//				}
//			}
//		};
//	}
//
//	
//	public String validationBussiness() {
//
//		if (CommonUtils.isNotEmpty(txtTahun.getValue()) == false) {
//			return "Tahun "
//					+ Labels.getLabel("message.error.mandatory");
//		}
//		
//		//System.out.println("id t08 -->"+initValue.getT08Id());
//		if (CommonUtils.isNotEmpty(initT05WebinarEvent.getT06WebinarAttendees())) {
//	
//			for(T06WebinarAttendee anT08 : initT05WebinarEvent.getT06WebinarAttendees()){
//				if(CommonUtils.isNotEmpty(anT08)){
//					if(		anT08.getTahun().equals(txtTahun.getValue())
//								&&
//							anT08.getT08Id() != initValue.getT08Id()
//							
//						){
//						return "Tahun tidak boleh duplikat  !";	
//					}
//				}
//			}
//			
//			
//		}
//		
//		
//		
//		
//		
//		
//		return null;
//	}
//
//	private void doSave() {
//		initValue.setTahun(txtTahun.getValue());		
//		initValue.setTarget(decTarget.getValue());
//		
//		this.resultValue = initValue;
//	}

	

	private Window getWindow() {
		return this;
	}

}

