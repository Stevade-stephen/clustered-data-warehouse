package org.bloomberg.io.clustereddatawarehouse.repositories;


import org.bloomberg.io.clustereddatawarehouse.models.FXDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FXDealRepository extends JpaRepository<FXDeal, Long> {

    Optional<FXDeal> findByDealUniqueId(String dealUniqueId);


}

