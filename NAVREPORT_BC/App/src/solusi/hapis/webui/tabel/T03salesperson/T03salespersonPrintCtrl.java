package solusi.hapis.webui.tabel.T03salesperson;


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
 * Date: 13-03-2018
 */

public class T03salespersonPrintCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT03salespersonPrint;
	
	protected Borderlayout borderlayout_T03salespersonPrint;
	
	// Screen Parameter Components

	protected Textbox txtSalesFrom;  
	protected Textbox txtSalesUpto;
	
	private T03salespersonMainCtrl T03salespersonMainCtrl;
	
    /**
     * default constructor.<br>
     */
    public T03salespersonPrintCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT03salespersonMainCtrl((T03salespersonMainCtrl) arg.get("ModuleMainController"));
        	T03salespersonMainCtrl.setT03salespersonPrintCtrl(this);
        }
    }
	
	public void onCreate$windowT03salespersonPrint(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T03salespersonPrint.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT03salespersonPrint.invalidate();
	}
	
	public void doReadOnlyMode(boolean b) throws ParseException {
		// Set Default		

		txtSalesUpto.setValue("ZZZ");
				
		txtSalesFrom.setReadonly(b);
		txtSalesFrom.setDisabled(b);
		txtSalesUpto.setReadonly(b);
		txtSalesUpto.setDisabled(b);	
		
		
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getParameterReport() throws ParseException {
		
		String vSalesFrom = ".";
		if(CommonUtils.isNotEmpty(txtSalesFrom.getValue())){
			vSalesFrom = txtSalesFrom.getValue();
		}
 
		String vSalesUpto = "ZZZ";
		if(CommonUtils.isNotEmpty(txtSalesUpto.getValue())){
			vSalesUpto= txtSalesUpto.getValue();
		}
		
		
			
		param.put("salesFrom", vSalesFrom);
		param.put("salesUpto", vSalesUpto);
		
		
		return param;
	}
    
    
	public void setT03salespersonMainCtrl(T03salespersonMainCtrl T03salespersonMainCtrl) {
		this.T03salespersonMainCtrl = T03salespersonMainCtrl;
	}

	public T03salespersonMainCtrl getT03salespersonMainCtrl() {
		return this.T03salespersonMainCtrl;
	}

}

