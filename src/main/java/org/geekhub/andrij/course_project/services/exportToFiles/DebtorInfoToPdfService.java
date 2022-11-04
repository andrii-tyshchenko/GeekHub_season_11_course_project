package org.geekhub.andrij.course_project.services.exportToFiles;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.geekhub.andrij.course_project.entities.DebtorInfo;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DebtorInfoToPdfService {
    public void export(HttpServletResponse response, List<DebtorInfo> debtors) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=debtors_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Document document = new Document(PageSize.A4);
        ServletOutputStream outputStream = response.getOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(16);
        font.setColor(Color.BLUE);

        Paragraph title = new Paragraph("Top debtors:", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(title);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.25f, 1.25f, 1.0f, 3.5f, 1.75f, 1.75f, 1.75f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, debtors);

        document.add(table);

        document.close();

        outputStream.close();
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Acc. number", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Section", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Apt.", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Payments, UAH", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Accruals, UAH", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Debt, UAH", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<DebtorInfo> debtors) {
        for (DebtorInfo debtor : debtors) {
            table.addCell(String.valueOf(debtor.getAccountNumber()));
            table.addCell(String.valueOf(debtor.getSection()));
            table.addCell(String.valueOf(debtor.getApartmentNumber()));

            String lastName = (debtor.getLastName() == null) ? "n/a" : debtor.getLastName();
            String firstName = (debtor.getFirstName() == null) ? "n/a" : debtor.getFirstName();
            String patronymic = (debtor.getPatronymic() == null) ? "n/a" : debtor.getPatronymic();

            table.addCell(lastName + " " + firstName + " " + patronymic);
            table.addCell(new DecimalFormat("#,##0.00").format(debtor.getUserPayments()));
            table.addCell(new DecimalFormat("#,##0.00").format(debtor.getUserAccruals()));
            table.addCell(new DecimalFormat("#,##0.00").format(debtor.getDebt()));
        }
    }
}