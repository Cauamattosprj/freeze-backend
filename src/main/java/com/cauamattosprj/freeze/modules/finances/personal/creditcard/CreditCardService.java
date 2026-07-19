package com.cauamattosprj.freeze.modules.finances.personal.creditcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository repository;

    public List<CreditCardDTO> findAll() {
        return repository.findAll().stream().map(CreditCardDTO::new).toList();
    }

    public CreditCardDTO findById(UUID id) {
        return new CreditCardDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit card not found with id: " + id)));
    }

    public CreditCardDTO create(CreditCardDTO dto) {
        CreditCard entity = new CreditCard(dto);
        entity.setId(null);
        return new CreditCardDTO(repository.save(entity));
    }

    public CreditCardDTO update(UUID id, CreditCardDTO dto) {
        CreditCard existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit card not found with id: " + id));
        if (dto.getLabel() != null) existing.setLabel(dto.getLabel());
        if (dto.getHolderName() != null) existing.setHolderName(dto.getHolderName());
        if (dto.getNumber() != null) existing.setNumber(dto.getNumber());
        if (dto.getExpiry() != null) existing.setExpiry(dto.getExpiry());
        if (dto.getCvv() != null) existing.setCvv(dto.getCvv());
        if (dto.getLimitAmount() != null) existing.setLimitAmount(dto.getLimitAmount());
        if (dto.getBrand() != null) existing.setBrand(dto.getBrand());
        if (dto.getDueDate() != null) existing.setDueDate(dto.getDueDate());
        return new CreditCardDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        CreditCard existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit card not found with id: " + id));
        repository.delete(existing);
    }
}
