package org.bloomberg.io.clustereddatawarehouse.utils;

import com.google.common.base.Strings;
import org.bloomberg.io.clustereddatawarehouse.dtos.FXDealDto;
import org.bloomberg.io.clustereddatawarehouse.exceptions.InvalidDealRequestException;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

@Component
public class FXDealValidator {

    public void validateFXDeal(FXDealDto fxDeal) {
        validateNotNull(fxDeal);
        validateUniqueId(fxDeal.getDealUniqueId());
        validateCurrency(fxDeal.getFromCurrency());
        validateCurrency(fxDeal.getToCurrency());
        validateFromAndToCurrency(fxDeal.getFromCurrency(), fxDeal.getToCurrency());
        validatePositiveAmount(fxDeal.getDealAmountInOrderingCurrency());
    }

    private void validateFromAndToCurrency(String fromCurrencyISO, String toCurrencyISO) {
        if (fromCurrencyISO.equals(toCurrencyISO)){
            throw new InvalidDealRequestException("From and To currencies should not be the same");
        }
    }

    private void validateUniqueId(String dealUniqueId) {
        if(Strings.isNullOrEmpty(dealUniqueId)){
            throw new InvalidDealRequestException("FX Deal unique ID cannot be null or empty");
        }
    }

    private void validateNotNull(Object value) {
        if (Objects.isNull(value)) {
            throw new InvalidDealRequestException("FX Deal request should not be null");
        }
    }

    private void validateCurrency(String currency) {
        if (Strings.isNullOrEmpty(currency) || isValidCurrencyCode(currency)) {
            throw new InvalidDealRequestException("Invalid Currency ISO " + currency);
        }
    }


    private void validatePositiveAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDealRequestException("Deal amount must be greater than zero.");
        }
    }

    private boolean isValidCurrencyCode(String orderingCurrencyISO) {
        return Currency.getAvailableCurrencies().stream()
                .noneMatch(v -> v.getCurrencyCode().equals(orderingCurrencyISO));
    }
}

