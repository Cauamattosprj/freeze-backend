package com.cauamattosprj.freeze.modules.finances.personal.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository repository;

    public List<IncomeDTO> findAll() {
        return repository.findAll().stream().map(IncomeDTO::new).toList();
    }

    public IncomeDTO findById(UUID id) {
        return new IncomeDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id: " + id)));
    }

    public IncomeDTO create(IncomeDTO dto) {
        Income entity = new Income(dto);
        entity.setId(null);
        return new IncomeDTO(repository.save(entity));
    }

    public IncomeDTO update(UUID id, IncomeDTO dto) {
        Income existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id: " + id));
        if (dto.getLabel() != null) existing.setLabel(dto.getLabel());
        if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getDueDate() != null) existing.setDueDate(dto.getDueDate());
        if (dto.getCategory() != null) existing.setCategory(dto.getCategory());
        return new IncomeDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        Income existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id: " + id));
        repository.delete(existing);
    }
}
