package com.delivery_man.service.impl;

import com.delivery_man.entity.Picture;
import com.delivery_man.repository.PictureRepository;
import com.delivery_man.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    @Override
    public void savePicture(String publicUrl, String category, Long idNumber) {

        Picture picture = new Picture(publicUrl, category, idNumber);

        pictureRepository.save(picture);
    }
}
