package com.capitole.zara.products.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RateJpaRepository extends JpaRepository<RateEntity, Long> {

    @Query("Select t from RateEntity t where (:date Between t.startDate and t.endDate) and " +
            " t.productId = :productId and t.brandId = :brandId")
    List<RateEntity> searchByDateAndProductIdAndBrandId(LocalDateTime date, Integer productId, Integer brandId);
}
