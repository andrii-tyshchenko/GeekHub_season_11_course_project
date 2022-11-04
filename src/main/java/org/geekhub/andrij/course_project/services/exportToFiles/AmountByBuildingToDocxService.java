package org.geekhub.andrij.course_project.services.exportToFiles;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;

@Service
public class AmountByBuildingToDocxService {
    private XWPFDocument document;

    public void export(HttpServletResponse response, String title, Double monthlyAmountOfBuilding) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + title + ".docx";
        response.setHeader(headerKey, headerValue);

        document = new XWPFDocument();

        writeHeader(title);
        writeText(new DecimalFormat("#,##0.00").format(monthlyAmountOfBuilding) + " UAH");

        ServletOutputStream outputStream = response.getOutputStream();

        document.write(outputStream);

        document.close();

        outputStream.close();
    }

    private void writeHeader(String title) {
        XWPFParagraph header = document.createParagraph();

        header.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun headerRun = header.createRun();

        headerRun.setText(title);
        headerRun.setColor("0d6efd");
        headerRun.setBold(true);
        headerRun.setFontFamily("Helvetica");
        headerRun.setFontSize(16);
        headerRun.addBreak();
    }

    private void writeText(String text) {
        XWPFParagraph p = document.createParagraph();

        p.setAlignment(ParagraphAlignment.BOTH);

        XWPFRun pRun = p.createRun();

        pRun.setText(text);
        pRun.setFontFamily("Helvetica");
        pRun.setFontSize(14);
        pRun.addBreak();
    }
}