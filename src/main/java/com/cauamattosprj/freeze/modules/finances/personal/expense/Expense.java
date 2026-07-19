package com.cauamattosprj.freeze.modules.finances.personal.expense;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String label;
    private Integer amount;
    private String status;
    private Date dueDate;
    private String category;

    public Expense() {}

    public Expense(ExpenseDTO dto) {
        this.label = dto.getLabel();
        this.amount = dto.getAmount();
        this.status = dto.getStatus();
        this.dueDate = dto.getDueDate();
        this.category = dto.getCategory();
    }
}
