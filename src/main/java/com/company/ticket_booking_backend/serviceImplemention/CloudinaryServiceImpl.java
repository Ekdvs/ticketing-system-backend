package com.company.ticket_booking_backend.serviceImplemention;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.company.ticket_booking_backend.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "ticket-booking-site")
            );

            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed: " + e.getMessage());
        }
    }

    @Override
    public void deleteImage(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

        } catch (Exception e) {
            throw new RuntimeException("Image delete failed: " + e.getMessage());
        }
    }

    @Override
    public String uploadPdf(byte[] pdfBytes, String fileName) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    pdfBytes,
                    ObjectUtils.asMap(
                            "resource_type", "raw",
                            "public_id", "ticket-booking-site/" + fileName
                    )
            );

            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("PDF upload failed: " + e.getMessage());
        }
    }

    // 🔥 Extract public_id from URL
    private String extractPublicId(String imageUrl) {


        String[] parts = imageUrl.split("/");
        String fileName = parts[parts.length - 1]; // abc.jpg

        String publicId = "ticket-booking-site/" + fileName.substring(0, fileName.lastIndexOf("."));

        return publicId;
    }
}
