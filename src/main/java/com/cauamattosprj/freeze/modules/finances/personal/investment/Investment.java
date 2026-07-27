package com.cauamattosprj.freeze.modules.finances.personal.investment;

import com.cauamattosprj.freeze.modules.finances.personal.deposit.Deposit;
import com.cauamattosprj.freeze.modules.finances.personal.investment.enums.InvestmentCategory;
import com.cauamattosprj.freeze.modules.finances.personal.investment.enums.InvestmentType;
import com.cauamattosprj.freeze.modules.finances.personal.investment.enums.RentabilityPeriod;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "investments")
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String label;
    @Column(name = "initial_amount")
    private Double initialAmount;
    @Column(name = "rentability_rate")
    private Double rentabilityRate;

    @Enumerated(EnumType.STRING)
    private RentabilityPeriod rentabilityPeriod;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "investment_id")
    private List<Deposit> deposits = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private InvestmentType type;

    @Enumerated(EnumType.STRING)
    private InvestmentCategory category;

    public Investment() {}

    public Investment(InvestmentDTO dto) {
        this.label = dto.getLabel();
        this.initialAmount = dto.getInitialAmount();
        this.rentabilityRate = dto.getRentabilityRate();
        this.rentabilityPeriod = dto.getRentabilityPeriod();
        this.type = dto.getType();
        this.category = dto.getCategory();
        if (dto.getDeposits() != null) {
            this.deposits = dto.getDeposits().stream().map(Deposit::new).toList();
        }
    }
}
