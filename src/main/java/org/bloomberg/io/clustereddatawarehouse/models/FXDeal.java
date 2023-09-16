package org.bloomberg.io.clustereddatawarehouse.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fx_deals")
public class FXDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deal_id", nullable = false)
    private String dealUniqueId;
    @Column(name = "ordering_currency", nullable = false)
    private Currency fromCurrency;
    @Column(name = "to_currency", nullable = false)
    private Currency toCurrency;
    @Column(name = "deal_timestamp", nullable = false)
    private LocalDateTime dealTimestamp;
    @Column(name = "amount_ordering_currency", nullable = false)
    private BigDecimal dealAmountInOrderingCurrency;
}
