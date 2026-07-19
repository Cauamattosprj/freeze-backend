package com.cauamattosprj.freeze.modules.finances.personal.income;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID> {
}
