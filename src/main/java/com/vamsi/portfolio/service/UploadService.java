package com.vamsi.portfolio.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class UploadService {

    @Autowired
    private S3Client s3Client;

    @Value("${AWS_BUCKET_NAME}")
    private String bucketName;

    public String saveFile(MultipartFile file, String type) throws IOException {

        String folder = type.equalsIgnoreCase("Image")
                ? "images/"
                : "videos/";

        String fileName = folder + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request,
                RequestBody.fromBytes(file.getBytes()));

        return fileName;
    }
}