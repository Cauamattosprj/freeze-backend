package com.cauamattosprj.freeze.modules.finances.personal.investment;

import com.cauamattosprj.freeze.modules.finances.personal.deposit.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository repository;

    public List<InvestmentDTO> findAll() {
        return repository.findAll().stream().map(InvestmentDTO::new).toList();
    }

    public InvestmentDTO findById(UUID id) {
        return new InvestmentDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found with id: " + id)));
    }

    public InvestmentDTO create(InvestmentDTO dto) {
        Investment entity = new Investment(dto);
        entity.setId(null);
        if (entity.getDeposits() != null) {
            entity.getDeposits().forEach(d -> d.setId(null));
        }
        return new InvestmentDTO(repository.save(entity));
    }

    public InvestmentDTO update(UUID id, InvestmentDTO dto) {
        Investment existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found with id: " + id));
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
                existing.getDeposits().add(deposit);
            });
        }
        return new InvestmentDTO(repository.save(existing));
    }

    public void delete(UUID id) {
        Investment existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found with id: " + id));
        repository.delete(existing);
    }
}
