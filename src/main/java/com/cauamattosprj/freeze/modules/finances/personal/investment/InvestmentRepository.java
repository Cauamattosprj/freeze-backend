package com.cauamattosprj.freeze.modules.finances.personal.investment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvestmentRepository extends JpaRepository<Investment, UUID> {
    List<Investment> findAllByUserId(UUID userId);
}
