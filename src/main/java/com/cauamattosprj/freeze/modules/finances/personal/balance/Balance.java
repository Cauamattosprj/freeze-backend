package com.cauamattosprj.freeze.modules.finances.personal.balance;

import com.cauamattosprj.freeze.modules.finances.personal.expense.Expense;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Setter
@Getter
@Table(name = "balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "initial_amount")
    private Integer initialAmount;
    @Column(name = "user_id")
    private UUID userId;
}
