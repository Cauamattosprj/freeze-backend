package com.cauamattosprj.freeze.modules.finances.personal.income;

import com.cauamattosprj.freeze.modules.finances.personal.income.enums.IncomeStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "incomes")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String label;
    private Integer amount;
    private IncomeStatus status;
    @Column(name = "due_date")
    private Date dueDate;
    private String category;
    @Column(name = "user_id")
    private UUID userId;

    public Income() {}

    public Income(IncomeDTO dto) {
        this.label = dto.getLabel();
        this.amount = dto.getAmount();
        this.status = dto.getStatus();
        this.dueDate = dto.getDueDate();
        this.category = dto.getCategory();
    }
}
