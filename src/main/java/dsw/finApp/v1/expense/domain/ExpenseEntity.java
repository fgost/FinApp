package dsw.finApp.v1.expense.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses")
public class ExpenseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String expenseName;
    private Double expensePrice;
//    private ExpenseTypeEnum expenseType;
//    private ExpenseCategoryEnum expenseCategory;

    @PrePersist
    private void setCode() {
        this.code = UUID.randomUUID().toString();
    }
}
