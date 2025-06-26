package com.capitole.zara.products.adapter.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Rate")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_cadena")
    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Column(name = "price_list")
    private Integer idRate;
    private Integer productId;
    private Integer priority;
    private BigDecimal price;
    private String currency;
}
