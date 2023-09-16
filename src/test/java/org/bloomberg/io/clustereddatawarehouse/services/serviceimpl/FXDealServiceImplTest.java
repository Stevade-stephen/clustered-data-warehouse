package org.bloomberg.io.clustereddatawarehouse.services.serviceimpl;

import org.bloomberg.io.clustereddatawarehouse.dtos.FXDealDto;
import org.bloomberg.io.clustereddatawarehouse.exceptions.DealNotFoundException;
import org.bloomberg.io.clustereddatawarehouse.exceptions.DuplicateFXDealException;
import org.bloomberg.io.clustereddatawarehouse.exceptions.InvalidDealRequestException;
import org.bloomberg.io.clustereddatawarehouse.models.FXDeal;
import org.bloomberg.io.clustereddatawarehouse.repositories.FXDealRepository;
import org.bloomberg.io.clustereddatawarehouse.utils.FXDealValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class FXDealServiceImplTest {

    @InjectMocks
    private FXDealServiceImpl fxDealService;

    @Mock
    private FXDealRepository fxDealRepository;

    @Mock
    private FXDealValidator fxDealValidator;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testSaveFXDeal_ValidDeal() throws InvalidDealRequestException {
        FXDealDto validDeal = createValidDealDto();
        Currency fromCurrency = Currency.getInstance(validDeal.getFromCurrency());
        Currency toCurrency = Currency.getInstance(validDeal.getToCurrency());

        when(fxDealRepository.findByDealUniqueId(validDeal.getDealUniqueId())).thenReturn(Optional.empty());
        doNothing().when(fxDealValidator).validateFXDeal(validDeal);
        when(fxDealRepository.save(any(FXDeal.class))).thenReturn(createValidFXDeal(fromCurrency, toCurrency));

        FXDealDto savedDeal = fxDealService.saveFXDeal(validDeal);

        assertThat(savedDeal).isNotNull();
        assertThat(savedDeal.getDealUniqueId()).isEqualTo(validDeal.getDealUniqueId());
    }

    @Test
     void testSaveFXDeal_DuplicateDeal() {
        FXDealDto duplicateDeal = createValidDealDto();
        Currency fromCurrency = Currency.getInstance(duplicateDeal.getFromCurrency());
        Currency toCurrency = Currency.getInstance(duplicateDeal.getToCurrency());

        when(fxDealRepository.findByDealUniqueId(duplicateDeal.getDealUniqueId())).thenReturn(Optional.of(createValidFXDeal(fromCurrency, toCurrency)));
        doNothing().when(fxDealValidator).validateFXDeal(duplicateDeal);

        assertThatThrownBy(() -> fxDealService.saveFXDeal(duplicateDeal))
                .hasMessage("FX Deal already exists")
                .isInstanceOf(DuplicateFXDealException.class);
    }

    @Test
     void testSaveFXDeal_InvalidDeal() {
        FXDealDto invalidDeal = createInvalidDealDto();

        doThrow(InvalidDealRequestException.class).when(fxDealValidator).validateFXDeal(invalidDeal);

        assertThatThrownBy(() -> fxDealService.saveFXDeal(invalidDeal))
                .isInstanceOf(InvalidDealRequestException.class);
    }

    @Test
     void testFetchFXDeal_ExistingDeal() {
        String dealId = "DEAL123";
        Currency fromCurrency = Currency.getInstance("USD");
        Currency toCurrency = Currency.getInstance("EUR");
        when(fxDealRepository.findByDealUniqueId(dealId)).thenReturn(Optional.of(createValidFXDeal(fromCurrency, toCurrency)));
        FXDealDto fetchedDeal = fxDealService.fetchFXDeal(dealId);

        assertThat(fetchedDeal).isNotNull();
        assertThat(fetchedDeal.getDealUniqueId()).isEqualTo(dealId);
    }

    @Test
     void testFetchFXDeal_NonExistingDeal() {
        String dealId = "NONEXISTENT";
        when(fxDealRepository.findByDealUniqueId(dealId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fxDealService.fetchFXDeal(dealId))
                .hasMessage("FX Deal with id " + dealId + " is not found")
                .isInstanceOf(DealNotFoundException.class);
    }

    private FXDealDto createValidDealDto() {
        FXDealDto deal = new FXDealDto();
        deal.setDealUniqueId("DEAL123");
        deal.setFromCurrency("USD");
        deal.setToCurrency("EUR");
        deal.setDealAmountInOrderingCurrency(new BigDecimal("100.0"));
        return deal;
    }

    private FXDealDto createInvalidDealDto() {
        FXDealDto deal = new FXDealDto();
        deal.setDealUniqueId("DEAL123");
        // Missing fromCurrency and toCurrency
        return deal;
    }

    private FXDeal createValidFXDeal(Currency fromCurrency, Currency toCurrency) {
        return FXDeal.builder()
                .dealUniqueId("DEAL123")
                .dealAmountInOrderingCurrency(new BigDecimal("100.0"))
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .build();
    }
}
