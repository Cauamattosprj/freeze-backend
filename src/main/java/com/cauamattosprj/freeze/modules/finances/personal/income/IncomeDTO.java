package com.cauamattosprj.freeze.modules.finances.personal.income;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class IncomeDTO {
    private UUID id;
    private String label;
    private Integer amount;
    private String status;
    private Date dueDate;
    private String category;

    public IncomeDTO() {}

    public IncomeDTO(Income entity) {
        this.id = entity.getId();
        this.label = entity.getLabel();
        this.amount = entity.getAmount();
        this.status = entity.getStatus();
        this.dueDate = entity.getDueDate();
        this.category = entity.getCategory();
    }
}
