package com.pranjal.asset_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.pranjal.asset_service.dto.CloudinaryResponse;
import com.pranjal.asset_service.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryResponse uploadFile(MultipartFile file) {

        Map upload = null;
        try {
            upload = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap());
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }

        CloudinaryResponse response = new CloudinaryResponse();
        response.setImageUrl(upload.get("secure_url").toString());
        response.setPublicId(upload.get("public_id").toString());
        return response;
    }

    @Async
    public void delete(String url) {
        Map delete = null;
        try {
            delete = cloudinary.uploader().destroy(extractPublicId(url), ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
    }

    private String extractPublicId(String url){
        url = url.split("\\?")[0];
        String filename = url.substring(url.lastIndexOf("/") + 1);
        return filename.substring(0, filename.lastIndexOf("."));
    }
}
