package dsw.finApp.v1.budget.service;

import dsw.finApp.domain.Constants;
import dsw.finApp.exception.domain.ObjectNotFoundException;
import dsw.finApp.exception.util.ExceptionUtils;
import dsw.finApp.v1.budget.domain.BudgetEntity;
import dsw.finApp.v1.budget.repository.BudgetRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BudgetService {
    private final BudgetRepository repository;

    public BudgetService(BudgetRepository repository) {
        this.repository = repository;
    }

    public Page<BudgetEntity> findAll(Pageable pageable, String budgetName, Double budgetLimit) {
        boolean hasBudgetName = budgetName != null && !budgetName.isBlank();
        boolean hasLimit = budgetLimit != null;
        boolean hasBoth = hasBudgetName && hasLimit;

        if (hasBoth)
            return repository.findByBudgetNameContainingIgnoreCaseOrBudgetLimitContainingIgnoreCase(pageable, budgetName, budgetLimit);
        if (hasLimit)
            return repository.findByBudgetLimitContainingIgnoreCase(pageable, budgetLimit);
        if (hasBudgetName)
            return repository.findByBudgetNameContainingIgnoreCase(pageable, budgetName);

        return repository.findAll(pageable);
    }

    public BudgetEntity findByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new ObjectNotFoundException(Constants.BUDGET_NOT_FOUND));
    }

    @Transactional
    public BudgetEntity insert(BudgetEntity budgetEntity) {
        try {
            return repository.save(budgetEntity);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionUtils.buildSameIdentifierException(Constants.BUDGET_DUPLICATED);
        }
    }

    @Transactional
    public BudgetEntity update(String code, BudgetEntity budgetEntity) {
        var existentEntity = findByCode(code);
        existentEntity.setCode(code);
        existentEntity.setBudgetName(budgetEntity.getBudgetName());
        existentEntity.setBudgetLimit(budgetEntity.getBudgetLimit());
        try {
            return repository.save(existentEntity);
        }catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.BUDGET_NOT_PERSISTED);
        }
    }

    @Transactional
    public void deleteByCode(String code) {
        try{
            var entity = findByCode(code);
            repository.delete(entity);
        }catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.BUDGET_DELETION_ERROR);
        }
    }
}
