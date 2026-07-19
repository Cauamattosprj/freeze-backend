package com.cauamattosprj.freeze.modules.finances.personal.deposit;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class DepositDTO {
    private UUID id;
    private Double amount;
    private Date date;

    public DepositDTO() {}

    public DepositDTO(Deposit entity) {
        this.id = entity.getId();
        this.amount = entity.getAmount();
        this.date = entity.getDate();
    }
}
