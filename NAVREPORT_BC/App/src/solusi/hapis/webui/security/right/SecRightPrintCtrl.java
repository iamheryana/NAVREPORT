package solusi.hapis.webui.security.right;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.common.config.report.ParameterReportDTO;
import solusi.hapis.common.config.report.ReportDTO;
import solusi.hapis.policy.model.UserImpl;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SecRightPrintCtrl extends GFCBaseCtrl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5621313074817347892L;
	protected Radiogroup rdg_screenParameter;
	protected Radio rd_ya;
	protected Radio rd_tidak;
	protected Textbox txt_kodeFrom;
	protected Textbox txt_kodeTo;
	@SuppressWarnings("unused")
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	protected Borderlayout borderlayout_SecRightPrint;
	protected Window windowSecRightPrint;
	private SecRightMainCtrl mainCtrl;
	
    /**
     * default constructor.<br>
     */
    public SecRightPrintCtrl() {
        super();
    }

    
    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setMainCtrl((SecRightMainCtrl) arg.get("ModuleMainController"));
        	mainCtrl.setSecRightPrintCtrl(this);
        }
    }
	
	public void onCreate$windowSecRightPrint(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_SecRightPrint.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowSecRightPrint.invalidate();
	}
	
	public void doReadOnlyMode(boolean b) {
		txt_kodeFrom.setReadonly(b);
		txt_kodeTo.setReadonly(b);
		if (b == false) {
			txt_kodeTo.setValue("ZZZZZZ");
		} else {
			txt_kodeTo.setValue("");
		}
		rd_tidak.setDisabled(b);
		rd_ya.setDisabled(b);
		rd_tidak.setSelected(true);
	}
	
	public ReportDTO getParameterReport(){
		ReportDTO dto = new ReportDTO();
		dto.setRptLocation("WEB-INF/pages/listing/L_SEC_RIGHT.rpt");
		dto.setRptFormat(ReportDTO.REPORT_FORMAT_PDF);
		dto.setRptJudul("Listing Right");
		
		UserImpl user = (UserImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	
		String vKodeFrom = " ";
		if (StringUtils.isNotEmpty(txt_kodeFrom.getValue())) {
			vKodeFrom = txt_kodeFrom.getValue();
		}
		String vKodeTo = "ZZZZZZ";
		if (StringUtils.isNotEmpty(txt_kodeTo.getValue())) {
			vKodeTo = txt_kodeTo.getValue();
		}
		String vScreenParam = "N";
		if (StringUtils.isNotEmpty(rdg_screenParameter.getSelectedItem().getValue())) {
			vScreenParam = rdg_screenParameter.getSelectedItem().getValue();
		}
		dto.getParamReports().add(new ParameterReportDTO("@NamaFr",  vKodeFrom));
		dto.getParamReports().add(new ParameterReportDTO("@NamaTo",  vKodeTo));
//		dto.getParamReports().add(new ParameterReportDTO("@Btcp",  (company.getCurprd() != null) ? sdf.format(company.getCurprd()) : null));
//		dto.getParamReports().add(new ParameterReportDTO("@Company",  company.getPerusahaan()));
		dto.getParamReports().add(new ParameterReportDTO("@SP",  vScreenParam));
		dto.getParamReports().add(new ParameterReportDTO("@UserId",  user.getUsername()));
		
		return dto;
	}
	
	public void setMainCtrl(SecRightMainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}

	public SecRightMainCtrl getMainCtrl() {
		return mainCtrl;
	}
}