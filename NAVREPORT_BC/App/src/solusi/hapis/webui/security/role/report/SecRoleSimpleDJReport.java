/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package solusi.hapis.webui.security.role.report;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecRole;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.webui.util.ZksampleDateFormat;
import solusi.hapis.webui.util.ZksampleMessageUtils;

import java.io.*;
import java.util.*;

/**
 * A simple report implemented with the DynamicJasper framework.<br>
 * <br>
 * This report shows a list of branches.<br>
 * <br>
 * The report uses the FastReportBuilder that have many parameters defined as
 * defaults, so it's very easy to create a simple report with it.<br>
 *
 * @author bbruhns
 * @author sge
 */
public class SecRoleSimpleDJReport extends Window implements Serializable {

    private static final long serialVersionUID = 1L;

    private Iframe iFrame;
    private ByteArrayOutputStream output;
    private InputStream mediais;
    private AMedia amedia;
    private final String zksample2title = "[Zksample2] DynamicJasper Report Sample";

    public SecRoleSimpleDJReport(Component parent) throws InterruptedException {
        super();
        this.setParent(parent);

        try {
            doPrint();
        } catch (final Exception e) {
            ZksampleMessageUtils.showErrorMessage(e.toString());
        }
    }

    public void doPrint() throws JRException, ColumnBuilderException, ClassNotFoundException, IOException {

        FastReportBuilder drb = new FastReportBuilder();
        DynamicReport dr;

        /**
         * Set the styles. In a report created with DynamicReportBuilder we do
         * this in an other way.
         */
        // Rows content
        Style columnStyleNumbers = new Style();
        columnStyleNumbers.setFont(Font.ARIAL_SMALL);
        columnStyleNumbers.setHorizontalAlign(HorizontalAlign.RIGHT);

        // Header for number row content
        Style columnStyleNumbersBold = new Style();
        columnStyleNumbersBold.setFont(Font.ARIAL_MEDIUM_BOLD);
        columnStyleNumbersBold.setHorizontalAlign(HorizontalAlign.RIGHT);
        columnStyleNumbersBold.setBorderBottom(Border.PEN_1_POINT);

        // Rows content
        Style columnStyleText = new Style();
        columnStyleText.setFont(Font.ARIAL_SMALL);
        columnStyleText.setHorizontalAlign(HorizontalAlign.LEFT);

        // Header for String row content
        Style columnStyleTextBold = new Style();
        columnStyleTextBold.setFont(Font.ARIAL_MEDIUM_BOLD);
        columnStyleTextBold.setHorizontalAlign(HorizontalAlign.LEFT);
        columnStyleTextBold.setBorderBottom(Border.PEN_1_POINT);

        // Subtitle
        Style subtitleStyle = new Style();
        subtitleStyle.setHorizontalAlign(HorizontalAlign.LEFT);
        subtitleStyle.setFont(Font.ARIAL_MEDIUM_BOLD);

        // Localized column headers
        String rolShortdescription = Labels.getLabel("listheader_SecRoleList_rolShortdescription.label");
        String rolLongdescription = Labels.getLabel("listheader_SecRoleList_rolLongdescription.label");

        drb.addColumn(rolShortdescription, "rolShortdescription", String.class.getName(), 40, columnStyleText, columnStyleTextBold);
        drb.addColumn(rolLongdescription, "rolLongdescription", String.class.getName(), 100, columnStyleText, columnStyleTextBold);

        // Sets the Report Columns, header, Title, Groups, Etc Formats
        // DynamicJasper documentation
        drb.setTitle(this.zksample2title);
        drb.setSubtitle("List of security roles: " + ZksampleDateFormat.getDateFormater().format(new Date()));
        drb.setSubtitleStyle(subtitleStyle);
        drb.setPrintBackgroundOnOddRows(true);
        drb.setUseFullPageWidth(true);
        dr = drb.build();

        // Get information from database
        SecurityService as = (SecurityService) SpringUtil.getBean("securityService");
        List<SecRole> resultList = as.getAllRoles();

        // Create Datasource and put it in Dynamic Jasper Format
        List data = new ArrayList(resultList.size());

        for (SecRole obj : resultList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rolShortdescription", obj.getRolShortdescription());
            map.put("rolLongdescription", obj.getRolLongdescription());
            data.add(map);
        }

        // Generate the Jasper Print Object
        JRDataSource ds = new JRBeanCollectionDataSource(data);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);

        String outputFormat = "PDF";

        output = new ByteArrayOutputStream();

        if (outputFormat.equalsIgnoreCase("PDF")) {
            JasperExportManager.exportReportToPdfStream(jp, output);
            mediais = new ByteArrayInputStream(output.toByteArray());
            amedia = new AMedia("FirstReport.pdf", "pdf", "application/pdf", mediais);

            callReportWindow(amedia, "PDF");
        } else if (outputFormat.equalsIgnoreCase("XLS")) {
            JExcelApiExporter exporterXLS = new JExcelApiExporter();
            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jp);
            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();
            mediais = new ByteArrayInputStream(output.toByteArray());
            amedia = new AMedia("FileFormatExcel", "xls", "application/vnd.ms-excel", mediais);

            callReportWindow(amedia, "XLS");
        } else if (outputFormat.equalsIgnoreCase("RTF") || outputFormat.equalsIgnoreCase("DOC")) {
            JRRtfExporter exporterRTF = new JRRtfExporter();
            exporterRTF.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporterRTF.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
            exporterRTF.exportReport();
            mediais = new ByteArrayInputStream(output.toByteArray());
            amedia = new AMedia("FileFormatRTF", "rtf", "application/rtf", mediais);

            callReportWindow(this.amedia, "RTF-DOC");
        }
    }

    private void callReportWindow(AMedia aMedia, String format) {
        final boolean modal = true;

        this.setTitle("Dynamic JasperReports. Sample Report for ZKoss");
        this.setId("ReportWindow");
        this.setVisible(true);
        this.setMaximizable(true);
        this.setMinimizable(true);
        this.setSizable(true);
        this.setClosable(true);
        this.setHeight("100%");
        this.setWidth("80%");
        this.addEventListener("onClose", new OnCloseReportEventListener());

        iFrame = new Iframe();
        iFrame.setId("jasperReportId");
        iFrame.setWidth("100%");
        iFrame.setHeight("100%");
        iFrame.setContent(aMedia);
        iFrame.setParent(this);

        if (modal == true) {
            try {
                this.doModal();
            } catch (final SuspendNotAllowedException e) {
                throw new RuntimeException(e);
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * EventListener for closing the Report Window.<br>
     *
     * @author sge
     */
    public final class OnCloseReportEventListener implements EventListener {
        @Override
        public void onEvent(Event event) throws Exception {
            closeReportWindow();
        }
    }

    /**
     * We must clear something to prevent errors or problems <br>
     * by opening the report a few times. <br>
     *
     * @throws IOException
     */
    private void closeReportWindow() throws IOException {

        // TODO check this
        try {
            amedia.getStreamData().close();
            output.close();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        this.onClose();

    }

}
