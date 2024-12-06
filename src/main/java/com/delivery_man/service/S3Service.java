package com.delivery_man.service;

import com.delivery_man.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    String uploadImage(MultipartFile image, User user) throws IOException;
}
