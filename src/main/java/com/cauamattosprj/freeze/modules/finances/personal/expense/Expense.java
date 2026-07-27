package com.cauamattosprj.freeze.modules.finances.personal.expense;

import com.cauamattosprj.freeze.modules.finances.personal.creditcard.CreditCard;
import com.cauamattosprj.freeze.modules.finances.personal.expense.enums.ExpenseStatus;
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
    private ExpenseStatus status;
    @Column(name = "due_date")
    private Date dueDate;
    private String category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;

    public Expense() {}

    public Expense(ExpenseDTO dto) {
        this.label = dto.getLabel();
        this.amount = dto.getAmount();
        this.status = dto.getStatus();
        this.dueDate = dto.getDueDate();
        this.category = dto.getCategory();
    }


}
