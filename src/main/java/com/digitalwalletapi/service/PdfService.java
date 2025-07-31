package com.digitalwalletapi.service;

import com.digitalwalletapi.model.Transaction;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateTransactionReceipt(Transaction transaction) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A5, 36, 36, 36, 36); // tamanho de recibo
        PdfWriter.getInstance(document, outputStream);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font valueFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        Paragraph title = new Paragraph("Comprovante de Transação", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        addField(document, "ID da Transação:", String.valueOf(transaction.getId()), labelFont, valueFont);
        addField(document, "Tipo:", transaction.getType().name(), labelFont, valueFont);
        addField(document, "Valor:", "R$ " + transaction.getAmount().setScale(2, RoundingMode.HALF_UP), labelFont, valueFont);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        addField(document, "Data/Hora:", transaction.getTimestamp().format(formatter), labelFont, valueFont);

        addField(document, "Conta de Origem:", transaction.getAccount().getUser().getName(), labelFont, valueFont);

        if (transaction.getTargetAccount() != null) {
            addField(document, "Conta de Destino:", transaction.getTargetAccount().getUser().getName(), labelFont, valueFont);

        }

        document.add(new Paragraph("\n\nAssinatura: __________________________\n"));
        document.add(new Paragraph("Este é um documento gerado automaticamente.", new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC)));

        document.close();
        return outputStream.toByteArray();

    }

    private void addField(Document doc, String label, String value, Font labelFont, Font valueFont) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(new Chunk(label + " ", labelFont));
        p.add(new Chunk(value, valueFont));
        p.setSpacingAfter(10f);
        doc.add(p);
    }

}
