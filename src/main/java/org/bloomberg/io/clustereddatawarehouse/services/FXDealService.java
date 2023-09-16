package org.bloomberg.io.clustereddatawarehouse.services;

import org.bloomberg.io.clustereddatawarehouse.dtos.FXDealDto;
import org.bloomberg.io.clustereddatawarehouse.exceptions.InvalidDealRequestException;

public interface FXDealService {
    FXDealDto saveFXDeal(FXDealDto dealRequestDto) throws InvalidDealRequestException;
    FXDealDto fetchFXDeal(String dealId);
}
