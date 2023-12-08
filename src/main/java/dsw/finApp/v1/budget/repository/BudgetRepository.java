package dsw.finApp.v1.budget.repository;

import dsw.finApp.v1.budget.domain.BudgetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {

    Optional<BudgetEntity> findByCode(String code);

    Page<BudgetEntity> findAll(Pageable pageable);

    Page<BudgetEntity> findByBudgetLimitContainingIgnoreCase(Pageable pageable, Double budgetLimit);

    Page<BudgetEntity> findByBudgetNameContainingIgnoreCase(Pageable pageable, String budgetName);

    Page<BudgetEntity> findByBudgetNameContainingIgnoreCaseOrBudgetLimitContainingIgnoreCase(Pageable pageable, String budgetName, Double budgetLimit);
}
