package solusi.hapis.webui.finance.T03CetakSlip;



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
 * Date: 23-01-2020 */

public class T03CetakSlipPrintCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT03CetakSlipPrint;
	
	protected Borderlayout borderlayout_T03CetakSlipPrint;
	
	// Screen Parameter Components

	protected Textbox txtNoFrom;  
	protected Textbox txtNoUpto;
	
	private T03CetakSlipMainCtrl T03CetakSlipMainCtrl;
	
    /**
     * default constructor.<br>
     */
    public T03CetakSlipPrintCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT03CetakSlipMainCtrl((T03CetakSlipMainCtrl) arg.get("ModuleMainController"));
        	T03CetakSlipMainCtrl.setT03CetakSlipPrintCtrl(this);
        }
    }
	
	public void onCreate$windowT03CetakSlipPrint(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T03CetakSlipPrint.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT03CetakSlipPrint.invalidate();
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
    
    
	public void setT03CetakSlipMainCtrl(T03CetakSlipMainCtrl T03CetakSlipMainCtrl) {
		this.T03CetakSlipMainCtrl = T03CetakSlipMainCtrl;
	}

	public T03CetakSlipMainCtrl getT03CetakSlipMainCtrl() {
		return this.T03CetakSlipMainCtrl;
	}

}

