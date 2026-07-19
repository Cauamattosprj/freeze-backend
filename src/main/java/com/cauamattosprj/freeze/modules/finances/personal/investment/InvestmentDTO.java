package com.cauamattosprj.freeze.modules.finances.personal.investment;

import com.cauamattosprj.freeze.modules.finances.personal.deposit.DepositDTO;
import com.cauamattosprj.freeze.modules.finances.personal.investment.enums.InvestmentCategory;
import com.cauamattosprj.freeze.modules.finances.personal.investment.enums.InvestmentType;
import com.cauamattosprj.freeze.modules.finances.personal.investment.enums.RentabilityPeriod;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class InvestmentDTO {
    private UUID id;
    private String label;
    private Double initialAmount;
    private Double rentabilityRate;
    private RentabilityPeriod rentabilityPeriod;
    private InvestmentType type;
    private InvestmentCategory category;
    private List<DepositDTO> deposits = new ArrayList<>();

    public InvestmentDTO() {}

    public InvestmentDTO(Investment entity) {
        this.id = entity.getId();
        this.label = entity.getLabel();
        this.initialAmount = entity.getInitialAmount();
        this.rentabilityRate = entity.getRentabilityRate();
        this.rentabilityPeriod = entity.getRentabilityPeriod();
        this.type = entity.getType();
        this.category = entity.getCategory();
        if (entity.getDeposits() != null) {
            this.deposits = entity.getDeposits().stream().map(DepositDTO::new).toList();
        }
    }
}
