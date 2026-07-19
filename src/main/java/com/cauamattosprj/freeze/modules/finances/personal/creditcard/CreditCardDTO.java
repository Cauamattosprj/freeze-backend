package com.cauamattosprj.freeze.modules.finances.personal.creditcard;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CreditCardDTO {
    private UUID id;
    private String label;
    private String holderName;
    private String number;
    private String expiry;
    private String cvv;
    private Double limitAmount;
    private String brand;
    private Date dueDate;

    public CreditCardDTO() {}

    public CreditCardDTO(CreditCard entity) {
        this.id = entity.getId();
        this.label = entity.getLabel();
        this.holderName = entity.getHolderName();
        this.number = entity.getNumber();
        this.expiry = entity.getExpiry();
        this.cvv = entity.getCvv();
        this.limitAmount = entity.getLimitAmount();
        this.brand = entity.getBrand();
        this.dueDate = entity.getDueDate();
    }
}
