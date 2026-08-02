package com.cauamattosprj.freeze.modules.finances.personal.expense;

import com.cauamattosprj.freeze.modules.finances.personal.creditcard.CreditCard;
import com.cauamattosprj.freeze.modules.finances.personal.creditcard.CreditCardRepository;
import com.cauamattosprj.freeze.modules.finances.personal.expense.enums.ExpenseStatus;
import com.cauamattosprj.freeze.modules.users.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public List<ExpenseDTO> findAll() {
        return repository.findAllByUserId(authenticatedUserService.getAuthenticatedUserId()).stream().map(ExpenseDTO::new).toList();
    }

    public List<ExpenseDTO> findAllByCreditCard(UUID creditCardId) {
        CreditCard creditCard = findOwnedCreditCard(creditCardId);
        return repository.findAllByCreditCard_Id(creditCard.getId()).stream().map(ExpenseDTO::new).toList();
    }

    public List<ExpenseDTO> findAllPaid() {
        return repository.findAllByStatusAndUserId(ExpenseStatus.PAID, authenticatedUserService.getAuthenticatedUserId()).stream().map(ExpenseDTO::new).toList();
    }

    public ExpenseDTO findById(UUID id) {
        return new ExpenseDTO(findOwnedExpense(id));
    }

    public ExpenseDTO create(ExpenseDTO dto) {
        Expense entity = new Expense(dto);
        entity.setId(null);
        entity.setUserId(authenticatedUserService.getAuthenticatedUserId());
        if (dto.getCreditCardId() != null) {
            entity.setCreditCard(findOwnedCreditCard(dto.getCreditCardId()));
        }
        return new ExpenseDTO(repository.save(entity));
    }

    public ExpenseDTO update(UUID id, ExpenseDTO dto) {
        Expense existing = findOwnedExpense(id);
        if (dto.getLabel() != null) existing.setLabel(dto.getLabel());
        if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getDueDate() != null) existing.setDueDate(dto.getDueDate());
        if (dto.getCategory() != null) existing.setCategory(dto.getCategory());
        if (dto.getCreditCardId() != null) {
            existing.setCreditCard(findOwnedCreditCard(dto.getCreditCardId()));
        }
        return new ExpenseDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        repository.delete(findOwnedExpense(id));
    }

    private Expense findOwnedExpense(UUID id) {
        Expense entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));
        UUID authUserId = authenticatedUserService.getAuthenticatedUserId();
        if (!authUserId.equals(entity.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found");
        }
        return entity;
    }

    private CreditCard findOwnedCreditCard(UUID creditCardId) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found"));
        UUID authUserId = authenticatedUserService.getAuthenticatedUserId();
        if (!authUserId.equals(creditCard.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found");
        }
        return creditCard;
    }
}
