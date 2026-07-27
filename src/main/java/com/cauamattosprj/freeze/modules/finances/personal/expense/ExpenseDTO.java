package com.cauamattosprj.freeze.modules.finances.personal.expense;

import com.cauamattosprj.freeze.modules.finances.personal.expense.enums.ExpenseStatus;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ExpenseDTO {
    private UUID id;
    private String label;
    private Integer amount;
    private ExpenseStatus status;
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
        if (entity.getCreditCard() != null) {
            this.creditCardId = entity.getCreditCard().getId();
        }
    }
}
