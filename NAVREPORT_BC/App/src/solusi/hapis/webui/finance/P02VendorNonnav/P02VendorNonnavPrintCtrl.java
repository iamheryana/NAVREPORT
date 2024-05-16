package solusi.hapis.webui.finance.P02VendorNonnav;


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
 * Date: 30-01-2020 */

public class P02VendorNonnavPrintCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowP02VendorNonnavPrint;
	
	protected Borderlayout borderlayout_P02VendorNonnavPrint;
	
	// Screen Parameter Components

	protected Textbox txtNoFrom;  
	protected Textbox txtNoUpto;
	
	private P02VendorNonnavMainCtrl P02VendorNonnavMainCtrl;
	
    /**
     * default constructor.<br>
     */
    public P02VendorNonnavPrintCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setP02VendorNonnavMainCtrl((P02VendorNonnavMainCtrl) arg.get("ModuleMainController"));
        	P02VendorNonnavMainCtrl.setP02VendorNonnavPrintCtrl(this);
        }
    }
	
	public void onCreate$windowP02VendorNonnavPrint(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_P02VendorNonnavPrint.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowP02VendorNonnavPrint.invalidate();
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
    
    
	public void setP02VendorNonnavMainCtrl(P02VendorNonnavMainCtrl P02VendorNonnavMainCtrl) {
		this.P02VendorNonnavMainCtrl = P02VendorNonnavMainCtrl;
	}

	public P02VendorNonnavMainCtrl getP02VendorNonnavMainCtrl() {
		return this.P02VendorNonnavMainCtrl;
	}

}

