package com.cauamattosprj.freeze.modules.finances.personal.balance;

import com.cauamattosprj.freeze.modules.finances.personal.expense.ExpenseDTO;
import com.cauamattosprj.freeze.modules.finances.personal.expense.ExpenseService;
import com.cauamattosprj.freeze.modules.finances.personal.income.Income;
import com.cauamattosprj.freeze.modules.finances.personal.income.IncomeDTO;
import com.cauamattosprj.freeze.modules.finances.personal.income.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Service
public class BalanceService {
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private BalanceRepository repository;

    @GetMapping()
    public Integer getBalance() {
        List<IncomeDTO> receivedIncomes = incomeService.findAllReceived();
        List<ExpenseDTO> paidExpenses = expenseService.findAllPaid();
        Integer balanceInitialAmount = 0;
//        Balance balanceInitialAmount = repository.getBalanceById(balanceId);

        Integer totalIncomesAmount = 0;
        Integer totalExpensesAmount = 0;


        for (IncomeDTO income : receivedIncomes) {
            totalIncomesAmount += income.getAmount();
        }

        for (ExpenseDTO expense : paidExpenses) {
            totalExpensesAmount += expense.getAmount();
        }

        Integer balance = balanceInitialAmount + (totalIncomesAmount - totalExpensesAmount);
        return balance;
    }
}
