package com.cauamattosprj.freeze.modules.finances.personal.deposit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepositService {

    @Autowired
    private DepositRepository repository;

    public List<DepositDTO> findAll() {
        return repository.findAll().stream().map(DepositDTO::new).toList();
    }

    public DepositDTO findById(UUID id) {
        return new DepositDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deposit not found with id: " + id)));
    }

    public DepositDTO create(DepositDTO dto) {
        Deposit entity = new Deposit(dto);
        entity.setId(null);
        return new DepositDTO(repository.save(entity));
    }

    public DepositDTO update(UUID id, DepositDTO dto) {
        Deposit existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deposit not found with id: " + id));
        if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
        if (dto.getDate() != null) existing.setDate(dto.getDate());
        return new DepositDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        Deposit existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deposit not found with id: " + id));
        repository.delete(existing);
    }
}
