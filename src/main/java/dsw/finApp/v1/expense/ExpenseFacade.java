package dsw.finApp.v1.expense;

import dsw.finApp.utils.pagination.DefaultWrapper;
import dsw.finApp.utils.pagination.PaginationRequest;
import dsw.finApp.utils.pagination.PaginationUtils;
import dsw.finApp.v1.expense.mapper.ExpenseRequestMapper;
import dsw.finApp.v1.expense.mapper.ExpenseResponseMapper;
import dsw.finApp.v1.expense.model.request.ExpenseRequest;
import dsw.finApp.v1.expense.model.response.ExpenseResponse;
import dsw.finApp.v1.expense.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ExpenseFacade {
    private final ExpenseService expenseService;
    private final PaginationUtils paginationUtils;

    public DefaultWrapper findAll(PaginationRequest paginationRequest, String expenseName, Double price) {
        Pageable pageable = paginationUtils.getPageableWithUserPreferencesSupport(paginationRequest);
        var entities = expenseService.findAll(pageable, expenseName, price);
        var dtos = entities.getContent().stream().map(ExpenseResponseMapper.INSTANCE::mapEntityToResponse).collect(Collectors.toList());
        var page = PaginationUtils.pageOf(List.copyOf(dtos), pageable, entities.getTotalElements());
        var wrapper = new DefaultWrapper(page);
        return wrapper;
    }

    public ExpenseResponse findByCode(String code) {
        var entity = expenseService.findByCode(code);
        var dto = ExpenseResponseMapper.INSTANCE.mapEntityToExpenseResponse(entity);
        return dto;
    }

    public ExpenseResponse insert(ExpenseRequest input) {
        var entity = ExpenseRequestMapper.INSTANCE.mapModelToEntity(input);
        var savedEntity = expenseService.insert(entity);
        var dto = ExpenseResponseMapper.INSTANCE.mapEntityToExpenseResponse(savedEntity);
        return dto;
    }

    public ExpenseResponse update(String code, ExpenseRequest request) {
        var entity = ExpenseRequestMapper.INSTANCE.mapModelToEntity(request);
        var savedEntity = expenseService.update(code, entity);
        var dto = ExpenseResponseMapper.INSTANCE.mapEntityToResponse(savedEntity);
        return dto;
    }

    public void deleteByCode(String code) {
        expenseService.deleteByCode(code);
    }

    public void deleteById(String code) {
        expenseService.deleteById(code);
    }
}
