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

    /**
     * picture 테이블 저장 로직
     *
     * @param publicUrl 업로드 된 이미지 주소
     * @param category  연관된 테이블 이름
     * @param idNumber  연관된 테이블의 식별자
     */
    @Override
    public void savePicture(String publicUrl, String category, Long idNumber) {

        Picture picture = new Picture(publicUrl, category, idNumber);

        pictureRepository.save(picture);
    }
}
