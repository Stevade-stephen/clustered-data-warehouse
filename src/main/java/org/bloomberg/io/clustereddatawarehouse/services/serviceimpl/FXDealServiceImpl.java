package org.bloomberg.io.clustereddatawarehouse.services.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bloomberg.io.clustereddatawarehouse.dtos.FXDealDto;
import org.bloomberg.io.clustereddatawarehouse.exceptions.DealNotFoundException;
import org.bloomberg.io.clustereddatawarehouse.exceptions.DuplicateFXDealException;
import org.bloomberg.io.clustereddatawarehouse.exceptions.InvalidDealRequestException;
import org.bloomberg.io.clustereddatawarehouse.models.FXDeal;
import org.bloomberg.io.clustereddatawarehouse.repositories.FXDealRepository;
import org.bloomberg.io.clustereddatawarehouse.services.FXDealService;
import org.bloomberg.io.clustereddatawarehouse.utils.FXDealValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FXDealServiceImpl implements FXDealService {

    private final FXDealRepository fxDealRepository;
    private final FXDealValidator fxDealValidator;
    @Override
    public FXDealDto saveFXDeal(FXDealDto dealRequestDto) throws InvalidDealRequestException {
        log.info("Inside saveFXDeal with payload: {}", dealRequestDto);

        fxDealValidator.validateFXDeal(dealRequestDto);
        Optional<FXDeal> optionalFXDeal = fxDealRepository.findByDealUniqueId(dealRequestDto.getDealUniqueId());

        if(optionalFXDeal.isPresent()) throw new DuplicateFXDealException("FX Deal already exists");

        Currency fromCurrency = Currency.getInstance(dealRequestDto.getFromCurrency());
        Currency toCurrency = Currency.getInstance(dealRequestDto.getToCurrency());

        FXDeal deal = FXDeal.builder()
                .dealUniqueId(dealRequestDto.getDealUniqueId())
                .dealAmountInOrderingCurrency(dealRequestDto.getDealAmountInOrderingCurrency())
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .dealTimestamp(dealRequestDto.getDealTimestamp())
                .build();


        return fxDealToDto(fxDealRepository.save(deal));
    }

    @Override
    public FXDealDto fetchFXDeal(String dealId) {
        log.info("Inside fetchFXDeal method");
        FXDeal fxDeal = fxDealRepository.findByDealUniqueId(dealId).orElseThrow(() -> new DealNotFoundException("FX Deal with id " + dealId + " is not found"));
        return fxDealToDto(fxDeal);
    }

    private FXDealDto fxDealToDto (FXDeal fxDeal){
        log.info("Converting FXDeal to its Dto");
        FXDealDto fxDealDto = new FXDealDto();
        fxDealDto.setFromCurrency(fxDeal.getFromCurrency().getCurrencyCode());
        fxDealDto.setToCurrency(fxDeal.getToCurrency().getCurrencyCode());
        BeanUtils.copyProperties(fxDeal, fxDealDto);
        log.info("FXDeal Dto created");
        return fxDealDto;
    }
}
