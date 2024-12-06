package com.delivery_man.service;

import com.delivery_man.dto.DashBoardGetResponseDto;

import java.util.List;

public interface AdminServce {
    List<DashBoardGetResponseDto> getDashboardStats(String startDate, String endDate, List<String> shopName);
}
