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
            Paragraph name = new Paragraph("Nome: " + student.getName());
            Paragraph cpf = new Paragraph("CPF: " + student.getCpf());
            Paragraph rg = new Paragraph("RG: " + student.getRg());

            document.add(name);
            document.add(cpf);
            document.add(rg);

            document.add(new Paragraph(""));

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