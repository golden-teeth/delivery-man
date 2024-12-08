package com.delivery_man.service.impl;

import com.delivery_man.model.dto.admin.DashBoardGetResponseDto;
import com.delivery_man.repository.OrderRepository;
import com.delivery_man.repository.ShopRepository;
import com.delivery_man.service.AdminServce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminServce {
    public final ShopRepository shopRepository;
    public final OrderRepository orderRepository;

    /**
     *
     * @param start
     * @param end
     * @param shopNames
     * @return
     */
    @Override
    public List<DashBoardGetResponseDto> getDashboardStats(String start, String end, List<String> shopNames) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        List<DashBoardGetResponseDto> dashBoardGetResponseDtos = new ArrayList<>();
        if(shopNames==null){
            DashBoardGetResponseDto dto = orderRepository.findOrderCountAndAmount(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay(),null);
            dto.setMessage("조회 완료");
            dashBoardGetResponseDtos.add(dto);
        }else{
            for(String shopName : shopNames){
                DashBoardGetResponseDto dto = orderRepository.findOrderCountAndAmount(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay(),shopName);
                if(dto == null){
                    dto = new DashBoardGetResponseDto(0L, BigDecimal.ZERO,shopName);
                    dto.setMessage("조회 결과가 없습니다");
                }else{
                    dto.setMessage("조회 완료");
                }
                dashBoardGetResponseDtos.add(dto);
            }
        }

        return dashBoardGetResponseDtos;
    }
}
