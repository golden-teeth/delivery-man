package com.delivery_man.service.impl;

import com.delivery_man.dto.MenuCreateRequestDto;
import com.delivery_man.dto.MenuResponseDto;
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
}
