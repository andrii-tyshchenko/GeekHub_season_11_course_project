package org.geekhub.andrij.course_project.services.exportToFiles;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.geekhub.andrij.course_project.entities.AmountBySection;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class AmountBySectionToXlsxService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public void export(HttpServletResponse response, String title, List<AmountBySection> amountBySections) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + title + ".xlsx";
        response.setHeader(headerKey, headerValue);

        workbook = new XSSFWorkbook();

        writeHeaderLine(title);
        writeDataLines(amountBySections);

        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);

        workbook.close();

        outputStream.close();
    }

    private void writeHeaderLine(String title) {
        sheet = workbook.createSheet(title);

        Row row = sheet.createRow(0);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);

        createCell(row, 0, "Section", style);
        createCell(row, 1, "Amount, UAH", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);

        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    private void writeDataLines(List<AmountBySection> amountBySections) {
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);

        int rowCount = 1;

        for (AmountBySection amountBySection : amountBySections) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            createCell(row, columnCount++, amountBySection.getSection(), style);
            createCell(row, columnCount, amountBySection.getAmount(), style);
        }
    }
}