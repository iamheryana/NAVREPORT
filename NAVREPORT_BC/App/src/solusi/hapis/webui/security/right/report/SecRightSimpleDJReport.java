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
package solusi.hapis.webui.security.right.report;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
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

import solusi.hapis.backend.model.SecRight;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.webui.util.ZksampleDateFormat;
import solusi.hapis.webui.util.ZksampleMessageUtils;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * A simple report implemented with the DynamicJasper framework.<br>
 * <br>
 * This report shows a list of Security Single Rights.<br>
 * <br>
 * The report uses the DynamicReportBuilder that allowed more control over the
 * columns. Additionally the report uses a CustomExpression for showing how to
 * work with it. The CustomExpression checks a boolean field and writes only a
 * 'T' for 'true and 'F' as 'False.<br>
 *
 * @author bbruhns
 * @author sge
 */
public class SecRightSimpleDJReport extends Window implements Serializable {

    private static final long serialVersionUID = 1L;

    private Iframe iFrame;
    private ByteArrayOutputStream output;
    private InputStream mediais;
    private AMedia amedia;
    private final String zksample2title = Labels.getLabel("print.Title.Security_single_rights_list");

    public SecRightSimpleDJReport(Component parent) throws InterruptedException {
        super();
        this.setParent(parent);

        try {
            doPrint();
        } catch (final Exception e) {
            ZksampleMessageUtils.showErrorMessage(e.toString());
        }
    }

    public void doPrint() throws JRException, ColumnBuilderException, ClassNotFoundException, IOException {

        // Localized column headers
        String rigName = Labels.getLabel("listheader_SecRightList_rigName.label");
        String rigType = Labels.getLabel("listheader_SecRightList_rigType.label");

        // Styles: Title
        Style titleStyle = new Style();
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        Font titleFont = Font.ARIAL_BIG_BOLD;
        titleFont.setUnderline(true);
        titleStyle.setFont(titleFont);
        // titleStyle.setBorderBottom(Border.PEN_1_POINT);

        // Styles: Subtitle
        Style subtitleStyle = new Style();
        subtitleStyle.setHorizontalAlign(HorizontalAlign.LEFT);
        subtitleStyle.setFont(Font.ARIAL_MEDIUM_BOLD);

        /**
         * Set the styles. In a report created with DynamicReportBuilder we do
         * this in an other way.
         */
        // ColumnHeader Style Text (left-align)
        Style columnHeaderStyleText = new Style();
        columnHeaderStyleText.setFont(Font.ARIAL_MEDIUM_BOLD);
        columnHeaderStyleText.setHorizontalAlign(HorizontalAlign.LEFT);
        columnHeaderStyleText.setBorderBottom(Border.PEN_1_POINT);

        // ColumnHeader Style Text (right-align)
        Style columnHeaderStyleNumber = new Style();
        columnHeaderStyleNumber.setFont(Font.ARIAL_MEDIUM_BOLD);
        columnHeaderStyleNumber.setHorizontalAlign(HorizontalAlign.RIGHT);
        columnHeaderStyleNumber.setBorderBottom(Border.PEN_1_POINT);

        // Footer Style (center-align)
        Style footerStyle = new Style();
        footerStyle.setFont(Font.ARIAL_SMALL);
        footerStyle.getFont().setFontSize(8);
        footerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        footerStyle.setBorderTop(Border.PEN_1_POINT);

        // Rows content Style (left-align)
        Style columnDetailStyleText = new Style();
        columnDetailStyleText.setFont(Font.ARIAL_SMALL);
        columnDetailStyleText.setHorizontalAlign(HorizontalAlign.LEFT);

        // Rows content Style (right-align)
        Style columnDetailStyleNumbers = new Style();
        columnDetailStyleNumbers.setFont(Font.ARIAL_SMALL);
        columnDetailStyleNumbers.setHorizontalAlign(HorizontalAlign.RIGHT);

        DynamicReportBuilder drb = new DynamicReportBuilder();
        DynamicReport dr;

        // Sets the Report Columns, header, Title, Groups, Etc Formats
        // DynamicJasper documentation
        drb.setTitle(zksample2title);
        // drb.setSubtitle("DynamicJasper Sample");
        drb.setSubtitleStyle(subtitleStyle);

        drb.setHeaderHeight(20);
        drb.setDetailHeight(15);
        drb.setFooterVariablesHeight(10);
        drb.setMargins(20, 20, 30, 15);

        drb.setDefaultStyles(titleStyle, subtitleStyle, columnHeaderStyleText, columnDetailStyleText);
        drb.setPrintBackgroundOnOddRows(true);

        /**
         * Columns Definitions. A new ColumnBuilder instance for each column.
         */
        // Right name
        AbstractColumn colRightName = ColumnBuilder.getNew().setColumnProperty("rigName", String.class.getName()).build();
        colRightName.setTitle(rigName);
        colRightName.setWidth(60);
        colRightName.setHeaderStyle(columnHeaderStyleText);
        colRightName.setStyle(columnDetailStyleText);
        // Right type
        AbstractColumn colRightType = ColumnBuilder.getNew().setCustomExpression(getMyRightTypExpression()).build();
        colRightType.setTitle(rigType);
        colRightType.setWidth(40);
        colRightType.setHeaderStyle(columnHeaderStyleText);
        colRightType.setStyle(columnDetailStyleText);

        // Add the columns to the report in the whished order
        drb.addColumn(colRightName);
        drb.addColumn(colRightType);

        // TEST
        Style atStyle = new StyleBuilder(true).setFont(Font.COMIC_SANS_SMALL).setTextColor(Color.red).build();
        /**
         * Adding many autotexts in the same position (header/footer and
         * aligment) makes them to be one on top of the other
         */

        AutoText created = new AutoText(Labels.getLabel("common.Created") + ": " + ZksampleDateFormat.getDateTimeFormater().format(new Date()), AutoText.POSITION_HEADER, HorizontalBandAlignment.RIGHT);
        created.setWidth(new Integer(120));
        created.setStyle(atStyle);
        drb.addAutoText(created);

        AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y, AutoText.POSITION_HEADER, HorizontalBandAlignment.RIGHT);
        autoText.setWidth(new Integer(20));
        autoText.setStyle(atStyle);
        drb.addAutoText(autoText);

