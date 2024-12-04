package com.delivery_man.service.impl;

import com.delivery_man.dto.MenuCreateRequestDto;
import com.delivery_man.dto.MenuResponseDto;
import com.delivery_man.dto.MenuUpdateRequestDto;
import com.delivery_man.entity.Menu;
import com.delivery_man.repository.MenuRepository;
import com.delivery_man.service.MenuService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {
    private final MenuRepository repository;

    @Override
    public MenuResponseDto create(MenuCreateRequestDto dto) {
        //검증
        //shop id 검증
        //user id 검증

        Menu menu = new Menu(dto);
        Menu savedMenu = repository.save(menu);
        return new MenuResponseDto(savedMenu);
    }

    @Override
    public MenuResponseDto update(MenuUpdateRequestDto dto) {
        //검증
        //dto 검증
        validateUpdateDto(dto);
        //shop id 검증
        //user id 검증

        //menu 검증
        Menu findByMenuId = repository.findById(dto.getMenuId())
                .orElseThrow(()->new RuntimeException());


        findByMenuId.update(dto);

        return new MenuResponseDto(findByMenuId);

    }

    @Override
    public void delete(Long shopId, Long menuId) {
        //검증
        //shop 검증

        //menuId 검증
        Menu menuById = repository.findById(menuId)
                .orElseThrow(() -> new RuntimeException());


        menuById.delete();
        }


    private void validateUpdateDto(MenuUpdateRequestDto dto) {
        if((dto.getName()==null && dto.getPrice()!=null) ||
                (dto.getName()!=null && dto.getPrice()==null)){
            throw new RuntimeException();
        }
    }

}
