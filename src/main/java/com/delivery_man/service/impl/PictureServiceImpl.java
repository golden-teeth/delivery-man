package com.delivery_man.service.impl;

import com.delivery_man.entity.Picture;
import com.delivery_man.entity.User;
import com.delivery_man.repository.PictureRepository;
import com.delivery_man.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    @Override
    public void uploadPicture(User user, List<MultipartFile> images) {

        try {
            String uploadsDir = "src/main/resources/static/uploads/pictures/";

            for (MultipartFile image : images) {
                String dbFilePath = savePicture(image, uploadsDir);

                Picture picture = new Picture(dbFilePath, user);
                pictureRepository.save(picture);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String savePicture(MultipartFile image, String uploadsDir) throws IOException {

        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();

        String filePath = uploadsDir + fileName;

        String dbFilePath = "/uploads/pictures/" + fileName;

        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        return dbFilePath;
    }
}
