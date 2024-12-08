package com.delivery_man.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.PictureErrorCode;
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

    /**
     * 이미지 업로드 로직
     *
     * @param image 입력 받은 이미지
     * @return
     * @throws IOException
     */
    @Transactional
    @Override
    public String uploadImage(MultipartFile image) throws IOException {

        //사진을 입력 할 경우
        if (image != null) {

            //이미지 파일 형식
            String extension = getImageExtension(image);

            //업로드 할 파일 이름 생성
            String fileName = UUID.randomUUID() + "_" + extension;

            //메타데이터 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(image.getContentType());
            objectMetadata.setContentLength(image.getSize());

            //업로드 요청 생성
            PutObjectRequest putObjectRequest = null;
            try {
                putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), objectMetadata);
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }

            //파일 업로드
            amazonS3.putObject(putObjectRequest);

            return getPublicUrl(fileName);
        }
        return "사진이 없습니다.";
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName() ,fileName);
    }

    /**
     * 이미지 파일 형식 생성, 검증 로직
     *
     * @param image 입력 받은 이미지
     * @return
     */
    private String getImageExtension(MultipartFile image) {

        String extension = "";
        String originalFilename = image.getOriginalFilename();

        //이미지 파일 형식 생성
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        //이미지 파일 형식 검증
        if (!extension.equals(".png") && !extension.equals(".jpg") && !extension.equals(".jpeg")) {
            throw new ApiException(PictureErrorCode.INVALID_FORMAT);
        }
        return extension;
    }
}
