package com.cauamattosprj.freeze.modules.finances.personal.balance;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BalanceRepository extends JpaRepository<Balance, UUID> {
    Balance getBalanceById(UUID id);
}
