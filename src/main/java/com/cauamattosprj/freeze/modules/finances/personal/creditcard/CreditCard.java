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
    private String holderName;
    private String number;
    private String expiry;
    private String cvv;
    private Double limitAmount;
    private String brand;
    private Date dueDate;

    public CreditCard() {}

    public CreditCard(CreditCardDTO dto) {
        this.label = dto.getLabel();
        this.holderName = dto.getHolderName();
        this.number = dto.getNumber();
        this.expiry = dto.getExpiry();
        this.cvv = dto.getCvv();
        this.limitAmount = dto.getLimitAmount();
        this.brand = dto.getBrand();
        this.dueDate = dto.getDueDate();
    }
}
