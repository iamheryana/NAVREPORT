package solusi.hapis.webui.logistic.T01SoAdj;


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
 * Date: 28-08-2019 */

public class T01SoAdjPrintCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT01SoAdjPrint;
	
	protected Borderlayout borderlayout_T01SoAdjPrint;
	
	// Screen Parameter Components

	protected Textbox txtNoFrom;  
	protected Textbox txtNoUpto;
	
	private T01SoAdjMainCtrl T01SoAdjMainCtrl;
	
    /**
     * default constructor.<br>
     */
    public T01SoAdjPrintCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT01SoAdjMainCtrl((T01SoAdjMainCtrl) arg.get("ModuleMainController"));
        	T01SoAdjMainCtrl.setT01SoAdjPrintCtrl(this);
        }
    }
	
	public void onCreate$windowT01SoAdjPrint(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T01SoAdjPrint.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT01SoAdjPrint.invalidate();
	}
	
	public void doReadOnlyMode(boolean b) throws ParseException {
		// Set Default		

		txtNoUpto.setValue("ZZZZZZZZZZZZZZZZZZZZ");
				
		txtNoFrom.setReadonly(b);
		txtNoFrom.setDisabled(b);
		txtNoUpto.setReadonly(b);
		txtNoUpto.setDisabled(b);	
		
		
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getParameterReport() throws ParseException {
		
		String vItemFrom = ".";
		if(CommonUtils.isNotEmpty(txtNoFrom.getValue())){
			vItemFrom = txtNoFrom.getValue();
		}
 
		String vItemUpto = "ZZZZZZZZZZZZZZZZZZZZ";
		if(CommonUtils.isNotEmpty(txtNoUpto.getValue())){
			vItemUpto= txtNoUpto.getValue();
		}
		
		
			
		param.put("ItemFrom", vItemFrom);
		param.put("ItemUpto", vItemUpto);
		
		
		return param;
	}
    
    
	public void setT01SoAdjMainCtrl(T01SoAdjMainCtrl T01SoAdjMainCtrl) {
		this.T01SoAdjMainCtrl = T01SoAdjMainCtrl;
	}

	public T01SoAdjMainCtrl getT01SoAdjMainCtrl() {
		return this.T01SoAdjMainCtrl;
	}

}

