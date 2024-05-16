package solusi.hapis.webui.security.right;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecRight;
import solusi.hapis.util.Codec.RightType;
import solusi.hapis.webui.util.EnumConverter;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.GFCListModelCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

public class SecRightDetailCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6106920225720615799L;
	protected Window windowSecRightDetail; // autowired

	protected Borderlayout borderlayout_SecRightDetail; // autowired

	protected Textbox txtb_RightName; // autowired
	protected Textbox txtb_RightType; // autowired
	protected Bandbox cmb_RightType;
	protected Listbox list_RightType;

	// Databinding
	protected transient AnnotateDataBinder binder;
	private SecRightMainCtrl secRightMainCtrl;

	/**
	 * default constructor.<br>
	 */
	public SecRightDetailCtrl() {
		super();
	}

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setSecRightMainCtrl((SecRightMainCtrl) arg.get("ModuleMainController"));
        	secRightMainCtrl.setSecRightDetailCtrl(this);
        }
        windowSecRightDetail.addEventListener(Events.ON_OK, onEnterForm());
    }
    
    private EventListener onEnterForm(){
    	return new EventListener() {
  			
    		@Override
  			public void onEvent(Event event) throws Exception {
  				getSecRightMainCtrl().onClick$btnSave(event);
  			}
    	};
    }
    
    public void onCreate$windowSecRightDetail(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        binder.loadAll();
        doFitSize(event);
    }

    public void doFitSize(Event event) {
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - 148;
        borderlayout_SecRightDetail.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowSecRightDetail.invalidate();
    }

    public void doReadOnlyMode(boolean b) {
        txtb_RightName.setReadonly(b);
        cmb_RightType.setDisabled(b);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doRenderComboBox(){
		ListBoxUtil.resetList(list_RightType);
		GFCListModelCtrl.getInstance().setListModel((new EnumConverter(RightType.class)).getEnumToList(),
				list_RightType, cmb_RightType, (getSecRight() != null) ? getSecRight().getRigType() : null);
    }
	
	public void onSelect$list_RightType(Event event) throws Exception {
		SecRight right = getSecRight();

		right.setRigType(Integer.parseInt(list_RightType.getSelectedItem().getValue().toString()));
		setSecRight(right);
	}
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	/**
	 * Best Pratice Hint:<br>
	 * The setters/getters for the local annotated data binded Beans/Sets are
	 * administered in the module's mainController. Working in this way you have
	 * clean line to share this beans/sets with other controllers.
	 */

	public SecRight getSecRight() {
		// STORED IN THE module's MainController
		return getSecRightMainCtrl().getSelectedSecRight();
	}

	public void setSecRight(SecRight selectedSecRight) {
		// STORED IN THE module's MainController
		getSecRightMainCtrl().setSelectedSecRight(selectedSecRight);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public SecRightMainCtrl getSecRightMainCtrl() {
		return secRightMainCtrl;
	}

	public void setSecRightMainCtrl(SecRightMainCtrl secRightMainCtrl) {
		this.secRightMainCtrl = secRightMainCtrl;
	}
}
