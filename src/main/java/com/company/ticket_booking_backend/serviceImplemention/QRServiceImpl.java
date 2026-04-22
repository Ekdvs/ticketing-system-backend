package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.service.QRService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QRServiceImpl implements QRService {

    @Override
    public byte[] generateQR(String data) throws Exception {

        BitMatrix matrix = new MultiFormatWriter()
                .encode(data, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", out);

        return out.toByteArray();
    }
}