        AutoText name1 = new AutoText("The Zksample2 Ltd.", AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
        name1.setPrintWhenExpression(ExpressionHelper.printInFirstPage());
        AutoText name2 = new AutoText("Software Consulting", AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
        name2.setPrintWhenExpression(ExpressionHelper.printInFirstPage());
        AutoText street = new AutoText("256, ZK Direct RIA Street ", AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
        street.setPrintWhenExpression(ExpressionHelper.printInFirstPage());
        AutoText city = new AutoText("ZKoss City", AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
        city.setPrintWhenExpression(ExpressionHelper.printInFirstPage());
        drb.addAutoText(name1).addAutoText(name2).addAutoText(street).addAutoText(city);
        // Footer
        AutoText footerText = new AutoText("Help to prevent the global warming by writing cool software.", AutoText.POSITION_FOOTER, HorizontalBandAlignment.CENTER);
        footerText.setStyle(footerStyle);
        drb.addAutoText(footerText);

        /**
         * ADD ALL USED BUT NOT DIRECT PRINTED FIELDS to the report. We replace
         * the field 'rigType' with a customExpression
         */
        drb.addField("rigType", Integer.class.getName());

        drb.setUseFullPageWidth(true); // use full width of the page
        dr = drb.build(); // build the report

        // Get information from database
        SecurityService sv = (SecurityService) SpringUtil.getBean("securityService");
        List<SecRight> resultList = sv.getAllRights();

        // Create Datasource and put it in Dynamic Jasper Format
        List data = new ArrayList(resultList.size());

        for (SecRight obj : resultList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rigName", obj.getRigName());
            map.put("rigType", obj.getRigType());
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

    /**
     * A CustomExpression that checks an integer and writes a´the corresponding
     * names.<br>
     *
     * @return
     */
    @SuppressWarnings("serial")
    private CustomExpression getMyRightTypExpression() {
        return new CustomExpression() {

            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {

                String result = "";

                /**
                 * Int | Type <br>
                 * --------------------------<br>
                 * 0 | Page <br>
                 * 1 | Menu Category <br>
                 * 2 | Menu Item <br>
                 * 3 | Method/Event <br>
                 * 4 | DomainObject/Property <br>
                 * 5 | Tab <br>
                 * 6 | Component <br>
                 */

                final int rigType = (Integer) fields.get("rigType");

                if (rigType == 0) {
                    result = "Page";
                } else if (rigType == 1) {
                    result = "Menu Category";
                } else if (rigType == 2) {
                    result = "Menu Item";
                } else if (rigType == 3) {
                    result = "Method/Event";
                } else if (rigType == 4) {
                    result = "DomainObject/Property";
                } else if (rigType == 5) {
                    result = "Tab";
                } else if (rigType == 6) {
                    result = "Component";
                }
                return result;
            }

            @Override
            public String getClassName() {
                return String.class.getName();
            }
        };
    }

    private void callReportWindow(AMedia aMedia, String format) {
        final boolean modal = true;

        setTitle("Dynamic JasperReports. Sample Report for the zk framework.");
        setId("ReportWindow");
        setVisible(true);
        setMaximizable(true);
        setMinimizable(true);
        setSizable(true);
        setClosable(true);
        setHeight("100%");
        setWidth("80%");
        addEventListener("onClose", new OnCloseReportEventListener());

        iFrame = new Iframe();
        iFrame.setId("jasperReportId");
        iFrame.setWidth("100%");
        iFrame.setHeight("100%");
        iFrame.setContent(aMedia);
        iFrame.setParent(this);

        if (modal == true) {
            try {
                doModal();
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

        onClose();

    }

}
