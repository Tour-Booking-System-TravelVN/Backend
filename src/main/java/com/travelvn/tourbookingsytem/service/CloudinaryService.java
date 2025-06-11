package com.travelvn.tourbookingsytem.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    @Autowired
    private Cloudinary cloudinary;

    public Map<String, String> uploadFile(MultipartFile file, String folder) {
        try {
            logger.info("Đang tải tệp lên Cloudinary, thư mục: {}", folder);
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", "auto"
            ));
            Map<String, String> result = new HashMap<>();
            result.put("publicId", (String) uploadResult.get("public_id"));
            result.put("url", (String) uploadResult.get("secure_url"));
            logger.info("Tệp được tải lên thành công: {}", uploadResult.get("public_id"));
            return result;
        } catch (IOException e) {
            logger.error("Không thể tải tệp lên Cloudinary: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể tải tệp lên Cloudinary", e);
        }
    }

    public void deleteFile(String publicId) {
        try {
            logger.info("Đang xóa tệp từ Cloudinary: {}", publicId);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            logger.info("Xóa tệp thành công: {}", publicId);
        } catch (Exception e) {
            logger.error("Không thể xóa tệp từ Cloudinary: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể xóa tệp từ Cloudinary", e);
        }
    }
}