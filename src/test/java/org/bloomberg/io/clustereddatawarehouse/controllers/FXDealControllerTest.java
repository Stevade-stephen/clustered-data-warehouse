package org.bloomberg.io.clustereddatawarehouse.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bloomberg.io.clustereddatawarehouse.dtos.FXDealDto;
import org.bloomberg.io.clustereddatawarehouse.services.FXDealService;
import org.bloomberg.io.clustereddatawarehouse.utils.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FXDealController.class)
class FXDealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FXDealService fxDealService;



    @Test
    void testSaveFXDeal() throws Exception {
        // Create a sample FXDealDto for testing
        FXDealDto dealDto = createValidDealDto();


        // Convert the FXDealDto to JSON
        String dealJson = asJsonString(dealDto)/* Convert dealDto to JSON using Jackson or other JSON libraries */;

        when(fxDealService.saveFXDeal(dealDto))
                .thenReturn(dealDto);
        // Perform a POST request to save the FXDeal
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/fx-deals/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dealJson))
                .andExpect(status().isCreated())
                .andReturn();

        // Extract the saved FXDealDto from the response
        MockHttpServletResponse saveResponse = mvcResult.getResponse();
        String saveResponseContent = saveResponse.getContentAsString();
        ApiResponse savedDeal = stringToDto(saveResponseContent)/* Parse saveResponseContent to FXDealDto */;
//
//        // Add assertions here to validate the savedDeal and response content
        assertThat(savedDeal.getMessage()).isEqualTo("FX Deal saved successfully");
    }

    @Test
    void testGetFXDeal() throws Exception {
        // Create a sample FXDeal in your database using fxDealService or a test database

        // Perform a GET request to retrieve the saved FXDeal
        MvcResult retrieveResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/fx-deals/{dealId}", "sampleDeal123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Extract the retrieved FXDealDto from the response
        MockHttpServletResponse retrieveResponse = retrieveResult.getResponse();
        String retrieveResponseContent = retrieveResponse.getContentAsString();
        ApiResponse savedDeal = stringToDto(retrieveResponseContent)/* Parse saveResponseContent to FXDealDto */;

        // Add assertions here to validate the retrievedDeal and response content
        assertThat(savedDeal.getMessage()).isEqualTo("FX Deal retrieved successfully");
    }

    private FXDealDto createValidDealDto() {
        FXDealDto deal = new FXDealDto();
        deal.setDealUniqueId("DEAL123");
        deal.setFromCurrency("USD");
        deal.setToCurrency("EUR");
        deal.setDealAmountInOrderingCurrency(new BigDecimal("100.0"));
        return deal;
    }

    private String asJsonString(FXDealDto deal) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(deal);
    }

    private ApiResponse stringToDto(String deal) throws JsonProcessingException {
        return new ObjectMapper().readValue(deal, ApiResponse.class);
    }
}
