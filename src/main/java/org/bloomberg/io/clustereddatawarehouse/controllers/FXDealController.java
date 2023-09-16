package org.bloomberg.io.clustereddatawarehouse.controllers;

import lombok.RequiredArgsConstructor;
import org.bloomberg.io.clustereddatawarehouse.dtos.FXDealDto;
import org.bloomberg.io.clustereddatawarehouse.services.FXDealService;
import org.bloomberg.io.clustereddatawarehouse.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fx-deals")
@RequiredArgsConstructor
public class FXDealController {

    private final FXDealService fxDealService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveFXDeal(@RequestBody FXDealDto requestDto) {
        FXDealDto dealResponseDto = fxDealService.saveFXDeal(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body((ApiResponse.builder().data(dealResponseDto)
                .status(HttpStatus.CREATED)
                .message("FX Deal saved successfully")
                .build()));
    }


    @GetMapping("/{dealId}")
    public ResponseEntity<ApiResponse> getFXDeal(@PathVariable String dealId){
        FXDealDto deal = fxDealService.fetchFXDeal(dealId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().data(deal)
                .status(HttpStatus.OK)
                .message("FX Deal retrieved successfully")
                .build());
    }
}
