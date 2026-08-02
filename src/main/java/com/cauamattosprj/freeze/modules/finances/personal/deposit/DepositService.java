package com.cauamattosprj.freeze.modules.finances.personal.deposit;

import com.cauamattosprj.freeze.modules.users.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class DepositService {

    @Autowired
    private DepositRepository repository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public List<DepositDTO> findAll() {
        return repository.findAllByUserId(authenticatedUserService.getAuthenticatedUserId()).stream().map(DepositDTO::new).toList();
    }

    public DepositDTO findById(UUID id) {
        return new DepositDTO(findOwnedDeposit(id));
    }

    public DepositDTO create(DepositDTO dto) {
        Deposit entity = new Deposit(dto);
        entity.setId(null);
        entity.setUserId(authenticatedUserService.getAuthenticatedUserId());
        return new DepositDTO(repository.save(entity));
    }

    public DepositDTO update(UUID id, DepositDTO dto) {
        Deposit existing = findOwnedDeposit(id);
        if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
        if (dto.getDate() != null) existing.setDate(dto.getDate());
        return new DepositDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        Deposit existing = findOwnedDeposit(id);
        repository.delete(existing);
    }

    private Deposit findOwnedDeposit(UUID id) {
        Deposit entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deposit not found"));
        UUID authUserId = authenticatedUserService.getAuthenticatedUserId();
        if (!authUserId.equals(entity.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deposit not found");
        }
        return entity;
    }
}
