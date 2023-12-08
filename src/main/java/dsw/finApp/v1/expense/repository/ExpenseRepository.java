package dsw.finApp.v1.expense.repository;

import dsw.finApp.v1.expense.domain.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    Optional<ExpenseEntity> findByExpenseName(String expenseName);

    Optional<ExpenseEntity> findByCode(String code);

    Page<ExpenseEntity> findAll(Pageable pageable);

    Page<ExpenseEntity> findByExpensePriceContainingIgnoreCase(Pageable pageable, Double expensePrice);

    Page<ExpenseEntity> findByExpenseNameContainingIgnoreCase(Pageable pageable, String expenseName);

    Page<ExpenseEntity> findByExpenseNameContainingIgnoreCaseOrExpensePriceContainingIgnoreCase(Pageable pageable, String expenseName, Double expensePrice);
}
