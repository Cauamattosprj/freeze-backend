package com.cauamattosprj.freeze.modules.finances.personal.income;

import com.cauamattosprj.freeze.modules.finances.personal.income.enums.IncomeStatus;
import com.cauamattosprj.freeze.modules.users.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository repository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public List<IncomeDTO> findAll() {
        return repository.findAllByUserId(authenticatedUserService.getAuthenticatedUserId()).stream().map(IncomeDTO::new).toList();
    }

    public List<IncomeDTO> findAllReceived() {
        return repository.findAllByStatusIsAndUserId(IncomeStatus.RECEIVED, authenticatedUserService.getAuthenticatedUserId()).stream().map(IncomeDTO::new).toList();
    }

    public IncomeDTO findById(UUID id) {
        return new IncomeDTO(findOwnedIncome(id));
    }

    public IncomeDTO create(IncomeDTO dto) {
        Income entity = new Income(dto);
        entity.setId(null);
        entity.setUserId(authenticatedUserService.getAuthenticatedUserId());
        return new IncomeDTO(repository.save(entity));
    }

    public IncomeDTO update(UUID id, IncomeDTO dto) {
        Income existing = findOwnedIncome(id);
        if (dto.getLabel() != null) existing.setLabel(dto.getLabel());
        if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getDueDate() != null) existing.setDueDate(dto.getDueDate());
        if (dto.getCategory() != null) existing.setCategory(dto.getCategory());
        return new IncomeDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        repository.delete(findOwnedIncome(id));
    }

    private Income findOwnedIncome(UUID id) {
        Income entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Income not found"));
        UUID authUserId = authenticatedUserService.getAuthenticatedUserId();
        if (!authUserId.equals(entity.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Income not found");
        }
        return entity;
    }
}
