package com.cauamattosprj.freeze.modules.finances.personal.deposit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, UUID> {
}
