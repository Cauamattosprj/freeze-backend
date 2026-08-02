package com.cauamattosprj.freeze.modules.finances.personal.creditcard;

import com.cauamattosprj.freeze.modules.users.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository repository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public List<CreditCardDTO> findAll() {
        return repository.findAllByUserId(authenticatedUserService.getAuthenticatedUserId()).stream().map(CreditCardDTO::new).toList();
    }

    public CreditCardDTO findById(UUID id) {
        return new CreditCardDTO(findOwnedCreditCard(id));
    }

    public CreditCardDTO create(CreditCardDTO dto) {
        CreditCard entity = new CreditCard(dto);
        entity.setId(null);
        entity.setUserId(authenticatedUserService.getAuthenticatedUserId());
        return new CreditCardDTO(repository.save(entity));
    }

    public CreditCardDTO update(UUID id, CreditCardDTO dto) {
        CreditCard existing = findOwnedCreditCard(id);
        if (dto.getLabel() != null) existing.setLabel(dto.getLabel());
        if (dto.getHolderName() != null) existing.setHolderName(dto.getHolderName());
        if (dto.getLimitAmount() != null) existing.setLimitAmount(dto.getLimitAmount());
        if (dto.getBrand() != null) existing.setBrand(dto.getBrand());
        if (dto.getDueDate() != null) existing.setDueDate(dto.getDueDate());
        return new CreditCardDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        repository.delete(findOwnedCreditCard(id));
    }

    private CreditCard findOwnedCreditCard(UUID id) {
        CreditCard entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found"));
        UUID authUserId = authenticatedUserService.getAuthenticatedUserId();
        if (!authUserId.equals(entity.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found");
        }
        return entity;
    }
}
