package solusi.hapis.webui.tabel.T07itemsatindo;


import java.io.Serializable;
import java.text.ParseException;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 05-07-2018
 */

public class T07itemsatindoPrintCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT07itemsatindoPrint;
	
	protected Borderlayout borderlayout_T07itemsatindoPrint;
	
	// Screen Parameter Components

	protected Textbox txtItemFrom;  
	protected Textbox txtItemUpto;
	
	private T07itemsatindoMainCtrl T07itemsatindoMainCtrl;
	
    /**
     * default constructor.<br>
     */
    public T07itemsatindoPrintCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT07itemsatindoMainCtrl((T07itemsatindoMainCtrl) arg.get("ModuleMainController"));
        	T07itemsatindoMainCtrl.setT07itemsatindoPrintCtrl(this);
        }
    }
	
	public void onCreate$windowT07itemsatindoPrint(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T07itemsatindoPrint.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT07itemsatindoPrint.invalidate();
	}
	
	public void doReadOnlyMode(boolean b) throws ParseException {
		// Set Default		

		txtItemUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
				
		txtItemFrom.setReadonly(b);
		txtItemFrom.setDisabled(b);
		txtItemUpto.setReadonly(b);
		txtItemUpto.setDisabled(b);	
		
		
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getParameterReport() throws ParseException {
		
		String vItemFrom = ".";
		if(CommonUtils.isNotEmpty(txtItemFrom.getValue())){
			vItemFrom = txtItemFrom.getValue();
		}
 
		String vItemUpto = "ZZZ";
		if(CommonUtils.isNotEmpty(txtItemUpto.getValue())){
			vItemUpto= txtItemUpto.getValue();
		}
		
		
			
		param.put("ItemFrom", vItemFrom);
		param.put("ItemUpto", vItemUpto);
		
		
		return param;
	}
    
    
	public void setT07itemsatindoMainCtrl(T07itemsatindoMainCtrl T07itemsatindoMainCtrl) {
		this.T07itemsatindoMainCtrl = T07itemsatindoMainCtrl;
	}

	public T07itemsatindoMainCtrl getT07itemsatindoMainCtrl() {
		return this.T07itemsatindoMainCtrl;
	}

}

