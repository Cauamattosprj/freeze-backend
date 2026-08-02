package com.cauamattosprj.freeze.modules.finances.personal.investment;

import com.cauamattosprj.freeze.modules.finances.personal.deposit.Deposit;
import com.cauamattosprj.freeze.modules.users.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository repository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public List<InvestmentDTO> findAll() {
        return repository.findAllByUserId(authenticatedUserService.getAuthenticatedUserId()).stream().map(InvestmentDTO::new).toList();
    }

    public InvestmentDTO findById(UUID id) {
        return new InvestmentDTO(findOwnedInvestment(id));
    }

    public InvestmentDTO create(InvestmentDTO dto) {
        Investment entity = new Investment(dto);
        entity.setId(null);
        UUID authUserId = authenticatedUserService.getAuthenticatedUserId();
        entity.setUserId(authUserId);
        if (entity.getDeposits() != null) {
            entity.getDeposits().forEach(d -> {
                d.setId(null);
                d.setUserId(authUserId);
            });
        }
        return new InvestmentDTO(repository.save(entity));
    }

    public InvestmentDTO update(UUID id, InvestmentDTO dto) {
        Investment existing = findOwnedInvestment(id);
        if (dto.getLabel() != null) existing.setLabel(dto.getLabel());
        if (dto.getInitialAmount() != null) existing.setInitialAmount(dto.getInitialAmount());
        if (dto.getRentabilityRate() != null) existing.setRentabilityRate(dto.getRentabilityRate());
        if (dto.getRentabilityPeriod() != null) existing.setRentabilityPeriod(dto.getRentabilityPeriod());
        if (dto.getType() != null) existing.setType(dto.getType());
        if (dto.getCategory() != null) existing.setCategory(dto.getCategory());
        if (dto.getDeposits() != null) {
            existing.getDeposits().clear();
            dto.getDeposits().forEach(depositDTO -> {
                Deposit deposit = new Deposit(depositDTO);
                deposit.setUserId(authenticatedUserService.getAuthenticatedUserId());
                existing.getDeposits().add(deposit);
            });
        }
        return new InvestmentDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        repository.delete(findOwnedInvestment(id));
    }

    private Investment findOwnedInvestment(UUID id) {
        Investment entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Investment not found"));
        UUID authUserId = authenticatedUserService.getAuthenticatedUserId();
        if (!authUserId.equals(entity.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Investment not found");
        }
        return entity;
    }
}
