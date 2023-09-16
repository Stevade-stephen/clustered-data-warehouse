package org.bloomberg.io.clustereddatawarehouse.utils;

import org.bloomberg.io.clustereddatawarehouse.dtos.FXDealDto;
import org.bloomberg.io.clustereddatawarehouse.exceptions.InvalidDealRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FXDealValidatorTest {

    @InjectMocks
    private FXDealValidator fxDealValidator;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testValidateFXDeal_ValidDeal() {
        FXDealDto validDeal = createValidDeal();
        assertThatCode(() -> fxDealValidator.validateFXDeal(validDeal))
                .doesNotThrowAnyException();
    }

    @Test
    void testValidateFXDeal_NullDeal() {
        assertThatThrownBy(() -> fxDealValidator.validateFXDeal(null))
                .hasMessage("FX Deal request should not be null")
                .isInstanceOf(InvalidDealRequestException.class);
    }

    @Test
    void testValidateUniqueId_NullUniqueId() {
        FXDealDto deal = createValidDeal();
        deal.setDealUniqueId(null);
        assertThatThrownBy(() -> fxDealValidator.validateFXDeal(deal))
                .hasMessage("FX Deal unique ID cannot be null or empty")
                .isInstanceOf(InvalidDealRequestException.class);
    }

    @Test
    void testValidateUniqueId_EmptyUniqueId() {
        FXDealDto deal = createValidDeal();
        deal.setDealUniqueId("");
        assertThatThrownBy(() -> fxDealValidator.validateFXDeal(deal))
                .hasMessage("FX Deal unique ID cannot be null or empty")
                .isInstanceOf(InvalidDealRequestException.class);
    }

    @Test
    void testValidateCurrency_InvalidCurrency() {
        FXDealDto deal = createValidDeal();
        deal.setFromCurrency("INVALID");
        assertThatThrownBy(() -> fxDealValidator.validateFXDeal(deal))
                .isInstanceOf(InvalidDealRequestException.class);
    }

    @Test
    void testValidateCurrency_InvalidFromAndToCurrency() {
        FXDealDto deal = createValidDeal();
        deal.setFromCurrency("EUR");
        deal.setToCurrency("EUR");
        assertThatThrownBy(() -> fxDealValidator.validateFXDeal(deal))
                .hasMessage("From and To currencies should not be the same")
                .isInstanceOf(InvalidDealRequestException.class);
    }

    @Test
    void testValidatePositiveAmount_NegativeAmount() {
        FXDealDto deal = createValidDeal();
        deal.setDealAmountInOrderingCurrency(new BigDecimal("-100.0"));
        assertThatThrownBy(() -> fxDealValidator.validateFXDeal(deal))
                .hasMessage("Deal amount must be greater than zero.")
                .isInstanceOf(InvalidDealRequestException.class);
    }

    @Test
    void testValidatePositiveAmount_ZeroAmount() {
        FXDealDto deal = createValidDeal();
        deal.setDealAmountInOrderingCurrency(BigDecimal.ZERO);
        assertThatThrownBy(() -> fxDealValidator.validateFXDeal(deal))
                .hasMessage("Deal amount must be greater than zero.")
                .isInstanceOf(InvalidDealRequestException.class);
    }

    private FXDealDto createValidDeal() {
        FXDealDto deal = new FXDealDto();
        deal.setDealUniqueId("DEAL123");
        deal.setFromCurrency("USD");
        deal.setToCurrency("EUR");
        deal.setDealAmountInOrderingCurrency(new BigDecimal("100.0"));
        return deal;
    }
}
