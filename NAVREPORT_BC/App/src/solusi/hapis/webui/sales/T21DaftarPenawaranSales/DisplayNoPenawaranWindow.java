package solusi.hapis.webui.sales.T21DaftarPenawaranSales;


import java.io.Serializable;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.North;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import solusi.hapis.common.CommonUtils;

public class DisplayNoPenawaranWindow extends Window implements Serializable {
	private static final long serialVersionUID = 7826099855146654896L;

	// binding
	private String initValue;
	private String resultValue;

	
	// input text
	private Textbox txtNoPenawaran;	
	

	public static String show(Component parent, String NoPenawaran) {
		return new DisplayNoPenawaranWindow(parent, NoPenawaran).resultValue;
	}

	private DisplayNoPenawaranWindow(Component parent, String NoPenawaran)  {
		super();
		this.initValue = NoPenawaran;
		setParent(parent);
		createBox();
	}

	private void createBox() {
		this.setBorder("none");
		this.setWidth("300px");
		this.setHeight("100px");
//		this.addEventListener(Events.ON_OK, onClickCopy() );
		

		Borderlayout borderLayoutAddDetail = new Borderlayout();

		North north1 = new North();
		north1.setBorder("none");
		north1.setTitle("No Penawaran");
		north1.setParent(borderLayoutAddDetail);
		
		
		South south1 = new South();
		south1.setBorder("none");
		//south1.setTitle("No Penawaran");
		
		
		
		Div divToolbar = new Div();
		divToolbar.setSclass("z-toolbar");
		divToolbar.setStyle("padding:0");
		Hbox hbox = new Hbox();
		hbox.setPack("stretch");
		hbox.setSclass("hboxRemoveWhiteStrips");
		hbox.setWidth("100%");
		Toolbar toolbar = new Toolbar();
		toolbar.setAlign("center");
		//toolbar.setStyle("border-style: none;");
		//toolbar.setStyle("float:right; border-style: none;");
		toolbar.setHeight("28px");
		
		
		
		
//		Button btnCopy = new Button();
//		btnCopy.setHeight("24px");
//		btnCopy.setLabel("Copy");
//		btnCopy.addEventListener(Events.ON_CLICK, onClickCopy());
//		btnCopy.setParent(toolbar);

		Button btnClose = new Button();
		btnClose.setHeight("24px");
		btnClose.setLabel(Labels.getLabel("common.button.close"));
		btnClose.addEventListener(Events.ON_CLICK, closeWindow());
		btnClose.setParent(toolbar);
		
		toolbar.setParent(hbox);
		hbox.setParent(divToolbar);
		divToolbar.setParent(south1);
		south1.setParent(borderLayoutAddDetail);

		Center center = new Center();
		center.setBorder("none");
		center.setAutoscroll(true);
		Panel panel = new Panel();
		panel.setBorder("none");
		Panelchildren panelchildren = new Panelchildren();
		Groupbox groupbox = new Groupbox();
		groupbox.setMold("3d");
		groupbox.setClosable(false);
		//Caption caption = new Caption();
		//caption.setLabel("No Penawaran");
		//caption.setParent(groupbox);
		
		Grid grid = new Grid();
		grid.setSclass("GridLayoutNoBorder");
		grid.setStyle("border:0px; padding-left:5px; padding-right:5px;");

		Columns columns = new Columns();

		Column column1 = new Column();
		//column1.setWidth("150px");
		column1.setParent(columns);

		//Column column2 = new Column();
		//column2.setParent(columns);

		columns.setParent(grid);

		Rows rows = new Rows();

		Row row1 = new Row();

	
			this.txtNoPenawaran = new Textbox();
			this.txtNoPenawaran.setCols(30);
			this.txtNoPenawaran.setReadonly(true);
			this.txtNoPenawaran.setParent(row1);		
		row1.setParent(rows);
		
	
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
		if (CommonUtils.isNotEmpty(initValue)) {
			txtNoPenawaran.setValue(initValue);
		}
		
		

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
	
	

/*	private EventListener onClickCopy() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
					StringSelection stringSelection = new StringSelection(initValue);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);
					
					//getWindow().onClose();
				
			}
		};
	}*/

	



	

	private Window getWindow() {
		return this;
	}

}

