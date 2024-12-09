package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.errorcode.MenuErrorCode;
import com.delivery_man.constant.errorcode.ShopErrorCode;
import com.delivery_man.constant.errorcode.UserErrorCode;
import com.delivery_man.model.dto.menu.MenuCreateRequestDto;
import com.delivery_man.model.dto.menu.MenuResponseDto;
import com.delivery_man.model.dto.menu.MenuUpdateRequestDto;
import com.delivery_man.model.dto.menu.MenuWithPictureResponseDto;
import com.delivery_man.model.entity.Menu;
import com.delivery_man.model.entity.Shop;
import com.delivery_man.model.entity.User;
import com.delivery_man.repository.MenuRepository;
import com.delivery_man.repository.ShopRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.MenuService;
import com.delivery_man.service.PictureService;
import com.delivery_man.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final PictureService pictureService;

    @Override
    public MenuWithPictureResponseDto create(MenuCreateRequestDto dto, MultipartFile image) throws IOException {
        //검증
        //shop id 검증
        Shop shop = shopRepository.findById(dto.getShopId())
                        .orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        //user id 검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        Menu menu = new Menu(dto);
        menu.updateShop(shop);
        Menu savedMenu = menuRepository.save(menu);

        //picture 테이블에 담을 변수 생성
        String category = savedMenu.getClass().getSimpleName();
        Long idNumber = savedMenu.getId();
        //업로드 된 이미지 파일 주소
        String publicUrl = s3Service.uploadImage(image);

        //picture 테이블에 저장
        pictureService.savePicture(publicUrl, category, idNumber);

        return new MenuWithPictureResponseDto(savedMenu, publicUrl);
    }

    @Override
    public MenuResponseDto update(MenuUpdateRequestDto dto) {
        //검증
        //dto 검증
        validateUpdateDto(dto);
        //shop id 검증
        Shop shop = shopRepository.findById(dto.getShopId()).
                orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        //user id 검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        //menu 검증
        Menu findByMenuId = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ApiException(MenuErrorCode.MENU_NOT_FOUND));


        findByMenuId.update(dto);

        return new MenuResponseDto(findByMenuId);

    }

    @Override
    public void delete(Long shopId, Long menuId, Long userId) {
        //검증
        //user id 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        //shop 검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));

        //menuId 검증
        Menu menuById = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException());


        menuById.delete();
    }


    private void validateUpdateDto(MenuUpdateRequestDto dto) {
        if ((dto.getName() == null && dto.getPrice() != null) ||
                (dto.getName() != null && dto.getPrice() == null)) {
            throw new RuntimeException();
        }
    }

}
