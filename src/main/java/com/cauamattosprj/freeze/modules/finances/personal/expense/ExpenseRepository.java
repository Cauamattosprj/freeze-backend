package com.cauamattosprj.freeze.modules.finances.personal.expense;

import com.cauamattosprj.freeze.modules.finances.personal.creditcard.CreditCard;
import com.cauamattosprj.freeze.modules.finances.personal.expense.enums.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    Collection<Expense> findAllByCreditCard_Id(UUID creditCardId);

    Collection<Expense> findAllByStatus(ExpenseStatus status);
}
