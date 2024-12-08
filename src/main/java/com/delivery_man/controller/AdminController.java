package com.delivery_man.controller;

import com.delivery_man.model.dto.admin.DashBoardGetResponseDto;
import com.delivery_man.service.AdminServce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminServce adminServce;
    @GetMapping("/dashboard/stats")
    public ResponseEntity<List<DashBoardGetResponseDto>> getDashboardStats(
            @RequestParam(required = false, defaultValue = "1970-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2099-12-31") String endDate,
            @RequestParam(required = false) List<String> shopNames
    ) {
        List<DashBoardGetResponseDto> dashBoardGetResponseDtos = adminServce.getDashboardStats(startDate,endDate,shopNames);
        return new ResponseEntity<>(dashBoardGetResponseDtos, HttpStatus.OK);
    }
}
