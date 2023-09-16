package org.bloomberg.io.clustereddatawarehouse.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FXDealDto {
    private String dealUniqueId;
    private String fromCurrency;
    private String toCurrency;
    private LocalDateTime dealTimestamp;
    private BigDecimal dealAmountInOrderingCurrency;
}
