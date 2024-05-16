package solusi.hapis.webui.security.group;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecGroup;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SecGroupDetailCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1575867942967315848L;
	protected Window windowSecGroupDetail; // autowired

	protected Borderlayout borderlayout_SecGroupDetail; // autowired

	// Databinding
	protected transient AnnotateDataBinder binder;
	private SecGroupMainCtrl secGroupMainCtrl;
	
	protected Textbox txtb_ShortDesc;
	protected Textbox txtb_LongDesc;
	
	/**
	 * default constructor.<br>
	 */
	public SecGroupDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		/**
		 * 1. Set an 'alias' for this composer name to access it in the
		 * zul-file.<br>
		 * 2. Set the parameter 'recurse' to 'false' to avoid problems with
		 * managing more than one zul-file in one page. Otherwise it would be
		 * overridden and can ends in curious error messages.
		 */
		this.self.setAttribute("controller", this, false);

		/**
		 * 1. Get the overhanded MainController.<br>
		 * 2. Set this controller in the MainController.<br>
		 * 3. Check if a 'selectedObject' exists yet in the MainController.<br>
		 */
		if (arg.containsKey("ModuleMainController")) {
			setSecGroupMainCtrl((SecGroupMainCtrl) arg.get("ModuleMainController"));

			// SET THIS CONTROLLER TO THE module's Parent/MainController
			getSecGroupMainCtrl().setSecGroupDetailCtrl(this);
		}
		windowSecGroupDetail.addEventListener(Events.ON_OK, onEnterForm());
	}
	 
	private EventListener onEnterForm(){
		return new EventListener() {
				
			@Override
			public void onEvent(Event event) throws Exception {
				getSecGroupMainCtrl().onClick$btnSave(event);
			}
		};
	}
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// +++++++++++++++ Component Events ++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	/**
	 * Automatically called method from zk.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$windowSecGroupDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		binder.loadAll();
		doFitSize(event);
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// +++++++++++++++++ Business Logic ++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	/**
	 * Recalculates the container size for this controller and resize them.
	 * <p/>
	 * Calculate how many rows have been place in the listbox. Get the
	 * currentDesktopHeight from a hidden Intbox from the index.zul that are
	 * filled by onClientInfo() in the indexCtroller.
	 */
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_SecGroupDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowSecGroupDetail.invalidate();
	}

	/**
	 * Set all components in readOnly/disabled modus.
	 * <p/>
	 * true = all components are readOnly or disabled.<br>
	 * false = all components are accessable.<br>
	 * 
	 * @param b
	 */
	public void doReadOnlyMode(boolean b) {
		txtb_ShortDesc.setReadonly(b);
		txtb_LongDesc.setReadonly(b);
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
	public SecGroup getSecGroup() {
		// STORED IN THE module's MainController
		return getSecGroupMainCtrl().getSelectedSecGroup();
	}

	public void setSecGroup(SecGroup anSecGroup) {
		// STORED IN THE module's MainController
		getSecGroupMainCtrl().setSelectedSecGroup(anSecGroup);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public SecGroupMainCtrl getSecGroupMainCtrl() {
		return secGroupMainCtrl;
	}

	public void setSecGroupMainCtrl(SecGroupMainCtrl secGroupMainCtrl) {
		this.secGroupMainCtrl = secGroupMainCtrl;
	}
}
