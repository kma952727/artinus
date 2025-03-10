package com.artinus.membership.domain.subscription.repository;

import com.artinus.membership.domain.subscription.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanJpaRepository extends JpaRepository<Plan, Long> {
    @Query("""
                select pl
                from Plan pl
                join fetch pl.channel ch
                where ch.id = :channelId
                and pl.isDefault = true
               """
    )
    Optional<Plan> findByChannelId(@Param("channelId") Long channelId);
}
