package com.cauamattosprj.freeze.modules.finances.personal.income;

import com.cauamattosprj.freeze.modules.finances.personal.income.enums.IncomeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID> {
    Collection<Income> findAllByUserId(UUID userId);

    Collection<Income> findAllByStatusIsAndUserId(IncomeStatus status, UUID userId);
}
