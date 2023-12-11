package dsw.finApp.v1.expense.service;

import dsw.finApp.domain.Constants;
import dsw.finApp.exception.domain.ObjectNotFoundException;
import dsw.finApp.exception.util.ExceptionUtils;
import dsw.finApp.v1.expense.domain.ExpenseEntity;
import dsw.finApp.v1.expense.repository.ExpenseRepository;
import dsw.finApp.v1.users.domain.Expense;
import dsw.finApp.v1.users.domain.Preference;
import dsw.finApp.v1.users.domain.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.function.Predicate;

@AllArgsConstructor
@Service
public class ExpenseService {
    private final ExpenseRepository repository;

    public Page<ExpenseEntity> findAll(Pageable pageable, String expenseName, Double price) {
        boolean hasExpenseName = expenseName != null && !expenseName.isBlank();
        boolean hasPrice = price != null;
        boolean hasBoth = hasExpenseName && hasPrice;

        if (hasBoth)
            return repository.findByExpenseNameContainingIgnoreCaseOrExpensePriceContainingIgnoreCase(pageable, expenseName, price);
        if (hasPrice)
            return repository.findByExpensePriceContainingIgnoreCase(pageable, price);
        if (hasExpenseName)
            return repository.findByExpenseNameContainingIgnoreCase(pageable, expenseName);

        return repository.findAll(pageable);
    }

    public ExpenseEntity findByCode(String code) {
        var entity = repository.findByCode(code)
                .orElseThrow(() -> new ObjectNotFoundException(Constants.EXPENSE_NOT_FOUND));
        return entity;
    }

    @Transactional
    public ExpenseEntity insert(ExpenseEntity expenseEntity) {
        try {
            return repository.save(expenseEntity);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionUtils.buildSameIdentifierException(Constants.EXPENSE_DUPLICATED);
        }
    }

    @Transactional
    public ExpenseEntity update(String code, ExpenseEntity expenseEntity) {
        var existentEntity = findByCode(code);
        existentEntity.setCode(code);
        existentEntity.setExpenseName(expenseEntity.getExpenseName());
        existentEntity.setExpensePrice(expenseEntity.getExpensePrice());
        existentEntity.setExpenseType(expenseEntity.getExpenseType());
        var dto = existentEntity;
        try {
            return repository.save(dto);
        }catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.EXPENSE_NOT_PERSISTED);
        }
    }

    @Transactional
    public void deleteByCode(String code) {
        try{
            var entity = findByCode(code);
            repository.delete(entity);
        }catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.EXPENSE_DELETION_ERROR);
        }
    }

    @Transactional
    public void deleteById(String code) {
        try{
            var expense = findByCode(code);
            Long id = expense.getId();
            repository.deleteAssociationByExpenseId(id);
        }catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.EXPENSE_DELETION_ERROR);
        }
    }
}
