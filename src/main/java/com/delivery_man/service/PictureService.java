package com.delivery_man.service;

import com.delivery_man.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PictureService {

    void uploadPicture(User user, List<MultipartFile> images);

    String savePicture(MultipartFile image, String uploadsDir) throws IOException;
}
