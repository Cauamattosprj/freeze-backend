package com.cauamattosprj.freeze.modules.finances.personal.creditcard;

import com.cauamattosprj.freeze.modules.finances.personal.expense.Expense;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "credit_cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String label;
    @Column(name = "holder_name")
    private String holderName;
    @Column(name = "limit_amount")
    private Double limitAmount;
    private String brand;
    @Column(name = "due_date")
    private Date dueDate;
    @Column(name = "user_id")
    private UUID userId;

    public CreditCard() {}

    public CreditCard(CreditCardDTO dto) {
        this.label = dto.getLabel();
        this.holderName = dto.getHolderName();
        this.limitAmount = dto.getLimitAmount();
        this.brand = dto.getBrand();
        this.dueDate = dto.getDueDate();
    }
}
