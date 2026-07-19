package com.cauamattosprj.freeze.modules.finances.personal.deposit;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Double amount;
    private Date date;

    public Deposit() {}

    public Deposit(DepositDTO dto) {
        this.id = dto.getId();
        this.amount = dto.getAmount();
        this.date = dto.getDate();
    }
}
