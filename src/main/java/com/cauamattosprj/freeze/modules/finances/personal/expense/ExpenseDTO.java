package com.cauamattosprj.freeze.modules.finances.personal.expense;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ExpenseDTO {
    private UUID id;
    private String label;
    private Integer amount;
    private String status;
    private Date dueDate;
    private String category;
    private UUID creditCardId;

    public ExpenseDTO() {}

    public ExpenseDTO(Expense entity) {
        this.id = entity.getId();
        this.label = entity.getLabel();
        this.amount = entity.getAmount();
        this.status = entity.getStatus();
        this.dueDate = entity.getDueDate();
        this.category = entity.getCategory();
        this.creditCardId = entity.getCreditCard().getId();
    }
}
