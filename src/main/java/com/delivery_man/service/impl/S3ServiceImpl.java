package com.delivery_man.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.PictureErrorCode;
import com.delivery_man.entity.User;
import com.delivery_man.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    @Override
    public String uploadImage(MultipartFile image, User user) throws IOException {

        if (image != null) {
            String extension = getImageExtension(image);
            String fileName = UUID.randomUUID() + "_" + user.getId() + "_profile" + extension;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(image.getContentType());
            objectMetadata.setContentLength(image.getSize());

            PutObjectRequest putObjectRequest = null;
            try {
                putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), objectMetadata);
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }

            amazonS3.putObject(putObjectRequest);

            return getPublicUrl(fileName);
        }
        return "사진이 없습니다.";
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName() ,fileName);
    }

    private String getImageExtension(MultipartFile image) {
        String extension = "";
        String originalFilename = image.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        if (!extension.equals(".png") && !extension.equals(".jpg") && !extension.equals(".jpeg")) {
            throw new ApiException(PictureErrorCode.INVALID_FORMAT);
        }
        return extension;
    }
}
