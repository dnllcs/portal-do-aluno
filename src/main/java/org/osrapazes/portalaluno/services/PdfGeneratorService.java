package org.osrapazes.portalaluno.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.osrapazes.portalaluno.models.Student;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorService {
    
    public void generateEnrollmentStatement(Student student, String filePath) {
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            writer.setPageEvent(new PdfFooter());

            document.open();

            Image image = Image.getInstance("src/main/resources/images/logo.png");
            image.setAlignment(Element.ALIGN_CENTER);
            image.scalePercent(50);

            Paragraph title = new Paragraph("DECLARAÇÃO", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);

            Paragraph text = new Paragraph("Declaramos, para os fins a que se fizerem necessários, que " + student.getName() +
            ", portador do CPF de número" + student.getCpf() + "é aluno(a) vinculado(a) a esta instituição, sob o número " + student.getEnrollment().getEnrollmentCode());

            document.add(image);
            document.add(new Paragraph("\n"));
            document.add(title);
            document.add(new Paragraph("\n"));
            document.add(text);

            document.add(new Paragraph("\n"));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class PdfFooter extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            PdfContentByte cb = writer.getDirectContent();
            Phrase footer = new Phrase("Documento gerado em: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), font);

            float x = document.right() - document.rightMargin();
            float y = document.bottom() - 10;
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer, x, y, 0);
        }
    }
}