package com.cauamattosprj.freeze.modules.finances.personal.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    public List<ExpenseDTO> findAll() {
        return repository.findAll().stream().map(ExpenseDTO::new).toList();
    }

    public ExpenseDTO findById(UUID id) {
        return new ExpenseDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id)));
    }

    public ExpenseDTO create(ExpenseDTO dto) {
        Expense entity = new Expense(dto);
        entity.setId(null);
        return new ExpenseDTO(repository.save(entity));
    }

    public ExpenseDTO update(UUID id, ExpenseDTO dto) {
        Expense existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        if (dto.getLabel() != null) existing.setLabel(dto.getLabel());
        if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getDueDate() != null) existing.setDueDate(dto.getDueDate());
        if (dto.getCategory() != null) existing.setCategory(dto.getCategory());
        return new ExpenseDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        Expense existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        repository.delete(existing);
    }
}
